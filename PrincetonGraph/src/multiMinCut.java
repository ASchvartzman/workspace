import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public class multiMinCut {
    FlowNetwork G; 
    HashSet<Integer> terminals = new HashSet<Integer>();
    String sourceFile;
    boolean cap = false;
    HashSet<ArrayList<Integer>> cutSets = new HashSet<ArrayList<Integer>>();
    HashSet<Double> cutValues = new HashSet<Double>();
    ArrayList<Integer> representatives = new ArrayList<Integer>();
    boolean directed = false; 
    
    public multiMinCut(FlowNetwork G, HashSet<Integer> terminals, String sourceFile){
        this.G = G;
        this.terminals = terminals; 
        this.sourceFile = sourceFile;
    }
    
    public multiMinCut(FlowNetwork G, HashSet<Integer> terminals, String sourceFile, boolean cap){
        this.G = G;
        this.terminals = terminals; 
        this.sourceFile = sourceFile;
        this.cap = cap;
    }
    
    //from rossetta code? 
    public static <T> List<List<T>> powerset(Collection<T> list) {
        List<List<T>> ps = new ArrayList<List<T>>();
        ps.add(new ArrayList<T>());   // add the empty set
       
        // for every item in the original list
        for (T item : list) {
          List<List<T>> newPs = new ArrayList<List<T>>();
       
          for (List<T> subset : ps) {
            // copy all of the current powerset's subsets
            newPs.add(subset);
       
            // plus the subsets appended with the current item
            List<T> newSubset = new ArrayList<T>(subset);
            newSubset.add(item);
            newPs.add(newSubset);
          }
       
          // powerset is now powerset of list.subList(0, list.indexOf(item)+1)
          ps = newPs;
        }
        //ps.remove(new ArrayList<T>());
        return ps;
      }
    
    public static <T> List<List<T>> powerset2(Collection<T> list) {
        List<List<T>> ps = new ArrayList<List<T>>();
        ps.add(new ArrayList<T>());   // add the empty set
       
        // for every item in the original list
        for (T item : list) {
          List<List<T>> newPs = new ArrayList<List<T>>();
       
          for (List<T> subset : ps) {
            // copy all of the current powerset's subsets
            newPs.add(subset);
       
            // plus the subsets appended with the current item
            List<T> newSubset = new ArrayList<T>(subset);
            newSubset.add(item);
            if(newSubset.size() <= 2 && newSubset.size() >= 1){
                newPs.add(newSubset);
            }
          }
       
          // powerset is now powerset of list.subList(0, list.indexOf(item)+1)
          ps = newPs;
        }
        return ps;
      }
        
    public static String readFile(File file) throws IOException {
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
    
    /**
     * Given a partition of terminalSet, returns the FordFulkerson graph that represents the mincut of the partition.
     * @throws IOException
     */
    public FordFulkerson multiMinCuts(List<Integer> partition) throws IOException{
        FordFulkerson maxflow;
        FlowNetwork myNetwork = new FlowNetwork(this.G.V() + 2);
        String largeGraph = readFile(new File(this.sourceFile));
        String[] edgeLines = largeGraph.split("\\r?\\n");
        for(String line: edgeLines){
            String[] values = line.split("\t");
            int first = Integer.parseInt(values[0]);
            int second = Integer.parseInt(values[1]);
            double d = 1.0
              //     + 1/(first+1.0) + 1/(second+1.0);
            + 1.0/(Math.pow(2.0, 1.0 + first))
            + 1.0/(Math.pow(2.0, 1.0 + second));
            //System.out.println(d);
            if(this.cap == true){
                d = Double.parseDouble(values[2]);
            }
            myNetwork.addEdge(new FlowEdge(first, second, d));
            //makes the graphs undirected.
            myNetwork.addEdge(new FlowEdge(second, first, d));
        }            
        for(int i: partition){
            myNetwork.addEdge(new FlowEdge(this.G.V(), i, Double.POSITIVE_INFINITY));
        }
        for(int i: findCompliment(this.terminals, partition)){
            myNetwork.addEdge(new FlowEdge(i, this.G.V() + 1, Double.POSITIVE_INFINITY));
        }            
        maxflow = new FordFulkerson(myNetwork, this.G.V(), this.G.V() + 1);
        //System.out.println(maxflow.value() + " for " + partition );
        return maxflow;
    }
    
    public FordFulkerson multiMinCuts2(List<Integer> partition, List<Integer> partition2) throws IOException{
        List<Integer> intersection = partition2; 
        intersection.removeAll(partition);
        if(intersection.size() == partition2.size() && partition2.size() == partition.size() && partition.size() == 2){
            //System.out.println(partition2 + ", " + partition);
            FordFulkerson maxflow = null;
            //System.out.println(partition + " " + partition2);
            FlowNetwork myNetwork = new FlowNetwork(this.G.V() + 2);
            String largeGraph = readFile(new File(this.sourceFile));
            String[] edgeLines = largeGraph.split("\\r?\\n");
            for(String line: edgeLines){
                String[] values = line.split("\t");
                int first = Integer.parseInt(values[0]);
                int second = Integer.parseInt(values[1]);
                double d = 1.0 + 
                       (first+second);
                      // 1.0/(Math.pow(2.0, first)) 
                      //  + 1.0/(Math.pow(2.0, second));
                //+ 1.0/(first+1.0) + 1.0/(second + 1.0);
                if(this.cap == true){
                    d = Double.parseDouble(values[2]) + 1;
                }
                myNetwork.addEdge(new FlowEdge(first, second, d));
                //makes the graphs undirected.
                myNetwork.addEdge(new FlowEdge(second, first, d));
            }            
            for(int i: partition){
                myNetwork.addEdge(new FlowEdge(this.G.V(), i, Double.POSITIVE_INFINITY));
            }
            for(int i: partition2){
                myNetwork.addEdge(new FlowEdge(i, this.G.V() + 1, Double.POSITIVE_INFINITY));
            }            
            maxflow = new FordFulkerson(myNetwork, this.G.V(), this.G.V() + 1);
            //System.out.println(maxflow.value() + " for " + partition );
            cutValues.add(maxflow.value());
            return maxflow;
        }
        return null;
        
    }
    
    /**
     * Finds all 2^{k} cutsets of a graph that separate its k terminals. 
     * In order to do this, it calls multiMinCut on all partitions of the terminalSet.   
     * @return an ArrayList<ArrayList<Integer>> representing the list of lists of vertices in each cutset. 
     * @throws IOException
     */
    public ArrayList<ArrayList<Integer>> findAllCutSets() throws IOException{
        ArrayList<ArrayList<Integer>> setOfCuts = new ArrayList<ArrayList<Integer>>();
        for(List<Integer> terminalSet: powerset(this.terminals)){
            FordFulkerson maxflow = this.multiMinCuts(terminalSet);  
            ArrayList<Integer> cutSet = new ArrayList<Integer>();
            for (int v = 0; v < this.G.V(); v++) {
                if (maxflow.inCut(v)){
                    cutSet.add(v);
                }
            }
            setOfCuts.add(cutSet);
            cutSets.add(cutSet);
        }
        return setOfCuts;
    }

    public ArrayList<ArrayList<Integer>> findAllCutSets2() throws IOException{
        ArrayList<ArrayList<Integer>> setOfCuts = new ArrayList<ArrayList<Integer>>();
        for(List<Integer> terminalSet: powerset2(this.terminals)){
            for(List<Integer> terminalSet2: powerset2(this.terminals)){
                FordFulkerson maxflow = this.multiMinCuts2(terminalSet, terminalSet2);  
                if(maxflow != null){
                ArrayList<Integer> cutSet = new ArrayList<Integer>();
                for (int v = 0; v < this.G.V(); v++) {
                    if (maxflow.inCut(v)){
                        cutSet.add(v);
                    }
                }
                setOfCuts.add(cutSet);
                cutSets.add(cutSet);
                }
            }
        }
        return setOfCuts;
    }
    
    /*
     * Given a set set and a subset terminalSet \subsetof set, returns the complement of terminalSet wrt set. 
     */
    public HashSet<Integer> findCompliment(HashSet<Integer> set, List<Integer> terminalSet){
        HashSet<Integer> compliment = new HashSet<Integer>();
        for(int i: set){
            if(!terminalSet.contains(i)){
                compliment.add(i);  
            }
        }
        return compliment; 
    }
    
    /*
     * Simple method that returns true if {a, b} \cap set = \emptyset or {a,b} \subsetof  set, and false otherwise. 
     */
    public boolean twoElementIntersection(ArrayList<Integer> set, int a, int b){
        if(set.contains(a) && set.contains(b))          { return true  ;}
        else if(!set.contains(a) && !set.contains(b))   { return true  ;}
        else                                            { return false ;}
    }
   
    /**
     * Finds all equivalence classes on the graph, as defined by Hagerup et al. 
     * @return a HashSet of elements that represent each of the equivalence classes found. 
     * @throws IOException
     */
    public ArrayList<HashSet<Integer>> equivalenceClasses() throws IOException{
        ArrayList<HashSet<Integer>> equivalences = new ArrayList<HashSet<Integer>>();
        ArrayList<ArrayList<Integer>> setOfCuts = findAllCutSets();
        HashSet<Integer> vertexSet = new HashSet<Integer>(this.G.V());
        for(int i = 0; i < this.G.V(); i++){
            if(!vertexSet.contains(i)){
                //StdOut.println(i);
                HashSet<Integer> classes = new HashSet<Integer>();
                vertexSet.add(i);
                classes.add(i);
                for(int j = i+1; j < this.G.V(); j++){
                    boolean flag = false;
                    for(ArrayList<Integer> cutSet: setOfCuts){
                        if(!twoElementIntersection(cutSet, i, j)){
                            flag = true;
                            break;
                        }
                    }
                    if(flag == false){
                        vertexSet.add(j);
                        classes.add(j);
                    }
                }
                equivalences.add(classes);
                //System.out.println(classes);
            }
        }
        return equivalences;
    }
    
    public ArrayList<HashSet<Integer>> equivalenceClasses2() throws IOException{
        ArrayList<HashSet<Integer>> equivalences = new ArrayList<HashSet<Integer>>();
        ArrayList<ArrayList<Integer>> setOfCuts = findAllCutSets2();
        HashSet<Integer> vertexSet = new HashSet<Integer>(this.G.V());
        for(int i = 0; i < this.G.V(); i++){
            if(!vertexSet.contains(i)){
                HashSet<Integer> classes = new HashSet<Integer>();
                vertexSet.add(i);
                classes.add(i);
                for(int j = i+1; j < this.G.V(); j++){
                    boolean flag = false;
                    for(ArrayList<Integer> cutSet: setOfCuts){
                        if(!twoElementIntersection(cutSet, i, j)){
                            flag = true;
                            break;
                        }
                    }
                    if(flag == false){
                        vertexSet.add(j);
                        classes.add(j);
                    }
                }
                equivalences.add(classes);
            }
        }
        return equivalences;
    }

    
    public HashSet<Integer> representatives() throws IOException{
        HashSet<Integer> rep = new HashSet<Integer>();
        int first; 
        ArrayList<HashSet<Integer>> equivalences = this.equivalenceClasses();
        for(HashSet<Integer> equivalence: equivalences){
            first = this.getRep(equivalence);
            rep.add(first);
            this.representatives.add(first);
        }
        
        //System.out.println(this.representatives);
        return rep;
    }
    
    public HashSet<Integer> representatives2() throws IOException{
        HashSet<Integer> rep = new HashSet<Integer>();
        int first; 
        ArrayList<HashSet<Integer>> equivalences = this.equivalenceClasses2();
        for(HashSet<Integer> equivalence: equivalences){
            first = this.getRep(equivalence);
            rep.add(first);
            this.representatives.add(first);
        }
        
        //System.out.println(this.representatives);
        return rep;
    }
    
    public int getRep(HashSet<Integer> set){
        HashSet<Integer> intersection = new HashSet<Integer>(set);
        intersection.retainAll(this.terminals);
        int first; 
        if(intersection.size() > 0){
            first = intersection.iterator().next();
            return first;
        }
        else{
            first = set.iterator().next();
            return first;
        }  
    }
    
    public double compareClasses(HashSet<Integer> setOne, HashSet<Integer> setTwo) throws IOException{
        String largeGraph = readFile(new File(this.sourceFile));
        double capacity = 0.0;
        String[] edgeLines = largeGraph.split("\\r?\\n");
        for(String line: edgeLines){
            String[] values = line.split("\t");
            int first = Integer.parseInt(values[0]);
            int second = Integer.parseInt(values[1]);
            if(setOne.contains(first) && setTwo.contains(second)){
                if(this.cap == true){
                    capacity += Double.parseDouble(values[2]);
                }
                else{
                capacity += 1.0;
                //+ 1.0/(first+1.0) + 1.0/(second + 1.0);                   
                //+ 1.0/Math.pow(2.0, second+1);
                //System.out.println(first + " " + second);
                }
            }
        }
        //System.out.println(setOne + " to " + setTwo + " " + capacity );
        
        return capacity;
    }
    
    public FlowNetwork mimickingNetwork() throws IOException{
        ArrayList<HashSet<Integer>> equivalences = this.equivalenceClasses();
        FlowNetwork myNetwork = new FlowNetwork(this.G.V() + 2);
        PrintWriter writer = new PrintWriter("mimicking" + this.sourceFile, "UTF-8");
        for(HashSet<Integer> setOne: equivalences){
            for(HashSet<Integer> setTwo: equivalences){
                if(!setOne.equals(setTwo)){
                    double capacity = compareClasses(setOne, setTwo);
                    int repOne = this.getRep(setOne);
                    int repTwo = this.getRep(setTwo);
                    if(capacity != 0){
                        myNetwork.addEdge(new FlowEdge(repOne, repTwo, capacity));
                    //makes graph undirected
                    //myNetwork.addEdge(new FlowEdge(repTwo, repOne, capacity));
                        writer.write(repOne + "\t" + repTwo + "\t" + capacity + "\n");
                    //writer.write(repTwo + "\t" + repOne + "\t" + capacity + "\n");
                    //System.out.println(repOne + "\t" + repTwo + "\t" + capacity);
                    }
                }
            }
        }
        writer.close();
        return myNetwork; 
    }
    
    public FlowNetwork mimickingNetwork2() throws IOException{
        ArrayList<HashSet<Integer>> equivalences = this.equivalenceClasses2();
        FlowNetwork myNetwork = new FlowNetwork(this.G.V() + 2);
        PrintWriter writer = new PrintWriter("mimicking2" + this.sourceFile, "UTF-8");
        for(HashSet<Integer> setOne: equivalences){
            for(HashSet<Integer> setTwo: equivalences){
                if(!setOne.equals(setTwo)){
                    double capacity = compareClasses(setOne, setTwo);
                    int repOne = this.getRep(setOne);
                    int repTwo = this.getRep(setTwo);
                    if(capacity != 0){
                        myNetwork.addEdge(new FlowEdge(repOne, repTwo, capacity));
                    //makes graph undirected
//                    myNetwork.addEdge(new FlowEdge(repTwo, repOne, capacity));
                        writer.write(repOne + "\t" + repTwo + "\t" + capacity + "\n");
                        //writer.write(repTwo + "\t" + repOne + "\t" + capacity + "\n");
                    //System.out.println(repOne + "\t" + repTwo + "\t" + capacity);
                    }
                }
            }
        }
        writer.close();
        return myNetwork; 
    }
    
    public boolean compareNetworks() throws IOException{
        FlowNetwork mimickFlow = this.mimickingNetwork(); 
        multiMinCut mimicking = new multiMinCut(mimickFlow, this.terminals, "mimicking" + this.sourceFile, true);
        for(List<Integer> terminalSet: powerset(this.terminals)){
            if(Math.abs(mimicking.multiMinCuts(terminalSet).value() - this.multiMinCuts(terminalSet).value()) >= 0.01){
                System.out.println(terminalSet);
                System.out.println(mimicking.multiMinCuts(terminalSet).value() + " " + this.multiMinCuts(terminalSet).value());
                return false;
            }
        }
        return true;
    }

    public boolean compareNetworks2() throws IOException{
        FlowNetwork mimickFlow = this.mimickingNetwork2(); 
        multiMinCut mimicking = new multiMinCut(mimickFlow, this.terminals, "mimicking2" + this.sourceFile, true);
        for(List<Integer> terminalSet: powerset2(this.terminals)){
            if(Math.abs(mimicking.multiMinCuts(terminalSet).value() - this.multiMinCuts(terminalSet).value()) >= 0.01){
                System.out.println(terminalSet);
                System.out.println(mimicking.multiMinCuts(terminalSet).value() + " " + this.multiMinCuts(terminalSet).value());
                return false;
            }
        }
        return true;
    }
    
    public double averageDegreeOfMimick() throws IOException{
        return ((double) count("mimicking" + this.sourceFile))/(this.representatives.size());            
    }
    
    //Stack Overflow
    public int count(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
        }
    }
    
    public static void main(String[] args) throws IOException{
        HashSet<Integer> myList = new HashSet<Integer>();
        int max = 25; 
        String source = "myGrid5";
   
//            Random Generator.
            for(int i = 0; i < 25; i++){
                //double d = Math.random();
                //System.out.println(d);
                myList.add(i);
//                myList.add((int) (d*((max))));
            }
  
            List<List<Integer>> myPowerSet = powerset2(myList);
            System.out.println(myPowerSet);
            
            FlowNetwork myNetwork = new FlowNetwork(max);
            
            multiMinCut myMultiMinCut = new multiMinCut(myNetwork, myList,source + ".txt", false);
            myMultiMinCut.mimickingNetwork2();
            
            
//            File log = new File(source + "results");
//            PrintWriter out = new PrintWriter(new FileWriter(log, true));
            System.out.println("Testing " + myList);
            //System.out.println(myMultiMinCut.representatives2().size());
//            System.out.println(myMultiMinCut.cutSets);
//           for(ArrayList<Integer> item:myMultiMinCut.cutSets){
//               System.out.println(item);
//           }
//            System.out.println(myMultiMinCut.compareNetworks2());
            System.out.println(myMultiMinCut.cutValues);
            System.out.println(myMultiMinCut.cutValues.size());
//            out.println(myMultiMinCut.averageDegreeOfMimick());
//            out.close();
           
        System.out.println("blarg");
    }
}   
   
