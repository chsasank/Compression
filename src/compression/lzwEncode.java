package compression;

import java.util.HashMap;
/**
 * Be careful! I'm inserting spaces at end!
 * @author Sasank Chilamkurthy
 */
public class lzwEncode {
    private final HashMap table;    //Dictionary will be strored in hashmap
    private int currentIndex;
    private String buffer = "";
    private int currentExpectedLength;
   
    /**
     * Constructor
     * Insert all one length strings into dictionary for initialization
     */
    public lzwEncode(){
        currentIndex = -1;
        table = new HashMap();
        
        //insert all one length string in table
        for (int i = 0; i < 128 ; i++){
            currentIndex += 1;
            table.put(String.valueOf(Character.toChars(i)), currentIndex);
        }
        currentIndex+=1;
        table.put("",currentIndex);
        currentExpectedLength = 2;
    }
    
    /**
     * Takes in character c to compress. Output null if you have to send more characters to get output
     * outputs compressed string 
     * @param c
     * @return
     */
    public String in(char c){
        buffer = buffer+c;
        if(table.containsKey(buffer)){
            //If this buffer is in dictionary, ask to send in more characters.
            return null;
        }
        else{
            currentIndex += 1;
            table.put(buffer, currentIndex);//if not found in table, put buffer in the table
            Object index = table.get(buffer.substring(0, buffer.length() - 1));//Note that buffer[0:N-1] is in dictionary
            buffer = "";                    //empty buffer
            //update current expected length to reflect new string placed in dictionary
            if(table.size() == 128*128 ) currentExpectedLength = 3;   
            else if(table.size() == 128*128*128) currentExpectedLength = 4;
            else if(table.size() == 128*128*128*128) currentExpectedLength = 5;
            
            return toString((int)index)+ c;// '('+index +')'+ c + '|';//(debug text)
        }
    }
    
    //Converts your index to base 128 sytem and send currentexpectedlength characters.
    private String toString(int id){
        String s = "";
        int temp = id;
        int i = currentExpectedLength;
        while(i !=0 ){
            s = s+ String.valueOf(Character.toChars(temp%128));
            temp = temp/128;
            i--;
        }
        return s; // String.valueOf(id);
    }
    
    /**
     * You have to do this at the end of the file, so that whatever character which already are in buffer are returned back
     * @return
     */
    public String completed(){
        //we shall insert spaces into buffer until 'in' method returns something other than null.
        //It is turninhg out that returning buffer is creating lots of problems        
        if (buffer.equals("")) {
            return "";
        }
        // if buffer is not empty, you have to insert ' ' (space)
        // Can I do better than inserting spaces? Better
        else {
            String s;
            s = in(' ');
            while (s == null) {
                s = in(' ');
            }
            return s;
        }

    }
    
    public void print(){
        System.out.println(table);
    }
    
    
}
