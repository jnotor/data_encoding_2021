// DE17B.java CS5125/6025 Cheng 2021
// LZW decoding
// without handling the special situation when codeword == dictionarySize - 1
// see lecture note 4/7/2021
// java DE17B < encoded > original

import java.io.*;
import java.util.*;

public class DE17B{

 static final int dictionaryCapacity = 4096;
 int[] prefix = new int[dictionaryCapacity];
 int[] lastSymbol = new int[dictionaryCapacity];
 int dictionarySize = 0;
 int firstSymbol = 0;

 void initializeDictionary(){
   for (int i = 0; i < 256; i++){
      prefix[i] = -1; lastSymbol[i] = i;
   }
   dictionarySize = 256;
 }

 void outputPhrase(int index){  // recursive
   if (index >= 0 && index < dictionarySize){ 
     outputPhrase(prefix[index]);
     System.out.write(lastSymbol[index]);
     if (index < 256) firstSymbol = lastSymbol[index];
   }
 }  

 void decode(){
   Scanner in = new Scanner(System.in);
   while (in.hasNextLine()){ //  Loop
     int codeword = Integer.parseInt(in.nextLine()); //read next codeword
     outputPhrase(codeword); //output the corresponding phrase in the dictionary using the codeword as the index
     if (dictionarySize < dictionaryCapacity){
      // fill the second component of the last phrase with the first byte of the phrase just output 
      lastSymbol[dictionarySize - 1] = firstSymbol; 
      prefix[dictionarySize++] = codeword; // fill the first component of the next entry with the codeword
     }
   } 
 }  

 public static void main(String[] args){
   DE17B de17 = new DE17B();
   de17.initializeDictionary();
   de17.decode();
 }
}

