import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class QuadtreeNode {
    public int x, y, width, height;
    public double averageR;
    public double averageG;
    public double averageB;
    public double averageRGB;
    public boolean isLeaf;
    public QuadtreeNode[] children;

    public QuadtreeNode(int x, int y, int width, int height) { // constructor
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.averageR = 0;
        this.averageG = 0;
        this.averageB = 0;
        this.averageRGB = 0;
        this.isLeaf = false;
        this.children = new QuadtreeNode[4]; // NW, NE, SW, SE
    }

    public void normalizeColor(BufferedImage img, QuadtreeNode node) {
        int gray = (int) Math.round(Math.max(0, Math.min(255, node.averageRGB)));
        Color grayColor = new Color(gray, gray, gray);
    
        for (int i = node.x; i < node.x + node.width; i++) {
            for (int j = node.y; j < node.y + node.height; j++) {
                image.setRGB(i, j, node.averageRGB); //cara mengeset rgb nya 
            }
        }
    }

    public static void getRGB(BufferedImage img, QuadtreeNode node) {
        long sumR = 0; 
        long sumG = 0; 
        long sumB = 0;

        for (int i = node.x; i < node.x + node.width; i++) {
            for (int j = node.y; j < node.y + node.height; j++) {
                Color color = new Color(img.getRGB(i, j));
                sumR += color.getRed();
                sumG += color.getGreen();
                sumB += color.getBlue();
            }
        }

        averageR = sumR / (double) pixelCount;
        averageG = sumG / (double) pixelCount;
        averageB = sumB / (double) pixelCount;
        averageRGB = (averageR + averageG + averageB) / 3.0;
    }
}
