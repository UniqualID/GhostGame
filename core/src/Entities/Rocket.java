package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import Helpers.AssetLoader;
import World.GameRenderer;

public class Rocket {
    private final float SPEEDLIMIT = 1000f;
    private final float DRAG = 900f;
    private final float ACCEL = 1400f;

    private Vector2 position, velocity, acceleration, drag;
    private Vector3 target;
    private float rotation, dx,dy, magnitude;
    private int width;
    private int height;
    private OrthographicCamera camera;

    private Polygon boundingRect;

    public Rocket(float x, float y, int width, int height){
        position = new Vector2(x,y);
        acceleration = new Vector2(0f,0f);
        velocity = new Vector2(0f, 0f);
        target = new Vector3(0f,0f,0f);
        drag = new Vector2(0f,0f);
        magnitude = 0f;
        this.width = width;
        this.height = height;

        int rectWidth = width / 3 * 2;
        boundingRect = new Polygon(new float[]{(width/6),0, (5*width/6),0, (5*width/6),height, (width/6),height});
        boundingRect.setOrigin(width/2, height/2);
    }
    public void update(float delta){

        if (Gdx.input.isTouched()) {
            velocity.add(acceleration.cpy().scl(delta));
            magnitude = magnitude(velocity);
            if (magnitude >= SPEEDLIMIT){
                velocity.set(velocity.x / magnitude * SPEEDLIMIT, velocity.y / magnitude * SPEEDLIMIT);
            }

            // CAN'T FIGURE OUT TRIG SO JUST ADD 90 DEGREES TO MAKE EVERYTHING WORK LMAOOOOOOO
            rotation = (float)Math.toDegrees(Math.atan2((double)acceleration.y,(double)acceleration.x)) - 90;

        } else if(magnitude(velocity) >= 10) {
            magnitude = magnitude(velocity);
            acceleration.set(velocity.x / magnitude * DRAG * -1, velocity.y / magnitude * DRAG *-1);
            velocity.add(acceleration.cpy().scl(delta));
        } else if(magnitude(velocity) < 10){
            velocity.set(0,0);
            acceleration.set(0,0);
        }

        if((position.x < 0 && velocity.x < 0) || (position.x + width > GameRenderer.gameWidth && velocity.x > 0))
            velocity.x = 0;
        if((position.y < 0 && velocity.y < 0) || (position.y + height > GameRenderer.gameHeight && velocity.y > 0))
            velocity.y = 0;
        position.add(velocity.cpy().scl(delta));


        boundingRect.setPosition(position.x,position.y);
        boundingRect.setRotation(rotation);
//        Gdx.app.log("Rocket", "Target x/y: " + target.x + "\t" + target.y
//                + "\tRocket x/y: " + position.x + "\t" + position.y + "\t" + magnitude(velocity) + "\t" + magnitude(acceleration) + "\t" + rotation);
    }
    public float magnitude(Vector2 vector){return (float) Math.sqrt(Math.pow(vector.x, 2) + Math.pow(vector.y, 2));}
    public void onClick(int x, int y, OrthographicCamera camera) {
        target.set(x, y,0f);
        camera.unproject(target);

        dx = target.x - position.x;
        dy = target.y - position.y;
        magnitude = (float) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        dx = dx/magnitude * ACCEL;
        dy = dy/magnitude * ACCEL;

        acceleration.set(dx, dy);

    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getRotation() {
        return rotation;
    }
    public Polygon getBoundingRect() {
        return boundingRect;
    }

    public void stop() {
        velocity.set(0,0);
        acceleration.set(0,0);
    }


}
