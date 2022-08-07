package ru.yamost.main;


import ru.yamost.main.models.City;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    final static String filePath = "resources/data.csv";

    public static void main(String[] args) {
        List<City> citiesList = fillDataClass();
        //printList(citiesList); // Вывод результата получения данных из файла
        //sortByCityName(citiesList); // Сортировка списка городов по наименованию в алфавитном порядке по убыванию без учета регистра.
        //printList(citiesList); // Вывод результата сортировки
        sortByCityNameAndDistrict(citiesList);
        printList(citiesList);
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
}