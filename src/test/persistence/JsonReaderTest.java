package persistence;

import model.Parcel;
import model.Person;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/nonSuchFile.json");
        try {
            Person p = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyPerson() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPerson.json");
        try {
            Person p = reader.read();
            assertEquals("Diana", p.getPersonName());
            assertEquals(0, p.getTotalNumberParcels());
            assertEquals(0,p.getTotalNumberParcels());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralPerson() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPerson.json");
        try {
            Person person = reader.read();
            assertEquals("Louis", person.getPersonName());
            List<Parcel> parcels = person.getReadyParcels();
            assertEquals(2,parcels.size());
            checkParcel("Louis", 1, "cake", parcels.get(0));
            assertEquals(2,parcels.get(0).getPostage());
            assertEquals("Normal",parcels.get(0).getCategory());
            assertFalse(parcels.get(0).getStatus());
            assertTrue(parcels.get(0).getPostmark());
            checkParcel("Louis", 1,"bulb", parcels.get(1));
            assertFalse(parcels.get(1).getStatus());
            assertTrue(parcels.get(1).getPostmark());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
