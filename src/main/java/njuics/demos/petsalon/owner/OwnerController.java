package njuics.demos.petsalon.owner;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

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
public class OwnerController {
	@Autowired
	private OwnerRepository ownerRepository;
	OwnerController(OwnerRepository ownerRepository){this.ownerRepository=ownerRepository;}


	@PostMapping(path="add")
	public @ResponseBody String addNewOwner (@RequestBody Owner owner) {
		ownerRepository.save(owner);
		return "New Owner Saved";
	}

	@GetMapping(path="owners")
	public @ResponseBody 
	List<Resource<Owner>> getAllOwners() {
		Iterable<Owner> ownersIt = ownerRepository.findAll();
		List<Owner> ownersLs = new ArrayList<>();
		ownersIt.forEach(i -> { ownersLs.add(i); });
		
		List<Resource<Owner>> owners = ownersLs.stream().map(
				owner -> new Resource<>(owner, 
						linkTo(methodOn(OwnerController.class).getOneOwner(owner.getId())).withSelfRel(),
						linkTo(methodOn(OwnerController.class).getAllOwners()).withRel("owners")
						)
				).collect(Collectors.toList());
		return owners;
	}
	
	@GetMapping(path="/owners/{id}")
	public @ResponseBody
	Resource<Owner> getOneOwner(@PathVariable Integer id) {
		Owner owner = ownerRepository.findById(id)
				.orElseThrow(() -> new RuntimeException());
		return new Resource<>(owner,
				linkTo(methodOn(OwnerController.class).getOneOwner(owner.getId())).withSelfRel(),
				linkTo(methodOn(OwnerController.class).getAllOwners()).withRel("owners"));
	}
}