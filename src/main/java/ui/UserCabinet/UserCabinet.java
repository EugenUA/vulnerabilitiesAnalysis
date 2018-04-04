package ui.UserCabinet;
import entities.dbEntities.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.SimpleService.Service;
import ui.Authentication_UpdateCredentials.UpdateUserCredentials;
import ui.Vulnerability_Product_Search.ProductsSearch;
import ui.Vulnerability_Product_Search.VulnerabilitiesSearch;
import ui.WelcomePage;

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
            System.out.println("--------");
            System.out.println("PRESS 'U' to update your credentials, change password or delete your page;");
            System.out.println("--------");
            System.out.println("PRESS 'P' to search for products");
            System.out.println("PRESS 'V' to search for vulnerabilities");
            System.out.println("--------");
            System.out.println("PRESS 'S' to see some statistics");
            System.out.println("--------");
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

            if(key.equals("P") || key.equals("p")){
                ProductsSearch pp = new ProductsSearch(user,service);
                pp.searchByProduct();
            }

            if(key.equals("V") || key.equals("v")){
                VulnerabilitiesSearch vs = new VulnerabilitiesSearch(user, service);
                vs.searchbyVulnerability();
            }

            if(key.equals("Q") || key.equals("q")){
                System.out.println("THE VULNERABILITY AGGREGATION SYSTEM CLOSES! BYE!");
                System.exit(0);
            }
        }
    }

}
