package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.List;
import java.util.ArrayList;

public class Person implements Writable {
    private String personName;
    private List<Parcel> allParcels;

    // EFFECTS: constructs an empty parcel list
    //
    public Person(String personName) {
        this.personName = personName;
        this.allParcels = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds the given parcel to this parcel list
    //
    public void addParcel(Parcel parcel) {
        String pn = parcel.getHeading();
        allParcels.add(parcel);
        EventLog.getInstance().logEvent(new Event("Parcel: " + pn + " is added to "
                + this.getPersonName() + "'s parcel list."));
        if (this.getAllParcels().size() == 1) {
            EventLog.getInstance().logEvent(new Event("Parcel: " + pn + " is "
                    + this.getPersonName() + "'s prior parcel."));
        }
//        if (pn.equals(personName)) {
//            allParcels.add(parcel);
//        }
    }

    // EFFECTS: return a list of all parcels in this list
    //          that are ready to delivery
    //
    public List<Parcel> getReadyParcels() {
        List<Parcel> readyParcels = new ArrayList<>();
        for (Parcel parcel: this.allParcels) {
            String ca = parcel.getCategory();
            boolean postmark = parcel.getPostmark();
            String pn = parcel.getHeading();
            if (!ca.equals("Not Allowed")) {
                readyParcels.add(parcel);
                EventLog.getInstance().logEvent(new Event("Parcel: " + pn + "is added to "
                        + this.getPersonName() + "'s ready parcel list."));
            }
        }
        return readyParcels;
    }

    // EFFECTS: return the total number of parcels in this list
    //
    public int getTotalNumberParcels() {
        return this.allParcels.size();
    }
/*
    // EFFECTS: return true if the parcel owner is this person
    //
    public boolean isParcel(Parcel parcel) {
        String n = parcel.getName();
        return n.equals(personName);
    }

 */

    public List<Parcel> getAllParcels() {
        return this.allParcels;
    }


    // eEFFECTS: return the name of this person
    public String getPersonName() {
        return personName;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", personName);
        json.put("parcels", parcelsToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray parcelsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Parcel t : allParcels) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

//    public List<Parcel> getParcels() {
//        return allParcels;
//    }


}
