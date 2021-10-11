package com.whygee.bataille.desktop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.whygee.bataille.desktop.Game;


public class MenuScreen implements Screen {
	private final TextButton startButton;
	private final TextButton.TextButtonStyle textButtonStyle;
	private final Game game;
	private final Stage stage;
	private final Table table;
	private final SelectBox<String> selectBox;
	private Skin skin;

	public MenuScreen(Game game) {
		this.game = game;

		stage = new Stage();
		table = new Table();

		skin = new Skin(Gdx.files.internal("clean-crispy-ui.json"), new TextureAtlas(Gdx.files.internal("clean-crispy-ui.atlas")));

		selectBox = new SelectBox<>(skin);
		String[] values = new String[]{"Slow", "Normal", "Fast"};
		selectBox.setItems(values);

		BitmapFont font = new BitmapFont(Gdx.files.internal("montague.fnt"));
		textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.font = font;
		textButtonStyle.fontColor = Color.WHITE;
		textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("up-button.jpg")));
		textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture("down-button.jpg")));

		startButton = new TextButton("Start", textButtonStyle);
		startButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent e, float x, float y) {
				game.setScreen(new GameScreen(game,
						selectBox.getSelected().equals(values[0])
						? 0 : selectBox.getSelected().equals(values[1]) ? 1 : 2
				));
			}
		});

		table.add(startButton).center().width(250).height(100).row();
		table.add(selectBox).center().width(250).height(40).pad(25).row();
		table.setFillParent(true);
		table.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("background.png"))));
		stage.addActor(table);
	}

	@Override
	public void show () {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.graphics.setTitle("Bataille de cartes - Menu");
		stage.draw();
		stage.act(Gdx.graphics.getDeltaTime());

	}

	@Override
	public void dispose () {
		stage.dispose();
	}

	@Override
	public void hide(){
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
}
