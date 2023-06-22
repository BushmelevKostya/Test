package com.gui.test.client;

import com.gui.test.common.exception.*;
import com.gui.test.common.product.Product;

import java.io.IOException;

public abstract class ClientCommand {
    protected Product product;
    public abstract void execute(String value) throws ExitException, FalseValuesException, NumberFormatException, UniqueException, NullStringException, IOException, FalseTypeException, InfiniteException;
    
    public Product getProduct() {
        return product;
    }
}