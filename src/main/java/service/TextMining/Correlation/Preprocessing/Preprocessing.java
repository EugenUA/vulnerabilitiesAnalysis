package service.TextMining.Correlation.Preprocessing;

import com.github.chen0040.data.text.TextFilter;
import com.github.chen0040.data.text.PorterStemmer;
import com.github.chen0040.data.text.StopWordRemoval;
import entities.miningEntities.MiningEntity;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;

public class Preprocessing {

    private static final Logger logger = LogManager.getLogger(Preprocessing.class);
    private ArrayList<MiningEntity> miningEntities;

    public Preprocessing(ArrayList<MiningEntity> miningEntities){
        this.miningEntities = miningEntities;
    }

    public ArrayList<MiningEntity> getPreprocessingEntities(){
        return this.miningEntities;
    }

    public void conductPreprocessingStep(){

        StopWordRemoval stopWordRemoval = new StopWordRemoval();
        stopWordRemoval.setRemoveXmlTag(true);

        TextFilter stemmer = new PorterStemmer();

        /* TOKENIZER */
        for(MiningEntity entity : this.miningEntities){

            /* DELETE ALL EMAILS, CVEs AND URLS FROM ADVISORY */
            String EMAIL_PATTERN = "([^.@\\s]+)(\\.[^.@\\s]+)*@([^.@\\s]+\\.)+([^.@\\s]+)";
            String URL_PATTERN = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
            String CVE_PATTERN = "\"CVE-\\\\d{4}-\\\\d{4,7}\"";
            entity.setShort_description(entity.getShort_description().replaceAll(EMAIL_PATTERN,""));
            entity.setLong_description(entity.getLong_description().replaceAll(EMAIL_PATTERN,""));
            entity.setShort_description(entity.getShort_description().replaceAll(URL_PATTERN,""));
            entity.setLong_description(entity.getLong_description().replaceAll(URL_PATTERN,""));
            entity.setShort_description(entity.getShort_description().replaceAll(CVE_PATTERN,""));
            entity.setLong_description(entity.getLong_description().replaceAll(CVE_PATTERN,""));

            /* DELETING ALL NEW LINES FROM THE BEGINNING AND THE END OF THE STRING*/
            entity.setShort_description(entity.getShort_description().trim());
            entity.setLong_description(entity.getLong_description().trim());

            /* remove non-letter characters (punctuation and numbers) & fold input text to lower case */
            entity.setPreprocessed_short_description(Arrays.asList(
                    entity.getShort_description().replaceAll("[^a-zA-Z ]", " ").toLowerCase().split("\\s+")));
            entity.setPreprocessed_long_description(Arrays.asList(
                    entity.getLong_description().replaceAll("[^a-zA-Z ]", " ").toLowerCase().split("\\s+")));
        }

        /* STOP WORDS REMOVAL */
        for(MiningEntity entity : this.miningEntities){
            entity.setPreprocessed_short_description(
                    stopWordRemoval.filter(entity.getPreprocessed_short_description())
            );
            entity.setPreprocessed_long_description(
                    stopWordRemoval.filter(entity.getPreprocessed_long_description())
            );
        }

        /* STEMMING (Porter Stemmer)*/
        for(MiningEntity entity : this.miningEntities){
            entity.setPreprocessed_short_description(
                    stemmer.filter(entity.getPreprocessed_short_description())
            );
            entity.setPreprocessed_long_description(
                    stemmer.filter(entity.getPreprocessed_long_description())
            );
        }
    }
}
