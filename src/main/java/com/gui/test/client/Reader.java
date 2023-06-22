package com.gui.test.client;

/**
 * Replace String to command and key. If string have not key, field id is null
 */
public class Reader {
    private String[] data;
    private String command;
    private String value;

    /**
     * @param data String with command and key
     */
    public Reader(String data) throws ArrayIndexOutOfBoundsException {
        try {
            String[] list = data.trim().split(" ");
            this.data = list;
            this.command = list[0];
            this.value = list[1];
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    public String getCommand() {
        return command;
    }

    public String[] getData() {
        return data;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
