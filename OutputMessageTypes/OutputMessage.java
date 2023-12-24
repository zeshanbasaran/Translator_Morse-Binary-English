package OutputMessageTypes;

// Import Java API Classes
import java.io.PrintWriter;
import java.io.IOException;

public abstract class OutputMessage {
  private PrintWriter output;

  public OutputMessage(PrintWriter output) {
    this.output = output;
  }

  public void writeLine(String line) throws IOException {
  // -----------------------------------------------------------
  // Writes provided line of text to output file.
  // Throws IOException if output file closed.
  // -----------------------------------------------------------
    output.write(line + "\n");
  }
  
  public void close() throws IOException {
  // -----------------------------------------------------------
  // Flushes and closes currently open output file.
  // -----------------------------------------------------------
      output.flush();
      output.close();
  }

  // Abstract Methods

  protected abstract String getLetterWithOrdinal(int ordinal_value);
  // -----------------------------------------------------------
  // Returns letter (or encoded letter) with ordinal value n.
  // (i.e., in nth position of the encodings)
  // -----------------------------------------------------------

  public abstract void writeLetter(int ordinal_value) throws 
                                                    IOException;
  // -----------------------------------------------------------
  // Writes letter with ordinal_value to output file.
  // -----------------------------------------------------------

  public abstract void writeEndOfWord() throws IOException;
  // -----------------------------------------------------------
  // Writes appropriate end-of-word chars to output file.
  // -----------------------------------------------------------

  public abstract void writeEndOfSentence() throws IOException;
  // -----------------------------------------------------------
  // Writes appropriate end-of-sentence chars to output file.
  // -----------------------------------------------------------

}