// DE4C.java CS5125/6025 Cheng 2019
// checking primality of Group 5 q and (q-1)/2
// checking that 2 is a primitive element in GF(q) (a primitive root of q)
// Usage: java DE4C

import java.math.*;
import java.io.*;
import java.util.*;

public class DE4C{
  String hexQ = null;
  BigInteger q = null;
  BigInteger p = null;
  static BigInteger two = new BigInteger("2");

  void readQ(String filename){
    Scanner in = null;
    try {
     in = new Scanner(new File(filename));
    } catch (FileNotFoundException e){
      System.err.println(filename + " not found");
      System.exit(1);
    }
    hexQ = in.nextLine();
    in.close();
    q = new BigInteger(hexQ, 16);
  }

 void testPrimality(){
   BigInteger subtracter = null;
   subtracter = new BigInteger("1");
   BigInteger divider = null;
   divider = new BigInteger("2");
   if (q.isProbablePrime(200)) 
    System.out.println("q is probably prime");
    p = q.subtract(subtracter);
    p = p.divide(divider);
   if (p.isProbablePrime(200)) 
    System.out.println("p is probably prime");
 }

 void testPrimitiveness(){
  // compute pow(2, p) mod q
  BigInteger big_power = null;
  big_power = new BigInteger("2");
  BigInteger p2q = big_power.modPow(p, q);
   System.out.println(p2q.toString(16));
 }

 public static void main(String[] args){
   DE4C de4 = new DE4C();
   de4.readQ("DHgroup5.txt");
   de4.testPrimality();
   de4.testPrimitiveness();
 }
}

   