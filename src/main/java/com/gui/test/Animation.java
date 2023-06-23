package com.gui.test;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

/** Rotates images round pivot points and places them in a canvas */
public class Animation extends Application {
	/**
	 * Sets the transform for the GraphicsContext to rotate around a pivot point.
	 *
	 * @param gc    the graphics context the transform to applied to.
	 * @param angle the angle of rotation.
	 * @param px    the x pivot co-ordinate for the rotation (in canvas co-ordinates).
	 * @param py    the y pivot co-ordinate for the rotation (in canvas co-ordinates).
	 */
	private void rotate(GraphicsContext gc, double angle, double px, double py) {
		Rotate r = new Rotate(angle, px, py);
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
	}
	
	/**
	 * Draws an image on a graphics context.
	 * <p>
	 * The image is drawn at (tlpx, tlpy) rotated by angle pivoted around the point:
	 * (tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2)
	 *
	 * @param gc    the graphics context the image is to be drawn on.
	 * @param angle the angle of rotation.
	 * @param tlpx  the top left x co-ordinate where the image will be plotted (in canvas co-ordinates).
	 * @param tlpy  the top left y co-ordinate where the image will be plotted (in canvas co-ordinates).
	 */
	public void drawRotatedImage(GraphicsContext gc, Image image, double angle, double tlpx, double tlpy) {
		gc.save(); // saves the current state on stack, including the current transform
		rotate(gc, angle, tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2);
		gc.drawImage(image, tlpx, tlpy);
		gc.restore(); // back to original state (before rotation)
	}
	
	@Override
	public void start(Stage stage) {
		Image image = new Image(
				"https://image.pngaaa.com/505/2459505-middle.png", 100, 60, true, true
		);
		
		// creates a canvas on which rotated images are rendered.
		Canvas canvas = new Canvas(1200, 800);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		drawRotatedImage(gc, image, 0, 50, 50);
		drawRotatedImage(gc, image, 180, 300, 300);
		
		// supplies a tiled background image on which the canvas is drawn.
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
		System.out.println(1);
	}
	
	public void start() {
		launch(Animation.class);
	}
}
