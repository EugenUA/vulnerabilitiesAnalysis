package service.AggregateAlerts;

import entities.aggregationEntities.Email;
import entities.aggregationEntities.RSS;
import entities.dbEntities.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.AggregateAlerts.EmailAggregation.FetchingIncomingEmails;
import service.AggregateAlerts.RSSAggregation.ParseRSS;
import service.ServiceException;

import java.util.ArrayList;

public class AggregationMainEntrance {

    public static final Logger logger = LogManager.getLogger(AggregationMainEntrance.class);

    /* Aggregated Advisories Storage */
    private ArrayList<Email> receivedEmails;
    private ArrayList<RSS> receivedRSS;

    private User user;

    public AggregationMainEntrance(User user){
        this.user = user;

        receivedEmails = new ArrayList<Email>();
        receivedRSS = new ArrayList<RSS>();
    }

    public void aggregateSecurityAlerts(){

        /* AGGREGATION PROCEDURE */

        receivedEmails = aggregateSecurityAlertsIncomingFromEmail();
        receivedRSS = aggregateSecurityAlertsIncomingFromRSSFeeds();

    }

    private ArrayList<Email> aggregateSecurityAlertsIncomingFromEmail(){
        /* EMAIL */
        try {
            FetchingIncomingEmails fetchEmail = new FetchingIncomingEmails();
            fetchEmail.fetch();
            ArrayList<Email> emails = fetchEmail.getIncomingEmails();
            System.out.println("Email checked! " + "Number of received advisories: " + emails.size() );

            // Print Emails
            /*for(Email email : emails){
                System.out.println(email);
            }*/

            return emails;
        } catch(ServiceException e){
            System.out.println("Due to connection problems the email box cannot be checked!");
        }
        return null; // should not be reachable
        /* END EMAILS PARSING */
    }

    private ArrayList<RSS> aggregateSecurityAlertsIncomingFromRSSFeeds(){
        /* RSS-Feed */
        try{
            ParseRSS parseRSSFeed = new ParseRSS();
            parseRSSFeed.readRSSSources();
            ArrayList<RSS> rssAlerts = parseRSSFeed.getRssAlerts();

           /* for(RSSAlert alert : rssAlerts){
                System.out.println("--------------------------------------------");
                System.out.println(alert);
                System.out.println("---------------------------------------------");
            }*/

            System.out.println("RSS-feeds checked! " + "Number of received advisories: " + rssAlerts.size());
            return rssAlerts;
        } catch(ServiceException e) {
            System.out.println("Due to connection problems the RSS-feeds cannot be checked!");
        }
        /* END RSS FEED PARSING */
        return null; // should not be reachable
    }

}
