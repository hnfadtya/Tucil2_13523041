import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Compressor {
    private final String inputPath;
    private final int errorMethod;
    private final double threshold;
    private final int minBlockSize;
    private final double targetCompression;
    private final String outputPath;

    private BufferedImage inputImage;
    private QuadtreeNode root;

    // Constructor
    public Compressor(String inputPath, int errorMethod, double threshold, int minBlockSize, double targetCompression, String outputPath) {
        this.inputPath = inputPath;
        this.errorMethod = errorMethod;
        this.threshold = threshold;
        this.minBlockSize = minBlockSize;
        this.targetCompression = targetCompression;
        this.outputPath = outputPath;
    }

    // Main processing method
    public void compressImage() {
        try {
            long start = System.currentTimeMillis();
            // load and compress image
            inputImage = loadImage(inputPath);
            BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            root = QuadtreeNode(0, 0, inputImage.getWidth(), inputImage.getHeight());
            splitQuadtree(inputImage, root);

            // reconstruct (actually compress and reconstructing image could be done simultaneously)
            reconstructImage(outputImage, root);            
            saveCompressedImage(outputImage, outputPath);

            long end = System.currentTimeMillis();
            System.out.println("Execution Time: " + (end - start) + "ms");
            // System.out.println("Image size before compressed: " +  + "satuan ukuran");
            // System.out.println("Image size after compressed: " +  + "satuan ukuran");
            // System.out.println("Compression percentage: " +  + "%");
            // System.out.println("Tree depth: " +  + "satuan depth");
            // System.out.println("Tree nodes: " +  + "nodes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Recursion for normalize block
    private void splitQuadtree(BufferedImage image, QuadtreeNode node) {
        int nodeSize = node.width * node.height;
        int childrenNodeSize = nodeSize / 4;
        QuadtreeNode.getRGB();
        double variance = ErrorCalculator.calculateError(errorMethod, image, node, nodeSize); // error

        if (nodeSize <= minBlockSize || childrenNodeSize >= minBlockSize || variance < threshold) {
            node.isLeaf = true;
            return;
        }

        for (QuadtreeNode child : node.children) {
            splitQuadtree(image, child);
        }
    }

    // Recursion for reconstruct the image
    private void reconstructImage(BufferedImage image, QuadtreeNode node) {
        if (node == null) return;

        if (node.isLeaf) {
            QuadtreeNode.normalizeColor(image, node);
        } else {
            for (QuadtreeNode child : node.children) {
                reconstructImage(image, child);
            }
        }
    }

    public BufferedImage loadImage(String path) throws IOException {
        return ImageIO.read(new File(path));
    }
    
    public void saveCompressedImage(BufferedImage image, String path) throws IOException {
        ImageIO.write(image, "png", new File(path));
    }
}