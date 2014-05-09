package verse.company.model;

public class AddUser {
	// Start of Variable Declaration
		private int companyId;
		private String companyName;
		private String userEmail;
		private String message;
		private String key;
		private String value;
		private String role;
		// End of Variable Declaration
		
		// Constructor
		public AddUser() {
		}
		
		// Start of Get and Set Declaration
		public int getCompanyId() {
			return companyId;
		}

		public void setCompanyId(int companyId) {
			this.companyId = companyId;
		}
		
		public String getUserEmail() {
			return userEmail;
		}

		public void setUserEmail(String userEmail) {
			this.userEmail = userEmail;
		}
		
		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
		
		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}
		
		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		
		public String getCompanyName() {
			return companyName;
		}

		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}
		
		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}
		// End of Get and Set Declaration
}
