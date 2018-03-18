package service.TextMining.Correlation.SimilarityFunctions;

public class EuclideanDistance {

    public double euclideanDistance(double[] docVector1, double[] docVector2){

        double Sum = 0.0;
        for(int i=0;i<docVector1.length;i++) {
            Sum = Sum + Math.pow((docVector1[i]-docVector2[i]),2.0);
        }
        return Math.sqrt(Sum);

    }

}
