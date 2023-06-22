package com.gui.test.server;

import com.gui.test.common.product.Product;
import com.gui.test.common.exception.FalseValuesException;
import com.gui.test.common.exception.UniqueException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.TreeMap;


@Deprecated
public class JSONParser {
    private final Gson gson;

    public JSONParser() {
        this.gson = new GsonBuilder()
        .setPrettyPrinting()
        .serializeNulls()
        .create();
    }

    /**
     * this method write collection to JSON file
     *
     * @param products collection of products
     */
    public void parseToFile(TreeMap<Integer, Product> products) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MainCollection.getFileName()))) {
            gson.toJson(products, bw);
        } catch (IOException exception) {
            throw new IOException(exception.getMessage());
        } catch (IllegalArgumentException exception) {
            System.out.println("Файл был поврежден, введено невалидное значение поля!");
        }
    }

    /**
     * this method fills collection values out of file
     *
     * @return TreeMap<Integer, Product>
     */
    public TreeMap<Integer, Product> parse() throws FileNotFoundException, FalseValuesException, NumberFormatException, UniqueException {
        BufferedReader br = new BufferedReader(new FileReader(MainCollection.getFileName()));
        Type itemsTreeMapType = new TypeToken<TreeMap<Integer, Product>>() {}.getType();
        try {
            TreeMap<Integer, Product> collection = new Gson().fromJson(br, itemsTreeMapType);
            new ServerValidator().checkFile(collection);
            return collection;
        } catch (JsonSyntaxException exception) {
            System.out.println(exception.getMessage());
            return MainCollection.wrongJSON();
        } catch (NullPointerException ignored) {
        }
        return null;
    }
}
