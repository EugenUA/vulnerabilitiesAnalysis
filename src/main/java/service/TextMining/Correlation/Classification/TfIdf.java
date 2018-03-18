package service.TextMining.Correlation.Classification;

import java.util.List;

public class TfIdf {

    /**
     * @brief Calculates the tf of the term
     * @param totalterms array of all words of a document
     * @param termToCheck the term that needs to have tf
     * @return tf value for a term
     */

    public double tfCalculator(String[] totalterms, String termToCheck){
        double count = 0;  //to count the overall occurrence of the term termToCheck
        for (String s : totalterms) {
            if (s.equalsIgnoreCase(termToCheck)) {
                count++;
            }
        }
        return count / totalterms.length;
    }

    /**
     * Calculates idf for the term to check
     * @param allTerms all the terms of all documents
     * @param termToCheck the terms that needs idf
     * @return idf value for a term
     */
    public double idfCalculator(List<String[]> allTerms, String termToCheck){
        double count = 0;
        for (String[] ss : allTerms) {
            for (String s : ss) {
                if (s.equalsIgnoreCase(termToCheck)) {
                    count++;
                    break;
                }
            }
        }
        return 1 + Math.log(allTerms.size() / count);
    }

}
