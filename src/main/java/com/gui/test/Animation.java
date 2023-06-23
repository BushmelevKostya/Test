package com.gui.test;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

/** Rotates images round pivot points and places them in a canvas */
public class Animation {
	private static final int GRID_SIZE = 100;
	private static final int DURATION = 2000;
	
	private void rotate(GraphicsContext gc, double angle, double px, double py) {
		Rotate r = new Rotate(angle, px, py);
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
	}
	public void drawRotatedImage(GraphicsContext gc, Image image, double angle, double tlpx, double tlpy) {
		gc.save();
		rotate(gc, angle, tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2);
		gc.drawImage(image, tlpx, tlpy);
		gc.restore();
	}
	
	public void start(Stage stage, ArrayList<Integer> angle, ArrayList<Integer> tipx, ArrayList<Integer> tlpy) {
		Image image = new Image(
				"https://image.pngaaa.com/505/2459505-middle.png", 100, 60, true, true
		);
		
		Canvas canvas = new Canvas(1200, 800);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		for (int i = 0; i < angle.size(); i++) {
			drawRotatedImage(gc, image, angle.get(i), tipx.get(i), tlpy.get(i));
		}
		
		StackPane stack = new StackPane();
		stack.setMaxSize(canvas.getWidth(), canvas.getHeight());
		stack.setStyle("-fx-background-image: url('https://se.ifmo.ru/o/helios-theme/images/ducks-1.jpeg');");
		stack.getChildren().add(
				canvas
		);
		
		// places a resizable padded frame around the canvas.
		StackPane frame = new StackPane();
		frame.setPadding(new Insets(20));
		frame.getChildren().add(stack);
		
		stage.setScene(new Scene(frame, Color.BURLYWOOD));
		stage.show();
	}
}
