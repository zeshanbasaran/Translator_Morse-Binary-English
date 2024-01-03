# Translator_Morse-Binary-English

This Java program was created to translate messages between English, Morse code, and ASCII binary code for my COSC 237 final project. I utilized concepts of subclasses, abstract classes, and polymorphism to practice proper code organization. The program opens with a menu where users can choose their desired action.

```java
  public static void executeSelection(int selection)
                throws FileNotFoundException, IOException {
  // -----------------------------------------------------------
  // Executes selection values 1-5.
  // -----------------------------------------------------------
    switch(selection) {
      case 1: openFile(); break;
      case 2: displayCurrentFile(); break;
      case 3: translateToMorse(); break;
      case 4: translateToBinary(); break;
      case 5: translateToEnglish(); break;
    }
  }
```

Translations are produced through opening a text file, reading the file, and exporting the translation to a new text file.

## Credits
- Assignment instructions created by Professor Dierbach at Towson University
