package ke.co.turbosoft.tt.entity;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
public class TaskLog {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String description;
	@Convert(converter=LocalDatePersistenceConverter.class)
	private LocalDate date;
	private Integer minutes;
	@ManyToOne
	@JoinColumn(name="id_task")
	private Task task;
	@ManyToOne
	@JoinColumn(name="username")
	private User user;
	public TaskLog() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public Integer getMinutes() {
		return minutes;
	}
	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
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


}
