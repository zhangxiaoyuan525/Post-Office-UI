package persistence;

import model.Parcel;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkParcel(String name, double weight, String heading, Parcel parcel) {
        assertEquals(name, parcel.getName());
        assertEquals(weight, parcel.getWeight());
        assertEquals(heading, parcel.getHeading());

    }
}
