import math

def calculate_parity(bits, positions):
    parity = 0
    for position in positions:
        if bits[position] is not None:
            parity ^= bits[position]
    return parity

def hamming_code(binary_str):
    data_bits = [int(bit) for bit in binary_str]
    m = len(data_bits)

    # Calculate the number of parity bits needed
    r = 0
    while (2 ** r) < (m + r + 1):
        r += 1

    # Initialize the codeword with None for parity positions
    codeword = [None] * (m + r)

    # Place data bits into the codeword, skipping parity positions
    j = 0
    for i in range(1, len(codeword) + 1):
        if math.log2(i).is_integer():
            continue
        codeword[i - 1] = data_bits[j]
        j += 1

    # Calculate parity bits
    for i in range(r):
        parity_pos = (2 ** i) - 1
        parity_positions = [(j + 1) for j in range(parity_pos, len(codeword)) if ((j + 1) & (2 ** i)) != 0]
        codeword[parity_pos] = calculate_parity(codeword, [p - 1 for p in parity_positions])

    # Convert None values to parity bits and assemble the final codeword
    for i in range(r):
        parity_pos = (2 ** i) - 1
        if codeword[parity_pos] is None:
            codeword[parity_pos] = calculate_parity(codeword, [(2 ** i) - 1])

    return ''.join(str(bit) for bit in codeword)

# Example usage
binary_str = input("Enter binary string: ")

if not all(bit in '01' for bit in binary_str):
    raise ValueError("Binary string must only contain 0s and 1s")

hamming_codeword = hamming_code(binary_str)
print(f"Hamming Code for {binary_str}: {hamming_codeword}")
