package com.nau.lab5;

import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class CipherService {

    private static final String ALPHABET = "АБВГҐДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЬЮЯ";
    private static final int ALPHABET_SIZE = ALPHABET.length();

    // Шифрування за допомогою генератора Лемера
    public String encryptWithLehmer(String plainText, long a, long c, long m, long x0) {
        StringBuilder cipherText = new StringBuilder();
        long xi = x0;

        for (int i = 0; i < plainText.length(); i++) {
            char currentChar = plainText.charAt(i);
            int plainIndex = ALPHABET.indexOf(Character.toUpperCase(currentChar));

            if (plainIndex != -1) { // Символ знайдено в алфавіті
                // Генеруємо псевдовипадкове число за формулою Лемера
                xi = (a * xi + c) % m;
                int keyValue = (int) (xi % ALPHABET_SIZE);

                // Обчислюємо індекс зашифрованого символу
                int cipherIndex = (plainIndex + keyValue) % ALPHABET_SIZE;

                // Додаємо зашифрований символ до результату
                cipherText.append(ALPHABET.charAt(cipherIndex));
            } else {
                // Якщо символ не в алфавіті, додаємо його без змін
                cipherText.append(currentChar);
            }
        }

        return cipherText.toString();
    }

    // Дешифрування за допомогою генератора Лемера
    public String decryptWithLehmer(String cipherText, long a, long c, long m, long x0) {
        StringBuilder plainText = new StringBuilder();
        long xi = x0;

        for (int i = 0; i < cipherText.length(); i++) {
            char currentChar = cipherText.charAt(i);
            int cipherIndex = ALPHABET.indexOf(Character.toUpperCase(currentChar));

            if (cipherIndex != -1) { // Символ знайдено в алфавіті
                // Генеруємо псевдовипадкове число за формулою Лемера
                xi = (a * xi + c) % m;
                int keyValue = (int) (xi % ALPHABET_SIZE);

                // Обчислюємо індекс дешифрованого символу
                int plainIndex = (cipherIndex - keyValue + ALPHABET_SIZE) % ALPHABET_SIZE;

                // Додаємо дешифрований символ до результату
                plainText.append(ALPHABET.charAt(plainIndex));
            } else {
                // Якщо символ не в алфавіті, додаємо його без змін
                plainText.append(currentChar);
            }
        }

        return plainText.toString();
    }

    // Шифрування за допомогою генератора Блюм-Блюм-Шуба
    public String encryptWithBBS(String plainText, long p, long q, long x0) {
        StringBuilder cipherText = new StringBuilder();
        BigInteger m = BigInteger.valueOf(p).multiply(BigInteger.valueOf(q));
        BigInteger x = BigInteger.valueOf(x0);

        for (int i = 0; i < plainText.length(); i++) {
            char currentChar = plainText.charAt(i);
            int plainIndex = ALPHABET.indexOf(Character.toUpperCase(currentChar));

            if (plainIndex != -1) { // Символ знайдено в алфавіті
                // Генеруємо псевдовипадкове число за алгоритмом Блюм-Блюм-Шуба
                x = x.modPow(BigInteger.valueOf(2), m);
                int keyValue = x.mod(BigInteger.valueOf(ALPHABET_SIZE)).intValue();

                // Обчислюємо індекс зашифрованого символу
                int cipherIndex = (plainIndex + keyValue) % ALPHABET_SIZE;

                // Додаємо зашифрований символ до результату
                cipherText.append(ALPHABET.charAt(cipherIndex));
            } else {
                // Якщо символ не в алфавіті, додаємо його без змін
                cipherText.append(currentChar);
            }
        }

        return cipherText.toString();
    }

    // Дешифрування за допомогою генератора Блюм-Блюм-Шуба
    public String decryptWithBBS(String cipherText, long p, long q, long x0) {
        StringBuilder plainText = new StringBuilder();
        BigInteger m = BigInteger.valueOf(p).multiply(BigInteger.valueOf(q));
        BigInteger x = BigInteger.valueOf(x0);

        for (int i = 0; i < cipherText.length(); i++) {
            char currentChar = cipherText.charAt(i);
            int cipherIndex = ALPHABET.indexOf(Character.toUpperCase(currentChar));

            if (cipherIndex != -1) { // Символ знайдено в алфавіті
                // Генеруємо псевдовипадкове число за алгоритмом Блюм-Блюм-Шуба
                x = x.modPow(BigInteger.valueOf(2), m);
                int keyValue = x.mod(BigInteger.valueOf(ALPHABET_SIZE)).intValue();

                // Обчислюємо індекс дешифрованого символу
                int plainIndex = (cipherIndex - keyValue + ALPHABET_SIZE) % ALPHABET_SIZE;

                // Додаємо дешифрований символ до результату
                plainText.append(ALPHABET.charAt(plainIndex));
            } else {
                // Якщо символ не в алфавіті, додаємо його без змін
                plainText.append(currentChar);
            }
        }

        return plainText.toString();
    }
}
