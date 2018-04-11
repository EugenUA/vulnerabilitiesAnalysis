package ui;

import entities.dbEntities.User;
import jline.console.ConsoleReader;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.ProgramService.SendGreetingEmail.SendGreetingEmail;
import service.SimpleService.Service;
import service.ServiceException;
import ui.Authentication_UpdateCredentials.UserAuthentication;

import java.io.Console;
import java.util.Scanner;

/**
 * Created by Eugen on 23.08.2017.
 */
public class WelcomePage {

    private Scanner scanner;
    private Service service;
    private String key;
    private boolean flag = true;
    private boolean regFlag = false;
    private UserAuthentication ua;
    private User user;

    private SendGreetingEmail sendEmail;

    private static final Logger logger = LogManager.getLogger(WelcomePage.class);

    public WelcomePage(Service service){
        scanner = new Scanner(System.in);
        this.service = service;
        ua = new UserAuthentication();
    }

    public User welcome(){
        System.out.println("WELCOME TO VULNERABILITIES AGGREGATION SERVICE!");
        System.out.println("This software application requires all users to be registered!");

        while(flag) {
            System.out.println("PRESS 'R' if you want to register or 'L' if you are already registered.");
            System.out.println("PRESS 'Q' if you want to quit.");
            System.out.print("PRESS THE KEY: ");
            key = scanner.nextLine();
            System.out.println();

            if(key.equals("R") || key.equals("r")){
                user = ua.authenticateUser();
                if(user != null) {
                    try {
                        User testUser = null;
                        testUser = this.service.ifUserIsUnique(user);
                        if(testUser == null){
                            user = this.service.createUser(user);
                            regFlag = true;
                            System.out.println(" ----- REGISTRATION SUCCESSFULLY COMPLETED! ----- ");
                            this.sendEmail = new SendGreetingEmail(user);
                            sendEmail.sendGreetingMessage();
                            System.out.println();
                        } else {
                            user = null;
                            System.out.print("ERROR: ");
                            System.out.println("USER WITH SAME NAME OR EMAIL EXISTS ALREADY!");
                            System.out.println(" ----- REGISTRATION WAS NOT SUCCESSFUL! ----- ");
                            System.out.println();
                        }
                    } catch (ServiceException e) {
                        logger.debug(e.getMessage());
                    }
                }
            }

            if(key.equals("L") || key.equals("l") || regFlag){
                System.out.println(" ----- USER LOGIN ----- ");
                System.out.print("ENTER YOUR NAME: ");
                String name = scanner.nextLine();
                System.out.print("ENTER YOUR PASSWORD: ");
                String password = scanner.nextLine();
                try{
                    user = this.service.selectUser(name, DigestUtils.sha256Hex(password));
                    if(user != null){
                        flag = false;
                        regFlag = false;
                        System.out.println();
                        System.out.println("During next 3 Minutes new software advisories will be aggregated.");
                        System.out.println("The aggregation procedure repeats every 3 Minutes automatically.");
                        System.out.println();
                        return user;
                    } else {
                        System.out.println("YOU ENTERED WRONG CREDENTIALS! TRY AGAIN!");
                        System.out.println(" ----- USER LOGIN WAS NOT SUCCESSFUL ----- ");
                        System.out.println();
                    }
                } catch(ServiceException e){
                    logger.fatal(e.getMessage());
                }
            }

            if(key.equals("Q") || key.equals("q")){
                System.out.println("THE VULNERABILITY AGGREGATION SYSTEM CLOSES! BYE!");
                System.exit(0);
            }

            System.out.println("WRONG INPUT! Try Again!");
            System.out.println();
        }
        return user;
    }

}