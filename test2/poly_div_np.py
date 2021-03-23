import numpy as np

# input has to be the coeff of x^n + x^n-1 ... x^0
# divide_by_me = np.array([1, 3, 2])
# divide_me = np.array([2, 1, 0, 0])
divide_by_me = np.array([1, 1, 1])
divide_me = np.array([1, 0, 0])

quotient, remainder = np.polydiv(divide_me, divide_by_me)
print('remainder:', remainder)
print('quot:', quotient)