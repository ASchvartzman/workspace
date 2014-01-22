/**
   A bank account with overdraft.
   
   @author Chris Fletcher ... 2012
*/

public class OverdraftAccount extends Account {

    int overdraftAmount;

	boolean balanceIsOk(){
		if (super.getBalance() + overdraftAmount >= 0){
			return true;
		}
		return false;
	}	
	
  public OverdraftAccount(int overdraft) {
    // When you call super() in a constructor, 
    // you invoke your parent class' constructor.  
    // This is very useful when you want to 
    // initialize all of your inherited fields in 
    // the same way (i.e. 'bal' and 'open') 
    // without copy and pasting.
    super();
    overdraftAmount = overdraft; 
  }
  
}
