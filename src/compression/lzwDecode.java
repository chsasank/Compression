package compression;
import java.util.ArrayList;
/**
 * This class contains methods to uncompress 'on the fly'
 * It maintains a dictionary of previously encountered strings.
 * @author Sasank Chilamkurthy
 */

public class lzwDecode {
    private final ArrayList table;  //dictionary
    private String buffer = "";
    private int currentExpectedLength;
    
    /**
     * Constructor. Initializes dictionary to contain all one length strings
     */
    public lzwDecode(){
        //same initilization as lzwEncode

        table = new ArrayList();
        
        //insert all one length string in table
        //i,e characters of Ascii values 0-127
        for (int i = 0; i < 128 ; i++){
            table.add(String.valueOf(Character.toChars(i)));
        }
        table.add("");//insert null at the end, just to be sure!
        currentExpectedLength = 2;
        //System.out.println(table);
    }
    
    /**
     * Takes in character c to decompress. Output null if you have to send more characters to get output
     * outputs decompressed string 
     * @param c
     * @return
     */
    public String in(char c){
        
        try{
        buffer = buffer+c;
        if(buffer.length()<currentExpectedLength+1){
            return null;
        }
        if(buffer.length() == currentExpectedLength+1){
            //this 'index' was in 128 base before using toInt method
            int index = toInt(buffer);
            String out = (String) table.get(index) + buffer.charAt(buffer.length()-1);
            table.add(out);     //add this to dictionary
            //update current expected length to reflect new string placed in dictionary
            if(table.size() == 128*128-1) currentExpectedLength = 3;   
            else if(table.size() == 128*128*128-1) currentExpectedLength = 4;
            else if(table.size() == 128*128*128*128-1) currentExpectedLength = 5;            
            buffer = "";
            return out;
        }
        else{
            System.out.print("_");
            return null;
        }
        
        }
        
        catch(IndexOutOfBoundsException ex){
            //for debugging purposes
            System.out.println("Buffer - "+ buffer);
            System.out.println("Current Table Size- "+ table.size());
            System.out.println("CurrentExpectedLength - "+ currentExpectedLength);
            int index = toInt(buffer);
            System.out.println("Index calculated - "+ index);
            return "error";
        }
        
    }
    
    /**
     * Do this at the end of the file, so that buffer is emptied into file
     * @return
     */
    public String completed(){
        String temp = buffer;
        buffer = "";
        return temp;

    }
    
    //Converts string to int with bsae 128 system.
    private int toInt(String s) {
        String temp = s;
        int ans = 0;
        int power128 = 1;
        for(int i = 0; i < currentExpectedLength; i++){
            ans = ans + s.charAt(i)*power128;
            power128 = power128 * 128;
        }
        
        return ans;
    }
    
}
