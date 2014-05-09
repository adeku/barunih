package verse.sync.service;

import java.math.BigDecimal;
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

import verse.accounting.model.vac_chart_accounts;
import verse.accounting.model.vac_transaction_metas;
import verse.commonClass.CommonFunction;
import verse.model.vrs_companies;
import verse.model.vrs_company_metas;
import verse.model.vrs_file_library;
import verse.model.vrs_roles;
import verse.sync.model.vrs_sync_computerlist;
import verse.sync.model.vrs_sync_items;

@Service("serviceSyncAcademic_Accounting")
public class serviceSyncImplAcademic_Accounting implements
		serviceSyncAcademic_Accounting {

	@Autowired
	private serviceSync svs;
	CommonFunction cf = new CommonFunction();

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private serviceSyncAccounting ssac;
	
	@Autowired
	private serviceSyncAcademic ssa;
	
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
					ssac.saveInTable_vac_transactionsDownload(obj.get(
							"vac_transactions" + i).toString());
					i++;
					ssac.saveInTable_vac_transaction_detailsDownload(obj.get(
							"vac_transaction_details" + i).toString());
					i++;
					ssac.saveInTable_vac_postingsDownload(obj.get(
							"vac_postings" + i).toString());
					i++;
					ssac.saveInTable_vac_postingsDownload(obj.get(
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
			cf.viewAlert(" error saveTransactionsWhenDownload "+ex.getMessage());
		}
		return getBack;
	}	
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String saveStudents(String data) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			obj = new JSONObject(obj.get("dataThis").toString());
			int i = 0;
			svs.saveInvrs_UsersFromdownload(obj.get("vrs_users" + i).toString());
			i++;
			svs.saveInTable_vrs_rolesDownload(obj.get("vrs_roles" + i)
					.toString());

			boolean notError = true;
			try {
				i++;
				ssa.saveInTable_ved_candidatesDownload(obj.get(
						"ved_candidates" + i).toString());
				i++;
				ssac.saveInTable_vac_transactionsDownload(obj.get(
						"vac_transactions" + i).toString());
				i++;
				ssac.saveInTable_vac_transaction_detailsDownload(obj.get(
						"vac_transaction_details" + i).toString());
				i++;
				ssac.saveInTable_vac_postingsDownload(obj
						.get("vac_postings" + i).toString());
				i++;
				ssac.saveInTable_vac_postingsDownload(obj
						.get("vac_postings" + i).toString());
				i++;
				ssa.saveInTable_ved_studentsDownload(obj
						.get("ved_students" + i).toString());

				boolean looping = true;
				while (looping) {
					try {
						i++;
						ssac.saveInTable_vac_transactionsDownload(obj.get(
								"vac_transactions" + i).toString());
						i++;
						ssac.saveInTable_vac_transaction_detailsDownload(obj
								.get("vac_transaction_details" + i).toString());
						i++;
						ssac.saveInTable_vac_postingsDownload(obj.get(
								"vac_postings" + i).toString());
						i++;
						ssac.saveInTable_vac_postingsDownload(obj.get(
								"vac_postings" + i).toString());
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
						svs.saveInvrs_UsersFromdownload(obj
								.get("vrs_users" + i).toString());
						i++;
						svs.saveInTable_vrs_rolesDownload(obj.get(
								"vrs_roles" + i).toString());
						i++;
					} catch (Exception ex) {
						looping = false;
					}
				}
			}
		} catch (Exception ex) {
			cf.viewAlert("error saveStudents " + ex.getMessage());
		}
		return getBack;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String getVed_students(String getBack) {
		Criteria criteriaAdd = sessionFactory.getCurrentSession()
				.createCriteria(vrs_sync_items.class);
		criteriaAdd.add(Restrictions.eq("status", false));
		criteriaAdd.add(Restrictions.eq("action", "add"));
		criteriaAdd.addOrder(Order.asc("id"));
		criteriaAdd.setMaxResults(57);
		Iterator it = criteriaAdd.list().iterator();
		try {
			JSONObject objGetback = new JSONObject(getBack);
			JSONObject objStudent = new JSONObject();
			if (it.hasNext()) {
				vrs_sync_items vsi = (vrs_sync_items) it.next();
				if (vsi.getTable_name().equalsIgnoreCase("vrs_users")) {
					int i = 0;
					if (it.hasNext()) {
						vsi = (vrs_sync_items) it.next();
						if (vsi.getTable_name().equalsIgnoreCase("vrs_roles")) {
							objStudent.put("vrs_users" + i, objGetback);
							i++;
							objGetback = new JSONObject(svs.getvrs_roles_DAta(
									vsi.getId_tableInteger(), vsi.getAction(),
									vsi.getId()));
							if ((objStudent.toString().length() + objGetback
									.toString().length()) <= limitString) {
								objStudent.put(vsi.getTable_name() + i,
										objGetback);
								if (it.hasNext()) {
									vsi = (vrs_sync_items) it.next();
									if (vsi.getTable_name().equalsIgnoreCase(
											"ved_candidates")) {
										i++;
										objGetback = new JSONObject(
												ssa.getved_candidates_DAta(
														vsi.getId_table(),
														vsi.getAction(),
														vsi.getId()));
										if ((objStudent.toString().length() + objGetback
												.toString().length()) <= limitString) {
											objStudent.put(vsi.getTable_name()
													+ i, objGetback);
											if (it.hasNext()) {
												vsi = (vrs_sync_items) it
														.next();
												if (vsi.getTable_name()
														.equalsIgnoreCase(
																"vac_transactions")) {
													i++;
													objGetback = new JSONObject(
															ssac.getvac_transactions_DAta(
																	vsi.getId_tableInteger(),
																	vsi.getAction(),
																	vsi.getId()));
													if ((objStudent.toString()
															.length() + objGetback
															.toString()
															.length()) <= limitString) {
														objStudent
																.put(vsi.getTable_name()
																		+ i,
																		objGetback);
														if (it.hasNext()) {
															vsi = (vrs_sync_items) it
																	.next();
															if (vsi.getTable_name()
																	.equalsIgnoreCase(
																			"vac_transaction_details")) {
																i++;
																objGetback = new JSONObject(
																		ssac.getvac_transaction_details_DAta(
																				vsi.getId_tableInteger(),
																				vsi.getAction(),
																				vsi.getId()));
																if ((objStudent
																		.toString()
																		.length() + objGetback
																		.toString()
																		.length()) <= limitString) {
																	objStudent
																			.put(vsi.getTable_name()
																					+ i,
																					objGetback);
																	if (it.hasNext()) {
																		vsi = (vrs_sync_items) it
																				.next();
																		if (vsi.getTable_name()
																				.equalsIgnoreCase(
																						"vac_postings")) {
																			i++;
																			objGetback = new JSONObject(
																					ssac.getvac_postings_DAta(
																							vsi.getId_tableInteger(),
																							vsi.getAction(),
																							vsi.getId()));
																			if ((objStudent
																					.toString()
																					.length() + objGetback
																					.toString()
																					.length()) <= limitString) {
																				objStudent
																						.put(vsi.getTable_name()
																								+ i,
																								objGetback);
																				if (it.hasNext()) {
																					vsi = (vrs_sync_items) it
																							.next();
																					if (vsi.getTable_name()
																							.equalsIgnoreCase(
																									"vac_postings")) {
																						i++;
																						objGetback = new JSONObject(
																								ssac.getvac_postings_DAta(
																										vsi.getId_tableInteger(),
																										vsi.getAction(),
																										vsi.getId()));
																						if ((objStudent
																								.toString()
																								.length() + objGetback
																								.toString()
																								.length()) <= limitString) {
																							objStudent
																									.put(vsi.getTable_name()
																											+ i,
																											objGetback);
																							if (it.hasNext()) {
																								vsi = (vrs_sync_items) it
																										.next();
																								if (vsi.getTable_name()
																										.equalsIgnoreCase(
																												"ved_students")) {
																									i++;
																									objGetback = new JSONObject(
																											ssa.getved_students_DAta(
																													vsi.getId_table(),
																													vsi.getAction(),
																													vsi.getId()));
																									if ((objStudent
																											.toString()
																											.length() + objGetback
																											.toString()
																											.length()) <= limitString) {
																										objStudent
																												.put(vsi.getTable_name()
																														+ i,
																														objGetback);

																										try {
																											boolean lopping = true;

																											while (lopping
																													&& objStudent
																															.toString()
																															.length() <= limitString
																													&& it.hasNext()) {
																												vsi = (vrs_sync_items) it
																														.next();
																												if (vsi.getTable_name()
																														.equalsIgnoreCase(
																																"vac_transactions")) {
																													i++;
																													objGetback = new JSONObject(
																															ssac.getvac_transactions_DAta(
																																	vsi.getId_tableInteger(),
																																	vsi.getAction(),
																																	vsi.getId()));
																													if ((objStudent
																															.toString()
																															.length() + objGetback
																															.toString()
																															.length()) <= limitString) {
																														objStudent
																																.put(vsi.getTable_name()
																																		+ i,
																																		objGetback);
																														if (it.hasNext()) {
																															vsi = (vrs_sync_items) it
																																	.next();
																															if (vsi.getTable_name()
																																	.equalsIgnoreCase(
																																			"vac_transaction_details")) {
																																i++;
																																objGetback = new JSONObject(
																																		ssac.getvac_transaction_details_DAta(
																																				vsi.getId_tableInteger(),
																																				vsi.getAction(),
																																				vsi.getId()));
																																if ((objStudent
																																		.toString()
																																		.length() + objGetback
																																		.toString()
																																		.length()) <= limitString) {
																																	objStudent
																																			.put(vsi.getTable_name()
																																					+ i,
																																					objGetback);
																																	if (it.hasNext()) {
																																		vsi = (vrs_sync_items) it
																																				.next();
																																		if (vsi.getTable_name()
																																				.equalsIgnoreCase(
																																						"vac_postings")) {
																																			i++;
																																			objGetback = new JSONObject(
																																					ssac.getvac_postings_DAta(
																																							vsi.getId_tableInteger(),
																																							vsi.getAction(),
																																							vsi.getId()));
																																			if ((objStudent
																																					.toString()
																																					.length() + objGetback
																																					.toString()
																																					.length()) <= limitString) {
																																				objStudent
																																						.put(vsi.getTable_name()
																																								+ i,
																																								objGetback);
																																				if (it.hasNext()) {
																																					vsi = (vrs_sync_items) it
																																							.next();
																																					if (vsi.getTable_name()
																																							.equalsIgnoreCase(
																																									"vac_postings")) {
																																						i++;
																																						objGetback = new JSONObject(
																																								ssac.getvac_postings_DAta(
																																										vsi.getId_tableInteger(),
																																										vsi.getAction(),
																																										vsi.getId()));
																																						if ((objStudent
																																								.toString()
																																								.length() + objGetback
																																								.toString()
																																								.length()) <= limitString) {
																																							objStudent
																																									.put(vsi.getTable_name()
																																											+ i,
																																											objGetback);
																																						} else
																																							lopping = false;

																																					}
																																				}
																																			}
																																		}
																																	}
																																}
																															}
																														}
																													}
																												} else if (vsi
																														.getTable_name()
																														.equalsIgnoreCase(
																																"ved_candidates")) {
																													i++;
																													objGetback = new JSONObject(
																															ssa.getved_candidates_DAta(
																																	vsi.getId_table(),
																																	vsi.getAction(),
																																	vsi.getId()));
																													if ((objStudent
																															.toString()
																															.length() + objGetback
																															.toString()
																															.length()) <= limitString) {
																														objStudent
																																.put(vsi.getTable_name()
																																		+ i,
																																		objGetback);
																													}
																													lopping = false;
																												} else {
																													lopping = false;
																												}
																											}
																										} catch (Exception ex) {

																										}

																									}
																								}
																							}
																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									} else if (vsi.getTable_name()
											.equalsIgnoreCase("vrs_users")) {
										i++;
										objGetback = new JSONObject(
												svs.getvrs_userus_DAta(vsi
														.getId_tableInteger(),
														vsi.getAction(), vsi
																.getId()));
										if ((objStudent.toString().length() + objGetback
												.toString().length()) <= limitString) {
											objStudent.put(vsi.getTable_name()
													+ i, objGetback);
											boolean looping = true;
											try {
												while (it.hasNext()
														&& objStudent
																.toString()
																.length() <= limitString) {
													vsi = (vrs_sync_items) it
															.next();
													if (vsi.getTable_name()
															.equalsIgnoreCase(
																	"vrs_roles")) {
														i++;
														objGetback = new JSONObject(
																svs.getvrs_roles_DAta(
																		vsi.getId_tableInteger(),
																		vsi.getAction(),
																		vsi.getId()));
														if ((objStudent
																.toString()
																.length() + objGetback
																.toString()
																.length()) <= limitString) {
															objStudent
																	.put(vsi.getTable_name()
																			+ i,
																			objGetback);
															if (it.hasNext()) {
																if (vsi.getTable_name()
																		.equalsIgnoreCase(
																				"vrs_users")) {
																	i++;
																	objGetback = new JSONObject(
																			svs.getvrs_userus_DAta(
																					vsi.getId_tableInteger(),
																					vsi.getAction(),
																					vsi.getId()));
																	if ((objStudent
																			.toString()
																			.length() + objGetback
																			.toString()
																			.length()) <= limitString) {
																		objStudent
																				.put(vsi.getTable_name()
																						+ i,
																						objGetback);
																	} else
																		looping = false;
																}
															}
														}
													}
												}
											} catch (Exception ex) {
											}
										}
									}
								}
							}
						}
					}
				}
			}

			objGetback = new JSONObject();
			objGetback.put("tableName", "students");
			objGetback.put("dataThis", objStudent);
			getBack = objGetback.toString();

		} catch (Exception ex) {
			cf.viewAlert("error criteriaAdd " + ex.getMessage());
		}
		return getBack;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String getVac_transactionsforDownload(String getBack,
			String sqlstudentLimit, Object[] obj, int id_table, int computer_id) {
		try {
			Iterator it = sessionFactory.getCurrentSession()
					.createSQLQuery(sqlstudentLimit)
					.setInteger("compID", computer_id).list().iterator();
			String table_name = "vac_transactions";
			try {
				boolean lopping = true;
				JSONObject objGetback = new JSONObject(getBack);
				JSONObject objStudent = new JSONObject();
				int i = 0;
				boolean hasTransactions = false;
				while (lopping && objStudent.toString().length() <= limitString
						&& it.hasNext()) {
					obj = (Object[]) it.next();
					table_name = obj[3].toString();

					if (table_name.equals("vac_transactions")) {
						id_table = Integer.valueOf(String.valueOf(obj[7]
								.toString()));
						objGetback = new JSONObject(
								ssac.getvac_transactions_DAtaDownload(id_table,
										obj[4].toString(),
										Integer.valueOf(obj[0].toString())));
						if ((objStudent.toString().length() + objGetback
								.toString().length()) <= limitString) {
							objStudent.put(table_name + i, objGetback);
							i++;
							if (it.hasNext()) {
								obj = (Object[]) it.next();
								table_name = obj[3].toString();
								if (table_name
										.equals("vac_transaction_details")) {
									id_table = Integer.valueOf(String
											.valueOf(obj[7].toString()));
									objGetback = new JSONObject(
											ssac.getvac_transaction_details_DAtaDownload(
													id_table,
													obj[4].toString(),
													Integer.valueOf(obj[0]
															.toString())));
									if ((objStudent.toString().length() + objGetback
											.toString().length()) <= limitString) {
										objStudent.put(table_name + i,
												objGetback);
										i++;
										if (it.hasNext()) {
											obj = (Object[]) it.next();
											table_name = obj[3].toString();
											if (table_name
													.equals("vac_postings")) {
												id_table = Integer
														.valueOf(String.valueOf(obj[7]
																.toString()));
												objGetback = new JSONObject(
														ssac.getvac_postings_DAtaDownload(
																id_table,
																obj[4].toString(),
																Integer.valueOf(obj[0]
																		.toString())));
												if ((objStudent.toString()
														.length() + objGetback
														.toString().length()) <= limitString) {
													objStudent.put(table_name
															+ i, objGetback);
													i++;
													if (it.hasNext()) {
														obj = (Object[]) it
																.next();
														table_name = obj[3]
																.toString();
														if (table_name
																.equals("vac_postings")) {
															id_table = Integer
																	.valueOf(String
																			.valueOf(obj[7]
																					.toString()));
															objGetback = new JSONObject(
																	ssac.getvac_postings_DAtaDownload(
																			id_table,
																			obj[4].toString(),
																			Integer.valueOf(obj[0]
																					.toString())));
															if ((objStudent
																	.toString()
																	.length() + objGetback
																	.toString()
																	.length()) <= limitString) {
																objStudent
																		.put(table_name
																				+ i,
																				objGetback);
																i++;
															} else
																lopping = false;
														}
													}
												}
											}
										}
									}
								}
							}
						}
						hasTransactions = true;
					} else if (table_name.equals("ved_candidates")) {
						id_table = Integer.valueOf(String.valueOf(obj[7]
								.toString()));
						objGetback = new JSONObject(
								ssa.getved_candidates_DAtaDownload(
										Long.valueOf(id_table),
										obj[4].toString(),
										Integer.valueOf(obj[0].toString())));
						if ((objStudent.toString().length() + objGetback
								.toString().length()) <= limitString) {
							objStudent.put(table_name + i, objGetback);
							i++;
						}
						lopping = false;
					} else {
						lopping = false;
					}
				}
				if (hasTransactions) {
					objGetback = new JSONObject();
					objGetback.put("tableName", "transactions1");
					objGetback.put("dataThis", objStudent);
					getBack = objGetback.toString();
				}

			} catch (Exception ex) {
				cf.viewAlert("error in getVed_transactionsforDownload looping "
						+ ex.getMessage());
			}
		} catch (Exception ex) {
			cf.viewAlert("error in getVed_transactionsforDownload "
					+ ex.getMessage());
		}
		return getBack;
	}

	int limitString = 7700;	
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String saveInTable_students(String data, int computer_id) {
		String getBack = "";
		JSONObject objGET1 = new JSONObject();
		try {
			objGET1.put("tableName", "student");
			JSONObject obj = new JSONObject(data);
			obj = new JSONObject(obj.get("dataThis").toString());

			int i = 0;
			String str = "vrs_users";
			String datafromSAved = svs.saveInTable_vrs_users(obj.get(str + i)
					.toString(), computer_id);
			objGET1.put(str + i, datafromSAved);
			JSONObject objSAved = new JSONObject(datafromSAved);
			String iduserComposite = objSAved.get("id_compositesaved")
					.toString();

			i++;
			str = "vrs_roles";
			datafromSAved = svs.saveInTable_vrs_roles(obj.get(str + i)
					.toString(), iduserComposite, computer_id);
			objGET1.put(str + i, datafromSAved);
			objSAved = new JSONObject(datafromSAved);
			String idrolesComposite = objSAved.get("id_compositesaved")
					.toString();

			boolean notError = true;
			try {
				i++;
				str = "ved_candidates";
				datafromSAved = ssa.saveInTable_ved_candidates(obj.get(str + i)
						.toString(), idrolesComposite, computer_id);
				objGET1.put(str + i, datafromSAved);

				i++;
				str = "vac_transactions";
				datafromSAved = ssac.saveInTable_vac_transactions(
						obj.get(str + i).toString(), idrolesComposite,
						computer_id);
				objGET1.put(str + i, datafromSAved);
				objSAved = new JSONObject(datafromSAved);
				String idTransaction = objSAved.get("id_compositesaved")
						.toString();

				i++;
				str = "vac_transaction_details";
				datafromSAved = ssac.saveInTable_vac_transaction_details(obj
						.get(str + i).toString(), idTransaction, computer_id);
				objGET1.put(str + i, datafromSAved);
				objSAved = new JSONObject(datafromSAved);
				String idTransactionDetail = objSAved.get("id_compositesaved")
						.toString();

				i++;
				str = "vac_postings";
				datafromSAved = ssac.saveInTable_vac_postings(obj.get(str + i)
						.toString(), idTransactionDetail, computer_id);
				objGET1.put(str + i, datafromSAved);
				i++;
				str = "vac_postings";
				datafromSAved = ssac.saveInTable_vac_postings(obj.get(str + i)
						.toString(), idTransactionDetail, computer_id);
				objGET1.put(str + i, datafromSAved);

				i++;
				str = "ved_students";
				objGET1.put(str + i, ssa.saveInTable_ved_students(
						obj.get(str + i).toString(), computer_id));

				boolean looping = true;
				while (looping) {
					try {
						i++;
						str = "vac_transactions";
						datafromSAved = ssac.saveInTable_vac_transactions(obj
								.get(str + i).toString(), idrolesComposite,
								computer_id);
						objGET1.put(str + i, datafromSAved);
						objSAved = new JSONObject(datafromSAved);
						idTransaction = objSAved.get("id_compositesaved")
								.toString();
						i++;
						str = "vac_transaction_details";
						datafromSAved = ssac
								.saveInTable_vac_transaction_details(
										obj.get(str + i).toString(),
										idTransaction, computer_id);
						objGET1.put(str + i, datafromSAved);
						objSAved = new JSONObject(datafromSAved);
						idTransactionDetail = objSAved.get("id_compositesaved")
								.toString();
						i++;
						str = "vac_postings";
						datafromSAved = ssac.saveInTable_vac_postings(
								obj.get(str + i).toString(),
								idTransactionDetail, computer_id);
						objGET1.put(str + i, datafromSAved);
						i++;
						str = "vac_postings";
						datafromSAved = ssac.saveInTable_vac_postings(
								obj.get(str + i).toString(),
								idTransactionDetail, computer_id);
						objGET1.put(str + i, datafromSAved);
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
						str = "vrs_users";
						datafromSAved = svs.saveInTable_vrs_users(
								obj.get(str + i).toString(), computer_id);
						objGET1.put(str + i, datafromSAved);
						objSAved = new JSONObject(datafromSAved);
						iduserComposite = objSAved.get("id_compositesaved")
								.toString();

						i++;
						str = "vrs_roles";
						datafromSAved = svs.saveInTable_vrs_roles(
								obj.get(str + i).toString(), iduserComposite,
								computer_id);
						objGET1.put(str + i, datafromSAved);
						objSAved = new JSONObject(datafromSAved);
						i++;
					} catch (Exception ex) {
						looping = false;
					}
				}
			}

		} catch (Exception ex) {
			cf.viewAlert("error saveInTable_students " + ex.getMessage());
		}
		getBack = objGET1.toString();
		return getBack;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String saveIn_transactions1(String data, int computer_id) {
		String getBack = "", str = "", datafromSAved = "", idTransaction = "", idTransactionDetail = "";
		JSONObject objGET1 = new JSONObject();
		JSONObject objSAved = new JSONObject();
		try {
			objGET1.put("tableName", "transactions1");
			JSONObject obj = new JSONObject(data);
			obj = new JSONObject(obj.get("dataThis").toString());
			boolean looping = true;
			int i = 0;
			while (looping) {
				try {
					str = "vac_transactions";
					datafromSAved = ssac.saveInTable_vac_transactions(
							obj.get(str + i).toString(), "", computer_id);
					objGET1.put(str + i, datafromSAved);
					objSAved = new JSONObject(datafromSAved);
					idTransaction = objSAved.get("id_compositesaved")
							.toString();
					i++;
					str = "vac_transaction_details";
					datafromSAved = ssac.saveInTable_vac_transaction_details(obj
							.get(str + i).toString(), idTransaction,
							computer_id);
					objGET1.put(str + i, datafromSAved);
					objSAved = new JSONObject(datafromSAved);
					idTransactionDetail = objSAved.get("id_compositesaved")
							.toString();
					i++;
					str = "vac_postings";
					datafromSAved = ssac.saveInTable_vac_postings(
							obj.get(str + i).toString(), idTransactionDetail,
							computer_id);
					objGET1.put(str + i, datafromSAved);
					i++;
					str = "vac_postings";
					datafromSAved = ssac.saveInTable_vac_postings(
							obj.get(str + i).toString(), idTransactionDetail,
							computer_id);
					objGET1.put(str + i, datafromSAved);
					i++;
				} catch (Exception ex) {
					looping = false;
				}
			}
		} catch (Exception ex) {
			cf.viewAlert(" error in saveIn_transactions1 "+ex.getMessage());
		}
		getBack = objGET1.toString();
		return getBack;
	}
	
	
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String getVac_transactionsforUploD(String getBack) {
		try {
			Criteria criteriaAdd = sessionFactory.getCurrentSession()
					.createCriteria(vrs_sync_items.class);
			criteriaAdd.add(Restrictions.eq("status", false));
			criteriaAdd.add(Restrictions.eq("action", "add"));
			criteriaAdd.addOrder(Order.asc("id"));
			criteriaAdd.setMaxResults(57);
			Iterator it = criteriaAdd.list().iterator();
			boolean lopping = true;
			JSONObject objGetback = new JSONObject();
			JSONObject objStudent = new JSONObject();
			int i = 0;
			while (lopping && objStudent.toString().length() <= limitString
					&& it.hasNext()) {
				vrs_sync_items vsi = (vrs_sync_items) it.next();
				if (vsi.getTable_name().equalsIgnoreCase("vac_transactions")) {
					objGetback = new JSONObject(ssac.getvac_transactions_DAta(
							vsi.getId_tableInteger(), vsi.getAction(),
							vsi.getId()));
					if ((objStudent.toString().length() + objGetback.toString()
							.length()) <= limitString) {
						objStudent.put(vsi.getTable_name() + i, objGetback);
						i++;
						if (it.hasNext()) {
							vsi = (vrs_sync_items) it.next();
							if (vsi.getTable_name().equalsIgnoreCase(
									"vac_transaction_details")) {
								objGetback = new JSONObject(
										ssac.getvac_transaction_details_DAta(
												vsi.getId_tableInteger(),
												vsi.getAction(), vsi.getId()));
								if ((objStudent.toString().length() + objGetback
										.toString().length()) <= limitString) {
									objStudent.put(vsi.getTable_name() + i,
											objGetback);
									i++;
									if (it.hasNext()) {
										vsi = (vrs_sync_items) it.next();
										if (vsi.getTable_name()
												.equalsIgnoreCase(
														"vac_postings")) {
											objGetback = new JSONObject(
													ssac.getvac_postings_DAta(
															vsi.getId_tableInteger(),
															vsi.getAction(),
															vsi.getId()));
											if ((objStudent.toString().length() + objGetback
													.toString().length()) <= limitString) {
												objStudent
														.put(vsi.getTable_name()
																+ i, objGetback);
												i++;
												if (it.hasNext()) {
													vsi = (vrs_sync_items) it
															.next();
													if (vsi.getTable_name()
															.equalsIgnoreCase(
																	"vac_postings")) {
														objGetback = new JSONObject(
																ssac.getvac_postings_DAta(
																		vsi.getId_tableInteger(),
																		vsi.getAction(),
																		vsi.getId()));
														if ((objStudent
																.toString()
																.length() + objGetback
																.toString()
																.length()) <= limitString) {
															objStudent
																	.put(vsi.getTable_name()
																			+ i,
																			objGetback);
															i++;
														} else
															lopping = false;

													}
												}
											}
										}
									}
								}
							}
						}
					}
				} else {
					lopping = false;
				}
			}
			objGetback = new JSONObject();
			objGetback.put("tableName", "transactions1");
			objGetback.put("dataThis", objStudent);
			getBack = objGetback.toString();
		} catch (Exception ex) {
			cf.viewAlert("error in getVac_transactionsforUploD "
						+ ex.getMessage());
		}
		return getBack;
	}
	

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String getVed_studentsforDownload(String getBack,
			String sqlstudentLimit, Object[] obj, int id_table, int computer_id) {
		try {
			Iterator it = sessionFactory.getCurrentSession()
					.createSQLQuery(sqlstudentLimit)
					.setInteger("compID", computer_id).list().iterator();
			String table_name = "";
			it.next();
			if (it.hasNext()) {
				obj = (Object[]) it.next();
				table_name = obj[3].toString();
				if (table_name.equals("vrs_roles")) {
					id_table = Integer
							.valueOf(String.valueOf(obj[7].toString()));
					int i = 0;
					JSONObject objGetback = new JSONObject(getBack);
					JSONObject objStudent = new JSONObject();
					objStudent.put("vrs_users" + i, objGetback);
					objGetback = new JSONObject(svs.getvrs_roles_DAtaDownload(
							id_table, obj[4].toString(),
							Integer.valueOf(obj[0].toString())));
					i++;
					if (objStudent.toString().length() <= limitString) {
						objStudent.put(table_name + i, objGetback);
						if (it.hasNext()) {
							obj = (Object[]) it.next();
							table_name = obj[3].toString();

							if (table_name.equals("ved_candidates")) {
								id_table = Integer.valueOf(String
										.valueOf(obj[7].toString()));
								i++;
								objGetback = new JSONObject(
										ssa.getved_candidates_DAtaDownload(Long
												.valueOf(id_table), obj[4]
												.toString(), Integer
												.valueOf(obj[0].toString())));
								if ((objStudent.toString().length() + objGetback
										.toString().length()) <= limitString) {
									objStudent.put(table_name + i, objGetback);
									if (it.hasNext()) {
										obj = (Object[]) it.next();
										table_name = obj[3].toString();
										if (table_name
												.equals("vac_transactions")) {
											id_table = Integer
													.valueOf(String.valueOf(obj[7]
															.toString()));
											i++;
											objGetback = new JSONObject(
													ssac.getvac_transactions_DAtaDownload(
															id_table,
															obj[4].toString(),
															Integer.valueOf(obj[0]
																	.toString())));
											if ((objStudent.toString().length() + objGetback
													.toString().length()) <= limitString) {
												objStudent.put(table_name + i,
														objGetback);
												if (it.hasNext()) {
													obj = (Object[]) it.next();
													table_name = obj[3]
															.toString();
													if (table_name
															.equals("vac_transaction_details")) {
														id_table = Integer
																.valueOf(String
																		.valueOf(obj[7]
																				.toString()));
														i++;
														objGetback = new JSONObject(
																ssac.getvac_transaction_details_DAtaDownload(
																		id_table,
																		obj[4].toString(),
																		Integer.valueOf(obj[0]
																				.toString())));
														if ((objStudent
																.toString()
																.length() + objGetback
																.toString()
																.length()) <= limitString) {
															objStudent
																	.put(table_name
																			+ i,
																			objGetback);
															if (it.hasNext()) {
																obj = (Object[]) it
																		.next();
																table_name = obj[3]
																		.toString();
																if (table_name
																		.equals("vac_postings")) {
																	id_table = Integer
																			.valueOf(String
																					.valueOf(obj[7]
																							.toString()));
																	i++;
																	objGetback = new JSONObject(
																			ssac.getvac_postings_DAtaDownload(
																					id_table,
																					obj[4].toString(),
																					Integer.valueOf(obj[0]
																							.toString())));
																	if ((objStudent
																			.toString()
																			.length() + objGetback
																			.toString()
																			.length()) <= limitString) {
																		objStudent
																				.put(table_name
																						+ i,
																						objGetback);
																		if (it.hasNext()) {
																			obj = (Object[]) it
																					.next();
																			table_name = obj[3]
																					.toString();
																			if (table_name
																					.equals("vac_postings")) {
																				id_table = Integer
																						.valueOf(String
																								.valueOf(obj[7]
																										.toString()));
																				i++;
																				objGetback = new JSONObject(
																						ssac.getvac_postings_DAtaDownload(
																								id_table,
																								obj[4].toString(),
																								Integer.valueOf(obj[0]
																										.toString())));
																				objStudent
																						.put(table_name
																								+ i,
																								objGetback);
																				if ((objStudent
																						.toString()
																						.length() + objGetback
																						.toString()
																						.length()) <= limitString) {
																					objStudent
																							.put(table_name
																									+ i,
																									objGetback);
																					if (it.hasNext()) {
																						table_name = obj[3]
																								.toString();
																						if (table_name
																								.equals("ved_students")) {
																							id_table = Integer
																									.valueOf(String
																											.valueOf(obj[7]
																													.toString()));
																							i++;
																							objGetback = new JSONObject(
																									ssa.getved_students_DAtaDownload(
																											Long.valueOf(id_table),
																											obj[4].toString(),
																											Integer.valueOf(obj[0]
																													.toString())));
																							if ((objStudent
																									.toString()
																									.length() + objGetback
																									.toString()
																									.length()) <= limitString) {
																								objStudent
																										.put(table_name
																												+ i,
																												objGetback);

																								try {
																									boolean lopping = true;
																									while (lopping
																											&& objStudent
																													.toString()
																													.length() <= limitString
																											&& it.hasNext()) {
																										obj = (Object[]) it
																												.next();
																										table_name = obj[3]
																												.toString();

																										if (table_name
																												.equals("vac_transactions")) {
																											id_table = Integer
																													.valueOf(String
																															.valueOf(obj[7]
																																	.toString()));
																											i++;
																											objGetback = new JSONObject(
																													ssac.getvac_transactions_DAtaDownload(
																															id_table,
																															obj[4].toString(),
																															Integer.valueOf(obj[0]
																																	.toString())));
																											if ((objStudent
																													.toString()
																													.length() + objGetback
																													.toString()
																													.length()) <= limitString) {
																												objStudent
																														.put(table_name
																																+ i,
																																objGetback);
																												if (it.hasNext()) {
																													obj = (Object[]) it
																															.next();
																													table_name = obj[3]
																															.toString();
																													if (table_name
																															.equals("vac_transaction_details")) {
																														id_table = Integer
																																.valueOf(String
																																		.valueOf(obj[7]
																																				.toString()));
																														i++;
																														objGetback = new JSONObject(
																																ssac.getvac_transaction_details_DAtaDownload(
																																		id_table,
																																		obj[4].toString(),
																																		Integer.valueOf(obj[0]
																																				.toString())));
																														if ((objStudent
																																.toString()
																																.length() + objGetback
																																.toString()
																																.length()) <= limitString) {
																															objStudent
																																	.put(table_name
																																			+ i,
																																			objGetback);
																															if (it.hasNext()) {
																																obj = (Object[]) it
																																		.next();
																																table_name = obj[3]
																																		.toString();
																																if (table_name
																																		.equals("vac_postings")) {
																																	id_table = Integer
																																			.valueOf(String
																																					.valueOf(obj[7]
																																							.toString()));
																																	i++;
																																	objGetback = new JSONObject(
																																			ssac.getvac_postings_DAtaDownload(
																																					id_table,
																																					obj[4].toString(),
																																					Integer.valueOf(obj[0]
																																							.toString())));
																																	if ((objStudent
																																			.toString()
																																			.length() + objGetback
																																			.toString()
																																			.length()) <= limitString) {
																																		objStudent
																																				.put(table_name
																																						+ i,
																																						objGetback);
																																		if (it.hasNext()) {
																																			obj = (Object[]) it
																																					.next();
																																			table_name = obj[3]
																																					.toString();
																																			if (table_name
																																					.equals("vac_postings")) {
																																				id_table = Integer
																																						.valueOf(String
																																								.valueOf(obj[7]
																																										.toString()));
																																				i++;
																																				objGetback = new JSONObject(
																																						ssac.getvac_postings_DAtaDownload(
																																								id_table,
																																								obj[4].toString(),
																																								Integer.valueOf(obj[0]
																																										.toString())));
																																				if ((objStudent
																																						.toString()
																																						.length() + objGetback
																																						.toString()
																																						.length()) <= limitString)
																																					objStudent
																																							.put(table_name
																																									+ i,
																																									objGetback);
																																				else
																																					lopping = false;
																																			}
																																		}
																																	}
																																}
																															}
																														}
																													}
																												}
																											}
																										} else if (table_name
																												.equals("ved_candidates")) {
																											id_table = Integer
																													.valueOf(String
																															.valueOf(obj[7]
																																	.toString()));
																											i++;
																											objGetback = new JSONObject(
																													ssa.getved_candidates_DAtaDownload(
																															Long.valueOf(id_table),
																															obj[4].toString(),
																															Integer.valueOf(obj[0]
																																	.toString())));
																											if ((objStudent
																													.toString()
																													.length() + objGetback
																													.toString()
																													.length()) <= limitString) {
																												objStudent
																														.put(table_name
																																+ i,
																																objGetback);
																											}

																											lopping = false;
																										} else {
																											lopping = false;
																										}
																									}

																								} catch (Exception ex) {
																									cf.viewAlert("error in getVed_studentsforDownload 2442 "
																														+ ex.getMessage());
																								}

																							}
																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							} else if (table_name.equals("vrs_users")) {
								id_table = Integer.valueOf(String
										.valueOf(obj[7].toString()));
								objGetback = new JSONObject(
										svs.getvrs_userus_DAtaForDownload(
												Integer.valueOf(id_table),
												obj[4].toString(), Integer
														.valueOf(obj[0]
																.toString())));
								if ((objStudent.toString().length() + objGetback
										.toString().length()) <= limitString) {
									i++;
									objStudent.put("vrs_users" + i, objGetback);
									boolean looping = true;
									try {
										while (it.hasNext()
												&& objStudent.toString()
														.length() <= limitString) {
											obj = (Object[]) it.next();
											table_name = obj[3].toString();
											if (table_name.equals("vrs_roles")) {
												id_table = Integer
														.valueOf(String.valueOf(obj[7]
																.toString()));
												objGetback = new JSONObject(
														svs.getvrs_roles_DAtaDownload(
																id_table,
																obj[4].toString(),
																Integer.valueOf(obj[0]
																		.toString())));
												if ((objStudent.toString()
														.length() + objGetback
														.toString().length()) <= limitString) {
													i++;
													objStudent.put("vrs_roles"
															+ i, objGetback);
													if (it.hasNext()) {
														obj = (Object[]) it
																.next();
														table_name = obj[3]
																.toString();
														if (table_name
																.equals("vrs_users")) {
															id_table = Integer
																	.valueOf(String
																			.valueOf(obj[7]
																					.toString()));
															objGetback = new JSONObject(
																	svs.getvrs_userus_DAtaForDownload(
																			Integer.valueOf(id_table),
																			obj[4].toString(),
																			Integer.valueOf(obj[0]
																					.toString())));
															if ((objStudent
																	.toString()
																	.length() + objGetback
																	.toString()
																	.length()) <= limitString) {
																i++;
																objStudent
																		.put("vrs_users"
																				+ i,
																				objGetback);
															}
														}
													}
												}
											}
										}
									} catch (Exception ex) {
									}
								}
							}
						}
					}
					objGetback = new JSONObject();
					objGetback.put("tableName", "students");
					objGetback.put("dataThis", objStudent);
					getBack = objGetback.toString();
				}
			}
		} catch (Exception ex) {
			cf.viewAlert("error in getVed_studentsforDownload "
						+ ex.getMessage());
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
							"finance_cash_account")
							|| vcm.getCompanyMetasKey().equalsIgnoreCase(
									"finance_credit_account")
							|| vcm.getCompanyMetasKey().equals(
									"finance_spp_account")
							|| vcm.getCompanyMetasKey().equals(
									"finance_ssp_account")
							|| vcm.getCompanyMetasKey().equals(
									"finance_charges_account")
							|| vcm.getCompanyMetasKey().equals(
									"finance_books_supplies_account")
							|| vcm.getCompanyMetasKey().equals(
									"finance_school_uniforms_account")
							|| vcm.getCompanyMetasKey().equals(
									"finance_registration_form_sales_account")
							|| vcm.getCompanyMetasKey().equals(
									"finance_business_liabilities_account")
							|| vcm.getCompanyMetasKey().equals(
									"finance_cost_liabilities_account")
							|| vcm.getCompanyMetasKey().equals(
									"finance_service_expense_account")
							|| vcm.getCompanyMetasKey().equals(
									"finance_operating_expense_account")
							|| vcm.getCompanyMetasKey().equals(
									"finance_re-registration_account")) {
						objColumn.put("companyMetasKey",
								vcm.getCompanyMetasKey());
						criteria = sessionFactory.getCurrentSession()
								.createCriteria(vac_chart_accounts.class);
						criteria.add(Restrictions.eq("id",
								Integer.valueOf(vcm.getCompanyMetasValue())));
						itInsideMetas = criteria.list().iterator();
						if (itInsideMetas.hasNext()) {
							vac_chart_accounts vca = (vac_chart_accounts) itInsideMetas
									.next();
							objColumn.put("id_composite_vac_chart_account",
									vca.getId_composite());
							criteria = sessionFactory.getCurrentSession()
									.createCriteria(vrs_companies.class);
							criteria.add(Restrictions.eq("companyId",
									vca.getCompanyId()));
							itInsideMetas = criteria.list().iterator();
							if (itInsideMetas.hasNext()) {
								vc = (vrs_companies) itInsideMetas.next();
								objColumn.put("id_composite_company",
										vc.getId_composite());
							}
						} else if (vcm.getCompanyMetasValue().equalsIgnoreCase(
								"0")) {
							objColumn.put("valueKey", "0");
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
							"finance_cash_account")
							|| vcm.getCompanyMetasKey().equalsIgnoreCase(
									"finance_credit_account")
							|| vcm.getCompanyMetasKey().equals(
									"finance_spp_account")
							|| vcm.getCompanyMetasKey().equals(
									"finance_ssp_account")
							|| vcm.getCompanyMetasKey().equals(
									"finance_charges_account")
							|| vcm.getCompanyMetasKey().equals(
									"finance_books_supplies_account")
							|| vcm.getCompanyMetasKey().equals(
									"finance_school_uniforms_account")
							|| vcm.getCompanyMetasKey().equals(
									"finance_registration_form_sales_account")
							|| vcm.getCompanyMetasKey().equals(
									"finance_business_liabilities_account")
							|| vcm.getCompanyMetasKey().equals(
									"finance_service_expense_account")
							|| vcm.getCompanyMetasKey().equals(
									"finance_operating_expense_account")
							|| vcm.getCompanyMetasKey().equals(
									"finance_cost_liabilities_account")
							|| vcm.getCompanyMetasKey().equals(
									"finance_re-registration_account")) {
						objColumn.put("companyMetasKey",
								vcm.getCompanyMetasKey());
						criteria = sessionFactory.getCurrentSession()
								.createCriteria(vac_chart_accounts.class);
						criteria.add(Restrictions.eq("id",
								Integer.valueOf(vcm.getCompanyMetasValue())));
						itInsideMetas = criteria.list().iterator();
						if (itInsideMetas.hasNext()) {
							vac_chart_accounts vca = (vac_chart_accounts) itInsideMetas
									.next();
							objColumn.put("id_composite_vac_chart_account",
									vca.getId_composite());
							criteria = sessionFactory.getCurrentSession()
									.createCriteria(vrs_companies.class);
							criteria.add(Restrictions.eq("companyId",
									vca.getCompanyId()));
							itInsideMetas = criteria.list().iterator();
							if (itInsideMetas.hasNext()) {
								vc = (vrs_companies) itInsideMetas.next();
								objColumn.put("id_composite_company",
										vc.getId_composite());
							}
						} else if (vcm.getCompanyMetasValue().equalsIgnoreCase(
								"0")) {
							objColumn.put("valueKey", "0");
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
	
	public void saveInTable_vac_chart_account_balancesDownload(String data) {
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();

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

				String sql = "ALTER TABLE vac_chart_account_balances DISABLE TRIGGER insert_vac_chart_account_balancest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();
				sql = "insert into vac_chart_account_balances (company_id,account_id,last_balance,"
						+ "monthly_balance,last_archive_balance,id_composite) values (:company_id,:account_id,:last_balance,"
						+ monthlyBalance
						+ ",:last_archive_balance,:id_composite)";

				sessionFactory
						.getCurrentSession()
						.createSQLQuery(sql)
						.setInteger("company_id", companyID)
						.setInteger("account_id", accountID)
						.setBigDecimal(
								"last_balance",
								BigDecimal.valueOf(Double.valueOf(dataVrs_users
										.get("lastBalance").toString())))
						.setBigDecimal("last_archive_balance",
								lastArchiveBalance)

						.setInteger(
								"id_composite",
								Integer.valueOf(dataVrs_users.get(
										"id_composite").toString()))

						.executeUpdate();

				sql = "ALTER TABLE vac_chart_account_balances ENABLE TRIGGER insert_vac_chart_account_balancest";
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

				sql = "ALTER TABLE vac_chart_account_balances DISABLE TRIGGER insert_vac_chart_account_balancest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

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

				sql = "ALTER TABLE vac_chart_account_balances ENABLE TRIGGER insert_vac_chart_account_balancest";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();
			}
		} catch (Exception ex) {
			cf.viewAlert(" error saveInTable_vac_chart_account_balancesDownload "
					+ ex.getMessage());
		}
	}
	
	public void addTransactionMetas(int trransaction_id, String key, String val,
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
	
	public void saveInTable_vac_chart_accountsDownload(String data) {
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("vac_chart_accounts")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			if (action.equalsIgnoreCase("add")) {
				int company_id = 0;
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					company_id = vc.getCompanyId();
				}

				String monthly_balance = "null";
				if (dataVrs_users.get("monthlyBalance").toString().length() > 0)
					monthly_balance = "'"
							+ dataVrs_users.get("monthlyBalance").toString()
							+ "'";

				int cretedBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company-createdBy")
								.toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vsc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("createdBy").toString())));
					criteria.add(Restrictions.eq("companyId",
							vsc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						cretedBy = vr.getRoleId();
					}
				}
				int modifiedBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company-modifiedBy")
								.toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vsc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq("id_composite",
							Integer.valueOf(dataVrs_users.get("modifiedBy")
									.toString())));
					criteria.add(Restrictions.eq("companyId",
							vsc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						modifiedBy = vr.getRoleId();
					}
				}

				int parent = 0;
				try {
					if (dataVrs_users.get("compnayID_from_parent").toString() != null
							&& dataVrs_users.get("parentID_composite")
									.toString() != null) {
						if (dataVrs_users.get("compnayID_from_parent")
								.toString().length() > 0
								&& dataVrs_users.get("parentID_composite")
										.toString().length() > 0) {
							criteria = sessionFactory.getCurrentSession()
									.createCriteria(vrs_companies.class);
							criteria.add(Restrictions
									.eq("id_composite", Integer
											.valueOf(dataVrs_users.get(
													"compnayID_from_parent")
													.toString())));
							it = criteria.list().iterator();
							if (it.hasNext()) {
								vrs_companies vco = (vrs_companies) it.next();
								criteria = sessionFactory.getCurrentSession()
										.createCriteria(
												vac_chart_accounts.class);
								criteria.add(Restrictions.eq("companyId",
										vco.getCompanyId()));
								criteria.add(Restrictions.eq("id_composite",
										Integer.valueOf(dataVrs_users.get(
												"parentID_composite")
												.toString())));
								it = criteria.list().iterator();
								if (it.hasNext()) {
									vac_chart_accounts vcaa = (vac_chart_accounts) it
											.next();
									parent = vcaa.getId();
								}
							}
						}
					}
				} catch (Exception ex) {
				}

				BigDecimal lastArchiveBalance = BigDecimal.valueOf(0);
				if (dataVrs_users.get("lastArchiveBalance").toString() != null) {
					if (dataVrs_users.get("lastArchiveBalance").toString()
							.length() > 0) {
						lastArchiveBalance = BigDecimal.valueOf(Double
								.valueOf(dataVrs_users
										.get("lastArchiveBalance").toString()));
					}
				}

				String sql = "ALTER TABLE vac_chart_accounts DISABLE TRIGGER insert_vac_chart_accountst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

				int idData = -1;
				sql = "SELECT nextval('vac_chart_accounts_id_seq')";
				it = sessionFactory.getCurrentSession().createSQLQuery(sql)
						.list().iterator();
				if (it.hasNext()) {
					idData = Integer.valueOf(it.next().toString());
				}
				sql = "insert into vac_chart_accounts (id,company_id,code,category,name,description,starting_balance,last_balance"
						+ ",monthly_balance,last_archive_balance,created,created_by,modified,modified_by,parent,status,id_composite) "
						+ "values "
						+ "(:id,:company_id,:code,:category,:name,:description,:starting_balance,:last_balance,"
						+ monthly_balance
						+ ",:last_archive_balance,:created,:created_by,:modified,:modified_by,:parent,:status,:id_composite);";
				sessionFactory
						.getCurrentSession()
						.createSQLQuery(sql)
						.setInteger("id", idData)
						.setInteger("company_id", company_id)
						.setString("code", dataVrs_users.get("code").toString())
						.setString("category",
								dataVrs_users.get("category").toString())
						.setString("name", dataVrs_users.get("name").toString())
						.setString("description",
								dataVrs_users.get("description").toString())
						.setBigDecimal(
								"starting_balance",
								BigDecimal.valueOf(Double.valueOf(dataVrs_users
										.get("starting_balance").toString())))
						.setBigDecimal(
								"last_balance",
								BigDecimal.valueOf(Double.valueOf(dataVrs_users
										.get("lastBalance").toString())))
						.setBigDecimal("last_archive_balance",
								lastArchiveBalance)
						.setTimestamp(
								"created",
								svs.getTimeStamp(dataVrs_users.get("created")
										.toString()))
						.setInteger("created_by", cretedBy)
						.setTimestamp(
								"modified",
								svs.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modified_by", modifiedBy)
						.setInteger("parent", parent)
						.setInteger(
								"id_composite",
								Integer.valueOf(dataVrs_users.get(
										"id_composite").toString()))
						.setInteger(
								"status",
								Integer.valueOf(dataVrs_users.get("status")
										.toString())).executeUpdate();

				sessionFactory.getCurrentSession().clear();

				sql = "ALTER TABLE vac_chart_accounts ENABLE TRIGGER insert_vac_chart_accountst";
				sessionFactory.getCurrentSession().createSQLQuery(sql)
						.executeUpdate();

			} else if (action.equalsIgnoreCase("edit")) {
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vac_chart_accounts.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));
					int company_id = vc.getCompanyId();
					criteria.add(Restrictions.eq("companyId", company_id));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vac_chart_accounts vca = (vac_chart_accounts) it.next();
						int idvac_chart_accounts = vca.getId();

						int cretedBy = -1;
						criteria = sessionFactory.getCurrentSession()
								.createCriteria(vrs_companies.class);
						criteria.add(Restrictions.eq(
								"id_composite",
								Integer.valueOf(dataVrs_users.get(
										"company-createdBy").toString())));
						it = criteria.list().iterator();
						if (it.hasNext()) {
							vrs_companies vsc = (vrs_companies) it.next();
							criteria = sessionFactory.getCurrentSession()
									.createCriteria(vrs_roles.class);
							criteria.add(Restrictions.eq(
									"id_composite",
									Integer.valueOf(dataVrs_users.get(
											"createdBy").toString())));
							criteria.add(Restrictions.eq("companyId",
									vsc.getCompanyId()));
							it = criteria.list().iterator();
							if (it.hasNext()) {
								vrs_roles vr = (vrs_roles) it.next();
								cretedBy = vr.getRoleId();
							}
						}
						int modifiedBy = -1;
						criteria = sessionFactory.getCurrentSession()
								.createCriteria(vrs_companies.class);
						criteria.add(Restrictions.eq(
								"id_composite",
								Integer.valueOf(dataVrs_users.get(
										"company-modifiedBy").toString())));
						it = criteria.list().iterator();
						if (it.hasNext()) {
							vrs_companies vsc = (vrs_companies) it.next();
							criteria = sessionFactory.getCurrentSession()
									.createCriteria(vrs_roles.class);
							criteria.add(Restrictions.eq(
									"id_composite",
									Integer.valueOf(dataVrs_users.get(
											"modifiedBy").toString())));
							criteria.add(Restrictions.eq("companyId",
									vsc.getCompanyId()));
							it = criteria.list().iterator();
							if (it.hasNext()) {
								vrs_roles vr = (vrs_roles) it.next();
								modifiedBy = vr.getRoleId();
							}
						}

						int parent = 0;
						try {
							if (dataVrs_users.get("compnayID_from_parent")
									.toString() != null
									&& dataVrs_users.get("parentID_composite")
											.toString() != null) {
								if (dataVrs_users.get("compnayID_from_parent")
										.toString().length() > 0
										&& dataVrs_users
												.get("parentID_composite")
												.toString().length() > 0) {
									criteria = sessionFactory
											.getCurrentSession()
											.createCriteria(vrs_companies.class);
									criteria.add(Restrictions.eq(
											"id_composite",
											Integer.valueOf(dataVrs_users.get(
													"compnayID_from_parent")
													.toString())));
									it = criteria.list().iterator();
									if (it.hasNext()) {
										vrs_companies vco = (vrs_companies) it
												.next();
										criteria = sessionFactory
												.getCurrentSession()
												.createCriteria(
														vac_chart_accounts.class);
										criteria.add(Restrictions.eq(
												"companyId", vco.getCompanyId()));
										criteria.add(Restrictions.eq(
												"id_composite",
												Integer.valueOf(dataVrs_users
														.get("parentID_composite")
														.toString())));
										it = criteria.list().iterator();
										if (it.hasNext()) {
											vac_chart_accounts vcaa = (vac_chart_accounts) it
													.next();
											parent = vcaa.getId();
										}
									}
								}
							}
						} catch (Exception ex) {
						}

						BigDecimal lastArchiveBalance = BigDecimal.valueOf(0);
						if (dataVrs_users.get("lastArchiveBalance").toString() != null) {
							if (dataVrs_users.get("lastArchiveBalance")
									.toString().length() > 0) {
								lastArchiveBalance = BigDecimal.valueOf(Double
										.valueOf(dataVrs_users.get(
												"lastArchiveBalance")
												.toString()));
							}
						}

						String monthly_balance = "null";
						if (dataVrs_users.get("monthlyBalance").toString()
								.length() > 0)
							monthly_balance = "'"
									+ dataVrs_users.get("monthlyBalance")
											.toString() + "'";

						String sql = "ALTER TABLE vac_chart_accounts DISABLE TRIGGER insert_vac_chart_accountst";
						sessionFactory.getCurrentSession().createSQLQuery(sql)
								.executeUpdate();

						sql = "update vac_chart_accounts set company_id=:company_id,"
								+ "code=:code,"
								+ "category=:category,"
								+ "name=:name,description=:description,starting_balance=:starting_balance,"
								+ "last_balance=:last_balance,monthly_balance="
								+ monthly_balance
								+ ",last_archive_balance=:last_archive_balance,"
								+ "created=:created,created_by=:created_by,modified=:modified,modified_by=:modified_by,"
								+ "parent=:parent,status=:status where id=:idTable";
						sessionFactory
								.getCurrentSession()
								.createSQLQuery(sql)
								.setInteger("idTable", idvac_chart_accounts)
								.setInteger("company_id", company_id)
								.setString("code",
										dataVrs_users.get("code").toString())
								.setString(
										"category",
										dataVrs_users.get("category")
												.toString())
								.setString("name",
										dataVrs_users.get("name").toString())
								.setString(
										"description",
										dataVrs_users.get("description")
												.toString())
								.setBigDecimal(
										"starting_balance",
										BigDecimal.valueOf(Double
												.valueOf(dataVrs_users.get(
														"starting_balance")
														.toString())))
								.setBigDecimal(
										"last_balance",
										BigDecimal.valueOf(Double
												.valueOf(dataVrs_users.get(
														"lastBalance")
														.toString())))
								.setBigDecimal("last_archive_balance",
										lastArchiveBalance)
								.setTimestamp(
										"created",
										svs.getTimeStamp(dataVrs_users.get(
												"created").toString()))
								.setInteger("created_by", cretedBy)
								.setTimestamp(
										"modified",
										svs.getTimeStamp(dataVrs_users.get(
												"modified").toString()))
								.setInteger("modified_by", modifiedBy)
								.setInteger("parent", parent)
								.setInteger(
										"status",
										Integer.valueOf(dataVrs_users.get(
												"status").toString()))
								.executeUpdate();

						sql = "ALTER TABLE vac_chart_accounts ENABLE TRIGGER insert_vac_chart_accountst";
						sessionFactory.getCurrentSession().createSQLQuery(sql)
								.executeUpdate();
					}
				}
			}
		} catch (Exception ex) {
			cf.viewAlert(" error saveInTable_vac_chart_accountsDownload "
					+ ex.getMessage());
		}
	}
	
	public String saveInTable_vac_chart_accounts(String data, int computer_id) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			JSONObject objView = new JSONObject();
			objView.put("id_vrs_sync_itemsLOCAL",
					obj.get("id_vrs_sync_itemsLOCAL").toString());
			String action = obj.get("action").toString();
			JSONArray objData = new JSONArray(obj.get("vac_chart_accounts")
					.toString());
			JSONObject dataVrs_users = objData.getJSONObject(0);
			if (action.equalsIgnoreCase("add")) {
				int company_id = 0;
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					company_id = vc.getCompanyId();
				}

				String monthly_balance = "null";
				if (dataVrs_users.get("monthlyBalance").toString().length() > 0)
					monthly_balance = "'"
							+ dataVrs_users.get("monthlyBalance").toString()
							+ "'";

				int cretedBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company-createdBy")
								.toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vsc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("createdBy").toString())));
					criteria.add(Restrictions.eq("companyId",
							vsc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						cretedBy = vr.getRoleId();
					}
				}
				int modifiedBy = -1;
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company-modifiedBy")
								.toString())));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vsc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_roles.class);
					criteria.add(Restrictions.eq("id_composite",
							Integer.valueOf(dataVrs_users.get("modifiedBy")
									.toString())));
					criteria.add(Restrictions.eq("companyId",
							vsc.getCompanyId()));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vrs_roles vr = (vrs_roles) it.next();
						modifiedBy = vr.getRoleId();
					}
				}

				int parent = 0;
				try {
					if (dataVrs_users.get("compnayID_from_parent").toString() != null
							&& dataVrs_users.get("parentID_composite")
									.toString() != null) {
						if (dataVrs_users.get("compnayID_from_parent")
								.toString().length() > 0
								&& dataVrs_users.get("parentID_composite")
										.toString().length() > 0) {
							criteria = sessionFactory.getCurrentSession()
									.createCriteria(vrs_companies.class);
							criteria.add(Restrictions
									.eq("id_composite", Integer
											.valueOf(dataVrs_users.get(
													"compnayID_from_parent")
													.toString())));
							it = criteria.list().iterator();
							if (it.hasNext()) {
								vrs_companies vco = (vrs_companies) it.next();
								criteria = sessionFactory.getCurrentSession()
										.createCriteria(
												vac_chart_accounts.class);
								criteria.add(Restrictions.eq("companyId",
										vco.getCompanyId()));
								criteria.add(Restrictions.eq("id_composite",
										Integer.valueOf(dataVrs_users.get(
												"parentID_composite")
												.toString())));
								it = criteria.list().iterator();
								if (it.hasNext()) {
									vac_chart_accounts vcaa = (vac_chart_accounts) it
											.next();
									parent = vcaa.getId();
								}
							}
						}
					}
				} catch (Exception ex) {
				}

				BigDecimal lastArchiveBalance = BigDecimal.valueOf(0);
				if (dataVrs_users.get("lastArchiveBalance").toString() != null) {
					if (dataVrs_users.get("lastArchiveBalance").toString()
							.length() > 0) {
						lastArchiveBalance = BigDecimal.valueOf(Double
								.valueOf(dataVrs_users
										.get("lastArchiveBalance").toString()));
					}
				}

				int idData = -1;
				String sql = "SELECT nextval('vac_chart_accounts_id_seq')";
				it = sessionFactory.getCurrentSession().createSQLQuery(sql)
						.list().iterator();
				if (it.hasNext()) {
					idData = Integer.valueOf(it.next().toString());
				}

				sql = "insert into vac_chart_accounts (id,company_id,code,category,name,description,starting_balance,last_balance"
						+ ",monthly_balance,last_archive_balance,created,created_by,modified,modified_by,parent,status) "
						+ "values "
						+ "(:id,:company_id,:code,:category,:name,:description,:starting_balance,:last_balance,"
						+ monthly_balance
						+ ",:last_archive_balance,:created,:created_by,:modified,:modified_by,:parent,:status);";
				sessionFactory
						.getCurrentSession()
						.createSQLQuery(sql)
						.setInteger("id", idData)
						.setInteger("company_id", company_id)
						.setString("code", dataVrs_users.get("code").toString())
						.setString("category",
								dataVrs_users.get("category").toString())
						.setString("name", dataVrs_users.get("name").toString())
						.setString("description",
								dataVrs_users.get("description").toString())
						.setBigDecimal(
								"starting_balance",
								BigDecimal.valueOf(Double.valueOf(dataVrs_users
										.get("starting_balance").toString())))
						.setBigDecimal(
								"last_balance",
								BigDecimal.valueOf(Double.valueOf(dataVrs_users
										.get("lastBalance").toString())))
						.setBigDecimal("last_archive_balance",
								lastArchiveBalance)
						.setTimestamp(
								"created",
								svs.getTimeStamp(dataVrs_users.get("created")
										.toString()))
						.setInteger("created_by", cretedBy)
						.setTimestamp(
								"modified",
								svs.getTimeStamp(dataVrs_users.get("modified")
										.toString()))
						.setInteger("modified_by", modifiedBy)
						.setInteger("parent", parent)
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
				sessionFactory.getCurrentSession().clear();
				criteria = sessionFactory.getCurrentSession().createCriteria(
						vac_chart_accounts.class);
				criteria.add(Restrictions.eq("id", idData));
				it = criteria.list().iterator();
				if (it.hasNext()) {
					vac_chart_accounts vco = (vac_chart_accounts) it.next();
					objView.put("id_CompositeFromServer", vco.getId_composite());
					getBack = objView.toString();
				}
			} else if (action.equalsIgnoreCase("edit")) {
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_companies.class);
				criteria.add(Restrictions.eq("id_composite", Integer
						.valueOf(dataVrs_users.get("company_idComposite")
								.toString())));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_companies vc = (vrs_companies) it.next();
					criteria = sessionFactory.getCurrentSession()
							.createCriteria(vac_chart_accounts.class);
					criteria.add(Restrictions.eq("id_composite", Integer
							.valueOf(dataVrs_users.get("id_composite")
									.toString())));

					int company_id = vc.getCompanyId();
					criteria.add(Restrictions.eq("companyId", company_id));
					it = criteria.list().iterator();
					if (it.hasNext()) {
						vac_chart_accounts vca = (vac_chart_accounts) it.next();
						int idvac_chart_accounts = vca.getId();

						int cretedBy = -1;
						criteria = sessionFactory.getCurrentSession()
								.createCriteria(vrs_companies.class);
						criteria.add(Restrictions.eq(
								"id_composite",
								Integer.valueOf(dataVrs_users.get(
										"company-createdBy").toString())));
						it = criteria.list().iterator();
						if (it.hasNext()) {
							vrs_companies vsc = (vrs_companies) it.next();
							criteria = sessionFactory.getCurrentSession()
									.createCriteria(vrs_roles.class);
							criteria.add(Restrictions.eq(
									"id_composite",
									Integer.valueOf(dataVrs_users.get(
											"createdBy").toString())));
							criteria.add(Restrictions.eq("companyId",
									vsc.getCompanyId()));
							it = criteria.list().iterator();
							if (it.hasNext()) {
								vrs_roles vr = (vrs_roles) it.next();
								cretedBy = vr.getRoleId();
							}
						}
						int modifiedBy = -1;
						criteria = sessionFactory.getCurrentSession()
								.createCriteria(vrs_companies.class);
						criteria.add(Restrictions.eq(
								"id_composite",
								Integer.valueOf(dataVrs_users.get(
										"company-modifiedBy").toString())));
						it = criteria.list().iterator();
						if (it.hasNext()) {
							vrs_companies vsc = (vrs_companies) it.next();
							criteria = sessionFactory.getCurrentSession()
									.createCriteria(vrs_roles.class);
							criteria.add(Restrictions.eq(
									"id_composite",
									Integer.valueOf(dataVrs_users.get(
											"modifiedBy").toString())));
							criteria.add(Restrictions.eq("companyId",
									vsc.getCompanyId()));
							it = criteria.list().iterator();
							if (it.hasNext()) {
								vrs_roles vr = (vrs_roles) it.next();
								modifiedBy = vr.getRoleId();
							}
						}

						int parent = 0;
						try {
							if (dataVrs_users.get("compnayID_from_parent")
									.toString() != null
									&& dataVrs_users.get("parentID_composite")
											.toString() != null) {
								if (dataVrs_users.get("compnayID_from_parent")
										.toString().length() > 0
										&& dataVrs_users
												.get("parentID_composite")
												.toString().length() > 0) {
									criteria = sessionFactory
											.getCurrentSession()
											.createCriteria(vrs_companies.class);
									criteria.add(Restrictions.eq(
											"id_composite",
											Integer.valueOf(dataVrs_users.get(
													"compnayID_from_parent")
													.toString())));
									it = criteria.list().iterator();
									if (it.hasNext()) {
										vrs_companies vco = (vrs_companies) it
												.next();
										criteria = sessionFactory
												.getCurrentSession()
												.createCriteria(
														vac_chart_accounts.class);
										criteria.add(Restrictions.eq(
												"companyId", vco.getCompanyId()));
										criteria.add(Restrictions.eq(
												"id_composite",
												Integer.valueOf(dataVrs_users
														.get("parentID_composite")
														.toString())));
										it = criteria.list().iterator();
										if (it.hasNext()) {
											vac_chart_accounts vcaa = (vac_chart_accounts) it
													.next();
											parent = vcaa.getId();
										}
									}
								}
							}
						} catch (Exception ex) {
						}

						BigDecimal lastArchiveBalance = BigDecimal.valueOf(0);
						if (dataVrs_users.get("lastArchiveBalance").toString() != null) {
							if (dataVrs_users.get("lastArchiveBalance")
									.toString().length() > 0) {
								lastArchiveBalance = BigDecimal.valueOf(Double
										.valueOf(dataVrs_users.get(
												"lastArchiveBalance")
												.toString()));
							}
						}

						String monthly_balance = "null";
						if (dataVrs_users.get("monthlyBalance").toString()
								.length() > 0)
							monthly_balance = "'"
									+ dataVrs_users.get("monthlyBalance")
											.toString() + "'";

						String sql = "update vac_chart_accounts set company_id=:company_id,"
								+ "code=:code,"
								+ "category=:category,"
								+ "name=:name,description=:description,starting_balance=:starting_balance,"
								+ "last_balance=:last_balance,monthly_balance="
								+ monthly_balance
								+ ",last_archive_balance=:last_archive_balance,"
								+ "created=:created,created_by=:created_by,modified=:modified,modified_by=:modified_by,"
								+ "parent=:parent,status=:status where id=:idTable";
						sessionFactory
								.getCurrentSession()
								.createSQLQuery(sql)
								.setInteger("idTable", idvac_chart_accounts)
								.setInteger("company_id", company_id)
								.setString("code",
										dataVrs_users.get("code").toString())
								.setString(
										"category",
										dataVrs_users.get("category")
												.toString())
								.setString("name",
										dataVrs_users.get("name").toString())
								.setString(
										"description",
										dataVrs_users.get("description")
												.toString())
								.setBigDecimal(
										"starting_balance",
										BigDecimal.valueOf(Double
												.valueOf(dataVrs_users.get(
														"starting_balance")
														.toString())))
								.setBigDecimal(
										"last_balance",
										BigDecimal.valueOf(Double
												.valueOf(dataVrs_users.get(
														"lastBalance")
														.toString())))
								.setBigDecimal("last_archive_balance",
										lastArchiveBalance)
								.setTimestamp(
										"created",
										svs.getTimeStamp(dataVrs_users.get(
												"created").toString()))
								.setInteger("created_by", cretedBy)
								.setTimestamp(
										"modified",
										svs.getTimeStamp(dataVrs_users.get(
												"modified").toString()))
								.setInteger("modified_by", modifiedBy)
								.setInteger("parent", parent)
								.setInteger(
										"status",
										Integer.valueOf(dataVrs_users.get(
												"status").toString()))
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
						getBack = objView.toString();
					}
				}
			}
		} catch (Exception ex) {
			cf.viewAlert(" error saveInTable_vac_chart_accounts "
					+ ex.getMessage());
			getBack += " error saveInTable_vac_chart_accounts "
					+ ex.getMessage();
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
				vc.setCreated(svs.getTimeStamp(dataVrs_users.get("created")
						.toString()));
				vc.setModified(svs.getTimeStamp(dataVrs_users.get("modified")
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
								"finance_cash_account")
								|| objData_meta1.get("companyMetasKey").equals(
										"finance_credit_account")
								|| objData_meta1.get("companyMetasKey").equals(
										"finance_spp_account")
								|| objData_meta1.get("companyMetasKey").equals(
										"finance_ssp_account")
								|| objData_meta1.get("companyMetasKey").equals(
										"finance_charges_account")
								|| objData_meta1.get("companyMetasKey").equals(
										"finance_books_supplies_account")
								|| objData_meta1.get("companyMetasKey").equals(
										"finance_school_uniforms_account")
								|| objData_meta1.get("companyMetasKey").equals(
										"finance_business_liabilities_account")
								|| objData_meta1.get("companyMetasKey").equals(
										"finance_operating_expense_account")
								|| objData_meta1.get("companyMetasKey").equals(
										"finance_service_expense_account")
								|| objData_meta1.get("companyMetasKey").equals(
										"finance_cost_liabilities_account")
								|| objData_meta1
										.get("companyMetasKey")
										.equals("finance_registration_form_sales_account")
								|| objData_meta1.get("companyMetasKey").equals(
										"finance_re-registration_account")

						) {

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
													vac_chart_accounts.class);
									criteria.add(Restrictions.eq("companyId",
											vco.getCompanyId()));
									criteria.add(Restrictions.eq(
											"id_composite",
											Integer.valueOf(objData_meta1
													.get("id_composite_vac_chart_account")
													.toString())));
									it = criteria.list().iterator();
									if (it.hasNext()) {
										vac_chart_accounts vca = (vac_chart_accounts) it
												.next();
										metaValue = String.valueOf(vca.getId());
									}
								}
							} catch (Exception ex) {
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

						vcm.setCreated(svs.getTimeStamp(objData_meta1
								.get("created").toString()));
						vcm.setCompanyMetasKey(objData_meta1.get(
								"companyMetasKey").toString());
						vcm.setModified(svs.getTimeStamp(objData_meta1.get(
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
									svs.getTimeStamp(dataVrs_users.get("created")
											.toString()))
							.setInteger("createdBy", createdBy)
							.setTimestamp(
									"modified",
									svs.getTimeStamp(dataVrs_users.get("modified")
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
									.equals("finance_cash_account")
									|| objData_meta1.get("companyMetasKey")
											.equals("finance_credit_account")
									|| objData_meta1.get("companyMetasKey")
											.equals("finance_spp_account")
									|| objData_meta1.get("companyMetasKey")
											.equals("finance_ssp_account")
									|| objData_meta1.get("companyMetasKey")
											.equals("finance_charges_account")
									|| objData_meta1
											.get("companyMetasKey")
											.equals("finance_books_supplies_account")
									|| objData_meta1
											.get("companyMetasKey")
											.equals("finance_school_uniforms_account")
									|| objData_meta1
											.get("companyMetasKey")
											.equals("finance_registration_form_sales_account")
									|| objData_meta1
											.get("companyMetasKey")
											.equals("finance_business_liabilities_account")
									|| objData_meta1
											.get("companyMetasKey")
											.equals("finance_cost_liabilities_account")
									|| objData_meta1
											.get("companyMetasKey")
											.equals("finance_operating_expense_account")
									|| objData_meta1
											.get("companyMetasKey")
											.equals("finance_service_expense_account")
									|| objData_meta1
											.get("companyMetasKey")
											.equals("finance_re-registration_account")

							) {

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
														vac_chart_accounts.class);
										criteria.add(Restrictions.eq(
												"companyId", vco.getCompanyId()));
										criteria.add(Restrictions.eq(
												"id_composite",
												Integer.valueOf(objData_meta1
														.get("id_composite_vac_chart_account")
														.toString())));
										it = criteria.list().iterator();
										if (it.hasNext()) {
											vac_chart_accounts vca = (vac_chart_accounts) it
													.next();
											metaValue = String.valueOf(vca
													.getId());
										}
									}
								} catch (Exception ex) {

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
							svs.addCompaniesMetas(companyID,
									objData_meta1.get("companyMetasKey")
											.toString(), metaValue,
											svs.getTimeStamp(objData_meta1.get("created")
											.toString()),
											svs.getTimeStamp(objData_meta1.get("modified")
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
				vc.setCreated(svs.getTimeStamp(dataVrs_users.get("created")
						.toString()));
				vc.setModified(svs.getTimeStamp(dataVrs_users.get("modified")
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
								"finance_cash_account")
								|| objData_meta1.get("companyMetasKey").equals(
										"finance_credit_account")
								|| objData_meta1.get("companyMetasKey").equals(
										"finance_spp_account")
								|| objData_meta1.get("companyMetasKey").equals(
										"finance_ssp_account")
								|| objData_meta1.get("companyMetasKey").equals(
										"finance_charges_account")
								|| objData_meta1.get("companyMetasKey").equals(
										"finance_books_supplies_account")
								|| objData_meta1.get("companyMetasKey").equals(
										"finance_school_uniforms_account")
								|| objData_meta1.get("companyMetasKey").equals(
										"finance_business_liabilities_account")
								|| objData_meta1.get("companyMetasKey").equals(
										"finance_service_expense_account")
								|| objData_meta1.get("companyMetasKey").equals(
										"finance_operating_expense_account")
								|| objData_meta1.get("companyMetasKey").equals(
										"finance_cost_liabilities_account")
								|| objData_meta1
										.get("companyMetasKey")
										.equals("finance_registration_form_sales_account")
								|| objData_meta1.get("companyMetasKey").equals(
										"finance_re-registration_account")

						) {
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
													vac_chart_accounts.class);
									criteria.add(Restrictions.eq("companyId",
											vco.getCompanyId()));
									criteria.add(Restrictions.eq(
											"id_composite",
											Integer.valueOf(objData_meta1
													.get("id_composite_vac_chart_account")
													.toString())));
									it = criteria.list().iterator();
									if (it.hasNext()) {
										vac_chart_accounts vca = (vac_chart_accounts) it
												.next();
										metaValue = String.valueOf(vca.getId());
									}
								}
							} catch (Exception ex) {
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

						vcm.setCreated(svs.getTimeStamp(objData_meta1
								.get("created").toString()));
						vcm.setCompanyMetasKey(objData_meta1.get(
								"companyMetasKey").toString());
						vcm.setModified(svs.getTimeStamp(objData_meta1.get(
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
									svs.getTimeStamp(dataVrs_users.get("created")
											.toString()))
							.setInteger("createdBy", createdBy)
							.setTimestamp(
									"modified",
									svs.getTimeStamp(dataVrs_users.get("modified")
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
									.equals("finance_cash_account")
									|| objData_meta1.get("companyMetasKey")
											.equals("finance_credit_account")
									|| objData_meta1.get("companyMetasKey")
											.equals("finance_spp_account")
									|| objData_meta1.get("companyMetasKey")
											.equals("finance_ssp_account")
									|| objData_meta1.get("companyMetasKey")
											.equals("finance_charges_account")
									|| objData_meta1
											.get("companyMetasKey")
											.equals("finance_books_supplies_account")
									|| objData_meta1
											.get("companyMetasKey")
											.equals("finance_school_uniforms_account")
									|| objData_meta1
											.get("companyMetasKey")
											.equals("finance_registration_form_sales_account")
									|| objData_meta1
											.get("companyMetasKey")
											.equals("finance_business_liabilities_account")
									|| objData_meta1
											.get("companyMetasKey")
											.equals("finance_cost_liabilities_account")
									|| objData_meta1
											.get("companyMetasKey")
											.equals("finance_operating_expense_account")
									|| objData_meta1
											.get("companyMetasKey")
											.equals("finance_service_expense_account")
									|| objData_meta1
											.get("companyMetasKey")
											.equals("finance_re-registration_account")

							) {

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
														vac_chart_accounts.class);
										criteria.add(Restrictions.eq(
												"companyId", vco.getCompanyId()));
										criteria.add(Restrictions.eq(
												"id_composite",
												Integer.valueOf(objData_meta1
														.get("id_composite_vac_chart_account")
														.toString())));
										it = criteria.list().iterator();
										if (it.hasNext()) {
											vac_chart_accounts vca = (vac_chart_accounts) it
													.next();
											metaValue = String.valueOf(vca
													.getId());
										}
									}
								} catch (Exception ex) {
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

							svs.addCompaniesMetas(companyID,
									objData_meta1.get("companyMetasKey")
											.toString(), metaValue,
											svs.getTimeStamp(objData_meta1.get("created")
											.toString()),
											svs.getTimeStamp(objData_meta1.get("modified")
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
}
