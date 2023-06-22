package com.gui.test.server.commands;

import com.gui.test.common.product.Product;
import com.gui.test.server.MainCollection;
import com.gui.test.server.database.DBParser;
import com.gui.test.server.database.Migrations;

import java.sql.SQLException;
import java.util.TreeMap;

/**
 * Clear colleciton
 */
public class ClearCommand extends ServerCommand {
    DBParser parser = new DBParser(DBParser.migrations);
    
    public ClearCommand() {
    }
    
    /**
     * @param id this id is not used
     */
    
    public String execute(Integer id, Product product, String login) throws SQLException {
        this.migrations.clearProductTable(migrations.getUserId(login));
        parser.parse();
        return "Очищены элементы коллекции, принадлежащие пользователю " + login + "!";
    }
}