package asg4;

import java.util.ArrayList;

//
public class NLPCourse implements ICourse {
	ArrayList<IStudent> listStudents = new ArrayList<IStudent>();
	int counter = 0;
	
	public void register(IStudent s){
		listStudents.add(s);
		counter += 1; 
	}
	public void unRegister(IStudent s){
		listStudents.remove(s);
		counter -= 1; 
	}
	public double getAverageWeightedGrade(){
		double avgWeightedGrade = 0;
		for(int i = 0; i < counter; i++){
			if(listStudents.get(i) != null){
			int[] grades = listStudents.get(i).getGrade();
			avgWeightedGrade += 0.3*grades[0] + 0.3*grades[1] + 0.4*grades[2];
		}
		}
		return avgWeightedGrade/counter;
	}
}
