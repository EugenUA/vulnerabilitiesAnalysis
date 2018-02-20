package ui;

import entities.dbEntities.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.Console;
import java.util.Scanner;

/**
 * Created by Eugen on 23.08.2017.
 */
public class UserAuthentication {

    private User user;
    private Scanner scanner;
    private String name = null;
    private static String password1 = null;
    private String password2 = null;
    private String email = null;

    private Console console;


    public UserAuthentication(){
        console = System.console();
        scanner = new Scanner(System.in);
    }

    public User authenticateUser(){

        System.out.println(" ----- NEW USER REGISTRATION: ----- ");
        System.out.println("NOTE: Every field is mandatory!");
        System.out.print("ENTER YOUR NAME: ");
        name = scanner.nextLine();
        System.out.print("ENTER YOUR E-MAIL: ");
        email = scanner.nextLine();
        System.out.print("ENTER YOUR SECRET PASSWORD: ");
        password1 = scanner.nextLine();
        System.out.print("CONFIRM YOUR PASSWORD: ");
        password2 = scanner.nextLine();

        if(name != null && password1 != null && password2 != null && email != null &&
                name.length()>0 && password1.length()>0 && password2.length()>0 && email.length()>0){
            if(DigestUtils.sha256Hex(password1).equals(DigestUtils.sha256Hex(password2))) {
                user = new User(null, name, DigestUtils.sha256Hex(password1), email,false);
            } else {
                System.out.print("ERROR: ");
                System.out.println("Password and it's confirmation are not equal!");
                System.out.println(" ----- REGISTRATION WAS NOT SUCCESSFUL! ----- ");
                System.out.println();
                return null;
            }
        } else {
            System.out.print("ERROR: ");
            System.out.println("Every field is mandatory!");
            System.out.println(" ----- REGISTRATION WAS NOT SUCCESSFUL! ----- ");
            System.out.println();
            return null;
        }
        return user;
    }


}
