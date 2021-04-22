# https://github.com/ahmedfgad/ArithmeticEncodingPython
import pyae
from decimal import getcontext

getcontext().prec = 45

# frequency_table = {"a": 2,
#                    "b": 7,
#                    "c": 1}

frequency_table = {'E' : 2, 'C' : 1, 'S': 1}
AE = pyae.ArithmeticEncoding(frequency_table=frequency_table,
                            save_stages=True)

original_msg = 'EECS'
print("Original Message: {msg}".format(msg=original_msg))

encoded_msg, encoder , interval_min_value, interval_max_value = AE.encode(msg=original_msg, 
                                                                          probability_table=AE.probability_table)
print("Encoded Message: {msg}".format(msg=encoded_msg))


# binary_code, encoder_binary = AE.encode_binary(float_interval_min=interval_min_value,
#                                                float_interval_max=interval_max_value)

decoded_msg, decoder = AE.decode(encoded_msg=encoded_msg, 
                                 msg_length=len(original_msg),
                                 probability_table=AE.probability_table)
decoded_msg = "".join(decoded_msg)
print("Decoded Message: {msg}".format(msg=decoded_msg))
print("Message Decoded Successfully? {result}".format(result=original_msg == decoded_msg))


print('printing encoder: ')
intervals = []
# print(encoder[-1])
for key in encoder[-1]:
    intervals.append(encoder[-1][key][0])
    intervals.append(encoder[-1][key][1])

def decimalToBinary(n):
    return "{0:b}".format(int(n))
    
print('max:', max(intervals))
print('min:', min(intervals))
# print('printing bin encoder: ')
# for elem in encoder_binary:
#     print(elem)