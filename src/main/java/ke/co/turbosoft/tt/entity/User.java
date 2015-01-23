package ke.co.turbosoft.tt.entity;

import java.util.List;

import javax.persistence.*;

@Entity
public class User {
	
	@Id
	private String username;
	private String first_name;
	private String last_name;
	private String email;
	private String password;
	private char admin_role;
	@OneToMany(mappedBy="user")
	private List<TaskLog> tasklogs;
	
	public User() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public char getAdmin_role() {
		return admin_role;
	}

	public void setAdmin_role(char admin_role) {
		this.admin_role = admin_role;
	}

	public List<TaskLog> getTasklogs() {
		return tasklogs;
	}

	public void setTasklogs(List<TaskLog> tasklogs) {
		this.tasklogs = tasklogs;
	}
	
		

}
