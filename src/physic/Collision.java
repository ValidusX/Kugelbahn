package physic;

import Objects.Rectangle;
import javafx.scene.shape.Circle;

import java.util.Vector;

import static app.MainController.*;
import static app.MainController.rec2;
import static physic.CollisionHandling.collisionRectangle;
import static physic.Physics.*;


public class Collision {

    public static double angleLot = 0;
    public static boolean collisionCircleRed = false, collisionCircleBlue = false, checkSpringButton = false,
            angleEck1 = false, angleEck2 = false, checkSpringCollision = false;

    public static boolean collisionCircle(Circle circle, Circle objCircle, double posx, double posy) {
        //Abstand zwischen zwei Punkten
        //d = wurzel( (x2 - x)^2 + (y2 - y1)^2 )
        double d = Math.sqrt(Math.pow(objCircle.getLayoutX() - posx, 2) + Math.pow(objCircle.getLayoutY() - posy, 2));
        //Falls der Abstand kleiner als die beiden Radien zusammen sind, besteht eine Kollision
        if (d < (circle.getRadius() + objCircle.getRadius())) {
            collisionCircle = true;
            return true;
        }
        return false;
    }
    //Kollisionserkennung für gerade Objekte mithilfe des Lotfußpunktes und Hilfsebene
    public static boolean collisionLot(Rectangle rec, Vector<Double> punktA, Vector<Double> punktB,
                                       double posKugelX, double posKugelY, double oldposx, double oldposy,
                                       double circleRadius) {
        Vector<Double> vecKugel;
        vecKugel = vector(posKugelX - oldposx, posKugelY - oldposy);
        //g: vecX = vecA + lambda * vecB
        double[] b = new double[2];
        double[] a = new double[2];

        b[0] = punktB.get(0) - punktA.get(0);
        b[1] = punktB.get(1) - punktA.get(1);

        //d = (|vecA - vecA|) / (|vecB|);
        a[0] = posKugelX - punktA.get(0);
        a[1] = posKugelY - punktA.get(1);

        //Mit Hilfsebene Lotpunkt berechnen
        //Punkt P bzw. Position der Kugel in die Hilfsebene einsetzen
        double H2 = b[0] * posKugelX + b[1] * posKugelY;
        double lambda = ((b[0] * punktA.get(0) + b[1] * punktA.get(1)) - H2) / (b[0] * b[0] + b[1] * b[1]);
        if (lambda < 0) {
            lambda = lambda * -1;
        }

        //Schnittpunkt S bestimmen
        Vector<Double> s = vector(punktA.get(0) + lambda * b[0], punktA.get(1) + lambda * b[1]);

        s = checkSchnittpunkt(rec, punktA, punktB, posKugelX, posKugelY, s);

        //Definieren bis wann sich der Schnittpunkt befinden darf
        //Befindet es sich weiter als die Position des Objektes, besteht keine Kollision
        //X
        if (punktA.get(0) > punktB.get(0) && s.get(0) > punktA.get(0)) {
            return false;
        }
        if (punktB.get(0) > punktA.get(0) && s.get(0) > punktB.get(0)) {
            return false;
        }
        if (punktB.get(0).equals(punktA.get(0)) && vecKugel.get(0) < 0 && s.get(0) > punktA.get(0)) {
            return false;
        }
        if (punktB.get(0).equals(punktA.get(0)) && vecKugel.get(0) > 0 && s.get(0) < punktA.get(0)) {
            return false;
        }

        //Y
        if (punktB.get(1) > punktA.get(1) && s.get(1) > punktB.get(1)) {
            return false;
        }
        if (punktA.get(1) > punktB.get(1) && s.get(1) > punktA.get(1)) {
            return false;
        }
        if (punktA.get(1) > punktB.get(1) && s.get(1) < punktB.get(1)) {
            return false;
        }
        if (punktB.get(1) > punktA.get(1) && s.get(1) < punktA.get(1)) {
            return false;
        }
        if (punktB.get(1).equals(punktA.get(1)) && vecKugel.get(1) < 0 && s.get(1) > punktA.get(1)) {
            return false;
        }
        if (punktB.get(1).equals(punktA.get(1)) && vecKugel.get(1) > 0 && s.get(1) < punktA.get(1)) {
            return false;
        }
        if (punktB.get(1).equals(punktA.get(1)) && punktB.get(0) > punktA.get(0) &&
                s.get(0) > punktB.get(0)) {
            return false;
        }
        if (punktB.get(1).equals(punktA.get(1)) && punktA.get(0) > punktB.get(0) &&
                s.get(0) > punktA.get(0)) {
            return false;
        }
        if (punktB.get(1).equals(punktA.get(1)) && punktB.get(0) > punktA.get(0) &&
                s.get(0) < punktA.get(0)) {
            return false;
        }
        if (punktB.get(1).equals(punktA.get(1)) && punktA.get(0) > punktB.get(0) &&
                s.get(0) < punktB.get(0)) {
            return false;
        }
        //Vektor von Kugel zum Lotpunkt
        Vector<Double> sp = vector(posKugelX - s.get(0), posKugelY - s.get(1));
        double distanz = Math.sqrt(Math.pow(sp.get(0), 2) + Math.pow(sp.get(1), 2));
        //Wenn die Distanz kleiner ist der Radius des Kreises, besteht eine Kollision
        if (distanz < circleRadius && distanz > -circleRadius) {
            collisionRectangle = rec;
            return true;
        } else {
            angleEck1 = false;
            angleEck2 = false;
        }

        return false;
    }

    //Kontrolliert alle Seiten des Rechtecks für eine Kollision
    public static boolean checkCollisionRectangle(Rectangle rec, double posx, double posy, double oldposx, double oldposy,
                                                  double circleRadius) {
        boolean check;
        check = collisionLot(rec, rec.getP1(), rec.getP4(), posx, posy, oldposx, oldposy, circleRadius);
        if (check == true) {
            angleLot = getAngle(rec.getP1(), rec.getP4());
            return true;
        }
        check = collisionLot(rec, rec.getP2(), rec.getP3(), posx, posy, oldposx, oldposy, circleRadius);
        if (check == true) {
            angleLot = getAngle(rec.getP2(), rec.getP3());
            return true;
        }
        check = collisionLot(rec, rec.getP1(), rec.getP2(), posx, posy, oldposx, oldposy, circleRadius);
        if (check == true) {
            angleLot = getAngle(rec.getP1(), rec.getP2());
            return true;
        }
        check = collisionLot(rec, rec.getP3(), rec.getP4(), posx, posy, oldposx, oldposy, circleRadius);
        if (check == true) {
            angleLot = getAngle(rec.getP3(), rec.getP4());
            return true;
        }

        return false;
    }
    //Kontrolliert die Kollision der existierenden Objekte
    public static boolean checkCollision(Circle circle1, double posx, double posy, double oldposx, double oldposy) {

        boolean check;

        check = checkCollisionRectangle(recWorldTopBorder, posx, posy, oldposx, oldposy, circle1.getRadius());
        if (check == true) {
            return true;
        }
        check = checkCollisionRectangle(recWorldRightBorder, posx, posy, oldposx, oldposy, circle1.getRadius());
        if (check == true) {
            return true;
        }
        check = checkCollisionRectangle(recWorldBottomBorder, posx, posy, oldposx, oldposy, circle1.getRadius());
        if (check == true) {
            return true;
        }
        check = checkCollisionRectangle(recWorldLeftBorder, posx, posy, oldposx, oldposy, circle1.getRadius());
        if (check == true) {
            return true;
        }
        //Rec1 = der drehbare Balken
        check = checkCollisionRectangle(rec1, posx, posy, oldposx, oldposy, circle1.getRadius());
        if (check == true) {
            return true;
        }
        check = checkCollisionRectangle(rec2, posx, posy, oldposx, oldposy, circle1.getRadius());
        if (check == true) {
            return true;
        }
        check = checkCollisionRectangle(rec3, posx, posy, oldposx, oldposy, circle1.getRadius());
        if (check == true) {
            return true;
        }
        check = checkCollisionRectangle(rec4, posx, posy, oldposx, oldposy, circle1.getRadius());
        if (check == true) {
            return true;
        }
        //Ebene Fläche der Vorrichtung, wo die Kugel drauf liegt
        check = checkCollisionRectangle(recSpring, posx, posy, oldposx, oldposy, circle1.getRadius());
        if (check == true) {
            checkSpringCollision = true;
            return true;
        }
        check = checkCollisionRectangle(recLeftSpring, posx, posy, oldposx, oldposy, circle1.getRadius());
        if (check == true) {
            return true;
        }
        check = checkCollisionRectangle(recRightSpring, posx, posy, oldposx, oldposy, circle1.getRadius());
        if (check == true) {
            return true;
        }
        //Der grüne Knopf
        check = checkCollisionRectangle(recSpringButton, posx, posy, oldposx, oldposy, circle1.getRadius());
        if (check == true) {
            checkSpringButton = true;
            return true;
        }
        return false;
    }

    //Funktion, um den Schnittpunkt an die Kanten zu setzen
    //Sonst wäre der Schnittpunkt nicht an den Kanten, wenn sich die Kugel außerhalb des Objektes befindet
    public static Vector<Double> checkSchnittpunkt(Rectangle rec, Vector<Double> punktA, Vector<Double> punktB,
                                                   double posKugelX, double posKugelY, Vector<Double> s) {
        //Schnittpunkt zum äußersten Punkt verschieben, wenn sich die Kugel außerhalb des Objektes befindet
        if (rec.equals(rec2)) {

            if (punktB.get(0) < punktA.get(0)
                    &&
                    punktB.get(1) < punktA.get(1)
                    &&
                    posKugelX < punktB.get(0)
                    &&
                    posKugelY > punktB.get(1)) {
                angleEck1 = true;
                return vector(punktB.get(0), punktB.get(1));

            }
            if (punktA.get(0) < punktB.get(0)
                    &&
                    punktA.get(1) < punktB.get(1)
                    &&
                    posKugelX < punktA.get(0)
                    &&
                    posKugelY < punktA.get(1)) {
                angleEck2 = true;
                return vector(punktA.get(0), punktA.get(1));
            }
        }

        //Für den drehbaren Balken
        if (rec.equals(rec1) && angleBalken > 0 && angleBalken < 90) {
            //Links oben
            if (punktB.get(0) < punktA.get(0)
                    &&
                    punktB.get(1) > punktA.get(1)
                    &&
                    posKugelX < punktB.get(0)
                    &&
                    posKugelY > punktB.get(1)) {
                angleEck1 = true;
                return vector(punktB.get(0), punktB.get(1));
            }
            //rechts oben
            if (punktA.get(0) > punktB.get(0)
                    &&
                    punktA.get(1) > punktB.get(1)
                    &&
                    posKugelX > punktA.get(0)
                    &&
                    posKugelY > punktA.get(1)) {
                angleEck2 = true;
                return vector(punktA.get(0), punktA.get(1));
            }
            //rechts unten
            if (punktB.get(0) > punktA.get(0)
                    &&
                    punktB.get(1) > punktA.get(1)
                    &&
                    posKugelX > punktB.get(0)
                    &&
                    posKugelY < punktB.get(1)) {
                angleEck1 = true;
                return vector(punktB.get(0), punktB.get(1));
            }
            //Links unten
            if (punktA.get(0) < punktB.get(0)
                    &&
                    punktA.get(1) < punktB.get(1)
                    &&
                    posKugelX < punktA.get(0)
                    &&
                    posKugelY < punktA.get(1)) {
                angleEck1 = true;
                return vector(punktA.get(0), punktA.get(1));
            }
        }
        if (rec.equals(rec1) && angleBalken > 90 && angleBalken < 180) {
            //Links oben
            if (punktB.get(0) < punktA.get(0)
                    &&
                    punktB.get(1) > punktA.get(1)
                    &&
                    posKugelX < punktB.get(0)
                    &&
                    posKugelY > punktB.get(1)) {
                angleEck1 = true;
                return vector(punktB.get(0), punktB.get(1));
            }
            //rechts oben
            if (punktA.get(0) > punktB.get(0)
                    &&
                    punktA.get(1) > punktB.get(1)
                    &&
                    posKugelX > punktA.get(0)
                    &&
                    posKugelY > punktA.get(1)) {
                angleEck2 = true;
                return vector(punktA.get(0), punktA.get(1));
            }
            //rechts unten
            if (punktA.get(0) < punktB.get(0)
                    &&
                    punktA.get(1) > punktB.get(1)
                    &&
                    posKugelX < punktA.get(0)
                    &&
                    posKugelY < punktA.get(1)) {
                angleEck2 = true;
                return vector(punktA.get(0), punktA.get(1));
            }
            //Links unten
            if (punktB.get(0) > punktA.get(0)
                    &&
                    punktB.get(1) < punktA.get(1)
                    &&
                    posKugelX > punktB.get(0)
                    &&
                    posKugelY < punktB.get(1)) {
                angleEck1 = true;
                return vector(punktB.get(0), punktB.get(1));
            }
        }
        if (rec.equals(rec1) && angleBalken > 180 && angleBalken < 270) {
            //Links oben
            if (punktB.get(0) < punktA.get(0)
                    &&
                    punktB.get(1) < punktA.get(1)
                    &&
                    posKugelX < punktB.get(0)
                    &&
                    posKugelY > punktB.get(1)) {
                angleEck1 = true;
                return vector(punktB.get(0), punktB.get(1));
            }
            //rechts oben
            if (punktA.get(0) > punktB.get(0)
                    &&
                    punktA.get(1) > punktB.get(1)
                    &&
                    posKugelX > punktA.get(0)
                    &&
                    posKugelY > punktA.get(1)) {
                angleEck2 = true;
                return vector(punktA.get(0), punktA.get(1));
            }
            //rechts unten
            if (punktB.get(0) > punktA.get(0)
                    &&
                    punktB.get(1) > punktA.get(1)
                    &&
                    posKugelX > punktB.get(0)
                    &&
                    posKugelY < punktB.get(1)) {
                angleEck1 = true;
                return vector(punktB.get(0), punktB.get(1));
            }
            //Links unten
            if (punktA.get(0) < punktB.get(0)
                    &&
                    punktA.get(1) < punktB.get(1)
                    &&
                    posKugelX < punktA.get(0)
                    &&
                    posKugelY < punktA.get(1)) {
                angleEck1 = true;
                return vector(punktA.get(0), punktA.get(1));
            }
        }
        if (rec.equals(rec1) && angleBalken > 270 && angleBalken < 360) {
            //Links oben
            if (punktB.get(0) < punktA.get(0)
                    &&
                    punktB.get(1) > punktA.get(1)
                    &&
                    posKugelX < punktB.get(0)
                    &&
                    posKugelY > punktB.get(1)) {
                angleEck1 = true;
                return vector(punktB.get(0), punktB.get(1));
            }
            //rechts oben
            if (punktA.get(0) > punktB.get(0)
                    &&
                    punktA.get(1) < punktB.get(1)
                    &&
                    posKugelX > punktA.get(0)
                    &&
                    posKugelY > punktA.get(1)) {
                angleEck2 = true;
                return vector(punktA.get(0), punktA.get(1));
            }
            //rechts unten
            if (punktB.get(0) > punktA.get(0)
                    &&
                    punktB.get(1) < punktA.get(1)
                    &&
                    posKugelX > punktA.get(0)
                    &&
                    posKugelY < punktB.get(1)) {
                angleEck1 = true;
                return vector(punktB.get(0), punktB.get(1));
            }
            //Links unten
            if (punktA.get(0) < punktB.get(0)
                    &&
                    punktA.get(1) > punktB.get(1)
                    &&
                    posKugelX < punktA.get(0)
                    &&
                    posKugelY < punktA.get(1)) {
                angleEck2 = true;
                return vector(punktA.get(0), punktA.get(1));
            }
        }

        //Für gerade Ebenen
        if (!rec.equals(rec1) && punktB.get(1).equals(punktA.get(1))
                &&
                punktB.get(0) > punktA.get(0)
                &&
                posKugelX > punktB.get(0)
                &&
                posKugelX > punktA.get(0)) {
            angleEck1 = true;
            return vector(punktB.get(0), punktB.get(1));
        }
        if (!rec.equals(rec1) && punktB.get(1).equals(punktA.get(1))
                &&
                punktB.get(0) < punktA.get(0)
                &&
                posKugelX < punktB.get(0)
                &&
                posKugelX < punktA.get(0)) {
            angleEck1 = true;
            return vector(punktB.get(0), punktB.get(1));
        }

        if (!rec.equals(rec1) && punktB.get(1).equals(punktA.get(1))
                &&
                punktA.get(0) > punktB.get(0)
                &&
                posKugelX > punktA.get(0)
                &&
                posKugelX > punktB.get(0)) {
            angleEck2 = true;
            return vector(punktA.get(0), punktA.get(1));
        }

        if (!rec.equals(rec1) && punktB.get(1).equals(punktA.get(1))
                &&
                punktA.get(0) < punktB.get(0)
                &&
                posKugelX < punktA.get(0)
                &&
                posKugelX < punktB.get(0)) {
            angleEck2 = true;
            return vector(punktA.get(0), punktA.get(1));
        }
        return s;
    }

}