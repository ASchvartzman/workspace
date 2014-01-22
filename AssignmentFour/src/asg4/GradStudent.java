package asg4;

public class GradStudent implements IStudent {
	
	String name; 
	int lab; 
	int exam;
	int project;
	
	public int[] getGrade(){
		int[] grades = {this.lab, this.exam, this.project};
		return grades;
	}
		
	
	public String getName(){
		return this.name; 
	}
	
	public void setName(String myName){
		this.name = myName;
	}
	
	public void setGrade(int Lab, int Exam, int Project){
		this.lab = Lab;
		this.exam = Exam;
		this.project = Project;	
	}

	@Override
	public boolean isGradStudent() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
