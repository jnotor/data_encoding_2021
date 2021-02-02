// DE3C.java CS5125/6025 cheng 2020
// reads a string, its index, and the merkle path
// compute and output the merkle root that should be the same as the output from BC1A
// a proof that the string at the index position in the list of strings collected by BC1A
// Usage: java DE3C DE3Bout.txt

import java.io.*;
import java.util.*;
import java.security.*;

public class DE3C{

  MessageDigest md = null;
  String item = null;
  int k = -1;  
  int depth = 0;
  ArrayList<byte[]> merkle = new ArrayList<byte[]>();

  public DE3C(){
	try {
		md = MessageDigest.getInstance("SHA-256");
	} catch(NoSuchAlgorithmException e){
		System.err.println(e.getMessage());
		System.exit(1);
	}
  }

  void readMerklePath(String filename){
	Scanner in = null;
	try {
		in = new Scanner(new File(filename));
	} catch (FileNotFoundException e){
		System.err.println(e.getMessage());
		System.exit(1);
	}
<<<<<<< HEAD
	// Get name
	item = in.nextLine();

	// Get position
	k = Integer.parseInt(in.nextLine());

	// compute values of bytes and add to merkle arraylist; converting sha output here
=======
	item = in.nextLine();
	k = Integer.parseInt(in.nextLine());
>>>>>>> 8fce20417e2c22ee7409c0f44c515829f0cef413
	int[] halfBytes = new int[2];
	while (in.hasNextLine()){
		String line = in.nextLine();
		int len = line.length() / 2;
		byte[] value = new byte[len];
		for (int i = 0; i < len; i++){
			for (int j = 0; j < 2; j++){
				int a = line.charAt(2 * i + j);
				if (a <= '9') a -= '0';
				else a = a - 'a' + 10;
				halfBytes[j] = a;
			}
			value[i] = (byte)(halfBytes[0] * 16 + halfBytes[1]);
		}
		merkle.add(value);
		depth++;
	}	
	in.close();
  }

  void proof(){
<<<<<<< HEAD
	// a leaf of the merkle tree (where item var is the name selected. i think). SHA it 2x
	byte[] root = md.digest(md.digest(item.getBytes()));

	for (int i = 0; i < depth; i++){
		// left child
		if (k % 2 == 0){
			// concat the byte arrays with root on the left
			byte[] temp = new byte[root.length + merkle.get(i).length];
			System.arraycopy(root, 0, temp, 0, root.length);
			System.arraycopy(merkle.get(i), 0, temp, root.length, merkle.get(i).length);
			root = md.digest(md.digest(temp));
			// right child
		} else { 
			byte[] temp = new byte[merkle.get(i).length + root.length];
			System.arraycopy(merkle.get(i), 0, temp, 0, merkle.get(i).length);
			System.arraycopy(root, 0, temp, merkle.get(i).length, root.length);
			root = md.digest(md.digest(temp));
=======
	byte[] root = md.digest(md.digest(item.getBytes()));  // a leaf of the merkle tree
	for (int i = 0; i < depth; i++){
		if (k % 2 == 0){
			// your code to update root from root and merkle[i]
		}else{ 
			// your code to update root from root and merkle[i] in another way
>>>>>>> 8fce20417e2c22ee7409c0f44c515829f0cef413
		}
		k /= 2;
	}
	System.out.println(HexString(root));
  }


  String HexString(byte[] value){
<<<<<<< HEAD
	// helper function to print out the root
=======
>>>>>>> 8fce20417e2c22ee7409c0f44c515829f0cef413
  	String s = "";
	for (int i = 0; i < value.length; i++){
		String t = Integer.toHexString(Byte.toUnsignedInt(value[i]));
		if (t.length() == 1) s += "0" + t; else s += t;
	}
	return s;
  }

  public static void main(String[] args){
	if (args.length < 1){
		System.err.println("Usage: java DE3C DE3Bout.txt");
		return;
	}
	DE3C de3 = new DE3C();
	de3.readMerklePath(args[0]);
	de3.proof();
  }
}
 