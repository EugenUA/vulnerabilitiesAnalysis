package service.TextMining;

import entities.aggregationEntities.Email;
import entities.aggregationEntities.HTML;
import entities.aggregationEntities.RSS;
import entities.miningEntities.MiningEntity;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import service.TextMining.Correlation.Classification.Classification;
import service.TextMining.Correlation.Clustering.Clustering;
import service.TextMining.Correlation.Preprocessing.Preprocessing;


public class TextMiningMainEntrance {

    private static final Logger logger = LogManager.getLogger(TextMiningMainEntrance.class);

    /* RECEIVED ADVISORIES FROM ALL THE SOURCES */
    private ArrayList<Email> receivedEmails;
    private ArrayList<RSS> receivedRSS;
    private ArrayList<HTML> receivedHTML;

    /* ENTITIES FOR MINING FROM ADVISORIES */
    private ArrayList<MiningEntity> miningEntities;

    public TextMiningMainEntrance() {

        miningEntities = new ArrayList<MiningEntity>();
    }

    public TextMiningMainEntrance(ArrayList<Email> emails, ArrayList<RSS> rss, ArrayList<HTML> html) {
        this.receivedEmails = emails;
        this.receivedRSS = rss;
        this.receivedHTML = html;

        miningEntities = new ArrayList<MiningEntity>();
    }

    public ArrayList<MiningEntity> getMiningEntities() {
        return miningEntities;
    }

    public void setReceivedEmails(ArrayList<Email> receivedEmails) {
        this.receivedEmails = receivedEmails;
    }

    public void setReceivedRSS(ArrayList<RSS> receivedRSS) {
        this.receivedRSS = receivedRSS;
    }

    public void setReceivedHTML(ArrayList<HTML> receivedHTML) {
        this.receivedHTML = receivedHTML;
    }

    public void buildMiningEntities(){
        if(receivedEmails.size() > 0){
            for(Email email : receivedEmails){
                MiningEntity miningEntity = new MiningEntity();
                miningEntity.setShort_description(email.getSubject().replaceAll("\\[.*?\\] ",""));
                miningEntity.setLong_description(deletePGPSigning(email.getContent()));
                miningEntity.setDate(email.getReceivedDate());
                miningEntity.setSource(email.getTo());
                miningEntity.setSource_type("EMAIL");

                miningEntities.add(miningEntity);
            }
        }
        if(receivedRSS.size() > 0){
            for(RSS rss : receivedRSS){
                MiningEntity miningEntity = new MiningEntity();
                miningEntity.setShort_description(rss.getTitle().replaceAll("\\[.*?\\] ",""));
                miningEntity.setLong_description(html2text(rss.getDescription()));
                miningEntity.setDate(rss.getPubDate());
                miningEntity.setSource(rss.getLink());
                miningEntity.setSource_type("RSS");

                miningEntities.add(miningEntity);
            }
        }
        if(receivedHTML.size() > 0){
            for(HTML html : receivedHTML){
                MiningEntity miningEntity = new MiningEntity();
                miningEntity.setShort_description(html.getTitle().replaceAll("\\[.*?\\] ",""));
                miningEntity.setLong_description(html2text(html.getDescription()));
                miningEntity.setDate(html.getPubDate());
                miningEntity.setSource(html.getLink());
                miningEntity.setSource_type("HTML");

                miningEntities.add(miningEntity);
            }
        }
    }

    public String deletePGPSigning(String message){
        if(message.contains("BEGIN PGP SIGNED MESSAGE")){
            message = message.substring(message.indexOf('\n')+1);
            message = message.substring(message.indexOf('\n')+1);
            message = message.substring(message.indexOf('\n')+1);
            message = message.substring(message.indexOf('\n')+1);


            int index = message.indexOf("-----BEGIN PGP SIGNATURE");
            message = message.substring(0,index-1);
        }

        return message;
    }

    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }

    public void performTextMining(){

        this.buildMiningEntities();

        Preprocessing preprocessing = new Preprocessing(this.miningEntities);
        preprocessing.conductPreprocessingStep();

        Classification classification = new Classification(this.miningEntities);
        classification.classify();
        classification.createVectorSpaceModel();

        Clustering clustering = new Clustering(this.miningEntities);
        clustering.conductClustering();

        /* For Testing purposes */
        for(MiningEntity min : miningEntities){
            for(int i = 0; i < min.getLong_tfidfDocsVector().length; i++) {
                System.out.print(min.getLong_tfidfDocsVector()[i] + " ");
            }
            System.out.println();
        }

    }
}

