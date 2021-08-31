package com.starwars.ml.quasar_fire.entity;

public class CoordinatesEntity {

    private double x;
    private double y;

    public CoordinatesEntity(double[] elements){
        this.x = elements[0];
        this.y = elements[1];
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
