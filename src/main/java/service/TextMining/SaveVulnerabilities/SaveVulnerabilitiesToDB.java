package service.TextMining.SaveVulnerabilities;

import entities.miningEntities.MiningEntity;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.TextMining.Correlation.Clustering.Clustering;

import java.util.ArrayList;

public class SaveVulnerabilitiesToDB {

    private final static Logger logger = LogManager.getLogger(Clustering.class);
    private ArrayList<MiningEntity> miningEntities;
    private ArrayList<ArrayList<Integer>> internalClusters;

    public SaveVulnerabilitiesToDB(ArrayList<MiningEntity> miningEntities,
                                   ArrayList<ArrayList<Integer>> internalClusters){
        this.miningEntities = miningEntities;
        this.internalClusters = internalClusters;
    }

    public void saveVulnerabilities(){


    }

    public void coincideClusteredAlerts(){


    }

}
