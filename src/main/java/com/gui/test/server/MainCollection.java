package com.gui.test.server;

import com.gui.test.common.product.Product;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * In this class stored collection of objects
 *
 * @see Product
 */

public class MainCollection extends TreeMap<Integer, Product> {
    static private TreeMap<Integer, Product> collection = new TreeMap<>();
    static final String inicializationTime = new Date().toString();

    static private String fileName;

    public MainCollection() {
    }

    synchronized public static Object getType() {
        return collection.getClass();
    }

    synchronized public static String getTime() {
        return inicializationTime;
    }

    synchronized public static int getSize() {
        return collection.size();
    }

    synchronized public static TreeMap<Integer, Product> getCollection() {
        return collection;
    }

    synchronized public static void setCollection(TreeMap<Integer, Product> collection) {
        MainCollection.collection = collection;
    }

    synchronized public static void setFileName(String fileName) {
        MainCollection.fileName = fileName;
    }

    synchronized public static String getFileName() {
        return fileName;
    }


    /**
     * @return StringBuilder
     */
    synchronized public static String printCollection() {
        StringBuilder stringBuilder = new StringBuilder();
        collection.forEach((key, value) ->
        stringBuilder.append("key= ").append(key).append("\n").append(collection.get(key).toString()).append("\n").append("\n"));
        if (collection.size() == 0) {
            return "Коллекция пуста!";
        } else {
            return stringBuilder.toString();
        }
    }

    synchronized public static TreeMap<Integer, Product> notFoundCollection() {
        System.out.println("Указанный файл не существует, хотите создать новую коллекцию?\nyes/no");
        return initializeCollection();
    }

    synchronized public static TreeMap<Integer, Product> isNullCollection(TreeMap<Integer, Product> collection) {
        if (collection == null) {
            System.out.println("Указанный файл пуст, хотите создать новую коллекцию?\nyes/no");
            return initializeCollection();
        }
        return collection;
    }

    synchronized public static TreeMap<Integer, Product> wrongJSON() {
        System.out.println("Файл поврежден, проверьте наличие всех полей в файле и синтаксис json.");
        System.out.println("Хотите создать новую коллекцию?\nyes/no");
        return initializeCollection();
    }

    synchronized public static void wrongType() {
        System.out.println("Файл поврежден, указан неверный тип данных!");
        System.out.println("Хотите создать новую коллекцию?\nyes/no");
        initializeCollection();
    }

    synchronized public static TreeMap<Integer, Product> initializeCollection() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                String str = br.readLine();
                if (str.equals("yes")) {
                    return new TreeMap<>();
                } else if (str.equals("no")) {
                    System.exit(0);
                }
                System.out.println("Неверный ввод!");
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }
}
