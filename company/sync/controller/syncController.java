package verse.sync.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import verse.commonClass.CommonFunction;
import verse.commonClass.getParameterBean;
import verse.sync.service.serviceSync;
import verse.sync.service.serviceSyncVRS;

@Controller
@RequestMapping("/sync")
public class syncController {

	boolean idebugging = true;

	@Autowired
	getParameterBean pb;

	@Autowired
	serviceSync ssy;

	@Autowired
	serviceSyncVRS ssyV;

	CommonFunction cf = new CommonFunction();

	@RequestMapping(value = "/upload-data", method = RequestMethod.GET)
	public ModelAndView canSyncronized(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		String view = "f";
		int compid = Integer.valueOf(request.getHeader("computer_id"));
		String urlSend = pb.getServerCenter()
				+ "/sync/check-server-can-syncronized";
		try {
			URL url = new URL(urlSend);
			URLConnection con = url.openConnection();
			JSONObject obj = new JSONObject();
			obj.put("name", ssy.getComputerName(compid));
			obj.put("usr", ssy.getUser(compid));
			obj.put("pass", ssy.getPassword(compid));
			con.setRequestProperty("compIdentity", obj.toString());

			InputStream content = (InputStream) con.getContent();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					content));
			String line;
			while ((line = in.readLine()) != null) {
				view = line;
			}
			if (view.equalsIgnoreCase("t")) {
				uploadDataAll(request);
			}
		} catch (Exception ex) {
			if (idebugging)
				System.out.println("error in  upload-data "
						+ ex.getMessage());
		}
		model.put("data", view);
		return new ModelAndView("showSyncronize", model);
	}

	@RequestMapping(value = "/check-server-can-syncronized", method = RequestMethod.GET)
	public ModelAndView canSyncronizedonSERver(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		String view = "f";
		try {
			JSONObject obj = new JSONObject(request.getHeader("compIdentity")
					.toString());
			if (ssyV.isCAnSyncIdentity(obj.getString("name"), obj.getString("usr"), obj.getString("pass"))) {
				view = "t";
			}
			model.put("data", view);
		} catch (Exception ex) {
		}
		return new ModelAndView("showSyncronize", model);
	}

	@RequestMapping(value = "/get-my-identity", method = RequestMethod.GET)
	public ModelAndView getMyIdentity(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		String view = "";
		JSONObject obj = new JSONObject();
		int compid = Integer.valueOf(request.getHeader("computer_id"));
		try {
			obj.put("name", ssy.getComputerName(compid));
			obj.put("usr", ssy.getUser(compid));
			obj.put("pass", ssy.getPassword(compid));
			view = obj.toString();
		} catch (Exception ex) {
		}
		model.put("data", view);
		return new ModelAndView("showSyncronize", model);
	}

	int countDataUpload;

	public ModelAndView uploadDataAll(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		int computer_id = ssy.getComputerIdSync();
		ssyV.setDAtaSync(true, computer_id);
		String view = "";
		countDataUpload = ssy.getCountDataUpload(computer_id);
		if (countDataUpload > 0) {
			try {
				uploadData(request);

			} catch (Exception ex) {
				if (idebugging)
					System.out.println("error when upload " + ex.getMessage());
			}
			view = "Upload";
		} else {
			view = "Data Empty";
		}
		model.put("data", view);
		ssyV.setDAtaSync(false, computer_id);
		return new ModelAndView("showSyncronize", model);
	}

	@RequestMapping(value = "/set-vrs-sync-log", method = RequestMethod.GET)
	public ModelAndView set_vrs_sync_log(HttpServletRequest request) {
		String view = "";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String computerName = request.getHeader("computer"), user = request
					.getHeader("user"), password = request
					.getHeader("password");
			if (ssy.authenticationCheck(computerName, user, password)) {
				int computer_id = Integer.valueOf(request.getHeader(
						"computer_id").toString());
				int company_id = Integer.valueOf(request
						.getHeader("company_id").toString());
				String type = request.getHeader("type").toString();
				String dataReceive = request.getHeader("uploadData");
				if (dataReceive.length() > 0)
					ssyV.addVrs_Sync_log(dataReceive, company_id, computer_id,
							type);
			} else {
				view = "Authentication Wrong";
			}

		} catch (Exception ex) {
			view = "Wrong " + ex.getMessage();
		}

		model.put("data", view);
		return new ModelAndView("showSyncronize", model);
	}

	void set_vrs_syng_logAtServer(String data, int company_id, String type) {
		String urlSend = pb.getServerCenter() + "/sync/set-vrs-sync-log";
		int computer_id = ssy.getComputerIdSync();
		try {
			URL url = new URL(urlSend);
			URLConnection con = url.openConnection();
			con.setRequestProperty("computer_id", String.valueOf(computer_id));
			con.setRequestProperty("company_id", String.valueOf(company_id));
			con.setRequestProperty("type", type);
			con.setRequestProperty("computer", ssy.getComputerName(computer_id));
			con.setRequestProperty("user", ssy.getUser(computer_id));
			con.setRequestProperty("password", ssy.getPassword(computer_id));
			con.setRequestProperty("uploadData", data);

			con.setDoOutput(true);

			InputStream content = (InputStream) con.getContent();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					content));
			String view = "", line;
			while ((line = in.readLine()) != null) {
				view += line;
			}
		} catch (Exception ex) {
			if (idebugging)
				System.out.println("error in  set_vrs_syng_logAtServer "
						+ ex.getMessage());
		}
	}

	void uploadData(HttpServletRequest request) {
		try {
			String view = "";
			String urlSend = pb.getServerCenter() + "/sync/receive-upload";
			URL url = new URL(urlSend);
			URLConnection con = url.openConnection();

			int computer_id = ssy.getComputerIdSync();
			con.setRequestProperty("computer", ssy.getComputerName(computer_id));
			con.setRequestProperty("user", ssy.getUser(computer_id));
			con.setRequestProperty("password", ssy.getPassword(computer_id));
			String dataUpload = ssy.getDataREadyUpload();
			con.setRequestProperty("uploadData", dataUpload);
			con.setDoOutput(true);
			InputStream content = (InputStream) con.getContent();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					content));
			String line;
			while ((line = in.readLine()) != null) {
				view += line;
			}

			if (view.length() > 0) {
				ssy.updateDataVrs_sync_items(view);
				int company_idComposite = ssy
						.getid_compositeFromvrs_companies(cf
								.getCompanyID(request));
				set_vrs_syng_logAtServer(view, company_idComposite, "upload");
				ssyV.setDAteSyncronize(computer_id);

				countDataUpload--;
				if (countDataUpload > 0) {
					try {
						cf.viewAlert(" call uploadData");
						uploadData(request);
					} catch (Exception ex) {
						if (idebugging)
							System.out.println("error when upload "
									+ ex.getMessage());
					}
					view = "Upload";
				} else {
					view = "Data Empty";
				}
			}
		} catch (Exception ex) {
			if (cf.idebugging)
				System.out.println(" error in  uploadData " + ex.getMessage());
		}
	}

	@RequestMapping(value = "/receive-upload", method = RequestMethod.GET)
	public ModelAndView receiveData(HttpServletRequest request) {
		String view = "";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String computerName = request.getHeader("computer"), user = request
					.getHeader("user"), password = request
					.getHeader("password");
			int comp_id = ssyV.getComputerIdOnSErver(
					request.getHeader("computer").toString(), request
							.getHeader("user").toString(),
					request.getHeader("password").toString());

			if (ssy.authenticationCheck(computerName, user, password)) {
				String dataReceive = request.getHeader("uploadData");
				if (dataReceive.length() > 0)
					view = ssy.receiveUploadData(dataReceive, comp_id);
				ssyV.setDAteSyncronize(comp_id);
			} else {
				view = "Authentication Wrong";
			}

		} catch (Exception ex) {
			view = "Wrong receiveData " + ex.getMessage();
		}
		model.put("data", view);
		return new ModelAndView("showSyncronize", model);
	}

	void addSyncComputerList(int comp_id, String id_vrs_sync_items) {
		String urlSend = pb.getServerCenter() + "/sync/add-sync-computer-list";
		try {
			String view = "";
			URL url = new URL(urlSend);
			URLConnection con = url.openConnection();
			int computer_id = ssy.getComputerIdSync();
			con.setRequestProperty("computer", ssy.getComputerName(computer_id));
			con.setRequestProperty("user", ssy.getUser(computer_id));
			con.setRequestProperty("password", ssy.getPassword(computer_id));
			con.setRequestProperty("action", "add_vrs_sync_items");
			con.setRequestProperty("id", id_vrs_sync_items);
			con.setDoOutput(true);
			InputStream content = (InputStream) con.getContent();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					content));
			String line;
			while ((line = in.readLine()) != null) {
				view += line;
			}
		} catch (Exception ex) {

		}
	}

	public String get_id_vrs_sync_items(String data) {
		String getBack = "";
		try {
			JSONObject obj = new JSONObject(data);
			getBack = obj.get("id_vrs_sync_items").toString();
		} catch (Exception ex) {

		}

		if (getBack.length() < 1) {
			JSONObject objGetBack = new JSONObject();
			try {
				JSONObject obj = new JSONObject(data);
				String tableName = obj.get("tableName").toString();
				obj = new JSONObject(obj.get("dataThis").toString());
				if (tableName.equalsIgnoreCase("students")) {
					int i = 0;
					JSONObject obj1 = new JSONObject(obj.get("vrs_users" + i)
							.toString());

					objGetBack.put("tableName", "student");

					objGetBack.put("idvrs_users" + i,
							obj1.get("id_vrs_sync_items").toString());
					i++;
					obj1 = new JSONObject(obj.get("vrs_roles" + i).toString());
					objGetBack.put("idvrs_roles" + i,
							obj1.get("id_vrs_sync_items").toString());

					boolean notError = true;
					try {
						i++;
						obj1 = new JSONObject(obj.get("ved_candidates" + i)
								.toString());
						objGetBack.put("idved_candidates" + i,
								obj1.get("id_vrs_sync_items").toString());
						i++;
						obj1 = new JSONObject(obj.get("vac_transactions" + i)
								.toString());
						objGetBack.put("idvac_transactions" + i,
								obj1.get("id_vrs_sync_items").toString());
						i++;
						obj1 = new JSONObject(obj.get(
								"vac_transaction_details" + i).toString());
						objGetBack.put("idvac_transaction_details" + i, obj1
								.get("id_vrs_sync_items").toString());
						i++;
						obj1 = new JSONObject(obj.get("vac_postings" + i)
								.toString());
						objGetBack.put("idvac_postings" + i,
								obj1.get("id_vrs_sync_items").toString());
						i++;
						obj1 = new JSONObject(obj.get("vac_postings" + i)
								.toString());
						objGetBack.put("idvac_postings" + i,
								obj1.get("id_vrs_sync_items").toString());
						i++;
						obj1 = new JSONObject(obj.get("ved_students" + i)
								.toString());
						objGetBack.put("idved_students" + i,
								obj1.get("id_vrs_sync_items").toString());

						boolean looping = true;
						while (looping) {
							try {
								i++;
								obj1 = new JSONObject(obj.get(
										"vac_transactions" + i).toString());
								objGetBack.put("idvac_transactions" + i, obj1
										.get("id_vrs_sync_items").toString());
								i++;
								obj1 = new JSONObject(obj.get(
										"vac_transaction_details" + i)
										.toString());
								objGetBack.put("idvac_transaction_details" + i,
										obj1.get("id_vrs_sync_items")
												.toString());
								i++;
								obj1 = new JSONObject(obj.get(
										"vac_postings" + i).toString());
								objGetBack.put("idvac_postings" + i,
										obj1.get("id_vrs_sync_items")
												.toString());
								i++;
								obj1 = new JSONObject(obj.get(
										"vac_postings" + i).toString());
								objGetBack.put("idvac_postings" + i,
										obj1.get("id_vrs_sync_items")
												.toString());
							} catch (Exception ex) {
								looping = false;
							}
						}

						try {
							i++;
							obj1 = new JSONObject(obj.get("ved_candidates" + i)
									.toString());
							objGetBack.put("idved_candidates" + i,
									obj1.get("id_vrs_sync_items").toString());
						} catch (Exception ex) {
						}
					} catch (Exception ex) {
						notError = false;
					}

					if (!notError) {
						boolean looping = true;
						while (looping) {
							try {
								obj1 = new JSONObject(obj.get("vrs_users" + i)
										.toString());
								objGetBack.put("idvrs_users" + i,
										obj1.get("id_vrs_sync_items")
												.toString());
								i++;
								obj1 = new JSONObject(obj.get("vrs_roles" + i)
										.toString());
								objGetBack.put("idvrs_roles" + i,
										obj1.get("id_vrs_sync_items")
												.toString());
								i++;
							} catch (Exception ex) {
								looping = false;
							}
						}
					}
				} else if (tableName.equalsIgnoreCase("transactions1")) {
					int i = 0;
					boolean looping = true;
					JSONObject obj1 = new JSONObject();
					objGetBack.put("tableName", tableName);
					while (looping) {
						try {
							obj1 = new JSONObject(obj.get(
									"vac_transactions" + i).toString());
							objGetBack.put("idvac_transactions" + i,
									obj1.get("id_vrs_sync_items").toString());
							i++;
							obj1 = new JSONObject(obj.get(
									"vac_transaction_details" + i).toString());
							objGetBack.put("idvac_transaction_details" + i,
									obj1.get("id_vrs_sync_items").toString());
							i++;
							obj1 = new JSONObject(obj.get("vac_postings" + i)
									.toString());
							objGetBack.put("idvac_postings" + i,
									obj1.get("id_vrs_sync_items").toString());
							i++;
							obj1 = new JSONObject(obj.get("vac_postings" + i)
									.toString());
							objGetBack.put("idvac_postings" + i,
									obj1.get("id_vrs_sync_items").toString());
							i++;
						} catch (Exception ex) {
							looping = false;
						}
					}
					try {
						obj1 = new JSONObject(obj.get("ved_candidates" + i)
								.toString());
						objGetBack.put("idved_candidates" + i,
								obj1.get("id_vrs_sync_items").toString());
					} catch (Exception ex) {
					}
				}

			} catch (Exception ex) {

			}
			getBack = objGetBack.toString();
		}
		return getBack;
	}

	@RequestMapping(value = "/download-data/{id}/{companyID}", method = RequestMethod.GET)
	public ModelAndView canSyncronized1(@PathVariable("id") int id,
			@PathVariable("companyID") int companyID, HttpServletRequest req) {
		Map<String, Object> model = new HashMap<String, Object>();
		String view = "f";
		int compid = Integer.valueOf(req.getHeader("computer_id"));
		String urlSend = pb.getServerCenter()
				+ "/sync/check-server-can-syncronized";
		try {
			URL url = new URL(urlSend);
			URLConnection con = url.openConnection();
			JSONObject obj = new JSONObject();
			obj.put("name", ssy.getComputerName(compid));
			obj.put("usr", ssy.getUser(compid));
			obj.put("pass", ssy.getPassword(compid));
			con.setRequestProperty("compIdentity", obj.toString());

			InputStream content = (InputStream) con.getContent();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					content));
			String line;
			while ((line = in.readLine()) != null) {
				view = line;
			}
			if (view.equalsIgnoreCase("t")) {
				downloadData(id, companyID, req);
			}
		} catch (Exception ex) {
			if (idebugging)
				System.out.println("error in  canSyncronized1 "
						+ ex.getMessage());
		}
		model.put("data", view);
		return new ModelAndView("showSyncronize", model);
	}

	public ModelAndView downloadData(int id, int companyID,
			HttpServletRequest req) {
		int computer_id = ssy.getComputerIdSync();
		Map<String, Object> model = new HashMap<String, Object>();
		String view = "";
		ssyV.setDAtaSync(true, computer_id);
		String urlSend = pb.getServerCenter() + "/sync/ask-limit-download";

		try {
			URL url = new URL(urlSend);
			URLConnection con = url.openConnection();

			con.setRequestProperty("computer", ssy.getComputerName(computer_id));
			con.setRequestProperty("computer_id", String.valueOf(computer_id));
			con.setRequestProperty("user", ssy.getUser(computer_id));
			con.setRequestProperty("password", ssy.getPassword(computer_id));
			int user_idComposite = ssy.getUser_idComposite(id);
			con.setRequestProperty("user_id", String.valueOf(user_idComposite));

			con.setDoOutput(true);

			InputStream content = (InputStream) con.getContent();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					content));
			String line;
			while ((line = in.readLine()) != null) {
				view += line;
			}

			if (Integer.valueOf(view) > 0) {
				syncDownload(id, companyID);
				view = "Download";
			} else {
				view = "Empty Download";
			}

			model.put("data", view);
		} catch (Exception ex) {
			if (idebugging)
				System.out.println(" error in downloadData " + ex.getMessage());
		}
		ssyV.setDAtaSync(false, computer_id);

		return new ModelAndView("showSyncronize", model);
	}

	@RequestMapping(value = "/ask-limit-download", method = RequestMethod.GET)
	public ModelAndView ask_limit_download(HttpServletRequest request) {
		String view = "";
		Map<String, Object> model = new HashMap<String, Object>();
		int user_id = ssy.getUser_idfromCompositeID(Integer.valueOf(request
				.getHeader("user_id").toString()));

		int comp_id = ssyV.getComputerIdOnSErver(request.getHeader("computer")
				.toString(), request.getHeader("user").toString(), request
				.getHeader("password").toString());
		view = String.valueOf(ssy.getLimitDownload(user_id, comp_id));
		model.put("data", view);
		ssyV.setDAteSyncronize(comp_id);
		return new ModelAndView("showSyncronize", model);
	}

	void syncDownload(int userID, int companyID) {
		String view = "";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String urlSend = pb.getServerCenter() + "/sync/ask-download";
			URL url = new URL(urlSend);
			URLConnection con = url.openConnection();
			int computer_id = ssy.getComputerIdSync();

			con.setRequestProperty("computer", ssy.getComputerName(computer_id));
			con.setRequestProperty("user", ssy.getUser(computer_id));
			con.setRequestProperty("password", ssy.getPassword(computer_id));
			int user_idComposite = ssy.getUser_idComposite(userID);
			con.setRequestProperty("user_id", String.valueOf(user_idComposite));
			con.setDoOutput(true);

			InputStream content = (InputStream) con.getContent();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					content));
			String line;
			while ((line = in.readLine()) != null) {
				view += line;
			}
			if (view.length() > 0) {
				if (ssy.saveDAtaFromDownload(view)) {
					addSyncComputerList(computer_id,
							get_id_vrs_sync_items(view));
				}
				int company_idComposite = ssy
						.getid_compositeFromvrs_companies(companyID);
				set_vrs_syng_logAtServer(checkForLog(view),
						company_idComposite, "download");
				ssyV.setDAteSyncronize(computer_id);
				
				cf.viewAlert("call sync download");
				syncDownload(userID, companyID);
			}
		} catch (Exception ex) {
			if (idebugging)
				System.out.println(" error in syncDownload " + ex.getMessage());
		}
	}

	String checkForLog(String str) {
		String getBack = "";
		boolean analize = true;
		try {
			JSONObject obj = new JSONObject(str);
			getBack = obj.get("id_vrs_sync_items").toString();
			analize = false;
		} catch (Exception ex) {
		}
		if (analize) {
			try {
				JSONObject obj = new JSONObject(str);
				JSONObject objBack = new JSONObject();
				String tableName = obj.get("tableName").toString();

				if (tableName.equalsIgnoreCase("students")) {
					obj = new JSONObject(obj.get("dataThis").toString());

					objBack.put("tableName", "student");

					JSONObject objData = new JSONObject(obj.get("vrs_users0")
							.toString());
					objBack.put("vrs_users0", objData.get("id_vrs_sync_items")
							.toString());
					objData = new JSONObject(obj.get("vrs_roles1").toString());
					objBack.put("vrs_roles1", objData.get("id_vrs_sync_items")
							.toString());
					objData = new JSONObject(obj.get("ved_candidates2")
							.toString());
					objBack.put("ved_candidates2",
							objData.get("id_vrs_sync_items").toString());
					objData = new JSONObject(obj.get("vac_transactions3")
							.toString());
					objBack.put("vac_transactions3",
							objData.get("id_vrs_sync_items").toString());
					objData = new JSONObject(obj
							.get("vac_transaction_details4").toString());
					objBack.put("vac_transaction_details4",
							objData.get("id_vrs_sync_items").toString());
					objData = new JSONObject(obj.get("vac_postings5")
							.toString());
					objBack.put("vac_postings5",
							objData.get("id_vrs_sync_items").toString());
					objData = new JSONObject(obj.get("vac_postings6")
							.toString());
					objBack.put("vac_postings6",
							objData.get("id_vrs_sync_items").toString());
					objData = new JSONObject(obj.get("ved_students7")
							.toString());
					objBack.put("ved_students7",
							objData.get("id_vrs_sync_items").toString());

					int i = 8;
					boolean looping = true;
					while (looping) {
						try {
							objData = new JSONObject(obj.get(
									"vac_transactions" + i).toString());
							objBack.put("vac_transactions" + i,
									objData.get("id_vrs_sync_items").toString());
							i++;
							objData = new JSONObject(obj.get(
									"vac_transaction_details" + i).toString());
							objBack.put("vac_transaction_details" + i, objData
									.get("id_vrs_sync_items").toString());
							i++;
							objData = new JSONObject(obj
									.get("vac_postings" + i).toString());
							objBack.put("vac_postings" + i,
									objData.get("id_vrs_sync_items").toString());
							i++;
							objData = new JSONObject(obj
									.get("vac_postings" + i).toString());
							objBack.put("vac_postings" + i,
									objData.get("id_vrs_sync_items").toString());
							i++;
						} catch (Exception ex) {
							looping = false;
						}
					}

					try {
						objData = new JSONObject(obj.get("ved_candidates" + i)
								.toString());
						objBack.put("ved_candidates" + i,
								objData.get("id_vrs_sync_items").toString());
					} catch (Exception ex) {
					}

				} else if (tableName.equalsIgnoreCase("transactions1")) {
					int i = 0;
					JSONObject objData = new JSONObject();
					objBack.put("tableName", tableName);
					boolean looping = true;
					while (looping) {
						try {
							objData = new JSONObject(obj.get(
									"vac_transactions" + i).toString());
							objBack.put("vac_transactions" + i,
									objData.get("id_vrs_sync_items").toString());
							i++;
							objData = new JSONObject(obj.get(
									"vac_transaction_details" + i).toString());
							objBack.put("vac_transaction_details" + i, objData
									.get("id_vrs_sync_items").toString());
							i++;
							objData = new JSONObject(obj
									.get("vac_postings" + i).toString());
							objBack.put("vac_postings" + i,
									objData.get("id_vrs_sync_items").toString());
							i++;
							objData = new JSONObject(obj
									.get("vac_postings" + i).toString());
							objBack.put("vac_postings" + i,
									objData.get("id_vrs_sync_items").toString());
							i++;
						} catch (Exception ex) {
							looping = false;
						}
					}

					try {
						objData = new JSONObject(obj.get("ved_candidates" + i)
								.toString());
						objBack.put("ved_candidates" + i,
								objData.get("id_vrs_sync_items").toString());
					} catch (Exception ex) {
					}

				}
				getBack = objBack.toString();
			} catch (Exception ex) {
			}
		}
		return getBack;
	}

	@RequestMapping(value = "/ask-download", method = RequestMethod.GET)
	public ModelAndView askDownload(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String computerName = request.getHeader("computer"), user = request
					.getHeader("user"), password = request
					.getHeader("password");
			int user_id = ssy.getUser_idfromCompositeID(Integer.valueOf(request
					.getHeader("user_id").toString()));
			if (ssy.authenticationCheck(computerName, user, password)) {
				int computer_id = ssyV.getComputerIdOnSErver(
						request.getHeader("computer").toString(), request
								.getHeader("user").toString(), request
								.getHeader("password").toString());
				model.put("data", ssy.getDAtaDownload(user_id, computer_id));
			}
		} catch (Exception ex) {
		}
		return new ModelAndView("showSyncronize", model);
	}

	@RequestMapping(value = "/add-sync-computer-list", method = RequestMethod.GET)
	public ModelAndView addSyncCompList(HttpServletRequest request) {
		String view = "add items";
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String computerName = request.getHeader("computer"), user = request
					.getHeader("user"), password = request
					.getHeader("password");
			int comp_id = ssyV.getComputerIdOnSErver(
					request.getHeader("computer").toString(), request
							.getHeader("user").toString(),
					request.getHeader("password").toString());
			if (ssy.authenticationCheck(computerName, user, password)) {
				String id = request.getHeader("id").toString();
				if (cf.isNumeric(id)) {
					ssy.addComputerList(comp_id, Integer.valueOf(id));
				} else {
					ssyV.addComputerListString(comp_id, id);
				}

			}
		} catch (Exception ex) {
		}
		model.put("data", view);
		return new ModelAndView("showSyncronize", model);
	}

	public String getLokalURL() {
		return pb.getLocalComputer();
	}

	@RequestMapping(value = "/sync-up-to-date", method = RequestMethod.POST)
	public ModelAndView syncUpdate(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("CompleteSync", model);
	}

	@RequestMapping(value = "/sync-running", method = RequestMethod.POST)
	public ModelAndView syncRunnning(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("runningSync", model);
	}

	@RequestMapping(value = "/no-internet", method = RequestMethod.POST)
	public ModelAndView noInternet(HttpServletRequest req) {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("noInternet", model);
	}

	@RequestMapping(value = "/pause-sync", method = RequestMethod.POST)
	public ModelAndView pauseSync(HttpServletRequest req) {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("pauseSync", model);
	}
}
