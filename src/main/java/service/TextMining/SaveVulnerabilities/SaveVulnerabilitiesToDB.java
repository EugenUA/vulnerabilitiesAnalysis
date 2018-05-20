package service.TextMining.SaveVulnerabilities;

import entities.dbEntities.Description;
import entities.dbEntities.Product;
import entities.dbEntities.VulProd;
import entities.dbEntities.Vulnerability;
import entities.miningEntities.MiningEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.ServiceException;
import service.SimpleService.SimpleService;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SaveVulnerabilitiesToDB {

    private final static Logger logger = Logger.getLogger(SaveVulnerabilitiesToDB.class);
    private ArrayList<MiningEntity> miningEntities;
    private ArrayList<ArrayList<Integer>> internalClusters;

    private SimpleService simpleService;

    private int VulnerabilitiesCounter = 0;
    private int Productcounter = 0;

    public SaveVulnerabilitiesToDB(ArrayList<MiningEntity> miningEntities,
                                   ArrayList<ArrayList<Integer>> internalClusters){
        this.miningEntities = miningEntities;
        this.internalClusters = internalClusters;
        try {
            this.simpleService = new SimpleService();
        } catch(ServiceException e){
            // do not handle
        }
    }

    public void saveVulnerabilities(){

        for(ArrayList<Integer> cluster : internalClusters){

            /* Create DB Entities */
            Description description = new Description();
            ArrayList<Vulnerability> vulnerabilities = new ArrayList<Vulnerability>();
            Product product = new Product();
            VulProd vulProd = new VulProd();

            /* CVEs */
            ArrayList<String> cves = new ArrayList<String>();

            /* CVSSs */
            String cvss = null;

            for(Integer clusterElement : cluster){
                /* Fill in DB entities */
                /* Vulnerabilites */
                String longDescription = miningEntities.get(clusterElement).getLong_description();
                ArrayList<String> CVEcandidates = this.obtainAllRelevantCVEs(longDescription);
                for(String eachCandidate : CVEcandidates){
                    if(!cves.contains(eachCandidate)){
                        cves.add(eachCandidate);
                    }
                }
                if(cvss == null){
                    cvss = this.cvssObtainer(longDescription);
                }

                /* Description */
                if(description.getShort_description() == null) {
                    description.setShort_description(miningEntities.get(clusterElement).getShort_description());
                }
                description.setLong_description(description.getLong_description() + " \n " +
                        miningEntities.get(clusterElement).getLong_description());
                if(description.getPreprocessed_short_description() == null){
                    description.setPreprocessed_short_description(
                            StringUtils.join(miningEntities.get(clusterElement).getPreprocessed_short_description(), ','));
                }
                description.setPreprocessed_long_description(description.getPreprocessed_long_description() + " \n " +
                        StringUtils.join(miningEntities.get(clusterElement).getPreprocessed_long_description(), ','));

                /* Product */
                product = this.productFinder(miningEntities.get(clusterElement));
            }

            for(Integer clusterElement : cluster){
                if(cves.size() == 0){
                    Vulnerability vulnerability = new Vulnerability();
                    if(vulnerability.getName() == null){
                        vulnerability.setName(miningEntities.get(clusterElement).getShort_description());
                    }
                    vulnerability.setCvss(cvss);
                    vulnerability.setSource_type(miningEntities.get(clusterElement).getSource_type());
                    vulnerability.setSource(miningEntities.get(clusterElement).getSource());
                    vulnerability.setDate(miningEntities.get(clusterElement).getDate().replaceAll("/","-"));
                    vulnerabilities.add(vulnerability);
                } else {
                    for(String cve : cves){
                        Vulnerability vulnerability = new Vulnerability();
                        if(vulnerability.getName() == null){
                            vulnerability.setName(miningEntities.get(clusterElement).getShort_description());
                        }
                        vulnerability.setCve(cve);
                        vulnerability.setCvss(cvss);
                        vulnerability.setSource_type(miningEntities.get(clusterElement).getSource_type());
                        vulnerability.setSource(miningEntities.get(clusterElement).getSource());
                        vulnerability.setDate(miningEntities.get(clusterElement).getDate().replaceAll("/","-"));
                        if(!vulnerabilities.contains(vulnerability)) {
                            vulnerabilities.add(vulnerability);
                        }
                    }
                }
            }

            /* FROM THIS POINT VULNERABILITIES, DESCRIPTIONS and PRODUCTS ARE ALL SPECIFIED */

            for(Vulnerability vulnerability : vulnerabilities){
                try {
                    /* PUT VULNERABILITY INTO DB */
                    if(simpleService.getVulnerabilityByCVE(vulnerability.getCve()).isEmpty()) {
                        simpleService.createVulnerability(vulnerability);
                        VulnerabilitiesCounter++;

                    /* PUT DESCRIPTION INTO DB */
                        description.setVulnerability_id(vulnerability.getId());
                        simpleService.createDescription(description);

                    /* PUT PRODUCT INTO DB */
                        if (simpleService.getProduct(product) == null) {

                            simpleService.createProduct(product);
                            vulProd = new VulProd(vulnerability.getId(), product.getId());
                            simpleService.createVulProd(vulProd);
                            Productcounter++;

                        } else {

                            Product product1 = simpleService.getProduct(product);
                            vulProd = new VulProd(vulnerability.getId(), product1.getId());
                            simpleService.createVulProd(vulProd);
                        }
                    }

                } catch(ServiceException e){
                    System.out.println(e.getMessage());
                }
            }

        }

        if(miningEntities.size() > 0) {
            System.out.println("-------------------------------------------------------------");
            System.out.println("Aggregated advisories : " + miningEntities.size());
            System.out.println("Inserted new : ");
            System.out.println("              Vulnerabilities : " + VulnerabilitiesCounter);
            System.out.println("              Products        : " + Productcounter);
            System.out.println("-------------------------------------------------------------");
        }

    }

    public ArrayList<String> obtainAllRelevantCVEs(String advisory){
        ArrayList<String> cves = new ArrayList<String>();
        Matcher m = Pattern.compile("CVE-\\d{4}-\\d{4,7}").matcher(advisory);
        while (m.find()) {
            if(!(cves.contains(m.group()) || (advisory.indexOf(m.group()) > 2000))) {
                cves.add(m.group());
            }
        }
        return cves;
    }

    public String cvssObtainer(String advisory){
        /* Patterns for 2 version on CVSS */
        Pattern CVSSv2_PATTERN = Pattern.compile(
                "AV:[NAL]\\/AC:[LMH]\\/A[Uu]:[NSM]\\/C:[NPC]\\/I:[NPC]\\/A:[NPC]");
        Pattern CVSSv3_PATTERN = Pattern.compile(
                "AV:[NALP]\\/AC:[LH]\\/PR:[NLH]\\/UI:[NR]\\/S:[UC]\\/C:[NLH]\\/I:[NLH]\\/A:[NLH]");
        Matcher m = CVSSv2_PATTERN.matcher(advisory);
        while (m.find()) {
            return m.group();
        }
        Matcher m1 = CVSSv3_PATTERN.matcher(advisory);
        while (m1.find()) {
            return m1.group();
        }
        return null; // if no CVSS is specified in the advisory
    }

    public Product productFinder(MiningEntity miningEntity){
        Product product = new Product();
        try {
            HashMap<String, Double> sp = new HashMap<String, Double>();
            HashMap<String, Double> nsp = new HashMap<String, Double>();

            BufferedReader br = new BufferedReader(new FileReader(
                    "src/main/resources/spamVector"));
            String line = "";
            while ((line = br.readLine()) != null) {
                String parts[] = line.split(" ");
                double v = Double.parseDouble(parts[1]);
                sp.put(parts[0], v);
            }

            BufferedReader br1 = new BufferedReader(new FileReader(
                    "src/main/resources/nonspamVector"));
            String line1 = "";
            while ((line1 = br1.readLine()) != null) {
                String parts[] = line1.split(" ");
                double v = Double.parseDouble(parts[1]);
                nsp.put(parts[0], v);
            }

            ArrayList<String> asp = new ArrayList<String>();
            asp.addAll(sp.keySet());
            ArrayList<String> ansp = new ArrayList<String>();
            ansp.addAll(nsp.keySet());


            List<String> short_description = miningEntity.getPreprocessed_short_description();

            short_description.removeAll(asp);
            short_description.removeAll(ansp);

            ArrayList<String> versions = new ArrayList<String>();
            Matcher m = Pattern.compile("(?:(\\d+)\\.)?(?:(\\d+)\\.)?(?:(\\d+)\\.\\d+)").matcher(
                    miningEntity.getLong_description());
            while (m.find()) {
                if(!versions.contains(m.group())){
                    versions.add(m.group());
                }
            }

            product.setName(short_description.toString());
            product.setVersion(versions.toString());

        } catch (FileNotFoundException e){
            // do not handle
        } catch(IOException e){
            // do not handle
        }
        return product;
    }
}
