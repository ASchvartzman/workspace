package asg4;

public class JavaCourse implements ICourse {
	IStudent[] listStudents;
	
	public void register(IStudent s){
		listStudents.add(list.Students.length, s); 
	}
	
	public void unRegister(IStudent s){
		listStudents.remove(s); 
	}
	public double getAverageWeightedGrade(){
		double avgWeightedGrade = 0;
		for (int i = 0; i < listStudents.length; i++){
			avgWeightedGrade += listStudents[i].lab*0.6 + listStudents[i].exam*0.1 + listStudents[i].project*0.3;
		}
		avgWeightedGrade = avgWeightedGrade/listStudents.length;
		return avgWeightedGrade;
	}
}
