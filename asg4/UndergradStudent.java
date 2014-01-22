package asg4;

public class UndergradStudent implements IStudent{
	
	String name; 
	boolean isGradStudent = false;
	int lab; 
	int exam;
	int project
	
	
	public getName(){
		return this.name; 
	}
	
	public setName(String myName){
		this.name = myName;
	}
	
	public setGrade(int Lab, int Exam, int Project){
		this.lab = Lab;
		this.exam = Exam;
		this.project = Project;	
	}
	

}
