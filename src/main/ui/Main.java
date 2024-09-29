package ui;

import java.io.FileNotFoundException;

public class Main {
    //EFFECTS: launch the post application with GUI
    public static void main(String[] args) {
        try {
            new PostOfficeGUI(); // create a new instance of the PostOfficeGUI class
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}



