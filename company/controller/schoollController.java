package verse.company.controller;

import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import verse.academic.candidate.model.RejectForm;
import verse.academic.candidate.service.CandidateService;
import verse.academic.course.model.addCourseListModel;
import verse.academic.model.ved_attendances;
import verse.academic.model.ved_course_list;
import verse.academic.model.ved_enrollments;
import verse.academic.report.model.ScoreDetail;
import verse.academic.staff.service.StaffService;
import verse.academic.student.service.StudentService;
import verse.accounting.chartAccounts.model.formChartAcc;
import verse.accounting.model.FinanceDashboardModel;
import verse.accounting.service.AccountingService;
import verse.accountsadmin.companies.services.serviceCompanies;
import verse.commonClass.CommonFunction;
import verse.commonClass.pagination;
import verse.company.model.CompanyDetail;
import verse.company.model.CompanyUserList;
import verse.company.model.addSchollModel;
import verse.company.model.editSchollModel;
import verse.company.service.CompanyService;
import verse.menuList.model.ViewDashboard;
import verse.menuList.service.DashboardService;
import verse.model.RoleStaff;
import verse.model.vrs_companies;
import verse.model.vrs_company_metas;
import verse.model.vrs_role_metas;
import verse.model.vrs_roles;
import verse.people.model.Employee;
import verse.people.service.PeopleService;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.services.simpleworkflow.model.transform.ListWorkflowTypesRequestMarshaller;

@Controller
public class schoollController {
	
	@Autowired
	serviceCompanies sco;

	boolean idebugging = true;
	@Autowired
	private CompanyService companyService;
	
	

	@Autowired
	private StudentService studentService;

	@Autowired
	private CandidateService candidateService;

	@Autowired
	private DashboardService dashboardService;

	@Autowired
	private PeopleService peopleService;
	
	@Autowired
	private AccountingService accountingService;
	
	CommonFunction common = new CommonFunction();
	
	Map<String, Object> schoolMap = new HashMap<String, Object>();
	CommonFunction cf = new CommonFunction();
	
	int schoolPageId = 12;
	int staffPageId = 17;

	public Map<String, String> getLEvelSchoolOptions() {
		Map<String, String> dropDownOption = new LinkedHashMap<String, String>();
		dropDownOption.put("KB", "KB");
		dropDownOption.put("TK", "TK");
		dropDownOption.put("SD", "SD");
		dropDownOption.put("SMP", "SMP");
		dropDownOption.put("SMA", "SMA");
		return dropDownOption;
	}
	
	// @RequestMapping(value = "/addScholl/1", method = RequestMethod.GET)
	@RequestMapping(value = "/academic/schools/create", method = RequestMethod.GET)
	public ModelAndView addSchool(
			@ModelAttribute("OaddScholl") addSchollModel OaddScholl,
			HttpServletRequest request, Map<String, Object> model) {
		model.put("headerMenu", cf.menuOnHeader(request));
		String companyName = sco.getCompanyName(cf.getCompanyID(request));
		ModelAndView showModel = new ModelAndView("page404", model);
		if (cf.canAccess(request, schoolPageId, "create")) {
//			int schoolId = 51;
			model.put("message", companyService.getMessage());

			Map<String, String> dropDownOption = new LinkedHashMap<String, String>();
			

			
			
			model.put("levelOption", getLEvelSchoolOptions());

			dropDownOption = new LinkedHashMap<String, String>();
			dropDownOption.put("Class-based", "Class-based");
			dropDownOption.put("Courser-based", "Courser-based");
			model.put("formatOption", dropDownOption);

			List<Integer> countBaseSSP = new ArrayList<Integer>();
			countBaseSSP.add(0);
			countBaseSSP.add(1);
			model.put("countBaseSSP", countBaseSSP);

			model.put("headmasterOption",
					companyService.getHeadMasterList(request));
			int userId1 = Integer.valueOf(request.getSession()
					.getAttribute("u9988u").toString());

			try {
				int userId = Integer.valueOf(request.getSession()
						.getAttribute("u9988u").toString());
				model.put("comp", companyService.getHeadSchool(userId));
			} catch (Exception ex) {
			}

			
			model.put("myMenu", cf.menuOnSideBar(request));
			model.put("titleMessage", "Add Schools | "+ companyName + " | Verse");

			// userguide
			model.put("footer", dashboardService.getUserGuide("School"));
			model.put("footer_state", "Add School");
			
			model.put("cityList", peopleService.getCityList());
			model.put("provinceList", peopleService.getProvinceList());

			showModel = new ModelAndView("schoolCreate", model);
		}
		
		return showModel;
	}

	// @RequestMapping(value = "/addScholl/1", method = RequestMethod.POST)
	@RequestMapping(value = "/academic/schools/create", method = RequestMethod.POST)
	public ModelAndView addSchoolPost(
			@ModelAttribute("OaddScholl") @Valid addSchollModel OaddScholl,
			HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		boolean success = true;
		String message = "";
		try {
			if (OaddScholl.getSave().equalsIgnoreCase("AddSchool")) {
				companyService.addSchool(OaddScholl, request);

				if (success) {
					ModelAndView modelAndView = new ModelAndView(
							"redirect:/academic/schools");
					model = modelAndView.getModel();
					model.put("success", "Success!");
					model.put("action", "School has been created");
					modelAndView.addObject(model);
					return modelAndView;
				} else {
					model.put("message", message);
					return addSchool(OaddScholl, request, model);
				}
			}
		} catch (Exception ex) {
			if (idebugging)
				System.out.println(" error on school controller "
						+ ex.getMessage());
			success = false;
			message = ex.getMessage();
		}

		return addSchool(OaddScholl, request, model);
	}

	@RequestMapping(value = "/academic/schools/{code}", method = RequestMethod.POST)
	public ModelAndView getCompanyProfile(@PathVariable("code") int code,
			HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("headerMenu", cf.menuOnHeader(request));
		model.put("myMenu", cf.menuOnSideBar(request));
		model.put("show", companyService.getProfileSchool(code, request));

		return new ModelAndView("schoollist2", model);
	}

	private List<Employee> getAllUserDetails(List<RoleStaff> userList) {
		List<Employee> detail = new ArrayList<Employee>();
		Employee temp = new Employee();

		int roleId = 0;
		String status = "";
		int size = userList.size();
		Iterator it = userList.iterator();
		while (it.hasNext()) {
			RoleStaff comp = (RoleStaff) it.next();

			if (roleId == 0) {
				roleId = comp.getRoleId();
				temp.setRoleId(roleId);
			} else if (roleId != comp.getRoleId()) {
				roleId = comp.getRoleId();
				detail.add(temp);
				temp = new Employee();
				temp.setRoleId(roleId);
			}
			if (comp.getKey().equals("firstname")) {
				temp.setFirstname(comp.getValue());
				System.out.println("firstname" + temp.getFirstname());
			} else if (comp.getKey().equals("lastname")) {
				temp.setLastname(comp.getValue());
				System.out.println("lastname" + temp.getLastname());
			} else if (comp.getKey().equals("email")) {
				temp.setEmail(comp.getValue());
				System.out.println("email" + temp.getEmail());
			}
			if (comp.getKey().equals("staff_photo")) {
				temp.setPhoto(studentService.getPhoto2(comp.getValue()));
			} else {
				temp.setPhoto(studentService.getPhoto2(""));
			}
		}
		System.out.println("size:" + size);
		if (size != 0) {
			detail.add(temp);
		}
		Iterator it1 = detail.iterator();
		while (it1.hasNext()) {
			Employee comp = (Employee) it1.next();
			if (comp.getFirstname() == null)
				comp.setFirstname("");
			if (comp.getLastname() == null)
				comp.setLastname("");
			if (comp.getEmail() == null)
				comp.setEmail("");

			System.out.println(comp.getUserId());
			System.out.println(comp.getFirstname());
			System.out.println(comp.getLastname());
			System.out.println(comp.getEmail());
			System.out.println(comp.getStatus());
		}

		return detail;
	}

	@RequestMapping(value = "/academic/schools/{id}/details", method = RequestMethod.GET)
	public ModelAndView detailsCompany(@PathVariable("id") int id,
			@ModelAttribute("OaddScholl") addSchollModel OaddScholl,
			BindingResult result, HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		vrs_company_metas role_detail = new vrs_company_metas();
		String companyName = sco.getCompanyName(cf.getCompanyID(request));

		List role_details = companyService.getCompMeta(companyService
				.getCompId(id));
		Iterator it = role_details.iterator();

		while (it.hasNext()) {
			role_detail = (vrs_company_metas) it.next();
			System.out.println("key " + role_detail.getCompanyMetasKey());
			if (role_detail.getCompanyMetasKey().equals("address")) {
				model.put("School_address", role_detail.getCompanyMetasValue());
				System.out.println("address " + role_detail.getCompanyMetasValue());
			}
			if (role_detail.getCompanyMetasKey().equals("region")) {
				model.put("School_region", role_detail.getCompanyMetasValue());
			}
			if (role_detail.getCompanyMetasKey().equals("phone")) {
				model.put("School_phone", role_detail.getCompanyMetasValue());
				System.out.println("School_phone " + role_detail.getCompanyMetasValue());
			}
			if (role_detail.getCompanyMetasKey().equals("fax")) {
				model.put("fax", role_detail.getCompanyMetasValue());
			}
			if (role_detail.getCompanyMetasKey().equals("email")) {
				model.put("email", role_detail.getCompanyMetasValue());
			}
			if (role_detail.getCompanyMetasKey().equals("website")) {
				model.put("website", role_detail.getCompanyMetasValue());
			}
			if (role_detail.getCompanyMetasKey().equals("School_headmaster")) {
				String headmaster = role_detail.getCompanyMetasValue();
				model.put("headmasterId", headmaster);
				model.put("headmasterName", sco.getvrs_Roles_metas(Integer.valueOf(headmaster), "firstname") + " " + sco.getvrs_Roles_metas(Integer.valueOf(headmaster), "lastname") );
				/*model.put("School_headmaster",
						role_detail.getCompanyMetasValue());*/
			}
			if (role_detail.getCompanyMetasKey().equals("School_baseSPP")) {
				model.put("School_baseSPP", role_detail.getCompanyMetasValue());
			}
			if (role_detail.getCompanyMetasKey().equals("School_baseSSP")) {
				model.put("School_baseSSP", role_detail.getCompanyMetasValue());
			}
			if (role_detail.getCompanyMetasKey().equals("city")) {
				model.put("city", role_detail.getCompanyMetasValue());
			}
			if (role_detail.getCompanyMetasKey().equals("province")) {
				model.put("province", role_detail.getCompanyMetasValue());
			}
			if (role_detail.getCompanyMetasKey().equals("level")) {
				model.put("level", role_detail.getCompanyMetasValue());
			}
		}

		// model.put("save", "Save");

		model.put("headerMenu", cf.menuOnHeader(request));
		model.put("myMenu", cf.menuOnSideBar(request));
		model.put("titleMessage", "Detail School | "+ companyName + " | Verse");

		String prefix = "../../";
		model.put("prefix", prefix);

		model.put("id", id);
		model.put("companyId", companyService.getCompId(Integer.valueOf(id)));
		model.put("companyName",
				companyService.getCompName(Integer.valueOf(id)));
		System.out.println("id: " + id);
		System.out.println("companyName: "
				+ companyService.getCompName(Integer.valueOf(id)));
		System.out.println("role_details: " + role_details);

		return new ModelAndView("schoolDetails", model);
	}

	@RequestMapping(value = "/academic/schools/{id}/viewDetailSchool", method = RequestMethod.POST)
	public ModelAndView viewDetailSchool(@PathVariable("id") String id,
			HttpServletRequest request) {
		List school_details = companyService.getCompMeta(companyService
				.getCompId(Integer.valueOf(id)));
		Iterator it = school_details.iterator();
		JSONObject obj = new JSONObject();
		try {
			while (it.hasNext()) {
				vrs_company_metas vrs_looping = (vrs_company_metas) it.next();

				if (vrs_looping.getCompanyMetasKey().equalsIgnoreCase("address"))
					obj.put("School_address", vrs_looping.getCompanyMetasValue());
				else if (vrs_looping.getCompanyMetasKey().equalsIgnoreCase("region"))
					obj.put("School_region", vrs_looping.getCompanyMetasValue());
				else if (vrs_looping.getCompanyMetasKey().equalsIgnoreCase("phone"))
					obj.put("School_phone", vrs_looping.getCompanyMetasValue());
				else if (vrs_looping.getCompanyMetasKey().equalsIgnoreCase("fax"))
					obj.put("School_fax", vrs_looping.getCompanyMetasValue());
				else if (vrs_looping.getCompanyMetasKey().equalsIgnoreCase("email"))
					obj.put("School_email", vrs_looping.getCompanyMetasValue());
				else if (vrs_looping.getCompanyMetasKey().equalsIgnoreCase("website"))
					obj.put("School_website",vrs_looping.getCompanyMetasValue());
				else if (vrs_looping.getCompanyMetasKey().equalsIgnoreCase("School_headmaster"))
					obj.put("School_headmaster",peopleService.getRoleName(Integer.valueOf(vrs_looping.getCompanyMetasValue())));
				else if (vrs_looping.getCompanyMetasKey().equalsIgnoreCase("School_baseSSP1"))
					obj.put("School_baseSSP1",vrs_looping.getCompanyMetasValue());
				else if (vrs_looping.getCompanyMetasKey().equalsIgnoreCase("School_baseSSP2"))
					obj.put("School_baseSSP2",vrs_looping.getCompanyMetasValue());
				else if (vrs_looping.getCompanyMetasKey().equalsIgnoreCase("city"))
					obj.put("School_city", vrs_looping.getCompanyMetasValue());
				else if (vrs_looping.getCompanyMetasKey().equalsIgnoreCase("province"))
					obj.put("School_province",vrs_looping.getCompanyMetasValue());
				obj.put("photoView", sco.getPhotobyCompanyID(Integer.valueOf(id)));
			}
			obj.put("companyName",companyService.getCompName(Integer.valueOf(id)));
		} catch (Exception ex) {
		}

		Map model = new HashMap();
		model.put("responseView", obj.toString());
		return new ModelAndView("showView", model);
	}

	@RequestMapping(value = "/academic/schools/{id}/disable", method = RequestMethod.POST)
	public ModelAndView disableSchools(@PathVariable("id") String id,
			@ModelAttribute("OaddScholl") addSchollModel OaddScholl) {
		int staffId = OaddScholl.getStaffId();

		String message = companyService.deleteFunction(Integer.valueOf(id),
				staffId);
		System.out.println(message);

		return new ModelAndView("redirect:/academic/schools");
	}

	@RequestMapping(value = "/academic/schools/{id}/closed", method = RequestMethod.POST)
	public ModelAndView closedSchools(@PathVariable("id") String id,
			@ModelAttribute("OaddScholl") addSchollModel OaddScholl) {
		int staffId = OaddScholl.getStaffId();

		String message = companyService.closeSchools(Integer.valueOf(id),
				staffId);
		System.out.println(message);

		return new ModelAndView("redirect:/academic/schools");
	}


	// Staff Paging
	@RequestMapping(value = "/academic/staffs", method = RequestMethod.GET)
	public ModelAndView listStaffActive1(
			@ModelAttribute("candidate_reject") RejectForm candidate_reject,
			HttpServletRequest request, Map<String, Object> model) {
		return listStaffActiveShow(candidate_reject, request, 1, model);
	}

	@RequestMapping(value = "/academic/staffs/{id}", method = RequestMethod.GET)
	public ModelAndView listStaffActive2(@PathVariable("id") int id,
			@ModelAttribute("candidate_reject") RejectForm candidate_reject,
			HttpServletRequest request, Map<String, Object> model) {
		return listStaffActiveShow(candidate_reject, request, id, model);
	}

	public ModelAndView listStaffActiveShow(
			@ModelAttribute("candidate_reject") RejectForm candidate_reject,
			HttpServletRequest request, int pageSelected, Map<String, Object> model) {
		model.put("headerMenu", cf.menuOnHeader(request));
		String companyName = sco.getCompanyName(cf.getCompanyID(request));
		ModelAndView showModel = new ModelAndView("page404", model);
		if (cf.canAccess(request, staffPageId, "view")) {
			int limitData = 20;
	
			int schoolId = cf.getCompanyID(request);
	
			model.put("rolestaff", companyService.viewData(schoolId, "Employee",
					(pageSelected - 1) * limitData, limitData, request));
	
			model.put("status", 1);
			CommonFunction cf = new CommonFunction();
			model = common.getHeaderModel(request, model, "Staffs | " + companyName
					+ " | Verse");
			model.put("role", "Employee");
			model.put("success", request.getParameter("success"));
			model.put("action", request.getParameter("action"));
			model.put("status", 1);
	
			pagination pg = new pagination();
			pg.CountData = companyService.getCountData(schoolId);
			pg.limitData = limitData;
			pg.serverSElected = request.getRequestURI();
			pg.pageSelected = pageSelected;
			model.put("pagination", pg.getPAgination());
			
			if (cf.canAccess(request, staffPageId, "create"))
				model.put("canCreate", true);
			else
				model.put("canCreate", false);
			
			if (cf.canAccess(request, staffPageId, "update"))
				model.put("canUpdate", true);
			else
				model.put("canUpdate", false);
			
			if (cf.canAccess(request, staffPageId, "delete"))
				model.put("canDelete", true);
			else
				model.put("canDelete", false);
			
			model.put("footer", dashboardService.getUserGuide("Staff"));
			model.put("footer_state", "List Staff");
			
			return new ModelAndView("rolestaff", model);
		}
		return showModel;
	}

	@Autowired
	StaffService staffService;

	// Disabled-Enabled-Deleted
	@RequestMapping(value = "/staffs/{id}/delete", method = RequestMethod.POST)
	public ModelAndView deletePeople(@PathVariable("id") String id,
			@ModelAttribute("candidate_reject") RejectForm candidate_reject, HttpServletRequest req) {
		int staffId = cf.getRoleIDSelected(req);
		int companyId = cf.getCompanyID(req);

		String message = staffService.deletePeople(companyId, Integer.valueOf(id),
				staffId, candidate_reject.getNote());
		System.out.println(message);

		return new ModelAndView("redirect:/academic/staffs");
	}

	@RequestMapping(value = "/staffs/{id}/disable", method = RequestMethod.POST)
	public ModelAndView disablePeople(@PathVariable("id") String id,
			@ModelAttribute("candidate_reject") RejectForm candidate_reject) {
		int staffId = candidate_reject.getStaffId();

		String message = staffService.disablePeople(Integer.valueOf(id),
				staffId, candidate_reject.getNote());
		System.out.println("message: " + message);

		return new ModelAndView("redirect:/academic/inactiveStaffs");
	}

	@RequestMapping(value = "/staffs/{id}/enable", method = RequestMethod.POST)
	public ModelAndView enablePeople(@PathVariable("id") String id,
			@ModelAttribute("candidate_reject") RejectForm candidate_reject) {
		int staffId = candidate_reject.getStaffId();

		String message = staffService.enablePeople(Integer.valueOf(id),
				staffId, candidate_reject.getNote());
		System.out.println("message: " + message);

		return new ModelAndView("redirect:/academic/staffs");
	}

	// Paging School
	@RequestMapping(value = "/academic/schools", method = RequestMethod.GET)
	public ModelAndView listSchoolActive1(HttpServletRequest request) {
		return listSchoolActiveShow(request, 1);
	}

	@RequestMapping(value = "/academic/schools/{id}", method = RequestMethod.GET)
	public ModelAndView listSchoolActive2(@PathVariable("id") int id,
			HttpServletRequest request) {
		return listSchoolActiveShow(request, id);
	}

	public String getPAge(String str) {
		String getBack = "";
		StringTokenizer stk = new StringTokenizer(str, "/");
		while (stk.hasMoreTokens()) {
			getBack = stk.nextToken();
		}
		try {
			int pageThis = Integer.valueOf(getBack);
		} catch (Exception ex) {
			getBack = "";
		}
		return getBack;
	}

	public ModelAndView listSchoolActiveShow(HttpServletRequest request, int pageSelected) {
		Map<String, Object> model=new HashMap<String, Object>();
		model.put("headerMenu", cf.menuOnHeader(request));
		String companyName = sco.getCompanyName(cf.getCompanyID(request));
		ModelAndView showModel = new ModelAndView("page404", model);
		if (cf.canAccess(request, schoolPageId, "view")) {
			int limitData = 20;
			model.put("companies", companyService.viewDataSchool((pageSelected-1)*limitData,limitData,request));
			CommonFunction cf = new CommonFunction();
			
			//school budget
			int schoolId = common.getCompanyID(request);
//			FinanceDashboardModel financeModel = accountingService.getDashboardData(schoolId);
//			model.put("financeModel", financeModel);
			
			String urlBefore=request.getRequestURI();
			model.put("thisURL", getPAge(urlBefore));
			
			model = common.getHeaderModel(request, model, "Schools | " + companyName
					+ " | Verse");
			model.put("success", request.getParameter("success"));
			model.put("action", request.getParameter("action"));
//			model.put("successEdit", request.getParameter("successEdit"));
//			model.put("actionEdit", request.getParameter("actionEdit"));
			
			if (cf.canAccess(request, schoolPageId, "create"))
				model.put("canCreate", true);
			else
				model.put("canCreate", false);
			
			System.out.println("canCreate:" + model.get("canCreate"));
			
			if (cf.canAccess(request, schoolPageId, "update"))
				model.put("canUpdate", true);
			else
				model.put("canUpdate", false);
			
			System.out.println("canUpdate:" + model.get("canUpdate"));
			
			pagination pg = new pagination();
			pg.CountData = companyService.getCountDataSchool(request);
			pg.limitData = limitData;
			pg.serverSElected = request.getRequestURI();
			pg.pageSelected = pageSelected;
			model.put("pagination", pg.getPAgination());
			
			model.put("footer", dashboardService.getUserGuide("School"));
			model.put("footer_state", "List School");
			
			return new ModelAndView("schoollist", model);
		}
		return showModel;
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/academic/schools/{id}/edit", method = RequestMethod.GET)
	public ModelAndView EditSchoolTest(@PathVariable("id") int id,
			@ModelAttribute("OaddScholl") editSchollModel OaddScholl,
			 HttpServletRequest request) {
		ModelAndView showModel = new ModelAndView("page404");
		String companyName = sco.getCompanyName(cf.getCompanyID(request));
		if (cf.canAccess(request, schoolPageId, "update")) {
			Map<String, Object> model = new HashMap<String, Object>();
	
			vrs_company_metas role_detail = new vrs_company_metas();
			String schoolLevel = null;
			int companyId = cf.getCompanyID(request);
			int roleId = cf.getRoleIDSelected(request);
			List role_details = companyService.getCompMeta(companyService
					.getCompId(id));
			Iterator it = role_details.iterator();
	
			model.put("ulrBefore", request.getParameter("beforeThis"));
	
			while (it.hasNext()) {
				role_detail = (vrs_company_metas) it.next();
				if (role_detail.getCompanyMetasKey().equals("address")) {
					model.put("address", role_detail.getCompanyMetasValue());
				}
				if (role_detail.getCompanyMetasKey().equals("phone")) {
					model.put("phone", role_detail.getCompanyMetasValue());
				}
				if (role_detail.getCompanyMetasKey().equals("fax")) {
					model.put("fax", role_detail.getCompanyMetasValue());
				}
				if (role_detail.getCompanyMetasKey().equals("email")) {
					model.put("email", role_detail.getCompanyMetasValue());
				}
				if (role_detail.getCompanyMetasKey().equals("website")) {
					model.put("website", role_detail.getCompanyMetasValue());
				}
				if (role_detail.getCompanyMetasKey().equals("School_headmaster")) {
					Integer headMAsterID = -1;
					if(!role_detail.getCompanyMetasValue().equals(""))
						headMAsterID= sco.getUSeridFromRoleID(Integer
								.valueOf(role_detail.getCompanyMetasValue()));
					model.put("School_headmaster", headMAsterID);
					model.put("School_headmasterNAme", companyService.getUserNameFromIDUSEr(headMAsterID));
				}
				if (role_detail.getCompanyMetasKey().equals("province")) {
					model.put("province", role_detail.getCompanyMetasValue());
				}
				if (role_detail.getCompanyMetasKey().equals(
						"School_baseSPPOwnership")) {
					model.put("School_baseSPPOwnership",
							role_detail.getCompanyMetasValue());
				}
				
				if (role_detail.getCompanyMetasKey().equals("School_baseSPPBasic")) {
					model.put("School_baseSPPBasic",
							role_detail.getCompanyMetasValue());
				}
				if (role_detail.getCompanyMetasKey().equals("School_baseSPPOwnership")) {
					model.put("School_baseSPPOwnership",
							role_detail.getCompanyMetasValue());
				}
				if (role_detail.getCompanyMetasKey().equals("School_baseSPPEmployee")) {
					model.put("School_baseSPPEmployee",
							role_detail.getCompanyMetasValue());
				}
				if (role_detail.getCompanyMetasKey().equals("School_baseSSPBasic")) {
					model.put("School_baseSSPBasic",
							role_detail.getCompanyMetasValue());
				}
				if (role_detail.getCompanyMetasKey().equals("School_baseSSPContinuation")) {
					model.put("School_baseSSPContinuation",
							role_detail.getCompanyMetasValue());
				}
				if (role_detail.getCompanyMetasKey().equals("School_baseSSPEmployee")) {
					model.put("School_baseSSPEmployee",
							role_detail.getCompanyMetasValue());
				}
				if (role_detail.getCompanyMetasKey().equals("School_baseSSPOwnership")) {
					model.put("School_baseSSPOwnership",
							role_detail.getCompanyMetasValue());
				}
				
				
				try {
					int countSSP = 0;
					for (countSSP=0;countSSP<6;countSSP++) { 
						String keyChoose = "School_baseSSP"+countSSP;
						if (role_detail.getCompanyMetasKey().equals(keyChoose)) {
							model.put(keyChoose,
									role_detail.getCompanyMetasValue());
						}
					}
				} catch (Exception ex) {}
				
	
				if (role_detail.getCompanyMetasKey().equals("city")) {
					model.put("city", role_detail.getCompanyMetasValue());
				}
				if (role_detail.getCompanyMetasKey().equals("level")) {
					model.put("level", role_detail.getCompanyMetasValue());
					schoolLevel = role_detail.getCompanyMetasValue();
				}
	
			}
	
	
			vrs_role_metas candidate_detail = new vrs_role_metas();
			List candidate_details = candidateService.getRoleMetas(roleId);
			model.put("roleId", roleId);
			it = candidate_details.iterator();
			while (it.hasNext()) {
				candidate_detail = (vrs_role_metas) it.next();
				if (candidate_detail.getRoleMetasKey().equals("firstname")) {					
					model.put("firstname", candidate_detail.getRoleMetasValue());
				}
				if (candidate_detail.getRoleMetasKey().equals("lastname")) {
					model.put("lastname", candidate_detail.getRoleMetasValue());
				}
			}
	
			Map<String, String> dropDownOption = new LinkedHashMap<String, String>();
	
			
	
			List<Integer> countBaseSSP = new ArrayList<Integer>();
			List<String> a = new ArrayList();
			List<String> b = new ArrayList();
			String compMetaValue0 = null, compMetaValue1 = null, compMetaValue2 = null, compMetaValue3 = null, compMetaValue4 = null, compMetaValue5 = null, compMetaValue6 = null;
			String compMetaKey0 = null, compMetaKey1 = null, compMetaKey2 = null, compMetaKey3 = null, compMetaKey4 = null, compMetaKey5 = null, compMetaKey6 = null;
	
			if (schoolLevel != null && (schoolLevel.equals("TK")
					||schoolLevel.equals("KB"))					
					) {
	
				it = role_details.iterator();
				while (it.hasNext()) {
					role_detail = (vrs_company_metas) it.next();
	
					if (role_detail.getCompanyMetasKey().equals("School_baseSSP0")) {
						compMetaValue0 = role_detail.getCompanyMetasValue();
						compMetaKey0 = role_detail.getCompanyMetasKey();
						model.put("b", compMetaKey0);
					}
					if (role_detail.getCompanyMetasKey().equals("School_baseSSP1")) {
						compMetaValue1 = role_detail.getCompanyMetasValue();
						compMetaKey1 = role_detail.getCompanyMetasKey();
						model.put("b", compMetaKey1);
					}
				}
				a.add(compMetaValue0);
				a.add(compMetaValue1);
				b.add(compMetaKey0);
				b.add(compMetaKey1);
				System.out.println(a);
				model.put("School_baseSSP", a);
	
			} else if (schoolLevel != null && schoolLevel.equals("SD")) {
	
				it = role_details.iterator();
				while (it.hasNext()) {
					role_detail = (vrs_company_metas) it.next();
	
					if (role_detail.getCompanyMetasKey().equals("School_baseSSP0")) {
						compMetaValue0 = role_detail.getCompanyMetasValue();
						compMetaKey0 = role_detail.getCompanyMetasKey();
						model.put("b", compMetaKey0);
					}
					if (role_detail.getCompanyMetasKey().equals("School_baseSSP1")) {
						compMetaValue1 = role_detail.getCompanyMetasValue();
						compMetaKey1 = role_detail.getCompanyMetasKey();
						model.put("b", compMetaKey1);
					}
					if (role_detail.getCompanyMetasKey().equals("School_baseSSP2")) {
						compMetaValue2 = role_detail.getCompanyMetasValue();
						compMetaKey2 = role_detail.getCompanyMetasKey();
						model.put("b", compMetaKey2);
					}
					if (role_detail.getCompanyMetasKey().equals("School_baseSSP3")) {
						compMetaValue3 = role_detail.getCompanyMetasValue();
						compMetaKey3 = role_detail.getCompanyMetasKey();
						model.put("b", compMetaKey3);
					}
					if (role_detail.getCompanyMetasKey().equals("School_baseSSP4")) {
						compMetaValue4 = role_detail.getCompanyMetasValue();
						compMetaKey4 = role_detail.getCompanyMetasKey();
						model.put("b", compMetaKey4);
					}
					if (role_detail.getCompanyMetasKey().equals("School_baseSSP5")) {
						compMetaValue5 = role_detail.getCompanyMetasValue();
						compMetaKey5 = role_detail.getCompanyMetasKey();
						model.put("b", compMetaKey5);
					}
					if (role_detail.getCompanyMetasKey().equals("School_baseSSP6")) {
						compMetaValue6 = role_detail.getCompanyMetasValue();
						compMetaKey6 = role_detail.getCompanyMetasKey();
						model.put("b", compMetaKey6);
					}
	
				}
				a.add(compMetaValue0);
				a.add(compMetaValue1);
				a.add(compMetaValue2);
				a.add(compMetaValue3);
				a.add(compMetaValue4);
				a.add(compMetaValue5);
				a.add(compMetaValue6);
				b.add(compMetaKey0);
				b.add(compMetaKey1);
				b.add(compMetaKey2);
				b.add(compMetaKey3);
				b.add(compMetaKey4);
				b.add(compMetaKey5);
				b.add(compMetaKey6);
				System.out.println(a);
				model.put("School_baseSSP", a);
			} else if (schoolLevel != null && schoolLevel.equals("SMP")
					|| schoolLevel != null && schoolLevel.equals("SMA")) {
	
				it = role_details.iterator();
				while (it.hasNext()) {
					role_detail = (vrs_company_metas) it.next();
	
					if (role_detail.getCompanyMetasKey().equals("School_baseSSP0")) {
						compMetaValue0 = role_detail.getCompanyMetasValue();
						compMetaKey0 = role_detail.getCompanyMetasKey();
						model.put("b", compMetaKey0);
					}
					if (role_detail.getCompanyMetasKey().equals("School_baseSSP1")) {
						compMetaValue1 = role_detail.getCompanyMetasValue();
						compMetaKey1 = role_detail.getCompanyMetasKey();
						model.put("b", compMetaKey1);
					}
					if (role_detail.getCompanyMetasKey().equals("School_baseSSP2")) {
						compMetaValue2 = role_detail.getCompanyMetasValue();
						compMetaKey2 = role_detail.getCompanyMetasKey();
						model.put("b", compMetaKey2);
					}
					if (role_detail.getCompanyMetasKey().equals("School_baseSSP3")) {
						compMetaValue3 = role_detail.getCompanyMetasValue();
						compMetaKey3 = role_detail.getCompanyMetasKey();
						model.put("b", compMetaKey3);
					}
				}
				a.add(compMetaValue0);
				a.add(compMetaValue1);
				a.add(compMetaValue2);
				a.add(compMetaValue3);
				b.add(compMetaKey0);
				b.add(compMetaKey1);
				b.add(compMetaKey2);
				b.add(compMetaKey3);
				System.out.println(a);
				model.put("School_baseSSP", a);
			}
			
			String regFormPrice = candidateService.getCompanyMetaValue(id, "School_registrationFormPrice");
			if(regFormPrice == null)
				regFormPrice = "0";
			model.put("regFormPrice", regFormPrice);
	
			model = common.getHeaderModel(request, model, "Edit School | " + companyName
					+ " | Verse");
			String prefix = "../../";
			model.put("prefix", prefix);
	
			
			model.put("id", id);
			model.put("companyId", companyService.getCompId(Integer.valueOf(id)));
			model.put("companyName",companyService.getCompName(companyService.getCompId(Integer.valueOf(id))));
			model.put("positionChangedList", companyService.getpositionList());
			
			Map<String, String> headMAsterList = companyService.getHeadMasterList(request);
			model.put("headmasterOptionChangedList", getheadmasterOptionChangedList(headMAsterList));
			
			
			System.out.println("id: " + id);
			System.out.println("companyName: "	+ companyService.getCompName(companyService.getCompId(Integer.valueOf(id))));
			System.out.println("role_details: " + role_details);
			System.out.println("modified: " + companyService.getModifiedTime(companyService.getCompId(Integer.valueOf(id))));
	
			Timestamp modified = companyService.getModifiedTime(companyService.getCompId(Integer.valueOf(id)));
			String getTime = modified.getHours() + ":" + modified.getMinutes();
			String getDate = new SimpleDateFormat("dd/MM/yyyy").format(modified);
			model.put("TimeModified", getTime);
			model.put("DateModified", getDate);
		    System.out.println("getToDate: " + getDate);
		    System.out.println("getTime: " + modified.getHours() + ":" + modified.getMinutes());
		    
		    model.put("footer", dashboardService.getUserGuide("School"));
			model.put("footer_state", "Edit School");
			
			return new ModelAndView("schoolEdit", model);
		}
		
		return showModel;
		
	}
		
		

	public String getheadmasterOptionChangedList(
			Map<String, String> headMAsterList) {
		int i = 0;
		String getBack = "";
		Iterator it = headMAsterList.keySet().iterator();
		String str = "";
		while (it.hasNext()) {
			str = (String) it.next();
			getBack += "<option value=\"" + str + "\">"
					+ headMAsterList.get(str) + "</option>";
		}
		return getBack;
	}

	@RequestMapping(value = "/academic/schools/{id}/update", method = RequestMethod.POST)
	public ModelAndView updateSchoolTest(@PathVariable("id") int id,
			@ModelAttribute("OaddScholl") @Valid editSchollModel OaddScholl,
			BindingResult result, java.util.Map<String, Object> model,
			HttpServletRequest request) {
		CommonFunction cf = new CommonFunction();
		int roleID = cf.getRoleIDSelected(request);
		boolean success = true;
		String message = "";
		System.out.println("try save");
		message = companyService.updateSchool(roleID, id, OaddScholl,request);
		if(!message.equals("Success!"))
			success = false;
		if (success) {
			String urlNext = "redirect:/academic/schools";
			if (request.getParameter("urlBefore").length() > 0)
				urlNext += "/" + request.getParameter("urlBefore");
			
			ModelAndView modelAndView = new ModelAndView(urlNext);
			model = modelAndView.getModel();
			model.put("success", "Success!");
			model.put("action", "Your changes have been updated");
			modelAndView.addObject(model);
			return modelAndView;
		} else {
			model.put("message", message);
			return EditSchoolTest(id,OaddScholl, request);
		}
	}

	@RequestMapping(value = "/academic/schools/change-headmaster", method = RequestMethod.POST)
	public ModelAndView changeHeadMaster(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		String msg = companyService.changeHeadMASter(request);

		model.put("responseView", msg);
		return new ModelAndView("showView", model);
	}

}
