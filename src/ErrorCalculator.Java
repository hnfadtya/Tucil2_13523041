import java.awt.image.BufferedImage;

public class ErrorCalculator {
    // all method here are static because they are only using parameter and returning the result 
    
    // calculateError : deciding which error-method would be used (accessible -> public) 
    public static double calculateError(int method, BufferedImage img, QuadtreeNode node) {
        double error = 0;

        if (method == 1) {error = variance(img, node);}
        if (method == 2) {error = meanAbsoluteDeviation(img, node);}
        if (method == 3) {error = maxPixelDifference(img, node);}
        if (method == 4) {error = entropy(img, node);}
        
        return error;
    }

    private static double variance(BufferedImage img, QuadtreeNode node){
        double varR = 0;
        double varG = 0;
        double varB = 0;
        double varRGB;

        for (int x = node.x; x < node.x + node.width; x++) {
            for (int y = node.y; y < node.y + node.height; y++) {
                int rgb = img.getRGB(x, y);
                varR += (double) (Math.pow((Utils.getRed(rgb) -  node.averageR), 2));
                varG += (double) (Math.pow((Utils.getGreen(rgb) -  node.averageG), 2));
                varB += (double) (Math.pow((Utils.getBlue(rgb) -  node.averageB), 2));
            }
        }

        varRGB = (varR + varG + varB) / (3 * (node.width * node.height));
        return varRGB;
    }

    private static double meanAbsoluteDeviation(BufferedImage img, QuadtreeNode node){
        double madR = 0;
        double madG = 0;
        double madB = 0;
        double madRGB;

        for (int x = node.x; x < node.x + node.width; x++) {
            for (int y = node.y; y < node.y + node.height; y++) {
                int rgb = img.getRGB(x, y);
                madR += (double) (Math.abs(Utils.getRed(rgb) -  node.averageR));
                madG += (double) (Math.abs(Utils.getGreen(rgb) -  node.averageG));
                madB += (double) (Math.abs(Utils.getBlue(rgb) -  node.averageB));
            }
        }

        madRGB = (madR + madG + madB) / (3 * (node.width * node.height));
        return madRGB;
    }
    
    private static double maxPixelDifference(BufferedImage img, QuadtreeNode node){
        int differenceR, differenceG, differenceB;
        int redMaxMin[] = new int[2]; // max and min sequencially
        int greenMaxMin[] = new int[2]; // max and min sequencially
        int blueMaxMin[] = new int[2]; // max and min sequencially
        double differenceRGB;

        for (int x = node.x; x < node.x + node.width; x++) {
            for (int y = node.y; y < node.y + node.height; y++) {
                int rgb = img.getRGB(x, y);
                redMaxMin = Utils.compareRed(rgb, redMaxMin);
                greenMaxMin = Utils.compareGreen(rgb, greenMaxMin);
                blueMaxMin = Utils.compareBlue(rgb, blueMaxMin);
            }
        }
        differenceR = redMaxMin[0] - redMaxMin[1];
        differenceG = greenMaxMin[0] - greenMaxMin[1];
        differenceB = blueMaxMin[0] - blueMaxMin[1];
        
        differenceRGB = (double) (differenceR + differenceG + differenceB) / (3);
        return differenceRGB;
    }
    
    private static double entropy(BufferedImage img, QuadtreeNode node){
        int[] histogramR = new int[256];
        int[] histogramG = new int[256];
        int[] histogramB = new int[256];
        int totalPixels = node.width * node.height;
    
        for (int x = node.x; x < node.x + node.width; x++) {
            for (int y = node.y; y < node.y + node.height; y++) {
                int rgb = img.getRGB(x, y);
                int r = Utils.getRed(rgb);
                int g = Utils.getGreen(rgb);
                int b = Utils.getBlue(rgb);
    
                histogramR[r]++;
                histogramG[g]++;
                histogramB[b]++;
            }
        }
    
        double hR = calculateEntropy(histogramR, totalPixels);
        double hG = calculateEntropy(histogramG, totalPixels);
        double hB = calculateEntropy(histogramB, totalPixels);
    
        return (hR + hG + hB) / 3.0;
    }

    private static double calculateEntropy(int[] histogram, int totalPixels) {
        double entropy = 0.0;
        for (int i = 0; i < histogram.length; i++) {
            if (histogram[i] > 0) {
                double p = (double) histogram[i] / totalPixels;
                entropy -= p * (Math.log(p) / Math.log(2)); 
            }
        }
        return entropy;
    }
}