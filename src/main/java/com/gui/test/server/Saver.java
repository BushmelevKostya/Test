package com.gui.test.server;

import java.io.IOException;

import static java.lang.System.exit;


@Deprecated
public class Saver{
    public Saver() {
    }
    
    public static void save() {
        JSONParser parser = new JSONParser();
        try {
            parser.parseToFile(MainCollection.getCollection());
            System.out.println("Коллекция успешно сохранена!");
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            exit(0);
        }
    }
}