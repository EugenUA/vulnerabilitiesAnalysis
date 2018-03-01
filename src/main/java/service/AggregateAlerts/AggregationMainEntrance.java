package service.AggregateAlerts;

import entities.aggregationEntities.Email;
import entities.dbEntities.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.AggregateAlerts.EmailAggregation.FetchingIncomingEmails;
import service.ServiceException;

import java.util.ArrayList;

public class AggregationMainEntrance {

    public static final Logger logger = LogManager.getLogger(AggregationMainEntrance.class);

    /* Aggregated Advisories Storage */
    private ArrayList<Email> receivedEmails;

    private User user;

    public AggregationMainEntrance(User user){
        this.user = user;

        receivedEmails = new ArrayList<Email>();
    }

    public void aggregateSecurityAlerts(){

        /* AGGREGATION PROCEDURE */

        System.out.println("*********************************");
        System.out.println("ATTENTION!");
        System.out.println("Security advisories aggregation and processing procedures started!");
        System.out.println("It can take up to 5 minutes");

        //receivedEmails = aggregateSecurityAlertsIncomingFromEmail();

        System.out.println("Aggregation procedure successfully completed!");
        System.out.println("*********************************");
        System.out.println();


    }

    private ArrayList<Email> aggregateSecurityAlertsIncomingFromEmail(){
        /* EMAIL */
        try {
            FetchingIncomingEmails fetchEmail = new FetchingIncomingEmails();
            fetchEmail.fetch();
            ArrayList<Email> emails = fetchEmail.getIncomingEmails();
            System.out.println("Email checked! " + "Number of received advisories: " + emails.size() );

            // Print Emails
           /* for(Email email : emails){
                System.out.println(email);
            } */

            return emails;
        } catch(ServiceException e){
            System.out.println("Due to connection problems the email box cannot be checked!");
        }
        return null; // should not be reachable
        /* END EMAILS PARSING */
    }

}
