package verse.sync.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vrs_sync_computerlist")
public class vrs_sync_computerlist {
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "id_vrs_sync_items")
	private int id_vrs_sync_items;
	

	@Column(name = "id_computer")
	private int id_computer;
	
	@Column(name = "time_created")
	private Timestamp time_created;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_vrs_sync_items() {
		return id_vrs_sync_items;
	}

	public void setId_vrs_sync_items(int id_vrs_sync_items) {
		this.id_vrs_sync_items = id_vrs_sync_items;
	}

	public int getId_computer() {
		return id_computer;
	}

	public void setId_computer(int id_computer) {
		this.id_computer = id_computer;
	}

	public Timestamp getTime_created() {
		return time_created;
	}

	public void setTime_created(Timestamp time_created) {
		this.time_created = time_created;
	}
	
	
}
