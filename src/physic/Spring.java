package physic;

import Objects.Color2;
import Objects.Rectangle;
import app.MainController;
import javafx.scene.shape.Circle;

import static Objects.DrawRectangle.drawRectangle;
import static Objects.Rectangle.getRectanglePos;
import static app.MainController.*;
import static physic.Collision.checkSpringButton;
import static physic.Collision.checkSpringCollision;
import static physic.CollisionHandling.*;
import static physic.Fall.checkSpringBlue;
import static physic.Fall.checkSpringRed;
import static physic.Physics.*;

public class Spring {
    public static boolean federSprung = false;

    //Kontrollieren, ob die Bedingungen der Energieabgabe der Feder gegeben ist
    public static void controllSpring(Circle circleRed, Circle circleBlue) {

        //Abfragen, ob die Rote Kugel sich bei der Feder befindet
        checkSpringBlue = checkSpringCircleExist(circleBlue, recSpring);
        //Abfragen, ob der Button ausgelöst wurde
        boolean collisionButton = checkSpringButtonCollision();
        //Falls ja und der Button gedrückt wurde, wird die Geschwindigkeit an die Kugel übergeben
        if (checkSpringBlue == true && collisionButton == true) {
            checkRollBlue = false;
            checkFallBlue = true;
            vecStartSpeedObject.set(1, springSpeed(masseBlueCircle));
            deltaVyObject = springSpeed(masseBlueCircle);
        }

        //Abfragen, ob die Blaue Kugel sich bei der Feder befindet
        checkSpringRed = checkSpringCircleExist(circleRed, recSpring);
        //Falls ja und der Button gedrückt wurde, wird die Geschwindigkeit an die Kugel übergeben
        if (checkSpringRed == true && collisionButton == true) {
            checkRollRed = false;
            checkFallRed = true;
            vecStartSpeedCircle.set(1, springSpeed(masseRedCircle));
            deltaVy = springSpeed(masseRedCircle);
        }

    }

    //Wenn die Feder entspannt ist, wird die Energie der Kugel an die Kugel verlustfrei zurückgegeben
    //Dadurch springt die Kugel verlustfrei auf der Feder
    public static void controllSpringRelaxed(){
        if (checkSpringRed == true && checkSpringCollision == true && federSprung == true) {

            double vx = springRelaxedSpeed(masseRedCircle, saveVx, saveVy).get(0);
            double vy = springRelaxedSpeed(masseRedCircle, saveVx, saveVy).get(1);

            vecStartSpeedCircle.set(1, vy);
            vecStartSpeedCircle.set(0, vx);
            deltaVx = 0.0;
            deltaVy = 0.0;
            checkSpringCollision = false;
        }
        if (checkSpringBlue == true && checkSpringCollision == true && federSprung == true) {

            double vx = springRelaxedSpeed(masseBlueCircle, saveVx2, saveVy2).get(0);
            double vy = springRelaxedSpeed(masseBlueCircle, saveVx2, saveVy2).get(1);

            vecStartSpeedObject.set(1, vy);
            vecStartSpeedObject.set(0, vx);
            deltaVxObject = 0.0;
            deltaVyObject = 0.0;
            checkSpringCollision = false;
        }
    }
    //Kontrollieren, ob sich die Kugel bei der Feder befindet, damit nur die Kugel weggeschossen wird, die sich
    //auch bei der Vorrichtung befindet.
    public static boolean checkSpringCircleExist(Circle circle, Rectangle rec) {
        double toleranz = 5;
        if (circle.getLayoutY() >= rec.getP4().get(1) - circle.getRadius() - toleranz
                &&
                circle.getLayoutY() <= rec.getP4().get(1) + circle.getRadius() + toleranz
                &&
                circle.getLayoutX() - circle.getRadius() < rec.getP3().get(0)
                &&
                circle.getLayoutX() + circle.getRadius() > rec.getP4().get(0)) {
            return true;
        }

        return false;
    }
    //Kontrollieren, ob eine Kugel gegen den Button geflogen ist. Falls ja wird der grüne Button gegen
    //einen roten Button getauscht und true zurückgegeben
    public static boolean checkSpringButtonCollision() {
        if (checkSpringButton == true) {
            //Grünes Rechteck in rotes Rechteck umwandeln, wenn ein Objekt mit dem Button kollidiert
            gc.clearRect(recSpringButton.getP1().get(0), recSpringButton.getP1().get(1),
                    recSpringButton.getP2().get(0) - recSpringButton.getP1().get(0) + 1,
                    recSpringButton.getP3().get(1) - recSpringButton.getP2().get(1) + 1);
            getRectanglePos(recSpringButton, 161, 50, 170, 50, 170, 150, 161, 150);
            drawRectangle(MainController.gc, recSpringButton.getP1(), recSpringButton.getP2(),
                    recSpringButton.getP3(), recSpringButton.getP4(), Color2.red, 0);
            count++;
            if (count < 3) {

                return true;
            } else {
                checkSpringButton = false;
                federSprung = true;
                return false;
            }

        }
        return false;
    }
}
