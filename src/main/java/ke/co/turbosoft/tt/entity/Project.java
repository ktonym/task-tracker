package ke.co.turbosoft.tt.entity;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.util.List;

@Entity
public class Project extends AbstractEntity implements EntityItem<Integer>{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idProject;
	private String projectName;
	@ManyToOne
	@JoinColumn(name="id_company")
	private Company company;
	@OneToMany(mappedBy="project")
	private List<Task> tasks;	
	
	public Project() {
		super();
	}

	public Integer getIdProject() {
		return idProject;
	}

	public void setIdProject(Integer idProject) {
		this.idProject = idProject;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}


    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("idProject",idProject)
                .add("projectName", projectName);

        if(company!=null){
            company.addJson(builder);
        }

    }

    @Override
    public Integer getId() {
        return idProject;
    }
}
