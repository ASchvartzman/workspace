import java.util.ArrayList;

public class Node {
    int id; 
    boolean isTerminal; 
    ArrayList<Edge> neighboringEdges = new ArrayList<Edge>(); 
    
    public Node(int id, boolean isTerminal){
        this.id = id;
        this.isTerminal = isTerminal; 
    }
   
}
