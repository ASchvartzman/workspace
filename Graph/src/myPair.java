/**
 * 
 * Created this class to represent points in the grid. The parameters should be integers, where
 * the first coordinate represents the column and the second represents the row. 
 * 
 * (I would have used the Pair<,> class, but apparently it was off limits)
 * 
 * @author arielschvartzman
 *
 */

public class myPair{
    public Node fst;//first member of pair
    public Node snd;//second member of pair

    public myPair(Node first, Node second){
      this.fst = first;
      this.snd = second;
    }
}