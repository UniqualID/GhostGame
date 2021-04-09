package Helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

    public static BitmapFont font, shadow, whiteFont;
    public static TextureRegion ghost, blueghost, rocket, bg;
    public static Animation<TextureRegion> rocketAnimation;
    public static Animation<TextureRegion> sawAnimation;
    public static TextureAtlas atlas;
    public static Sound rocketSound, dead;
    public static Preferences prefs;

    public static void load() {
        prefs = Gdx.app.getPreferences("GhostGame");
        if (!prefs.contains("highscore")) {
            prefs.putInteger("highscore", 0);
        }
        atlas = new TextureAtlas(Gdx.files.internal("texture.atlas"));
        ghost = atlas.findRegion("ghost");
        blueghost = atlas.findRegion("blueGhost");
        if (ghost == null) {
            Gdx.app.log("AssetLoader", "shouldn't be happening");
        }
        bg = atlas.findRegion("Cave");
        rocket = atlas.findRegion("rocket", -1);
        TextureRegion[] rocketFrames = {atlas.findRegion("rocket", 0),
                atlas.findRegion("rocket", 1),
                atlas.findRegion("rocket", 2)};
        rocketAnimation = new Animation(0.05f, rocketFrames);
        rocketAnimation.setPlayMode(Animation.PlayMode.LOOP_RANDOM);

        TextureRegion[] sawFrames = {atlas.findRegion("saw", 0),
                atlas.findRegion("saw", 1),
                atlas.findRegion("saw", 2)};
        sawAnimation = new Animation(0.05f, sawFrames);
        sawAnimation.setPlayMode(Animation.PlayMode.LOOP);

        font = new BitmapFont(Gdx.files.internal("text.fnt"));
        font.getData().setScale(1f, 1f);

        whiteFont = new BitmapFont(Gdx.files.internal("whitetext.fnt"));
        whiteFont.getData().setScale(1f, 1f);

        shadow = new BitmapFont(Gdx.files.internal("shadow.fnt"));
        shadow.getData().setScale(1f, 1f);

        rocketSound = Gdx.audio.newSound(Gdx.files.internal("Explosion3.wav"));
        dead = Gdx.audio.newSound(Gdx.files.internal("dead.wav"));
        Gdx.app.log("AssetLoader", "Loaded");
    }

    public static void setHighScore(int val){
        prefs.putInteger("highScore",val);
        prefs.flush();
    }

    public static int getHighScore(){
        return prefs.getInteger("highScore");

    }
    public static void dispose(){
        font.dispose();
        shadow.dispose();
        whiteFont.dispose();
    }
}
