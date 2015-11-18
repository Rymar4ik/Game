package com.diplomprogect.gameobject;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.diplomprogect.gamehelpers.AssetLoader;

public class Plane {
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private float rotation;
    private int width;
    private int height;

    private Rectangle boundingRectangle;

    private boolean isAlive;



    public Plane(float x, float y, int width, int height) {

        this.width = width;
        this.height = height;
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 460);

        boundingRectangle = new Rectangle();

        isAlive = true;
    }

    public void update(float delta) {
        velocity.add(acceleration.cpy().scl(delta));

        if (velocity.y > 200) {
            velocity.y = 200;
        }

        if (position.y < -3) {
            position.y = -3;
            velocity.y = 0;
        }

        position.add(velocity.cpy().scl(delta));

        boundingRectangle.set(position.x,position.y+ 5,18,4);



            }



    public void die(){
        isAlive = false;
        velocity.y = 0;
    }

    public  void decelerate(){
        acceleration.y = 0;
    }

    public boolean isFalling(){
        return velocity.y > 110;
    }

    public boolean doNotFlap(){
        return velocity.y > 70;
    }

    public void onClick(){
        if(isAlive){
           AssetLoader.flap.play();
            velocity.y = -140;}
    }

    public float getX(){
        return position.x;
    }

    public float getY(){
        return position.y;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public float getRotation(){
        return rotation;
    }

    public Rectangle getBoundingRectangle() {
        return boundingRectangle;}

    public boolean isAlive() {
        return isAlive;
    }

    public void onRestart(int y) {
        rotation = 0;
        position.y = y;
        velocity.x = 0;
        velocity.y = 0;
        acceleration.x = 0;
        acceleration.y = 460;
        isAlive = true;}














}