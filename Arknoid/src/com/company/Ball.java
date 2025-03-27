package com.company;

//import javafx.scene.shape.Circle;

public class Ball {

    public double x;
    public double y;
    public double s = 0.4;
    public double vx;
    public double vy;

    public Ball(double x, double y){
        this.x = x;
        this.y = y;
        randomWektor();
    }

    public void draw(){

        StdDraw.filledCircle(x,y,2);

    }

    public void randomWektor() {

        this.vx = Math.random() * this.s;
        if(Math.random() < 0.5) {
            this.vx = this.vx * -1.0;
        }

        this.vy = Math.sqrt((this.s*this.s) - this.vx * this.vx);
//        if(Math.random() < 0.5) {
//            this.vy = this.vy * -1.0;
//        }

    }

    public void move(){

        this.x = x + vx;
        this.y = y + vy;

    }



}
