package verse.sync.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import verse.academic.model.ved_assignments;
import verse.academic.model.ved_attendances;
import verse.academic.model.ved_candidate_metas;
import verse.academic.model.ved_candidates;
import verse.academic.model.ved_classes;
import verse.academic.model.ved_course_list;
import verse.academic.model.ved_course_schedule;
import verse.academic.model.ved_courses;
import verse.academic.model.ved_enrollments;
import verse.academic.model.ved_reports;
import verse.academic.model.ved_scores;
import verse.academic.model.ved_students;
import verse.commonClass.CommonFunction;
import verse.model.vrs_companies;
import verse.model.vrs_roles;
import verse.sync.model.vrs_sync_computerlist;
import verse.sync.model.vrs_sync_items;
import verse.sync.service.serviceSync;
import verse.sync.service.serviceSyncVRS;


@Service("serviceSyncAcademic")
public class serviceImpSyncAcademic implements  serviceSyncAcademic {
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	serviceSync ssc;	
	@Autowired
	private serviceSyncVRS sVRS;
	@Autowired
	private serviceSyncAcademic_Accounting sacc;
	
	CommonFunction cf = new CommonFunction();
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String getved_students_DAtaDownload(Long id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				ved_students.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			ved_students vst = (ved_students) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "ved_students");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vst.getCustId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("CustId_composite", vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("CustCompany_composite",
								vc.getId_composite());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vst.getSchoolId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				objColumn.put("created", vst.getCreated());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vst.getCreatedBy()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("CreatedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("CreatedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("modified", vst.getModified());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vst.getModifiedBy()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("ModifiedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("ModifiedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("status", vst.getStatus());
				objColumn.put("student_id", vst.getStudentNumber());
				objColumn.put("student_idn", vst.getStudentIdNumber());

				objColumn.put("id_composite", vst.getId_composite());
				obj.append("ved_students", objColumn);
				getBack = obj.toString();
			} catch (Exception ex) {
			}

		}
		return getBack;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String getved_students_DAta(Long id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				ved_students.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			ved_students vst = (ved_students) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "ved_students");
				obj.put("id_vrs_sync_itemsLOCAL", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vst.getCustId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("CustId_composite", vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("CustCompany_composite",
								vc.getId_composite());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vst.getSchoolId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				objColumn.put("created", vst.getCreated());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vst.getCreatedBy()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("CreatedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("CreatedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("modified", vst.getModified());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vst.getModifiedBy()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("ModifiedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("ModifiedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("status", vst.getStatus());
				objColumn.put("student_id", vst.getStudentNumber());
				objColumn.put("student_idn", vst.getStudentIdNumber());

				if (action.equalsIgnoreCase("edit"))
					objColumn.put("id_composite", vst.getId_composite());
				obj.append("ved_students", objColumn);
				getBack = obj.toString();
			} catch (Exception ex) {
			}

		}
		return getBack;
	}
	
	public String getved_assignments_DAta(Long id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				ved_assignments.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			ved_assignments vas = (ved_assignments) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "ved_assignments");
				obj.put("id_vrs_sync_itemsLOCAL", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vas.getSchoolId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_courses.class);
				criteria.add(Restrictions.eq("id", vas.getCourseId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					ved_courses vcor = (ved_courses) itMeta.next();
					objColumn.put("CourseId_composite", vcor.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId",
							vcor.getSchoolId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("CoursecompanyID_Composite",
								vc.getId_composite());
					}
				}

				objColumn.put("term", vas.getTerm());
				objColumn.put("type", vas.getType());
				objColumn.put("weight", vas.getWeight());
				objColumn.put("created", vas.getCreated());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vas.getCreatedBy()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("CreatedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("CreatedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("assignDate", vas.getAssignDate());
				objColumn.put("dueDate", vas.getDueDate());
				objColumn.put("modified", vas.getModified());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vas.getModifiedBy()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("ModifiedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("ModifiedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("status", vas.getStatus());
				objColumn.put("title", vas.getTitle());
				objColumn.put("description", vas.getDescription());
				if (action.equalsIgnoreCase("edit"))
					objColumn.put("id_composite", vas.getId_composite());
				obj.append("ved_assignments", objColumn);
				getBack = obj.toString();

			} catch (Exception ex) {
			}
		}
		return getBack;
	}
	
	public String getved_assignments_DAtaDownload(Long id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				ved_assignments.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			ved_assignments vas = (ved_assignments) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "ved_assignments");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vas.getSchoolId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_courses.class);
				criteria.add(Restrictions.eq("id", vas.getCourseId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					ved_courses vcor = (ved_courses) itMeta.next();
					objColumn.put("CourseId_composite", vcor.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId",
							vcor.getSchoolId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("CoursecompanyID_Composite",
								vc.getId_composite());
					}
				}

				objColumn.put("term", vas.getTerm());
				objColumn.put("type", vas.getType());
				objColumn.put("weight", vas.getWeight());
				objColumn.put("created", vas.getCreated());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vas.getCreatedBy()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("CreatedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("CreatedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("assignDate", vas.getAssignDate());
				objColumn.put("dueDate", vas.getDueDate());
				objColumn.put("modified", vas.getModified());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vas.getModifiedBy()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("ModifiedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("ModifiedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("status", vas.getStatus());
				objColumn.put("title", vas.getTitle());
				objColumn.put("description", vas.getDescription());
				objColumn.put("id_composite", vas.getId_composite());
				obj.append("ved_assignments", objColumn);
				getBack = obj.toString();

			} catch (Exception ex) {
			}
		}
		return getBack;
	}
	
	public String getved_attendances_DAta(Long id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				ved_attendances.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			ved_attendances vat = (ved_attendances) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "ved_attendances");
				obj.put("id_vrs_sync_itemsLOCAL", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vat.getSchoolId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_enrollments.class);
				criteria.add(Restrictions.eq("enrollmentId",
						vat.getEnrollmentId()));
				itMeta = criteria.list().iterator();
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

				objColumn.put("attendanceDate", vat.getAttendanceDate());
				objColumn.put("attendanceEndDate", vat.getAttendanceEndDate());
				objColumn.put("attendanceType", vat.getAttendanceType());
				objColumn.put("attendanceNote", vat.getAttendanceNote());
				objColumn.put("attendanceCreated", vat.getAttendanceCreated());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId",
						vat.getAttendanceCreatedBy()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("CreatedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("CreatedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn
						.put("attendanceModified", vat.getAttendanceModified());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId",
						vat.getAttendanceModifiedBy()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("ModifiedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("ModifiedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("attendanceStatus", vat.getAttendanceStatus());
				objColumn.put("attendanceTerm", vat.getAttendanceTerm());
				if (action.equalsIgnoreCase("edit"))
					objColumn.put("id_composite", vat.getId_composite());

				obj.append("ved_attendances", objColumn);
				getBack = obj.toString();

			} catch (Exception ex) {
			}
		}
		return getBack;
	}

	public String getved_scores_DAtaDownload(Long id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				ved_scores.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			ved_scores vsco = (ved_scores) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "ved_scores");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vsco.getSchoolId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_enrollments.class);
				criteria.add(Restrictions.eq("enrollmentId",
						vsco.getEnrollmentId()));
				itMeta = criteria.list().iterator();
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

				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_assignments.class);
				criteria.add(Restrictions.eq("id", vsco.getAssignmentId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					ved_assignments vass = (ved_assignments) itMeta.next();
					objColumn.put("AssignmentId_Composite",
							vass.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId",
							vass.getSchoolId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("AssignmentcompanyID_Composite",
								vc.getId_composite());
					}
				}

				objColumn.put("score", vsco.getScore());
				objColumn.put("received", vsco.getReceived());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vsco.getReceivedBy()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("ReceivedByid_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("ReceivedByCompanyID_composite",
								vc.getId_composite());
					}
				}

				objColumn.put("modified", vsco.getModified());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vsco.getModifiedBy()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("ModifiedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("ModifiedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("status", vsco.getStatus());
				objColumn.put("id_composite", vsco.getId_composite());
				obj.append("ved_scores", objColumn);
				getBack = obj.toString();

			} catch (Exception ex) {
			}
		}

		return getBack;
	}
	
	public String getved_scores_DAta(Long id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				ved_scores.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			ved_scores vsco = (ved_scores) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "ved_scores");
				obj.put("id_vrs_sync_itemsLOCAL", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vsco.getSchoolId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_enrollments.class);
				criteria.add(Restrictions.eq("enrollmentId",
						vsco.getEnrollmentId()));
				itMeta = criteria.list().iterator();
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

				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_assignments.class);
				criteria.add(Restrictions.eq("id", vsco.getAssignmentId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					ved_assignments vass = (ved_assignments) itMeta.next();
					objColumn.put("AssignmentId_Composite",
							vass.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId",
							vass.getSchoolId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("AssignmentcompanyID_Composite",
								vc.getId_composite());
					}
				}

				objColumn.put("score", vsco.getScore());
				objColumn.put("received", vsco.getReceived());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vsco.getReceivedBy()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("ReceivedByid_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("ReceivedByCompanyID_composite",
								vc.getId_composite());
					}
				}

				objColumn.put("modified", vsco.getModified());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vsco.getModifiedBy()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("ModifiedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("ModifiedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("status", vsco.getStatus());
				if (action.equalsIgnoreCase("edit"))
					objColumn.put("id_composite", vsco.getId_composite());
				obj.append("ved_scores", objColumn);
				getBack = obj.toString();

			} catch (Exception ex) {
			}
		}
		return getBack;
	}
	

	public String getved_reports_DAtaDownload(Long id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";

		String sql = "select school_id,enrollment_id,term,scores,avg_total_score,comment,attendance"
				+ " ,created,created_by,"
				+ " modified,modified_by,status,id_composite "
				+ " from vw_ved_reports where id=:id";
		Iterator it = sessionFactory.getCurrentSession().createSQLQuery(sql)
				.setLong("id", id_table).list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			Object[] objved_reports = (Object[]) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "ved_reports");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("companyId",
						Integer.valueOf(objved_reports[0].toString())));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_enrollments.class);
				criteria.add(Restrictions.eq("enrollmentId",
						Long.valueOf(objved_reports[1].toString())));
				itMeta = criteria.list().iterator();
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

				objColumn.put("term", objved_reports[2]);
				objColumn.put("scores", objved_reports[3]);
				objColumn.put("avgTotalScore", objved_reports[4]);
				objColumn.put("comment", objved_reports[5]);
				objColumn.put("attendance", objved_reports[6]);
				objColumn.put("created", objved_reports[7]);

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId",
						Integer.valueOf(objved_reports[8].toString())));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("CreatedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("CreatedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("modified", objved_reports[9]);

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId",
						Integer.valueOf(objved_reports[10].toString())));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("ModifiedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("ModifiedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("status", objved_reports[11]);

				objColumn.put("id_composite", objved_reports[12]);
				obj.append("ved_reports", objColumn);
				getBack = obj.toString();

			} catch (Exception ex) {
			}
		}
		return getBack;
	}
	

	public String getved_reports_DAta(Long id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		String sql = "select school_id,enrollment_id,term,scores,avg_total_score,comment,"
				+ " attendance,created,created_by,"
				+ "modified,modified_by,status,id_composite "
				+ " from vw_ved_reports where id=:id";
		Iterator it = sessionFactory.getCurrentSession().createSQLQuery(sql)
				.setLong("id", id_table).list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			Object[] objved_reports = (Object[]) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "ved_reports");
				obj.put("id_vrs_sync_itemsLOCAL", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("companyId",
						Integer.valueOf(objved_reports[0].toString())));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_enrollments.class);
				criteria.add(Restrictions.eq("enrollmentId",
						Long.valueOf(objved_reports[1].toString())));
				itMeta = criteria.list().iterator();
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

				objColumn.put("term", objved_reports[2]);
				objColumn.put("scores", objved_reports[3]);
				objColumn.put("avgTotalScore", objved_reports[4]);
				objColumn.put("comment", objved_reports[5]);
				objColumn.put("attendance", objved_reports[6]);
				objColumn.put("created", objved_reports[7]);

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId",
						Integer.valueOf(objved_reports[8].toString())));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("CreatedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("CreatedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("modified", objved_reports[9]);

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId",
						Integer.valueOf(objved_reports[10].toString())));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("ModifiedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("ModifiedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("status", objved_reports[11]);

				if (action.equalsIgnoreCase("edit"))
					objColumn.put("id_composite", objved_reports[12]);
				obj.append("ved_reports", objColumn);
				getBack = obj.toString();

			} catch (Exception ex) {
			}
		}
		return getBack;
	}

	
	public String getved_enrollments_DAtaDownload(Long id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";

		String sql = "select school_id,student_id,class_id,course_id,advisor"
				+ ",attendance,created,created_by,modified,modified_by,status"
				+ ",id_composite  from vw_ved_enrollments where id=:id";
		Iterator it = sessionFactory.getCurrentSession().createSQLQuery(sql)
				.setLong("id", id_table).list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			Object[] objved_enrollments = (Object[]) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "ved_enrollments");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("companyId",
						Integer.valueOf(objved_enrollments[0].toString())));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_students.class);
				criteria.add(Restrictions.eq("studentId",
						Long.valueOf(objved_enrollments[1].toString())));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					ved_students vst = (ved_students) itMeta.next();
					objColumn.put("studentID_Composite", vst.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vst.getSchoolId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("studentCompany_Composite",
								vc.getId_composite());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_classes.class);
				criteria.add(Restrictions.eq("id",
						Long.valueOf(objved_enrollments[2].toString())));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					ved_classes vc = (ved_classes) itMeta.next();
					objColumn.put("ClassId_Composite", vc.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vc.getSchool_id()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vcom = (vrs_companies) itMeta.next();
						objColumn.put("ClasscompanyID_Composite",
								vcom.getId_composite());
					}
				}

				try {
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_courses.class);
					criteria.add(Restrictions.eq("id",
							Long.valueOf(objved_enrollments[3].toString())));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						ved_courses vcor = (ved_courses) itMeta.next();
						objColumn.put("CourseId_composite",
								vcor.getId_composite());
						criteria = sessionFactory.getCurrentSession()
								.createCriteria(vrs_companies.class);
						criteria.add(Restrictions.eq("companyId",
								vcor.getSchoolId()));
						itMeta = criteria.list().iterator();
						if (itMeta.hasNext()) {
							vrs_companies vc = (vrs_companies) itMeta.next();
							objColumn.put("CoursecompanyID_Composite",
									vc.getId_composite());
						}
					}
				} catch (Exception ex) {
				}

				objColumn.put("advisor", objved_enrollments[4]);
				objColumn.put("attendance", objved_enrollments[5]);
				objColumn.put("created", objved_enrollments[6]);

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId",
						Integer.valueOf(objved_enrollments[7].toString())));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("CreatedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("CreatedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("modified", objved_enrollments[8]);

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId",
						Integer.valueOf(objved_enrollments[9].toString())));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("ModifiedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("ModifiedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("status", objved_enrollments[10]);

				objColumn.put("id_composite", objved_enrollments[11]);
				obj.append("ved_enrollments", objColumn);
				getBack = obj.toString();
			} catch (Exception ex) {
			}
		}
		return getBack;
	}

	public String getved_enrollments_DAta(Long id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		String sql = "select school_id,student_id,class_id,course_id,advisor,attendance,created, "
				+ "created_by,modified,modified_by,status,id_composite  "
				+ " from vw_ved_enrollments where id=:id";

		Iterator it = sessionFactory.getCurrentSession().createSQLQuery(sql)
				.setLong("id", id_table).list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			Object[] objved_enrollments = (Object[]) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "ved_enrollments");
				obj.put("id_vrs_sync_itemsLOCAL", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("companyId",
						Integer.valueOf(objved_enrollments[0].toString())));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_students.class);
				criteria.add(Restrictions.eq("studentId",
						Long.valueOf(objved_enrollments[1].toString())));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					ved_students vst = (ved_students) itMeta.next();
					objColumn.put("studentID_Composite", vst.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vst.getSchoolId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("studentCompany_Composite",
								vc.getId_composite());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_classes.class);
				criteria.add(Restrictions.eq("id",
						Long.valueOf(objved_enrollments[2].toString())));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					ved_classes vc = (ved_classes) itMeta.next();
					objColumn.put("ClassId_Composite", vc.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vc.getSchool_id()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vcom = (vrs_companies) itMeta.next();
						objColumn.put("ClasscompanyID_Composite",
								vcom.getId_composite());
					}
				}

				try {
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_courses.class);
					criteria.add(Restrictions.eq("id",
							Long.valueOf(objved_enrollments[3].toString())));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						ved_courses vcor = (ved_courses) itMeta.next();
						objColumn.put("CourseId_composite",
								vcor.getId_composite());
						criteria = sessionFactory.getCurrentSession()
								.createCriteria(vrs_companies.class);
						criteria.add(Restrictions.eq("companyId",
								vcor.getSchoolId()));
						itMeta = criteria.list().iterator();
						if (itMeta.hasNext()) {
							vrs_companies vc = (vrs_companies) itMeta.next();
							objColumn.put("CoursecompanyID_Composite",
									vc.getId_composite());
						}
					}
				} catch (Exception ex) {
				}

				objColumn.put("advisor", objved_enrollments[4]);

				objColumn.put("attendance", objved_enrollments[5]);

				objColumn.put("created", objved_enrollments[6]);

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId",
						Integer.valueOf(objved_enrollments[7].toString())));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("CreatedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("CreatedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("modified", objved_enrollments[8]);

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId",
						Integer.valueOf(objved_enrollments[9].toString())));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("ModifiedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("ModifiedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("status", objved_enrollments[10]);

				if (action.equalsIgnoreCase("edit"))
					objColumn.put("id_composite", objved_enrollments[11]);
				obj.append("ved_enrollments", objColumn);
				getBack = obj.toString();
			} catch (Exception ex) {
			}
		}
		return getBack;
	}


	public String getved_courses_DAtaDownload(Long id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				ved_courses.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			ved_courses vcou = (ved_courses) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "ved_courses");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vcou.getSchoolId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_classes.class);
				criteria.add(Restrictions.eq("id", vcou.getClassId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					ved_classes vc = (ved_classes) itMeta.next();
					objColumn.put("ClassId_Composite", vc.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vc.getSchool_id()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vcom = (vrs_companies) itMeta.next();
						objColumn.put("ClasscompanyID_Composite",
								vcom.getId_composite());
					}
				}

				objColumn.put("description", vcou.getDescription());
				objColumn.put("grade", vcou.getGrade());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vcou.getInstructor()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("instructor_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("instructor_id_compositeCompany",
								vc.getId_composite());
					}
				}

				try {
					objColumn.put("avgScore", vcou.getAvgScore());
				} catch (Exception ex) {
					objColumn.put("avgScore", "");
				}

				objColumn.put("created", vcou.getCreated());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vcou.getCreatedBy()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("CreatedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("CreatedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("modified", vcou.getModified());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vcou.getModifiedBy()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("ModifiedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("ModifiedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("status", vcou.getStatus());

				String sql = "select id_composite from ved_course_list where id=:id";
				itMeta = sessionFactory.getCurrentSession().createSQLQuery(sql)

				.setInteger("id", vcou.getId_ved_course_list())

				.list().iterator();
				if (itMeta.hasNext()) {
					objColumn.put("idved_course_list_composite", itMeta.next());
				}

				objColumn.put("id_composite", vcou.getId_composite());
				obj.append("ved_courses", objColumn);
				getBack = obj.toString();

			} catch (Exception ex) {
			}
		}

		return getBack;
	}


	public String getved_courses_DAta(Long id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				ved_courses.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			ved_courses vcou = (ved_courses) it.next();
			JSONObject obj = new JSONObject();
			try {

				obj.put("action", action);
				obj.put("tableName", "ved_courses");
				obj.put("id_vrs_sync_itemsLOCAL", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vcou.getSchoolId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_classes.class);
				criteria.add(Restrictions.eq("id", vcou.getClassId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					ved_classes vc = (ved_classes) itMeta.next();
					objColumn.put("ClassId_Composite", vc.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vc.getSchool_id()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vcom = (vrs_companies) itMeta.next();
						objColumn.put("ClasscompanyID_Composite",
								vcom.getId_composite());
					}
				}

				objColumn.put("description", vcou.getDescription());
				objColumn.put("grade", vcou.getGrade());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vcou.getInstructor()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("instructor_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("instructor_id_compositeCompany",
								vc.getId_composite());
					}
				}

				try {
					objColumn.put("avgScore", vcou.getAvgScore());
				} catch (Exception ex) {
					objColumn.put("avgScore", "");
				}
				objColumn.put("created", vcou.getCreated());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vcou.getCreatedBy()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("CreatedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("CreatedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("modified", vcou.getModified());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vcou.getModifiedBy()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("ModifiedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("ModifiedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}
				objColumn.put("status", vcou.getStatus());

				String sql = "select id_composite from ved_course_list where id=:id";
				itMeta = sessionFactory.getCurrentSession().createSQLQuery(sql)

				.setInteger("id", vcou.getId_ved_course_list())

				.list().iterator();

				if (itMeta.hasNext()) {
					objColumn.put("idved_course_list_composite", itMeta.next());
				}

				if (action.equalsIgnoreCase("edit"))
					objColumn.put("id_composite", vcou.getId_composite());
				obj.append("ved_courses", objColumn);
				getBack = obj.toString();

			} catch (Exception ex) {
			}
		}
		return getBack;
	}

	public String getved_course_schedule_DAtaDownload(int id_table,
			String action, int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				ved_course_schedule.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			ved_course_schedule vcsch = (ved_course_schedule) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "ved_course_schedule");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_courses.class);
				criteria.add(Restrictions.eq("id", vcsch.getCourseId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					ved_courses vcor = (ved_courses) itMeta.next();
					objColumn.put("CourseId_composite", vcor.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId",
							vcor.getSchoolId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("CoursecompanyID_Composite",
								vc.getId_composite());
					}
				}

				objColumn.put("days", vcsch.getDays());
				objColumn.put("startTime", vcsch.getStartTime());
				objColumn.put("endTime", vcsch.getEndTime());
				objColumn.put("location", vcsch.getLocation());
				objColumn.put("notes", vcsch.getNotes());
				if (vcsch.getTestTime() == null)
					objColumn.put("testTime", "");
				else
					objColumn.put("testTime", vcsch.getTestTime());

				objColumn.put("created", vcsch.getCreated());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vcsch.getCreatedBy()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("CreatedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("CreatedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("modified", vcsch.getModified());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vcsch.getModifiedBy()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("ModifiedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("ModifiedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("id_composite", vcsch.getId_composite());
				obj.append("ved_course_schedule", objColumn);
				getBack = obj.toString();
			} catch (Exception ex) {
			}
		}

		return getBack;
	}
	

	public String getved_course_schedule_DAta(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				ved_course_schedule.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			ved_course_schedule vcsch = (ved_course_schedule) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "ved_course_schedule");
				obj.put("id_vrs_sync_itemsLOCAL", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_courses.class);
				criteria.add(Restrictions.eq("id", vcsch.getCourseId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					ved_courses vcor = (ved_courses) itMeta.next();
					objColumn.put("CourseId_composite", vcor.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId",
							vcor.getSchoolId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("CoursecompanyID_Composite",
								vc.getId_composite());
					}
				}

				objColumn.put("days", vcsch.getDays());
				objColumn.put("startTime", vcsch.getStartTime());
				objColumn.put("endTime", vcsch.getEndTime());
				objColumn.put("location", vcsch.getLocation());
				objColumn.put("notes", vcsch.getNotes());
				if (vcsch.getTestTime() == null)
					objColumn.put("testTime", "");
				else
					objColumn.put("testTime", vcsch.getTestTime());
				objColumn.put("created", vcsch.getCreated());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vcsch.getCreatedBy()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("CreatedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("CreatedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("modified", vcsch.getModified());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vcsch.getModifiedBy()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("ModifiedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("ModifiedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				if (action.equalsIgnoreCase("edit"))
					objColumn.put("id_composite", vcsch.getId_composite());
				obj.append("ved_course_schedule", objColumn);
				getBack = obj.toString();
			} catch (Exception ex) {
			}
		}
		return getBack;
	}
	

	public String getved_course_list_DAta(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				ved_course_list.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			ved_course_list vcli = (ved_course_list) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "ved_course_list");
				obj.put("id_vrs_sync_itemsLOCAL", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				objColumn.put("title", vcli.getTitle());
				objColumn.put("description", vcli.getDescription());
				objColumn.put("level", vcli.getLevel());
				objColumn.put("grade", vcli.getGrade());
				objColumn.put("created", vcli.getCreated());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vcli.getCreated_by()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("CreatedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("CreatedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("modified", vcli.getModified());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vcli.getModified_by()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("ModifiedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("ModifiedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("status", vcli.getStatus());
				String prerequisite = "";
				if (vcli.getPrerequisite() != null)
					prerequisite = vcli.getPrerequisite();
				objColumn.put("prerequisite", prerequisite);

				if (action.equalsIgnoreCase("edit"))
					objColumn.put("id_composite", vcli.getId_composite());
				obj.append("ved_course_list", objColumn);
				getBack = obj.toString();

			} catch (Exception ex) {
				cf.viewAlert("error in getved_course_list_DAtaDownload "
						+ ex.getMessage());
			}
		}
		return getBack;
	}
	
	public String getved_course_list_DAtaDownload(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				ved_course_list.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			ved_course_list vcli = (ved_course_list) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "ved_course_list");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();
				objColumn.put("title", vcli.getTitle());
				objColumn.put("description", vcli.getDescription());
				objColumn.put("level", vcli.getLevel());
				objColumn.put("grade", vcli.getGrade());
				objColumn.put("created", vcli.getCreated());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vcli.getCreated_by()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("CreatedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("CreatedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("modified", vcli.getModified());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vcli.getModified_by()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("ModifiedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("ModifiedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("status", vcli.getStatus());
				String prerequisite = "";
				if (vcli.getPrerequisite() != null)
					prerequisite = vcli.getPrerequisite();
				objColumn.put("prerequisite", prerequisite);
				objColumn.put("id_composite", vcli.getId_composite());
				obj.append("ved_course_list", objColumn);
				getBack = obj.toString();

			} catch (Exception ex) {
				cf.viewAlert("error in getved_course_list_DAtaDownload "
						+ ex.getMessage());
			}
		}
		return getBack;
	}


	public String getved_classes_DAta(long id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				ved_classes.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			ved_classes vcs = (ved_classes) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "ved_classes");
				obj.put("id_vrs_sync_itemsLOCAL", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vcs.getSchool_id()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				objColumn.put("name", vcs.getName());
				objColumn.put("grade", vcs.getGrade());
				objColumn.put("year", vcs.getYear());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vcs.getAdvisor()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("AdvisorID_composite", vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("AdvisorCompany_composite",
								vc.getId_composite());
					}
				}

				objColumn.put("created", vcs.getCreated());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vcs.getCreated_by()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("CreatedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("CreatedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("modified", vcs.getModified());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vcs.getModified_by()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("ModifiedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("ModifiedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}
				objColumn.put("status", vcs.getStatus());
				if (action.equalsIgnoreCase("edit"))
					objColumn.put("id_composite", vcs.getId_composite());
				obj.append("ved_classes", objColumn);
				getBack = obj.toString();
			} catch (Exception ex) {
			}
		}
		return getBack;
	}
	

	public String getved_attendances_DAtaDownload(Long id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				ved_attendances.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			ved_attendances vat = (ved_attendances) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "ved_attendances");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vat.getSchoolId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_enrollments.class);
				criteria.add(Restrictions.eq("enrollmentId",
						vat.getEnrollmentId()));
				itMeta = criteria.list().iterator();
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

				objColumn.put("attendanceDate", vat.getAttendanceDate());
				objColumn.put("attendanceEndDate", vat.getAttendanceEndDate());
				objColumn.put("attendanceType", vat.getAttendanceType());
				objColumn.put("attendanceNote", vat.getAttendanceNote());
				objColumn.put("attendanceCreated", vat.getAttendanceCreated());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId",
						vat.getAttendanceCreatedBy()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("CreatedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("CreatedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn
						.put("attendanceModified", vat.getAttendanceModified());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId",
						vat.getAttendanceModifiedBy()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("ModifiedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("ModifiedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("attendanceStatus", vat.getAttendanceStatus());
				objColumn.put("attendanceTerm", vat.getAttendanceTerm());
				objColumn.put("id_composite", vat.getId_composite());
				obj.append("ved_attendances", objColumn);
				getBack = obj.toString();

			} catch (Exception ex) {
			}
		}
		return getBack;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String getved_candidates_DAta(Long id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				ved_candidates.class);
		criteria.add(Restrictions.eq("candidateId", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			ved_candidates vca = (ved_candidates) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "ved_candidates");
				obj.put("id_vrs_sync_itemsLOCAL", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vca.getCustId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("CustId_composite", vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("CustCompany_composite",
								vc.getId_composite());
					}
				}
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vca.getSchoolId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				objColumn.put("created", vca.getCreated());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vca.getCreatedBy()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("CreatedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("CreatedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("modified", vca.getModified());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vca.getModifiedBy()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("ModifiedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("ModifiedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("status", vca.getStatus());
				if (action.equalsIgnoreCase("edit"))
					objColumn.put("id_composite", vca.getId_composite());

				obj.append("ved_candidates", objColumn);

				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_candidate_metas.class);
				criteria.add(Restrictions.eq("candidateId", id_table));
				it = criteria.list().iterator();
				while (it.hasNext()) {
					ved_candidate_metas vcm = (ved_candidate_metas) it.next();
					objColumn = new JSONObject();
					objColumn.put("candidateMetasKey",
							vcm.getCandidateMetasKey());
					objColumn.put("candidateMetasValue",
							vcm.getCandidateMetasValue());
					objColumn.put("created", vcm.getCreated());
					objColumn.put("modified", vcm.getModified());
					obj.append("ved_candidate_metas", objColumn);
				}
				getBack = obj.toString();

			} catch (Exception ex) {
			}
		}
		return getBack;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String getved_candidates_DAtaDownload(Long id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				ved_candidates.class);
		criteria.add(Restrictions.eq("candidateId", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			ved_candidates vca = (ved_candidates) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "ved_candidates");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vca.getCustId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("CustId_composite", vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("CustCompany_composite",
								vc.getId_composite());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vca.getSchoolId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				objColumn.put("created", vca.getCreated());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vca.getCreatedBy()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("CreatedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("CreatedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("modified", vca.getModified());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vca.getModifiedBy()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("ModifiedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("ModifiedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}
				objColumn.put("status", vca.getStatus());
				objColumn.put("id_composite", vca.getId_composite());

				obj.append("ved_candidates", objColumn);

				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_candidate_metas.class);
				criteria.add(Restrictions.eq("candidateId", id_table));
				it = criteria.list().iterator();
				while (it.hasNext()) {
					ved_candidate_metas vcm = (ved_candidate_metas) it.next();
					objColumn = new JSONObject();
					objColumn.put("candidateMetasKey",
							vcm.getCandidateMetasKey());
					objColumn.put("candidateMetasValue",
							vcm.getCandidateMetasValue());
					objColumn.put("created", vcm.getCreated());
					objColumn.put("modified", vcm.getModified());
					obj.append("ved_candidate_metas", objColumn);
				}
				getBack = obj.toString();

			} catch (Exception ex) {
			}
		}
		return getBack;
	}

	public String getved_classes_DAtaDownload(long id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				ved_classes.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			ved_classes vcs = (ved_classes) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "ved_classes");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vcs.getSchool_id()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				objColumn.put("name", vcs.getName());
				objColumn.put("grade", vcs.getGrade());
				objColumn.put("year", vcs.getYear());
				objColumn.put("year", vcs.getYear());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vcs.getAdvisor()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("AdvisorID_composite", vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("AdvisorCompany_composite",
								vc.getId_composite());
					}
				}

				objColumn.put("created", vcs.getCreated());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vcs.getCreated_by()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("CreatedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("CreatedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}

				objColumn.put("modified", vcs.getModified());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vcs.getModified_by()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("ModifiedBy_id_composite",
							vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("ModifiedBy_id_compositeCompany",
								vc.getId_composite());
					}
				}
				objColumn.put("status", vcs.getStatus());

				objColumn.put("id_composite", vcs.getId_composite());
				obj.append("ved_classes", objColumn);
				getBack = obj.toString();
			} catch (Exception ex) {
			}
		}
		return getBack;
	}

	public String saveInTable_ved_scores(String data, int computer_id) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL",
					obj.get("id_vrs_sync_itemsLOCAL").toString());

			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("ved_scores").toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			String sql = "";
			if (action.equalsIgnoreCase("add")) {
				ved_scores vsc = new ved_scores();
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					vsc.setSchoolId(vcom.getCompanyId());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"enrollmentcompanyID_Composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_enrollments.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"enrollmentID_Composite").toString())));
					criteria.add(Restrictions.eq("schoolId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_enrollments ven = (ved_enrollments) it.next();
						vsc.setEnrollmentId(ven.getEnrollmentId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"AssignmentcompanyID_Composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_assignments.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"AssignmentId_Composite").toString())));
					criteria.add(Restrictions.eq("schoolId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_assignments vass = (ved_assignments) it.next();
						vsc.setAssignmentId(vass.getId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ReceivedByCompanyID_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ReceivedByid_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vsc.setReceivedBy(vr.getRoleId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vsc.setModifiedBy(vr.getRoleId());
					}
				}

				vsc.setScore(Integer.valueOf(dataVrs_users.get("score")
						.toString()));
				vsc.setReceived(ssc.getTimeStamp(dataVrs_users.get("received")
						.toString()));
				vsc.setModified(ssc.getTimeStamp(dataVrs_users.get("modified")
						.toString()));
				vsc.setStatus(Integer.valueOf(dataVrs_users.get("status")
						.toString()));

				sessionFactory.getCurrentSession().save(vsc);
				sessionFactory.getCurrentSession().clear();

				sql = "SELECT last_value from vrs_sync_items_id_seq";
				it = sessionFactory.getCurrentSession().createSQLQuery(sql)
						.list().iterator();
				int id_vrs_sync_items = 0;
				if (it.hasNext()) {
					id_vrs_sync_items = Integer.valueOf(it.next().toString());
					vrs_sync_computerlist vscc = new vrs_sync_computerlist();
					vscc.setId_computer(computer_id);
					vscc.setId_vrs_sync_items(id_vrs_sync_items);
					Timestamp tstamp = new Timestamp(new Date().getTime());
					vscc.setTime_created(tstamp);
					sessionFactory.getCurrentSession().save(vscc);
					objView.put("id_vrs_sync_items", id_vrs_sync_items);
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_scores.class);
				criteria.add(Restrictions.eq("id", vsc.getId()));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					ved_scores vsco = (ved_scores) it.next();
					objView.put("id_CompositeFromServer",
							vsco.getId_composite());
					getBack = objView.toString();
				}

			} else if (action.equalsIgnoreCase("edit")) {
				Long idTable = Long.valueOf(-1);
				int schoolID = -1;
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					schoolID = vcom.getCompanyId();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_scores.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));
					criteria.add(Restrictions.eq("schoolId", schoolID));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_scores vsco = (ved_scores) it.next();
						idTable = vsco.getId();
					}
				}

				Long enrollmentID = Long.valueOf(-1);
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"enrollmentcompanyID_Composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_enrollments.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"enrollmentID_Composite").toString())));
					criteria.add(Restrictions.eq("schoolId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_enrollments ven = (ved_enrollments) it.next();
						enrollmentID = ven.getEnrollmentId();
					}
				}

				Long assignmentsID = Long.valueOf(-1);
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"AssignmentcompanyID_Composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_assignments.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"AssignmentId_Composite").toString())));
					criteria.add(Restrictions.eq("schoolId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_assignments vass = (ved_assignments) it.next();
						assignmentsID = vass.getId();
					}
				}

				int receivedBY = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ReceivedByCompanyID_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ReceivedByid_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						receivedBY = vr.getRoleId();
					}
				}

				int modifiedBY = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						modifiedBY = vr.getRoleId();
					}
				}

				sql = "update ved_scores set schoolId=:schoolId, "
						+ " enrollmentId=:enrollmentId, assignmentId=:assignmentId, score=:score,"
						+ " received=:received,receivedBy=:receivedBy,modified=:modified,"
						+ " modifiedBy=:modifiedBy,status=:status "
						+ "  where id =:id";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)
						.setLong("id", idTable)
						.setInteger("schoolId", schoolID)
						.setLong("enrollmentId", enrollmentID)
						.setLong("assignmentId", assignmentsID)
						.setInteger(
								"score",
								Integer.valueOf(dataVrs_users.get("score")
										.toString()))
						.setTimestamp(
								"received",
								ssc.getTimeStamp(dataVrs_users.get("received")
										.toString()))
						.setInteger("receivedBy", receivedBY)
						.setTimestamp(
								"modified",
								ssc.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modifiedBy", modifiedBY)
						.setInteger(
								"status",
								Integer.valueOf(dataVrs_users.get("status")
										.toString()))

						.executeUpdate();
				sessionFactory.getCurrentSession().clear();

				sql = "SELECT last_value from vrs_sync_items_id_seq";
				it = sessionFactory.getCurrentSession().createSQLQuery(sql)
						.list().iterator();
				int id_vrs_sync_items = 0;
				if (it.hasNext()) {
					id_vrs_sync_items = Integer.valueOf(it.next().toString());
					vrs_sync_computerlist vsc = new vrs_sync_computerlist();
					vsc.setId_computer(computer_id);
					vsc.setId_vrs_sync_items(id_vrs_sync_items);
					Timestamp tstamp = new Timestamp(new Date().getTime());
					vsc.setTime_created(tstamp);
					sessionFactory.getCurrentSession().save(vsc);
					objView.put("id_vrs_sync_items", id_vrs_sync_items);
				}
				getBack = objView.toString();
			}
		} catch (Exception ex) {
			getBack += " error saveInTable_ved_scores " + ex.getMessage();
		}

		return getBack;
	}
	

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String saveInTable_ved_students(String data, int computer_id) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL",
					obj.get("id_vrs_sync_itemsLOCAL").toString());
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("ved_students")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			String sql = "";
			if (action.equalsIgnoreCase("add")) {
				ved_students vst = new ved_students();

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					vst.setSchoolId(vcom.getCompanyId());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CustCompany_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CustId_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vst.setCustId(vr.getRoleId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vst.setCreatedBy(vr.getRoleId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vst.setModifiedBy(vr.getRoleId());
					}
				}

				vst.setCreated(ssc.getTimeStamp(dataVrs_users.get("created")
						.toString()));
				vst.setModified(ssc.getTimeStamp(dataVrs_users.get("modified")
						.toString()));
				vst.setStatus(Integer.valueOf(dataVrs_users.get("status")
						.toString()));
				try {
					vst.setStudentNumber(dataVrs_users.get("student_id")
							.toString());
				} catch (Exception ex) {
				}
				try {
					vst.setStudentIdNumber(dataVrs_users.get("student_idn")
							.toString());
				} catch (Exception ex) {
				}

				sessionFactory.getCurrentSession().save(vst);
				sessionFactory.getCurrentSession().clear();

				sql = "SELECT last_value from vrs_sync_items_id_seq";
				it = sessionFactory.getCurrentSession().createSQLQuery(sql)
						.list().iterator();
				int id_vrs_sync_items = 0;
				if (it.hasNext()) {
					id_vrs_sync_items = Integer.valueOf(it.next().toString());
					vrs_sync_computerlist vsc = new vrs_sync_computerlist();
					vsc.setId_computer(computer_id);
					vsc.setId_vrs_sync_items(id_vrs_sync_items);
					Timestamp tstamp = new Timestamp(new Date().getTime());
					vsc.setTime_created(tstamp);
					sessionFactory.getCurrentSession().save(vsc);
					objView.put("id_vrs_sync_items", id_vrs_sync_items);
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_students.class);
				criteria.add(Restrictions.eq("id", vst.getStudentId()));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					ved_students vstu = (ved_students) it.next();
					objView.put("id_CompositeFromServer",
							vstu.getId_composite());
					getBack = objView.toString();
				}

			} else if (action.equalsIgnoreCase("edit")) {
				Long idTable = Long.valueOf(-1);
				int schooID = -1;
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					schooID = vcom.getCompanyId();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_students.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));
					criteria.add(Restrictions.eq("schoolId", schooID));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_students vst = (ved_students) it.next();
						idTable = vst.getStudentId();
					}
				}

				int custID = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CustCompany_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CustId_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						custID = vr.getRoleId();
					}
				}

				int createdBY = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						createdBY = vr.getRoleId();
					}
				}

				int modifiedBY = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						modifiedBY = vr.getRoleId();
					}
				}

				String student_id = "", student_idn = "";
				try {
					student_id = dataVrs_users.get("student_id").toString();
				} catch (Exception ex) {
				}
				try {
					student_idn = dataVrs_users.get("student_idn").toString();
				} catch (Exception ex) {
				}

				sql = "update ved_students set custId=:custId,schoolId=:schoolId,"
						+ " created=:created,createdBy=:createdBy,modified=:modified,"
						+ " modifiedBy=:modifiedBy,status=:status ,studentNumber=:studentNumber"
						+ " ,studentIdNumber=:studentIdNumber "
						+ "  where id=:id";

				sessionFactory
						.getCurrentSession()
						.createQuery(sql)
						.setLong("id", idTable)
						.setInteger("custId", custID)
						.setInteger("schoolId", schooID)
						.setTimestamp(
								"created",
								ssc.getTimeStamp(dataVrs_users.get("created")
										.toString()))
						.setInteger("createdBy", createdBY)
						.setTimestamp(
								"modified",
								ssc.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modifiedBy", modifiedBY)
						.setInteger(
								"status",
								Integer.valueOf(dataVrs_users.get("status")
										.toString()))
						.setString("studentNumber", student_id)
						.setString("studentIdNumber", student_id)

						.executeUpdate();
				sessionFactory.getCurrentSession().clear();

				sql = "SELECT last_value from vrs_sync_items_id_seq";
				it = sessionFactory.getCurrentSession().createSQLQuery(sql)
						.list().iterator();
				int id_vrs_sync_items = 0;
				if (it.hasNext()) {
					id_vrs_sync_items = Integer.valueOf(it.next().toString());
					vrs_sync_computerlist vsc = new vrs_sync_computerlist();
					vsc.setId_computer(computer_id);
					vsc.setId_vrs_sync_items(id_vrs_sync_items);
					Timestamp tstamp = new Timestamp(new Date().getTime());
					vsc.setTime_created(tstamp);
					sessionFactory.getCurrentSession().save(vsc);
					objView.put("id_vrs_sync_items", id_vrs_sync_items);
				}
				getBack = objView.toString();
			}
		} catch (Exception ex) {
			getBack += " error saveInTable_ved_students " + ex.getMessage();
		}

		return getBack;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void saveInTable_ved_studentsDownload(String data) {
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("ved_students")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			String sql = "";
			if (action.equalsIgnoreCase("add")) {
				ved_students vst = new ved_students();

				int school_id = -1;
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					school_id = vcom.getCompanyId();
					vst.setSchoolId(school_id);
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CustCompany_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CustId_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vst.setCustId(vr.getRoleId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vst.setCreatedBy(vr.getRoleId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vst.setModifiedBy(vr.getRoleId());
					}
				}

				vst.setCreated(ssc.getTimeStamp(dataVrs_users.get("created")
						.toString()));
				vst.setModified(ssc.getTimeStamp(dataVrs_users.get("modified")
						.toString()));
				vst.setStatus(Integer.valueOf(dataVrs_users.get("status")
						.toString()));

				try {
					vst.setStudentNumber(dataVrs_users.get("student_id")
							.toString());
				} catch (Exception ex) {
				}
				try {
					vst.setStudentIdNumber(dataVrs_users.get("student_idn")
							.toString());
				} catch (Exception ex) {
				}

				int id_compositeThis = Integer.valueOf(dataVrs_users.get(
						"id_composite").toString());
				vst.setId_composite(id_compositeThis);

				sql = "ALTER TABLE ved_students DISABLE TRIGGER insert_ved_studentst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sVRS.changeIdComposite_ved_candidates(id_compositeThis,
						school_id, "ved_students");
				sessionFactory.getCurrentSession().save(vst);
				sessionFactory.getCurrentSession().clear();

				sql = "ALTER TABLE ved_students ENABLE TRIGGER insert_ved_studentst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

			} else if (action.equalsIgnoreCase("edit")) {
				Long idTable = Long.valueOf(-1);
				int schooID = -1;
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					schooID = vcom.getCompanyId();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_students.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));
					criteria.add(Restrictions.eq("schoolId", schooID));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_students vst = (ved_students) it.next();
						idTable = vst.getStudentId();
					}
				}

				int custID = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CustCompany_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CustId_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						custID = vr.getRoleId();
					}
				}

				int createdBY = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						createdBY = vr.getRoleId();
					}
				}

				int modifiedBY = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						modifiedBY = vr.getRoleId();
					}
				}

				sql = "ALTER TABLE ved_students DISABLE TRIGGER insert_ved_studentst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();
				String student_id = "", student_idn = "";
				try {
					student_id = dataVrs_users.get("student_id").toString();
				} catch (Exception ex) {
				}
				try {
					student_idn = dataVrs_users.get("student_idn").toString();
				} catch (Exception ex) {
				}

				sql = "update ved_students set custId=:custId,schoolId=:schoolId,"
						+ " created=:created,createdBy=:createdBy,modified=:modified,"
						+ " modifiedBy=:modifiedBy,status=:status,studentNumber=:studentNumber"
						+ " ,studentIdNumber=:studentIdNumber "
						+ "  where id=:id";

				sessionFactory
						.getCurrentSession()
						.createQuery(sql)
						.setLong("id", idTable)
						.setInteger("custId", custID)
						.setInteger("schoolId", schooID)
						.setTimestamp(
								"created",
								ssc.getTimeStamp(dataVrs_users.get("created")
										.toString()))
						.setInteger("createdBy", createdBY)
						.setTimestamp(
								"modified",
								ssc.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modifiedBy", modifiedBY)
						.setInteger(
								"status",
								Integer.valueOf(dataVrs_users.get("status")
										.toString()))
						.setString("studentNumber", student_id)
						.setString("studentIdNumber", student_id)

						.executeUpdate();
				sessionFactory.getCurrentSession().clear();

				sql = "ALTER TABLE ved_students ENABLE TRIGGER insert_ved_studentst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();
			}
		} catch (Exception ex) {
			cf.viewAlert(" error saveInTable_ved_studentsDownload "
					+ ex.getMessage());
		}
	}
	
	public String saveInTable_ved_scoresDownload(String data) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("ved_scores").toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			String sql = "";
			if (action.equalsIgnoreCase("add")) {
				ved_scores vsc = new ved_scores();
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					vsc.setSchoolId(vcom.getCompanyId());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"enrollmentcompanyID_Composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_enrollments.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"enrollmentID_Composite").toString())));
					criteria.add(Restrictions.eq("schoolId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_enrollments ven = (ved_enrollments) it.next();
						vsc.setEnrollmentId(ven.getEnrollmentId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"AssignmentcompanyID_Composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_assignments.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"AssignmentId_Composite").toString())));
					criteria.add(Restrictions.eq("schoolId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_assignments vass = (ved_assignments) it.next();
						vsc.setAssignmentId(vass.getId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ReceivedByCompanyID_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ReceivedByid_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vsc.setReceivedBy(vr.getRoleId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vsc.setModifiedBy(vr.getRoleId());
					}
				}

				vsc.setScore(Integer.valueOf(dataVrs_users.get("score")
						.toString()));
				vsc.setReceived(ssc.getTimeStamp(dataVrs_users.get("received")
						.toString()));
				vsc.setModified(ssc.getTimeStamp(dataVrs_users.get("modified")
						.toString()));
				vsc.setStatus(Integer.valueOf(dataVrs_users.get("status")
						.toString()));
				vsc.setId_composite(Integer.valueOf(dataVrs_users.get(
						"id_composite").toString()));

				sql = "ALTER TABLE ved_scores DISABLE TRIGGER insert_ved_scorest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sessionFactory.getCurrentSession().save(vsc);
				sessionFactory.getCurrentSession().clear();

				sql = "ALTER TABLE ved_scores ENABLE TRIGGER insert_ved_scorest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

			} else if (action.equalsIgnoreCase("edit")) {
				Long idTable = Long.valueOf(-1);
				int schoolID = -1;
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					schoolID = vcom.getCompanyId();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_scores.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));
					criteria.add(Restrictions.eq("schoolId", schoolID));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_scores vsco = (ved_scores) it.next();
						idTable = vsco.getId();
					}
				}

				Long enrollmentID = Long.valueOf(-1);
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"enrollmentcompanyID_Composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_enrollments.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"enrollmentID_Composite").toString())));
					criteria.add(Restrictions.eq("schoolId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_enrollments ven = (ved_enrollments) it.next();
						enrollmentID = ven.getEnrollmentId();
					}
				}

				Long assignmentsID = Long.valueOf(-1);
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"AssignmentcompanyID_Composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_assignments.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"AssignmentId_Composite").toString())));
					criteria.add(Restrictions.eq("schoolId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_assignments vass = (ved_assignments) it.next();
						assignmentsID = vass.getId();
					}
				}

				int receivedBY = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ReceivedByCompanyID_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ReceivedByid_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						receivedBY = vr.getRoleId();
					}
				}

				int modifiedBY = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						modifiedBY = vr.getRoleId();
					}
				}

				sql = "ALTER TABLE ved_scores DISABLE TRIGGER insert_ved_scorest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sql = "update ved_scores set schoolId=:schoolId, "
						+ " enrollmentId=:enrollmentId, assignmentId=:assignmentId, score=:score,"
						+ " received=:received,receivedBy=:receivedBy,modified=:modified,"
						+ " modifiedBy=:modifiedBy,status=:status"
						+ "  where id =:id";

				sessionFactory
						.getCurrentSession()
						.createQuery(sql)
						.setLong("id", idTable)
						.setInteger("schoolId", schoolID)
						.setLong("enrollmentId", enrollmentID)
						.setLong("assignmentId", assignmentsID)
						.setInteger(
								"score",
								Integer.valueOf(dataVrs_users.get("score")
										.toString()))
						.setTimestamp(
								"received",
								ssc.getTimeStamp(dataVrs_users.get("received")
										.toString()))
						.setInteger("receivedBy", receivedBY)
						.setTimestamp(
								"modified",
								ssc.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modifiedBy", modifiedBY)
						.setInteger(
								"status",
								Integer.valueOf(dataVrs_users.get("status")
										.toString()))

						.executeUpdate();

				sql = "ALTER TABLE ved_scores ENABLE TRIGGER insert_ved_scorest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

			}
		} catch (Exception ex) {
			getBack += " error saveInTable_ved_scores " + ex.getMessage();
		}

		return getBack;
	}

	public String saveInTable_ved_reports(String data, int computer_id) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL",
					obj.get("id_vrs_sync_itemsLOCAL").toString());

			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("ved_reports").toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			String sql = "";
			if (action.equalsIgnoreCase("add")) {
				int schoolID = -1;
				Long idTAble = Long.valueOf(0);
				sql = "select nextval('vrs_reports_id_seq') as nextValue";
				Iterator it = sessionFactory.getCurrentSession()
						.createSQLQuery(sql).list().iterator();
				if (it.hasNext()) {
					idTAble = Long.valueOf(it.next().toString());
				}

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					schoolID = vcom.getCompanyId();
				}

				Long enrollment_id = Long.valueOf(-1);
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"enrollmentcompanyID_Composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_enrollments.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"enrollmentID_Composite").toString())));
					criteria.add(Restrictions.eq("schoolId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_enrollments ven = (ved_enrollments) it.next();
						enrollment_id = ven.getEnrollmentId();
					}
				}

				int createdBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						createdBy = vr.getRoleId();
					}
				}

				int modifiedBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						modifiedBy = vr.getRoleId();
					}
				}

				String attendance = "'"
						+ dataVrs_users.get("attendance").toString() + "'";
				String scores = "'" + dataVrs_users.get("scores").toString()
						+ "'";

				sql = " insert into ved_reports (id,school_id,enrollment_id,term,scores,avg_total_score,comment,"
						+ " attendance,created,created_by,"
						+ "modified,modified_by,status) values (:id,:school_id,:enrollment_id,:term,"
						+ scores
						+ ","
						+ ":avg_total_score,:comment,"
						+ " "
						+ attendance
						+ ",:created,:created_by,"
						+ ":modified,:modified_by,:status);";

				sessionFactory
						.getCurrentSession()
						.createSQLQuery(sql)
						.setLong("id", idTAble)
						.setInteger("school_id", schoolID)
						.setLong("enrollment_id", enrollment_id)
						.setString("term", dataVrs_users.get("term").toString())
						.setFloat(
								"avg_total_score",
								Float.valueOf(dataVrs_users
										.get("avgTotalScore").toString()))
						.setString("comment",
								dataVrs_users.get("comment").toString())
						.setTimestamp(
								"created",
								ssc.getTimeStamp(dataVrs_users.get("created")
										.toString()))
						.setInteger("created_by", createdBy)
						.setTimestamp(
								"modified",
								ssc.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modified_by", modifiedBy)
						.setInteger(
								"status",
								Integer.valueOf(dataVrs_users.get("status")
										.toString()))

						.executeUpdate();
				sessionFactory.getCurrentSession().clear();

				sql = "SELECT last_value from vrs_sync_items_id_seq";
				it = sessionFactory.getCurrentSession().createSQLQuery(sql)
						.list().iterator();
				int id_vrs_sync_items = 0;
				if (it.hasNext()) {
					id_vrs_sync_items = Integer.valueOf(it.next().toString());
					vrs_sync_computerlist vsc = new vrs_sync_computerlist();
					vsc.setId_computer(computer_id);
					vsc.setId_vrs_sync_items(id_vrs_sync_items);
					Timestamp tstamp = new Timestamp(new Date().getTime());
					vsc.setTime_created(tstamp);
					sessionFactory.getCurrentSession().save(vsc);
					objView.put("id_vrs_sync_items", id_vrs_sync_items);
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_reports.class);
				criteria.add(Restrictions.eq("id", idTAble));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					ved_reports vre = (ved_reports) it.next();
					objView.put("id_CompositeFromServer", vre.getId_composite());
					getBack = objView.toString();
				}
			} else if (action.equalsIgnoreCase("edit")) {
				int schoolID = -1;
				Long idTAble = Long.valueOf(0);

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					schoolID = vcom.getCompanyId();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_reports.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));
					criteria.add(Restrictions.eq("schoolId", schoolID));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_reports vre = (ved_reports) it.next();
						idTAble = vre.getId();
					}
				}

				Long enrollment_id = Long.valueOf(-1);
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"enrollmentcompanyID_Composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_enrollments.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"enrollmentID_Composite").toString())));
					criteria.add(Restrictions.eq("schoolId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_enrollments ven = (ved_enrollments) it.next();
						enrollment_id = ven.getEnrollmentId();
					}
				}

				int createdBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						createdBy = vr.getRoleId();
					}
				}

				int modifiedBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						modifiedBy = vr.getRoleId();
					}
				}

				String attendance = "'"
						+ dataVrs_users.get("attendance").toString() + "'";
				String scores = "'" + dataVrs_users.get("scores").toString()
						+ "'";

				sql = " update ved_reports set school_id=:school_id,"
						+ " enrollment_id=:enrollment_id,"
						+ " term=:term,scores="
						+ scores
						+ ",avg_total_score=:avg_total_score,"
						+ " comment=:comment,"
						+ " attendance="
						+ attendance
						+ " ,created=:created,"
						+ " created_by=:created_by,"
						+ " modified=:modified,modified_by=:modified_by,status=:status "
						+ " where id=:id";

				sessionFactory
						.getCurrentSession()
						.createSQLQuery(sql)
						.setLong("id", idTAble)
						.setInteger("school_id", schoolID)
						.setLong("enrollment_id", enrollment_id)
						.setString("term", dataVrs_users.get("term").toString())
						.setFloat(
								"avg_total_score",
								Float.valueOf(dataVrs_users
										.get("avgTotalScore").toString()))
						.setString("comment",
								dataVrs_users.get("comment").toString())
						.setTimestamp(
								"created",
								ssc.getTimeStamp(dataVrs_users.get("created")
										.toString()))
						.setInteger("created_by", createdBy)
						.setTimestamp(
								"modified",
								ssc.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modified_by", modifiedBy)
						.setInteger(
								"status",
								Integer.valueOf(dataVrs_users.get("status")
										.toString()))

						.executeUpdate();
				sessionFactory.getCurrentSession().clear();

				sql = "SELECT last_value from vrs_sync_items_id_seq";
				it = sessionFactory.getCurrentSession().createSQLQuery(sql)
						.list().iterator();
				int id_vrs_sync_items = 0;
				if (it.hasNext()) {
					id_vrs_sync_items = Integer.valueOf(it.next().toString());
					vrs_sync_computerlist vsc = new vrs_sync_computerlist();
					vsc.setId_computer(computer_id);
					vsc.setId_vrs_sync_items(id_vrs_sync_items);
					Timestamp tstamp = new Timestamp(new Date().getTime());
					vsc.setTime_created(tstamp);
					sessionFactory.getCurrentSession().save(vsc);
					objView.put("id_vrs_sync_items", id_vrs_sync_items);
				}
				getBack = objView.toString();

			}

		} catch (Exception ex) {
			getBack += " error saveInTable_ved_reports = " + ex.getMessage();
		}

		return getBack;
	}
	
	public void saveInTable_ved_enrollmentsDownload(String data) {
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("ved_enrollments")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			String sql = "";
			if (action.equalsIgnoreCase("add")) {
				int schoolID = -1;
				Long idTAble = Long.valueOf(0);
				sql = "select nextval('vrs_enrollment_id_seq') as nextValue";
				Iterator it = sessionFactory.getCurrentSession()
						.createSQLQuery(sql).list().iterator();
				if (it.hasNext()) {
					idTAble = Long.valueOf(it.next().toString());
				}

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					schoolID = vcom.getCompanyId();
				}

				long studentID = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"studentCompany_Composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_students.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"studentID_Composite").toString())));
					criteria.add(Restrictions.eq("schoolId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_students vst = (ved_students) it.next();
						studentID = vst.getStudentId();
					}
				}

				long classID = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ClasscompanyID_Composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_classes.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ClassId_Composite").toString())));
					criteria.add(Restrictions.eq("school_id",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_classes vcl = (ved_classes) it.next();
						classID = vcl.getId();
					}
				}

				String courseId = "null";
				try {
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CoursecompanyID_Composite").toString())));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_companies vcom = (vrs_companies) it.next();
						criteria = sessionFactory.getCurrentSession()
								.createCriteria(ved_courses.class);
						criteria.add(Restrictions.eq(
								"id_composite",
								Integer.valueOf(dataVrs_users.get(
										"CourseId_composite").toString())));
						criteria.add(Restrictions.eq("schoolId",
								vcom.getCompanyId()));
						it = criteria.list().iterator();
						if (it.hasNext()) {
							ved_courses vco = (ved_courses) it.next();
							courseId = String.valueOf(vco.getId());
						}
					}
				} catch (Exception ex) {
				}

				int createdBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						createdBy = vr.getRoleId();
					}
				}

				int modifiedBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						modifiedBy = vr.getRoleId();
					}
				}

				String attendance = "'"
						+ dataVrs_users.get("attendance").toString() + "'";

				sql = "ALTER TABLE ved_enrollments DISABLE TRIGGER insert_ved_enrollmentst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sql = "insert into ved_enrollments (id,school_id,student_id,class_id,"
						+ "course_id,advisor,attendance,created, "
						+ "created_by,modified,modified_by,status,id_composite) "
						+ " values (:id,:school_id,:student_id,:class_id,"
						+ courseId
						+ ",:advisor,"
						+ attendance
						+ ",:created, "
						+ ":created_by,:modified,:modified_by,:status,:id_composite)";

				sessionFactory
						.getCurrentSession()
						.createSQLQuery(sql)
						.setLong("id", idTAble)
						.setInteger("school_id", schoolID)
						.setLong("student_id", studentID)
						.setLong("class_id", classID)
						.setString("advisor",
								dataVrs_users.get("advisor").toString())
						.setTimestamp(
								"created",
								ssc.getTimeStamp(dataVrs_users.get("created")
										.toString()))
						.setInteger("created_by", createdBy)
						.setTimestamp(
								"modified",
								ssc.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modified_by", modifiedBy)
						.setInteger(
								"status",
								Integer.valueOf(dataVrs_users.get("status")
										.toString()))

						.setInteger(
								"id_composite",
								Integer.valueOf(dataVrs_users.get(
										"id_composite").toString()))

						.executeUpdate();
				sessionFactory.getCurrentSession().clear();

				sql = "ALTER TABLE ved_enrollments ENABLE TRIGGER insert_ved_enrollmentst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

			} else if (action.equalsIgnoreCase("edit")) {
				int schoolID = -1;
				Long idTable = Long.valueOf(-1);
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					schoolID = vcom.getCompanyId();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_enrollments.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));
					criteria.add(Restrictions.eq("schoolId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_enrollments ven = (ved_enrollments) it.next();
						idTable = ven.getEnrollmentId();
					}
				}

				long studentID = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"studentCompany_Composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_students.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"studentID_Composite").toString())));
					criteria.add(Restrictions.eq("schoolId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_students vst = (ved_students) it.next();
						studentID = vst.getStudentId();
					}
				}

				long classID = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ClasscompanyID_Composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_classes.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ClassId_Composite").toString())));
					criteria.add(Restrictions.eq("school_id",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_classes vcl = (ved_classes) it.next();
						classID = vcl.getId();
					}
				}

				String courseId = "null";
				try {
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CoursecompanyID_Composite").toString())));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_companies vcom = (vrs_companies) it.next();
						criteria = sessionFactory.getCurrentSession()
								.createCriteria(ved_courses.class);
						criteria.add(Restrictions.eq(
								"id_composite",
								Integer.valueOf(dataVrs_users.get(
										"CourseId_composite").toString())));
						criteria.add(Restrictions.eq("schoolId",
								vcom.getCompanyId()));
						it = criteria.list().iterator();
						if (it.hasNext()) {
							ved_courses vco = (ved_courses) it.next();
							courseId = String.valueOf(vco.getId());
						}
					}
				} catch (Exception ex) {
				}

				int createdBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						createdBy = vr.getRoleId();
					}
				}

				int modifiedBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						modifiedBy = vr.getRoleId();
					}
				}

				String attendance = "'"
						+ dataVrs_users.get("attendance").toString() + "'";

				sql = "ALTER TABLE ved_enrollments DISABLE TRIGGER insert_ved_enrollmentst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sql = "update ved_enrollments set school_id=:school_id,student_id=:student_id,"
						+ " class_id=:class_id,"
						+ " course_id="
						+ courseId
						+ ",advisor=:advisor,attendance="
						+ attendance
						+ ","
						+ " created=:created, "
						+ " created_by=:created_by,modified=:modified,"
						+ " modified_by=:modified_by,status=:status "
						+ " where id=:id";

				sessionFactory
						.getCurrentSession()
						.createSQLQuery(sql)
						.setLong("id", idTable)
						.setInteger("school_id", schoolID)
						.setLong("student_id", studentID)
						.setLong("class_id", classID)
						.setString("advisor",
								dataVrs_users.get("advisor").toString())
						.setTimestamp(
								"created",
								ssc.getTimeStamp(dataVrs_users.get("created")
										.toString()))
						.setInteger("created_by", createdBy)
						.setTimestamp(
								"modified",
								ssc.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modified_by", modifiedBy)
						.setInteger(
								"status",
								Integer.valueOf(dataVrs_users.get("status")
										.toString()))

						.executeUpdate();
				sessionFactory.getCurrentSession().clear();

				sql = "ALTER TABLE ved_enrollments ENABLE TRIGGER insert_ved_enrollmentst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();
			}
		} catch (Exception ex) {
			cf.viewAlert(" error saveInTable_ved_enrollments "
					+ ex.getMessage());
		}
	}
	
	public String saveInTable_ved_enrollments(String data, int computer_id) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL",
					obj.get("id_vrs_sync_itemsLOCAL").toString());
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("ved_enrollments")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			String sql = "";
			if (action.equalsIgnoreCase("add")) {
				int schoolID = -1;
				Long idTAble = Long.valueOf(0);
				sql = "select nextval('vrs_enrollment_id_seq') as nextValue";
				Iterator it = sessionFactory.getCurrentSession()
						.createSQLQuery(sql).list().iterator();
				if (it.hasNext()) {
					idTAble = Long.valueOf(it.next().toString());
				}

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					schoolID = vcom.getCompanyId();
				}

				long studentID = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"studentCompany_Composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_students.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"studentID_Composite").toString())));
					criteria.add(Restrictions.eq("schoolId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_students vst = (ved_students) it.next();
						studentID = vst.getStudentId();
					}
				}

				long classID = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ClasscompanyID_Composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_classes.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ClassId_Composite").toString())));
					criteria.add(Restrictions.eq("school_id",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_classes vcl = (ved_classes) it.next();
						classID = vcl.getId();
					}
				}

				String courseId = "null";
				try {
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CoursecompanyID_Composite").toString())));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_companies vcom = (vrs_companies) it.next();
						criteria = sessionFactory.getCurrentSession()
								.createCriteria(ved_courses.class);
						criteria.add(Restrictions.eq(
								"id_composite",
								Integer.valueOf(dataVrs_users.get(
										"CourseId_composite").toString())));
						criteria.add(Restrictions.eq("schoolId",
								vcom.getCompanyId()));
						it = criteria.list().iterator();
						if (it.hasNext()) {
							ved_courses vco = (ved_courses) it.next();
							courseId = String.valueOf(vco.getId());
						}
					}
				} catch (Exception ex) {
				}

				int createdBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						createdBy = vr.getRoleId();
					}
				}

				int modifiedBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						modifiedBy = vr.getRoleId();
					}
				}

				String attendance = "'"
						+ dataVrs_users.get("attendance").toString() + "'";

				sql = "insert into ved_enrollments (id,school_id,student_id,class_id,"
						+ "course_id,advisor,attendance,created, "
						+ "created_by,modified,modified_by,status) "
						+ " values (:id,:school_id,:student_id,:class_id,"
						+ courseId
						+ ",:advisor,"
						+ attendance
						+ ",:created, "
						+ ":created_by,:modified,:modified_by,:status)";

				sessionFactory
						.getCurrentSession()
						.createSQLQuery(sql)
						.setLong("id", idTAble)
						.setInteger("school_id", schoolID)
						.setLong("student_id", studentID)
						.setLong("class_id", classID)
						.setString("advisor",
								dataVrs_users.get("advisor").toString())
						.setTimestamp(
								"created",
								ssc.getTimeStamp(dataVrs_users.get("created")
										.toString()))
						.setInteger("created_by", createdBy)
						.setTimestamp(
								"modified",
								ssc.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modified_by", modifiedBy)
						.setInteger(
								"status",
								Integer.valueOf(dataVrs_users.get("status")
										.toString()))

						.executeUpdate();
				sessionFactory.getCurrentSession().clear();

				sql = "SELECT last_value from vrs_sync_items_id_seq";
				it = sessionFactory.getCurrentSession().createSQLQuery(sql)
						.list().iterator();
				int id_vrs_sync_items = 0;
				if (it.hasNext()) {
					id_vrs_sync_items = Integer.valueOf(it.next().toString());
					vrs_sync_computerlist vsc = new vrs_sync_computerlist();
					vsc.setId_computer(computer_id);
					vsc.setId_vrs_sync_items(id_vrs_sync_items);
					Timestamp tstamp = new Timestamp(new Date().getTime());
					vsc.setTime_created(tstamp);
					sessionFactory.getCurrentSession().save(vsc);
					objView.put("id_vrs_sync_items", id_vrs_sync_items);
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_enrollments.class);
				criteria.add(Restrictions.eq("id", idTAble));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					ved_enrollments vcli = (ved_enrollments) it.next();
					objView.put("id_CompositeFromServer",
							vcli.getId_composite());
					getBack = objView.toString();
				}

			} else if (action.equalsIgnoreCase("edit")) {
				int schoolID = -1;
				Long idTable = Long.valueOf(-1);

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					schoolID = vcom.getCompanyId();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_enrollments.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));
					criteria.add(Restrictions.eq("schoolId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_enrollments ven = (ved_enrollments) it.next();
						idTable = ven.getEnrollmentId();
					}
				}

				long studentID = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"studentCompany_Composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_students.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"studentID_Composite").toString())));
					criteria.add(Restrictions.eq("schoolId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_students vst = (ved_students) it.next();
						studentID = vst.getStudentId();
					}
				}

				long classID = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ClasscompanyID_Composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_classes.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ClassId_Composite").toString())));
					criteria.add(Restrictions.eq("school_id",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_classes vcl = (ved_classes) it.next();
						classID = vcl.getId();
					}
				}

				String courseId = "null";
				try {
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CoursecompanyID_Composite").toString())));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_companies vcom = (vrs_companies) it.next();
						criteria = sessionFactory.getCurrentSession()
								.createCriteria(ved_courses.class);
						criteria.add(Restrictions.eq(
								"id_composite",
								Integer.valueOf(dataVrs_users.get(
										"CourseId_composite").toString())));
						criteria.add(Restrictions.eq("schoolId",
								vcom.getCompanyId()));
						it = criteria.list().iterator();
						if (it.hasNext()) {
							ved_courses vco = (ved_courses) it.next();
							courseId = String.valueOf(vco.getId());
						}
					}
				} catch (Exception ex) {
				}

				int createdBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						createdBy = vr.getRoleId();
					}
				}

				int modifiedBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						modifiedBy = vr.getRoleId();
					}
				}

				String attendance = "'"
						+ dataVrs_users.get("attendance").toString() + "'";

				sql = "update ved_enrollments set school_id=:school_id,student_id=:student_id,"
						+ " class_id=:class_id,"
						+ " course_id="
						+ courseId
						+ ",advisor=:advisor,attendance="
						+ attendance
						+ ","
						+ " created=:created, "
						+ " created_by=:created_by,modified=:modified,"
						+ " modified_by=:modified_by,status=:status "
						+ " where id=:id";
				sessionFactory
						.getCurrentSession()
						.createSQLQuery(sql)
						.setLong("id", idTable)
						.setInteger("school_id", schoolID)
						.setLong("student_id", studentID)
						.setLong("class_id", classID)
						.setString("advisor",
								dataVrs_users.get("advisor").toString())
						.setTimestamp(
								"created",
								ssc.getTimeStamp(dataVrs_users.get("created")
										.toString()))
						.setInteger("created_by", createdBy)
						.setTimestamp(
								"modified",
								ssc.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modified_by", modifiedBy)
						.setInteger(
								"status",
								Integer.valueOf(dataVrs_users.get("status")
										.toString()))

						.executeUpdate();

				sessionFactory.getCurrentSession().clear();

				sql = "SELECT last_value from vrs_sync_items_id_seq";
				it = sessionFactory.getCurrentSession().createSQLQuery(sql)
						.list().iterator();
				int id_vrs_sync_items = 0;
				if (it.hasNext()) {
					id_vrs_sync_items = Integer.valueOf(it.next().toString());
					vrs_sync_computerlist vsc = new vrs_sync_computerlist();
					vsc.setId_computer(computer_id);
					vsc.setId_vrs_sync_items(id_vrs_sync_items);
					Timestamp tstamp = new Timestamp(new Date().getTime());
					vsc.setTime_created(tstamp);
					sessionFactory.getCurrentSession().save(vsc);
					objView.put("id_vrs_sync_items", id_vrs_sync_items);
				}
				getBack = objView.toString();
			}
		} catch (Exception ex) {
			getBack += " error saveInTable_ved_enrollments " + ex.getMessage();
		}

		return getBack;
	}
	
	public String saveInTable_ved_course_schedule(String data, int computer_id) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL",
					obj.get("id_vrs_sync_itemsLOCAL").toString());
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("ved_course_schedule")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			String sql = "";
			if (action.equalsIgnoreCase("add")) {
				ved_course_schedule vcs = new ved_course_schedule();
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
						ved_courses vco = (ved_courses) it.next();
						vcs.setCourseId(vco.getId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vcs.setCreatedBy(vr.getRoleId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vcs.setModifiedBy(vr.getRoleId());
					}
				}

				vcs.setDays(dataVrs_users.get("days").toString());
				vcs.setStartTime(dataVrs_users.get("startTime").toString());
				vcs.setEndTime(dataVrs_users.get("endTime").toString());
				vcs.setLocation(dataVrs_users.get("location").toString());
				vcs.setNotes(dataVrs_users.get("notes").toString());
				if (dataVrs_users.get("testTime").toString().length() > 0)
					vcs.setTestTime(dataVrs_users.get("testTime").toString());
				vcs.setCreated(ssc.getTimeStamp(dataVrs_users.get("created")
						.toString()));
				vcs.setModified(ssc.getTimeStamp(dataVrs_users.get("modified")
						.toString()));
				sessionFactory.getCurrentSession().save(vcs);
				sessionFactory.getCurrentSession().clear();

				sql = "SELECT last_value from vrs_sync_items_id_seq";
				it = sessionFactory.getCurrentSession().createSQLQuery(sql)
						.list().iterator();
				int id_vrs_sync_items = 0;
				if (it.hasNext()) {
					id_vrs_sync_items = Integer.valueOf(it.next().toString());
					vrs_sync_computerlist vsc = new vrs_sync_computerlist();
					vsc.setId_computer(computer_id);
					vsc.setId_vrs_sync_items(id_vrs_sync_items);
					Timestamp tstamp = new Timestamp(new Date().getTime());
					vsc.setTime_created(tstamp);
					sessionFactory.getCurrentSession().save(vsc);
					objView.put("id_vrs_sync_items", id_vrs_sync_items);
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_course_schedule.class);
				criteria.add(Restrictions.eq("id", vcs.getId()));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					ved_course_schedule vcla = (ved_course_schedule) it.next();
					objView.put("id_CompositeFromServer",
							vcla.getId_composite());
					getBack = objView.toString();
				}
			} else if (action.equalsIgnoreCase("edit")) {
				int idTable = 0;
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(ved_course_schedule.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("id_composite").toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					ved_course_schedule vcs = (ved_course_schedule) it.next();
					idTable = vcs.getId();
				}

				Long courseID = Long.valueOf(0);
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CoursecompanyID_Composite").toString())));
				it = criteria.list().iterator();
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
						ved_courses vco = (ved_courses) it.next();
						courseID = vco.getId();
					}
				}

				int createdBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						createdBy = vr.getRoleId();
					}
				}

				int modifiedBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						modifiedBy = vr.getRoleId();
					}
				}

				String testTime = dataVrs_users.get("testTime").toString();
				if (testTime.length() < 1)
					testTime = null;
				sql = "update ved_course_schedule set courseId=:courseId,"
						+ " days=:days,startTime=:startTime,endTime=:endTime,location=:location,"
						+ " notes=:notes,testTime=:testTime,created=:created,createdBy=:createdBy,"
						+ " modified=:modified,modifiedBy=:modifiedBy where id=:id";

				sessionFactory
						.getCurrentSession()
						.createQuery(sql)
						.setInteger("id", idTable)
						.setLong("courseId", courseID)
						.setString("days", dataVrs_users.get("days").toString())
						.setString("startTime",
								dataVrs_users.get("startTime").toString())
						.setString("endTime",
								dataVrs_users.get("endTime").toString())
						.setString("location",
								dataVrs_users.get("location").toString())
						.setString("notes",
								dataVrs_users.get("notes").toString())
						.setString("testTime", testTime)
						.setTimestamp(
								"created",
								ssc.getTimeStamp(dataVrs_users.get("created")
										.toString()))
						.setInteger("createdBy", createdBy)
						.setTimestamp(
								"modified",
								ssc.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modifiedBy", modifiedBy).executeUpdate();

				sql = "SELECT last_value from vrs_sync_items_id_seq";
				it = sessionFactory.getCurrentSession().createSQLQuery(sql)
						.list().iterator();
				int id_vrs_sync_items = 0;
				if (it.hasNext()) {
					id_vrs_sync_items = Integer.valueOf(it.next().toString());
					vrs_sync_computerlist vsc = new vrs_sync_computerlist();
					vsc.setId_computer(computer_id);
					vsc.setId_vrs_sync_items(id_vrs_sync_items);
					Timestamp tstamp = new Timestamp(new Date().getTime());
					vsc.setTime_created(tstamp);
					sessionFactory.getCurrentSession().save(vsc);
					objView.put("id_vrs_sync_items", id_vrs_sync_items);
					getBack = objView.toString();
				}
			}
		} catch (Exception ex) {
			cf.viewAlert(" error saveInTable_ved_course_schedule "
					+ ex.getMessage());
		}
		return getBack;
	}
	
	public void saveInTable_ved_coursesDownload(String data) {
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("ved_courses").toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			String sql = "";
			if (action.equalsIgnoreCase("add")) {
				ved_courses vc = new ved_courses();

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					vc.setSchoolId(vcom.getCompanyId());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ClasscompanyID_Composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_classes.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ClassId_Composite").toString())));
					criteria.add(Restrictions.eq("school_id",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_classes vcl = (ved_classes) it.next();
						vc.setClassId(vcl.getId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vc.setCreatedBy(vr.getRoleId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vc.setModifiedBy(vr.getRoleId());
					}
				}

				sql = "select id  from ved_course_list where id_composite=:id_composite";
				it = sessionFactory
						.getCurrentSession()
						.createSQLQuery(sql)

						.setInteger(
								"id_composite",
								Integer.valueOf(dataVrs_users.get(
										"idved_course_list_composite")
										.toString()))

						.list().iterator();
				if (it.hasNext()) {
					vc.setId_ved_course_list(Integer.valueOf(it.next()
							.toString()));
				}

				vc.setDescription(dataVrs_users.get("description").toString());
				vc.setGrade(dataVrs_users.get("grade").toString());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"instructor_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"instructor_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vc.setInstructor(vr.getRoleId());
					}
				}

				if (dataVrs_users.get("avgScore").toString().length() > 0)
					vc.setAvgScore(Float.valueOf(dataVrs_users.get("avgScore")
							.toString()));

				vc.setCreated(ssc.getTimeStamp(dataVrs_users.get("created")
						.toString()));
				vc.setModified(ssc.getTimeStamp(dataVrs_users.get("modified")
						.toString()));
				vc.setStatus(Integer.valueOf(dataVrs_users.get("status")
						.toString()));

				vc.setId_composite(Integer.valueOf(dataVrs_users.get(
						"id_composite").toString()));

				sql = "ALTER TABLE ved_courses DISABLE TRIGGER insert_ved_coursest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sessionFactory.getCurrentSession().save(vc);
				sessionFactory.getCurrentSession().clear();

				sql = "ALTER TABLE ved_courses ENABLE TRIGGER insert_ved_coursest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

			} else if (action.equalsIgnoreCase("edit")) {
				int schoolID = -1;
				Long idTable = Long.valueOf(0);

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					schoolID = vcom.getCompanyId();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_courses.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));
					criteria.add(Restrictions.eq("schoolId", schoolID));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_courses vdc = (ved_courses) it.next();
						idTable = vdc.getId();
					}
				}
				Long classId = Long.valueOf(-1);
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ClasscompanyID_Composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_classes.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ClassId_Composite").toString())));
					criteria.add(Restrictions.eq("school_id",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_classes vcl = (ved_classes) it.next();
						classId = vcl.getId();
					}
				}

				int createdBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						createdBy = vr.getRoleId();
					}
				}

				int modifiedBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						modifiedBy = vr.getRoleId();
					}
				}

				int id_ved_course_list = -1;
				sql = "select id  from ved_course_list where id_composite=:id_composite";
				it = sessionFactory
						.getCurrentSession()
						.createSQLQuery(sql)

						.setInteger(
								"id_composite",
								Integer.valueOf(dataVrs_users.get(
										"idved_course_list_composite")
										.toString()))

						.list().iterator();
				if (it.hasNext()) {
					id_ved_course_list = Integer.valueOf(it.next().toString());
				}

				String avgScore = dataVrs_users.get("avgScore").toString();
				Float avgScoreii = null;
				if (avgScore.length() > 0)
					avgScoreii = Float.valueOf(avgScore);

				int instructorI = 0;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"instructor_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"instructor_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						instructorI = vr.getRoleId();
					}
				}

				sql = "ALTER TABLE ved_courses DISABLE TRIGGER insert_ved_coursest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sql = "update ved_courses set schoolId=:schoolId,classId=:classId, "
						+ " description=:description,grade=:grade, "
						+ " instructor=:instructor,avgScore=:avgScore, "
						+ " created=:created,createdBy=:createdBy, "
						+ " modified=:modified,modifiedBy=:modifiedBy,status=:status, "
						+ " id_ved_course_list=:id_ved_course_list "
						+ "  where id=:id";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)
						.setLong("id", idTable)
						.setInteger("schoolId", schoolID)
						.setLong("classId", classId)
						.setString("description",
								dataVrs_users.get("description").toString())
						.setString("grade",
								dataVrs_users.get("grade").toString())
						.setInteger("instructor", instructorI)
						.setFloat("avgScore", avgScoreii)
						.setTimestamp(
								"created",
								ssc.getTimeStamp(dataVrs_users.get("created")
										.toString()))
						.setInteger("createdBy", createdBy)
						.setInteger("id_ved_course_list", id_ved_course_list)
						.setTimestamp(
								"modified",
								ssc.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modifiedBy", modifiedBy)
						.setInteger(
								"status",
								Integer.valueOf(dataVrs_users.get("status")
										.toString()))

						.executeUpdate();

				sql = "ALTER TABLE ved_courses ENABLE TRIGGER insert_ved_coursest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

			}
		} catch (Exception ex) {
			cf.viewAlert(" error saveInTable_ved_coursesDownload "
					+ ex.getMessage());
		}
	}
	
	public void saveInTable_ved_classesDownload(String data) {
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("ved_classes").toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			if (action.equalsIgnoreCase("add")) {
				ved_classes vcl = new ved_classes();

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					vcl.setSchool_id(vc.getCompanyId());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"AdvisorCompany_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"AdvisorID_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vcl.setAdvisor(vr.getRoleId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vcl.setCreated_by(vr.getRoleId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vcl.setModified_by(vr.getRoleId());
					}
				}

				vcl.setName(dataVrs_users.get("name").toString());
				vcl.setGrade(dataVrs_users.get("grade").toString());
				vcl.setYear(Integer.valueOf(dataVrs_users.get("year")
						.toString()));
				vcl.setCreated(ssc.getTimeStamp(dataVrs_users.get("created")
						.toString()));
				vcl.setModified(ssc.getTimeStamp(dataVrs_users.get("modified")
						.toString()));
				vcl.setStatus(Integer.valueOf(dataVrs_users.get("status")
						.toString()));

				vcl.setStatus(Integer.valueOf(dataVrs_users.get("status")
						.toString()));

				vcl.setId_composite(Integer.valueOf(dataVrs_users.get(
						"id_composite").toString()));

				String sql = "ALTER TABLE ved_classes DISABLE TRIGGER insert_ved_classest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sessionFactory.getCurrentSession().save(vcl);
				sessionFactory.getCurrentSession().clear();

				sql = "ALTER TABLE ved_classes ENABLE TRIGGER insert_ved_classest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

			} else if (action.equalsIgnoreCase("edit")) {

				int schooId = -1;
				long idTAble = Long.valueOf(-1);
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					schooId = vc.getCompanyId();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_classes.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));
					criteria.add(Restrictions.eq("school_id", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_classes vcl = (ved_classes) it.next();
						idTAble = vcl.getId();
					}
				}

				int AdvisorID = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"AdvisorCompany_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"AdvisorID_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						AdvisorID = vr.getRoleId();
					}
				}

				int createdBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						createdBy = vr.getRoleId();
					}
				}

				int modifiedBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						modifiedBy = vr.getRoleId();
					}
				}

				String sql = "ALTER TABLE ved_classes DISABLE TRIGGER insert_ved_classest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sql = "update ved_classes set "
						+ " school_id=:school_id,name=:name,grade=:grade,"
						+ " year=:year,advisor=:advisor,created=:created,"
						+ " created_by=:created_by,modified=:modified,modified_by=:modified_by,"
						+ " status=:status " + " where id=:id";

				sessionFactory
						.getCurrentSession()
						.createQuery(sql)
						.setLong("id", idTAble)
						.setInteger("school_id", schooId)
						.setString("name", dataVrs_users.get("name").toString())
						.setString("grade",
								dataVrs_users.get("grade").toString())
						.setInteger(
								"year",
								Integer.valueOf(dataVrs_users.get("year")
										.toString()))
						.setInteger("advisor", AdvisorID)
						.setTimestamp(
								"created",
								ssc.getTimeStamp(dataVrs_users.get("created")
										.toString()))
						.setInteger("created_by", createdBy)
						.setTimestamp(
								"modified",
								ssc.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modified_by", modifiedBy)
						.setInteger(
								"status",
								Integer.valueOf(dataVrs_users.get("status")
										.toString()))

						.executeUpdate();

				sql = "ALTER TABLE ved_classes ENABLE TRIGGER insert_ved_classest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

			}
		} catch (Exception ex) {
			cf.viewAlert(" error saveInTable_ved_classesDownload "
					+ ex.getMessage());
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void saveInTable_ved_candidatesDownload(String data) {
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("ved_candidates")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			if (action.equalsIgnoreCase("add")) {
				ved_candidates vca = new ved_candidates();

				int company_id = -1;
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					company_id = vc.getCompanyId();
					vca.setSchoolId(company_id);
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CustCompany_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vsc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CustId_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vsc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vca.setCustId(vr.getRoleId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vca.setCreatedBy(vr.getRoleId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vca.setModifiedBy(vr.getRoleId());
					}
				}

				vca.setCreated(ssc.getTimeStamp(dataVrs_users.get("created")
						.toString()));
				vca.setModified(ssc.getTimeStamp(dataVrs_users.get("modified")
						.toString()));
				vca.setStatus(Integer.valueOf(dataVrs_users.get("status")
						.toString()));
				int idCompositeThis = Integer.valueOf(dataVrs_users.get(
						"id_composite").toString());
				vca.setId_composite(idCompositeThis);

				String sql = "ALTER TABLE ved_candidates DISABLE TRIGGER insert_ved_candidatest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sVRS.changeIdComposite_ved_candidates(idCompositeThis,
						company_id, "ved_candidates");
				sessionFactory.getCurrentSession().save(vca);

				sessionFactory.getCurrentSession().clear();

				sql = "ALTER TABLE ved_candidates ENABLE TRIGGER insert_ved_candidatest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				try {
					JSONArray objData_meta = new JSONArray(obj.get(
							"ved_candidate_metas").toString());
					JSONObject objData_meta1 = null;
					int i = 0;
					for (i = 0; i < objData_meta.length(); i++) {
						objData_meta1 = objData_meta.getJSONObject(i);
						ved_candidate_metas vcm = new ved_candidate_metas();
						vcm.setCreated(ssc.getTimeStamp(objData_meta1
								.get("created").toString()));
						vcm.setCandidateMetasKey(objData_meta1.get(
								"candidateMetasKey").toString());
						vcm.setModified(ssc.getTimeStamp(objData_meta1.get(
								"modified").toString()));
						vcm.setCandidateMetasValue(objData_meta1.get(
								"candidateMetasValue").toString());
						vcm.setCandidateId(vca.getCandidateId());
						sessionFactory.getCurrentSession().save(vcm);
					}
					sessionFactory.getCurrentSession().clear();
				} catch (Exception ex) {
					cf.viewAlert(" error saveInTable_ved_candidatesDownload in save metas   "
							+ ex.getMessage());
				}

			} else if (action.equalsIgnoreCase("edit")) {

				int schoolID = -1;
				Long idTable = Long.valueOf(-1);
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					schoolID = vc.getCompanyId();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_candidates.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));
					criteria.add(Restrictions.eq("schoolId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_candidates vcan = (ved_candidates) it.next();
						idTable = vcan.getCandidateId();
					}
				}

				int custID = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CustCompany_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vsc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CustId_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vsc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						custID = vr.getRoleId();
					}
				}

				int createdBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						createdBy = vr.getRoleId();
					}
				}

				int modifiedBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						modifiedBy = vr.getRoleId();
					}
				}

				String sql = "ALTER TABLE ved_candidates DISABLE TRIGGER insert_ved_candidatest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sql = "update ved_candidates set custId=:custId,"
						+ "schoolId=:schoolId,created=:created,"
						+ "createdBy=:createdBy,modified=:modified,"
						+ "modifiedBy=:modifiedBy,status=:status "
						+ " where candidateId=:candidateId";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)

						.setLong("candidateId", idTable)
						.setInteger("schoolId", schoolID)
						.setInteger("custId", custID)
						.setTimestamp(
								"created",
								ssc.getTimeStamp(dataVrs_users.get("created")
										.toString()))
						.setInteger("createdBy", createdBy)
						.setTimestamp(
								"modified",
								ssc.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modifiedBy", modifiedBy)
						.setInteger(
								"status",
								Integer.valueOf(dataVrs_users.get("status")
										.toString()))

						.executeUpdate();
				sql = "ALTER TABLE ved_candidates ENABLE TRIGGER insert_ved_candidatest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				try {
					JSONArray objData_meta = new JSONArray(obj.get(
							"ved_candidate_metas").toString());
					JSONObject objData_meta1 = null;
					int i = 0;
					for (i = 0; i < objData_meta.length(); i++) {
						objData_meta1 = objData_meta.getJSONObject(i);
						addCandidateMetas(idTable,
								objData_meta1.get("candidateMetasKey")
										.toString(),
								objData_meta1.get("candidateMetasValue")
										.toString(), ssc.getTimeStamp(objData_meta1
										.get("created").toString()),
										ssc.getTimeStamp(objData_meta1.get("modified")
										.toString()));
					}
				} catch (Exception ex) {
				}
			}
		} catch (Exception ex) {
			cf.viewAlert(" error saveInTable_ved_candidatesDownload "
					+ ex.getMessage());
		}
	}
	
	public String saveInTable_ved_classes(String data, int computer_id) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL",
					obj.get("id_vrs_sync_itemsLOCAL").toString());
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("ved_classes").toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			if (action.equalsIgnoreCase("add")) {
				ved_classes vcl = new ved_classes();

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					vcl.setSchool_id(vc.getCompanyId());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"AdvisorCompany_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"AdvisorID_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vcl.setAdvisor(vr.getRoleId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vcl.setCreated_by(vr.getRoleId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vcl.setModified_by(vr.getRoleId());
					}
				}

				vcl.setName(dataVrs_users.get("name").toString());
				vcl.setGrade(dataVrs_users.get("grade").toString());
				vcl.setYear(Integer.valueOf(dataVrs_users.get("year")
						.toString()));
				vcl.setCreated(ssc.getTimeStamp(dataVrs_users.get("created")
						.toString()));
				vcl.setModified(ssc.getTimeStamp(dataVrs_users.get("modified")
						.toString()));
				vcl.setStatus(Integer.valueOf(dataVrs_users.get("status")
						.toString()));

				vcl.setStatus(Integer.valueOf(dataVrs_users.get("status")
						.toString()));

				sessionFactory.getCurrentSession().save(vcl);
				sessionFactory.getCurrentSession().clear();

				String sql = "SELECT last_value from vrs_sync_items_id_seq";
				it = sessionFactory.getCurrentSession().createSQLQuery(sql)
						.list().iterator();
				int id_vrs_sync_items = 0;
				if (it.hasNext()) {
					id_vrs_sync_items = Integer.valueOf(it.next().toString());
					vrs_sync_computerlist vsc = new vrs_sync_computerlist();
					vsc.setId_computer(computer_id);
					vsc.setId_vrs_sync_items(id_vrs_sync_items);
					Timestamp tstamp = new Timestamp(new Date().getTime());
					vsc.setTime_created(tstamp);
					sessionFactory.getCurrentSession().save(vsc);
					objView.put("id_vrs_sync_items", id_vrs_sync_items);
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_classes.class);
				criteria.add(Restrictions.eq("id", vcl.getId()));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					ved_classes vcla = (ved_classes) it.next();
					objView.put("id_CompositeFromServer",
							vcla.getId_composite());
					getBack = objView.toString();
				}
			} else if (action.equalsIgnoreCase("edit")) {

				int schooId = -1;
				long idTAble = Long.valueOf(-1);
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					schooId = vc.getCompanyId();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_classes.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));
					criteria.add(Restrictions.eq("school_id", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_classes vcl = (ved_classes) it.next();
						idTAble = vcl.getId();
					}
				}

				int AdvisorID = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"AdvisorCompany_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"AdvisorID_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						AdvisorID = vr.getRoleId();
					}
				}

				int createdBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						createdBy = vr.getRoleId();
					}
				}

				int modifiedBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						modifiedBy = vr.getRoleId();
					}
				}

				String sql = "update ved_classes set "
						+ " school_id=:school_id,name=:name,grade=:grade,"
						+ " year=:year,advisor=:advisor,created=:created,"
						+ " created_by=:created_by,modified=:modified,modified_by=:modified_by,"
						+ " status=:status " + " where id=:id";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)
						.setLong("id", idTAble)
						.setInteger("school_id", schooId)
						.setString("name", dataVrs_users.get("name").toString())
						.setString("grade",
								dataVrs_users.get("grade").toString())
						.setInteger(
								"year",
								Integer.valueOf(dataVrs_users.get("year")
										.toString()))
						.setInteger("advisor", AdvisorID)
						.setTimestamp(
								"created",
								ssc.getTimeStamp(dataVrs_users.get("created")
										.toString()))
						.setInteger("created_by", createdBy)
						.setTimestamp(
								"modified",
								ssc.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modified_by", modifiedBy)
						.setInteger(
								"status",
								Integer.valueOf(dataVrs_users.get("status")
										.toString()))

						.executeUpdate();

				sql = "SELECT last_value from vrs_sync_items_id_seq";
				it = sessionFactory.getCurrentSession().createSQLQuery(sql)
						.list().iterator();
				int id_vrs_sync_items = 0;
				if (it.hasNext()) {
					id_vrs_sync_items = Integer.valueOf(it.next().toString());
					vrs_sync_computerlist vsc = new vrs_sync_computerlist();
					vsc.setId_computer(computer_id);
					vsc.setId_vrs_sync_items(id_vrs_sync_items);
					Timestamp tstamp = new Timestamp(new Date().getTime());
					vsc.setTime_created(tstamp);
					sessionFactory.getCurrentSession().save(vsc);
					objView.put("id_vrs_sync_items", id_vrs_sync_items);
					getBack = objView.toString();
				}

			}
		} catch (Exception ex) {
			cf.viewAlert(" error saveInTable_ved_classes " + ex.getMessage());
		}
		return getBack;
	}
	
	public String saveInTable_ved_course_list(String data, int computer_id) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL",
					obj.get("id_vrs_sync_itemsLOCAL").toString());
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("ved_course_list")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			String sql = "";
			if (action.equalsIgnoreCase("add")) {
				int idTable = 0;

				String prerequisite = "null";

				sql = "select nextval('ved_course_list_id_seq') as nextValue";
				Iterator it = sessionFactory.getCurrentSession()
						.createSQLQuery(sql).list().iterator();
				if (it.hasNext()) {
					idTable = Integer.valueOf(it.next().toString());
				}

				int createdBy = -1;
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						createdBy = vr.getRoleId();
					}
				}

				int modifiedBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						modifiedBy = vr.getRoleId();
					}
				}

				if (dataVrs_users.get("prerequisite").toString().length() > 0) {
					prerequisite = "'"
							+ dataVrs_users.get("prerequisite").toString()
							+ "'";
				}

				sql = "insert into ved_course_list (id,title,description,level,grade,created,"
						+ "created_by,modified,modified_by,status,prerequisite) "
						+ "values (:id,:title,:description,:level,:grade,:created,"
						+ ":created_by,:modified,:modified_by,:status,"
						+ prerequisite + ");";

				sessionFactory
						.getCurrentSession()
						.createSQLQuery(sql)
						.setInteger("id", idTable)
						.setString("title",
								dataVrs_users.get("title").toString())
						.setString("description",
								dataVrs_users.get("description").toString())
						.setString("level",
								dataVrs_users.get("level").toString())
						.setString("grade",
								dataVrs_users.get("grade").toString())
						.setTimestamp(
								"created",
								ssc.getTimeStamp(dataVrs_users.get("created")
										.toString()))
						.setInteger("created_by", createdBy)
						.setTimestamp(
								"modified",
								ssc.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modified_by", modifiedBy)
						.setInteger(
								"status",
								Integer.valueOf(dataVrs_users.get("status")
										.toString())).executeUpdate();
				sessionFactory.getCurrentSession().clear();

				sql = "SELECT last_value from vrs_sync_items_id_seq";
				it = sessionFactory.getCurrentSession().createSQLQuery(sql)
						.list().iterator();
				int id_vrs_sync_items = 0;
				if (it.hasNext()) {
					id_vrs_sync_items = Integer.valueOf(it.next().toString());
					vrs_sync_computerlist vsc = new vrs_sync_computerlist();
					vsc.setId_computer(computer_id);
					vsc.setId_vrs_sync_items(id_vrs_sync_items);
					Timestamp tstamp = new Timestamp(new Date().getTime());
					vsc.setTime_created(tstamp);
					sessionFactory.getCurrentSession().save(vsc);
					objView.put("id_vrs_sync_items", id_vrs_sync_items);
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_course_list.class);
				criteria.add(Restrictions.eq("id", idTable));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					ved_course_list vcli = (ved_course_list) it.next();
					objView.put("id_CompositeFromServer",
							vcli.getId_composite());
					getBack = objView.toString();
				}

			} else if (action.equalsIgnoreCase("edit")) {
				int idTable = 0;

				String prerequisite = "null";

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(ved_course_list.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("id_composite").toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					ved_course_list vcl = (ved_course_list) it.next();
					idTable = vcl.getId();
				}

				int createdBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						createdBy = vr.getRoleId();
					}
				}

				int modifiedBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						modifiedBy = vr.getRoleId();
					}
				}

				if (dataVrs_users.get("prerequisite").toString().length() > 0) {
					prerequisite = "'"
							+ dataVrs_users.get("prerequisite").toString()
							+ "'";
				}

				sql = "update ved_course_list set title=:title,description=:description,level=:level,"
						+ " grade=:grade,created=:created,created_by=:created_by,"
						+ " modified=:modified,modified_by=:modified_by,"
						+ " status=:status,prerequisite="
						+ prerequisite
						+ " where id=:id";

				sessionFactory
						.getCurrentSession()
						.createSQLQuery(sql)
						.setInteger("id", idTable)
						.setString("title",
								dataVrs_users.get("title").toString())
						.setString("description",
								dataVrs_users.get("description").toString())
						.setString("level",
								dataVrs_users.get("level").toString())
						.setString("grade",
								dataVrs_users.get("grade").toString())
						.setTimestamp(
								"created",
								ssc.getTimeStamp(dataVrs_users.get("created")
										.toString()))
						.setInteger("created_by", createdBy)
						.setTimestamp(
								"modified",
								ssc.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modified_by", modifiedBy)
						.setInteger(
								"status",
								Integer.valueOf(dataVrs_users.get("status")
										.toString()))

						.executeUpdate();

				sql = "SELECT last_value from vrs_sync_items_id_seq";
				it = sessionFactory.getCurrentSession().createSQLQuery(sql)
						.list().iterator();
				int id_vrs_sync_items = 0;
				if (it.hasNext()) {
					id_vrs_sync_items = Integer.valueOf(it.next().toString());
					vrs_sync_computerlist vsc = new vrs_sync_computerlist();
					vsc.setId_computer(computer_id);
					vsc.setId_vrs_sync_items(id_vrs_sync_items);
					Timestamp tstamp = new Timestamp(new Date().getTime());
					vsc.setTime_created(tstamp);
					sessionFactory.getCurrentSession().save(vsc);
					objView.put("id_vrs_sync_items", id_vrs_sync_items);
					getBack = objView.toString();
				}

			}
		} catch (Exception ex) {
			cf.viewAlert(" error saveInTable_ved_course_list "
					+ ex.getMessage());
		}
		return getBack;
	}
	
	public void saveInTable_ved_course_scheduleDownload(String data) {
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("ved_course_schedule")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			String sql = "";
			if (action.equalsIgnoreCase("add")) {
				ved_course_schedule vcs = new ved_course_schedule();
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
						ved_courses vco = (ved_courses) it.next();
						vcs.setCourseId(vco.getId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vcs.setCreatedBy(vr.getRoleId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vcs.setModifiedBy(vr.getRoleId());
					}
				}

				vcs.setDays(dataVrs_users.get("days").toString());
				vcs.setStartTime(dataVrs_users.get("startTime").toString());
				vcs.setEndTime(dataVrs_users.get("endTime").toString());
				vcs.setLocation(dataVrs_users.get("location").toString());
				vcs.setNotes(dataVrs_users.get("notes").toString());

				String testTime = dataVrs_users.get("testTime").toString();
				if (testTime.length() > 0)
					vcs.setTestTime(testTime);

				vcs.setCreated(ssc.getTimeStamp(dataVrs_users.get("created")
						.toString()));
				vcs.setModified(ssc.getTimeStamp(dataVrs_users.get("modified")
						.toString()));
				vcs.setId_composite(Integer.valueOf(dataVrs_users.get(
						"id_composite").toString()));

				sql = "ALTER TABLE ved_course_schedule DISABLE TRIGGER insert_ved_course_schedulet";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sessionFactory.getCurrentSession().save(vcs);
				sessionFactory.getCurrentSession().clear();

				sql = "ALTER TABLE ved_course_schedule ENABLE TRIGGER insert_ved_course_schedulet";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

			} else if (action.equalsIgnoreCase("edit")) {
				int idTable = 0;
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(ved_course_schedule.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("id_composite").toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					ved_course_schedule vcs = (ved_course_schedule) it.next();
					idTable = vcs.getId();
				}

				Long courseID = Long.valueOf(0);
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CoursecompanyID_Composite").toString())));
				it = criteria.list().iterator();
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
						ved_courses vco = (ved_courses) it.next();
						courseID = vco.getId();
					}
				}

				int createdBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						createdBy = vr.getRoleId();
					}
				}

				int modifiedBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						modifiedBy = vr.getRoleId();
					}
				}

				String testTime = dataVrs_users.get("testTime").toString();
				if (testTime.length() < 1)
					testTime = null;

				sql = "ALTER TABLE ved_course_schedule DISABLE TRIGGER insert_ved_course_schedulet";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sql = "update ved_course_schedule set courseId=:courseId,"
						+ " days=:days,startTime=:startTime,endTime=:endTime,location=:location,"
						+ " notes=:notes,testTime=:testTime,created=:created,createdBy=:createdBy,"
						+ " modified=:modified,modifiedBy=:modifiedBy where id=:id";

				sessionFactory
						.getCurrentSession()
						.createQuery(sql)
						.setInteger("id", idTable)
						.setLong("courseId", courseID)
						.setString("days", dataVrs_users.get("days").toString())
						.setString("startTime",
								dataVrs_users.get("startTime").toString())
						.setString("endTime",
								dataVrs_users.get("endTime").toString())
						.setString("location",
								dataVrs_users.get("location").toString())
						.setString("notes",
								dataVrs_users.get("notes").toString())
						.setString("testTime", testTime)
						.setTimestamp(
								"created",
								ssc.getTimeStamp(dataVrs_users.get("created")
										.toString()))
						.setInteger("createdBy", createdBy)
						.setTimestamp(
								"modified",
								ssc.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modifiedBy", modifiedBy).executeUpdate();

				sql = "ALTER TABLE ved_course_schedule ENABLE TRIGGER insert_ved_course_schedulet";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();
			}
		} catch (Exception ex) {
			cf.viewAlert(" error saveInTable_ved_course_schedule "
					+ ex.getMessage());
		}
	}
	
	public void saveInTable_ved_course_listDownload(String data) {
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("ved_course_list")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			String sql = "";
			if (action.equalsIgnoreCase("add")) {

				String prerequisite = "null";

				int createdBy = -1;
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						createdBy = vr.getRoleId();
					}
				}

				int modifiedBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						modifiedBy = vr.getRoleId();
					}
				}

				if (dataVrs_users.get("prerequisite").toString().length() > 0) {
					prerequisite = "'"
							+ dataVrs_users.get("prerequisite").toString()
							+ "'";
				}

				sql = "ALTER TABLE ved_course_list DISABLE TRIGGER insert_ved_course_listt";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sql = "insert into ved_course_list (title,description,level,grade,created,"
						+ "created_by,modified,modified_by,status,prerequisite,id_composite) "
						+ "values (:title,:description,:level,:grade,:created,"
						+ ":created_by,:modified,:modified_by,:status,"
						+ prerequisite + ",:id_composite);";

				sessionFactory
						.getCurrentSession()
						.createSQLQuery(sql)
						.setString("title",
								dataVrs_users.get("title").toString())
						.setString("description",
								dataVrs_users.get("description").toString())
						.setString("level",
								dataVrs_users.get("level").toString())
						.setString("grade",
								dataVrs_users.get("grade").toString())
						.setTimestamp(
								"created",
								ssc.getTimeStamp(dataVrs_users.get("created")
										.toString()))
						.setInteger("created_by", createdBy)
						.setTimestamp(
								"modified",
								ssc.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modified_by", modifiedBy)
						.setInteger(
								"status",
								Integer.valueOf(dataVrs_users.get("status")
										.toString()))
						.setInteger(
								"id_composite",
								Integer.valueOf(dataVrs_users.get(
										"id_composite").toString()))

						.executeUpdate();
				sessionFactory.getCurrentSession().clear();

				sql = "ALTER TABLE ved_course_list ENABLE TRIGGER insert_ved_course_listt";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

			} else if (action.equalsIgnoreCase("edit")) {
				int idTable = 0;

				String prerequisite = "null";

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(ved_course_list.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("id_composite").toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					ved_course_list vcl = (ved_course_list) it.next();
					idTable = vcl.getId();
				}

				int createdBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						createdBy = vr.getRoleId();
					}
				}

				int modifiedBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						modifiedBy = vr.getRoleId();
					}
				}

				if (dataVrs_users.get("prerequisite").toString().length() > 0) {
					prerequisite = "'"
							+ dataVrs_users.get("prerequisite").toString()
							+ "'";
				}

				sql = "ALTER TABLE ved_course_list DISABLE TRIGGER insert_ved_course_listt";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sql = "update ved_course_list set title=:title,description=:description,level=:level,"
						+ " grade=:grade,created=:created,created_by=:created_by,"
						+ " modified=:modified,modified_by=:modified_by,"
						+ " status=:status,prerequisite="
						+ prerequisite
						+ " where id=:id";

				sessionFactory
						.getCurrentSession()
						.createSQLQuery(sql)
						.setInteger("id", idTable)
						.setString("title",
								dataVrs_users.get("title").toString())
						.setString("description",
								dataVrs_users.get("description").toString())
						.setString("level",
								dataVrs_users.get("level").toString())
						.setString("grade",
								dataVrs_users.get("grade").toString())
						.setString("grade",
								dataVrs_users.get("grade").toString())
						.setTimestamp(
								"created",
								ssc.getTimeStamp(dataVrs_users.get("created")
										.toString()))
						.setInteger("created_by", createdBy)
						.setTimestamp(
								"modified",
								ssc.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modified_by", modifiedBy)
						.setInteger(
								"status",
								Integer.valueOf(dataVrs_users.get("status")
										.toString()))

						.executeUpdate();

				sql = "ALTER TABLE ved_course_list ENABLE TRIGGER insert_ved_course_listt";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

			}
		} catch (Exception ex) {
			cf.viewAlert(" error saveInTable_ved_course_list "
					+ ex.getMessage());
		}
	}
	
	public void addCandidateMetas(Long idTable, String key, String val,
			Timestamp created, Timestamp modified) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				ved_candidate_metas.class);
		criteria.add(Restrictions.eq("candidateId", idTable));
		criteria.add(Restrictions.eq("candidateMetasKey", key));
		if (criteria.list().size() < 1) {
			ved_candidate_metas vcm = new ved_candidate_metas();
			vcm.setCreated(created);
			vcm.setCandidateMetasKey(key);
			vcm.setModified(modified);
			vcm.setCandidateId(idTable);
			vcm.setCandidateMetasValue(val);
			sessionFactory.getCurrentSession().save(vcm);
		} else {
			String sql = "update ved_candidate_metas set candidateMetasValue=:val,"
					+ "modified=:modified where candidateMetasKey=:key and candidateId=:candidateId";
			sessionFactory.getCurrentSession().createQuery(sql)
					.setString("val", val).setTimestamp("modified", modified)
					.setLong("candidateId", idTable).setString("key", key)
					.executeUpdate();
		}

	}
	

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String saveInTable_ved_candidates(String data, String roleId,
			int computer_id) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL",
					obj.get("id_vrs_sync_itemsLOCAL").toString());
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("ved_candidates")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			if (action.equalsIgnoreCase("add")) {
				ved_candidates vca = new ved_candidates();

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					vca.setSchoolId(vc.getCompanyId());
				}

				if (roleId.length() < 1) {
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CustCompany_composite").toString())));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_companies vsc = (vrs_companies) it.next();
						criteria = sessionFactory.getCurrentSession()
								.createCriteria(vrs_roles.class);
						criteria.add(Restrictions.eq(
								"id_composite",
								Integer.valueOf(dataVrs_users.get(
										"CustId_composite").toString())));
						criteria.add(Restrictions.eq("companyId",
								vsc.getCompanyId()));
						it = criteria.list().iterator();
						if (it.hasNext()) {
							vrs_roles vr = (vrs_roles) it.next();
							vca.setCustId(vr.getRoleId());
						}
					}
				} else {
					vca.setCustId(Integer.valueOf(roleId));
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vca.setCreatedBy(vr.getRoleId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vca.setModifiedBy(vr.getRoleId());
					}
				}

				vca.setCreated(ssc.getTimeStamp(dataVrs_users.get("created")
						.toString()));
				vca.setModified(ssc.getTimeStamp(dataVrs_users.get("modified")
						.toString()));
				vca.setStatus(Integer.valueOf(dataVrs_users.get("status")
						.toString()));

				sessionFactory.getCurrentSession().save(vca);
				sessionFactory.getCurrentSession().clear();

				String sql = "SELECT last_value from vrs_sync_items_id_seq";
				it = sessionFactory.getCurrentSession().createSQLQuery(sql)
						.list().iterator();
				int id_vrs_sync_items = 0;
				if (it.hasNext()) {
					id_vrs_sync_items = Integer.valueOf(it.next().toString());
					vrs_sync_computerlist vsc = new vrs_sync_computerlist();
					vsc.setId_computer(computer_id);
					vsc.setId_vrs_sync_items(id_vrs_sync_items);
					Timestamp tstamp = new Timestamp(new Date().getTime());
					vsc.setTime_created(tstamp);
					sessionFactory.getCurrentSession().save(vsc);
					objView.put("id_vrs_sync_items", id_vrs_sync_items);
				}

				try {
					JSONArray objData_meta = new JSONArray(obj.get(
							"ved_candidate_metas").toString());
					JSONObject objData_meta1 = null;
					int i = 0;
					for (i = 0; i < objData_meta.length(); i++) {
						objData_meta1 = objData_meta.getJSONObject(i);
						ved_candidate_metas vcm = new ved_candidate_metas();
						vcm.setCreated(ssc.getTimeStamp(objData_meta1
								.get("created").toString()));
						vcm.setCandidateMetasKey(objData_meta1.get(
								"candidateMetasKey").toString());
						vcm.setModified(ssc.getTimeStamp(objData_meta1.get(
								"modified").toString()));
						vcm.setCandidateMetasValue(objData_meta1.get(
								"candidateMetasValue").toString());
						vcm.setCandidateId(vca.getCandidateId());
						sessionFactory.getCurrentSession().save(vcm);
					}
					sessionFactory.getCurrentSession().clear();
				} catch (Exception ex) {
					cf.viewAlert(" error ved_candidate metas "
							+ ex.getMessage());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_candidates.class);
				criteria.add(Restrictions.eq("id", vca.getCandidateId()));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					ved_candidates vdc = (ved_candidates) it.next();
					objView.put("id_CompositeFromServer", vdc.getId_composite());
					getBack = objView.toString();
				}

			} else if (action.equalsIgnoreCase("edit")) {

				int schoolID = -1;
				Long idTable = Long.valueOf(-1);
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					schoolID = vc.getCompanyId();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_candidates.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));
					criteria.add(Restrictions.eq("schoolId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_candidates vcan = (ved_candidates) it.next();
						idTable = vcan.getCandidateId();
					}
				}

				int custID = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CustCompany_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vsc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CustId_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vsc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						custID = vr.getRoleId();
					}
				}

				int createdBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						createdBy = vr.getRoleId();
					}
				}

				int modifiedBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						modifiedBy = vr.getRoleId();
					}
				}

				String sql = "update ved_candidates set custId=:custId,"
						+ "schoolId=:schoolId,created=:created,"
						+ "createdBy=:createdBy,modified=:modified,"
						+ "modifiedBy=:modifiedBy,status=:status "
						+ " where candidateId=:candidateId";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)

						.setLong("candidateId", idTable)
						.setInteger("schoolId", schoolID)
						.setInteger("custId", custID)
						.setTimestamp(
								"created",
								ssc.getTimeStamp(dataVrs_users.get("created")
										.toString()))
						.setInteger("createdBy", createdBy)
						.setTimestamp(
								"modified",
								ssc.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modifiedBy", modifiedBy)
						.setInteger(
								"status",
								Integer.valueOf(dataVrs_users.get("status")
										.toString()))

						.executeUpdate();

				sql = "SELECT last_value from vrs_sync_items_id_seq";
				it = sessionFactory.getCurrentSession().createSQLQuery(sql)
						.list().iterator();
				int id_vrs_sync_items = 0;
				if (it.hasNext()) {
					id_vrs_sync_items = Integer.valueOf(it.next().toString());
					vrs_sync_computerlist vsc = new vrs_sync_computerlist();
					vsc.setId_computer(computer_id);
					vsc.setId_vrs_sync_items(id_vrs_sync_items);
					Timestamp tstamp = new Timestamp(new Date().getTime());
					vsc.setTime_created(tstamp);
					sessionFactory.getCurrentSession().save(vsc);
					objView.put("id_vrs_sync_items", id_vrs_sync_items);
					getBack = objView.toString();
				}

				try {
					JSONArray objData_meta = new JSONArray(obj.get(
							"ved_candidate_metas").toString());
					JSONObject objData_meta1 = null;
					int i = 0;
					for (i = 0; i < objData_meta.length(); i++) {
						objData_meta1 = objData_meta.getJSONObject(i);
						addCandidateMetas(idTable,
								objData_meta1.get("candidateMetasKey")
										.toString(),
								objData_meta1.get("candidateMetasValue")
										.toString(), ssc.getTimeStamp(objData_meta1
										.get("created").toString()),
										ssc.getTimeStamp(objData_meta1.get("modified")
										.toString()));
					}
				} catch (Exception ex) {
				}
			}
		} catch (Exception ex) {
			getBack += " error saveInTable_ved_candidates " + ex.getMessage();
			cf.viewAlert(" error saveInTable_ved_candidates " + ex.getMessage());
		}
		return getBack;
	}
	
	public String saveInTable_ved_assignments(String data, int computer_id) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL",
					obj.get("id_vrs_sync_itemsLOCAL").toString());
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("ved_assignments")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			if (action.equalsIgnoreCase("add")) {
				ved_assignments vass = new ved_assignments();

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					vass.setSchoolId(vc.getCompanyId());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CoursecompanyID_Composite").toString())));
				it = criteria.list().iterator();
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
						ved_courses vco = (ved_courses) it.next();
						vass.setCourseId(vco.getId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vass.setCreatedBy(vr.getRoleId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vass.setModifiedBy(vr.getRoleId());
					}
				}

				vass.setTitle(dataVrs_users.get("title").toString());
				vass.setDescription(dataVrs_users.get("description").toString());
				vass.setTerm(dataVrs_users.get("term").toString());
				vass.setType(dataVrs_users.get("type").toString());
				vass.setWeight(Integer.valueOf(dataVrs_users.get("weight")
						.toString()));
				vass.setCreated(ssc.getTimeStamp(dataVrs_users.get("created")
						.toString()));
				vass.setModified(ssc.getTimeStamp(dataVrs_users.get("modified")
						.toString()));
				vass.setAssignDate(ssc.getTimeStamp(dataVrs_users.get("assignDate")
						.toString()));
				vass.setDueDate(ssc.getTimeStamp(dataVrs_users.get("dueDate")
						.toString()));

				sessionFactory.getCurrentSession().save(vass);
				sessionFactory.getCurrentSession().clear();

				String sql = "SELECT last_value from vrs_sync_items_id_seq";
				it = sessionFactory.getCurrentSession().createSQLQuery(sql)
						.list().iterator();
				int id_vrs_sync_items = 0;
				if (it.hasNext()) {
					id_vrs_sync_items = Integer.valueOf(it.next().toString());
					vrs_sync_computerlist vsc = new vrs_sync_computerlist();
					vsc.setId_computer(computer_id);
					vsc.setId_vrs_sync_items(id_vrs_sync_items);
					Timestamp tstamp = new Timestamp(new Date().getTime());
					vsc.setTime_created(tstamp);
					sessionFactory.getCurrentSession().save(vsc);
					objView.put("id_vrs_sync_items", id_vrs_sync_items);
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_assignments.class);
				criteria.add(Restrictions.eq("id", vass.getId()));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					ved_assignments vassi = (ved_assignments) it.next();
					objView.put("id_CompositeFromServer",
							vassi.getId_composite());
					getBack = objView.toString();
				}
			} else if (action.equalsIgnoreCase("edit")) {

				int companyID = -1;
				long idTable = -1;
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					companyID = vc.getCompanyId();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_assignments.class);
					criteria.add(Restrictions.eq("schoolId", companyID));
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_assignments vassi = (ved_assignments) it.next();
						idTable = vassi.getId();
					}
				}

				long courseID = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CoursecompanyID_Composite").toString())));
				it = criteria.list().iterator();
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
						ved_courses vco = (ved_courses) it.next();
						courseID = vco.getId();
					}
				}

				int createdBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						createdBy = vr.getRoleId();
					}
				}

				int modifiedBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						modifiedBy = vr.getRoleId();
					}
				}

				String sql = "update ved_assignments set schoolId=:schoolId,"
						+ "courseId=:courseId,term=:term,type=:type,weight=:weight,"
						+ "created=:created,createdBy=:createdBy,assignDate=:assignDate,"
						+ "dueDate=:dueDate,modified=:modified,modifiedBy=:modifiedBy,"
						+ "status=:status,title=:title,description=:description"
						+ " where id=:id";

				sessionFactory
						.getCurrentSession()
						.createQuery(sql)

						.setLong("id", idTable)
						.setInteger("schoolId", companyID)
						.setLong("courseId", courseID)
						.setString("term", dataVrs_users.get("term").toString())
						.setString("type", dataVrs_users.get("type").toString())
						.setInteger(
								"weight",
								Integer.valueOf(dataVrs_users.get("weight")
										.toString()))
						.setTimestamp(
								"created",
								ssc.getTimeStamp(dataVrs_users.get("created")
										.toString()))
						.setInteger("createdBy", createdBy)
						.setTimestamp(
								"assignDate",
								ssc.getTimeStamp(dataVrs_users.get("assignDate")
										.toString()))
						.setTimestamp(
								"modified",
								ssc.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modifiedBy", modifiedBy)
						.setTimestamp(
								"dueDate",
								ssc.getTimeStamp(dataVrs_users.get("dueDate")
										.toString()))
						.setInteger(
								"status",
								Integer.valueOf(dataVrs_users.get("status")
										.toString()))
						.setString("title",
								dataVrs_users.get("title").toString())
						.setString("description",
								dataVrs_users.get("description").toString())

						.executeUpdate();

				sql = "SELECT last_value from vrs_sync_items_id_seq";
				it = sessionFactory.getCurrentSession().createSQLQuery(sql)
						.list().iterator();
				int id_vrs_sync_items = 0;
				if (it.hasNext()) {
					id_vrs_sync_items = Integer.valueOf(it.next().toString());
					vrs_sync_computerlist vsc = new vrs_sync_computerlist();
					vsc.setId_computer(computer_id);
					vsc.setId_vrs_sync_items(id_vrs_sync_items);
					Timestamp tstamp = new Timestamp(new Date().getTime());
					vsc.setTime_created(tstamp);
					sessionFactory.getCurrentSession().save(vsc);
					objView.put("id_vrs_sync_items", id_vrs_sync_items);
					getBack = objView.toString();
				}

			}
		} catch (Exception ex) {
			cf.viewAlert(" error saveInTable_ved_assignments "
					+ ex.getMessage());
		}
		return getBack;
	}
	
	public void saveInTable_ved_assignmentsDownload(String data) {
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("ved_assignments")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			if (action.equalsIgnoreCase("add")) {
				ved_assignments vass = new ved_assignments();

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					vass.setSchoolId(vc.getCompanyId());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CoursecompanyID_Composite").toString())));
				it = criteria.list().iterator();
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
						ved_courses vco = (ved_courses) it.next();
						vass.setCourseId(vco.getId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vass.setCreatedBy(vr.getRoleId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vass.setModifiedBy(vr.getRoleId());
					}
				}

				vass.setTitle(dataVrs_users.get("title").toString());
				vass.setDescription(dataVrs_users.get("description").toString());
				vass.setTerm(dataVrs_users.get("term").toString());
				vass.setType(dataVrs_users.get("type").toString());
				vass.setWeight(Integer.valueOf(dataVrs_users.get("weight")
						.toString()));
				vass.setCreated(ssc.getTimeStamp(dataVrs_users.get("created")
						.toString()));
				vass.setModified(ssc.getTimeStamp(dataVrs_users.get("modified")
						.toString()));
				vass.setAssignDate(ssc.getTimeStamp(dataVrs_users.get("assignDate")
						.toString()));
				vass.setDueDate(ssc.getTimeStamp(dataVrs_users.get("dueDate")
						.toString()));
				vass.setId_composite(Integer.valueOf(dataVrs_users.get(
						"id_composite").toString()));

				String sql = "ALTER TABLE ved_assignments DISABLE TRIGGER insert_ved_assignmentst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();
				sessionFactory.getCurrentSession().save(vass);
				sessionFactory.getCurrentSession().clear();
				sql = "ALTER TABLE ved_assignments ENABLE TRIGGER insert_ved_assignmentst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

			} else if (action.equalsIgnoreCase("edit")) {

				int companyID = -1;
				long idTable = -1;
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					companyID = vc.getCompanyId();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_assignments.class);
					criteria.add(Restrictions.eq("schoolId", companyID));
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_assignments vassi = (ved_assignments) it.next();
						idTable = vassi.getId();
					}
				}

				long courseID = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CoursecompanyID_Composite").toString())));
				it = criteria.list().iterator();
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
						ved_courses vco = (ved_courses) it.next();
						courseID = vco.getId();
					}
				}

				int createdBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						createdBy = vr.getRoleId();
					}
				}

				int modifiedBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						modifiedBy = vr.getRoleId();
					}
				}

				String sql = "ALTER TABLE ved_assignments DISABLE TRIGGER insert_ved_assignmentst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sql = "update ved_assignments set schoolId=:schoolId,"
						+ "courseId=:courseId,term=:term,type=:type,weight=:weight,"
						+ "created=:created,createdBy=:createdBy,assignDate=:assignDate,"
						+ "dueDate=:dueDate,modified=:modified,modifiedBy=:modifiedBy,"
						+ "status=:status,title=:title,description=:description"
						+ " where id=:id";

				sessionFactory
						.getCurrentSession()
						.createQuery(sql)

						.setLong("id", idTable)
						.setInteger("schoolId", companyID)
						.setLong("courseId", courseID)
						.setString("term", dataVrs_users.get("term").toString())
						.setString("type", dataVrs_users.get("type").toString())
						.setInteger(
								"weight",
								Integer.valueOf(dataVrs_users.get("weight")
										.toString()))
						.setTimestamp(
								"created",
								ssc.getTimeStamp(dataVrs_users.get("created")
										.toString()))
						.setInteger("createdBy", createdBy)
						.setTimestamp(
								"assignDate",
								ssc.getTimeStamp(dataVrs_users.get("assignDate")
										.toString()))
						.setTimestamp(
								"modified",
								ssc.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modifiedBy", modifiedBy)
						.setTimestamp(
								"dueDate",
								ssc.getTimeStamp(dataVrs_users.get("dueDate")
										.toString()))
						.setInteger(
								"status",
								Integer.valueOf(dataVrs_users.get("status")
										.toString()))
						.setString("title",
								dataVrs_users.get("title").toString())
						.setString("description",
								dataVrs_users.get("description").toString())

						.executeUpdate();

				sessionFactory.getCurrentSession().clear();
				sql = "ALTER TABLE ved_assignments ENABLE TRIGGER insert_ved_assignmentst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

			}
		} catch (Exception ex) {
			cf.viewAlert(" error saveInTable_ved_assignmentsDownload "
					+ ex.getMessage());
		}
	}
	
	public void saveInTable_ved_attendancesDownload(String data) {
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("ved_attendances")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			if (action.equalsIgnoreCase("add")) {
				ved_attendances vat = new ved_attendances();

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					vat.setSchoolId(vc.getCompanyId());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"enrollmentcompanyID_Composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_enrollments.class);
					criteria.add(Restrictions.eq("schoolId", vc.getCompanyId()));
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"enrollmentID_Composite").toString())));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_enrollments ven = (ved_enrollments) it.next();
						vat.setEnrollmentId(ven.getEnrollmentId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vat.setAttendanceCreatedBy(vr.getRoleId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vat.setAttendanceModifiedBy(vr.getRoleId());
					}
				}

				vat.setAttendanceDate(ssc.getDate((dataVrs_users
						.get("attendanceDate").toString())));
				vat.setAttendanceEndDate(ssc.getDate((dataVrs_users
						.get("attendanceEndDate").toString())));
				vat.setAttendanceType(dataVrs_users.get("attendanceType")
						.toString());
				vat.setAttendanceNote(dataVrs_users.get("attendanceNote")
						.toString());
				vat.setAttendanceCreated(ssc.getTimeStamp(dataVrs_users.get(
						"attendanceCreated").toString()));
				vat.setAttendanceModified(ssc.getTimeStamp(dataVrs_users.get(
						"attendanceModified").toString()));
				vat.setAttendanceStatus(Integer.valueOf(dataVrs_users.get(
						"attendanceStatus").toString()));
				vat.setAttendanceTerm(dataVrs_users.get("attendanceTerm")
						.toString());

				vat.setId_composite(Integer.valueOf(dataVrs_users.get(
						"id_composite").toString()));

				String sql = "ALTER TABLE ved_attendances DISABLE TRIGGER insert_ved_attendancest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sessionFactory.getCurrentSession().save(vat);
				sessionFactory.getCurrentSession().clear();

				sql = "ALTER TABLE ved_attendances ENABLE TRIGGER insert_ved_attendancest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

			} else if (action.equalsIgnoreCase("edit")) {

				int schoolID = -1;
				Long idTable = Long.valueOf(-1);

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					schoolID = vc.getCompanyId();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_attendances.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));
					criteria.add(Restrictions.eq("schoolId", schoolID));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_attendances vat = (ved_attendances) it.next();
						idTable = vat.getAttendanceId();
					}
				}

				Long enrollmentID = Long.valueOf(-1);
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"enrollmentcompanyID_Composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_enrollments.class);
					criteria.add(Restrictions.eq("schoolId", vc.getCompanyId()));
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"enrollmentID_Composite").toString())));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_enrollments ven = (ved_enrollments) it.next();
						enrollmentID = ven.getEnrollmentId();
					}
				}

				int createdBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						createdBy = vr.getRoleId();
					}
				}

				int modifiedBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						modifiedBy = vr.getRoleId();
					}
				}

				String sql = "ALTER TABLE ved_attendances DISABLE TRIGGER insert_ved_attendancest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sql = "update ved_attendances set schoolId=:schoolId,"
						+ " enrollmentId=:enrollmentId,"
						+ "attendanceDate=:attendanceDate,"
						+ "attendanceEndDate=:attendanceEndDate,"
						+ "attendanceType=:attendanceType,"
						+ "attendanceNote=:attendanceNote,"
						+ "attendanceCreated=:attendanceCreated,"
						+ "attendanceCreatedBy=:attendanceCreatedBy,"
						+ "attendanceModified=:attendanceModified,"
						+ "attendanceModifiedBy=:attendanceModifiedBy,"
						+ "attendanceStatus=:attendanceStatus,"
						+ "attendanceTerm=:attendanceTerm" + " where id=:id";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)

						.setLong("id", idTable)
						.setInteger("schoolId", schoolID)
						.setLong("enrollmentId", enrollmentID)

						.setDate(
								"attendanceDate",
								ssc.getDate(dataVrs_users.get("attendanceDate")
										.toString()))
						.setDate(
								"attendanceEndDate",
								ssc.getDate(dataVrs_users.get("attendanceEndDate")
										.toString()))
						.setString("attendanceType",
								dataVrs_users.get("attendanceType").toString())
						.setString("attendanceNote",
								dataVrs_users.get("attendanceNote").toString())
						.setTimestamp(
								"attendanceCreated",
								ssc.getTimeStamp(dataVrs_users.get(
										"attendanceCreated").toString()))
						.setInteger("attendanceCreatedBy", createdBy)
						.setTimestamp(
								"attendanceModified",
								ssc.getTimeStamp(dataVrs_users.get(
										"attendanceModified").toString()))
						.setInteger("attendanceModifiedBy", modifiedBy)
						.setInteger(
								"attendanceStatus",
								Integer.valueOf(dataVrs_users.get(
										"attendanceStatus").toString()))
						.setString("attendanceTerm",
								dataVrs_users.get("attendanceTerm").toString())

						.executeUpdate();

				sql = "ALTER TABLE ved_attendances ENABLE TRIGGER insert_ved_attendancest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

			}
		} catch (Exception ex) {
			cf.viewAlert(" error saveInTable_ved_attendances "
					+ ex.getMessage());
		}
	}
	
	public String saveInTable_ved_attendances(String data, int computer_id) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL",
					obj.get("id_vrs_sync_itemsLOCAL").toString());
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("ved_attendances")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			if (action.equalsIgnoreCase("add")) {
				ved_attendances vat = new ved_attendances();

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					vat.setSchoolId(vc.getCompanyId());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"enrollmentcompanyID_Composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_enrollments.class);
					criteria.add(Restrictions.eq("schoolId", vc.getCompanyId()));
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"enrollmentID_Composite").toString())));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_enrollments ven = (ved_enrollments) it.next();
						vat.setEnrollmentId(ven.getEnrollmentId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vat.setAttendanceCreatedBy(vr.getRoleId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vat.setAttendanceModifiedBy(vr.getRoleId());
					}
				}

				vat.setAttendanceDate(ssc.getDate((dataVrs_users
						.get("attendanceDate").toString())));
				vat.setAttendanceEndDate(ssc.getDate((dataVrs_users
						.get("attendanceEndDate").toString())));
				vat.setAttendanceType(dataVrs_users.get("attendanceType")
						.toString());
				vat.setAttendanceNote(dataVrs_users.get("attendanceNote")
						.toString());
				vat.setAttendanceCreated(ssc.getTimeStamp(dataVrs_users.get(
						"attendanceCreated").toString()));
				vat.setAttendanceModified(ssc.getTimeStamp(dataVrs_users.get(
						"attendanceModified").toString()));
				vat.setAttendanceStatus(Integer.valueOf(dataVrs_users.get(
						"attendanceStatus").toString()));
				vat.setAttendanceTerm(dataVrs_users.get("attendanceTerm")
						.toString());

				sessionFactory.getCurrentSession().save(vat);
				sessionFactory.getCurrentSession().clear();

				String sql = "SELECT last_value from vrs_sync_items_id_seq";
				it = sessionFactory.getCurrentSession().createSQLQuery(sql)
						.list().iterator();
				int id_vrs_sync_items = 0;
				if (it.hasNext()) {
					id_vrs_sync_items = Integer.valueOf(it.next().toString());
					vrs_sync_computerlist vsc = new vrs_sync_computerlist();
					vsc.setId_computer(computer_id);
					vsc.setId_vrs_sync_items(id_vrs_sync_items);
					Timestamp tstamp = new Timestamp(new Date().getTime());
					vsc.setTime_created(tstamp);
					sessionFactory.getCurrentSession().save(vsc);
					objView.put("id_vrs_sync_items", id_vrs_sync_items);
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_attendances.class);
				criteria.add(Restrictions.eq("id", vat.getAttendanceId()));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					ved_attendances vatt = (ved_attendances) it.next();
					objView.put("id_CompositeFromServer",
							vatt.getId_composite());
					getBack = objView.toString();
				}

			} else if (action.equalsIgnoreCase("edit")) {

				int schoolID = -1;
				Long idTable = Long.valueOf(-1);

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					schoolID = vc.getCompanyId();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_attendances.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));
					criteria.add(Restrictions.eq("schoolId", schoolID));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_attendances vat = (ved_attendances) it.next();
						idTable = vat.getAttendanceId();
					}
				}

				Long enrollmentID = Long.valueOf(-1);
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"enrollmentcompanyID_Composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_enrollments.class);
					criteria.add(Restrictions.eq("schoolId", vc.getCompanyId()));
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"enrollmentID_Composite").toString())));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_enrollments ven = (ved_enrollments) it.next();
						enrollmentID = ven.getEnrollmentId();
					}
				}

				int createdBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						createdBy = vr.getRoleId();
					}
				}

				int modifiedBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						modifiedBy = vr.getRoleId();
					}
				}

				String sql = "update ved_attendances set schoolId=:schoolId,"
						+ " enrollmentId=:enrollmentId,"
						+ "attendanceDate=:attendanceDate,"
						+ "attendanceEndDate=:attendanceEndDate,"
						+ "attendanceType=:attendanceType,"
						+ "attendanceNote=:attendanceNote,"
						+ "attendanceCreated=:attendanceCreated,"
						+ "attendanceCreatedBy=:attendanceCreatedBy,"
						+ "attendanceModified=:attendanceModified,"
						+ "attendanceModifiedBy=:attendanceModifiedBy,"
						+ "attendanceStatus=:attendanceStatus,"
						+ "attendanceTerm=:attendanceTerm" + " where id=:id";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)

						.setLong("id", idTable)
						.setInteger("schoolId", schoolID)
						.setLong("enrollmentId", enrollmentID)

						.setDate(
								"attendanceDate",
								ssc.getDate(dataVrs_users.get("attendanceDate")
										.toString()))
						.setDate(
								"attendanceEndDate",
								ssc.getDate(dataVrs_users.get("attendanceEndDate")
										.toString()))
						.setString("attendanceType",
								dataVrs_users.get("attendanceType").toString())
						.setString("attendanceNote",
								dataVrs_users.get("attendanceNote").toString())
						.setTimestamp(
								"attendanceCreated",
								ssc.getTimeStamp(dataVrs_users.get(
										"attendanceCreated").toString()))
						.setInteger("attendanceCreatedBy", createdBy)
						.setTimestamp(
								"attendanceModified",
								ssc.getTimeStamp(dataVrs_users.get(
										"attendanceModified").toString()))
						.setInteger("attendanceModifiedBy", modifiedBy)
						.setInteger(
								"attendanceStatus",
								Integer.valueOf(dataVrs_users.get(
										"attendanceStatus").toString()))
						.setString("attendanceTerm",
								dataVrs_users.get("attendanceTerm").toString())

						.executeUpdate();

				sql = "SELECT last_value from vrs_sync_items_id_seq";
				it = sessionFactory.getCurrentSession().createSQLQuery(sql)
						.list().iterator();
				int id_vrs_sync_items = 0;
				if (it.hasNext()) {
					id_vrs_sync_items = Integer.valueOf(it.next().toString());
					vrs_sync_computerlist vsc = new vrs_sync_computerlist();
					vsc.setId_computer(computer_id);
					vsc.setId_vrs_sync_items(id_vrs_sync_items);
					Timestamp tstamp = new Timestamp(new Date().getTime());
					vsc.setTime_created(tstamp);
					sessionFactory.getCurrentSession().save(vsc);
					objView.put("id_vrs_sync_items", id_vrs_sync_items);
					getBack = objView.toString();
				}
			}
		} catch (Exception ex) {
			cf.viewAlert(" error saveInTable_ved_attendances "
					+ ex.getMessage());
		}
		return getBack;
	}
	
	public String saveInTable_ved_courses(String data, int computer_id) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL",
					obj.get("id_vrs_sync_itemsLOCAL").toString());
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("ved_courses").toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			String sql = "";
			if (action.equalsIgnoreCase("add")) {
				ved_courses vc = new ved_courses();

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					vc.setSchoolId(vcom.getCompanyId());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ClasscompanyID_Composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_classes.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ClassId_Composite").toString())));
					criteria.add(Restrictions.eq("school_id",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_classes vcl = (ved_classes) it.next();
						vc.setClassId(vcl.getId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vc.setCreatedBy(vr.getRoleId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vc.setModifiedBy(vr.getRoleId());
					}
				}

				sql = "select id  from ved_course_list where id_composite=:id_composite";
				it = sessionFactory
						.getCurrentSession()
						.createSQLQuery(sql)

						.setInteger(
								"id_composite",
								Integer.valueOf(dataVrs_users.get(
										"idved_course_list_composite")
										.toString()))

						.list().iterator();
				if (it.hasNext()) {
					vc.setId_ved_course_list(Integer.valueOf(it.next()
							.toString()));
				}

				vc.setDescription(dataVrs_users.get("description").toString());
				vc.setGrade(dataVrs_users.get("grade").toString());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"instructor_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"instructor_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vc.setInstructor(vr.getRoleId());
					}
				}

				String avgScore = dataVrs_users.get("avgScore").toString();
				if (avgScore.length() > 1)
					vc.setAvgScore(Float.valueOf(avgScore));

				vc.setCreated(ssc.getTimeStamp(dataVrs_users.get("created")
						.toString()));
				vc.setModified(ssc.getTimeStamp(dataVrs_users.get("modified")
						.toString()));
				vc.setStatus(Integer.valueOf(dataVrs_users.get("status")
						.toString()));

				sessionFactory.getCurrentSession().save(vc);
				sessionFactory.getCurrentSession().clear();

				sql = "SELECT last_value from vrs_sync_items_id_seq";
				it = sessionFactory.getCurrentSession().createSQLQuery(sql)
						.list().iterator();
				int id_vrs_sync_items = 0;
				if (it.hasNext()) {
					id_vrs_sync_items = Integer.valueOf(it.next().toString());
					vrs_sync_computerlist vsc = new vrs_sync_computerlist();
					vsc.setId_computer(computer_id);
					vsc.setId_vrs_sync_items(id_vrs_sync_items);
					Timestamp tstamp = new Timestamp(new Date().getTime());
					vsc.setTime_created(tstamp);
					sessionFactory.getCurrentSession().save(vsc);
					objView.put("id_vrs_sync_items", id_vrs_sync_items);
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						ved_courses.class);
				criteria.add(Restrictions.eq("id", vc.getId()));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					ved_courses vco = (ved_courses) it.next();
					objView.put("id_CompositeFromServer", vco.getId_composite());
					getBack = objView.toString();
				}
			} else if (action.equalsIgnoreCase("edit")) {
				int schoolID = -1;
				Long idTable = Long.valueOf(0);

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					schoolID = vcom.getCompanyId();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_courses.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));
					criteria.add(Restrictions.eq("schoolId", schoolID));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_courses vdc = (ved_courses) it.next();
						idTable = vdc.getId();
					}
				}
				Long classId = Long.valueOf(-1);
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ClasscompanyID_Composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_classes.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ClassId_Composite").toString())));
					criteria.add(Restrictions.eq("school_id",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_classes vcl = (ved_classes) it.next();
						classId = vcl.getId();
					}
				}

				int createdBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						createdBy = vr.getRoleId();
					}
				}

				int modifiedBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						modifiedBy = vr.getRoleId();
					}
				}

				int id_ved_course_list = -1;
				sql = "select id  from ved_course_list where id_composite=:id_composite";
				it = sessionFactory
						.getCurrentSession()
						.createSQLQuery(sql)

						.setInteger(
								"id_composite",
								Integer.valueOf(dataVrs_users.get(
										"idved_course_list_composite")
										.toString()))

						.list().iterator();
				if (it.hasNext()) {
					id_ved_course_list = Integer.valueOf(it.next().toString());
				}

				String avgScore = dataVrs_users.get("avgScore").toString();
				Float avgScoreii = null;
				if (avgScore.length() > 0)
					avgScoreii = Float.valueOf(avgScore);

				int instructorI = 0;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"instructor_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"instructor_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						instructorI = vr.getRoleId();
					}
				}

				sql = "update ved_courses set schoolId=:schoolId,classId=:classId, "
						+ " description=:description,grade=:grade, "
						+ " instructor=:instructor,avgScore=:avgScore, "
						+ " created=:created,createdBy=:createdBy, "
						+ " modified=:modified,modifiedBy=:modifiedBy,status=:status, "
						+ " id_ved_course_list=:id_ved_course_list "
						+ "  where id=:id";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)
						.setLong("id", idTable)
						.setInteger("schoolId", schoolID)
						.setLong("classId", classId)
						.setString("description",
								dataVrs_users.get("description").toString())
						.setString("grade",
								dataVrs_users.get("grade").toString())
						.setInteger("instructor", instructorI)
						.setFloat("avgScore", avgScoreii)
						.setTimestamp(
								"created",
								ssc.getTimeStamp(dataVrs_users.get("created")
										.toString()))
						.setInteger("createdBy", createdBy)
						.setInteger("id_ved_course_list", id_ved_course_list)
						.setTimestamp(
								"modified",
								ssc.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modifiedBy", modifiedBy)
						.setInteger(
								"status",
								Integer.valueOf(dataVrs_users.get("status")
										.toString()))

						.executeUpdate();

				sql = "SELECT last_value from vrs_sync_items_id_seq";
				it = sessionFactory.getCurrentSession().createSQLQuery(sql)
						.list().iterator();
				int id_vrs_sync_items = 0;
				if (it.hasNext()) {
					id_vrs_sync_items = Integer.valueOf(it.next().toString());
					vrs_sync_computerlist vsc = new vrs_sync_computerlist();
					vsc.setId_computer(computer_id);
					vsc.setId_vrs_sync_items(id_vrs_sync_items);
					Timestamp tstamp = new Timestamp(new Date().getTime());
					vsc.setTime_created(tstamp);
					sessionFactory.getCurrentSession().save(vsc);
					objView.put("id_vrs_sync_items", id_vrs_sync_items);
					getBack = objView.toString();
				}
			}
		} catch (Exception ex) {
			getBack += " error saveInTable_ved_courses " + ex.getMessage();
		}

		return getBack;
	}
	
	public void saveInTable_ved_reportsDownload(String data) {
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("ved_reports").toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			String sql = "";
			if (action.equalsIgnoreCase("add")) {
				int schoolID = -1;
				Long idTAble = Long.valueOf(0);
				sql = "select nextval('vrs_reports_id_seq') as nextValue";
				Iterator it = sessionFactory.getCurrentSession()
						.createSQLQuery(sql).list().iterator();
				if (it.hasNext()) {
					idTAble = Long.valueOf(it.next().toString());
				}

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					schoolID = vcom.getCompanyId();
				}

				Long enrollment_id = Long.valueOf(-1);
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"enrollmentcompanyID_Composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_enrollments.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"enrollmentID_Composite").toString())));
					criteria.add(Restrictions.eq("schoolId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_enrollments ven = (ved_enrollments) it.next();
						enrollment_id = ven.getEnrollmentId();
					}
				}

				int createdBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						createdBy = vr.getRoleId();
					}
				}

				int modifiedBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						modifiedBy = vr.getRoleId();
					}
				}

				String attendance = "'"
						+ dataVrs_users.get("attendance").toString() + "'";
				String scores = "'" + dataVrs_users.get("scores").toString()
						+ "'";

				sql = "ALTER TABLE ved_reports DISABLE TRIGGER insert_ved_reportst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sql = " insert into ved_reports (id,school_id,enrollment_id,term,scores,avg_total_score,comment,"
						+ " attendance,created,created_by,"
						+ "modified,modified_by,status,id_composite) values (:id,:school_id,:enrollment_id,:term,"
						+ scores
						+ ","
						+ ":avg_total_score,:comment,"
						+ " "
						+ attendance
						+ ",:created,:created_by,"
						+ ":modified,:modified_by,:status,:id_composite);";

				sessionFactory
						.getCurrentSession()
						.createSQLQuery(sql)
						.setLong("id", idTAble)
						.setInteger("school_id", schoolID)
						.setLong("enrollment_id", enrollment_id)
						.setString("term", dataVrs_users.get("term").toString())
						.setFloat(
								"avg_total_score",
								Float.valueOf(dataVrs_users
										.get("avgTotalScore").toString()))
						.setString("comment",
								dataVrs_users.get("comment").toString())
						.setTimestamp(
								"created",
								ssc.getTimeStamp(dataVrs_users.get("created")
										.toString()))
						.setInteger("created_by", createdBy)
						.setTimestamp(
								"modified",
								ssc.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modified_by", modifiedBy)
						.setInteger(
								"status",
								Integer.valueOf(dataVrs_users.get("status")
										.toString()))

						.setInteger(
								"id_composite",
								Integer.valueOf(dataVrs_users.get(
										"id_composite").toString()))

						.executeUpdate();
				sessionFactory.getCurrentSession().clear();

				sql = "ALTER TABLE ved_reports ENABLE TRIGGER insert_ved_reportst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

			} else if (action.equalsIgnoreCase("edit")) {
				int schoolID = -1;
				Long idTAble = Long.valueOf(0);

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					schoolID = vcom.getCompanyId();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_reports.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));
					criteria.add(Restrictions.eq("schoolId", schoolID));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_reports vre = (ved_reports) it.next();
						idTAble = vre.getId();
					}
				}

				Long enrollment_id = Long.valueOf(-1);
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"enrollmentcompanyID_Composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(ved_enrollments.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"enrollmentID_Composite").toString())));
					criteria.add(Restrictions.eq("schoolId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						ved_enrollments ven = (ved_enrollments) it.next();
						enrollment_id = ven.getEnrollmentId();
					}
				}

				int createdBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"CreatedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						createdBy = vr.getRoleId();
					}
				}

				int modifiedBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vcom = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"ModifiedBy_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vcom.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						modifiedBy = vr.getRoleId();
					}
				}

				String attendance = "'"
						+ dataVrs_users.get("attendance").toString() + "'";
				String scores = "'" + dataVrs_users.get("scores").toString()
						+ "'";

				sql = "ALTER TABLE ved_reports DISABLE TRIGGER insert_ved_reportst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sql = " update ved_reports set school_id=:school_id,"
						+ " enrollment_id=:enrollment_id,"
						+ " term=:term,scores="
						+ scores
						+ ",avg_total_score=:avg_total_score,"
						+ " comment=:comment,"
						+ " attendance="
						+ attendance
						+ " ,created=:created,"
						+ " created_by=:created_by,"
						+ " modified=:modified,modified_by=:modified_by,status=:status"
						+ " where id=:id";

				sessionFactory
						.getCurrentSession()
						.createSQLQuery(sql)
						.setLong("id", idTAble)
						.setInteger("school_id", schoolID)
						.setLong("enrollment_id", enrollment_id)
						.setString("term", dataVrs_users.get("term").toString())
						.setFloat(
								"avg_total_score",
								Float.valueOf(dataVrs_users
										.get("avgTotalScore").toString()))
						.setString("comment",
								dataVrs_users.get("comment").toString())
						.setTimestamp(
								"created",
								ssc.getTimeStamp(dataVrs_users.get("created")
										.toString()))
						.setInteger("created_by", createdBy)
						.setTimestamp(
								"modified",
								ssc.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modified_by", modifiedBy)
						.setInteger(
								"status",
								Integer.valueOf(dataVrs_users.get("status")
										.toString()))

						.executeUpdate();
				sessionFactory.getCurrentSession().clear();

				sql = "ALTER TABLE ved_reports ENABLE TRIGGER insert_ved_reportst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

			}

		} catch (Exception ex) {
			cf.viewAlert(" error saveInTable_ved_reportsDownload "
					+ ex.getMessage());
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String getDataREadyUpload() {
		String getBack = "", listCompany = "";

		Criteria criteriaAdd = sessionFactory.getCurrentSession()
				.createCriteria(vrs_sync_items.class);
		Criteria criteriaEdit = sessionFactory.getCurrentSession()
				.createCriteria(vrs_sync_items.class);
		Iterator it = null;

		criteriaAdd.add(Restrictions.eq("status", false));
		criteriaAdd.add(Restrictions.eq("action", "add"));
		criteriaEdit.add(Restrictions.eq("status", false));
		criteriaEdit.add(Restrictions.eq("action", "edit"));

		criteriaAdd.addOrder(Order.asc("id"));
		criteriaEdit.addOrder(Order.asc("id"));
		if (criteriaAdd.list().size() > 0) {
			criteriaAdd.setMaxResults(1);
			it = criteriaAdd.list().iterator();
		} else {
			criteriaEdit.setMaxResults(1);
			it = criteriaEdit.list().iterator();
		}

		if (it.hasNext()) {
			vrs_sync_items vsi = (vrs_sync_items) it.next();
			cf.viewAlert("id vsdi = " + vsi.getId() + " tablefor upload = "
					+ vsi.getTable_name());
			if (vsi.getTable_name().equalsIgnoreCase("ved_scores")) {
				getBack = getved_scores_DAta(Long.valueOf(vsi.getId_table()),
						vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("ved_students")) {
				getBack = getved_students_DAta(Long.valueOf(vsi.getId_table()),
						vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("ved_reports")) {
				getBack = getved_reports_DAta(Long.valueOf(vsi.getId_table()),
						vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("ved_courses")) {
				getBack = getved_courses_DAta(Long.valueOf(vsi.getId_table()),
						vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("ved_enrollments")) {
				getBack = getved_enrollments_DAta(
						Long.valueOf(vsi.getId_table()), vsi.getAction(),
						vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("ved_assignments")) {
				getBack = getved_assignments_DAta(
						Long.valueOf(vsi.getId_table()), vsi.getAction(),
						vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("ved_attendances")) {
				getBack = getved_attendances_DAta(
						Long.valueOf(vsi.getId_table()), vsi.getAction(),
						vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("ved_candidates")) {
				getBack = getved_candidates_DAta(
						Long.valueOf(vsi.getId_table()), vsi.getAction(),
						vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("ved_classes")) {
				getBack = getved_classes_DAta(Long.valueOf(vsi.getId_table()),
						vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("ved_course_list")) {
				getBack = getved_course_list_DAta(vsi.getId_tableInteger(),
						vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase(
					"ved_course_schedule")) {
				getBack = getved_course_schedule_DAta(vsi.getId_tableInteger(),
						vsi.getAction(), vsi.getId());
			}
		}
		cf.viewAlert(" getBack = " + getBack);
		return getBack;
	}
	
	
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String getDAtaDownload(int user_id, int computer_id) {
		String getBack = "", sql = "";
		if (user_id == 1) {
			sql = "select * from (select a.id,a.company_id,a.id_composite,a.table_name,a.action"
					+ // 4
					",a.status,a.time_created,a.id_table,"
					+ // 7
					"(select case when (select count(*) "
					+ "from vrs_sync_computerlist b where b.id_computer=:compID and b.id_vrs_sync_items=a.id)>0 "
					+ "then 1 else 0 end ) as exist  " + // 8
					"from vrs_sync_items a) as Data where exist=0 order by id ";

		} else if (user_id > 1) {
			String CompanyListForDownload = ssc.getcompanyList(computer_id);
			String conditionCompany = "";
			if (CompanyListForDownload.length() > 0) {
				conditionCompany = " company_id in (" + CompanyListForDownload
						+ ") or ";
			}
			sql = "select * from (select a.id,a.company_id,a.id_composite,a.table_name,a.action"
					+ // 4
					",a.status,a.time_created,a.id_table,"
					+ // 7
					"(select case when (select count(*) "
					+ "from vrs_sync_computerlist b where b.id_computer=:compID and b.id_vrs_sync_items=a.id)>0 "
					+ "then 1 else 0 end ) as exist  "
					+ // 8
					"from vrs_sync_items a) as Data where exist=0 "
					+ " and ("
					+ conditionCompany
					+ " table_name in ('ved_course_list','vrs_users','vrs_page_registry'"
					+ ",'vrs_service','vrs_user_guide','vrs_server_registry'))  order by id ";
		}

		String sqlstudentLimit = sql + " limit 57;";
		sql += "  limit 1;";

		cf.viewAlert("getDAtaDownload sql = " + sql + " computer_id =  "
				+ computer_id);

		Iterator it = sessionFactory.getCurrentSession().createSQLQuery(sql)
				.setInteger("compID", computer_id).list().iterator();

		String table_name = "";
		int id_table = 0;
		if (it.hasNext()) {
			Object[] obj = (Object[]) it.next();
			table_name = obj[3].toString();
			cf.viewAlert("getDAta ACademic Download table_name = " + table_name);
			id_table = Integer.valueOf(String.valueOf(obj[7].toString()));
			if (table_name.equalsIgnoreCase("vrs_file_library")) {
				getBack = getved_scores_DAtaDownload(Long.valueOf(id_table),
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name.equalsIgnoreCase("ved_students")) {
				getBack = getved_students_DAtaDownload(Long.valueOf(id_table),
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name.equalsIgnoreCase("ved_enrollments")) {
				getBack = getved_enrollments_DAtaDownload(
						Long.valueOf(id_table), obj[4].toString(),
						Integer.valueOf(obj[0].toString()));
			} else if (table_name.equalsIgnoreCase("ved_reports")) {
				getBack = getved_reports_DAtaDownload(Long.valueOf(id_table),
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name.equalsIgnoreCase("ved_courses")) {
				getBack = getved_courses_DAtaDownload(Long.valueOf(id_table),
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name.equalsIgnoreCase("ved_assignments")) {
				getBack = getved_assignments_DAtaDownload(
						Long.valueOf(id_table), obj[4].toString(),
						Integer.valueOf(obj[0].toString()));
			} else if (table_name.equalsIgnoreCase("ved_attendances")) {
				getBack = getved_attendances_DAtaDownload(
						Long.valueOf(id_table), obj[4].toString(),
						Integer.valueOf(obj[0].toString()));
			} else if (table_name.equalsIgnoreCase("ved_candidates")) {
				getBack = getved_candidates_DAtaDownload(
						Long.valueOf(id_table), obj[4].toString(),
						Integer.valueOf(obj[0].toString()));
			} else if (table_name.equalsIgnoreCase("ved_classes")) {
				getBack = getved_classes_DAtaDownload(Long.valueOf(id_table),
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name.equalsIgnoreCase("ved_course_list")) {
				getBack = getved_course_list_DAtaDownload(id_table,
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name.equalsIgnoreCase("ved_course_schedule")) {
				getBack = getved_course_schedule_DAtaDownload(id_table,
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			}
		}
		cf.viewAlert("getDAtaDownload getBack = " + getBack);
		return getBack;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String receiveUploadData(String data, int computer_id) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			String tableName = obj.get("tableName").toString();
			cf.viewAlert("receiveUploadData tableName = " + tableName);
			if (tableName.equalsIgnoreCase("ved_assignments")) {
				getBack = saveInTable_ved_assignments(data, computer_id);
			} else if (tableName.equalsIgnoreCase("ved_attendances")) {
				getBack = saveInTable_ved_attendances(data, computer_id);
			} else if (tableName.equalsIgnoreCase("ved_candidates")) {
				getBack = saveInTable_ved_candidates(data, "", computer_id);
			} else if (tableName.equalsIgnoreCase("ved_classes")) {
				getBack = saveInTable_ved_classes(data, computer_id);
			} else if (tableName.equalsIgnoreCase("ved_course_list")) {
				getBack = saveInTable_ved_course_list(data, computer_id);
			} else if (tableName.equalsIgnoreCase("ved_course_schedule")) {
				getBack = saveInTable_ved_course_schedule(data, computer_id);
			} else if (tableName.equalsIgnoreCase("ved_courses")) {
				getBack = saveInTable_ved_courses(data, computer_id);
			} else if (tableName.equalsIgnoreCase("ved_enrollments")) {
				getBack = saveInTable_ved_enrollments(data, computer_id);
			} else if (tableName.equalsIgnoreCase("ved_reports")) {
				getBack = saveInTable_ved_reports(data, computer_id);
			} else if (tableName.equalsIgnoreCase("ved_scores")) {
				getBack = saveInTable_ved_scores(data, computer_id);
			} else if (tableName.equalsIgnoreCase("ved_students")) {
				getBack = saveInTable_ved_students(data, computer_id);
			} else if (tableName.equalsIgnoreCase("vrs_addresses")) {
				getBack = sVRS.saveInTable_vrs_addresses(data, computer_id);
			} else if (tableName.equalsIgnoreCase("vrs_attendances")) {
				getBack = sVRS.saveInTable_vrs_attendances(data, "upload",
						computer_id);
			} else if (tableName.equalsIgnoreCase("vrs_subscriptions")) {
				getBack = sVRS.saveInTable_vrs_subscriptions(data, "upload",
						computer_id);
			} else if (tableName.equalsIgnoreCase("vrs_taxonomies")) {
				getBack = sVRS.saveInTable_vrs_taxonomies(data, "upload",
						computer_id);
			} else if (tableName.equalsIgnoreCase("students")) {
				getBack = sacc.saveInTable_students(data, computer_id);
			} else if (tableName.equalsIgnoreCase("transactions1")) {
				getBack = sacc.saveIn_transactions1(data, computer_id);
			}
		} catch (Exception ex) {
			getBack += " error receiveUploadData e " + ex.getMessage();

		}
		cf.viewAlert("receiveUploadData getBack = " + getBack);
		return getBack;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean saveDAtaFromDownload(String data) {
		boolean getBack = false;
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			String tableNAme = obj.get("tableName").toString();
			cf.viewAlert("saveDAtaFromDownload " + tableNAme);
			if (tableNAme.equalsIgnoreCase("ved_assignments")) {
				saveInTable_ved_assignmentsDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("ved_attendances")) {
				saveInTable_ved_attendancesDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("ved_candidates")) {
				saveInTable_ved_candidatesDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("ved_classes")) {
				saveInTable_ved_classesDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("ved_course_list")) {
				saveInTable_ved_course_listDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("ved_course_schedule")) {
				saveInTable_ved_course_scheduleDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("ved_courses")) {
				saveInTable_ved_coursesDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("ved_enrollments")) {
				saveInTable_ved_enrollmentsDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("ved_reports")) {
				saveInTable_ved_reportsDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("ved_scores")) {
				saveInTable_ved_scoresDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("ved_students")) {
				saveInTable_ved_studentsDownload(data);
				getBack = true;			
			}
		} catch (Exception ex) {
			cf.viewAlert(" error saveDAtaFromDownload " + ex.getMessage());
		}
		return getBack;
	}
}
