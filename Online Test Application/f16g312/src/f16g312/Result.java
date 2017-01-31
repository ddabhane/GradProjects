package f16g312;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;



@ManagedBean
@SessionScoped

public class Result {

private int uin;
private String courseName;
private String assessmentId;
private int score;


public int getUin() {
	return uin;
}
public void setUin(int uin) {
	this.uin = uin;
}
public String getCourseName() {
	return courseName;
}
public void setCourseName(String courseName) {
	this.courseName = courseName;
}
public String getAssessmentId() {
	return assessmentId;
}
public void setAssessmentId(String assessmentId) {
	this.assessmentId = assessmentId;
}
public int getScore() {
	return score;
}
public void setScore(int score) {
	this.score = score;
}


}
