package com.gui.test.common.product;

public enum UnitOfMeasure {
    CENTIMETERS,
    LITERS,
    MILLILITERS,
    GRAMS;

    public static UnitOfMeasure stringToUnitOfMeasure(String string) {
        UnitOfMeasure unitOfMeasure;
        switch (string) {
            case "CENTIMETERS" -> unitOfMeasure = CENTIMETERS;
            case "LITERS" -> unitOfMeasure = LITERS;
            case "MILLILITERS" -> unitOfMeasure = MILLILITERS;
            default -> unitOfMeasure = GRAMS;
        }
        return unitOfMeasure;
    }
}