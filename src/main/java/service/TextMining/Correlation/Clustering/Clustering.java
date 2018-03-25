package service.TextMining.Correlation.Clustering;

import entities.miningEntities.MiningEntity;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.TextMining.Correlation.SimilarityFunctions.CosineSimilarity;

import java.util.ArrayList;



public class Clustering {

    private final static Logger logger = LogManager.getLogger(Clustering.class);
    private ArrayList<MiningEntity> miningEntities;
    private ArrayList<ArrayList<Integer>> internalClusters;

    public Clustering(ArrayList<MiningEntity> miningEntities){
        this.miningEntities = miningEntities;
        this.internalClusters = new ArrayList<ArrayList<Integer>>();
    }

    public ArrayList<MiningEntity> getMiningEntities(){
        return this.miningEntities;
    }

    public ArrayList<ArrayList<Integer>> getInternalClusters(){
        return this.internalClusters;
    }

    public void conductClustering(){

        this.assignIndividualClusterToEachMiningEntity();
        boolean change;

        do {
            change = false;

            /* Find clusters with minimal distance */
            ArrayList<Integer> nearestClusters = new ArrayList<Integer>();
            double distance = 0;

            for (int i = 0; i < internalClusters.size(); i++) {
                for (int j = i + 1; j < internalClusters.size(); j++) {
                    double tempDist = this.similarityBetweenClusters(internalClusters.get(i), internalClusters.get(j));
                    //logger.info("Between " + i + "; " + j + " : " + tempDist);
                    if (tempDist > distance) {
                        distance = tempDist;
                        nearestClusters.clear();
                        nearestClusters.add(i);
                        nearestClusters.add(j);
                    }
                }
            }

            /* If distance is bigger than threshold concat clusters and set change to true */
            if(distance > 0.7){
                if(!nearestClusters.isEmpty()){
                    int i = nearestClusters.get(0);
                    int j = nearestClusters.get(1);
                    internalClusters.get(i).addAll(internalClusters.get(j));
                    internalClusters.remove(j);

                    System.out.println(internalClusters);
                }
                change = true;
            }

        } while(change);

        for(ArrayList<Integer> arrayList : internalClusters){
            for(Integer eachElem: arrayList){
                miningEntities.get(eachElem).setClusterNum(internalClusters.indexOf(arrayList));
            }
        }
    }

    /* Every single mining entity is placed in the single cluster */
    public void assignIndividualClusterToEachMiningEntity(){
        for(MiningEntity miningEntity : miningEntities){
            ArrayList<Integer> index = new ArrayList<Integer>();
            index.add(miningEntities.indexOf(miningEntity));
            internalClusters.add(index);
            miningEntity.setClusterNum(miningEntities.indexOf(miningEntity));
        }
    }

    /* Compute the similarity between clusters */
    public double similarityBetweenClusters(ArrayList<Integer> cluster1, ArrayList<Integer> cluster2){
        double similarity = 0;
        int counter = 0;
        for(Integer integer1 : cluster1){
            for(Integer integer2 : cluster2){
                similarity += new CosineSimilarity().cosineSimilarity(
                        miningEntities.get(Integer.parseInt(integer1.toString())).getLong_tfidfDocsVector(),
                        miningEntities.get(Integer.parseInt(integer2.toString())).getLong_tfidfDocsVector());
                counter++;
            }
        }
        return similarity/counter;
    }
}
