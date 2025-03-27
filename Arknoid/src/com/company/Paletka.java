package com.company;

public class Paletka {

    public double x = 50;
    public double y = 10;
    protected double halfWidth = 12;
    protected double halfHeight = 1;
    double speed = 0.3;

    public Punkt p1 = new Punkt(x - halfWidth, y - halfHeight);
    public Punkt p2 = new Punkt(x + halfWidth, y - halfHeight);;
    public Punkt p3 = new Punkt(x - halfWidth, y + halfHeight);;
    public Punkt p4 = new Punkt(x + halfWidth, y + halfHeight);;

    public void draw(){

        StdDraw.filledRectangle(x, y, halfWidth, halfHeight);


    }

    public void move(){

        //System.out.println(isRP + " " + isLP);

        if(StdDraw.isKeyPressed(65) && StdDraw.isKeyPressed(68)){

        }else if(StdDraw.isKeyPressed(68) && x < 89){
            x = x + speed;
        }else if(StdDraw.isKeyPressed(65) && x > 11){
            x = x - speed;
        }



//        if(isRP == true || x < 89){
//            x = x + 1;
//        } if(isLP == true || x > 11){
//            x = x-1;
//        }

        p1 = new Punkt(x - halfWidth, y - halfHeight);
        p2 = new Punkt(x + halfWidth, y - halfHeight);;
        p3 = new Punkt(x - halfWidth, y + halfHeight);;
        p4 = new Punkt(x + halfWidth, y + halfHeight);;

        //draw();

    }

}
