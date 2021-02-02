// DE3B.java CS5125/6025 cheng 2020
// store the strings in a file in block and generate the merkle path for
// a randomly selected string 
// output the string, its index in the store, and the necessary hash values on the merkle path
// Usage: java DE3B fileOfStrings

import java.io.*;
import java.util.*;
import java.security.*;

public class DE3B{

  MessageDigest md = null;
  ArrayList<String> items = new ArrayList<String>();
  int N = 0;
  int k = -1;  // randomly chosen element's index

  public DE3B(){
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

  void selectElement(){
	Random random = new Random();
	k = random.nextInt(N);
	System.out.println(items.get(k));
	System.out.println(k);
  }

  void merklePath(){
	int M = N; 
	ArrayList<byte[]> merkle = new ArrayList<byte[]>(M);
	items.forEach(x -> merkle.add(md.digest(md.digest(x.getBytes()))));
	while (M > 1){
		if (M % 2 > 0){ merkle.set(M, merkle.get(M - 1)); M++; }
		if (k % 2 == 0) System.out.println(HexString(merkle.get(k + 1)));
		else System.out.println(HexString(merkle.get(k - 1)));
		M /= 2; k /= 2;
		for (int i = 0; i < M; i++){ 
			md.update(merkle.get(2 * i));
			merkle.set(i, md.digest(md.digest(merkle.get(2 * i + 1))));
		}
	}
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
		System.err.println("Usage: java DE3B names12.txt");
		return;
	}
	DE3B de3 = new DE3B();
	de3.readData(args[0]);
	de3.selectElement();
	de3.merklePath();
  }
}
 