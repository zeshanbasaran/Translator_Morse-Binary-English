package Translator;

// Import Java API Classes
import java.io.IOException;

// Import Project Classes
import Exceptions.InvalidCharFoundException;
import InputMessageTypes.InputMessage;
import OutputMessageTypes.OutputMessage;

public class Translator {

  // Instance Variables
  private InputMessage input_message;
  private OutputMessage output_message;

  // Constructpr
  public Translator(InputMessage mesg_in, 
                    OutputMessage mesg_out) {
  // -----------------------------------------------------------
  // Passed specific InputMessage and OutputMessage types to
  // translate from and to
  // -----------------------------------------------------------
    input_message = mesg_in;
    output_message = mesg_out;
  }

  // Translate Method
  public void translate() throws IOException, 
                   InvalidCharFoundException {
  // -----------------------------------------------------------
  // Translates contents of input_message to output_message
  // (written to file <original_file_name>_<type>.txt, where
  // <type> is _ENGLISH, _MORSE, or _BINARY.
  // -----------------------------------------------------------
    String current_letter;

    // Read first letter of mesg_in
    current_letter = input_message.readLetter();

    // Read letter-by-letter until end-of-file found
    while(current_letter != null) {
      
      // Write appropriate chars if end-of-word or end-of-file
      if(input_message.endOfLine())
        output_message.writeEndOfSentence();
      else
        if(input_message.endOfWord())
          output_message.writeEndOfWord();
      else
        // Write single letter to mesg_out
        output_message.writeLetter(
                  input_message.getOrdinal(current_letter));

      // Read next letter from mesg_in
      current_letter = input_message.readLetter();
    }
    
    // Close files
    input_message.close();
    output_message.close();
  }
}