package de.freerider.model;

public class Customer {
    String id;
    String lastName;
    String firstName;
    String contact;
    Status status;

    Customer(String lastName, String firstName, String contact) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
        this.id = null;
        this.status = Status.New;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
