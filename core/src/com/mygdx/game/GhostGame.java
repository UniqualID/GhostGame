package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import Helpers.AssetLoader;
import Screens.StartScreen;

public class GhostGame extends Game {

	@Override
	public void create () {
		Gdx.app.log("GhostGame", "Created");
		AssetLoader.load();
		setScreen(new StartScreen(this));
	}

}
