import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;


public class Graph {
    ArrayList<Node> nodeList = new ArrayList<Node>();
    ArrayList<Edge> edgeList = new ArrayList<Edge>();

    public Graph(ArrayList<Node> nodeList, ArrayList<Edge> edgeList){
        this.nodeList = nodeList;
        this.edgeList = edgeList; 
    }
    
    public Graph(String file){
        String[] lines = file.split("\n");
        for(String line: lines){
            if(line.startsWith("#")){
                continue;
            }
            else{
                Node newFirst;
                Node newSecond;
                String[] nodePair = line.split("\t");
                int first = Integer.parseInt(nodePair[0]);
                int second = Integer.parseInt(nodePair[1]);
                if(!nodeExists(first)){
                    newFirst = new Node(first, false);
                }
                else{
                    newFirst = getNode(first);
                }
                if(!nodeExists(second)){
                    newSecond = new Node(second, false);
                }
                else{
                    newSecond = getNode(second);
                }
                if(edgeExists(newFirst, newSecond)){
                    continue;
                }
                else{
                    createEdge(newFirst, newSecond);
                }
            }
        }
    }
    
    public void createNode(int id, boolean isTerminal){
        Node newNode = new Node(id, isTerminal);
        this.nodeList.add(newNode);
    }
    
    public void createEdge(Node first, Node second){
        Edge newEdge = new Edge(first, second, 1);
        this.edgeList.add(newEdge);
        first.neighboringEdges.add(newEdge);
        second.neighboringEdges.add(newEdge);
    }
    
    public void removeNode(Node node){
        for(Edge edge: node.neighboringEdges){
            removeEdge(edge);
        }
        this.nodeList.remove(node);
    }
    
    public void removeEdge(Edge edge){
       this.edgeList.remove(edge); 
    }
    
    public boolean nodeExists(int id){
        for(Node node: this.nodeList){
            if(node.id == id){
                return true; 
            }
        }
        return false; 
    }
    
    public boolean edgeExists(Node first, Node second){
        for(Edge edge: this.edgeList){
            if (edge.endpoints.fst.equals(first) && edge.endpoints.snd.equals(second)){
                return true;
            }
            if (edge.endpoints.fst.equals(second) && edge.endpoints.snd.equals(first)){
                return true;
            }
        }
        return false;
    }
    
    public Node getNode(int id){
        for(Node node: this.nodeList){
            if(node.id == id){
                return node; 
            }
        }
        return null; 
    }
    
    private static String readFile(File file) throws IOException {
        FileInputStream stream = new FileInputStream(file);

        try {
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            /* Instead of using default, pass in a decoder. */
            return Charset.defaultCharset().decode(bb).toString();
        }
        finally {
            stream.close();
        }
    }
    
    public static void main (String[] arguments) throws IOException {
        Node node1 = new Node(1, false);
        Node node2 = new Node(1, false);
        Node node3 = new Node(1, false);
        Node node4 = new Node(1, false);
        Node node5 = new Node(1, false);
        
        Edge edge = new Edge(node1, node2, 1);
        Edge edge1 = new Edge(node3, node2, 1);
        Edge edge2 = new Edge(node4, node1, 1);
        Edge edge3 = new Edge(node5, node3, 1);
        Edge edge4 = new Edge(node3, node1, 1);
        
        ArrayList<Node> myNodes = new ArrayList<Node>();
        myNodes.add(node1);
        myNodes.add(node2);
        myNodes.add(node3);
        myNodes.add(node4);
        myNodes.add(node5);
        
        ArrayList<Edge> myEdges = new ArrayList<Edge>();
        myEdges.add(edge1);
        myEdges.add(edge2);
        myEdges.add(edge3);
        myEdges.add(edge4);
        myEdges.add(edge);
        
        Graph myGraph = new Graph(myNodes, myEdges);
        
        System.out.println(myGraph.edgeList.toString());
        System.out.println(myGraph.nodeList.toString());
        
        String largeGraph = readFile(new File("reallyLarge.txt"));
        Graph myGraph2 = new Graph(largeGraph);
        
        System.out.println("Done");
    }
        
}

