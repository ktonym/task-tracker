package ke.co.turbosoft.tt.entity;

import java.util.List;

import javax.persistence.*;

@Entity
public class Company {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	@OneToMany(mappedBy="company")
	private List<Project> projects;
	
	public Company() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	
	
	
}
