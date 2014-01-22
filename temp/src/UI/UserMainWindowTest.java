package UI;


import java.io.IOException;

import model.User;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.junit.Before;
import org.junit.Test;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import server.IMServer;

/**
 * Testing Strategy
 * 
 * Simple test case using FEST that checks functionality of buttons and textfields that don't rely
 * on the server or the user. Also tests functionality of the add picture button, by means of a URL. 
 * 
 * @category no_didit
 */

public class UserMainWindowTest {

    private FrameFixture window;
    UserMainWindow frame; 
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
        UserMainWindow frame = GuiActionRunner.execute(new GuiQuery<UserMainWindow>() {
            protected UserMainWindow executeInEDT() throws IOException {
                return new UserMainWindow(new User(null, null));  
            }
        });
        user = new User(null, "arielsc");
        frame = new UserMainWindow(user);
        window = new FrameFixture(frame);
        frame.setVisible(true);
        //window.show(); // shows the frame to test
    }
    
    @AfterMethod 
    public void tearDown() {
        window.cleanUp();
    }
    
    @Test
    public void userMainWindowTest(){
        window.label("welcome").requireText("Welcome to MAD Chat!" + "\n");
        window.textBox("picPathOrURL").enterText("http://i1.kym-cdn.com/entries/icons/original/000/007/447/hello-yes-this-is-dog.png");
        window.button("changePic").click();
        window.label("rules").requireText("<html> Hello, arielsc! <br>" + 
                "In order to start a conversation,<br>" +
                "click on a users name on the list.<br><br>" +
                "You can also review recent conversations under<br>" +
                " the recent talks list. <br><br> " +
                "You may also choose your own profile picture, <br> " +
                "with either a URL or a pathname. <br><br> " +
                "Finally, to log off safely, hit the Log Off Button!" +
                "</html>");
        window.label("who's online").requireText("Who's Online?");
        window.label("recentChats").requireText("Recent Chats:");
        tearDown();
    }
}
