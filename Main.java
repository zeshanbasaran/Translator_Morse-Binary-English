// English MessageTranslator Program
// -----------------------------------------------------------
// DESCRIPTION 
// This program translates messages between English, Morse
// code and binary code.
//
// English messages must only contain upper-case letters. 
// (No punctuation marks are allowed)
// -----------------------------------------------------------
// MENU OPTIONS
// 1 - Open a file
// 2 - Display current file
// 3 - Translate to Morse code
// 4 - Translate to Binary code
// 5 - Translate to English
// 6 - Quit
// -----------------------------------------------------------

// Import Java API Classes
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.InputMismatchException;

// Import Project Classes
import Exceptions.InvalidCharFoundException;
import Exceptions.InvalidMessageTypeException;
import InputMessageTypes.InputBinaryCodeMessage;
import InputMessageTypes.InputEnglishMessage;
import InputMessageTypes.InputMessage;
import InputMessageTypes.InputMorseCodeMessage;
import OutputMessageTypes.OutputBinaryCodeMessage;
import OutputMessageTypes.OutputEnglishMessage;
import OutputMessageTypes.OutputMorseCodeMessage;
import Translator.Translator;

class Main {
  private static BufferedReader input_file;
  private static InputMessage input_message;
  private static String current_file_name = "";
  private static Scanner keyboard = new Scanner(System.in);

  private final static String ENGLISH_FILE_TYPE = "_ENGLISH";
  private final static String MORSE_CODE_FILE_TYPE = "_MORSE";
  private final static String BINARY_CODE_FILE_TYPE = "_BINARY";
  
  public static void main(String[] args) {
    int selection;
    boolean quit = false;

    displayProgramWelcome();

    while(!quit) {
      try {
        displayOptions();
        selection = getSelection();

        if(selection == 6)
          quit = true;
        else
          executeSelection(selection);
      }
      catch(InvalidMessageTypeException e) {
        System.out.println("\n* INVALID MESSAGE TYPE FOUND: " +
                "(_ENGLISH, _MORSE or _BINARY expected *)\n");
      }
      catch(InvalidCharFoundException e) {
        System.out.println("\n* INVALID CHAR FOUND: " +
                e.getMessage() + " in input file *\n");
      }
      catch(FileNotFoundException e) {
        System.out.println("\n* FILE NAME NOT FOUND: " +
                         e.getMessage() + " *\n");
      }
      catch(IOException e) {
        System.out.println("\n* IO ERROR: " + 
                         e.getMessage() + " *\n");
      }
    } 
    
    System.out.println("Leaving program ...");
  }

  public static void displayProgramWelcome() {
  // -----------------------------------------------------------
  // Displays purpose and requirements of program.
  // -----------------------------------------------------------
    System.out.println(
      "Welcome to the Morse Code Translator Program\n");

    System.out.println(
      "This program translates messages between English,\n" +
      "Morse Code and Binary Code." + "\n");

    System.out.println(
      "Messages can contain upper case letters and\n" +
      "digits. (No punctuation marks are allowed.)\n");
  }
  
  public static void displayOptions() {
  // -----------------------------------------------------------
  // Displays numbered options 1-5.
  // -----------------------------------------------------------
    System.out.println("Menu Options");
    System.out.println("1 - Open File");
    System.out.println("2 - Display File");
    System.out.println("3 - Translate to Morse Code");
    System.out.println("4 - Translate to Binary");
    System.out.println("5 - Translate to English");
    System.out.println("6 - Quit");
  }
  
  public static int getSelection() {
  // -----------------------------------------------------------
  // Prompts for and returns selection 1-5.
  // -----------------------------------------------------------
    int selection = 0;
    boolean valid_selection = false;

    while(!valid_selection) {
      try {
        System.out.print("\nEnter Selection: ");
        selection = keyboard.nextInt();
    
        if(selection < 1 || selection > 6)
          System.out.println(
        "* Invalid Selection - Please Reenter: \n");

        valid_selection = true;
      }
      catch (InputMismatchException e) {
        System.out.println("Please enter a digit");

        // scan past end-of-line char
        keyboard.nextLine();  
      }
    }

    return selection;
  }

  public static void executeSelection(int selection)
                throws FileNotFoundException, IOException {
  // -----------------------------------------------------------
  // Executes selection values 1-4.
  // -----------------------------------------------------------
    switch(selection) {
      case 1: openFile(); break;
      case 2: displayCurrentFile(); break;
      case 3: translateToMorse(); break;
      case 4: translateToBinary(); break;
      case 5: translateToEnglish(); break;
    }
  }

  public static void rewindCurrentFile() throws IOException {
  // -----------------------------------------------------------
  // Rewinds file to start reading from the beginning.
  // -----------------------------------------------------------

    // close current file
    input_file.close();
  
    // re-open current file (to re-read)
    input_file = new BufferedReader(
                    new FileReader(current_file_name + ".txt"));
  } 

  public static void createInputMessage() throws FileNotFoundException,
                                          IOException {
  // -----------------------------------------------------------
  // Constructs the appropriate InputMessage type (English,
  // Morse or Binary) based on global current_file_name and
  // stores in global input_message.
  // -----------------------------------------------------------

    // Ensure that input file at beginning
     rewindCurrentFile();

     // Determine the kind of InputMessage object to create
     if(fileType(current_file_name).equals(ENGLISH_FILE_TYPE))
      input_message = new InputEnglishMessage(input_file);
    else
      if(fileType(current_file_name).equals(MORSE_CODE_FILE_TYPE))
        input_message = new InputMorseCodeMessage(input_file);
      else
        if(fileType(current_file_name).equals(BINARY_CODE_FILE_TYPE))
          input_message = new InputBinaryCodeMessage(input_file);
  }

  // --- OPTION 1
  public static void openFile() throws FileNotFoundException,
                                       IOException {
  // -----------------------------------------------------------
  // Prompts for text file name and opens.
  // -----------------------------------------------------------
    String file_name = "";
    boolean file_opened_successfully = false;

    while(!file_opened_successfully) {

      // Get file name
      System.out.println("Enter file name (without .txt): ");
      file_name = keyboard.next();

      input_file = new BufferedReader(
                      new FileReader(file_name + ".txt"));

      // Indicate file opened successfully
      file_opened_successfully = true;
    }
    
    // Display file opened successfully
    System.out.println(
        "File " + file_name + ".txt" + " opened.\n");
    
    // Update current file name
    current_file_name = file_name;
  }

  // --- OPTION 2
  public static void displayCurrentFile() throws
                    FileNotFoundException, IOException {
  // -----------------------------------------------------------
  // Displays currently open text file on screen.
  // -----------------------------------------------------------
    
    // Display current file name
    System.out.println(
       "\nContents of File " + current_file_name + ".txt:\n");

    // Rewind file to read from start
    rewindCurrentFile();

    // Read first line of file
    String line_read = input_file.readLine();

    // Continue reading until end-of-file found
    while(line_read != null) {
      System.out.println(line_read);
      line_read = input_file.readLine();
    }

    System.out.println();
  }

  // --- OPTION 3
  public static void translateToMorse() throws 
        FileNotFoundException, InvalidCharFoundException,
                               IOException {
  // -----------------------------------------------------------
  // Translates currently open message file to Morse Code and 
  // writes to file <current_file_name>_MORSE.txt
  // -----------------------------------------------------------
    
    // Check that file open
    if(current_file_name.equals(""))
      System.out.println(
        "* NO FILE CURRENTLY OPEN TO TRANSLATE *\n");
    else {

      // Construct InputMessage Object (for current_file_name)
      createInputMessage();
      
      // Construct OutputMessage
      OutputMorseCodeMessage output_message = 
        new OutputMorseCodeMessage(
          new PrintWriter(current_file_name + "_MORSE" + ".txt"));

      // Construct Translator object
      Translator translator = 
        new Translator(input_message, output_message);

      // Begin translation
      translator.translate();
    }
  }

  // --- OPTION 4
  public static void translateToBinary() throws 
        FileNotFoundException, InvalidCharFoundException,
                               IOException {
  // -----------------------------------------------------------
  // Translates currently open message file to Binary code and
  // writes to file <current_file_name>_BINARY.txt
  // -----------------------------------------------------------
    
    // Check that file open
    if(current_file_name.equals(""))
      System.out.println(
        "* NO FILE CURRENTLY OPEN TO TRANSLATE *\n");
    else {
    
      // Construct InputMessage Object (for current_file_name)
      createInputMessage();

      // Construct OutputMessage
      OutputBinaryCodeMessage output_message = 
        new OutputBinaryCodeMessage(
          new PrintWriter(current_file_name + "_BINARY" + ".txt"));

      // Construct Translator object
      Translator translator = 
        new Translator(input_message, output_message);

      // Begin translation
      translator.translate();
    }
  }

  // --- OPTION 5
  public static void translateToEnglish() throws
                      FileNotFoundException, IOException {
  // -----------------------------------------------------------
  // Translates currently open message file to English and 
  // writes to file <current_file_name>_ENGLISH.txt.
  // -----------------------------------------------------------
    
    // Check that file open
    if(current_file_name.equals(""))
      System.out.println(
        "* NO FILE CURRENTLY OPENED TO TRANSLATE *\n");
    else {
     
      // Construct InputMessage Object (for current_file_name)
      createInputMessage(); 

      // Construct OutputMessage
      OutputEnglishMessage output_message = 
        new OutputEnglishMessage(
          new PrintWriter(current_file_name + "_ENGLISH" + ".txt"));

      // Construct Translator object
      Translator translator = 
        new Translator(input_message, output_message);

      // Begin translation
      translator.translate();
    }
  }

  // Private Methods

  private static String fileType(String file_name) throws 
                                InvalidMessageTypeException {

    if(!file_name.contains("_"))         
      throw new InvalidMessageTypeException("Missing _xxxx");
    else {
      int underscore_loc = file_name.lastIndexOf('_');
      String suffix = 
        file_name.substring(underscore_loc, file_name.length());

      if(suffix.equals("_ENGLISH"))
        return ENGLISH_FILE_TYPE;
      else
        if(suffix.equals("_MORSE"))
          return MORSE_CODE_FILE_TYPE;
        else
          if(suffix.equals("_BINARY"))
            return BINARY_CODE_FILE_TYPE;
          else
            throw new InvalidMessageTypeException();
    }
  }
}