package Objects;

import java.util.Vector;

public class Rectangle {

    //Eck-Punkte des Rechtecks bestimmen
    public static Rectangle getRectanglePos(Rectangle rec,double p1x,double p1y,double p2x,double p2y,double p3x,
                                       double p3y,double p4x,double p4y){
        Vector<Double> p1 = new Vector<>();
        Vector<Double> p2 = new Vector<>();
        Vector<Double> p3 = new Vector<>();
        Vector<Double> p4 = new Vector<>();
        p1.add(0,p1x);
        p1.add(1,p1y);
        p2.add(0,p2x);
        p2.add(1,p2y);
        p3.add(0,p3x);
        p3.add(1,p3y);
        p4.add(0,p4x);
        p4.add(1,p4y);
        rec.setP1(p1);
        rec.setP2(p2);
        rec.setP3(p3);
        rec.setP4(p4);
        return rec;
    }
    private Vector<Double> p1 = new Vector<>();
    private Vector<Double> p2 = new Vector<>();
    private Vector<Double> p3 = new Vector<>();
    private Vector<Double> p4 = new Vector<>();
    public Vector<Double> getP1() {
        return p1;
    }

    public void setP1(Vector<Double> p1) {
        this.p1 = p1;
    }

    public Vector<Double> getP2() {
        return p2;
    }

    public void setP2(Vector<Double> p2) {
        this.p2 = p2;
    }

    public Vector<Double> getP3() {
        return p3;
    }

    public void setP3(Vector<Double> p3) {
        this.p3 = p3;
    }

    public Vector<Double> getP4() {
        return p4;
    }

    public void setP4(Vector<Double> p4) {
        this.p4 = p4;
    }

}
