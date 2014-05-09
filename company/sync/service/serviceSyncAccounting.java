package verse.sync.service;

public interface serviceSyncAccounting {
	public String getvac_postings_DAta(int id_table, String action,
			int id_vrs_sync_items);
	public String getvac_chart_account_balances_DAtaDownload(int id_table,
			String action, int id_vrs_sync_items);
	public String getvac_chart_account_balances_DAta(int id_table,
			String action, int id_vrs_sync_items);
	public String getvac_budgets_DAtaDownload(int id_table, String action,
			int id_vrs_sync_items);
	public String getvac_budgets_DAta(int id_table, String action,
			int id_vrs_sync_items);
	public String getvac_transactions_DAta(int id_table, String action,
			int id_vrs_sync_items);
	public String getvac_chart_accounts_DAta(int id_table, String action,
			int id_vrs_sync_items);
	public String getvac_transaction_details_DAta(int id_table, String action,
			int id_vrs_sync_items);
	public String getvac_postings_DAtaDownload(int id_table, String action,
			int id_vrs_sync_items);
	public String getvac_transactions_DAtaDownload(int id_table, String action,
			int id_vrs_sync_items);
	public String getvac_chart_accounts_DAtaDownload(int id_table,
			String action, int id_vrs_sync_items);
	public String getvac_transaction_details_DAtaDownload(int id_table,
			String action, int id_vrs_sync_items);
	public String saveInTable_vac_chart_account_balances(String data,
			int computer_id);
	public String saveInTable_vac_budgets(String data, int computer_id);
	public String saveInTable_vac_postings(String data,
			String transaction_details__ID, int computer_id);
	public String saveInTable_vac_transaction_details(String data,
			String transaction_ID, int computer_id);
	public String saveInTable_vac_transactions(String data, String roleID,
			int computer_id);
	public void saveInTable_vac_postingsDownload(String data);
	public void saveInTable_vac_transaction_detailsDownload(String data);
	public void saveInTable_vac_transactionsDownload(String data);	
	public void saveInTable_vac_budgetsDownload(String data);
}

