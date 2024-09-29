package ui;

import model.Event;
import model.Parcel;
import model.Person;
import persistence.JsonReader;
import persistence.JsonWriter;

import model.EventLog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


// Post office application with GUI
public class PostOfficeGUI extends JFrame implements ActionListener {
    private static final String JASON_STORE_1 = "./data/myFile1.json";
    private static final String JASON_STORE_2 = "./data/myFile2.json";
    private JsonWriter jsonWriter1;
    private JsonReader jsonReader1;
    private JsonWriter jsonWriter2;
    private JsonReader jsonReader2;

    private Parcel parcel1;
    private Parcel parcel2;
    private Parcel parcel3;
    private Parcel parcel4;
    private List<Parcel> parcels;

    private Person person1;
    private Person person2;
    private List<Person> persons;
    private Scanner input;

    private ImageIcon teddyBearImage;
    private ImageIcon booksImage;
    private ImageIcon bottlesImage;
    private ImageIcon riceImage;

    // GUI components
    private JButton checkButton;
    private JButton weighButton;
    private JButton deliveryButton;
    private JButton viewButton;
    private JButton inspectButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton quitButton;
    private JButton priorityButton;

    private JTextArea outputArea;
    private JComboBox<String> parcelComboBox;
    private JComboBox<String> personComboBox;

    private static final String STATUS_OK = "Content of selected parcel can only be seen by 'view' after inspection!";
    private JLabel statusLabel;

    private JPanel postPanel;
    private JLabel imageAsLabel;


    //EFFECTS: run the post application with GUI
    public PostOfficeGUI() throws FileNotFoundException {
        super("Post Office GUI"); // set the title of the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set the default close operation
        setLayout(new BorderLayout()); // set the layout of the window
        setSize(800, 1600); // set the size of the window
        input = new Scanner(System.in);
        init(); // initialize the data and components
        addComponents(); // add the components to the window

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); // set it vertical

        setPanel();


        add(outputArea); // add output area to windows

        // Add a WindowListener to perform actions when the window is closing.
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleWindowClosing();
            }
        });

        setVisible(true); // make the window visible
    }


    //MODIFIES: this
    //EFFECTS: print out the event log when user quits the application.
    private void handleWindowClosing() {
        System.out.println("Window is closing. Event log:");

        for (Event next: EventLog.getInstance()) {
            System.out.println(next.toString());
        }
    }

    //MODIFIES: this
    //EFFECTS: initialize the panel and output area
    public void setPanel() {
        statusLabel = new JLabel(STATUS_OK);
        add(statusLabel,BorderLayout.SOUTH);

        postPanel = new JPanel();
        postPanel.setPreferredSize(new Dimension(200,200));
        add(postPanel);
        loadImages();

        outputArea = new JTextArea(); // output area
        outputArea.setEditable(false); // set it not editable
    }

    //MODIFIES: this
    //EFFECTS: initialize the data and components
    public void init() throws FileNotFoundException {
        initData();

        input = new Scanner(System.in);
        input.useDelimiter("\n");

        initButton();

        outputArea = new JTextArea();
        outputArea.setEditable(false);

//        inputField = new JTextField();
//        inputField.setEditable(true);

        initComboBox();

        // add action listeners to the buttons
        checkButton.addActionListener(this);
        weighButton.addActionListener(this);
        deliveryButton.addActionListener(this);
        viewButton.addActionListener(this);
        inspectButton.addActionListener(this);
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
        quitButton.addActionListener(this);
        priorityButton.addActionListener(this);
    }

    //MODIFIES: this
    //EFFECTS: initialize the buttons
    public void initButton() {
        checkButton = new JButton("Check");
        weighButton = new JButton("Weigh");
        deliveryButton = new JButton("Delivery");
        viewButton = new JButton("View");
        inspectButton = new JButton("Inspect");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        quitButton = new JButton("Quit");
        priorityButton = new JButton("Priority");
    }

    //MODIFIES: this
    //EFFECTS: initialize the data
    public void initData() {
        jsonWriter1 = new JsonWriter(JASON_STORE_1);
        jsonReader1 = new JsonReader(JASON_STORE_1);
        jsonWriter2 = new JsonWriter(JASON_STORE_2);
        jsonReader2 = new JsonReader(JASON_STORE_2);

        parcel1 = new Parcel("Tina", 8, "teddy bear");
        parcel2 = new Parcel("Frank", 5, "books");
        parcel3 = new Parcel("Tina", 55, "bottles");
        parcel4 = new Parcel("Tina", 25, "rice");
        parcels = new ArrayList<>();
        parcels.add(parcel1);
        parcels.add(parcel2);
        parcels.add(parcel3);
        parcels.add(parcel4);

        person1 = new Person("Tina");
        person2 = new Person("Frank");
        persons = new ArrayList<>();
        persons.add(person1);
        persons.add(person2);

    }

    //MODIFIES: this
    //EFFECTS: initialize the combo boxes
    public void initComboBox() {
        parcelComboBox = new JComboBox<>();
        parcelComboBox.addItem(parcel1.getHeading());
        parcelComboBox.addItem(parcel2.getHeading());
        parcelComboBox.addItem(parcel3.getHeading());
        parcelComboBox.addItem(parcel4.getHeading());

        personComboBox = new JComboBox<>();
        personComboBox.addItem("Tina");
        personComboBox.addItem("Frank");
    }

    //MODIFIES: this
    //EFFECTS: add the components to the window
    public void addComponents() {
        JPanel buttonPanel = new JPanel(); // create a panel for the buttons
        buttonPanel.setLayout(new GridLayout(4, 2)); // set the layout of the panel
        // add the buttons to the panel
        buttonPanel.add(checkButton);
        buttonPanel.add(weighButton);
        buttonPanel.add(deliveryButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(inspectButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(quitButton);
        buttonPanel.add(priorityButton);

//        buttonPanel.add(createPrintCombo());

        JPanel comboPanel = new JPanel(); // create a panel for the combo boxes
        comboPanel.setLayout(new GridLayout(1, 2)); // set the layout of the panel
        // add the combo boxes to the panel
        comboPanel.add(parcelComboBox);
        comboPanel.add(personComboBox);

        JScrollPane scrollPane = new JScrollPane(outputArea); // create a scroll pane for the output area

        // add the panels and the scroll pane to the window
        add(buttonPanel, BorderLayout.SOUTH);
        add(comboPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.NORTH);
    }



    //MODIFIES: this
    //EFFECTS: process user command from the GUI
    public void processCommand(ActionEvent e) {
        Object source = e.getSource(); // get the source of the event
        if (source == quitButton) { // if the source is the quit button
            handleWindowClosing();
            System.exit(0); // exit the program
        } else if (source == checkButton) { // if the source is the check button
            checkName(); // call the checkName method
        } else if (source == weighButton) { // if the source is the weigh button
            checkWeight(); // call the checkWeight method
        } else if (source == deliveryButton) { // if the source is the delivery button
            doDelivery(); // call the doDelivery method
        } else if (source == viewButton) { // if the source is the view button
            viewHeading(); // call the viewHeading method
        } else if (source == inspectButton) { // if the source is the inspect button
            doInspect(); // call the doInspect method
        } else if (source == saveButton) { // if the source is the save button
            savePerson(); // call the savePerson method
        } else if (source == loadButton) { // if the source is the load button
            loadPerson(); // call the loadPerson method
        } else if (source == priorityButton) {
            priorityParcel();
        }
    }

    // this method is required to implement the ActionListener interface
    @Override
    public void actionPerformed(ActionEvent e) {
        processCommand(e); // call the processCommand method with the event
    }

    // MODIFIES: this
    // EFFECTS: Check whether owner of the parcel belongs to the claimer
    public void checkName() {
        Parcel selected = null;
        for (Parcel parcel: parcels) {
            if (parcel.getHeading() == parcelComboBox.getSelectedItem()) {
                selected = parcel;
            }
        } // get the selected parcel from the combo box
        String parcelName = selected.getName();
        Person identity = null;
        for (Person person: persons) {
            if (person.getPersonName() == personComboBox.getSelectedItem()) {
                identity = person;
            }
        } // get the selected parcel from the combo box
        String personName = identity.getPersonName();

        if (parcelName.equals(personName)) {
            outputArea.append("This parcel matched the owner !!\n"); // append the message to the output area
            selected.doVerify();
            identity.addParcel(selected);
        } else {
            outputArea.append("This name doesn't match the owner of this parcel\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: Check the weight of the selected parcel and display it in the output area
    public void checkWeight() {
        Parcel selected = null;
        for (Parcel parcel: parcels) {
            if (parcel.getHeading() == parcelComboBox.getSelectedItem()) {
                selected = parcel;
            }
        } // get the selected parcel from the combo box
        double weight = selected.getWeight(); // get the weight of the parcel
        if (selected.getStatus()) {
            outputArea.append("Now the weight of parcel is:" + weight + "kg\n");
            outputArea.append("The parcel is possible to delivery!\n");
        } else {
            outputArea.append("Please check the owner of this parcel first.\n");
        }
    }

    //MODIFIES: this
    //EFFECTS: return each person's priority parcel which is the first item in their parcel list.
    public void priorityParcel() {
        outputArea.append("The priority items of each person are:\n"); // append the message to the output area
        List<Person> personList = new ArrayList<>();
        personList.add(person1);
        personList.add(person2);
        for (Person person: personList) {
            List<Parcel> parcelList = person.getAllParcels();
            List<String> parcelHeadings = new ArrayList<>();
            for (Parcel parcel: parcelList) {
                if (parcel.getStatus() && parcel.getPostmark()) {
                    parcelHeadings.add(parcel.getHeading());
                }
            }
            if (parcelHeadings.isEmpty()) {
                outputArea.append(person.getPersonName() + " has no items\n"); // append the message to the output area
            } else {
                String priority = parcelHeadings.get(0);
                outputArea.append(person1.getPersonName() + ": " + priority + "\n");
            }
        }

    }


    // MODIFIES: this
    // EFFECTS: delivery the selected parcel from the list and add it to owner's parcel list.
    public void doDelivery() {
        Parcel selected = null;
        for (Parcel parcel: parcels) {
            if (parcel.getHeading() == parcelComboBox.getSelectedItem()) {
                selected = parcel;
            }
        } // get the selected parcel from the combo box
        String name = selected.getName(); // get the name of the parcel
        String content = selected.getHeading(); // get the content of the parcel
        if (selected.getStatus()) {
            selected.getStamped();
//            parcelComboBox.removeItem(selected.getHeading()); // remove the parcel from the combo box
            outputArea.append("The parcel for " + name + " with " + content + " has been delivered.\n");
            // append the message to the output area
        } else {
            outputArea.append("Please check the weight first!");
        }
    }

    // MODIFIES: this
    // EFFECTS: Display each person's parcel list and show the content of selected parcel after inspection.
    public void viewHeading() {
        outputArea.append("The following parcels are already delivered:\n"); // append the message to the output area
        List<Parcel> parcelList1 = person1.getAllParcels();
        List<String> parcelHeadings1 = new ArrayList<>();
        for (Parcel parcel: parcelList1) {
            if (parcel.getStatus() && parcel.getPostmark()) {
                parcelHeadings1.add(parcel.getHeading());
            }
        }
        outputArea.append(person1.getPersonName() + ": " + parcelHeadings1 + "\n");
        // append the name and content to the output area
        List<Parcel> parcelList2 = person2.getAllParcels();
        List<String> parcelHeadings2 = new ArrayList<>();
        for (Parcel parcel: parcelList2) {
            if (parcel.getStatus() && parcel.getPostmark()) {
                parcelHeadings2.add(parcel.getHeading());
            }
        }
        outputArea.append(person2.getPersonName() + ": " + parcelHeadings2 + "\n");
        // append the name and content to the output area
    }

    // MODIFIES: this
    // EFFECTS: Inspect the content of the selected parcel and display it in the output area
    //          Remind: only if you have inspected the parcel, then in viewHeading(), you can see the content.
    public void doInspect() {
        Parcel selected = null;
        for (Parcel parcel: parcels) {
            if (parcel.getHeading() == parcelComboBox.getSelectedItem()) {
                selected = parcel;
            }
        } // get the selected parcel from the combo box
        String parcelName = selected.getHeading();
        if (parcelName == "teddy bear") {
            setTeddyBear();
        } else if (parcelName == "books") {
            setBooks();
        } else if (parcelName == "bottles") {
            setBottles();
        } else {
            setRice();
        }
    }

    //MODIFIES: this
    //EFFECTS: show the image of teddy bear
    public void setTeddyBear() {
        removeExistingImage();
        imageAsLabel = new JLabel(teddyBearImage);
        postPanel.add(imageAsLabel);
    }

    //MODIFIES: this
    //EFFECTS: show the image of books
    public void setBooks() {
        removeExistingImage();
        imageAsLabel = new JLabel(booksImage);
        postPanel.add(imageAsLabel);
    }

    //MODIFIES: this
    //EFFECTS: show the image of bottles
    public void setBottles() {
        removeExistingImage();
        imageAsLabel = new JLabel(bottlesImage);
        postPanel.add(imageAsLabel);
    }

    //MODIFIES: this
    //EFFECTS: show the image of rice.
    public void setRice() {
        removeExistingImage();
        imageAsLabel = new JLabel(riceImage);
        postPanel.add(imageAsLabel);
    }

    //EFFECTS: load all images for GUI
    private void loadImages() {
        String sep = System.getProperty("file.separator");
        teddyBearImage = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "teddyBear.png");
        booksImage = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "books.png");
        bottlesImage = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "bottles.png");
        riceImage = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "rice.png");

    }

    //MODIFIES: this
    //EFFECTS: remove the image shown before
    private void removeExistingImage() {
        if (imageAsLabel != null) {
            postPanel.remove(imageAsLabel);
        }
    }


    // MODIFIES: this
    // EFFECTS: Save the state of the selected person to a JSON file and display it in the output area
    public void savePerson() {
//        Person selected = (Person) personComboBox.getSelectedItem(); // get the selected person from the combo box
        String name1 = person1.getPersonName(); // get the name of the person
        String name2 = person2.getPersonName();
        String fileName1 = "myFile1.json"; // create a file name based on the name
        String fileName2 = "myFile2.json";
        try {
            jsonWriter1.open();
            jsonWriter1.write(person1);
            jsonWriter1.close();

            jsonWriter2.open();
            jsonWriter2.write(person2);
            jsonWriter2.close(); // close the JSON writer

            outputArea.append("Saved " + name1 + " to " + fileName1 + ".\n");
            outputArea.append("Saved " + name2 + " to " + fileName2 + ".\n"); // append the message to the output area
        } catch (FileNotFoundException e) {
            outputArea.append("Unable to write to file: " + fileName1 + fileName2 + ".\n");
            // append the error message to the output area
        }
    }

    // MODIFIES: this
    // EFFECTS: Load the state of the selected person from a JSON file and display it in the output area
    public void loadPerson() {

        try {
            person1 = jsonReader1.read();
            outputArea.append("Load" + person1.getPersonName() + "from" + JASON_STORE_1 + ".\n");
            person2 = jsonReader2.read();
            outputArea.append("Load" + person2.getPersonName() + "from" + JASON_STORE_2 + ".\n");
            // append the message to the output area
        } catch (IOException e) {
            outputArea.append("Unable to read from file: " + JASON_STORE_1 + JASON_STORE_2 + ".\n");
            // append the error message to the output area
        }
    }





}
