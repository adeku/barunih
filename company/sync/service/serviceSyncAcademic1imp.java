package verse.sync.service;

import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import verse.academic.model.ved_course_list;
import verse.academic.model.ved_courses;
import verse.academic.model.ved_enrollments;
import verse.academic.report.model.GradeReport;
import verse.academic.report.model.GradeReportDetail;
import verse.commonClass.CommonFunction;
import verse.model.vrs_companies;
import verse.model.vrs_roles;

@Service("serviceSyncAcademic1")
public class serviceSyncAcademic1imp implements serviceSyncAcademic1 {
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	serviceSyncVRS ssv;
	@Autowired
	private serviceSync svs;
	CommonFunction cf = new CommonFunction();

	public String get_ved_grade_report(int id_table, String action,
			int id_vrs_sync_items) {
		String fback = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				GradeReport.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		if (it.hasNext()) {
			GradeReport gr = (GradeReport) it.next();

			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "ved_grade_report");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();
				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_enrollments.class);
				criteria.add(Restrictions.eq("enrollmentId",
						gr.getEnrolmentId()));
				Iterator itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					ved_enrollments vel = (ved_enrollments) itMeta.next();
					objColumn.put("enrollmentID_Composite",
							vel.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vel.getSchoolId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("enrollmentcompanyID_Composite",
								vc.getId_composite());
					}
				}
				if (gr.getSerialNumber() == null)
					objColumn.put("serialNumber", "");
				else
					objColumn.put("serialNumber", gr.getSerialNumber());
				objColumn.put("created", gr.getCreatedDate());
				objColumn.put("modified", gr.getModifiedDate());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", gr.getCreated_by_role()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("created_by_role_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("created_by_role_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("status", gr.getStatus());
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId",
						gr.getCancelled_by_role()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("cancelled_by_role_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("cancelled_by_role_id_compositeCompany",
								vc.getId_composite());
					}
				}
				objColumn.put("id_composite", gr.getId_composite());

				obj.append("ved_grade_report", objColumn);
				fback = obj.toString();

			} catch (Exception ex) {
				cf.viewAlert("error get_ved_grade_report " + ex.getMessage());
			}
		}
		return fback;
	}

	public String get_ved_grade_report_detail(int id_table, String action,
			int id_vrs_sync_items) {
		String fback = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				GradeReportDetail.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = criteria.list().iterator();
		if (it.hasNext()) {
			GradeReportDetail grd = (GradeReportDetail) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "ved_grade_report_detail");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();
				try {
					ved_course_list vcl = grd.getCourseList();
					objColumn.put("idved_course_list_composite",
							vcl.getId_composite());
				} catch (Exception ex) {
					objColumn.put("idved_course_list_composite", "");
				}
				objColumn.put("totalAvg", grd.getTotalAvg());
				if (grd.getComments() == null)
					objColumn.put("comments", "");
				else
					objColumn.put("comments", grd.getComments());
				try {
					GradeReport grr = grd.getGradeReport();
					objColumn.put("GradeReport_Idcomposite",
							grr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_enrollments.class);
					criteria.add(Restrictions.eq("enrollmentId",
							grr.getEnrolmentId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						ved_enrollments vel = (ved_enrollments) itMeta.next();
						objColumn.put("enrollmentID_Composite",
								vel.getId_composite());
						criteria = sessionFactory.getCurrentSession()
								.createCriteria(vrs_companies.class);
						criteria.add(Restrictions.eq("companyId",
								vel.getSchoolId()));
						itMeta = criteria.list().iterator();
						if (itMeta.hasNext()) {
							vrs_companies vc = (vrs_companies) itMeta.next();
							objColumn.put("enrollmentcompanyID_Composite",
									vc.getId_composite());
						}
					}
				} catch (Exception ex) {
					objColumn.put("GradeReport_Idcomposite", "");
					objColumn.put("enrollmentID_Composite", "");
					objColumn.put("enrollmentcompanyID_Composite", "");
				}

				try {
					ved_courses vco = grd.getCourse();
					objColumn.put("CourseId_composite", vco.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vco.getSchoolId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vcco = (vrs_companies) itMeta.next();
						objColumn.put("CoursecompanyID_Composite",
								vcco.getId_composite());
					}
				} catch (Exception ex) {
					objColumn.put("CourseId_composite", "");
					objColumn.put("CoursecompanyID_Composite", "");
				}

				objColumn.put("id_composite", grd.getId_composite());

				obj.append("ved_grade_report_detail", objColumn);
				fback = obj.toString();
			} catch (Exception ex) {
				cf.viewAlert("error get_ved_grade_report_detail "
						+ ex.getMessage());
			}
		}
		return fback;
	}


	public String saveInTable_ved_grade_report(String data, String forWhat,
			int computer_id) {
		String fBacK = "", sql = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL", obj.get("id_vrs_sync_items")
					.toString());
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("ved_grade_report")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);

			Long enrollmentID = Long.valueOf(-1);
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(vrs_companies.class);
			criteria.add(Restrictions.eq(
					"id_composite",
					Integer.valueOf(dataVrs_users.get(
							"enrollmentcompanyID_Composite").toString())));
			Iterator it = criteria.list().iterator();
			if (it.hasNext()) {
				vrs_companies vc = (vrs_companies) it.next();
				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_enrollments.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"enrollmentID_Composite").toString())));
				criteria.add(Restrictions.eq("schoolId", vc.getCompanyId()));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					ved_enrollments ven = (ved_enrollments) it.next();
					enrollmentID = ven.getEnrollmentId();
				}
			}

			int createbyRole = -1;
			criteria = sessionFactory.getCurrentSession().createCriteria(
					vrs_companies.class);
			criteria.add(Restrictions.eq(
					"id_composite",
					Integer.valueOf(dataVrs_users.get(
							"created_by_role_id_compositeCompany").toString())));
			it = criteria.list().iterator();
			if (it.hasNext()) {
				vrs_companies vcom = (vrs_companies) it.next();
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"created_by_role_id_composite").toString())));
				criteria.add(Restrictions.eq("companyId", vcom.getCompanyId()));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_roles vr = (vrs_roles) it.next();
					createbyRole = vr.getRoleId();
				}
			}

			int cancelledBy = -1;
			try {
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get(
								"cancelled_by_role_id_compositeCompany")
								.toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get(
									"cancelled_by_role_id_composite")
									.toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						cancelledBy = vr.getRoleId();
					}
				}
			} catch (Exception ex) {
			}

			if (action.equalsIgnoreCase("add")) {
				GradeReport vgr = new GradeReport();
				vgr.setEnrolmentId(enrollmentID);
				vgr.setSerialNumber(dataVrs_users.get("serialNumber")
						.toString());
				vgr.setCreatedDate(svs.getTimeStamp(dataVrs_users.get(
						"created").toString()));
				vgr.setModifiedDate(svs.getTimeStamp(dataVrs_users.get(
						"modified").toString()));
				vgr.setCreated_by_role(createbyRole);
				vgr.setStatus(dataVrs_users.get("status").toString());
				if (cancelledBy != -1)
					vgr.setCancelled_by_role(cancelledBy);

				if (forWhat.equalsIgnoreCase("download")) {
					vgr.setId_composite(Integer.valueOf(dataVrs_users.get(
							"id_composite").toString()));
					sql = "ALTER TABLE ved_grade_report DISABLE TRIGGER insert_ved_grade_reportt";
					sessionFactory.getCurrentSession().createSQLQuery(sql)
							.executeUpdate();
				}
				sessionFactory.getCurrentSession().save(vgr);
				sessionFactory.getCurrentSession().clear();
				if (forWhat.equalsIgnoreCase("download")) {
					sql = "ALTER TABLE ved_grade_report ENABLE TRIGGER insert_ved_grade_reportt";
					sessionFactory.getCurrentSession().createSQLQuery(sql)
							.executeUpdate();
				}
				if (forWhat.equalsIgnoreCase("upload")) {
					objView = ssv.setId_vrs_sync_itemsLastID(objView,
							computer_id);
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(GradeReport.class);
					criteria.add(Restrictions.eq("id", vgr.getId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						GradeReport vstu = (GradeReport) it.next();
						objView.put("id_CompositeFromServer",
								vstu.getId_composite());
						fBacK = objView.toString();
					}
				}
			} else if (action.equalsIgnoreCase("edit")) {
				criteria = sessionFactory.getCurrentSession().createCriteria(
						GradeReport.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("id_composite").toString())));
				criteria.add(Restrictions.eq("enrolmentId", enrollmentID));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					GradeReport vgr = (GradeReport) it.next();
					if (forWhat.equalsIgnoreCase("download")) {
						vgr.setId_composite(Integer.valueOf(dataVrs_users.get(
								"id_composite").toString()));
						sql = "ALTER TABLE ved_grade_report DISABLE TRIGGER insert_ved_grade_reportt";
						sessionFactory.getCurrentSession().createSQLQuery(sql)
								.executeUpdate();
					}
					sql = "update ved_grade_report set serial_number=:serial_number,created=:created,modified=:modified"
							+ ",status=:status,published_by=:published_by where id=:id1";
					sessionFactory
							.getCurrentSession()
							.createSQLQuery(sql)
							.setString(
									"serial_number",
									dataVrs_users.get("serialNumber")
											.toString())
							.setTimestamp(
									"created",
									svs.getTimeStamp(dataVrs_users.get(
											"created").toString()))
							.setTimestamp(
									"modified",
									svs.getTimeStamp(dataVrs_users.get(
											"modified").toString()))
							.setInteger("published_by", createbyRole).setInteger("id1", vgr.getId())
							.setString("status",
									dataVrs_users.get("status").toString())
							.executeUpdate();
					if (cancelledBy != -1) {
						sql = "update ved_grade_report set cancelled_by=:cancelled_by  where id=:id1  ";
						sessionFactory
						.getCurrentSession()
						.createSQLQuery(sql)
						.setInteger("cancelled_by", cancelledBy)
						.setInteger("id1", vgr.getId())
						.executeUpdate();
					}
					if (forWhat.equalsIgnoreCase("download")) {
						sql = "ALTER TABLE ved_grade_report ENABLE TRIGGER insert_ved_grade_reportt";
						sessionFactory.getCurrentSession().createSQLQuery(sql)
								.executeUpdate();
					}
					if (forWhat.equalsIgnoreCase("upload")) {
						objView = ssv.setId_vrs_sync_itemsLastID(objView,
								computer_id);
					}
					fBacK = objView.toString();
				}
			}
		} catch (Exception ex) {
			cf.viewAlert("error saveInTable_ved_grade_report "
					+ ex.getMessage());
		}
		return fBacK;
	}

	public String saveInTable_ved_grade_report_detail(String data,
			String forWhat, int computer_id) {
		String fBacK = "", sql = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL", obj.get("id_vrs_sync_items")
					.toString());
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj
					.get("ved_grade_report_detail").toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);

			int idCourseList = -1;
			ved_course_list vcl = new ved_course_list();
			try {
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(ved_course_list.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"idved_course_list_composite").toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vcl = (ved_course_list) it.next();
					idCourseList = vcl.getId();
				}
			} catch (Exception ex) {

			}

			int gradeReportID = -1;
			GradeReport gre = new GradeReport();
			try {
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"enrollmentcompanyID_Composite").toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vvco = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_enrollments.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"enrollmentID_Composite").toString())));
					criteria.add(Restrictions.eq("schoolId",
							vvco.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_enrollments ven = (ved_enrollments) it.next();
						criteria = sessionFactory.getCurrentSession()
								.createCriteria(GradeReport.class);
						criteria.add(Restrictions.eq(
								"id_composite",
								Integer.valueOf(dataVrs_users.get(
										"GradeReport_Idcomposite").toString())));
						criteria.add(Restrictions.eq("enrolmentId",
								ven.getEnrollmentId()));
						it = criteria.list().iterator();
						if (it.hasNext()) {
							gre = (GradeReport) it.next();
							gradeReportID = gre.getId();
						}
					}
				}
			} catch (Exception ex) {
			}

			Long course_id = Long.valueOf(-1);
			ved_courses vco = new ved_courses();
			try {
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CoursecompanyID_Composite").toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_courses.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CourseId_composite").toString())));
					criteria.add(Restrictions.eq("schoolId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vco = (ved_courses) it.next();
						course_id = vco.getId();
					}
				}
			} catch (Exception ex) {
			}

			if (action.equalsIgnoreCase("add")) {
				GradeReportDetail grd = new GradeReportDetail();
				if (idCourseList != -1) {
					grd.setCourseList(vcl);
				}
				grd.setTotalAvg(Long.valueOf(dataVrs_users.get("totalAvg")
						.toString()));
				grd.setComments(dataVrs_users.get("comments").toString());
				if (gradeReportID != -1) {
					grd.setGradeReport(gre);
				}
				if (course_id != -1)
					grd.setCourse(vco);
				if (forWhat.equalsIgnoreCase("download")) {
					grd.setId_composite(Integer.valueOf(dataVrs_users.get(
							"id_composite").toString()));
					sql = "ALTER TABLE ved_grade_report_detail DISABLE TRIGGER insert_ved_grade_report_detailt";
					sessionFactory.getCurrentSession().createSQLQuery(sql)
							.executeUpdate();
				}
				sessionFactory.getCurrentSession().save(grd);
				sessionFactory.getCurrentSession().clear();
				if (forWhat.equalsIgnoreCase("download")) {
					sql = "ALTER TABLE ved_grade_report_detail ENABLE TRIGGER insert_ved_grade_report_detailt";
					sessionFactory.getCurrentSession().createSQLQuery(sql)
							.executeUpdate();
				}

				if (forWhat.equalsIgnoreCase("upload")) {
					objView = ssv.setId_vrs_sync_itemsLastID(objView,
							computer_id);
					Criteria criteria = sessionFactory.getCurrentSession()
							.createCriteria(GradeReportDetail.class);
					criteria.add(Restrictions.eq("id", grd.getId()));
					Iterator it = criteria.list().iterator();
					if (it.hasNext()) {
						GradeReportDetail vstu = (GradeReportDetail) it.next();
						objView.put("id_CompositeFromServer",
								vstu.getId_composite());
						fBacK = objView.toString();
					}
				}
			} else if (action.equalsIgnoreCase("edit")) {
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(GradeReportDetail.class);
				criteria.createAlias("gradeReport", "gradeReport");
				criteria.add(Restrictions.eq("gradeReport.id", gre.getId()));
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("id_composite").toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					GradeReportDetail grIe = (GradeReportDetail) it.next();
					if (forWhat.equalsIgnoreCase("download")) {
						sql = "ALTER TABLE ved_grade_report_detail DISABLE TRIGGER insert_ved_grade_report_detailt";
						sessionFactory.getCurrentSession().createSQLQuery(sql)
								.executeUpdate();
					}
					sql = "update ved_grade_report_detail set comments=:comments,total_avg=:total_avg where id = :id1";
					sessionFactory.getCurrentSession().createSQLQuery(sql)
					.setString("comments", dataVrs_users.get("comments").toString())
					.setLong("total_avg", Long.valueOf(dataVrs_users.get("totalAvg")
							.toString()))
							.setInteger("id1", grIe.getId())
					.executeUpdate();
					if (idCourseList != -1) {
						sql = "update GradeReportDetail a set a.courseList.id=:course_list_id where a.id = :id1";
						sessionFactory.getCurrentSession().createQuery(sql)
						.setInteger("course_list_id", vcl.getId())
						.setInteger("id1", grIe.getId())
						.executeUpdate();
					}
					if (course_id != -1) {
						sql = "update GradeReportDetail a set a.course.id=:courseid where a.id = :id1";
						sessionFactory.getCurrentSession().createQuery(sql)
						.setLong("courseid", vco.getId())
						.setInteger("id1", grIe.getId())
						.executeUpdate();
					}
					
					if (forWhat.equalsIgnoreCase("download")) {
						sql = "ALTER TABLE ved_grade_report_detail ENABLE TRIGGER insert_ved_grade_report_detailt";
						sessionFactory.getCurrentSession().createSQLQuery(sql)
								.executeUpdate();
					}
					if (forWhat.equalsIgnoreCase("upload")) {
						objView = ssv.setId_vrs_sync_itemsLastID(objView,
								computer_id);
					}
					fBacK = objView.toString();
				}
			}

		} catch (Exception ex) {
			cf.viewAlert("error saveInTable_ved_grade_report_detail "
					+ ex.getMessage());
		}
		return fBacK;
	}
}
