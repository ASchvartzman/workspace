package UI;


import static org.junit.Assert.*;

import java.io.IOException;

import model.User;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
//import org.fest.swing.edt.GuiActionRunner;
//import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.junit.Before;
import org.junit.Test;
//import org.fest.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import server.IMServer;

/**
 * Testing Strategy
 * 
 * Simple test case using FEST that checks functionality of buttons and textfields that don't rely
 * on the server or the user. This test tries to type in the user textfield, the addUser textfield, checks
 * that the name of the other user is on the list and tests the buttons on the window. 
 * 
 * @category no_didit
 */


public class ConversationWindowTest {

    private FrameFixture window;
    ConversationWindow frame; 
    User user;
    
    @BeforeClass 
    public void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }
    
    @BeforeClass
    public void startServer(){
        new Thread(new Runnable() {
            public void run() {
                try {
                    IMServer server = new IMServer(4444);
                    server.runServer();   
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                finally{
                    
                }
            }
        }).start();
        
    }
    
    @Before
    public void setUp() throws IOException {
        user = new User(null, "arielsc");
        frame = new ConversationWindow("arielsc", new User(null, "ariel"), "simple1234");
        window = new FrameFixture(frame);
        frame.setVisible(true);
        window.show(); // shows the frame to test
    }
    
    @AfterMethod 
    public void tearDown() {
        window.cleanUp();
    }
    
    @Test
    public void conversationWindowTest(){
        String[] users = window.list("onlineUsers").contents();
        String[] expectedUsers = {"arielsc"};
        assertEquals(users[0], expectedUsers[0]);
        //window.textBox("chatHistory").requireNotEditable();
        window.textBox("input").enterText("This is a test message");
        window.textBox("addThisPerson").enterText("someUser");
        //window.button("addUser").requireEnabled();
        window.button("Leave").click();
        tearDown();
        
    }
}