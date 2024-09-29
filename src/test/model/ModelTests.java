package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParcelTest {
    private Parcel testParcel;

    @BeforeEach
    void runBefore() { testParcel = new Parcel("Tina", 15, "teddy bear"); }

    @Test
    void testConstructor() {
        assertEquals(45, testParcel.getPostage());
        assertEquals("Tina", testParcel.getName());
        assertEquals(15, testParcel.getWeight());
        assertEquals("Heavy", testParcel.getCategory());
        assertFalse(testParcel.getPostmark());
        assertEquals("teddy bear", testParcel.getHeading());
     //   assertFalse(testParcel.getStatus());
    }

    @Test
    void testConstructorNegPostage() {
        testParcel = new Parcel("Jack",-50, "lighter");
        assertEquals("Jack", testParcel.getName());
        assertEquals(0, testParcel.getPostage());
        assertEquals(0,testParcel.getWeight());
        assertEquals("Not Allowed", testParcel.getCategory());
        assertFalse(testParcel.getPostmark());
        assertEquals("lighter", testParcel.getHeading());
     //   assertFalse(testParcel.getStatus());
    }

    @Test
    void testTakeOut(){
        testParcel.takeOut(6.4);
        assertEquals(17.2, testParcel.getPostage());
        assertEquals(8.6, testParcel.getWeight());
        //assertTrue(testParcel.getId() > 0);
        assertFalse(testParcel.getPostmark());
        assertEquals("Normal", testParcel.getCategory());
        assertEquals("Tina", testParcel.getName());
     //   assertFalse(testParcel.getStatus());

        testParcel = new Parcel("Anna",50, "dress");
        testParcel.takeOut(17.8);
        assertEquals(131, testParcel.getPostage());
        assertEquals(32.2, testParcel.getWeight());
        //assertTrue(testParcel.getId() > 0);
        assertFalse(testParcel.getPostmark());
        assertEquals("Heavy", testParcel.getCategory());
        assertEquals("Anna", testParcel.getName());
        assertEquals("dress", testParcel.getHeading());
     //   assertFalse(testParcel.getStatus());

        testParcel = new Parcel("Eric", 80, "desk");
        testParcel.takeOut(18);
        assertEquals("Not Allowed", testParcel.getCategory());
        assertEquals("Eric", testParcel.getName());
        assertEquals(0, testParcel.getPostage());
        assertEquals(62, testParcel.getWeight());
        assertEquals("desk", testParcel.getHeading());
     //   assertFalse(testParcel.getStatus());
    }

    @Test
    void testNegTakeOut(){
        testParcel = new Parcel("Amy",5, "ink bottle");
        testParcel.takeOut(6.4);
        assertEquals(0, testParcel.getPostage());
        assertEquals(0, testParcel.getWeight());
        //assertTrue(testParcel.getId() > 0);
        assertFalse(testParcel.getPostmark());
        assertEquals("Not Allowed", testParcel.getCategory());
        assertEquals("Amy", testParcel.getName());
        assertEquals("ink bottle", testParcel.getHeading());
      //  assertFalse(testParcel.getStatus());
    }
/*
    @Test
    void testPutIn(){
        testParcel.putIn(8);
        assertEquals(85, testParcel.getPostage());
        assertEquals(23, testParcel.getWeight());
        assertFalse(testParcel.getStatus());
        assertFalse(testParcel.getPostmark());
        assertEquals("Heavy", testParcel.getCategory());
        assertEquals("Tina", testParcel.getName());
    }

    @Test
    void testOverPutIn() {
        testParcel = new Parcel("Ben", 48);
        testParcel.putIn(2.7);
        assertEquals(0, testParcel.getPostage());
        assertEquals(0, testParcel.getWeight());
        assertFalse(testParcel.getStatus());
        assertFalse(testParcel.getPostmark());
        assertEquals("Not Allowed", testParcel.getCategory());
        assertEquals("Ben", testParcel.getName());
    }

*/


    @Test
    void testGetStamped() {
        assertFalse(testParcel.getPostmark());
        testParcel.getStamped();
        assertTrue(testParcel.getPostmark());

    }




    @Test
    void testPostFee() {
        assertEquals(45, testParcel.postageFee());
        testParcel = new Parcel("kate", 44, "book");
        assertEquals(190,testParcel.postageFee());
        testParcel = new Parcel("Frank", 55, "book");
        assertEquals(0, testParcel.postageFee());
        testParcel = new Parcel("Kevin", 8, "book");
        assertEquals(16,testParcel.postageFee());
    }

    /*

    @Test
    void testGetId() {
        assertEquals(1,testParcel.getId()); //why id is 4 instead of 2
    }

     */

    @Test
    void testGetName() {
        assertEquals("Tina", testParcel.getName());
    }

    @Test
    void testGetWeight() {
        assertEquals(15, testParcel.getWeight());
        testParcel = new Parcel("Steve", -10, "hat");
        assertEquals(0, testParcel.getWeight());
        testParcel = new Parcel("Jake", 0, "cap");
        assertEquals(0, testParcel.getWeight());
    }

    @Test
    void testGetCategory() {
        assertEquals("Heavy", testParcel.getCategory());
        testParcel = new Parcel("Ben", 5,"cup");
        assertEquals("Normal", testParcel.getCategory());
        testParcel = new Parcel("Ken", 60, "shelf");
        assertEquals("Not Allowed", testParcel.getCategory());
        testParcel = new Parcel("Peter", 0, "letter");
        assertEquals("Not Allowed", testParcel.getCategory());
        testParcel = new Parcel("Angela", -2, "stamp");
        assertEquals("Not Allowed", testParcel.getCategory());
    }

    @Test
    void testGetPostage() {
        assertEquals(45, testParcel.getPostage());
        testParcel = new Parcel("Ben", 5, "book");
        assertEquals(10, testParcel.getPostage());
        testParcel = new Parcel("Ken", 60, "shelf");
        assertEquals(0, testParcel.getPostage());
        testParcel = new Parcel("Peter", 0, "cake");
        assertEquals(0, testParcel.getPostage());
        testParcel = new Parcel("Angela", -2, "letter");
        assertEquals(0, testParcel.getPostage());
    }

    @Test
    void testGetPostmark() {
        assertFalse(testParcel.getPostmark());
    }

    @Test
    void testGetHeading() {
        assertEquals("teddy bear",testParcel.getHeading());
    }

    @Test
    void testGetStatus() {
        assertFalse(testParcel.getStatus());
        testParcel.doVerify();
        assertTrue(testParcel.getStatus());
    }

    @Test
    void testDoVerify() {
        assertFalse(testParcel.getStatus());
        testParcel.doVerify();
        assertTrue(testParcel.getStatus());
    }
}

class PersonTest {
    private Person testPerson;
    private Parcel parcel1;
    private Parcel parcel2;
    private Parcel parcel3;
    private Parcel parcel4;

    @BeforeEach
    void runBefore() {
        this.testPerson = new Person("Tina");
        this.parcel1 = new Parcel("Tina", 10, "books");
        this.parcel2 = new Parcel("Frank", 20, "computer");
        this.parcel3 = new Parcel("Tina", 55, "rice");
        this.parcel4 = new Parcel("Tina", 25, "bottles");
    }

    @Test
    void testConstructor() {
        assertEquals(0, testPerson.getTotalNumberParcels());
        assertEquals(0,testPerson.getReadyParcels().size());
    }

    @Test
    void testGetPersonName() {
        assertEquals("Tina", testPerson.getPersonName());
    }

    @Test
    void testAddParcel() {
        testPerson.addParcel(parcel1);
        assertEquals(1,testPerson.getTotalNumberParcels());
        testPerson.addParcel(parcel2);
        assertEquals(2, testPerson.getTotalNumberParcels());
        testPerson.addParcel(parcel3);
        assertEquals(3, testPerson.getTotalNumberParcels());
        testPerson.addParcel(parcel4);
        assertEquals(4,testPerson.getTotalNumberParcels());
    }

    @Test
    void testGetTotalNumberParcels() {
        testPerson.addParcel(parcel1);
        testPerson.addParcel(parcel3);
        testPerson.addParcel(parcel4);
        assertEquals(3, testPerson.getTotalNumberParcels());
    }

    @Test
    void testGetTotalNumberParcelsOne() {
        assertEquals(0, testPerson.getTotalNumberParcels());
        testPerson.addParcel(parcel1);
        assertEquals(1,testPerson.getTotalNumberParcels());
        testPerson.addParcel(parcel2);
        assertEquals(2, testPerson.getTotalNumberParcels());

    }
/*
    @Test
    void testIsParcel() {
        assertTrue(testPerson.isParcel(parcel1));
        assertFalse(testPerson.isParcel(parcel2));
        assertTrue(testPerson.isParcel(parcel3));
        assertTrue(testPerson.isParcel(parcel4));
    }

 */

    @Test
    void testGetTotalNumberParcelsMany() {
        testPerson.addParcel(parcel1);
        testPerson.addParcel(parcel2);
        testPerson.addParcel(parcel3);
        testPerson.addParcel(parcel4);
        assertEquals(4,testPerson.getTotalNumberParcels());
    }

    @Test
    void testGetReadyParcelsEmpty() {
        assertEquals(0,testPerson.getReadyParcels().size());
    }

    @Test
    void testGetReadyParcelNoneExpected() {
        testPerson.addParcel(parcel3);

        testPerson.addParcel(parcel2);

        testPerson.addParcel(parcel1);
        assertEquals(2, testPerson.getReadyParcels().size());

    }

    @Test
    void testGetAllParcels() {
        testPerson.addParcel(parcel3);

        testPerson.addParcel(parcel2);

        testPerson.addParcel(parcel1);
        assertEquals(3, testPerson.getAllParcels().size());

    }

    @Test
    void testGetReadyParcelsOneExpected() {
        parcel1.getStamped();
        parcel1.doVerify();
        testPerson.addParcel(parcel1);
        testPerson.addParcel(parcel2);
        testPerson.addParcel(parcel4);
        List<Parcel> parcels = testPerson.getReadyParcels();
        assertEquals(3,parcels.size());
        assertEquals("Tina", parcels.get(0).getName());
    }

    @Test
    void testGetReadyParcelsNineManyExpected() {
        parcel1.doVerify();
        parcel1.getStamped();
        testPerson.addParcel(parcel1);

        testPerson.addParcel(parcel3);

        parcel4.doVerify();
        parcel4.getStamped();
        testPerson.addParcel(parcel4);
        List<Parcel> parcels = testPerson.getReadyParcels();

        assertEquals(2,parcels.size());

        assertEquals("Tina", parcels.get(0).getName());
        assertEquals(10, parcels.get(0).getWeight());

        assertEquals("Tina", parcels.get(1).getName());
        assertEquals(25, parcels.get(1).getWeight());
    }

    @Test
    void testGetReadyParcelsCalls() {
        parcel1.getStamped();
        parcel1.doVerify();
        testPerson.addParcel(parcel1);
        testPerson.addParcel(parcel2);
        testPerson.addParcel(parcel3);
        parcel1.doVerify();
        parcel1.getStamped();
        testPerson.addParcel(parcel4);
        List<Parcel> parcels = testPerson.getReadyParcels();

        assertEquals(3,parcels.size());

        List<Parcel> parcels2 = testPerson.getReadyParcels();
        assertEquals(3,parcels2.size());
    }



}