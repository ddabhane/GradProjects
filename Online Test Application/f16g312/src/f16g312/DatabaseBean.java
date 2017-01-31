package f16g312;


public class DatabaseBean {
	private String userName;
	private String password;
	private String dbms;
	private String dbmsHost;
	private String databaseSchema;
	
	
	
	public DatabaseBean() {
		super();
		/*this.userName = "root";
		this.password = "root@123";
		this.dbms = "mysql";
		this.dbmsHost = "localhost:3306";
		this.databaseSchema = "f16g312";*/
		
/*		this.userName = "s16g40";
		this.password = "s16g40FpqU5";
		this.dbms = "mysql";
		this.dbmsHost = "131.193.209.57";
		this.databaseSchema = "s16g40"; */
		
		this.userName = "f16g312";
		this.password = "g312U7qJDd";
		this.dbms = "mysql";
		this.dbmsHost = "131.193.209.57";
		this.databaseSchema = "f16g312"; 
		

	}

	public DatabaseBean(String userName, String password, String dbms, String dbmsHost, String databaseSchema) {
		super();
		this.userName = userName;
		this.password = password;
		this.dbms = dbms;
		this.dbmsHost = dbmsHost;
		this.databaseSchema = databaseSchema;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDbms() {
		return dbms;
	}
	public void setDbms(String dbms) {
		this.dbms = dbms;
	}
	public String getDbmsHost() {
		return dbmsHost;
	}
	public void setDbmsHost(String dbmsHost) {
		this.dbmsHost = dbmsHost;
	}
	public String getDatabaseSchema() {
		return databaseSchema;
	}
	public void setDatabaseSchema(String databaseSchema) {
		this.databaseSchema = databaseSchema;
	}
	
	

}
