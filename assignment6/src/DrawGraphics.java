import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Polygon;
import java.util.ArrayList;

public class DrawGraphics {
    BouncingBox box;
    Rectangle myRect; 
    Polygon myPolygon;
    ArrayList<BouncingBox> myBoxes = new ArrayList<BouncingBox>();
    
    /** Initializes this class for drawing. */
    public DrawGraphics() {
        box = new BouncingBox(200, 50, Color.RED);
        myRect = new Rectangle(10, 10, 30, 30);
        BouncingBox myBox1 = new BouncingBox(100, 30, Color.GRAY);
        BouncingBox myBox2 = new BouncingBox(250, 130, Color.BLUE);
        BouncingBox myBox3 = new BouncingBox(90, 150, Color.CYAN);
        myBoxes.add(myBox1);
        myBoxes.add(myBox2);
        myBoxes.add(myBox3);
    }

    /** Draw the contents of the window on surface. Called 20 times per second. */
    public void draw(Graphics surface) {
        surface.drawLine(50, 50, 250, 250);
        box.draw(surface);
        surface.fillRect(60 ,40 ,30, 40);
        surface.drawOval(200, 200, 50, 20);
        surface.drawArc(80, 200, 50, 45, 0, 135);
        int i = 0; 
        for(BouncingBox b : myBoxes){
        	b.draw(surface);
        	b.setMovementVector(-2*i + 4 , 3*i - 2);
        	i++; 
        }
    }
}