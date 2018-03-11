package service.TextMining;

import entities.aggregationEntities.Email;
import entities.aggregationEntities.HTML;
import entities.aggregationEntities.RSS;
import entities.miningEntities.MiningEntity;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class TextMiningMainEntrance {

    private static final Logger logger = LogManager.getLogger(TextMiningMainEntrance.class);

    /* RECEIVED ADVISORIES FROM ALL THE SOURCES */
    private ArrayList<Email> receivedEmails;
    private ArrayList<RSS> receivedRSS;
    private ArrayList<HTML> receivedHTML;

    /* ENTITIES FOR MINING FROM ADVISORIES */
    private ArrayList<MiningEntity> miningEntities;

    public TextMiningMainEntrance(){}

    public TextMiningMainEntrance(ArrayList<Email> emails, ArrayList<RSS> rss, ArrayList<HTML> html){
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

    }

    public void performTextMinig(){



    }
}
