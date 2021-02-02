// DE4.java CS5151/6051 cheng 2018
// Examples 10-9 and 10-10 of Antonopoulos's Mastering Bitcoin
// by appending nonce to text and apply SHA256 until a certain leading nibbles become zeros
// Usage: java DE4 text
/* Prompt: DE4.java implements proof-of-work, following Examples 10-8 and 10-10.
* It only prints out the text, nonce, and hash when the number of leading zeros 
* increases (in the unit of half-byte or nibble).  How many iterations you have 
* to run to reach a difficulty represented by seven leading zero nibbles when 
* the text is "I am Satoshi Nakamoto"?  
* What is the answer to a text chosen by you?
*/
import java.io.*;
import java.util.*;
import java.security.*;

public class DE4{

  MessageDigest md = null;
  String text = null;

  public DE4(String t){
	try {
		md = MessageDigest.getInstance("SHA-256");
	} catch(NoSuchAlgorithmException e){
		System.err.println(e.getMessage());
		System.exit(1);
	}
	text = t;
  }


  String HexString(byte[] value){
  	String s = "";
	for (int i = 0; i < value.length; i++){
		String t = Integer.toHexString(Byte.toUnsignedInt(value[i]));
		if (t.length() == 1) s += "0" + t; else s += t;
	}
	return s;
  }
	
  void proofOfWork(){
	byte[] t = text.getBytes();
	md.update(t);
	System.out.println(text + " => " + HexString(md.digest()));
	int difficulty = 0;  int i = 0;
	while (difficulty < 10){
		md.update(t); 
		String nonce = Integer.toString(i++);
		String digest = HexString(md.digest(nonce.getBytes()));
		int j = 0; while(digest.charAt(j) == '0') j++;
		if (j > difficulty){
			System.out.println(text + nonce + " => " + digest);
			difficulty = j;
		}
	}
  }

  public static void main(String[] args){
	if (args.length < 1){
		System.err.println("Usage: java DE4 'I am Satoshi Nakamoto'");
		return;
	}
	DE4 de4 = new DE4(args[0]);
	de4.proofOfWork();
  }
}
 