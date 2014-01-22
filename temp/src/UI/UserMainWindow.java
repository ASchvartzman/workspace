package UI;

import static javax.swing.GroupLayout.Alignment.BASELINE;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.WindowConstants;

import model.User;
import UI.ConversationWindow;


/**
 * Main window for the client. 
 * 
 * It displays a JLabel WelcomeMessage, some instructions for the user (JLabel Rules).
 * 
 * There is a JList onlineUsers that shows which users are online, as well as another 
 * JList recentTalks displaying recent conversations. 
 * 
 * There's an Image image (defaulted to an anon user), which can be set to any picture a user through a JTextField
 * addPic and a JButton addPicButton. 
 * 
 * Finally, there's a JButton logOff for the user to logOff.
 *
 * @author wyrobnik, arielschvartzman
 *
 */
public class UserMainWindow extends JFrame {
	
	final JList onlineUsers;                   // Displays who is online   
	final JList recentTalks;                   // Displays recent conversations, during current session
	final DefaultListModel recentHistory;      // DefaultListModel for recentTalks
	final DefaultListModel onlineData;         // DefaultListModel for onlineUsers
	final JScrollPane scrollUsers = new JScrollPane();
	final JScrollPane scrollTalks = new JScrollPane();
    final JLabel WelcomeMessage;               // Displays a Welcome Message for the client.
    final JLabel Rules;                        // Explains how to use the program
    final JLabel whoDidYouTalkTo;              // Label for recentTalks
    final JLabel Online;                       // Label for onlineUsers
    final JTextField addPic;                   // Takes a pathname/URL, used to change profile Picture
    final JButton addPicButton;                // JButton for the addPic
    Image image;                               // Profile Picture, defautle to an anonymous user. 
    final JButton logOffButton;                // JButton to leave the chat rooom
    User user = null;                          // User object, created when the client is initated. 
	
    
    /**
     * 
     * UserMainWindow constructor. Sets up the UI, the components and the listeners for the lists, 
     * fields and buttons. 
     * 
     * @param user, a User object representing the user associated to this UserMainWindow
     * @throws IOException
     */
	public UserMainWindow(final User user) throws IOException{
		this.user = user; 
	    //Creating the new Frame, and setting a standard size
	    final JFrame frame = new JFrame();
	    
	    Font font = new Font("Verdana", Font.BOLD, 24);
	    WelcomeMessage = new JLabel("Welcome to MAD Chat!" + "\n");
	    WelcomeMessage.setFont(font);
	    WelcomeMessage.setName("welcome");

	    
	    Font font2 = new Font("Verdana", Font.PLAIN, 12);
	    Rules = new JLabel("<html> Hello, " + user.getUserID() + "! <br>" + 
	            "In order to start a conversation,<br>" +
	            "click on a users name on the list.<br><br>" +
	            "You can also review recent conversations under<br>" +
	            " the recent talks list. <br><br> " +
	            "You may also choose your own profile picture, <br> " +
	            "with either a URL or a pathname. <br><br> " +
	            "Finally, to log off safely, hit the Log Off Button!" +
	            "</html>");
	    Rules.setFont(font2);
	    Rules.setName("rules");
	    
	    Online = new JLabel("Who's Online?");
	    Online.setFont(font2);
	    Online.setName("who's online");
	    
	    whoDidYouTalkTo = new JLabel("Recent Chats:");
	    whoDidYouTalkTo.setFont(font2);       
	    whoDidYouTalkTo.setName("recentChats");

	    
	    addPic = new JTextField();
        addPic.setPreferredSize(new Dimension(200, 25));
        addPic.setMaximumSize(addPic.getPreferredSize());        
        addPic.setName("picPathOrURL");

	    
        addPicButton = new JButton("Change Picture: ");
        addPicButton.setName("changePic");
        addPicButton.setPreferredSize(new Dimension(100, 25));
        addPicButton.setMaximumSize(new Dimension(100, 25));
	    
	    GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        onlineData = new DefaultListModel();
        onlineUsers = new JList(onlineData);
        onlineUsers.setPreferredSize(new Dimension(200, 200));
        onlineUsers.setMaximumSize(onlineUsers.getPreferredSize());
        JScrollPane scrollUsers = new JScrollPane (onlineUsers, 
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollUsers.setSize(new Dimension(200, 200));

        
        recentHistory = new DefaultListModel();
        recentTalks = new JList(recentHistory);
        recentTalks.setPreferredSize(new Dimension(200, 200));
        recentTalks.setMaximumSize(recentTalks.getPreferredSize());
        JScrollPane scrollTalks = new JScrollPane (recentTalks, 
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollTalks.setSize(new Dimension(200, 200));
        
        
        logOffButton = new JButton("Log Off");
        logOffButton.setName("logOff");
        logOffButton.setPreferredSize(new Dimension(100, 25));
        logOffButton.setMaximumSize(new Dimension(100, 25));
        
        //Defaulted Anonymous Image
        image = ImageIO.read(new File("src/UI/male200.png"));
        final JLabel picLabel = new JLabel(new ImageIcon(image));
        picLabel.setName("image");

        
        //Horizontal grouping of the components
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup()
               .addComponent(WelcomeMessage)
               .addComponent(Rules)
               .addComponent(picLabel)
               .addComponent(addPicButton)
               .addComponent(addPic))
            .addGroup(layout.createParallelGroup()
                .addComponent(Online)
                .addComponent(scrollUsers)
                .addComponent(whoDidYouTalkTo)
                .addComponent(scrollTalks)
                .addComponent(logOffButton))
            );
         
        //Vertical grouping of components
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(BASELINE)
                .addComponent(WelcomeMessage)
                .addComponent(Online))
            .addGroup(layout.createParallelGroup(BASELINE)
                .addComponent(scrollUsers)
                .addComponent(Rules))
            .addGroup(layout.createParallelGroup(BASELINE)
                .addComponent(whoDidYouTalkTo)
                .addComponent(addPicButton))
            .addGroup(layout.createParallelGroup(BASELINE)
                .addComponent(picLabel)
                .addComponent(scrollTalks))
            .addGroup(layout.createParallelGroup(BASELINE)
                .addComponent(addPic)
                .addComponent(logOffButton))
        );
        
        //Listener for the Mouse and the JList onlineUsers
        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                	try{
	                	String selectedItem = onlineUsers.getSelectedValue().toString();
	                	if (!selectedItem.equals("")){
	                		user.sendServer("startConversation " + user.getUserID() + " " + selectedItem);
	                	}
                	}catch(NullPointerException e1){                		
                	}
                 }
            }
        };
        onlineUsers.addMouseListener(mouseListener);
        
        
        //Listener for the Mouse and the JList recentTalks
        ////////FIX FOR GROUP SITUATION
        MouseListener mouseListener2 = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    try{
                        String[] selectedItem = recentTalks.getSelectedValue().toString().split(" ");
                        String convID = selectedItem[selectedItem.length -1];
                        if (!selectedItem.equals("")){
                            ConversationWindow oldConv = user.conversations.get(convID);
                            ConversationWindow freezeConv = new ConversationWindow(oldConv.otherUser, user, convID);
                            freezeConv.chatHistory.setText(oldConv.chatHistory.getText());
                            freezeConv.setVisible(true);
                            freezeConv.addPerson.setEnabled(false);
                            freezeConv.freezeWindow();
                            freezeConv.setTitle("Old Conversation between " + user.getUserID() + ", " + oldConv.otherUser);
                        }
                    }catch(NullPointerException e1){   
                        e1.printStackTrace();
                    }
                 }
            }
        };
        recentTalks.addMouseListener(mouseListener2);
                
        //Listener for LogOffButton 
        logOffButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                logOffMethod();
            }
        });
        
        //Listener for the addPic Button
        addPicButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(!addPic.equals("")){
                    Image image = null;
                    //Tries to read URL
                    if(addPic.getText().startsWith("http")){
                        try {
                            URL url = new URL(addPic.getText());
                            image = ImageIO.read(url);
                            
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    //Otherwise, tries to read file pathname
                    else{
                        try{
                            File sourceimage = new File(addPic.getText());
                            image = ImageIO.read(sourceimage);
                        } catch (IOException e1){
                            e1.printStackTrace();
                        }
                    }
                    //Necessary to rescale down the picture
                    int height = image.getHeight(null);
                    int width = image.getWidth(null);
                    Image imageScaled = image.getScaledInstance(200, 200*height/width, Image.SCALE_DEFAULT);
                    picLabel.setIcon(new ImageIcon(imageScaled));
                }
                //Reset the textfield. 
                addPic.setText("");
            }
                
            });
        
        ToolTipManager.sharedInstance().registerComponent(onlineUsers);
        
        
        this.addWindowListener(new WindowListener(){
			@Override
			public void windowClosing(WindowEvent arg0) {
				logOffMethod();
			}
			@Override
			public void windowActivated(WindowEvent arg0) {}
			@Override
			public void windowClosed(WindowEvent arg0){}
			public void windowDeactivated(WindowEvent arg0) {}
			@Override
			public void windowDeiconified(WindowEvent arg0) {}
			@Override
			public void windowIconified(WindowEvent arg0) {}
			@Override
			public void windowOpened(WindowEvent arg0) {}
			
        });
        
        // Adds a title to the GUI window, sets the size of the window and packs it. 
        setTitle("MAD Chat");
        frame.pack();
        frame.setLayout(new BorderLayout());
        Insets insets = frame.getInsets();
        this.setSize(insets.left + insets.right + 500, insets.bottom + insets.top + 500);
        this.setVisible(true);
        this.setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	/**
	 * Sends a logOff message to the user, who in turn sends the message to the server. Also closes
	 * all windows. 
	 */
	private void logOffMethod(){
    	if (isDisplayable()) {
            user.sendServer("logOff " + user.getUserID());
            setVisible(false);
            System.exit(0);
    	}
    }
	
	/**
	 * Simple method to display the window at a given point. 
	 * @param windowLocation
	 */
	public void openWindow(Point windowLocation){
		this.setLocation(windowLocation);
		this.setVisible(true);
	}
	
	/**
	 * Method to set an image and repaint it in the GUI.
	 * @param i, the image to be redefined. 
	 */
	public void setImage(Image i){ 
	    image = i;
	    this.repaint();
    }
	
	/**
	 * It clears the list of onlineUsers and then readds all users that are currently online, except for
	 * one self. 
	 * 
	 * @param users, an array of Strings provided by the user representing who is online
	 */
	public void updateOnlineUsers(String[] users){
		onlineData.clear();           
		for (String username:users){
		    if(!username.equals(user.getUserID())){
			onlineData.addElement(username);
		    }
		}
	}
	
	/**
	 * Adds user to JList recentHistory.
	 * 
	 * @param user, a String representing the user and a conversation ID passed in by the User.class 
	 */
	public void updateRecentTalks(String user){
	    recentHistory.addElement(user);
	}
	
	/**
	 * Removes the simple conversation recentHistory entry from the list(when it becomes a Group)
	 * @param otherUser	User that is currently in that conversation
	 * @param oldConvID	The conversation ID
	 */
	public void removeRecentTalks(String otherUser, String oldConvID){
		String entry = otherUser + " " + oldConvID;
		recentHistory.removeElement(entry);
	}
}
	
//    TO DO: Delete. 
//    public static void main(final String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    User user = new User(null, "new User");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
