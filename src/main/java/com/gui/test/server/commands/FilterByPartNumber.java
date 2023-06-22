package com.gui.test.server.commands;

import com.gui.test.common.product.Product;
import com.gui.test.server.MainCollection;

/**
 * Print all objects with a given PartNumber
 */
public class FilterByPartNumber extends ServerCommand {
    
    public FilterByPartNumber() {
    }

    /**
     * during execution need to enter value of PartNumber
     *
     * @param id is not used
     */
    @Override
    public String execute(Integer id, Product product, String login) {
    
        MainCollection.getCollection().entrySet().stream()
            .filter(entry -> entry.getValue().getPartNumber().equals(id.toString()))
            .forEach(entry -> addToResponse(entry.getKey() + " ->\n" + entry.getValue() + "\n"));
        
        return response;
    }
    

}