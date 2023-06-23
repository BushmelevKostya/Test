package com.gui.test.client.commands;

import com.gui.test.client.ClientCommand;
import com.gui.test.client.ClientExecutor;
import com.gui.test.common.exception.ExitException;

import java.util.Stack;

/**
 * Execute script out of file
 * name of file - Script.txt
 */
public class ExecuteClientCommand extends ClientCommand {
    public static Stack<String> stack = new Stack<>();

    /**
     * during execution need to enter name of file
     *
     * @param value this id is not used
     */
    @Override
    public void execute(String value) throws ExitException {
        if (stack.contains(value)){
            System.out.println("Скрипты вызываются бесконечно!");
            throw new ExitException();
        }
        stack.push(value);
        ClientExecutor.getClientExecutor().runScript(value);
        stack.pop();
    }
}
