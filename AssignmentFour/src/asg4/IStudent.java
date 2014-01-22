package asg4;

public interface IStudent {

	public String getName();
	public void setName(String name);
	public void setGrade(int lab, int exam, int project);
	public boolean isGradStudent(); //returns true for Grad students, false otherwise
	public int[] getGrade();
}
