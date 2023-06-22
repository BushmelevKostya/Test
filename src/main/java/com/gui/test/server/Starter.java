package com.gui.test.server;

import com.gui.test.common.exception.FalseValuesException;
import com.gui.test.common.exception.UniqueException;

import java.io.FileNotFoundException;

import static java.lang.System.exit;
@Deprecated
public class Starter {
    private final JSONParser parser;
    private final String filename;

    public Starter(String filename) {
        this.parser = new JSONParser();
        this.filename = filename;
    }

    /**
     * this method use when program start
     */
    public void run() {
        MainCollection.setFileName(this.filename);
        try {
            var collection = parser.parse();
            MainCollection.setCollection(MainCollection.isNullCollection(collection));
        } catch (FileNotFoundException exception) {
            MainCollection.setCollection(MainCollection.notFoundCollection());
        } catch (FalseValuesException | UniqueException exception) {
            System.out.println(exception.getMessage());
            exit(0);
        } catch (NumberFormatException exception) {
            MainCollection.wrongType();
        }
    }
}
