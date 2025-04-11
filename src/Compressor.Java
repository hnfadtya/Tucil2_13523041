import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class Compressor {
    private final File inputPath;
    private final int errorMethod;
    private final double threshold;
    private final int minBlockSize;
    private final double targetCompression;
    private final File outputPath;

    private BufferedImage inputImage;
    private QuadtreeNode root;
    private int countNode = 1;
    private int countNodeDump = 1;
    private int checkpoint = 0;

    // Constructor
    public Compressor(File inputPath, int errorMethod, double threshold, int minBlockSize, double targetCompression, File outputPath) {
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
            root = new QuadtreeNode(0, 0, inputImage.getWidth(), inputImage.getHeight());
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
            System.out.println("Tree nodes: " + countNode + "nodes");
            System.out.println("Tree nodes: " + countNodeDump + "nodes");
            System.out.println("Checkpoint: " + checkpoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Recursion for normalize block
    private void splitQuadtree(BufferedImage image, QuadtreeNode node) {
        int nodeSize = node.width * node.height;
        int childrenNodeSize = nodeSize / 4;
        node.calculateAverageColor(image);
        double variance = ErrorCalculator.calculateError(errorMethod, image, node, nodeSize); // error

        System.out.println("avgR : " + node.averageR);
        System.out.println("avgG : " + node.averageG);
        System.out.println("avgB : " + node.averageB);
        System.out.println("avgRGB : " + node.averageColor);
        System.out.println("node size : " + nodeSize);
        System.out.println("children size : " + childrenNodeSize);
        System.out.println("this is error : " + variance);

        if (nodeSize <= minBlockSize || childrenNodeSize < minBlockSize || variance <= threshold) {
            node.isLeaf = true;
            checkpoint++;
            System.out.println("checkpoint");
            return;
        }

        node.setChildren();
        for (QuadtreeNode child : node.children) {
            splitQuadtree(image, child);
        }
        countNode += 4;
    }
    
    // Recursion for reconstruct the image
    private void reconstructImage(BufferedImage image, QuadtreeNode node) {
        if (node == null) return;
        
        if (node.isLeaf) {
            node.normalizeColor(image);
        } else {
            for (QuadtreeNode child : node.children) {
                reconstructImage(image, child);
            }
            countNodeDump += 4;
        }
    }
    
    public static BufferedImage loadImage(File inputFile) throws IOException {
        return ImageIO.read(inputFile);
    }
    
    public static void saveCompressedImage(BufferedImage image, File file) throws IOException {    
        // Buat folder kalau belum ada
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();  // buat folder secara rekursif
        }

        ImageIO.write(image, "jpg", file);
    }
}