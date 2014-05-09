package verse.company.model;

import java.util.List;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


public class editSchollModel {
	@NotEmpty(message = "Text is Required")
	private String name;	
	@NotEmpty(message = "Text is Required")
	private String address;
	@NotEmpty(message = "Text is Required")
	private String phone;	
	@NotEmpty(message = "Text is Required")
	private String email;	
	private String save;	
	private String schoolId;
	@NotEmpty(message = "Text is Required")
	private String city;	
	@NotEmpty(message = "Text is Required")
	private String province;
	private String format;
	private String headmaster;	
	private String fax;	
	private String website;	
	private String registrationFormPrice;

	@NotEmpty(message = "Text is Required")
	private String baseSPPBasic;
	@NotEmpty(message = "Text is Required")
	private String baseSPPOwnership;
	@NotEmpty(message = "Text is Required")
	private String baseSPPEmployee;
	
	@NotEmpty(message = "Text is Required")
	private String baseSSPBasic;
	@NotEmpty(message = "Text is Required")
	private String baseSSPContinuation;
	@NotEmpty(message = "Text is Required")
	private String baseSSPEmployee;
	@NotEmpty(message = "Text is Required")
	private String baseSSPOwnership;
	private List<String> baseSSP;
	
	private String companyId;
	private String termLabels;
	private String emailSchool;
	private String phoneSchool;
	@URL
	private String websiteSchool;
	private int staffId;
	private String note;	
	private CommonsMultipartFile fileCompany;	
	private String fileName;	
	private String fileType;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSave() {
		return save;
	}

	public void setSave(String save) {
		this.save = save;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}


	public String getHeadmaster() {
		return headmaster;
	}

	public void setHeadmaster(String headmaster) {
		this.headmaster = headmaster;
	}


	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getTermLabels() {
		return termLabels;
	}

	public void setTermLabels(String termLabels) {
		this.termLabels = termLabels;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
	
	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getEmailSchool() {
		return emailSchool;
	}

	public void setEmailSchool(String emailSchool) {
		this.emailSchool = emailSchool;
	}

	public String getPhoneSchool() {
		return phoneSchool;
	}

	public void setPhoneSchool(String phoneSchool) {
		this.phoneSchool = phoneSchool;
	}

	public String getWebsiteSchool() {
		return websiteSchool;
	}

	public void setWebsiteSchool(String websiteSchool) {
		this.websiteSchool = websiteSchool;
	}

	public List<String> getBaseSSP() {
		return baseSSP;
	}

	public void setBaseSSP(List<String> baseSSP) {
		this.baseSSP = baseSSP;
	}

	public CommonsMultipartFile getFileCompany() {
		return fileCompany;
	}

	public void setFileCompany(CommonsMultipartFile fileCompany) {
		this.fileCompany = fileCompany;
		this.setFileName(fileCompany.getOriginalFilename());
		this.setFileType(fileCompany.getFileItem().getContentType());
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getRegistrationFormPrice() {
		return registrationFormPrice;
	}

	public void setRegistrationFormPrice(String registrationFormPrice) {
		this.registrationFormPrice = registrationFormPrice;
	}

	public String getBaseSPPBasic() {
		return baseSPPBasic;
	}

	public void setBaseSPPBasic(String baseSPPBasic) {
		this.baseSPPBasic = baseSPPBasic;
	}

	public String getBaseSPPOwnership() {
		return baseSPPOwnership;
	}

	public void setBaseSPPOwnership(String baseSPPOwnership) {
		this.baseSPPOwnership = baseSPPOwnership;
	}

	public String getBaseSPPEmployee() {
		return baseSPPEmployee;
	}

	public void setBaseSPPEmployee(String baseSPPEmployee) {
		this.baseSPPEmployee = baseSPPEmployee;
	}

	public String getBaseSSPBasic() {
		return baseSSPBasic;
	}

	public void setBaseSSPBasic(String baseSSPBasic) {
		this.baseSSPBasic = baseSSPBasic;
	}

	public String getBaseSSPContinuation() {
		return baseSSPContinuation;
	}

	public void setBaseSSPContinuation(String baseSSPContinuation) {
		this.baseSSPContinuation = baseSSPContinuation;
	}

	public String getBaseSSPEmployee() {
		return baseSSPEmployee;
	}

	public void setBaseSSPEmployee(String baseSSPEmployee) {
		this.baseSSPEmployee = baseSSPEmployee;
	}

	public String getBaseSSPOwnership() {
		return baseSSPOwnership;
	}

	public void setBaseSSPOwnership(String baseSSPOwnership) {
		this.baseSSPOwnership = baseSSPOwnership;
	}
}
