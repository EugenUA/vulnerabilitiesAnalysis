package service.ProgramService.StemmingOfTrainingData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

import com.github.chen0040.data.text.PorterStemmer;
import com.github.chen0040.data.text.TextFilter;

public class StemmingOfClassificationVectors {

    public static void main(String[] args) throws Exception{

        HashMap<String,Double> sp = new HashMap<String,Double>();
        BufferedReader br = new BufferedReader(new FileReader(
                "C:/Users/egruzdev/Documents/vulnerabilities analysis/vulnerabilitiesAnalysis/src/main/resources/spamVector"));
        String line = "";

        while((line = br.readLine()) != null){
            String[] parts = line.split(" ");
            double v = Double.parseDouble(parts[1]);
            sp.put(parts[0], v);
        }

        PrintWriter writer = new PrintWriter(
                "C:/Users/egruzdev/Documents/vulnerabilities analysis/vulnerabilitiesAnalysis/src/main/resources/spamVector");
        writer.print("");
        writer.close();

        PrintWriter writerWrite = new PrintWriter(
                "C:/Users/egruzdev/Documents/vulnerabilities analysis/vulnerabilitiesAnalysis/src/main/resources/spamVector");
        for(Map.Entry<String,Double>e : sp.entrySet()){
            writerWrite.write(stemmer(e.getKey()).get(0) + " " + e.getValue());
            writerWrite.println();
        }
        writerWrite.close();






        HashMap<String,Double> nsp = new HashMap<String,Double>();
        BufferedReader br1 = new BufferedReader(new FileReader(
                "C:/Users/egruzdev/Documents/vulnerabilities analysis/vulnerabilitiesAnalysis/src/main/resources/nonspamVector"));
        String line1 = "";

        while((line1 = br1.readLine()) != null){
            String[] parts = line1.split(" ");
            double v = Double.parseDouble(parts[1]);
            nsp.put(parts[0], v);
        }

        PrintWriter writer2 = new PrintWriter(
                "C:/Users/egruzdev/Documents/vulnerabilities analysis/vulnerabilitiesAnalysis/src/main/resources/nonspamVector");
        writer2.print("");
        writer2.close();

        PrintWriter writerWrite2 = new PrintWriter(
                "C:/Users/egruzdev/Documents/vulnerabilities analysis/vulnerabilitiesAnalysis/src/main/resources/nonspamVector");
        for(Map.Entry<String,Double>e : nsp.entrySet()){
            writerWrite2.write(stemmer(e.getKey()).get(0) + " " + e.getValue());
            writerWrite2.println();
        }
        writerWrite2.close();
    }


    public static List<String> stemmer(String word){
        TextFilter stemmer = new PorterStemmer();
        List words = Arrays.asList(word);
        return stemmer.filter(words);
    }

}
