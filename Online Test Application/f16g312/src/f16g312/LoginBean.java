package f16g312;

import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.net.InetAddress;

@ManagedBean
@SessionScoped
public class LoginBean {
	private String firstName;
	private int userId;
	private String role;
	private ResultSet rs;
	private boolean authenticationFailed;
	private boolean renderInstructor;

	private String clientIp;
	private Date loginTime;
	private String login_date;

	public String getLogin_date() {
		return login_date;
	}

	public void setLogin_date(String login_date) {
		this.login_date = login_date;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public boolean isRenderInstructor() {
		return renderInstructor;
	}

	public void setRenderInstructor(boolean renderInstructor) {
		this.renderInstructor = renderInstructor;
	}

	private String username;
	private String password;

	private String dbUserName;
	private String dbPassword;
	private String dbmsHost;
	private String dbms;
	private String dbSchema;

	public String getDbUserName() {
		return dbUserName;
	}

	public void setDbUserName(String dbUserName) {
		this.dbUserName = dbUserName;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbpassword) {
		this.dbPassword = dbpassword;
	}

	public String getDbmsHost() {
		return dbmsHost;
	}

	public void setDbmsHost(String dbmsHost) {
		this.dbmsHost = dbmsHost;
	}

	public String getDbms() {
		return dbms;
	}

	public void setDbms(String dbms) {
		this.dbms = dbms;
	}

	public String getDbSchema() {
		return dbSchema;
	}

	public void setDbSchema(String dbSchema) {
		this.dbSchema = dbSchema;
	}

	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@PostConstruct
	public void init() {

		authenticationFailed = false;
		renderInstructor = false;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isAuthenticationFailed() {
		return authenticationFailed;
	}

	public void setAuthenticationFailed(boolean authenticationFailed) {
		this.authenticationFailed = authenticationFailed;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String Validate() {
		int check = 0;
		try {
			String query = "select * from f16g312_user;";
			DatabaseAccessBean dbaccess = new DatabaseAccessBean();
			Connection conn = dbaccess.connectToDatabase();
			rs = dbaccess.execQuery(query);
			while (rs.next()) {
				String userName_db = rs.getString(1);
				String password_db = rs.getString(2);
				if (username.equalsIgnoreCase(userName_db) && password.equals(password_db)) {
					this.userId = rs.getInt(6);
					this.firstName = rs.getString(4);
					this.role = rs.getString(5);
					check = 1;
					dbaccess.uin = this.userId;
					break;

				}
			}

			conn.close();
			if (check == 0) {
				authenticationFailed = true;
				return "false";
			} else {

				InetAddress iAddress = InetAddress.getLocalHost();
				clientIp = iAddress.getHostAddress();

				loginTime = new Date();
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				login_date = dateFormat.format(loginTime);

				if (role.equalsIgnoreCase("student"))
					return "Student";
				else if (role.equalsIgnoreCase("instructor")) {
					renderInstructor = true;
					return "Instructor";
				} else if (role.equalsIgnoreCase("TA"))
					return "TA";
				else if (role.equalsIgnoreCase("Admin")) {
					return "Admin";
				} else {
					authenticationFailed = true;
					return "false";
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
	}

	public String validateDBLogin() {

		try {
			System.out.println("validateDBLogin");
			System.out.println(dbUserName);
			System.out.println(dbmsHost);
		 if(dbUserName.equals("f16g312") &&
			// dbPassword.equals("g312U7qJDd") &&
			// if(dbUserName.equals("root") &&
					 dbPassword.equals("g312U7qJDd") &&
			((dbmsHost.equals("131.193.209.54") || dbmsHost.equals("131.193.209.57")
							|| dbmsHost.equals("localhost")) && dbSchema.equals("f16g312")
							&& dbms.equalsIgnoreCase("mysql"))) {
				System.out.println("test");
				return "DBPage";
			}

			else {
				authenticationFailed = true;
				return "false";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "logout";
	}

	public String doAction() {
		String url = FacesContext.getCurrentInstance().getViewRoot().getViewId();
		return "/target?faces-redirect=true&backurl=" + url;
	}

	public String viewInstructor() {
		return "BackToInstructor";
	}

}
