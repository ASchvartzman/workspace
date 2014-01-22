package asg4;


/**
 * Assignment Specification
 * 
 * Two interfaces given:
 * 1. ICourse interface, and 2. IStudent interface
 * 
 * Two kinds of students (each should implement IStudent): 
 * 1. GradStudent
 * 2. UndergradStudent
 * 
 * You can set the grade for either type by calling:
 * 
 * IStudent gradStudent =  new GradStudent();
 * gradStudent.setName("Anant");
 * gradStudent.setGrade(67, 96, 95); 
 * 
 * IStudent undergradStudent = new UndergradStudent();
 * undergradStudent.setName("Jim");
 * undergradStudent.setGrade(95, 76, 85);
 * 
 * 
 * Three Courses (each should implement ICourse)
 * 1. JavaCourse
 * 2. MLCourse
 * 3. NLP Course
 * 
 * Either type of student should be able to register to any course:
 * 
 * ICourse javaCourse =  new JavaCourse();
 * javaCourse.register(gradStudent);
 * 
 * ICourse nlpCourse =  new NLPCourse();
 * nlpCourse.register(undergradStudent);
 * 
 *
 * weighted grade calculation (for each student) for these courses are different
 * for Java course	: lab is weighted: 60%, exam: 10%, project: 30%
 * for NLP 	course	: lab is weighted: 30%, exam: 30%, project: 40%
 * for ML 	course	: lab is weighted: 20%, exam: 60%, project: 20%
 * 
 * you need to find, average weighted grade for each course.
 * 
 * all required interface definitions are given
 * 
 */


/**
 * 
 * DO NOT CHANGE THE BELOW CODE
 *
 */
public class Test {
	public static void main(String[] args){
				
		IStudent gradStudent1 =  new GradStudent();
		gradStudent1.setName("Anant");
		gradStudent1.setGrade(95, 76, 85);
		
		IStudent gradStudent2 =  new GradStudent();
		gradStudent2.setName("Kasia");
		gradStudent2.setGrade(67, 96, 95);
		
		IStudent undergradStudent1 = new UndergradStudent();
		undergradStudent1.setName("Jim");
		undergradStudent1.setGrade(95, 76, 85);
		
		IStudent undergradStudent2 = new UndergradStudent();
		undergradStudent2.setName("Chris");
		undergradStudent2.setGrade(45, 86, 75);
		
		ICourse javaCourse =  new JavaCourse();
		javaCourse.register(gradStudent1);
		javaCourse.register(undergradStudent1);		
		System.out.println(javaCourse.getAverageWeightedGrade());
		
		javaCourse.unRegister(undergradStudent1);
		javaCourse.register(undergradStudent2);
		System.out.println(javaCourse.getAverageWeightedGrade());
		
		ICourse nlpCourse =  new NLPCourse();
		nlpCourse.register(gradStudent1);
		nlpCourse.register(undergradStudent2);
		
		System.out.println(nlpCourse.getAverageWeightedGrade());
		nlpCourse.register(gradStudent2);
		nlpCourse.unRegister(undergradStudent2);
		System.out.println(nlpCourse.getAverageWeightedGrade());
		
		ICourse mlCourse =  new MLCourse();
		mlCourse.register(gradStudent1);
		mlCourse.unRegister(gradStudent1);
		
		System.out.println(mlCourse.getAverageWeightedGrade());
		
	}

}
