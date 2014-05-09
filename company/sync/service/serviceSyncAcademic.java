package verse.sync.service;

import java.sql.Timestamp;

public interface serviceSyncAcademic {
	public String getved_students_DAtaDownload(Long id_table, String action,
			int id_vrs_sync_items);
	public String getved_students_DAta(Long id_table, String action,
			int id_vrs_sync_items);
	public String getved_scores_DAtaDownload(Long id_table, String action,
			int id_vrs_sync_items);
	public String getved_scores_DAta(Long id_table, String action,
			int id_vrs_sync_items);
	public String getved_reports_DAtaDownload(Long id_table, String action,
			int id_vrs_sync_items);
	public String getved_reports_DAta(Long id_table, String action,
			int id_vrs_sync_items);
	public String getved_enrollments_DAta(Long id_table, String action,
			int id_vrs_sync_items);
	public String getved_courses_DAtaDownload(Long id_table, String action,
			int id_vrs_sync_items);
	public String getved_courses_DAta(Long id_table, String action,
			int id_vrs_sync_items);
	public String getved_course_schedule_DAta(int id_table, String action,
			int id_vrs_sync_items);
	public String getved_course_list_DAtaDownload(int id_table, String action,
			int id_vrs_sync_items);
	public String getved_course_list_DAta(int id_table, String action,
			int id_vrs_sync_items);
	public String getved_classes_DAtaDownload(long id_table, String action,
			int id_vrs_sync_items);
	public String getved_candidates_DAtaDownload(Long id_table, String action,
			int id_vrs_sync_items);
	public String getved_candidates_DAta(Long id_table, String action,
			int id_vrs_sync_items);
	public String getved_classes_DAta(long id_table, String action,
			int id_vrs_sync_items);
	public String getved_attendances_DAtaDownload(Long id_table, String action,
			int id_vrs_sync_items);
	public String getved_attendances_DAta(Long id_table, String action,
			int id_vrs_sync_items);
	public String getved_assignments_DAtaDownload(Long id_table, String action,
			int id_vrs_sync_items);
	public String getved_assignments_DAta(Long id_table, String action,
			int id_vrs_sync_items);
	public String saveInTable_ved_scores(String data, int computer_id);
	public String saveInTable_ved_scoresDownload(String data);
	public void saveInTable_ved_studentsDownload(String data);
	public String saveInTable_ved_students(String data, int computer_id);
	public void saveInTable_ved_reportsDownload(String data);
	public String saveInTable_ved_reports(String data, int computer_id);
	public void saveInTable_ved_enrollmentsDownload(String data);
	public String saveInTable_ved_enrollments(String data, int computer_id);
	public String saveInTable_ved_courses(String data, int computer_id);
	public String saveInTable_ved_course_schedule(String data, int computer_id);
	public void saveInTable_ved_coursesDownload(String data);
	public void saveInTable_ved_course_listDownload(String data);
	public void saveInTable_ved_course_scheduleDownload(String data);
	public String saveInTable_ved_course_list(String data, int computer_id);
	public void saveInTable_ved_classesDownload(String data);
	public String saveInTable_ved_classes(String data, int computer_id);
	public void saveInTable_ved_candidatesDownload(String data);
	public String saveInTable_ved_candidates(String data, String roleId,
			int computer_id);
	public String saveInTable_ved_attendances(String data, int computer_id);
	public String saveInTable_ved_assignments(String data, int computer_id);
	public void saveInTable_ved_attendancesDownload(String data);
	public void saveInTable_ved_assignmentsDownload(String data);
	public String getved_enrollments_DAtaDownload(Long id_table, String action,
			int id_vrs_sync_items);
	public String getved_course_schedule_DAtaDownload(int id_table,
			String action, int id_vrs_sync_items);
	public void addCandidateMetas(Long idTable, String key, String val,
			Timestamp created, Timestamp modified);
}
