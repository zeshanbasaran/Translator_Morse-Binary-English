package InputMessageTypes;


// Import Java API Classes
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

// Import Project Classes
import Exceptions.InvalidCharFoundException;

public class InputEnglishMessage extends InputMessage {
  
  // Symbolic Constants
  private final String EOL = "\n";
  private final String empty_line = "";
  private final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  
  // Instance Variables
  private String line_read = null;
  private String line_buffer;
  private int current_char_index;
  private String last_char_read;
  private boolean first_line_read = false;

  public InputEnglishMessage(BufferedReader input) throws 
                                      FileNotFoundException {
    super(input);
  }

  // Character-Ordinal Conversion Methods

  public int getOrdinal(String chr) {
  // -----------------------------------------------------------
  // Returns ordinal value (position) of chr in the encoding.
  // -----------------------------------------------------------
    int i = 0;
    int ordinal_value = 0;
    boolean found = false;

    while(i < ALPHABET.length() && !found) {
      if(ALPHABET.charAt(i) == chr.charAt(0)) {
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
                                InvalidCharFoundException {
  // -----------------------------------------------------------
  // Retrieves next letter from current line read.
  // Returns null if line_read equals null (end-of-file).
  // Returns EOL if line_read is empty string (blank line).
  // Reads next line of file if at last char of current line.
  //
  // Throws IOException if current input message file not open.
  // Throws InvalidCharFoundException if letter other than
  // A-Z, blank char or \n found.
  // -----------------------------------------------------------
    
    // If no lines read yet, read first line
    if(!first_line_read) {

      // Read first line of file
      line_read = getNextLine();

      // Mark end of line buffer with '*'
      line_buffer = line_read + "*";

      // Set current char as first char of line buffer
      current_char_index = 0;

      // Indicate that first line of file read
      first_line_read = true;
    }

    // At end-of-line?
    if(endOfLine()) {

      // Read next line of file
      line_read = getNextLine();

      // Make end of line buffer with '*'
      line_buffer = line_read + "*";

      // Set current char as first char of line buffer
      current_char_index = 0;
    }

    // At end-of-file?
    if(line_read == null)
      return null;

    // If empty line read, then return EOL string
    if(line_read.equals(empty_line))
      return EOL;
  
    // Return next letter of current line buffer
    String char_read = 
        line_buffer.substring(current_char_index,
                            current_char_index + 1);

    // Save current char read
    last_char_read = char_read;

    // Check for invalid character (only A-Z, 0-9 valid)
    if(invalidChar(char_read))
      throw new InvalidCharFoundException(char_read);
    
    // Increment to next char in current line buffer
    current_char_index = current_char_index + 1;

    return char_read;
  }
  
  public boolean endOfWord() {
  // -----------------------------------------------------------
  // Returns true if last char read a blank char
  // otherwise, returns false.
  // -----------------------------------------------------------
    final char blank_char = ' ';
    return last_char_read.charAt(0) == blank_char;
  }
  
  public boolean endOfLine() {
  // -----------------------------------------------------------
  // Returns true if last char in line buffer read
  // otherwise, returns false.
  // -----------------------------------------------------------
    return current_char_index >= line_buffer.length();
  }

  // Private Methods

  private boolean invalidChar(String chr_str) {
  // -----------------------------------------------------------
  // Returns true if ch_str not A-Z, 0-9, a blank, '\n' or '*'.
  // -----------------------------------------------------------
    final char blank_char = ' ';
    char chr = chr_str.charAt(0);

    return !((int)(chr) >= (int)('A') && (int)(chr) <= (int)('Z') ||
             chr == blank_char || chr == '\n' || chr == '*');
  }
}