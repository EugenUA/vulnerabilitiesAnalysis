package service.AggregateAlerts.HTMLAggregation;


import entities.aggregationEntities.HTML;
import entities.dbEntities.VulnerabilitiesSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import service.ProgramService.ReadPropertiesFile.GetConfigValues;
import service.ServiceException;
import service.SimpleService.SimpleService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ParseHTML {

    private static final Logger logger = Logger.getLogger(ParseHTML.class);
    private ArrayList<HTML> htmlAlerts = new ArrayList<HTML>();
    private GetConfigValues configValues = new GetConfigValues();
    private String[] htmlSources;
    private SimpleService service;

    public ParseHTML(){
        try{
            service = new SimpleService();
        } catch(ServiceException e){
            logger.fatal(e.getMessage());
        }
    }

    public ArrayList<HTML> getAlerts(){
        return this.htmlAlerts;
    }

    public void parseHTMLSources() throws ServiceException{

        try {
            configValues.getConfigValues();
        } catch(IOException e) {
            // do not handle
        }

        htmlSources = configValues.getHtmlSources();

        for(String eachSource : htmlSources){
            VulnerabilitiesSource sourceDB = new VulnerabilitiesSource();
            try {
                if (service.getVulnerabilitySourceByLink(eachSource) == null) {

                    sourceDB.setLink(eachSource);

                   /* if(eachSource.contains("freebsd")){
                        sourceDB.setSource_name("FreeBSD");
                    } else if(eachSource.contains("secuniaresearch")){
                        sourceDB.setSource_name("Secunia Research");
                    }*/

                    sourceDB.setSource_type("HTML");

                    Date date = Calendar.getInstance().getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sourceDB.setLast_access(sdf.format(date));

                    service.createVulnerabilitiesSource(sourceDB);

                    readHTML(eachSource, 10, "");

                } else {
                    sourceDB = service.getVulnerabilitySourceByLink(eachSource);
                    readHTML(eachSource,-1,sourceDB.getLast_access());

                    Date date = Calendar.getInstance().getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sourceDB.setLast_access(sdf.format(date));


                    service.updateVulnerabilitiesSource(sourceDB);
                }
            } catch(ServiceException e){
                sourceDB.setNotes(e.getMessage());
                service.updateVulnerabilitiesSource(sourceDB);
            } finally {
                sourceDB.setNotes("OK");
                service.updateVulnerabilitiesSource(sourceDB);
            }
        }
    }

    private void readHTML(String source, int maxNum, String last_date) throws ServiceException {
        if (source.contains("freebsd")) {
            try {
                Document doc = Jsoup.connect(source)
                        .timeout(10000).validateTLSCertificates(false).get();
                Elements newsHeadlines = doc.select("#mp-itn b a");

                Elements elements = doc.select("a[href*=//security.FreeBSD.org/advisories/FreeBSD-SA]");

                int i = 0;
                for (Element elem : elements) {
                    String link = elem.attr("abs:href");

                    Document alertDocument = Jsoup.connect(link).timeout(10000)
                            .validateTLSCertificates(false).get();
                    Element alert = alertDocument.body();
                    if(!alert.text().contains("REVISED")) {
                        HTML htmlAlert = parse_pgp_signed_message(alert.text());

                        if((maxNum != -1) && (maxNum > i) && (last_date.equals(""))) {
                            htmlAlert.setOrigin("FreeBSD");
                            htmlAlert.setLink(link);

                            htmlAlerts.add(htmlAlert);

                            i++;
                        } else if((maxNum == -1) && (!last_date.equals(""))) {

                            //logger.debug("HERE");

                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                Date date = simpleDateFormat.parse(htmlAlert.getPubDate());
                                Date lastDate = simpleDateFormat.parse(last_date);

                                if(lastDate.before(date)) {
                                    htmlAlert.setOrigin("FreeBSD");
                                    htmlAlert.setLink(link);

                                    htmlAlerts.add(htmlAlert);
                                } else {
                                    break;
                                }
                            } catch (ParseException ex) {
                                throw new ServiceException(ex.getMessage());
                            }
                        } else{
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                logger.debug(e.getMessage());
                throw new ServiceException(e.getMessage());
            }
        }

        if(source.contains("secuniaresearch")){

            try {
                Document doc = Jsoup.connect(source)
                        .timeout(10000).validateTLSCertificates(false).get();
                Elements newsHeadlines = doc.select("#mp-itn b a");

                Elements elements = doc.select("a[href*=/secunia_research/]");

                int i = 0;
                for (Element elem : elements) {
                    String link = elem.attr("abs:href");

                    Document alertDocument = Jsoup.connect(link).timeout(10000)
                            .validateTLSCertificates(false).get();
                    Element alert = alertDocument.body();
                    HTML htmlAlert = this.parse_pgp_signed_message2(alert.text());

                    if((maxNum != -1) && (maxNum > i)) {
                        // find title:
                        // Group of all h-Tags
                        Elements hTags = alert.select("h1, h2, h3, h4, h5, h6");

                        // Group of all h1-Tags
                        Elements h1Tags = hTags.select("h1");

                        String title = h1Tags.text().substring(h1Tags.text().indexOf("Secunia Research: ")
                                + "Secunia Research: ".length());

                        htmlAlert.setOrigin("Secunia Research");
                        htmlAlert.setLink(link);
                        htmlAlert.setTitle(title);

                        htmlAlerts.add(htmlAlert);
                        i++;
                    } else if((maxNum == -1) && (!last_date.equals(""))) {

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy/MM/dd");
                        try {
                            Date date = simpleDateFormat1.parse(htmlAlert.getPubDate());
                            Date lastDate = simpleDateFormat.parse(last_date);

                            if(lastDate.before(date)) {
                                // find title:
                                // Group of all h-Tags
                                Elements hTags = alert.select("h1, h2, h3, h4, h5, h6");

                                // Group of all h1-Tags
                                Elements h1Tags = hTags.select("h1");

                                String title = h1Tags.text().substring(h1Tags.text().indexOf("Secunia Research: ")
                                        + "Secunia Research: ".length());

                                htmlAlert.setOrigin("Secunia Research");
                                htmlAlert.setLink(link);
                                htmlAlert.setTitle(title);

                                htmlAlerts.add(htmlAlert);
                            } else {
                                break;
                            }
                        } catch (ParseException ex) {
                            throw new ServiceException(ex.getMessage());
                        }
                    } else{
                        break;
                    }
                }
            } catch (IOException e) {
                logger.debug(e.getMessage());
                throw new ServiceException(e.getMessage());
            }
        }
    }


    /* Used for FreeBSD */
    private HTML parse_pgp_signed_message(String message){
        HTML resultAlert = new HTML();
        /* Delete everything after Solution */
        // Deletion begins with "VI.   Correction details
        String result = message.substring(0, message.indexOf("VI"));

        /* Get title */
        if(result.contains("Topic") && result.contains("Category")) {
            String title = result.substring(result.indexOf("Topic: ") + "Topic: ".length(), result.indexOf("Category"));
            resultAlert.setTitle(title);
        }

        /* Get pubDate */
        if(result.contains("Announced")) {
            String pubDate = result.substring(result.indexOf("Announced: ") + "Announced: ".length(),
                    result.indexOf("Announced: ") + "Announced: ".length() + 10);
            resultAlert.setPubDate(pubDate);
        }

        /* Get Description */
        if(result.contains("CVE")) {
            String description = result.substring(result.indexOf("CVE"));
            resultAlert.setDescription(description);
        } else if (result.contains("Background")) {
            String description = result.substring(result.indexOf("Background"));
            resultAlert.setDescription(description);
        }
        return resultAlert;
    }


    /* Used for Secunia Alerts */
    private HTML parse_pgp_signed_message2(String message){
        HTML resultAlert = new HTML();
        message = message.substring(message.indexOf("="));

        /* Get pubDate */
        if(message.contains("Secunia Research")){
            String pubDate = message.substring(message.indexOf("Secunia Research ") + "Secunia Research ".length(),
                    message.indexOf("Secunia Research ") + "Secunia Research ".length() + 10);
            resultAlert.setPubDate(pubDate);
        }

        /* Get description */
        String resultDescription = message;
        if(message.contains("Affected Software")){
            resultDescription = message.substring(message.indexOf("1) Affected Software"));
        }
        if(message.contains("Time Table")){
            resultDescription = resultDescription.substring(0, resultDescription.indexOf("Time Table")-3);
        }

        resultAlert.setDescription(resultDescription);

        return resultAlert;
    }
}
