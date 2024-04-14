def xor_encrypt(text, key):
    encrypted = ""
    for i in range(len(text)):
        encrypted += chr(ord(text[i]) ^ ord(key[i % len(key)]))
    return encrypted

def count_coincidences(ciphertext):
    coincidences = []
    for shift in range(1, len(ciphertext)):
        count = 0
        for i in range(len(ciphertext) - shift):
            if ciphertext[i] == ciphertext[i + shift]:
                count += 1
        coincidences.append(count)
    return coincidences.index(max(coincidences)) + 1

def hamming_distance(str1, str2):
    return sum(ch1 != ch2 for ch1, ch2 in zip(str1, str2))

def find_key_length(ciphertext):
    distances = []
    for key_length in range(2, min(40, len(ciphertext) // 4)):  
        blocks = [ciphertext[i:i+key_length] for i in range(0, len(ciphertext), key_length)][:4] 
        avg_distance = sum(hamming_distance(block1, block2) for block1, block2 in zip(blocks, blocks[1:])) / 6
        distances.append((key_length, avg_distance / key_length))
    return min(distances, key=lambda x: x[1])[0]


plaintext = "Verschlüsselter Twxt!"
key = "secret"

ciphertext = xor_encrypt(plaintext, key)
print("Ciphertext:", ciphertext)

key_length_coincidences = count_coincidences(ciphertext)
print("Key Length (Counting Coincidences):", key_length_coincidences)

key_length_hamming = find_key_length(ciphertext)
print("Key Length (Hamming Distance):", key_length_hamming)
