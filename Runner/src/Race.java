import java.util.Random;

public class Race {
    
    Runner[] myrunners; 
    int capacity;
    int runnerSoFar = 0;
    String raceName;
    // Add the missing fields for this class
    
    void printInformation(){
    	System.out.println("The name of the race is " + raceName +
    			" and the capacity is " + capacity + " contestants."); 
    
    }
    
    void addRunner(Runner person){
    	if (runnerSoFar < capacity){
    		person.register();
    		myrunners[runnerSoFar] = person;
    		runnerSoFar += 1; 
    	}
    }
    
    void printParticipants(){
    	for(Runner r: myrunners){
    		System.out.println(r.name);
    	}
    }
    
    void printResults(){
    	for(Runner r: myrunners){
    		System.out.println(r.result);
    	}
    }
    
    double getAverageResult(){
    	double totalSum = 0;
    	for(int i = 0; i < runnerSoFar; i++){
    		totalSum = totalSum + myrunners[i].result;
    	}
    	return totalSum/runnerSoFar;
    }
    
    Race(String name, int number){
    	raceName = name;
    	capacity = number; 
    	myrunners = new Runner[number];
    } 
    // return the Runner object of the fastest runner
    // note: this doesn't make sense if runRace has not been called
    // note: make sure this works even if less than max number of participants ran the race
    //         e.g., 4 runners registered for a 10 person race
    public Runner getFastestRunner() {
    	Runner bestSoFar = myrunners[0];
    	for(int i = 0; i < runnerSoFar; i++){
    		if(myrunners[i].result < bestSoFar.result){
    			bestSoFar = myrunners[i];
    		}
    	}
    	return bestSoFar;
    }
    
    // Simulate a race and set the results for each participant
    public void runRace() {
        Random rand = new Random(0);
        for (Runner r : myrunners) {
        for (int i = 0; i < myrunners.length; i++) {
            if (myrunners[i] != null)
                myrunners[i].setResult(rand.nextInt(100));        
        }
        }
    }
       
    // Add the missing methods for this class
    
    
    
    public static void main(String[] args) {
        // create a race with a maximum of 4 participants
        Race race = new Race("The annual MIT Turkey Trot", 4);
        
        // print race information
        race.printInformation();
        System.out.println();
        
        // create an array of runners
        Runner[] runners = {new Runner("runner0"),
                            new Runner("runner1"),
                            new Runner("runner2"),
                            new Runner("runner3"),
                            new Runner("runner4")};
                            
        // try to register the runners in the race
        for (int i = 0; i < runners.length; i++) {
            race.addRunner(runners[i]);
        }
        
        
        System.out.println("runner0 registered? (should be true): " + runners[0].isRegistered());
        System.out.println("runner1 registered? (should be true): " + runners[1].isRegistered());
        System.out.println("runner2 registered? (should be true): " + runners[2].isRegistered());
        System.out.println("runner3 registered? (should be true): " + runners[3].isRegistered());
        System.out.println("runner4 registered? (should be false): " + runners[4].isRegistered());
        System.out.println();
        
        
        System.out.println("Participants of The annual MIT Turkey Trot:");
        race.printParticipants();
        System.out.println();
        
        // set the results for each participant
        race.runRace();
        
        System.out.println("Results of The annual MIT Turkey Trot:");
        race.printResults();
        System.out.println();
        
        Runner fastestRunner = race.getFastestRunner();
        System.out.println("fastestRunner's name: " + fastestRunner.getName());
        System.out.println("fastestRunner's result: " + fastestRunner.getResult());
        System.out.println();
        
        double avgResult = race.getAverageResult();
        System.out.println("average time: " + avgResult);
    }

}