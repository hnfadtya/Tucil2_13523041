import java.awt.Color;
import java.awt.image.BufferedImage;

public class QuadtreeNode {
    public int x, y, width, height;
    public double averageR;
    public double averageG;
    public double averageB;
    public Color averageColor;
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
        this.averageColor = new Color(0, 0, 0);
        this.isLeaf = false;
        this.children = new QuadtreeNode[4]; // NW, NE, SW, SE
    }

    public void setChildren() {
        int count = 0;
        int childWidth = this.width / 2;
        int childHeight = this.height / 2;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                int X = this.x + childWidth * i;
                int Y = this.y + childHeight * j;
                this.children[count] = new QuadtreeNode(X, Y, childWidth, childHeight);
                count++;
            }
        }
    }

    public void normalizeColor(BufferedImage img) {        
        for (int i = this.x; i < this.x + this.width; i++) {
            for (int j = this.y; j < this.y + this.height; j++) {
                img.setRGB(i, j, averageColor.getRGB());
            }
        }

        System.out.println("X : " + this.x);
        // System.out.println("Y : " + this.x);
        // System.out.println("avgRGB : " + (int) this.averageRGB);
    }

    public void calculateAverageColor(BufferedImage img) {
        long sumR = 0, sumG = 0, sumB = 0;
        int pixelCount = width * height;
    
        for (int i = this.x; i < this.x + this.width; i++) {
            for (int j = this.y; j < this.y + this.height; j++) {
                Color color = new Color(img.getRGB(i, j));
                sumR += color.getRed();
                sumG += color.getGreen();
                sumB += color.getBlue();
            }
        }
    
        this.averageR = sumR / (double) pixelCount;
        this.averageG = sumG / (double) pixelCount;
        this.averageB = sumB / (double) pixelCount;
        this.averageColor = new Color((int)this.averageR, (int)this.averageG, (int)this.averageB);

        // System.out.println("avgR : " + this.averageR);
        // System.out.println("avgG : " + this.averageG);
        // System.out.println("avgB : " + this.averageB);
        // System.out.println("avgRGB : " + this.averageRGB);
    }    
}
