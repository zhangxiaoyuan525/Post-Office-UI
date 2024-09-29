package model;

import org.json.JSONObject;
import persistence.Writable;

// Representing a parcel having an id, weight(in kg), category, owner name, postage.
public class Parcel implements Writable {
    private double weight;                  // the weight of parcel
    private String category;                // what category this parcel is (normal, heavy or over-weight)
    private String name;              // the parcel owner name
    private boolean status;           // whether the parcel matches its owner
    private double postage;                 // the postage parcel costs
    private boolean postmark;               // whether this parcel is ready to delivery or not
    private String heading;              // the name of parcel





    // REQUIRES: parcelName has a non-zero length
    // EFFECTS: name of the parcel owner is set to parcelName;
    //          Each parcel has an individual positive number to track;
    //          the weight of parcel can only be positive;
    //          the category of parcel is determined by its weight.
    //
    public Parcel(String parcelName, double parcelWeight, String parcelHeading) {
        // id = nextParcelId++;
        name = parcelName;
        postmark = false;
        heading = parcelHeading;
        status = false;

        if (parcelWeight > 0) {
            weight = parcelWeight;
        } else {
            weight = 0;
        }

        if (weight == 0) {
            category = "Not Allowed";
            postage = 0;
        } else if (weight <= 10) {
            category = "Normal";
            postage = weight * 2;
        } else if (weight <= 50) {
            category = "Heavy";
            postage = 10 * 2 + (weight - 10) * 5;
        } else {
            category = "Not Allowed";
            postage = 0;
        }
    }



    // EFFECTS: return id of parcel
    //
    /*public int getId() {
        return id;
    }
    */


    //EFFECTS: return name of parcel owner
    //
    public String getName() {
        return name;
    }

    // EFFECTS: return weight of parcel
    //
    public double getWeight() {
        return weight;
    }

    // EFFECTS: return category of parcel
    //
    public String getCategory() {
        return category;
    }

    // EFFECTS: return postage of parcel
    //
    public double getPostage() {
        return postage;
    }

    //EFFECTS: return true if parcel is ready to delivery
    //
    public Boolean getPostmark() {
        return postmark;
    }

    //MODIFIES: this
    //EFFECTS: stamped a parcel if it's paid already
    //
    public void getStamped() {
        postmark = true;
    }

    // EFFECTS: get the heading of parcel
    public String getHeading() {
        return heading;
    }

    //MODIFIES: this
    //EFFECTS: verify the status of parcel
    public void doVerify() {
        status = true;
    }

    //EFFECTS: get the status of the parcel
    public Boolean getStatus() {
        return status;
    }

//    //EFFECTS: get the postage of the parcel paid
//    public void doPay() {
//
//        postage = 0;
//    }


/*
    //EFFECTS: stamped the parcel if it's ready to delivery,
    //         or invalid the postmark if requirements are not all meet
    //
    public Boolean statusCheck() {
        if (this.category != "Not Allowed") {
            if (this.postage == 0) {
                this.postmark = true;
                return true;
            }
            postmark = false;
            return false;
        } else {
            postmark = false;
        }
        return false;
    }

 */

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: take things out of parcel and
    //          the weight of parcel is returned
    //
    public void takeOut(double amount) {
        weight = weight - amount;
        if (weight <= 0) {              // renew the category, postage of parcel
            weight = 0;
            category = "Not Allowed";
            postage = 0;
        } else if (weight <= 10) {
            category = "Normal";
            postage = weight * 2;
        } else if (weight <= 50) {
            category = "Heavy";
            postage = 10 * 2 + (weight - 10) * 5;
        } else {
            category = "Not Allowed";
            postage = 0;
        }

    }
/*
    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: put things in parcel and
    //          the weight of parcel is returned
    //
    public void putIn(double amount) {
        weight = weight + amount;
        if (weight <= 0) {
            weight = 0;
            category = "Not Allowed";
            postage = 0;
        } else if (weight <= 10) {
            category = "Normal";
            postage = weight * 2;
        } else if (weight <= 50) {
            category = "Heavy";
            postage = 10 * 2 + (weight - 10) * 5;
        } else {
            weight = 0;
            category = "Not Allowed";
            postage = 0;
        }

    }

 */

    // REQUIRES: the weight of parcel should not be 0
    // MODIFIES: this
    // EFFECTS: get the postage should pay before delivery
    //
    public double postageFee() {
        String ca = category;
        if (!ca.equals("Not Allowed")) {
            if (ca.equals("Normal")) {
                postage = weight * 2;
            } else {
                postage = 10 * 2 + (weight - 10) * 5;
            }
            return postage;
        } else {
            return 0;
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("category", category);
        json.put("weight", weight);
        json.put("postage", postage);
        json.put("postmark", postmark);
        json.put("heading", heading);
        json.put("status", status);
        return json;
    }

}


