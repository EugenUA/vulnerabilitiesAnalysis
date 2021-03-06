package service.TextMining;

import entities.aggregationEntities.Email;
import entities.aggregationEntities.HTML;
import entities.aggregationEntities.RSS;
import entities.miningEntities.MiningEntity;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.jsoup.Jsoup;
import service.TextMining.Correlation.Classification.Classification;
import service.TextMining.Correlation.Clustering.Clustering;
import service.TextMining.Correlation.Preprocessing.Preprocessing;
import service.TextMining.SaveVulnerabilities.SaveVulnerabilitiesToDB;


public class TextMiningMainEntrance {

    private static final Logger logger = Logger.getLogger(TextMiningMainEntrance.class);

    /* RECEIVED ADVISORIES FROM ALL THE SOURCES */
    private ArrayList<Email> receivedEmails;
    private ArrayList<RSS> receivedRSS;
    private ArrayList<HTML> receivedHTML;

    /* ENTITIES FOR MINING FROM ADVISORIES */
    private ArrayList<MiningEntity> miningEntities;

    public TextMiningMainEntrance() {

        miningEntities = new ArrayList<MiningEntity>();
    }

    public TextMiningMainEntrance(ArrayList<Email> emails, ArrayList<RSS> rss, ArrayList<HTML> html) {
        this.receivedEmails = emails;
        this.receivedRSS = rss;
        this.receivedHTML = html;

        miningEntities = new ArrayList<MiningEntity>();
    }

    public ArrayList<MiningEntity> getMiningEntities() {
        return miningEntities;
    }

    public void setReceivedEmails(ArrayList<Email> receivedEmails) {
        this.receivedEmails = receivedEmails;
    }

    public void setReceivedRSS(ArrayList<RSS> receivedRSS) {
        this.receivedRSS = receivedRSS;
    }

    public void setReceivedHTML(ArrayList<HTML> receivedHTML) {
        this.receivedHTML = receivedHTML;
    }

    public void buildMiningEntities(){
        if(receivedEmails.size() > 0){
            for(Email email : receivedEmails){
                MiningEntity miningEntity = new MiningEntity();
                miningEntity.setShort_description(email.getSubject().replaceAll("\\[.*?\\] ",""));
                miningEntity.setLong_description(deletePGPSigning(email.getContent()));
                if(email.getReceivedDate() == null) {
                    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    miningEntity.setDate(date);
                }else{
                    miningEntity.setDate(email.getReceivedDate());
                }
                miningEntity.setSource(email.getTo());
                miningEntity.setSource_type("EMAIL");

                miningEntities.add(miningEntity);
            }
        }
        if(receivedRSS.size() > 0){
            for(RSS rss : receivedRSS){
                MiningEntity miningEntity = new MiningEntity();
                miningEntity.setShort_description(rss.getTitle().replaceAll("\\[.*?\\] ",""));
                miningEntity.setLong_description(html2text(rss.getDescription()));
                miningEntity.setDate(rss.getPubDate());
                miningEntity.setSource(rss.getLink());
                miningEntity.setSource_type("RSS");

                miningEntities.add(miningEntity);
            }
        }
        if(receivedHTML.size() > 0){
            for(HTML html : receivedHTML){
                MiningEntity miningEntity = new MiningEntity();
                miningEntity.setShort_description(html.getTitle().replaceAll("\\[.*?\\] ",""));
                miningEntity.setLong_description(html2text(html.getDescription()));
                miningEntity.setDate(html.getPubDate());
                miningEntity.setSource(html.getLink());
                miningEntity.setSource_type("HTML");

                miningEntities.add(miningEntity);
            }
        }
    }

    public String deletePGPSigning(String message){
        if(message.contains("BEGIN PGP SIGNED MESSAGE")){
            message = message.substring(message.indexOf('\n')+1);
            message = message.substring(message.indexOf('\n')+1);
            message = message.substring(message.indexOf('\n')+1);
            message = message.substring(message.indexOf('\n')+1);


            int index = message.indexOf("-----BEGIN PGP SIGNATURE");
            message = message.substring(0,index-1);
        }

        return message;
    }

    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }

    public void performTextMining(){

        /*System.out.println("Emails: " + receivedEmails.size());
        System.out.println("RSS: " + receivedRSS.size());
        System.out.println("HTML: " + receivedHTML.size());*/

        this.buildMiningEntities();

        Preprocessing preprocessing = new Preprocessing(this.miningEntities);
        preprocessing.conductPreprocessingStep();

        Classification classification = new Classification(this.miningEntities);
        classification.classify();
        classification.createVectorSpaceModel();

        Clustering clustering = new Clustering(this.miningEntities);
        clustering.conductClustering();

        SaveVulnerabilitiesToDB saveVulnerabilities = new SaveVulnerabilitiesToDB(
                clustering.getMiningEntities(), clustering.getInternalClusters());
        saveVulnerabilities.saveVulnerabilities();

        /* For Testing purposes */
       /* for(MiningEntity min : miningEntities){
            System.out.println(min.getPreprocessed_short_description());
            System.out.println(min.getPreprocessed_long_description());
            System.out.println();
        }*/

    }
}

