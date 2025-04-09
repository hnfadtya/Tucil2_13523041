import java.awt.image.BufferedImage;

public class Compressor {
    private final String inputPath;
    private final int errorMethod;
    private final double threshold;
    private final int minBlockSize;
    private final double targetCompression;
    private final String outputPath;

    private BufferedImage inputImage;
    private QuadTreeNode root;

    public Compressor(String inputPath, int errorMethod, double threshold, int minBlockSize, double targetCompression, String outputPath) {
        this.inputPath = inputPath;
        this.errorMethod = errorMethod;
        this.threshold = threshold;
        this.minBlockSize = minBlockSize;
        this.targetCompression = targetCompression;
        this.outputPath = outputPath;
    }

    public void run() {
        long start = System.currentTimeMillis();

        try {
            inputImage = ImageUtils.loadImage(inputPath);
            root = buildQuadtree(0, 0, inputImage.getWidth(), inputImage.getHeight());

            BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            drawQuadtree(outputImage, root);

            ImageUtils.saveImage(outputImage, outputPath);

            long end = System.currentTimeMillis();
            System.out.println("Execution Time: " + (end - start) + "ms");
            // Nanti tambahkan info ukuran, kedalaman pohon, node, dll
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private QuadTreeNode buildQuadtree(int x, int y, int width, int height) {
        // placeholder untuk pengecekan error dan rekursi
        QuadTreeNode node = new QuadTreeNode(x, y, width, height);
        // TODO: panggil metode error dan lakukan pembagian blok
        return node;
    }

    private void drawQuadtree(BufferedImage image, QuadTreeNode node) {
        if (node == null) return;

        if (node.isLeaf) {
            for (int i = node.x; i < node.x + node.width; i++) {
                for (int j = node.y; j < node.y + node.height; j++) {
                    int rgb = (node.averageR << 16) | (node.averageG << 8) | node.averageB;
                    image.setRGB(i, j, rgb);
                }
            }
        } else {
            for (QuadTreeNode child : node.children) {
                drawQuadtree(image, child);
            }
        }
    }
}
