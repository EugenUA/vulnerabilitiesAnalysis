package service.TextMining.Correlation;

import com.github.chen0040.data.text.TextFilter;
import com.github.chen0040.data.text.PorterStemmer;
import com.github.chen0040.data.text.StopWordRemoval;
import entities.miningEntities.MiningEntity;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import sun.font.TrueTypeFont;

import java.lang.reflect.Array;
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

        /* TOKENIZER & remove non-letter characters (punctuation and numbers) & fold input text to lower case*/
        for(MiningEntity entity : this.miningEntities){
            entity.setPreprocessed_short_description(Arrays.asList(
                    entity.getShort_description().replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+")));
            entity.setPreprocessed_long_description(Arrays.asList(
                    entity.getLong_description().replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+")));
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
