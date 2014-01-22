import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedWriter;

public class MagicSquares {
    public static boolean testMagic(String pathName) throws IOException {
        // Open the file
        BufferedReader reader = new BufferedReader(new FileReader(pathName));

        boolean isMagic = true;
        int lastSum = -1;
        
        // For each line in the file ...
        String line;
        while ((line = reader.readLine()) != null) {
            // ... sum each row of numbers
            String[] parts = line.split("\t");
            int sum = 0;
            for (String part : parts) {
                sum += Integer.parseInt(part);
            }

            if (lastSum == -1) {
                // If this is the first row, remember the sum
                lastSum = sum;
            } else if (lastSum != sum) {
                // if the sums don't match, it isn't magic, so stop reading
                isMagic = false;
                break;
            }
//            System.out.println(sum);
//            System.out.println(line);
        }
        reader.close();
        return isMagic;
    }
    

    public static boolean testMagicCols(String pathName) throws IOException {
        // Open the file
        BufferedReader reader = new BufferedReader(new FileReader(pathName));

        boolean isMagicCols = true;
        
        // So basically, what I'm doing here is just parsing the array into one big array...
        String line;
        //... which is called entries
        ArrayList<Integer> entries = new ArrayList<Integer>();
        while ((line = reader.readLine()) != null) {
            String[] parts = (line.split("\t"));
            for (String part : parts) {
				entries.add(Integer.parseInt(part));
            }
        }
        reader.close();
        
        
        // So, once I have entries, I can determine the size of the square
        int length = entries.size(); 
        int size = (int)Math.sqrt(length);
        //(initialize partial sums of all columns)
        int[] partialSums = new int[size];
        for(int i = 0; i < length ; i++){
        	//...and sort the entries according to their residue mod size! 
        	partialSums[i % size] += entries.get(i);
        }
        int constant = partialSums[0];
        // quick check that all elements are the same in the partialSums array
        for(int i = 1; i < partialSums.length; i++){
        	if(partialSums[i] != constant){
        		// if not, return false!
        		isMagicCols = false;
        		break;
        	} 
        }
     return isMagicCols;
    }

    public static boolean testMagicDiags(String pathName) throws IOException {
        // Open the file
        BufferedReader reader = new BufferedReader(new FileReader(pathName));
        
        String line;
        ArrayList<Integer> entries = new ArrayList<Integer>();
        while ((line = reader.readLine()) != null) {
            String[] parts = (line.split("\t"));
            for (String part : parts) {
				entries.add(Integer.parseInt(part));
            }
        }
        reader.close();
        // this is the same as above, just parsing the thing
        
        
        int length = entries.size(); 
        int size = (int)Math.sqrt(length);
        // initialize both sums
        int mainDiagonal = 0;
        int antiDiagonal = 0;
        for(int j = 0; j < size; j++){
        	for(int h = 0; h < size; h++){
        		if(h == j){
        			// h == j corresponds to the line through the main diagonal
        			mainDiagonal += entries.get(j*size + h);
        		}  
        		if(h + j == size-1){
        			//similarly, h+j == size -1 is the line through the anti-diagonal
        			// note that both cases can happen at the same time if the size is odd! diagonals intersect!
        			antiDiagonal += entries.get(size*j + h);	
        		}
        	}
        }
        //this is pretty obvious, i guess
        boolean isMagicDiags = (mainDiagonal == antiDiagonal); 

        return isMagicDiags;        
        
    }
    
    public static void main(String[] args) throws IOException {
        String[] fileNames = { "Mercury.txt", "Luna.txt" };
        for (String fileName : fileNames) {
            System.out.println(fileName + " is magic rows? " + testMagic(fileName));
        }
        for (String fileName : fileNames) {
            System.out.println(fileName + " is magic columns? " + testMagicCols(fileName));
        }
        for (String fileName : fileNames) {
            System.out.println(fileName + " is magic diagonals? " + testMagicDiags(fileName));
        }
    }
}
