package njuics.demos.petsalon.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/test")
public class ServiceController {
	@Autowired
	private ServiceRepository serviceRepository;
	ServiceController(ServiceRepository serviceRepository){this.serviceRepository=serviceRepository;}
	@PostMapping(path="/service")
	public @ResponseBody String addNewService (@RequestBody Service service) {
		serviceRepository.save(service);
		return "New Service Saved";
	}
	@GetMapping(path="/service")
	public @ResponseBody 
	List<Resource<Service>> getAllService() {
		Iterable<Service> serviceIt = serviceRepository.findAll();
		List<Service> serviceLs = new ArrayList<>();
		serviceIt.forEach(i -> { serviceLs.add(i); });
		
		List<Resource<Service>> service = serviceLs.stream().map(
				serv -> new Resource<>(serv, 
						linkTo(methodOn(ServiceController.class).getOneService(serv.getId())).withSelfRel(),
						linkTo(methodOn(ServiceController.class).getAllService()).withRel("service")
						)
				).collect(Collectors.toList());
		return service;
	}
	
	@GetMapping("/service/{id}")
	public @ResponseBody 
	Resource<Service> getOneService(@PathVariable Integer id) {
		Service service = serviceRepository.findById(id)
				.orElseThrow(() -> new RuntimeException());
		return new Resource<>(service,
			linkTo(methodOn(ServiceController.class).getOneService(id)).withSelfRel(),
			linkTo(methodOn(ServiceController.class).getAllService()).withRel("service"));
	}
}