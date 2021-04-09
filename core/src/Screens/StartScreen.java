package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GhostGame;

import Helpers.AssetLoader;

public class StartScreen implements Screen {
    private GhostGame game;
    private SpriteBatch batch;
    private OrthographicCamera camera;

    public StartScreen(GhostGame myGame){
        game = myGame;
        batch = new SpriteBatch();
    }


    @Override
    public void show() {
        Gdx.app.log("Startscreen", "Attached");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        AssetLoader.shadow.draw(batch, "Dodge the Ghost!", Gdx.graphics.getWidth()/2 - (new GlyphLayout(AssetLoader.shadow, "Dodge the Ghost!").width/2 )
                ,Gdx.graphics.getHeight()/2 - (new GlyphLayout(AssetLoader.shadow, "Dodge the Ghost!").height/2));
        batch.end();

        if(Gdx.input.justTouched()){
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
