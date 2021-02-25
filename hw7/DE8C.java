// DE8C.java CS5125/6025 cheng 2021
// implements multiplication for GF(2^8)
// and modPow using both the Russian peasant's algorithm and the discrete log
// You are asked to find all primitive elements of GF(2^8) 
// for each of the three irreducibles
// an element a is primitive if logPow(a, b) is not 1 for all factors b of 255
// and they are 3, 5, 17, and the pairwise products of these prime factors.
// Usage: java DE8C

public class DE8C {
	static final int logBase = 3;
	static final int numberOfBits = 8;
	static final int fieldSize = 1 << numberOfBits; // 256

	int[] alog = new int[fieldSize];
	int[] log = new int[fieldSize]; // ME: used to index alog with some math

	int logMultiply(int a, int b) {
		return (a == 0 || b == 0) ? 0 : alog[(log[a] + log[b]) % (fieldSize - 1)];
	}

	int modPow(int a, int b) { // Russian peasant's algorithm
		int power = 1;
		for (; b > 0; b >>= 1) { // bitshift by 1
			if ((b & 1) > 0)
				power = logMultiply(power, a);
			a = logMultiply(a, a);
		}
		return power;
	}

	int logPow(int a, int b) { // modPow using discrete log
		return alog[(log[a] * b) % (fieldSize - 1)];
	}

	void listPowers(int base) {
		for (int i = 1; i < fieldSize; i++)
			System.out.print(logPow(base, i) + " ");
		System.out.println();
	}

	void reportPrimitives(int irreducible) {
		int temp = 0;
		
		System.out.print("checking:" + irreducible);
		System.out.println();
		
		// check all elements of 2^8 - 1 = 255
		for (int base = 1; base<fieldSize; base++) { 
			// check exponents which are exponents of 255
			int[] log_pows = {3, 5, 17, 15, 51, 85};

			boolean report = true;
			for (int j=0; j<log_pows.length; j++) {
				if (logPow(base, log_pows[j]) == 1) {
					// System.out.println("base: " + base + " pow: " + log_pows[j]);
					report = false;
					break;
				}
			}

			if (report) {
				System.out.print(base + " ");
				temp++;
			}
		}
		// https://codyplanteen.com/assets/rs/gf256_prim.pdf - num primitives
		System.out.println("count: " + temp);
	}

	int modMultiply(int a, int b, int m) { // almost the same as multiply of DE8A. lol wut
		int product = 0;
		for (; b > 0; b >>= 1) {
			if ((b & 1) > 0)
				product ^= a;
			a <<= 1;
			if ((a & fieldSize) > 0)
				a ^= m;
		}
		return product;
	}

	void makeLog(int irreducible) { // log_3
		alog[0] = 1; // alog is modPow
		for (int i = 1; i < fieldSize; i++)
			alog[i] = modMultiply(logBase, alog[i - 1], irreducible);
		for (int i = 1; i < fieldSize; i++)
			log[alog[i]] = i;
		
		// listPowers(3);
	}

	public static void main(String[] args) {
		DE8C de8 = new DE8C();

		int[] irreduc_polys = {0x11b, 0x11d, 0x12b};
		for (int i=0; i<irreduc_polys.length; i++) {
			de8.makeLog(irreduc_polys[i]);
			de8.reportPrimitives(irreduc_polys[i]);
			System.out.println();
			System.out.println();
			// break;
		}
	}
}

