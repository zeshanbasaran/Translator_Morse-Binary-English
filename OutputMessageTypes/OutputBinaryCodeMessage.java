package OutputMessageTypes;

import java.io.*;

public class OutputBinaryCodeMessage extends OutputMessage {

    // Instance Variables
    private String output_buffer;
    private String[][] binary_code = new String[26][8];
    private String binary_code_filename = "./Data/BinaryCode_Table.txt";

    // Constructor
    public OutputBinaryCodeMessage(PrintWriter output) throws
                              FileNotFoundException, IOException {
        super(output);
        populateBinaryCode(binary_code_filename);
        clearBuffer();
    }

    // Protected Methods
    protected String getLetterWithOrdinal(int ordinal_value) {
        // Returns Binary-encoded letter with ordinal value n.
        return binary_code[ordinal_value][1];
    }

    // Output Writing Methods
    public void writeLetter(int ordinal_value) throws IOException {
        // Writes Binary-coded letter to output file with ordinal_value.
        writeBinary(getLetterWithOrdinal(ordinal_value));
    }

    public void writeEndOfWord() throws IOException {
        // Writes a space for end-of-word.
        writeBinary("00100000");
    }

    public void writeEndOfSentence() throws IOException {
        // Writes two spaces for end-of-sentence.
        writeBinary("00100000");
        writeBinary("00100000");
    }

    // Private Methods
    private void populateBinaryCode(String file_name) throws
                           FileNotFoundException, IOException {
        // Populates binary_code array with the Binary code for the
        // upper-case letters, read from the text file
        // indicated in binary_code_filename.
        try (BufferedReader input_file = new BufferedReader(new FileReader(file_name))) {
            String line;
            int i = 0;

            // Continue reading lines until end of file
            while ((line = input_file.readLine()) != null) {
                // Read letter part of Binary code file
                binary_code[i][0] = line.substring(0, 1);

                // Read corresponding Binary code of letter
                binary_code[i][1] = line.substring(1, line.length());

                i++;
            }
        }
    }

    // Private Methods

    private void clearBuffer() {
        // Clears the output buffer by setting it to an empty string.
        output_buffer = "";
    }

    private void writeBinary(String binaryString) throws IOException {
        // Writes binaryString to the output buffer.
        output_buffer += binaryString;

        // Check if the output buffer has reached a complete line (multiple of 8 bits)
        if (output_buffer.length() >= 8 && output_buffer.length() % 8 == 0) {
            // Write the complete line to the output file
            writeLine(output_buffer);

            // Clear the buffer
            clearBuffer();
        }
    }
}