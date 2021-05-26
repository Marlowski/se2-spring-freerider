package de.freerider.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerTest {

    private Customer mats;
    private Customer thomas;


    @BeforeEach
    public void setUpEach() {
        mats = new Customer("Hummels", "Mats", "matshummels@mail.de");
        thomas = new Customer("Mueller", "Thomas", "thomasmueller@mail.de");
    }

    @Test
    void testIdNull() {
        assertNull(thomas.getId());
    }

    @Test
    void testSetId() {
        mats.setId("1234");
        assertNotNull(mats.getId());
    }

    @Test
    void testSetIdOnlyOnce() {
        thomas.setId("1234");
        String setId = thomas.getId();
        thomas.setId("5678");
        assertEquals(setId, thomas.getId());
    }

    @Test
    void testResetId() {
        mats.setId("1234");
        assertNotNull(mats.getId());
        mats.setId(null);
        assertNull(mats.getId());
    }

    @Test
    void testNamesInitial() {
        assertNotNull(thomas.getFirstName());
        assertNotNull(thomas.getLastName());
    }

    @Test
    void testNamesSetNull() {
        mats.setFirstName(null);
        mats.setLastName(null);
        assertEquals(mats.getFirstName(), "");
        assertEquals(mats.getLastName(), "");
    }

    @Test
    void testSetNames() {
        String newFirstName = "Robin";
        String newLastName = "Rot";

        thomas.setFirstName(newFirstName);
        thomas.setLastName(newLastName);
        assertEquals(thomas.getFirstName(), newFirstName);
        assertEquals(thomas.getLastName(), newLastName);
    }

    @Test
    void testContactsInitial() {
        assertNotNull(mats.getContact());
    }

    @Test
    void testContactsSetNull() {
        thomas.setContact(null);
        assertEquals(thomas.getContact(), "");
    }

    @Test
    void testSetContact() {
        String newContact = "mynewmail@email.de";
        mats.setContact(newContact);
        assertEquals(mats.getContact(), newContact);
    }

    @Test
    void testStatusInitial() {
        assertEquals(thomas.getStatus(), Status.New);
    }

    @Test
    void testSetStatus() {
        mats.setStatus(Status.InRegistration);
        assertEquals(mats.getStatus(), Status.InRegistration);

        mats.setStatus(Status.Active);
        assertEquals(mats.getStatus(), Status.Active);

        mats.setStatus(Status.Suspended);
        assertEquals(mats.getStatus(), Status.Suspended);

        mats.setStatus(Status.Suspended);
        assertEquals(mats.getStatus(), Status.Suspended);

        mats.setStatus(Status.Deleted);
        assertEquals(mats.getStatus(), Status.Deleted);
    }
}

