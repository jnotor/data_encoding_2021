DE8C.java implements the exponentiation operation a^b for GF(2^8) as modPow(a, b), 
or more efficiently, logPow(a, b).  If an element a of GF(2^8) is not primitive 
(meaning that the powers do not cover all 255 nonzero elements and it cannot be 
used as the a base to discrete log), then at least one of the six powers (a^3, 
a^5, a^17, a^15, a^51, and a^85) will be 1.  Write a function to list all 
primitive elements.  Do the same with the other two irreducibles 0x11d and 0x12b.  
Submit your code and the three lists of primitives.