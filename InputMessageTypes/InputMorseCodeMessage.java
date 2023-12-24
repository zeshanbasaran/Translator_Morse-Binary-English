package InputMessageTypes;

// Import Java API Classes
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

// Import Project Classes
import Exceptions.InvalidCharFoundException;

public class InputMorseCodeMessage extends InputMessage {
  
  // Instance Variables
  private String morse_letter;
  private String previous_morse_letter = "";
  private String[][] morse_code = new String[26][2];
  private String morse_code_filename = "Data/MorseCode_Table.txt";

  public InputMorseCodeMessage(BufferedReader input) throws
                            FileNotFoundException, IOException {
    super(input);
    populateMorseCode(morse_code_filename);
  }

  // Character-Ordinal Conversion Methods

 public int getOrdinal(String chr) {
  // -----------------------------------------------------------
  // Returns ordinal value (position) of chr in the encoding.
  // -----------------------------------------------------------
    int i = 0;
    int ordinal_value = 0;
    boolean found = false;

    while(i < morse_code.length && !found) {
      if(morse_code[i][1].equals(chr)) {
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
  // Reads and returns next Morse-encoded letter from input file
  // or null if end-of-file found.
  //
  // Also sets previous_morse_letter to current value of
  // morse_letter, and sets morse_letter to new letter read.
  // -----------------------------------------------------------
    final String empty_string = "";

    // Read line (containing one letter of Morse code)
    String line_read = getNextLine();

    // Check for end-of-file
    if(line_read == null)
      return null;
    else {
      // Check for invalid Morse code found
      if(!line_read.equals(empty_string))
        if(invalidMorseCode(line_read))
          throw new InvalidCharFoundException(line_read);

      // Save current morse coded letter as previous
      previous_morse_letter = morse_letter;
      morse_letter = line_read;
    
      // Return Morse-coded letter read
      return morse_letter;
    }
  }

  public boolean endOfWord() {
  // -----------------------------------------------------------
  // Returns true if current morse letter read is a blank line
  // and previous letter read is not (a blank line).
  // -----------------------------------------------------------
    final String blank_line = "";

    return morse_letter.equals(blank_line) &&
           !previous_morse_letter.equals(blank_line);
  }

  public boolean endOfLine() throws IOException {
  // -----------------------------------------------------------  
  // Returns true if current morse letter read and previous
  // letter read are both blank lines.
  // -----------------------------------------------------------
    final String blank_line = "";
    
    return morse_letter.equals(blank_line) &&
           previous_morse_letter.equals(blank_line);
  }

  // Private Methods

  private void populateMorseCode(String file_name) throws
                           FileNotFoundException, IOException {
  // -----------------------------------------------------------
  // Populates array morse_code with the Morse code for the
  // upper-case letters read from the text file indicated in
  // morse_code_filename.
  // -----------------------------------------------------------
    String line;

    // Open Morse code file
    BufferedReader input_file = 
       new BufferedReader(new FileReader(file_name));
    
    // Read first line
    line = input_file.readLine();
    int i = 0;

    // Continue reading lines until end of file
    while(line != null) {

      // Read letter part of Morse code file
      morse_code[i][0] = line.substring(0,1);

      // Read corresponding Morse code of letter
      morse_code[i][1] = line.substring(1, line.length());

      // Read next line of file
      line = input_file.readLine();
      i = i + 1;
    }

    // Close file
    input_file.close();
  }

  private boolean invalidMorseCode(String chr_str) {
  // -----------------------------------------------------------
  // Returns true if ch_str not valid Morse code for A-Z, 0-9.
  // -----------------------------------------------------------
    boolean found = false;

    // Search for chr_str in Morse code table
    int i = 0;

    while(i < morse_code.length && !found)
      if(chr_str.equals(morse_code[i][1]))
        found = true;
      else
        i = i + 1;

    // Return true if Morse code NOT found, else false
    return !found;
  }
}