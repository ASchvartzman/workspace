package asg4;

public class NLPCourse implements ICourse {
	
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
			avgWeightedGrade += listStudents[i].lab*0.3 + listStudents[i].exam*0.3 + listStudents[i].project*0.4;
		}
		avgWeightedGrade = avgWeightedGrade/listStudents.length;
		return avgWeightedGrade;
	}
	
}
