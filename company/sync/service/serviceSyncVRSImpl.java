package verse.sync.service;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import verse.commonClass.CommonFunction;
import verse.model.vrs_accounts;
import verse.model.vrs_addresses;
import verse.model.vrs_attendances;
import verse.model.vrs_companies;
import verse.model.vrs_roles;
import verse.model.vrs_subscriptions;
import verse.model.vrs_taxonomies;
import verse.model.vrs_users;
import verse.sync.model.vrs_server_registry;
import verse.sync.model.vrs_sync_computerlist;
import verse.sync.model.vrs_sync_items;
import verse.sync.model.vrs_sync_log;

@Service("serviceSyncVRS")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class serviceSyncVRSImpl implements serviceSyncVRS {

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private serviceSync svs;

	CommonFunction cf = new CommonFunction();

	public Time getTimeOnly(String text) {
		Time tstamp = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:s");
			tstamp = new Time(sdf.parse(text).getTime());
		} catch (Exception ex) {
		}
		return tstamp;
	}

	public String getvrs_addresses_DAta(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_addresses.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			vrs_addresses vad = (vrs_addresses) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vrs_addresses");
				obj.put("id_vrs_sync_itemsLOCAL", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vad.getCompanyId()));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vrc = (vrs_companies) it.next();
					objColumn.put("company_idComposite", vrc.getId_composite());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vad.getRoleId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("role_id_composite", vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("rolecompany_id_composite",
								vc.getId_composite());
					}
				}

				objColumn.put("name", vad.getName());
				objColumn.put("street1", vad.getStreet1());
				objColumn.put("street2", vad.getStreet2());
				objColumn.put("district", vad.getDistrict());
				objColumn.put("city", vad.getCity());
				objColumn.put("province", vad.getProvince());
				objColumn.put("postalCode", vad.getPostalCode());
				objColumn.put("country", vad.getCountry());
				objColumn.put("latitude", vad.getLatitude());
				objColumn.put("longitude", vad.getLongitude());
				if (action.equalsIgnoreCase("edit"))
					objColumn.put("id_composite", vad.getId_composite());

				obj.append("vrs_addresses", objColumn);

				getBack = obj.toString();
			} catch (Exception ex) {
					cf.viewAlert("error in getvrs_addresses_DAta "
							+ ex.getMessage());
			}
		}

		return getBack;
	}

	public String getvrs_addresses_DAtaDownload(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_addresses.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			vrs_addresses vad = (vrs_addresses) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vrs_addresses");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vad.getCompanyId()));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vrc = (vrs_companies) it.next();
					objColumn.put("company_idComposite", vrc.getId_composite());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vad.getRoleId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("role_id_composite", vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("rolecompany_id_composite",
								vc.getId_composite());
					}
				}

				objColumn.put("name", vad.getName());
				objColumn.put("street1", vad.getStreet1());
				objColumn.put("street2", vad.getStreet2());
				objColumn.put("district", vad.getDistrict());
				objColumn.put("city", vad.getCity());
				objColumn.put("province", vad.getProvince());
				objColumn.put("postalCode", vad.getPostalCode());
				objColumn.put("country", vad.getCountry());
				objColumn.put("latitude", vad.getLatitude());
				objColumn.put("longitude", vad.getLongitude());
				objColumn.put("id_composite", vad.getId_composite());

				obj.append("vrs_addresses", objColumn);

				getBack = obj.toString();
			} catch (Exception ex) {
				cf.viewAlert(" error in getvrs_addresses_DAta "
							+ ex.getMessage());
			}
		}

		return getBack;
	}

	public String saveInTable_vrs_addresses(String data, int computer_id) {
		String getBack = "", sql = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL",
					obj.get("id_vrs_sync_itemsLOCAL").toString());
			String action = obj.get("action").toString();

			JSONArray objData = new JSONArray(obj.get("vrs_addresses")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);

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
			}

			int roleID = 0;
			criteria = sessionFactory.getCurrentSession().createCriteria(
					vrs_companies.class);
			criteria.add(Restrictions.eq(
					"id_composite",
					Integer.valueOf(dataVrs_users.get(
							"rolecompany_id_composite").toString())));
			it = criteria.list().iterator();
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

			if (action.equalsIgnoreCase("add")) {
				vrs_addresses vad = new vrs_addresses();
				vad.setCompanyId(companyID);
				vad.setRoleId(roleID);
				vad.setName(dataVrs_users.get("name").toString());
				vad.setStreet1(dataVrs_users.get("street1").toString());
				vad.setStreet2(dataVrs_users.get("street2").toString());
				vad.setDistrict(dataVrs_users.get("district").toString());
				vad.setCity(dataVrs_users.get("city").toString());
				vad.setProvince(dataVrs_users.get("province").toString());
				vad.setPostalCode(dataVrs_users.get("postalCode").toString());
				vad.setCountry(dataVrs_users.get("country").toString());
				vad.setLatitude(Float.valueOf(dataVrs_users.get("latitude")
						.toString()));
				vad.setLongitude(Float.valueOf(dataVrs_users.get("longitude")
						.toString()));
				sessionFactory.getCurrentSession().save(vad);
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
						vrs_addresses.class);
				criteria.add(Restrictions.eq("id", vad.getId()));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_addresses vstu = (vrs_addresses) it.next();
					objView.put("id_CompositeFromServer",
							vstu.getId_composite());
					getBack = objView.toString();
				}
			} else if (action.equalsIgnoreCase("edit")) {
				int idTable = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_addresses.class);
				criteria.add(Restrictions.eq("companyId", companyID));
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("id_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_addresses vbu = (vrs_addresses) it.next();
					idTable = vbu.getId();
				}

				sql = "update vrs_addresses set "
						+ " companyId=:companyId ,roleId=:roleId ,name=:name"
						+ " ,street1=:street1 ,street2=:street2 ,district=:district"
						+ " ,city=:city ,province=:province ,postalCode=:postalCode"
						+ " ,country=:country ,latitude=:latitude ,longitude=:longitude"
						+ "   where id=:id";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)
						.setInteger("id", idTable)
						.setInteger("companyId", companyID)
						.setInteger("roleId", roleID)
						.setString("name", dataVrs_users.get("name").toString())
						.setString("street1",
								dataVrs_users.get("street1").toString())
						.setString("street2",
								dataVrs_users.get("street2").toString())
						.setString("district",
								dataVrs_users.get("district").toString())
						.setString("city", dataVrs_users.get("city").toString())
						.setString("province",
								dataVrs_users.get("province").toString())
						.setString("postalCode",
								dataVrs_users.get("postalCode").toString())
						.setString("country",
								dataVrs_users.get("country").toString())
						.setFloat(
								"latitude",
								Float.valueOf(dataVrs_users.get("latitude")
										.toString()))
						.setFloat(
								"longitude",
								Float.valueOf(dataVrs_users.get("longitude")
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
			}
			getBack = objView.toString();
		} catch (Exception ex) {
			cf.viewAlert(" error in saveInTable_vrs_addresses "
						+ ex.getMessage());
		}
		return getBack;
	}

	public String saveInTable_vrs_addressesDownload(String data) {
		String getBack = "", sql = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("vrs_addresses")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);

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
			}

			int roleID = 0;
			criteria = sessionFactory.getCurrentSession().createCriteria(
					vrs_companies.class);
			criteria.add(Restrictions.eq(
					"id_composite",
					Integer.valueOf(dataVrs_users.get(
							"rolecompany_id_composite").toString())));
			it = criteria.list().iterator();
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

			if (action.equalsIgnoreCase("add")) {
				vrs_addresses vad = new vrs_addresses();
				vad.setCompanyId(companyID);
				vad.setRoleId(roleID);
				vad.setName(dataVrs_users.get("name").toString());
				vad.setStreet1(dataVrs_users.get("street1").toString());
				vad.setStreet2(dataVrs_users.get("street2").toString());
				vad.setDistrict(dataVrs_users.get("district").toString());
				vad.setCity(dataVrs_users.get("city").toString());
				vad.setProvince(dataVrs_users.get("province").toString());
				vad.setPostalCode(dataVrs_users.get("postalCode").toString());
				vad.setCountry(dataVrs_users.get("country").toString());
				vad.setLatitude(Float.valueOf(dataVrs_users.get("latitude")
						.toString()));
				vad.setLongitude(Float.valueOf(dataVrs_users.get("longitude")
						.toString()));

				vad.setId_composite((Integer.valueOf(dataVrs_users.get(
						"id_composite").toString())));

				sql = "ALTER TABLE vrs_addresses DISABLE TRIGGER insert_vrs_addressest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sessionFactory.getCurrentSession().save(vad);

				sql = "ALTER TABLE vrs_addresses ENABLE TRIGGER insert_vrs_addressest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

			} else if (action.equalsIgnoreCase("edit")) {
				int idTable = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_addresses.class);
				criteria.add(Restrictions.eq("companyId", companyID));
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("id_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_addresses vbu = (vrs_addresses) it.next();
					idTable = vbu.getId();
				}

				sql = "ALTER TABLE vrs_addresses DISABLE TRIGGER insert_vrs_addressest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sql = "update vrs_addresses set "
						+ " companyId=:companyId ,roleId=:roleId ,name=:name"
						+ " ,street1=:street1 ,street2=:street2 ,district=:district"
						+ " ,city=:city ,province=:province ,postalCode=:postalCode"
						+ " ,country=:country ,latitude=:latitude ,longitude=:longitude"
						+ "   where id=:id";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)
						.setInteger("id", idTable)
						.setInteger("companyId", companyID)
						.setInteger("roleId", roleID)
						.setString("name", dataVrs_users.get("name").toString())
						.setString("street1",
								dataVrs_users.get("street1").toString())
						.setString("street2",
								dataVrs_users.get("street2").toString())
						.setString("district",
								dataVrs_users.get("district").toString())
						.setString("city", dataVrs_users.get("city").toString())
						.setString("province",
								dataVrs_users.get("province").toString())
						.setString("postalCode",
								dataVrs_users.get("postalCode").toString())
						.setString("country",
								dataVrs_users.get("country").toString())
						.setFloat(
								"latitude",
								Float.valueOf(dataVrs_users.get("latitude")
										.toString()))
						.setFloat(
								"longitude",
								Float.valueOf(dataVrs_users.get("longitude")
										.toString())).executeUpdate();

				sql = "ALTER TABLE vrs_addresses ENABLE TRIGGER insert_vrs_addressest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

			}
			getBack = objView.toString();
		} catch (Exception ex) {
			cf.viewAlert(" error in saveInTable_vrs_addresses "
						+ ex.getMessage());
		}
		return getBack;
	}

	public String getvrs_attendances_DAta(long id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_attendances.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			vrs_attendances vad = (vrs_attendances) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vrs_attendances");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vad.getCompanyId()));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vrc = (vrs_companies) it.next();
					objColumn.put("company_idComposite", vrc.getId_composite());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vad.getRoleId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("role_id_composite", vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("rolecompany_id_composite",
								vc.getId_composite());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vad.getCreatedBy()));
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

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vad.getModifiedBy()));
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

				objColumn.put("referenceId", vad.getReferenceId());
				objColumn.put("day", vad.getDay());
				objColumn.put("presence", vad.getPresence());
				objColumn.put("firstIn", vad.getFirstIn());
				objColumn.put("lastOut", vad.getLastOut());
				objColumn.put("stayDuration", vad.getStayDuration());
				objColumn.put("breakTime", vad.getBreakTime());
				objColumn.put("lateArrival", vad.getLateArrival());
				objColumn.put("leftEarly", vad.getLeftEarly());
				objColumn.put("overTime", vad.getOverTime());
				objColumn.put("timeOff", vad.getTimeOff());
				objColumn.put("penalty", vad.getPenalty());
				objColumn.put("created", vad.getCreated());
				objColumn.put("modified", vad.getModified());
				objColumn.put("notes", vad.getNotes());
				objColumn.put("id_composite", vad.getId_composite());

				obj.append("vrs_attendances", objColumn);
				getBack = obj.toString();

			} catch (Exception ex) {
				cf.viewAlert(" error in getvrs_attendances_DAta "
							+ ex.getMessage());
			}
		}
		return getBack;
	}

	public JSONObject setId_vrs_sync_itemsLastID(JSONObject objView,
			int computer_id) {
		String sql = "SELECT last_value from vrs_sync_items_id_seq";
		Iterator it = sessionFactory.getCurrentSession().createSQLQuery(sql)
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
			try {
				objView.put("id_vrs_sync_items", id_vrs_sync_items);
			} catch (Exception ex) {
				cf.viewAlert(" error in setId_vrs_sync_itemsLastID "
							+ ex.getMessage());
			}
		}
		return objView;
	}

	public String saveInTable_vrs_attendances(String data, String forWhat,
			int computer_id) {
		String getBack = "", sql = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL", obj.get("id_vrs_sync_items")
					.toString());

			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("vrs_attendances")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);

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
			}

			int roleID = 0;
			criteria = sessionFactory.getCurrentSession().createCriteria(
					vrs_companies.class);
			criteria.add(Restrictions.eq(
					"id_composite",
					Integer.valueOf(dataVrs_users.get(
							"rolecompany_id_composite").toString())));
			it = criteria.list().iterator();
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
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"CreatedBy_id_composite").toString())));
				criteria.add(Restrictions.eq("companyId", vcom.getCompanyId()));
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
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"ModifiedBy_id_composite").toString())));
				criteria.add(Restrictions.eq("companyId", vcom.getCompanyId()));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_roles vr = (vrs_roles) it.next();
					modifiedBY = vr.getRoleId();
				}
			}
			if (action.equalsIgnoreCase("add")) {
				vrs_attendances vad = new vrs_attendances();
				vad.setCompanyId(companyID);
				vad.setRoleId(roleID);
				vad.setReferenceId(Long.valueOf(dataVrs_users
						.get("referenceId").toString()));
				vad.setDay(svs.getDate(dataVrs_users.get("day").toString()));
				vad.setPresence(Integer.valueOf(dataVrs_users.get("presence")
						.toString()));
				vad.setFirstIn(getTimeOnly(dataVrs_users.get("firstIn")
						.toString()));
				vad.setLastOut(getTimeOnly(dataVrs_users.get("lastOut")
						.toString()));
				vad.setStayDuration(getTimeOnly(dataVrs_users.get(
						"stayDuration").toString()));
				vad.setBreakTime(getTimeOnly(dataVrs_users.get("breakTime")
						.toString()));
				vad.setLateArrival(getTimeOnly(dataVrs_users.get("lateArrival")
						.toString()));
				vad.setLeftEarly(getTimeOnly(dataVrs_users.get("leftEarly")
						.toString()));
				vad.setOverTime(getTimeOnly(dataVrs_users.get("overTime")
						.toString()));
				vad.setTimeOff(getTimeOnly(dataVrs_users.get("timeOff")
						.toString()));
				vad.setPenalty(getTimeOnly(dataVrs_users.get("penalty")
						.toString()));
				vad.setCreated(svs.getTimeStamp(dataVrs_users.get("created")
						.toString()));
				vad.setCreatedBy(createdBY);
				vad.setModified(svs.getTimeStamp(dataVrs_users.get("modified")
						.toString()));
				vad.setModifiedBy(modifiedBY);
				vad.setNotes(dataVrs_users.get("notes").toString());

				if (forWhat.equalsIgnoreCase("download")) {
					sql = "ALTER TABLE vrs_attendances DISABLE TRIGGER insert_vrs_attendancest";
					sessionFactory.getCurrentSession().createSQLQuery(sql)
							.executeUpdate();
					vad.setId_composite(Integer.valueOf(dataVrs_users.get(
							"id_composite").toString()));
				}
				sessionFactory.getCurrentSession().save(vad);
				sessionFactory.getCurrentSession().clear();
				if (forWhat.equalsIgnoreCase("download")) {
					sql = "ALTER TABLE vrs_attendances ENABLE TRIGGER insert_vrs_attendancest";
					sessionFactory.getCurrentSession().createSQLQuery(sql)
							.executeUpdate();
				}

				if (forWhat.equalsIgnoreCase("upload")) {
					objView = setId_vrs_sync_itemsLastID(objView, computer_id);

					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_attendances.class);
					criteria.add(Restrictions.eq("id", vad.getId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_attendances vstu = (vrs_attendances) it.next();
						objView.put("id_CompositeFromServer",
								vstu.getId_composite());
						getBack = objView.toString();
					}
				}
			} else if (action.equalsIgnoreCase("edit")) {
				long idTable = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_attendances.class);
				criteria.add(Restrictions.eq("companyId", companyID));
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("id_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_attendances vbu = (vrs_attendances) it.next();
					idTable = vbu.getId();
				}

				if (forWhat.equalsIgnoreCase("download")) {
					sql = "ALTER TABLE vrs_attendances DISABLE TRIGGER insert_vrs_attendancest";
					sessionFactory.getCurrentSession().createSQLQuery(sql)
							.executeUpdate();
				}

				sql = "update vrs_attendances set companyId=:companyId ,roleId=:roleId"
						+ " ,referenceId=:referenceId "
						+ " ,day=:day ,presence=:presence ,firstIn=:firstIn ,lastOut=:lastOut"
						+ " ,stayDuration=:stayDuration ,breakTime=:breakTime "
						+ " ,lateArrival=:lateArrival ,leftEarly=:leftEarly ,overTime=:overTime "
						+ " ,timeOff=:timeOff ,penalty=:penalty ,createdBy=:createdBy"
						+ " ,created=:created ,modified=:modified ,modifiedBy=:modifiedBy ,notes=:notes"
						+ " where id=:id";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)
						.setLong("id", idTable)
						.setInteger("companyId", companyID)
						.setInteger("roleId", roleID)
						.setLong(
								"referenceId",
								Long.valueOf(dataVrs_users.get("referenceId")
										.toString()))
						.setDate(
								"day",
								svs.getDate(dataVrs_users.get("day").toString()))
						.setInteger(
								"presence",
								Integer.valueOf(dataVrs_users.get("presence")
										.toString()))
						.setTime(
								"firstIn",
								getTimeOnly(dataVrs_users.get("firstIn")
										.toString()))
						.setTime(
								"lastOut",
								getTimeOnly(dataVrs_users.get("lastOut")
										.toString()))
						.setTime(
								"stayDuration",
								getTimeOnly(dataVrs_users.get("stayDuration")
										.toString()))

						.setTime(
								"breakTime",
								getTimeOnly(dataVrs_users.get("breakTime")
										.toString()))

						.setTime(
								"lateArrival",
								getTimeOnly(dataVrs_users.get("lateArrival")
										.toString()))

						.setTime(
								"leftEarly",
								getTimeOnly(dataVrs_users.get("leftEarly")
										.toString()))

						.setTime(
								"overTime",
								getTimeOnly(dataVrs_users.get("overTime")
										.toString()))

						.setTime(
								"timeOff",
								getTimeOnly(dataVrs_users.get("timeOff")
										.toString()))

						.setTime(
								"penalty",
								getTimeOnly(dataVrs_users.get("penalty")
										.toString()))
						.setTimestamp(
								"created",
								svs.getTimeStamp(dataVrs_users.get("created")
										.toString()))
						.setInteger("createdBy", createdBY)
						.setTimestamp(
								"modified",
								svs.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modifiedBy", modifiedBY)
						.setString("notes",
								dataVrs_users.get("notes").toString())

						.executeUpdate();
				if (forWhat.equalsIgnoreCase("download")) {
					sql = "ALTER TABLE vrs_attendances ENABLE TRIGGER insert_vrs_attendancest";
					sessionFactory.getCurrentSession().createSQLQuery(sql)
							.executeUpdate();
				}
				if (forWhat.equalsIgnoreCase("upload")) {
					objView = setId_vrs_sync_itemsLastID(objView, computer_id);
				}
				getBack = objView.toString();
			}

		} catch (Exception ex) {
			cf.viewAlert(" error in saveInTable_vrs_attendances "
						+ ex.getMessage());
		}
		return getBack;
	}

	public String getvrs_accounts(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_accounts.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		if (it.hasNext()) {
			vrs_accounts vac = (vrs_accounts) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vrs_accounts");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();
				objColumn.put("id_composite", vac.getId_composite());
				objColumn.put("status", vac.getStatus());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_users.class);
				criteria.add(Restrictions.eq("id", vac.getUser_id()));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_users vus = (vrs_users) it.next();
					objColumn.put("id_composite_users", vus.getId_composite());
				}
				objColumn.put("created", vac.getCreated());
				objColumn.put("email", vac.getEmail());
				objColumn.put("modified", vac.getModified());
				objColumn.put("password", vac.getPassword());
				objColumn.put("shortcode", vac.getShortcode());
				obj.append("vrs_accounts", objColumn);
				getBack = obj.toString();
			} catch (Exception ex) {

			}
		} else {
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", "del");
				obj.put("tableName", "vrs_accounts");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_sync_items.class);
				criteria.add(Restrictions.eq("id", id_vrs_sync_items));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_sync_items vsi = (vrs_sync_items) it.next();
					JSONObject objColumn = new JSONObject();
					objColumn.put("id_composite", vsi.getId_composite());
					obj.append("vrs_accounts", objColumn);
					getBack = obj.toString();
				}
				getBack = obj.toString();
			} catch (Exception ex) {

			}
		}

		return getBack;
	}

	public String getvrs_server_registry(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_server_registry.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			vrs_server_registry vsr = (vrs_server_registry) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vrs_server_registry");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				objColumn.put("computer_name222", vsr.getComputer_name());
				objColumn.put("userserver333", vsr.getUserserver());
				objColumn.put("password444", vsr.getPassword());

				String companyList = vsr.getCompany_list();
				companyList = companyList.replace("{", "");
				companyList = companyList.replace("}", "");
				StringTokenizer stk = new StringTokenizer(companyList, ",");
				String companyIDComposite = "";
				while (stk.hasMoreTokens()) {
					int companyId1DAta = Integer.valueOf(stk.nextToken()
							.toString());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", companyId1DAta));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vco = (vrs_companies) itMeta.next();
						companyIDComposite += "," + vco.getId_composite();
					}
				}
				if (companyIDComposite.length() > 0) {
					companyIDComposite = companyIDComposite.substring(1);
				}
				objColumn.put("companyListComposite", companyIDComposite);

				obj.append("vrs_server_registry", objColumn);
				getBack = obj.toString();

			} catch (Exception ex) {
				cf.viewAlert(" error in getvrs_server_registry "
							+ ex.getMessage());
			}
		}
		return getBack;
	}

	public String saveInTable_vrs_server_registry(String data, String forWhat,
			int computer_id) {

		String getBack = "", sql = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL", obj.get("id_vrs_sync_items")
					.toString());

			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("vrs_server_registry")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			String companyIDComposite = dataVrs_users.get(
					"companyListComposite").toString();
			StringTokenizer stk = new StringTokenizer(companyIDComposite, ",");
			String companyID = "";
			while (stk.hasMoreTokens()) {
				int companyId1DAta = Integer
						.valueOf(stk.nextToken().toString());
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", companyId1DAta));
				Iterator itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vco = (vrs_companies) itMeta.next();
					companyID += "," + vco.getCompanyId();
				}
			}

			if (companyID.length() > 0) {
				companyID = companyID.substring(1);
				companyID = "'{" + companyID + "}'";
			}

			if (action.equalsIgnoreCase("edit")) {

				if (forWhat.equalsIgnoreCase("download")) {
					sql = "ALTER TABLE vrs_server_registry DISABLE TRIGGER insert_vrs_server_registryt";
					sessionFactory.getCurrentSession().createSQLQuery(sql)
							.executeUpdate();
				}

				sql = "update vrs_server_registry set "
						+ " company_list= "
						+ companyID
						+ " where computer_name=:computer_name and userserver=:userserver and password=:password";
				sessionFactory
						.getCurrentSession()
						.createSQLQuery(sql)
						.setString(
								"computer_name",
								dataVrs_users.get("computer_name222")
										.toString())
						.setString("userserver",
								dataVrs_users.get("userserver333").toString())
						.setString("password",
								dataVrs_users.get("password444").toString())
						.executeUpdate();

				if (forWhat.equalsIgnoreCase("upload")) {
					objView = setId_vrs_sync_itemsLastID(objView, computer_id);
				}
				getBack = objView.toString();

			}
		} catch (Exception ex) {
			cf.viewAlert(" error in saveInTable_vrs_server_registry "
						+ ex.getMessage());
		}
		return getBack;
	}

	public String getvrs_subscriptions(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_subscriptions.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			vrs_subscriptions vsub = (vrs_subscriptions) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vrs_subscriptions");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);

				JSONObject objColumn = new JSONObject();
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vsub.getCompany_id()));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vrc = (vrs_companies) it.next();
					objColumn.put("company_idComposite", vrc.getId_composite());
				}
				objColumn.put("service", vsub.getService());
				objColumn.put("packages_subscription",
						vsub.getPackages_subscription());
				objColumn.put("status", vsub.getStatus());
				objColumn.put("amount", vsub.getAmount());
				objColumn.put("enddate", vsub.getEnddate());
				objColumn.put("created", vsub.getCreated());
				objColumn.put("id_composite", vsub.getId_composite());

				obj.append("vrs_subscriptions", objColumn);
				getBack = obj.toString();

			} catch (Exception ex) {
				cf.viewAlert(" error in getvrs_subscriptions "
							+ ex.getMessage());
			}
		}
		return getBack;
	}

	public String saveInTable_vrs_subscriptions(String data, String forWhat,
			int computer_id) {
		String getBack = "", sql = "";
		System.out.println(" saveInTable_vrs_subscriptions 1");
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL", obj.get("id_vrs_sync_items")
					.toString());

			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("vrs_subscriptions")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			int companyID = -1;
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(vrs_companies.class);
			criteria.add(Restrictions.eq("id_composite", Integer
					.valueOf(dataVrs_users.get("company_idComposite")
							.toString())));
			System.out.println(" company_idComposite =  "
					+ dataVrs_users.get("company_idComposite"));
			Iterator it = criteria.list().iterator();
			if (it.hasNext()) {
				vrs_companies vc = (vrs_companies) it.next();
				companyID = vc.getCompanyId();
			}
			if (action.equalsIgnoreCase("add")) {
				vrs_subscriptions vsub = new vrs_subscriptions();
				vsub.setCompany_id(companyID);
				vsub.setService(dataVrs_users.get("service").toString());
				vsub.setPackages_subscription(Integer.valueOf(dataVrs_users
						.get("packages_subscription").toString()));
				vsub.setStatus(Integer.valueOf(dataVrs_users.get("status")
						.toString()));
				vsub.setAmount(Integer.valueOf(dataVrs_users.get("amount")
						.toString()));
				vsub.setEnddate(svs.getDate(dataVrs_users.get("enddate")
						.toString()));
				vsub.setCreated(svs.getTimeStamp(dataVrs_users.get("created")
						.toString()));
				if (forWhat.equalsIgnoreCase("download")) {
					sql = "ALTER TABLE vrs_subscriptions DISABLE TRIGGER insert_vrs_subscriptionst";
					sessionFactory.getCurrentSession().createSQLQuery(sql)
							.executeUpdate();
					vsub.setId_composite(Integer.valueOf(dataVrs_users.get(
							"id_composite").toString()));
				}

				sessionFactory.getCurrentSession().save(vsub);
				sessionFactory.getCurrentSession().clear();

				if (forWhat.equalsIgnoreCase("download")) {
					sql = "ALTER TABLE vrs_subscriptions ENABLE TRIGGER insert_vrs_subscriptionst";
					sessionFactory.getCurrentSession().createSQLQuery(sql)
							.executeUpdate();
				}

				if (forWhat.equalsIgnoreCase("upload")) {
					objView = setId_vrs_sync_itemsLastID(objView, computer_id);

					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_subscriptions.class);
					criteria.add(Restrictions.eq("id", vsub.getId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_subscriptions vstu = (vrs_subscriptions) it.next();
						objView.put("id_CompositeFromServer",
								vstu.getId_composite());
						getBack = objView.toString();
					}
				}
			} else if (action.equalsIgnoreCase("edit")) {
				int idTable = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_subscriptions.class);
				criteria.add(Restrictions.eq("company_id", companyID));
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("id_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_subscriptions vbu = (vrs_subscriptions) it.next();
					idTable = vbu.getId();
				}

				if (forWhat.equalsIgnoreCase("download")) {
					sql = "ALTER TABLE vrs_subscriptions DISABLE TRIGGER insert_vrs_subscriptionst";
					sessionFactory.getCurrentSession().createSQLQuery(sql)
							.executeUpdate();
				}

				sql = "update vrs_subscriptions set "
						+ " company_id=:company_id ,service=:service ,packages_subscription=:packages_subscription"
						+ " ,status=:status ,amount=:amount ,enddate=:enddate ,created=:created "
						+ "  where id=:id";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)
						.setInteger("id", idTable)
						.setInteger("company_id", companyID)
						.setString("service",
								dataVrs_users.get("service").toString())
						.setInteger(
								"packages_subscription",
								Integer.valueOf(dataVrs_users.get(
										"packages_subscription").toString()))
						.setInteger(
								"status",
								Integer.valueOf(dataVrs_users.get("status")
										.toString()))
						.setInteger(
								"amount",
								Integer.valueOf(dataVrs_users.get("amount")
										.toString()))
						.setDate(
								"enddate",
								svs.getDate(dataVrs_users.get("enddate")
										.toString()))
						.setTimestamp(
								"created",
								svs.getTimeStamp(dataVrs_users.get("created")
										.toString()))

						.executeUpdate();
				if (forWhat.equalsIgnoreCase("download")) {
					sql = "ALTER TABLE vrs_subscriptions ENABLE TRIGGER insert_vrs_subscriptionst";
					sessionFactory.getCurrentSession().createSQLQuery(sql)
							.executeUpdate();
				}
				if (forWhat.equalsIgnoreCase("upload")) {
					objView = setId_vrs_sync_itemsLastID(objView, computer_id);
				}
				getBack = objView.toString();
			}

		} catch (Exception ex) {
			cf.viewAlert(" error in saveInTable_vrs_subscriptions "
						+ ex.getMessage());
		}
		return getBack;
	}

	public String getvrs_taxonomies(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_taxonomies.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			vrs_taxonomies vtax = (vrs_taxonomies) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vrs_taxonomies");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);

				JSONObject objColumn = new JSONObject();
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vtax.getCompanyId()));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vrc = (vrs_companies) it.next();
					objColumn.put("company_idComposite", vrc.getId_composite());
				}

				objColumn.put("type", vtax.getType());
				objColumn.put("title", vtax.getTitle());
				objColumn.put("description", vtax.getDescription());
				objColumn.put("slug", vtax.getSlug());
				objColumn.put("count", vtax.getCount_taxonomies());
				objColumn.put("created", vtax.getCreated());
				objColumn.put("modified", vtax.getModified());
				objColumn.put("status", vtax.getStatus());
				objColumn.put("parent", vtax.getParent());
				objColumn.put("id_composite", vtax.getId_composite());

				obj.append("vrs_taxonomies", objColumn);
				getBack = obj.toString();

			} catch (Exception ex) {
				cf.viewAlert(" error in getvrs_taxonomies "
							+ ex.getMessage());
			}
		}
		return getBack;
	}

	public String saveInTable_vrs_taxonomies(String data, String forWhat,
			int computer_id) {
		String getBack = "", sql = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL", obj.get("id_vrs_sync_items")
					.toString());

			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("vrs_taxonomies")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
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
			}
			if (action.equalsIgnoreCase("add")) {
				vrs_taxonomies vtax = new vrs_taxonomies();
				vtax.setCompanyId(companyID);
				vtax.setType(dataVrs_users.get("type").toString());
				vtax.setTitle(dataVrs_users.get("title").toString());
				vtax.setDescription(dataVrs_users.get("description").toString());
				vtax.setSlug(dataVrs_users.get("slug").toString());
				vtax.setCount_taxonomies(Integer.valueOf(dataVrs_users.get(
						"count").toString()));
				vtax.setCreated(svs.getTimeStamp(dataVrs_users.get("created")
						.toString()));
				vtax.setModified(svs.getTimeStamp(dataVrs_users.get("modified")
						.toString()));
				vtax.setStatus(Integer.valueOf(dataVrs_users.get("status")
						.toString()));
				vtax.setParent(Integer.valueOf(dataVrs_users.get("parent")
						.toString()));
				if (forWhat.equalsIgnoreCase("download")) {
					sql = "ALTER TABLE vrs_taxonomies DISABLE TRIGGER insert_vrs_taxonomiest";
					sessionFactory.getCurrentSession().createSQLQuery(sql)
							.executeUpdate();
					vtax.setId_composite(Integer.valueOf(dataVrs_users.get(
							"id_composite").toString()));
				}

				sessionFactory.getCurrentSession().save(vtax);
				sessionFactory.getCurrentSession().clear();

				if (forWhat.equalsIgnoreCase("download")) {
					sql = "ALTER TABLE vrs_taxonomies ENABLE TRIGGER insert_vrs_taxonomiest";
					sessionFactory.getCurrentSession().createSQLQuery(sql)
							.executeUpdate();
				}

				if (forWhat.equalsIgnoreCase("upload")) {
					objView = setId_vrs_sync_itemsLastID(objView, computer_id);

					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_taxonomies.class);
					criteria.add(Restrictions.eq("id", vtax.getId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_taxonomies vstu = (vrs_taxonomies) it.next();
						objView.put("id_CompositeFromServer",
								vstu.getId_composite());
						getBack = objView.toString();
					}
				}
			} else if (action.equalsIgnoreCase("edit")) {
				int idTable = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_taxonomies.class);
				criteria.add(Restrictions.eq("companyId", companyID));
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("id_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_taxonomies vbu = (vrs_taxonomies) it.next();
					idTable = vbu.getId();
				}

				if (forWhat.equalsIgnoreCase("download")) {
					sql = "ALTER TABLE vrs_taxonomies DISABLE TRIGGER insert_vrs_taxonomiest";
					sessionFactory.getCurrentSession().createSQLQuery(sql)
							.executeUpdate();
				}

				sql = "update vrs_taxonomies set companyId=:companyId ,type=:type   ,title=:title "
						+ " ,description=:description ,slug=:slug ,count_taxonomies=:count_taxonomies "
						+ "  ,created=:created "
						+ " ,modified=:modified ,status=:status ,parent=:parent  "
						+ " where id=:id";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)
						.setInteger("id", idTable)
						.setInteger("companyId", companyID)
						.setString("type", dataVrs_users.get("type").toString())
						.setString("title",
								dataVrs_users.get("title").toString())
						.setString("description",
								dataVrs_users.get("description").toString())
						.setString("slug", dataVrs_users.get("slug").toString())
						.setInteger(
								"count_taxonomies",
								Integer.valueOf(dataVrs_users.get("count")
										.toString()))
						.setTimestamp(
								"created",
								svs.getTimeStamp(dataVrs_users.get("created")
										.toString()))
						.setTimestamp(
								"modified",
								svs.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger(
								"status",
								Integer.valueOf(dataVrs_users.get("status")
										.toString()))
						.setInteger(
								"parent",
								Integer.valueOf(dataVrs_users.get("parent")
										.toString()))

						.executeUpdate();
				if (forWhat.equalsIgnoreCase("download")) {
					sql = "ALTER TABLE vrs_taxonomies ENABLE TRIGGER insert_vrs_taxonomiest";
					sessionFactory.getCurrentSession().createSQLQuery(sql)
							.executeUpdate();
				}
			}
		} catch (Exception ex) {
			cf.viewAlert(" error in saveInTable_vrs_taxonomies "
						+ ex.getMessage());
		}
		return getBack;
	}

	public void setDAteSyncronize(int computerID) {
		CommonFunction cf = new CommonFunction();
		try {

			String sql = "ALTER TABLE vrs_server_registry DISABLE TRIGGER insert_vrs_server_registryt";
			sessionFactory.getCurrentSession().createSQLQuery(sql)
					.executeUpdate();
			sql = "update vrs_server_registry set last_syncronize=now() where id=:id";
			sessionFactory.getCurrentSession().createQuery(sql)
					.setInteger("id", computerID).executeUpdate();

			sql = "ALTER TABLE vrs_server_registry ENABLE TRIGGER insert_vrs_server_registryt";
			sessionFactory.getCurrentSession().createSQLQuery(sql)
					.executeUpdate();

		} catch (Exception ex) {
			cf.viewAlert(" error in setDAteSyncronize "
						+ ex.getMessage());
		}
	}

	public void setDAtaSync(boolean start, int computerID) {
		String sql = "";
		sql = "ALTER TABLE vrs_server_registry DISABLE TRIGGER insert_vrs_server_registryt";
		sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
		if (start) {
			sql = "update vrs_server_registry set in_process=true where id=:id";
			sessionFactory.getCurrentSession().createQuery(sql)
					.setInteger("id", computerID).executeUpdate();
		} else {
			sql = "update vrs_server_registry set in_process=false where id=:id";
			sessionFactory.getCurrentSession().createQuery(sql)
					.setInteger("id", computerID).executeUpdate();
		}
		sql = "ALTER TABLE vrs_server_registry ENABLE TRIGGER insert_vrs_server_registryt";
		sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();

	}

	public boolean isCAnSync(int computerID) {
		boolean isCansync = false;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_server_registry.class);
		criteria.add(Restrictions.eq("id", computerID));
		criteria.add(Restrictions.eq("is_syncronize", true));
		criteria.add(Restrictions.eq("in_process", false));
		if (criteria.list().size() > 0) {
			isCansync = true;
		}
		return isCansync;
	}

	public boolean isCAnSyncIdentity(String namei, String usr, String pass) {
		boolean isCansync = false;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_server_registry.class);
		criteria.add(Restrictions.eq("computer_name", namei));
		criteria.add(Restrictions.eq("userserver", usr));
		criteria.add(Restrictions.eq("password", pass));
		criteria.add(Restrictions.eq("is_syncronize", true));
		criteria.add(Restrictions.eq("in_process", false));
		if (criteria.list().size() > 0) {
			isCansync = true;
		}
		return isCansync;
	}

	public int getcompositeidinteger(String table_name) {
		int getBack = 1;
		String sql = "select max(id_composite) as max_id from " + table_name;
		try {
			Iterator it = sessionFactory.getCurrentSession()
					.createSQLQuery(sql).list().iterator();
			if (it.hasNext()) {
				getBack = Integer.valueOf(it.next().toString());
				getBack++;
			}
		} catch (Exception ex) {
			System.out
					.println("error getcompositeidinteger " + ex.getMessage());
		}
		return getBack;
	}

	public int getcompositeidintegerbycompanyid(String table_name,
			int id_company) {
		int getBack = 1;
		String sql = "select max(id_composite) as max_id from " + table_name
				+ " where company_id = " + id_company;
		try {
			Iterator it = sessionFactory.getCurrentSession()
					.createSQLQuery(sql).list().iterator();
			if (it.hasNext()) {
				getBack = Integer.valueOf(it.next().toString());
				getBack++;
			}
		} catch (Exception ex) {
			System.out.println("error getcompositeidintegerbycompanyid "
					+ ex.getMessage());
		}
		return getBack;
	}

	public int getcompositeidintegerbySchoolID(String table_name, int id_company) {
		int getBack = 1;
		String sql = "select max(id_composite) as max_id from " + table_name
				+ " where school_id = " + id_company;
		try {
			Iterator it = sessionFactory.getCurrentSession()
					.createSQLQuery(sql).list().iterator();
			if (it.hasNext()) {
				getBack = Integer.valueOf(it.next().toString());
				getBack++;
			}
		} catch (Exception ex) {
			System.out.println("error getcompositeidintegerbySchoolID "
					+ ex.getMessage());
		}
		return getBack;
	}

	public void addComputerListString(int comp_id, String data) {
		System.out.println(" addComputerListString 1 " + data);
		try {
			JSONObject obj = new JSONObject(data);
			if (obj.get("tableName").toString().equals("student")) {
				int i = 0;
				int idHere = Integer.valueOf(obj.get("idvrs_users" + i)
						.toString());
				svs.addComputerList(comp_id, idHere);
				i++;
				idHere = Integer.valueOf(obj.get("idvrs_roles" + i).toString());
				svs.addComputerList(comp_id, idHere);

				boolean notERror = true;
				try {
					i++;
					idHere = Integer.valueOf(obj.get("idved_candidates" + i)
							.toString());
					svs.addComputerList(comp_id, idHere);

					i++;
					idHere = Integer.valueOf(obj.get("idvac_transactions" + i)
							.toString());
					svs.addComputerList(comp_id, idHere);

					i++;
					idHere = Integer.valueOf(obj.get(
							"idvac_transaction_details" + i).toString());
					svs.addComputerList(comp_id, idHere);

					i++;
					idHere = Integer.valueOf(obj.get("idvac_postings" + i)
							.toString());
					svs.addComputerList(comp_id, idHere);

					i++;
					idHere = Integer.valueOf(obj.get("idvac_postings" + i)
							.toString());
					svs.addComputerList(comp_id, idHere);

					i++;
					idHere = Integer.valueOf(obj.get("idved_students" + i)
							.toString());
					svs.addComputerList(comp_id, idHere);

					boolean looping = true;
					while (looping) {
						try {

							i++;
							idHere = Integer.valueOf(obj.get(
									"idvac_transactions" + i).toString());
							svs.addComputerList(comp_id, idHere);
							i++;
							idHere = Integer
									.valueOf(obj.get(
											"idvac_transaction_details" + i)
											.toString());
							svs.addComputerList(comp_id, idHere);
							i++;
							idHere = Integer.valueOf(obj.get(
									"idvac_postings" + i).toString());
							svs.addComputerList(comp_id, idHere);
							i++;
							idHere = Integer.valueOf(obj.get(
									"idvac_postings" + i).toString());
							svs.addComputerList(comp_id, idHere);
						} catch (Exception ex) {
							looping = false;
						}
					}

					try {
						i++;
						idHere = Integer.valueOf(obj
								.get("idved_candidates" + i).toString());
						svs.addComputerList(comp_id, idHere);
					} catch (Exception ex) {
					}
				} catch (Exception ex) {
					notERror = false;
				}

				if (!notERror) {
					boolean looping = true;
					while (looping) {
						try {
							idHere = Integer.valueOf(obj.get("idvrs_users" + i)
									.toString());
							svs.addComputerList(comp_id, idHere);
							i++;
							idHere = Integer.valueOf(obj.get("idvrs_roles" + i)
									.toString());
							svs.addComputerList(comp_id, idHere);
							i++;
						} catch (Exception ex) {
							looping = false;
						}
					}
				}
			} else if (obj.get("tableName").toString().equals("transactions1")) {
				System.out.println(" addComputerListString transactions1 ");
				int idHere = 0, i = 0;
				boolean looping = true;
				while (looping) {
					try {
						idHere = Integer.valueOf(obj.get(
								"idvac_transactions" + i).toString());
						svs.addComputerList(comp_id, idHere);
						i++;
						idHere = Integer.valueOf(obj.get(
								"idvac_transaction_details" + i).toString());
						svs.addComputerList(comp_id, idHere);
						i++;
						idHere = Integer.valueOf(obj.get("idvac_postings" + i)
								.toString());
						svs.addComputerList(comp_id, idHere);
						i++;
						idHere = Integer.valueOf(obj.get("idvac_postings" + i)
								.toString());
						svs.addComputerList(comp_id, idHere);
						i++;
					} catch (Exception ex) {
						looping = false;
					}
				}
				try {
					idHere = Integer.valueOf(obj.get("idved_candidates" + i)
							.toString());
					svs.addComputerList(comp_id, idHere);
				} catch (Exception ex) {
				}
			}

		} catch (Exception ex) {
			cf.viewAlert("error addComputerListString "
						+ ex.getMessage());
		}
	}

	int limitString = 7700;

	

	

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void changeIdComposite_vrs_users(int idComposite) {
		String sql = "select id_composite from vrs_users where id_composite=:id_composite";
		Iterator it = sessionFactory.getCurrentSession().createSQLQuery(sql)
				.setInteger("id_composite", idComposite).list().iterator();
		if (it.hasNext()) {
			boolean search = true;
			int maxID = idComposite + 1;
			while (search) {
				sql = "select id_composite from vrs_users where id_composite=:id_composite";
				it = sessionFactory.getCurrentSession().createSQLQuery(sql)
						.setInteger("id_composite", maxID).list().iterator();
				if (it.hasNext())
					maxID++;
				else {
					search = false;
				}
			}
			maxID--;
			try {
				if (idComposite == maxID) {
					sql = "update vrs_users set id_composite=:idNext where id_composite=:idThis";
					sessionFactory.getCurrentSession().createSQLQuery(sql)
							.setInteger("idNext", (maxID + 1))
							.setInteger("idThis", maxID).executeUpdate();
					sql = "update vrs_sync_items set id_composite = :idNext where id_composite = :idThis and table_name = :tableName and status=false";
					sessionFactory.getCurrentSession().createSQLQuery(sql)
							.setInteger("idNext", (maxID + 1))
							.setString("tableName", "vrs_users")
							.setInteger("idThis", maxID).executeUpdate();

				} else if (idComposite < maxID) {
					int biggerID = 0;
					for (biggerID = maxID; biggerID > idComposite; biggerID--) {

						sql = "update vrs_users set id_composite=:idNext where id_composite=:idThis";
						sessionFactory.getCurrentSession().createSQLQuery(sql)
								.setInteger("idNext", (biggerID))
								.setInteger("idThis", biggerID - 1)
								.executeUpdate();
						sql = "update vrs_sync_items set id_composite = :idNext where id_composite = :idThis and table_name = :tableName and status=false";
						sessionFactory.getCurrentSession().createSQLQuery(sql)
								.setInteger("idNext", (biggerID))
								.setString("tableName", "vrs_users")
								.setInteger("idThis", biggerID - 1)
								.executeUpdate();

					}
				}
			} catch (Exception ex) {
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void changeIdComposite_vrs_roles(int idComposite, int company_id,
			String tableName) {
		String sql = "select id_composite from "
				+ tableName
				+ "  where  company_id=:companyThis and id_composite=:id_composite";
		Iterator it = sessionFactory.getCurrentSession().createSQLQuery(sql)
				.setInteger("id_composite", idComposite)
				.setInteger("companyThis", company_id).list().iterator();
		if (it.hasNext()) {
			boolean search = true;
			int maxID = idComposite + 1;
			while (search) {
				sql = "select id_composite from "
						+ tableName
						+ "  where company_id=:companyThis and  id_composite=:id_composite";
				it = sessionFactory.getCurrentSession().createSQLQuery(sql)
						.setInteger("id_composite", maxID)
						.setInteger("companyThis", company_id).list()
						.iterator();
				if (it.hasNext()) {
					maxID++;
				} else {
					search = false;
				}
			}
			maxID--;
			try {
				if (idComposite == maxID) {
					sql = "update "
							+ tableName
							+ " set id_composite = :id_compositeNext  where company_id = :company_idThis and id_composite = :id_compositeThis";
					sessionFactory.getCurrentSession().createSQLQuery(sql)
							.setInteger("id_compositeNext", (maxID + 1))
							.setInteger("id_compositeThis", maxID)
							.setInteger("company_idThis", company_id)
							.executeUpdate();
					sql = "update vrs_sync_items set id_composite = :idNext where id_composite = :idThis and company_id = :company_idThis "
							+ " and table_name = :tableName and status=false";
					sessionFactory.getCurrentSession().createSQLQuery(sql)
							.setInteger("idNext", (maxID + 1))
							.setString("tableName", tableName)
							.setInteger("company_idThis", company_id)
							.setInteger("idThis", maxID).executeUpdate();

				} else if (idComposite < maxID) {
					int biggerID = 0;
					for (biggerID = maxID; biggerID > idComposite; biggerID--) {
						sql = "update "
								+ tableName
								+ " set id_composite = :id_compositeNext  where company_id = :company_idThis and id_composite = :id_compositeThis";
						sessionFactory.getCurrentSession().createSQLQuery(sql)
								.setInteger("id_compositeNext", (biggerID))
								.setInteger("id_compositeThis", biggerID - 1)
								.setInteger("company_idThis", company_id)
								.executeUpdate();
						sql = "update vrs_sync_items set id_composite = :idNext where id_composite = :idThis and company_id = :company_idThis "
								+ " and table_name = :tableName and status=false";
						sessionFactory.getCurrentSession().createSQLQuery(sql)
								.setInteger("idNext", (biggerID))
								.setString("tableName", tableName)
								.setInteger("company_idThis", company_id)
								.setInteger("idThis", biggerID - 1)
								.executeUpdate();
						sessionFactory.getCurrentSession().clear();
					}
				}
			} catch (Exception ex) {
			}
		}

	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void changeIdComposite_ved_candidates(int idComposite,
			int school_id, String tableName) {
		String sql = "select id_composite from "
				+ tableName
				+ "  where  school_id=:school_idThis and id_composite=:id_composite";
		Iterator it = sessionFactory.getCurrentSession().createSQLQuery(sql)
				.setInteger("id_composite", idComposite)
				.setInteger("school_idThis", school_id).list().iterator();
		if (it.hasNext()) {
			boolean search = true;
			int maxID = idComposite + 1;
			while (search) {
				sql = "select id_composite from "
						+ tableName
						+ "  where school_id = :school_idThis and id_composite=:id_composite";
				it = sessionFactory.getCurrentSession().createSQLQuery(sql)
						.setInteger("id_composite", maxID)
						.setInteger("school_idThis", school_id).list()
						.iterator();
				if (it.hasNext())
					maxID++;
				else {
					search = false;
				}
			}
			maxID--;
			try {
				if (idComposite == maxID) {
					sql = "update "
							+ tableName
							+ " set id_composite = :id_compositeNext  where school_id = :school_idThis and id_composite = :id_compositeThis";
					sessionFactory.getCurrentSession().createSQLQuery(sql)
							.setInteger("id_compositeNext", (maxID + 1))
							.setInteger("id_compositeThis", maxID)
							.setInteger("school_idThis", school_id)
							.executeUpdate();
					sql = "update vrs_sync_items set id_composite = :idNext where id_composite = :idThis and company_id = :company_idThis "
							+ " and table_name = :tableName and status=false";
					sessionFactory.getCurrentSession().createSQLQuery(sql)
							.setInteger("idNext", (maxID + 1))
							.setString("tableName", tableName)
							.setInteger("company_idThis", school_id)
							.setInteger("idThis", maxID).executeUpdate();
				} else if (idComposite < maxID) {
					int biggerID = 0;
					for (biggerID = maxID; biggerID > idComposite; biggerID--) {
						sql = "update "
								+ tableName
								+ " set id_composite = :id_compositeNext  where school_id = :school_idThis and id_composite = :id_compositeThis";
						sessionFactory.getCurrentSession().createSQLQuery(sql)
								.setInteger("id_compositeNext", (biggerID))
								.setInteger("id_compositeThis", biggerID - 1)
								.setInteger("school_idThis", school_id)
								.executeUpdate();

						sql = "update vrs_sync_items set id_composite = :idNext where id_composite = :idThis and company_id = :company_idThis "
								+ " and table_name = :tableName and status=false";
						sessionFactory.getCurrentSession().createSQLQuery(sql)
								.setInteger("idNext", (biggerID))
								.setString("tableName", tableName)
								.setInteger("company_idThis", school_id)
								.setInteger("idThis", biggerID - 1)
								.executeUpdate();
					}
				}
			} catch (Exception ex) {
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void doSetttingItems(int id_vrs_sync_items,
			int id_CompositeFromServer) {
		String sql = "update vrs_sync_items set status=:status where id=:id";
		sessionFactory.getCurrentSession().createQuery(sql)
				.setInteger("id", id_vrs_sync_items).setBoolean("status", true)
				.executeUpdate();

		if (id_CompositeFromServer != -1) {
			sql = "select table_name,id_table from vrs_sync_items where  id=:id";
			Iterator it = sessionFactory.getCurrentSession().createQuery(sql)
					.setInteger("id", id_vrs_sync_items).list().iterator();
			String table_name = "";
			int id_table = 0;
			if (it.hasNext()) {
				Object[] rows = (Object[]) it.next();
				table_name = rows[0].toString();
				id_table = Integer.valueOf(rows[1].toString());
				sql = "ALTER TABLE " + table_name + " DISABLE TRIGGER insert_"
						+ table_name + "t";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sql = "update " + table_name
						+ " set id_composite=:id_compositeNew where id=:id";
				if (table_name.equalsIgnoreCase("ved_grade_report")
						|| table_name
								.equalsIgnoreCase("ved_grade_report_detail")) {
					sessionFactory
							.getCurrentSession()
							.createSQLQuery(sql)
							.setInteger("id", id_table)
							.setInteger("id_compositeNew",
									id_CompositeFromServer).executeUpdate();
				} else {
					sessionFactory
							.getCurrentSession()
							.createQuery(sql)
							.setInteger("id", id_table)
							.setInteger("id_compositeNew",
									id_CompositeFromServer).executeUpdate();
				}

				sql = "ALTER TABLE " + table_name + " ENABLE TRIGGER insert_"
						+ table_name + "t";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	void doSetttingItemsbyObject(JSONObject objDAta1) {
		try {
			int id_vrs_sync_items = Integer.valueOf(objDAta1.get(
					"id_vrs_sync_itemsLOCAL").toString());
			int id_CompositeFromServer = -1;
			try {
				id_CompositeFromServer = Integer.valueOf(objDAta1.get(
						"id_CompositeFromServer").toString());
			} catch (Exception ex) {
			}
			doSetttingItems(id_vrs_sync_items, id_CompositeFromServer);
		} catch (Exception ex) {
			cf.viewAlert("error in doSetttingItemsbyObject "+ex.getMessage());
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean updateDataVrs_sync_itemsGroup(String datai,
			boolean alreadySetItems, JSONObject obj) {
		try {
			if (obj.get("tableName").toString().equalsIgnoreCase("student")) {
				int i = 0;
				String tableName = "vrs_users";
				JSONObject objDAta1 = new JSONObject(obj.get(tableName + i)
						.toString());
				doSetttingItemsbyObject(objDAta1);

				i++;
				tableName = "vrs_roles";
				objDAta1 = new JSONObject(obj.get(tableName + i).toString());
				doSetttingItemsbyObject(objDAta1);

				boolean notError = true;
				try {

					i++;
					tableName = "ved_candidates";
					objDAta1 = new JSONObject(obj.get(tableName + i).toString());
					doSetttingItemsbyObject(objDAta1);

					i++;
					tableName = "vac_transactions";
					objDAta1 = new JSONObject(obj.get(tableName + i).toString());
					doSetttingItemsbyObject(objDAta1);

					i++;
					tableName = "vac_transaction_details";
					objDAta1 = new JSONObject(obj.get(tableName + i).toString());
					doSetttingItemsbyObject(objDAta1);

					i++;
					tableName = "vac_postings";
					objDAta1 = new JSONObject(obj.get(tableName + i).toString());
					doSetttingItemsbyObject(objDAta1);

					i++;
					tableName = "vac_postings";
					objDAta1 = new JSONObject(obj.get(tableName + i).toString());
					doSetttingItemsbyObject(objDAta1);

					i++;
					tableName = "ved_students";
					objDAta1 = new JSONObject(obj.get(tableName + i).toString());
					doSetttingItemsbyObject(objDAta1);

					boolean looping = true;
					while (looping) {
						try {
							i++;
							tableName = "vac_transactions";
							objDAta1 = new JSONObject(obj.get(tableName + i)
									.toString());
							doSetttingItemsbyObject(objDAta1);

							i++;
							tableName = "vac_transaction_details";
							objDAta1 = new JSONObject(obj.get(tableName + i)
									.toString());
							doSetttingItemsbyObject(objDAta1);

							i++;
							tableName = "vac_postings";
							objDAta1 = new JSONObject(obj.get(tableName + i)
									.toString());
							doSetttingItemsbyObject(objDAta1);

							i++;
							tableName = "vac_postings";
							objDAta1 = new JSONObject(obj.get(tableName + i)
									.toString());
							doSetttingItemsbyObject(objDAta1);
						} catch (Exception ex) {
							looping = false;
						}
					}
				} catch (Exception ex) {
					notError = false;
				}

				if (!notError) {
					boolean looping = true;
					while (looping) {
						try {
							tableName = "vrs_users";
							objDAta1 = new JSONObject(obj.get(tableName + i)
									.toString());
							doSetttingItemsbyObject(objDAta1);

							i++;
							tableName = "vrs_roles";
							objDAta1 = new JSONObject(obj.get(tableName + i)
									.toString());
							doSetttingItemsbyObject(objDAta1);
							i++;
						} catch (Exception ex) {
							looping = false;
						}
					}

				}

			} else if (obj.get("tableName").toString()
					.equalsIgnoreCase("transactions1")) {
				int i = 0;
				boolean looping = true;
				String tableName = "";
				JSONObject objDAta1 = new JSONObject();
				while (looping) {
					try {
						tableName = "vac_transactions";
						objDAta1 = new JSONObject(obj.get(tableName + i)
								.toString());
						doSetttingItemsbyObject(objDAta1);

						i++;
						tableName = "vac_transaction_details";
						objDAta1 = new JSONObject(obj.get(tableName + i)
								.toString());
						doSetttingItemsbyObject(objDAta1);

						i++;
						tableName = "vac_postings";
						objDAta1 = new JSONObject(obj.get(tableName + i)
								.toString());
						doSetttingItemsbyObject(objDAta1);

						i++;
						tableName = "vac_postings";
						objDAta1 = new JSONObject(obj.get(tableName + i)
								.toString());
						doSetttingItemsbyObject(objDAta1);
						i++;
					} catch (Exception ex) {
						looping = false;
					}
				}
			}
			alreadySetItems = true;
		} catch (Exception ex) {
			alreadySetItems = false;
		}
		return alreadySetItems;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addSyncLogData(int id_vrs_sync_items, int company_id,
			int id_computer, String type) {
		try {
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(vrs_sync_items.class);
			criteria.add(Restrictions.eq("id", id_vrs_sync_items));
			Iterator it = criteria.list().iterator();

			if (it.hasNext()) {
				vrs_sync_items vsi = (vrs_sync_items) it.next();
				vrs_sync_log vsl = new vrs_sync_log();
				vsl.setAction(vsi.getAction());
				vsl.setId_composite(vsi.getId_composite());
				vsl.setId_computer(id_computer);
				vsl.setTable_name(vsi.getTable_name());

				Timestamp tstamp = new Timestamp(new Date().getTime());
				vsl.setTime_created(tstamp);
				vsl.setType(type);

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", company_id));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					company_id = vc.getCompanyId();
				}

				vsl.setCompany_id(company_id);
				sessionFactory.getCurrentSession().save(vsl);
			}

		} catch (Exception ex) {
			cf.viewAlert("eror addSyncLogData " + ex.getMessage());
		}

	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addVrs_Sync_log(String data, int company_id, int id_computer,
			String type) {
		boolean alreadyLog = false;

		try {
			JSONObject obj = new JSONObject(data);
			int id_vrs_sync_items = Integer.valueOf(obj
					.get("id_vrs_sync_items").toString());
			addSyncLogData(id_vrs_sync_items, company_id, id_computer, type);
			alreadyLog = true;

		} catch (Exception ex) {

		}
		if (!alreadyLog) {
			try {
				JSONObject obj = new JSONObject(data);
				String whatFor = obj.get("tableName").toString();
				if (whatFor.equalsIgnoreCase("student")) {
					int i = 0;
					int id_vrs_sync_items = Integer.valueOf(obj.get(
							"vrs_users" + i).toString());
					addSyncLogData(id_vrs_sync_items, company_id, id_computer,
							type);
					i++;
					id_vrs_sync_items = Integer.valueOf(obj
							.get("vrs_roles" + i).toString());
					addSyncLogData(id_vrs_sync_items, company_id, id_computer,
							type);

					boolean notError = true;
					try {
						i++;
						id_vrs_sync_items = Integer.valueOf(obj.get(
								"ved_candidates" + i).toString());
						addSyncLogData(id_vrs_sync_items, company_id,
								id_computer, type);
						i++;
						id_vrs_sync_items = Integer.valueOf(obj.get(
								"vac_transactions" + i).toString());
						addSyncLogData(id_vrs_sync_items, company_id,
								id_computer, type);
						i++;
						id_vrs_sync_items = Integer.valueOf(obj.get(
								"vac_transaction_details" + i).toString());
						addSyncLogData(id_vrs_sync_items, company_id,
								id_computer, type);
						i++;
						id_vrs_sync_items = Integer.valueOf(obj.get(
								"vac_postings" + i).toString());
						addSyncLogData(id_vrs_sync_items, company_id,
								id_computer, type);
						i++;
						id_vrs_sync_items = Integer.valueOf(obj.get(
								"vac_postings" + i).toString());
						addSyncLogData(id_vrs_sync_items, company_id,
								id_computer, type);
						i++;
						id_vrs_sync_items = Integer.valueOf(obj.get(
								"ved_students" + i).toString());
						addSyncLogData(id_vrs_sync_items, company_id,
								id_computer, type);

						boolean looping = true;
						while (looping) {
							try {
								i++;
								id_vrs_sync_items = Integer.valueOf(obj.get(
										"vac_transactions" + i).toString());
								addSyncLogData(id_vrs_sync_items, company_id,
										id_computer, type);

								i++;
								id_vrs_sync_items = Integer.valueOf(obj.get(
										"vac_transaction_details" + i)
										.toString());
								addSyncLogData(id_vrs_sync_items, company_id,
										id_computer, type);

								i++;
								id_vrs_sync_items = Integer.valueOf(obj.get(
										"vac_postings" + i).toString());
								addSyncLogData(id_vrs_sync_items, company_id,
										id_computer, type);
								i++;
								id_vrs_sync_items = Integer.valueOf(obj.get(
										"vac_postings" + i).toString());
								addSyncLogData(id_vrs_sync_items, company_id,
										id_computer, type);

							} catch (Exception ex) {
								looping = false;
							}
						}

						try {
							id_vrs_sync_items = Integer.valueOf(obj.get(
									"ved_candidates" + i).toString());
							addSyncLogData(id_vrs_sync_items, company_id,
									id_computer, type);

						} catch (Exception ex) {
							looping = false;
						}

					} catch (Exception ex) {
						notError = false;
					}

					if (!notError) {
						boolean looping = true;
						while (looping) {
							try {
								id_vrs_sync_items = Integer.valueOf(obj.get(
										"vrs_users" + i).toString());
								addSyncLogData(id_vrs_sync_items, company_id,
										id_computer, type);
								i++;
								id_vrs_sync_items = Integer.valueOf(obj.get(
										"vrs_roles" + i).toString());
								addSyncLogData(id_vrs_sync_items, company_id,
										id_computer, type);
								i++;
							} catch (Exception ex) {
								looping = false;
							}
						}
					}

				} else if (whatFor.equalsIgnoreCase("transactions1")) {
					int i = 0, id_vrs_sync_items = 0;
					boolean looping = true;
					while (looping) {
						try {
							id_vrs_sync_items = Integer.valueOf(obj.get(
									"vac_transactions" + i).toString());
							addSyncLogData(id_vrs_sync_items, company_id,
									id_computer, type);
							i++;
							id_vrs_sync_items = Integer.valueOf(obj.get(
									"vac_transaction_details" + i).toString());
							addSyncLogData(id_vrs_sync_items, company_id,
									id_computer, type);
							i++;
							id_vrs_sync_items = Integer.valueOf(obj.get(
									"vac_postings" + i).toString());
							addSyncLogData(id_vrs_sync_items, company_id,
									id_computer, type);
							i++;
							id_vrs_sync_items = Integer.valueOf(obj.get(
									"vac_postings" + i).toString());
							addSyncLogData(id_vrs_sync_items, company_id,
									id_computer, type);
							i++;
						} catch (Exception ex) {
							looping = false;
						}
					}

					try {
						id_vrs_sync_items = Integer.valueOf(obj.get(
								"ved_candidates" + i).toString());
						addSyncLogData(id_vrs_sync_items, company_id,
								id_computer, type);
					} catch (Exception ex) {
						looping = false;

						while (looping) {
							try {
								id_vrs_sync_items = Integer.valueOf(obj.get(
										"vrs_users" + i).toString());
								addSyncLogData(id_vrs_sync_items, company_id,
										id_computer, type);
								i++;
								id_vrs_sync_items = Integer.valueOf(obj.get(
										"vrs_roles" + i).toString());
								addSyncLogData(id_vrs_sync_items, company_id,
										id_computer, type);

								i++;
							} catch (Exception ex2) {
								looping = false;
							}
						}
					}
				}
			} catch (Exception ex) {
				cf.viewAlert("eror addVrs_Sync_logstudent "
							+ ex.getMessage());
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int getComputerIdOnSErver(String computername, String userServer,
			String passwordSErver) {
		int getBack = 0;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_server_registry.class);
		criteria.add(Restrictions.eq("computer_name", computername));
		criteria.add(Restrictions.eq("userserver", userServer));
		criteria.add(Restrictions.eq("password", passwordSErver));
		Iterator it = criteria.list().iterator();
		if (it.hasNext()) {
			vrs_server_registry vsr = (vrs_server_registry) it.next();
			getBack = vsr.getId();
		}
		return getBack;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void saveInTable_vrs_accounts(String data, String forWhat) {
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("vrs_accounts")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);

			int userid = 0;
			if (action.equalsIgnoreCase("add")) {				
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_users.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("id_composite_users")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_users vus = (vrs_users) it.next();
					userid = vus.getId();
					
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_accounts.class);
					criteria.add(Restrictions.eq("user_id", userid));
					if (criteria.list().size()<1) {
						vrs_accounts vac = new vrs_accounts();
						vac.setCreated(svs.getTimeStamp(dataVrs_users.get("created")
								.toString()));
						vac.setEmail(dataVrs_users.get("email").toString());
						vac.setId_composite(Integer.valueOf(dataVrs_users.get(
								"id_composite").toString()));
						vac.setModified(svs.getTimeStamp(dataVrs_users.get("modified")
								.toString()));
						vac.setPassword(dataVrs_users.get("password").toString());
						vac.setShortcode(dataVrs_users.get("shortcode").toString());
						vac.setStatus(Integer.valueOf(dataVrs_users.get("status")
								.toString()));
						vac.setUser_id(userid);
						sessionFactory.getCurrentSession().save(vac);
					}
					
				}
			} else if (action.equalsIgnoreCase("edit")) {
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_users.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("id_composite_users")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_users vus = (vrs_users) it.next();
					userid = vus.getId();
				}
				int idTable = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_accounts.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("id_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_accounts vac = (vrs_accounts) it.next();
					vac.setCreated(svs.getTimeStamp(dataVrs_users
							.get("created").toString()));
					vac.setEmail(dataVrs_users.get("email").toString());
					vac.setId_composite(Integer.valueOf(dataVrs_users.get(
							"id_composite").toString()));
					vac.setModified(svs.getTimeStamp(dataVrs_users.get(
							"modified").toString()));
					vac.setPassword(dataVrs_users.get("password").toString());
					vac.setShortcode(dataVrs_users.get("shortcode").toString());
					vac.setStatus(Integer.valueOf(dataVrs_users.get("status")
							.toString()));
					vac.setUser_id(userid);
					sessionFactory.getCurrentSession().update(vac);
				}
			} else if (action.equalsIgnoreCase("del")) {
				int idTable = -1;
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_accounts.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("id_composite").toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_accounts vac = (vrs_accounts) it.next();
					sessionFactory.getCurrentSession().delete(vac);
				}
			}

		} catch (Exception ex) {
			cf.viewAlert("error in saveInTable_vrs_accounts " + ex.getMessage());
		}
	}
}
