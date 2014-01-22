/**
   The bank.
   
   @author Chris Fletcher ... 2012
*/

public class Bank {

  public static void closeAllAccounts(Account[] check) {
	  // TODO: you will implement this.
	  for(Account myAccount: check){
		  if(myAccount.balanceIsOk()){
			  continue; 
		  } else{
			  myAccount.close();
		  }
	  }
  }
	
  public static void main(String[] args) {
	  
    // Initialize all of our accounts
    Account[] accounts = new Account[4];
    Account firstAccount = new Account();
    Account secondAccount = new Account();
    OverdraftAccount thirdAccount = new OverdraftAccount(5);
    OverdraftAccount fourthAccount = new OverdraftAccount(10);
    
    // Fill the array
    accounts[0] = firstAccount;
    accounts[1] = secondAccount;
    accounts[2] = thirdAccount;
    accounts[3] = fourthAccount;
    
    // Put some money in the accounts
    firstAccount.deposit(25.0);
    secondAccount.withdraw(10.0);
    thirdAccount.withdraw(3.0);
    fourthAccount.withdraw(12.0);
    
    // Close bad accounts
    closeAllAccounts(accounts);
    
    // What accounts got closed?
    System.out.println("Is account 1 open? " + firstAccount.isOpen()); // should be yes!
    System.out.println("Is account 2 open? " + secondAccount.isOpen()); // should be no
    System.out.println("Is account 3 open? " + thirdAccount.isOpen()); // should be yes!
    System.out.println("Is account 4 open? " + fourthAccount.isOpen()); // should be no
  }
}
