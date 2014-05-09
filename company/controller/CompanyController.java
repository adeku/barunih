package verse.company.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import verse.model.vrs_companies;
import verse.model.vrs_company_metas;
import verse.model.vrs_user_metas;
import verse.model.vrs_users;
import verse.academic.assignment.model.AssignmentForm;
import verse.academic.model.ved_assignments;
import verse.commonClass.CommonFunction;
import verse.company.model.AddSubCompany;
import verse.company.model.AddUser;
import verse.company.model.CompanyDetail;
import verse.company.model.CompanyUserList;
import verse.company.model.CreateCompany;
import verse.model.vrs_roles;
import verse.company.service.CompanyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/companies")
public class CompanyController {

	@Autowired
	private CompanyService companyService;
	// private RoleService roleService;

	CommonFunction common = new CommonFunction();

	private vrs_roles role;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView addCompany(
			@ModelAttribute("company") CreateCompany company,
			BindingResult result, HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CommonFunction cf = new CommonFunction();
		model.put("headerMenu", cf.menuOnHeader(request));
		model.put("myMenu", cf.menuOnSideBar(request));
		model.put("titleMessage", "Company");
		String prefix = "../";
		model.put("prefix", prefix);
		return new ModelAndView("companyCreate", model);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveCompany(
			@ModelAttribute("company") CreateCompany company,
			BindingResult result, HttpServletRequest request) {
		int userId = Integer.valueOf(request.getSession()
				.getAttribute("u9988u").toString());
		// Set Company Field
		vrs_companies comp = new vrs_companies();
		// comp.setCreatedBy(userId);
		// comp.setModifiedBy(userId);
		comp.setCompanyName(company.getCompanyName());
		comp.setCompanyType(company.getCompanyType());

		// Set Company Metas Field
		vrs_company_metas comp_metas = new vrs_company_metas();
		comp_metas.setCompanyMetasKey("Address");
		comp_metas.setCompanyMetasValue(company.getAddress());

		// Set Role Field
		vrs_roles role = new vrs_roles();
		role.setUserId(userId);

		companyService.addCompany(comp, comp_metas, role);

		return new ModelAndView("redirect:/companies");
	}



	// Controller Company Add User
	@RequestMapping(value = "/addUser", method = RequestMethod.GET)
	public ModelAndView addUser(@ModelAttribute("addUser") AddUser addUser,
			BindingResult result, HttpServletRequest request) {
		int userId = Integer.valueOf(request.getSession()
				.getAttribute("u9988u").toString());
		Map<String, Object> model = new HashMap<String, Object>();
		Map<Integer, String> comp = new LinkedHashMap<Integer, String>();
		Map<String, String> credential = new LinkedHashMap<String, String>();

		List<String> allowed_cred = new ArrayList<String>();
		allowed_cred.add("IDCard");
		allowed_cred.add("FBId");

		// Get User's Companies
		comp = companyService.getCompanies(userId);
		for (Iterator iterator = allowed_cred.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			credential.put(string, string);
		}

		model.put("comp", comp);
		model.put("credential", credential);
		model.put("headerMenu", common.menuOnHeader(request));
		model.put("myMenu", common.menuOnSideBar(request));
		model.put("titleMessage", "Company Add User");

		String prefix = "../";
		model.put("prefix", prefix);
		// System.out.println(comp);
		return new ModelAndView("addUser", model);
	}

	// Controller Company Save User
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public ModelAndView saveUser(@ModelAttribute("addUser") AddUser addUser,
			BindingResult result, HttpServletRequest request) {
		int userId = Integer.valueOf(request.getSession()
				.getAttribute("u9988u").toString());
		Map<String, Object> model = new HashMap<String, Object>();
		Map<Integer, String> comp = new LinkedHashMap<Integer, String>();
		Map<String, String> credential = new LinkedHashMap<String, String>();

		List<String> allowed_cred = new ArrayList<String>();
		allowed_cred.add("IDCard");
		allowed_cred.add("FBId");

		// Get User's Companies
		comp = companyService.getCompanies(userId);
		for (Iterator iterator = allowed_cred.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			credential.put(string, string);
		}
		/*
		 * credential.put("IDCard", "IDCard"); credential.put("FBId", "FBId");
		 */

		String key = "";
		if (allowed_cred.contains(addUser.getKey()))
			key = addUser.getKey();
		String value = addUser.getValue();
		String email = addUser.getUserEmail();
		String eMessage = addUser.getMessage();
		String message = "";

		vrs_roles role = new vrs_roles();
		role.setCompanyId(addUser.getCompanyId());
		role.setRole(addUser.getRole());

		message = companyService.addUser(role, email, key, value, eMessage);
		if (key == "" && message == "Success")
			message = "Success, but credential didn't saved because field "
					+ addUser.getKey() + " doesn't exist";
		model.put("message", message);
		model.put("comp", comp);
		model.put("credential", credential);
		model.put("headerMenu", common.menuOnHeader(request));
		model.put("myMenu", common.menuOnSideBar(request));
		model.put("titleMessage", "Company Add User");

		String prefix = "../";
		model.put("prefix", prefix);

		addUser = new AddUser();

		return new ModelAndView("addUser", model);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listCompanies(HttpServletRequest request) {
		// Take Session User ID
		int userId = Integer.valueOf(request.getSession()
				.getAttribute("u9988u").toString());

		Map<String, Object> model = new HashMap<String, Object>();

		model.put("companies", companyService.listCompanies(userId));

		System.out.println("companies"
				+ companyService.listCompanies(userId));
//		System.out.println("getCompany::: " + companyService.getCompIdX());
		System.out.println("userID::: " + userId);

		CommonFunction cf = new CommonFunction();
		model.put("headerMenu", cf.menuOnHeader(request));
		model.put("myMenu", cf.menuOnSideBar(request));
		model.put("titleMessage", "Company");
		String prefix = "../";
		model.put("prefix", prefix);
		return new ModelAndView("companylist", model);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView showCompany(@PathVariable("id") String id) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<CompanyUserList> userList = companyService.listUsers(Integer
				.valueOf(id));
		List<CompanyDetail> detail = new ArrayList<CompanyDetail>();
		CompanyDetail temp = new CompanyDetail();

		int userId = 0;
		int size = userList.size();
		Iterator it = userList.iterator();
		while (it.hasNext()) {
			CompanyUserList comp = (CompanyUserList) it.next();
			if (userId == 0) {

				System.out.println("masukkkkkkkk awal");
				userId = comp.getUserId();
				temp.setUserId(userId);
				temp.setRole(comp.getRole());
			} else if (userId != comp.getUserId()) {
				System.out.println("masukkkkkkkk");
				userId = comp.getUserId();
				detail.add(temp);
				temp = new CompanyDetail();
				temp.setUserId(userId);
				temp.setRole(comp.getRole());
			}

			System.out.println("key:" + comp.getKey());

			if (comp.getKey().equals("firstname")) {

				temp.setFirstName(comp.getValue());
				System.out.println("masukkkkkkkk firstname"
						+ temp.getFirstName());
			} else if (comp.getKey().equals("lastname")) {
				temp.setLastName(comp.getValue());
				System.out.println("masukkkkkkkk lasname" + temp.getLastName());
			} else if (comp.getKey().equals("email")) {
				temp.setEmail(comp.getValue());
				System.out.println("masukkkkkkkk em" + temp.getEmail());
			}
			System.out.println(comp.getKey() + comp.getValue());
			// System.out.println(temp.getey() + comp.getValue());

		}
		System.out.println("size:" + size);
		if (size != 0) {
			detail.add(temp);
		}
		Iterator it1 = detail.iterator();
		while (it1.hasNext()) {
			CompanyDetail comp = (CompanyDetail) it1.next();
			if (comp.getFirstName() == null)
				comp.setFirstName("");
			if (comp.getLastName() == null)
				comp.setLastName("");
			if (comp.getEmail() == null)
				comp.setEmail("");
			System.out.println(comp.getUserId());
			System.out.println(comp.getFirstName());
			System.out.println(comp.getLastName());
			System.out.println(comp.getEmail());
		}

		// model.put("companies",
		// companyService.listUsers(Integer.valueOf(id)));
		model.put("detail", detail);
		model.put("id", id);
		return new ModelAndView("/company/showCompany", model);
	}

	// company add sub company
	@RequestMapping(value = "/addSubCompany", method = RequestMethod.GET)
	public ModelAndView addSubCompany(
			@ModelAttribute("addSubCompany") AddSubCompany addSubCompany,
			BindingResult result, HttpServletRequest request) {

		int userId = Integer.valueOf(request.getSession()
				.getAttribute("u9988u").toString());
		Map<String, Object> model = new HashMap<String, Object>();
		Map<Integer, String> comp = new LinkedHashMap<Integer, String>();
		Map<String, String> type = new LinkedHashMap<String, String>();

		System.out.println("Masuk Sini Controller add : " + userId);

		comp = companyService.getCompanies(userId);
		type.put("Region", "Region");
		type.put("Outlet", "Outlet");
		model.put("comp", comp);
		model.put("type", type);
		return new ModelAndView("/company/addSubCompany", model);
	}

	@RequestMapping(value = "/addSubCompany", method = RequestMethod.POST)
	public ModelAndView saveSubCompany(
			@ModelAttribute("addSubCompany") AddSubCompany addSubCompany,
			BindingResult result, HttpServletRequest request) {

		System.out
				.println("getCompanyName : " + addSubCompany.getCompanyName());

		int userId = Integer.valueOf(request.getSession()
				.getAttribute("u9988u").toString());
		Map<String, Object> model = new HashMap<String, Object>();
		Map<Integer, String> company = new LinkedHashMap<Integer, String>();
		Map<String, String> type = new LinkedHashMap<String, String>();

		// companyParent + companyType
		company = companyService.getCompanies(userId);

		type.put("Region", "Region");
		type.put("Outlet", "Outlet");

		vrs_companies companies = new vrs_companies();

		vrs_roles role = companyService.getRole(userId,
				companies.getCompanyId());

		companies.setParent(addSubCompany.getCompanyId());
		companies.setCompanyName(addSubCompany.getCompanyName());
		companies.setCompanyType(addSubCompany.getType());
		companies.setCreatedBy(role.getRoleId());
		companies.setModifiedBy(role.getRoleId());

		vrs_company_metas comp_metas = new vrs_company_metas();
		comp_metas.setCompanyId(companies.getCompanyId());
		comp_metas.setCompanyMetasKey("Address");
		comp_metas.setCompanyMetasValue(addSubCompany.getAddress());

		System.out.println("Masuk Sini Controller save");
		System.out.println("companyController : " + companies.getCompanyId());

		companyService.addSubCompany(companies, comp_metas);
		model.put("company", company);
		model.put("type", type);

		return new ModelAndView("/company/addSubCompany", model);
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView editCompany(@PathVariable("id") int id,
			@ModelAttribute("company") CreateCompany company,
			BindingResult result, java.util.Map<String, Object> model,
			HttpServletRequest request) {

		CommonFunction cf = new CommonFunction();
		vrs_companies companies = companyService.getSchoolComp(id);

		company.setCompanyName(companies.getCompanyName());
		company.setCompanyType(companies.getCompanyType());

		// Set Company Metas Field
		vrs_company_metas comp_metas = companyService.getMetaComp(id);
		if (comp_metas.getCompanyMetasKey().equals("Address")) {
			company.setAddress(comp_metas.getCompanyMetasValue());
			System.out.println("address" + comp_metas.getCompanyMetasValue());
		}

		model.put("headerMenu", cf.menuOnHeader(request));
		model.put("myMenu", cf.menuOnSideBar(request));
		model.put("titleMessage", "Company");
		model.put("companyName", companies.getCompanyName());
		model.put("companyType", companies.getCompanyType());
		model.put("address", company.getAddress());
		String prefix = "../";
		model.put("prefix", prefix);

		return new ModelAndView("companyEdit", model);
	}

	@RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
	public ModelAndView updateCompany(@PathVariable("id") int id,
			@ModelAttribute("company") CreateCompany company,
			BindingResult result, java.util.Map<String, Object> model) {
		// int schoolId = 42;
		int roleId = 74;

		vrs_companies companies = companyService.getSchoolComp(id);

		companies.setCompanyName(company.getCompanyName());
		companies.setCompanyType(company.getCompanyType());

		vrs_company_metas comp_metas = companyService.getMetaComp(id);

		if (comp_metas.getCompanyMetasKey().equals("Address"))
			comp_metas.setCompanyMetasValue(company.getAddress());
		// System.out.println("address" + comp_metas.getCompanyMetasValue());}

		String message = companyService.updateCompanies(companies);

		model.put("message", message);

		return new ModelAndView("companyEdit", model);
	}
}
