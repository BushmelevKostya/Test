package com.gui.test;

import com.gui.test.common.product.Product;
import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

/** Rotates images round pivot points and places them in a canvas */
public class Animation {
	Image image;
	GraphicsContext gc;
	ArrayList<Product> products;
	int CENTERX;
	int CENTERY;
	Canvas canvas;
	public void drawRotatedImage(double tlpx, double tlpy, int angle) {
		gc.save();
		rotate(angle, tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2);
		gc.drawImage(image, tlpx, tlpy);
		gc.restore();
	}
	
	public void start(Stage stage, ArrayList<Integer> angle, ArrayList<Integer> tipx, ArrayList<Integer> tlpy, ArrayList<Product> products) {
		canvas = new Canvas(1200, 600);
		
		CENTERX = (int) canvas.getWidth()/2;
		CENTERY = (int) canvas.getHeight()/2;
		
		for (int i = 0; i < tipx.size(); i++) {
			tipx.set(i, tipx.get(i) + CENTERX);
			tlpy.set(i, tlpy.get(i) + CENTERY);
		}
		
		this.products = products;
		image = new Image("https://image.pngaaa.com/505/2459505-middle.png", 100, 60, true, true);
		gc = canvas.getGraphicsContext2D();
		StackPane stack = new StackPane();
		stack.setMaxSize(canvas.getWidth(), canvas.getHeight());
		stack.setStyle("-fx-background-image: url('https://se.ifmo.ru/o/helios-theme/images/ducks-1.jpeg');");
		stack.getChildren().add(
				canvas
		);
		
		StackPane frame = new StackPane();
		frame.setPadding(new Insets(20));
		frame.getChildren().add(stack);
		
		stage.setScene(new Scene(frame, Color.BURLYWOOD));
		
		drawScene(tipx, tlpy, 0, 0);
		
		canvas.setOnMouseClicked(event -> {
			
			double mouseX = event.getX();
			double mouseY = event.getY();
			
			for (int i = 0; i < tipx.size(); i++) {
				double newTlpx = tipx.get(i);
				double newTlpy = tlpy.get(i);
				double width = image.getWidth();
				double height = image.getHeight();
				
				if (mouseX >= newTlpx && mouseX <= newTlpx + width && mouseY >= newTlpy && mouseY <= newTlpy + height) {
					animation(tipx, tlpy, i);
					
					break;
				}
			}
		});
		
		stage.show();
	}
	
	
	private void showInfo(int i) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Объект " + (i + 1));
		alert.setHeaderText("Информация об объекте");
		var message = products.get(i).toString();
		
		alert.setContentText(message);
		
		alert.show();
	}
	public void animation(ArrayList<Integer> x, ArrayList<Integer> y, int index) {
		long startNanoTime = System.nanoTime();
		final int[] angle = {0};
		new AnimationTimer() {
			public void handle(long currentNanoTime) {
				
				changeScene(x, y, index, angle[0]);
				
				angle[0] += 8;

				if (angle[0] >= 360) {
					stop();
					changeScene(x, y, index, 0);
					showInfo(index);
				}
			}
		}.start();
	}
	
	private void changeScene(ArrayList<Integer> x, ArrayList<Integer> y, int index, int angle) {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.save();
		drawScene(x, y, index, angle);
		gc.restore();
	}
	private void rotate(double angle, double px, double py) {
		gc.translate(px, py);
		gc.rotate(angle);
		gc.translate(-px, -py);
	}
	
	public void drawScene(ArrayList<Integer> tipx, ArrayList<Integer> tlpy, int index, int angle) {
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(4.0);
		
		gc.strokeLine(CENTERX, 0, CENTERX, canvas.getHeight());
		gc.strokeLine(0, CENTERY, canvas.getWidth(), CENTERY);
		
		for (int i = 0; i < tipx.size(); i++) {
			if (i == index) drawRotatedImage(tipx.get(i), tlpy.get(i), angle);
			else drawRotatedImage(tipx.get(i), tlpy.get(i), 0);
		}
	}
}
