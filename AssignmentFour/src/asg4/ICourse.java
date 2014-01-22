package asg4;

public interface ICourse {
	public void register(IStudent s);
	public void unRegister(IStudent s);
	public double getAverageWeightedGrade();
}
