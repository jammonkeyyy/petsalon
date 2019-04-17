# petsalon
## 通过参考https://spring.io/guides/tutorials/rest/ 上的demo，了解基于rest的mvc设计
## 为model分别创建controller,实现消息的接收与发送
```
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
```
### 通过postman和Mysql查看运行的结果如下：

