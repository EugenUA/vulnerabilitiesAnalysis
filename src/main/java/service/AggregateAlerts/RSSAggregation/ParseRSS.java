package service.AggregateAlerts.RSSAggregation;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import entities.aggregationEntities.RSS;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.ProgramService.ReadPropertiesFile.GetConfigValues;
import service.ServiceException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class ParseRSS {

    private final static Logger logger = LogManager.getLogger(ParseRSS.class);
    private ArrayList<RSS> rssAlerts= new ArrayList<RSS>();
    private GetConfigValues configValues = new GetConfigValues();
    private String[] rssSources;

    public ParseRSS(){
    }

    public ArrayList<RSS> getRssAlerts() {
        return this.rssAlerts;
    }

    public void readRSSSources() throws ServiceException{

        try {
            configValues.getConfigValues();
        } catch(IOException e) {
            throw new ServiceException("Unable to read config file");
        }

        rssSources = configValues.getRssSources();

        for(String eachSource : rssSources){
            readRSS(eachSource);
        }
    }

    private void readRSS(String feedURL) throws ServiceException {
        try{
            URL feedUrl = new URL(feedURL);

            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));

            for (Iterator i = feed.getEntries().iterator(); i.hasNext();) {
                SyndEntry entry = (SyndEntry) i.next();

                RSS rssAlert = new RSS();

                rssAlert.setOrigin(feed.getTitle());
                rssAlert.setTitle(entry.getTitle());
                rssAlert.setDescription(entry.getDescription().getValue());
                rssAlert.setLink(entry.getLink());
                rssAlert.setPubDate(entry.getPublishedDate().toString());

                rssAlerts.add(rssAlert);

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
