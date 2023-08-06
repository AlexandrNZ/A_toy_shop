package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;

public class ToyLottery {
    private static final String FILE_PATH = "result.txt";

    private static class Toy {
        private int id;
        private String name;
        private int weight;

        public Toy(int id, String name, int weight) {
            this.id = id;
            this.name = name;
            this.weight = weight;
        }
    }

    public static void main(String[] args) {
        // Инициализация массива строк с игрушками
        String[] toyStrings = {
                "2 конструктор 20",
                "2 робот 20",
                "6 кукла 60"
        };

        // Создание коллекции для хранения игрушек
        PriorityQueue<Toy> toys = new PriorityQueue<>((t1, t2) -> t2.weight - t1.weight);

        // Заполнение коллекции игрушками из массива строк
        for (String toyString : toyStrings) {
            String[] parts = toyString.split(" ");
            int id = Integer.parseInt(parts[0]);
            String name = String.join(" ", parts[1]);
            int weight = Integer.parseInt(parts[2]);

            Toy toy = new Toy(id, name, weight);
            toys.add(toy);
        }

        // Запись результатов в файл
        try {
            FileWriter writer = new FileWriter(FILE_PATH);
            for (int i = 0; i < 10; i++) {
                int randomNumber = (int) (Math.random() * 100); // Генерация случайного числа от 0 до 99

                if (randomNumber < 20) {
                    writer.write("Выпало: 1\n");
                } else if (randomNumber < 40) {
                    int randomToyId = getRandomToyId(toys);
                    writer.write("Выпало: " + randomToyId + "\n");
                } else {
                    writer.write("Выпало: 3\n");
                }
            }
            writer.close();
            System.out.println("Запись в файл успешно выполнена");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getRandomToyId(PriorityQueue<Toy> toys) {
        int totalWeight = 0;
        for (Toy toy : toys) {
            totalWeight += toy.weight;
        }

        int randomNumber = (int) (Math.random() * totalWeight);
        int currentWeight = 0;
        for (Toy toy : toys) {
            currentWeight += toy.weight;
            if (randomNumber < currentWeight) {
                return toy.id;
            }
        }

        return -1;
    }
}