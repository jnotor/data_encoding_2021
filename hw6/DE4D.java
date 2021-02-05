// DE4D.java CS5125/6025 Cheng 2019
// Elliptic curve cryptography
// Usage: java DE4D ECP256.txt/secp256k1.txt
/* from assignment: DE4D is the elliptic curve program doing secret sharing and 
 encryption.  Complete the code and show the running of the program including 
 the outputs.*/

import java.math.*;
import java.io.*;
import java.util.*;

class Point {
   public BigInteger x;
   public BigInteger y;
   static Point O = new Point(null, null);

   public Point(BigInteger xx, BigInteger yy) {
      x = xx;
      y = yy;
   }

   public String toString() {
      return this.equals(O) ? "O" : "(" + x.toString(16) + ",\n" + y.toString(16) + ")";
   }
}

public class DE4D {

   static BigInteger three = new BigInteger("3");
   static final int privateKeySize = 255;
   BigInteger p; // modulus
   Point G; // base point
   BigInteger a; // curve parameter
   BigInteger b; // curve parameter
   BigInteger n; // order of G
   BigInteger privateKeyA;
   Point publicKeyA;
   BigInteger privateKeyB;
   Point publicKeyB;
   Random random = new Random();

   void readCurveSpecs(String filename) {
      Scanner in = null;
      try {
         in = new Scanner(new File(filename));
      } catch (FileNotFoundException e) {
         System.err.println(filename + " not found");
         System.exit(1);
      }
      p = new BigInteger(in.nextLine(), 16);
      n = new BigInteger(in.nextLine(), 16);
      a = new BigInteger(in.nextLine(), 16);
      b = new BigInteger(in.nextLine(), 16);
      G = new Point(new BigInteger(in.nextLine(), 16), new BigInteger(in.nextLine(), 16));
      in.close();
   }

   Point add(Point P1, Point P2) {
      if (P1.equals(Point.O))
         return P2;
      if (P2.equals(Point.O))
         return P1;
      if (P1.x.equals(P2.x))
         if (P1.y.equals(P2.y))
            return selfAdd(P1);
         else
            return Point.O;
      BigInteger t1 = P1.x.subtract(P2.x).mod(p);
      BigInteger t2 = P1.y.subtract(P2.y).mod(p);
      BigInteger k = t2.multiply(t1.modInverse(p)).mod(p); // slope
      t1 = k.multiply(k).subtract(P1.x).subtract(P2.x).mod(p); // x3
      t2 = P1.x.subtract(t1).multiply(k).subtract(P1.y).mod(p); // y3
      return new Point(t1, t2);
   }

   Point selfAdd(Point P) {
      if (P.equals(Point.O))
         return Point.O; // O+O=O
      BigInteger t1 = P.y.add(P.y).mod(p); // 2y
      BigInteger t2 = P.x.multiply(P.x).mod(p).multiply(three).add(a).mod(p); // 3xx+a
      BigInteger k = t2.multiply(t1.modInverse(p)).mod(p); // slope or tangent
      t1 = k.multiply(k).subtract(P.x).subtract(P.x).mod(p); // x3 = kk-x-x
      t2 = P.x.subtract(t1).multiply(k).subtract(P.y).mod(p); // y3 = k(x-x3)-y
      return new Point(t1, t2);
   }

   Point multiply(Point P, BigInteger n) {
      if (n.equals(BigInteger.ZERO))
         return Point.O;
      int len = n.bitLength(); // position preceding the most significant bit 1
      Point product = P;
      for (int i = len - 2; i >= 0; i--) {
         product = selfAdd(product);
         if (n.testBit(i))
            product = add(product, P);
      }
      return product;
   }

   void checkParameters() {
      // NOTE: not called
      BigInteger lhs = G.y.multiply(G.y).mod(p);
      BigInteger rhs = G.x.modPow(three, p).add(G.x.multiply(a).mod(p)).add(b).mod(p);
      // These two lines should be the same
      System.out.println("LHS:");
      System.out.println(lhs.toString(16));
      System.out.println(" should = RHS: ");
      System.out.println(rhs.toString(16)); 

      Point power = multiply(G, n);
      System.out.println("Power should be 0: ");
      System.out.println(power); // This should be O
   }

   void generateKeys() {
      privateKeyA = new BigInteger(privateKeySize, random);
      publicKeyA = multiply(G, privateKeyA);
      privateKeyB = new BigInteger(privateKeySize, random);
      publicKeyB = multiply(G, privateKeyB);
   }

   void sharedSecret() { 
      // Figure 10.7 of book
      // System.out.println("\nsecret computed by A: ");
      Point KA = multiply(publicKeyB, privateKeyA); 
      // System.out.println(KA);
      
      // System.out.println("\nsecret computed by B: ");
      Point KB = multiply(publicKeyA, privateKeyB);
      // System.out.println(KB);
   }

   void encryptionForB() { 
      // page 305 of book
      // Create Message
      BigInteger message = new BigInteger(privateKeySize, random);
      System.out.println("\nMessage is: ");
      System.out.println(message.toString(16));

      // Encrypt Message
      BigInteger k = new BigInteger(privateKeySize, random);
      Point kG = multiply(G, k); // k times point G
      Point kY = multiply(publicKeyB, k); // k times point publicKeyB
      BigInteger mu = message.multiply(kY.x).mod(p); // message times kY.x mod p
      // BigInteger mu = message.multiply(kY.x.mod(p)); // message times kY.x mod p
      // BigInteger mu = kY.x.mod(p).multiply(message); // message times kY.x mod p

      // NOTE: (kG, mu) is the encrypted message
      
      // System.out.println("\nkG: ");
      // System.out.println(kG);
      // System.out.println("\nmu: ");
      // System.out.println(mu.toString(16));
      
      // Decrypt Message
      Point kY2 = multiply(kG, privateKeyB); // B computes kY as privateKeyB times kG
      // BigInteger decodedMessage  = mu.multiply(kY2.x.modInverse(p)); // kY2.x modinverse times mu mod p
      // BigInteger decodedMessage  = kY2.x.modInverse(p).multiply(mu); // kY2.x modinverse times mu mod p
      // BigInteger decodedMessage  = mu.multiply(kY2.x).modInverse(p); // kY2.x modinverse times mu mod p
      BigInteger decodedMessage  = kY2.x.modInverse(p).multiply(mu); // kY2.x modinverse times mu mod p
      // BigInteger decodedMessage  = kY2.x.modInverse(mu.mod(p)); // kY2.x modinverse times mu mod p

      System.out.println("\nDecoded message: ");
      System.out.println(decodedMessage.toString(16));

      int comparison = message.compareTo(decodedMessage);
      if (comparison == 0) {
         System.out.println("\n\nMessage and decoded messages are a match!");
      }
      else {
         System.out.println("\n\nNo Match");
      }
   }

   public static void main(String[] args) {
      if (args.length < 1) {
         System.err.println("Usage: java DE4D ECP256.txt/secp256k1.txt");
         return;
      }
      DE4D de4 = new DE4D();
      de4.readCurveSpecs(args[0]);
      // de4.checkParameters();
      de4.generateKeys();
      de4.sharedSecret();
      de4.encryptionForB();
   }
}
