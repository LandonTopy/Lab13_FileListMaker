
import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static java.nio.file.StandardOpenOption.CREATE;

public class IOHelper {

    private static JFileChooser chooser = new JFileChooser();

    public static String openFile(ArrayList<String> list) throws IOException {
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


                //Rec holds what the reader finds on the line
                String rec = "";
                //Prints File Path of selected File
                System.out.println("File Path: " + file);
                //Moving through file, reading, and printing each line of the selected file
                while (reader.ready()) {
                    rec = reader.readLine();
                    //Adds each line of the txt file to the Array list
                    list.add(rec);
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
        return null;
    }

    public static void writeFile(ArrayList<String> list, String name) throws IOException{
        ArrayList<String> recs = list;
        String fileName = name;
        //This variable will hold the users current working directory (program folder)
        //"user.dir" is shorthand for current working directory (project folder)
        File workingDirectory = new File(System.getProperty("user.dir"));
        //Path is automatically set for user
        //In this case, the file will be stored in the src folder and the name is already chosen
        Path file = Paths.get(workingDirectory.getPath() + "\\src\\" + name);

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
