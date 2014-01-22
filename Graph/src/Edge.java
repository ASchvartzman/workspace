
public class Edge {
    int lowerCapacity = 0;
    int upperCapacity = 1;
    myPair endpoints = new myPair(null, null);
    
    public Edge(Node first, Node second, int upperCapacity){
        this.endpoints.fst = first;
        this.endpoints.snd = second;
        this.upperCapacity = upperCapacity;
    }
    
    public Edge(Node first, Node second, int upperCapacity, int lowerCapacity){
        this.endpoints.fst = first;
        this.endpoints.snd = second;
        this.upperCapacity = upperCapacity;
        this.lowerCapacity = lowerCapacity;
    }
}
