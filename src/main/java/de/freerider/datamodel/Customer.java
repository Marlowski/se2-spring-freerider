package de.freerider.datamodel;

import org.springframework.stereotype.Component;

import java.util.Objects;

public class Customer {
	private String id;
	private String lastName;
	private String firstName;
	private String contact;
	private Status status;

	public Customer(String lastName, String firstName, String contact) {
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
		//id is already set and not trying to reset it
		if(id != null && this.id != null) {
			return;
			//reset exiting id
		} else if(id == null && this.id != null) {
			this.id = null;
			//id is null and gets set
		} else {
			this.id = id;
		}
	}

	public String getLastName() {
		if (lastName == null) {
			return "";
		} else return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = Objects.requireNonNullElse(lastName, "");
	}

	public String getFirstName() {
		if (firstName == null) {
			return "";
		} else return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = Objects.requireNonNullElse(firstName, "");
	}

	public String getContact() {
		if(contact == null) {
			return "";
		} else return contact;
	}

	public void setContact(String contact) {
		this.contact = Objects.requireNonNullElse(contact, "");
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		if(status == null) return;
		this.status = status;
	}
}