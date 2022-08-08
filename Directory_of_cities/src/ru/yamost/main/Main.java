package ru.yamost.main;


import ru.yamost.main.models.City;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    final static String filePath = "resources/data.csv";

    public static void main(String[] args) {
        List<City> citiesList = fillDataClass();
        //printList(citiesList); // Вывод результата получения данных из файла.
        //sortByCityName(citiesList); // Сортировка списка городов по наименованию в алфавитном порядке по убыванию без учета регистра.
        //printList(citiesList); // Вывод результата сортировки.
        //sortByCityNameAndDistrict(citiesList);
        //printList(citiesList);
        /*int[] arrayOfPopulations = getArrayPopulation(citiesList); // Получение массива населений из списка городов.
        int[] maxPopulation = getMaxPopulation(arrayOfPopulations); // Получение максимального значения населения и его позиции в массиве.
        if (maxPopulation != null) {
            System.out.println("[" + maxPopulation[0] + "] = " + maxPopulation[1]); // Вывод результата.
        }*/
        HashMap<String, Integer> hashMap = getNumOfCitiesInRegions(citiesList); // Получаем количество городов в каждом регионе.
        printNumOfCitiesInRegions(hashMap); // Выводим результат.
    }

    /**
     * Cоздает список модели City, и заполняет его из файла формата .csv.
     *
     * @return Возвращает список с заполненными данными из файла.
     */
    public static List<City> fillDataClass() {
        final List<City> list = new ArrayList<>();
        File file;
        Scanner scanner;
        try { // Пробуем получить файл по path.
            file = new File(filePath);
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) { // Если не получается, сообщаем об ошибке.
            System.out.println("Error! File not found!");
            return null;
        }
        City oneCity;
        scanner.useDelimiter("[;\r]"); // Задаем разделители - ';' и '/r' - символ возврата каретки
        while (scanner.hasNextLine()) {  // Считываем пока есть следующая строка данных, по строке.
            oneCity = new City();
            scanner.next(); // Пропускаем номер города в списке.
            oneCity.setName(scanner.next());
            oneCity.setRegion(scanner.next());
            oneCity.setDistrict(scanner.next());
            oneCity.setPopulation(Integer.parseInt(scanner.next()));
            oneCity.setFoundation(scanner.next());
            list.add(oneCity);
        }
        return list;
    }

    /**
     * Печатает в стандартный вывод каждый элемент списка.
     *
     * @param list Список модели City
     */
    public static void printList(List<City> list) {
        if (list == null) {
            return;
        }
        for (City city : list) {
            System.out.println(city);
        }
    }

    /**
     * Сортировка списка городов по наименованию(city.name) в алфавитном порядке по убыванию без учета регистра.
     *
     * @param list список городов
     */
    public static void sortByCityName(List<City> list) {
        if (list == null) {
            return;
        }
        Comparator<City> comparator = (o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName());
        list.sort(comparator);
    }

    /**
     * Сортировка списка городов по федеральному округу и наименованию города внутри каждого федерального округа
     * в алфавитном порядке по убыванию с учетом регистра;
     *
     * @param list список городов
     */
    public static void sortByCityNameAndDistrict(List<City> list) {
        if (list == null) {
            return;
        }
        Comparator<City> comparator = (o1, o2) -> o1.getDistrict().compareToIgnoreCase(o2.getDistrict());
        list.sort(comparator);
        comparator = (o1, o2) -> {
            if (o1.getDistrict().equalsIgnoreCase(o2.getDistrict()))
                return o1.getName().compareToIgnoreCase(o2.getName());
            else return 0;
        };
        list.sort(comparator);
    }

    /**
     * Возвращает преобразованный список городов в массив populations.
     * @param list Список городов
     * @return Массив populations из списка городов.
     */
    public static int[] getArrayPopulation(List<City> list) {
        if (list == null) {
            return null;
        }
        int size = list.size();
        int[] arrayOfPopulation = new int[size];
        for (int i = 0; i < size; i++) {
            arrayOfPopulation[i] = list.get(i).getPopulation();
        }
        return arrayOfPopulation;
    }

    /**
     *  Находит максимальное значения в массиве и возвращает его позицию в массиве и значение
     * @param populations Массив населений всех городов.
     * @return Массив в котором первый элемент - индекс максимального значения.
     * Второй элемент - максимальное значение.
     */
    public static int[] getMaxPopulation(int[] populations) {
        if (populations == null) {
            return null;
        }
        int size = populations.length;
        int[] maxPopulation = {-1, Integer.MIN_VALUE}; // 0 - index, 1 - value.
        for (int i = 0; i < size; i++) {
            if (populations[i] > maxPopulation[1]) {
                maxPopulation[0] = i; // Запоминаем индекс.
                maxPopulation[1] = populations[i]; // Запоминаем значение.
            }
        }
        return maxPopulation;
    }

    /**
     *
     * @param list Список городов России.
     * @return Возвращает HashMap, где key - регион, а value - количество город в нем.
     */
    public static HashMap<String, Integer> getNumOfCitiesInRegions(List<City> list) {
        if (list == null) {
            return null;
        }
        HashMap<String, Integer> hashMap = new HashMap<>();
        Set<String> keys = new HashSet<>(); // Множество ключей для более быстрой проверки на вхождение.
        List<String> keysList = new ArrayList<>(); // Список из ключей к map, чтобы можно было связать с позицией.
        String region;
        for (City city : list) {
            region = city.getRegion();
            if (!keys.contains(region)) { // Если нет в множестве.
                keys.add(region); // Добавляем регион в множество.
                keysList.add(region); // Добавляем регион в список для существования порядка.
            }
        }
        int keyListSize = keys.size();
        int[] NumOfCitiesInRegions = new int[keyListSize]; // Список значений для map
        for (City city : list) {
            // Прибавляем в тот же индекс где находится текстовая запись региона.
            // Тем самым связываем список из ключей и их значений.
            NumOfCitiesInRegions[keysList.indexOf(city.getRegion())]++;
        }
        for (int i = 0; i < keyListSize; i++) { // Заполняем HashMap.
            hashMap.put(keysList.get(i), NumOfCitiesInRegions[i]);
        }
        return hashMap;
    }

    /**
     * Печает все значения из HashMap в определенном формате
     * @param hashMap HashMap, где key - регион, а value - количество город в нем.
     */
    public static void printNumOfCitiesInRegions(HashMap<String, Integer> hashMap) {
        hashMap.forEach((s, integer) -> System.out.println(s + " - " + integer));
    }
}