package com.gui.test.server.commands;

import com.gui.test.common.product.Organization;
import com.gui.test.common.product.Product;
import com.gui.test.server.MainCollection;
import com.gui.test.server.commands.ServerCommand;

import java.util.ArrayList;

/**
 * Print information about all manufacturers
 */
public class PrintFieldAscendingManufacturer extends ServerCommand {

    public PrintFieldAscendingManufacturer() {
    }

    /**
     * @param id isn't used
     */
    @Override
    public String execute(Integer id, Product product, String login) {
        MainCollection.getCollection().values().stream()
                .map(Product::getManufacturer)
                .sorted(Organization::compareTo)
                .forEach(organization -> addToResponse(organization.toString() + "\n"));
        return response;
    }
}
