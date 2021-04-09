package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class TrackGhost extends Ghost {
    private float speedUp = 1f;
    private float startVelocity = 120f;
    private float dx, dy, magnitude;
    private Rocket rocket;

    public TrackGhost(Vector2 velocity, Vector2 position, int height, int width, Rocket rocket) {
        super(velocity, position, height, width);
        this.rocket = rocket;
        dx = dy = 0;
    }

    @Override
    public void update(float delta){
        dx = rocket.getX() - position.x;
        dy = rocket.getY() - position.y;
        magnitude = (float) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        dx = dx / magnitude * startVelocity * speedUp;
        dy = dy / magnitude * startVelocity * speedUp;
        velocity.set(dx, dy);
        position.add(velocity.cpy().scl(delta));
        boundingCircle.set(position.x + width/2, position.y + height/2, height/2);
        Gdx.app.log("TrackGhost", "Position: " + position.x + "\t" + position.y);
    }

    @Override
    public void increaseSpeed(){
        speedUp *= 1.01;
    }
}
