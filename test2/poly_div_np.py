import numpy as np

# input has to be the coeff of x^n + x^n-1 ... x^0
x = np.array([1, 3, 2])
y = np.array([2, 1, 0, 0])

quotient, remainder = np.polydiv(y, x)
print('remainder:', remainder)
print('quot:', quotient)