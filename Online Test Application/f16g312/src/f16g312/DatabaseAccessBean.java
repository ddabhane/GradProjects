package f16g312;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
//import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
//import javax.faces.model.SelectItem;

@ManagedBean(name = "databaseAccessBean")
@SessionScoped
public class DatabaseAccessBean {

	static Connection connection;
	DatabaseMetaData databaseMetaData;
	Statement statement;
	ResultSet resultSet, rs;
	ResultSetMetaData resultSetMetaData;
	ResultSet result;
	DatabaseBean databaseBean;
	String query;
	List<String> displayRows;
	/******* ADDED BY KRITIKA FOR DATABASE FUNCTIONALITY *********/
	List<String> tables;
	List<String> selectedTables;
	List<String> columns;
	boolean renderTableList;
	boolean renderQuery;
	/**********************************************************/
	static int uin;

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, Object> m = context.getExternalContext().getSessionMap();
		databaseBean = (DatabaseBean) m.get("databaseBean");
		renderQuery = false;
		/*******
		 * ADDED BY KRITIKA FOR displaying table list on startup
		 *********/
		String mt = listTables();
		/**********************************************************/
	}

	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

	public DatabaseBean getDatabaseBean() {
		return databaseBean;
	}

	public void setDatabaseBean(DatabaseBean databaseBean) {
		this.databaseBean = databaseBean;
	}

	public List<String> getDisplayRows() {
		return displayRows;
	}

	public void setDisplayRows(List<String> displayRows) {
		this.displayRows = displayRows;
	}

	public List<String> getSelectedTables() {
		return selectedTables;
	}

	public void setSelectedTables(List<String> selectedTables) {
		this.selectedTables = selectedTables;
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	public boolean isRenderTableList() {
		return renderTableList;
	}

	public void setRenderTableList(boolean renderTableList) {
		this.renderTableList = renderTableList;
	}

	public boolean isRenderQuery() {
		return renderQuery;
	}

	public void setRenderQuery(boolean renderQuery) {
		this.renderQuery = renderQuery;
	}

	public List<String> getTables() {
		return tables;
	}

	public void setTables(List<String> tables) {
		this.tables = tables;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Connection connectToDatabase() {
		Connection conn = null;
		DatabaseBean daib = new DatabaseBean();
		String jdbcDriver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://" + daib.getDbmsHost() + "/" + daib.getDatabaseSchema();
		// ":3306/"
		try {
			Class.forName(jdbcDriver);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(url, daib.getUserName(), daib.getPassword());
		} catch (SQLException e) {
			/******* ADDED BY KRITIKA FOR connection failure *********/
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Connection to Database Failed!"));
			/**********************************************************/
			e.printStackTrace();
		}
		return conn;
	}

	public String createTables() {
		try {
			statement = connectToDatabase().createStatement();

			String createRosterActgTableQuery = "create table if not exists f16g312.f16g312_roster ( "
					+ "uin integer not null ," + "netid varchar(25) not null ," + "lastname varchar(50),"
					+ "firstname varchar(50)," + "role varchar(20)," + "primary key (uin)"
					+ "course varchar(25) not null ," + ")";

			String createRosterAdbmsTableQuery = "create table if not exists f16g312.f16g312_rosteradbms ( "
					+ "uin integer not null ," + "netid varchar(25) not null ," + "lastname varchar(50),"
					+ "firstname varchar(50)," + "role varchar(20)," + "primary key (uin)" + ")";

			String createScoreRoster = "CREATE TABLE if not exists f16g312.f16g312_scoreRoster ("
					+ "Last_Name VARCHAR(45) NULL, First_Name VARCHAR(45) NULL, Username VARCHAR(45) NULL, "
					+ "Student_ID INT NULL, Last_Access DATETIME NULL, Availability VARCHAR(45) NULL, "
					+ "Total DECIMAL(10,2) NULL, Exam01 DECIMAL(10,2) NULL, "
					+ "Exam02 DECIMAL(10,2) NULL, Exam03 DECIMAL(10,2) NULL, "
					+ "Project DECIMAL(10,2) NULL, Course varchar(45) NULL);";

			String createRosterStatsTableQuery = "create table if not exists f16g312.f16g312_rosterstats ( "
					+ "uin integer not null ," + "netid varchar(25) not null ," + "lastname varchar(50),"
					+ "firstname varchar(50)," + "role varchar(20)," + "primary key (uin)" + ")";

			String createCourseTableQuery = "create table if not exists f16g312.f16g312_course ( "
					+ "courseid varchar(25) not null unique," + "coursename varchar(20)," + "section varchar(50)" + ")";

			String createEnrollTableQuery = "create table if not exists f16g312.f16g312_enroll ( "
					+ "uin integer not null,"
					+ "coursename varchar(20) not null," /* + "primary key (uin,coursename)" */
					+ ")";

			String createAssessmentTableQuery = "create table if not exists f16g312.f16g312_assessment ("
					+ "assessmentid varchar(25) not null," + "questionid integer not null auto_increment,"
					+ "questiontype varchar(20)," + "questionstring TEXT," + "answer TEXT,"
					+ "answererror double precision," + "coursename varchar(20),"
					+ "primary key(questionid,assessmentid)" + ")";

			String createUserTableQuery = "create table if not exists f16g312.f16g312_user (" + "userName varchar(50),"
					+ "password varchar(50)," + "lastname varchar(50)," + "firstname varchar(50)," + "role varchar(20),"
					+ "uin integer not null unique," + "primary key (uin)" + ")";

			String createResultTableQuery = "create table if not exists f16g312.f16g312_result ("
					+ "uin integer not null," + "assessmentid varchar(25) not null," + "coursename varchar(20),"
					+ "score integer," + "primary key(uin,assessmentid,coursename)" + ")";

			statement.executeUpdate(createRosterActgTableQuery);
			statement.executeUpdate(createRosterAdbmsTableQuery);
			statement.executeUpdate(createRosterStatsTableQuery);
			statement.executeUpdate(createUserTableQuery);
			statement.executeUpdate(createCourseTableQuery);
			statement.executeUpdate(createAssessmentTableQuery);
			statement.executeUpdate(createEnrollTableQuery);
			statement.executeUpdate(createResultTableQuery);
			statement.executeUpdate(createScoreRoster);

			String insertRosterActg1 = "INSERT INTO f16g312.f16g312_roster VALUES (6001,'abc1', 'Student1', 'SN1', 'Student', 'Accounting')";
			String insertRosterActg2 = "INSERT INTO f16g312.f16g312_roster VALUES (6002,'abc2', 'Student2', 'SN2', 'Student', 'Accounting')";
			String insertRosterActg3 = "INSERT INTO f16g312.f16g312_roster VALUES (6003,'abc3', 'Student3', 'SN3', 'Student', 'Accounting')";
			String insertRosterActg4 = "INSERT INTO f16g312.f16g312_roster VALUES (6004,'abc4', 'Student4', 'SN4', 'Student', 'Accounting')";

			String insertRosterstats5 = "INSERT INTO f16g312.f16g312_rosterstats VALUES (6005,'abc5', 'Student5', 'SN5', 'Student')";
			String insertRosterstats6 = "INSERT INTO f16g312.f16g312_rosterstats VALUES (6006,'abc6', 'Student6', 'SN6', 'Student')";
			String insertRosterstats7 = "INSERT INTO f16g312.f16g312_rosterstats VALUES (6007,'abc7', 'Student7', 'SN7', 'Student')";
			String insertRosterstats8 = "INSERT INTO f16g312.f16g312_rosterstats VALUES (6008,'abc8', 'Student8', 'SN8', 'Student')";

			String insertRosterAdbms9 = "INSERT INTO f16g312.f16g312_rosteradbms VALUES (6009,'abc9', 'Student9', 'SN1', 'Student')";
			String insertRosterAdbms10 = "INSERT INTO f16g312.f16g312_rosteradbms VALUES (6010,'abc10', 'Student10', 'SN1', 'Student')";
			String insertRosterAdbms11 = "INSERT INTO f16g312.f16g312_rosteradbms VALUES (6011,'abc11', 'Student11', 'SN1', 'Student')";

			statement.executeUpdate(insertRosterActg1);
			statement.executeUpdate(insertRosterActg2);
			statement.executeUpdate(insertRosterActg3);
			statement.executeUpdate(insertRosterActg4);
			statement.executeUpdate(insertRosterstats5);
			statement.executeUpdate(insertRosterstats6);
			statement.executeUpdate(insertRosterstats7);
			statement.executeUpdate(insertRosterstats8);
			statement.executeUpdate(insertRosterAdbms9);
			statement.executeUpdate(insertRosterAdbms10);
			statement.executeUpdate(insertRosterAdbms11);

			String insertCourse1 = "INSERT INTO f16g312.f16g312_course VALUES ('ACT500','Accounting','ACT1')";
			String insertCourse2 = "INSERT INTO f16g312.f16g312_course VALUES ('IDS521','ADBMS','DB1')";
			String insertCourse3 = "INSERT INTO f16g312.f16g312_course VALUES ('IDS567','Statistics','STAT1')";

			statement.executeUpdate(insertCourse1);
			statement.executeUpdate(insertCourse2);
			statement.executeUpdate(insertCourse3);

			String insertEnroll1 = "INSERT INTO f16g312.f16g312_enroll VALUES (6001,'Accounting')";
			String insertEnroll2 = "INSERT INTO f16g312.f16g312_enroll VALUES (6002,'Accounting')";
			String insertEnroll3 = "INSERT INTO f16g312.f16g312_enroll VALUES (6003,'Accounting')";
			String insertEnroll4 = "INSERT INTO f16g312.f16g312_enroll VALUES (6004,'Accounting')";
			String insertEnroll5 = "INSERT INTO f16g312.f16g312_enroll VALUES (6005,'Statistics')";
			String insertEnroll6 = "INSERT INTO f16g312.f16g312_enroll VALUES (6006,'Statistics')";
			String insertEnroll7 = "INSERT INTO f16g312.f16g312_enroll VALUES (6007,'Statistics')";
			String insertEnroll8 = "INSERT INTO f16g312.f16g312_enroll VALUES (6008,'Statistics')";
			String insertEnroll9 = "INSERT INTO f16g312.f16g312_enroll VALUES (6009,'ADBMS')";
			String insertEnroll10 = "INSERT INTO f16g312.f16g312_enroll VALUES (6010,'ADBMS')";
			String insertEnroll11 = "INSERT INTO f16g312.f16g312_enroll VALUES (6011,'ADBMS')";
			statement.executeUpdate(insertEnroll1);
			statement.executeUpdate(insertEnroll2);
			statement.executeUpdate(insertEnroll3);
			statement.executeUpdate(insertEnroll4);
			statement.executeUpdate(insertEnroll5);
			statement.executeUpdate(insertEnroll6);
			statement.executeUpdate(insertEnroll7);
			statement.executeUpdate(insertEnroll8);
			statement.executeUpdate(insertEnroll9);
			statement.executeUpdate(insertEnroll10);
			statement.executeUpdate(insertEnroll11);

			String createUser1 = "INSERT INTO f16g312.f16g312_user VALUES('student1',password1','Student1','SN1','Student')";
			String createUser2 = "INSERT INTO f16g312.f16g312_user VALUES('student2',password2','Student2','SN2','Student')";
			String createUser3 = "INSERT INTO f16g312.f16g312_user VALUES('student3',password3','Student3','SN3','Student')";
			String createUser4 = "INSERT INTO f16g312.f16g312_user VALUES('student4',password4','Student4','SN4','Student')";
			String createUser5 = "INSERT INTO f16g312.f16g312_user VALUES('student5',password5','Student5','SN5','Student')";
			String createUser6 = "INSERT INTO f16g312.f16g312_user VALUES('student6',password6','Student6','SN6','Student')";
			String createUser7 = "INSERT INTO f16g312.f16g312_user VALUES('student7',password7','Student7','SN7','Student')";
			String createUser8 = "INSERT INTO f16g312.f16g312_user VALUES('student8',password8','Student8','SN8','Student')";
			String createUser9 = "INSERT INTO f16g312.f16g312_user VALUES('student9',password9','Student9','SN9','Student')";
			String createUser10 = "INSERT INTO f16g312.f16g312_user VALUES('student10',password10','Student10','SN10','Student')";
			String createUser11 = "INSERT INTO f16g312.f16g312_user VALUES('student11',password11','Student11','SN11','Student')";

			String createUser12 = "INSERT INTO f16g312.f16g312_user VALUES('instructor1',password1','Instructor1','INS1','Instructor')";
			String createUser13 = "INSERT INTO f16g312.f16g312_user VALUES('instructor2',password2','Instructor2','INS2','Instructor')";
			String createUser14 = "INSERT INTO f16g312.f16g312_user VALUES('instructor3',password3','Instructor3','INS3','Instructor')";
			String createUser15 = "INSERT INTO f16g312.f16g312_user VALUES('admin1',password1','Admin1','Ad1','Admin')";

			statement.executeUpdate(createUser1);
			statement.executeUpdate(createUser2);
			statement.executeUpdate(createUser3);
			statement.executeUpdate(createUser4);
			statement.executeUpdate(createUser5);
			statement.executeUpdate(createUser6);
			statement.executeUpdate(createUser7);
			statement.executeUpdate(createUser8);
			statement.executeUpdate(createUser9);
			statement.executeUpdate(createUser10);
			statement.executeUpdate(createUser11);
			statement.executeUpdate(createUser12);
			statement.executeUpdate(createUser13);
			statement.executeUpdate(createUser14);
			statement.executeUpdate(createUser15);

			statement.close();
			return "SUCCESS";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("One or more tables could not be created!"));
			e.printStackTrace();
			return "FALSE";
		}

	}

	public List<ScoreRoster> fetchAllData(String course) {
		List<ScoreRoster> rosterTableData = new ArrayList<ScoreRoster>();
		try {
			statement = connectToDatabase().createStatement();
			// rs=statement.executeQuery("select * from
			// f16g312_scoreroster"+course);
			System.out.println(course);
			rs = statement.executeQuery("select * from f16g312_scoreroster where Course='" + course + "'");

			if (rs == null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("There is no data currently!"));
			} else {
				while (rs.next()) {
					ScoreRoster rosterRecord = new ScoreRoster();
					rosterRecord.setFirstName(rs.getString("First_Name"));
					rosterRecord.setLastName(rs.getString("Last_Name"));
					rosterRecord.setUserName(rs.getString("Username"));
					rosterRecord.setStudentID(rs.getInt("Student_ID"));
					rosterRecord.setLastAccess(rs.getString("Last_Access"));
					rosterRecord.setAvailability(rs.getString("Availability"));
					rosterRecord.setTotal(rs.getDouble("Total"));
					rosterRecord.setExam01(rs.getDouble("Exam01"));
					rosterRecord.setExam01(rs.getDouble("Exam02"));
					rosterRecord.setExam01(rs.getDouble("Exam03"));
					rosterRecord.setProject(rs.getDouble("Project"));
					rosterRecord.setCourse(rs.getString("Course"));

					rosterTableData.add(rosterRecord);
				}
			}
			return rosterTableData;
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Some roster record could not be fetched!"));
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return rosterTableData;

	}

	public ResultSet execQuery(String query) {
		// TODO Auto-generated method stub
		try {

			statement = connectToDatabase().createStatement();

			rs = statement.executeQuery(query);

		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Some error occured in executing the query!"));
			e.printStackTrace();
		}
		return rs;
	}

	public List<Assess> fetchAssess(String id, String course) {
		List<Assess> assessTableData = new ArrayList<Assess>();
		try {
			statement = connectToDatabase().createStatement();
			rs = statement.executeQuery(
					"select questionid,questiontype,questionstring,answer,answererror,courseName from f16g312_assessment where assessmentid = '"
							+ id + "' and coursename='" + course + "'");
			if (rs == null) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("There are no assessments currently!"));
			} else {
				while (rs.next()) {
					Assess assessRecord = new Assess();

					assessRecord.setQuestionId(rs.getInt("questionId"));
					assessRecord.setQuestionType(rs.getString("questionType"));
					assessRecord.setQuestionString(rs.getString("questionString"));
					assessRecord.setAnswer(rs.getString("answer"));
					assessRecord.setAnswerError(rs.getDouble("answerError"));
					assessRecord.setCourseName(rs.getString("courseName"));

					assessTableData.add(assessRecord);

				}
			}

		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Some error occured in fetching assessments!"));
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return assessTableData;

	}

	public List<Assess> fetchStudentAssess(String id, String course) {
		List<Assess> assessTableData = new ArrayList<Assess>();
		try {
			statement = connectToDatabase().createStatement();
			rs = statement.executeQuery(
					"select assessmentid,questionid,questiontype,questionstring from f16g312_assessment where assessmentid = '"
							+ id + "' and coursename='" + course + "'");
			if (rs == null) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("There are no student access currently!"));
			} else {
				while (rs.next()) {
					Assess assessRecord = new Assess();

					assessRecord.setAssessmentId(rs.getString("assessmentid"));
					assessRecord.setQuestionId(rs.getInt("questionId"));
					assessRecord.setQuestionType(rs.getString("questionType"));
					assessRecord.setQuestionString(rs.getString("questionString"));

					assessTableData.add(assessRecord);

				}
			}

		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Some error occured in fetching student assessments!"));
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return assessTableData;

	}

	public ArrayList<String> fetchAllCourses() {

		ArrayList<String> courses = new ArrayList<String>();
		try {
			statement = connectToDatabase().createStatement();
			rs = statement.executeQuery("select distinct coursename from f16g312_course");
			if (rs == null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("There are no courses currently!"));
			} else {
				while (rs.next()) {
					// Assess assessRecord = new Assess();

					courses.add(rs.getString("coursename"));

				}

			}
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Some error occured in fetching courses!"));
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// Courses c = new Courses();

		return courses;

		// return "SUCCESS";
	}

	public ArrayList<String> fetchMyCourses() {

		ArrayList<String> courses = new ArrayList<String>();
		try {
			statement = connectToDatabase().createStatement();
			rs = statement.executeQuery("select coursename from f16g312_enroll where uin ='" + uin + "'");
			if (rs == null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("There are no courses currently!"));
			} else {
				while (rs.next()) {
					// Assess assessRecord = new Assess();

					courses.add(rs.getString("coursename"));

				}

			}
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Some error occured in fetching my course!"));
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// Courses c = new Courses();

		return courses;

		// return "SUCCESS";
	}

	public ArrayList<String> fetchAllAssess() {

		ArrayList<String> assessments = new ArrayList<String>();
		try {
			statement = connectToDatabase().createStatement();
			rs = statement.executeQuery("select distinct assessmentid from f16g312_assessment");
			if (rs == null) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("There are no assessments currently!"));
			} else {
				while (rs.next()) {
					// Assess assessRecord = new Assess();

					assessments.add(rs.getString("assessmentid"));

				}

			}

		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Some error occured in fetching all assessments!"));
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// Courses c = new Courses();

		return assessments;

		// return "SUCCESS";
	}

	public ArrayList<String> fetchMyAssess(String course) {

		ArrayList<String> assessments = new ArrayList<String>();
		try {
			statement = connectToDatabase().createStatement();
			rs = statement.executeQuery(
					"select distinct assessmentid from f16g312_assessment where coursename ='" + course + "'");
			if (rs == null) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("There are no assessments currently!"));
			} else {
				while (rs.next()) {
					// Assess assessRecord = new Assess();
					assessments.add(rs.getString("assessmentid"));

				}

			}

		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Some error occured in fetching my Assessment!"));
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// Courses c = new Courses();

		return assessments;

		// return "SUCCESS";
	}

	// --------------------------------------------------------

	public List<Roster> fetchAllEnrolledStudents(String selected_course) {

		List<Roster> enrolledstudents = new ArrayList<Roster>();

		try {
			statement = connectToDatabase().createStatement();
			rs = statement.executeQuery("select * from f16g312_roster" + selected_course);
			if (rs == null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("There are no enrolled students!"));
			} else {
				while (rs.next()) {
					Roster enrolleddetails = new Roster();

					enrolleddetails.setUin(rs.getInt("uin"));
					enrolleddetails.setNetId(rs.getString("netid"));
					enrolleddetails.setLastName(rs.getString("lastname"));
					enrolleddetails.setFirstName(rs.getString("firstname"));
					enrolleddetails.setRole(rs.getString("role"));

					enrolledstudents.add(enrolleddetails);

				}

			}
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Some error occured in fetching all students!"));
			e.printStackTrace();
		}
		/*
		 * finally{ try { rs.close(); } catch (SQLException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } try {
		 * statement.close(); } catch (SQLException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); }
		 * 
		 * }
		 */

		// Courses c = new Courses();

		return enrolledstudents;

		// return "SUCCESS";
	}

	// ---------------------------------------------------------------

	public String listTables() {
		String catalog = null;
		String schemaPattern = null;
		String tableNamePattern = null;
		String[] types = null;
		tables = new ArrayList<String>();

		try {
			databaseMetaData = connectToDatabase().getMetaData();
			rs = databaseMetaData.getTables(catalog, schemaPattern, tableNamePattern, types);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
				tables.add(rs.getString("TABLE_NAME"));
			}
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Some error occured in listing tables!"));
			e.printStackTrace();
		}
		renderTableList = true;
		return "SUCCESS";
	}

	public String listColumns() {
		String catalog = null;
		String schemaPattern = null;
		String columnNamePattern = null;
		columns = new ArrayList<String>();
		try {
			databaseMetaData = connectToDatabase().getMetaData();
			for (String table_name : selectedTables) {
				rs = databaseMetaData.getColumns(catalog, schemaPattern, table_name, columnNamePattern);

				while (rs.next()) {
					columns.add(rs.getString("COLUMN_NAME"));
				}

			}
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Some columns could not be listed!"));
			e.printStackTrace();
		}

		return "SUCCESS";
	}

	/*********************
	 * Modified the entire process Query method
	 **********************/
	public String processSQLQuery() {
		renderQuery = false;
		int no_of_columns = 0;
		try {

			statement = connectToDatabase().createStatement();
			if (query.toLowerCase().startsWith("select")) {
				rs = statement.executeQuery(query);
				resultSetMetaData = rs.getMetaData();
				no_of_columns = resultSetMetaData.getColumnCount();
				displayRows = new ArrayList<String>();
				if (rs == null) {
					renderQuery = false;
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("There are no rows to display!"));
				} else {

					StringBuilder sb1 = new StringBuilder();
					sb1.append("\t<tr>\n");
					for (int i = 1; i <= no_of_columns; i++) {
						sb1.append("\t\t<td><b>" + resultSetMetaData.getColumnName(i) + "</b></td>\n");
					}
					sb1.append("\t</tr>\n");
					displayRows.add(sb1.toString());
					while (rs.next()) {

						StringBuilder sb = new StringBuilder();
						sb.append("\t<tr>\n");
						for (int i = 1; i <= no_of_columns; i++) {
							sb.append("\t\t<td>" + rs.getString(i) + "</td>\n");
						}
						sb.append("\t</tr>\n");
						displayRows.add(sb.toString());
					}

				}
				renderQuery = true;
			} else {
				statement.executeUpdate(query);
			}
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			renderQuery = false;
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("There is some error in your query!"));

		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return "SUCCESS";
	}

	public String dropTables() {
		try {
			statement = connectToDatabase().createStatement();
			statement.executeUpdate("drop table f16g312.f16g312_rosteractg");
			statement.executeUpdate("drop table f16g312.f16g312_rosteradbms");
			statement.executeUpdate("drop table f16g312.f16g312_rosterstats");
			statement.executeUpdate("drop table f16g312.f16g312_course");
			statement.executeUpdate("drop table f16g312.f16g312_assessment");
			statement.executeUpdate("drop table f16g312.f16g312_user");
			statement.executeUpdate("drop table f16g312.f16g312_enroll");
			statement.executeUpdate("drop table f16g312.f16g312_result");
			statement.executeUpdate("drop table f16g312.f16g312_scoreRoster");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "SUCCESS";

	}

	public boolean deleteFromDatabase(String query) {
		try {
			statement = connectToDatabase().createStatement();

			statement.executeUpdate(query);
			statement.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void main(String args[]) {
		DatabaseAccessBean dbaccess = new DatabaseAccessBean();
		String x;
		// x = dbaccess.dropTables();
		x = dbaccess.createTables();
	}

	public int calculateScore(List<Assess> assessList, String assessmentid) {
		try {
			String query = "select questiontype,answer,answererror from f16g312_assessment where assessmentid ='"
					+ assessmentid + "'";

			rs = execQuery(query);
			int score = 0;

			rs.next();

			for (int i = 0; i < assessList.size(); i++) {

				String qType = rs.getString(1);
				String ans = rs.getString(2);
				double ansErr = rs.getDouble(3);

				String studentAns = assessList.get(i).getStudentAnswer();

				if (studentAns.equals("")) {
					rs.next();
					continue;
				}

				if (qType.equals("Categorical")) {

					if (ans.equalsIgnoreCase(studentAns)) {
						score++;
					}
					rs.next();
				} else {
					double d_ans = Double.parseDouble(ans);
					double d_studentAns = Double.parseDouble(studentAns);

					if (Math.abs(d_ans - d_studentAns) <= ansErr) {
						score++;
					}
					rs.next();
				}
			}

			return score * 25;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public ArrayList<Result> fetchAllResult() {

		ArrayList<Result> resultTableData = new ArrayList<Result>();

		try {
			statement = connectToDatabase().createStatement();

			rs = statement.executeQuery("select * from f16g312_result where uin = '" + uin + "'");
			if (rs == null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No results to display!"));
			} else {
				while (rs.next()) {

					Result resultRecord = new Result();
					resultRecord.setUin(rs.getInt("uin"));
					resultRecord.setCourseName(rs.getString("coursename"));
					resultRecord.setAssessmentId(rs.getString("assessmentid"));
					resultRecord.setScore(rs.getInt("score"));
					resultTableData.add(resultRecord);
				}
			}
			statement.close();
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return resultTableData;

	}

	public ArrayList<Result> fetchAllStudentResult(String course) {

		ArrayList<Result> resultTableData = new ArrayList<Result>();

		try {
			statement = connectToDatabase().createStatement();

			rs = statement.executeQuery("select * from f16g312_result where coursename = '" + course + "'");
			if (rs == null) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("There are not results currently to display!"));
			} else {
				while (rs.next()) {

					Result resultRecord = new Result();
					resultRecord.setUin(rs.getInt("uin"));
					resultRecord.setCourseName(rs.getString("coursename"));
					resultRecord.setAssessmentId(rs.getString("assessmentid"));
					resultRecord.setScore(rs.getInt("score"));
					resultTableData.add(resultRecord);
				}
			}
			statement.close();
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return resultTableData;

	}
}
