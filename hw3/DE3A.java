// DE3A.java CS5125/6025 cheng 2020
// store the strings in a file in block and generate the Merkle root for the strings
// Usage: java BC1A fileOfStrings

import java.io.*;
import java.util.*;
import java.security.*;

public class DE3A{

  MessageDigest md = null;
  ArrayList<String> items = new ArrayList<String>();
  int N = 0;

  public DE3A(){
	try {
		md = MessageDigest.getInstance("SHA-256");
	} catch(NoSuchAlgorithmException e){
		System.err.println(e.getMessage());
		System.exit(1);
	}
  }

  void readData(String filename){
	Scanner in = null;
	try {
		in = new Scanner(new File(filename));
	} catch (FileNotFoundException e){
		System.err.println(e.getMessage());
		System.exit(1);
	}
	while (in.hasNextLine()) items.add(in.nextLine());
	in.close();
	N = items.size();
  }

  void merkleRoot(){
	int M = N; 
	ArrayList<byte[]> merkle = new ArrayList<byte[]>(M);
	items.forEach(x -> merkle.add(md.digest(md.digest(x.getBytes()))));
	while (M > 1){
		if (M % 2 > 0){ merkle.set(M, merkle.get(M - 1)); M++; }
		M /= 2;
		for (int i = 0; i < M; i++){ 
			md.update(merkle.get(2 * i));
			merkle.set(i, md.digest(md.digest(merkle.get(2 * i + 1))));
		}
	}
	System.out.print("merkle root: ");
	System.out.println(HexString(merkle.get(0)));
  }

  String HexString(byte[] value){
  	String s = "";
	for (int i = 0; i < value.length; i++){
		String t = Integer.toHexString(Byte.toUnsignedInt(value[i]));
		if (t.length() == 1) s += "0" + t; else s += t;
	}
	return s;
  }
	

  public static void main(String[] args){
	if (args.length < 1){
		System.err.println("Usage: java DE3A names12.txt");
		return;
	}
	DE3A de3 = new DE3A();
	de3.readData(args[0]);
	de3.merkleRoot();
  }
}
 