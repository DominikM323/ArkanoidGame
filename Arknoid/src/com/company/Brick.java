package com.company;

public class Brick {

    double x;
    double y;
    boolean wasHit = false;
    protected double halfWidth = 6;
    protected double halfHeight = 2;
    public boolean isShown;

    public Punkt p1 ;
    public Punkt p2 ;
    public Punkt p3 ;
    public Punkt p4 ;

    public Brick(double x, double y){

        this.x = x;
        this.y = y;
        this.isShown = true;

        p1 = new Punkt(x - halfWidth, y - halfHeight);
        p2 = new Punkt(x + halfWidth, y - halfHeight);;
        p3 = new Punkt(x - halfWidth, y + halfHeight);;
        p4 = new Punkt(x + halfWidth, y + halfHeight);;


    }

    public void drawBrick(){

        StdDraw.filledRectangle(x, y, halfWidth , halfHeight);


    }

}
