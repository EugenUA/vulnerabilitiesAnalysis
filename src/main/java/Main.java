import entities.dbEntities.User;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import service.AggregateAlerts.AggregationMainEntrance;
import service.SimpleService.Service;
import service.ServiceException;
import service.SimpleService.SimpleService;
import service.TextMining.TextMiningMainEntrance;
import ui.UserCabinet.UserCabinet;
import ui.WelcomePage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);
    // Enabling of polling:
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) {
        try {
            BasicConfigurator.configure();
            Service service = new SimpleService();

            boolean flag;
            do {
                flag = false;

                WelcomePage wPage = new WelcomePage(service);
                User user = wPage.welcome();

                AggregationMainEntrance aggregationMainEntrance = new AggregationMainEntrance(user);
                TextMiningMainEntrance textMiningMainEntrance = new TextMiningMainEntrance();
                scheduler.scheduleAtFixedRate(()->{
                     /* POLLING BEGIN */

                     // aggregate alerts from all the sources
                     aggregationMainEntrance.aggregateSecurityAlerts();

                     //System.out.println("AGGREGATION END");

                     // create mining entities and feed them to text mining module
                     textMiningMainEntrance.setReceivedEmails(aggregationMainEntrance.getReceivedEmails());
                     textMiningMainEntrance.setReceivedHTML(aggregationMainEntrance.getReceivedHTML());
                     textMiningMainEntrance.setReceivedRSS(aggregationMainEntrance.getReceivedRSS());

                     // conduct text mining
                     textMiningMainEntrance.performTextMining();

                     //System.out.println("TEXT MINING END");

                     /* END OF POLLING */
                }, 0, 3L, TimeUnit.MINUTES);  // initial delay: 0 Minutes;  delay: 3 Minutes


                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                }

                if (user != null) {
                    UserCabinet userCabinet = new UserCabinet(user, service);
                    userCabinet.enterUserCabinet();

                    if (user.getIsDeleted()) {
                        flag = true;
                    }
                }
            } while (flag);

        } catch (ServiceException e) {
            logger.fatal(e.getMessage());
        }
    }
}