package app;

import Objects.Rectangle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import static Objects.DrawRectangle.*;
import static Objects.Rectangle.getRectanglePos;
import static data.HistoryLine.*;
import static physic.CollisionHandling.*;
import static physic.Collision.*;
import static physic.Physics.*;
import static physic.Fall.*;
import static Objects.Color2.*;
import static physic.Roll.*;
import static physic.Spring.*;


public class MainController implements Initializable {

    @FXML
    private TextField startpositionY;
    @FXML
    private TextField startpositionX;
    @FXML
    private TextField startpositionBlueY;
    @FXML
    private TextField startpositionBlueX;
    @FXML
    private TextField startpositionGreyY;
    @FXML
    private TextField startpositionGreyX;
    @FXML
    private TextField startSpeedX;
    @FXML
    private TextField startSpeedY;
    @FXML
    private TextField startSpeedBlueX;
    @FXML
    private TextField startSpeedBlueY;
    @FXML
    private TextField startSpeedGreyX;
    @FXML
    private TextField startSpeedGreyY;
    @FXML
    private TextField windX;
    @FXML
    private TextField windY;
    @FXML
    private TextField angle;
    @FXML
    private TextField feder;
    @FXML
    private TextField feder2;
    @FXML
    private TextField masseGrey;
    @FXML
    private TextField masseBlue;
    @FXML
    private TextField masseRed;
    @FXML
    private Label statusFeder;
    @FXML
    private Label vecRedx;
    @FXML
    private Label vecRedy;
    @FXML
    private Label vecRedSpeedx;
    @FXML
    private Label vecRedSpeedy;
    @FXML
    private Label vecBluex;
    @FXML
    private Label vecBluey;
    @FXML
    private Label vecGreyx;
    @FXML
    private Label vecGreyy;
    @FXML
    private Label vecBlueSpeedx;
    @FXML
    private Label vecBlueSpeedy;
    @FXML
    private Label vecGreySpeedx;
    @FXML
    private Label vecGreySpeedy;
    @FXML
    private Circle circleRed;
    @FXML
    private Circle circleBlue;
    @FXML
    private Circle circleGrey;
    @FXML
    private Canvas canvas;
    @FXML
    private CheckBox checkBoxDrawLine;
    @FXML
    private Button pause;
    @FXML
    private Button play;

    private static final DecimalFormatSymbols dotSymbol = new DecimalFormatSymbols(Locale.ENGLISH);
    private static final DecimalFormat df = new DecimalFormat("0.00", dotSymbol);
    private static final double g = -9.81;
    public static boolean pauseButton = false;
    public static GraphicsContext gc;
    double posx = 0, posy = 0;
    public static double masseRedCircle = 0, masseBlueCircle = 0, masseGreyCircle = 0, federweg = 0;
    public static double distanceX = 0, distanceY = 0, federkonstante = 0, angleBalken = 0;
    public static Rectangle rec1 = new Rectangle();
    Rectangle rec1SavePos = new Rectangle();
    public static Rectangle rec2 = new Rectangle();
    public static Rectangle rec3 = new Rectangle();
    public static Rectangle rec4 = new Rectangle();
    public static Rectangle recSpring = new Rectangle();
    public static Rectangle recLeftSpring = new Rectangle();
    public static Rectangle recRightSpring = new Rectangle();
    public static Rectangle recSpringButton = new Rectangle();
    public static Rectangle recWorldTopBorder = new Rectangle();
    public static Rectangle recWorldRightBorder = new Rectangle();
    public static Rectangle recWorldBottomBorder = new Rectangle();
    public static Rectangle recWorldLeftBorder = new Rectangle();

    @FXML
    protected void onResetButtonClick() {
        circleRed.setLayoutY(Double.parseDouble(startpositionY.getText()));
        circleRed.setLayoutX(Double.parseDouble(startpositionX.getText()));
        circleBlue.setLayoutY(Double.parseDouble(startpositionBlueY.getText()));
        circleBlue.setLayoutX(Double.parseDouble(startpositionBlueX.getText()));
        circleGrey.setLayoutY(Double.parseDouble(startpositionGreyY.getText()));
        circleGrey.setLayoutX(Double.parseDouble(startpositionGreyX.getText()));
        angleBalken = Double.parseDouble(angle.getText());
        countFallRed = 0;
        countFallBlue = 0;
        countFallGrey = 0;
        countRollRed = 0;
        countRollBlue = 0;
        countRollGrey = 0;
        positionRedY = 0;
        positionBlueY = 0;
        positionBlueX = 0;
        positionGreyY = 0;
        positionGreyX = 0;
        positionRedX = 0;
        curDistanceRed = 0;
        curDistanceBlue = 0;
        curDistanceGrey = 0;
        checkFallRed = true;
        checkFallBlue = true;
        checkFallGrey = true;
        checkRollRed = false;
        checkRollBlue = false;
        checkRollGrey = false;
        federSprung = false;
        vecStartSpeedCircle = vector(0, 0);
        vecStartSpeedObject = vector(0, 0);
        vecStartSpeedObject2 = vector(0, 0);
        angleLot = 0.0;
        count = 0;
        collisionCircleRed = false;
        collisionCircleBlue = false;
        collisionCircle = false;
        checkSpring = false;
        checkSpringButton = false;
        pauseButton = false;
        play.setVisible(true);
        pause.setVisible(false);

        if (canvas != null && gc != null) {
            //Zeichenfläche zurücksetzen
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            gc = canvas.getGraphicsContext2D();

            getRectanglePos(recSpringButton, 161, 50, 180, 50, 180, 150, 161, 150);
            rotateRectangle(rec1, Math.toRadians(Double.parseDouble(angle.getText())),
                    rec1SavePos.getP1(), rec1SavePos.getP2(), rec1SavePos.getP3(), rec1SavePos.getP4(),
                    (rec1SavePos.getP2().get(0) - rec1SavePos.getP1().get(0)) / 2 + rec1SavePos.getP1().get(0),
                    (rec1SavePos.getP4().get(1) - rec1SavePos.getP1().get(1)) / 2 + rec1SavePos.getP1().get(1));
            drawRectangle(gc, rec1.getP1(), rec1.getP2(), rec1.getP3(), rec1.getP4(), black, Double.parseDouble(angle.getText()));
            drawRectangle(gc, rec2.getP1(), rec2.getP2(), rec2.getP3(), rec2.getP4(), black, 120);
            drawRectangle(gc, rec3.getP1(), rec3.getP2(), rec3.getP3(), rec3.getP4(), black, 45);
            drawRectangle(gc, rec4.getP1(), rec4.getP2(), rec4.getP3(), rec4.getP4(), brown, 0);
            drawRectangle(gc, recSpring.getP1(), recSpring.getP2(), recSpring.getP3(), recSpring.getP4(), blue, 0);
            drawRectangle(gc, recLeftSpring.getP1(), recLeftSpring.getP2(), recLeftSpring.getP3(), recLeftSpring.getP4(), blue, 0);
            drawRectangle(gc, recRightSpring.getP1(), recRightSpring.getP2(), recRightSpring.getP3(),
                    recRightSpring.getP4(), blue, 0);
            drawRectangle(gc, recSpringButton.getP1(), recSpringButton.getP2(),
                    recSpringButton.getP3(), recSpringButton.getP4(), green, 0);
            drawRectangle(gc, recWorldTopBorder.getP1(), recWorldTopBorder.getP2(),
                    recWorldTopBorder.getP3(), recWorldTopBorder.getP4(), brown, 0);
            drawRectangle(gc, recWorldRightBorder.getP1(), recWorldRightBorder.getP2(),
                    recWorldRightBorder.getP3(), recWorldRightBorder.getP4(), brown, 0);
            drawRectangle(gc, recWorldBottomBorder.getP1(), recWorldBottomBorder.getP2(),
                    recWorldBottomBorder.getP3(), recWorldBottomBorder.getP4(), brown, 0);
            drawRectangle(gc, recWorldLeftBorder.getP1(), recWorldLeftBorder.getP2(),
                    recWorldLeftBorder.getP3(), recWorldLeftBorder.getP4(), brown, 0);
        }
        timeline.stop();
    }

    @FXML
    protected void onStartButtonClick() {
        if (pauseButton == false) {
            vecSpeedUp(0, g);
            vecWind(Double.parseDouble(windX.getText()), Double.parseDouble(windY.getText()));
            vecStartSpeedCircle = vector(Double.parseDouble(startSpeedX.getText()),
                    Double.parseDouble(startSpeedY.getText()));
            vecStartSpeedObject = vector(Double.parseDouble(startSpeedBlueX.getText()),
                    Double.parseDouble(startSpeedBlueY.getText()));
            vecStartSpeedObject2 = vector(Double.parseDouble(startSpeedGreyX.getText()),
                    Double.parseDouble(startSpeedGreyY.getText()));
            masseRedCircle = Double.parseDouble(masseRed.getText());
            masseBlueCircle = Double.parseDouble(masseBlue.getText());
            masseGreyCircle = Double.parseDouble(masseGrey.getText());
            federweg = Double.parseDouble(feder2.getText());
            federkonstante = Double.parseDouble(feder.getText());
            play.setVisible(false);
            pause.setVisible(true);
            angleBalken = Double.parseDouble(angle.getText());
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
            pauseButton = false;
        } else {
            play.setVisible(false);
            pause.setVisible(true);
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }
    }

    @FXML
    protected void onPauseButtonClick() {
        if (timeline.getStatus() == Animation.Status.RUNNING) {
            pause.setVisible(false);
            play.setVisible(true);
            pauseButton = true;
            timeline.stop();
        }
    }

    //Die Federspannung zurücksetzen
    @FXML
    protected void onResetFeder() {
        count = 0;
        checkSpringButton = false;
        federSprung = false;
        statusFeder.setText("gespannt");
        statusFeder.setTextFill(green);
        gc.clearRect(recSpringButton.getP1().get(0), recSpringButton.getP1().get(1),
                recSpringButton.getP2().get(0) - recSpringButton.getP1().get(0) + 1,
                recSpringButton.getP3().get(1) - recSpringButton.getP2().get(1) + 1);

        getRectanglePos(recSpringButton, 161, 50, 180, 50, 180, 150, 161, 150);
        drawRectangle(MainController.gc, recSpringButton.getP1(), recSpringButton.getP2(),
                recSpringButton.getP3(), recSpringButton.getP4(), green, 0);
    }

    public void dragAndDrop(Circle circleDrag, TextField positionX, TextField positionY) {
        //Hover Animation
        circleDrag.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> circleDrag.setCursor(Cursor.HAND));
        circleDrag.addEventHandler(MouseEvent.MOUSE_EXITED, event -> circleDrag.setCursor(Cursor.DEFAULT));

        //Speichern der aktuellen Position
        circleDrag.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            posx = event.getX();
            posy = event.getY();
        });

        circleDrag.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> circleDrag.setCursor(Cursor.DEFAULT));

        //Drag/Drop
        circleDrag.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            circleDrag.setCursor(Cursor.MOVE);
            distanceX = event.getX();
            distanceY = event.getY();
            circleDrag.setLayoutX(circleDrag.getLayoutX() + distanceX);
            circleDrag.setLayoutY(circleDrag.getLayoutY() + distanceY);
            positionX.setText(df.format(circleDrag.getLayoutX()));
            positionY.setText(df.format(circleDrag.getLayoutY()));
            if (circleDrag.equals(circleRed)) {
                checkRollRed = false;
                checkFallRed = true;
            }
            if (circleDrag.equals(circleBlue)) {
                checkRollBlue = false;
                checkFallBlue = true;
            }
            if (circleDrag.equals(circleGrey)) {
                checkRollGrey = false;
                checkFallGrey = true;
            }

        });

    }

    //Simulation mit 60 Frames die Sekunde
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000.0 / 60.0), new EventHandler<>() {

        @Override
        public void handle(ActionEvent actionEvent) {
            gc = canvas.getGraphicsContext2D();

            //Feder
            controllSpring(circleRed, circleBlue);
            controllSpringRelaxed();
            //Bewegung der Kugel
            rollCircleRed(circleRed, circleBlue, circleGrey);
            //Collisionhandling nach jeder Bewegung
            collisionHandling1();
            //CollisionHandlingCircle nach jeder Bewegung
            collisionHandlingCircle(circleRed, circleBlue, circleGrey);

            rollCircleBlue(circleBlue, circleRed, circleGrey);
            collisionHandling1();
            collisionHandlingCircle(circleRed, circleBlue, circleGrey);

            rollCircleGrey(circleGrey, circleRed, circleBlue);
            collisionHandling1();
            collisionHandlingCircle(circleRed, circleBlue, circleGrey);

            fallCircleGrey(circleGrey, circleRed, circleBlue);
            collisionHandling1();
            collisionHandlingCircle(circleRed, circleBlue, circleGrey);

            fallCircleRed(circleRed, circleBlue, circleGrey);
            collisionHandling1();
            collisionHandlingCircle(circleRed, circleBlue, circleGrey);

            fallCircleBlue(circleBlue, circleRed, circleGrey);
            collisionHandling1();
            collisionHandlingCircle(circleRed, circleBlue, circleGrey);

            //Daten für die Gui
            getVecAndSpeed(vecKugelRed, vecRedx, vecRedy, vecRedSpeedx, vecRedSpeedy, deltaVx, deltaVy);
            getVecAndSpeed(vecKugelBlue, vecBluex, vecBluey, vecBlueSpeedx, vecBlueSpeedy, deltaVxObject, deltaVyObject);
            getVecAndSpeed(vecKugelGrey, vecGreyx, vecGreyy, vecGreySpeedx, vecGreySpeedy, deltaVxObject2, deltaVyObject2);

            federweg = Double.parseDouble(feder2.getText());
            federkonstante = Double.parseDouble(feder.getText());

            if(checkSpringButton == true){
                statusFeder.setText("entspannt");
                statusFeder.setTextFill(red);
            }
            //Verlauf der Kugel zeichnen, falls checkBox == true
            if (checkBoxDrawLine.isSelected() && canvas != null) {
                drawLine(circleRed, gc);
                drawLine2(circleBlue, gc);
                drawLine3(circleGrey, gc);
            }
        }
    }));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dotSymbol.setDecimalSeparator('.');
        play.setVisible(true);
        pause.setVisible(false);
        statusFeder.setText("gespannt");
        statusFeder.setTextFill(green);
        dragAndDrop(circleRed, startpositionX, startpositionY);
        dragAndDrop(circleBlue, startpositionBlueX, startpositionBlueY);
        dragAndDrop(circleGrey, startpositionGreyX, startpositionGreyY);

        getRectanglePos(rec1, 300, 535, 800, 535, 800, 560, 300, 560);
        getRectanglePos(rec1SavePos, rec1.getP1().get(0), rec1.getP1().get(1), rec1.getP2().get(0), rec1.getP2().get(1),
                rec1.getP3().get(0), rec1.getP3().get(1), rec1.getP4().get(0), rec1.getP4().get(1));
        getRectanglePos(rec2, 300, 285, 1300, 285, 1300, 315, 300, 315);
        getRectanglePos(rec3, -200, 535, 500, 535, 500, 560, -200, 560);
        getRectanglePos(rec4, 300, 485, 1000, 485, 1000, 515, 300, 515);
        getRectanglePos(recSpring, 100, 0, 140, 0, 140, 100, 100, 100);

        getRectanglePos(recLeftSpring, 80, 0, 99, 0, 99, 200, 80, 200);
        getRectanglePos(recRightSpring, 141, 0, 160, 0, 160, 200, 141, 200);
        getRectanglePos(recSpringButton, 161, 50, 180, 50, 180, 150, 161, 150);
        getRectanglePos(recWorldTopBorder, 0, 685, 1300, 685, 1300, 700, 0, 700);
        getRectanglePos(recWorldRightBorder, 1285, 0, 1300, 0, 1300, 700, 1285, 700);
        getRectanglePos(recWorldBottomBorder, 0, 0, 1300, 0, 1300, 15, 0, 15);
        getRectanglePos(recWorldLeftBorder, 0, 0, 15, 0, 15, 700, 0, 700);

        rotateRectangle(rec1, Math.toRadians(Double.parseDouble(angle.getText())),
                rec1.getP1(), rec1.getP2(), rec1.getP3(), rec1.getP4(), (rec1.getP2().get(0) - rec1.getP1().get(0)) / 2 + rec1.getP1().get(0),
                (rec1.getP4().get(1) - rec1.getP1().get(1)) / 2 + rec1.getP1().get(1));
        rotateRectangle(rec2, 60,
                rec2.getP1(), rec2.getP2(), rec2.getP3(), rec2.getP4(), (rec2.getP2().get(0) - rec2.getP1().get(0)) / 2 + rec2.getP1().get(0),
                (rec2.getP4().get(1) - rec2.getP1().get(1)) / 2 + rec2.getP1().get(1));
        rotateRectangle(rec3, 70,
                rec3.getP1(), rec3.getP2(), rec3.getP3(), rec3.getP4(), (rec3.getP2().get(0) - rec3.getP1().get(0)) / 2 + rec3.getP1().get(0),
                (rec3.getP4().get(1) - rec3.getP1().get(1)) / 2 + rec3.getP1().get(1));
        //Objekte zeichnen beim starten des Programms
        if (canvas != null) {
            gc = canvas.getGraphicsContext2D();

            drawRectangle(gc, rec1.getP1(), rec1.getP2(), rec1.getP3(), rec1.getP4(), black, Double.parseDouble(angle.getText()));
            drawRectangle(gc, rec2.getP1(), rec2.getP2(), rec2.getP3(), rec2.getP4(), black, 45);
            drawRectangle(gc, rec3.getP1(), rec3.getP2(), rec3.getP3(), rec3.getP4(), black, 45);
            drawRectangle(gc, rec4.getP1(), rec4.getP2(), rec4.getP3(), rec4.getP4(), brown, 0);
            drawRectangle(gc, recSpring.getP1(), recSpring.getP2(), recSpring.getP3(), recSpring.getP4(), blue, 0);
            drawRectangle(gc, recLeftSpring.getP1(), recLeftSpring.getP2(),
                    recLeftSpring.getP3(), recLeftSpring.getP4(), blue, 0);
            drawRectangle(gc, recRightSpring.getP1(), recRightSpring.getP2(),
                    recRightSpring.getP3(), recRightSpring.getP4(), blue, 0);
            drawRectangle(gc, recSpringButton.getP1(), recSpringButton.getP2(),
                    recSpringButton.getP3(), recSpringButton.getP4(), green, 0);
            drawRectangle(gc, recWorldTopBorder.getP1(), recWorldTopBorder.getP2(),
                    recWorldTopBorder.getP3(), recWorldTopBorder.getP4(), brown, 0);
            drawRectangle(gc, recWorldRightBorder.getP1(), recWorldRightBorder.getP2(),
                    recWorldRightBorder.getP3(), recWorldRightBorder.getP4(), brown, 0);
            drawRectangle(gc, recWorldBottomBorder.getP1(), recWorldBottomBorder.getP2(),
                    recWorldBottomBorder.getP3(), recWorldBottomBorder.getP4(), brown, 0);
            drawRectangle(gc, recWorldLeftBorder.getP1(), recWorldLeftBorder.getP2(),
                    recWorldLeftBorder.getP3(), recWorldLeftBorder.getP4(), brown, 0);

        }
    }

    //Daten für die GUI
    public static void getVecAndSpeed(Vector<Double> vecKugel, Label vecX, Label vecY, Label speedX,
                                      Label speedY, double vx, double vy) {
        if (vecKugel != null) {
            vecKugel = vectorNorm(vecKugel);
            if (vx != 0 || vy != 0) {
                vecX.setText(df.format(vecKugel.get(0)));
                vecY.setText(df.format(vecKugel.get(1)));
                speedX.setText(df.format(vx));
                speedY.setText(df.format(vy));
            } else {
                vecX.setText("0.0");
                vecY.setText("0.0");
            }
        }
    }
}
