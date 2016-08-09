/*
 * DARSHAN DABHANE
 */

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


class EmployeeDatabase extends JFrame implements KeyListener, ActionListener
{
	static int count;			//Stores the count of employee array
	static String command;		//Stores the command entered (Add / Delete / Modify / read)
	static String lname;		//Stores the content of the lastname field
	static String fname;		//Stores the content of the firstname field
	static String dept;			//Stores the content of the department field
	static String job;			//Stores the content of the job field
	int wordCount = 0;			
	String modifyField;			//Stores the fieldname to be modified
	String readField;			//Stores the fieldname to be read
	String matchValue;			//Stores the match value of the field
	String newValue;			//Stores the new value of the field
	String ln,fn,d,jb;				
	String inp;					//Stores the String command and values by user in textfield
	int length;					//Stores length of employee array
	int words;					// count of number of words in string inp
	
	Employee[] employeeArray = new Employee[100];
 		
	Container c;
	JTextField txt1;
	JTextArea txtArea;
	JButton btnSave,btnLoad;
	
    EmployeeDatabase()							//Constructor for class EmployeeDatabase
	{	
		c =getContentPane();
		c.setLayout(new FlowLayout());
		txt1 = new JTextField(40);
		txtArea = new JTextArea(20,40);
		btnSave = new JButton("SAVE");
		btnLoad = new JButton("LOAD");
		txt1.addKeyListener(this);
		btnSave.addActionListener(this);
		btnLoad.addActionListener(this);
		c.add(txt1);
		c.add(txtArea);
		c.add(btnSave);
		c.add(btnLoad);
	}
    
    
	public static void main(String[] args)
	{	
		
		EmployeeDatabase eda = new EmployeeDatabase();
		eda.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		eda.setBounds(700, 700, 500, 420);
		eda.setVisible(true);
		eda.setTitle("Employee Database");
	}	
		
	public void keyPressed(KeyEvent keyEvent) {					// This event occurs and this method invoked when user types in text field and presses enter.
   	 if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER) { 
   		 inp = txt1.getText();
   		 if(!inp.trim().isEmpty())								
   			 processing();
   		else
   			JOptionPane.showMessageDialog(c, "Invalid Command!"); //Invalid Command message is displayed if users types nothing in text field and presses enter.
   		}
     }
	
	public void keyReleased(KeyEvent keyEvent) {	}
	
	public void keyTyped(KeyEvent keyEvent) {	}
	
	//This event occurs and this method invoked when user clicks the SAVE or LOAD button that accordingly calls the saveDataToFile and loadDataFromFile functions.
	public void actionPerformed(ActionEvent e)  
	{
		if(e.getSource()==btnSave)
		saveDataToFile();
		
		if(e.getSource()==btnLoad)
		loadDataFromFile();
	} 
	
	//This method removes the element from specific index position and shrinks the array
	Employee[] removeAt(int k, Employee[] arr) 
	{	
		for (int i = k; i<count; i++)
		{
			arr[i] = arr[i+1];
			arr[i+1] = null; 
		}
		count = count - 1;
		return arr;
	}
	
	// This method performs the core function of the program.
	public void processing(){	
	try {
		length = (int) employeeArray.length;
		
		if(count == employeeArray.length)
			throw new ArrayIndexOutOfBoundsException();
		
		txtArea.setText("");
			input(inp);
				
			//calls the add function of class EmployeeDatabase and adds employee details to the database.
				if(command.equalsIgnoreCase("Add")){
					
					boolean breakloop = true;	
					if(fname!=null && dept!=null && job!=null){	
						for( int j = 0; j < employeeArray.length && breakloop; j++){
							if(employeeArray[j] == null){	
								employeeArray[j] = new Employee(lname,fname,dept,job);
								txtArea.append("The following employee has been successfully added"+"\n");
								txtArea.append("Last name: "+employeeArray[j].lastName +"\n");
								txtArea.append("First name: "+employeeArray[j].firstName+"\n") ;
								txtArea.append("Department: "+employeeArray[j].department +"\n");
								txtArea.append("Job: "+employeeArray[j].job + "\n");
								breakloop = false;
							}}}	
				}
					
			//Calls the modify function of class EmployeeDatabase and modifies the records based on single field.
				else if (command.equalsIgnoreCase("Modify") && words == 4) 
				{	
					modify(inp);
					
					if (modifyField.equalsIgnoreCase("lastName")){
						for(int p = 0 ; p < count; p++ ){
							if (employeeArray[p].lastName.equals(matchValue)){
								 employeeArray[p].setLastName(newValue);
								 txtArea.append("The field has been successfully updated" + "\n");
								 txtArea.append("Last name: "+employeeArray[p].lastName + "\n");
								 txtArea.append("First name: "+employeeArray[p].firstName + "\n");
								 txtArea.append("Department: "+employeeArray[p].department + "\n");
								 txtArea.append("Job: "+employeeArray[p].job + "\n");
							}}}
					
					else if (modifyField.equalsIgnoreCase("firstName")){
						for(int p = 0 ; p < count; p++ ){	
							if (employeeArray[p].firstName.equals(matchValue)){
								 employeeArray[p].setFirstName(newValue);
								 txtArea.append("The field has been successfully updated"+ "\n");
								 txtArea.append("Last name: "+employeeArray[p].lastName + "\n");
								 txtArea.append("First name: "+employeeArray[p].firstName + "\n");
								 txtArea.append("Department: "+employeeArray[p].department + "\n");
								 txtArea.append("Job: "+employeeArray[p].job + "\n");
							}}}
					
					else if (modifyField.equalsIgnoreCase("department")){
						for(int p = 0 ; p < count;p++ ){
							if (employeeArray[p].department.equals(matchValue)){
								 employeeArray[p].setDepartment(newValue);
								 txtArea.append("The field has been successfully updated"+ "\n");
								 txtArea.append("Last name: "+employeeArray[p].lastName + "\n");
								 txtArea.append("First name: "+employeeArray[p].firstName + "\n");
								 txtArea.append("Department: "+employeeArray[p].department + "\n");
								 txtArea.append("Job: "+employeeArray[p].job + "\n");
							}}}
					
					else if (modifyField.equalsIgnoreCase("job")){
						for(int p = 0 ; p < count;p++ ){
							if (employeeArray[p].job.equals(matchValue)){
								 employeeArray[p].setJob(newValue);
								 txtArea.append("The field has been successfully updated"+ "\n");
								 txtArea.append("Last name: "+employeeArray[p].lastName + "\n");
								 txtArea.append("First name: "+employeeArray[p].firstName + "\n");
								 txtArea.append("Department: "+employeeArray[p].department + "\n");
								 txtArea.append("Job: "+employeeArray[p].job + "\n");
							}}}
					else
						JOptionPane.showMessageDialog(c, "Invalid Command!");	// Invalid command message when the filed name to be modified is incorrect.
						
				}
				
				
			//Calls the delete function of class EmployeeDatabase and deletes the record based on the user inputs.
				else if (command.equalsIgnoreCase("Delete") && words == 5) 
				{	
					delete(inp);
					
					if(ln.equals("*") && fn.equals("*") && d.equals("*") && jb.equals("*")) {  
						int cnt = count;
						
						for(int z = 0 ; z < cnt; z++ )
							employeeArray = removeAt(z, employeeArray);
						
					    txtArea.append((cnt) +" record deleted" + "\n");
					    txtArea.append("Count of Employees: " +count + "\n");
					}
					
					else if(ln.equals("*") && !fn.equals("*") && !d.equals("*") && !jb.equals("*"))
					{
						int cnt = count;	
						for(int z= 0; z<cnt ;z++) {	
						if(employeeArray[z]==null)
							{ break; }
						else{
							if(fn.equals(employeeArray[z].firstName) && d.equals(employeeArray[z].department) && jb.equals(employeeArray[z].job))
							{
								employeeArray = removeAt(z, employeeArray);	
								z=z-1;
							}
						}	}
						txtArea.append((cnt - count) +" record deleted" + "\n");
						txtArea.append("Count of Employees: " +count + "\n");
					}
					
					else if(!ln.equals("*") && fn.equals("*") && !d.equals("*") && !jb.equals("*"))
					{
						int cnt = count;	
						for(int z= 0; z<cnt ;z++) {	
						if(employeeArray[z]==null)
							{ break; }
						else{
							if(ln.equals(employeeArray[z].lastName) && d.equals(employeeArray[z].department) && jb.equals(employeeArray[z].job))
							{
								employeeArray = removeAt(z, employeeArray);	
								z=z-1;
							}
						}	}
						txtArea.append((cnt - count) +" record deleted" + "\n");
						txtArea.append("Count of Employees: " +count + "\n");
					}
					else if(!ln.equals("*") && !fn.equals("*") && d.equals("*") && !jb.equals("*"))
					{
						int cnt = count;	
						for(int z= 0; z<cnt ;z++) {	
						if(employeeArray[z]==null)
							{ break; }
						else{
							if(ln.equals(employeeArray[z].lastName) && fn.equals(employeeArray[z].firstName) && jb.equals(employeeArray[z].job))
							{
								employeeArray = removeAt(z, employeeArray);	
								z=z-1;
							}
						}	}
						
						txtArea.append((cnt - count) +" record deleted" + "\n");
						txtArea.append("Count of Employees: " +count + "\n");
					}
		
					else if(!ln.equals("*") && !fn.equals("*") && !d.equals("*") && jb.equals("*"))
					{
						int cnt = count;	
						for(int z= 0; z<cnt ;z++) {	
						if(employeeArray[z]==null)
							{ break; }
						else{
							if(ln.equals(employeeArray[z].lastName) && fn.equals(employeeArray[z].firstName) && d.equals(employeeArray[z].department))
							{
								employeeArray = removeAt(z, employeeArray);	
								z=z-1;
							}
						}	}
						
						txtArea.append((cnt - count) +" record deleted" + "\n");
						
						txtArea.append("Count of Employees: " +count + "\n");
					}
					else if(ln.equals("*") && fn.equals("*") && !d.equals("*") && !jb.equals("*"))
					{
						int cnt = count;	
						for(int z= 0; z<cnt ;z++) {	
						if(employeeArray[z]==null)
							{ break; }
						else{
							if(jb.equals(employeeArray[z].job) && d.equals(employeeArray[z].department))
							{
								employeeArray = removeAt(z, employeeArray);	
								z=z-1;
							}
						}	}
		
						txtArea.append((cnt - count) +" record deleted" + "\n");
						
						txtArea.append("Count of Employees: " +count + "\n");
					}
					else if(!ln.equals("*") && !fn.equals("*") && d.equals("*") && jb.equals("*"))
					{
						int cnt = count;	
						for(int z= 0; z<cnt ;z++) {	
						if(employeeArray[z]==null)
							{ break; }
						else{
							if(ln.equals(employeeArray[z].lastName) && fn.equals(employeeArray[z].firstName))
							{
								employeeArray = removeAt(z, employeeArray);	
								z=z-1;
							}
						}	}
						
						txtArea.append((cnt - count) +" record deleted" + "\n");
						
						txtArea.append("Count of Employees: " +count + "\n");

					}
					else if(!ln.equals("*") && fn.equals("*") && d.equals("*") && !jb.equals("*"))
					{
						int cnt = count;	
						for(int z= 0; z<cnt ;z++) {	
						if(employeeArray[z]==null)
							{ break; }
						else{
							if(ln.equals(employeeArray[z].lastName) && jb.equals(employeeArray[z].job))
							{
								employeeArray = removeAt(z, employeeArray);	
								z=z-1;
							}
						}	}
						
						txtArea.append((cnt - count) +" record deleted" + "\n");
						txtArea.append("Count of Employees: " +count + "\n");
					}
					else if(ln.equals("*") && !fn.equals("*") && !d.equals("*") && jb.equals("*"))
					{
						int cnt = count;	
						for(int z= 0; z<cnt ;z++) {	
						if(employeeArray[z]==null)
							{ break; }
						else{
							if(d.equals(employeeArray[z].department) && fn.equals(employeeArray[z].firstName))
							{
								employeeArray = removeAt(z, employeeArray);	
								z=z-1;
							}
						}	}
						txtArea.append((cnt - count) +" record deleted" + "\n");
						
						txtArea.append("Count of Employees: " +count + "\n");
					}
					else if(!ln.equals("*") && fn.equals("*") && !d.equals("*") && jb.equals("*"))
					{
						int cnt = count;	
						for(int z= 0; z<cnt ;z++) {	
						if(employeeArray[z]==null)
							{ break; }
						else{
							if(d.equals(employeeArray[z].department) && ln.equals(employeeArray[z].lastName))
							{
								employeeArray = removeAt(z, employeeArray);	
								z=z-1;
							}
						}	}
						
						txtArea.append((cnt - count) +" record deleted" + "\n");
						
						txtArea.append("Count of Employees: " +count + "\n");
					}
					else if(ln.equals("*") && !fn.equals("*") && d.equals("*") && !jb.equals("*"))
					{
						int cnt = count;	
						for(int z= 0; z<cnt ;z++) {	
						if(employeeArray[z]==null)
							{ break; }
						else{
							if(fn.equals(employeeArray[z].firstName) && jb.equals(employeeArray[z].job))
							{
								employeeArray = removeAt(z, employeeArray);	
								z=z-1;
							}
						}	}
						
						txtArea.append((cnt - count) +" record deleted" + "\n");
						txtArea.append("Count of Employees: " +count + "\n");
					}
					else if(!ln.equals("*") && fn.equals("*") && d.equals("*") && jb.equals("*"))
					{
						int cnt = count;	
						for(int z= 0; z<cnt ;z++) {	
						if(employeeArray[z]==null)
							{ break; }
						else{
							if(ln.equals(employeeArray[z].lastName))
							{
								employeeArray = removeAt(z, employeeArray);	
								z=z-1;
							}
						}	}
						
						txtArea.append((cnt - count) +" record deleted" + "\n");
						txtArea.append("Count of Employees: " +count + "\n");
					}
					else if(ln.equals("*") && !fn.equals("*") && d.equals("*") && jb.equals("*"))
					{
						int cnt = count;	
						for(int z= 0; z<cnt ;z++) {	
						if(employeeArray[z]==null)
							{ break; }
						else{
							if(fn.equals(employeeArray[z].firstName))
							{
								employeeArray =removeAt(z, employeeArray);	
								z=z-1;
							}
						}	}
						
						txtArea.append((cnt - count) +" record deleted" + "\n");
						txtArea.append("Count of Employees: " +count + "\n");
					}
					else if(ln.equals("*") && fn.equals("*") && !d.equals("*") && jb.equals("*"))
					{
						int cnt = count;	
						for(int z= 0; z<cnt ;z++) {	
						if(employeeArray[z]==null)
							{ break; }
						else{
							if(d.equals(employeeArray[z].department))
							{
								employeeArray = removeAt(z, employeeArray);	
								z=z-1;
							}
						}	}
					
						txtArea.append((cnt - count) +" record deleted" + "\n");
						txtArea.append("Count of Employees: " +count + "\n");
					}
					else if(ln.equals("*") && fn.equals("*") && d.equals("*") && !jb.equals("*"))
					{
						int cnt = count;	
						for(int z= 0; z<cnt ;z++) {	
						if(employeeArray[z]==null)
							{ break; }
						else{
							if(jb.equals(employeeArray[z].job))
							{
								employeeArray = removeAt(z, employeeArray);	
								z=z-1;
							}
						}	}
						
						txtArea.append((cnt - count) +" records deleted" + "\n");
						txtArea.append("Count of Employees: " +count + "\n");
					}
					else
					{
						for(int q = 1 ; q <= count; q++ ){
							if ((employeeArray[q].lastName).equals(ln) && (employeeArray[q].firstName).equals(fn) && 
								(employeeArray[q].department).equals(d) && (employeeArray[q].job).equals(jb))
							{
								employeeArray = removeAt(q, employeeArray);
								count = count - 1;
								txtArea.append("\n");
								txtArea.append("records Deleted");
							}	
						}
					}		
				}
				
			//Calls the read function of class EmployeeDatabase and displays the record based on the user input as match value for a specific field.
				else if (command.equalsIgnoreCase("Read") && words == 3)
				{
					read(inp);
					int p;
					if(!readField.equals("*") && !matchValue.equals("*"))
					{
						if (readField.equalsIgnoreCase("lastName")){
							for( p = 0 ; p < count; p++ ){
								if (employeeArray[p].lastName.equals(matchValue)){
										
										txtArea.append("\n");
										
										txtArea.append(employeeArray[p].getLastname()+" "+employeeArray[p].getFirstName()+" "+employeeArray[p].getDepartment()+" "+employeeArray[p].getJob()+"\n");
									} 
								} 
							}
					
						else if (readField.equalsIgnoreCase("firstName")){
							for( p = 0 ; p < count; p++ ){	
								if (employeeArray[p].firstName.equals(matchValue))
								{
									txtArea.append("\n");
									txtArea.append(employeeArray[p].getLastname()+" "+employeeArray[p].getFirstName()+" "+employeeArray[p].getDepartment()+" "+employeeArray[p].getJob()+"\n");
								}
							}
						}
					
						else if (readField.equalsIgnoreCase("department")){
							for( p = 0 ; p < count;p++ ){
								if (employeeArray[p].department.equals(matchValue)){
									txtArea.append("\n");
									txtArea.append(employeeArray[p].getLastname()+" "+employeeArray[p].getFirstName()+" "+employeeArray[p].getDepartment()+" "+employeeArray[p].getJob()+"\n");
								}}}
					
						else if (readField.equalsIgnoreCase("job")){
							for( p = 0 ; p < count;p++ ){
								if (employeeArray[p].job.equals(matchValue)){
									txtArea.append("\n");
									txtArea.append(employeeArray[p].getLastname()+" "+employeeArray[p].getFirstName()+" "+employeeArray[p].getDepartment()+" "+employeeArray[p].getJob()+"\n");
								}}}
						else
							JOptionPane.showMessageDialog(c, "Invalid Command!");
					}
				
					else if(!readField.equals("*") && matchValue.equals("*"))
					{	
						if (readField.equalsIgnoreCase("lastName"))
							for( p = 0 ; p < count;p++ ){	
								if(!employeeArray[p].lastName.equals("unassigned"))
									txtArea.append(employeeArray[p].getLastname()+" "+employeeArray[p].getFirstName()+" "+employeeArray[p].getDepartment()+" "+employeeArray[p].getJob()+"\n");
							}
						
						else if (readField.equalsIgnoreCase("firstName"))
							for( p = 0 ; p < count;p++ ){	
								if(!employeeArray[p].firstName.equals("unassigned"))
									txtArea.append(employeeArray[p].getLastname()+" "+employeeArray[p].getFirstName()+" "+employeeArray[p].getDepartment()+" "+employeeArray[p].getJob()+"\n");
							}
						
						else if (readField.equalsIgnoreCase("department"))
							for( p = 0 ; p < count;p++ ){	
								if(!employeeArray[p].department.equals("unassigned"))
								txtArea.append(employeeArray[p].getLastname()+" "+employeeArray[p].getFirstName()+" "+employeeArray[p].getDepartment()+" "+employeeArray[p].getJob()+"\n");
							}
						
						else if (readField.equalsIgnoreCase("job"))
							for( p = 0 ; p < count;p++ ){
								if(!employeeArray[p].job.equals("unassigned"))
								txtArea.append(employeeArray[p].getLastname()+" "+employeeArray[p].getFirstName()+" "+employeeArray[p].getDepartment()+" "+employeeArray[p].getJob()+"\n");
							}
					}
					
					else 
					{
						for( p = 0 ; p < count ;p++ )
						{
							txtArea.append(employeeArray[p].getLastname()+" "+employeeArray[p].getFirstName()+" "+employeeArray[p].getDepartment()+" "+employeeArray[p].getJob()+"\n");
						}
					}
				}
				else
					JOptionPane.showMessageDialog(c, "Invalid Command!");
		 }
	catch(ArrayIndexOutOfBoundsException ae ) 				// Whenever ArrayIndexOutOfBoundsException is caught the array size is doubled
	{	
		txtArea.append("ArrayIndexOutOfBoundsException Occured"+"\n");
		txtArea.append("Length of array Doubled"+"\n");
		
		
		Employee[] employeeArray1 = new Employee[length*2];
		for (int l = 0 ; l<length; l++)
		{
			employeeArray1[l] = employeeArray[l];
		}
		employeeArray = employeeArray1;
		processing();
	}
	catch(Exception e){	}
	
	}
	
		
	//This method returns the number of words in the typed sentence i.e String inp. 
	int inputWordCount(String s)
	{
		int wordCount = 0;
	    boolean word = false;
	    int endOfLine = s.length() - 1;

	    for (int i = 0; i < s.length(); i++) 
	    {
	    	if ((Character.isLetter(s.charAt(i)) || s.charAt(i) == '*') && i != endOfLine) 
	            word = true;
	       
	        else if ((!Character.isLetter(s.charAt(i)) || s.charAt(i) != '*') && word){
	            wordCount++;
	            word = false;
	        } 
	        else if ((Character.isLetter(s.charAt(i)) || s.charAt(i) == '*') && i == endOfLine) 
	        	wordCount++;
	    }
	    return wordCount;
	}
	
	void input(String a) // Called when employee is added
	{
		int Space;
		String empDetails = a;
		words = inputWordCount(empDetails);
	
		if(!(words<3 || words>5))
		{
		Space=empDetails.indexOf(' ');
		command = empDetails.substring(0, Space);
		if(command.equalsIgnoreCase("add"))
		{
			if(words == 5){	
				int space=empDetails.indexOf(' ');
				int space2 = empDetails.indexOf(' ', space + 1);
				int space3 = empDetails.indexOf(' ', space2 + 1);
				int space4 = empDetails.indexOf(' ', space3 +1);
				command = empDetails.substring(0, space);
				lname = (empDetails.substring(space, space2)).trim();
				fname = (empDetails.substring(space2, space3)).trim();
				dept = (empDetails.substring(space3, space4)).trim();
				job = (empDetails.substring(space4,empDetails.length())).trim();
				count = count + 1;
				txtArea.append("Count of Employees: " +count+"\n");
				}
		
			else if(words == 3){
				int space = empDetails.indexOf(' ');
				int space2 = empDetails.indexOf(' ', space + 1);
				command = empDetails.substring(0, space);
				lname = empDetails.substring(space, space2).trim();
				fname = (empDetails.substring(space2,empDetails.length())).trim();
				dept = "unassigned";
				job = "unassigned";
				count = count + 1;
				txtArea.append("Count of Employees: " +count+"\n");
				}
		
			else if(words == 4){
				int space=empDetails.indexOf(' ');
				int space2 = empDetails.indexOf(' ', space + 1);
				int space3 = empDetails.indexOf(' ', space2 + 1);
				command = empDetails.substring(0, space);
				lname = (empDetails.substring(space, space2)).trim();
				fname = (empDetails.substring(space2, space3)).trim();
				dept = (empDetails.substring(space3,empDetails.length())).trim();
				job = "unassigned";
				count = count + 1;
				txtArea.append("Count of Employees: " +count+"\n");
				}
			else
				JOptionPane.showMessageDialog(c, "Invalid Command!");
			} 	
		}
		else
			JOptionPane.showMessageDialog(c, "Invalid Command!");
	}
	
	
	/*
	  Takes field name, match value and new value and stores them in respective global variables. 
	  This function is called from processing function (Condition where command is modify).
	*/
	void modify(String modify) {
		
	    // command fieldname matchvalue newvalue
			if (words == 4)
			{	
			int space = modify.indexOf(' ');
			int space2 = modify.indexOf(' ', space + 1);
			int space3 = modify.indexOf(' ', space2 + 1);
			modifyField = (modify.substring(space, space2)).trim();
			matchValue = (modify.substring(space2, space3)).trim();
			newValue = (modify.substring(space3,modify.length())).trim();
			}
		}
	
	/* 
	  Takes field name and match values for lastName, firstName, department, job and stores them in respective global variables. 
	   This function is called from processing function (Condition where command is delete).
	*/
	
	void delete(String delete){
	// command lastname firstname department job
		if (words == 5)
		{	
			int space = delete.indexOf(' ');
			int space2 = delete.indexOf(' ', space + 1);
			int space3 = delete.indexOf(' ', space2 + 1);
			int space4 = delete.indexOf(' ', space3 + 1);
			ln = (delete.substring(space, space2)).trim();
			fn = (delete.substring(space2, space3)).trim();
			d = (delete.substring(space3, space4)).trim();
			jb = (delete.substring(space4,delete.length())).trim();
		}	
	}
	
	/* 
	  Takes field name and match values for lastName, firstName, department, job and stores them in respective global variables. 
	  This function is called from processing function (Condition where command is read).
	*/

	void read(String read){
			if (words == 3)
			{		
			//Enter in the following format: fieldname matchvalue");
			int space=read.indexOf(' ');
			int space2 = read.indexOf(' ', space + 1);
			readField = read.substring(space, space2).trim();
			matchValue = (read.substring(space2,read.length())).trim();
			}
			else if(words > 3)
				JOptionPane.showMessageDialog(c, "Invalid Command!");
				
		}
	
	/* The following methods saves and loads employee array to and from file respectively  */
	void saveDataToFile() 
	{ 
		File outFile = new File("EDA2.data");
		try
		{
			FileOutputStream outFileStream = new FileOutputStream(outFile);
			ObjectOutputStream outObjectStream = new ObjectOutputStream(outFileStream);
			outObjectStream.writeObject(employeeArray);
			for (int i = 0; i < employeeArray.length; i++)
			{
				outObjectStream.writeObject(employeeArray[i]);
			}
			outFileStream.close();
			outObjectStream.close();
		}
		catch(Exception e)
		{
			
		}
	}
	
	void loadDataFromFile()
	{
		File inFile = new File("EDA2.data");
		try {
			FileInputStream inFileStream = new FileInputStream(inFile);
			ObjectInputStream inObjectStream = new ObjectInputStream(inFileStream);
			employeeArray = (Employee []) inObjectStream.readObject();
			for (int i = 0; i < employeeArray.length; i++)
			{
				employeeArray[i] = (Employee)inObjectStream.readObject();
			}
			inFileStream.close();
			inObjectStream.close();
		}
		catch(Exception e)
		{
			
		}
		finally
		{	
			for(int c = 0; c<employeeArray.length; c++)
			{
				if(employeeArray[c]!=null)
				{
					count = count + 1;
				}
			}
		}
	}
}
	
/*
   This class consist of set and get functions for setting and retrieving values of the records within the database.
 */
 
class Employee implements Serializable
{
	String lastName;
	String firstName;
	String department;
	String job;
	
	
	// This is parameterized constructor of class Employee that is invoked from the processing function when employee is added.
	Employee(String lname, String fname, String dept, String job)
	{
		setLastName(lname);
		setFirstName(fname);
		setDepartment(dept);
		setJob(job);
	}
	
	void setLastName(String ln) { lastName = ln; }
	
	void setFirstName(String fn) { firstName = fn; }
	
	void setDepartment(String dept) { department = dept; }
	
	void setJob(String jb) { job = jb; }
	
	String getLastname() { return lastName; }
	
	String getFirstName() { return firstName; }
	
	String getDepartment() { return department; }
	
	String getJob() { return job; }	
}






