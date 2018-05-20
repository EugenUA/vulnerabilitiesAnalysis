package service.TextMining.Correlation.Classification;

import entities.miningEntities.MiningEntity;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.TextMining.Correlation.SimilarityFunctions.CosineSimilarity;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Classification {

    private static final Logger logger = Logger.getLogger(Classification.class);
    private ArrayList<MiningEntity> miningEntities;

    private List<String[]> short_description_array; // all terms of each document (short)
    private List<String[]> long_description_array; // all terms of each document (long)

    private List<String> short_description_allTerms; // all terms of all documents (short)
    private List<String> long_description_allTerms; // all terms of all documents (long)

    private List<double[]> short_tfidfDocsVector = new ArrayList<double[]>();
    private List<double[]> long_tfidfDocsVector = new ArrayList<double[]>();

    public Classification(ArrayList<MiningEntity> miningEntities){
        this.miningEntities = miningEntities;

        short_description_array = new ArrayList<String[]>();
        long_description_array = new ArrayList<String[]>();

        short_description_allTerms = new ArrayList<String>();
        long_description_allTerms = new ArrayList<String>();
    }

    public ArrayList<MiningEntity> getMiningEntities(){return this.miningEntities;}

    public void createVectorSpaceModel(){

        /* TF_IDF CALCULATOR SHORT */

        for(MiningEntity miningEntity : miningEntities){
            short_description_array.add(miningEntity.getPreprocessed_short_description().toArray(new String[0]));
        }

        for(String[] str : short_description_array){
            for(int i = 0; i < str.length; i++){
                if(!short_description_allTerms.contains(str[i])){
                    short_description_allTerms.add(str[i]);
                }
            }
        }

        double tf;
        double idf;
        double tfidf;
        for (String[] docTermsArray : short_description_array) {
            double[] tfidfvectors = new double[short_description_allTerms.size()];
            int count = 0;
            for (String terms : short_description_allTerms) {
                tf = new TfIdf().tfCalculator(docTermsArray, terms);
                idf = new TfIdf().idfCalculator(short_description_array, terms);
                tfidf = tf * idf;
                tfidfvectors[count] = tfidf;
                count++;
            }
            short_tfidfDocsVector.add(tfidfvectors);
        }

        int i = 0;
        for(MiningEntity miningEntity : miningEntities){
            miningEntity.setShort_tfidfDocsVector(short_tfidfDocsVector.get(i));
            i++;
        }

        /* TF_IDF CALCULATOR LONG */
        for(MiningEntity miningEntity : miningEntities){
            long_description_array.add(miningEntity.getPreprocessed_long_description().toArray(new String[0]));
        }

        for(String[] str : long_description_array){
            for(int j = 0; j < str.length; j++){
                if(!long_description_allTerms.contains(str[j])){
                    long_description_allTerms.add(str[j]);
                }
            }
        }

        double tfL;
        double idfL;
        double tfidfL;
        for (String[] docTermsArray : long_description_array) {
            double[] tfidfvectorsL = new double[long_description_allTerms.size()];
            int countL = 0;
            for (String terms : long_description_allTerms) {
                tfL = new TfIdf().tfCalculator(docTermsArray, terms);
                idfL = new TfIdf().idfCalculator(long_description_array, terms);
                tfidfL = tfL * idfL;
                tfidfvectorsL[countL] = tfidfL;
                countL++;
            }
            long_tfidfDocsVector.add(tfidfvectorsL);  //storing document vectors;
        }

        int k = 0;
        for(MiningEntity miningEntity : miningEntities){
            miningEntity.setLong_tfidfDocsVector(long_tfidfDocsVector.get(k));
            k++;
        }
    }

    //Test
    public void getCosineSimilarity() {
        for (int i = 0; i < short_tfidfDocsVector.size(); i++)
        {
            for (int j = 0; j < short_tfidfDocsVector.size(); j++)
            {
                if(i!=j)
                    System.out.println("between " + i + " and " + j + "  =  "+ new CosineSimilarity().cosineSimilarity (
                            short_tfidfDocsVector.get(i),  short_tfidfDocsVector.get(j)));
            }
        }
    }

    /* With use of Naive Bayes Classifier (with prepared features */
    public void classify(){

        ArrayList<Integer> indexes = new ArrayList<Integer>();

        for(MiningEntity miningEntity : miningEntities){
            List<String> toClassify = miningEntity.getPreprocessed_long_description();
            try {
                double spam = 0;
                double nonspam = 0;
                HashMap<String,Double> sp = new HashMap<String,Double>();
                HashMap<String,Double> nsp = new HashMap<String,Double>();

                BufferedReader br = new BufferedReader(new FileReader(
                        "src/main/resources/spamVector"));
                String line = "";
                while((line = br.readLine())!=null){
                    String parts[] = line.split(" ");
                    double v =  Double.parseDouble(parts[1]);
                    sp.put(parts[0],v);
                }

                BufferedReader br1 = new BufferedReader(new FileReader(
                        "src/main/resources/nonspamVector"));
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
                    }
                }

                double totalspam = spam;
                double totalnon = nonspam;
                if(totalspam >= totalnon) {
                    indexes.add(miningEntities.indexOf(miningEntity));
                }

            } catch(FileNotFoundException e){
                // do not handle
            } catch(IOException e){
                // do not handle
            }
        }

        for(Integer index : indexes){
            miningEntities.remove(Integer.parseInt(index.toString()));
        }
    }

}
