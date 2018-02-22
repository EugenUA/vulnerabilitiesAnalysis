package service.ProgramService.ReadPropertiesFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetConfigValues {

    private InputStream inputStream;

    private String email;
    private String password;
    private String host;
    private String storeType;
    private String storeProtocol;
    private String pop3SPort;

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
}
