package verse.company.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vrs_companies_list")
public class CompanyList {
	// Start of Variable Declaration
	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	/*@Column(name="compname")
	private String compname;*/
	
	@Column(name="user_id")
	private int userId;
	// End of Variable Declaration
	
	@Column(name="parent")
	private int parent;
	
	// Constructor
	public CompanyList() {
	}
	
	// Start of Get and Set Declaration
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	/*public String getCompname() {
		return compname;
	}

	public void setCompname(String compname) {
		this.compname = compname;
	}*/
	
	// End of Get and Set Declaration
}
