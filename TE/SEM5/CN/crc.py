def xor(a, b):
    result = []
    for i in range(1, len(b)):
        if a[i] == b[i]:
            result.append('0')
        else:
            result.append('1')
    return ''.join(result)

def mod2div(dividend, divisor):
    pick = len(divisor)
    tmp = dividend[0:pick]

    while pick < len(dividend):
        if tmp[0] == '1':
            tmp = xor(divisor, tmp) + dividend[pick]
        else:
            tmp = xor('0' * pick, tmp) + dividend[pick]
        pick += 1

    if tmp[0] == '1':
        tmp = xor(divisor, tmp)
    else:
        tmp = xor('0' * pick, tmp)

    return tmp

def encode_data(data, key):
    l_key = len(key)
    appended_data = data + '0' * (l_key - 1)
    remainder = mod2div(appended_data, key)
    codeword = data + remainder
    return codeword

def check_parity(data):
    parity = '0' if data.count('1') % 2 == 0 else '1'
    return parity + data

def check_error(received_data, key):
    remainder = mod2div(received_data, key)
    return 'No error detected' if '1' not in remainder else 'Error detected'

data = input("Enter the data to be transferred (binary string): ")
generator = input("Enter the generator polynomial (binary string): ")

encoded_data = encode_data(data, generator)

parity_encoded_data = check_parity(encoded_data)
print(f"Data after adding CRC and parity bit: {parity_encoded_data}")

received_data = input("Enter the received data (binary string): ")

status = check_error(received_data, generator)
print(f"Status: {status}")
