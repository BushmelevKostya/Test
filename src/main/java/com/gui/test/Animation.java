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
	
	Canvas canvas;
	public void drawRotatedImage(double tlpx, double tlpy, int angle) {
		gc.save();
		rotate(gc, angle, tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2);
		gc.drawImage(image, tlpx, tlpy);
		gc.restore();
	}
	
	public void start(Stage stage, ArrayList<Integer> angle, ArrayList<Integer> tipx, ArrayList<Integer> tlpy, ArrayList<Product> products) {
		canvas = new Canvas(1200, 600);
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
		
//			animation(gc, canvas, image, tipx.get(i), tlpy.get(i));
			
			double mouseX = event.getX();
			double mouseY = event.getY();
			
			for (int i = 0; i < tipx.size(); i++) {
				double newTlpx = tipx.get(i);
				double newTlpy = tlpy.get(i);
				double width = image.getWidth();
				double height = image.getHeight();
				
				if (mouseX >= newTlpx && mouseX <= newTlpx + width && mouseY >= newTlpy && mouseY <= newTlpy + height) {
					animation(tipx, tlpy, i);
					
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Объект " + (i + 1));
					alert.setHeaderText("Информация об объекте");
					var message = products.get(i).toString();
					
					alert.setContentText(message);
					
					alert.showAndWait();
					
					break;
				}
			}
		});
		
		stage.show();
	}
	
	public void animation(ArrayList<Integer> x, ArrayList<Integer> y, int index) {
		long startNanoTime = System.nanoTime();
		final int[] angle = {0};
		int durationSeconds = 4;
		new AnimationTimer() {
			public void handle(long currentNanoTime) {
				// Вычисляем время, прошедшее с начала анимации
				double elapsedTime = (currentNanoTime - startNanoTime) / 1_000_000_000.0;

				// Очищаем холст
				gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

				// Рисуем изображение с учетом анимации
				gc.save();
				drawScene(x, y, index, angle[0]);
				gc.restore();

				// Обновляем значения позиции и угла
				angle[0] += 1; // изменение угла

				// Проверяем, достигнут ли конечный размер и длительность анимации
				if (elapsedTime >= durationSeconds) {
					stop(); // останавливаем анимацию
				}
			}
		}.start();

	}
	private void rotate(GraphicsContext gc, double angle, double px, double py) {
		gc.translate(px, py);
		gc.rotate(angle);
		gc.translate(-px, -py);
	}
	
	public void drawScene(ArrayList<Integer> tipx, ArrayList<Integer> tlpy, int index, int angle) {
		gc.setStroke(Color.LIGHTGRAY);
		gc.setLineWidth(1.0);
		
		var gridSize = 50;
		
		for (double x = 0; x <= canvas.getWidth(); x += gridSize) {
			gc.strokeLine(x, 0, x, canvas.getHeight());
		}
		
		for (double y = 0; y <= canvas.getHeight(); y += gridSize) {
			gc.strokeLine(0, y, canvas.getWidth(), y);
		}
		
		for (int i = 0; i < tipx.size(); i++) {
			if (i == index) drawRotatedImage(tipx.get(i), tlpy.get(i), angle);
			else drawRotatedImage(tipx.get(i), tlpy.get(i), 0);
		}
	}
}
