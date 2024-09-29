package ui;

import model.Parcel;
import model.Person;
import persistence.JsonReader;
import persistence.JsonWriter;
import persistence.Writable;

import javax.imageio.IIOException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


// Post office application
public class PostOffice {
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

    private Person person1;
    private Person person2;
    private Scanner input;

    //EFFECTS: run the post application
    public PostOffice() throws FileNotFoundException {
        input = new Scanner(System.in);
        jsonWriter1 = new JsonWriter(JASON_STORE_1);
        jsonReader1 = new JsonReader(JASON_STORE_1);
        jsonWriter2 = new JsonWriter(JASON_STORE_2);
        jsonReader2 = new JsonReader(JASON_STORE_2);
        runPost();
    }

    //MODIFIES: this
    //EFFECTS: process user input
    protected void runPost() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: process user command
    public void processCommand(String command) {
        if (!command.equals("c")) {
            if (command.equals("w")) {
                checkWeight();
            } else if (command.equals("d")) {
                doDelivery();
            } else if (command.equals("v")) {
                viewHeading();
            } else if (command.equals("i")) {
                doInspect();
            } else if (command.equals("s")) {
                savePerson();
            } else if (command.equals("l")) {
                loadPerson();
            } else {
                System.out.println("Selection not valid...");
            }
        } else {
            checkName();
        }
    }

    // MODIFIES: this
    // EFFECTS: initialize post
    public void init() {
        parcel1 = new Parcel("Tina", 8, "teddy bear");
        parcel2 = new Parcel("Frank", 5, "books");
        parcel3 = new Parcel("Tina", 55, "bottles");
        parcel4 = new Parcel("Tina", 25, "rice");

        person1 = new Person("Tina");
        person2 = new Person("Frank");
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: display menu of options to user
    protected void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\nc -> check");
        System.out.println("\nw -> weigh");
        System.out.println("\nd -> delivery");
        System.out.println("\nv -> view");
        System.out.println("\ni -> inspect");
        System.out.println("\ns -> save person state to file");
        System.out.println("\nl -> load person state to file");
        System.out.println("\nq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: Check whether owner of the parcel belongs to the claimer
    public void checkName() {
        Parcel selected = selectParcel();
        String parcelName = selected.getName();
        Person identity = selectPerson();
        String personName = identity.getPersonName();

        if (parcelName.equals(personName)) {
            System.out.println("This parcel matched the owner !!\n");
            identity.addParcel(selected);
            selected.doVerify();
        } else {
            System.out.println("This name doesn't match the owner of this parcel\n");
        }

    }

    // REQUIRES: the weight of parcel should be positive
    // MODIFIES: this
    // EFFECTS: weigh the parcel and determine whether it's possible for delivery
    //          and get the postage of it.
    public void checkWeight() {
        Parcel selected = selectParcel();
        Boolean status = selected.getStatus();
        input = new Scanner(System.in);


        if (status) {
            while (selected.getCategory().equals("Not Allowed")) {
                System.out.println("The parcel is too heavy!\n");
                System.out.println("The weight is: kg");
                System.out.println(selected.getWeight() + "\n");
                System.out.println("Please take out weight: kg\n");
                double amount = input.nextDouble();
                if (amount >= selected.getWeight()) {
                    System.out.println("The weight should be positive\n");
                } else {
                    selected.takeOut(amount);
                }
            }
            System.out.println("Now the weight of parcel is:" + selected.getWeight() + "kg" + "\n");
            System.out.println("The parcel is possible to delivery!\n");
            System.out.println("Please choose the parcel to pay\n");
            payParcel();
        } else {
            System.out.println("Please check the owner of this parcel first.\n");
        }
    }

    // REQUIRES: the amount of payment should be positive
    // MODIFIES: this
    // EFFECTS: pay the parcel before delivery
    private void payParcel() {
        Parcel selected = selectParcel();
        Person identity = selectPerson();
        double postage = selected.getPostage();
        System.out.println("Please enter the amount you want to pay: $\n");
        while (postage > 0) {
            System.out.println("Postage waiting to pay:$\n");
            System.out.println(postage);
            double amount = input.nextDouble();
            if (amount <= 0) {
                System.out.println("Please enter a positive amount\n");
            } else {
                postage = postage - amount;
            }
        }
        if (postage < 0) {
            System.out.println("Your payment is done and your will get your refund later.\n");
            System.out.println("The refund is:$\n");
            System.out.println(-postage);
        } else {
            System.out.println("Your payment is done, your parcel is ready to delivery\n");
        }
        selected.getStamped();
        identity.getReadyParcels();



    }

    // MODIFIES: this
    // EFFECTS: delivery the ready parcels.
    public void doDelivery() {
        Parcel selected = selectParcel();
        Person person = selectPerson();
        boolean postmark = selected.getPostmark();
        boolean status = selected.getStatus();
        if (status) {
            if (postmark) {
                System.out.println("The parcel is delivering, thank you.\n");
            } else {
                System.out.println("The payment is not finished.\n");
            }
            viewParcels(person);
        } else {
            System.out.println("Please check the owner of this parcel first.\n");
        }
    }

    // EFFECTS: view the parcels that are claimed and paid already
    private void viewParcels(Person person) {
        int number = person.getReadyParcels().size();
        System.out.println("The ready parcel number is:\n");
        System.out.println(number);
//        System.out.println((number + 1) / 2);
//        if ((person.getReadyParcels().contains(parcel3) && person.getReadyParcels().contains(parcel4))) {
//            System.out.println(number - 2);
//        } else if (person.getReadyParcels().contains(parcel3) || person.getReadyParcels().contains(parcel4)) {
//            System.out.println(number - 1);
//        } else {
//            System.out.println(number / 2);
//        }
    }

    // MODIFIES: this
    // EFFECT: view the heading of parcels which are already sent.
    public void viewHeading() {
        Person selected = selectPerson();
        List<Parcel> parcelList = selected.getReadyParcels();
        List<String> parcelHeadings = new ArrayList<>();
        for (Parcel parcel: parcelList) {
            parcelHeadings.add(parcel.getHeading());
        }
        System.out.println(parcelHeadings);
    }

    public void doInspect() {
        int numOfPeople = 0;
        List<List<String>> parcels = new ArrayList<>();
        while (numOfPeople != 2) {
            numOfPeople++;
            Person selected = selectPerson();
            List<Parcel> parcelList = selected.getReadyParcels();
            List<String> parcelHeadings = new ArrayList<>();
            for (Parcel parcel: parcelList) {
                parcelHeadings.add(parcel.getHeading());
            }
            parcels.add(parcelHeadings);
        }
        System.out.println(parcels);

    }




    // EFFECTS: prompts user to select parcel and do the post progress
    private Parcel selectParcel() {
        String selection = "";

        while (!(selection.equals("1") || selection.equals("2") || selection.equals("3") || selection.equals("4"))) {
            System.out.println("1 for parcel1");
            System.out.println("2 for parcel2");
            System.out.println("3 for parcel3");
            System.out.println("4 for parcel4");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (!selection.equals("1")) {
            if (selection.equals("2")) {
                return parcel2;
            } else if (selection.equals("3")) {
                return parcel3;
            } else {
                return parcel4;
            }
        } else {
            return parcel1;
        }
    }

    // EFFECTS: prompts user to select the name of claimed person
    private Person selectPerson() {
        String selection = "";

        while (!(selection.equals("t") || selection.equals("f"))) {
            input = new Scanner(System.in);
            System.out.println("t for Tina");
            System.out.println("f for Frank");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("t")) {
            return person1;
        } else {
            return person2;
        }
    }

    // EFFECTS: saves the postoffice to file
    public void savePerson() {
        try {
            jsonWriter1.open();
            jsonWriter1.write(person1);
            jsonWriter1.close();

            jsonWriter2.open();
            jsonWriter2.write(person2);
            jsonWriter2.close();

            System.out.println("Saved" + person1.getPersonName() + "to" + JASON_STORE_1);
            System.out.println("Saved" + person2.getPersonName() + "to" + JASON_STORE_2);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file" + JASON_STORE_1 + "or" + JASON_STORE_2);
        }
    }

    //MODIFIES: this
    //EFFECTS: this
    public void loadPerson() {
        try {
            person1 = jsonReader1.read();
            System.out.println("Load" + person1.getPersonName() + "from" + JASON_STORE_1);
            person2 = jsonReader2.read();
            System.out.println("Load" + person2.getPersonName() + "from" + JASON_STORE_2);
        } catch (IOException e) {
            System.out.println("Unable to read from file:" + JASON_STORE_1 + "or" + JASON_STORE_2);
        }
    }


}
