package ee.ttu.algoritmid.tsp;

import java.io.*;
import java.net.URISyntaxException;

public class MatrixLoader {

    private static final String SEPARATOR = " ";

    private static final String NOT_ENOUGH_ROWS = "Input file does not contain enough rows.";
    private static final String NOT_ENOUGH_COLUMNS = "Input file does not contain enough columns.";
    private static final String FILE_ERROR = "Check that your input file is with a correct name and located in the " +
            "same directory as the MatrixLoader class.";

    public static int[][] loadFile(String filename, int matrixHeight) throws Exception {
        final File file = getFileInThisDirectory(filename);
        final BufferedReader reader = new BufferedReader(new FileReader(file));

        int[][] matrix = new int[matrixHeight][matrixHeight];

        for (int row = 0; row < matrixHeight; row++) {
            final String line = reader.readLine();
            if (line == null) {
                throw new IllegalArgumentException(NOT_ENOUGH_ROWS);
            }

            final String[] pieces = line.split(SEPARATOR);
            if (pieces.length < matrixHeight) {
                throw new IllegalArgumentException(NOT_ENOUGH_COLUMNS);
            }

            int colIndex = 0;
            for (int col = 0; col < pieces.length; col++) {
                if (colIndex >= matrixHeight) {
                    break;
                }

                try {
                    final int value = Integer.valueOf(pieces[col]);
                    matrix[row][colIndex] = value;
                    colIndex++;
                } catch(NumberFormatException e) {

                }
            }

            if (colIndex != matrixHeight) {
                throw new IllegalArgumentException(NOT_ENOUGH_COLUMNS);
            }
        }

        reader.close();

        return matrix;
    }

    private static File getFileInThisDirectory(String filename) throws URISyntaxException, IllegalArgumentException {
        try {
            return new File(MatrixLoader.class.getResource(filename).toURI());
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(FILE_ERROR);
        }
    }
}
