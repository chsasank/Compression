
package compression;

import java.io.*;

/**
 * This class has main method. 
 * Can call methods lzCompress and lzUncompress to compress and uncompress files respectively
 * Depends on classes lzwEncode and lzwDecode
 * @author Sasank Chilamkurthy
 */
public class Compression {


    public static void main(String[] args) {
        // TODO code application logic here
        //lzCompress("big.txt");
        lzUncompress("big.txt.lz");
        
      }

    /**
     * Uncompresses text file which is in lz format
     * @param fileName
     */
    public static void lzUncompress(String fileName){
        lzwDecode foo = new lzwDecode();
        
        try {
            PrintWriter fo = new PrintWriter(new BufferedWriter(new FileWriter("uncompressed_"+fileName.substring(0,fileName.length()-3), true)));
            FileReader fr = new FileReader(fileName);
            int k;
            String writeBuffer = "";//so that I need not write everything at once. Will write when length of this>1000
            while ((k = fr.read()) != -1) { //read input file from fr.
                String out = foo.in((char) k); //send input character to lz to encode
                if (out != null) {
                    writeBuffer = writeBuffer+out;
                    if(writeBuffer.length()>100){
                        fo.print(writeBuffer); //write if len>1000
                        writeBuffer = ""; //then empty buffer
                    }
                }
            }
            fo.print(writeBuffer);//+out);//write remaining buffer
            //fo.print(foo.completed());//do final steps
            fr.close();
            fo.close();

        }
        
        catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '"
                    + fileName + "'");
        }
        catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                    + fileName + "'");
            // Or we could just do this: 
            // ex.printStackTrace();
        }

    }
    /**
     * Compresses file with fileName using LZ scheme
     * @param fileName
     */
    public static void lzCompress(String fileName){
        lzwEncode foo = new lzwEncode();

        try {
            PrintWriter fo = new PrintWriter(new BufferedWriter(new FileWriter(fileName+".lz", true)));
            FileReader fr = new FileReader(fileName);
            int k;
            String writeBuffer = "";            //so that I need not write everything at once. Will write when length of this>1000
            while ((k = fr.read()) != -1) {     //read input file from fr.
                String out = foo.in((char) k);  //send input character to lz to encode
                if (out != null) {  
                    writeBuffer = writeBuffer+out;
                    if(writeBuffer.length()>1000){
                        fo.print(writeBuffer);  //write if len>1000
                        writeBuffer = "";       //then reset buffer
                    }
                }
            }
            fo.print(writeBuffer);//write remaining write buffer
            fo.print(foo.completed());//do final steps
            fr.close();
            fo.close();

        }
        
        catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '"
                    + fileName + "'");
        } 
        catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                    + fileName + "'");
            // Or we could just do this: 
            // ex.printStackTrace();
        }
    }
}
