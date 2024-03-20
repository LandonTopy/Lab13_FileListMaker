import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class InputHelper {
    public static void main(String[] args) {
        //TestMethods
        Scanner scan = new Scanner(System.in);
        System.out.println(getRegExString(scan, "Enter your Birthday", "\\d{2}/\\d{2}/\\d{4}"));
        System.out.println(getDouble(scan, "Please enter a double value"));
        System.out.println(getRangedDouble(scan, "Please enter a double between 1-10", 1, 10));
        System.out.println(getInt(scan, "Please enter an int value"));
        System.out.println(getPositiveRangedInt(scan, "Please enter a positive int value that is not 0"));
        System.out.println(getYNConfirm(scan, "Yes or No [Y/N]"));
        System.out.println(getNonZeroLenString(scan, "Enter a string with at least 1 character"));
    }

    //This method loops until a valid input is received. Returns int value.
    public static int getInt(Scanner in, String prompt) {
        boolean done = false;
        int x = 0;
        System.out.println(prompt);
        do {
            if (in.hasNextInt()) {
                x = in.nextInt();
                done = true;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
            in.nextLine(); //Buffer
        } while (!done);
        return x;
    }


    public static int getRangedInt(Scanner in, String prompt, int min, int max) {
        boolean done = false;
        int x = 0;
        System.out.println(prompt);
        do {
            if (in.hasNextInt()) {
                x = in.nextInt();
                if (x >= min && x <= max) {
                    done = true;
                } else {
                    System.out.println("Out of range. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please try again.");
            }
            in.nextLine(); //Buffer
        } while (!done);
        return x;
    }

    public static double getDouble(Scanner in, String prompt) {
        boolean done = false;
        double x = 0;
        System.out.println(prompt);
        do {
            if (in.hasNextDouble()) {
                x = in.nextDouble();
                done = true;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
            in.nextLine(); //Buffer
        } while (!done);
        return x;
    }

    public static double getRangedDouble(Scanner in, String prompt, double min, double max) {
        boolean done = false;
        double x = 0;
        System.out.println(prompt);
        do {
            if (in.hasNextDouble()) {
                x = in.nextDouble();
                if (x >= min && x <= max) {
                    done = true;
                } else {
                    System.out.println("Out of range. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please try again.");
            }
            in.nextLine(); //Buffer
        } while (!done);
        return x;
    }

    public static int getPositiveRangedInt(Scanner in, String prompt) {
        boolean done = false;
        int x = 0;
        System.out.println(prompt);
        do {
            if (in.hasNextInt()) {
                x = in.nextInt();
                if (x > 0) {
                    done = true;
                } else {
                    System.out.println("Out of range. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        } while (!done);
        return x;
    }

    public static boolean getYNConfirm(Scanner in, String prompt) {
        boolean done = false;
        boolean x = false;
        String y;
        System.out.println(prompt);
        do {
            y = in.nextLine();
            if (y.equalsIgnoreCase("Y")) {
                x = true;
                done = true;
            } else if (y.equalsIgnoreCase("N")) {
                done = true;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        } while (!done);
        return x;
    }

    public static String getNonZeroLenString(Scanner in, String prompt) {
        boolean done = false;
        String x;
        System.out.println(prompt);
        do {
            x = in.nextLine();
            int length = x.length();
            if (length > 0) {
                done = true;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
            in.nextLine(); //Buffer
        } while (!done);
        return x;
    }

    public static String getRegExString(Scanner in, String prompt, String regEx) {
        boolean done = false;
        String input;
        do {
            System.out.println(prompt);
            input = in.nextLine();
            if (input.matches(regEx)) {
                done = true;
            } else {
                System.out.println("Invalid input");
            }
        } while (!done);
        return input;
    }
    public static void readFile(String[] args) throws IOException {

        //Creates a JFileChooser object that will open the JFileChooser Wizard GUI
        //Allows user to search for files that they want read by the program
        //Much easier for the user than typing in a file directory manually
        JFileChooser chooser = new JFileChooser();

        //The try block will prompt the user to open a file
        //If an error occurs in this block, the catch block will handle the IO Exception
        try {
            //This variable will hold the users current working directory (program folder)
            //"user.dir" is shorthand for current working directory (project folder)
            File workingDirectory = new File(System.getProperty("user.dir"));

            //This will make the JFileChooser GUI default to look in the workingDirectory first
            //User can still navigate out of this folder if desired
            chooser.setCurrentDirectory(workingDirectory);

            //Checks to see if the user picks a file in the file chooser wizard
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                //Stores user selected file
                File selectedFile = chooser.getSelectedFile();
                //Holds the path to the selected file
                Path file = selectedFile.toPath();

                //InputStream is needed in order to create our Buffered Reader
                //InputStream allows bytes of data to be read from a file
                //BufferedReader is our actual "reader" tool that will be used to read characters from file
                InputStream in = new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));


                //Starts at line 0 and moves line by line through the file
                int line = 0;
                //Rec holds what the reader finds on the line
                String rec = "";
                //Prints File Path of selected File
                System.out.println("File Path: " + file);
                //Moving through file, reading, and printing each line of the selected file
                while (reader.ready()) {
                    rec = reader.readLine();
                    line++;
                    //Prints the line # and the contents of the line
                    System.out.printf("\nLine%4d: %-60s ", line, rec);
                }
                reader.close(); // must close the file to seal it and clear buffer
                System.out.println("\n\nData file read!"); //Success message
            } else {
                //This else statement is hit when the user closes the JFileChooser Wizard without selecting file
                System.out.println("File not selected. Please restart program.");
                System.exit(0); //Shuts down program
            }
        }
        //This catch block is hit when the user file the user attempts to open a file that can not be found
        catch (FileNotFoundException e) {
            System.out.println("File not found!");
            //Prints the error along with additional info related to the error
            e.printStackTrace();
        }
        //This catch block is hit for all other IO Exceptions
        catch (IOException e) {
            //Prints the error along with additional info related to the error
            e.printStackTrace();
        }
    }

    public static void writingFile(String[] args) throws IOException   {

        //Sample data that is being added to an ArrayList named recs
        ArrayList<String> recs = new ArrayList<String>();
        recs.add("Sample data Line 1");
        recs.add("Sample data Line 2");
        recs.add("Sample data Line 3");

        //This variable will hold the users current working directory (program folder)
        //"user.dir" is shorthand for current working directory (project folder)
        File workingDirectory = new File(System.getProperty("user.dir"));
        //Path is automatically set for user
        //In this case, the file will be stored in the src folder and the name is already chosen
        Path file = Paths.get(workingDirectory.getPath() + "\\src\\data.txt");

        //The try block will attempt to write a new txt file
        //If an error occurs in this block, the catch block will handle the IO Exception
        try {
            //OutputStream is needed in order to create our Buffered Writer
            //OutputStream allows bytes of data to be written to a file
            //BufferedWriter is our actual writing tool that will be used to write characters to file
            OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

            //Actually writing data from recs to new file
            for (String r : recs) {
                //r is the String being written
                //0 is the index of the String the writer starts writing at
                //r.length is the index of the String the writer stops writing at
                writer.write(r, 0, r.length());
                //need new line added before we write more data - ensures next bit of data is put on own line
                writer.newLine();
            }
            writer.close(); //closes file and clears buffer
            System.out.println("Data file written!");
        }
        //This catch block is hit for a variety of IO Exceptions
        catch (IOException e) {
            //Prints the error along with additional info related to the error
            e.printStackTrace();
        }
    }
}


