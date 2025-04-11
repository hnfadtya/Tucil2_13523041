import java.awt.Color;
import java.awt.image.BufferedImage;

public class ErrorCalculator {
    // all method here are static because they are only using parameter and returning the result 
    
    // calculateError : deciding which error-method would be used (accessible -> public) 
    public static double calculateError(int method, BufferedImage img, QuadtreeNode node, int pixelCount) {
        double error = 0;

        if (method == 1) {error = variance(img, node, pixelCount);}
        if (method == 2) {error = meanAbsoluteDeviation(img, node, pixelCount);}
        if (method == 3) {error = maxPixelDifference(img, node);}
        if (method == 4) {error = Entropy(img, node);}
        
        return error;
    }

    private static double variance(BufferedImage img, QuadtreeNode node, int pixelCount){
        double varR = 0;
        double varG = 0;
        double varB = 0;
        double varRGB;

        for (int i = node.x; i < node.x + node.width; i++) {
            for (int j = node.y; j < node.y + node.height; j++) {
                Color color = new Color(img.getRGB(i, j));
                varR += Math.pow((color.getRed() - node.averageR), 2);
                varG += Math.pow((color.getGreen() - node.averageG), 2);
                varB += Math.pow((color.getBlue() - node.averageB), 2);
            }
        }

        varRGB = (varR + varG + varB) / (3 * pixelCount);
        return varRGB;
    }

    private static double meanAbsoluteDeviation(BufferedImage img, QuadtreeNode node, int pixelCount){
        double madR = 0;
        double madG = 0;
        double madB = 0;
        double madRGB;

        for (int i = node.x; i < node.x + node.width; i++) {
            for (int j = node.y; j < node.y + node.height; j++) {
                Color color = new Color(img.getRGB(i, j));
                madR += Math.abs(color.getRed() - node.averageR);
                madG += Math.abs(color.getGreen() - node.averageG);
                madB += Math.abs(color.getBlue() - node.averageB);
            }
        }

        madRGB = (madR + madG + madB) / (3 * pixelCount);
        return madRGB;
    }

    private static double maxPixelDifference(BufferedImage img, QuadtreeNode node){
        return 1.0;
    }
    
    private static double Entropy(BufferedImage img, QuadtreeNode node){
        return 1.0;
    }
}