package physic;

import javafx.scene.shape.Circle;

import static physic.Collision.*;
import static physic.Physics.*;


public class Fall {
    public static double countFallRed = 1, countFallBlue = 1, countFallGrey = 1, positionBlueY = 0, positionBlueX = 0,
            positionRedY = 0, positionRedX = 0, positionGreyY = 0, positionGreyX = 0;
    public static boolean checkSpring = false, checkSpringBlue = false, checkSpringRed = false, greyRedCollisionCircle = false,
            greyBlueCollisionCircle = false, redGreyCollisionCircle = false, redBlueCollisionCircle = false, blueGreyCollisionCircle = false,
            blueRedCollisionCircle = false;

    //Fallfunktion für die rote Kugel
    public static void fallCircleRed(Circle circleRed, Circle circleBlue, Circle circleGrey) {

        if (checkFallRed == true) {
            //Vektor mit der Fallbeschleunigung + möglichen Wind
            vecFalling = addVector(vecWind.get(0), vecWind.get(1), vecAccelerationFall.get(0), vecAccelerationFall.get(1));

            countFallRed++;
            double timeFall = time * countFallRed;
            double newPositionX, newPositionY;
            // v = awind*t+vstart
            if (vecStartSpeedCircle != null && vecStartSpeedCircle.size() > 1) {
                deltaVx = ((vecFalling.get(0) * timeFall) + vecStartSpeedCircle.get(0));
                deltaVy = ((vecFalling.get(1) * timeFall) + vecStartSpeedCircle.get(1));
            } else {
                deltaVx = ((vecFalling.get(0) * timeFall));
                deltaVy = ((vecFalling.get(1) * timeFall));
            }

            //Positon der Kugel zwischenspeichern
            double savePosX = positionRedX;
            double savePosY = positionRedY;

            // s = vstart * t + 0.5 * g * t^2
            if (vecStartSpeedCircle != null && vecStartSpeedCircle.size() > 1) {
                positionRedX = vecStartSpeedCircle.get(0) * timeFall + vecFalling.get(0) * 0.5 * Math.pow(timeFall, 2);
                positionRedY = vecStartSpeedCircle.get(1) * timeFall + vecFalling.get(1) * 0.5 * Math.pow(timeFall, 2);
            } else {
                positionRedX = vecFalling.get(0) * 0.5 * Math.pow(timeFall, 2);
                positionRedY = vecFalling.get(1) * 0.5 * Math.pow(timeFall, 2);
            }

            newPositionX = (positionRedX - savePosX);
            //Neue Position in X
            double posxMeter = pixelToMeter(circleRed.getLayoutX()) + newPositionX;

            newPositionY = (positionRedY - savePosY);
            //Neue Position in Y
            double posyMeter = pixelToMeter(circleRed.getLayoutY()) + newPositionY;

            //Kollisionsabfrage
            collisionCircleRed = checkCollision(circleRed, meterToPixel(posxMeter), meterToPixel(posyMeter),
                    circleRed.getLayoutX(), circleRed.getLayoutY());
            //Kollisionsabfrage Kugeln
            boolean collisionCircle2 = collisionCircle(circleRed, circleBlue, meterToPixel(posxMeter), meterToPixel(posyMeter));
            boolean collisionCircle3 = collisionCircle(circleRed, circleGrey, meterToPixel(posxMeter), meterToPixel(posyMeter));
            if (collisionCircle2 == true) {
                redBlueCollisionCircle = true;
                collisionCircle = true;
            }
            if (collisionCircle3 == true) {
                redGreyCollisionCircle = true;
                collisionCircle = true;
            }
            //Daten für die GUI
            vecKugelRed = vector(meterToPixel(posxMeter) - circleRed.getLayoutX(),
                    meterToPixel(posyMeter) - circleRed.getLayoutY());
            //Bewegung der Kugel
            if (collisionCircleRed == false && collisionCircle == false) {
                circleRed.setLayoutX(meterToPixel(posxMeter));
                circleRed.setLayoutY(meterToPixel(posyMeter));
            }

        }
    }

    //Fallfunktion für die blaue Kugel
    public static void fallCircleBlue(Circle circleBlue, Circle circleRed, Circle circleGrey) {
        if (checkFallBlue == true) {
            vecFalling = addVector(vecWind.get(0), vecWind.get(1), vecAccelerationFall.get(0), vecAccelerationFall.get(1));
            countFallBlue++;
            double timeObject = time * countFallBlue;
            double newPositionX, newPositionY;
            if (vecStartSpeedObject != null && vecStartSpeedObject.size() > 1) {
                // v = a * t + vstart
                deltaVxObject = ((vecFalling.get(0) * timeObject) + vecStartSpeedObject.get(0));
                deltaVyObject = ((vecFalling.get(1) * timeObject)) + vecStartSpeedObject.get(1);
            } else {
                // v = a * t
                deltaVxObject = ((vecFalling.get(0) * timeObject));
                deltaVyObject = ((vecFalling.get(1) * timeObject));
            }

            double savePosX = positionBlueX;
            double savePosY = positionBlueY;

            if (vecStartSpeedObject != null && vecStartSpeedObject.size() > 1) {
                // s = vstart * t + a * 0.5 * t^2
                positionBlueX = vecStartSpeedObject.get(0) * timeObject + vecFalling.get(0) * 0.5 * Math.pow(timeObject, 2);
                positionBlueY = vecStartSpeedObject.get(1) * timeObject + vecFalling.get(1) * 0.5 * Math.pow(timeObject, 2);
            } else {
                // s = a * 0.5 * t^2
                positionBlueX = vecFalling.get(0) * 0.5 * Math.pow(timeObject, 2);
                positionBlueY = vecFalling.get(1) * 0.5 * Math.pow(timeObject, 2);
            }

            newPositionX = (positionBlueX - savePosX);
            double posxMeter = pixelToMeter(circleBlue.getLayoutX()) + newPositionX;

            newPositionY = (positionBlueY - savePosY);
            double posyMeter = pixelToMeter(circleBlue.getLayoutY()) + newPositionY;

            collisionCircleBlue = checkCollision(circleBlue, meterToPixel(posxMeter), meterToPixel(posyMeter), circleBlue.getLayoutX(), circleBlue.getLayoutY());

            vecKugelBlue = vector(meterToPixel(posxMeter) - circleBlue.getLayoutX(),
                    meterToPixel(posyMeter) - circleBlue.getLayoutY());

            boolean collisionCircle2 = collisionCircle(circleBlue, circleRed, meterToPixel(posxMeter), meterToPixel(posyMeter));
            boolean collisionCircle3 = collisionCircle(circleBlue, circleGrey, meterToPixel(posxMeter), meterToPixel(posyMeter));
            if (collisionCircle2 == true) {
                blueRedCollisionCircle = true;
                collisionCircle = true;
            }
            if (collisionCircle3 == true) {
                blueGreyCollisionCircle = true;
                collisionCircle = true;
            }
            if (collisionCircleBlue == false && collisionCircle == false) {
                circleBlue.setLayoutX(meterToPixel(posxMeter));
                circleBlue.setLayoutY(meterToPixel(posyMeter));
            }

        }
    }

    //Fallfunktion für die graue Kugel
    public static void fallCircleGrey(Circle circleGrey, Circle circleRed, Circle circleBlue) {
        if (checkFallGrey == true) {
            vecFalling = addVector(vecWind.get(0), vecWind.get(1), vecAccelerationFall.get(0), vecAccelerationFall.get(1));
            countFallGrey++;
            double timeObject = time * countFallGrey;
            double newPositionX, newPositionY;
            if (vecStartSpeedObject2 != null && vecStartSpeedObject2.size() > 1) {
                // v = a * t + vstart
                deltaVxObject2 = ((vecFalling.get(0) * timeObject) + vecStartSpeedObject2.get(0));
                deltaVyObject2 = ((vecFalling.get(1) * timeObject)) + vecStartSpeedObject2.get(1);
            } else {
                // v = a * t
                deltaVxObject2 = ((vecFalling.get(0) * timeObject));
                deltaVyObject2 = ((vecFalling.get(1) * timeObject));
            }

            double savePosX = positionGreyX;
            double savePosY = positionGreyY;

            if (vecStartSpeedObject2 != null && vecStartSpeedObject2.size() > 1) {
                // s = vstart * t + a * 0.5 * t^2
                positionGreyX = vecStartSpeedObject2.get(0) * timeObject + vecFalling.get(0) * 0.5 * Math.pow(timeObject, 2);
                positionGreyY = vecStartSpeedObject2.get(1) * timeObject + vecFalling.get(1) * 0.5 * Math.pow(timeObject, 2);
            } else {
                // s = a * 0.5 * t^2
                positionGreyX = vecFalling.get(0) * 0.5 * Math.pow(timeObject, 2);
                positionGreyY = vecFalling.get(1) * 0.5 * Math.pow(timeObject, 2);
            }

            newPositionX = (positionGreyX - savePosX);
            double posxMeter = pixelToMeter(circleGrey.getLayoutX()) + newPositionX;

            newPositionY = (positionGreyY - savePosY);
            double posyMeter = pixelToMeter(circleGrey.getLayoutY()) + newPositionY;


            collisionCircleGrey = checkCollision(circleGrey, meterToPixel(posxMeter), meterToPixel(posyMeter),
                    circleGrey.getLayoutX(), circleGrey.getLayoutY());
            boolean collisionCircle2 = collisionCircle(circleGrey, circleRed, meterToPixel(posxMeter), meterToPixel(posyMeter));
            boolean collisionCircle3 = collisionCircle(circleGrey, circleBlue, meterToPixel(posxMeter), meterToPixel(posyMeter));
            if (collisionCircle2 == true) {
                greyRedCollisionCircle = true;
                collisionCircle = true;
            }
            if (collisionCircle3 == true) {
                greyBlueCollisionCircle = true;
                collisionCircle = true;
            }

            vecKugelGrey = vector(meterToPixel(posxMeter) - circleGrey.getLayoutX(),
                    meterToPixel(posyMeter) - circleGrey.getLayoutY());

            if (collisionCircleGrey == false && collisionCircle == false) {
                circleGrey.setLayoutX(meterToPixel(posxMeter));
                circleGrey.setLayoutY(meterToPixel(posyMeter));
            }

        }
    }

}
