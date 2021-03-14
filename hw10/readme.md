DE11.java performs Reed-Solomon error correction encoding to a short message that 
you provide and then randomly adds error to a number (specified by you)  of bytes
in the encoding result.  

It then finds the syndromes and uses Berlekamp-Massey  to solve for the error 
locator equation (coefficients) and then the error locations by finding all zeros
of the locator equation.  

It then uses the Forney algorithm  to first make the polynomial Z and then find 
the error magnitudes of the byte errors at different locations.  
Then the error locations and magnitudes are used to correct the errors.  

You need to replace a few lines of pseudo code in the Forney algorithm to make 
the last step of the error correction work.  

Show your code and some run of the program.

