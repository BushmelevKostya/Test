package com.gui.test;

import javafx.stage.Stage;
import javafx.stage.Window;

public class WindowController {
	public  WindowController(){}
	public static Stage getStageFromWindow(Window window) {
			return (Stage) window;
	}
}
