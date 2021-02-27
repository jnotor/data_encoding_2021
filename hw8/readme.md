DE9A.java is an implementation of AES encryption that takes plaintext blocks from 
stdin and dumps a concatenation of ciphertext blocks in stdout.  The key is 
copied from Stallings' AES example.  

DE9B.java is the decryption program, as the inverse of DE9A, meaning that when the ciphertext from DE9A is fed from stdin, the plaintext comes out in its stdout.  

You need to complete inverseShiftRows() and inverseAddRoundKey() to make it work as a complete reverse of all the transforms in DE9A.  

Test your implementation on DE9A outputs from plaintextx that you know to make sure it works.  Then decrypt DE9test1 (a binary file, unreadable unless using HexDump) and submit your code with the decryption result (which should be an English text).