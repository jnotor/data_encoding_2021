mobi_d = {
    '1' : 1, 
    '2' : -1,
    '3' : -1,
    '4' : 0,
    '5' : -1,
    '6' : 1,
    '7' : -1,
    '8' : 0,
}

n = 8
mobi_sum = 0
for d in range(1, 9):
    mobi_cell = mobi_d[str(d)] * 2 ** (n/d)
    print(mobi_cell)
    mobi_sum += mobi_cell

print('sum:', mobi_sum)
print('P_sub2(n):', mobi_sum / n)
