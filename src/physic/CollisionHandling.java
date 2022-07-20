package physic;

import Objects.Rectangle;
import javafx.scene.shape.Circle;

import java.util.Vector;

import static physic.Fall.*;

import static physic.Collision.*;
import static app.MainController.*;
import static physic.Physics.*;
import static physic.Physics.deltaVyObject;

public class CollisionHandling {
    public static double angleRed = 0, angleBlue = 0, angleGrey = 0, saveVy = 0, saveVx = 0, saveVy2 = 0, saveVx2 = 0;
    public static Rectangle collisionRectangle, collisionRectangleBlue, collisionRectangleRed, collisionRectangleGrey,
            saveCollisionRectangleRed,
            saveCollisionRectangleBlue, saveCollisionRectangleGrey;

    public static void collisionHandling1() {

        //Kollisionhandling Kugel Rot
        if (collisionCircleRed == true) {

            //Vorheriges Objekt (Rechteck) zwischenspeichern für die Funktion "angleBetweenObjects", da dort
            //der Winkel zwischen zwei Objekten berechnet wird
            if (collisionRectangleRed != null) {
                saveCollisionRectangleRed = collisionRectangleRed;
            }
            //Objekt mit das die Kugel kollidiert ist, wird gespeichert.
            collisionRectangleRed = collisionRectangle;

            Rectangle rec = collisionRectangle;
            //Winkel des Objektes bestimmen
            angleRed = getAngleCollision(rec);
            //Geschwindigkeit der Kugel nach der Kollision bestimmen
            vecStartSpeedCircle = collisionHandling2(deltaVx, deltaVy, angleRed, masseRedCircle);
            //Notwendig für die Klasse Spring, funktion " controllSpringRelaxed()"
            saveVy = deltaVy;
            saveVx = deltaVx;
            //Kontrollieren ob die Kugel anfängt zu rollen oder abspringt
            checkRollRed = checkRoll(vecStartSpeedCircle, rec, saveCollisionRectangleRed, deltaVy);
            if (checkRollRed == true) {
                checkFallRed = false;
            } else {
                deltaVx = 0;
                deltaVy = 0;
                checkFallRed = true;
            }
            resetValueCircleRed();
        }

        //Kollisionhandling Kugel Blau
        if (collisionCircleBlue == true) {
            if (collisionRectangleBlue != null) {
                saveCollisionRectangleBlue = collisionRectangleBlue;
            }
            collisionRectangleBlue = collisionRectangle;
            Rectangle rec = collisionRectangle;

            angleBlue = getAngleCollision(rec);
            vecStartSpeedObject = collisionHandling2(deltaVxObject, deltaVyObject, angleBlue, masseBlueCircle);
            saveVy2 = deltaVyObject;
            saveVx2 = deltaVxObject;
            checkRollBlue = checkRoll(vecStartSpeedObject, rec, saveCollisionRectangleBlue, deltaVyObject);

            if (checkRollBlue == true) {
                checkFallBlue = false;
            } else {
                deltaVxObject = 0;
                deltaVyObject = 0;
                checkFallBlue = true;
            }
            resetValueCircleBlue();
        }
        //Kollisionhandling Kugel Grau
        if (collisionCircleGrey == true) {
            if (collisionRectangleGrey != null) {
                saveCollisionRectangleGrey = collisionRectangleGrey;
            }
            collisionRectangleGrey = collisionRectangle;
            Rectangle rec = collisionRectangle;

            angleGrey = getAngleCollision(rec);
            vecStartSpeedObject2 = collisionHandling2(deltaVxObject2, deltaVyObject2, angleGrey, masseGreyCircle);

            checkRollGrey = checkRoll(vecStartSpeedObject2, rec, saveCollisionRectangleGrey, deltaVyObject2);
            if (checkRollGrey == true) {
                checkFallGrey = false;
            } else {
                deltaVxObject2 = 0;
                deltaVyObject2 = 0;
                checkFallGrey = true;
            }
            resetValueCircleGrey();
        }
    }
    //Kugelkollision
    public static Vector<Double> checkCircleCollision(Circle circle, Circle circle2, Vector<Double> vecStartSpeed1,
                                                      Vector<Double> vecStartSpeed2, double masse1, double masse2) {

        //Kugel Kollision
        if (collisionCircle == true && vecStartSpeed1.size() > 1 && vecStartSpeed2.size() > 1) {

            //Richtungsvektor von Kugel1 zu Kugel2
            Vector<Double> n = vector(circle.getLayoutX() - circle2.getLayoutX(),
                    circle.getLayoutY() - circle2.getLayoutY());
            double betragN = Math.sqrt(Math.pow(n.get(0), 2) + Math.pow(n.get(1), 2));
            //Richtungsvektor normieren
            Vector<Double> un = vector(n.get(0) / betragN, n.get(1) / betragN);
            //Vektor der orthogonal zum Richtungsvektor ist
            Vector<Double> ut = vector(un.get(1) * -1, un.get(0));

            //Geschwindigkeiten mit den Vektoren multiplizieren
            double v1n = un.get(0) * vecStartSpeed1.get(0) + un.get(1) * vecStartSpeed1.get(1);
            double v1t = ut.get(0) * vecStartSpeed1.get(0) + ut.get(1) * vecStartSpeed1.get(1);
            double v2n = un.get(0) * vecStartSpeed2.get(0) + un.get(1) * vecStartSpeed2.get(1);
            double v2t = ut.get(0) * vecStartSpeed2.get(0) + ut.get(1) * vecStartSpeed2.get(1);
            //Neue Geschwindigkeit nach der Kollision berechnen
            double v1nStrich = (masse1*v1n+masse2*(2*v2n-v1n))/(masse1 + masse2);
            double v2nStrich = (masse2*v2n+masse1*(2*v1n-v2n))/(masse1 + masse2);
            //Neue Geschwindigkeit mit den Richtungsvektoren multiplizieren
            Vector<Double> vecv1nS = vector(v1nStrich * un.get(0), v1nStrich * un.get(1));
            Vector<Double> vecv1tS = vector(v1t * ut.get(0), v1t * ut.get(1));
            Vector<Double> vecv2nS = vector(v2nStrich * un.get(0), v2nStrich * un.get(1));
            Vector<Double> vecv2tS = vector(v2t * ut.get(0), v2t * ut.get(1));

            Vector<Double> vec1 = vector(vecv1nS.get(0) + vecv1tS.get(0), vecv1nS.get(1) + vecv1tS.get(1));
            Vector<Double> vec2 = vector(vecv2nS.get(0) + vecv2tS.get(0), vecv2nS.get(1) + vecv2tS.get(1));

            collisionCircle = false;
            //Neue Vektoren übergeben
            return vector4(vec1.get(0), vec1.get(1), vec2.get(0), vec2.get(1));
        }
        return vector4(0, 0, 0, 0);
    }

    //Für Objekte mit einer geraden Fläche
    public static Vector<Double> collisionHandling2(double vx, double vy, double angle, double masse) {


        Vector<Double> vecStartSpeed;
        Vector<Double> vecSpeed = vector(vx, vy);

        Vector<Double> vecSpeed2 = matrixRotate(vecSpeed.get(0), vecSpeed.get(1), angle * -1);
        vecStartSpeed = matrixRotateBack(vecSpeed2.get(0), vecSpeed2.get(1) * -1, angle * -1);

        //Energieverlust bei Kollision
        //Kinetische Energie E = 0.5 * m * v^2
        double e_kiny = 0.5 * masse * Math.pow(vecStartSpeed.get(1), 2);
        double e_kinx = 0.5 * masse * Math.pow(vecStartSpeed.get(0), 2);
        double k = 0.6; //  k = 1 Elastisch k = 0 Plastisch
        //v = sqrt((2 * E * k) / m)
        double vy2 = Math.sqrt((e_kiny * 2 * k) / masse);
        double vx2 = Math.sqrt((e_kinx * 2 * k) / masse);

        if (vecStartSpeed.get(1) < 0) {
            vecStartSpeed.set(1, vy2 * -1);
        } else {
            vecStartSpeed.set(1, vy2);
        }
        if (vecStartSpeed.get(0) < 0) {
            vecStartSpeed.set(0, vx2 * -1);
        } else {
            vecStartSpeed.set(0, vx2);
        }

        return vecStartSpeed;
    }
    //Um nach jeder Kugelkollision die richtige Geschwindigkeit an die Kugeln zurückzugeben
    public static void collisionHandlingCircle(Circle circleRed, Circle circleBlue, Circle circleGrey) {

        Vector<Double> vecSpeed = vector(deltaVx, deltaVy);
        Vector<Double> vecSpeedObject = vector(deltaVxObject, deltaVyObject);
        Vector<Double> vecSpeedObject2 = vector(deltaVxObject2, deltaVyObject2);
        if (redBlueCollisionCircle == true) {
            Vector<Double> vec = checkCircleCollision(circleRed, circleBlue, vecSpeed, vecSpeedObject, masseRedCircle, masseBlueCircle);
            vecStartSpeedCircle = vector(vec.get(0), vec.get(1));
            vecStartSpeedObject = vector(vec.get(2), vec.get(3));
            resetValueCircleRed();
            resetValueCircleBlue();
            redBlueCollisionCircle = false;
        }
        if (blueRedCollisionCircle == true) {
            Vector<Double> vec = checkCircleCollision(circleBlue, circleRed, vecSpeedObject, vecSpeed, masseBlueCircle, masseRedCircle);
            vecStartSpeedObject = vector(vec.get(0), vec.get(1));
            vecStartSpeedCircle = vector(vec.get(2), vec.get(3));
            resetValueCircleRed();
            resetValueCircleBlue();
            blueRedCollisionCircle = false;
        }
        if (redGreyCollisionCircle == true) {
            Vector<Double> vec = checkCircleCollision(circleRed, circleGrey, vecSpeed, vecSpeedObject2, masseRedCircle, masseGreyCircle);
            vecStartSpeedCircle = vector(vec.get(0), vec.get(1));
            vecStartSpeedObject2 = vector(vec.get(2), vec.get(3));
            resetValueCircleRed();
            resetValueCircleGrey();
            redGreyCollisionCircle = false;
        }
        if (greyRedCollisionCircle == true) {
            Vector<Double> vec = checkCircleCollision(circleGrey, circleRed, vecSpeedObject2, vecSpeed, masseGreyCircle, masseRedCircle);
            vecStartSpeedObject2 = vector(vec.get(0), vec.get(1));
            vecStartSpeedCircle = vector(vec.get(2), vec.get(3));
            resetValueCircleRed();
            resetValueCircleGrey();
            greyRedCollisionCircle = false;
        }
        if (blueGreyCollisionCircle == true) {
            Vector<Double> vec = checkCircleCollision(circleBlue, circleGrey, vecSpeedObject, vecSpeedObject2, masseBlueCircle, masseGreyCircle);
            vecStartSpeedObject = vector(vec.get(0), vec.get(1));
            vecStartSpeedObject2 = vector(vec.get(2), vec.get(3));
            resetValueCircleBlue();
            resetValueCircleGrey();
            blueGreyCollisionCircle = false;
        }
        if (greyBlueCollisionCircle == true) {
            Vector<Double> vec = checkCircleCollision(circleGrey, circleBlue, vecSpeedObject2, vecSpeedObject, masseGreyCircle, masseBlueCircle);
            vecStartSpeedObject2 = vector(vec.get(0), vec.get(1));
            vecStartSpeedObject = vector(vec.get(2), vec.get(3));
            resetValueCircleBlue();
            resetValueCircleGrey();
            greyBlueCollisionCircle = false;
        }
    }

    //Kontrolliert, ob die Kugel in Y Richtung schwach genug ist, das sie anfängt zu rollen
    public static boolean checkRoll(Vector<Double> vecStartSpeed, Rectangle rec, Rectangle recOld, double vy) {
        double angle = angleBetweenObjects(rec, recOld);
        //Wenn die Bedingungen erfüllt sind, darf die Kugel rollen
        if (vecStartSpeed != null && vecStartSpeed.size() > 1 && rec != null && recOld != null) {
            if (vecStartSpeed.get(1) < 2 && vecStartSpeed.get(1) > -2
                    &&
                    !rec.getP1().get(0).equals(rec.getP2().get(0))
                    &&
                    Math.toDegrees(angle) > 90
                    &&
                    vy <= 0) {
                return true;
            }
        } else {
            return false;
        }
        return false;
    }

    //Wenn die Kugel an eine spitze Ecke fällt, ist der Winkel fest codiert
    public static double getAngleCollision(Rectangle rec) {
        if (angleEck1 == true) {
            return Math.toRadians(35);
        } else if (angleEck2 == true) {
            return Math.toRadians(-35);
        } else if (angleLot == 0 && rec != null) {
            return getAngle(rec.getP1(), rec.getP2());
        } else {
            return angleLot;
        }

    }

    //Winkel zwischen zwei Objekten bestimmen
    public static double angleBetweenObjects(Rectangle rec, Rectangle recOld) {

        if (recOld != null) {
            //Vektor des vorherigen Objektes
            Vector<Double> vecOldRec = vector(recOld.getP1().get(0) - recOld.getP2().get(0),
                    recOld.getP1().get(1) - recOld.getP2().get(1));
            //Vektor des jetztigen Objektes
            Vector<Double> vecRec = vector(rec.getP1().get(0) - rec.getP2().get(0),
                    rec.getP1().get(1) - rec.getP2().get(1));
            //Falls die Vektoren nicht zueinanderlaufen, wird X vom alten Objekt geändert
            if (vecOldRec.get(0) < 0 && vecRec.get(0) > 0 || (vecOldRec.get(0) > 0 && vecRec.get(0) < 0)) {
                vecOldRec = vector(vecOldRec.get(0) * -1, vecOldRec.get(1));
            }
            //Falls die Objekte gleich sind
            if (recOld.getP1().get(1) == rec.getP1().get(1) && recOld.getP2().get(1) == rec.getP2().get(1)) {
                return 91;
            } else {
                //Winkel zwischen den beiden Vektoren berechnen
                return Math.acos((vecOldRec.get(0) * vecRec.get(0) + vecOldRec.get(1) * vecRec.get(1)) /
                        ((Math.sqrt(Math.pow(vecOldRec.get(0), 2) + Math.pow(vecOldRec.get(1), 2))) *
                                Math.sqrt(Math.pow(vecRec.get(0), 2) + Math.pow(vecRec.get(1), 2))));
            }
        }
        return 0;
    }
}
