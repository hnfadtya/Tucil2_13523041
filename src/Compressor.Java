import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class Compressor {
    private final File inputFile;
    private final int errorMethod;
    private final double threshold;
    private final int minBlockSize;
    private final File compressedFile;

    private BufferedImage inputImage;
    private QuadtreeNode root;

    // Constructor
    public Compressor(File inputFile, int errorMethod, double threshold, int minBlockSize, File compressedFile) {
        this.inputFile = inputFile;
        this.errorMethod = errorMethod;
        this.threshold = threshold;
        this.minBlockSize = minBlockSize;
        this.compressedFile = compressedFile;
    }

    // Main processing method
    public void compressImage() {
        try {
            long start = System.currentTimeMillis();
            
            // load and compress image
            inputImage = loadImage(inputFile);
            BufferedImage compressedImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            root = new QuadtreeNode(0, 0, inputImage.getWidth(), inputImage.getHeight());
            splitQuadtree(inputImage, root);

            // reconstruct (actually compress and reconstructing image could be done simultaneously)
            reconstructImage(compressedImage, root);            
            saveCompressedImage(compressedImage, compressedFile);
            long end = System.currentTimeMillis();

            System.out.println("Execution Time: " + (end - start) + "ms");
            IO.printCompressionStats(inputFile, compressedFile, root);
        } catch (Exception e) {
            System.out.println("Error occur: " + e.getMessage());
        }
    }

    // Recursion for normalize block
    private void splitQuadtree(BufferedImage image, QuadtreeNode node) {
        int nodeSize = node.width * node.height;
        int childrenNodeSize = nodeSize / 4;
        node.calculateAverageColor(image);
        double variance = ErrorCalculator.calculateError(errorMethod, image, node); // error

        if (nodeSize <= minBlockSize || childrenNodeSize < minBlockSize || variance <= threshold) {
            node.isLeaf = true;
            return;
        }

        node.setChildren();
        for (QuadtreeNode child : node.children) {
            splitQuadtree(image, child);
        }
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

        // Ekstrak ekstensi dari nama file
        String fileName = file.getName();
        String extension = "jpg"; // default
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            extension = fileName.substring(dotIndex + 1).toLowerCase();
        }

        // Simpan gambar dengan format sesuai ekstensi
        ImageIO.write(image, extension, file);
    }
}