package verse.sync.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vrs_sync_items")
public class vrs_sync_items {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "company_id")
	private int company_id;
	
	@Column(name = "id_composite")
	private int id_composite;

	@Column(name = "table_name")
	private String table_name;

	@Column(name = "action")
	private String action;

	@Column(name = "status")
	private boolean status;

	@Column(name = "time_created")
	private Timestamp time_created;
	
	@Column(name = "id_table")
	private long id_table;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Timestamp getTime_created() {
		return time_created;
	}

	public void setTime_created(Timestamp time_created) {
		this.time_created = time_created;
	}

	public long getId_table() {
		return id_table;
	}

	public void setId_table(long id_table) {
		this.id_table = id_table;
	}
	
	public int getId_tableInteger() {
		return (int)id_table;
	}

	public void setId_tableInteger(int id_table) {
		this.id_table = id_table;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	
}
