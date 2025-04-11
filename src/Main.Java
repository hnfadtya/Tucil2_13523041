import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printHeader();

            // 1. Input at the begining
            System.out.println("Enter the absolute path from image to compress..");
            System.out.println("Enter (Exit) to quit from this program..");
            String input = scanner.nextLine();
            if ((input).equals("Exit")) {
                printFooter();
                break;
            }
            
            // 2. Input filename for being compressed
            String absolutePathInput = input;
            File inputFile = new File(absolutePathInput);
            if (!inputFile.exists() || !inputFile.isFile()) {
                System.out.println("Error: Path '" + absolutePathInput + "' tidak ditemukan!\n");
                continue;
            }
            
            // 3. Choosing Error Method
            System.out.println("[1] Variance");
            System.out.println("[2] Mean Absolute Deviation");
            System.out.println("[3] Max Pixel Difference");
            System.out.println("[4] Entropy");
            System.out.print("Enter error method based on list above (1-4): ");
            int errorMethod = scanner.nextInt();
            if (errorMethod != 1 && errorMethod != 2 && errorMethod != 3 && errorMethod != 4) {
                System.out.println("Pilihan tidak valid!\n");
                continue;
            }

            // 4. Input required parameter
            System.out.print("Enter threshold: ");
            double threshold = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Enter minimum block size: ");
            int minBlockSize = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter target compression: ");
            double targetCompression = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Enter file output path: ");
            String absolutePathOutput = scanner.nextLine();
            File outputFile = new File(absolutePathOutput);


            // 5. Compressing File
            Compressor compressor = new Compressor(inputFile, errorMethod, threshold, minBlockSize, targetCompression, outputFile);
            try {
                compressor.compressImage();
                System.out.println("\nSuccess compressing file");
            } catch (Exception e) {
                System.out.println("Compressing file failed: " + e.getMessage());
                continue;
            }
        }
    }

    private static void printHeader() {
        System.out.println("======================================");
        System.out.println("        IQ PUZZLER PRO SOLVER         ");
        System.out.println("======================================");
    }

    private static void printFooter() {
        System.out.println("\n======================================");
        System.out.println("        TERIMA KASIH TELAH COBA!      ");
        System.out.println("======================================");
    }
}