// DE5.java CS5125/6025 2021 Cheng
// Various functions for Stallings Chapter 2 number theory concepts

import java.io.*;
import java.util.*;

public class DE5{

  boolean divides(int a, int b){ return b % a == 0; }

  void divisors(int n){
	for (int i = 1; i <= n; i++) if (divides(i, n)) System.out.println(i);
  }

  boolean isPrime(int n){
	int i = 2; for (; i < n / 2; i++) if (divides(i, n)) return false;
	return true;
  }

  void listPrimes(){
	for (int i = 2; i < 2000; i++) if (isPrime(i)) System.out.println(i);
  }
		
  int gcd(int a, int b){
	if (a < b){ int t = a; a = b; b = t; }
	int r = a % b;
	evaluate(a);
	while (r > 0){ a = b; b = r; r = a % b; }
	return b;
  }

  boolean relativelyPrime(int a, int b){ return gcd(a, b) == 1; }


  void additionTable(int modulus){
	System.out.print("+"); 
	for (int i = 0; i < modulus; i++) System.out.print(" " + i);
	System.out.println();
	for (int i = 0; i < modulus; i++){
		System.out.print(i);
		for (int j = 0; j < modulus; j++) System.out.print(" " + ((i + j) % modulus));
		System.out.println();
	}
	System.out.println();
  }

  void multiplicationTable(int modulus){
	System.out.print("x"); 
	for (int i = 0; i < modulus; i++) System.out.print(" " + i);
	System.out.println();
	for (int i = 0; i < modulus; i++){
		System.out.print(i);
		for (int j = 0; j < modulus; j++) System.out.print(" " + ((i * j) % modulus));
		System.out.println();
	}
	System.out.println();
  }

  void powerTable(int modulus){
	System.out.print("^"); 
	for (int i = 2; i < modulus; i++) System.out.print(" " + i);
	System.out.println();
	for (int i = 1; i < modulus; i++){
		int power = i;
		System.out.print(i);
		for (int j = 2; j < modulus; j++){
			power = (power * i) % modulus;
			System.out.print(" " + power);
		}
		System.out.println();
	}
	System.out.println();
  }

	
 public static void main(String[] args){
	if (args.length < 1){
		System.err.println("usage: java DE5 modulus");
		return;
	}
	DE5 de5 = new DE5();
	int modulus = Integer.parseInt(args[0]);
	de5.additionTable(modulus);
	de5.multiplicationTable(modulus);
	de5.powerTable(modulus);
 }
}
