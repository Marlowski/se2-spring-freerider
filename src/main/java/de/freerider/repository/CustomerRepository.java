package de.freerider.repository;

import de.freerider.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
class CustomerRepository { //implements CrudRepository<Customer, String> {
	private final IDGenerator idGen = new IDGenerator( "C", IDGenerator.IDTYPE.NUM, 6 );
	/*
	@Autowired
	CrudRepository<Customer, String> customerManager;
	 */
}
