def check_valid_isbn(num_str):
    if len(num_str) != 13:
        return False
  
    isbn_sum = 0
    for i in range(len(num_str)):
        num_at_char = int(num_str[i])
        
        # if there was a remainder, we know its not even
        if i % 2:
            # multiply by 1 for evens, 3 for odds
            num_at_char = num_at_char * 3
        
        isbn_sum += num_at_char
        
    # if the sum isnt a multiple of ten, return false
    if isbn_sum % 10:
        return False

    return True

def check_isbn_10(num_str):
    if len(num_str) != 10:
        return False
  
    isbn_sum = 0
    for i in range(len(num_str)):
        num_at_char = int(num_str[i])
        # num_at_char = num_at_char * num_at_char
        isbn_sum += num_at_char

    if isbn_sum % 10 != int(num_str[-1]):
        return False

    return True

def main():
    check_string = '1010101010101'
    print(check_valid_isbn(check_string))
    
    ten_check_string = '0205080057'
    print(check_isbn_10(ten_check_string))

if __name__ == '__main__':
    main()