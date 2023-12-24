package InputMessageTypes;

// Import Java API Classes
import java.io.BufferedReader;
import java.io.IOException;

public abstract class InputMessage {
  private BufferedReader input;
  private boolean EOF = false;  // end of file flag

  public InputMessage(BufferedReader input) {
    this.input = input;
  }

  public String getNextLine() throws IOException {
  // -----------------------------------------------------------
  // Reads and returns next line of input file.
  // If end-of-file found, sets EOF to true and returns null.
  // -----------------------------------------------------------
    String line = input.readLine();

    // End of file?
    if(line == null) {
      EOF = true;
    }

    return line;
  }

  public boolean endOfMessage() {
  // -----------------------------------------------------------
  // Returns true if end of file found, otherwise returns false.
  // -----------------------------------------------------------
    return EOF;
  }

  public void close() throws IOException {
  // -----------------------------------------------------------
  // Closes current file.
  // Throws IOException is file already closed.
  // -----------------------------------------------------------
    input.close();
  }

  // Abstract Methods

  public abstract int getOrdinal(String chr);
  // -----------------------------------------------------------
  // Returns ordinal value (position) of chr in the encoding.
  // -----------------------------------------------------------

  public abstract String readLetter() throws IOException;
  // -----------------------------------------------------------
  // Reads and returns next letter of message.
  // -----------------------------------------------------------

  public abstract boolean endOfWord();
  // -----------------------------------------------------------
  // Returns true if at end-of-word of message reading,
  // otherwise, returns false.
  // -----------------------------------------------------------

  public abstract boolean endOfLine() throws IOException;
  // -----------------------------------------------------------
  // Returns true if at end-of-line of message reading,
  // otherwise, returns false.
  // -----------------------------------------------------------
}