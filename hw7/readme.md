DE8C.java implements the exponentiation operation a^b for GF(2^8) as modPow(a, b), 
or more efficiently, logPow(a, b).  

If an element a of GF(2^8) is not primitive  (meaning that the powers do not 
cover all 255 nonzero elements and it cannot be used as the a base to discrete log), 
then at least one of the six powers (a^3, a^5, a^17, a^15, a^51, and a^85) will be 1.  
an element a is primitive if logPow(a, b) is not 1 for all factors b of 255 (3, 5, 17)
and the pairwise products of these prime factors (3*5=15, 5*17=85, 3*17=51)

not primitive if logPow(a, b) is 1 for any factors, 3, 5, 17


Write a function to list all primitive elements.  Do the same with the other two
irreducibles 0x11d and 0x12b. 

Submit your code and the three lists of primitives.

checks = [logPow(a, 3), logPow(a, 5), logPow(a, 17)]
if all(i for i in checks if i != 1)