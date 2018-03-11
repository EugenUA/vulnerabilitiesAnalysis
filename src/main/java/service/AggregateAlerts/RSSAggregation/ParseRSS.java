package service.AggregateAlerts.RSSAggregation;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import entities.aggregationEntities.RSS;
import entities.dbEntities.VulnerabilitiesSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.ProgramService.ReadPropertiesFile.GetConfigValues;
import service.ServiceException;
import service.SimpleService.Service;
import service.SimpleService.SimpleService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ParseRSS {

    private final static Logger logger = LogManager.getLogger(ParseRSS.class);
    private ArrayList<RSS> rssAlerts= new ArrayList<RSS>();
    private GetConfigValues configValues = new GetConfigValues();
    private SimpleService service;
    private String[] rssSources;

    public ParseRSS(){
        try{
            service = new SimpleService();
        } catch (ServiceException e){
            logger.fatal(e.getMessage());
        }
    }

    public ArrayList<RSS> getRssAlerts() {
        return this.rssAlerts;
    }

    public void readRSSSources() throws ServiceException{

        try {
            configValues.getConfigValues();
        } catch(IOException e) {
            // do not handle
        }

        rssSources = configValues.getRssSources();

        for(String eachSource : rssSources){
            VulnerabilitiesSource sourceDB = new VulnerabilitiesSource();
            try{
            if(service.getVulnerabilitySourceByLink(eachSource) == null){

                /* SET SOURCE NAME BY DEFAULT ADVISORIES */
                if(eachSource.contains("us-cert")){
                    sourceDB.setSource_name("US CERT Security Alerts");
                } else if(eachSource.contains("seclists")) {
                    sourceDB.setSource_name("Security List");
                } else if(eachSource.contains("cisco")){
                    sourceDB.setSource_name("Cisco Security Advisories");
                }

                sourceDB.setLink(eachSource);
                sourceDB.setSource_type("RSS");

                java.util.Date date = Calendar.getInstance().getTime();

                sourceDB.setLast_access(date.toString());

                service.createVulnerabilitiesSource(sourceDB);

                readRSS(eachSource, "");

            } else{

                sourceDB = service.getVulnerabilitySourceByLink(eachSource);

                String last_date = sourceDB.getLast_access();

                java.util.Date date = Calendar.getInstance().getTime();
                sourceDB.setLast_access(date.toString());

                service.updateVulnerabilitiesSource(sourceDB);

                readRSS(eachSource, last_date);
            }
            } catch (ServiceException e){
               sourceDB.setNotes(e.getMessage());
               service.updateVulnerabilitiesSource(sourceDB);
            } finally {
                sourceDB.setNotes("OK");
                service.updateVulnerabilitiesSource(sourceDB);
            }
        }
    }

    private void readRSS(String feedURL, String last_date) throws ServiceException {
        try{
            URL feedUrl = new URL(feedURL);

            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));

            for (Iterator i = feed.getEntries().iterator(); i.hasNext();) {
                SyndEntry entry = (SyndEntry) i.next();

                try {

                    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                    Date lastDate = sdf.parse(last_date);
                    Date pubDate = sdf.parse(entry.getPublishedDate().toString());

                    if(last_date.equals("")) {

                        RSS rssAlert = new RSS();

                        rssAlert.setOrigin(feed.getTitle());
                        rssAlert.setTitle(entry.getTitle());
                        rssAlert.setDescription(entry.getDescription().getValue());
                        rssAlert.setLink(entry.getLink());
                        rssAlert.setPubDate(entry.getPublishedDate().toString());

                        rssAlerts.add(rssAlert);
                    } else {
                        if(lastDate.before(pubDate)){
                            RSS rssAlert = new RSS();

                            rssAlert.setOrigin(feed.getTitle());
                            rssAlert.setTitle(entry.getTitle());
                            rssAlert.setDescription(entry.getDescription().getValue());
                            rssAlert.setLink(entry.getLink());
                            rssAlert.setPubDate(entry.getPublishedDate().toString());

                            rssAlerts.add(rssAlert);
                        } else {
                            break;
                        }
                    }
                } catch (ParseException e){
                    logger.debug(e.getMessage());
                    //throw new ServiceException(e.getMessage());
                }

            }

        } catch(MalformedURLException e){
            logger.debug(e.getMessage());
            throw new ServiceException(e.getMessage());
        } catch(IOException e){
            logger.debug(e.getMessage());
            throw new ServiceException(e.getMessage());
        } catch(FeedException e){
            logger.debug(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

}
