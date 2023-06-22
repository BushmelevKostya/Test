package com.gui.test.common.product;

import java.io.Serializable;

/**
 * defines location object Product in space
 *
 * @see Product
 */
public class Coordinates implements Serializable {
    /**
     * value this field must be less than -230
     */
    private long x;
    /**
     * Field can be null
     * value this field must be more than 702
     */
    private Long y;

    /**
     * @param x coordinate x of object Product
     * @param y coordinate x of object Product
     */
    public Coordinates(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates() {
    }

    public void setX(long x) {
        this.x = x;
    }

    public void setY(long y) {
        this.y = y;
    }

    public long getX() {
        return x;
    }

    public Long getY() {
        return y;
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}