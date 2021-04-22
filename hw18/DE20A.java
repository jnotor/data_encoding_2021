// DE20A.java CS5125/6025 cheng 2021
// enumerate atoms in a .mp4 file
// based on Apple's QuickTime File Format Specification
// Usage: java DE20A sample.mp4

import java.io.*;
import java.util.*;
import java.nio.ByteBuffer;

public class DE20A{

	static final int BUFSIZE = 16384;
	static final String[] containerAtoms = new String[]{
		"moov", "trak", "mdia", "minf", "dinf", "stbl", "udta", "edts"};
	byte[] buffer = new byte[BUFSIZE];
	InputStream in = null;
	int dataLength = 0;
	int pos = 0;
	HashSet<String> containers = new HashSet<String>();

  public DE20A(String filename){
       try{
         in = new FileInputStream(filename);
       } catch (FileNotFoundException e){
         System.err.println(filename + " not found");
         System.exit(1);
       }
	for (int i = 0; i < containerAtoms.length; i++) containers.add(containerAtoms[i]);
  }

  void readData(){
      try{
        dataLength = in.read(buffer);
      }catch(IOException e){ 
       System.err.println("IOException");
       System.exit(1); 
      }
  }  

  void get_ts_and_dur(String type){
	  /*  example of getting "Time scale" from "mvhd"
		if (type.equals("mvhd")) System.out.println(ByteBuffer.wrap(buffer, pos + 5 * 4, 4).getInt());
		*/

	if (type.equals("mvhd") || type.equals("mdhd")) {
		System.out.println("time scale: " + ByteBuffer.wrap(buffer, pos + 5 * 4, 4).getInt());
		System.out.println("duration: " + ByteBuffer.wrap(buffer, pos + 6 * 4, 4).getInt());
	}
	// according to the slides, i dont believe an atom of this type carries time scale or duration information
	// else if (type.equals("smhd")) {
	// 	System.out.println("time scale: " + ByteBuffer.wrap(buffer, pos + 5 * 4, 4).getInt());
	// 	System.out.println("duration: " + ByteBuffer.wrap(buffer, pos + 6 * 4, 4).getInt());
	// }
	else if (type.equals("tkhd")) {
		// according to the slides, i dont believe an atom of this type carries time scale information
		// System.out.println("time scale: " + ByteBuffer.wrap(buffer, pos + 5 * 4, 4).getInt());
		System.out.println("duration: " + ByteBuffer.wrap(buffer, pos + 7 * 4, 4).getInt());
	}
	System.out.println();
}


  void get_size_n_type(String type) {
	/* example of getting entry 1's "type" from "stsd"
	if (type.equals("stsd")) System.out.println(new String(buffer, pos + 5 * 4, 4));
	*/
	if (type.equals("stsz")) {
		  System.out.println("entry 1 size:" + ByteBuffer.wrap(buffer, pos + 5 * 4, 4).getInt());
		  System.out.println("entry 1 type:" + new String(buffer, pos + 6 * 4, 4));
	}
	else {
		System.out.println("entry 1 size:" + ByteBuffer.wrap(buffer, pos + 4 * 4, 4).getInt());
		System.out.println("entry 1 type:" + new String(buffer, pos + 5 * 4, 4));
	}

	System.out.println();
}

  void nextAtom(){
	int size = ByteBuffer.wrap(buffer, pos, 4).getInt();
	String type = new String(buffer, pos + 4, 4);
	
	String[] ts_duration_vars = {"mvhd", "tkhd", "mdhd", "smhd"};
	
	if (Arrays.asList(ts_duration_vars).contains(type)) {
		System.out.println(type + " size: " + size); 
		get_ts_and_dur(type);
	}
	
	String[] entry_count_vars = {"stsd", "stts", "stss", "stsc", "stsz", "stco"};
	
	if (Arrays.asList(entry_count_vars).contains(type)) {
		System.out.println(type + " size: " + size);  
		get_size_n_type(type);
	}

	if (containers.contains(type)) pos += 8;
	else pos += size;
	if (dataLength == BUFSIZE)
		while (pos >= BUFSIZE){
			readData();
			pos -= BUFSIZE;
		}
  }

  void atoms(){
	while (pos < dataLength - 8) nextAtom();
  }

   public static void main(String[] args){
	if (args.length < 1){         
		System.out.println("Usage: java DE20A sample.mp4");  
		System.exit(1);
	}
	DE20A de20 = new DE20A(args[0]);
	de20.readData();
	de20.atoms();
   }
}
	