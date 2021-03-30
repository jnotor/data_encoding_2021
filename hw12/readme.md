DE13A.java implements JPEG-LS, a lossless transform on a BMP image and results in
a BMP image of the same size but with much smaller entropy and that can be further
encoded using Huffman or VLC (but we will not do that in this assignment). You 
can use Windows paint and other tools to see the transformed BMP images and 
appreciate the low entropy it must have.  

DE13B.java is the inverse of DE13A and it will recover the BMP image before the 
JPEG-LS transform.  You need to code the unmapError() with the part called 
interleave which turn positive and negative differences between the pixel value 
and the prediction into all positive small integers. 

LenaRGB.bmp is there for you to verify your implementation and DE13test1.bmp is 
one for you to run your DE13B on and to submit the recovered original image.