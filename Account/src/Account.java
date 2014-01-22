
/**
   A class for bank accounts.
   
   This class provides the basic functionality of accounts.
   It allows deposits and withdrawals but not overdraft
   limits or interest rates.   
   @author Stuart Reynolds ... 1999
   @author Chris Fletcher ... 2012
*/

public class Account {
	
  private double bal;  // The current balance
  private boolean open;
	
  public Account() {    
    bal=0.0;
    open=true;
  }

  public void deposit(double sum) {
    if (sum>0) { 
      bal+=sum;    
    } else {
      System.err.println("Account.deposit(...): "
                       + "cannot deposit negative amount.");
	  }
  }

  public void withdraw(double sum) {
    if (sum>0) {
      bal-=sum;    
    } else {
      System.err.println("Account.withdraw(...): "
                       + "cannot withdraw negative amount.");
    }											 
  }

  public double getBalance() {
    return bal;
  }
	
  public void close() {
    open=false;
  }
  
  public String isOpen() {
    if (open) {
      return "yes";
    } else {
      return "no";
    }
  }
  
  boolean balanceIsOk(){
	  if(bal >= 0){
		  return true;
	  }
	  return false; 
	  }
}