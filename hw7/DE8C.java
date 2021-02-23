// DE8C.java CS5125/6025 cheng 2021
// implements multiplication for GF(2^8)
// and modPow using both the Russian peasant's algorithm and the discrete log
// You are asked to find all primitive elements of GF(2^8) 
// for each of the three irreducibles
// an element a is primitive if logPow(a, b) is not 1 for all factors b of 255
// and they are 3, 5, 17, and the pairwise products of these prime factors.
// Usage: java DE8C

public class DE8C{

 	static final int numberOfBits = 8;
 	static final int fieldSize = 1 << numberOfBits;
 	static final int irreducible = 0x11b;  // this could be 0x11d or 0x12b
 	static final int logBase = 3;
 	int[] alog = new int[fieldSize];
 	int[] log = new int[fieldSize];

  int modMultiply(int a, int b, int m){ // almost the same as multiply of DE8A
    	int product = 0;
	for (; b > 0; b >>= 1){
		if ((b & 1) > 0) product ^= a;
		a <<= 1;
		if ((a & fieldSize) > 0) a ^= m;
	}
	return product;
  }    

  int modPow(int a, int b){  // Russian peasant's algorithm
	int power = 1;
	for (; b > 0; b >>= 1){
		if ((b & 1) > 0) power = logMultiply(power, a);
		a = logMultiply(a, a);
	}
	return power;
  }

  int logPow(int a, int b){  // modPow using discrete log
	  return alog[(log[a] * b) % (fieldSize - 1)];
  }

  void listPowers(int base){
	for (int i = 1; i < fieldSize; i++)
		System.out.print(logPow(base, i) + " ");
	System.out.println();
  }  

  void makeLog(){   // log_3
	alog[0] = 1;  // alog is modPow
	for (int i = 1; i < fieldSize; i++)
		alog[i] = modMultiply(logBase, alog[i - 1], irreducible);
	for (int i = 1; i < fieldSize; i++) log[alog[i]] = i;
  }

  int logMultiply(int a, int b){
	return (a == 0 || b == 0) ? 0 : alog[(log[a] + log[b]) % (fieldSize - 1)];
  }

public static void main(String[] args){
	DE8C de8 = new DE8C();
	de8.makeLog();
}
} 
