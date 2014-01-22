public class FooCorporation {
	public static void payDay(double basePay, double numHrs) {
		boolean payError = false;
		boolean hourError = false;
		boolean fourtyOrMore = false; 
		if(basePay < 8.0){
			payError = true;
		}
		
		if(numHrs > 40.0){
			fourtyOrMore = true;
		}
		
		if(numHrs > 60.0){
			hourError = true;
			}

		if (hourError || payError) {
			System.out.println("Error! Either too many hours (> 60.0) or too little money (< 8.0).");
		} else if(fourtyOrMore){
			System.out.println("You've earned " + (basePay*40 + basePay*1.5*(numHrs - 40)));
		}else{
			System.out.println("You've earned " + (basePay*numHrs));
		}
		
	}

	public static void main(String[] args) {
		System.out.println("Employee 1:");
		payDay(7.75, 35.0);
		System.out.println("Employee 2:");
		payDay(8.35, 47.0);
		System.out.println("Employee 3:");
		payDay(10.50, 73.0);
	}
}