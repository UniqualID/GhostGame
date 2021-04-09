package World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GhostGame;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sound.midi.Track;

import Entities.Ghost;
import Entities.Rocket;
import Entities.SawGhost;
import Entities.TrackGhost;
import Helpers.AssetLoader;
import Screens.GameScreen;
import Screens.StartScreen;

public class GameWorld {
    private GhostGame game;
    private static final float GHOSTSPAWNTIME = 15;
    private static final float INCREASESPEEDTIME = 7;
    private static final int GHOSTCAP = 15;
    private Rocket rocket;
    private List<Ghost> ghosts;
    public static Random random;
    private float ghostTimer, ghostSpeedTimer;
    private boolean isEnd = false;
    private float score = 0;
    public enum GameState{
        READY,RUNNING,GAMEOVER;
    }
    public GameState gamestate;
    public GameWorld(GhostGame game){
        this.game = game;
        random = new Random();
        this.rocket = new Rocket(300,300,50,50);
        this.ghosts = new ArrayList<Ghost>();
        ghosts.add(new TrackGhost(vectorFromAngle(randomInRange(0,2*Math.PI)).scl(300f), new Vector2(300,600), 50,50, rocket));
        this.gamestate = GameState.READY;
        ghostTimer = ghostSpeedTimer = 0;
    }

    public void update(float delta){
        switch(gamestate){
            case READY:
                updateReady(delta);
                break;
            case RUNNING:
                updateRunning(delta);
                break;
            default:
                updateGameover(delta);
                break;
        }
    }
    public void updateReady(float delta){
        if(Gdx.input.justTouched())
            gamestate = GameState.RUNNING;
    }
    public void updateGameover(float delta){
        if((int)score > AssetLoader.getHighScore()){
            AssetLoader.setHighScore((int) score);
        }
        if(Gdx.input.justTouched())
            game.setScreen(new StartScreen(game));
    }
    public void updateRunning(float delta) {
        score += delta;
        ghostTimer += delta;
        ghostSpeedTimer += delta;
        if(ghostTimer > GHOSTSPAWNTIME && ghosts.size() < GHOSTCAP){
            if(ghosts.size() == 2)
                ghosts.add(new SawGhost(new Vector2(0,300), new Vector2(-25,600), 50,50));
            else if (ghosts.size() == 6)
                ghosts.add(new SawGhost(new Vector2(0,300), new Vector2(GameRenderer.gameWidth-25,600), 50,50));
            else if (ghosts.size() == 12)
                ghosts.add(new SawGhost(new Vector2(300,0), new Vector2(0,-25), 50,50));
            else if (ghosts.size() == 13)
                ghosts.add(new SawGhost(new Vector2(300,0), new Vector2(0,GameRenderer.gameHeight-25), 50,50));
            else if(ghosts.size() % 5 == 0)
                ghosts.add(new TrackGhost(vectorFromAngle(randomInRange(0,2*Math.PI)).scl(300f), new Vector2(300,600), 50,50, rocket));
            else
                ghosts.add(new Ghost(vectorFromAngle(randomInRange(0,2*Math.PI)).scl(300f), new Vector2(300,600), 50,50));
            ghostTimer = 0;
        }
        if(ghostSpeedTimer > INCREASESPEEDTIME){
            for(Ghost ghost : ghosts){
                ghost.increaseSpeed();
            }
            ghostSpeedTimer = 0;
        }
        rocket.update(delta);
        for(Ghost g : ghosts ){
            if(g != null)
                g.update(delta);
        }

        if(collision()){
            stop();
        }
    }

    private void stop() {
        rocket.stop();
        for(Ghost ghost : ghosts){
            ghost.stop();
        }
        gamestate = GameState.GAMEOVER;
    }

    private boolean collision() {
        for(Ghost ghost : ghosts){
            if(overlaps(rocket.getBoundingRect(), ghost.getBoundingCircle())) {
                AssetLoader.dead.play();
                return true;
            }
        }
        return false;
    }

    public boolean overlaps(Polygon polygon, Circle circle) {
        float []vertices=polygon.getTransformedVertices();
        Vector2 center=new Vector2(circle.x, circle.y);
        float squareRadius=circle.radius*circle.radius;
        for (int i=0;i<vertices.length;i+=2){
            if (i==0){
                if (Intersector.intersectSegmentCircle(new Vector2(vertices[vertices.length-2], vertices[vertices.length-1]), new Vector2(vertices[i], vertices[i+1]), center, squareRadius))
                    return true;
            } else {
                if (Intersector.intersectSegmentCircle(new Vector2(vertices[i-2], vertices[i-1]), new Vector2(vertices[i], vertices[i+1]), center, squareRadius))
                    return true;
            }
        }
        return false;
    }

    public Rocket getRocket(){return rocket;}
    public List<Ghost> getGhosts(){return ghosts;}

    public static Vector2 vectorFromAngle(double theta){
        return new Vector2((float)Math.cos(theta), (float)Math.sin(theta));
    }

    //courtesy of stackoverflow
    public static double randomInRange(double min, double max) {
        double range = max - min;
        double scaled = random.nextDouble() * range;
        double shifted = scaled + min;
        return shifted; // == (rand.nextDouble() * (max-min)) + min;
    }

    public int getScore(){return (int)(score);};
}
