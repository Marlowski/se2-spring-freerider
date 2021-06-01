package de.freerider.repository;

import de.freerider.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CustomerRepositoryTest {

    @Autowired
    private CrudRepository<Customer, String> customerRepository;

    private Customer mats;
    private Customer thomas;


    @BeforeEach
    public void setUpEach() {
        mats = new Customer("Hummels", "Mats", "matshummels@mail.de");
        thomas = new Customer("Mueller", "Thomas", "thomasmueller@mail.de");
        customerRepository.deleteAll();
        assertEquals(customerRepository.count(), 0);
    }

    @Test
    void testSave_SaveWithId() {
        mats.setId("1");
        thomas.setId("2");
        assertNotNull(mats.getId());
        assertNotNull(thomas.getId());
        Customer saved1 = customerRepository.save(mats);
        Customer saved2 = customerRepository.save(thomas);
        assertEquals(saved1, mats);
        assertEquals(saved2, thomas);
        assertEquals(customerRepository.count(), 2);
        assertTrue(customerRepository.existsById("1"));
        assertTrue(customerRepository.existsById("2"));
    }

    @Test
    void testSave_SaveWithNullId() {
        assertNull(mats.getId());
        assertNull(thomas.getId());
        Customer saved1 = customerRepository.save(mats);
        Customer saved2 = customerRepository.save(thomas);
        assertEquals(customerRepository.count(), 2);
        assertNotNull(saved1.getId());
        assertNotNull(saved2.getId());
    }

    @Test
    void testSave_NullObject() {
        assertThrows(IllegalArgumentException.class, () -> {
            customerRepository.save(null);
        });
        assertEquals(customerRepository.count(), 0);
    }

    @Test
    void testSave_SaveTwice() {
        assertNull(mats.getId());
        Customer saved1 = customerRepository.save(mats);
        assertEquals(customerRepository.count(), 1);
        assertEquals(saved1, mats);
        Customer saved2 = customerRepository.save(mats);
        assertEquals(customerRepository.count(), 1);
        assertEquals(saved2, mats);
        assertEquals(saved2, saved1);
    }

    @Test
    void testSave_SameIdTwice() {
        mats.setId("1");
        thomas.setId("1");
        assertNotNull(mats.getId());
        assertNotNull(thomas.getId());
        customerRepository.save(mats);
        assertEquals(customerRepository.count(), 1);
        Customer savedSameId = customerRepository.save(thomas);
        assertEquals(customerRepository.count(), 1);
        assertEquals(savedSameId, mats);
    }

    @Test
    void testSaveAll_SaveWithId() {
        mats.setId("1");
        thomas.setId("2");
        assertNotNull(mats.getId());
        assertNotNull(thomas.getId());
        ArrayList<Customer> list = new ArrayList<>();
        list.add(mats);
        list.add(thomas);
        ArrayList<Customer> savedList = (ArrayList<Customer>) customerRepository.saveAll(list);
        assertEquals(customerRepository.count(), 2);
        assertTrue(customerRepository.existsById("1"));
        assertTrue(customerRepository.existsById("2"));
        for (Customer c : savedList) {
            assertEquals(c, customerRepository.findById(c.getId()).stream().findFirst().orElse(null));
        }
    }

    @Test
    void testSaveAll_SaveWithNullId() {
        mats.setId("1");
        assertNotNull(mats.getId());
        ArrayList<Customer> list = new ArrayList<>();
        list.add(mats);
        list.add(thomas);
        ArrayList<Customer> savedList = (ArrayList<Customer>) customerRepository.saveAll(list);
        assertEquals(customerRepository.count(), 2);
        for (Customer c : savedList) {
            assertNotNull(c.getId());
        }
    }

    @Test
    void testSaveAll_NullObjectList() {
        ArrayList<Customer> list = new ArrayList<>();
        list.add(null);
        assertThrows(IllegalArgumentException.class, () -> {
            customerRepository.saveAll(list);
        });
        assertEquals(customerRepository.count(), 0);
    }

    @Test
    void testSaveAll_NullObject() {
        assertThrows(IllegalArgumentException.class, () -> {
            customerRepository.saveAll(null);
        });
        assertEquals(customerRepository.count(), 0);
    }

    @Test
    void testSaveAll_Twice() {
        ArrayList<Customer> list = new ArrayList<>();
        thomas.setId("1");
        assertNotNull(thomas.getId());
        list.add(thomas);
        customerRepository.saveAll(list);
        assertEquals(customerRepository.count(), 1);
        customerRepository.saveAll(list);
        assertEquals(customerRepository.count(), 1);
    }

    @Test
    void testSaveAll_SameIdTwice() {
        mats.setId("1");
        thomas.setId("1");
        assertNotNull(mats.getId());
        assertNotNull(thomas.getId());
        ArrayList<Customer> list = new ArrayList<>();
        list.add(mats);
        list.add(thomas);
        customerRepository.saveAll(list);
        assertEquals(customerRepository.count(), 1);
    }

    @Test
    void testFindById() {
        mats.setId("1");
        assertNotNull(mats.getId());
        customerRepository.save(mats);
        assertEquals(mats, customerRepository.findById("1").stream().findFirst().orElse(null));
    }

    @Test
    void testFindById_NoResult() {
        mats.setId("1");
        assertNotNull(mats.getId());
        customerRepository.save(mats);
        assertFalse(customerRepository.findById("2").isPresent());
    }

    @Test
    void testFindById_EmptyRepo() {
        assertFalse(customerRepository.findById("1").isPresent());
    }

    @Test
    void testFindById_FindNull() {
        mats.setId("1");
        assertNotNull(mats.getId());
        customerRepository.save(mats);
        assertThrows(IllegalArgumentException.class, () -> {
            customerRepository.findById(null);
        });
    }

    @Test
    void testExistsById() {
        mats.setId("1");
        assertNotNull(mats.getId());
        customerRepository.save(mats);
        assertTrue(customerRepository.existsById("1"));
        assertFalse(customerRepository.existsById("2"));
    }

    @Test
    void testExistsById_nullObject() {
        assertThrows(IllegalArgumentException.class, () -> {
            customerRepository.existsById(null);
        });
    }

    @Test
    void testFindAll() {
        Customer saved1 = customerRepository.save(mats);
        Customer saved2 = customerRepository.save(thomas);
        assertEquals(customerRepository.count(), 2);
        ArrayList<Customer> list = (ArrayList<Customer>) customerRepository.findAll();
        assertEquals(list.size(), 2);
        assertTrue(list.contains(saved1));
        assertTrue(list.contains(saved2));
    }

    @Test
    void testFindAll_EmptyRepo() {
        ArrayList<Customer> list = (ArrayList<Customer>) customerRepository.findAll();
        assertEquals(list.size(), 0);
    }

    @Test
    void testFindAllById() {
        mats.setId("1");
        thomas.setId("2");
        assertNotNull(mats.getId());
        assertNotNull(thomas.getId());
        customerRepository.save(mats);
        customerRepository.save(thomas);
        ArrayList<String> idList = new ArrayList<>();
        idList.add(mats.getId());
        idList.add(thomas.getId());
        ArrayList<Customer> customerList = (ArrayList<Customer>) customerRepository.findAllById(idList);
        assertEquals(customerList.size(), 2);
        assertTrue(customerList.contains(mats));
        assertTrue(customerList.contains(thomas));
    }

    @Test
    void testFindAllById_DontFindAll() {
        mats.setId("1");
        thomas.setId("2");
        assertNotNull(mats.getId());
        assertNotNull(thomas.getId());
        customerRepository.save(mats);
        customerRepository.save(thomas);
        ArrayList<String> idList = new ArrayList<>();
        idList.add(mats.getId());
        idList.add("3");
        ArrayList<Customer> customerList = (ArrayList<Customer>) customerRepository.findAllById(idList);
        assertEquals(customerList.size(), 1);
        assertNotEquals(customerList.size(), idList.size());
        assertTrue(customerList.contains(mats));
        assertFalse(customerList.contains(thomas));
    }

    @Test
    void testFindAllById_NothingFound() {
        mats.setId("1");
        assertNotNull(mats.getId());
        customerRepository.save(mats);
        ArrayList<String> idList = new ArrayList<>();
        idList.add("3");
        ArrayList<Customer> list = (ArrayList<Customer>) customerRepository.findAllById(idList);
        assertNotNull(list);
        assertEquals(list.size(), 0);
    }

    @Test
    void testFindAllById_NullObjectList() {
        mats.setId("1");
        assertNotNull(mats.getId());
        customerRepository.save(mats);
        ArrayList<String> idList = new ArrayList<>();
        idList.add("1");
        idList.add(null);
        assertThrows(IllegalArgumentException.class, () -> {
            customerRepository.findAllById(idList);
        });
    }

    @Test
    void testFindAllById_NullObject() {
        assertThrows(IllegalArgumentException.class, () -> {
            customerRepository.findAllById(null);
        });
    }

    @Test
    void testCount() {
        customerRepository.save(mats);
        customerRepository.save(thomas);
        assertEquals(customerRepository.count(), 2);
    }

    @Test
    void testCount_EmptyRepo() {
        assertEquals(customerRepository.count(), 0);
    }

    @Test
    void testDeleteById() {
        mats.setId("1");
        assertNotNull(mats.getId());
        customerRepository.save(mats);
        customerRepository.deleteById("1");
        assertEquals(customerRepository.count(), 0);
    }

    @Test
    void testDeleteById_NoMatch() {
        mats.setId("1");
        assertNotNull(mats.getId());
        customerRepository.save(mats);
        assertEquals(customerRepository.count(), 1);
        assertFalse(customerRepository.existsById("3"));
        customerRepository.deleteById("3");
        assertEquals(customerRepository.count(), 1);
    }

    @Test
    void testDeleteById_NullObject() {
        assertThrows(IllegalArgumentException.class, () -> {
            customerRepository.deleteById(null);
        });
    }

    @Test
    void testDelete() {
        mats.setId("1");
        assertNotNull(mats.getId());
        Customer saved = customerRepository.save(mats);
        assertEquals(customerRepository.count(), 1);
        customerRepository.delete(saved);
        assertFalse(customerRepository.existsById("1"));
        assertEquals(customerRepository.count(), 0);
    }

    @Test
    void testDelete_NoMatch() {
        mats.setId("1");
        thomas.setId("2");
        assertNotNull(thomas.getId());
        assertNotNull(mats.getId());
        customerRepository.save(thomas);
        assertEquals(customerRepository.count(), 1);
        customerRepository.delete(mats);
        assertEquals(customerRepository.count(), 1);
    }

    @Test
    void testDelete_NullObject() {
        assertThrows(IllegalArgumentException.class, () -> {
            customerRepository.delete(null);
        });
    }

    @Test
    void testDelete_NullId() {
        assertNull(mats.getId());
        assertThrows(IllegalArgumentException.class, () -> {
            customerRepository.delete(mats);
        });
    }

    @Test
    void testDeleteAllById() {
        mats.setId("1");
        thomas.setId("2");
        assertNotNull(thomas.getId());
        assertNotNull(mats.getId());
        customerRepository.save(thomas);
        customerRepository.save(mats);
        assertEquals(customerRepository.count(), 2);
        ArrayList<String> idList = new ArrayList<>();
        idList.add("1");
        idList.add("2");
        customerRepository.deleteAllById(idList);
        assertEquals(customerRepository.count(), 0);
    }

    @Test
    void testDeleteAllById_DontFindAll() {
        mats.setId("1");
        thomas.setId("2");
        assertNotNull(thomas.getId());
        assertNotNull(mats.getId());
        customerRepository.save(thomas);
        customerRepository.save(mats);
        assertEquals(customerRepository.count(), 2);
        ArrayList<String> idList = new ArrayList<>();
        idList.add("1");
        idList.add("3");
        customerRepository.deleteAllById(idList);
        assertEquals(customerRepository.count(), 1);
    }

    @Test
    void testDeleteAllById_NoMatch() {
        mats.setId("1");
        assertNotNull(mats.getId());
        customerRepository.save(mats);
        assertEquals(customerRepository.count(), 1);
        ArrayList<String> idList = new ArrayList<>();
        idList.add("2");
        idList.add("3");
        customerRepository.deleteAllById(idList);
        assertEquals(customerRepository.count(), 1);
    }

    @Test
    void testDeleteAllById_NullObjectList() {
        mats.setId("1");
        assertNotNull(mats.getId());
        customerRepository.save(mats);
        assertEquals(customerRepository.count(), 1);
        ArrayList<String> idList = new ArrayList<>();
        idList.add("2");
        idList.add(null);
        assertThrows(IllegalArgumentException.class, () -> {
            customerRepository.deleteAllById(idList);
        });
        assertEquals(customerRepository.count(), 1);
    }

    @Test
    void testDeleteAllById_NullObject() {
        assertThrows(IllegalArgumentException.class, () -> {
            customerRepository.deleteAllById(null);
        });
    }

    @Test
    void testDeleteAllBy_deletedTwice() {
        mats.setId("1");
        assertNotNull(mats.getId());
        customerRepository.save(mats);
        assertEquals(customerRepository.count(), 1);
        ArrayList<String> idList = new ArrayList<>();
        idList.add("1");
        idList.add("1");
        customerRepository.deleteAllById(idList);
        assertEquals(customerRepository.count(), 0);
    }

    @Test
    void testDeleteAllIterable() {
        mats.setId("1");
        thomas.setId("2");
        assertNotNull(thomas.getId());
        assertNotNull(mats.getId());
        ArrayList<Customer> savedList = new ArrayList<>();
        savedList.add(customerRepository.save(thomas));
        savedList.add(customerRepository.save(mats));
        assertEquals(customerRepository.count(), 2);
        customerRepository.deleteAll(savedList);
        assertEquals(customerRepository.count(), 0);
    }

    @Test
    void testDeleteAllIterable_DontFindAll() {
        mats.setId("1");
        thomas.setId("2");
        assertNotNull(thomas.getId());
        assertNotNull(mats.getId());
        customerRepository.save(mats);
        ArrayList<Customer> list = new ArrayList<>();
        list.add(thomas);
        assertEquals(customerRepository.count(), 1);
        customerRepository.deleteAll(list);
        assertEquals(customerRepository.count(), 1);
    }

    @Test
    void testDeleteAllIterable_NullObjectList() {
        mats.setId("1");
        thomas.setId("2");
        assertNotNull(mats.getId());
        assertNotNull(thomas.getId());
        customerRepository.save(mats);
        customerRepository.save(thomas);
        assertEquals(customerRepository.count(), 2);
        ArrayList<Customer> list = new ArrayList<>();
        list.add(mats);
        list.add(null);
        assertThrows(IllegalArgumentException.class, () -> {
            customerRepository.deleteAll(list);
        });
        assertEquals(customerRepository.count(), 1);
    }

    @Test
    void testDeleteAllIterable_NullObject() {
        assertThrows(IllegalArgumentException.class, () -> {
            customerRepository.deleteAll(null);
        });
    }

    @Test
    void testDeleteAllIterable_DeleteTwice() {
        mats.setId("1");
        thomas.setId("2");
        assertNotNull(mats.getId());
        assertNotNull(thomas.getId());
        customerRepository.save(mats);
        customerRepository.save(thomas);
        assertEquals(customerRepository.count(), 2);
        ArrayList<Customer> list = new ArrayList<>();
        list.add(mats);
        list.add(mats);
        customerRepository.deleteAll(list);
        assertEquals(customerRepository.count(), 1);
    }

    @Test
    void testDeleteAll() {
        mats.setId("1");
        thomas.setId("2");
        assertNotNull(mats.getId());
        assertNotNull(thomas.getId());
        customerRepository.save(mats);
        customerRepository.save(thomas);
        assertEquals(customerRepository.count(), 2);
        customerRepository.deleteAll();
        assertEquals(customerRepository.count(), 0);
    }

    @Test
    void testDeleteAll_EmptyRepo() {
        customerRepository.deleteAll();
        assertEquals(customerRepository.count(), 0);
    }
}
