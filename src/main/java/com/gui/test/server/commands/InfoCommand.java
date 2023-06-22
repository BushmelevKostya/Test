package com.gui.test.server.commands;

import com.gui.test.common.product.Product;
import com.gui.test.server.MainCollection;
import com.gui.test.server.commands.ServerCommand;

/**
 * Print info about collection
 */
public class InfoCommand extends ServerCommand {
    public InfoCommand() {
    }

    /**
     * @param id isn't used
     */
    @Override
    public String execute(Integer id, Product product, String login) {
        addToResponse("Класс коллекции: " + MainCollection.getType() + "\n");
        addToResponse("Время создания коллекции: " + MainCollection.getTime() + "\n");
        addToResponse("В коллекции элементов: " + MainCollection.getSize());
        return response;
    }
}
