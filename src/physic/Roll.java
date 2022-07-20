package physic;

import Objects.Rectangle;
import javafx.scene.shape.Circle;

import java.util.Vector;

import static app.MainController.*;
import static physic.CollisionHandling.*;
import static physic.Collision.*;
import static physic.Physics.*;
import static physic.Fall.*;


public class Roll {
    public static double countRollRed = 1, countRollBlue = 1, countRollGrey = 1,f_a = 0, f_r = 0,
            curDistanceRed = 0, curDistanceBlue = 0, curDistanceGrey = 0;

    //Rollfunktion der roten Kugel
    public static void rollCircleRed(Circle circleRed, Circle circleBlue, Circle circleGrey) {
        if (checkRollRed == true && vecStartSpeedCircle.size()>1 && collisionRectangleRed!= null) {

            //Kollisionsobjekt definieren
            Rectangle rec = collisionRectangleRed;
            //Vektor der Ebene, auf die die Kugel rollt
            Vector<Double> vecE = vector(rec.getP1().get(0) - rec.getP2().get(0),
                    rec.getP1().get(1) - rec.getP2().get(1));
            double betragNormE = Math.sqrt(Math.pow(vecE.get(0), 2) + Math.pow(vecE.get(1), 2));
            //Vektor Ebene normalisiert
            Vector<Double> vecNormE = vector(vecE.get(0) / betragNormE, vecE.get(1) / betragNormE);

            countRollRed++;
            double timeRoll = time * countRollRed;
            double nu = 0.5; //Rollreibungszahl aus dem Tabellenbuch entnommen
            double alpha = angleRed;
            double a, v, saveIncline, sy, sx;
            double f_g = masseRedCircle * g;  // FG = m * g Gewichtskraft
            f_a = f_g * Math.sin(alpha);     // FA = m * g * sin(alpha) Hangabtriebskraft
            double f_gn = f_g * Math.cos(alpha);    // FN = m * g * cos(alpha) Normalkraft
            f_r = (nu * f_gn) / circleRed.getRadius(); //Reibungskraft 0.5 Rollreibung von Stahl auf Stahl

            changeFr();
            //Beschleunigung
            a = (f_a +f_r) / masseRedCircle;

            saveIncline = curDistanceRed;
            //s = 1/2*a*t^2 + vstart * t
            curDistanceRed = 0.5 * a * Math.pow(timeRoll, 2) + vecStartSpeedCircle.get(0) * timeRoll;

            Vector<Double> vecPos = positionCircleRoll(rec, vecNormE,vecStartSpeedCircle,
                    curDistanceRed, saveIncline);

            changeVorzeichen();
            //Neu berechnete Strecke für die Kugel
            sx = vecPos.get(0);
            sy = vecPos.get(1);

            //v = a * t + vstart
            v = a * timeRoll + vecStartSpeedCircle.get(0);


            double posy = pixelToMeter(circleRed.getLayoutY()) + sy;
            double posx = pixelToMeter(circleRed.getLayoutX()) + sx;

            Vector<Double> vecKugel = vector( meterToPixel(posx)- circleRed.getLayoutX(),
                    meterToPixel(posy)- circleRed.getLayoutY());

            //Geschwindigkeit der Kugel
            deltaVx = speedX(vecKugel,vecNormE,v);
            deltaVy = vecNormE.get(1) * v;

            //Kollisionsabfrage
            collisionCircleRed = checkCollision(circleRed,meterToPixel(posx),meterToPixel(posy),
                    circleRed.getLayoutX(),circleRed.getLayoutY());
            boolean collisionCircle2 = collisionCircle(circleRed, circleBlue, meterToPixel(posx),meterToPixel(posy));
            boolean collisionCircle3 = collisionCircle(circleRed, circleGrey, meterToPixel(posx),meterToPixel(posy));
            if(collisionCircle2 == true){
                redBlueCollisionCircle = true;
                collisionCircle = true;
            }
            if(collisionCircle3 == true){
                redGreyCollisionCircle = true;
                collisionCircle = true;
            }

            //Gibt Informationen für die GUI zurück z. B. Vek X oder Speed X
            vecKugelRed = vecKugel;
            //Abfrage, ob die Kugel sich noch auf der Ebene befindet
            checkRollRed = checkExist(rec,circleRed);
            if(checkRollRed == false){
                vecStartSpeedCircle.set(0,deltaVx);
                vecStartSpeedCircle.set(1,deltaVy);
                checkFallRed = true;
            }

            if (collisionCircleRed == false&& collisionCircle == false) {
                    if (vecNormE.get(1)!=0) {
                        if (f_a > f_r) {
                            circleRed.setLayoutY(meterToPixel(posy));
                            circleRed.setLayoutX(meterToPixel(posx));
                        }
                    } else {
                        circleRed.setLayoutY(meterToPixel(posy));
                        circleRed.setLayoutX(meterToPixel(posx));
                    }
            }
        }
    }


    //Rollfunktion blaue Kugel
    public static void rollCircleBlue(Circle circleBlue, Circle circleRed, Circle circleGrey) {


        if (checkRollBlue == true && vecStartSpeedObject.size() > 1 && collisionRectangleBlue != null) {
            //Kollisionsobjekt definieren
            Rectangle rec = collisionRectangleBlue;
            //Vektor der Ebene, auf die die Kugel rollt
            Vector<Double> vecE = vector(rec.getP1().get(0) - rec.getP2().get(0),
                    rec.getP1().get(1) - rec.getP2().get(1));
            double betragNormE = Math.sqrt(Math.pow(vecE.get(0), 2) + Math.pow(vecE.get(1), 2));
            //Vektor Ebene normalisiert
            Vector<Double> vecNormE = vector(vecE.get(0) / betragNormE, vecE.get(1) / betragNormE);

            countRollBlue++;
            double timeRoll = time * countRollBlue;
            double nu = 0.5;
            double alpha = angleBlue;
            double a,v, saveIncline,sx,sy;

            double f_g = masseBlueCircle * g;    // FG = m * g Gewichtskraft
            f_a = f_g * Math.sin(alpha);     // FA = m * g * sin(alpha) Hangabtriebskraft
            double f_gn = f_g * Math.cos(alpha);    // FN = m * g * cos(alpha) Normalkraft
            f_r = (nu * f_gn) / circleBlue.getRadius(); //Reibungskraft 0.5 Rollreibung von Stahl auf Stahl

            //Reibungskraft sollte immer entgegen der Kugel sein
            changeFr();
            //Beschleunigung
            a = (f_a +f_r) / masseBlueCircle;

            saveIncline = curDistanceBlue;
            //s = 1/2*a*t^2 + vstart * t
            curDistanceBlue = 0.5 * a * Math.pow(timeRoll, 2) + vecStartSpeedObject.get(0) * timeRoll;

            Vector<Double> vecPos = positionCircleRoll(rec, vecNormE,vecStartSpeedObject,
                    curDistanceBlue, saveIncline);
            //Neu berechnete Strecke für die Kugel
            sx = vecPos.get(0);
            sy = vecPos.get(1);

            //v = a * t + vstart
            v = a * timeRoll + vecStartSpeedObject.get(0);

            changeVorzeichen();
            double posy = pixelToMeter(circleBlue.getLayoutY()) + sy;
            double posx = pixelToMeter(circleBlue.getLayoutX()) + sx;
            //Vektor der Kugel
            Vector<Double> vecKugel = vector( meterToPixel(posx)- circleBlue.getLayoutX(),
                    meterToPixel(posy)- circleBlue.getLayoutY());
            //Geschwindigkeit der Kugel
            deltaVxObject = speedX(vecKugel,vecNormE,v);
            deltaVyObject = vecNormE.get(1) * v;

            //Kollisionsabfrage
            collisionCircleBlue = checkCollision(circleBlue,meterToPixel(posx),meterToPixel(posy),
                    circleBlue.getLayoutX(),circleBlue.getLayoutY());
            boolean collisionCircle2 = collisionCircle(circleBlue, circleRed, meterToPixel(posx),meterToPixel(posy));
            boolean collisionCircle3 = collisionCircle(circleBlue, circleGrey, meterToPixel(posx),meterToPixel(posy));
            if(collisionCircle2 == true){
                blueRedCollisionCircle = true;
                collisionCircle = true;
            }
            if(collisionCircle3 == true){
                blueGreyCollisionCircle = true;
                collisionCircle = true;
            }

            //Gibt Informationen für die GUI zurück z. B. Vek X oder Speed X
            vecKugelBlue = vecKugel;

            //Ob die Kugel sich noch auf der Ebene befindet
            checkRollBlue = checkExist(rec,circleBlue);
            if(checkRollBlue == false){
                vecStartSpeedObject.set(0,deltaVxObject);
                vecStartSpeedObject.set(1,deltaVyObject);
                checkFallBlue = true;
            }

                //Bewegung der Kugel
                if (collisionCircleBlue == false&& collisionCircle == false) {
                        if (vecNormE.get(1)!=0) {
                            if (f_a > f_r) {
                                circleBlue.setLayoutY(meterToPixel(posy));
                                circleBlue.setLayoutX(meterToPixel(posx));
                            }
                        } else {
                            circleBlue.setLayoutY(meterToPixel(posy));
                            circleBlue.setLayoutX(meterToPixel(posx));
                        }
                    }
                }
            }


    //Rollfunktion blaue Kugel
    public static void rollCircleGrey(Circle circleGrey, Circle circleRed, Circle circleBlue) {


        if (checkRollGrey == true && vecStartSpeedObject2.size() > 1 && collisionRectangleGrey != null) {
            //Kollisionsobjekt definieren
            Rectangle rec = collisionRectangleGrey;
            //Vektor der Ebene, auf die die Kugel rollt
            Vector<Double> vecE = vector(rec.getP1().get(0) - rec.getP2().get(0),
                    rec.getP1().get(1) - rec.getP2().get(1));
            double betragNormE = Math.sqrt(Math.pow(vecE.get(0), 2) + Math.pow(vecE.get(1), 2));
            //Vektor Ebene normalisiert
            Vector<Double> vecNormE = vector(vecE.get(0) / betragNormE, vecE.get(1) / betragNormE);

            countRollGrey++;
            double timeRoll = time * countRollGrey;
            double nu = 0.5;
            double alpha = angleGrey;
            double a,v, saveIncline,sx,sy;
            double f_g = masseGreyCircle * g;    // FG = m * g Gewichtskraft
            f_a = f_g * Math.sin(alpha);     // FA = m * g * sin(alpha) Hangabtriebskraft
            double f_gn = f_g * Math.cos(alpha);    // FN = m * g * cos(alpha) Normalkraft
            f_r = (nu * f_gn) / circleGrey.getRadius(); //Reibungskraft 0.5 Rollreibung von Stahl auf Stahl

            //Reibungskraft sollte immer entgegen der Kugel sein
            changeFr();
            //Beschleunigung
            a = (f_a +f_r) / masseGreyCircle;

            saveIncline = curDistanceGrey;
            //s = 1/2*a*t^2 + vstart * t
            curDistanceGrey = 0.5 * a * Math.pow(timeRoll, 2) + vecStartSpeedObject2.get(0) * timeRoll;

            Vector<Double> vecPos = positionCircleRoll(rec, vecNormE,vecStartSpeedObject2,
                    curDistanceGrey, saveIncline);
            //Neu berechnete Strecke für die Kugel
            sx = vecPos.get(0);
            sy = vecPos.get(1);

            //v = a * t + vstart
            v = a * timeRoll + vecStartSpeedObject2.get(0);

            changeVorzeichen();
            //Neue Position für die Kugel
            double posy = pixelToMeter(circleGrey.getLayoutY()) + sy;
            double posx = pixelToMeter(circleGrey.getLayoutX()) + sx;
            //Vektor der Kugel
            Vector<Double> vecKugel = vector( meterToPixel(posx)- circleGrey.getLayoutX(),
                    meterToPixel(posy)- circleGrey.getLayoutY());
            //Geschwindigkeit der Kugel
            deltaVxObject2 = speedX(vecKugel,vecNormE,v);
            deltaVyObject2 = vecNormE.get(1) * v;
            //Kollisionsabfrage
            collisionCircleGrey = checkCollision(circleGrey,meterToPixel(posx),meterToPixel(posy),
                    circleGrey.getLayoutX(),circleGrey.getLayoutY());
            boolean collisionCircle2 = collisionCircle(circleGrey, circleRed, meterToPixel(posx),meterToPixel(posy));
            boolean collisionCircle3 = collisionCircle(circleGrey, circleBlue, meterToPixel(posx),meterToPixel(posy));
            if(collisionCircle2 == true){
                greyRedCollisionCircle = true;
                collisionCircle = true;
            }
            if(collisionCircle3 == true){
                greyBlueCollisionCircle = true;
                collisionCircle = true;
            }

            //Gibt Informationen für die GUI zurück z. B. Vek X oder Speed X
            vecKugelGrey = vecKugel;

            //Ob die Kugel sich noch auf der Ebene befindet
            checkRollGrey = checkExist(rec,circleGrey);
            if(checkRollGrey == false){
                vecStartSpeedObject2.set(0,deltaVxObject2);
                vecStartSpeedObject2.set(1,deltaVyObject2);
                checkFallGrey = true;
            }

            //Bewegung der Kugel
            if (collisionCircleGrey == false&& collisionCircle == false) {

                if (vecNormE.get(1)!=0) {
                    if (f_a > f_r) {
                        circleGrey.setLayoutY(meterToPixel(posy));
                        circleGrey.setLayoutX(meterToPixel(posx));
                    }
                } else {
                    circleGrey.setLayoutY(meterToPixel(posy));
                    circleGrey.setLayoutX(meterToPixel(posx));
                }
            }
        }
    }

    //Die Strecke X und Y für die Kugel bestimmen
public static Vector<Double> positionCircleRoll(Rectangle rec, Vector<Double> vecNormE, Vector<Double> vecStartSpeed,
                                                       double distance,
                                                       double saveIncline){
        double sxIncline=0,syIncline=0;

    //Schräge Links oben
    if(rec.getP1().get(1)<rec.getP2().get(1)&&
            rec.getP1().get(0)>rec.getP2().get(0))
    {
        sxIncline = vecNormE.get(0) * (distance - saveIncline);
        syIncline = vecNormE.get(1) * (distance - saveIncline);
    }
    //Schräge Links unten
    if(rec.getP1().get(1)>rec.getP2().get(1)&&
            rec.getP1().get(0)>rec.getP2().get(0))
    {
        sxIncline = vecNormE.get(0) * (distance - saveIncline);
        syIncline = vecNormE.get(1) * (distance - saveIncline);
    }
    //Schräge Rechts unten
    if(rec.getP1().get(1)>rec.getP2().get(1)&&
            rec.getP1().get(0)<rec.getP2().get(0))
    {
        sxIncline = vecNormE.get(0)*-1* (distance - saveIncline);
        syIncline = vecNormE.get(1)*-1* (distance - saveIncline);
    }
    //Schräge Rechts oben
    if(rec.getP1().get(1)<rec.getP2().get(1)&&
            rec.getP1().get(0)<rec.getP2().get(0))
    {
        sxIncline = vecNormE.get(0)*-1* (distance - saveIncline);
        syIncline = vecNormE.get(1)*-1* (distance - saveIncline);
    }

    //Gerade Ebene
    if(rec.getP1().get(1).equals(rec.getP2().get(1)))
    {
        if(vecStartSpeed.get(0)>0 && (distance-saveIncline)<0 ||
                vecStartSpeed.get(0)<0 && (distance-saveIncline)>0 || vecStartSpeed.get(0) == 0)
        {
            sxIncline = 0;
        }
        else
        {
            if (vecNormE.get(0) < 0)
            {
                sxIncline = vecNormE.get(0) *-1 * (distance - saveIncline);
            }
            else
            {
                sxIncline = vecNormE.get(0) * (distance - saveIncline);
            }
        }
    }

    Vector<Double> vec = vector(sxIncline,syIncline);
    return vec;
}
    //Funktion, um zu überprüfen, ob die Kugel noch auf der Ebene rollt
    public static boolean checkExist(Rectangle rec,Circle circle){
        if(rec.getP1().get(0)>rec.getP2().get(0) && (circle.getLayoutX()  > rec.getP1().get(0)+5
                || circle.getLayoutX() < rec.getP2().get(0)-5))
        {
            return false;
        }
        if (rec.getP1().get(0)<rec.getP2().get(0)&& (circle.getLayoutX()  < rec.getP1().get(0)-5
                || circle.getLayoutX()  > rec.getP2().get(0)+5))
        {
            return false;
        }else if(rec.getP1().get(0).equals(rec.getP2().get(0))){
            return false;
        }
        return true;
    }
    //Vorzeichen der Geschwindigkeit vertauschen, wenn die Richtung der Ebene sich verändert
    public static double speedX(Vector<Double> vecKugel,Vector<Double> vecNormE,double v){

        if(vecKugel.get(0)>0 && vecNormE.get(0)>0)
        {
            return vecNormE.get(0) * v;
        }
        else if(vecKugel.get(0)>0 && vecNormE.get(0)<0)
        {
            return vecNormE.get(0) *-1 * v;
        }
        else if(vecKugel.get(0)<0 && vecNormE.get(0)>0)
        {
            return vecNormE.get(0) * v;
        }
        else if(vecKugel.get(0)<0 && vecNormE.get(0)<0)
        {
            return vecNormE.get(0) *-1 * v;
        }
        return 0;
    }

    public static void changeVorzeichen(){
        if(f_r<0){
            f_r =f_r*-1;
        }
        if(f_a<0){
            f_a= f_a*-1;
        }
    }
    public static void changeFr(){
        if(f_a<0){
            if(f_r<0){
                f_r = f_r*-1;
            }
        }
        if(f_a>0){
            if(f_r>0){
                f_r = f_r*-1;
            }
        }
    }
}
