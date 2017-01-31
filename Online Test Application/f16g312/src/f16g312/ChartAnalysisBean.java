package f16g312;
  
	import java.io.File;
	import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
	import java.sql.ResultSetMetaData;
	import java.sql.SQLException;
	import java.util.ArrayList;

	import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
	import javax.faces.event.ValueChangeEvent;
	import javax.servlet.jsp.jstl.sql.Result;
	import org.jfree.chart.ChartFactory;
	import org.jfree.chart.ChartUtilities;
	import org.jfree.chart.JFreeChart;
	import org.jfree.chart.plot.PlotOrientation;
	import org.jfree.data.category.DefaultCategoryDataset;
	import org.jfree.data.general.DefaultPieDataset;

	import org.jfree.data.xy.XYSeries;
	import org.jfree.data.xy.XYSeriesCollection;


	@ManagedBean
	@SessionScoped
	public class ChartAnalysisBean {

		private DatabaseAccessBean dBAccess;
		private DatabaseBean databaseBean;
		private LoginBean loginBean;
		
		private ResultSet rs;
		private ResultSetMetaData rsmd;
		private Result result;
		private String rosterTableName;
		private int numberColumns;
		private int numberRows;
		private ArrayList<String> columnNames;
		
		private boolean renderSet;
		private String selectedAsmt;
		private String courseName;
		private String selectedCourseName;
		private ArrayList<Double> scoresList = new ArrayList<Double>();
		private int countA=0;
		private int countB=0;
		private int countC=0;
		private int countD=0;
		private int countE=0;
		private int countRE1=0;
		private int countRE2=0;
		private int countRE3=0;
		private int countRE4=0;
		private int countRE5=0;
		private int countRE6=0;
		private int noOfStudents;
		private ArrayList<String> assessmentsList ;
		
		private String pieChartPath =null;
		private String barChartPath = null;
		private String histChartPath = null;
		
		public String getPieChartPath() {
			return pieChartPath;
		}

		public void setPieChartPath(String pieChartPath) {
			this.pieChartPath = pieChartPath;
		}

		public DatabaseAccessBean getdBAccess() {
			return dBAccess;
		}

		public void setdBAccess(DatabaseAccessBean dBAccess) {
			this.dBAccess = dBAccess;
		}

		public DatabaseBean getDatabaseBean() {
			return databaseBean;
		}

		public void setDatabaseBean(DatabaseBean databaseBean) {
			this.databaseBean = databaseBean;
		}

		public LoginBean getLoginBean() {
			return loginBean;
		}

		public void setLoginBean(LoginBean loginBean) {
			this.loginBean = loginBean;
		}

		public ResultSet getRs() {
			return rs;
		}

		public void setRs(ResultSet rs) {
			this.rs = rs;
		}

		public ResultSetMetaData getRsmd() {
			return rsmd;
		}

		public void setRsmd(ResultSetMetaData rsmd) {
			this.rsmd = rsmd;
		}

		public Result getResult() {
			return result;
		}

		public void setResult(Result result) {
			this.result = result;
		}

		public String getRosterTableName() {
			return rosterTableName;
		}

		public void setRosterTableName(String rosterTableName) {
			this.rosterTableName = rosterTableName;
		}

		public int getNumberColumns() {
			return numberColumns;
		}

		public void setNumberColumns(int numberColumns) {
			this.numberColumns = numberColumns;
		}

		public String getSelectedAsmt() {
			return selectedAsmt;
		}

		public void setSelectedAsmt(String selectedAsmt) {
			this.selectedAsmt = selectedAsmt;
		}

		public String getSelectedCourseName() {
			return selectedCourseName;
		}

		public void setSelectedCourseName(String selectedCourseName) {
			this.selectedCourseName = selectedCourseName;
		}

		public ArrayList<Double> getScoresList() {
			return scoresList;
		}

		public void setScoresList(ArrayList<Double> scoresList) {
			this.scoresList = scoresList;
		}

		public int getNoOfStudents() {
			return noOfStudents;
		}

		public void setNoOfStudents(int noOfStudents) {
			this.noOfStudents = noOfStudents;
		}

		public ArrayList<String> getAssessmentsList() {
			return assessmentsList;
		}

		public void setAssessmentsList(ArrayList<String> assessmentsList) {
			this.assessmentsList = assessmentsList;
		}

		public int getNumberRows() {
			return numberRows;
		}

		public boolean isRenderSet() {
			return renderSet;
		}

		public String getCourseName() {
			return courseName;
		}

		public void setColumnNames(ArrayList<String> columnNames) {
			this.columnNames = columnNames;
		}

		
		public String getBarChartPath() {
			return barChartPath;
		}

		public void setBarChartPath(String bieChartPath) {
			this.barChartPath = bieChartPath;
		}
		
		

		public String getHistChartPath() {
			return histChartPath;
		}

		public void setHistChartPath(String histChartPath) {
			this.histChartPath = histChartPath;
		}

		public String createGraphAnalysisData(){
			DatabaseAccessBean dbaccess = new DatabaseAccessBean();
			String status = "FAIL";
			selectedAsmt=rosterTableName;
			String sqlQuery = "select score from f16g312_result where assessmentid='"+selectedAsmt+"' "
					+ "and coursename='"+selectedCourseName+"';";	
//			String sqlQuery = "select score from f16g312_scoreRoster where course='"+selectedCourseName+"' and assessment='"+selectedAsmt+"';"; // + "and course='"+selectedCourseName+"';";
			System.out.println("sql: This is creating Graph analysis data "+sqlQuery);
			try {
				rs = dbaccess.execQuery(sqlQuery);
				//System.out.println("rs:"+rs);
				clearcount();
				while(rs.next()){
					scoresList.add(rs.getDouble(1));
					Double j=rs.getDouble(1);
					Double i = (j/250)*100;
					//Double i=j*5*2; // For 10 questions
					if(i>=90 && i<=100)
						incrementCountA();
					else if(i>=80 && i<=89.44)
						incrementCountB();
					else if (i>=70 && i<=79.44)
						incrementCountC();
					else if(i>=60 && i<=69.44)
						incrementCountD();
					else 
						{
						incrementCountE();
						if(i>=50 && i<=59.44)
							incrementCountRE1();
						if(i>=40 && i<=49.44)
							incrementCountRE2();
						if(i>=30 && i<=39.44)
							incrementCountRE3();
						if(i>=20 && i<=29.44)
							incrementCountRE4();
						if(i>=10 && i<=19.44)
							incrementCountRE5();
						if(i<10)
							incrementCountRE6();
						}
				}
				noOfStudents=countA+countB+countC+countD+countE;
				//System.out.println("numofsstu "+noOfStudents);
				setRenderSet(true);
				status = "SUCCESS";
			} catch (SQLException e) {
				e.printStackTrace();
				setRenderSet(false);
				status = "FAIL";
			}
			return status;
		}

		private void clearcount(){
			DatabaseAccessBean dbaccess = new DatabaseAccessBean();
			countA=0;
			countB=0;
			countC=0;
			countD=0;
			countE=0;
			countRE1=0;
			countRE2=0;
			countRE3=0;
			countRE4=0;
			countRE5=0;
			countRE6=0;
		}
		
		private void incrementCountRE6() {
			DatabaseAccessBean dbaccess = new DatabaseAccessBean();
			this.countRE6++;
			//System.out.println(countRE6);
			
		}

		private void incrementCountRE5() {
			DatabaseAccessBean dbaccess = new DatabaseAccessBean();
			this.countRE5++;
			//System.out.println(countRE5);
			
		}

		private void incrementCountRE4() {
			DatabaseAccessBean dbaccess = new DatabaseAccessBean();
			this.countRE4++;
			//System.out.println(countRE4);
			
		}

		private void incrementCountRE3() {
			DatabaseAccessBean dbaccess = new DatabaseAccessBean();
			this.countRE3++;
			//System.out.println(countRE3);
			
		}

		private void incrementCountRE2() {
			DatabaseAccessBean dbaccess = new DatabaseAccessBean();
			this.countRE2++;
			//System.out.println(countRE2);
			
		}

		private void incrementCountRE1() {
			DatabaseAccessBean dbaccess = new DatabaseAccessBean();
				this.countRE1++;
				//System.out.println(countRE1);
		}

		private void incrementCountA() {
			DatabaseAccessBean dbaccess = new DatabaseAccessBean();
			this.countA++;
			//System.out.println(countA);
		}
		private void incrementCountE() {
			DatabaseAccessBean dbaccess = new DatabaseAccessBean();
			this.countE++;
			//System.out.println(countE);
			
		}

		private void incrementCountD() {
			DatabaseAccessBean dbaccess = new DatabaseAccessBean();
			this.countD++;
			//System.out.println(countD);
		}

		private void incrementCountC() {
			DatabaseAccessBean dbaccess = new DatabaseAccessBean();
			this.countC++;
			//System.out.println(countC);
		}

		private void incrementCountB() {
			DatabaseAccessBean dbaccess = new DatabaseAccessBean();
			this.countB++;
			//System.out.println(countB);
		}


		/**
		* This method creates a Pie chart with the number of students having each grade in a particular assessment selected
		* by the from the User Interface
		* @return JFreeChart chart 
		*/
		public JFreeChart createPieChart() {
			DatabaseAccessBean dbaccess = new DatabaseAccessBean();
		
			System.out.println("entered pie chart");
			createGraphAnalysisData();
			DefaultPieDataset data = new DefaultPieDataset();
			
			 
			 
			System.out.println(countA);
			System.out.println(countB);
			System.out.println(countC);
			System.out.println(countD);
			System.out.println(countE);
					 
			data.setValue("Grade A", countA);
			data.setValue("Grade B", countB);
			data.setValue("Grade C", countC);
			data.setValue("Grade D", countD);
			data.setValue("Grade E", countE);
			 
			//System.out.println("Thisis the data => "+data);
			 
			/*JFreeChart chart = ChartFactory.createPieChart(
			"Pie Chart", data, true, true, false
			);*/
			JFreeChart Piechart = ChartFactory.createPieChart("Pie Chart", data, true, true, false);
			return Piechart;
			}
		
		public JFreeChart createBarChart(){
			DatabaseAccessBean dbaccess = new DatabaseAccessBean();
			//System.out.println("entered bar chart");
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			String series=courseName;
			String gradeA=">90%";
			String gradeB="80-90%";
			String gradeC="70-80%";
			String gradeD="60-70%";
			String gradeE="<60%";
			
			dataset.addValue(countE, series, gradeE);
			dataset.addValue(countD, series, gradeD);
			dataset.addValue(countC, series, gradeC);
			dataset.addValue(countB, series, gradeB);
			dataset.addValue(countA, series, gradeA);
			JFreeChart barChart=ChartFactory.createBarChart("Bar Chart", "Grades", "Number of Students", dataset);
			return barChart;
		}
		
	public JFreeChart createHistogram(){	
		DatabaseAccessBean dbaccess = new DatabaseAccessBean();
			
		XYSeries series = new XYSeries("Number of Students");
		
		series.add(0, countRE6);
		series.add(10, countRE5);
		series.add(20, countRE4);
		series.add(30,countRE3);
		series.add(40, countRE2);
		series.add(50,countRE1);
		series.add(60, countD);
		series.add(70, countC);
		series.add(80, countB);
		series.add(90, countA);
		XYSeriesCollection dataset = new XYSeriesCollection(series);
		JFreeChart histogram = ChartFactory.createXYBarChart(
	            "Histogram",
	            "Score(Absolute Percentage)", 
	            false,
	            "Number of Students", 
	            dataset,
	            PlotOrientation.VERTICAL,
	            true,
	            true,
	            false
	        );
		
		
		return histogram;
		}
		
	/*	public JFreeChart createBarChart(){
			
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			dataset.addValue();
			JFreeChart barChart=ChartFactory.create;
			return barChart;
			
		}*/
		
		
		public void setRenderSet(boolean renderSet) {
			this.renderSet = renderSet;
		}
		
		public void setNumberRows(int numberRows) {
			this.numberRows = numberRows;
		}
		public void selectAsssessment(ValueChangeEvent value){
			DatabaseAccessBean dbaccess = new DatabaseAccessBean();
			selectedAsmt = (String) value.getNewValue();
			setAssessmentTableName(selectedAsmt);
		}

		
		public String populateAssessments(){
			DatabaseAccessBean dbaccess = new DatabaseAccessBean();
			PreparedStatement preparedStatement = null;
			Connection conn = dbaccess.connectToDatabase();
			assessmentsList = new ArrayList<String>();
			String status="FAIL";
			
			String sqlQuery="select distinct(assessmentid) from f16g312_result WHERE coursename='"+courseName+"';";
			System.out.println("This is here "+ sqlQuery);
			try{
			ResultSet res= dbaccess.execQuery(sqlQuery);
			//System.out.println("rs:"+res);
			
			while(res.next()){
				
				assessmentsList.add(res.getString("assessmentid"));
			}
			//System.out.println("count:"+count);
			//createGraphAnalysisData();
			}catch(SQLException e){
				e.printStackTrace();
			}
			return status;
		}
		
		
		public void displayAssessments(ValueChangeEvent value){
			
			courseName = (String)value.getNewValue();
			setCourseName(courseName);
			//System.out.println("Selected Course:"+courseName);
		}

		public void setAssessmentTableName(String rosterTableName) {
			
			this.rosterTableName = rosterTableName;
		}
		
		public void setCourseName(String courseName) {
			this.courseName = courseName;
			this.selectedCourseName = courseName;
			//System.out.println("Course name set:"+courseName);
		}
		public ArrayList<String> getColumnNames() {
			return columnNames;
		}
		
		public String createCharts() {
			createGraphAnalysisData();
			System.out.println("Entered Method");
			FacesContext context = FacesContext.getCurrentInstance();
			String path = context.getExternalContext().getRealPath("");
			System.out.println("path-> "+path);
			JFreeChart pieChart=createPieChart();
			JFreeChart barChart=createBarChart();
			JFreeChart histChart = createHistogram();
			/*
			File outPie = new File("piechart.png");
			File outBar = new File("barchart.png");
			File outHist = new File("histchart.png");
			*/
			File outPie = new File(path+"/f16g312_piechart.png");
			File outBar = new File(path+"/f16g312_barchart.png");
			File outHist = new File(path+"/f16g312_histchart.png");
			
			System.out.println("outpie :"+outPie);
			System.out.println("outbar :"+outBar);
			System.out.println("outbar :"+outHist);
			
			try {
				ChartUtilities.saveChartAsPNG(outPie, pieChart, 600, 450);
				ChartUtilities.saveChartAsPNG(outBar, barChart, 600, 450);
				ChartUtilities.saveChartAsPNG(outHist, histChart, 600, 450);
				pieChartPath = "/f16g312_piechart.png";
				barChartPath = "/f16g312_barchart.png";
				histChartPath = "/f16g312_histchart.png";
				/*System.out.println("piepath "+pieChartPath);
				System.out.println("barpath "+barChartPath);*/
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			return "success";
		}
	}

