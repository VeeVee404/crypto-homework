import cv2 
import numpy as np 
import random

def permute_rows(image, num_blocks):
    height, width, channels = image.shape
    block_height = height // num_blocks

    row_indices = list(range(height))
    random.shuffle(row_indices)

    encrypted_image = np.zeros_like(image)

    # Wende die Permutationen auf die Zeilen an
    for i in range(num_blocks):
        start_idx = i * block_height
        end_idx = (i + 1) * block_height
        permuted_indices = row_indices[start_idx:end_idx]

        # Kopiere die Zeilen mit Permutationen in das verschlüsselte Bild
        encrypted_image[start_idx:end_idx, :, :] = image[permuted_indices, :, :]

    return encrypted_image


image = cv2.imread("bild.jpg")

# Definiere die Anzahl der horizontalen Bildblöcke
num_blocks = 5

encrypted_image = permute_rows(image, num_blocks)

cv2.imwrite("verschluesseltes_bild.jpg", encrypted_image)
