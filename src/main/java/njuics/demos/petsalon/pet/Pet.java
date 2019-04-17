package njuics.demos.petsalon.pet;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import njuics.demos.petsalon.owner.Owner;
import njuics.demos.petsalon.service.Service;
import org.springframework.core.style.ToStringCreator;

@Entity // This tells Hibernate to make a table out of this class
public class Pet {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String name;
    private PetType type;
    
	@JsonBackReference
	@ManyToOne(targetEntity= Owner.class)
    @JoinColumn(name="owner", referencedColumnName = "id")
    private Owner owner;

	//@JsonBackReference
    @OneToMany(targetEntity= Service.class, cascade=CascadeType.ALL)
	@JoinColumn(name="pet", referencedColumnName = "id")
    private List<Service> serviceList;

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
	public PetType getType() {
		return type;
	}
	public void setType(PetType type) {
		this.type = type;
	}
	public Owner getOwner() {
		return owner;
	}
	public void setOwner(Owner owner) {
		this.owner = owner;
	}
	public List<Service> getServiceList() {
		return serviceList;
	}
	public void setServiceList(List<Service> serviceList) {
		this.serviceList = serviceList;
	}
	public String toString(){
		return new ToStringCreator(this)
				.append("id",this.getId())
				.append("name",this.getName())
				.append("service",this.getServiceList())
				.append("owner",this.getOwner())
				.toString();
	}
}

