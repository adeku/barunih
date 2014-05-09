package verse.company.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import verse.model.RoleName;
import verse.model.RoleStaff;
import verse.model.vrs_companies;
import verse.model.vrs_company_metas;
import verse.model.vrs_logs;
import verse.model.vrs_role_metas;
import verse.model.vrs_roles;
import verse.model.vrs_accounts;
import verse.model.vrs_user_metas;
import verse.model.vrs_users;
import verse.people.model.Employee;
import verse.upload.model.vrs_file_library;

import verse.academic.course.model.addCourseListModel;
import verse.academic.model.ved_assignments;
import verse.academic.model.ved_attendances;
import verse.academic.model.ved_candidate_metas;
import verse.academic.model.ved_candidates;
import verse.accounting.budget.model.BudgetForm;
import verse.accounting.budget.service.BudgetService;
import verse.accountsadmin.companies.model.vw_firstname_vrs_users;
import verse.accountsadmin.companies.services.serviceCompanies;
import verse.commonClass.CommonFunction;
import verse.commonClass.emailClassService;
import verse.commonClass.getParameterBean;
import verse.company.model.AddUser;
import verse.company.model.CompanyList;
import verse.company.model.CompanyUserList;
import verse.company.model.ViewSchoolList;
import verse.company.model.addSchollModel;
import verse.company.model.editSchollModel;
import verse.company.service.CompanyService;

import org.apache.commons.collections.map.HashedMap;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import org.json.JSONObject;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

@Service("companyService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class CompanyServiceImpl implements CompanyService {

	boolean idebugging = true;
	String message = "";
	@Autowired
	private SessionFactory sessionFactory;

	String prefix;

	@Autowired
	private emailClassService emailService;

	CommonFunction common = new CommonFunction();

	public CompanyServiceImpl() {
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addCompany(vrs_companies company, vrs_company_metas comp_metas,
			vrs_roles role) {

		Timestamp tstamp = new Timestamp(new Date().getTime());

		// Set Company Field
		company.setCreatedBy(0);
		company.setModifiedBy(0);
		company.setCreated(tstamp);
		company.setModified(tstamp);
		// company.setParent(getHeadSchool(userId));

		// Save Company
		sessionFactory.getCurrentSession().saveOrUpdate(company);

		// Set Role Field
		role.setCompanyId(company.getCompanyId());
		role.setRole("Admin");
		role.setCreated(tstamp);
		role.setModified(tstamp);
		// Save Role
		sessionFactory.getCurrentSession().saveOrUpdate(role);

		company.setCreatedBy(role.getRoleId());
		company.setModifiedBy(role.getRoleId());
		// Update Company
		sessionFactory.getCurrentSession().saveOrUpdate(company);

		// Set Company Metas Field
		comp_metas.setCompanyId(company.getCompanyId());
		comp_metas.setCreated(tstamp);
		comp_metas.setModified(tstamp);
		// Save Company Meta
		sessionFactory.getCurrentSession().saveOrUpdate(comp_metas);

	}

	public int getRoleForeignKey(int company, int user_id) {
		int getBack = 0;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_roles.class);
		criteria.add(Restrictions.eq("userId", user_id));
		criteria.add(Restrictions.eq("companyId", company));
		List results = criteria.list();
		Iterator it = results.iterator();
		if (it.hasNext()) {
			vrs_roles vrslooping = (vrs_roles) it.next();
			getBack = vrslooping.getRoleId();
		}
		return getBack;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addSchool(addSchollModel OaddScholl, HttpServletRequest request) {
		try {
			Timestamp tstamp = new Timestamp(new Date().getTime());
			message = "";

			CommonFunction common = new CommonFunction();
			int parentId = common.getCompanyID(request);
			int roleIDSElected = common.getRoleIDSelected(request);

			// add Company Field
			vrs_companies company = new vrs_companies();
			company.setCreatedBy(roleIDSElected);
			company.setModifiedBy(roleIDSElected);
			company.setCompanyName(OaddScholl.getName());
			company.setParent(parentId);
			company.setCompanyType("School");
			company.setCreated(tstamp);
			company.setStatus(1);
			company.setModified(tstamp);
			sessionFactory.getCurrentSession().saveOrUpdate(company);
			int company_id = company.getCompanyId();

			// add company metas
			addCompanyMetas(company.getCompanyId(), "address",
					OaddScholl.getAddress());

			addCompanyMetas(company.getCompanyId(), "city",
					OaddScholl.getCity());

			addCompanyMetas(company.getCompanyId(), "province",
					OaddScholl.getProvince());


			
			addCompanyMetas(company.getCompanyId(), "level",
					OaddScholl.getLevel());

			addCompanyMetas(company.getCompanyId(), "phone",
					OaddScholl.getPhone());

			addCompanyMetas(company.getCompanyId(), "email",
					OaddScholl.getEmail());

			addCompanyMetas(company.getCompanyId(), "website",
					OaddScholl.getWebsite());

			if (OaddScholl.getHeadmaster() != null) {
				if (OaddScholl.getHeadmaster().length() > 0) {
					vrs_roles vrl = new vrs_roles();
					vrl.setCompanyId(company_id);
					vrl.setCreated(tstamp);
					vrl.setModified(tstamp);
					vrl.setRole("Employee");
					vrl.setStatus(1);
					vrl.setUserId(Integer.valueOf(OaddScholl.getHeadmaster()));
					sessionFactory.getCurrentSession().save(vrl);
					int role_id = vrl.getRoleId();

					sco.addRolesMetas(role_id, "Academic_Position",
							"Headmaster");
					String hql = "insert into  vrs_role_metas (roleId,roleMetasKey,roleMetasValue,created,modified) select "
							+ role_id
							+ ",key,value,now(),now() "
							+ "from vrs_user_metas where user_id=:user_id";
					sessionFactory
							.getCurrentSession()
							.createQuery(hql)
							.setInteger("user_id",
									Integer.valueOf(OaddScholl.getHeadmaster()))
							.executeUpdate();
					hql = "update vrs_companies set modified=now(),modifiedBy=:modifiedBy where companyId=:companyId";
					sessionFactory.getCurrentSession().createQuery(hql)
							.setInteger("modifiedBy", roleIDSElected)
							.setInteger("companyId", company_id)
							.executeUpdate();
					addCompanyMetas(company.getCompanyId(),
							"School_headmaster", String.valueOf(role_id));
				}
			}
			
			addCompanyMetas(company.getCompanyId(), "School_registrationFormPrice",
					OaddScholl.getRegistrationFormPrice());

			
			addCompanyMetas(company.getCompanyId(), "School_baseSPPBasic",
					OaddScholl.getBaseSPPBasic());
			addCompanyMetas(company.getCompanyId(), "School_baseSPPOwnership",
					OaddScholl.getBaseSPPOwnership());
			addCompanyMetas(company.getCompanyId(), "School_baseSPPEmployee",
					OaddScholl.getBaseSPPEmployee());
			

			addCompanyMetas(company.getCompanyId(), "School_baseSSPBasic",
					OaddScholl.getBaseSSPBasic());
			addCompanyMetas(company.getCompanyId(), "School_baseSSPContinuation",
					OaddScholl.getBaseSSPContinuation());
			addCompanyMetas(company.getCompanyId(), "School_baseSSPOwnership",
					OaddScholl.getBaseSSPOwnership());
			addCompanyMetas(company.getCompanyId(), "School_baseSSPEmployee",
					OaddScholl.getBaseSSPEmployee());

			
			addCompanyMetas(company.getCompanyId(), "fax", OaddScholl.getFax());

			for (int i = 0; i < OaddScholl.getBaseSSP().size(); i++) {
				addCompanyMetas(company.getCompanyId(), "School_baseSSP"
						+ String.valueOf(i),
						(OaddScholl.getBaseSSP().get(i)).toString());
			}

			// add file upload
			
			try {

				String extension = OaddScholl.getFileType();
				if (extension.startsWith("image")) {
					String fileName = OaddScholl.getFileName();
					String destination = fileName;

					File file = new File(destination);
					MultipartFile mpf = OaddScholl.getFileCompany();
					mpf.transferTo(file);

					if (file.isFile()) {
						BufferedImage originalImage = ImageIO.read(file);
						int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB
								: originalImage.getType();
						BufferedImage resizeImageJpg = common.resizeImageWithHint(
								originalImage, type);
						ImageIO.write(
								resizeImageJpg,
								file.getName().substring(
										file.getName().indexOf(".") + 1), file);
						destination = "company/imageDocument/";
						AmazonS3 s3 = new AmazonS3Client(new BasicAWSCredentials(
								parameterBeans.getAwsaccess(),
								parameterBeans.getAwssecret()));
						boolean exist = true;
						String fileNameNew = fileName;
						String md5File = "";
						while (exist) {
							try {
								S3Object object = s3
										.getObject(new GetObjectRequest(
												parameterBeans.getAwsbucket(),
												destination + fileNameNew));
							} catch (Exception ex) {
								Criteria criteria = sessionFactory
										.getCurrentSession().createCriteria(
												vrs_file_library.class);
								criteria.add(Restrictions.eq("file_name",
										fileNameNew));
								if (criteria.list().size() < 1)
									exist = false;
							}
							if (exist) {
								Date dt = new Date();
								md5File = common.MD5Encode(fileNameNew + dt.toString());
								if (md5File.length() > 5)
									md5File = md5File.substring(0, 5);
								fileNameNew = fileName.substring(0,
										fileName.lastIndexOf("."))
										+ "_"
										+ md5File

										+ fileName.substring(
												fileName.lastIndexOf("."),
												fileName.length());
							}
						}
						PutObjectRequest por = new PutObjectRequest(
								parameterBeans.getAwsbucket(), destination
										+ fileNameNew, file);
						s3.putObject(por);
						vrs_file_library vfl = new vrs_file_library();
						vfl.setCompany_id(company_id);
						vfl.setDescription("Company Photo");
						vfl.setFile_name(fileNameNew);
						vfl.setFile_type(extension);
						sessionFactory.getCurrentSession().save(vfl);
						addCompanyMetas(company.getCompanyId(), "CompanyPhoto",
								String.valueOf(vfl.getId()));
						file.delete();
					}
				}
				
				//add session company on left
				common.addCompanyOptioninSeidebar(company_id, OaddScholl.getName(), OaddScholl.getCity(), request);
				//end add session company on left

			} catch (Exception ex) {
				if (common.idebugging)
					System.out.println("error in uploadFileToAWSForcompany = "
							+ ex.getMessage());
			}
			
			// end add file upload
			message = "School Added";
		} catch (Exception ex) {
			System.out.println("message = " + ex.getMessage());
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void editSchool(addSchollModel OaddScholl, HttpServletRequest request) {
		message = "";
		CommonFunction common = new CommonFunction();
		int parent = common.getCompanyID(request);
		int role_id = 74;
		int company_id = common.getCompanyID(request);

		int userId = Integer.valueOf(request.getSession()
				.getAttribute("u9988u").toString());
		

		boolean CanAdd = true;
		message += checkAlreadyUseByAnotherCompany(company_id, "phone",
				OaddScholl.getPhone());
		message += checkAlreadyUseByAnotherCompany(company_id, "email",
				OaddScholl.getEmail());
		message += checkHeadMasterExist(OaddScholl.getHeadmaster());
		if (message.length() > 0) {
			message = " School Can't Add Because <br/>" + message;
			CanAdd = false;
		}

		if (CanAdd) {
			// add Company Field

			String hqlUpdate = "";
			Session sesi = sessionFactory.getCurrentSession();

			if (OaddScholl.getName().length() > 0) {

				hqlUpdate = "update vrs_companies set modifiedBy=:usr,modified=now(),companyName=:company_name,parent=:parent where companyId=:company_id";
				sesi = sessionFactory.getCurrentSession();
				sesi.createQuery(hqlUpdate)
						.setInteger("company_id", company_id)
						.setString("company_name", OaddScholl.getName())
						.setInteger("usr", role_id)
						.setInteger("parent", parent).executeUpdate();
			} else {
				hqlUpdate = "update vrs_companies set modifiedBy=:usr,modified=now(),companyName=:company_name,parent=:parent where companyId=:company_id";
				sesi = sessionFactory.getCurrentSession();
				sesi.createQuery(hqlUpdate)
						.setInteger("company_id", company_id)
						.setString("company_name", OaddScholl.getName())
						.setInteger("parent", parent).setInteger("usr", userId)
						.executeUpdate();

				
			}

			// add company metas
			editCompanyMetas(company_id, "address", OaddScholl.getAddress());
			

			editCompanyMetas(company_id, "city", OaddScholl.getCity());
			

			editCompanyMetas(company_id, "province", OaddScholl.getProvince());


			editCompanyMetas(company_id, "level", OaddScholl.getLevel());

			editCompanyMetas(company_id, "School_headmaster",
					OaddScholl.getHeadmaster());


			editCompanyMetas(company_id, "telephone", OaddScholl.getPhone());

			editCompanyMetas(company_id, "email", OaddScholl.getEmail());

			editCompanyMetas(company_id, "fax", OaddScholl.getFax());

			editCompanyMetas(company_id, "website", OaddScholl.getWebsite());

			message = "School Editted";
		}
	}

	public String checkAlreadyUseByAnotherCompany(int comp_id, String key,
			String value) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_company_metas.class);
		criteria.add(Restrictions.not(Restrictions.eq("companyId", comp_id)));
		criteria.add(Restrictions.eq("companyMetasKey", key));
		criteria.add(Restrictions.eq("companyMetasValue", value));
		List results = criteria.list();
		Iterator it = results.iterator();
		if (it.hasNext()) {
			getBack += " <br/> This  " + key + " = " + value + " Already Exist";
			vrs_company_metas vrslooping = (vrs_company_metas) it.next();
		}
		return getBack;
	}

	public Map<String, String> getSchoolEdited(HttpServletRequest request) {
		Map<String, String> getBack = new HashMap<String, String>();
		try {
			// select * from vrs_companies where parent in (select company_id
			// from vrs_roles where user_id=40 and role='Admin')
			int parent = 2;
			int userId = Integer.valueOf(request.getSession()
					.getAttribute("u9988u").toString());

			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(vrs_companies.class);
			criteria.add(Restrictions.eq("parent", parent));
			criteria.add(Restrictions.eq("status", 0));
			List results = criteria.list();
			Iterator it = results.iterator();
			if (it.hasNext()) {
				vrs_companies vrslooping = (vrs_companies) it.next();
				getBack.put(String.valueOf(vrslooping.getCompanyId()),
						vrslooping.getCompanyName());
			}
		} catch (Exception ex) {
			if (idebugging)
				System.out
						.println(" error in company service implement getSchoolEdited "
								+ ex.getMessage());
		}

		return getBack;
	}

	public Map<String, String> getHeadMasterList(HttpServletRequest request) {
		Map<String, String> getBack = new LinkedHashMap<String, String>();
		CommonFunction common = new CommonFunction();

		DetachedCriteria subCriteria = DetachedCriteria
				.forClass(vrs_roles.class);
		subCriteria.add(Restrictions.eq("status", 1));
		subCriteria.add(Restrictions.eq("companyId",
				common.getCompanyID(request)));
		subCriteria.setProjection(Projections.property("userId"));
		
		int companyPArent = common.getCompanyParent(request, sessionFactory.getCurrentSession());
		List<Integer> companyIds = new ArrayList<Integer>();
		companyIds = common.getCompaniesChildren(sessionFactory.getCurrentSession(), companyPArent, companyIds);
		subCriteria.add(Restrictions.in("companyId", companyIds));
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vw_firstname_vrs_users.class);
		criteria.add(Property.forName("id").in(subCriteria));
		criteria.addOrder(Order.asc("firstname"));

		Iterator it = criteria.list().iterator();
		getBack.put("", "Select Headmaster");
		String namePeople = "";
		while (it.hasNext()) {
			vw_firstname_vrs_users vrslooping = (vw_firstname_vrs_users) it
					.next();
			namePeople = "";
			if (vrslooping.getFirstname() != null)
				namePeople = vrslooping.getFirstname();
			if (vrslooping.getLastname() != null) {
				if (namePeople.length() > 0)
					namePeople += " ";
				namePeople += vrslooping.getLastname();
			}
			if (namePeople.length()>0)
			getBack.put(String.valueOf(vrslooping.getId()), namePeople);
		}

		return getBack;
	}

	public Map<String, String> getTeacher() {
		Map<String, String> getBack = new HashMap<String, String>();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_roles.class);
		criteria.add(Restrictions.eq("role", "Staff"));
		List results = criteria.list();
		Iterator it = results.iterator();
		while (it.hasNext()) {
			vrs_roles vrslooping = (vrs_roles) it.next();
			getBack.put(
					String.valueOf(vrslooping.getRoleId()),
					sco.getvrs_Roles_metas(vrslooping.getRoleId(), "firstname")
							+ " "
							+ sco.getvrs_Roles_metas(vrslooping.getRoleId(),
									"lastname"));
		}

		return getBack;
	}

	public String getProfileSchool(int comp_id, HttpServletRequest request) {

		
		String getBack = "";
		JSONObject obj = new JSONObject();
		try {
			int userId = Integer.valueOf(request.getSession()
					.getAttribute("u9988u").toString());

			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(vrs_companies.class);
			criteria.add(Restrictions.eq("companyId", comp_id));
			criteria.add(Restrictions.eq("companyType", "School"));
			List results = criteria.list();
			Iterator it = results.iterator();
			if (it.hasNext()) {
				vrs_companies vrslooping = (vrs_companies) it.next();
				obj.put("sname", vrslooping.getCompanyName());
			}

			criteria = sessionFactory.getCurrentSession().createCriteria(
					vrs_company_metas.class);
			criteria.add(Restrictions.eq("companyId", comp_id));
			criteria.add(Restrictions.eq("companyMetasKey", "address"));
			results = criteria.list();
			it = results.iterator();
			if (it.hasNext()) {
				vrs_company_metas vrslooping = (vrs_company_metas) it.next();
				obj.put("saddress", vrslooping.getCompanyMetasValue());
			}

			criteria = sessionFactory.getCurrentSession().createCriteria(
					vrs_company_metas.class);
			criteria.add(Restrictions.eq("companyId", comp_id));
			criteria.add(Restrictions.eq("companyMetasKey", "city"));
			results = criteria.list();
			it = results.iterator();
			if (it.hasNext()) {
				vrs_company_metas vrslooping = (vrs_company_metas) it.next();
				obj.put("city", vrslooping.getCompanyMetasValue());
			}

			criteria = sessionFactory.getCurrentSession().createCriteria(
					vrs_company_metas.class);
			criteria.add(Restrictions.eq("companyId", comp_id));
			criteria.add(Restrictions.eq("companyMetasKey", "province"));
			results = criteria.list();
			it = results.iterator();
			if (it.hasNext()) {
				vrs_company_metas vrslooping = (vrs_company_metas) it.next();
				obj.put("province", vrslooping.getCompanyMetasValue());
			}

			criteria = sessionFactory.getCurrentSession().createCriteria(
					vrs_company_metas.class);
			criteria.add(Restrictions.eq("companyId", comp_id));
			criteria.add(Restrictions.eq("companyMetasKey", "School_region"));
			results = criteria.list();
			it = results.iterator();
			if (it.hasNext()) {
				vrs_company_metas vrslooping = (vrs_company_metas) it.next();
				obj.put("region", vrslooping.getCompanyMetasValue());
			}

			criteria = sessionFactory.getCurrentSession().createCriteria(
					vrs_company_metas.class);
			criteria.add(Restrictions.eq("companyId", comp_id));
			criteria.add(Restrictions.eq("companyMetasKey", "level"));
			results = criteria.list();
			it = results.iterator();
			if (it.hasNext()) {
				vrs_company_metas vrslooping = (vrs_company_metas) it.next();
				obj.put("level", vrslooping.getCompanyMetasValue());
			}

			criteria = sessionFactory.getCurrentSession().createCriteria(
					vrs_company_metas.class);
			criteria.add(Restrictions.eq("companyId", comp_id));
			criteria.add(Restrictions.eq("companyMetasKey", "format"));
			results = criteria.list();
			it = results.iterator();
			if (it.hasNext()) {
				vrs_company_metas vrslooping = (vrs_company_metas) it.next();
				obj.put("format", vrslooping.getCompanyMetasValue());
			}

			criteria = sessionFactory.getCurrentSession().createCriteria(
					vrs_company_metas.class);
			criteria.add(Restrictions.eq("companyId", comp_id));
			criteria.add(Restrictions
					.eq("companyMetasKey", "School_headmaster"));
			results = criteria.list();
			it = results.iterator();
			if (it.hasNext()) {
				vrs_company_metas vrslooping = (vrs_company_metas) it.next();
				obj.put("headmaster", vrslooping.getCompanyMetasValue());
			}

			criteria = sessionFactory.getCurrentSession().createCriteria(
					vrs_company_metas.class);
			criteria.add(Restrictions.eq("companyId", comp_id));
			criteria.add(Restrictions.eq("companyMetasKey", "School_baseSPP"));
			results = criteria.list();
			it = results.iterator();
			if (it.hasNext()) {
				vrs_company_metas vrslooping = (vrs_company_metas) it.next();
				obj.put("baseSPP", vrslooping.getCompanyMetasValue());
			}

			criteria = sessionFactory.getCurrentSession().createCriteria(
					vrs_company_metas.class);
			criteria.add(Restrictions.eq("companyId", comp_id));
			criteria.add(Restrictions.eq("companyMetasKey", "School_baseSSP"));
			results = criteria.list();
			it = results.iterator();
			if (it.hasNext()) {
				vrs_company_metas vrslooping = (vrs_company_metas) it.next();
				obj.put("baseSSP", vrslooping.getCompanyMetasValue());
			}

			criteria = sessionFactory.getCurrentSession().createCriteria(
					vrs_company_metas.class);
			criteria.add(Restrictions.eq("companyId", comp_id));
			criteria.add(Restrictions.eq("companyMetasKey", "phone"));
			results = criteria.list();
			it = results.iterator();
			if (it.hasNext()) {
				vrs_company_metas vrslooping = (vrs_company_metas) it.next();
				obj.put("phone", vrslooping.getCompanyMetasValue());
			}

			criteria = sessionFactory.getCurrentSession().createCriteria(
					vrs_company_metas.class);
			criteria.add(Restrictions.eq("companyId", comp_id));
			criteria.add(Restrictions.eq("companyMetasKey", "email"));
			results = criteria.list();
			it = results.iterator();
			if (it.hasNext()) {
				vrs_company_metas vrslooping = (vrs_company_metas) it.next();
				obj.put("email", vrslooping.getCompanyMetasValue());
			}

			criteria = sessionFactory.getCurrentSession().createCriteria(
					vrs_company_metas.class);
			criteria.add(Restrictions.eq("companyId", comp_id));
			criteria.add(Restrictions.eq("companyMetasKey",
					"School_term_labels"));
			results = criteria.list();
			it = results.iterator();
			if (it.hasNext()) {
				vrs_company_metas vrslooping = (vrs_company_metas) it.next();
				String tempTermLabels = vrslooping.getCompanyMetasValue()
						.replace("{", "");
				tempTermLabels = tempTermLabels.replace("}", "");
				obj.put("termLabels", tempTermLabels);
			}

			getBack = obj.toString();
		} catch (Exception ex) {
			if (idebugging)
				System.out.println(" company service impl " + ex.getMessage());
		}
		return getBack;
	}

	public String checkHeadMasterExist(String StaffID) {
		String getBack = "";
		try {
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(vrs_roles.class);
			criteria.add(Restrictions.eq("role", "Staff"));
			criteria.add(Restrictions.eq("id", Integer.valueOf(StaffID)));
			
			List results = criteria.list();
			Iterator it = results.iterator();
			if (it.hasNext()) {
				getBack = "";
			} else {
				getBack = "<br/>This  user is not Staff";
			}
		} catch (Exception ex) {
			getBack += " <br/>message Error = " + ex.getMessage();
		}
		return getBack;
	}

	public Map<String, String> getCompanyList() {
		Map<String, String> optionList = new LinkedHashMap<String, String>();
		// check this
		return optionList;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String getMessage() {
		return message;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	String checkDuplicate(String key, String value) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_company_metas.class);
		criteria.add(Restrictions.eq("companyMetasKey", key));
		criteria.add(Restrictions.eq("companyMetasValue", value));
		List results = criteria.list();
		Iterator it = results.iterator();
		if (it.hasNext()) {
			getBack += " <br/> This  " + key + " = " + value + " Already Exist";
			vrs_company_metas vrslooping = (vrs_company_metas) it.next();
		}
		return getBack;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	void addCompanyMetas(int company_id, String key, String value) {
		// add if not exist
		if (value != null) {
			if (value.length() > 0) {
				Timestamp tstamp = new Timestamp(new Date().getTime());
				vrs_company_metas comp_metas = new vrs_company_metas();
				comp_metas.setCompanyId(company_id);
				comp_metas.setCompanyMetasKey(key);
				comp_metas.setCompanyMetasValue(value);
				comp_metas.setCreated(tstamp);
				comp_metas.setModified(tstamp);
				sessionFactory.getCurrentSession().saveOrUpdate(comp_metas);
			}
		}
	}

	boolean checkKeyExist(int company_id, String key) {
		boolean getBack = false;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_company_metas.class);
		criteria.add(Restrictions.eq("companyMetasKey", key));
		criteria.add(Restrictions.eq("companyId", company_id));
		List results = criteria.list();
		Iterator it = results.iterator();
		if (it.hasNext()) {
			getBack = true;
		}
		return getBack;
	}

	void editCompanyMetas(int company_id, String key, String value) {
		// add if not exist
		if (value.length() > 0) {
			if (checkKeyExist(company_id, key)) {
				String hqlUpdate = "update vrs_company_metas set companyMetasValue=:value,modified=now() where companyId=:company_id and companyMetasKey=:key";
				Session sesi = sessionFactory.getCurrentSession();
				sesi.createQuery(hqlUpdate)
						.setInteger("company_id", company_id)
						.setString("key", key).setString("value", value)
						.executeUpdate();
			} else {

				Timestamp tstamp = new Timestamp(new Date().getTime());
				vrs_company_metas comp_metas = new vrs_company_metas();
				comp_metas.setCompanyId(company_id);
				comp_metas.setCompanyMetasKey(key);
				comp_metas.setCompanyMetasValue(value);
				comp_metas.setCreated(tstamp);
				comp_metas.setModified(tstamp);
				sessionFactory.getCurrentSession().saveOrUpdate(comp_metas);
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String addUser(vrs_roles role, String email, String key,
			String value, String message) {

		Timestamp tstamp = new Timestamp(new Date().getTime());
		int userId = -1;
		String userEmail = "";
		if (email != "") {
			userId = this.getUserId("email", email);
		}
		if (userId == -1 && value != "") {
			userId = this.getUserId(key, value);
		}
		if (userId != -1) {
			role.setUserId(userId);
		}

		else if (email != "" || value != "") {
			vrs_users user = new vrs_users();
			user.setCreated(tstamp);
			user.setModified(tstamp);
			sessionFactory.getCurrentSession().saveOrUpdate(user);

			if (email != "") {
				vrs_user_metas userMeta = new vrs_user_metas();
				userMeta.setUser_id(user.getId());
				userMeta.setKey("email");
				userMeta.setValue(email);
				userMeta.setCreated(tstamp);
				userMeta.setModified(tstamp);
				sessionFactory.getCurrentSession().saveOrUpdate(userMeta);
			}

			if (key != "" && value != "") {
				vrs_user_metas userMeta1 = new vrs_user_metas();
				userMeta1.setUser_id(user.getId());
				userMeta1.setKey(key);
				userMeta1.setValue(value);
				userMeta1.setCreated(tstamp);
				userMeta1.setModified(tstamp);
				sessionFactory.getCurrentSession().saveOrUpdate(userMeta1);
			}

			userId = user.getId();
			role.setUserId(userId);
		}
		// Set userEmail
		if (email == "")
			userEmail = this.getEmail(userId);
		else
			userEmail = email;
		if (!this.checkRoleExist(role.getUserId(), role.getCompanyId())
				&& userId != -1) {
			if (message != "" && userEmail == "")
				return "Error!! You must enter email address to notify your user!";
			role.setCreated(tstamp);
			role.setModified(tstamp);
			sessionFactory.getCurrentSession().saveOrUpdate(role);
			if (message != "") {
				emailService.sendEmail(userEmail,
						"A company just add you as their user", message);
			}
			return "Success";
		} else {
			if (userId == -1)
				return "Error!! Please input email or credential!";
			else
				return "Error!! Role for this user already existed.";
		}

	}

	public int getUserId(String key, String value) {
		int userId = -1;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_user_metas.class);
		criteria.add(Restrictions.eq("key", key));
		criteria.add(Restrictions.eq("value", value));
		List results = criteria.list();
		Iterator it = null;
		for (it = results.iterator(); it.hasNext();) {
			vrs_user_metas userMeta = (vrs_user_metas) it.next();
			userId = userMeta.getUser_id();
		}
		return userId;
	}

	public String getEmail(int userId) {
		String email = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_user_metas.class);
		criteria.add(Restrictions.eq("key", "email"));
		criteria.add(Restrictions.eq("user_id", userId));
		List results = criteria.list();
		Iterator it = null;
		for (it = results.iterator(); it.hasNext();) {
			vrs_user_metas userMeta = (vrs_user_metas) it.next();
			email = userMeta.getValue();
		}
		return email;
	}

	// Check If Role Existed
	public boolean checkRoleExist(int userId, int companyId) {
		boolean roleExist = false;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_roles.class);
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("companyId", companyId));
		List results = criteria.list();
		Iterator it = results.iterator();
		if (it.hasNext()) {
			roleExist = true;
		}
		return roleExist;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Map<Integer, String> getCompanies(int userId2) {
		Map<Integer, String> result = new LinkedHashMap<Integer, String>();
		List<CompanyList> temp;
		String hqlSelect = "";
		// Session session = sessionFactory.getCurrentSession();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				CompanyList.class);
		criteria.add(Restrictions.eq("userId", userId2));
		List list = criteria.list();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			CompanyList comp = (CompanyList) it.next();
			result.put(comp.getId(), comp.getName());
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Map<Integer, String> getHeadSchool(int userId) {
		Map<Integer, String> result = new LinkedHashMap<Integer, String>();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				CompanyList.class);
		criteria.add(Restrictions.eq("userId", userId));
		// criteria.add(Restrictions.ne("parent", 0));
		// criteria.add(Restrictions.not(Restrictions.in("parent", new
		// String[]{"0"})));
		List list = criteria.list();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			CompanyList comp = (CompanyList) it.next();
			result.put(comp.getId(), comp.getName());
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<CompanyList> listCompanies(int userId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				CompanyList.class);
		// int userId =
		// Integer.valueOf(request.getSession().getAttribute("u9988u").toString());
		criteria.add(Restrictions.eq("userId", userId));
		// criteria.add(Restrictions.eq("id", this.getCompId()));
		List<CompanyList> list = criteria.list();
		return list;
	}

	// public int getCompIdX(int userId) {
	public String getCompIdX(int userId) {
		// int result = 0;
		String result = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_companies.class);
		// criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("companyId", userId));
		criteria.add(Restrictions.ne("parent", 0));
		List<?> results = criteria.list();
		Iterator<?> it = results.iterator();
		if (it.hasNext()) {
			vrs_companies roleM = (vrs_companies) it.next();
			result = roleM.getCompanyName();
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<vrs_companies> getSchoolList2(int id,
			addSchollModel OaddScholl, HttpServletRequest request) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_companies.class);
		criteria.add(Restrictions.eq("companyId", id));
		List<vrs_companies> list = criteria.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<CompanyUserList> listUsers(int companyId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				CompanyUserList.class);
		criteria.add(Restrictions.eq("companyId", companyId));
		List<CompanyUserList> list = criteria.list();
		return list;
	}

	// add SubCompany
	// company add subcompany
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addSubCompany(vrs_companies company, vrs_company_metas compMeta) {

		Timestamp tstamp = new Timestamp(new Date().getTime());
		company.setCreated(tstamp);
		company.setModified(tstamp);
		sessionFactory.getCurrentSession().saveOrUpdate(company);
		compMeta.setCompanyId(company.getCompanyId());
		compMeta.setCreated(tstamp);
		compMeta.setModified(tstamp);
		sessionFactory.getCurrentSession().saveOrUpdate(compMeta);

	}

	public vrs_roles getRole(int userId, int compId) {
		vrs_roles result = new vrs_roles();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_roles.class);
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("companyId", compId));
		List list = criteria.list();
		Iterator it = list.iterator();
		if (it.hasNext()) {
			vrs_roles role = (vrs_roles) it.next();
			result = role;
		}
		return result;

	}

	public List<vrs_companies> getSchoolList() {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_companies.class);
		criteria.add(Restrictions.eq("companyType", "School"));
		criteria.addOrder(Order.asc("companyName"));

		return criteria.list();
	}

	public String getSchoolAddress(int CompanyId) {
		String result = null ;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_company_metas.class);
		criteria.add(Restrictions.eq("companyMetasKey", "company address"));
		criteria.add(Restrictions.eq("companyId", CompanyId));
		Iterator it = criteria.list().iterator();
		
		if(it.hasNext()){
			vrs_company_metas vas = (vrs_company_metas) it.next();
			result = vas.getCompanyMetasValue();
		}

		return result;

	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<RoleStaff> getAllStaffs(int companyId) {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				RoleStaff.class);
		criteria.add(Restrictions.eq("companyId", companyId));
		criteria.addOrder(Order.asc("id"));

		return criteria.list();
	}

	@Autowired
	serviceCompanies sco;
	@Autowired
	getParameterBean parameterBeans;

	// Paging Staff
	@SuppressWarnings("unchecked")
	public int getCountData(int companyId) {
		int getBack = 0;
		try {
			List<Integer> companyIds = new ArrayList<Integer>();
			companyIds = common.getCompaniesChildren(
					sessionFactory.getCurrentSession(), companyId, companyIds);

			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(vrs_roles.class);
			criteria.add(Restrictions.in("companyId", companyIds));
			criteria.add(Restrictions.eq("role", "Employee"));
			criteria.add(Restrictions.eq("status", 1));
			criteria.addOrder(Order.asc("roleId"));
			getBack = criteria.list().size();
		} catch (Exception ex) {
		}
		return getBack;
	}

	@SuppressWarnings("unchecked")
	public List viewData(int companyId, String role, int offset, int limit,
			HttpServletRequest request) {
		List getBack = new ArrayList();
		try {

			prefix = "../";
			String urlThis = request.getRequestURI();
			StringTokenizer stk = new StringTokenizer(urlThis, "/");
			if (stk.nextElement().equals("verse")) {
				if (stk.countTokens() > 2)
					prefix = "../../";
			} else {
				if (stk.countTokens() > 1)
					prefix = "../../";
			}

			List<Integer> companyIds = new ArrayList<Integer>();
			companyIds = common.getCompaniesChildren(
					sessionFactory.getCurrentSession(), companyId, companyIds);

			
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(RoleName.class);
			criteria.add(Restrictions.in("companyId", companyIds));
			criteria.add(Restrictions.eq("status", 1));
			criteria.add(Restrictions.eq("role", role));

			criteria.setMaxResults(limit);
			criteria.setFirstResult(offset);
			// criteria.addOrder(Order.asc("roleId"));
			criteria.addOrder(Order.desc("modified"));

			Iterator it = criteria.list().iterator();
			Map model = null;

			while (it.hasNext()) {
				RoleName vrs_looping = (RoleName) it.next();
				model = new HashedMap();
				model.put("id", vrs_looping.getId());
				model.put("name", vrs_looping.getName());
				// model.put("lastname", sco.getvrs_Roles_metas(
				// vrs_looping.getId(), "lastname"));
				model.put("email",
						sco.getvrs_Roles_metas(vrs_looping.getId(), "email"));
				model.put("placeofbirth", sco.getvrs_Roles_metas(
						vrs_looping.getId(), "placeofbirth"));

				String dob = sco.getvrs_Roles_metas(vrs_looping.getId(),
						"dateofbirth");
				String[] temp1;
				String delimiter = "/";
				temp1 = dob.split(delimiter);

				for (int i = 0; i < temp1.length; i++) {
					if (temp1.length == 3) {
						int dateTemp = Integer.valueOf(temp1[2]);
						int year = Calendar.getInstance().get(Calendar.YEAR);

						int age = year - dateTemp;
						model.put("dateofbirth", age);
					}
				}

				model.put("gender",
						sco.getvrs_Roles_metas(vrs_looping.getId(), "gender"));
				model.put("statusemp", sco.getvrs_Roles_metas(
						vrs_looping.getId(), "statusemp"));
				model.put("employeestatus", sco.getvrs_Roles_metas(
						vrs_looping.getId(), "employeestatus"));
				model.put("position",
						sco.getvrs_Roles_metas(vrs_looping.getId(), "position"));
				model.put("startdate", sco.getvrs_Roles_metas(
						vrs_looping.getId(), "startdate"));
				model.put("startsalary", sco.getvrs_Roles_metas(
						vrs_looping.getId(), "startsalary"));
				model.put("reasonforleaving", sco.getvrs_Roles_metas(
						vrs_looping.getId(), "reasonforleaving"));
				model.put("address",
						sco.getvrs_Roles_metas(vrs_looping.getId(), "address"));
				model.put("city",
						sco.getvrs_Roles_metas(vrs_looping.getId(), "city"));
				model.put("phone",
						sco.getvrs_Roles_metas(vrs_looping.getId(), "phone"));
				model.put("mobile",
						sco.getvrs_Roles_metas(vrs_looping.getId(), "mobile"));
				model.put("idcard",
						sco.getvrs_Roles_metas(vrs_looping.getId(), "idcard"));
				model.put("noskmengajar", sco.getvrs_Roles_metas(
						vrs_looping.getId(), "noskmengajar"));
				model.put("nopernyataanyayasan", sco.getvrs_Roles_metas(
						vrs_looping.getId(), "nopernyataanyayasan"));
				model.put("noskmutasi", sco.getvrs_Roles_metas(
						vrs_looping.getId(), "noskmutasi"));
				model.put("suratpembagianmengajar", sco.getvrs_Roles_metas(
						vrs_looping.getId(), "suratpembagianmengajar"));
				model.put("nosk",
						sco.getvrs_Roles_metas(vrs_looping.getId(), "nosk"));
				model.put("tglsk",
						sco.getvrs_Roles_metas(vrs_looping.getId(), "tglsk"));
				model.put("unitkerja", sco.getvrs_Roles_metas(
						vrs_looping.getId(), "unitkerja"));
				model.put("latestdegree", sco.getvrs_Roles_metas(
						vrs_looping.getId(), "latestdegree"));
				model.put("major",
						sco.getvrs_Roles_metas(vrs_looping.getId(), "major"));
				model.put("yearofgraduation", sco.getvrs_Roles_metas(
						vrs_looping.getId(), "yearofgraduation"));
				model.put("maritalstatus", sco.getvrs_Roles_metas(
						vrs_looping.getId(), "maritalstatus"));
				model.put("spousename", sco.getvrs_Roles_metas(
						vrs_looping.getId(), "spousename"));
				model.put("numberofchildren", sco.getvrs_Roles_metas(
						vrs_looping.getId(), "numberofchildren"));
				model.put("namecontactperson", sco.getvrs_Roles_metas(
						vrs_looping.getId(), "namecontactperson"));
				model.put("relationcontactperson", sco.getvrs_Roles_metas(
						vrs_looping.getId(), "relationcontactperson"));
				model.put("phonenumbercontactperson", sco.getvrs_Roles_metas(
						vrs_looping.getId(), "phonenumbercontactperson"));
				model.put("roleId", vrs_looping.getId());
				// model.put("province",
				// sco.getvrs_Roles_metas(vrs_looping.getRoleId(), "province"));

				model.put("status", vrs_looping.getStatus());
				String getPhoto = getPhoto(sco.getvrs_Roles_metas(
						vrs_looping.getId(), "people_photo"));
				getBack.add(model);
			}
		} catch (Exception ex) {
			if (idebugging)
				System.out.println(" error in peopleserviceImpl viewData = "
						+ ex.getMessage());
		}
		return getBack;
	}

	public String getPhoto(String idPhoto) {
		String getBack = prefix + "img/no-person.png";
		if (idPhoto != null) {
			if (idPhoto.length() > 0) {
				Criteria criteria = sessionFactory.getCurrentSession()
						.createCriteria(vrs_file_library.class);
				criteria.add(Restrictions.eq("id", Integer.valueOf(idPhoto)));
				Iterator it = criteria.list().iterator();
				if (it.hasNext()) {
					vrs_file_library vrs_looping = (vrs_file_library) it.next();
					getBack = parameterBeans.getAwsDownloadLink();
					if (vrs_looping.getFile_type().startsWith("image"))
						getBack += "imageDocument/"
								+ vrs_looping.getFile_name();
					else
						getBack += "fileDocument/" + vrs_looping.getFile_name();
					try {
						URL url = new URL(getBack);
						HttpURLConnection huc = (HttpURLConnection) url
								.openConnection();
						huc.setRequestMethod("GET");
						huc.connect();
						if (!huc.getResponseMessage().equalsIgnoreCase("OK"))
							getBack = "";
						huc.disconnect();
					} catch (Exception ex) {
						getBack = prefix + "img/no-person.png";
					}
				}
			}
		}
		return getBack;
	}

	public Map<String, String> getSchoolEdited2(HttpServletRequest request,
			int schoolId) {
		Map<String, String> getBack = new HashMap<String, String>();
		try {
			// select * from vrs_companies where parent in (select company_id
			// from vrs_roles where user_id=40 and role='Admin')
			int parent = 2;
			int userId = Integer.valueOf(request.getSession()
					.getAttribute("u9988u").toString());

			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(vrs_companies.class);
			criteria.add(Restrictions.eq("parent", parent));
			criteria.add(Restrictions.eq("status", 1));
			List results = criteria.list();
			Iterator it = results.iterator();
			while (it.hasNext()) {
				vrs_companies vrslooping = (vrs_companies) it.next();
				getBack.put(String.valueOf(vrslooping.getCompanyId()),
						vrslooping.getCompanyName());
			}
		} catch (Exception ex) {
			if (idebugging)
				System.out
						.println(" error in company service implement getSchoolEdited "
								+ ex.getMessage());
		}

		return getBack;
	}

	public List getCompMeta(int companyId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_company_metas.class);
		criteria.add(Restrictions.eq("companyId", companyId));
		List results = criteria.list();
		return results;
	}

	public int getCompId(int companyId) {
		int result = 0;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_company_metas.class);
		criteria.add(Restrictions.eq("companyId", companyId));
		List results = criteria.list();
		Iterator it = results.iterator();
		if (it.hasNext()) {
			vrs_company_metas roleM = (vrs_company_metas) it.next();
			result = roleM.getCompanyId();
		}
		return result;
	}

	public int getCompIdTest() {
		int result = 0;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_company_metas.class);
		// criteria.add(Restrictions.eq("companyId", companyId));
		List results = criteria.list();
		Iterator it = results.iterator();
		if (it.hasNext()) {
			vrs_company_metas roleM = (vrs_company_metas) it.next();
			result = roleM.getCompanyId();
		}
		return result;
	}

	public String getCompName(int companyId) {
		String result = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_companies.class);
		criteria.add(Restrictions.eq("companyId", companyId));
		List results = criteria.list();
		Iterator it = results.iterator();
		if (it.hasNext()) {
			vrs_companies roleM = (vrs_companies) it.next();
			result = roleM.getCompanyName();
		}
		return result;
	}

	public Timestamp getModifiedTime(int companyId) {
		Timestamp result = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_companies.class);
		criteria.add(Restrictions.eq("companyId", companyId));
		List results = criteria.list();
		Iterator it = results.iterator();
		if (it.hasNext()) {
			vrs_companies roleM = (vrs_companies) it.next();
			result = roleM.getModified();
		}
		return result;
	}

	public vrs_companies getSchoolComp(int companyId) {
		vrs_companies company = new vrs_companies();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_companies.class);
		criteria.add(Restrictions.eq("companyId", companyId));
		List results = criteria.list();
		Iterator it = null;
		for (it = results.iterator(); it.hasNext();) {
			company = (vrs_companies) it.next();
		}
		return company;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String deleteFunction(int id, int userId) {
		String message = "";
		Timestamp tstamp = new Timestamp(new Date().getTime());
		vrs_companies company = this.getSchoolComp(id);
		company.setModified(tstamp);
		company.setModifiedBy(userId);
		company.setStatus(0);
		sessionFactory.getCurrentSession().update(company);

		/*
		 * if (!note.isEmpty()) { vrs_common common = new vrs_common();
		 * common.setTableName("School_Disabled"); common.setModified(tstamp);
		 * common.setModifiedBy(userId); common.setNote(note);
		 * common.setStatus(0);
		 * sessionFactory.getCurrentSession().saveOrUpdate(common); }
		 */
		if (message == "")
			message = "Success!";

		return message;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String closeSchools(int id, int userId) {
		String message = "";

		Timestamp tstamp = new Timestamp(new Date().getTime());
		vrs_companies company = this.getSchoolComp(id);
		company.setModified(tstamp);
		company.setModifiedBy(userId);
		company.setStatus(-1);
		sessionFactory.getCurrentSession().update(company);

		if (message == "")
			message = "Success!";

		return message;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String updateCompanies(vrs_companies companies) {
		String message = "";
		Timestamp tstamp = new Timestamp(new Date().getTime());

		companies.setModified(tstamp);
		sessionFactory.getCurrentSession().saveOrUpdate(companies);

		vrs_company_metas company = new vrs_company_metas();

		company.setModified(tstamp);
		sessionFactory.getCurrentSession().saveOrUpdate(company);

		if (message == "")
			message = "Success!";
		return message;
	}

	public vrs_company_metas getMetaComp(int companyId) {
		vrs_company_metas company = new vrs_company_metas();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_company_metas.class);
		criteria.add(Restrictions.eq("companyId", companyId));
		List results = criteria.list();
		Iterator it = null;
		for (it = results.iterator(); it.hasNext();) {
			company = (vrs_company_metas) it.next();
		}
		return company;
	}

	public String getSchoolEditedTest(HttpServletRequest request) {
		String result = "";
		// try {
		// select * from vrs_companies where parent in (select company_id
		// from vrs_roles where user_id=40 and role='Admin')
		int parent = 2;
		// int userId = Integer.valueOf(request.getSession()
		// .getAttribute("u9988u").toString());

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_companies.class);
		criteria.add(Restrictions.eq("parent", parent));
		criteria.add(Restrictions.eq("status", 1));
		List results = criteria.list();
		Iterator it = results.iterator();
		while (it.hasNext()) {
			vrs_companies vrslooping = (vrs_companies) it.next();
			result = vrslooping.getCompanyName();
		}

		return result;
	}

	public int getSchoolEditedTest2(HttpServletRequest request) {
		int result = 0;
		try {
			// select * from vrs_companies where parent in (select company_id
			// from vrs_roles where user_id=40 and role='Admin')
			int parent = 2;
			int userId = Integer.valueOf(request.getSession()
					.getAttribute("u9988u").toString());

			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(vrs_companies.class);
			criteria.add(Restrictions.eq("parent", parent));
			criteria.add(Restrictions.eq("status", 1));
			List results = criteria.list();
			Iterator it = results.iterator();
			if (it.hasNext()) {
				vrs_companies vrslooping = (vrs_companies) it.next();
				result = vrslooping.getCompanyId();
			}
		} catch (Exception ex) {
			if (idebugging)
				System.out
						.println(" error in company service implement getSchoolEdited "
								+ ex.getMessage());
		}

		return result;
	}

	// paging school
	@SuppressWarnings("unchecked")
	public int getCountDataSchool(HttpServletRequest request) {
		int getBack = 0;
		try {
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(ViewSchoolList.class);
			CommonFunction cf = new CommonFunction();

			if (cf.getPosition(request).equalsIgnoreCase("Administrator")) {
				int company_id = cf.getCompanyID(request);
				criteria.add(Restrictions.or(
						Restrictions.eq("companyId", company_id),
						Restrictions.eq("parentId", company_id)));
			}
			getBack = criteria.list().size();
		} catch (Exception ex) {
		}
		return getBack;
	}

	@Autowired
	BudgetService budgetService;

	@SuppressWarnings("unchecked")
	public List viewDataSchool(int offset, int limit, HttpServletRequest request) {
		List getBack = new ArrayList();
		try {

			prefix = "../";
			String urlThis = request.getRequestURI();
			StringTokenizer stk = new StringTokenizer(urlThis, "/");
			if (stk.nextElement().equals("verse")) {
				if (stk.countTokens() > 2)
					prefix = "../../";
			} else {
				if (stk.countTokens() > 1)
					prefix = "../../";
			}

			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(ViewSchoolList.class);
			CommonFunction cf = new CommonFunction();

			// For Administrator to view all school
			if (cf.getPosition(request).equalsIgnoreCase("Administrator")) {
				int company_id = cf.getCompanyID(request);
				criteria.add(Restrictions.or(
						Restrictions.eq("companyId", company_id),
						Restrictions.eq("parentId", company_id)));
			}
			criteria.add(Restrictions.ne("status", -1));
			criteria.setMaxResults(limit);
			criteria.setFirstResult(offset);
			criteria.addOrder(Order.desc("modified"));
			Iterator it = criteria.list().iterator();
			Map model = null;
			while (it.hasNext()) {
				ViewSchoolList vrs_looping = (ViewSchoolList) it.next();
				model = new HashedMap();
				model.put("companyId", vrs_looping.getCompanyId());
				model.put("school", vrs_looping.getName());
				model.put("headmaster", vrs_looping.getHeadmaster());
				model.put("address", vrs_looping.getAddress());
				model.put("addressCompany", vrs_looping.getAddressCompany());
				model.put("student", vrs_looping.getStudent());
				model.put("staffs", vrs_looping.getStaff());
				model.put("candidates", vrs_looping.getCandidates());
				model.put("budgets", vrs_looping.getBudget());

				if (vrs_looping.getRoleId() != null) {
					int headmasterId = Integer.valueOf(vrs_looping.getRoleId());
					model.put("headmasterId", headmasterId);
				}
				
				model.put("companyPhoto", sco.getPhotobyCompanyID(vrs_looping.getCompanyId()));
				getBack.add(model);

			}
		} catch (Exception ex) {
			if (idebugging)
				System.out.println(" error in schoolImpl viewData = "
						+ ex.getMessage());
		}

		return getBack;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String updateSchool(int roleID, int schoolId,
			editSchollModel schoolData, HttpServletRequest request) {
		boolean emailExist = this.checkCompanyMetaCredExist(schoolId, "email",
				schoolData.getEmail(), sessionFactory.getCurrentSession());
		CommonFunction common = new CommonFunction();
		ArrayList<Integer> result_email = common.checkRoleMetaCredExistId(
				schoolId, "email", schoolData.getEmail(),
				sessionFactory.getCurrentSession());
		String message = "";
		vrs_company_metas companyMeta = this.getCompanyMetass(schoolId);

		ArrayList<String> list_field = new ArrayList<String>();
		list_field.add("firstname");

		String hqlUpdate = "";

		hqlUpdate = "update vrs_companies set modifiedBy=:usr,modified=now(),"
				+ "companyName=:company_name where companyId=:company_id";
		sessionFactory.getCurrentSession().createQuery(hqlUpdate)
				.setInteger("company_id", schoolId)
				.setString("company_name", schoolData.getName())
				.setInteger("usr", roleID).executeUpdate();
		System.out.println("emailExist"+emailExist);
		if (!emailExist) {
			message = this
					.updateCompanyMeta(schoolId, "address",
							schoolData.getAddress(),
							sessionFactory.getCurrentSession());
			if (message != "")
				return message;

			message = this.updateCompanyMeta(schoolId, "city",
					schoolData.getCity(), sessionFactory.getCurrentSession());
			if (message != "")
				return message;

			message = this.updateCompanyMeta(schoolId, "province",
					schoolData.getProvince(),
					sessionFactory.getCurrentSession());
			if (message != "")
				return message;


			message = this.updateCompanyMeta(schoolId, "phone",
					schoolData.getPhone(), sessionFactory.getCurrentSession());
			if (message != "")
				return message;

			message = this.updateCompanyMeta(schoolId, "email",
					schoolData.getEmail(), sessionFactory.getCurrentSession());
			if (message != "")
				return message;

			message = this
					.updateCompanyMeta(schoolId, "website",
							schoolData.getWebsite(),
							sessionFactory.getCurrentSession());
			if (message != "")
				return message;

			
			message = this.updateCompanyMeta(schoolId,
					"School_baseSPPBasic",
					schoolData.getBaseSPPBasic(),
					sessionFactory.getCurrentSession());
			if (message != "")
				return message;
			
			message = this.updateCompanyMeta(schoolId,
					"School_baseSPPOwnership",
					schoolData.getBaseSPPOwnership(),
					sessionFactory.getCurrentSession());
			if (message != "")
				return message;
			
			message = this.updateCompanyMeta(schoolId,
					"School_baseSPPEmployee",
					schoolData.getBaseSPPEmployee(),
					sessionFactory.getCurrentSession());
			if (message != "")
				return message;
			
			message = this.updateCompanyMeta(schoolId,
					"School_baseSSPBasic",
					schoolData.getBaseSSPBasic(),
					sessionFactory.getCurrentSession());
			if (message != "")
				return message;
			
			message = this.updateCompanyMeta(schoolId,
					"School_baseSSPContinuation",
					schoolData.getBaseSSPContinuation(),
					sessionFactory.getCurrentSession());
			if (message != "")
				return message;
			
			message = this.updateCompanyMeta(schoolId,
					"School_baseSSPOwnership",
					schoolData.getBaseSSPOwnership(),
					sessionFactory.getCurrentSession());
			if (message != "")
				return message;
			
			message = this.updateCompanyMeta(schoolId,
					"School_baseSSPEmployee",
					schoolData.getBaseSSPEmployee(),
					sessionFactory.getCurrentSession());
			if (message != "")
				return message;
			
			
			for (int i = 0; i < schoolData.getBaseSSP().size(); i++) {
				message = this.updateCompanyMeta(schoolId,
						 "School_baseSSP"
									+ String.valueOf(i),
						(schoolData.getBaseSSP().get(i)).toString(),
						sessionFactory.getCurrentSession());
				if (message != "")
					return message;
			}
			
			

			message = this.updateCompanyMeta(schoolId, "fax",
					schoolData.getFax(), sessionFactory.getCurrentSession());
			if (message != "")
				return message;
			
			message = this.updateCompanyMeta(schoolId, "School_registrationFormPrice",
					schoolData.getRegistrationFormPrice(), sessionFactory.getCurrentSession());
			if (message != "")
				return message;

//upload edit photo
			
			try {
				String extension = schoolData.getFileType();
				if (extension.startsWith("image")) {
					boolean fileExistBefore = false;
					vrs_file_library vfl = new vrs_file_library();
					Criteria criteria = sessionFactory.getCurrentSession()
							.createCriteria(vrs_file_library.class);
					// delete file before
					try {
						int fileid = Integer.valueOf(sco.getCompanyMeta(schoolId,"CompanyPhoto"));
						criteria.add(Restrictions.eq("id", fileid));
						Iterator it = criteria.list().iterator();
						if (it.hasNext()) {
							vfl = (vrs_file_library) it.next();
							String fileNameBefore = "company/imageDocument/"
									+ vfl.getFile_name();
							fileExistBefore = true;
							try {
								AmazonS3 s3del = new AmazonS3Client(
										new BasicAWSCredentials(
												parameterBeans.getAwsaccess(),
												parameterBeans.getAwssecret()));
								S3Object object = s3del
										.getObject(new GetObjectRequest(
												parameterBeans.getAwsbucket(),
												fileNameBefore));
								s3del.deleteObject("yck", fileNameBefore);
							} catch (Exception ex) {
							}
						}
					} catch (Exception ex) {
						if (common.idebugging)
							System.out.println("error in delte files "+ex.getMessage());
					}
					// end delete file before

					String fileName = schoolData.getFileName();
					String destination = fileName;

					File file = new File(destination);
					MultipartFile mpf = schoolData.getFileCompany();
					mpf.transferTo(file);

					if (file.isFile()) {
						BufferedImage originalImage = ImageIO.read(file);
						int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB
								: originalImage.getType();
						BufferedImage resizeImageJpg = common.resizeImageWithHint(
								originalImage, type);
						ImageIO.write(
								resizeImageJpg,
								file.getName().substring(
										file.getName().indexOf(".") + 1), file);
						destination = "company/imageDocument/";
						AmazonS3 s3 = new AmazonS3Client(new BasicAWSCredentials(
								parameterBeans.getAwsaccess(),
								parameterBeans.getAwssecret()));
						System.out.println(" upload 1 = ");
						boolean exist = true;
						String fileNameNew = fileName;
						String md5File = "";
						while (exist) {
							try {
								S3Object object = s3
										.getObject(new GetObjectRequest(
												parameterBeans.getAwsbucket(),
												destination + fileNameNew));
							} catch (Exception ex) {
								criteria = sessionFactory.getCurrentSession()
										.createCriteria(vrs_file_library.class);
								criteria.add(Restrictions.eq("file_name",
										fileNameNew));
								if (criteria.list().size() < 1)
									exist = false;
							}
							if (exist) {
								Date dt = new Date();
								md5File = common.MD5Encode(fileNameNew + dt.toString());
								if (md5File.length() > 5)
									md5File = md5File.substring(0, 5);
								fileNameNew = fileName.substring(0,
										fileName.lastIndexOf("."))
										+ "_"
										+ md5File

										+ fileName.substring(
												fileName.lastIndexOf("."),
												fileName.length());
							}
						}
						PutObjectRequest por = new PutObjectRequest(
								parameterBeans.getAwsbucket(), destination
										+ fileNameNew, file);
						s3.putObject(por);

						if (fileExistBefore) {
							vfl.setFile_name(fileNameNew);
							sessionFactory.getCurrentSession().update(vfl);
						} else {
							int company_id = common.getCompanyID(request);
							vfl = new vrs_file_library();
							vfl.setCompany_id(company_id);
							vfl.setDescription("Company Photo");
							vfl.setFile_name(fileNameNew);
							vfl.setFile_type(extension);
							sessionFactory.getCurrentSession().save(vfl);
							sco.savein_vrs_company_metas(schoolId,"CompanyPhoto", String.valueOf(vfl.getId()));
						}
						file.delete();
					}
					
				}

			} catch (Exception ex) {
				if (common.idebugging)
					System.out.println("error in uploadEditFileToAWSForcompany = "
							+ ex.getMessage());
			}
			
			common.changeCompanyOptioninSeidebar(schoolId, schoolData.getName(), schoolData.getCity(), request);
			
			//end upload edit photo
		} else {
			if (emailExist) {
				message = "Error! Email exist!";
				Iterator it = result_email.iterator();
				it.next();
				while (it.hasNext()) {
					Integer result_roleId = (Integer) it.next();
					System.out
							.println("schoolID yang kembar: " + result_roleId);
				}

				return message;
			}

			else
				return "Error! Phone exist!";
		}

		if (message == "")
			message = "Success!";

		return message;
	}

	public boolean checkCompanyMetaCredExist(int schoolId, String key,
			String value, Session session) {
		boolean result = false;
		ArrayList<String> list_cred = new ArrayList<String>();
		list_cred.add("email");
		list_cred.add("phone");

		if (list_cred.contains(key)) {
			Criteria criteria = session.createCriteria(vrs_company_metas.class);
			criteria.add(Restrictions.eq("companyMetasKey", key));
			criteria.add(Restrictions.eq("companyMetasValue", value));
			List results = criteria.list();
			Iterator it = results.iterator();
			while (it.hasNext()) {
				vrs_company_metas companyMeta = (vrs_company_metas) it.next();
				if (schoolId != companyMeta.getCompanyId()) {
					result = true;
				}
			}
		}
		return result;
	}

	public vrs_company_metas getCompanyMetass(int companyId) {
		vrs_company_metas companyMeta = new vrs_company_metas();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vrs_company_metas.class);
		criteria.add(Restrictions.eq("companyId", companyId));
		List results = criteria.list();
		Iterator it = null;
		for (it = results.iterator(); it.hasNext();) {
			companyMeta = (vrs_company_metas) it.next();
		}
		return companyMeta;
	}

	public vrs_company_metas getCompanyMetong(int schoolId, String key,
			Session session) {
		vrs_company_metas result = new vrs_company_metas();

		Criteria criteria = session.createCriteria(vrs_company_metas.class);
		criteria.add(Restrictions.eq("companyId", schoolId));
		criteria.add(Restrictions.eq("companyMetasKey", key));
		List results = criteria.list();
		Iterator it = results.iterator();
		if (it.hasNext()) {
			vrs_company_metas companyMeta = (vrs_company_metas) it.next();
			result = companyMeta;
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String updateCompanyMeta(int schoolId, String key, String value,
			Session session) {
		boolean credExist = checkCompanyMetaCredExist(schoolId, key, value,
				session);

		if (value != null && !value.isEmpty()) {
			if (credExist) {
				return "Error! " + key + " already exist!";
			} else {
				vrs_company_metas companyMeta = getCompanyMetong(schoolId, key,
						session);
				session.clear();
				if (companyMeta.getCompanyMetasId() == null) {
					saveCompanyMeta((long) 0, schoolId, key, value, session);
				} else {
					saveCompanyMeta(companyMeta.getCompanyMetasId(), schoolId,
							key, value, session);
				}
				return "";
			}
		}
		return "";
		/*
		 * else { return "Error! "+key+": value is empty"; }
		 */

	}

	public void saveCompanyMeta(Long companyMetaId, int schoolId, String key,
			String value, Session session) {
		Timestamp tstamp = new Timestamp(new Date().getTime());
		vrs_company_metas companyMeta = new vrs_company_metas();
		if (companyMetaId != 0)
			companyMeta.setCompanyMetasId(companyMetaId);
		companyMeta.setCompanyMetasKey(key);
		companyMeta.setCompanyId(schoolId);
		companyMeta.setCompanyMetasValue(value);
		companyMeta.setCreated(tstamp);
		companyMeta.setModified(tstamp);
		session.saveOrUpdate(companyMeta);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String getpositionList() {
		String getBack = "", position = "";
		String sql_qry = "select roleMetasValue from vrs_role_metas where roleMetasKey='Academic_Position' and roleMetasValue is not null "
				+ " and length(roleMetasValue)>0 "
				+ " and roleMetasValue not in ('Headmaster','Administrator') "
				+ " group by roleMetasValue order by roleMetasValue";
		Iterator it = sessionFactory.getCurrentSession().createQuery(sql_qry)
				.iterate();
		if(it.hasNext())
		{
			getBack = "<option value=\"Please assign new position for Headmaster\" >Please assign new position for Headmaster</option>";
			while (it.hasNext()) {
				position = it.next().toString();
				getBack += "<option value=\"" + position + "\">" + position
						+ "</position>";
			}
		}
		return getBack;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String changeHeadMASter(HttpServletRequest request) {
		String getBack = "Headmaster Changed.";
		if (request.getParameter("headmasterNew").toString().length() < 1) {
			getBack = "Please choose new Headmaster";
		} else {
			int schoolId = Integer.valueOf((request.getParameter("schoolID")
					.toString()));
			String sql = "";
			Timestamp tstamp = new Timestamp(new Date().getTime());
			Iterator it = null;
			try {
				int oldHeadMaster = Integer.valueOf((request
						.getParameter("oldHeadmaster").toString()));
				sql = "select roleId from vrs_roles where userId=:userId and companyId=:companyId";
				it = sessionFactory.getCurrentSession().createQuery(sql)
						.setInteger("userId", oldHeadMaster)
						.setInteger("companyId", schoolId).list().iterator();
				if (it.hasNext()) {
					oldHeadMaster = Integer.valueOf(it.next().toString());
				}

				String position = request.getParameter("positionNew")
						.toString();
				sql = "update vrs_roles set modified=now() where roleId=:roleId";
				sessionFactory.getCurrentSession().createQuery(sql)
						.setInteger("roleId", oldHeadMaster).executeUpdate();
				if (!position.equalsIgnoreCase("Administrator"))
					sco.addRolesMetas(oldHeadMaster, "Academic_Position",
							position);
			} catch (Exception ex) {
			}
			int headmasterNew = Integer.valueOf(request.getParameter(
					"headmasterNew").toString());
			sql = "select roleId from vrs_roles where userId=:userId and companyId=:companyId";
			it = sessionFactory.getCurrentSession().createQuery(sql)
					.setInteger("userId", headmasterNew)
					.setInteger("companyId", schoolId).list().iterator();
			if (it.hasNext()) {
				headmasterNew = Integer.valueOf(it.next().toString());
			} else {
				int userID = headmasterNew;
				vrs_roles vrl = new vrs_roles();
				vrl.setCompanyId(schoolId);
				vrl.setCreated(tstamp);
				vrl.setModified(tstamp);
				vrl.setRole("Employee");
				vrl.setStatus(1);
				vrl.setUserId(headmasterNew);
				sessionFactory.getCurrentSession().save(vrl);
				headmasterNew = vrl.getRoleId();

				sql = "insert into  vrs_role_metas (roleId,roleMetasKey,roleMetasValue,created,modified) select "
						+ headmasterNew
						+ ",key,value,now(),now() "
						+ "from vrs_user_metas where user_id=:user_id";
				sessionFactory.getCurrentSession().createQuery(sql)
						.setInteger("user_id", userID).executeUpdate();
			}

			updateCompanyMeta(schoolId, "School_headmaster",
					String.valueOf(headmasterNew),
					sessionFactory.getCurrentSession());

			sql = "update vrs_roles set modified=now() where roleId=:roleId";
			sessionFactory.getCurrentSession().createQuery(sql)
					.setInteger("roleId", headmasterNew).executeUpdate();
			sco.addRolesMetas(headmasterNew, "Academic_Position", "Headmaster");

			CommonFunction cf = new CommonFunction();
			int roleId = cf.getRoleIDSelected(request);
			int companyId = cf.getCompanyID(request);

			sql = "update vrs_companies set modifiedBy=:roleID,modified=now() where companyId=:company_id";
			sessionFactory.getCurrentSession().createQuery(sql)
					.setInteger("company_id", schoolId)
					.setInteger("roleID", roleId).executeUpdate();

			vrs_logs logs = new vrs_logs();
			logs.setRoleId(roleId);
			logs.setModified(tstamp);
			logs.setAction("Change Headmaster");
			logs.setTableName("vrs_companies");
			logs.setTableId(schoolId);
			logs.setCompanyId(companyId);
			logs.setReason(request.getParameter("reason"));
			sessionFactory.getCurrentSession().saveOrUpdate(logs);

		}
		return getBack;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String getUserNameFromIDUSEr(int userID) {
		String getBack = "";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				vw_firstname_vrs_users.class);
		criteria.add(Restrictions.eq("id", userID));
		Iterator it = criteria.list().iterator();
		if (it.hasNext()) {
			vw_firstname_vrs_users vfvu = (vw_firstname_vrs_users) it.next();
			getBack = vfvu.getFirstname();
			if (getBack.length() > 0)
				getBack += " ";
			getBack += vfvu.getLastname();
		}
		return getBack;
	}

}
