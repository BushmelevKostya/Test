package com.gui.test;

import javafx.animation.AnimationTimer;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TestApplication extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		Image image = new Image("https://i.pinimg.com/236x/8a/de/fe/8adefe5af862b4f9cec286c6ee4722cb.jpg");
		double durationSeconds = 4.0; // продолжительность анимации в секундах
		animateImage(image, durationSeconds, primaryStage);
	}
	
	public void animateImage(Image image, double durationSeconds, Stage stage) {
		Canvas canvas = new Canvas(image.getWidth(), image.getHeight());
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		StackPane stackPane = new StackPane();
		stackPane.getChildren().add(canvas);
		
		Scene scene = new Scene(stackPane, image.getWidth(), image.getHeight());
		stage.setScene(scene);
		stage.show();
		
		final double[] tlpx = {0}; // начальная позиция X
		final double[] tlpy = {0}; // начальная позиция Y
		final double[] angle = {0}; // начальный угол поворота
		
		long startNanoTime = System.nanoTime();
		new AnimationTimer() {
			public void handle(long currentNanoTime) {
				// Вычисляем время, прошедшее с начала анимации
				double elapsedTime = (currentNanoTime - startNanoTime) / 1_000_000_000.0;
				
				// Очищаем холст
				gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				
				// Рисуем изображение с учетом анимации
				gc.save();
				rotate(gc, angle[0], tlpx[0] + image.getWidth() / 2, tlpy[0] + image.getHeight() / 2);
				gc.drawImage(image, tlpx[0], tlpy[0]);
				gc.restore();
				
				// Обновляем значения позиции и угла
				tlpx[0] += 1; // смещение по X
				tlpy[0] += 1; // смещение по Y
				angle[0] += 1; // изменение угла
				
				// Проверяем, достигнут ли конечный размер и длительность анимации
				if (tlpx[0] >= canvas.getWidth() || tlpy[0] >= canvas.getHeight() || elapsedTime >= durationSeconds) {
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
}
