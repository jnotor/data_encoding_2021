// DE12B.java CS5125/6025 Cheng 2021
// Huffman decoder
// Usage:  java DE12B < encoded > original

import java.io.*;
import java.util.*;

public class DE12B{

  int[][] codetree = null;
  int buf = 0; int position = 0;
  int actualNumberOfSymbols = 0;
  int filesize = 0;

 void readTree(){  // read Huffman tree
  try{
   actualNumberOfSymbols = System.in.read();
   codetree = new int[actualNumberOfSymbols * 2 - 1][2];
   for (int i = 0; i < actualNumberOfSymbols * 2 - 1; i++){
     codetree[i][0] = System.in.read();
     codetree[i][1] = System.in.read();
   }
   for (int i = 0; i < 3; i++){  // read filesize
     int a = System.in.read();
     filesize |= a << (i * 8);
   }
  } catch (IOException e){
     System.err.println(e);
     System.exit(1);
  }
 }

 int inputBit(){ // get one bit from System.in
   if (position == 0)
     try{
       buf = System.in.read();
       if (buf < 0) return -1;
       position = 0x80;
     }catch(IOException e){
        System.err.println(e);
        return -1;
     }
   int t = ((buf & position) == 0) ? 0 : 1;
   position >>= 1;  
   return t;
 }

 void decode(){  // Your two lines of code for updating k are needed for this to work.
  int bit = -1;   // next bit from compressed file: 0 or 1.  no more bit: -1
  int k = 0;  // index to the Huffman tree array; k = 0 is the root of tree
  int n = 0;  // number of symbols decoded, stop the while loop when n == filesize

  while ((bit = inputBit()) >= 0){
    /* my thoughts: if bit is zero, it should be left child, if 1 it should be 
       right child. child can be calculated as position * 2 + 1 for left, + another 1
       for right

       there are 255 elements in codetree, and there are only ever 2 elements at
       codetree[k]

      codetree is a 2D array [][] of ints

        for (int i=0; i<codetree.length; i++){
          System.out.print(codetree[i][0]);
          System.out.print("       length:" + codetree[i].length);
          System.out.println("          " + codetree[i][1]);
        }
    
    */
    // Your code: replace k by the index of a child according to bit (Walk down tree)
    k = k * 2 + 1; // left child

    if (bit == 0) { // right child
      k++; 
    }
    
    if (k >= codetree.length){ // bounds check
      k = 0;
    }
    
    // System.out.println(bit + "  " + k);
    if (codetree[k][0] == 0){  // leaf
       System.out.write(codetree[k][1]);
       if (n++ == filesize) break; // ignore any additional bits
       // Your code: restart for the next symbol by move to the root (Go up to root)
       k = 0;
      }
  }
  System.out.flush();
 }

 public static void main(String[] args){
  DE12B de12 = new DE12B();
  de12.readTree();
  de12.decode(); 
 }
}
