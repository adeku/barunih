package verse.sync.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import verse.commonClass.CommonFunction;
import verse.model.vrs_companies;
import verse.model.vrs_company_metas;
import verse.model.vrs_file_library;
import verse.model.vrs_logs;
import verse.model.vrs_role_metas;
import verse.model.vrs_roles;
import verse.model.vrs_rule_policies;
import verse.model.vrs_user_guide;
import verse.model.vrs_user_metas;
import verse.model.vrs_users;
import verse.roleAccess.model.vrs_access;
import verse.roleAccess.model.vrs_page_registry;
import verse.roleAccess.model.vrs_service;
import verse.sync.model.vrs_server_registry;
import verse.sync.model.vrs_sync_computerlist;
import verse.sync.model.vrs_sync_items;

@Service("serviceSync")
public class serviceSyncImpl implements serviceSync {

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private serviceSyncVRS sVRS;
	@Autowired
	private serviceSyncAcademic_Accounting sacc;
	@Autowired
	private serviceSyncAccounting ssac;
	@Autowired
	private serviceSyncAcademic ssa;

	CommonFunction cf = new CommonFunction();

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int getComputerIdSync() {
		int getBack = -1;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_server_registry.class);
		criteria.addOrder(Order.asc("id"));
		Iterator it = criteria.list().iterator();
		if (it.hasNext()) {
			vrs_server_registry vsr = (vrs_server_registry) it.next();
			getBack = vsr.getId();
		}
		return getBack;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String getComputerName(int compid) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_server_registry.class);
		criteria.add(Restrictions.eq("id", compid));
		Iterator it = criteria.list().iterator();
		if (it.hasNext()) {
			vrs_server_registry vsr = (vrs_server_registry) it.next();
			getBack = vsr.getComputer_name();
		}
		return getBack;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String getUser(int compid) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_server_registry.class);
		criteria.add(Restrictions.eq("id", compid));
		Iterator it = criteria.list().iterator();
		if (it.hasNext()) {
			vrs_server_registry vsr = (vrs_server_registry) it.next();
			getBack = vsr.getUserserver();
		}
		return getBack;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String getPassword(int compid) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_server_registry.class);
		criteria.add(Restrictions.eq("id", compid));
		Iterator it = criteria.list().iterator();
		if (it.hasNext()) {
			vrs_server_registry vsr = (vrs_server_registry) it.next();
			getBack = vsr.getPassword();
		}
		return getBack;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String getCompamyList(int compid) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_server_registry.class);
		criteria.add(Restrictions.eq("id", compid));
		Iterator it = criteria.list().iterator();
		if (it.hasNext()) {
			vrs_server_registry vsr = (vrs_server_registry) it.next();
			getBack = vsr.getCompany_list();
		}
		return getBack;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean authenticationCheck(String compName, String user,
			String password) {
		boolean getBack = false;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_server_registry.class);
		criteria.add(Restrictions.eq("computer_name", compName));
		criteria.add(Restrictions.eq("userserver", user));
		criteria.add(Restrictions.eq("password", password));
		if (criteria.list().size() > 0)
			getBack = true;
		return getBack;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String getvrs_userus_DAta(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_users.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		if (it.hasNext()) {
			vrs_users vu = (vrs_users) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vrs_users");
				obj.put("id_vrs_sync_itemsLOCAL", id_vrs_sync_items);

				JSONObject objColumn = new JSONObject();
				objColumn.put("created", vu.getCreated());
				objColumn.put("modified", vu.getModified());
				objColumn.put("parent", vu.getParent());
				obj.append("vrs_users", objColumn);

				if (action.equalsIgnoreCase("edit"))
					objColumn.put("userCompositeID", vu.getId_composite());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_user_metas.class);
				criteria.add(Restrictions.eq("user_id", id_table));
				it = criteria.list().iterator();
				while (it.hasNext()) {
					vrs_user_metas vum = (vrs_user_metas) it.next();
					objColumn = new JSONObject();
					objColumn.put("key", vum.getKey());
					objColumn.put("value", vum.getValue());
					objColumn.put("created", vum.getCreated());
					objColumn.put("modified", vum.getModified());
					obj.append("vrs_user_metas", objColumn);
				}

				getBack = obj.toString();
			} catch (Exception ex) {
			}
		}
		return getBack;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String getvrs_userus_DAtaForDownload(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_users.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		if (it.hasNext()) {
			vrs_users vu = (vrs_users) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vrs_users");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);

				JSONObject objColumn = new JSONObject();
				objColumn.put("created", vu.getCreated());
				objColumn.put("modified", vu.getModified());
				objColumn.put("parent", vu.getParent());
				objColumn.put("id_composite", vu.getId_composite());
				obj.append("vrs_users", objColumn);

				if (action.equalsIgnoreCase("edit"))
					objColumn.put("userCompositeID", vu.getId_composite());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_user_metas.class);
				criteria.add(Restrictions.eq("user_id", id_table));
				it = criteria.list().iterator();
				while (it.hasNext()) {
					vrs_user_metas vum = (vrs_user_metas) it.next();
					objColumn = new JSONObject();
					objColumn.put("key", vum.getKey());
					objColumn.put("value", vum.getValue());
					objColumn.put("created", vum.getCreated());
					objColumn.put("modified", vum.getModified());
					obj.append("vrs_user_metas", objColumn);
				}

				getBack = obj.toString();
			} catch (Exception ex) {
			}
		}
		return getBack;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String getvrs_roles_DAta(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_roles.class);
		criteria.add(Restrictions.eq("roleId", id_table));
		Iterator it = criteria.list().iterator();
		if (it.hasNext()) {
			vrs_roles vu = (vrs_roles) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vrs_roles");
				obj.put("id_vrs_sync_itemsLOCAL", id_vrs_sync_items);

				JSONObject objColumn = new JSONObject();
				int userId = vu.getUserId();
				int companyId = vu.getCompanyId();
				objColumn.put("role", vu.getRole());

				if (action.equalsIgnoreCase("edit"))
					objColumn.put("roleCompositeID", vu.getId_composite());

				objColumn.put("created", vu.getCreated());
				objColumn.put("modified", vu.getModified());
				objColumn.put("status", vu.getStatus());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_users.class);
				criteria.add(Restrictions.eq("id", userId));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_users vus = (vrs_users) it.next();
					objColumn.put("id_composite_users", vus.getId_composite());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", companyId));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vrc = (vrs_companies) it.next();
					objColumn.put("id_composite_companies",
							vrc.getId_composite());
				}
				obj.append("vrs_roles", objColumn);

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_role_metas.class);
				criteria.add(Restrictions.eq("roleId", id_table));
				it = criteria.list().iterator();
				while (it.hasNext()) {
					vrs_role_metas vum = (vrs_role_metas) it.next();
					objColumn = new JSONObject();
					if (vum.getRoleMetasKey().equalsIgnoreCase("people_photo")) {

						objColumn.put("roleMetasKey", vum.getRoleMetasKey());

						objColumn.put("created", vum.getCreated());
						objColumn.put("modified", vum.getModified());

						criteria = sessionFactory.getCurrentSession()
								.createCriteria(vrs_file_library.class);
						criteria.add(Restrictions.eq("id",
								Integer.valueOf(vum.getRoleMetasValue())));
						Iterator itphoto = criteria.list().iterator();
						if (itphoto.hasNext()) {
							vrs_file_library vfl = (vrs_file_library) itphoto
									.next();
							objColumn.put("roleMetasValueComposite",
									vfl.getId_composite());
						}

					} else {
						objColumn.put("roleMetasKey", vum.getRoleMetasKey());
						objColumn
								.put("roleMetasValue", vum.getRoleMetasValue());
						objColumn.put("created", vum.getCreated());
						objColumn.put("modified", vum.getModified());
					}

					obj.append("vrs_role_metas", objColumn);
				}

				getBack = obj.toString();

			} catch (Exception ex) {
			}
		}
		return getBack;
	}

	public JSONObject getTableIDComposite(String tableName, int TableID,
			JSONObject obj) {
		try {
			if (tableName.equalsIgnoreCase("vrs_roles")) {
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", TableID));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_roles vr = (vrs_roles) it.next();
					obj.put("tableID-Composite", vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_companies vc = (vrs_companies) it.next();
						obj.put("tableID-Composite-company",
								vc.getId_composite());
					}
				}
			}
		} catch (Exception ex) {
		}
		return obj;
	}

	public String getvrs_logs_DAta(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_logs.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itCompsite = it;
		if (it.hasNext()) {
			vrs_logs vl = (vrs_logs) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("tableName", "vrs_logs");
				obj.put("id_vrs_sync_itemsLOCAL", id_vrs_sync_items);
				obj.put("action1", action);

				JSONObject objColumn = new JSONObject();
				String tableName = vl.getTableName();
				objColumn.put("tableName", tableName);
				objColumn.put("action", vl.getAction());
				objColumn.put("modified", vl.getModified());
				if (vl.getReason() == null)
					objColumn.put("reason", "");
				else
					objColumn.put("reason", vl.getReason());

				if (action.equalsIgnoreCase("edit"))
					objColumn.put("logid", vl.getId());

				// get company id_composite
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vl.getCompanyId()));
				itCompsite = criteria.list().iterator();
				if (itCompsite.hasNext()) {
					vrs_companies vc = (vrs_companies) itCompsite.next();
					objColumn.put("company_id_composite", vc.getId_composite());
				}

				// get role composite
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vl.getRoleId()));
				itCompsite = criteria.list().iterator();
				if (itCompsite.hasNext()) {
					vrs_roles vr = (vrs_roles) itCompsite.next();
					objColumn.put("role_id_composite", vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itCompsite = criteria.list().iterator();
					if (itCompsite.hasNext()) {
						vrs_companies vc = (vrs_companies) itCompsite.next();
						objColumn.put("rolecompany_id_composite",
								vc.getId_composite());
					}
				}

				objColumn = getTableIDComposite(tableName, vl.getTableId(),
						objColumn);

				obj.append("vrs_logs", objColumn);
				getBack = obj.toString();
			} catch (Exception ex) {
				cf.viewAlert(" error in getvrs_logs_DAta " + ex.getMessage());
			}
		}
		return getBack;
	}

	

	public String getvrs_user_guide_DAtaDownload(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_user_guide.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			vrs_user_guide vug = (vrs_user_guide) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vrs_user_guide");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				objColumn.put("title", vug.getTitle());
				objColumn.put("excerpt", vug.getExcerpt());
				objColumn.put("content", vug.getContent());
				objColumn.put("image", vug.getImage());
				objColumn.put("service", vug.getService());
				objColumn.put("type", vug.getType());
				objColumn.put("last_edited", vug.getLast_edited());

				objColumn.put("id_composite", vug.getId_composite());
				obj.append("vrs_user_guide", objColumn);
				getBack = obj.toString();

			} catch (Exception ex) {
			}
		}
		return getBack;
	}

	public String getvrs_user_guide_DAta(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_user_guide.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			vrs_user_guide vug = (vrs_user_guide) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vrs_user_guide");
				obj.put("id_vrs_sync_itemsLOCAL", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				objColumn.put("title", vug.getTitle());
				objColumn.put("excerpt", vug.getExcerpt());
				objColumn.put("content", vug.getContent());
				objColumn.put("image", vug.getImage());
				objColumn.put("service", vug.getService());
				objColumn.put("type", vug.getType());
				objColumn.put("last_edited", vug.getLast_edited());

				if (action.equalsIgnoreCase("edit"))
					objColumn.put("id_composite", vug.getId_composite());
				obj.append("vrs_user_guide", objColumn);
				getBack = obj.toString();

			} catch (Exception ex) {
			}
		}
		return getBack;
	}

	public String getvrs_service_DAtaDownload(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_service.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			vrs_service vse = (vrs_service) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vrs_service");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				objColumn.put("name", vse.getService_name());
				objColumn.put("id_composite", vse.getId_composite());
				obj.append("vrs_service", objColumn);
				getBack = obj.toString();
			} catch (Exception ex) {
			}
		}
		return getBack;
	}

	public String getvrs_service_DAta(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_service.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			vrs_service vse = (vrs_service) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vrs_service");
				obj.put("id_vrs_sync_itemsLOCAL", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				objColumn.put("name", vse.getService_name());
				if (action.equalsIgnoreCase("edit"))
					objColumn.put("id_composite", vse.getId_composite());
				obj.append("vrs_service", objColumn);
				getBack = obj.toString();
			} catch (Exception ex) {
			}
		}
		return getBack;
	}

	public String getvrs_rule_policies_DAtaDownload(long id_table,
			String action, int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_rule_policies.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			vrs_rule_policies vrpp = (vrs_rule_policies) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vrs_rule_policies");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vrpp.getCompanyId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				objColumn.put("name", vrpp.getName());
				objColumn.put("functionName", vrpp.getFunctionName());
				objColumn.put("created", vrpp.getCreated());
				objColumn.put("modified", vrpp.getModified());

				objColumn.put("id_composite", vrpp.getId_composite());
				obj.append("vrs_rule_policies", objColumn);
				getBack = obj.toString();

			} catch (Exception ex) {
			}

		}

		return getBack;
	}

	public String getvrs_rule_policies_DAta(long id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_rule_policies.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			vrs_rule_policies vrpp = (vrs_rule_policies) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vrs_rule_policies");
				obj.put("id_vrs_sync_itemsLOCAL", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vrpp.getCompanyId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				objColumn.put("name", vrpp.getName());
				objColumn.put("functionName", vrpp.getFunctionName());
				objColumn.put("created", vrpp.getCreated());
				objColumn.put("modified", vrpp.getModified());

				if (action.equalsIgnoreCase("edit"))
					objColumn.put("id_composite", vrpp.getId_composite());
				obj.append("vrs_rule_policies", objColumn);
				getBack = obj.toString();

			} catch (Exception ex) {
			}

		}

		return getBack;
	}

	public String getvrs_page_registry_DAtaDownload(int id_table,
			String action, int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_page_registry.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			vrs_page_registry vpr = (vrs_page_registry) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vrs_page_registry");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_service.class);
				criteria.add(Restrictions.eq("id", vpr.getService_id()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_service vsc = (vrs_service) itMeta.next();
					objColumn.put("serviceID_Composite", vsc.getId_composite());
				}

				objColumn.put("page_name", vpr.getPage_name());
				objColumn.put("description", vpr.getDescription());

				objColumn.put("id_composite", vpr.getId_composite());
				obj.append("vrs_page_registry", objColumn);
				getBack = obj.toString();

			} catch (Exception ex) {
			}
		}

		return getBack;
	}

	public String getvrs_page_registry_DAta(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_page_registry.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			vrs_page_registry vpr = (vrs_page_registry) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vrs_page_registry");
				obj.put("id_vrs_sync_itemsLOCAL", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_service.class);
				criteria.add(Restrictions.eq("id", vpr.getService_id()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_service vsc = (vrs_service) itMeta.next();
					objColumn.put("serviceID_Composite", vsc.getId_composite());
				}

				objColumn.put("page_name", vpr.getPage_name());
				objColumn.put("description", vpr.getDescription());

				if (action.equalsIgnoreCase("edit"))
					objColumn.put("id_composite", vpr.getId_composite());
				obj.append("vrs_page_registry", objColumn);
				getBack = obj.toString();

			} catch (Exception ex) {
			}
		}
		return getBack;
	}

	public String getvrs_file_library_DAtaDownload(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_file_library.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			vrs_file_library vfl = (vrs_file_library) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vrs_file_library");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				objColumn.put("file_name", vfl.getFile_name());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vfl.getCompany_id()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				objColumn.put("description", vfl.getDescription());
				objColumn.put("file_type", vfl.getFile_type());

				objColumn.put("id_composite", vfl.getId_composite());
				obj.append("vrs_file_library", objColumn);
				getBack = obj.toString();

			} catch (Exception ex) {
			}
		}

		return getBack;
	}

	public String getvrs_file_library_DAta(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_file_library.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			vrs_file_library vfl = (vrs_file_library) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vrs_file_library");
				obj.put("id_vrs_sync_itemsLOCAL", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				objColumn.put("file_name", vfl.getFile_name());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vfl.getCompany_id()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				objColumn.put("description", vfl.getDescription());
				objColumn.put("file_type", vfl.getFile_type());

				if (action.equalsIgnoreCase("edit"))
					objColumn.put("id_composite", vfl.getId_composite());
				obj.append("vrs_file_library", objColumn);
				getBack = obj.toString();

			} catch (Exception ex) {
			}
		}
		return getBack;
	}

	public String getvrs_access_DAtaDownload(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_access.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			vrs_access vac = (vrs_access) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vrs_access");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vac.getCompany_id()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				objColumn.put("positiones", vac.getPositiones());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_page_registry.class);
				criteria.add(Restrictions.eq("id", vac.getPage_id()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_page_registry vpr = (vrs_page_registry) itMeta.next();
					objColumn.put("Page_id_Composite", vpr.getId_composite());
				}

				objColumn.put("viewed", vac.isViewed());
				objColumn.put("created", vac.isCreated());
				objColumn.put("updated", vac.isUpdated());
				objColumn.put("deleted", vac.isDeleted());
				objColumn.put("created_time", vac.getCreated_time());
				objColumn.put("modified", vac.getModified());

				objColumn.put("id_composite", vac.getId_composite());
				obj.append("vrs_access", objColumn);
				getBack = obj.toString();

			} catch (Exception ex) {
			}
		}

		return getBack;
	}

	public String getvrs_access_DAta(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_access.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			vrs_access vac = (vrs_access) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vrs_access");
				obj.put("id_vrs_sync_itemsLOCAL", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vac.getCompany_id()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				objColumn.put("positiones", vac.getPositiones());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_page_registry.class);
				criteria.add(Restrictions.eq("id", vac.getPage_id()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_page_registry vpr = (vrs_page_registry) itMeta.next();
					objColumn.put("Page_id_Composite", vpr.getId_composite());
				}

				objColumn.put("viewed", vac.isViewed());
				objColumn.put("created", vac.isCreated());
				objColumn.put("updated", vac.isUpdated());
				objColumn.put("deleted", vac.isDeleted());
				objColumn.put("created_time", vac.getCreated_time());
				objColumn.put("modified", vac.getModified());

				if (action.equalsIgnoreCase("edit"))
					objColumn.put("id_composite", vac.getId_composite());
				obj.append("vrs_access", objColumn);
				getBack = obj.toString();

			} catch (Exception ex) {
			}
		}
		return getBack;
	}
	

	public String getvrs_companies_DAta(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_companies.class);
		criteria.add(Restrictions.eq("companyId", id_table));
		Iterator it = criteria.list().iterator();
		if (it.hasNext()) {
			vrs_companies vc = (vrs_companies) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vrs_companies");
				obj.put("id_vrs_sync_itemsLOCAL", id_vrs_sync_items);

				JSONObject objColumn = new JSONObject();

				objColumn.put("status", vc.getStatus());
				objColumn.put("companyName", vc.getCompanyName());
				objColumn.put("companyType", vc.getCompanyType());
				objColumn.put("created", vc.getCreated());
				objColumn.put("modified", vc.getModified());
				if (action.equalsIgnoreCase("edit"))
					objColumn.put("id_composite", vc.getId_composite());

				vrs_roles vr;
				vrs_companies vco;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vc.getCreatedBy()));
				Iterator itComposite = criteria.list().iterator();
				if (itComposite.hasNext()) {
					vr = (vrs_roles) itComposite.next();
					objColumn.put("createdBy", vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itComposite = criteria.list().iterator();
					if (itComposite.hasNext()) {
						vco = (vrs_companies) itComposite.next();
						objColumn.put("company-createdBy",
								vco.getId_composite());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vc.getModifiedBy()));
				itComposite = criteria.list().iterator();
				if (itComposite.hasNext()) {
					vr = (vrs_roles) itComposite.next();
					objColumn.put("modifiedBy", vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itComposite = criteria.list().iterator();
					if (itComposite.hasNext()) {
						vco = (vrs_companies) itComposite.next();
						objColumn.put("company-modifiedBy",
								vco.getId_composite());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vc.getParent()));
				itComposite = criteria.list().iterator();
				if (itComposite.hasNext()) {
					vco = (vrs_companies) itComposite.next();
					objColumn.put("parent", vco.getId_composite());
				}

				obj.append("vrs_companies", objColumn);

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_company_metas.class);
				criteria.add(Restrictions.eq("companyId", id_table));
				it = criteria.list().iterator();
				Iterator itInsideMetas = it;
				while (it.hasNext()) {
					vrs_company_metas vcm = (vrs_company_metas) it.next();
					objColumn = new JSONObject();
					if (vcm.getCompanyMetasKey().equalsIgnoreCase(
							"School_headmaster")) {
						objColumn.put("companyMetasKey",
								vcm.getCompanyMetasKey());
						criteria = sessionFactory.getCurrentSession()
								.createCriteria(vrs_roles.class);
						criteria.add(Restrictions.eq("roleId",
								Integer.valueOf(vcm.getCompanyMetasValue())));
						itInsideMetas = criteria.list().iterator();
						if (itInsideMetas.hasNext()) {
							vr = (vrs_roles) itInsideMetas.next();
							objColumn.put("role_idComposite",
									vr.getId_composite());
							criteria = sessionFactory.getCurrentSession()
									.createCriteria(vrs_companies.class);
							criteria.add(Restrictions.eq("companyId",
									vr.getCompanyId()));
							itInsideMetas = criteria.list().iterator();
							if (itInsideMetas.hasNext()) {
								vc = (vrs_companies) itInsideMetas.next();
								objColumn.put("role_company_idComposite",
										vc.getId_composite());
							}
						}
						objColumn.put("created", vcm.getCreated());
						objColumn.put("modified", vcm.getModified());
					} else if (vcm.getCompanyMetasKey().equalsIgnoreCase(
							"CompanyPhoto")) {
						objColumn.put("companyMetasKey",
								vcm.getCompanyMetasKey());
						criteria = sessionFactory.getCurrentSession()
								.createCriteria(vrs_file_library.class);
						criteria.add(Restrictions.eq("id",
								Integer.valueOf(vcm.getCompanyMetasValue())));
						itInsideMetas = criteria.list().iterator();
						if (itInsideMetas.hasNext()) {
							vrs_file_library vfl = (vrs_file_library) itInsideMetas
									.next();
							objColumn.put("id_composite_vrs_file_library",
									vfl.getId_composite());
							criteria = sessionFactory.getCurrentSession()
									.createCriteria(vrs_companies.class);
							criteria.add(Restrictions.eq("companyId",
									vfl.getCompany_id()));
							itInsideMetas = criteria.list().iterator();
							if (itInsideMetas.hasNext()) {
								vc = (vrs_companies) itInsideMetas.next();
								objColumn.put("id_composite_company",
										vc.getId_composite());
							}
						}
					} else {

						objColumn.put("companyMetasKey",
								vcm.getCompanyMetasKey());
						objColumn.put("companyMetasValue",
								vcm.getCompanyMetasValue());
						objColumn.put("created", vcm.getCreated());
						objColumn.put("modified", vcm.getModified());
					}
					obj.append("vrs_company_metas", objColumn);
				}
				getBack = obj.toString();

			} catch (Exception ex) {
			}
		}
		return getBack;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String getvrs_roles_DAtaDownload(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_roles.class);
		criteria.add(Restrictions.eq("roleId", id_table));
		Iterator it = criteria.list().iterator();
		if (it.hasNext()) {
			vrs_roles vu = (vrs_roles) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vrs_roles");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);

				JSONObject objColumn = new JSONObject();
				int userId = vu.getUserId();
				int companyId = vu.getCompanyId();
				objColumn.put("role", vu.getRole());
				objColumn.put("created", vu.getCreated());
				objColumn.put("modified", vu.getModified());
				objColumn.put("status", vu.getStatus());
				objColumn.put("id_composite", vu.getId_composite());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_users.class);
				criteria.add(Restrictions.eq("id", userId));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_users vus = (vrs_users) it.next();
					objColumn.put("id_composite_users", vus.getId_composite());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", companyId));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vrc = (vrs_companies) it.next();
					objColumn.put("id_composite_companies",
							vrc.getId_composite());
				}
				obj.append("vrs_roles", objColumn);

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_role_metas.class);
				criteria.add(Restrictions.eq("roleId", id_table));
				it = criteria.list().iterator();
				while (it.hasNext()) {

					vrs_role_metas vum = (vrs_role_metas) it.next();
					objColumn = new JSONObject();
					if (vum.getRoleMetasKey().equalsIgnoreCase("people_photo")) {

						objColumn.put("roleMetasKey", vum.getRoleMetasKey());

						objColumn.put("created", vum.getCreated());
						objColumn.put("modified", vum.getModified());

						criteria = sessionFactory.getCurrentSession()
								.createCriteria(vrs_file_library.class);
						criteria.add(Restrictions.eq("id",
								Integer.valueOf(vum.getRoleMetasValue())));
						Iterator itMEta = criteria.list().iterator();
						if (itMEta.hasNext()) {
							vrs_file_library vfl = (vrs_file_library) itMEta
									.next();
							objColumn.put("roleMetasValueComposite",
									vfl.getId_composite());
						}

					} else {
						objColumn.put("roleMetasKey", vum.getRoleMetasKey());
						objColumn
								.put("roleMetasValue", vum.getRoleMetasValue());
						objColumn.put("created", vum.getCreated());
						objColumn.put("modified", vum.getModified());
					}

					obj.append("vrs_role_metas", objColumn);
				}

				getBack = obj.toString();

			} catch (Exception ex) {
			}
		}
		return getBack;
	}

	public String getvrs_logs_DAtaDownload(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_logs.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itCompsite = it;
		if (it.hasNext()) {
			vrs_logs vl = (vrs_logs) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("tableName", "vrs_logs");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);
				obj.put("action1", action);

				JSONObject objColumn = new JSONObject();
				String tableName = vl.getTableName();
				objColumn.put("tableName", tableName);
				objColumn.put("action", vl.getAction());
				objColumn.put("modified", vl.getModified());
				if (vl.getReason() == null)
					objColumn.put("reason", "");
				else
					objColumn.put("reason", vl.getReason());

				if (action.equalsIgnoreCase("edit"))
					objColumn.put("logid", vl.getId());

				// get company id_composite
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vl.getCompanyId()));
				itCompsite = criteria.list().iterator();
				if (itCompsite.hasNext()) {
					vrs_companies vc = (vrs_companies) itCompsite.next();
					objColumn.put("company_id_composite", vc.getId_composite());
				}

				// get role composite
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vl.getRoleId()));
				itCompsite = criteria.list().iterator();
				if (itCompsite.hasNext()) {
					vrs_roles vr = (vrs_roles) itCompsite.next();
					objColumn.put("role_id_composite", vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itCompsite = criteria.list().iterator();
					if (itCompsite.hasNext()) {
						vrs_companies vc = (vrs_companies) itCompsite.next();
						objColumn.put("rolecompany_id_composite",
								vc.getId_composite());
					}
				}

				objColumn = getTableIDComposite(tableName, vl.getTableId(),
						objColumn);

				obj.append("vrs_logs", objColumn);
				getBack = obj.toString();

			} catch (Exception ex) {
				cf.viewAlert("error getvrs_logs_DAtaDownload = "
						+ ex.getMessage());
			}
		}
		return getBack;
	}

	public String getvrs_companies_DAtaDownload(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_companies.class);
		criteria.add(Restrictions.eq("companyId", id_table));
		Iterator it = criteria.list().iterator();
		if (it.hasNext()) {
			vrs_companies vc = (vrs_companies) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vrs_companies");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);

				JSONObject objColumn = new JSONObject();
				objColumn.put("status", vc.getStatus());
				objColumn.put("companyName", vc.getCompanyName());
				objColumn.put("companyType", vc.getCompanyType());
				objColumn.put("created", vc.getCreated());
				objColumn.put("modified", vc.getModified());
				objColumn.put("id_composite", vc.getId_composite());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vc.getCreatedBy()));
				Iterator itComposite = criteria.list().iterator();
				vrs_companies vsc = null;
				vrs_roles vr = null;
				int companyRoles = 0;
				if (itComposite.hasNext()) {
					vr = (vrs_roles) itComposite.next();
					objColumn.put("createdBy", vr.getId_composite());
					companyRoles = vr.getCompanyId();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", companyRoles));
					itComposite = criteria.list().iterator();
					if (itComposite.hasNext()) {
						vsc = (vrs_companies) itComposite.next();
						objColumn.put("Company-createdBy",
								vsc.getId_composite());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vc.getModifiedBy()));
				itComposite = criteria.list().iterator();
				if (itComposite.hasNext()) {
					vr = (vrs_roles) itComposite.next();
					objColumn.put("modifiedBy", vr.getId_composite());
					companyRoles = vr.getCompanyId();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", companyRoles));
					itComposite = criteria.list().iterator();
					if (itComposite.hasNext()) {
						vsc = (vrs_companies) itComposite.next();
						objColumn.put("Company-modifiedBy",
								vsc.getId_composite());
					}

				}

				companyRoles = vc.getParent();
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", companyRoles));
				itComposite = criteria.list().iterator();
				if (itComposite.hasNext()) {
					vsc = (vrs_companies) itComposite.next();
					objColumn.put("parent", vsc.getId_composite());
				}

				obj.append("vrs_companies", objColumn);

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_company_metas.class);
				criteria.add(Restrictions.eq("companyId", id_table));
				it = criteria.list().iterator();
				Iterator itInsideMetas = it;
				while (it.hasNext()) {
					vrs_company_metas vcm = (vrs_company_metas) it.next();
					objColumn = new JSONObject();
					objColumn.put("companyMetasKey", vcm.getCompanyMetasKey());
					if (vcm.getCompanyMetasKey().equalsIgnoreCase(
							"School_headmaster")) {
						criteria = sessionFactory.getCurrentSession()
								.createCriteria(vrs_roles.class);
						criteria.add(Restrictions.eq("roleId",
								Integer.valueOf(vcm.getCompanyMetasValue())));

						itInsideMetas = criteria.list().iterator();
						if (itInsideMetas.hasNext()) {
							vr = (vrs_roles) itInsideMetas.next();
							objColumn.put("role_idComposite",
									vr.getId_composite());
							criteria = sessionFactory.getCurrentSession()
									.createCriteria(vrs_companies.class);
							criteria.add(Restrictions.eq("companyId",
									vr.getCompanyId()));
							itInsideMetas = criteria.list().iterator();
							if (itInsideMetas.hasNext()) {
								vc = (vrs_companies) itInsideMetas.next();
								objColumn.put("role_company_idComposite",
										vc.getId_composite());
							}
						}
					} else if (vcm.getCompanyMetasKey().equalsIgnoreCase(
							"CompanyPhoto")) {
						objColumn.put("companyMetasKey",
								vcm.getCompanyMetasKey());
						criteria = sessionFactory.getCurrentSession()
								.createCriteria(vrs_file_library.class);
						criteria.add(Restrictions.eq("id",
								Integer.valueOf(vcm.getCompanyMetasValue())));
						itInsideMetas = criteria.list().iterator();
						if (itInsideMetas.hasNext()) {
							vrs_file_library vfl = (vrs_file_library) itInsideMetas
									.next();
							objColumn.put("id_composite_vrs_file_library",
									vfl.getId_composite());
							criteria = sessionFactory.getCurrentSession()
									.createCriteria(vrs_companies.class);
							criteria.add(Restrictions.eq("companyId",
									vfl.getCompany_id()));
							itInsideMetas = criteria.list().iterator();
							if (itInsideMetas.hasNext()) {
								vc = (vrs_companies) itInsideMetas.next();
								objColumn.put("id_composite_company",
										vc.getId_composite());
							}
						}
					} else {
						objColumn.put("companyMetasValue",
								vcm.getCompanyMetasValue());
					}

					objColumn.put("created", vcm.getCreated());
					objColumn.put("modified", vcm.getModified());
					obj.append("vrs_company_metas", objColumn);
				}

				getBack = obj.toString();

			} catch (Exception ex) {
				cf.viewAlert(" error in getvrs_companies_DAtaDownload "
						+ ex.getMessage());
			}
		}
		return getBack;
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
			if (vsi.getTable_name().equalsIgnoreCase("vrs_file_library")) {
				getBack = getvrs_file_library_DAta(vsi.getId_tableInteger(),
						vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("vrs_users")) {
				getBack = getvrs_userus_DAta(vsi.getId_tableInteger(),
						vsi.getAction(), vsi.getId());
				getBack = sacc.getVed_students(getBack);
			} else if (vsi.getTable_name().equalsIgnoreCase("vrs_roles")) {
				getBack = getvrs_roles_DAta(vsi.getId_tableInteger(),
						vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("vrs_companies")) {
				getBack = getvrs_companies_DAta(vsi.getId_tableInteger(),
						vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("vrs_logs")) {
				getBack = getvrs_logs_DAta(vsi.getId_tableInteger(),
						vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase(
					"vac_chart_accounts")) {
				getBack = ssac.getvac_chart_accounts_DAta(vsi.getId_tableInteger(),
						vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("vac_postings")) {
				getBack = ssac.getvac_postings_DAta(vsi.getId_tableInteger(),
						vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("vac_transactions")) {
				getBack = ssac.getvac_transactions_DAta(vsi.getId_tableInteger(),
						vsi.getAction(), vsi.getId());
				if (vsi.getAction().equalsIgnoreCase("add")) {
					getBack = sacc.getVac_transactionsforUploD(getBack);
				}
			} else if (vsi.getTable_name().equalsIgnoreCase(
					"vac_transaction_details")) {
				getBack = ssac.getvac_transaction_details_DAta(
						vsi.getId_tableInteger(), vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("vac_budgets")) {
				getBack = ssac.getvac_budgets_DAta(vsi.getId_tableInteger(),
						vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase(
					"vac_chart_account_balances")) {
				getBack = ssac.getvac_chart_account_balances_DAta(
						vsi.getId_tableInteger(), vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("ved_assignments")) {
				getBack = ssa.getved_assignments_DAta(
						Long.valueOf(vsi.getId_table()), vsi.getAction(),
						vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("ved_attendances")) {
				getBack = ssa.getved_attendances_DAta(
						Long.valueOf(vsi.getId_table()), vsi.getAction(),
						vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("ved_candidates")) {
				getBack = ssa.getved_candidates_DAta(
						Long.valueOf(vsi.getId_table()), vsi.getAction(),
						vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("ved_classes")) {
				getBack = ssa.getved_classes_DAta(Long.valueOf(vsi.getId_table()),
						vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("ved_course_list")) {
				getBack = ssa.getved_course_list_DAta(vsi.getId_tableInteger(),
						vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase(
					"ved_course_schedule")) {
				getBack = ssa.getved_course_schedule_DAta(vsi.getId_tableInteger(),
						vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("ved_courses")) {
				getBack = ssa.getved_courses_DAta(Long.valueOf(vsi.getId_table()),
						vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("ved_enrollments")) {
				getBack = ssa.getved_enrollments_DAta(
						Long.valueOf(vsi.getId_table()), vsi.getAction(),
						vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("ved_reports")) {
				getBack = ssa.getved_reports_DAta(Long.valueOf(vsi.getId_table()),
						vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("ved_scores")) {
				getBack = ssa.getved_scores_DAta(Long.valueOf(vsi.getId_table()),
						vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("ved_students")) {
				getBack = ssa.getved_students_DAta(Long.valueOf(vsi.getId_table()),
						vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("vrs_access")) {
				getBack = getvrs_access_DAta(vsi.getId_tableInteger(),
						vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name()
					.equalsIgnoreCase("vrs_page_registry")) {
				getBack = getvrs_page_registry_DAta(vsi.getId_tableInteger(),
						vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name()
					.equalsIgnoreCase("vrs_rule_policies")) {
				getBack = getvrs_rule_policies_DAta(
						Long.valueOf(vsi.getId_table()), vsi.getAction(),
						vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("vrs_service")) {
				getBack = getvrs_service_DAta(vsi.getId_tableInteger(),
						vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("vrs_user_guide")) {
				getBack = getvrs_user_guide_DAta(vsi.getId_tableInteger(),
						vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("vrs_addresses")) {
				getBack = sVRS.getvrs_addresses_DAta(vsi.getId_tableInteger(),
						vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("vrs_attendances")) {
				getBack = sVRS.getvrs_attendances_DAta(
						Long.valueOf(vsi.getId_table()), vsi.getAction(),
						vsi.getId());
			} else if (vsi.getTable_name()
					.equalsIgnoreCase("vrs_subscriptions")) {
				getBack = sVRS.getvrs_subscriptions(vsi.getId_tableInteger(),
						vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("vrs_taxonomies")) {
				getBack = sVRS.getvrs_taxonomies(vsi.getId_tableInteger(),
						vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("ved_grade_report")) {
				getBack = sv1.get_ved_grade_report(vsi.getId_tableInteger(),
						vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase(
					"ved_grade_report_detail")) {
				getBack = sv1.get_ved_grade_report_detail(
						vsi.getId_tableInteger(), vsi.getAction(), vsi.getId());
			}
		}
		cf.viewAlert(" getBack = " + getBack);
		return getBack;
	}

	public Timestamp getTimeStamp(String text) {
		Timestamp tstamp = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:s");
			tstamp = new Timestamp(sdf.parse(text).getTime());
		} catch (Exception ex) {
		}
		return tstamp;
	}

	public Date getDate(String text) {
		Date tstamp = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			tstamp = new Date(sdf.parse(text).getTime());
		} catch (Exception ex) {
		}
		return tstamp;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String saveInTable_vrs_users(String data, int computer_id) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL",
					obj.get("id_vrs_sync_itemsLOCAL").toString());
			String action = obj.get("action").toString();

			JSONArray objData = new JSONArray(obj.get("vrs_users").toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);

			if (action.equalsIgnoreCase("add")) {
				vrs_users vs = new vrs_users();
				vs.setModified(getTimeStamp(dataVrs_users.get("modified")
						.toString()));
				vs.setParent(Integer.valueOf(dataVrs_users.get("parent")
						.toString()));
				vs.setCreated(getTimeStamp(dataVrs_users.get("created")
						.toString()));
				sessionFactory.getCurrentSession().save(vs);
				int user_id = vs.getId();
				objView.put("id_compositesaved", String.valueOf(user_id));
				sessionFactory.getCurrentSession().clear();

				String sql = "SELECT last_value from vrs_sync_items_id_seq";
				Iterator it = sessionFactory.getCurrentSession()
						.createSQLQuery(sql).list().iterator();
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
							"vrs_user_metas").toString());
					JSONObject objData_meta1 = null;
					int i = 0;
					for (i = 0; i < objData_meta.length(); i++) {
						objData_meta1 = objData_meta.getJSONObject(i);
						vrs_user_metas vum = new vrs_user_metas();
						vum.setCreated(getTimeStamp(objData_meta1
								.get("created").toString()));
						vum.setKey(objData_meta1.get("key").toString());
						vum.setModified(getTimeStamp(objData_meta1.get(
								"modified").toString()));
						vum.setUser_id(user_id);
						vum.setValue(objData_meta1.get("value").toString());
						sessionFactory.getCurrentSession().save(vum);
					}
					sessionFactory.getCurrentSession().clear();
				} catch (Exception ex) {
				}

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_users.class);
				criteria.add(Restrictions.eq("id", user_id));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_users vus = (vrs_users) it.next();
					objView.put("id_CompositeFromServer", vus.getId_composite());
					getBack = objView.toString();
				}
			} else if (action.equalsIgnoreCase("edit")) {
				int user_id = -1;
				String sql = "";
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_users.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("userCompositeID")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_users vus = (vrs_users) it.next();
					user_id = vus.getId();
					sql = "update vrs_users set modified=:modified where id=:id";
					sessionFactory
							.getCurrentSession()
							.createQuery(sql)
							.setInteger("id", user_id)
							.setTimestamp(
									"modified",
									getTimeStamp(dataVrs_users.get("modified")
											.toString())).executeUpdate();

					sql = "SELECT last_value from vrs_sync_items_id_seq";
					it = sessionFactory.getCurrentSession().createSQLQuery(sql)
							.list().iterator();
					int id_vrs_sync_items = 0;
					if (it.hasNext()) {
						id_vrs_sync_items = Integer.valueOf(it.next()
								.toString());
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
								"vrs_user_metas").toString());
						JSONObject objData_meta1 = null;
						int i = 0;
						for (i = 0; i < objData_meta.length(); i++) {
							objData_meta1 = objData_meta.getJSONObject(i);
							addvrs_user_metas(user_id, objData_meta1.get("key")
									.toString(), objData_meta1.get("value")
									.toString());
						}
						sessionFactory.getCurrentSession().clear();
					} catch (Exception ex) {
					}
				}
				getBack = objView.toString();
			}

		} catch (Exception ex) {
			getBack += " error " + ex.getMessage();
		}

		return getBack;
	}

	void addvrs_user_metas(int user_id, String key, String val) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_user_metas.class);
		criteria.add(Restrictions.eq("user_id", user_id));
		criteria.add(Restrictions.eq("key", key));
		Iterator it = criteria.list().iterator();
		Timestamp tstamp = new Timestamp(new Date().getTime());
		if (it.hasNext()) {
			String sql = "update vrs_user_metas set value=:val,modified=:modified where user_id=:user_id and key=:key";
			sessionFactory.getCurrentSession().createQuery(sql)
					.setString("val", val).setString("key", key)
					.setInteger("user_id", user_id)
					.setTimestamp("modified", tstamp).executeUpdate();
		} else {
			vrs_user_metas vum = new vrs_user_metas();
			;
			vum.setKey(key);
			vum.setUser_id(user_id);
			vum.setValue(val);
			vum.setModified(tstamp);
			vum.setCreated(tstamp);
			sessionFactory.getCurrentSession().save(vum);
		}
	}

	

	public void saveInTable_vrs_user_guideDownload(String data) {
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("vrs_user_guide")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			String sql = "";
			if (action.equalsIgnoreCase("add")) {
				vrs_user_guide vus = new vrs_user_guide();
				vus.setTitle(dataVrs_users.get("title").toString());
				vus.setExcerpt(dataVrs_users.get("excerpt").toString());
				vus.setContent(dataVrs_users.get("content").toString());
				vus.setImage(dataVrs_users.get("image").toString());
				vus.setService(dataVrs_users.get("service").toString());
				vus.setType(dataVrs_users.get("type").toString());
				vus.setLast_edited(getTimeStamp(dataVrs_users
						.get("last_edited").toString()));
				vus.setId_composite(Integer.valueOf(dataVrs_users.get(
						"id_composite").toString()));

				sql = "ALTER TABLE vrs_user_guide DISABLE TRIGGER insert_vrs_user_guidet";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sessionFactory.getCurrentSession().save(vus);
				sessionFactory.getCurrentSession().clear();

				sql = "ALTER TABLE vrs_user_guide ENABLE TRIGGER insert_vrs_user_guidet";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

			} else if (action.equalsIgnoreCase("edit")) {
				int idTable = -1;
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_user_guide.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("id_composite").toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_user_guide vug = (vrs_user_guide) it.next();
					idTable = vug.getId();
				}

				sql = "ALTER TABLE vrs_user_guide DISABLE TRIGGER insert_vrs_user_guidet";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();
				sql = "update vrs_user_guide set title=:title, "
						+ "excerpt=:excerpt, content=:content,"
						+ "image=:image, service=:service, "
						+ "type=:type, last_edited=:last_edited where id=:id";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)
						.setInteger("id", idTable)
						.setString("title",
								dataVrs_users.get("title").toString())
						.setString("excerpt",
								dataVrs_users.get("excerpt").toString())
						.setString("content",
								dataVrs_users.get("content").toString())
						.setString("image",
								dataVrs_users.get("image").toString())
						.setString("service",
								dataVrs_users.get("service").toString())
						.setString("type", dataVrs_users.get("type").toString())
						.setTimestamp(
								"last_edited",
								getTimeStamp(dataVrs_users.get("last_edited")
										.toString()))

						.executeUpdate();
				sessionFactory.getCurrentSession().clear();

				sql = "ALTER TABLE vrs_user_guide ENABLE TRIGGER insert_vrs_user_guidet";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

			}
		} catch (Exception ex) {
			cf.viewAlert("error saveInTable_vrs_user_guideDownload "
					+ ex.getMessage());
		}
	}

	public String saveInTable_vrs_user_guide(String data, int computer_id) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL",
					obj.get("id_vrs_sync_itemsLOCAL").toString());

			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("vrs_user_guide")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			String sql = "";
			if (action.equalsIgnoreCase("add")) {
				vrs_user_guide vus = new vrs_user_guide();
				vus.setTitle(dataVrs_users.get("title").toString());
				vus.setExcerpt(dataVrs_users.get("excerpt").toString());
				vus.setContent(dataVrs_users.get("content").toString());
				vus.setImage(dataVrs_users.get("image").toString());
				vus.setService(dataVrs_users.get("service").toString());
				vus.setType(dataVrs_users.get("type").toString());
				vus.setLast_edited(getTimeStamp(dataVrs_users
						.get("last_edited").toString()));

				sessionFactory.getCurrentSession().save(vus);
				sessionFactory.getCurrentSession().clear();

				sql = "SELECT last_value from vrs_sync_items_id_seq";
				Iterator it = sessionFactory.getCurrentSession()
						.createSQLQuery(sql).list().iterator();
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

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_user_guide.class);
				criteria.add(Restrictions.eq("id", vus.getId()));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_user_guide vug = (vrs_user_guide) it.next();
					objView.put("id_CompositeFromServer", vug.getId_composite());
					getBack = objView.toString();
				}

			} else if (action.equalsIgnoreCase("edit")) {
				int idTable = -1;
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_user_guide.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("id_composite").toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_user_guide vug = (vrs_user_guide) it.next();
					idTable = vug.getId();
				}

				sql = "update vrs_user_guide set title=:title, "
						+ "excerpt=:excerpt, content=:content,"
						+ "image=:image, service=:service, "
						+ "type=:type, last_edited=:last_edited where id=:id";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)
						.setInteger("id", idTable)
						.setString("title",
								dataVrs_users.get("title").toString())
						.setString("excerpt",
								dataVrs_users.get("excerpt").toString())
						.setString("content",
								dataVrs_users.get("content").toString())
						.setString("image",
								dataVrs_users.get("image").toString())
						.setString("service",
								dataVrs_users.get("service").toString())
						.setString("type", dataVrs_users.get("type").toString())
						.setTimestamp(
								"last_edited",
								getTimeStamp(dataVrs_users.get("last_edited")
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
			getBack += " error saveInTable_vrs_user_guide " + ex.getMessage();
		}

		return getBack;
	}

	public void saveInTable_vrs_serviceDownload(String data) {
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("vrs_service").toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			String sql = "";
			if (action.equalsIgnoreCase("add")) {
				vrs_service vse = new vrs_service();
				vse.setService_name(dataVrs_users.get("name").toString());
				vse.setId_composite(Integer.valueOf(dataVrs_users.get(
						"id_composite").toString()));

				sql = "ALTER TABLE vrs_service DISABLE TRIGGER insert_vrs_servicet";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sessionFactory.getCurrentSession().save(vse);
				sessionFactory.getCurrentSession().clear();

				sql = "ALTER TABLE vrs_service ENABLE TRIGGER insert_vrs_servicet";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();
			} else if (action.equalsIgnoreCase("edit")) {
				int idTable = -1;
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_service.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("id_composite").toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_service vser = (vrs_service) it.next();
					idTable = vser.getId();
				}

				sql = "ALTER TABLE vrs_service DISABLE TRIGGER insert_vrs_servicet";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();
				sql = "update vrs_service set service_name=:service_name where id=:id";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)
						.setInteger("id", idTable)
						.setString("service_name",
								dataVrs_users.get("name").toString())

						.executeUpdate();
				sessionFactory.getCurrentSession().clear();

				sql = "ALTER TABLE vrs_service ENABLE TRIGGER insert_vrs_servicet";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();
			}

		} catch (Exception ex) {
			cf.viewAlert(" error saveInTable_vrs_serviceDownload = "
					+ ex.getMessage());
		}
	}

	public String saveInTable_vrs_service(String data, int computer_id) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL",
					obj.get("id_vrs_sync_itemsLOCAL").toString());
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("vrs_service").toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			String sql = "";
			if (action.equalsIgnoreCase("add")) {
				vrs_service vse = new vrs_service();
				vse.setService_name(dataVrs_users.get("name").toString());
				sessionFactory.getCurrentSession().save(vse);
				sessionFactory.getCurrentSession().clear();

				sql = "SELECT last_value from vrs_sync_items_id_seq";
				Iterator it = sessionFactory.getCurrentSession()
						.createSQLQuery(sql).list().iterator();
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

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_service.class);
				criteria.add(Restrictions.eq("id", vse.getId()));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_service vser = (vrs_service) it.next();
					objView.put("id_CompositeFromServer",
							vser.getId_composite());
					getBack = objView.toString();
				}
			} else if (action.equalsIgnoreCase("edit")) {
				int idTable = -1;
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_service.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("id_composite").toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_service vser = (vrs_service) it.next();
					idTable = vser.getId();
				}

				sql = "update vrs_service set service_name=:service_name where id=:id";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)
						.setInteger("id", idTable)
						.setString("service_name",
								dataVrs_users.get("name").toString())

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
			getBack += " error saveInTable_vrs_service " + ex.getMessage();
		}

		return getBack;
	}

	public void saveInTable_vrs_rule_policiesDownload(String data) {
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("vrs_rule_policies")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			String sql = "";
			if (action.equalsIgnoreCase("add")) {
				vrs_rule_policies vrp = new vrs_rule_policies();
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					vrp.setCompanyId(vc.getCompanyId());
				}

				vrp.setName(dataVrs_users.get("name").toString());
				vrp.setFunctionName(dataVrs_users.get("functionName")
						.toString());
				vrp.setCreated(getTimeStamp(dataVrs_users.get("created")
						.toString()));
				vrp.setModified(getTimeStamp(dataVrs_users.get("modified")
						.toString()));

				vrp.setId_composite(Integer.valueOf(dataVrs_users.get(
						"id_composite").toString()));

				sql = "ALTER TABLE vrs_rule_policies DISABLE TRIGGER insert_vrs_rule_policiest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sessionFactory.getCurrentSession().save(vrp);
				sessionFactory.getCurrentSession().clear();

				sql = "ALTER TABLE vrs_rule_policies ENABLE TRIGGER insert_vrs_rule_policiest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();
			} else if (action.equalsIgnoreCase("edit")) {
				long idTable = -1;
				int companyID = -1;
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
							.createCriteria(vrs_rule_policies.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));
					criteria.add(Restrictions.eq("companyId", companyID));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_rule_policies vrp = (vrs_rule_policies) it.next();
						idTable = vrp.getId();
					}
				}

				sql = "ALTER TABLE vrs_rule_policies DISABLE TRIGGER insert_vrs_rule_policiest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sql = "update vrs_rule_policies set companyId=:companyId,"
						+ " name=:name,functionName=:functionName,"
						+ " created=:created,modified=:modified where id=:id";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)
						.setLong("id", idTable)
						.setInteger("companyId", companyID)
						.setString("name", dataVrs_users.get("name").toString())
						.setString("functionName",
								dataVrs_users.get("functionName").toString())
						.setTimestamp(
								"created",
								getTimeStamp(dataVrs_users.get("created")
										.toString()))

						.setTimestamp(
								"modified",
								getTimeStamp(dataVrs_users.get("modified")
										.toString()))

						.executeUpdate();
				sessionFactory.getCurrentSession().clear();

				sql = "ALTER TABLE vrs_rule_policies ENABLE TRIGGER insert_vrs_rule_policiest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

			}
		} catch (Exception ex) {
			cf.viewAlert(" error saveInTable_vrs_rule_policiesDownload "
					+ ex.getMessage());
		}
	}

	public String saveInTable_vrs_rule_policies(String data, int computer_id) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL",
					obj.get("id_vrs_sync_itemsLOCAL").toString());
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("vrs_rule_policies")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			String sql = "";
			if (action.equalsIgnoreCase("add")) {
				vrs_rule_policies vrp = new vrs_rule_policies();
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					vrp.setCompanyId(vc.getCompanyId());
				}

				vrp.setName(dataVrs_users.get("name").toString());
				vrp.setFunctionName(dataVrs_users.get("functionName")
						.toString());
				vrp.setCreated(getTimeStamp(dataVrs_users.get("created")
						.toString()));
				vrp.setModified(getTimeStamp(dataVrs_users.get("modified")
						.toString()));

				sessionFactory.getCurrentSession().save(vrp);
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
						vrs_rule_policies.class);
				criteria.add(Restrictions.eq("id", vrp.getId()));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_rule_policies vrpo = (vrs_rule_policies) it.next();
					objView.put("id_CompositeFromServer",
							vrpo.getId_composite());
					getBack = objView.toString();
				}
			} else if (action.equalsIgnoreCase("edit")) {
				long idTable = -1;
				int companyID = -1;
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
							.createCriteria(vrs_rule_policies.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));
					criteria.add(Restrictions.eq("companyId", companyID));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_rule_policies vrp = (vrs_rule_policies) it.next();
						idTable = vrp.getId();
					}
				}

				sql = "update vrs_rule_policies set companyId=:companyId,"
						+ " name=:name,functionName=:functionName,"
						+ " created=:created,modified=:modified where id=:id";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)
						.setLong("id", idTable)
						.setInteger("companyId", companyID)
						.setString("name", dataVrs_users.get("name").toString())
						.setString("functionName",
								dataVrs_users.get("functionName").toString())
						.setTimestamp(
								"created",
								getTimeStamp(dataVrs_users.get("created")
										.toString()))

						.setTimestamp(
								"modified",
								getTimeStamp(dataVrs_users.get("modified")
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
			getBack += " error saveInTable_vrs_rule_policies "
					+ ex.getMessage();
		}

		return getBack;
	}

	public void saveInTable_vrs_page_registryDownload(String data) {
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("vrs_page_registry")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			String sql = "";
			if (action.equalsIgnoreCase("add")) {
				vrs_page_registry vpr = new vrs_page_registry();

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_service.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("serviceID_Composite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_service vsrc = (vrs_service) it.next();
					vpr.setService_id(vsrc.getId());
				}

				vpr.setPage_name(dataVrs_users.get("page_name").toString());
				vpr.setDescription(dataVrs_users.get("description").toString());

				vpr.setId_composite(Integer.valueOf(dataVrs_users.get(
						"id_composite").toString()));

				sql = "ALTER TABLE vrs_page_registry DISABLE TRIGGER insert_vrs_page_registryt";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sessionFactory.getCurrentSession().save(vpr);
				sessionFactory.getCurrentSession().clear();

				sql = "ALTER TABLE vrs_page_registry ENABLE TRIGGER insert_vrs_page_registryt";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

			} else if (action.equalsIgnoreCase("edit")) {

				int serviceID = -1;
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_service.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("serviceID_Composite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_service vsrc = (vrs_service) it.next();
					serviceID = vsrc.getId();
				}

				int idTable = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_page_registry.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("id_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_page_registry vpr = (vrs_page_registry) it.next();
					idTable = vpr.getId();
				}

				sql = "ALTER TABLE vrs_page_registry DISABLE TRIGGER insert_vrs_page_registryt";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sql = "update vrs_page_registry set service_id=:service_id,"
						+ "page_name=:page_name,description=:description where id=:id";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)
						.setLong("id", idTable)
						.setInteger("service_id", serviceID)
						.setString("page_name",
								dataVrs_users.get("page_name").toString())
						.setString("description",
								dataVrs_users.get("description").toString())

						.executeUpdate();
				sessionFactory.getCurrentSession().clear();

				sql = "ALTER TABLE vrs_page_registry ENABLE TRIGGER insert_vrs_page_registryt";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

			}
		} catch (Exception ex) {
			cf.viewAlert(" error saveInTable_vrs_page_registryDownload "
					+ ex.getMessage());
		}
	}

	public String saveInTable_vrs_page_registry(String data, int computer_id) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL",
					obj.get("id_vrs_sync_itemsLOCAL").toString());
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("vrs_page_registry")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			String sql = "";
			if (action.equalsIgnoreCase("add")) {
				vrs_page_registry vpr = new vrs_page_registry();

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_service.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("serviceID_Composite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_service vsrc = (vrs_service) it.next();
					vpr.setService_id(vsrc.getId());
				}

				vpr.setPage_name(dataVrs_users.get("page_name").toString());
				vpr.setDescription(dataVrs_users.get("description").toString());

				sessionFactory.getCurrentSession().save(vpr);
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
						vrs_page_registry.class);
				criteria.add(Restrictions.eq("id", vpr.getId()));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_page_registry vpri = (vrs_page_registry) it.next();
					objView.put("id_CompositeFromServer",
							vpri.getId_composite());
					getBack = objView.toString();
				}

			} else if (action.equalsIgnoreCase("edit")) {

				int serviceID = -1;
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_service.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("serviceID_Composite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_service vsrc = (vrs_service) it.next();
					serviceID = vsrc.getId();
				}

				int idTable = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_page_registry.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("id_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_page_registry vpr = (vrs_page_registry) it.next();
					idTable = vpr.getId();
				}

				sql = "update vrs_page_registry set service_id=:service_id,"
						+ "page_name=:page_name,description=:description where id=:id";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)
						.setLong("id", idTable)
						.setInteger("service_id", serviceID)
						.setString("page_name",
								dataVrs_users.get("page_name").toString())
						.setString("description",
								dataVrs_users.get("description").toString())

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
			getBack += " error saveInTable_vrs_page_registry "
					+ ex.getMessage();
		}

		return getBack;
	}

	public void saveInTable_vrs_file_libraryDownload(String data) {
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("vrs_file_library")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			String sql = "";
			if (action.equalsIgnoreCase("add")) {
				vrs_file_library vfl = new vrs_file_library();
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					vfl.setCompany_id(vc.getCompanyId());
				}
				vfl.setFile_name(dataVrs_users.get("file_name").toString());
				vfl.setFile_type(dataVrs_users.get("file_type").toString());
				vfl.setDescription(dataVrs_users.get("description").toString());
				vfl.setId_composite(Integer.valueOf(dataVrs_users.get(
						"id_composite").toString()));

				sql = "ALTER TABLE vrs_file_library DISABLE TRIGGER insert_vrs_file_libraryt";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sessionFactory.getCurrentSession().save(vfl);
				sessionFactory.getCurrentSession().clear();

				sql = "ALTER TABLE vrs_file_library ENABLE TRIGGER insert_vrs_file_libraryt";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

			} else if (action.equalsIgnoreCase("edit")) {
				int idTable = -1;
				int companyID = -1;
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
							.createCriteria(vrs_file_library.class);

					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));
					criteria.add(Restrictions.eq("company_id", companyID));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_file_library vfl = (vrs_file_library) it.next();
						idTable = vfl.getId();
					}
				}

				sql = "ALTER TABLE vrs_file_library DISABLE TRIGGER insert_vrs_file_libraryt";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sql = "update vrs_file_library set file_name=:file_name,"
						+ "company_id=:company_id,"
						+ " description=:description,"
						+ "file_type=:file_type where id=:id";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)
						.setLong("id", idTable)
						.setInteger("company_id", companyID)
						.setString("file_name",
								dataVrs_users.get("file_name").toString())
						.setString("description",
								dataVrs_users.get("description").toString())
						.setString("file_type",
								dataVrs_users.get("file_type").toString())

						.executeUpdate();
				sessionFactory.getCurrentSession().clear();

				sql = "ALTER TABLE vrs_file_library ENABLE TRIGGER insert_vrs_file_libraryt";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();
			}
		} catch (Exception ex) {
			cf.viewAlert(" error saveInTable_vrs_file_library "
					+ ex.getMessage());
		}
	}

	public String saveInTable_vrs_file_library(String data, int computer_id) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL",
					obj.get("id_vrs_sync_itemsLOCAL").toString());

			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("vrs_file_library")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			String sql = "";
			if (action.equalsIgnoreCase("add")) {
				vrs_file_library vfl = new vrs_file_library();
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					vfl.setCompany_id(vc.getCompanyId());
				}
				vfl.setFile_name(dataVrs_users.get("file_name").toString());
				vfl.setFile_type(dataVrs_users.get("file_type").toString());
				vfl.setDescription(dataVrs_users.get("description").toString());

				sessionFactory.getCurrentSession().save(vfl);
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
						vrs_file_library.class);
				criteria.add(Restrictions.eq("id", vfl.getId()));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_file_library vfli = (vrs_file_library) it.next();
					objView.put("id_CompositeFromServer",
							vfli.getId_composite());
					getBack = objView.toString();
				}

			} else if (action.equalsIgnoreCase("edit")) {
				int idTable = -1;
				int companyID = -1;
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
							.createCriteria(vrs_file_library.class);

					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));
					criteria.add(Restrictions.eq("company_id", companyID));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_file_library vfl = (vrs_file_library) it.next();
						idTable = vfl.getId();
					}
				}

				sql = "update vrs_file_library set file_name=:file_name,"
						+ "company_id=:company_id,"
						+ " description=:description,"
						+ "file_type=:file_type where id=:id";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)
						.setLong("id", idTable)
						.setInteger("company_id", companyID)
						.setString("file_name",
								dataVrs_users.get("file_name").toString())
						.setString("description",
								dataVrs_users.get("description").toString())
						.setString("file_type",
								dataVrs_users.get("file_type").toString())

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
			getBack += " error saveInTable_vrs_file_library " + ex.getMessage();
		}

		return getBack;
	}

	public void saveInTable_vrs_accessDownload(String data) {
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("vrs_access").toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			String sql = "";
			if (action.equalsIgnoreCase("add")) {
				vrs_access vacc = new vrs_access();

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					vacc.setCompany_id(vc.getCompanyId());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_page_registry.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("Page_id_Composite")
								.toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_page_registry vpr = (vrs_page_registry) it.next();
					vacc.setPage_id(vpr.getId());
				}

				vacc.setPositiones(dataVrs_users.get("positiones").toString());
				vacc.setViewed(Boolean.valueOf(dataVrs_users.get("viewed")
						.toString()));
				vacc.setCreated(Boolean.valueOf(dataVrs_users.get("created")
						.toString()));
				vacc.setUpdated(Boolean.valueOf(dataVrs_users.get("updated")
						.toString()));
				vacc.setDeleted(Boolean.valueOf(dataVrs_users.get("deleted")
						.toString()));
				vacc.setCreated_time(getTimeStamp(dataVrs_users.get(
						"created_time").toString()));
				vacc.setModified(getTimeStamp(dataVrs_users.get("modified")
						.toString()));

				vacc.setId_composite(Integer.valueOf(dataVrs_users.get(
						"id_composite").toString()));

				sql = "ALTER TABLE vrs_access DISABLE TRIGGER insert_vrs_accesst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sessionFactory.getCurrentSession().save(vacc);
				sessionFactory.getCurrentSession().clear();

				sql = "ALTER TABLE vrs_access ENABLE TRIGGER insert_vrs_accesst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

			} else if (action.equalsIgnoreCase("edit")) {
				int idTable = -1;
				int companyID = -1;
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
							.createCriteria(vrs_access.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));
					criteria.add(Restrictions.eq("company_id", companyID));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_access vaccs = (vrs_access) it.next();
						idTable = vaccs.getId();
					}
				}

				int pageID = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_page_registry.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("Page_id_Composite")
								.toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_page_registry vpr = (vrs_page_registry) it.next();
					pageID = vpr.getId();
				}

				sql = "ALTER TABLE vrs_access DISABLE TRIGGER insert_vrs_accesst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sql = "update vrs_access set company_id=:company_id,positiones=:positiones,"
						+ " page_id=:page_id,viewed=:viewed,created=:created,"
						+ " updated=:updated,deleted=:deleted,created_time=:created_time,"
						+ " modified=:modified" + "  where id=:id";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)
						.setLong("id", idTable)
						.setInteger("company_id", companyID)
						.setInteger("page_id", pageID)
						.setString("positiones",
								dataVrs_users.get("positiones").toString())
						.setBoolean(
								"viewed",
								Boolean.valueOf(dataVrs_users.get("viewed")
										.toString()))
						.setBoolean(
								"created",
								Boolean.valueOf(dataVrs_users.get("created")
										.toString()))
						.setBoolean(
								"updated",
								Boolean.valueOf(dataVrs_users.get("updated")
										.toString()))
						.setBoolean(
								"deleted",
								Boolean.valueOf(dataVrs_users.get("deleted")
										.toString()))
						.setTimestamp(
								"created_time",
								getTimeStamp(dataVrs_users.get("created_time")
										.toString()))
						.setTimestamp(
								"modified",
								getTimeStamp(dataVrs_users.get("modified")
										.toString()))

						.executeUpdate();
				sessionFactory.getCurrentSession().clear();

				sql = "ALTER TABLE vrs_access ENABLE TRIGGER insert_vrs_accesst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

			}
		} catch (Exception ex) {
			cf.viewAlert(" error saveInTable_vrs_accessDownload "
					+ ex.getMessage());
		}
	}

	public String saveInTable_vrs_access(String data, int computer_id) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL",
					obj.get("id_vrs_sync_itemsLOCAL").toString());
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("vrs_access").toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			String sql = "";
			if (action.equalsIgnoreCase("add")) {
				vrs_access vacc = new vrs_access();

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					vacc.setCompany_id(vc.getCompanyId());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_page_registry.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("Page_id_Composite")
								.toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_page_registry vpr = (vrs_page_registry) it.next();
					vacc.setPage_id(vpr.getId());
				}

				vacc.setPositiones(dataVrs_users.get("positiones").toString());
				vacc.setViewed(Boolean.valueOf(dataVrs_users.get("viewed")
						.toString()));
				vacc.setCreated(Boolean.valueOf(dataVrs_users.get("created")
						.toString()));
				vacc.setUpdated(Boolean.valueOf(dataVrs_users.get("updated")
						.toString()));
				vacc.setDeleted(Boolean.valueOf(dataVrs_users.get("deleted")
						.toString()));
				vacc.setCreated_time(getTimeStamp(dataVrs_users.get(
						"created_time").toString()));
				vacc.setModified(getTimeStamp(dataVrs_users.get("modified")
						.toString()));

				sessionFactory.getCurrentSession().save(vacc);
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
						vrs_access.class);
				criteria.add(Restrictions.eq("id", vacc.getId()));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_access vaccs = (vrs_access) it.next();
					objView.put("id_CompositeFromServer",
							vaccs.getId_composite());
					getBack = objView.toString();
				}

			} else if (action.equalsIgnoreCase("edit")) {
				int idTable = -1;
				int companyID = -1;
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
							.createCriteria(vrs_access.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));
					criteria.add(Restrictions.eq("company_id", companyID));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_access vaccs = (vrs_access) it.next();
						idTable = vaccs.getId();
					}
				}

				int pageID = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_page_registry.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("Page_id_Composite")
								.toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_page_registry vpr = (vrs_page_registry) it.next();
					pageID = vpr.getId();
				}
				sql = "update vrs_access set company_id=:company_id,positiones=:positiones,"
						+ " page_id=:page_id,viewed=:viewed,created=:created,"
						+ " updated=:updated,deleted=:deleted,created_time=:created_time,"
						+ " modified=:modified" + "  where id=:id";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)
						.setLong("id", idTable)
						.setInteger("company_id", companyID)
						.setInteger("page_id", pageID)
						.setString("positiones",
								dataVrs_users.get("positiones").toString())
						.setBoolean(
								"viewed",
								Boolean.valueOf(dataVrs_users.get("viewed")
										.toString()))
						.setBoolean(
								"created",
								Boolean.valueOf(dataVrs_users.get("created")
										.toString()))
						.setBoolean(
								"updated",
								Boolean.valueOf(dataVrs_users.get("updated")
										.toString()))
						.setBoolean(
								"deleted",
								Boolean.valueOf(dataVrs_users.get("deleted")
										.toString()))
						.setTimestamp(
								"created_time",
								getTimeStamp(dataVrs_users.get("created_time")
										.toString()))
						.setTimestamp(
								"modified",
								getTimeStamp(dataVrs_users.get("modified")
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
			getBack += " error saveInTable_vrs_access " + ex.getMessage();
		}

		return getBack;
	}

	

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String saveInTable_vrs_roles(String data, String userComposite,
			int computer_id) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL",
					obj.get("id_vrs_sync_itemsLOCAL").toString());

			String action = obj.get("action").toString();

			if (action.equalsIgnoreCase("add")) {
				JSONArray objData = new JSONArray(obj.get("vrs_roles")
						.toString());
				JSONObject dataVrs_users = objData.getJSONObject(0);
				vrs_roles vs = new vrs_roles();

				vs.setCreated(getTimeStamp(dataVrs_users.get("created")
						.toString()));
				vs.setModified(getTimeStamp(dataVrs_users.get("modified")
						.toString()));
				vs.setRole(dataVrs_users.get("role").toString());
				vs.setStatus(Integer.valueOf(dataVrs_users.get("status")
						.toString()));

				Criteria criteria;
				Iterator it;
				if (userComposite.length() < 1) {
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_users.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"id_composite_users").toString())));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_users vus = (vrs_users) it.next();
						vs.setUserId(vus.getId());
					}
				} else {
					vs.setUserId(Integer.valueOf(userComposite));
				}

				int companyId = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"id_composite_companies").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vco = (vrs_companies) it.next();
					companyId = vco.getCompanyId();
					vs.setCompanyId(companyId);
				}

				sessionFactory.getCurrentSession().save(vs);
				int role_id = vs.getRoleId();
				objView.put("id_compositesaved", String.valueOf(role_id));
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
							"vrs_role_metas").toString());
					JSONObject objData_meta1 = null;
					int i = 0;
					for (i = 0; i < objData_meta.length(); i++) {
						objData_meta1 = objData_meta.getJSONObject(i);
						String valueDAta = "";
						String keyDAta = objData_meta1.get("roleMetasKey")
								.toString();

						if (keyDAta.equalsIgnoreCase("people_photo")) {
							valueDAta = objData_meta1.get(
									"roleMetasValueComposite").toString();
							criteria = sessionFactory.getCurrentSession()
									.createCriteria(vrs_file_library.class);
							criteria.add(Restrictions.eq("id_composite",
									Integer.valueOf(valueDAta)));
							criteria.add(Restrictions.eq("company_id",
									companyId));
							it = criteria.list().iterator();
							if (it.hasNext()) {
								vrs_file_library vfl = (vrs_file_library) it
										.next();
								valueDAta = String.valueOf(vfl.getId());

							}
						} else {
							valueDAta = objData_meta1.get("roleMetasValue")
									.toString();
						}

						vrs_role_metas vrm = new vrs_role_metas();
						vrm.setCreated(getTimeStamp(objData_meta1
								.get("created").toString()));
						vrm.setRoleMetasKey(keyDAta);
						vrm.setModified(getTimeStamp(objData_meta1.get(
								"modified").toString()));
						vrm.setRoleId(role_id);
						vrm.setRoleMetasValue(valueDAta);
						sessionFactory.getCurrentSession().save(vrm);
					}
					sessionFactory.getCurrentSession().clear();
				} catch (Exception ex) {
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", role_id));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_roles vr = (vrs_roles) it.next();
					objView.put("id_CompositeFromServer", vr.getId_composite());
					getBack = objView.toString();
				}
			} else if (action.equalsIgnoreCase("edit")) {
				vrs_roles vr = null;
				JSONArray objData = new JSONArray(obj.get("vrs_roles")
						.toString());
				JSONObject dataVrs_users = objData.getJSONObject(0);
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"id_composite_companies").toString())));
				int companyID = -1, roleID = -1, userID = -1;
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {

					vrs_companies vc = (vrs_companies) it.next();
					companyID = vc.getCompanyId();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq("companyId", companyID));
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("roleCompositeID")
									.toString())));
					it = criteria.list().iterator();
					if (it.hasNext()) {

						vr = (vrs_roles) it.next();
						roleID = vr.getRoleId();
						criteria = sessionFactory.getCurrentSession()
								.createCriteria(vrs_users.class);
						criteria.add(Restrictions.eq(
								"id_composite",
								Integer.valueOf(dataVrs_users.get(
										"id_composite_users").toString())));
						it = criteria.list().iterator();
						if (it.hasNext()) {

							vrs_users vuu = (vrs_users) it.next();
							userID = vuu.getId();
						}

						sessionFactory.getCurrentSession().clear();

						String sql = "";

						sql = "update vrs_roles set created=:created,modified=:modified"
								+ ",role=:role,status=:status,userId=:userId"
								+ ",companyId=:companyId,id_composite=:id_composite where roleId=:roleId";
						sessionFactory
								.getCurrentSession()
								.createQuery(sql)
								.setInteger("roleId", roleID)
								.setTimestamp(
										"created",
										getTimeStamp(dataVrs_users.get(
												"created").toString()))
								.setTimestamp(
										"modified",
										getTimeStamp(dataVrs_users.get(
												"modified").toString()))
								.setString("role",
										dataVrs_users.get("role").toString())
								.setInteger(
										"status",
										Integer.valueOf(dataVrs_users.get(
												"status").toString()))
								.setInteger("userId", userID)
								.setInteger("companyId", companyID)
								.setInteger(
										"id_composite",
										Integer.valueOf(dataVrs_users.get(
												"roleCompositeID").toString()))
								.executeUpdate();

						sessionFactory.getCurrentSession().clear();
						sql = "SELECT last_value from vrs_sync_items_id_seq";
						it = sessionFactory.getCurrentSession()
								.createSQLQuery(sql).list().iterator();
						int id_vrs_sync_items = 0;
						if (it.hasNext()) {
							id_vrs_sync_items = Integer.valueOf(it.next()
									.toString());
							vrs_sync_computerlist vsc = new vrs_sync_computerlist();
							vsc.setId_computer(computer_id);
							vsc.setId_vrs_sync_items(id_vrs_sync_items);
							Timestamp tstamp = new Timestamp(
									new Date().getTime());
							vsc.setTime_created(tstamp);
							sessionFactory.getCurrentSession().save(vsc);
							objView.put("id_vrs_sync_items", id_vrs_sync_items);
						}

						try {
							JSONArray objData_meta = new JSONArray(obj.get(
									"vrs_role_metas").toString());
							JSONObject objData_meta1 = null;
							int i = 0;
							for (i = 0; i < objData_meta.length(); i++) {
								objData_meta1 = objData_meta.getJSONObject(i);
								String valueDAta = "";
								String keyDAta = objData_meta1.get(
										"roleMetasKey").toString();

								if (keyDAta.equalsIgnoreCase("people_photo")) {
									valueDAta = objData_meta1.get(
											"roleMetasValueComposite")
											.toString();
									criteria = sessionFactory
											.getCurrentSession()
											.createCriteria(
													vrs_file_library.class);
									criteria.add(Restrictions.eq(
											"id_composite",
											Integer.valueOf(valueDAta)));
									criteria.add(Restrictions.eq("company_id",
											companyID));
									it = criteria.list().iterator();
									if (it.hasNext()) {
										vrs_file_library vfl = (vrs_file_library) it
												.next();
										valueDAta = String.valueOf(vfl.getId());

									}
								} else {
									valueDAta = objData_meta1.get(
											"roleMetasValue").toString();
								}

								addRolesMetas(
										roleID,
										keyDAta,
										valueDAta,
										getTimeStamp(objData_meta1.get(
												"created").toString()),
										getTimeStamp(objData_meta1.get(
												"modified").toString()));

							}
							sessionFactory.getCurrentSession().clear();
						} catch (Exception ex) {
						}
						getBack = objView.toString();
					}
				}
			}

		} catch (Exception ex) {
			cf.viewAlert(" error saveInTable_vrs_roles " + ex.getMessage());
		}
		return getBack;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addRolesMetas(int role_id, String key, String value,
			Timestamp created, Timestamp modified) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_role_metas.class);
		criteria.add(Restrictions.eq("roleId", role_id));
		criteria.add(Restrictions.eq("roleMetasKey", key));
		if (criteria.list().size() < 1) {
			vrs_role_metas vrsave = new vrs_role_metas();
			vrsave.setCreated(created);
			vrsave.setModified(modified);
			vrsave.setRoleId(role_id);
			vrsave.setRoleMetasKey(key);
			vrsave.setRoleMetasValue(value);
			sessionFactory.getCurrentSession().save(vrsave);
		} else {
			String hql = "update vrs_role_metas set roleMetasValue=:value,modified=:modified where roleId=:role_id and roleMetasKey=:key";
			Session sesi = sessionFactory.getCurrentSession();
			sesi.createQuery(hql).setInteger("role_id", role_id)
					.setString("value", value).setString("key", key)
					.setTimestamp("modified", modified).executeUpdate();
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String receiveUploadData(String data, int computer_id) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			String tableName = obj.get("tableName").toString();
			cf.viewAlert("receiveUploadData tableName = " + tableName);
			if (tableName.equalsIgnoreCase("vrs_file_library")) {
				getBack = saveInTable_vrs_file_library(data, computer_id);
			} else if (tableName.equalsIgnoreCase("vrs_users")) {
				getBack = saveInTable_vrs_users(data, computer_id);
			} else if (tableName.equalsIgnoreCase("vrs_roles")) {
				getBack = saveInTable_vrs_roles(data, "", computer_id);
			} else if (tableName.equalsIgnoreCase("vrs_companies")) {
				getBack = saveInTable_vrs_companies(data, computer_id);
			} else if (tableName.equalsIgnoreCase("vrs_logs")) {
				getBack = saveInTable_vrs_logs(data, computer_id);
			} else if (tableName.equalsIgnoreCase("vac_chart_accounts")) {
				getBack = sacc.saveInTable_vac_chart_accounts(data, computer_id);
			} else if (tableName.equalsIgnoreCase("vac_transactions")) {
				getBack = ssac.saveInTable_vac_transactions(data, "", computer_id);
			} else if (tableName.equalsIgnoreCase("vac_transaction_details")) {
				getBack = ssac.saveInTable_vac_transaction_details(data, "",
						computer_id);
			} else if (tableName.equalsIgnoreCase("vac_postings")) {
				getBack = ssac.saveInTable_vac_postings(data, "", computer_id);
			} else if (tableName.equalsIgnoreCase("vac_budgets")) {
				getBack = ssac.saveInTable_vac_budgets(data, computer_id);
			} else if (tableName.equalsIgnoreCase("vac_chart_account_balances")) {
				getBack = ssac.saveInTable_vac_chart_account_balances(data,
						computer_id);
			} else if (tableName.equalsIgnoreCase("ved_assignments")) {
				getBack = ssa.saveInTable_ved_assignments(data, computer_id);
			} else if (tableName.equalsIgnoreCase("ved_attendances")) {
				getBack = ssa.saveInTable_ved_attendances(data, computer_id);
			} else if (tableName.equalsIgnoreCase("ved_candidates")) {
				getBack = ssa.saveInTable_ved_candidates(data, "", computer_id);
			} else if (tableName.equalsIgnoreCase("ved_classes")) {
				getBack = ssa.saveInTable_ved_classes(data, computer_id);
			} else if (tableName.equalsIgnoreCase("ved_course_list")) {
				getBack = ssa.saveInTable_ved_course_list(data, computer_id);
			} else if (tableName.equalsIgnoreCase("ved_course_schedule")) {
				getBack = ssa.saveInTable_ved_course_schedule(data, computer_id);
			} else if (tableName.equalsIgnoreCase("ved_courses")) {
				getBack = ssa.saveInTable_ved_courses(data, computer_id);
			} else if (tableName.equalsIgnoreCase("ved_enrollments")) {
				getBack = ssa.saveInTable_ved_enrollments(data, computer_id);
			} else if (tableName.equalsIgnoreCase("ved_reports")) {
				getBack = ssa.saveInTable_ved_reports(data, computer_id);
			} else if (tableName.equalsIgnoreCase("ved_scores")) {
				getBack = ssa.saveInTable_ved_scores(data, computer_id);
			} else if (tableName.equalsIgnoreCase("ved_students")) {
				getBack = ssa.saveInTable_ved_students(data, computer_id);
			} else if (tableName.equalsIgnoreCase("vrs_access")) {
				getBack = saveInTable_vrs_access(data, computer_id);
			} else if (tableName.equalsIgnoreCase("vrs_page_registry")) {
				getBack = saveInTable_vrs_page_registry(data, computer_id);
			} else if (tableName.equalsIgnoreCase("vrs_rule_policies")) {
				getBack = saveInTable_vrs_rule_policies(data, computer_id);
			} else if (tableName.equalsIgnoreCase("vrs_service")) {
				getBack = saveInTable_vrs_service(data, computer_id);
			} else if (tableName.equalsIgnoreCase("vrs_user_guide")) {
				getBack = saveInTable_vrs_user_guide(data, computer_id);
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
			} else if (tableName.equalsIgnoreCase("ved_grade_report")) {
				getBack = sv1.saveInTable_ved_grade_report(data, "upload",
						computer_id);
			} else if (tableName.equalsIgnoreCase("ved_grade_report_detail")) {
				getBack = sv1.saveInTable_ved_grade_report_detail(data,
						"upload", computer_id);
			}
		} catch (Exception ex) {
			getBack += " error receiveUploadData e " + ex.getMessage();

		}
		cf.viewAlert("receiveUploadData getBack = " + getBack);
		return getBack;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateDataVrs_sync_items(String datai) {
		if (datai.length() > 0) {
			try {
				boolean alreadySetItems = false;
				JSONObject obj = new JSONObject(datai);
				try {
					int id_vrs_sync_items = Integer.valueOf(obj.get(
							"id_vrs_sync_itemsLOCAL").toString());
					int id_CompositeFromServer = -1;
					try {
						id_CompositeFromServer = Integer.valueOf(obj.get(
								"id_CompositeFromServer").toString());
					} catch (Exception ex) {
					}
					sVRS.doSetttingItems(id_vrs_sync_items,
							id_CompositeFromServer);

					alreadySetItems = true;
				} catch (Exception ex) {
					alreadySetItems = false;
				}
				if (!alreadySetItems) {
					sVRS.updateDataVrs_sync_itemsGroup(datai, alreadySetItems,
							obj);
				}

			} catch (Exception ex) {
				cf.viewAlert("error  updateDataVrs_sync_items "
						+ ex.getMessage() + " datai = " + datai);
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int getCountDataUpload(int computer_id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_sync_items.class);
		criteria.add(Restrictions.eq("status", false));
		return criteria.list().size();
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
			String CompanyListForDownload = getcompanyList(computer_id);
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
			cf.viewAlert("getDAtaDownload table_name = " + table_name);
			id_table = Integer.valueOf(String.valueOf(obj[7].toString()));
			if (table_name.equalsIgnoreCase("vrs_file_library")) {
				getBack = getvrs_file_library_DAtaDownload(id_table,
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name.equals("vrs_users")) {
				getBack = getvrs_userus_DAtaForDownload(id_table,
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
				getBack = sacc.getVed_studentsforDownload(getBack,
						sqlstudentLimit, obj, id_table, computer_id);
			} else if (table_name.equals("vrs_roles")) {
				getBack = getvrs_roles_DAtaDownload(id_table,
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name.equals("vrs_companies")) {
				getBack = getvrs_companies_DAtaDownload(id_table,
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name.equals("vrs_logs")) {
				getBack = getvrs_logs_DAtaDownload(id_table, obj[4].toString(),
						Integer.valueOf(obj[0].toString()));
			} else if (table_name.equals("vac_chart_accounts")) {
				getBack = ssac.getvac_chart_accounts_DAtaDownload(id_table,
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name.equals("vac_transactions")) {
				getBack = ssac.getvac_transactions_DAtaDownload(id_table,
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
				if (obj[4].toString().equalsIgnoreCase("add")) {
					getBack = sacc.getVac_transactionsforDownload(getBack,
							sqlstudentLimit, obj, id_table, computer_id);
				}
			} else if (table_name.equals("vac_transaction_details")) {
				getBack = ssac.getvac_transaction_details_DAtaDownload(id_table,
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name.equals("vac_postings")) {
				getBack = ssac.getvac_postings_DAtaDownload(id_table,
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name.equalsIgnoreCase("vac_budgets")) {
				getBack = ssac.getvac_budgets_DAtaDownload(id_table,
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name
					.equalsIgnoreCase("vac_chart_account_balances")) {
				getBack = ssac.getvac_chart_account_balances_DAtaDownload(id_table,
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name.equalsIgnoreCase("ved_assignments")) {
				getBack = ssa.getved_assignments_DAtaDownload(
						Long.valueOf(id_table), obj[4].toString(),
						Integer.valueOf(obj[0].toString()));
			} else if (table_name.equalsIgnoreCase("ved_attendances")) {
				getBack = ssa.getved_attendances_DAtaDownload(
						Long.valueOf(id_table), obj[4].toString(),
						Integer.valueOf(obj[0].toString()));
			} else if (table_name.equalsIgnoreCase("ved_candidates")) {
				getBack = ssa.getved_candidates_DAtaDownload(
						Long.valueOf(id_table), obj[4].toString(),
						Integer.valueOf(obj[0].toString()));
			} else if (table_name.equalsIgnoreCase("ved_classes")) {
				getBack = ssa.getved_classes_DAtaDownload(Long.valueOf(id_table),
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name.equalsIgnoreCase("ved_course_list")) {
				getBack = ssa.getved_course_list_DAtaDownload(id_table,
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name.equalsIgnoreCase("ved_course_schedule")) {
				getBack = ssa.getved_course_schedule_DAtaDownload(id_table,
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name.equalsIgnoreCase("ved_courses")) {
				getBack = ssa.getved_courses_DAtaDownload(Long.valueOf(id_table),
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name.equalsIgnoreCase("ved_enrollments")) {
				getBack = ssa.getved_enrollments_DAtaDownload(
						Long.valueOf(id_table), obj[4].toString(),
						Integer.valueOf(obj[0].toString()));
			} else if (table_name.equalsIgnoreCase("ved_reports")) {
				getBack = ssa.getved_reports_DAtaDownload(Long.valueOf(id_table),
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name.equalsIgnoreCase("ved_scores")) {
				getBack = ssa.getved_scores_DAtaDownload(Long.valueOf(id_table),
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name.equalsIgnoreCase("ved_students")) {
				getBack = ssa.getved_students_DAtaDownload(Long.valueOf(id_table),
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name.equalsIgnoreCase("vrs_access")) {
				getBack = getvrs_access_DAtaDownload(id_table,
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name.equalsIgnoreCase("vrs_page_registry")) {
				getBack = getvrs_page_registry_DAtaDownload(id_table,
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name.equalsIgnoreCase("vrs_rule_policies")) {
				getBack = getvrs_rule_policies_DAtaDownload(
						Long.valueOf(id_table), obj[4].toString(),
						Integer.valueOf(obj[0].toString()));
			} else if (table_name.equalsIgnoreCase("vrs_service")) {
				getBack = getvrs_service_DAtaDownload(id_table,
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name.equalsIgnoreCase("vrs_user_guide")) {
				getBack = getvrs_user_guide_DAtaDownload(id_table,
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name.equalsIgnoreCase("vrs_addresses")) {
				getBack = sVRS.getvrs_addresses_DAtaDownload(id_table,
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name.equalsIgnoreCase("vrs_attendances")) {
				getBack = sVRS.getvrs_attendances_DAta(id_table,
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name.equalsIgnoreCase("vrs_subscriptions")) {
				getBack = sVRS.getvrs_subscriptions(id_table,
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name.equalsIgnoreCase("vrs_taxonomies")) {
				getBack = sVRS.getvrs_taxonomies(id_table, obj[4].toString(),
						Integer.valueOf(obj[0].toString()));
			} else if (table_name.equals("vrs_server_registry")) {
				getBack = sVRS.getvrs_server_registry(id_table,
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name.equals("vrs_accounts")) {
				getBack = sVRS.getvrs_accounts(id_table, obj[4].toString(),
						Integer.valueOf(obj[0].toString()));
			} else if (table_name.equals("ved_grade_report")) {
				getBack = sv1.get_ved_grade_report(id_table, obj[4].toString(),
						Integer.valueOf(obj[0].toString()));
			} else if (table_name.equals("ved_grade_report_detail")) {
				getBack = sv1.get_ved_grade_report_detail(id_table,
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			}
		}
		cf.viewAlert("getDAtaDownload getBack = " + getBack);
		return getBack;
	}

	@Autowired
	serviceSyncAcademic1 sv1;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void saveInTable_vrs_rolesDownload(String data) {
		try {

			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			JSONArray objData = new JSONArray(obj.get("vrs_roles").toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			String action = obj.get("action").toString();

			if (action.equalsIgnoreCase("add")) {
				vrs_roles vs = new vrs_roles();

				vs.setCreated(getTimeStamp(dataVrs_users.get("created")
						.toString()));
				vs.setModified(getTimeStamp(dataVrs_users.get("modified")
						.toString()));
				vs.setRole(dataVrs_users.get("role").toString());
				vs.setStatus(Integer.valueOf(dataVrs_users.get("status")
						.toString()));
				int id_compositeThis = Integer.valueOf(dataVrs_users.get(
						"id_composite").toString());
				vs.setId_composite(id_compositeThis);

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_users.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("id_composite_users")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_users vus = (vrs_users) it.next();
					vs.setUserId(vus.getId());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"id_composite_companies").toString())));
				it = criteria.list().iterator();
				int companyId = -1;
				if (it.hasNext()) {
					vrs_companies vco = (vrs_companies) it.next();
					companyId = vco.getCompanyId();
					vs.setCompanyId(companyId);
				}

				String sql = "ALTER TABLE vrs_roles DISABLE TRIGGER insert_vrs_rolest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();
				sVRS.changeIdComposite_vrs_roles(id_compositeThis, companyId,
						"vrs_roles");
				sessionFactory.getCurrentSession().save(vs);
				sessionFactory.getCurrentSession().clear();
				sql = "ALTER TABLE vrs_roles ENABLE TRIGGER insert_vrs_rolest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				int role_id = vs.getRoleId();

				try {
					JSONArray objData_meta = new JSONArray(obj.get(
							"vrs_role_metas").toString());
					JSONObject objData_meta1 = null;
					int i = 0;
					for (i = 0; i < objData_meta.length(); i++) {

						objData_meta1 = objData_meta.getJSONObject(i);
						String valueDAta = "";
						String keyDAta = objData_meta1.get("roleMetasKey")
								.toString();
						if (keyDAta.equalsIgnoreCase("people_photo")) {
							valueDAta = objData_meta1.get(
									"roleMetasValueComposite").toString();
							criteria = sessionFactory.getCurrentSession()
									.createCriteria(vrs_file_library.class);
							criteria.add(Restrictions.eq("id_composite",
									Integer.valueOf(valueDAta)));
							criteria.add(Restrictions.eq("company_id",
									companyId));
							it = criteria.list().iterator();
							if (it.hasNext()) {
								vrs_file_library vfl = (vrs_file_library) it
										.next();
								valueDAta = String.valueOf(vfl.getId());
							}
						} else {
							valueDAta = objData_meta1.get("roleMetasValue")
									.toString();
						}

						vrs_role_metas vrm = new vrs_role_metas();
						vrm.setCreated(getTimeStamp(objData_meta1
								.get("created").toString()));
						vrm.setRoleMetasKey(keyDAta);
						vrm.setModified(getTimeStamp(objData_meta1.get(
								"modified").toString()));
						vrm.setRoleId(role_id);
						vrm.setRoleMetasValue(valueDAta);
						sessionFactory.getCurrentSession().save(vrm);
					}
				} catch (Exception ex) {
				}
			} else if (action.equalsIgnoreCase("edit")) {
				vrs_roles vr = null;
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"id_composite_companies").toString())));
				int companyID = -1, roleID = -1, userID = -1;
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {

					vrs_companies vc = (vrs_companies) it.next();
					companyID = vc.getCompanyId();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq("companyId", companyID));
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));
					it = criteria.list().iterator();
					if (it.hasNext()) {

						vr = (vrs_roles) it.next();
						roleID = vr.getRoleId();
						criteria = sessionFactory.getCurrentSession()
								.createCriteria(vrs_users.class);
						criteria.add(Restrictions.eq(
								"id_composite",
								Integer.valueOf(dataVrs_users.get(
										"id_composite_users").toString())));
						it = criteria.list().iterator();
						if (it.hasNext()) {

							vrs_users vuu = (vrs_users) it.next();
							userID = vuu.getId();
						}

						sessionFactory.getCurrentSession().clear();

						String sql = "ALTER TABLE vrs_roles DISABLE TRIGGER insert_vrs_rolest";
						sessionFactory.getCurrentSession().createSQLQuery(sql)
								.executeUpdate();

						sql = "update vrs_roles set created=:created,modified=:modified"
								+ ",role=:role,status=:status,userId=:userId"
								+ ",companyId=:companyId,id_composite=:id_composite where roleId=:roleId";
						sessionFactory
								.getCurrentSession()
								.createQuery(sql)
								.setInteger("roleId", roleID)
								.setTimestamp(
										"created",
										getTimeStamp(dataVrs_users.get(
												"created").toString()))
								.setTimestamp(
										"modified",
										getTimeStamp(dataVrs_users.get(
												"modified").toString()))
								.setString("role",
										dataVrs_users.get("role").toString())
								.setInteger(
										"status",
										Integer.valueOf(dataVrs_users.get(
												"status").toString()))
								.setInteger("userId", userID)
								.setInteger("companyId", companyID)
								.setInteger(
										"id_composite",
										Integer.valueOf(dataVrs_users.get(
												"id_composite").toString()))
								.executeUpdate();
						sessionFactory.getCurrentSession().clear();
						sql = "ALTER TABLE vrs_roles ENABLE TRIGGER insert_vrs_rolest";
						sessionFactory.getCurrentSession().createSQLQuery(sql)
								.executeUpdate();

						try {
							JSONArray objData_meta = new JSONArray(obj.get(
									"vrs_role_metas").toString());
							JSONObject objData_meta1 = null;
							int i = 0;
							for (i = 0; i < objData_meta.length(); i++) {
								objData_meta1 = objData_meta.getJSONObject(i);
								String valueDAta = "";
								String keyDAta = objData_meta1.get(
										"roleMetasKey").toString();

								if (keyDAta.equalsIgnoreCase("people_photo")) {
									valueDAta = objData_meta1.get(
											"roleMetasValueComposite")
											.toString();
									criteria = sessionFactory
											.getCurrentSession()
											.createCriteria(
													vrs_file_library.class);
									criteria.add(Restrictions.eq(
											"id_composite",
											Integer.valueOf(valueDAta)));
									criteria.add(Restrictions.eq("company_id",
											companyID));
									it = criteria.list().iterator();
									if (it.hasNext()) {
										vrs_file_library vfl = (vrs_file_library) it
												.next();
										valueDAta = String.valueOf(vfl.getId());
									}
								} else {
									valueDAta = objData_meta1.get(
											"roleMetasValue").toString();
								}

								addRolesMetas(
										roleID,
										keyDAta,
										valueDAta,
										getTimeStamp(objData_meta1.get(
												"created").toString()),
										getTimeStamp(objData_meta1.get(
												"modified").toString()));
							}
							sessionFactory.getCurrentSession().clear();
						} catch (Exception ex) {
						}
					}
				}
			}
			sessionFactory.getCurrentSession().clear();

		} catch (Exception ex) {
			cf.viewAlert(" error in saveInTable_vrs_rolesDownload "
					+ ex.getMessage());
		}
	}

	public void saveInTable_vrs_logsDownload(String data) {
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			JSONArray objData = new JSONArray(obj.get("vrs_logs").toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			String action = obj.get("action1").toString();

			int roleID = -1;
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(vrs_companies.class);
			criteria.add(Restrictions.eq(
					"id_composite",
					Integer.valueOf(dataVrs_users.get(
							"rolecompany_id_composite").toString())));
			Iterator it = criteria.list().iterator();
			if (it.hasNext()) {
				vrs_companies vco = (vrs_companies) it.next();
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("role_id_composite")
								.toString())));
				criteria.add(Restrictions.eq("companyId", vco.getCompanyId()));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_roles vr = (vrs_roles) it.next();
					roleID = vr.getRoleId();
				}
			}

			int companyId = -1;
			criteria = sessionFactory.getCurrentSession().createCriteria(
					vrs_companies.class);
			criteria.add(Restrictions.eq("id_composite", Integer
					.valueOf(dataVrs_users.get("company_id_composite")
							.toString())));
			it = criteria.list().iterator();
			if (it.hasNext()) {
				vrs_companies vco = (vrs_companies) it.next();
				companyId = vco.getCompanyId();

			}

			String tableName = dataVrs_users.get("tableName").toString();
			if (action.equalsIgnoreCase("add")) {

				vrs_logs vc = new vrs_logs();

				vc.setTableName(tableName);
				vc.setAction(dataVrs_users.get("action").toString());
				vc.setModified(getTimeStamp(dataVrs_users.get("modified")
						.toString()));
				vc.setReason(dataVrs_users.get("reason").toString());

				vc.setCompanyId(companyId);

				vc.setRoleId(roleID);
				vc.setTableId(getTableId(tableName, dataVrs_users));
				String sql = "ALTER TABLE vrs_logs DISABLE TRIGGER insert_vrs_logst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();
				sessionFactory.getCurrentSession().save(vc);
				int company_id = vc.getCompanyId();
				sessionFactory.getCurrentSession().clear();
				sql = "ALTER TABLE vrs_logs ENABLE TRIGGER insert_vrs_logst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

			} else if (action.equalsIgnoreCase("edit")) {
				String sql = "ALTER TABLE vrs_logs DISABLE TRIGGER insert_vrs_logst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();
				sql = "update vrs_logs set modified=:modified "
						+ " ,companyId=:companyId,roleId=:roleId,tableName=:tableName "
						+ " ,tableId=:tableId,action=:action,reason=:reason"
						+ "  where id=:id";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)
						.setTimestamp(
								"modified",
								getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("companyId", companyId)
						.setInteger("roleId", roleID)
						.setString("tableName", tableName)
						.setInteger("tableId",
								getTableId(tableName, dataVrs_users))
						.setString("action",
								dataVrs_users.get("action").toString())
						.setString("reason",
								dataVrs_users.get("reason").toString())
						.setInteger(
								"id",
								Integer.valueOf(dataVrs_users.get("logid")
										.toString())).executeUpdate();
				sql = "ALTER TABLE vrs_logs ENABLE TRIGGER insert_vrs_logst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

			}
		} catch (Exception ex) {
			cf.viewAlert("error in saveInTable_vrs_logs = " + ex.getMessage());
		}
	}

	

	public void saveInTable_vrs_companiesDownload(String data) {
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("vrs_companies")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			if (action.equalsIgnoreCase("add")) {
				vrs_companies vc = new vrs_companies();
				vc.setCompanyName(dataVrs_users.get("companyName").toString());
				vc.setCompanyType(dataVrs_users.get("companyType").toString());
				vc.setCreated(getTimeStamp(dataVrs_users.get("created")
						.toString()));
				vc.setModified(getTimeStamp(dataVrs_users.get("modified")
						.toString()));
				vc.setStatus(Integer.valueOf(dataVrs_users.get("status")
						.toString()));
				vc.setId_composite(Integer.valueOf(dataVrs_users.get(
						"id_composite").toString()));

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("Company-createdBy")
								.toString())));
				Iterator it = criteria.list().iterator();
				vrs_companies vsc = null;
				int companyRoles = 0;
				vrs_roles vr = null;
				if (it.hasNext()) {
					vsc = (vrs_companies) it.next();
					companyRoles = vsc.getCompanyId();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("createdBy").toString())));
					criteria.add(Restrictions.eq("companyId", companyRoles));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vr = (vrs_roles) it.next();
						vc.setCreatedBy(vr.getRoleId());
					}

				}
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("Company-modifiedBy")
								.toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vsc = (vrs_companies) it.next();
					companyRoles = vsc.getCompanyId();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq("id_composite",
							Integer.valueOf(dataVrs_users.get("modifiedBy")
									.toString())));
					criteria.add(Restrictions.eq("companyId", companyRoles));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vr = (vrs_roles) it.next();
						vc.setModifiedBy(vr.getRoleId());
					}
				}

				companyRoles = Integer.valueOf(dataVrs_users.get("parent")
						.toString());
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", companyRoles));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vsc = (vrs_companies) it.next();
					vc.setParent(vsc.getCompanyId());
				}

				String sql = "ALTER TABLE vrs_companies DISABLE TRIGGER insert_vrs_companiest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();
				sessionFactory.getCurrentSession().save(vc);
				sessionFactory.getCurrentSession().clear();
				sql = "ALTER TABLE vrs_companies ENABLE TRIGGER insert_vrs_companiest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				int company_id = vc.getCompanyId();

				try {
					JSONArray objData_meta = new JSONArray(obj.get(
							"vrs_company_metas").toString());
					JSONObject objData_meta1 = null;
					int i = 0;
					for (i = 0; i < objData_meta.length(); i++) {
						objData_meta1 = objData_meta.getJSONObject(i);
						vrs_company_metas vcm = new vrs_company_metas();
						String metaValue = "";
						try {
							metaValue = objData_meta1.get("companyMetasValue")
									.toString();
						} catch (Exception ex) {
						}
						if (objData_meta1.get("companyMetasKey").equals(
								"School_headmaster")) {
							criteria = sessionFactory.getCurrentSession()
									.createCriteria(vrs_companies.class);
							criteria.add(Restrictions.eq("id_composite",
									Integer.valueOf(objData_meta1.get(
											"role_company_idComposite")
											.toString())));
							it = criteria.list().iterator();
							if (it.hasNext()) {
								vrs_companies vco = (vrs_companies) it.next();
								criteria = sessionFactory.getCurrentSession()
										.createCriteria(vrs_roles.class);

								criteria.add(Restrictions
										.eq("id_composite", Integer
												.valueOf(objData_meta1.get(
														"role_idComposite")
														.toString())));
								criteria.add(Restrictions.eq("companyId",
										vco.getCompanyId()));
								it = criteria.list().iterator();
								if (it.hasNext()) {
									vr = (vrs_roles) it.next();
									metaValue = String.valueOf(vr.getRoleId());
								}
							}
						} else if (objData_meta1.get("companyMetasKey").equals(
								"CompanyPhoto")) {
							metaValue = "0";
							try {
								criteria = sessionFactory.getCurrentSession()
										.createCriteria(vrs_companies.class);
								criteria.add(Restrictions.eq("id_composite",
										Integer.valueOf(objData_meta1.get(
												"id_composite_company")
												.toString())));
								it = criteria.list().iterator();
								if (it.hasNext()) {
									vrs_companies vco = (vrs_companies) it
											.next();
									criteria = sessionFactory
											.getCurrentSession()
											.createCriteria(
													vrs_file_library.class);
									criteria.add(Restrictions.eq("company_id",
											vco.getCompanyId()));
									criteria.add(Restrictions.eq(
											"id_composite",
											Integer.valueOf(objData_meta1
													.get("id_composite_vrs_file_library")
													.toString())));
									it = criteria.list().iterator();
									if (it.hasNext()) {
										vrs_file_library vfl = (vrs_file_library) it
												.next();
										metaValue = String.valueOf(vfl.getId());
									}
								}
							} catch (Exception ex) {
							}
						}

						vcm.setCreated(getTimeStamp(objData_meta1
								.get("created").toString()));
						vcm.setCompanyMetasKey(objData_meta1.get(
								"companyMetasKey").toString());
						vcm.setModified(getTimeStamp(objData_meta1.get(
								"modified").toString()));
						vcm.setCompanyId(company_id);
						vcm.setCompanyMetasValue(metaValue);

						sessionFactory.getCurrentSession().save(vcm);
					}
				} catch (Exception ex) {
					cf.viewAlert(" error in add company " + ex.getMessage());
				}

			} else if (action.equalsIgnoreCase("edit")) {
				int companyID = -1, createdBy = -1, modifiedBy = -1, companyPArent = -1;
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("id_composite").toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					companyID = vc.getCompanyId();

					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"Company-createdBy").toString())));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_companies vco = (vrs_companies) it.next();
						criteria = sessionFactory.getCurrentSession()
								.createCriteria(vrs_roles.class);
						criteria.add(Restrictions.eq("id_composite", Integer
								.valueOf(dataVrs_users.get("createdBy")
										.toString())));
						criteria.add(Restrictions.eq("companyId",
								vco.getCompanyId()));
						it = criteria.list().iterator();
						if (it.hasNext()) {
							vrs_roles vr = (vrs_roles) it.next();
							createdBy = vr.getRoleId();
						}
					}

					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"Company-modifiedBy").toString())));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_companies vco = (vrs_companies) it.next();
						criteria = sessionFactory.getCurrentSession()
								.createCriteria(vrs_roles.class);
						criteria.add(Restrictions.eq("id_composite", Integer
								.valueOf(dataVrs_users.get("modifiedBy")
										.toString())));
						criteria.add(Restrictions.eq("companyId",
								vco.getCompanyId()));
						it = criteria.list().iterator();
						if (it.hasNext()) {
							vrs_roles vr = (vrs_roles) it.next();
							modifiedBy = vr.getRoleId();
						}
					}

					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("parent").toString())));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_companies vco = (vrs_companies) it.next();
						companyPArent = vco.getCompanyId();
					}

					String sql = "ALTER TABLE vrs_companies DISABLE TRIGGER insert_vrs_companiest";
					sessionFactory.getCurrentSession().createSQLQuery(sql)
							.executeUpdate();

					sql = "update vrs_companies set companyName=:companyName"
							+ ",companyType=:companyType, created=:created,"
							+ "createdBy=:createdBy,modified=:modified,"
							+ "modifiedBy=:modifiedBy,parent=:parent,"
							+ "status=:status "
							+ " where companyId=:companyId and id_composite=:id_composite ";
					sessionFactory
							.getCurrentSession()
							.createQuery(sql)
							.setString("companyName",
									dataVrs_users.get("companyName").toString())
							.setString("companyType",
									dataVrs_users.get("companyType").toString())
							.setTimestamp(
									"created",
									getTimeStamp(dataVrs_users.get("created")
											.toString()))
							.setInteger("createdBy", createdBy)
							.setTimestamp(
									"modified",
									getTimeStamp(dataVrs_users.get("modified")
											.toString()))
							.setInteger("modifiedBy", modifiedBy)
							.setInteger("parent", companyPArent)
							.setInteger(
									"status",
									Integer.valueOf(dataVrs_users.get("status")
											.toString()))
							.setInteger(
									"id_composite",
									Integer.valueOf(dataVrs_users.get(
											"id_composite").toString()))
							.setInteger("companyId", companyID)

							.executeUpdate();
					sql = "ALTER TABLE vrs_companies ENABLE TRIGGER insert_vrs_companiest";
					sessionFactory.getCurrentSession().createSQLQuery(sql)
							.executeUpdate();

					try {
						JSONArray objData_meta = new JSONArray(obj.get(
								"vrs_company_metas").toString());
						JSONObject objData_meta1 = null;
						int i = 0;
						for (i = 0; i < objData_meta.length(); i++) {
							objData_meta1 = objData_meta.getJSONObject(i);
							String metaValue = "";

							try {
								metaValue = objData_meta1.get(
										"companyMetasValue").toString();
							} catch (Exception ex) {
							}

							if (objData_meta1.get("companyMetasKey").equals(
									"School_headmaster")) {
								criteria = sessionFactory.getCurrentSession()
										.createCriteria(vrs_companies.class);
								criteria.add(Restrictions.eq("id_composite",
										Integer.valueOf(objData_meta1.get(
												"role_company_idComposite")
												.toString())));
								it = criteria.list().iterator();
								if (it.hasNext()) {
									vrs_companies vco = (vrs_companies) it
											.next();
									criteria = sessionFactory
											.getCurrentSession()
											.createCriteria(vrs_roles.class);
									criteria.add(Restrictions.eq(
											"id_composite", Integer
													.valueOf(objData_meta1.get(
															"role_idComposite")
															.toString())));
									criteria.add(Restrictions.eq("companyId",
											vco.getCompanyId()));
									it = criteria.list().iterator();
									if (it.hasNext()) {
										vrs_roles vr = (vrs_roles) it.next();
										metaValue = String.valueOf(vr
												.getRoleId());
									}
								}
							} else if (objData_meta1.get("companyMetasKey")
									.equals("CompanyPhoto")) {
								metaValue = "0";
								try {
									criteria = sessionFactory
											.getCurrentSession()
											.createCriteria(vrs_companies.class);
									criteria.add(Restrictions.eq(
											"id_composite",
											Integer.valueOf(objData_meta1.get(
													"id_composite_company")
													.toString())));
									it = criteria.list().iterator();
									if (it.hasNext()) {
										vrs_companies vco = (vrs_companies) it
												.next();
										criteria = sessionFactory
												.getCurrentSession()
												.createCriteria(
														vrs_file_library.class);
										criteria.add(Restrictions.eq(
												"company_id",
												vco.getCompanyId()));
										criteria.add(Restrictions.eq(
												"id_composite",
												Integer.valueOf(objData_meta1
														.get("id_composite_vrs_file_library")
														.toString())));
										it = criteria.list().iterator();
										if (it.hasNext()) {
											vrs_file_library vfl = (vrs_file_library) it
													.next();
											metaValue = String.valueOf(vfl
													.getId());
										}
									}
								} catch (Exception ex) {
								}
							}

							addCompaniesMetas(companyID,
									objData_meta1.get("companyMetasKey")
											.toString(), metaValue,
									getTimeStamp(objData_meta1.get("created")
											.toString()),
									getTimeStamp(objData_meta1.get("modified")
											.toString()));

						}
					} catch (Exception ex) {
					}
				}
			}
			sessionFactory.getCurrentSession().clear();

		} catch (Exception ex) {
			cf.viewAlert(" error saveInTable_vrs_companiesDownload "
					+ ex.getMessage());
		}
	}

	public int getTableId(String tableName, JSONObject dataVrs_users) {
		int getBack = -1;
		try {

			if (tableName.equalsIgnoreCase("vrs_roles")) {
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"tableID-Composite-company").toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vco = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"tableID-Composite").toString())));
					criteria.add(Restrictions.eq("companyId",
							vco.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						getBack = vr.getRoleId();
					}
				}
			}
		} catch (Exception ex) {

		}

		return getBack;
	}

	public String saveInTable_vrs_logs(String data, int computer_id) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL",
					obj.get("id_vrs_sync_itemsLOCAL").toString());
			String action = obj.get("action1").toString();

			JSONArray objData = new JSONArray(obj.get("vrs_logs").toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);

			int roleID = -1;
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(vrs_companies.class);
			criteria.add(Restrictions.eq(
					"id_composite",
					Integer.valueOf(dataVrs_users.get(
							"rolecompany_id_composite").toString())));
			Iterator it = criteria.list().iterator();
			if (it.hasNext()) {
				vrs_companies vco = (vrs_companies) it.next();
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("role_id_composite")
								.toString())));
				criteria.add(Restrictions.eq("companyId", vco.getCompanyId()));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_roles vr = (vrs_roles) it.next();
					roleID = vr.getRoleId();
				}
			}

			int companyId = -1;
			criteria = sessionFactory.getCurrentSession().createCriteria(
					vrs_companies.class);
			criteria.add(Restrictions.eq("id_composite", Integer
					.valueOf(dataVrs_users.get("company_id_composite")
							.toString())));
			it = criteria.list().iterator();
			if (it.hasNext()) {
				vrs_companies vco = (vrs_companies) it.next();
				companyId = vco.getCompanyId();

			}

			String tableName = dataVrs_users.get("tableName").toString();
			if (action.equalsIgnoreCase("add")) {

				vrs_logs vc = new vrs_logs();

				vc.setTableName(tableName);
				vc.setAction(dataVrs_users.get("action").toString());
				vc.setModified(getTimeStamp(dataVrs_users.get("modified")
						.toString()));
				vc.setReason(dataVrs_users.get("reason").toString());

				vc.setCompanyId(companyId);

				vc.setRoleId(roleID);
				vc.setTableId(getTableId(tableName, dataVrs_users));
				sessionFactory.getCurrentSession().save(vc);
				int company_id = vc.getCompanyId();
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
				getBack = objView.toString();
			} else if (action.equalsIgnoreCase("edit")) {
				String sql = "update vrs_logs set modified=:modified "
						+ " ,companyId=:companyId,roleId=:roleId,tableName=:tableName "
						+ " ,tableId=:tableId,action=:action,reason=:reason"
						+ "  where id=:id";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)
						.setTimestamp(
								"modified",
								getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("companyId", companyId)
						.setInteger("roleId", roleID)
						.setString("tableName", tableName)
						.setInteger("tableId",
								getTableId(tableName, dataVrs_users))
						.setString("action",
								dataVrs_users.get("action").toString())
						.setString("reason",
								dataVrs_users.get("reason").toString())
						.setInteger(
								"id",
								Integer.valueOf(dataVrs_users.get("logid")
										.toString())).executeUpdate();

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
			cf.viewAlert(" error saveInTable_vrs_logs " + ex.getMessage());
			getBack += " error saveInTable_vrs_logs " + ex.getMessage();
		}
		return getBack;
	}

	public String saveInTable_vrs_companies(String data, int computer_id) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL",
					obj.get("id_vrs_sync_itemsLOCAL").toString());

			String action = obj.get("action").toString();

			JSONArray objData = new JSONArray(obj.get("vrs_companies")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			if (action.equalsIgnoreCase("add")) {
				vrs_companies vc = new vrs_companies();

				vc.setCompanyName(dataVrs_users.get("companyName").toString());
				vc.setCompanyType(dataVrs_users.get("companyType").toString());
				vc.setCreated(getTimeStamp(dataVrs_users.get("created")
						.toString()));
				vc.setModified(getTimeStamp(dataVrs_users.get("modified")
						.toString()));
				vc.setStatus(Integer.valueOf(dataVrs_users.get("status")
						.toString()));

				vrs_roles vr;
				vrs_companies vco;
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company-createdBy")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vco = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("createdBy").toString())));
					criteria.add(Restrictions.eq("companyId",
							vco.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vr = (vrs_roles) it.next();
						vc.setCreatedBy(vr.getRoleId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company-modifiedBy")
								.toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vco = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq("id_composite",
							Integer.valueOf(dataVrs_users.get("modifiedBy")
									.toString())));
					criteria.add(Restrictions.eq("companyId",
							vco.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vr = (vrs_roles) it.next();
						vc.setModifiedBy(vr.getRoleId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite",
						Integer.valueOf(dataVrs_users.get("parent").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vco = (vrs_companies) it.next();
					vc.setParent(vco.getCompanyId());
				}

				sessionFactory.getCurrentSession().save(vc);
				sessionFactory.getCurrentSession().clear();

				int company_id = vc.getCompanyId();
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
							"vrs_company_metas").toString());
					JSONObject objData_meta1 = null;
					int i = 0;
					for (i = 0; i < objData_meta.length(); i++) {
						objData_meta1 = objData_meta.getJSONObject(i);
						vrs_company_metas vcm = new vrs_company_metas();
						String metaValue = "";
						try {
							metaValue = objData_meta1.get("companyMetasValue")
									.toString();
						} catch (Exception ex) {
						}
						if (objData_meta1.get("companyMetasKey").equals(
								"School_headmaster")) {
							criteria = sessionFactory.getCurrentSession()
									.createCriteria(vrs_companies.class);
							criteria.add(Restrictions.eq("id_composite",
									Integer.valueOf(objData_meta1.get(
											"role_company_idComposite")
											.toString())));
							it = criteria.list().iterator();
							if (it.hasNext()) {
								vco = (vrs_companies) it.next();
								criteria = sessionFactory.getCurrentSession()
										.createCriteria(vrs_roles.class);
								criteria.add(Restrictions
										.eq("id_composite", Integer
												.valueOf(objData_meta1.get(
														"role_idComposite")
														.toString())));
								criteria.add(Restrictions.eq("companyId",
										vco.getCompanyId()));
								it = criteria.list().iterator();
								if (it.hasNext()) {
									vr = (vrs_roles) it.next();
									metaValue = String.valueOf(vr.getRoleId());
								}
							}
						} else if (objData_meta1.get("companyMetasKey").equals(
								"CompanyPhoto")) {
							metaValue = "0";
							try {
								criteria = sessionFactory.getCurrentSession()
										.createCriteria(vrs_companies.class);
								criteria.add(Restrictions.eq("id_composite",
										Integer.valueOf(objData_meta1.get(
												"id_composite_company")
												.toString())));
								it = criteria.list().iterator();
								if (it.hasNext()) {
									vco = (vrs_companies) it.next();
									criteria = sessionFactory
											.getCurrentSession()
											.createCriteria(
													vrs_file_library.class);
									criteria.add(Restrictions.eq("company_id",
											vco.getCompanyId()));
									criteria.add(Restrictions.eq(
											"id_composite",
											Integer.valueOf(objData_meta1
													.get("id_composite_vrs_file_library")
													.toString())));
									it = criteria.list().iterator();
									if (it.hasNext()) {
										vrs_file_library vfl = (vrs_file_library) it
												.next();
										metaValue = String.valueOf(vfl.getId());
									}
								}
							} catch (Exception ex) {
							}
						}

						vcm.setCreated(getTimeStamp(objData_meta1
								.get("created").toString()));
						vcm.setCompanyMetasKey(objData_meta1.get(
								"companyMetasKey").toString());
						vcm.setModified(getTimeStamp(objData_meta1.get(
								"modified").toString()));
						vcm.setCompanyId(company_id);
						vcm.setCompanyMetasValue(metaValue);

						sessionFactory.getCurrentSession().save(vcm);
					}
					sessionFactory.getCurrentSession().clear();
				} catch (Exception ex) {
					cf.viewAlert(" error  company metas in saveInTable_vrs_companies "
							+ ex.getMessage());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", company_id));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc1 = (vrs_companies) it.next();
					objView.put("id_CompositeFromServer", vc1.getId_composite());
					getBack = objView.toString();

				}

			} else if (action.equalsIgnoreCase("edit")) {
				int companyID = -1, createdBy = -1, modifiedBy = -1, companyPArent = -1;
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("id_composite").toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					companyID = vc.getCompanyId();

					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"company-createdBy").toString())));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_companies vco = (vrs_companies) it.next();
						criteria = sessionFactory.getCurrentSession()
								.createCriteria(vrs_roles.class);
						criteria.add(Restrictions.eq("id_composite", Integer
								.valueOf(dataVrs_users.get("createdBy")
										.toString())));
						criteria.add(Restrictions.eq("companyId",
								vco.getCompanyId()));
						it = criteria.list().iterator();
						if (it.hasNext()) {
							vrs_roles vr = (vrs_roles) it.next();
							createdBy = vr.getRoleId();
						}
					}

					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"company-modifiedBy").toString())));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_companies vco = (vrs_companies) it.next();
						criteria = sessionFactory.getCurrentSession()
								.createCriteria(vrs_roles.class);
						criteria.add(Restrictions.eq("id_composite", Integer
								.valueOf(dataVrs_users.get("modifiedBy")
										.toString())));
						criteria.add(Restrictions.eq("companyId",
								vco.getCompanyId()));
						it = criteria.list().iterator();
						if (it.hasNext()) {
							vrs_roles vr = (vrs_roles) it.next();
							modifiedBy = vr.getRoleId();
						}
					}

					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("parent").toString())));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_companies vco = (vrs_companies) it.next();
						companyPArent = vco.getCompanyId();
					}

					String sql = "update vrs_companies set companyName=:companyName"
							+ ",companyType=:companyType, created=:created,"
							+ "createdBy=:createdBy,modified=:modified,"
							+ "modifiedBy=:modifiedBy,parent=:parent,"
							+ "status=:status "
							+ " where companyId=:companyId and id_composite=:id_composite ";
					sessionFactory
							.getCurrentSession()
							.createQuery(sql)
							.setString("companyName",
									dataVrs_users.get("companyName").toString())
							.setString("companyType",
									dataVrs_users.get("companyType").toString())
							.setTimestamp(
									"created",
									getTimeStamp(dataVrs_users.get("created")
											.toString()))
							.setInteger("createdBy", createdBy)
							.setTimestamp(
									"modified",
									getTimeStamp(dataVrs_users.get("modified")
											.toString()))
							.setInteger("modifiedBy", modifiedBy)
							.setInteger("parent", companyPArent)
							.setInteger(
									"status",
									Integer.valueOf(dataVrs_users.get("status")
											.toString()))
							.setInteger(
									"id_composite",
									Integer.valueOf(dataVrs_users.get(
											"id_composite").toString()))
							.setInteger("companyId", companyID)

							.executeUpdate();

					sessionFactory.getCurrentSession().clear();
					sql = "SELECT last_value from vrs_sync_items_id_seq";
					it = sessionFactory.getCurrentSession().createSQLQuery(sql)
							.list().iterator();
					int id_vrs_sync_items = 0;
					if (it.hasNext()) {
						id_vrs_sync_items = Integer.valueOf(it.next()
								.toString());
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
								"vrs_company_metas").toString());
						JSONObject objData_meta1 = null;
						int i = 0;
						for (i = 0; i < objData_meta.length(); i++) {
							objData_meta1 = objData_meta.getJSONObject(i);
							String metaValue = "";

							try {
								metaValue = objData_meta1.get(
										"companyMetasValue").toString();
							} catch (Exception ex) {
							}
							if (objData_meta1.get("companyMetasKey").equals(
									"School_headmaster")) {
								criteria = sessionFactory.getCurrentSession()
										.createCriteria(vrs_companies.class);
								criteria.add(Restrictions.eq("id_composite",
										Integer.valueOf(objData_meta1.get(
												"role_company_idComposite")
												.toString())));
								it = criteria.list().iterator();
								if (it.hasNext()) {
									vrs_companies vco = (vrs_companies) it
											.next();
									criteria = sessionFactory
											.getCurrentSession()
											.createCriteria(vrs_roles.class);
									criteria.add(Restrictions.eq(
											"id_composite", Integer
													.valueOf(objData_meta1.get(
															"role_idComposite")
															.toString())));
									criteria.add(Restrictions.eq("companyId",
											vco.getCompanyId()));
									it = criteria.list().iterator();
									if (it.hasNext()) {
										vrs_roles vr = (vrs_roles) it.next();
										metaValue = String.valueOf(vr
												.getRoleId());
									}
								}
							} else if (objData_meta1.get("companyMetasKey")
									.equals("CompanyPhoto")) {
								metaValue = "0";
								try {
									criteria = sessionFactory
											.getCurrentSession()
											.createCriteria(vrs_companies.class);
									criteria.add(Restrictions.eq(
											"id_composite",
											Integer.valueOf(objData_meta1.get(
													"id_composite_company")
													.toString())));
									it = criteria.list().iterator();
									if (it.hasNext()) {
										vrs_companies vco = (vrs_companies) it
												.next();
										criteria = sessionFactory
												.getCurrentSession()
												.createCriteria(
														vrs_file_library.class);
										criteria.add(Restrictions.eq(
												"company_id",
												vco.getCompanyId()));
										criteria.add(Restrictions.eq(
												"id_composite",
												Integer.valueOf(objData_meta1
														.get("id_composite_vrs_file_library")
														.toString())));
										it = criteria.list().iterator();
										if (it.hasNext()) {
											vrs_file_library vfl = (vrs_file_library) it
													.next();
											metaValue = String.valueOf(vfl
													.getId());
										}
									}
								} catch (Exception ex) {
								}
							}
							addCompaniesMetas(companyID,
									objData_meta1.get("companyMetasKey")
											.toString(), metaValue,
									getTimeStamp(objData_meta1.get("created")
											.toString()),
									getTimeStamp(objData_meta1.get("modified")
											.toString()));

						}
					} catch (Exception ex) {
						cf.viewAlert(" error saveInTable_vrs_companies add metas "
								+ ex.getMessage());
					}
					sessionFactory.getCurrentSession().clear();
					getBack = objView.toString();
				}

			}

		} catch (Exception ex) {
			cf.viewAlert(" error saveInTable_vrs_companies " + ex.getMessage());
			getBack += " error saveInTable_vrs_companies " + ex.getMessage();
		}
		return getBack;
	}

	public void addCompaniesMetas(int company_id, String key, String value,
			Timestamp created, Timestamp modified) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_company_metas.class);
		criteria.add(Restrictions.eq("companyId", company_id));
		criteria.add(Restrictions.eq("companyMetasKey", key));
		if (criteria.list().size() < 1) {
			vrs_company_metas vcsave = new vrs_company_metas();
			vcsave.setCreated(created);
			vcsave.setModified(modified);
			vcsave.setCompanyId(company_id);
			vcsave.setCompanyMetasKey(key);
			vcsave.setCompanyMetasValue(value);
			sessionFactory.getCurrentSession().save(vcsave);
		} else {
			String hql = "update vrs_company_metas "
					+ "set companyMetasValue=:value,"
					+ "modified=:modified where companyId=:companyId and companyMetasKey=:companyMetasKey";
			Session sesi = sessionFactory.getCurrentSession();
			sesi.createQuery(hql).setInteger("companyId", company_id)
					.setString("value", value)
					.setString("companyMetasKey", key)
					.setTimestamp("modified", modified).executeUpdate();
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void saveInvrs_UsersFromdownload(String data) {
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			JSONArray objData = new JSONArray(obj.get("vrs_users").toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);

			String action = obj.get("action").toString();

			if (action.equalsIgnoreCase("add")) {
				vrs_users vs = new vrs_users();
				int userId_composite = Integer.valueOf(dataVrs_users.get(
						"id_composite").toString());
				vs.setCreated(getTimeStamp(dataVrs_users.get("created")
						.toString()));
				vs.setModified(getTimeStamp(dataVrs_users.get("modified")
						.toString()));
				vs.setParent(Integer.valueOf(dataVrs_users.get("parent")
						.toString()));
				vs.setId_composite(userId_composite);
				String sql = "ALTER TABLE vrs_users DISABLE TRIGGER insert_vrs_userst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();
				sVRS.changeIdComposite_vrs_users(userId_composite);
				sessionFactory.getCurrentSession().save(vs);
				sessionFactory.getCurrentSession().clear();
				sql = "ALTER TABLE vrs_users ENABLE TRIGGER insert_vrs_userst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				try {
					int user_id = vs.getId();
					JSONArray objData_meta = new JSONArray(obj.get(
							"vrs_user_metas").toString());
					JSONObject objData_meta1 = null;
					int i = 0;
					for (i = 0; i < objData_meta.length(); i++) {
						objData_meta1 = objData_meta.getJSONObject(i);
						vrs_user_metas vum = new vrs_user_metas();
						vum.setCreated(getTimeStamp(objData_meta1
								.get("created").toString()));
						vum.setKey(objData_meta1.get("key").toString());
						vum.setModified(getTimeStamp(objData_meta1.get(
								"modified").toString()));
						vum.setUser_id(user_id);
						vum.setValue(objData_meta1.get("value").toString());
						sessionFactory.getCurrentSession().save(vum);
					}
				} catch (Exception ex) {
				}
			} else if (action.equalsIgnoreCase("edit")) {
				int user_id = -1;
				String sql = "";
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_users.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("userCompositeID")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_users vus = (vrs_users) it.next();
					user_id = vus.getId();
					sql = "update vrs_users set modified=:modified where id=:id";
					sessionFactory
							.getCurrentSession()
							.createQuery(sql)
							.setInteger("id", user_id)
							.setTimestamp(
									"modified",
									getTimeStamp(dataVrs_users.get("modified")
											.toString())).executeUpdate();

					try {
						JSONArray objData_meta = new JSONArray(obj.get(
								"vrs_user_metas").toString());
						JSONObject objData_meta1 = null;
						int i = 0;
						for (i = 0; i < objData_meta.length(); i++) {
							objData_meta1 = objData_meta.getJSONObject(i);
							addvrs_user_metas(user_id, objData_meta1.get("key")
									.toString(), objData_meta1.get("value")
									.toString());
						}
						sessionFactory.getCurrentSession().clear();
					} catch (Exception ex) {

					}
				}

			}

		} catch (Exception ex) {
			cf.viewAlert(" error in saveInvrs_UsersFromdownload "
					+ ex.getMessage());
		}

	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean saveDAtaFromDownload(String data) {
		boolean getBack = false;
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			String tableNAme = obj.get("tableName").toString();
			cf.viewAlert("saveDAtaFromDownload " + tableNAme);
			if (tableNAme.equalsIgnoreCase("vrs_file_library")) {
				saveInTable_vrs_file_libraryDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("vrs_users")) {
				saveInvrs_UsersFromdownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("vrs_roles")) {
				saveInTable_vrs_rolesDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("vrs_companies")) {
				saveInTable_vrs_companiesDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("vrs_logs")) {
				saveInTable_vrs_logsDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("vac_chart_accounts")) {
				sacc.saveInTable_vac_chart_accountsDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("vac_transactions")) {
				ssac.saveInTable_vac_transactionsDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("vac_transaction_details")) {
				ssac.saveInTable_vac_transaction_detailsDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("vac_postings")) {
				ssac.saveInTable_vac_postingsDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("vac_budgets")) {
				ssac.saveInTable_vac_budgetsDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("vac_chart_account_balances")) {
				sacc.saveInTable_vac_chart_account_balancesDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("ved_assignments")) {
				ssa.saveInTable_ved_assignmentsDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("ved_attendances")) {
				ssa.saveInTable_ved_attendancesDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("ved_candidates")) {
				ssa.saveInTable_ved_candidatesDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("ved_classes")) {
				ssa.saveInTable_ved_classesDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("ved_course_list")) {
				ssa.saveInTable_ved_course_listDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("ved_course_schedule")) {
				ssa.saveInTable_ved_course_scheduleDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("ved_courses")) {
				ssa.saveInTable_ved_coursesDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("ved_enrollments")) {
				ssa.saveInTable_ved_enrollmentsDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("ved_reports")) {
				ssa.saveInTable_ved_reportsDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("ved_scores")) {
				ssa.saveInTable_ved_scoresDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("ved_students")) {
				ssa.saveInTable_ved_studentsDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("vrs_access")) {
				saveInTable_vrs_accessDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("vrs_page_registry")) {
				saveInTable_vrs_page_registryDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("vrs_rule_policies")) {
				saveInTable_vrs_rule_policiesDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("vrs_service")) {
				saveInTable_vrs_serviceDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("vrs_user_guide")) {
				saveInTable_vrs_user_guideDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("vrs_addresses")) {
				sVRS.saveInTable_vrs_addressesDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("vrs_attendances")) {
				sVRS.saveInTable_vrs_attendances(data, "download", 0);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("vrs_subscriptions")) {
				sVRS.saveInTable_vrs_subscriptions(data, "download", 0);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("vrs_taxonomies")) {
				sVRS.saveInTable_vrs_taxonomies(data, "download", 0);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("students")) {
				sacc.saveStudents(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("transactions1")) {
				sacc.saveTransactionsWhenDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("vrs_server_registry")) {
				sVRS.saveInTable_vrs_server_registry(data, "download", 0);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("vrs_accounts")) {
				sVRS.saveInTable_vrs_accounts(data, "download");
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("ved_grade_report")) {
				sv1.saveInTable_ved_grade_report(data, "download", 0);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("ved_grade_report_detail")) {
				sv1.saveInTable_ved_grade_report_detail(data, "download", 0);
				getBack = true;
			}
		} catch (Exception ex) {
			cf.viewAlert(" error saveDAtaFromDownload " + ex.getMessage());
		}
		return getBack;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addComputerList(int comp_id, int id) {
		try {
			vrs_sync_computerlist vsc = new vrs_sync_computerlist();
			vsc.setId_computer(comp_id);
			vsc.setId_vrs_sync_items(id);
			Timestamp tstamp = new Timestamp(new Date().getTime());
			vsc.setTime_created(tstamp);
			sessionFactory.getCurrentSession().save(vsc);
		} catch (Exception ex) {
			cf.viewAlert("eror in addComputerList " + ex.getMessage());
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String getcompanyList(int computer_id) {
		String companyList = "", listCompany = "";

		Criteria criteria1 = sessionFactory.getCurrentSession().createCriteria(
				vrs_server_registry.class);
		criteria1.add(Restrictions.eq("id", computer_id));
		Iterator it = criteria1.list().iterator();
		try {
			if (it.hasNext()) {
				vrs_server_registry vsr = (vrs_server_registry) it.next();
				listCompany = vsr.getCompany_list();
				listCompany = listCompany.replace("{", "");
				listCompany = listCompany.replace("}", "");
			}
			companyList = listCompany;
		} catch (Exception ex) {
			cf.viewAlert(" error getcompanyList" + ex.getMessage());
		}
		return companyList;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String getCompanyChild(int company_id) {
		String companyList = "";
		try {
			if (company_id != 0) {
				companyList += "," + String.valueOf(company_id);
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("parent", company_id));
				criteria.addOrder(Order.asc("companyId"));
				Iterator it = criteria.list().iterator();
				while (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					companyList += getCompanyChild(vc.getCompanyId());
				}
			}
		} catch (Exception ex) {
		}
		return companyList;

	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int getLimitDownload(int user_id, int comp_id) {
		int getData = 0;
		try {
			String sql = "";
			Iterator it = null;
			if (user_id == 1) {
				sql = "select count(*) as countData from (select a.id,a.company_id,a.id_composite,a.table_name,a.action"
						+ // 4
						",a.status,a.time_created,a.id_table,"
						+ // 7
						"(select case when (select count(*) "
						+ "from vrs_sync_computerlist b where b.id_computer=:compID and b.id_vrs_sync_items=a.id)>0 "
						+ "then 1 else 0 end ) as exist  " + // 8
						"from vrs_sync_items a) as Data where exist=0;";
			} else if (user_id > 1) {
				String CompanyListForDownload = getcompanyList(comp_id);
				String conditionCompany = "";
				if (CompanyListForDownload.length() > 0) {
					conditionCompany = " company_id in ("
							+ CompanyListForDownload + ") or ";
				}
				sql = "select count(*) as countData from (select a.id,a.company_id,a.id_composite,a.table_name,a.action"
						+ // 4
						",a.status,a.time_created,a.id_table,"
						+ // 7
						"(select case when (select count(*) "
						+ "from vrs_sync_computerlist b where b.id_computer=:compID and b.id_vrs_sync_items=a.id)>0 "
						+ "then 1 else 0 end ) as exist  "
						+ // 8
						"from vrs_sync_items a) as Data where exist=0 "
						+ " and ( "
						+ conditionCompany
						+ " (table_name in ('ved_course_list','vrs_users',"
						+ "'vrs_page_registry','vrs_service','vrs_user_guide','vrs_server_registry')));";
			}
			cf.viewAlert("getLimitDownload sql = " + sql + " comp_id "
					+ comp_id);

			it = sessionFactory.getCurrentSession().createSQLQuery(sql)
					.setInteger("compID", comp_id).list().iterator();
			if (it.hasNext()) {
				getData = Integer.valueOf(it.next().toString());
			}
			cf.viewAlert("getLimitDownload  getData = " + getData);

		} catch (Exception ex) {
			cf.viewAlert("eror in addComputerList = " + ex.getMessage());
		}
		return getData;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int getid_compositeFromvrs_companies(int company_id) {
		int getBack = 0;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_companies.class);
		criteria.add(Restrictions.eq("companyId", company_id));
		Iterator it = criteria.list().iterator();
		if (it.hasNext()) {
			vrs_companies vc = (vrs_companies) it.next();
			getBack = vc.getId_composite();
		}
		return getBack;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int getUser_idComposite(int user_id) {
		int getBack = -1;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_users.class);
		criteria.add(Restrictions.eq("id", user_id));
		Iterator it = criteria.list().iterator();
		if (it.hasNext()) {
			vrs_users vu = (vrs_users) it.next();
			getBack = vu.getId_composite();
		}
		return getBack;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int getUser_idfromCompositeID(int user_idComposite) {
		int getBack = -1;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_users.class);
		criteria.add(Restrictions.eq("id_composite", user_idComposite));
		Iterator it = criteria.list().iterator();
		if (it.hasNext()) {
			vrs_users vu = (vrs_users) it.next();
			getBack = vu.getId();
		}
		return getBack;
	}
}