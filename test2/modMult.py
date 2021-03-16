import sys

# Python 3 program of finding modulo multiplication  in a given GF(2^n) with given irreducible

# Returns (a * b) % mod 
def moduloMultiplication(a, b, mod): 
    res = 0; # Initialize result 

    # Update a if it is more than 
    # or equal to mod 
    a = a % mod; 

    while(b):
        if b & 1 > 0:
            res ^= a

        a <<= 1

        if a & GF > 0:
            a ^= mod

        b >>= 1

    return res; 

# Driver Code 
a = int(sys.argv[1]); 
b = int(sys.argv[2]); 
irreducible = int(sys.argv[3]); 
GF = 2**3 if len(sys.argv) < 5 else int(sys.argv[4])

print(moduloMultiplication(a, b, irreducible)); 
	
# This code is contributed 
# by Shivi_Aggarwal 
