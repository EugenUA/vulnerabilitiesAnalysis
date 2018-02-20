package ui;
import entities.dbEntities.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.SimpleService.Service;

import java.util.Scanner;

/**
 * Created by Eugen on 24.08.2017.
 */
public class UserCabinet {

    private static final Logger logger = LogManager.getLogger(WelcomePage.class);

    private User user;
    private Service service;
    private Scanner scanner;
    private String key;
    private boolean flag = true;


    public UserCabinet(User inputUser, Service service){
        this.user = inputUser;
        this.service = service;
        scanner = new Scanner(System.in);
    }

    public void enterUserCabinet(){
        System.out.println("---------------------------------------------------------------");
        System.out.println("-_-_-_-_-_-_-_- VULNERABILITY AGGREGATION SERVICE -_-_-_-_-_-_-");
        System.out.println("---------------------------------------------------------------");
        System.out.println("WELCOME TO YOUR PERSONAL CABINET, " + user.getName() + "!" );

        while(flag){
            System.out.println("LEGEND:");
            System.out.println("PRESS 'U' to update your credentials, change password or delete your page;");
            // TODO OTHER POSSIBILITIES FOR USER (incl. VULNER. AGGR.)
            System.out.println("PRESS 'Q' to quit the application.");
            System.out.print("PRESS A KEY: ");
            key = scanner.nextLine();

            if(key.equals("U") || key.equals("u")){
                UpdateUserCredentials uUC = new UpdateUserCredentials(user, service);
                user = uUC.updateCredentials();
                if(user.getIsDeleted()){
                    flag = false;
                    break;
                } else {
                    System.out.println("WELCOME TO YOUR PERSONAL CABINET, " + user.getName() + "!");
                }
            }

            if(key.equals("Q") || key.equals("q")){
                System.out.println("THE VULNERABILITY AGGREGATION SYSTEM CLOSES! BYE!");
                System.exit(0);
            }
        }
    }

}
