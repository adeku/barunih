package verse.company.service;

//import java.util.List;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import verse.academic.course.model.addCourseListModel;
import verse.company.model.AddUser;
import verse.company.model.CompanyList;
import verse.company.model.CompanyUserList;
import verse.company.model.addSchollModel;
import verse.company.model.editSchollModel;
import verse.model.RoleStaff;
import verse.model.vrs_companies;
import verse.model.vrs_company_metas;
import verse.model.vrs_roles;


public interface CompanyService {
	
	public void addCompany(vrs_companies company, vrs_company_metas comp_metas, vrs_roles role);	

	public List<CompanyList> listCompanies(int userId);
	
//	public Map<Integer, String> getCompanies (int userId);
	
	public String addUser(vrs_roles role, String email, String key, String value, String message);
	
	public int getUserId(String key, String value);
	
	public String getEmail(int userId);
	
	public List<CompanyUserList> listUsers(int companyId);
	
	public void addSubCompany(vrs_companies company, vrs_company_metas compMeta);
	
	public void addSchool(addSchollModel OaddScholl,HttpServletRequest request);
	
	public String getMessage();
	
	public Map<String, String> getHeadMasterList(HttpServletRequest request);
	
	public Map<String, String> getSchoolEdited(HttpServletRequest request);
	
	public String getProfileSchool(int comp_id,HttpServletRequest request);
	
	public void editSchool(addSchollModel OaddScholl,HttpServletRequest request);
	
	public Map<Integer, String> getHeadSchool(int userId);
	public Map<String, String> getTeacher();
	
	public vrs_roles getRole(int userId, int compId);
	
	public String getSchoolAddress(int CompanyId);
	
	public List<vrs_companies> getSchoolList();
	public List<vrs_companies> getSchoolList2(int id, addSchollModel OaddScholl, HttpServletRequest request);
	public List getCompMeta(int companyId);
	public int getCompId(int companyId);
	public String getCompName(int companyId);
	public List<RoleStaff> getAllStaffs(int companyId);
//	public String deleteFunction(int id, int userId, String note) ;
	public String deleteFunction(int id, int userId) ;
	public String getCompIdX(int userId);
	public vrs_companies getSchoolComp(int companyId);
	public String updateCompanies(vrs_companies companies);
	public vrs_company_metas getMetaComp(int companyId);
	public Map<Integer, String> getCompanies(int userId2);
//	public String closeSchools(int id, int userId, String note) ;
	public String closeSchools(int id, int userId) ;
	public String getSchoolEditedTest(HttpServletRequest request);
	public int getSchoolEditedTest2(HttpServletRequest request);
	
	public int getCountData(int companyId);
	public List viewData(int companyId, String role, int offset,int limit, HttpServletRequest request);
	
	public int getCountDataSchool( HttpServletRequest request);
	public List viewDataSchool(int offset, int limit, HttpServletRequest request);
	public String updateSchool(int roleID, int schoolId, editSchollModel schoolData, HttpServletRequest request);
	public Timestamp getModifiedTime(int companyId);
	public String getpositionList();
	public String changeHeadMASter(HttpServletRequest request);
	public String getUserNameFromIDUSEr(int userID);
}
