package verse.company.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vrs_company_users")
public class CompanyUserList {
	// Start of Variable Declaration
	@SuppressWarnings("unused")
	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="company_id")
	private int companyId;
	
	@Column(name="user_id")
	private int userId;
	
	@Column(name="key")
	private String key;
	
	@Column(name="value")
	private String value;
	
	@Column(name="role")
	private String role;
	
	@Column(name="roleid")
	private int roleId;
	
	@Column(name="status")
	private int status;
	// End of Variable Declaration
	
	// Constructor
	public CompanyUserList() {
	}
	
	// Start of Get and Set Declaration
	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	// End of Get and Set Declaration

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
