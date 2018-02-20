package service.ProgramService.SendGreetingEmail;

import entities.dbEntities.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.ProgramService.ReadPropertiesFile.GetConfigValues;
import service.ServiceException;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Properties;

public class SendGreetingEmail {

    private static final Logger logger = LogManager.getLogger(SendGreetingEmail.class);
    GetConfigValues configValues;
    String destinationAddress = null;
    String userName = null;
    String userPassword = null;

    public SendGreetingEmail(User user) {
        configValues = new GetConfigValues();

        this.destinationAddress = user.getEmail();
        this.userName = user.getName();
        this.userPassword = user.getPassword();
    }

    public void sendGreetingMessage() throws ServiceException {

        try{
            configValues.getConfigValues();
        } catch(IOException e){
            logger.debug(e.getMessage());
            throw new ServiceException(e.getMessage());
        }

        String from = configValues.getEmail();
        final String user = from;
        final String password = configValues.getPassword();


        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(destinationAddress));

            // Set Subject: header field
            message.setSubject("Welcome to Vulnerabilities Aggregation Service!");

            // Now set the actual message
            message.setText("You are successfully registered to the Vulnerabilities Aggregation Service! \n \n" +
                    "YOUR PERSONAL INFORMATION: \n" +
                    "Login Name: " + userName + " \n" +
                    "Password: " + userPassword + "\n  \n" +
                    "If you don't want to have an account in Vulnerabilities Aggregation Service, please login and " +
                    "go to your personal cabinet, then choose the option to delete your account. \n" +
                    "If after the deletion you want to restore your account, please go to registration page and enter " +
                    "your old credentials. After this your old page will be restored. \n" +
                    "The developer is thankful for your registration! \n" +
                    "Best regards, \n" +
                    "Eugen Gruzdev.");

            // Send message
            Transport.send(message);

            System.out.println("----- You will receive the greeting message which confirms your registration! -----");
        } catch (MessagingException e) {
            logger.debug(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }
}
