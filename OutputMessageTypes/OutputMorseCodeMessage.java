package OutputMessageTypes;

// Import Java API Classes
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class OutputMorseCodeMessage extends OutputMessage {
  
  // Instance Variables
  private String[][] morse_code = new String[26][2];
  private String morse_code_filename = "./Data/MorseCode_Table.txt";

  // Constructor
  public OutputMorseCodeMessage(PrintWriter output) throws
                              FileNotFoundException, IOException {
    super(output);
    populateMorseCode(morse_code_filename);
  }

  // Protected Methods
  
   protected String getLetterWithOrdinal(int ordinal_value) {
  // -----------------------------------------------------------
  // Returns Morse-encoded letter with ordinal value n.
  // (i.e., in nth position of the encodings)
  // -----------------------------------------------------------
    return morse_code[ordinal_value][1];
  }

  // Output Writing Methods
  
  public void writeLetter(int ordinal_value) throws IOException {
  // -----------------------------------------------------------
  // Writes Morse-coded letter to output file with ordinal_value.
  // Throws IOException if output file not open.
  // -----------------------------------------------------------
      writeLine(getLetterWithOrdinal(ordinal_value));
  }
  
  public void writeEndOfWord() throws IOException {
  // -----------------------------------------------------------
  // Writes blank line to output file (for end-of-word)
  // -----------------------------------------------------------
    writeLine("");
  }
  
  public void writeEndOfSentence() throws IOException {
  // -----------------------------------------------------------
  // Writes two blank lines to output file (for end-of-sentence)
  // -----------------------------------------------------------
    writeLine("\n");  
  }
  
  // Private Methods

  private void populateMorseCode(String file_name) throws
                           FileNotFoundException, IOException {
  // -----------------------------------------------------------
  // Populates morse_code array with the Morse code for the
  // upper-case letters and digits 0-9, read from the text file
  // indicated in morse_code_filename.
  //
  // Throws FileNotFoundException if file not found.
  // Throws IOException if output file not open.
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

}