package f16g312;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import org.apache.myfaces.custom.fileupload.UploadedFile;
//import org.apache.commons.el.parser.ParseException;
import org.apache.commons.io.FilenameUtils;
//import org.apache.commons.io.IOUtils;
import org.apache.commons.io.IOUtils;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
//import java.text.SimpleDateFormat;
import java.io.*;
import java.lang.String;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

@ManagedBean
@SessionScoped
//@ViewScoped
public class ActionBeanFile {

	private UploadedFile uploadedFile;
	private String fileLabel;
	private Roster roster;
	private ScoreRoster scoreRoster;
	private List<Roster> rosterList;
	private List<ScoreRoster> scoreRosterList;
	private boolean renderList;
	private boolean uploadRoster;

	private Assess assess;
	private List<Assess> assessList;
	private boolean renderAssessList;
	private boolean uploadAssessment;
	private String hasHeader;
	private int examId;
	private String delimiterType;
	int colcount = 0;
	int rowcount = 0;

	
	
	public ScoreRoster getScoreRoster() {
		return scoreRoster;
	}

	public void setScoreRoster(ScoreRoster scoreRoster) {
		this.scoreRoster = scoreRoster;
	}

	public List<ScoreRoster> getScoreRosterList() {
		return scoreRosterList;
	}

	public void setScoreRosterList(List<ScoreRoster> scoreRosterList) {
		this.scoreRosterList = scoreRosterList;
	}

	public String getDelimiterType() {
		return delimiterType;
	}

	public void setDelimiterType(String delimiterType) {
		this.delimiterType = delimiterType;
	}

	public boolean isUploadRoster() {
		return uploadRoster;
	}

	public void setUploadRoster(boolean uploadRoster) {
		this.uploadRoster = uploadRoster;
	}

	public boolean isUploadAssessment() {
		return uploadAssessment;
	}

	public void setUploadAssessment(boolean uploadAssessment) {
		this.uploadAssessment = uploadAssessment;
	}

	private ArrayList<String> assessNames;
	private String selected_assess;

	private String selected_course;
	private ArrayList<String> courselist;

	private ArrayList<Result> resultList;
	private boolean renderResultList;

	public ArrayList<Result> getResultList() {
		return resultList;
	}

	public void setResultList(ArrayList<Result> resultList) {
		this.resultList = resultList;
	}

	public boolean isRenderResultList() {
		return renderResultList;
	}

	public void setRenderResultList(boolean renderResultList) {
		this.renderResultList = renderResultList;
	}

	public String getSelected_course() {
		return selected_course;
	}

	public void setSelected_course(String selected_course) {
		this.selected_course = selected_course;
	}

	public ArrayList<String> getCourselist() {
		return courselist;
	}

	public void setCourselist(ArrayList<String> courselist) {
		this.courselist = courselist;
	}

	public String getSelected_assess() {
		return selected_assess;
	}

	public void setSelected_assess(String selected_assess) {
		this.selected_assess = selected_assess;
	}

	public ArrayList<String> getAssessNames() {
		return assessNames;
	}

	public void setAssessNames(ArrayList<String> assessNames) {
		this.assessNames = assessNames;
	}

	public Assess getAssess() {
		return assess;
	}

	public void setAssess(Assess assess) {
		this.assess = assess;
	}

	public List<Assess> getAssessList() {
		return assessList;
	}

	public void setAssessList(List<Assess> assessList) {
		this.assessList = assessList;
	}

	public boolean isRenderAssessList() {
		return renderAssessList;
	}

	public void setRenderAssessList(boolean renderAssessList) {
		this.renderAssessList = renderAssessList;
	}

	public ActionBeanFile() {
		rosterList = new ArrayList<Roster>();
		renderList = false;
		assessList = new ArrayList<Assess>();
		renderAssessList = false;
		courselist = new ArrayList<String>();
		resultList = new ArrayList<Result>();
		renderResultList = false;
		uploadRoster = false;
		uploadAssessment = false;

	}
	
	public String resetRoster() {
		rosterList.clear();
		renderList = false;
		uploadRoster = false;
		colcount=0;
		rowcount=0;
		return "SUCCESS";
	}

	public String resetAssessment() {
		renderAssessList = false;
		assessList.clear();
		uploadAssessment = false;
		colcount=0;
		rowcount=0;
		return "SUCCESS";
	}

	public String resetResult() {
		renderResultList = false;
		resultList.clear();
		colcount=0;
		rowcount=0;
		return "SUCCESS";
	}

	public Roster getRoster() {
		return roster;
	}

	public void setRoster(Roster roster) {
		this.roster = roster;
	}

	public List<Roster> getRosterList() {
		return rosterList;
	}

	public int getRowcount() {
		return rowcount;
	}

	public void setRowcount(int rowcount) {
		this.rowcount = rowcount;
	}

	public void setRosterList(List<Roster> rosterList) {
		this.rosterList = rosterList;
	}

	public boolean isRenderList() {
		return renderList;
	}

	public void setRenderList(boolean renderList) {
		this.renderList = renderList;
	}

	public String getFileLabel() {
		return fileLabel;
	}

	public void setFileLabel(String fileLabel) {
		this.fileLabel = fileLabel;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public String getHasHeader() {
		return hasHeader;
	}

	public void setHasHeader(String hasHeader) {
		this.hasHeader = hasHeader;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, Object> m = context.getExternalContext().getSessionMap();
		roster = (Roster) m.get("roster");
		scoreRoster = (ScoreRoster) m.get("scoreRoster");
		assess = (Assess) m.get("assess");
		rowcount = 0;
	}

	public List<String> parseCSV(String data) {
		List<String> tokens = new ArrayList<String>();
		StringBuilder buffer = new StringBuilder();

		int parenthesesCounter = 0;
		int quoteCounter = 0;

		for (char c : data.toCharArray()) {

			if (c == '(')
				parenthesesCounter++;
			if (c == ')')
				parenthesesCounter--;
			if (c == '"')
				quoteCounter++;
			if (c == ',' && parenthesesCounter == 0 && (quoteCounter == 2 || quoteCounter == 0)) {
				// lets add token inside buffer to our tokens
				tokens.add(buffer.toString());
				// now we need to clear buffer
				buffer.delete(0, buffer.length());
			} else {
				if (c != '"') {

					buffer.append(c);
				}
			}
		}
		// lets not forget about part after last comma
		tokens.add(buffer.toString());

		return tokens;
	}

	@SuppressWarnings("finally")
	public String processUploadAssessment() {

		DatabaseAccessBean dbaccess = new DatabaseAccessBean();
		PreparedStatement preparedStatement = null;
		Connection conn = dbaccess.connectToDatabase();
		FacesContext context = FacesContext.getCurrentInstance();
		String path = context.getExternalContext().getRealPath("");
		try {
			int i = 0;

			// CREATING A PREPARED STATEMENT
			String insertTableSQL = "INSERT INTO f16g312_assessment"
					+ "(assessmentid,questiontype,questionstring,answer,answererror,coursename) VALUES"
					+ "(?,?,?,?,?,?)";

			preparedStatement = conn.prepareStatement(insertTableSQL);

			// RETRIEVING INFORMATION FROM CSV FILE

			// opening a file input stream

			File tempFile;

			String fileName = FilenameUtils.getBaseName(uploadedFile.getName());
			String extension = FilenameUtils.getExtension(uploadedFile.getName());

			// BufferedReader reader = new BufferedReader(new
			// FileReader(tempFile));

			String line = "";
			tempFile = File.createTempFile(fileName + "_", "." + extension, new File(path));
			System.out.println(path);
			OutputStream output = null;
			output = new FileOutputStream(tempFile);
			IOUtils.copy(uploadedFile.getInputStream(), output);
			BufferedReader reader = null;
			int header = 0;
			reader = new BufferedReader(new FileReader(tempFile));
			String delimiter = null;
			if (delimiterType.equalsIgnoreCase("Tab"))
				delimiter = "\\t";
			else if (delimiterType.equalsIgnoreCase("Comma"))
				delimiter = ",";
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(delimiter);
				System.out.println(tokens[0]);
				System.out.println(tokens[1]);
				System.out.println(tokens[2]);
				System.out.println(tokens[3]);
				/*
				 * System.out.println(tokens[4]); System.out.println(tokens[5]);
				 */
				if (hasHeader.equalsIgnoreCase("YES"))
					header++;
				if (header != 1) {
					preparedStatement.setInt(1, examId);
					// preparedStatement.setInt(2, Integer.parseInt(tokens[1]));
					preparedStatement.setString(2, tokens[0]);
					// preparedStatement.(2,Integer.parseInt(tokens[1]));
					preparedStatement.setString(3, tokens[1]);
					preparedStatement.setString(4, tokens[2]);
					preparedStatement.setBigDecimal(5, new BigDecimal(tokens[3]));
					preparedStatement.setString(6, selected_course);
					// if(tokens[4].isEmpty())
					// preparedStatement.setDouble(6,0);
					// else
					// preparedStatement.setDouble(6,Double.parseDouble(tokens[4]));

					preparedStatement.executeUpdate();
					
					rowcount++;
					// }
					System.out.println("Data imported");
					
					// closing CSV reader
				}
				
			}
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Total number of rows added is"+rowcount));
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Assessment Upload Successfull"));
			preparedStatement.close();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {// CLOSING CONNECTION
			try {
				if (dbaccess.statement != null)
					conn.close();
				uploadAssessment = true;
				return "SUCCESS";
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
				uploadAssessment = true;
				return "SUCCESS";
			} catch (SQLException se) {
				se.printStackTrace();
				return "FAIL";
			}

		}

	}

	@SuppressWarnings("finally")
	public String processUploadRoster() {

		DatabaseAccessBean dbaccess = new DatabaseAccessBean();
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement1 = null;
		Connection conn = dbaccess.connectToDatabase();
		FacesContext context = FacesContext.getCurrentInstance();
		
		String path = context.getExternalContext().getRealPath("");

		try {

			// CREATING A PREPARED STATEMENT
			String insertTableSQL = "INSERT INTO f16g312_roster" + selected_course
					+ "(uin,netid,lastname,firstname,role) VALUES" + "(?,?,?,?,?)";

			preparedStatement = conn.prepareStatement(insertTableSQL);

			String insertEnrollSQL = "INSERT INTO f16g312_enroll" + "(uin,coursename) VALUES" + "(?,?)";

			preparedStatement1 = conn.prepareStatement(insertEnrollSQL);

			// RETRIEVING INFORMATION FROM CSV FILE
			// opening a file input stream

			File tempFile;

			String fileName = FilenameUtils.getBaseName(uploadedFile.getName());
			String extension = FilenameUtils.getExtension(uploadedFile.getName());

			tempFile = new File(path + "/" + fileName + "." + extension);
			FileOutputStream fos = new FileOutputStream(tempFile);
			// or uploaded to disk
			fos.write(uploadedFile.getBytes());
			fos.close();
			// FileOutputStream output = new FileOutputStream(tempFile);
			// IOUtils.copy(uploadedFile.getInputStream(), output);

			// IOUtils.closeQuietly(output);
			BufferedReader reader = new BufferedReader(new FileReader(tempFile));

			String line = null; // line read from csv
			Scanner scanner = null; // scanned line

			reader.readLine(); // omits the first line

			// READING FILE LINE BY LINE AND UPLOADING INFORMATION TO DATABASE
	while ((line = reader.readLine()) != null) {
				scanner = new Scanner(line);
				scanner.useDelimiter(",");
				
				while (scanner.hasNext()) {
					preparedStatement.setInt(1, Integer.parseInt(scanner.next()));
					preparedStatement.setString(2, scanner.next());
					preparedStatement.setString(3, scanner.next());
					preparedStatement.setString(4, scanner.next());
					preparedStatement.setString(5, scanner.next());

				}
				rowcount++;
				preparedStatement.executeUpdate();
				
			}
			
			preparedStatement.close();

			BufferedReader reader1 = new BufferedReader(new FileReader(tempFile));

			reader1.readLine(); // omits the first line

			// READING FILE LINE BY LINE AND UPLOADING INFORMATION TO DATABASE
			while ((line = reader1.readLine()) != null) {
				scanner = new Scanner(line);
				scanner.useDelimiter(",");

				while (scanner.hasNext()) {

					preparedStatement1.setInt(1, Integer.parseInt(scanner.next()));
					preparedStatement1.setString(2, selected_course);
					while (scanner.hasNext()) {
						// skip values
						scanner.next();
					}

				}
				
				preparedStatement1.executeUpdate();
			}
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Total number of rows added is"+rowcount));
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Roster Upload Successfully!"));
			
			reader1.close(); // closing CSV reader
			reader.close(); // closing CSV reader

		}

		catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {// CLOSING CONNECTION
			try {
				if (dbaccess.statement != null)
					conn.close();
				uploadRoster = true;
				return "SUCCESS";
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
				uploadRoster = true;
				return "SUCCESS";
			} catch (SQLException se) {
				se.printStackTrace();
				return "FAIL";
			}

		}

	}

	@SuppressWarnings("finally")
	public String processUploadScoreRoster() {
		DatabaseAccessBean dbaccess = new DatabaseAccessBean();
		PreparedStatement preparedStatement = null;
		Connection conn = dbaccess.connectToDatabase();
		FacesContext context = FacesContext.getCurrentInstance();
		String path = context.getExternalContext().getRealPath("");
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.US);
		java.util.Date date;
		java.sql.Date sqlDate;
		
//		  Calendar cal = Calendar.getInstance(); 
//		  Timestamp timeStamp = new Timestamp(cal.getTimeInMillis());
		
		try {
			int i = 0;

			// CREATING A PREPARED STATEMENT
			String insertTableSQL = "INSERT INTO f16g312_scoreRoster"	
					+ "(Last_Name,First_Name,Username,Student_ID,Last_Access,Availability,Total,Exam01,Exam02,Exam03,Project,Course) VALUES"
					+ "(?,?,?,?,?,?,?,?,?,?,?,?)";

			preparedStatement = conn.prepareStatement(insertTableSQL);

			// RETRIEVING INFORMATION FROM CSV FILE

			// opening a file input stream

			File tempFile;

			String fileName = FilenameUtils.getBaseName(uploadedFile.getName());
			String extension = FilenameUtils.getExtension(uploadedFile.getName());

			// BufferedReader reader = new BufferedReader(new
			// FileReader(tempFile));

			String line = "";
			tempFile = File.createTempFile(fileName + "_", "." + extension, new File(path));
			System.out.println(path);
			OutputStream output = null;
			output = new FileOutputStream(tempFile);
			IOUtils.copy(uploadedFile.getInputStream(), output);
			BufferedReader reader = null;
			int header = 0;
			reader = new BufferedReader(new FileReader(tempFile));
			String delimiter = null;
			if (delimiterType.equalsIgnoreCase("Tab"))
				delimiter = "\\t";
			else if (delimiterType.equalsIgnoreCase("Comma"))
				delimiter = ",";
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(delimiter);
/*				System.out.println(tokens[0]);
				System.out.println(tokens[1]);
				System.out.println(tokens[2]);
				System.out.println(tokens[3]);
				System.out.println(tokens[4]);
				System.out.println(tokens[5]);
				System.out.println(tokens[6]);
				System.out.println(tokens[7]);
				System.out.println(tokens[8]);
				System.out.println(tokens[9]);
				System.out.println(tokens[10]);
	*/			
				if (hasHeader.equalsIgnoreCase("YES"))
					header++;
				if (header != 1) {
					preparedStatement.setString(1, tokens[0]);
					preparedStatement.setString(2, tokens[1]);
					preparedStatement.setString(3, tokens[2]);
					preparedStatement.setInt(4, Integer.parseInt(tokens[3]));
					date = formatter.parse(tokens[4]);
					sqlDate = new java.sql.Date(date.getTime());
					preparedStatement.setDate(5, sqlDate);
					preparedStatement.setString(6, tokens[5]);
					preparedStatement.setDouble(7, Double.parseDouble(tokens[6]));
					preparedStatement.setDouble(8, Double.parseDouble(tokens[7]));
					preparedStatement.setDouble(9, Double.parseDouble(tokens[8]));
					preparedStatement.setDouble(10, Double.parseDouble(tokens[9]));
					preparedStatement.setDouble(11, Double.parseDouble(tokens[10]));
					preparedStatement.setString(12, selected_course);
					// if(tokens[4].isEmpty())
					// preparedStatement.setDouble(6,0);
					// else
					// preparedStatement.setDouble(6,Double.parseDouble(tokens[4]));

					preparedStatement.executeUpdate();
					// }

					System.out.println("Data imported");

					// closing CSV reader
				}
				rowcount++;
			}
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Total number of rows added is"+rowcount));
			preparedStatement.close();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {// CLOSING CONNECTION
			try {
				if (dbaccess.statement != null)
					conn.close();
				uploadAssessment = true;
				return "SUCCESS";
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
				uploadAssessment = true;
				return "SUCCESS";
			} catch (SQLException se) {
				se.printStackTrace();
				return "FAIL";
			}

		}

	}

	// @SuppressWarnings("finally")
	public String processRosterDisplay() {

		DatabaseAccessBean dbaccess = new DatabaseAccessBean();

		try {
			/*rosterList = dbaccess.fetchAllData(selected_course);

			if (rosterList != null) {
				renderList = true;*/
				scoreRosterList = dbaccess.fetchAllData(selected_course);

				if (scoreRosterList != null) {
					renderList = true;
				
			}
			return "SUCCESS";

		} catch (Exception e) {
			return "FALSE";
		}

	}

	// @SuppressWarnings("finally")
	public String processAssessDisplay() {

		DatabaseAccessBean dbaccess = new DatabaseAccessBean();
		String id = selected_assess;
		try {
			assessList = dbaccess.fetchAssess(id,selected_course);

			if (assessList != null) {
				renderAssessList = true;
			}
			
			return "SUCCESS";

		} catch (Exception e) {
			return "FALSE";
		}

	}

	public String processStudentAssessDisplay() {

		DatabaseAccessBean dbaccess = new DatabaseAccessBean();
		String id = selected_assess;
		try {
			assessList = dbaccess.fetchStudentAssess(id,selected_course);

			if (assessList != null) {
				renderAssessList = true;
			}
			return "SUCCESS";

		} catch (Exception e) {
			return "FALSE";
		}

	}

	public String displayAssessments() {
		DatabaseAccessBean dbaccess = new DatabaseAccessBean();
		setAssessNames(dbaccess.fetchAllAssess());
		return "SUCCESS";
	}

	public String displayCourses() {
		DatabaseAccessBean dbaccess = new DatabaseAccessBean();
		setCourselist(dbaccess.fetchAllCourses());
		return "SUCCESS";
	}

	public String myCourses() {
		DatabaseAccessBean dbaccess = new DatabaseAccessBean();
		setCourselist(dbaccess.fetchMyCourses());
		return "SUCCESS";
	}

	public String displayMyAssessments() {
		DatabaseAccessBean dbaccess = new DatabaseAccessBean();
		setAssessNames(dbaccess.fetchMyAssess(selected_course));
		return "SUCCESS";
	}

	public String displayEnrolledStudents() {
		DatabaseAccessBean dbaccess = new DatabaseAccessBean();
		rosterList = dbaccess.fetchAllEnrolledStudents(selected_course);
		if (rosterList.size() >= 1) {
			renderList = true;
		}
		return "SUCCESS";

	}

	public String grade() {

		DatabaseAccessBean dbaccess = new DatabaseAccessBean();

		try {

			int score = dbaccess.calculateScore(assessList, selected_assess);

			renderAssessList = false;

			PreparedStatement preparedStatement = null;
			Connection conn = dbaccess.connectToDatabase();

			// CREATING A PREPARED STATEMENT
			String insertResultTable = "INSERT INTO f16g312_result" + "(uin,assessmentid,coursename,score) VALUES"
					+ "(?,?,?,?)";

			preparedStatement = conn.prepareStatement(insertResultTable);

			preparedStatement.setInt(1, dbaccess.uin);
			preparedStatement.setString(2, selected_assess);
			preparedStatement.setString(3, selected_course);
			preparedStatement.setInt(4, score);

			preparedStatement.executeUpdate();

			preparedStatement.close();

			/*
			 * preparedStatement.close();
			 * 
			 */

		}

		catch (Exception e) {
			e.printStackTrace();
			return "FALSE";
		}

		return "redirect";
	}

	public String processResultDisplay() {

		DatabaseAccessBean dbaccess = new DatabaseAccessBean();

		try {
			resultList = dbaccess.fetchAllResult();

			if (resultList != null) {
				renderResultList = true;
			}
			return "SUCCESS";

		} catch (Exception e) {
			return "FALSE";
		}

	}

	public String processStudentResultDisplay() {

		DatabaseAccessBean dbaccess = new DatabaseAccessBean();

		try {
			resultList = dbaccess.fetchAllStudentResult(selected_course);

			if (resultList != null) {
				renderResultList = true;
			}
			return "SUCCESS";

		} catch (Exception e) {
			return "FALSE";
		}

	}
}
