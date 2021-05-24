package de.freerider.repository;

import de.freerider.model.Customer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;


@Component
class CustomerRepository implements CrudRepository<Customer, String> {
	private final IDGenerator idGen = new IDGenerator( "C", IDGenerator.IDTYPE.NUM, 6 );
	private final HashMap<String, Customer> repository = new HashMap<String, Customer>();

	@Override
	public <S extends Customer> S save(S entity) throws IllegalArgumentException {
		if(entity == null) throw new IllegalArgumentException("Passed entity cant be null!");
		//new customer with no id
		if(entity.getId() == null || entity.getId().equals("")) {
			String id = idGen.nextId();
			while(repository.containsKey(id)) {
				id = idGen.nextId();
			}
			entity.setId(id);
			repository.put(id, entity);
		} else {
			//dont add object to repo if customer with same id already exists
			if (!repository.containsKey(entity.getId())) {
				//new Customer with existing id
				repository.put(entity.getId(), entity);
			}
		}
		return entity;
	}

	@Override
	public <S extends Customer> Iterable<S> saveAll(Iterable<S> entities) throws IllegalArgumentException {
		if(entities != null) {
			Collection<S> savedEntities = new ArrayList<>();
			for(S customer : entities) {
				if(customer == null) throw new IllegalArgumentException("Passed entity cant be null!");
				savedEntities.add(save(customer));
			}
			return savedEntities;
		} else {
			throw new IllegalArgumentException("Passed entity cant be null!");
		}
	}

	@Override
	public Optional<Customer> findById(String s) throws IllegalArgumentException {
		if(s == null) throw new IllegalArgumentException("Passed entity cant be null!");
		if(!existsById(s)) return Optional.empty();
		return Optional.of(repository.get(s));
	}

	@Override
	public boolean existsById(String s) throws IllegalArgumentException {
		if(s == null) throw new IllegalArgumentException("Passed entity cant be null!");
		return repository.containsKey(s);
	}

	@Override
	public Iterable<Customer> findAll() {
		return repository.values();
	}

	@Override
	public Iterable<Customer> findAllById(Iterable<String> strings) throws IllegalArgumentException {
		if(strings != null) {
			Collection<Customer> savedEntities = new ArrayList<>();
			for(String id : strings) {
				if(id == null) throw new IllegalArgumentException("Passed entity cant be null!");
				savedEntities.add(findById(id).get());
			}
			return savedEntities;
		} else {
			throw new IllegalArgumentException("Passed entity cant be null!");
		}
	}

	@Override
	public long count() {
		return repository.size();
	}

	@Override
	public void deleteById(String s) throws IllegalArgumentException {
		if(s == null) throw new IllegalArgumentException("Passed entity cant be null!");
		repository.remove(s);
	}

	@Override
	public void delete(Customer entity) throws IllegalArgumentException	 {
		if(entity == null) throw new IllegalArgumentException("Passed entity cant be null!");
		if(entity.getId() != null) repository.remove(entity.getId());
	}

	@Override
	public void deleteAllById(Iterable<? extends String> strings) throws IllegalArgumentException {
		if(strings != null) {
			for(String id : strings) {
				if(id == null) throw new IllegalArgumentException("Passed entity cant be null!");
				deleteById(id);
			}
		} else {
			throw new IllegalArgumentException("Passed entity cant be null!");
		}
	}

	@Override
	public void deleteAll(Iterable<? extends Customer> entities) throws IllegalArgumentException {
		if(entities != null) {
			for(Customer customer : entities) {
				if(customer == null) throw new IllegalArgumentException("Passed entity cant be null!");
				delete(customer);
			}
		} else {
			throw new IllegalArgumentException("Passed entity cant be null!");
		}
	}

	@Override
	public void deleteAll() {
		repository.clear();
	}
}
