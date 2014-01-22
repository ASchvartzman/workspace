

class RecordSheet {
	

    String[] names = {
        "Elena", "Thomas", "Hamilton", "Suzie", "Phil", "Matt", "Alex",
        "Emma", "John", "James", "Jane", "Emily", "Daniel", "Neda",
        "Aaron", "Kate"
    };

    int[] times = {
        341, 273, 278, 329, 445, 402, 388, 275, 243, 334, 412, 393, 299,
        343, 317, 265
    };
	
	public String[] get_names(){
		return names;
	}
	
	public int[] get_times(){
		return times;
	}
    

	
	/*  write your code below */
	/* fill in the required methods such that it compiles and runs */

	// method to find both best times at once
	public int[] find_lowest_time(int[] someList){
		int lowest_time = 1000;
		int second_lowest_time = 10001; 
	
		for(int i = 0; i < someList.length;i++){
			if (someList[i] <= lowest_time){
				second_lowest_time = lowest_time;
				lowest_time = someList[i];
			}
			else if(someList[i] < second_lowest_time){
				second_lowest_time = someList[i];
			}
		}
		int[] lowestTimes = {lowest_time, second_lowest_time};
		return lowestTimes;
	}
	//for simplicity, this is just a normal linear-time search
	//it could be implemented to be binary search, but seems overkill
	public int find_lowest_index(int[] someList){
		int bestTime = find_lowest_time(someList)[0];
		int index = 0;
		while(index < someList.length){
			if (bestTime == someList[index]){
				break; 
			}
			index++;
		}
		return index;
	}
	
	//same note as above
	public int find_second_lowest_index(int[] someList){
		int bestTime = find_lowest_time(someList)[1];
		int index = 0;
		while(index < someList.length){
			if (bestTime == someList[index]){
				break; 
			}
			index++;
		}
		return index;
	}
	/* -- end -- */
}

/* Don't change anything below this line */
class Marathon {
		
    public static void main (String[] arguments) {
        RecordSheet record_sheet = new RecordSheet();
		String[] names = record_sheet.get_names();
		int[] times = record_sheet.get_times();
		
		System.out.println("Printing all records:");
        for (int i = 0; i < names.length; i++) {
            System.out.println(names[i] + ": " + times[i]);
        }
				
		int lowest = record_sheet.find_lowest_index(times);  // you need to implement this
		System.out.println("Printing fastest record:");
		System.out.println(names[lowest] + ": " + times[lowest]);
		
		int second_lowest = record_sheet.find_second_lowest_index(times); // you need to implement this
		System.out.println("Printing second fastest record:");
		System.out.println(names[second_lowest] + ": " + times[second_lowest]);
		
    }

}
