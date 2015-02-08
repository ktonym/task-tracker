package ke.co.turbosoft.tt.entity;

import javax.json.JsonObjectBuilder;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Objects;

@Entity
public class User extends AbstractEntity implements EntityItem<String>{
	
	@Id
	private String username;
	private String firstName;
	private String lastName;
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Invalid email")
	private String email;
	private String password;
	private Character adminRole;
	@OneToMany(mappedBy="user")
	private List<TaskLog> tasklogs;
	
	public User() {
	}

    public User(String username, String firstName, String lastName, String email, String password, Character adminRole) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.adminRole = adminRole;
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

    @Override
    public String getId() {
        return username;
    }

    @Override
    public String toString(){
        return "ke.co.turbosoft.tt.entity.User[ username=" + username +" ]";
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        return Objects.equals(this.username, other.username);
    }


}
