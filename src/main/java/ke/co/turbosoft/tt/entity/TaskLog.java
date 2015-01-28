package ke.co.turbosoft.tt.entity;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

@Entity
public class TaskLog extends AbstractEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idTaskLog;
	private String taskDescription;
	@Convert(converter=LocalDatePersistenceConverter.class)
	private LocalDate taskLogDate;
	private Integer taskMinutes;
	@ManyToOne
	@JoinColumn(name="id_task")
	private Task task;
	@ManyToOne
	@JoinColumn(name="username")
	private User user;

    static final SimpleDateFormat DATE_FORMAT_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");

	public TaskLog() {
		super();
	}
	public Integer getIdTaskLog() {
		return idTaskLog;
	}
	public void setIdTaskLog(Integer idTaskLog) {
		this.idTaskLog = idTaskLog;
	}
	public String getTaskDescription() {
		return taskDescription;
	}
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}
	public LocalDate getTaskLogDate() {
		return taskLogDate;
	}
	public void setTaskLogDate(LocalDate taskLogDate) {
		this.taskLogDate = taskLogDate;
	}
	public Integer getTaskMinutes() {
		return taskMinutes;
	}
	public void setTaskMinutes(Integer taskMinutes) {
		this.taskMinutes = taskMinutes;
	}
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}


    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("idTaskLog", idTaskLog )
                .add("taskDescription", taskDescription)
                .add("taskLogDate", taskLogDate==null ? "" : DATE_FORMAT_yyyyMMdd.format(taskLogDate))
                .add("taskMinutes", taskMinutes);

        if(user!=null){
            user.addJson(builder);
        }
        if(task!=null){
            task.addJson(builder);
        }

    }
}
