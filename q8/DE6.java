import java.io.*;
import java.util.*;

public class DE6{

  void RSA(int p, int q, int e, int M){
	int n = p * q;
	System.out.println(n);

	int totient = (p - 1) * (q - 1);
	System.out.println(totient);

	int d = 1; for (; d < totient; d++)
		if ((d * e) % totient == 1) break;
	System.out.println(d);
		
	int C = M;
	for (int i = 0; i < e; i++) C = (C * M) % n;
	System.out.println(C);
	
	int M2 = C;
	for (int i = 0; i < d; i++) M2 = (M2 * C) % n;
	System.out.println(M2);
	// print n, totient, d, C, and M2
  }

public static void main(String[] args){
	DE6 de6 = new DE6();
	de6.RSA(17, 31, 7, 2);
}
}
