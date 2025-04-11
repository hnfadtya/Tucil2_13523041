import java.awt.Color;
import java.awt.image.BufferedImage;

public class QuadtreeNode {
    public int x, y, width, height;
    public int averageR;
    public int averageG;
    public int averageB;
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
                img.setRGB(i, j, this.averageColor.getRGB());
            }
        }
    }

    public void calculateAverageColor(BufferedImage img) {
        int sumR = 0, sumG = 0, sumB = 0;
        int pixelCount = width * height;
    
        for (int i = this.x; i < this.x + this.width; i++) {
            for (int j = this.y; j < this.y + this.height; j++) {
                int rgb = img.getRGB(x, y);
                sumR += Utils.getRed(rgb);
                sumG += Utils.getGreen(rgb);
                sumB += Utils.getBlue(rgb);
            }
        }
    
        this.averageR = sumR / pixelCount;
        this.averageG = sumG / pixelCount;
        this.averageB = sumB / pixelCount;
        this.averageColor = new Color(this.averageR, this.averageG, this.averageB);
    }    
}
