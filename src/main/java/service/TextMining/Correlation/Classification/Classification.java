package service.TextMining.Correlation.Classification;

import entities.miningEntities.MiningEntity;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.TextMining.Correlation.Preprocessing.Preprocessing;

import java.util.ArrayList;

public class Classification {

    private static final Logger logger = LogManager.getLogger(Preprocessing.class);
    private ArrayList<MiningEntity> miningEntities;

    public Classification(ArrayList<MiningEntity> miningEntities){
        this.miningEntities = miningEntities;
    }

    public void classify(){

    }

}
