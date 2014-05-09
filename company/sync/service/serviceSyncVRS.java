package verse.sync.service;

import org.json.JSONObject;


public interface serviceSyncVRS {
	public String getvrs_addresses_DAta(int id_table, String action,
			int id_vrs_sync_items);
	public String getvrs_addresses_DAtaDownload(int id_table, String action,
			int id_vrs_sync_items);
	public String getvrs_attendances_DAta(long id_table, String action,
			int id_vrs_sync_items);
	public String getvrs_server_registry(int id_table, String action,
			int id_vrs_sync_items);
	public String getvrs_subscriptions(int id_table, String action,
			int id_vrs_sync_items);
	public String getvrs_taxonomies(int id_table, String action,
			int id_vrs_sync_items);
	public boolean isCAnSyncIdentity(String namei,String usr,String pass);	
	
	public String saveInTable_vrs_addresses(String data, int computer_id);
	public String saveInTable_vrs_addressesDownload(String data);
	public String saveInTable_vrs_attendances(String data,String forWhat, int computer_id);
	public String saveInTable_vrs_server_registry(String data, String forWhat, int computer_id);
	public String saveInTable_vrs_subscriptions(String data, String forWhat, int computer_id);
	public String saveInTable_vrs_taxonomies(String data, String forWhat, int computer_id);
	
	public void setDAteSyncronize(int computerID);
	public void setDAtaSync(boolean start,int computerID);
	public boolean isCAnSync(int computerID) ;
	
	public int getcompositeidinteger(String table_name);
	public int getcompositeidintegerbycompanyid(String table_name,int id_company);
	public int getcompositeidintegerbySchoolID(String table_name,int id_company);
	
	public void addComputerListString(int comp_id,String data);
	
	void changeIdComposite_vrs_users(int idComposite);	
	public void changeIdComposite_vrs_roles(int idComposite, int company_id, String tableName);
	public void changeIdComposite_ved_candidates(int idComposite, int school_id, String tableName);	
	
	
	public boolean updateDataVrs_sync_itemsGroup(String datai, boolean alreadySetItems, JSONObject obj);
	public void doSetttingItems(int id_vrs_sync_items, int id_CompositeFromServer);
	public void addVrs_Sync_log(String data,int company_id,int id_computer,String type);
	
	public int getComputerIdOnSErver(String computername,String userServer, String passwordSErver);
	public String getvrs_accounts (int id_table, String action, int id_vrs_sync_items);
	public void saveInTable_vrs_accounts(String data, String forWhat);
	public JSONObject setId_vrs_sync_itemsLastID(JSONObject objView,
			int computer_id);
}