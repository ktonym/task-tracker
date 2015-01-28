package ke.co.turbosoft.tt.entity;

import javax.json.JsonObjectBuilder;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class User extends AbstractEntity{
	
	@Id
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Character adminRole;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public Character getAdminRole() {
		return adminRole;
	}

	public void setAdminRole(Character adminRole) {
		this.adminRole = adminRole;
	}

	public List<TaskLog> getTasklogs() {
		return tasklogs;
	}

	public void setTasklogs(List<TaskLog> tasklogs) {
		this.tasklogs = tasklogs;
	}
	
	public boolean isAdmin(){
		return adminRole == null ? false : adminRole.equals('Y');
	}


    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("username",username)
                .add("firstName", firstName)
                .add("lastName",lastName)
                .add("email",email)
                .add("adminRole", adminRole+"")
                .add("fullName", firstName + " " + lastName);
    }
}
