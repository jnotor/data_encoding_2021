// DE13B.java CS5125/6025 Cheng 2021
// Decoder for DE13A
// need to make the inverse of DE13A.mapError() in unmapError()
// Usage: Java DE13B < image.bmp > image2.bmp

import java.io.*;
import java.util.*;

public class DE13B {
  static int borderValue = 128; // a,b,c for x on first row and column
  int width, height; // image dimensions
  int[][][] raw; // the raw image stored here

  void readHeader() {
    byte[] header = new byte[54];
    try {
      System.in.read(header);
    } catch (IOException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }
    if (header[0] != 'B' || header[1] != 'M' || header[14] != 40 || header[28] != 24) {
      System.err.println("wrong file format");
      System.exit(1);
    }
    width = Byte.toUnsignedInt(header[19]) * 256 + Byte.toUnsignedInt(header[18]);
    height = Byte.toUnsignedInt(header[23]) * 256 + Byte.toUnsignedInt(header[22]);
    try {
      System.out.write(header); // the encoded file has the same bmp header
    } catch (IOException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }
  }

  void readImage() {
    byte[] image = new byte[height * width * 3];
    raw = new int[height][width][3];
    try {
      System.in.read(image);
    } catch (IOException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }
    int index = 0;
    for (int i = 0; i < height; i++)
      for (int j = 0; j < width; j++)
        for (int k = 0; k < 3; k++)
          raw[i][j][k] = Byte.toUnsignedInt(image[index++]);
  }

  void dejpegls() {
    int a, b, c;
    for (int i = 0; i < height; i++) // find the neighboring pixels
      for (int j = 0; j < width; j++)
        for (int k = 0; k < 3; k++) {
          if (j == 0)
            a = borderValue;
          else
            a = raw[i][j - 1][k];
          if (i == 0) {
            b = c = borderValue;
          } else {
            if (j == 0)
              c = borderValue;
            else
              c = raw[i - 1][j - 1][k];
            b = raw[i - 1][j][k];
          }
          int prediction = predict(a, b, c);
          raw[i][j][k] = unmapError(raw[i][j][k], prediction);
          System.out.write(raw[i][j][k]);
        }
    System.out.flush();
  }

  int predict(int a, int b, int c) {
    int x;
    if ((c >= a) && (c >= b))
      x = (a >= b) ? b : a;
    else if ((c <= a) && (c <= b))
      x = (a >= b) ? a : b;
    else
      x = a + b - c;
    return x;
  }

  int unmapError(int error, int predicted){
	  // Logic:  if error is even, it is 2e and hence e is ???
	  // otherwise it is -2e-1, and hence e is ???
     int e = (error % 2 == 0) ? error/2 : -((error+1)/2);
     int value = predicted + e;
     if (value > 255) value -= 256;
     else if (value < 0) value += 256;
     return value;
  }

  public static void main(String[] args) {
    DE13B de13 = new DE13B();
    de13.readHeader();
    de13.readImage();
    de13.dejpegls();
  }
}
