package com.gui.test.client.commands;

import com.gui.test.client.ClientCommand;

import static java.lang.System.exit;

public class ExitCommand extends ClientCommand {

    public ExitCommand() {
    }

    @Override
    public void execute(String id) {
        exit(0);
    }
}
