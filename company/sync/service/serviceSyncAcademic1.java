package verse.sync.service;

public interface serviceSyncAcademic1 {
	public String get_ved_grade_report(int id_table, String action,
			int id_vrs_sync_items);
	public String get_ved_grade_report_detail(int id_table, String action,
			int id_vrs_sync_items);
	public String saveInTable_ved_grade_report(String data,String forWhat, int computer_id);
	public String saveInTable_ved_grade_report_detail(String data,String forWhat, int computer_id);
}
