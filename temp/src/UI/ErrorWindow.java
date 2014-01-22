package UI;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

public class ErrorWindow extends JFrame{
    JLabel errorMessage;
    
    public ErrorWindow(){
        errorMessage = new JLabel("An error occurred. Your request was not processed.");
        
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        //Horizontal Grouping of the Component(s)
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(errorMessage))
                );

        //Vertical Grouping of the Component(s)
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(errorMessage))
                );
        
        this.addWindowListener(new WindowListener(){
            @Override
            public void windowClosing(WindowEvent arg0) {
                setVisible(false);
                System.exit(0);
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
        pack();
        setTitle("Error!");
        setLayout(new BorderLayout());
        Insets insets = getInsets();
        this.setSize(insets.left + insets.right + 350, insets.bottom + insets.top + 25);
        this.setVisible(true);
        this.setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
    
    public void openWindow(){
        this.setVisible(true);
    }
    
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ErrorWindow main = new ErrorWindow();
                main.setVisible(true);
            }
        });
    } 
}
