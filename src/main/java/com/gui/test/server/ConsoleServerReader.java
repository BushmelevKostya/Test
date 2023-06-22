package com.gui.test.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.System.exit;

public class ConsoleServerReader implements Runnable{
	private final BufferedReader reader;
	
	public ConsoleServerReader() {
		reader = new BufferedReader(new InputStreamReader(System.in));
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				String input = reader.readLine().strip();
				if ("exit".equals(input)) exit(0);
				else System.out.println("Неверный ввод\nexit - завершить работу сервера");
			} catch (IOException exception) {
				System.out.println(exception.getMessage());;
			}
		}
	}
}
