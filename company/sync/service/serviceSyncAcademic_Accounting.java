package verse.sync.service;

import java.sql.Timestamp;

public interface serviceSyncAcademic_Accounting {
	public String saveTransactionsWhenDownload(String data);
	public String saveStudents(String data);
	public String getVed_students(String getBack);
	public String saveInTable_students(String data, int computer_id);
	public String getVac_transactionsforDownload(String getBack,
			String sqlstudentLimit, Object[] obj, int id_table, int computer_id);
	public String getVed_studentsforDownload(String getBack,
			String sqlstudentLimit, Object[] obj, int id_table, int computer_id);
	public String getVac_transactionsforUploD(String getBack);
	public String saveIn_transactions1(String data, int computer_id);
	public String getvrs_companies_DAta(int id_table, String action,
			int id_vrs_sync_items);
	public String getvrs_companies_DAtaDownload(int id_table, String action,
			int id_vrs_sync_items);
	public void saveInTable_vac_chart_account_balancesDownload(String data);
	public void addTransactionMetas(int trransaction_id, String key, String val,
			Timestamp created, Timestamp modified);
	public String saveInTable_vac_chart_accounts(String data, int computer_id);
	public void saveInTable_vac_chart_accountsDownload(String data);
	public void saveInTable_vrs_companiesDownload(String data);
	public String saveInTable_vrs_companies(String data, int computer_id);
}
