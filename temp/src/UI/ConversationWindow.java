package UI;

import static javax.swing.GroupLayout.Alignment.BASELINE;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.DefaultCaret;

import model.User;

/**
 * Main Conversation Window.
 * 
 * It has a JTextField userText, that takes in messages from the user and sends them to the server.
 * These get immediately displayed in the JTextArea chatHistory. 
 * 
 * It has a JList conversation group, which displays the users currently in the conversation. 
 * 
 * Users invite people to the conversation by typing their names on the JTextField addThisPerson, and hitting
 * the JButton addPerson. 
 * 
 * Users may leave the conversation by hitting the JButton leaveChat.
 * 
 * @author arielschvartzman
 *
 */

public class ConversationWindow extends JFrame implements Cloneable {
    final JTextField userText;                                      // Takes in user messages
    final JTextArea chatHistory;                                    // Displays chatHistory. Not editable. 
    final JScrollPane scrollHistory = new JScrollPane();            // Used to make the chatHistory scrollable
    final JScrollPane scrollGroup = new JScrollPane();              // Used to make the convGroup scrollable
    final JList convGroup;                                          // List representing users in conversation
    final DefaultListModel peopleChatting = new DefaultListModel(); // DefaultListModel for convGroup
    final JButton addPerson;                                        // JButton for addThisPerson
    final JButton leaveChat;                                        // JButton to leave chat
    JComboBox usersOnline = new JComboBox();
    DefaultComboBoxModel usersModel = new DefaultComboBoxModel();
    public String otherUser;
    public boolean group = false;
    public User user;
    private String convID;
    
    public ConversationWindow(String otherUser, final User user, String convID){
        this.otherUser = otherUser;
    	this.convID = convID;
        this.user = user;
        JFrame frame = new JFrame();
        
        leaveChat = new JButton("Leave");
        leaveChat.setName("Leave");
        
        addPerson = new JButton("Add User: ");
        addPerson.setName("addUser");
        addPerson.setPreferredSize(new Dimension(100, 25));
        addPerson.setMaximumSize(new Dimension(100, 25));
        
        userText = new JTextField();
        userText.setName("input");
        userText.setPreferredSize(new Dimension(400, 25));
        userText.setMaximumSize(userText.getPreferredSize());
                
        chatHistory = new JTextArea();
        chatHistory.setName("chatHistory");
        chatHistory.setEditable(false);
        chatHistory.setLineWrap(true);
        //Auto-scrolling the chatHistory.
        DefaultCaret caret = (DefaultCaret)chatHistory.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        
        usersOnline = new JComboBox(usersModel);
        
        peopleChatting.addElement(otherUser);
        convGroup = new JList(peopleChatting);
        convGroup.setName("onlineUsers");
        convGroup.setPreferredSize(new Dimension(100, 25));
        convGroup.setMaximumSize(convGroup.getPreferredSize());
        
        JScrollPane scroll = new JScrollPane (chatHistory, 
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setSize(new Dimension(400, 400));
        
        JScrollPane scrollGroup = new JScrollPane(convGroup,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollGroup.setSize(new Dimension(100, 25));    
        
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        //Horizontal grouping of components
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(scroll)
                        .addComponent(userText)
                        .addComponent(leaveChat))
                .addGroup(layout.createParallelGroup()
                        .addComponent(scrollGroup)
                        .addComponent(addPerson)
                        .addComponent(usersOnline))
                );
             
        //Vertical grouping of components
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(BASELINE)
                .addComponent(scroll)
                .addComponent(scrollGroup))
            .addGroup(layout.createParallelGroup(BASELINE)
                .addComponent(userText)
                .addComponent(usersOnline))
            .addGroup(layout.createParallelGroup(BASELINE)
                .addComponent(addPerson)
                .addComponent(leaveChat))

                    
            );
        //Listener for the UserText
        userText.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
               if(!userText.getText().equals("")){
            	   if (userText.getText().startsWith("*")){
            		   user.sendServer("*sendMessage "+ConversationWindow.this.convID+" " + user.getUserID() +" "+ user.getUserID() + ": " + userText.getText());										
            	   }
            	   else{
            	       user.sendServer("sendMessage "+ConversationWindow.this.convID+" " + user.getUserID() +" "+ user.getUserID() + ": " + userText.getText());
            	   }
               }
                   userText.setText("");
               }
        });  
            
        //Listener for the AddUser Button 
        //Need to finish
        addPerson.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String input = (String) usersOnline.getSelectedItem();
                if(!input.equals("") && !input.equals(null) && !input.contains(" ")){
                    user.sendServer("addUser " + ConversationWindow.this.convID + " " + input);
                }
            }
        });
        
        //Listener for the Leave Button
        leaveChat.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
            	if (group){
            		user.sendServer("leave " + ConversationWindow.this.convID + " " + user.getUserID());
            		user.sendServer("showOnline");
            	}
            	if (isDisplayable()) {
                    setVisible(false);
                }
            }
        });
        
        // Adds a title to the GUI window, sets the size of the window and packs it. 
        user.sendServer("showOnline");
        frame.pack();
        frame.setLayout(new BorderLayout());
        Insets insets = frame.getInsets();
        frame = null;
        this.setSize(insets.left + insets.right + 500, insets.bottom + insets.top + 500);
        this.setVisible(true);
        this.setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Conversation between " + user.getUserID() + ", " + otherUser);
    }

    /**
     * Method used to receive messages sent from users. Updates the chatHistory by adding the new line. 
     * 
     * @param message, a String representing the new message sent. 
     */
	public void receiveMessage(String message){
		String currentHistory = chatHistory.getText();
	    String updatedHistory;
		if(!currentHistory.equals("")){
	   	 	updatedHistory = currentHistory +message;
	   	 	chatHistory.setText(updatedHistory);
	   	    Toolkit.getDefaultToolkit().beep();
		   	 }
	    else{
	    	chatHistory.setText(message);
	    }
 	}	
	
	/**
	 * Receives a userName and updates the GUI to show that this person just joined the chat.
	 * @param userName, the user Name of the added user. 
	 */
	public void receiveUser(String userName){
	    peopleChatting.addElement(userName);
	}
    
	public void removeUser(String userName){
		peopleChatting.removeElement(userName);
		if (peopleChatting.size() == 0){
			freezeWindow();
		}
	}
	
	/**
	 * Freezes all fields and unnecessary buttons in the window. This method is called
	 * when a user leaves the chat room, or when someone is reviewing an old conversation. 
	 * 
	 */
	public void freezeWindow(){
	    userText.setEditable(false);
	    usersOnline.setEnabled(false);	    
	}
	
	
	public String getConvID(){
		return this.convID;
	}
	
	
	public void updateWhosOnline(String[] users){
	    usersModel.removeAllElements();
	    for(String user: users){
	        if(!user.equals(this.user.getUserID()) && !peopleChatting.contains(user)){
	            usersModel.addElement(user);
	        }
	    }
	}
	
	public void changeConvID(){
		if (!this.convID.startsWith("simple")){
			throw new RuntimeException("Invalid change of chat type (simple to group)");
		}
		this.convID = "group" + this.convID.substring(6);
	}

//    DELETE
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ConversationWindow main = new ConversationWindow(null, null, null);
                main.freezeWindow();
                main.setVisible(true);
            }
        });
    } 
}

     