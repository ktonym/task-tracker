package ke.co.turbosoft.tt.entity;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.util.List;

@Entity
public class Company extends AbstractEntity implements EntityItem<Integer> {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idCompany;
	private String companyName;
	@OneToMany(mappedBy="company")
	private List<Project> projects;
	
	public Company() {
		super();
	}

	public Integer getIdCompany() {
		return idCompany;
	}

	public void setIdCompany(Integer idCompany) {
		this.idCompany = idCompany;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}


    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("idCompany",idCompany)
                .add("companyName",companyName);
    }

    @Override
    public Integer getId() {
        return idCompany;
    }
}
