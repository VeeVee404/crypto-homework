
import cv2
import numpy as np

def similarity_score(image, row_indices):
 
    score = 0
    for i in range(len(row_indices) - 1):
        row1 = image[row_indices[i], :, :]
        row2 = image[row_indices[i+1], :, :]
        score += np.sum(np.abs(row1 - row2))
    return score

def ciphertext_only_attack(encrypted_image, num_blocks):
    height, _, _ = encrypted_image.shape
    block_height = height // num_blocks

    best_score = float('inf')
    best_row_indices = None

    # Durchsuche alle möglichen Permutationen für die obere und untere Bildhälfte
    for i in range(1, block_height):
        row_indices = list(range(i, height)) + list(range(i))
        score = similarity_score(encrypted_image, row_indices)
        if score < best_score:
            best_score = score
            best_row_indices = row_indices

    return best_row_indices

encrypted_image = cv2.imread("verschluesseltes_bild.jpg")

guessed_num_blocks = 2

# Führe eine Ciphertext-only-Attacke durch, um die ursprüngliche Permutation der Zeilen zu erraten
guessed_row_indices = ciphertext_only_attack(encrypted_image, guessed_num_blocks)

print("Erratene Zeilenindizes:", guessed_row_indices)
