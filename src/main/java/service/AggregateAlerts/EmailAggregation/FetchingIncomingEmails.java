package service.AggregateAlerts.EmailAggregation;
import service.ProgramService.ReadPropertiesFile.GetConfigValues;
import service.ServiceException;

import entities.aggregationEntities.Email;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.mail.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

public class FetchingIncomingEmails {

    private static final Logger logger = LogManager.getLogger(FetchingIncomingEmails.class);

    private ArrayList<Email> incomingEmails = new ArrayList<Email>();
    private GetConfigValues configValues = new GetConfigValues();

    public ArrayList<Email> getIncomingEmails(){
        return incomingEmails;
    }

    public void fetch() throws ServiceException {
        try {
            configValues.getConfigValues();
        } catch(IOException e) {
            throw new ServiceException("Unable to read config file");
        }

        try {
            // create properties field
            Properties properties = new Properties();
            properties.put("mail.store.protocol", configValues.getStoreProtocol());
            properties.put("mail.pop3s.host", configValues.getHost());
            properties.put("mail.pop3s.port", configValues.getPop3SPort());
            properties.put("mail.pop3s.starttls.enable", "true");
            properties.put("mail.pop3s.ssl.trust", configValues.getHost());
            Session emailSession = Session.getDefaultInstance(properties);
            //emailSession.setDebug(true);

            // create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore(configValues.getStoreProtocol());

            store.connect(configValues.getHost(), configValues.getEmail(), configValues.getPassword());

            // create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in));

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            //System.out.println("messages.length---" + messages.length);

            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                Email email = new Email();
                writePart(message, email);
                incomingEmails.add(email);
            }

            // close the store and folder objects
            emailFolder.close(false);
            store.close();
        } catch(MessagingException e) {
            logger.debug(e.getMessage());
            throw new ServiceException(e.getMessage());
        } catch(IOException e){
            logger.debug(e.getMessage());
            throw new ServiceException(e.getMessage());
        } catch(Exception e){
            logger.debug(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    /*
    * This method checks for content-type
    * based on which, it processes and
    * fetches the content of the message
    */
    public static void writePart(Part p, Email email) throws Exception {
        if (p instanceof Message)
            //Call methos writeEnvelope
            writeEnvelope((Message) p, email);

        //check if the content is plain text
        if (p.isMimeType("text/plain")) {
            //System.out.println((String) p.getContent());
            email.setContent(email.getContent() + p.getContent());
        }
        //check if the content has attachment
        else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) p.getContent();
            int count = mp.getCount();
            for (int i = 0; i < count; i++)
                writePart(mp.getBodyPart(i), email);
        }
        //check if the content is a nested message
        else if (p.isMimeType("message/rfc822")) {
            writePart((Part) p.getContent(), email);
        }
        else if (p.getContentType().contains("image/")) {
            System.out.println("content type" + p.getContentType());
            File f = new File("image" + new Date().getTime() + ".jpg");
            DataOutputStream output = new DataOutputStream(
                    new BufferedOutputStream(new FileOutputStream(f)));
            com.sun.mail.util.BASE64DecoderStream test =
                    (com.sun.mail.util.BASE64DecoderStream) p
                            .getContent();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = test.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        }
        else {
            String htmlFormat = "";
            Object o = p.getContent();
            if (o instanceof String) {
                htmlFormat += (String) o;
            }
            else if (o instanceof InputStream) {
                InputStream is = (InputStream) o;
                is = (InputStream) o;
                int c;
                while ((c = is.read()) != -1)
                    htmlFormat += c;
            }
            else {
                htmlFormat += o.toString();
            }
            email.setHtmlFormat(htmlFormat);
        }

    }

    /*
   * This method would print FROM, TO, SUBJECT and RECEIVED DATE of the message
   */
    public static void writeEnvelope(Message m, Email email) throws Exception {
        Address[] a;

        // FROM
        if ((a = m.getFrom()) != null) {
            for (int j = 0; j < a.length; j++)
                email.setFrom(a[j].toString());
        }

        // TO
        if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
            for (int j = 0; j < a.length; j++)
                email.setTo(a[j].toString());
        }

        // SUBJECT
        if (m.getSubject() != null) {
            email.setSubject(m.getSubject());
        }

        // RECEIVED DATE
        if (m.getReceivedDate() != null) {
            String date = new SimpleDateFormat("yyyy-MM-dd",   Locale.getDefault()).format(m.getReceivedDate());
            email.setReceivedDate(date);
        }
    }
}
