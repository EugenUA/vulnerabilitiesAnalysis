package service.TextMining.Correlation.Classification;

import entities.miningEntities.MiningEntity;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.TextMining.Correlation.Preprocessing.Preprocessing;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Classification {

    private static final Logger logger = LogManager.getLogger(Classification.class);
    private ArrayList<MiningEntity> miningEntities;

    public Classification(ArrayList<MiningEntity> miningEntities){
        this.miningEntities = miningEntities;
    }

    /* With use of Naive Bayes Classifier (with prepared features */
    public void classify(){
        for(MiningEntity miningEntity : miningEntities){
            List<String> toClassify = miningEntity.getPreprocessed_long_description();
            try {
                double spam = 0;
                double nonspam = 0;
                HashMap<String,Double> sp = new HashMap<String,Double>();
                HashMap<String,Double> nsp = new HashMap<String,Double>();

                BufferedReader br = new BufferedReader(new FileReader(
                        "C:/Users/egruzdev/Documents/vulnerabilities analysis/vulnerabilitiesAnalysis/src/main/resources/spamVector"));
                String line = "";
                while((line = br.readLine())!=null){
                    String parts[] = line.split(" ");
                    double v =  Double.parseDouble(parts[1]);
                    sp.put(parts[0],v);
                }

                BufferedReader br1 = new BufferedReader(new FileReader(
                        "C:/Users/egruzdev/Documents/vulnerabilities analysis/vulnerabilitiesAnalysis/src/main/resources/nonspamVector"));
                String line1 = "";
                while((line1 = br1.readLine())!=null){
                    String parts[] = line1.split(" ");
                    double v =  Double.parseDouble(parts[1]);
                    nsp.put(parts[0],v);
                }

                for(String word : toClassify){
                    if(sp.containsKey(word)){
                        spam += sp.get(word);
                    } else
                    if(nsp.containsKey(word)){
                        nonspam += nsp.get(word);
                    } else {
                        //nonspam += 1;
                    }
                }

                double totalspam = spam;
                double totalnon = nonspam;
                if(totalspam >= totalnon)System.out.println("Spam" + totalspam);
                else System.out.println("NonSpam " + totalnon + " (spam was " + totalspam + ")");

            } catch(FileNotFoundException e){
                System.out.println(e.getMessage());
            } catch(IOException e){
                // do not handle
            }
        }
    }

}
