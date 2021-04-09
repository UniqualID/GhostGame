package Entities;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import World.GameRenderer;

public class Ghost {
    public Vector2 velocity, position;
    public Circle boundingCircle;

    public Vector2 getVelocity() {
        return velocity;
    }

    public float getX() {
        return position.x;
    }

    public float getY(){
        return position.y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Circle getBoundingCircle(){
        return boundingCircle;
    }
    public int height, width;

    public Ghost(Vector2 velocity, Vector2 position, int height, int width){
        this.velocity = velocity;
        this.position = position;
        this.height = height;
        this.width = width;
        boundingCircle = new Circle();
    }

    public void update(float delta){
        position.add(velocity.cpy().scl(delta));

        if((position.x < 0 && velocity.x < 0) || (position.x + width > GameRenderer.gameWidth && velocity.x > 0)){
            velocity.x *= -1;
        }
        if((position.y < 0 && velocity.y < 0) || (position.y + height > GameRenderer.gameHeight && velocity.y > 0)){
            velocity.y *= -1;
        }
        boundingCircle.set(position.x + width/2, position.y + height/2, height/2);
    }

    public void stop() {
        velocity.set(0,0);
    }

    public void increaseSpeed() {
        velocity.scl(1.01f);
    }
}
