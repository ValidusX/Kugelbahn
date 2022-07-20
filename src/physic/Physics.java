package physic;

import Objects.Rectangle;

import java.util.Vector;

import static app.MainController.*;
import static physic.Fall.*;
import static physic.Roll.*;
import static physic.Collision.*;


public class Physics {
    static final double g = -9.81;
    public static double time = 0.01666666666;
    public static double deltaVy = 0, deltaVx = 0, deltaVxObject = 0, deltaVyObject = 0, deltaVxObject2 = 0,
            deltaVyObject2 = 0, count = 0;
    public static boolean checkFallRed = true, checkFallBlue = true, checkFallGrey = true, collisionCircle = false,
            collisionCircleGrey = false;
    public static boolean checkRollRed = false, checkRollBlue = false, checkRollGrey = false;

    public static Vector<Double> vecAccelerationFall = new Vector();
    public static Vector<Double> vecStartSpeedCircle = new Vector();
    public static Vector<Double> vecWind = new Vector();
    public static Vector<Double> vecFalling = new Vector();
    public static Vector<Double> vecStartSpeedObject = new Vector<>();
    public static Vector<Double> vecStartSpeedObject2 = new Vector<>();
    public static Vector<Double> vecKugelRed = new Vector<>();
    public static Vector<Double> vecKugelBlue = new Vector<>();
    public static Vector<Double> vecKugelGrey = new Vector<>();

    public static double meterToPixel(double s) {
        double faktor = 10; //10 Pixel = 1m

        return s * faktor;
    }

    public static double pixelToMeter(double s) {
        double faktorPixelMeter = 10; //10 Pixel = 1m

        return s / faktorPixelMeter;
    }

    public static void vecSpeedUp(double x, double g) {
        if (vecAccelerationFall.size() == 0) {
            vecAccelerationFall.insertElementAt(x, 0);
            vecAccelerationFall.insertElementAt(g, 1);
        } else {
            vecAccelerationFall.set(0, x);
            vecAccelerationFall.set(1, g);
        }
    }

    public static void vecWind(double windX, double windY) {
        if (vecWind.size() == 0) {
            vecWind.insertElementAt(windX, 0);
            vecWind.insertElementAt(windY, 1);
        } else {
            vecWind.set(0, windX);
            vecWind.set(1, windY);
        }
    }

    public static Vector<Double> vector(double x, double y) {
        Vector<Double> vec = new Vector<>();
        if (vec.size() > 1) {
            vec.set(0, x);
            vec.set(1, y);
        } else {
            vec.add(0, x);
            vec.add(1, y);
        }

        return vec;
    }

    public static Vector<Double> vector4(double x1, double y1, double x2, double y2) {
        Vector<Double> vec = new Vector<>();
        if (vec.size() > 1) {
            vec.set(0, x1);
            vec.set(1, y1);
            vec.set(2, x2);
            vec.set(3, y2);
        } else {
            vec.add(0, x1);
            vec.add(1, y1);
            vec.add(2, x2);
            vec.add(3, y2);
        }

        return vec;
    }

    //Vektor normieren
    public static Vector<Double> vectorNorm(Vector<Double> vec) {
        if (vec.size() > 0) {
            double betragNormE = Math.sqrt(Math.pow(vec.get(0), 2) + Math.pow(vec.get(1), 2));
            Vector<Double> vecNormE = vector(vec.get(0) / betragNormE, vec.get(1) / betragNormE);
            return vecNormE;
        }
        Vector<Double> vec2 = vector(0, 0);
        return vec2;
    }

    //Addition Vektor
    public static Vector<Double> addVector(double vecX1, double vecY1, double vecX2, double vecY2) {
        Vector<Double> vec = new Vector<>();
        if (vec.size() > 1) {
            vec.set(0, vecX1 + vecX2);
            vec.set(1, vecY1 + vecY2);
        } else {
            vec.add(0, vecX1 + vecX2);
            vec.add(1, vecY1 + vecY2);
        }
        return vec;
    }

    public static Vector<Double> matrixRotate(double x, double y, double angle) {
        Vector<Double> vec = new Vector<>();
        vec.add(0, Math.cos(angle) * x - Math.sin(angle) * y);
        vec.add(1, Math.sin(angle) * x + Math.cos(angle) * y);

        return vec;
    }

    public static double[][] matrixTranslation(double x, double y, double[][] matrix) {

        matrix[0][0] = matrix[0][0] + x;
        matrix[1][0] = matrix[1][0] + y;
        matrix[0][1] = matrix[0][1] + x;
        matrix[1][1] = matrix[1][1] + y;
        matrix[0][2] = matrix[0][2] + x;
        matrix[1][2] = matrix[1][2] + y;
        matrix[0][3] = matrix[0][3] + x;
        matrix[1][3] = matrix[1][3] + y;

        return matrix;
    }

    public static Rectangle rotateRectangle(Rectangle rec, double angle, Vector<Double> p1, Vector<Double> p2,
                                            Vector<Double> p3, Vector<Double> p4, double tranX, double tranY) {


        double[][] matrix = new double[2][4];
        matrix[0][0] = p1.get(0);
        matrix[1][0] = p1.get(1);
        matrix[0][1] = p2.get(0);
        matrix[1][1] = p2.get(1);
        matrix[0][2] = p3.get(0);
        matrix[1][2] = p3.get(1);
        matrix[0][3] = p4.get(0);
        matrix[1][3] = p4.get(1);
        if (tranX < 0) {
            tranX = tranX * -1;
        }
        if (tranY < 0) {
            tranY = tranY * -1;
        }
        double saveX = tranX;
        double saveY = tranY;

        matrix = matrixTranslation(-saveX, -saveY, matrix);

        p1 = vector(matrix[0][0], matrix[1][0]);
        p2 = vector(matrix[0][1], matrix[1][1]);
        p3 = vector(matrix[0][2], matrix[1][2]);
        p4 = vector(matrix[0][3], matrix[1][3]);

        p1 = matrixRotate(p1.get(0), p1.get(1), angle);
        p2 = matrixRotate(p2.get(0), p2.get(1), angle);
        p3 = matrixRotate(p3.get(0), p3.get(1), angle);
        p4 = matrixRotate(p4.get(0), p4.get(1), angle);

        matrix[0][0] = p1.get(0);
        matrix[1][0] = p1.get(1);
        matrix[0][1] = p2.get(0);
        matrix[1][1] = p2.get(1);
        matrix[0][2] = p3.get(0);
        matrix[1][2] = p3.get(1);
        matrix[0][3] = p4.get(0);
        matrix[1][3] = p4.get(1);

        matrix = matrixTranslation(saveX, saveY, matrix);

        rec.setP1(vector(matrix[0][0], matrix[1][0]));
        rec.setP2(vector(matrix[0][1], matrix[1][1]));
        rec.setP3(vector(matrix[0][2], matrix[1][2]));
        rec.setP4(vector(matrix[0][3], matrix[1][3]));

        return rec;
    }

    public static Vector<Double> matrixRotateBack(double x, double y, double angle) {
        Vector<Double> vec = new Vector<>();
        vec.add(0, Math.cos(angle) * x + Math.sin(angle) * y);
        vec.add(1, -1 * Math.sin(angle) * x + Math.cos(angle) * y);

        return vec;
    }
    //Geschwindigkeit der gespannten Feder berechnen
    public static double springSpeed(double masse) {

        //Gespeicherte Energie ( Spannenergie)
        double Espan = 0.5 * federkonstante * Math.pow(federweg, 2);
        //Energieerhaltungssatz : Spannenergie = Kinetische Energie
        double Ekin = Espan;                    //Ekin = 0.5 * masse * v^2
        //Nach v umstellen: v = sqrt(k*s^2/masse)
        double v = Math.sqrt(2*Ekin/masse);

        return v;
    }
    //Geschwindigkeit der entspannten Feder
    public static Vector<Double> springRelaxedSpeed(double masse, double vx, double vy) {

        //Kugel gegen Feder mit kinetischer Energie
        double kinx = 0.5 * masse * Math.pow(vx, 2);
        double kiny = 0.5 * masse * Math.pow(vy, 2);

        //Kinetische Energie = Spannenergie, daher kinetische Energie wirkt auf die Kugel
        //Neues V berechnen
        double vresx = Math.sqrt(2 * kinx / masse);
        double vresy = Math.sqrt(2 * kiny / masse);

        //Falls das v ursprünglich ein negatives Vorzeichen hatte
        if (vresy < 0) {
            vresy = vresy * -1;
        }
        if (vx < 0) {
            vresx = vresx * -1;
        }

        Vector<Double> vec = vector(vresx, vresy);

        return vec;
    }

    public static double getAngle(Vector<Double> p1, Vector<Double> p2) {

        double rx, ry;
        double k = 1, ky = 1;

        if (p1.get(0) < p2.get(0)) {
            rx = p2.get(0) - p1.get(0);
        } else if (p1.get(0) > p2.get(0)) {
            rx = p1.get(0) - p2.get(0);
            k = -1;
        } else {
            rx = 0.01;
        }

        if (p1.get(1) < p2.get(1)) {
            ry = p2.get(1) - p1.get(1);

        } else if (p1.get(1) > p2.get(1)) {
            ky = -1;
            ry = p1.get(1) - p2.get(1);
        } else {
            ry = 0.0;
        }

        return Math.atan(ry / rx) * k * ky;

    }

    public static void resetValueCircleRed() {
        countFallRed = 0;
        curDistanceRed = 0;
        countRollRed = 0;
        positionRedY = 0;
        positionRedX = 0;
        collisionCircleRed = false;
    }

    public static void resetValueCircleBlue() {
        curDistanceBlue = 0;
        countRollBlue = 0;
        countFallBlue = 0;
        positionBlueY = 0;
        positionBlueX = 0;
        collisionCircleBlue = false;
    }

    public static void resetValueCircleGrey() {
        curDistanceGrey = 0;
        countRollGrey = 0;
        countFallGrey = 0;
        positionGreyY = 0;
        positionGreyX = 0;
        collisionCircleGrey = false;
    }
}