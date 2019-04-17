package njuics.demos.petsalon.service;

import org.springframework.data.repository.CrudRepository;

//This will be AUTO IMPLEMENTED by Spring into a Bean called serviceRepository
//CRUD refers Create, Read, Update, Delete

public interface ServiceRepository extends CrudRepository<Service, Integer> {
	
}
