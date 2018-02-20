package ui;

import entities.dbEntities.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.SimpleService.Service;
import service.ServiceException;

import java.util.Scanner;

/**
 * Created by Eugen on 24.08.2017.
 */
public class UpdateUserCredentials {

    private static final Logger logger = LogManager.getLogger(UpdateUserCredentials.class);

    private User user;
    private Service service;
    private Scanner scanner;
    private String key;
    private boolean flag = true;

    public UpdateUserCredentials(User user, Service service){
        this.user = user;
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public User updateCredentials(){
        System.out.println(" ----- UPDATE USER CREDENTIALS ----- ");

        while(flag){
            System.out.println("LEGEND:");
            System.out.println("PRESS 'N' if you want to change your name;");
            System.out.println("PRESS 'E' if you want to change your email;");
            System.out.println("PRESS 'P' if you want to change your password;");
            System.out.println("PRESS 'D' if you want to delete your account;");
            System.out.println("PRESS 'Q' if you want to return to your cabinet.");
            System.out.print("PRESS THE KEY:");
            key = scanner.nextLine();

            if(key.equals("N") || key.equals("n")){
                String newName = null;
                String oldName = null;
                System.out.print("ENTER YOUR OLD NAME: ");
                oldName = scanner.nextLine();
                System.out.print("ENTER NEW NAME: ");
                newName = scanner.nextLine();
                if(oldName.equals(user.getName())) {
                    if (newName != null && newName.length() > 0) {
                        try {
                            user = service.updateName(user, newName);
                            System.out.println(" ----- YOUR NAME WAS SUCCESSFULLY CHANGED ----- ");
                        } catch (ServiceException e) {
                            logger.debug(e.getMessage());
                        }
                    } else {
                        System.out.println("ERROR: YOU ENTERED INVALID NAME!");
                    }
                } else {
                    System.out.println("YOUR INPUT FOR OLD NAME DOES NOT COINCIDE WITH YOUR NAME IN THE DATABASE!");
                }
            }

            if(key.equals("E") || key.equals("e")){
                String newEmail= null;
                System.out.print("ENTER NEW EMAIL: ");
                newEmail = scanner.nextLine();
                if (newEmail != null && newEmail.length() > 0) {
                    try {
                        user = service.updateEmail(user, newEmail);
                        System.out.println(" ----- YOUR EMAIL WAS SUCCESSFULLY CHANGED ----- ");
                    } catch (ServiceException e) {
                        logger.debug(e.getMessage());
                    }
                } else {
                    System.out.println("ERROR: YOU ENTERED INVALID EMAIL!");
                }
            }

            if(key.equals("P") || key.equals("p")){
                String newPassword = null;
                String oldPassword = null;
                System.out.print("ENTER YOUR OLD PASSWORD: ");
                oldPassword = scanner.nextLine();
                System.out.print("ENTER NEW PASSWORD: ");
                newPassword = scanner.nextLine();
                if(DigestUtils.sha256Hex(oldPassword).equals(user.getPassword())) {
                    if (newPassword != null && newPassword.length() > 0) {
                        try {
                            user = service.updatePassword(user, DigestUtils.sha256Hex(newPassword));
                            System.out.println(" ----- YOUR PASSWORD WAS SUCCESSFULLY CHANGED ----- ");
                        } catch (ServiceException e) {
                            logger.debug(e.getMessage());
                        }
                    } else {
                        System.out.println("ERROR: YOU ENTERED INVALID PASSWORD!");
                    }
                } else {
                    System.out.println("YOUR INPUT FOR OLD PASSWORD DOES NOT COINCIDE WITH YOUR PASSWORD IN THE DATABASE!");
                }
            }

            if(key.equals("D") || key.equals("d")){
                String answer;
                System.out.println("DO YOU REALLY WANT TO DELETE YOUR ACCOUNT? (Type: 'yes' or 'no')");
                System.out.print("INPUT YOUR DECISION HERE: ");
                answer = scanner.nextLine();
                if(answer.equals("yes")){
                    try{
                        user = service.deleteAccount(user);
                        flag = false;
                        System.out.println("----- YOUR ACCOUNT IS DELETED! -----");
                        break;
                    } catch(ServiceException e){
                        logger.debug(e.getMessage());
                    }
                }
            }

            if(key.equals("Q") || key.equals("q")){
                flag = false;
                break;
            }
        }

        return user;
    }

}