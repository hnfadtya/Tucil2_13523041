import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            IO.printHeader();

            // 1. Input at the begining
            System.out.println("Enter the absolute path from image to compress..");
            System.out.println("Enter 'Exit' to quit from this program..");
            System.out.print(">>> ");
            String input = scanner.nextLine().trim();

            if ((input).equalsIgnoreCase("Exit")) {
                IO.printFooter();
                break;
            }

            // 2. Input required parameter
            File inputFile = new File(input);
            if (!inputFile.exists() || !inputFile.isFile()) {
                System.out.println("Error: Path '" + input + "' cannot be found!\n");
                continue;
            }

            IO.showErrorMethodMenu();
            int errorMethod = IO.getValidatedIntInput(scanner, "Enter method in (1-4): ", 1, 4);
            double threshold = IO.getValidatedDoubleInput(scanner, "\nEnter threshold value: ");
            int minBlockSize = IO.getValidatedIntInput(scanner, "Enter minimum block size: ", 1, Integer.MAX_VALUE);
            File outputFile = IO.getValidatedOutputFile(scanner, "Enter file output path: ");

            // 3. Compressing File
            Compressor compressor = new Compressor(inputFile, errorMethod, threshold, minBlockSize, outputFile);
            try {
                compressor.compressImage();
                System.out.println("\nSuccess compressing file");
            } catch (Exception e) {
                System.out.println("Compressing file failed: " + e.getMessage());
            }
        }
        scanner.close();
    }
}