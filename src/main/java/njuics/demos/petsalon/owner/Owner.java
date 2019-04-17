package njuics.demos.petsalon.owner;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import njuics.demos.petsalon.pet.Pet;
import org.springframework.core.style.ToStringCreator;

@Entity
public class Owner {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    private String name;

	//@JsonBackReference
    @OneToMany(targetEntity= Pet.class, cascade=CascadeType.ALL)
	@JoinColumn(name="owner", referencedColumnName = "id")
    private Set<Pet> petSet; // 宠物集合
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Pet> getPetSet() {
		return petSet;
	}
	public void setPetSet(Set<Pet> petSet) {
		this.petSet = petSet;
	}
	@Override
	public String toString(){
		return new ToStringCreator(this)
				.append("id",this.getId())
				.append("name",this.getName())
				.toString();
	}
}
