package verse.sync.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vrs_sync_log")
public class vrs_sync_log {
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "id_computer")
	private int id_computer;

	@Column(name = "company_id")
	private int company_id;
	
	@Column(name = "id_composite")
	private int id_composite;
	
	@Column(name = "table_name")
	private String table_name;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "action")
	private String action;
	
	@Column(name = "time_created")
	private Timestamp time_created;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_computer() {
		return id_computer;
	}

	public void setId_computer(int id_computer) {
		this.id_computer = id_computer;
	}

	public int getCompany_id() {
		return company_id;
	}

	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}

	public int getId_composite() {
		return id_composite;
	}

	public void setId_composite(int id_composite) {
		this.id_composite = id_composite;
	}

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Timestamp getTime_created() {
		return time_created;
	}

	public void setTime_created(Timestamp time_created) {
		this.time_created = time_created;
	}
}
