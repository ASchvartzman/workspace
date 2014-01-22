package UI;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * LoginWindow
 * 
 * First interaction window between server and client. 
 * 
 * This window has three fields the client must field in order to connect: 
 * - Server IP
 * - Port (defaulted to 4444)
 * - username, which must be one string without spaces
 * 
 * There are JTextFields for these inputs, and there's a JButton Submit for when the user 
 * is ready to submit the information. 
 * 
 * @author wyrobnik, arielschvartzman
 *
 */
public class LoginWindow extends JFrame {
	private final JLabel userIDInputLabel;		//Label telling user what is requested
	private final JButton submitButton;			//JButton to submit
	private final JTextField userIDInputField;	//Input Field for Username/ID
	private volatile Boolean finish = false;	//See atremptLogin method
	private final JLabel iPInputLabel;          //IP goes here
	private final JTextField iPInput;           //TextField for IP
	private final JLabel portLabel;             //PortLabel goes here
	private final JTextField portInput;         //Textfield for Port
	private String newUserID = null;            //Username
	private String port = "4444";               //Default Port
	private String ip = "";                     //Default IP
	
	/**
	 * Constructor, setup of GUI window. 
	 */
	public LoginWindow(){
		iPInputLabel = new JLabel("What is the servers IP address?");
		iPInput = new JTextField();
		//Fixes the size of the field
		iPInput.setPreferredSize(new Dimension(100, 25));
		iPInput.setMaximumSize(iPInput.getPreferredSize());
		iPInput.setName("ip");
		
		portLabel = new JLabel("Port number?");
		portInput = new JTextField("4444");
		//Fixes the size of the field
	    portInput.setPreferredSize(new Dimension(100, 25));
	    portInput.setMaximumSize(portInput.getPreferredSize());
	    portInput.setName("port");
	    
		userIDInputLabel = new JLabel("What is your User Name?");
		userIDInputField = new JTextField();
		//Fixes the size of the field
		userIDInputField.setPreferredSize(new Dimension(100, 25));
		userIDInputField.setMaximumSize(userIDInputField.getPreferredSize());
		userIDInputField.setName("username");
		
		submitButton = new JButton("Submit");
		submitButton.setName("Submit");
		
		this.setSize(200, 240);
		 GroupLayout layout = new GroupLayout(this.getContentPane());
	        setLayout(layout);
	        
	        layout.setAutoCreateContainerGaps(true);
	        layout.setAutoCreateGaps(true);
	        
	        //Vertical Grouping of the components
	        layout.setVerticalGroup(
	                layout.createSequentialGroup()
	                		.addComponent(iPInputLabel)
	                        .addComponent(iPInput)
	                        .addComponent(portLabel)
	                        .addComponent(portInput)
	                		.addComponent(userIDInputLabel)
	                        .addComponent(userIDInputField)
	                       	.addComponent(submitButton));
	        //Horizontal Grouping of the components
	        layout.setHorizontalGroup(
                    layout.createParallelGroup() 
                    .addGroup(layout.createParallelGroup()
                    		.addGroup(layout.createParallelGroup()
    	                    		.addComponent(iPInputLabel))
    	                    .addGroup(layout.createParallelGroup()
	                    		.addComponent(iPInput))
	                    	.addGroup(layout.createParallelGroup()
	                    		.addComponent(portLabel))
	                    	.addGroup(layout.createParallelGroup()
	                    		.addComponent(portInput))
                    		.addGroup(layout.createParallelGroup()
                    				.addComponent(userIDInputLabel))
	                    .addGroup(layout.createParallelGroup()
	                    		.addComponent(userIDInputField))
	                    .addGroup(layout.createParallelGroup()
	                    		.addComponent(submitButton))));
	        
	        //Listener for the submit button, calls requestLogin
	        submitButton.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent event){
	        		requestLogin();
	        	}
	        });
	        
	        //Listener for the IDInputField
	        userIDInputField.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent event){
	        		requestLogin();
	        	}
	        });
	        
	        //Listener for the PortInput
	        portInput.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent event){
	        		requestLogin();
	        	}
	        });
	        
	        //Listener for the IPInput
	        iPInput.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent event){
	        		requestLogin();
	        	}
	        });
	}
	
	/**
	 * When the user submits a username, then this method is called 
	 */
	private void requestLogin(){
    	newUserID = userIDInputField.getText();
    	port = portInput.getText();
    	ip = iPInput.getText();
    	this.finish = true;				// Tells attemptLogin, that the user has inputed a username
    }
	
	/**
	 * ???
	 * @return
	 */
	public List<String> attemptLogin() {
        final LoginWindow main = this;
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	main.setVisible(true);
            }
        });
		while(!this.finish); ;		// Loops until a new Username is available (is called from the CLient class)
		this.finish = false;		// In case of invalid input, for next attempt needs to be false again
		List<String> outputList = Arrays.asList(ip, port, newUserID);
		return outputList;
	//
	}
	
}
