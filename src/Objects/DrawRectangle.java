package Objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Vector;

public class DrawRectangle {

    //p1 unten links - p2 unten rechts - p3 oben rechts - p4 oben links
    public static void drawRectangle(GraphicsContext gc, Vector<Double> p1, Vector<Double> p2,
                                     Vector<Double> p3, Vector<Double> p4, Color color,double angle) {
        gc.setStroke(color);
        gc.setFill(color);
        if(angle==0) {
            gc.fillRect(p1.get(0), p1.get(1), p2.get(0) - p1.get(0), p4.get(1) - p1.get(1));
        }
        gc.restore();
        gc.setGlobalAlpha(1);
        gc.setLineWidth(1);
        gc.beginPath();
        gc.moveTo(p1.get(0), p1.get(1));
        gc.lineTo(p2.get(0), p2.get(1));
        gc.lineTo(p3.get(0), p3.get(1));
        gc.lineTo(p4.get(0), p4.get(1));
        gc.lineTo(p1.get(0), p1.get(1));
        gc.stroke();
    }

}
