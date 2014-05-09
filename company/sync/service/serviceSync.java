package verse.sync.service;

import java.sql.Timestamp;
import java.util.Date;

public interface serviceSync {
	public int getComputerIdSync();
	
	public String getComputerName(int compid);
	public String getUser(int compid);
	public String getPassword(int compid);		
	public String getCompamyList(int compid);
	public boolean authenticationCheck(String compName,String user,String password);
	public String getDataREadyUpload();
	public String receiveUploadData(String data, int computer_id);
	public void updateDataVrs_sync_items(String datai);
	public int getCountDataUpload(int computer_id );
	public String getDAtaDownload(int user_id,int computer_id);
	public boolean saveDAtaFromDownload(String data);
	public void addComputerList(int comp_id,int id);	
	public int getLimitDownload(int user_id,int comp_id);
	public int getid_compositeFromvrs_companies(int company_id);
	public int getUser_idComposite(int user_id);
	public int getUser_idfromCompositeID(int user_idComposite);
	public String getcompanyList(int user_id);
	public String getCompanyChild(int company_id);
	public void addRolesMetas(int role_id, String key, String value, Timestamp created, Timestamp modified);
	public Timestamp getTimeStamp(String text);
	public Date getDate(String text);
	
	public void saveInvrs_UsersFromdownload(String data);
	public String saveInTable_vrs_users(String data, int computer_id);
	public void saveInTable_vrs_rolesDownload(String data);	
	public String saveInTable_vrs_roles(String data,String userComposite, int computer_id);
	public String getvrs_roles_DAtaDownload(int id_table, String action,
			int id_vrs_sync_items);
	
	public String getvrs_userus_DAta(int id_table, String action,
			int id_vrs_sync_items);
	public String getvrs_userus_DAtaForDownload(int id_table, String action,
			int id_vrs_sync_items);
	public String getvrs_roles_DAta(int id_table, String action,
			int id_vrs_sync_items);
	public void addCompaniesMetas(int company_id, String key, String value,
			Timestamp created, Timestamp modified);
}
