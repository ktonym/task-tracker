package ke.co.turbosoft.tt.entity;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.util.List;

@Entity
public class Task extends AbstractEntity{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idTask;
	private String taskName;
	@ManyToOne
	@JoinColumn(name="id_project")
	private Project project;
	@OneToMany(mappedBy="task")
	private List<TaskLog> tasklogs;
	
	public Task() {
		super();
	}

	public Integer getIdTask() {
		return idTask;
	}

	public void setIdTask(Integer idTask) {
		this.idTask = idTask;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<TaskLog> getTasklogs() {
		return tasklogs;
	}

	public void setTasklogs(List<TaskLog> tasklogs) {
		this.tasklogs = tasklogs;
	}


    @Override
    public void addJson(JsonObjectBuilder builder) {

        builder.add("idTask",idTask)
                .add("taskName", taskName);

        if(project!=null){
            project.addJson(builder);

            Company company = project.getCompany();
            company.addJson(builder);

        }

    }
}
