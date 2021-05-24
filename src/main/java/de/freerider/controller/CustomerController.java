package de.freerider.controller;

import de.freerider.model.Customer;
import de.freerider.repository.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;


@RestController
public class CustomerController {

    @Autowired
    private CrudRepository<Customer, String> customerManager;

    @RequestMapping("/")
    public String index() {
        Customer c1 = new Customer("Müller", "Markus","markusmueller@email.de");
        ArrayList<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Rübe", "Regina","reginaruebe@email.de"));
        customers.add(new Customer("Lang", "Lukas","lukaslang@email.de"));
        customers.add(new Customer("Apfel", "Anna","annaapfel@email.de"));
        customers.add(new Customer("Taller", "Thorsten","thorstentaller@email.de"));

        try {
            Customer savedC1 = customerManager.save(c1);
            ArrayList<Customer> savedCustomers = (ArrayList<Customer>) customerManager.saveAll(customers);
            ArrayList<String> customersId = new ArrayList<>();
            customersId.add(savedCustomers.get(0).getId());
            customersId.add(savedCustomers.get(1).getId());
            customersId.add(savedCustomers.get(2).getId());
            customersId.add(savedCustomers.get(3).getId());

            ArrayList<String> toBeDeletedIds = new ArrayList<>();
            toBeDeletedIds.add(customersId.get(2));

            ArrayList<Customer> deleteAllCustomers = new ArrayList<>();
            deleteAllCustomers.add(savedCustomers.get(3));

            String existTest = "<b>c1 existiert im repository:</b> " + customerManager.existsById(c1.getId()) +"<br/>";
            String findByIdTest = "<b>c1 Vorname (Markus):</b> " + customerManager.findById(savedC1.getId()).get().getFirstName() +"<br/>";
            String findAllTest = "<b>Alle Kunden Objekte:</b> " + customerManager.findAll().toString() +"<br/>";
            String findAllByIdTest = "<b>Nach ID gesuchte Kunden:</b> " + customerManager.findAllById(customersId);
            String countTest = "<b>Anzahl gespeicherter Kunden:</b> " + customerManager.count() +"<br/>";
            customerManager.save(c1);
            String countAfterTest = "<b>Anzahl gespeicherter Kunden nach versuchter Duplikats Speicherung:</b> " + customerManager.count() +"<br/>";
            customerManager.deleteById(customersId.get(0));
            String deleteByIdTest = "<b>Anzahl Kunden nach deleteById (4):</b> " + customerManager.count();
            customerManager.delete(savedCustomers.get(1));
            String deleteTest = "<b>Anzahl Kunden nach deleteByEntity (3):</b> " + customerManager.count();
            String deleteAllByIdTest = "<b>Anzahl Kunden vor deleteAllById (3) Löschung:</b> " + customerManager.count();
            customerManager.deleteAllById(toBeDeletedIds);
            deleteAllByIdTest = deleteAllByIdTest + " / <b>Nach Löschung (2):</b> " + customerManager.count();
            customerManager.deleteAll(deleteAllCustomers);
            String deleteAllTest = "<b>Anzahl Kunden nach deleteAll Operation (1):</b> " + customerManager.count();
            customerManager.deleteAll();
            String purgeRepoTest = "<b>Anzahl Kunden nach kompletter Löschung des repositorys (0):</b> " + customerManager.count();

            String sl = "<p style='display: block; margin: 10px 0;'>-----</p>";
            return existTest + sl +
                    findByIdTest + sl +
                    findAllTest + sl +
                    findAllByIdTest + sl +
                    countTest + sl +
                    countAfterTest + sl +
                    deleteByIdTest + sl +
                    deleteTest + sl +
                    deleteAllByIdTest + sl +
                    deleteAllTest + sl +
                    purgeRepoTest;

        } catch (IllegalArgumentException e) {
            System.out.println(e.toString());
            return "<h1>IllegalArgumentException occurred!<h1>";
        }
    }
}
