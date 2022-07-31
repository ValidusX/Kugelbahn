package data;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
//Verlaufslinie der Kugel zeichnen
public class HistoryLine {
    //Verlaufslinie rote Kugel
    public static void drawLine(Circle circle, GraphicsContext gc) {
        gc.setStroke(new Color(0.9, 0.35, 0.4, 1));
        gc.setFill(new Color(0.9, 0.35, 0.4, 1));
        gc.setGlobalAlpha(1);
        gc.setLineWidth(6);
        gc.strokeLine(circle.getLayoutX(), circle.getLayoutY(), circle.getLayoutX(),
                circle.getLayoutY());
    }

    //Verlaufslinie blaue Kugel
    public static void drawLine2(Circle circle, GraphicsContext gc) {
        gc.setStroke(new Color(0.3, 0.4, 0.97, 1));
        gc.setFill(new Color(0.3, 0.4, 0.97, 1));
        gc.setGlobalAlpha(1);
        gc.setLineWidth(4);
        gc.strokeLine(circle.getLayoutX(), circle.getLayoutY(), circle.getLayoutX(),
                circle.getLayoutY());
    }
    //Verlaufslinie graue Kugel
    public static void drawLine3(Circle circle, GraphicsContext gc) {
        gc.setStroke(new Color(0.73, 0.78, 0.87, 1));
        gc.setFill(new Color(0.73, 0.78, 0.87, 1));
        gc.setGlobalAlpha(1);
        gc.setLineWidth(4);
        gc.strokeLine(circle.getLayoutX(), circle.getLayoutY(), circle.getLayoutX(),
                circle.getLayoutY());
    }
}
