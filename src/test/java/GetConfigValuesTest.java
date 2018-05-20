
import org.junit.Before;
import org.junit.Test;
import service.ProgramService.ReadPropertiesFile.GetConfigValues;

import java.io.IOException;

public class GetConfigValuesTest {

    GetConfigValues configValues = new GetConfigValues();

    @Before
    public void setup() throws IOException{
        configValues.getConfigValues();
    }

    @Test
    public void rssSourcesTest() {
        String[] result = configValues.getRssSources();
        for(int i = 0; i < result.length; i++){
            System.out.println(result[i]);
        }
    }

    @Test
    public void htmlSourcesTest() {
        String[] result = configValues.getHtmlSources();
        for(int i = 0; i < result.length; i++){
            System.out.println(result[i]);
        }
    }

}
