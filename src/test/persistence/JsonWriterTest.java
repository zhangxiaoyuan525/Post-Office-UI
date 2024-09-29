package persistence;

import model.Parcel;
import model.Person;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Person person = new Person("Helen");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyPerson() {
        try {
            Person person = new Person("Peter");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(person);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            person = reader.read();
            assertEquals("Peter", person.getPersonName());
            assertEquals(0, person.getTotalNumberParcels());
            assertEquals(0,person.getReadyParcels().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralPerson() {
        try {
            Person person = new Person("Erica");
            person.addParcel(new Parcel("Erica", 20, "apple"));
            person.addParcel(new Parcel("Ken", 0.5, "postcard"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(person);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            person = reader.read();
            assertEquals("Erica", person.getPersonName());
            List<Parcel> parcels = person.getReadyParcels();
            assertEquals(2, person.getTotalNumberParcels());
            assertEquals(2,person.getReadyParcels().size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
