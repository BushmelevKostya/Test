package com.gui.test.client.builder;

import com.gui.test.common.product.Product;
import com.gui.test.common.exception.ExitException;

import java.io.IOException;

public interface Builder {
    void reset();

    void reset(Product product);

    void setBName() throws ExitException, IOException;

    void setBCoordinates() throws ExitException;

    void setBCreationDate();

    void setBPrice() throws ExitException;

    void setBPartNumber() throws ExitException;

    void setBManufactureCost() throws ExitException;

    void setBUnitOfMeasure() throws ExitException, IOException;

    void setBOrganization() throws ExitException, IOException;

    /**
     * @return Product
     */
    Product getResult();
}