package World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


import java.util.List;

import Entities.Ghost;
import Entities.Rocket;
import Entities.TrackGhost;
import Entities.SawGhost;
import Helpers.AssetLoader;

public class GameRenderer {
    private GameWorld world;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    private Rocket rocket;
    private List<Ghost> ghosts;

    private String score;
    public static float gameWidth, gameHeight;
    public OrthographicCamera camera;
    public GameRenderer(GameWorld world) {
        this.world = world;
        this.rocket = world.getRocket();
        this.ghosts = world.getGhosts();
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        gameWidth = 660;
        gameHeight = height / (width / gameWidth);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 660, gameHeight);

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

    public void render(float runtime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.disableBlending();
        batch.draw(AssetLoader.bg, 0f,0f, gameWidth, gameHeight);

        batch.enableBlending();

        if(world.gamestate == GameWorld.GameState.READY){
            score = "Click Anywhere\n to Start!";
            AssetLoader.shadow.draw(batch, "" + score, gameWidth / 2 - (12 * score.length()), gameHeight / 10 * 8);
            AssetLoader.whiteFont.draw(batch, "" + score, gameWidth / 2 - (12 * score.length() - 3), gameHeight / 10 * 8 - 3);

        } else if(world.gamestate == GameWorld.GameState.RUNNING) {
            score = world.getScore() + "";
            AssetLoader.shadow.draw(batch, "" + score, gameWidth / 2 - (10 * score.length()), gameHeight / 10 * 8);
            AssetLoader.whiteFont.draw(batch, "" + score, gameWidth / 2 - (10 * score.length() - 3), gameHeight / 10 * 8 - 3);
        }else{
            score = world.getScore() + "";
            AssetLoader.shadow.draw(batch, "" + score, gameWidth / 2 - (10 * score.length()), gameHeight / 10 * 8);
            AssetLoader.whiteFont.draw(batch, "" + score, gameWidth / 2 - (10 * score.length() - 3), gameHeight / 10 * 8 - 3);
            score = "Game Over! \n Click anywhere";
            AssetLoader.shadow.draw(batch, "" + score, gameWidth / 2 - (10 * score.length()), gameHeight / 10 * 7);
            AssetLoader.whiteFont.draw(batch, "" + score, gameWidth / 2 - (10 * score.length() - 3), gameHeight / 10 * 7 - 3);
            score = "High score: " + AssetLoader.getHighScore();
            AssetLoader.shadow.draw(batch, "" + score, gameWidth / 2 - (20 * score.length()), gameHeight / 10 * 5);
            AssetLoader.whiteFont.draw(batch, "" + score, gameWidth / 2 - (20 * score.length() - 3), gameHeight / 10 * 5 - 3);
        }
        //draw rocket animation
        if(Gdx.input.isTouched()){
            batch.draw(AssetLoader.rocketAnimation.getKeyFrame(runtime), rocket.getX(), rocket.getY(),
                    rocket.getWidth() / 2f, rocket.getHeight()/2f, rocket.getWidth(), rocket.getHeight(),1,1,rocket.getRotation());
        }else
            batch.draw(AssetLoader.rocket, rocket.getX(), rocket.getY(),
                    rocket.getWidth() / 2f, rocket.getHeight()/2f, rocket.getWidth(), rocket.getHeight(),1,1,rocket.getRotation());

        //enemy entities here
        for(Ghost ghost : ghosts)
            if(ghost instanceof TrackGhost)
                batch.draw(AssetLoader.blueghost, ghost.getX(), ghost.getY(), ghost.getWidth(), ghost.getHeight());
            else if(ghost instanceof SawGhost)
                batch.draw(AssetLoader.sawAnimation.getKeyFrame(runtime), ghost.getX(), ghost.getY(), ghost.getWidth(), ghost.getHeight());
            else batch.draw(AssetLoader.ghost, ghost.getX(), ghost.getY(), ghost.getWidth(), ghost.getHeight());
        batch.end();

        //draw bounding polygons for debug
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.setColor(Color.RED);
//        shapeRenderer.polygon(rocket.getBoundingRect().getTransformedVertices());
//        for(Ghost ghost : ghosts)
//            shapeRenderer.circle(ghost.getBoundingCircle().x, ghost.getBoundingCircle().y, ghost.getBoundingCircle().radius);
        shapeRenderer.end();
    }
}
