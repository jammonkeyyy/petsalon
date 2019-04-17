package njuics.demos.petsalon.pet;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/pets")
public class PetController {
	@Autowired
	private PetRepository petRepository;

	PetController(PetRepository petRepository){this.petRepository=petRepository;}

	@PostMapping(path="add")
	public @ResponseBody String addNewPet (@RequestBody Pet pet) {
		petRepository.save(pet);
		return "saved!";
	}
	
	@GetMapping(path="all")
	public @ResponseBody 
	List<Resource<Pet>> getAllPets() {
		// 先将 petRepository.findAll() 从 Iterable<> 转换为 List<>
		Iterable<Pet> petsIt = petRepository.findAll();
		List<Pet> petsLs = new ArrayList<>();
		petsIt.forEach(i -> { petsLs.add(i); });
		
		List<Resource<Pet>> pets = petsLs.stream().map(
				pet -> new Resource<>(pet, 
						linkTo(methodOn(PetController.class).getOnePet(pet.getId())).withSelfRel(),
						linkTo(methodOn(PetController.class).getAllPets()).withRel("pets")
						)
				).collect(Collectors.toList());
		return pets;
	}

	@GetMapping("/{id}")
	public @ResponseBody 
	Resource<Pet> getOnePet(@PathVariable Integer id) {
		Pet pet = petRepository.findById(id)
				.orElseThrow(() -> new RuntimeException());

		return new Resource<>(pet,
			linkTo(methodOn(PetController.class).getOnePet(id)).withSelfRel(),
			linkTo(methodOn(PetController.class).getAllPets()).withRel("pets"));
	}
}
