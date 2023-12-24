package InputMessageTypes;

// Import Java API Classes
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

// Import Project Classes
import Exceptions.InvalidBinaryCodeFoundException;

public class InputBinaryCodeMessage extends InputMessage{
    // Symbolic Constants
    private final String EOL = "\n";
    private final String empty_line = "";

    // Instance Variables
    private String[][] binary_code = new String[26][2];
    private String binary_code_filename = "Data/BinaryCode_Table.txt";
    private String line_read = null;
    private String line_buffer;
    private int current_bit_index;
    private String last_bit_read;
    private boolean first_line_read = false;

    public InputBinaryCodeMessage(BufferedReader input) throws 
                                        FileNotFoundException, IOException {
        super(input);
        populateBinaryCode(binary_code_filename);
    }

    // Character-Ordinal Conversion Methods

    public int getOrdinal(String chr) {
    // -----------------------------------------------------------
    // Returns ordinal value (position) of chr in the encoding.
    // -----------------------------------------------------------
      int i = 0;
      int ordinal_value = 0;
      boolean found = false;
  
      while(i < binary_code.length && !found) {
        if(binary_code[i][1].equals(chr)) {
          ordinal_value = i;
          found = true;
        }
        else
          i = i + 1;
      }
      return ordinal_value;
    }
  
    // Input Reading Methods
  
    public String readLetter() throws IOException, 
                                  InvalidBinaryCodeFoundException {
    // -----------------------------------------------------------
    // Retrieves next letter from current line read.
    // Returns null if line_read equals null (end-of-file).
    // Returns EOL if line_read is empty string (blank line).
    // Reads next line of file if at last char of current line.
    //
    // Throws IOException if current input message file not open.
    // Throws InvalidBinaryCodeFoundException if bits and not in
    // groups of 8, or a bit is not either 0 or 1.
    // -----------------------------------------------------------
      
      // If no lines read yet, read first line
      if(!first_line_read) {
  
        // Read first line of file
        line_read = getNextLine();
  
        // Mark end of line buffer with '*'
        line_buffer = line_read + "*";
  
        // Set current char as first char of line buffer
        current_bit_index = 0;
  
        // Indicate that first line of file read
        first_line_read = true;
      }
  
      // At end-of-line?
      if(endOfLine()) {
  
        // Read next line of file
        line_read = getNextLine();
  
        // Make end of line buffer with '*'
        line_buffer = line_read + "*";
  
        // Set current bit as first bit of line buffer
        current_bit_index = 0;
      }
  
      // At end-of-file?
      if(line_read == null)
        return null;
  
      // If empty line read, then return EOL string
      if(line_read.equals(empty_line))
        return EOL;
    
      // Return next bit of current line buffer
      String bits_read;

      // Check for invalid bit (only 0 or 1 valid)
      try {
            bits_read = line_buffer.substring(current_bit_index, Math.min(current_bit_index + 8, line_buffer.length() - 1));
      } catch (StringIndexOutOfBoundsException e) {
            // If the substring operation goes out of bounds, it means the binary code is too short
            throw new InvalidBinaryCodeFoundException(line_buffer);
    }
  
      // Save current bit read
      last_bit_read = bits_read;
  
      // Check for invalid bit (only A-Z, 0-9 valid)
      if(invalidBinaryCode(bits_read))
        throw new InvalidBinaryCodeFoundException(bits_read);
      
      // Increment to next bit in current line buffer
      current_bit_index+= 8;
  
      return bits_read;
    }
    
    public boolean endOfWord() {
    // -----------------------------------------------------------
    // Returns true if last bit read a blank bit
    // otherwise, returns false.
    // -----------------------------------------------------------
      final char blank_char = ' ';
      return last_bit_read.charAt(0) == blank_char;
    }
    
    public boolean endOfLine() {
    // -----------------------------------------------------------
    // Returns true if last bit in line buffer read
    // otherwise, returns false.
    // -----------------------------------------------------------
      return current_bit_index >= line_buffer.length();
    }
  
    // Private Methods
  
    private void populateBinaryCode(String file_name) throws
                            FileNotFoundException, IOException {
    // -----------------------------------------------------------
    // Populates array binary_code with the Morse code for the
    // upper-case letters read from the text file indicated in
    // binary_code_filename.
    // -----------------------------------------------------------
        String line;

        // Open Binary code file
        BufferedReader input_file = 
        new BufferedReader(new FileReader(file_name));
        
        // Read first line
        line = input_file.readLine();
        int i = 0;

        // Continue reading lines until end of file
        while(line != null) {

            // Read letter part of Binary code file
            binary_code[i][0] = line.substring(0,1);

            // Read corresponding Binary code of letter
            binary_code[i][1] = line.substring(1, line.length());

            // Read next line of file
            line = input_file.readLine();
            i = i + 1;
    }

    // Close file
    input_file.close();
  }

    private boolean invalidBinaryCode(String bits) {
    // -----------------------------------------------------------
    // Returns true if bits are not in groups of 8, and bits are
    // not either 0 or 1
    // -----------------------------------------------------------
        
        // Returns true is bits are not in groups of 8
        if (bits.length() % 8 != 0)
            return true;

        // Returns true if a bit is not a 0 or 1
        for (int i = 0; i < bits.length(); i++) {
            char bit = bits.charAt(i);
            if (bit != '0' && bit != '1') {
                return true;
            }
        }
        
        // If all conditions are satisfied, binary code is valid
        return false;
    }
}
