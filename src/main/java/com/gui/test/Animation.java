package com.gui.test;

import com.gui.test.common.product.Product;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
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

/** Rotates images round pivot points and places them in a canvas */
public class Animation {
	
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
	
	public void start(Stage stage, ArrayList<Integer> angle, ArrayList<Integer> tipx, ArrayList<Integer> tlpy, ArrayList<Product> products) {
		Image image = new Image("https://image.pngaaa.com/505/2459505-middle.png", 200, 100, true, true);
		Canvas canvas = new Canvas(1200, 600);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		for (int i = 0; i < angle.size(); i++) {
			drawRotatedImage(gc, image, angle.get(i), tipx.get(i), tlpy.get(i));
		}
		
		canvas.setOnMouseClicked(event -> {
			double mouseX = event.getX();
			double mouseY = event.getY();
			
			for (int i = 0; i < tipx.size(); i++) {
				double newTlpx = tipx.get(i);
				double newTlpy = tlpy.get(i);
				double width = image.getWidth();
				double height = image.getHeight();
				
				if (mouseX >= newTlpx && mouseX <= newTlpx + width && mouseY >= newTlpy && mouseY <= newTlpy + height) {
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
