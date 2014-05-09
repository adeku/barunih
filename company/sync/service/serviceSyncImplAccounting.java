package verse.sync.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

import verse.accounting.model.vac_budgets;
import verse.accounting.model.vac_chart_account_balances;
import verse.accounting.model.vac_chart_accounts;
import verse.accounting.model.vac_postings;
import verse.accounting.model.vac_transaction_details;
import verse.accounting.model.vac_transaction_metas;
import verse.accounting.model.vac_transactions;
import verse.commonClass.CommonFunction;
import verse.model.RoleName;
import verse.model.vrs_companies;
import verse.model.vrs_roles;
import verse.sync.model.vrs_sync_computerlist;
import verse.sync.model.vrs_sync_items;

@Service("serviceSyncAccounting")
public class serviceSyncImplAccounting implements serviceSyncAccounting {	
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private serviceSyncVRS sVRS;	
	@Autowired
	private serviceSyncAcademic_Accounting sacc;
	@Autowired
	private serviceSyncAcademic ssa;
	
	CommonFunction cf = new CommonFunction();
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String getvac_postings_DAta(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vac_postings.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		if (it.hasNext()) {
			vac_postings vcp = (vac_postings) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vac_postings");
				obj.put("id_vrs_sync_itemsLOCAL", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vcp.getCompanyId()));
				Iterator itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				if (action.equalsIgnoreCase("edit"))
					objColumn.put("id_composite", vcp.getId_composite());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vac_transaction_details.class);
				criteria.add(Restrictions.eq("id", vcp.getRefId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vac_transaction_details vtd = (vac_transaction_details) itMeta
							.next();
					objColumn.put("RefId_id_composite", vtd.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId",
							vtd.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("RefId_id_company_composite",
								vc.getId_composite());
					}
				}
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vac_chart_accounts.class);
				criteria.add(Restrictions.eq("id", vcp.getAccountId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vac_chart_accounts vca = (vac_chart_accounts) itMeta.next();
					objColumn.put("AccountId_id_composite",
							vca.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId",
							vca.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("AccountId_id_company_composite",
								vc.getId_composite());
					}
				}
				objColumn.put("amount", vcp.getAmount());
				objColumn.put("created", vcp.getCreated());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vcp.getCreatedBy()));
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

				objColumn.put("modified", vcp.getModified());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vcp.getModifiedBy()));
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
				objColumn.put("status", vcp.getStatus());
				obj.append("vac_postings", objColumn);
				getBack = obj.toString();
			} catch (Exception ex) {
			}
		}
		return getBack;
	}
	
	public String getvac_chart_account_balances_DAtaDownload(int id_table,
			String action, int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vac_chart_account_balances.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			vac_chart_account_balances vcab = (vac_chart_account_balances) it
					.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vac_chart_account_balances");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vcab.getCompanyId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vac_chart_accounts.class);
				criteria.add(Restrictions.eq("id", vcab.getAccountId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vac_chart_accounts vca = (vac_chart_accounts) itMeta.next();
					objColumn.put("AccountId_id_composite",
							vca.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId",
							vca.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("AccountId_id_company_composite",
								vc.getId_composite());
					}
				}

				objColumn.put("lastBalance", vcab.getLastBalance());
				String monthlyBalance = "";
				if (vcab.getMonthlyBalance() != null)
					if (vcab.getMonthlyBalance().toString().length() > 0)
						monthlyBalance = vcab.getMonthlyBalance();
				objColumn.put("monthlyBalance", monthlyBalance);
				String lastArchiveBalance = "";
				if (vcab.getLastArchiveBalance() != null)
					if (vcab.getLastArchiveBalance().toString().length() > 0)
						lastArchiveBalance = vcab.getLastArchiveBalance()
								.toString();
				objColumn.put("lastArchiveBalance", lastArchiveBalance);
				objColumn.put("id_composite", vcab.getId_composite());
				obj.append("vac_chart_account_balances", objColumn);
				getBack = obj.toString();

			} catch (Exception ax) {
			}
		}

		return getBack;
	}
	
	public String getvac_chart_account_balances_DAta(int id_table,
			String action, int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vac_chart_account_balances.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			vac_chart_account_balances vcab = (vac_chart_account_balances) it
					.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vac_chart_account_balances");
				obj.put("id_vrs_sync_itemsLOCAL", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vcab.getCompanyId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vac_chart_accounts.class);
				criteria.add(Restrictions.eq("id", vcab.getAccountId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vac_chart_accounts vca = (vac_chart_accounts) itMeta.next();
					objColumn.put("AccountId_id_composite",
							vca.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId",
							vca.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("AccountId_id_company_composite",
								vc.getId_composite());
					}
				}

				objColumn.put("lastBalance", vcab.getLastBalance());
				String monthlyBalance = "";
				if (vcab.getMonthlyBalance() != null)
					if (vcab.getMonthlyBalance().toString().length() > 0)
						monthlyBalance = vcab.getMonthlyBalance();
				objColumn.put("monthlyBalance", monthlyBalance);
				String lastArchiveBalance = "";
				if (vcab.getLastArchiveBalance() != null)
					if (vcab.getLastArchiveBalance().toString().length() > 0)
						lastArchiveBalance = vcab.getLastArchiveBalance()
								.toString();
				objColumn.put("lastArchiveBalance", lastArchiveBalance);
				if (action.equalsIgnoreCase("edit"))
					objColumn.put("id_composite", vcab.getId_composite());
				obj.append("vac_chart_account_balances", objColumn);
				getBack = obj.toString();

			} catch (Exception ax) {
			}
		}
		return getBack;

	}
	
	public String getvac_budgets_DAtaDownload(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vac_budgets.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			vac_budgets vb = (vac_budgets) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vac_budgets");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vb.getCompanyId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vac_chart_accounts.class);
				criteria.add(Restrictions.eq("id", vb.getAccountId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vac_chart_accounts vca = (vac_chart_accounts) itMeta.next();
					objColumn.put("AccountId_id_composite",
							vca.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId",
							vca.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("AccountId_id_company_composite",
								vc.getId_composite());
					}
				}
				objColumn.put("amount", vb.getAmount());
				objColumn.put("monthlyAmount", vb.getMonthlyAmount());
				objColumn.put("created", vb.getCreated());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vb.getCreatedBy()));
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

				objColumn.put("modified", vb.getModified());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vb.getModifiedBy()));
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

				objColumn.put("status", vb.getStatus());
				objColumn.put("id_composite", vb.getId_composite());
				obj.append("vac_budgets", objColumn);
				getBack = obj.toString();

			} catch (Exception ex) {
			}
		}
		return getBack;
	}
	
	public String getvac_budgets_DAta(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vac_budgets.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			vac_budgets vb = (vac_budgets) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vac_budgets");
				obj.put("id_vrs_sync_itemsLOCAL", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vb.getCompanyId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vac_chart_accounts.class);
				criteria.add(Restrictions.eq("id", vb.getAccountId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vac_chart_accounts vca = (vac_chart_accounts) itMeta.next();
					objColumn.put("AccountId_id_composite",
							vca.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId",
							vca.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("AccountId_id_company_composite",
								vc.getId_composite());
					}
				}
				objColumn.put("amount", vb.getAmount());
				objColumn.put("monthlyAmount", vb.getMonthlyAmount());
				objColumn.put("created", vb.getCreated());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vb.getCreatedBy()));
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

				objColumn.put("modified", vb.getModified());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vb.getModifiedBy()));
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

				objColumn.put("status", vb.getStatus());
				if (action.equalsIgnoreCase("edit"))
					objColumn.put("id_composite", vb.getId_composite());
				obj.append("vac_budgets", objColumn);
				getBack = obj.toString();

			} catch (Exception ex) {
			}
		}
		return getBack;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String getvac_transactions_DAta(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vac_transactions.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			vac_transactions vtr = (vac_transactions) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vac_transactions");
				obj.put("id_vrs_sync_itemsLOCAL", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vtr.getCompanyId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}
				if (action.equalsIgnoreCase("edit"))
					objColumn.put("id_composite", vtr.getId_composite());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vtr.getRoleName()
						.getId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("PartyId_id_composite", vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("PartyId_id_compositeCompany",
								vc.getId_composite());
					}
				}
				objColumn.put("type", vtr.getType());
				objColumn.put("refNumber", vtr.getRefNumber());
				objColumn.put("amount", vtr.getAmount());
				objColumn.put("trxDate", vtr.getTrxDate());
				objColumn.put("created", vtr.getCreated());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vtr.getCreatedBy()));
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
				objColumn.put("modified", vtr.getModified());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vtr.getModifiedBy()));
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

				objColumn.put("status", vtr.getStatus());
				obj.append("vac_transactions", objColumn);

				List<vac_transaction_metas> vtm = vtr.getTransaction_metas();
				for (vac_transaction_metas trans_meta : vtm) {
					objColumn = new JSONObject();
					objColumn.put("metaKey", trans_meta.getKey());
					objColumn.put("metaValue", trans_meta.getValue());
					objColumn.put("created", trans_meta.getCreated());
					objColumn.put("modified", trans_meta.getModified());
					obj.append("vac_transaction_metas", objColumn);
				}
				getBack = obj.toString();
			} catch (Exception ex) {
			}
		}
		return getBack;
	}
	

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String getvac_transactions_DAtaDownload(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vac_transactions.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			vac_transactions vtr = (vac_transactions) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vac_transactions");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vtr.getCompanyId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				objColumn.put("id_composite", vtr.getId_composite());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vtr.getRoleName()
						.getId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("PartyId_id_composite", vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("PartyId_id_compositeCompany",
								vc.getId_composite());
					}
				}
				objColumn.put("type", vtr.getType());
				objColumn.put("refNumber", vtr.getRefNumber());
				objColumn.put("amount", vtr.getAmount());
				objColumn.put("trxDate", vtr.getTrxDate());
				objColumn.put("created", vtr.getCreated());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vtr.getCreatedBy()));
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

				objColumn.put("modified", vtr.getModified());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vtr.getModifiedBy()));
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

				objColumn.put("status", vtr.getStatus());
				obj.append("vac_transactions", objColumn);
				List<vac_transaction_metas> vtm = vtr.getTransaction_metas();
				for (vac_transaction_metas trans_meta : vtm) {
					objColumn = new JSONObject();
					objColumn.put("metaKey", trans_meta.getKey());
					objColumn.put("metaValue", trans_meta.getValue());
					objColumn.put("created", trans_meta.getCreated());
					objColumn.put("modified", trans_meta.getModified());
					obj.append("vac_transaction_metas", objColumn);
				}
				getBack = obj.toString();

			} catch (Exception ex) {
				cf.viewAlert("error getvrs_logs_DAtaDownload = "
						+ ex.getMessage());
			}
		}
		return getBack;
	}

	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String getvac_postings_DAtaDownload(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vac_postings.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		if (it.hasNext()) {
			vac_postings vcp = (vac_postings) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vac_postings");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vcp.getCompanyId()));
				Iterator itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				objColumn.put("id_composite", vcp.getId_composite());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vac_transaction_details.class);
				criteria.add(Restrictions.eq("id", vcp.getRefId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vac_transaction_details vtd = (vac_transaction_details) itMeta
							.next();
					objColumn.put("RefId_id_composite", vtd.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId",
							vtd.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("RefId_id_company_composite",
								vc.getId_composite());
					}
				}
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vac_chart_accounts.class);
				criteria.add(Restrictions.eq("id", vcp.getAccountId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vac_chart_accounts vca = (vac_chart_accounts) itMeta.next();
					objColumn.put("AccountId_id_composite",
							vca.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId",
							vca.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("AccountId_id_company_composite",
								vc.getId_composite());
					}
				}
				objColumn.put("amount", vcp.getAmount());
				objColumn.put("created", vcp.getCreated());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vcp.getCreatedBy()));
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

				objColumn.put("modified", vcp.getModified());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vcp.getModifiedBy()));
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
				objColumn.put("status", vcp.getStatus());
				obj.append("vac_postings", objColumn);
				getBack = obj.toString();
			} catch (Exception ex) {
			}
		}
		return getBack;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String getvac_transaction_details_DAta(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vac_transaction_details.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			vac_transaction_details vtd = (vac_transaction_details) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vac_transaction_details");
				obj.put("id_vrs_sync_itemsLOCAL", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vtd.getCompanyId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}
				if (action.equalsIgnoreCase("edit"))
					objColumn.put("id_composite", vtd.getId_composite());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vac_transactions.class);
				criteria.add(Restrictions.eq("id", vtd.getTransactions()
						.getId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vac_transactions vtr = (vac_transactions) itMeta.next();
					objColumn.put("TransactionId_id_composite",
							vtr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId",
							vtr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("TransactionId_company_id_composite",
								vc.getId_composite());
					}
				}

				objColumn.put("title", vtd.getTitle());
				objColumn.put("itemId", vtd.getItemId());
				objColumn.put("quantity", vtd.getQuantity());
				objColumn.put("unitPrice", vtd.getUnitPrice());
				objColumn.put("discount", vtd.getDiscount());
				objColumn.put("remaining", vtd.getRemaining());
				if (vtd.getRefId() == null)
					objColumn.put("refId", "null");
				else
					objColumn.put("refId", vtd.getRefId());

				obj.append("vac_transaction_details", objColumn);
				getBack = obj.toString();
			} catch (Exception ex) {
			}
		}
		return getBack;
	}
	
	public String getvac_chart_accounts_DAta(int id_table, String action,
			int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vac_chart_accounts.class);
		criteria.add(Restrictions.eq("id", id_table));

		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			vac_chart_accounts vsi = (vac_chart_accounts) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vac_chart_accounts");
				obj.put("id_vrs_sync_itemsLOCAL", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vsi.getCompanyId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				objColumn.put("code", vsi.getCode());
				objColumn.put("category", vsi.getCategory());
				objColumn.put("name", vsi.getName());
				String description = "";

				if (vsi.getDescription() != null)
					if (vsi.getDescription().length() > 0)
						description = vsi.getDescription();
				objColumn.put("description", description);

				objColumn.put("starting_balance", vsi.getStarting_balance());
				objColumn.put("lastBalance", vsi.getLastBalance());

				if (vsi.getMonthlyBalance() == null)
					objColumn.put("monthlyBalance", "");
				else
					objColumn.put("monthlyBalance", vsi.getMonthlyBalance());

				if (vsi.getLastArchiveBalance() == null)

					objColumn.put("lastArchiveBalance", "");
				else
					objColumn.put("lastArchiveBalance",
							vsi.getLastArchiveBalance());
				objColumn.put("created", vsi.getCreated());
				if (action.equalsIgnoreCase("edit"))
					objColumn.put("id_composite", vsi.getId_composite());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vsi.getCreatedBy()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("createdBy", vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vco = (vrs_companies) itMeta.next();
						objColumn.put("company-createdBy",
								vco.getId_composite());
					}
				}

				objColumn.put("modified", vsi.getModified());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vsi.getModifiedBy()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("modifiedBy", vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vco = (vrs_companies) itMeta.next();
						objColumn.put("company-modifiedBy",
								vco.getId_composite());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vac_chart_accounts.class);
				criteria.add(Restrictions.eq("id", vsi.getParent()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vac_chart_accounts vco = (vac_chart_accounts) itMeta.next();
					objColumn.put("parentID_composite", vco.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId",
							vco.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vsc = (vrs_companies) itMeta.next();
						objColumn.put("compnayID_from_parent",
								vsc.getId_composite());
					}
				}

				objColumn.put("status", vsi.getStatus());
				obj.append("vac_chart_accounts", objColumn);
				getBack = obj.toString();

			} catch (Exception ex) {
			}
		}

		return getBack;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String getvac_transaction_details_DAtaDownload(int id_table,
			String action, int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vac_transaction_details.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			vac_transaction_details vtd = (vac_transaction_details) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vac_transaction_details");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vtd.getCompanyId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				objColumn.put("id_composite", vtd.getId_composite());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vac_transactions.class);
				criteria.add(Restrictions.eq("id", vtd.getTransactions()
						.getId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vac_transactions vtr = (vac_transactions) itMeta.next();
					objColumn.put("TransactionId_id_composite",
							vtr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId",
							vtr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vc = (vrs_companies) itMeta.next();
						objColumn.put("TransactionId_company_id_composite",
								vc.getId_composite());
					}
				}

				objColumn.put("title", vtd.getTitle());
				objColumn.put("itemId", vtd.getItemId());
				objColumn.put("quantity", vtd.getQuantity());
				objColumn.put("unitPrice", vtd.getUnitPrice());
				objColumn.put("discount", vtd.getDiscount());
				objColumn.put("remaining", vtd.getRemaining());
				if (vtd.getRefId() == null)
					objColumn.put("refId", "null");
				else
					objColumn.put("refId", vtd.getRefId());

				obj.append("vac_transaction_details", objColumn);
				getBack = obj.toString();

			} catch (Exception ex) {
			}
		}
		return getBack;
	}
	
	public String getvac_chart_accounts_DAtaDownload(int id_table,
			String action, int id_vrs_sync_items) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vac_chart_accounts.class);
		criteria.add(Restrictions.eq("id", id_table));
		Iterator it = criteria.list().iterator();
		Iterator itMeta = it;
		if (it.hasNext()) {
			vac_chart_accounts vsi = (vac_chart_accounts) it.next();
			JSONObject obj = new JSONObject();
			try {
				obj.put("action", action);
				obj.put("tableName", "vac_chart_accounts");
				obj.put("id_vrs_sync_items", id_vrs_sync_items);
				JSONObject objColumn = new JSONObject();

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("companyId", vsi.getCompanyId()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_companies vc = (vrs_companies) itMeta.next();
					objColumn.put("company_idComposite", vc.getId_composite());
				}

				objColumn.put("code", vsi.getCode());
				objColumn.put("category", vsi.getCategory());
				objColumn.put("name", vsi.getName());
				String description = "";
				if (vsi.getDescription() != null)
					if (vsi.getDescription().length() > 0)
						description = vsi.getDescription();
				objColumn.put("description", description);
				objColumn.put("starting_balance", vsi.getStarting_balance());
				objColumn.put("lastBalance", vsi.getLastBalance());
				objColumn.put("id_composite", vsi.getId_composite());

				if (vsi.getMonthlyBalance() == null)
					objColumn.put("monthlyBalance", "");
				else
					objColumn.put("monthlyBalance", vsi.getMonthlyBalance());

				if (vsi.getLastArchiveBalance() == null)

					objColumn.put("lastArchiveBalance", "");
				else
					objColumn.put("lastArchiveBalance",
							vsi.getLastArchiveBalance());
				objColumn.put("created", vsi.getCreated());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vsi.getCreatedBy()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("createdBy", vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vco = (vrs_companies) itMeta.next();
						objColumn.put("company-createdBy",
								vco.getId_composite());
					}
				}

				objColumn.put("modified", vsi.getModified());

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_roles.class);
				criteria.add(Restrictions.eq("roleId", vsi.getModifiedBy()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vrs_roles vr = (vrs_roles) itMeta.next();
					objColumn.put("modifiedBy", vr.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId", vr.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vco = (vrs_companies) itMeta.next();
						objColumn.put("company-modifiedBy",
								vco.getId_composite());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vac_chart_accounts.class);
				criteria.add(Restrictions.eq("id", vsi.getParent()));
				itMeta = criteria.list().iterator();
				if (itMeta.hasNext()) {
					vac_chart_accounts vco = (vac_chart_accounts) itMeta.next();
					objColumn.put("parentID_composite", vco.getId_composite());
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("companyId",
							vco.getCompanyId()));
					itMeta = criteria.list().iterator();
					if (itMeta.hasNext()) {
						vrs_companies vsc = (vrs_companies) itMeta.next();
						objColumn.put("compnayID_from_parent",
								vsc.getId_composite());
					}
				}

				objColumn.put("status", vsi.getStatus());
				obj.append("vac_chart_accounts", objColumn);
				getBack = obj.toString();
			} catch (Exception ex) {
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
			if (vsi.getTable_name().equalsIgnoreCase(
					"vac_chart_accounts")) {
				getBack = getvac_chart_accounts_DAta(vsi.getId_tableInteger(),
						vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("vac_postings")) {
				getBack = getvac_postings_DAta(vsi.getId_tableInteger(),
						vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("vac_transactions")) {
				getBack = getvac_transactions_DAta(vsi.getId_tableInteger(),
						vsi.getAction(), vsi.getId());
				if (vsi.getAction().equalsIgnoreCase("add")) {
					getBack = sacc.getVac_transactionsforUploD(getBack);
				}
			} else if (vsi.getTable_name().equalsIgnoreCase(
					"vac_transaction_details")) {
				getBack = getvac_transaction_details_DAta(
						vsi.getId_tableInteger(), vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase("vac_budgets")) {
				getBack = getvac_budgets_DAta(vsi.getId_tableInteger(),
						vsi.getAction(), vsi.getId());
			} else if (vsi.getTable_name().equalsIgnoreCase(
					"vac_chart_account_balances")) {
				getBack = getvac_chart_account_balances_DAta(
						vsi.getId_tableInteger(), vsi.getAction(), vsi.getId());
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
			}
		}
		cf.viewAlert(" getBack = " + getBack);
		return getBack;
	}
	
	public String saveInTable_vac_chart_account_balances(String data,
			int computer_id) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL",
					obj.get("id_vrs_sync_itemsLOCAL").toString());
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get(
					"vac_chart_account_balances").toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			if (action.equalsIgnoreCase("add")) {
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

				int accountID = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"AccountId_id_company_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vac_chart_accounts.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"AccountId_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vac_chart_accounts vca = (vac_chart_accounts) it.next();
						accountID = vca.getId();
					}
				}

				int idTabel = -1;
				String sql = "SELECT nextval('vac_chart_account_balances_id_seq')";
				it = sessionFactory.getCurrentSession().createSQLQuery(sql)
						.list().iterator();
				if (it.hasNext()) {
					idTabel = Integer.valueOf(it.next().toString());
				}

				String monthlyBalance = "null";
				if (dataVrs_users.get("monthlyBalance") != null)
					if (dataVrs_users.get("monthlyBalance").toString().length() > 0)
						monthlyBalance = "'"
								+ dataVrs_users.get("monthlyBalance")
										.toString() + "'";

				BigDecimal lastArchiveBalance = null;
				if (dataVrs_users.get("lastArchiveBalance").toString().length() > 0)
					lastArchiveBalance = BigDecimal.valueOf(Double
							.valueOf(dataVrs_users.get("lastArchiveBalance")
									.toString()));

				sql = "insert into vac_chart_account_balances (id,company_id,account_id,last_balance,"
						+ "monthly_balance,last_archive_balance) values (:id,:company_id,:account_id,:last_balance,"
						+ monthlyBalance + ",:last_archive_balance)";

				sessionFactory
						.getCurrentSession()
						.createSQLQuery(sql)
						.setInteger("id", idTabel)
						.setInteger("company_id", companyID)
						.setInteger("account_id", accountID)
						.setBigDecimal(
								"last_balance",
								BigDecimal.valueOf(Double.valueOf(dataVrs_users
										.get("lastBalance").toString())))
						.setBigDecimal("last_archive_balance",
								lastArchiveBalance)

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
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vac_chart_account_balances.class);
				criteria.add(Restrictions.eq("id", idTabel));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vac_chart_account_balances vcaba = (vac_chart_account_balances) it
							.next();
					objView.put("id_CompositeFromServer",
							vcaba.getId_composite());
					getBack = objView.toString();
				}
			} else if (action.equalsIgnoreCase("edit")) {
				int companyID = -1, idTable = -1;
				String sql = "";
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					companyID = vc.getCompanyId();
					sql = "select id from vac_chart_account_balances where company_id=:company_id and id_composite=:id_composite";

					it = sessionFactory
							.getCurrentSession()
							.createSQLQuery(sql)
							.setInteger("company_id", companyID)
							.setInteger(
									"id_composite",
									Integer.valueOf(dataVrs_users.get(
											"id_composite").toString())).list()
							.iterator();
					if (it.hasNext()) {
						idTable = Integer.valueOf(it.next().toString());
					}

				}

				int AccountID = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"AccountId_id_company_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vac_chart_accounts.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"AccountId_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vac_chart_accounts vca = (vac_chart_accounts) it.next();
						AccountID = vca.getId();
					}
				}

				String monthlyBalance = "null";
				if (dataVrs_users.get("monthlyBalance") != null)
					if (dataVrs_users.get("monthlyBalance").toString().length() > 0)
						monthlyBalance = "'"
								+ dataVrs_users.get("monthlyBalance")
										.toString() + "'";

				BigDecimal lastArchiveBalance = null;
				if (dataVrs_users.get("lastArchiveBalance").toString().length() > 0)
					lastArchiveBalance = BigDecimal.valueOf(Double
							.valueOf(dataVrs_users.get("lastArchiveBalance")
									.toString()));

				sql = "update vac_chart_account_balances set companyId=:companyId,"
						+ " accountId=:accountId,lastBalance=:lastBalance,monthlyBalance="
						+ monthlyBalance
						+ ","
						+ " lastArchiveBalance=:lastArchiveBalance "
						+ " where id=:id";

				sessionFactory
						.getCurrentSession()
						.createQuery(sql)

						.setInteger("id", idTable)
						.setInteger("companyId", companyID)
						.setInteger("accountId", AccountID)
						.setBigDecimal(
								"lastBalance",
								BigDecimal.valueOf(Double.valueOf(dataVrs_users
										.get("lastBalance").toString())))
						.setBigDecimal("lastArchiveBalance", lastArchiveBalance)

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
			cf.viewAlert(" error saveInTable_vac_chart_account_balances "
					+ ex.getMessage());
		}
		return getBack;
	}
	
	@Autowired
	serviceSync ssc;
	
	public String saveInTable_vac_budgets(String data, int computer_id) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL",
					obj.get("id_vrs_sync_itemsLOCAL").toString());
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("vac_budgets").toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			if (action.equalsIgnoreCase("add")) {

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

				int accountID = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"AccountId_id_company_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vac_chart_accounts.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"AccountId_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vac_chart_accounts vca = (vac_chart_accounts) it.next();
						accountID = vca.getId();
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
						createdBY = vr.getRoleId();
					}
				}

				int ModifiedBy = -1;
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
						ModifiedBy = vr.getRoleId();
					}
				}

				String monthly_amount = "null";
				if (dataVrs_users.get("monthlyAmount") != null)
					if (dataVrs_users.get("monthlyAmount").toString().length() > 0)
						monthly_amount = "'"
								+ dataVrs_users.get("monthlyAmount").toString()
								+ "'";

				int idTabel = -1;
				String sql = "SELECT nextval('vac_budgets_id_seq')";
				it = sessionFactory.getCurrentSession().createSQLQuery(sql)
						.list().iterator();
				if (it.hasNext()) {
					idTabel = Integer.valueOf(it.next().toString());
				}

				sql = "insert into vac_budgets (id,company_id,account_id,amount,monthly_amount,created,"
						+ "created_by,modified,modified_by,status) values (:id,"
						+ ":company_id,:account_id,:amount,"
						+ monthly_amount
						+ ",:created,"
						+ ":created_by,:modified,:modified_by,:status)";

				sessionFactory
						.getCurrentSession()
						.createSQLQuery(sql)

						.setInteger("id", idTabel)
						.setInteger("company_id", companyID)
						.setInteger("account_id", accountID)
						.setBigDecimal(
								"amount",
								BigDecimal.valueOf(Double.valueOf(dataVrs_users
										.get("amount").toString())))
						.setTimestamp(
								"created",
								ssc.getTimeStamp(dataVrs_users.get("created")
										.toString()))
						.setInteger("created_by", createdBY)
						.setTimestamp(
								"modified",
								ssc.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modified_by", ModifiedBy)
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
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vac_budgets.class);
				criteria.add(Restrictions.eq("id", idTabel));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vac_budgets vbudget = (vac_budgets) it.next();
					objView.put("id_CompositeFromServer",
							vbudget.getId_composite());
					getBack = objView.toString();
				}
			} else if (action.equalsIgnoreCase("edit")) {
				int companyID = -1, idTable = -1;
				String sql = "";
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					companyID = vc.getCompanyId();
					sql = "select id from vac_budgets where company_id = :company_id and id_composite=:id_composite";
					it = sessionFactory
							.getCurrentSession()
							.createSQLQuery(sql)
							.setInteger("company_id", companyID)
							.setInteger(
									"id_composite",
									Integer.valueOf(dataVrs_users.get(
											"id_composite").toString())).list()
							.iterator();
					if (it.hasNext()) {
						idTable = Integer.valueOf(it.next().toString());
					}
				}

				int AccountID = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"AccountId_id_company_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vac_chart_accounts.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"AccountId_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vac_chart_accounts vca = (vac_chart_accounts) it.next();
						AccountID = vca.getId();
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
						createdBY = vr.getRoleId();
					}
				}

				int ModifiedBy = -1;
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
						ModifiedBy = vr.getRoleId();
					}
				}

				String monthly_amount = "null";
				if (dataVrs_users.get("monthlyAmount") != null)
					if (dataVrs_users.get("monthlyAmount").toString().length() > 0)
						monthly_amount = "'"
								+ dataVrs_users.get("monthlyAmount").toString()
								+ "'";

				sql = "update vac_budgets set company_id=:companyId,"
						+ "account_id=:accountId,amount=:amount,monthly_amount="
						+ monthly_amount
						+ ","
						+ " created=:created,created_by=:createdBy,modified=:modified,modified_by=:modifiedBy,"
						+ " status=:status " + " where id=:id";

				sessionFactory
						.getCurrentSession()
						.createSQLQuery(sql)

						.setInteger("id", idTable)
						.setInteger("companyId", companyID)
						.setInteger("accountId", AccountID)
						.setBigDecimal(
								"amount",
								BigDecimal.valueOf(Double.valueOf(dataVrs_users
										.get("amount").toString())))

						.setTimestamp(
								"created",
								ssc.getTimeStamp(dataVrs_users.get("created")
										.toString()))
						.setInteger("createdBy", createdBY)
						.setTimestamp(
								"modified",
								ssc.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modifiedBy", ModifiedBy)
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
			cf.viewAlert(" error saveInTable_vac_budgets " + ex.getMessage());
		}
		return getBack;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String saveInTable_vac_postings(String data,
			String transaction_details__ID, int computer_id) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL",
					obj.get("id_vrs_sync_itemsLOCAL").toString());
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("vac_postings")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			if (action.equalsIgnoreCase("add")) {
				vac_postings vp = new vac_postings();

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					vp.setCompanyId(vc.getCompanyId());
				}

				if (transaction_details__ID.length() < 1) {
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"RefId_id_company_composite").toString())));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_companies vc = (vrs_companies) it.next();
						criteria = sessionFactory.getCurrentSession()
								.createCriteria(vac_transaction_details.class);
						criteria.add(Restrictions.eq(
								"id_composite",
								Integer.valueOf(dataVrs_users.get(
										"RefId_id_composite").toString())));
						criteria.add(Restrictions.eq("companyId",
								vc.getCompanyId()));
						it = criteria.list().iterator();
						if (it.hasNext()) {
							vac_transaction_details vctd = (vac_transaction_details) it
									.next();
							vp.setRefId(vctd.getId());
						}
					}
				} else {
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vac_transaction_details.class);
					criteria.add(Restrictions.eq("id",
							Integer.valueOf(transaction_details__ID)));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vac_transaction_details vctd = (vac_transaction_details) it
								.next();
						vp.setRefId(vctd.getId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"AccountId_id_company_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vac_chart_accounts.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"AccountId_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vac_chart_accounts vca = (vac_chart_accounts) it.next();
						vp.setAccountId(vca.getId());
					}
				}
				vp.setAmount(BigDecimal.valueOf(Double.valueOf(dataVrs_users
						.get("amount").toString())));
				vp.setCreated(ssc.getTimeStamp(dataVrs_users.get("created")
						.toString()));

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
						vp.setCreatedBy(vr.getRoleId());
					}
				}
				vp.setModified(ssc.getTimeStamp(dataVrs_users.get("modified")
						.toString()));

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
						vp.setModifiedBy(vr.getRoleId());
					}
				}
				vp.setStatus(Integer.valueOf(dataVrs_users.get("status")
						.toString()));
				try {
					sessionFactory.getCurrentSession().save(vp);
				} catch (Exception ex) {
					cf.viewAlert(" error saveInTable_vac_postings "
							+ ex.getMessage());
				}
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
						vac_postings.class);
				criteria.add(Restrictions.eq("id", vp.getId()));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vac_postings vpo = (vac_postings) it.next();
					objView.put("id_CompositeFromServer", vpo.getId_composite());
					getBack = objView.toString();
				}
			} else if (action.equalsIgnoreCase("edit")) {

				int companyID = -1, idTable = -1;
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
							.createCriteria(vac_postings.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vac_postings vpo = (vac_postings) it.next();
						idTable = vpo.getId();
					}
				}

				int refID = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"RefId_id_company_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vac_transaction_details.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"RefId_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vac_transaction_details vctd = (vac_transaction_details) it
								.next();
						refID = vctd.getId();
					}
				}

				int accountID = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"AccountId_id_company_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vac_chart_accounts.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"AccountId_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vac_chart_accounts vca = (vac_chart_accounts) it.next();
						accountID = vca.getId();
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

				String sql = "update vac_postings set "
						+ " companyId=:companyId,refId=:refId,accountId=:accountId,"
						+ " amount=:amount,modified=:modified,modifiedBy=:modifiedBy, "
						+ " status=:status where id=:id";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)

						.setInteger("companyId", companyID)
						.setInteger("id", idTable)
						.setInteger("refId", refID)
						.setInteger("accountId", accountID)
						.setBigDecimal(
								"amount",
								BigDecimal.valueOf(Double.valueOf(dataVrs_users
										.get("amount").toString())))
						.setTimestamp(
								"modified",
								ssc.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modifiedBy", modifiedBy)
						.setInteger(
								"status",
								Integer.valueOf(dataVrs_users.get("status")
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
					getBack = objView.toString();
				}

			}
		} catch (Exception ex) {
		}
		return getBack;
	}
	
	void addTransactionMetas(int trransaction_id, String key, String val,
			Timestamp created, Timestamp modified) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vac_transaction_metas.class);
		criteria.createAlias("metas_transaction", "transaction");
		criteria.add(Restrictions.eq("transaction.id", trransaction_id));
		criteria.add(Restrictions.eq("key", key));
		if (criteria.list().size() < 1) {
			vac_transaction_metas vtm = new vac_transaction_metas();
			vtm.setCreated(created);
			vtm.setKey(key);
			vtm.setModified(modified);
			vtm.setTransactionId(trransaction_id);
			vtm.setValue(val);
			sessionFactory.getCurrentSession().save(vtm);
		} else {
			String sql = "update vac_transaction_metas set value=:val,modified=:modified where key=:key and metas_transaction.id=:transactionId";
			sessionFactory.getCurrentSession().createQuery(sql)
					.setString("val", val).setTimestamp("modified", modified)
					.setInteger("transactionId", trransaction_id)
					.setString("key", key).executeUpdate();
		}

	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String saveInTable_vac_transactions(String data, String roleID,
			int computer_id) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL",
					obj.get("id_vrs_sync_itemsLOCAL").toString());
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("vac_transactions")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			if (action.equalsIgnoreCase("add")) {
				vac_transactions vtr = new vac_transactions();

				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					vtr.setCompanyId(vc.getCompanyId());
				}

				if (roleID.length() < 1) {
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"PartyId_id_compositeCompany").toString())));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_companies vc = (vrs_companies) it.next();
						criteria = sessionFactory.getCurrentSession()
								.createCriteria(vrs_roles.class);
						criteria.add(Restrictions.eq(
								"id_composite",
								Integer.valueOf(dataVrs_users.get(
										"PartyId_id_composite").toString())));
						criteria.add(Restrictions.eq("companyId",
								vc.getCompanyId()));
						it = criteria.list().iterator();
						if (it.hasNext()) {
							vrs_roles vr = (vrs_roles) it.next();
							vtr.setRoleName(new RoleName(vr.getRoleId()));
						}
					}
				} else {
					vtr.setRoleName(new RoleName(Integer.valueOf(roleID)));
				}
				vtr.setType(dataVrs_users.get("type").toString());
				try {
					vtr.setRefNumber(dataVrs_users.get("refNumber").toString());
				} catch (Exception ex) {
					vtr.setRefNumber(null);
				}

				vtr.setAmount(BigDecimal.valueOf(Double.valueOf(dataVrs_users
						.get("amount").toString())));
				vtr.setTrxDate(ssc.getTimeStamp(dataVrs_users.get("trxDate")
						.toString()));
				vtr.setCreated(ssc.getTimeStamp(dataVrs_users.get("created")
						.toString()));

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
						vtr.setCreatedBy(vr.getRoleId());
					}
				}

				vtr.setModified(ssc.getTimeStamp(dataVrs_users.get("modified")
						.toString()));

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
						vtr.setModifiedBy(vr.getRoleId());
					}
				}

				vtr.setStatus(Integer.valueOf(dataVrs_users.get("status")
						.toString()));
				sessionFactory.getCurrentSession().save(vtr);
				int idTable = vtr.getId();
				objView.put("id_compositesaved", String.valueOf(idTable));

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
							"vac_transaction_metas").toString());
					JSONObject objData_meta1 = null;
					int i = 0;
					for (i = 0; i < objData_meta.length(); i++) {
						objData_meta1 = objData_meta.getJSONObject(i);
						vac_transaction_metas vtm = new vac_transaction_metas();
						vtm.setCreated(ssc.getTimeStamp(objData_meta1
								.get("created").toString()));
						vtm.setKey(objData_meta1.get("metaKey").toString());
						vtm.setModified(ssc.getTimeStamp(objData_meta1.get(
								"modified").toString()));
						vtm.setTransactionId(idTable);
						vtm.setValue(objData_meta1.get("metaValue").toString());
						sessionFactory.getCurrentSession().save(vtm);
					}
					sessionFactory.getCurrentSession().clear();
				} catch (Exception ex) {
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vac_transactions.class);
				criteria.add(Restrictions.eq("id", vtr.getId()));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vac_transactions vtrc = (vac_transactions) it.next();
					objView.put("id_CompositeFromServer",
							vtrc.getId_composite());
					getBack = objView.toString();
				}

			} else if (action.equalsIgnoreCase("edit")) {

				int companyID = -1, idTable = -1;
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
							.createCriteria(vac_transactions.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vac_transactions vtrs = (vac_transactions) it.next();
						idTable = vtrs.getId();
					}
				}

				int partyID = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"PartyId_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"PartyId_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						partyID = vr.getRoleId();

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

				String refNumber = null;
				try {
					refNumber = dataVrs_users.get("refNumber").toString();
				} catch (Exception ex) {
				}

				String sql = "update vac_transactions a set a.companyId=:companyId,"
						+ " a.roleName.id=:partyId,a.refNumber=:refNumber,a.type=:type,a.amount=:amount,"
						+ " a.trxDate=:trxDate,a.modified=:modified,a.modifiedBy=:modifiedBy,a.status=:status "
						+ " where a.id=:id";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)

						.setInteger("companyId", companyID)
						.setInteger("id", idTable)
						.setInteger("partyId", partyID)
						.setString("refNumber", refNumber)
						.setString("type", dataVrs_users.get("type").toString())
						.setBigDecimal(
								"amount",
								BigDecimal.valueOf(Double.valueOf(dataVrs_users
										.get("amount").toString())))
						.setTimestamp(
								"trxDate",
								ssc.getTimeStamp(dataVrs_users.get("trxDate")
										.toString()))

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
				}

				try {
					JSONArray objData_meta = new JSONArray(obj.get(
							"vac_transaction_metas").toString());
					JSONObject objData_meta1 = null;
					int i = 0;
					for (i = 0; i < objData_meta.length(); i++) {
						objData_meta1 = objData_meta.getJSONObject(i);
						addTransactionMetas(idTable,
								objData_meta1.get("metaKey").toString(),
								objData_meta1.get("metaValue").toString(),
								ssc.getTimeStamp(objData_meta1.get("created")
										.toString()),
										ssc.getTimeStamp(objData_meta1.get("modified")
										.toString()));
					}
				} catch (Exception ex) {
				}
				sessionFactory.getCurrentSession().clear();
				getBack = objView.toString();
			}
		} catch (Exception ex) {
			cf.viewAlert(" error saveInTable_vac_transactions "
					+ ex.getMessage());
			getBack += " error saveInTable_vac_transactions " + ex.getMessage();
		}
		return getBack;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String saveInTable_vac_transaction_details(String data,
			String transaction_ID, int computer_id) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL",
					obj.get("id_vrs_sync_itemsLOCAL").toString());
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj
					.get("vac_transaction_details").toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			if (action.equalsIgnoreCase("add")) {
				vac_transaction_details vtd = new vac_transaction_details();
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					vtd.setCompanyId(vc.getCompanyId());
				}

				if (transaction_ID.length() < 1) {
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_companies.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get(
									"TransactionId_company_id_composite")
									.toString())));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_companies vc = (vrs_companies) it.next();
						criteria = sessionFactory.getCurrentSession()
								.createCriteria(vac_transactions.class);
						criteria.add(Restrictions.eq("companyId",
								vc.getCompanyId()));
						criteria.add(Restrictions.eq("id_composite", Integer
								.valueOf(dataVrs_users.get(
										"TransactionId_id_composite")
										.toString())));
						it = criteria.list().iterator();
						if (it.hasNext()) {
							vac_transactions vtr = (vac_transactions) it.next();
							vtd.setTransactions(vtr);
						}
					}
				} else {
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vac_transactions.class);
					criteria.add(Restrictions.eq("id",
							Integer.valueOf(transaction_ID)));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vac_transactions vtr = (vac_transactions) it.next();
						vtd.setTransactions(vtr);
					}
				}

				vtd.setTitle(dataVrs_users.get("title").toString());
				vtd.setItemId(Integer.valueOf(dataVrs_users.get("itemId")
						.toString()));
				vtd.setQuantity(Integer.valueOf(dataVrs_users.get("quantity")
						.toString()));
				vtd.setUnitPrice(BigDecimal.valueOf(Double
						.valueOf(dataVrs_users.get("unitPrice").toString())));
				vtd.setDiscount(BigDecimal.valueOf(Double.valueOf(dataVrs_users
						.get("discount").toString())));
				vtd.setRemaining(BigDecimal.valueOf(Double
						.valueOf(dataVrs_users.get("remaining").toString())));
				if (dataVrs_users.get("refId").equals("null"))
					vtd.setRefId(null);
				else
					vtd.setRefId(Integer.valueOf(dataVrs_users.get("refId")
							.toString()));
				sessionFactory.getCurrentSession().save(vtd);
				objView.put("id_compositesaved", vtd.getId());

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
						vac_transaction_details.class);
				criteria.add(Restrictions.eq("id", vtd.getId()));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vac_transaction_details vtrd = (vac_transaction_details) it
							.next();
					objView.put("id_CompositeFromServer",
							vtrd.getId_composite());
					getBack = objView.toString();
				}
			} else if (action.equalsIgnoreCase("edit")) {
				int companyID = -1, idTable = -1;
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
							.createCriteria(vac_transaction_details.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vac_transaction_details vtde = (vac_transaction_details) it
								.next();
						idTable = vtde.getId();

					}
				}

				int transactionID = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get(
								"TransactionId_company_id_composite")
								.toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vac_transactions.class);
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"TransactionId_id_composite").toString())));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vac_transactions vtr = (vac_transactions) it.next();
						transactionID = vtr.getId();
					}
				}

				int refId = 0;

				if (!dataVrs_users.get("refId").equals("null"))
					refId = Integer.valueOf(dataVrs_users.get("refId")
							.toString());

				String sql = "update vac_transaction_details a set "
						+ " a.companyId=:companyId,a.transactions.id=:transactionId,a.title=:title,"
						+ " a.itemId=:itemId,a.quantity=:quantity,"
						+ " a.unitPrice=:unitPrice,a.discount=:discount,a.remaining=:remaining,a.refId=:refId "
						+ "  where a.id=:id";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)

						.setInteger("companyId", companyID)
						.setInteger("id", idTable)
						.setInteger("transactionId", transactionID)
						.setString("title",
								dataVrs_users.get("title").toString())
						.setInteger(
								"itemId",
								Integer.valueOf(dataVrs_users.get("itemId")
										.toString()))
						.setInteger(
								"quantity",
								Integer.valueOf(dataVrs_users.get("quantity")
										.toString()))
						.setBigDecimal(
								"unitPrice",
								BigDecimal.valueOf(Double.valueOf(dataVrs_users
										.get("unitPrice").toString())))
						.setBigDecimal(
								"discount",
								BigDecimal.valueOf(Double.valueOf(dataVrs_users
										.get("discount").toString())))
						.setBigDecimal(
								"remaining",
								BigDecimal.valueOf(Double.valueOf(dataVrs_users
										.get("remaining").toString())))
						.setInteger("refId", refId)

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
			cf.viewAlert(" error saveInTable_vac_transaction_details "
					+ ex.getMessage());
		}
		return getBack;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String receiveUploadData(String data, int computer_id) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			String tableName = obj.get("tableName").toString();
			cf.viewAlert("receiveUploadData tableName = " + tableName);
			if (tableName.equalsIgnoreCase("vac_transaction_details")) {
				getBack = saveInTable_vac_transaction_details(data, "",
						computer_id);
			} else if (tableName.equalsIgnoreCase("vac_postings")) {
				getBack = saveInTable_vac_postings(data, "", computer_id);
			} else if (tableName.equalsIgnoreCase("vac_budgets")) {
				getBack = saveInTable_vac_budgets(data, computer_id);
			} else if (tableName.equalsIgnoreCase("vac_chart_account_balances")) {
				getBack = saveInTable_vac_chart_account_balances(data,
						computer_id);
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
			} else if (tableName.equalsIgnoreCase("vac_transactions")) {
				getBack = saveInTable_vac_transactions(data, "", computer_id);
			} else if (tableName.equalsIgnoreCase("vac_transaction_details")) {
				getBack = saveInTable_vac_transaction_details(data, "",
						computer_id);
			} else if (tableName.equalsIgnoreCase("vac_postings")) {
				getBack = saveInTable_vac_postings(data, "", computer_id);
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
			cf.viewAlert("getDAtaDownload table_name = " + table_name);
			id_table = Integer.valueOf(String.valueOf(obj[7].toString()));
			if (table_name.equals("vac_chart_accounts")) {
				getBack = getvac_chart_accounts_DAtaDownload(id_table,
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name.equals("vac_transactions")) {
				getBack = getvac_transactions_DAtaDownload(id_table,
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
				if (obj[4].toString().equalsIgnoreCase("add")) {
					getBack = sacc.getVac_transactionsforDownload(getBack,
							sqlstudentLimit, obj, id_table, computer_id);
				}
			} else if (table_name.equals("vac_transaction_details")) {
				getBack = getvac_transaction_details_DAtaDownload(id_table,
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name.equals("vac_postings")) {
				getBack = getvac_postings_DAtaDownload(id_table,
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name.equalsIgnoreCase("vac_budgets")) {
				getBack = getvac_budgets_DAtaDownload(id_table,
						obj[4].toString(), Integer.valueOf(obj[0].toString()));
			} else if (table_name
					.equalsIgnoreCase("vac_chart_account_balances")) {
				getBack = getvac_chart_account_balances_DAtaDownload(id_table,
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
			}
		}
		cf.viewAlert("getDAtaDownload getBack = " + getBack);
		return getBack;
	}
	

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void saveInTable_vac_transactionsDownload(String data) {
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("vac_transactions")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			if (action.equalsIgnoreCase("add")) {
				vac_transactions vtr = new vac_transactions();

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
					vtr.setCompanyId(company_id);
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"PartyId_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"PartyId_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						vtr.setRoleName(new RoleName(vr.getRoleId()));
					}
				}
				vtr.setType(dataVrs_users.get("type").toString());
				try {
					vtr.setRefNumber(dataVrs_users.get("refNumber").toString());
				} catch (Exception ex) {
					vtr.setRefNumber(null);
				}
				vtr.setAmount(BigDecimal.valueOf(Double.valueOf(dataVrs_users
						.get("amount").toString())));
				vtr.setTrxDate(ssc.getTimeStamp(dataVrs_users.get("trxDate")
						.toString()));
				vtr.setCreated(ssc.getTimeStamp(dataVrs_users.get("created")
						.toString()));

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
						vtr.setCreatedBy(vr.getRoleId());
					}
				}

				vtr.setModified(ssc.getTimeStamp(dataVrs_users.get("modified")
						.toString()));

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
						vtr.setModifiedBy(vr.getRoleId());
					}
				}

				vtr.setStatus(Integer.valueOf(dataVrs_users.get("status")
						.toString()));
				int id_composite = Integer.valueOf(dataVrs_users.get(
						"id_composite").toString());
				vtr.setId_composite(id_composite);

				String sql = "ALTER TABLE vac_transactions DISABLE TRIGGER insert_vac_transactionst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();
				sVRS.changeIdComposite_vrs_roles(id_composite, company_id,
						"vac_transactions");
				sessionFactory.getCurrentSession().save(vtr);
				int idTable = vtr.getId();
				sessionFactory.getCurrentSession().clear();
				sql = "ALTER TABLE vac_transactions ENABLE TRIGGER insert_vac_transactionst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				try {
					JSONArray objData_meta = new JSONArray(obj.get(
							"vac_transaction_metas").toString());
					JSONObject objData_meta1 = null;
					int i = 0;
					for (i = 0; i < objData_meta.length(); i++) {
						objData_meta1 = objData_meta.getJSONObject(i);
						vac_transaction_metas vtm = new vac_transaction_metas();
						vtm.setCreated(ssc.getTimeStamp(objData_meta1
								.get("created").toString()));
						vtm.setKey(objData_meta1.get("metaKey").toString());
						vtm.setModified(ssc.getTimeStamp(objData_meta1.get(
								"modified").toString()));
						vtm.setTransactionId(idTable);
						vtm.setValue(objData_meta1.get("metaValue").toString());
						sessionFactory.getCurrentSession().save(vtm);
					}
				} catch (Exception ex) {
				}
			} else if (action.equalsIgnoreCase("edit")) {
				int companyID = -1, idTable = -1;
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
							.createCriteria(vac_transactions.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vac_transactions vtrs = (vac_transactions) it.next();
						idTable = vtrs.getId();
					}
				}

				int partyID = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"PartyId_id_compositeCompany").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"PartyId_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						partyID = vr.getRoleId();

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

				String refNumber = null;
				try {
					refNumber = dataVrs_users.get("refNumber").toString();
				} catch (Exception ex) {
				}

				String sql = "ALTER TABLE vac_transactions DISABLE TRIGGER insert_vac_transactionst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sql = "update vac_transactions a set a.companyId=:companyId,"
						+ " a.roleName.id=:partyId,a.refNumber=:refNumber,a.type=:type,a.amount=:amount,"
						+ " a.trxDate=:trxDate,a.modified=:modified,a.modifiedBy=:modifiedBy,a.status=:status "
						+ " where a.id=:id";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)

						.setInteger("companyId", companyID)
						.setInteger("id", idTable)
						.setInteger("partyId", partyID)
						.setString("refNumber", refNumber)
						.setString("type", dataVrs_users.get("type").toString())
						.setBigDecimal(
								"amount",
								BigDecimal.valueOf(Double.valueOf(dataVrs_users
										.get("amount").toString())))
						.setTimestamp(
								"trxDate",
								ssc.getTimeStamp(dataVrs_users.get("trxDate")
										.toString()))

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
				sessionFactory.getCurrentSession().clear();
				sql = "ALTER TABLE vac_transactions ENABLE TRIGGER insert_vac_transactionst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				try {
					JSONArray objData_meta = new JSONArray(obj.get(
							"vac_transaction_metas").toString());
					JSONObject objData_meta1 = null;
					int i = 0;
					for (i = 0; i < objData_meta.length(); i++) {
						objData_meta1 = objData_meta.getJSONObject(i);
						addTransactionMetas(idTable,
								objData_meta1.get("metaKey").toString(),
								objData_meta1.get("metaValue").toString(),
								ssc.getTimeStamp(objData_meta1.get("created")
										.toString()),
										ssc.getTimeStamp(objData_meta1.get("modified")
										.toString()));
					}
				} catch (Exception ex) {
				}
			}
		} catch (Exception ex) {
			cf.viewAlert(" error saveInTable_vac_transactionsDownload "
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
			if (tableNAme.equalsIgnoreCase("vac_transactions")) {
				saveInTable_vac_transactionsDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("vac_transaction_details")) {
				saveInTable_vac_transaction_detailsDownload(data);
				getBack = true;
			} else if (tableNAme.equalsIgnoreCase("vac_postings")) {
				saveInTable_vac_postingsDownload(data);
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
			}
		} catch (Exception ex) {
			cf.viewAlert(" error saveDAtaFromDownload " + ex.getMessage());
		}
		return getBack;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String saveTransactionsWhenDownload(String data) {
		String getBack = "";
		try {
			int i = 0;
			JSONObject obj = new JSONObject(data);
			obj = new JSONObject(obj.get("dataThis").toString());

			boolean looping = true;
			while (looping) {
				try {
					saveInTable_vac_transactionsDownload(obj.get(
							"vac_transactions" + i).toString());
					i++;
					saveInTable_vac_transaction_detailsDownload(obj.get(
							"vac_transaction_details" + i).toString());
					i++;
					saveInTable_vac_postingsDownload(obj.get(
							"vac_postings" + i).toString());
					i++;
					saveInTable_vac_postingsDownload(obj.get(
							"vac_postings" + i).toString());
					i++;
				} catch (Exception ex) {
					looping = false;
				}
			}

			try {
				String candidateSTR = obj.get("ved_candidates" + i).toString();
				JSONObject objCandiate = new JSONObject(candidateSTR);
				ssa.saveInTable_ved_candidatesDownload(candidateSTR);
			} catch (Exception ex) {
			}

		} catch (Exception ex) {
			cf.viewAlert("error saveTransactionsWhenDownload "
					+ ex.getMessage());
		}
		return getBack;
	}
	
	
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void saveInTable_vac_postingsDownload(String data) {
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("vac_postings")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			if (action.equalsIgnoreCase("add")) {
				vac_postings vp = new vac_postings();

				int company_idThis = -1;
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					company_idThis = vc.getCompanyId();
					vp.setCompanyId(company_idThis);
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"RefId_id_company_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vac_transaction_details.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"RefId_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vac_transaction_details vctd = (vac_transaction_details) it
								.next();
						vp.setRefId(vctd.getId());
					}
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"AccountId_id_company_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vac_chart_accounts.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"AccountId_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vac_chart_accounts vca = (vac_chart_accounts) it.next();
						vp.setAccountId(vca.getId());
					}
				}
				vp.setAmount(BigDecimal.valueOf(Double.valueOf(dataVrs_users
						.get("amount").toString())));
				vp.setCreated(ssc.getTimeStamp(dataVrs_users.get("created")
						.toString()));

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
						vp.setCreatedBy(vr.getRoleId());
					}
				}

				vp.setModified(ssc.getTimeStamp(dataVrs_users.get("modified")
						.toString()));

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
						vp.setModifiedBy(vr.getRoleId());
					}
				}
				vp.setStatus(Integer.valueOf(dataVrs_users.get("status")
						.toString()));

				int id_compositethis = Integer.valueOf(dataVrs_users.get(
						"id_composite").toString());
				vp.setId_composite(id_compositethis);

				String sql = "ALTER TABLE vac_postings DISABLE TRIGGER insert_vac_postingst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sVRS.changeIdComposite_vrs_roles(id_compositethis,
						company_idThis, "vac_postings");
				sessionFactory.getCurrentSession().save(vp);

				sessionFactory.getCurrentSession().clear();
				sql = "ALTER TABLE vac_postings ENABLE TRIGGER insert_vac_postingst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();
			} else if (action.equalsIgnoreCase("edit")) {
				int companyID = -1, idTable = -1;
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
							.createCriteria(vac_postings.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vac_postings vpo = (vac_postings) it.next();
						idTable = vpo.getId();
					}
				}

				int refID = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"RefId_id_company_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vac_transaction_details.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"RefId_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vac_transaction_details vctd = (vac_transaction_details) it
								.next();
						refID = vctd.getId();
					}
				}

				int accountID = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"AccountId_id_company_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vac_chart_accounts.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"AccountId_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vac_chart_accounts vca = (vac_chart_accounts) it.next();
						accountID = vca.getId();
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

				String sql = "ALTER TABLE vac_postings DISABLE TRIGGER insert_vac_postingst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sql = "update vac_postings set "
						+ " companyId=:companyId,refId=:refId,accountId=:accountId,"
						+ " amount=:amount,modified=:modified,modifiedBy=:modifiedBy, "
						+ " status=:status " + "  where id=:id";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)

						.setInteger("companyId", companyID)
						.setInteger("id", idTable)
						.setInteger("refId", refID)
						.setInteger("accountId", accountID)
						.setBigDecimal(
								"amount",
								BigDecimal.valueOf(Double.valueOf(dataVrs_users
										.get("amount").toString())))
						.setTimestamp(
								"modified",
								ssc.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modifiedBy", modifiedBy)
						.setInteger(
								"status",
								Integer.valueOf(dataVrs_users.get("status")
										.toString())).executeUpdate();
				sql = "ALTER TABLE vac_postings ENABLE TRIGGER insert_vac_postingst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

			}
		} catch (Exception ex) {
			cf.viewAlert(" error saveInTable_vac_postingsDownload "
					+ ex.getMessage());
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void saveInTable_vac_transaction_detailsDownload(String data) {
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj
					.get("vac_transaction_details").toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			if (action.equalsIgnoreCase("add")) {
				vac_transaction_details vtd = new vac_transaction_details();

				int company_idThis = -1;
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					company_idThis = vc.getCompanyId();
					vtd.setCompanyId(company_idThis);
				}

				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get(
								"TransactionId_company_id_composite")
								.toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vac_transactions.class);
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"TransactionId_id_composite").toString())));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vac_transactions vtr = (vac_transactions) it.next();
						vtd.setTransactions(vtr);
					}
				}

				vtd.setTitle(dataVrs_users.get("title").toString());
				vtd.setItemId(Integer.valueOf(dataVrs_users.get("itemId")
						.toString()));
				vtd.setQuantity(Integer.valueOf(dataVrs_users.get("quantity")
						.toString()));
				vtd.setUnitPrice(BigDecimal.valueOf(Double
						.valueOf(dataVrs_users.get("unitPrice").toString())));
				vtd.setDiscount(BigDecimal.valueOf(Double.valueOf(dataVrs_users
						.get("discount").toString())));
				vtd.setRemaining(BigDecimal.valueOf(Double
						.valueOf(dataVrs_users.get("remaining").toString())));
				if (dataVrs_users.get("refId").equals("null"))
					vtd.setRefId(null);
				else
					vtd.setRefId(Integer.valueOf(dataVrs_users.get("refId")
							.toString()));
				int id_compositeThis = Integer.valueOf(dataVrs_users.get(
						"id_composite").toString());
				vtd.setId_composite(id_compositeThis);

				String sql = "ALTER TABLE vac_transaction_details DISABLE TRIGGER insert_vac_transaction_detailst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();
				sVRS.changeIdComposite_vrs_roles(id_compositeThis,
						company_idThis, "vac_transaction_details");
				sessionFactory.getCurrentSession().save(vtd);
				sessionFactory.getCurrentSession().clear();
				sql = "ALTER TABLE vac_transaction_details ENABLE TRIGGER insert_vac_transaction_detailst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();
			} else if (action.equalsIgnoreCase("edit")) {

				int companyID = -1, idTable = -1;
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
							.createCriteria(vac_transaction_details.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vac_transaction_details vtde = (vac_transaction_details) it
								.next();
						idTable = vtde.getId();

					}
				}

				int transactionID = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get(
								"TransactionId_company_id_composite")
								.toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vac_transactions.class);
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"TransactionId_id_composite").toString())));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vac_transactions vtr = (vac_transactions) it.next();
						transactionID = vtr.getId();
					}
				}

				int refId = 0;

				if (!dataVrs_users.get("refId").equals("null"))
					refId = Integer.valueOf(dataVrs_users.get("refId")
							.toString());

				String sql = "ALTER TABLE vac_transaction_details DISABLE TRIGGER insert_vac_transaction_detailst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();
				sql = "update vac_transaction_details a set "
						+ " a.companyId=:companyId,a.transactions.id=:transactionId,a.title=:title,"
						+ " a.itemId=:itemId,a.quantity=:quantity,"
						+ " a.unitPrice=:unitPrice,a.discount=:discount,a.remaining=:remaining,a.refId=:refId "
						+ "  where a.id=:id";
				sessionFactory
						.getCurrentSession()
						.createQuery(sql)

						.setInteger("companyId", companyID)
						.setInteger("id", idTable)
						.setInteger("transactionId", transactionID)
						.setString("title",
								dataVrs_users.get("title").toString())
						.setInteger(
								"itemId",
								Integer.valueOf(dataVrs_users.get("itemId")
										.toString()))
						.setInteger(
								"quantity",
								Integer.valueOf(dataVrs_users.get("quantity")
										.toString()))
						.setBigDecimal(
								"unitPrice",
								BigDecimal.valueOf(Double.valueOf(dataVrs_users
										.get("unitPrice").toString())))
						.setBigDecimal(
								"discount",
								BigDecimal.valueOf(Double.valueOf(dataVrs_users
										.get("discount").toString())))
						.setBigDecimal(
								"remaining",
								BigDecimal.valueOf(Double.valueOf(dataVrs_users
										.get("remaining").toString())))
						.setInteger("refId", refId)

						.executeUpdate();
				sql = "ALTER TABLE vac_transaction_details ENABLE TRIGGER insert_vac_transaction_detailst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();
			}

		} catch (Exception ex) {
			cf.viewAlert(" error saveInTable_vac_transaction_detailsDownload "
					+ ex.getMessage());
		}
	}
	
	public void saveInTable_vac_budgetsDownload(String data) {
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("vac_budgets").toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			if (action.equalsIgnoreCase("add")) {

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

				int accountID = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"AccountId_id_company_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vac_chart_accounts.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"AccountId_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vac_chart_accounts vca = (vac_chart_accounts) it.next();
						accountID = vca.getId();
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
						createdBY = vr.getRoleId();
					}
				}

				int ModifiedBy = -1;
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
						ModifiedBy = vr.getRoleId();
					}
				}

				String monthly_amount = "null";
				if (dataVrs_users.get("monthlyAmount") != null)
					if (dataVrs_users.get("monthlyAmount").toString().length() > 0)
						monthly_amount = "'"
								+ dataVrs_users.get("monthlyAmount").toString()
								+ "'";

				int idTabel = -1;
				String sql = "SELECT nextval('vac_budgets_id_seq')";
				it = sessionFactory.getCurrentSession().createSQLQuery(sql)
						.list().iterator();
				if (it.hasNext()) {
					idTabel = Integer.valueOf(it.next().toString());
				}

				sql = "ALTER TABLE vac_budgets DISABLE TRIGGER insert_vac_budgetst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sql = "insert into vac_budgets (id,company_id,account_id,amount,monthly_amount,created,"
						+ "created_by,modified,modified_by,status,id_composite) values (:id,"
						+ ":company_id,:account_id,:amount,"
						+ monthly_amount
						+ ",:created,"
						+ ":created_by,:modified,:modified_by,:status,:id_composite)";

				sessionFactory
						.getCurrentSession()
						.createSQLQuery(sql)
						.setInteger("id", idTabel)
						.setInteger("company_id", companyID)
						.setInteger("account_id", accountID)
						.setBigDecimal(
								"amount",
								BigDecimal.valueOf(Double.valueOf(dataVrs_users
										.get("amount").toString())))
						.setTimestamp(
								"created",
								ssc.getTimeStamp(dataVrs_users.get("created")
										.toString()))
						.setInteger("created_by", createdBY)
						.setTimestamp(
								"modified",
								ssc.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modified_by", ModifiedBy)
						.setInteger(
								"status",
								Integer.valueOf(dataVrs_users.get("status")
										.toString()))
						.setInteger(
								"id_composite",
								Integer.valueOf(dataVrs_users.get(
										"id_composite").toString()))

						.executeUpdate();

				sql = "ALTER TABLE vac_budgets ENABLE TRIGGER insert_vac_budgetst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

			} else if (action.equalsIgnoreCase("edit")) {
				int companyID = -1, idTable = -1;
				String sql = "";
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					companyID = vc.getCompanyId();
					sql = "select id from vac_budgets where company_id = :company_id and id_composite=:id_composite";
					it = sessionFactory
							.getCurrentSession()
							.createSQLQuery(sql)
							.setInteger("company_id", companyID)
							.setInteger(
									"id_composite",
									Integer.valueOf(dataVrs_users.get(
											"id_composite").toString())).list()
							.iterator();
					if (it.hasNext()) {
						idTable = Integer.valueOf(it.next().toString());
					}
				}

				int AccountID = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq(
						"id_composite",
						Integer.valueOf(dataVrs_users.get(
								"AccountId_id_company_composite").toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vac_chart_accounts.class);
					criteria.add(Restrictions.eq(
							"id_composite",
							Integer.valueOf(dataVrs_users.get(
									"AccountId_id_composite").toString())));
					criteria.add(Restrictions.eq("companyId", vc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vac_chart_accounts vca = (vac_chart_accounts) it.next();
						AccountID = vca.getId();
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
						createdBY = vr.getRoleId();
					}
				}

				int ModifiedBy = -1;
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
						ModifiedBy = vr.getRoleId();
					}
				}
				String monthly_amount = "null";
				if (dataVrs_users.get("monthlyAmount") != null)
					if (dataVrs_users.get("monthlyAmount").toString().length() > 0)
						monthly_amount = "'"
								+ dataVrs_users.get("monthlyAmount").toString()
								+ "'";

				sql = "ALTER TABLE vac_budgets DISABLE TRIGGER insert_vac_budgetst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				sql = "update vac_budgets set company_id=:companyId,"
						+ "account_id=:accountId,amount=:amount,monthly_amount="
						+ monthly_amount
						+ ","
						+ " created=:created,created_by=:createdBy,modified=:modified,modified_by=:modifiedBy,"
						+ " status=:status " + " where id=:id";

				sessionFactory
						.getCurrentSession()
						.createSQLQuery(sql)

						.setInteger("id", idTable)
						.setInteger("companyId", companyID)
						.setInteger("accountId", AccountID)
						.setBigDecimal(
								"amount",
								BigDecimal.valueOf(Double.valueOf(dataVrs_users
										.get("amount").toString())))

						.setTimestamp(
								"created",
								ssc.getTimeStamp(dataVrs_users.get("created")
										.toString()))
						.setInteger("createdBy", createdBY)
						.setTimestamp(
								"modified",
								ssc.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modifiedBy", ModifiedBy)
						.setInteger(
								"status",
								Integer.valueOf(dataVrs_users.get("status")
										.toString()))

						.executeUpdate();
				sql = "ALTER TABLE vac_budgets ENABLE TRIGGER insert_vac_budgetst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();
			}
		} catch (Exception ex) {
			cf.viewAlert(" error saveInTable_vac_budgetsDownload "
					+ ex.getMessage());
		}
	}
}
