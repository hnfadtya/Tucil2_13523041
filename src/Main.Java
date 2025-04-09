import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true && (scanner.nextLine()).equals("Exit")) {
            printHeader();

            System.out.println("[1] Variance");
            System.out.println("[2] Mean Absolute Deviation");
            System.out.println("[3] Max Pixel Difference");
            System.out.println("[4] Entropy");
            System.out.println("Press (Exit) to quit from this program..");

            System.out.print("Enter file input path (folder test/): ");
            String filename = scanner.nextLine().trim();
            File inputPath = new File("test/" + filename);

            if (!inputPath.exists()) {
                System.out.println("Error: File '" + filename + "' tidak ditemukan!\n");
                continue;
            }

            System.out.print("Enter error method based on list above (1-4): ");
            String method = scanner.nextLine();
            if (!input.equals("1") || !input.equals("2") || !input.equals("3") || !input.equals("4")) {
                System.out.println("Pilihan tidak valid!\n");
                continue;
            }

            System.out.print("Enter threshold: ");
            String threshold = scanner.nextLine();

            System.out.print("Enter minimum block size: ");
            String minBlockSize = scanner.nextLine();

            System.out.print("Enter target compression: ");
            String targetCompression = scanner.nextLine();

            System.out.print("Enter file output path: ");
            String outputPath = scanner.nextLine();

            System.out.println("\nMemuat file: " + filename);
            long start = System.currentTimeMillis();

            Compressor compressor = new Compressor(inputPath, errorMethod, threshold, minBlockSize, targetCompression, outputPath);
            compressor.run();
            try {
                solver.loadFile(inputFile.getAbsolutePath());
            } catch (IOException e) {
                System.out.println("Gagal memuat file: " + e.getMessage());
                continue;
            }

            long end = System.currentTimeMillis();
            // System.out.printf("Waktu pencarian: " + (end - start) + "ms\n\n");
            // System.out.println("Banyak kasus yang ditinjau: " + solver.getCheckedCases());
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

    private static void saveSolutionToFile(char[][] board, String inputFilename, int row, int col) {
        String outputFilename = "test/output/" + inputFilename.replace(".txt", "_solution.txt");
        File outputFile = new File(outputFilename);

        try {
            outputFile.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(outputFile);

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    writer.write(board[i][j] + " ");
                }
                writer.write("\n");
            }

            writer.close();
            System.out.println("Solusi disimpan ke: " + outputFilename);
        } catch (IOException e) {
            System.out.println("Gagal menyimpan solusi: " + e.getMessage());
        }
    }
}