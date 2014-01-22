package UI;


import org.junit.Before;
import org.junit.Test;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
//Don't delete these
//import org.fest.assertions.*;
//import org.fest.util.*;

import server.IMServer;

/**
 * Testing Strategy
 * 
 * Simple test case using FEST that checks functionality of buttons and textfields that don't rely
 * on the server or the user. This test opens a window, tries to insert an IP, a user name and click the 
 * submit button. It also checks that the port number is defaulted to 4444.
 * 
 * @category no_didit
 */

public class LoginWindowTest {
        
    private FrameFixture window;
    LoginWindow frame; 
    
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
    public void setUp() {
        LoginWindow frame = GuiActionRunner.execute(new GuiQuery<LoginWindow>() {
            protected LoginWindow executeInEDT() {
                return new LoginWindow();  
            }
        });
        frame = new LoginWindow();
        window = new FrameFixture(frame);
        frame.setVisible(true);
        window.show();
    }
    
    @AfterMethod 
    public void tearDown() {
        window.cleanUp();
    }
    
    @Test 
    public void loginTest() {
        
        window.textBox("ip").enterText("18.189.68.232");
        window.textBox("port").requireText("4444");
        window.textBox("username").enterText("arielsc");
        window.button("Submit").click();
        tearDown();
    }
    
    
    //This should throw a pop up window on the *server* side, not on the UI.
    //As far as the UI is concerned, this is still a valid input. 
    @Test
    public void loginFailTest(){
        window.textBox("ip").enterText("18.189.68.232");
        window.textBox("port").requireText("4444");
        window.textBox("username").enterText("ari lsc");
        window.button("Submit").click();
        tearDown();
    }
}


