package com.nau.lab5;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Component
public class CipherCommandLineRunner implements CommandLineRunner {

    private final CipherService cipherService;
    private final FileService fileService;

    public CipherCommandLineRunner(CipherService cipherService, FileService fileService) {
        this.cipherService = cipherService;
        this.fileService = fileService;
    }

    @Override
    public void run(String... args) {
        try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
            System.out.println("Шифр гамування - лабораторна робота 5");
            System.out.println("Виберіть операцію:");
            System.out.println("1. Шифрування");
            System.out.println("2. Дешифрування");
            int operation = Integer.parseInt(scanner.nextLine());

            System.out.println("Виберіть генератор псевдовипадкових чисел:");
            System.out.println("1. Генератор Лемера");
            System.out.println("2. Генератор Блюм-Блюм-Шуба");
            int generator = Integer.parseInt(scanner.nextLine());

            System.out.println("Введіть ім'я вхідного файлу:");
            String inputFileName = scanner.nextLine();

            System.out.println("Введіть ім'я вихідного файлу:");
            String outputFileName = scanner.nextLine();

            String text;
            if (inputFileName.isEmpty()) {
                // Якщо ім'я файлу не вказано, використовуємо стандартний текст
                text = "Я, Попов Кирил Олександрович студент університету";
                System.out.println("Використовується текст за замовчуванням: " + text);
            } else {
                // Читаємо текст з файлу
                text = fileService.readFromFile(inputFileName);
                System.out.println("Текст з файлу: " + text);
            }

            String result;
            if (generator == 1) {
                System.out.println("Введіть параметри для генератора Лемера:");
                System.out.println("Модуль (m):");
                long m = Long.parseLong(scanner.nextLine());
                System.out.println("Множник (a):");
                long a = Long.parseLong(scanner.nextLine());
                System.out.println("Приріст (c):");
                long c = Long.parseLong(scanner.nextLine());
                System.out.println("Початкове значення (X0):");
                long x0 = Long.parseLong(scanner.nextLine());

                result = (operation == 1) ?
                        cipherService.encryptWithLehmer(text, a, c, m, x0) :
                        cipherService.decryptWithLehmer(text, a, c, m, x0);
            } else {
                System.out.println("Введіть параметри для генератора Блюм-Блюм-Шуба:");
                System.out.println("Перше просте число (p):");
                long p = Long.parseLong(scanner.nextLine());
                System.out.println("Друге просте число (q):");
                long q = Long.parseLong(scanner.nextLine());
                System.out.println("Початкове значення (X0):");
                long x0 = Long.parseLong(scanner.nextLine());

                result = (operation == 1) ?
                        cipherService.encryptWithBBS(text, p, q, x0) :
                        cipherService.decryptWithBBS(text, p, q, x0);
            }

            System.out.println("Результат: " + result);
            fileService.writeToFile(outputFileName, result);
            System.out.println("Операція завершена успішно!");

        } catch (Exception e) {
            System.err.println("Помилка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
