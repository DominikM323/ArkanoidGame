package com.company;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.io.*;
import javax.sound.sampled.*;
import java.net.URL;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

public class Main {

    public static int lives;
    public static Paletka player;
    public static Ball ball;
    public static boolean isGameOn;
    public static int frames = 0;
    public static Date dateStart = new Date();
    public static ArrayList<Brick> bricks = new ArrayList<Brick>();
    public static int BRICK_ROWS = 5;
    public static int BRICK_ROW_LENGTH = 7;
    public static int SCORE;
    public static int BRICKS_HIT = 0;

    public static void main(String[] args) throws InterruptedException {

        init();
        newGame();
        //PlayHitSound();
        play();



    }

    public static void play() throws InterruptedException {

        while (true) {
            //System.out.println("aaa");
            Date date1 = new Date();
            frames++;
            player.move();
            ball.move();
            drawFrame();
            findCollisions(ball, player);
            findBrickCollisions();
            loseABall(ball);
            nextLevel();
            slowFrame(date1);
            //Date date2 = new Date();

        }

    }

    public static void init() {
        StdDraw.setCanvasSize(512, 512);
        StdDraw.setScale(0.0, 100.0);
        StdDraw.setYscale(-10, 100.0);
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.DARK_GRAY);
        StdDraw.enableDoubleBuffering();

        SCORE = 0;
    }

    public static void slowFrame(Date date1) throws InterruptedException {
        do {
            Thread.sleep(1);
        } while (new Date().getTime() - date1.getTime() < 4);
    }

    public static void newGame() {

        ball = new Ball(50, 20);
        player = new Paletka();
        isGameOn = true;
        lives = 3;
        spawnBricks();        //!
    }

    public static void drawFrame() {
        StdDraw.clear(StdDraw.DARK_GRAY);

        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.line(0, 0, 100, 0);
        ball.draw();

        StdDraw.setPenColor(StdDraw.DARK_GRAY);
        StdDraw.filledRectangle(50, -5, 50, 5);
        StdDraw.setPenColor(StdDraw.WHITE);
        player.draw();
        StdDraw.text(30, -5, "Score: " + SCORE);


        for (int i = 0; i < bricks.size(); i++) {
            if (bricks.get(i).isShown == true) {
                bricks.get(i).drawBrick();
            }
        }


        StdDraw.filledCircle(5, -4.5, 1);
        StdDraw.text(7.5, -5, String.valueOf(lives));
        //StdDraw.text(90, -5, String.valueOf(frames));

        //long seconds = (new Date().getTime() - dateStart.getTime())/1000;

//        if(seconds > 0) {
//            StdDraw.text(70, -5, String.valueOf(frames / seconds));
//        }
        StdDraw.show();

    }

    public static void findCollisions(Ball ball, Paletka player) {


        //top,left and right wall collisions
        if ((ball.x > 98 && ball.vx > 0) || (ball.x < 2 && ball.vx < 0)) {
            ball.vx = ball.vx * -1.0;
            PlayWallSound();
        }
        if (ball.y > 98 && ball.vy > 0) {
            ball.vy = ball.vy * -1.0;
            PlayWallSound();
        }

        //top pallet collisions
        if (ball.y <= 12 && ball.y > 8 && ball.x > player.p1.x && ball.x < player.p2.x) {
            ball.y = 12;
            ball.vy = ball.vy * -1.0;
            PlayPlayerSound();
            if (StdDraw.isKeyPressed(68) && player.x < 87.5) {

                ball.vx = ball.vx + 0.05;
                if (ball.vx > ball.s) {
                    ball.vx = 0.15;
                }
                ball.vy = Math.sqrt((ball.s * ball.s) - ball.vx * ball.vx);


            } else if (StdDraw.isKeyPressed(65) && player.x > 12.5) {

                ball.vx = ball.vx - 0.05;
                if (ball.vx <= ball.s * -1.0) {
                    ball.vx = -0.15;
                }
                ball.vy = Math.sqrt((ball.s * ball.s) - ball.vx * ball.vx);


            }


        }


    }

    public static void PlayHitSound(){

          PlaySound("HIT.wav");

    }

    public static void PlayPlayerSound(){

        PlaySound("HIT2.wav");

    }

    public static void PlayWallSound(){

        PlaySound("HIT3.wav");

    }

    public static void PlayLoseSound(){

        PlaySound("LOSE.wav");

    }

    public static void PlayWinSound(){
        PlaySound("WIN.wav");
    }

    public static void PlaySound(String fileName){

        try {
            // Open an audio input stream.
            Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
            File file = new File(path+"/sound/" + fileName);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();

            clip.open(audioIn);
            //--------
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-12.5f);
            //---------
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void findBrickCollisions() {

        for (int i = 0; i < bricks.size(); i++) {


            if (bricks.get(i).isShown == true) {
                if (ball.y > 60 && (ball.y >= bricks.get(i).p1.y - 2) && (ball.y <= bricks.get(i).p4.y + 2)
                        && (ball.x >= bricks.get(i).p1.x - 2) && (ball.x <= bricks.get(i).p4.x + 2)) {


                    PlayHitSound();
                    bricks.get(i).isShown = false;
                    BRICKS_HIT += 1;
                    bricks.get(i).x = 999;
                    bricks.get(i).y = 999;
                    //System.out.println("p1x: " + bricks.get(i).p1.x + "  p1y: " + bricks.get(i).p1.y + " p4x: " + bricks.get(i).p4.x + "  p4y: " + bricks.get(i).p4.y);
                    SCORE += 10;

                    if (ball.x >= bricks.get(i).p1.x - 1.8 && ball.x <= bricks.get(i).p2.x + 1.8) {
                        ball.vy = ball.vy * -1.0;
                    } else {
                        ball.vx = ball.vx * -1.0;


                    }

                }
            }



        }

    }

    public static void nextLevel() {

        if (BRICKS_HIT == BRICK_ROW_LENGTH * BRICK_ROWS) {
            PlayWinSound();
            BRICKS_HIT = 0;
            ball = new Ball(50, 20);
            player = new Paletka();
            bricks.clear();
            spawnBricks();
            SCORE +=150;
            drawFrame();
            try {

                while (true) {
                    Thread.sleep(10);
                    if (StdDraw.isKeyPressed(32)) {
                        break;
                    }
                }

            } catch (InterruptedException e) {

            }

        }
    }

    public static void lose(){

        BRICKS_HIT = 0;


        try {

            while (true) {

                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setFont(new Font("Sans Seriff", Font.BOLD, 30));
                StdDraw.text(50, 50 , "GAME OVER");
                StdDraw.show();
                StdDraw.setFont();

                Thread.sleep(10);
                if (StdDraw.isKeyPressed(32)) {
                    break;
                }
            }

        } catch (InterruptedException e) {
        }
        SCORE = 0;
        lives = 3;
        ball = new Ball(50, 20);
        player = new Paletka();
        bricks.clear();
        spawnBricks();
        drawFrame();

    }

    public static void spawnBricks() {
        //bricks.add(new Brick(9, 94));

        double xb = 8;
        double yb = 100;

        for (int i = 0; i < BRICK_ROWS; i++) {

            xb = 8;
            yb = yb - 6;

            for (int j = 0; j < BRICK_ROW_LENGTH; j++) {

                bricks.add(new Brick(xb, yb));
                xb = xb + 14;
            }

        }
    }

    public static void loseABall(Ball ball) {

        if (ball.y < -10) {

            PlayLoseSound();

            if (lives > 0) {
                lives -= 1;
            } else if (lives == 0) {
                //game over
                lose();
            }

            ball.x = 50;
            ball.y = 20;
            player.x = 50;
            drawFrame();

            try {

                while (true) {
                    Thread.sleep(10);
                    if (StdDraw.isKeyPressed(32)) {
                        break;
                    }
                }

            } catch (InterruptedException e) {

            }

            ball.randomWektor();
        }

    }
}


