package service.ProgramService.ReadPropertiesFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class GetConfigValues {

    private InputStream inputStream;
    private Reader reader;
    private BufferedReader bufferedReader;

    private String email;
    private String password;
    private String host;
    private String storeType;
    private String storeProtocol;
    private String pop3SPort;

    private String[] rssSources;
    private String[] htmlSources;

    public void getConfigValues() throws IOException{
        try{
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            host = prop.getProperty("host");
            storeType = prop.getProperty("storeType");
            storeProtocol = prop.getProperty("storeProtocol");
            pop3SPort = prop.getProperty("pop3sPort");
            email = prop.getProperty("email");
            password = prop.getProperty("password");


            String filePathRSS = "src/main/resources/rss.txt";
            Path path = Paths.get(filePathRSS);
            long rssNumber = Files.lines(path).count();
            rssSources = new String[Math.toIntExact(rssNumber)];

            /* Reading rss file */
            String url;
            int counter = 0;
            inputStream = new FileInputStream(filePathRSS);
            reader = new InputStreamReader(inputStream, "UTF-8");
            bufferedReader = new BufferedReader(reader);
            while((url = bufferedReader.readLine()) != null){
                if(url != null || !url.equals("") || !url.equals(" ")) {
                    rssSources[counter] = url;
                    counter++;
                }
            }

            String filePathHTML = "src/main/resources/html.txt";
            Path pathHTML = Paths.get(filePathHTML);
            long htmlNumber = Files.lines(pathHTML).count();
            htmlSources = new String[Math.toIntExact(htmlNumber)];

            /* Reading rss file */
            counter = 0;
            inputStream = new FileInputStream(filePathHTML);
            reader = new InputStreamReader(inputStream, "UTF-8");
            bufferedReader = new BufferedReader(reader);
            while((url = bufferedReader.readLine()) != null){
                if(url != null || !url.equals("") || !url.equals(" ")) {
                    htmlSources[counter] = url;
                    counter++;
                }
            }


        } finally {
            inputStream.close();
        }
    }

    public String getPop3SPort() {
        return pop3SPort;
    }

    public String getStoreProtocol() {
        return storeProtocol;
    }

    public String getHost() {
        return host;
    }

    public String getStoreType() {
        return storeType;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String[] getRssSources() {
        return rssSources;
    }

    public String[] getHtmlSources() {
        return htmlSources;
    }
}
