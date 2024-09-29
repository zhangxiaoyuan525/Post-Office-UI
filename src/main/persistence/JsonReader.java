package persistence;

import model.Parcel;
import model.Person;
import org.json.JSONArray;
import org.json.JSONObject;
import ui.PostOffice;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads person from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads person from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Person read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePerson(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses person from JSON object and returns it
    private Person parsePerson(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Person po = new Person(name);
        addParcelTo(po, jsonObject);
        return po;
    }

    // Perosn
    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to person
    private void addParcelTo(Person p, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("parcels");
        for (Object json : jsonArray) {
            JSONObject nextParcel = (JSONObject) json;
            addParcels(p, nextParcel);
        }
    }

    // MODIFIES: p
    // EFFECTS: parses parcel from JSON object and adds it to person
    private void addParcels(Person p, JSONObject jsonObject) {
        Boolean postmark = jsonObject.getBoolean("postmark");
        String category = jsonObject.getString("category");
        double postage = jsonObject.getDouble("postage");
        double weight = jsonObject.getDouble("weight");
        String name = jsonObject.getString("name");
        String heading = jsonObject.getString("heading");
        Boolean status = jsonObject.getBoolean("status");
        Parcel parcel = new Parcel(name, weight, heading);
        parcel.getStamped();
        parcel.doVerify();
        p.addParcel(parcel);
    }
}
