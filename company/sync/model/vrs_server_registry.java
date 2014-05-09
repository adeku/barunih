package verse.sync.model;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vrs_server_registry")
public class vrs_server_registry {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "computer_name")
	private String computer_name;
	
	@Column(name = "userserver")
	private String userserver;

	@Column(name = "password")
	private String password;
	
	@Column(name = "is_syncronize")
	private boolean is_syncronize;
	
	@Column(name = "last_syncronize")
	private Time last_syncronize;
	
	@Column(name = "in_process")
	private boolean in_process;
	
	@Column(name = "company_list")
	private String company_list;
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComputer_name() {
		return computer_name;
	}

	public void setComputer_name(String computer_name) {
		this.computer_name = computer_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Time getLast_syncronize() {
		return last_syncronize;
	}

	public void setLast_syncronize(Time last_syncronize) {
		this.last_syncronize = last_syncronize;
	}

	public boolean isIs_syncronize() {
		return is_syncronize;
	}

	public void setIs_syncronize(boolean is_syncronize) {
		this.is_syncronize = is_syncronize;
	}

	public boolean isIn_process() {
		return in_process;
	}

	public void setIn_process(boolean in_process) {
		this.in_process = in_process;
	}

	public String getCompany_list() {
		return company_list;
	}

	public void setCompany_list(String company_list) {
		this.company_list = company_list;
	}

	public String getUserserver() {
		return userserver;
	}

	public void setUserserver(String userserver) {
		this.userserver = userserver;
	}
	
	
}
