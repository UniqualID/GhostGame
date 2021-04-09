package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.mygdx.game.GhostGame;

import Helpers.AssetLoader;
import Helpers.InputHandler;
import World.GameRenderer;
import World.GameWorld;


public class GameScreen implements Screen {

    private GameWorld world;
    private GameRenderer renderer;
    private float runtime = 0f;
    private GhostGame game;
    public GameScreen(GhostGame game) {
        this.game = game;
        world = new GameWorld(game);
        renderer = new GameRenderer(world);
        Gdx.input.setInputProcessor(new InputHandler(world.getRocket(), renderer.camera));
        Gdx.app.log("GameScreen", "Attached");
    }

    @Override
    public void render(float delta) {
        runtime += delta;
        world.update(delta);
        renderer.render(runtime);
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("GameScreen", "resizing");
    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen", "show called");
    }

    @Override
    public void hide() {
        Gdx.app.log("GameScreen", "hide called");
    }

    @Override
    public void pause() {
        Gdx.app.log("GameScreen", "pause called");
    }

    @Override
    public void resume() {
        Gdx.app.log("GameScreen", "resume called");
    }

    @Override
    public void dispose() {
        AssetLoader.dispose();
    }

}
