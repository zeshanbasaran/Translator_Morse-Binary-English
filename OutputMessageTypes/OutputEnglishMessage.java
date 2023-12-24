package OutputMessageTypes;

// Import Java API Classes
import java.io.PrintWriter;
import java.io.IOException;

public class OutputEnglishMessage extends OutputMessage {

  // Symbolic Constants
  private final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  
  // Instance Variables
  private String output_buffer;
  
  // Constructor
  public OutputEnglishMessage(PrintWriter output) {
    super(output);
    clearBuffer();
  }

  // Protected Methods

  protected String getLetterWithOrdinal(int ordinal_value) {
  // -----------------------------------------------------------
  // Returns letter of the alphabet with ordinal value n.
  // (i.e., in nth position of the encodings)
  // -----------------------------------------------------------
    return Character.toString(ALPHABET.charAt(ordinal_value));
  }

  // Output Writing Methods
  public void writeLetter(int ordinal_value) {
  // -----------------------------------------------------------
  // Appends letter with ordinal_value to output buffer.
  // -----------------------------------------------------------
    output_buffer = output_buffer + 
                      getLetterWithOrdinal(ordinal_value);
  }
  
  public void writeEndOfWord() {
  // -----------------------------------------------------------
  // Appends blank char to output buffer.
  // -----------------------------------------------------------
    final String blank_char = " ";
    output_buffer = output_buffer + blank_char;
  }
  
  public void writeEndOfSentence() throws IOException {
  // -----------------------------------------------------------
  // Writes contents of output buffer to currently open output
  // file as a line of text. Also clears line buffer.
  // -----------------------------------------------------------
    writeLine(output_buffer);
    clearBuffer();
  }

  // Private Methods

  private void clearBuffer() {
  // -----------------------------------------------------------
  // Clears line buffer by setting to empty string.
  // -----------------------------------------------------------
    final String empty_string = "";
    output_buffer = empty_string;
  }

}
