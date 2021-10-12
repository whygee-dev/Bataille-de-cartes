package com.whygee.bataille.desktop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.whygee.bataille.desktop.Carte;
import com.whygee.bataille.desktop.Game;
import com.whygee.bataille.desktop.GameLogic;

import java.util.ArrayList;


public class GameScreen implements Screen {
    private final Game game;
    private Stage stage;
    private Texture cardJ1Texture, cardJ2Texture, vsTexture, winnerTexture, batailleTexture;
    private Rectangle cardJ1, cardJ2, vs;
    private OrthographicCamera camera;
    private static final int WORLD_WIDTH = 300;
    private static final int WORLD_HEIGHT = 300;
    private long startTime = 0;
    private int roundWinner = -1;
    private ArrayList<Carte> awaitingBataille = new ArrayList<Carte>();
    private boolean ended = false;
    private BitmapFont font;
    private final int speed;
    private final Table table;

    public GameScreen(Game game, int speed) {
        startTime = TimeUtils.nanoTime();
        this.speed = speed;
        camera = new OrthographicCamera(WORLD_WIDTH,WORLD_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        this.game = game;

        stage = new Stage(new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera));
        vsTexture = new Texture("vs.png");
        winnerTexture = new Texture("winner.png");
        batailleTexture = new Texture("bataille.png");
        cardJ1 = new Rectangle(); cardJ1.width = camera.viewportWidth / 10; cardJ1.height = camera.viewportHeight / 6;
        cardJ2 = new Rectangle(); cardJ2.width = camera.viewportWidth / 10; cardJ2.height = camera.viewportHeight / 6;
        vs = new Rectangle(); vs.width = 50; vs.height = 50;
        font = new BitmapFont();

        table = new Table();
        table.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("background.png"))));
        table.setFillParent(true);
        stage.addActor(table);
        stage.getViewport().apply();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        Gdx.graphics.setTitle("Bataille de cartes - En cours");

        stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
        stage.getCamera().update();
        stage.draw();
        stage.getBatch().begin();

        stage.getBatch().draw(
                vsTexture,
                camera.viewportWidth / 2 - vs.width / 2,
                camera.viewportHeight / 2 - vs.height / 2,
                vs.width,vs.height
        );
        font.draw(stage.getBatch(),
                Integer.toString(game.getJ1().getNombreDeCartes()),
                camera.viewportWidth / 2 - cardJ1.width / 2 - 67.5f,
                camera.viewportHeight / 2 - 50 );

        font.draw(stage.getBatch(),
                Integer.toString(game.getJ2().getNombreDeCartes()),
                camera.viewportWidth / 2 + cardJ2.width / 2 + 52.5f,
                camera.viewportHeight / 2 - 50);

        font.draw(stage.getBatch(),
                "Awaiting bataille: " + awaitingBataille.size(),
                camera.viewportWidth / 2 - 58,
                camera.viewportHeight / 2 - 65);
        
        if (TimeUtils.timeSinceNanos(startTime) > Math.max(2000000000 - speed * 1000000000, 5000000) && !ended) {
            playRound();
            startTime = TimeUtils.nanoTime();
        }

        if (roundWinner == 1) {
            stage.getBatch().draw(
                    winnerTexture,
                    camera.viewportWidth / 2 - cardJ1.width / 2 - 80,
                    camera.viewportHeight / 2 + cardJ1.height / 2 + 5 ,
                    40, 40);
        }

        if (roundWinner == 2) {
            stage.getBatch().draw(
                    winnerTexture,
                    camera.viewportWidth / 2 + cardJ2.width / 2 + 40,
                    camera.viewportHeight / 2 + cardJ2.height / 2 + 5 ,
                    40, 40);
        }

        if (roundWinner == 0) {
            stage.getBatch().draw(
                    batailleTexture,
                    camera.viewportWidth / 2 - vs.width ,
                    camera.viewportHeight / 2 - vs.height ,
                    2 * vs.width,2 * vs.height
            );
        }

        renderCards();

        stage.getBatch().end();
        stage.act(Gdx.graphics.getDeltaTime());

    }

    public void playRound(){
        if (game.getJ1().getNombreDeCartes() == 0 || game.getJ2().getNombreDeCartes() == 0) {
            ended = true;
            System.out.println("Game over");

            return;
        }
        try{
            Carte[] cartes = GameLogic.popCards(game.getJ1(), game.getJ2());
            cardJ1Texture = new Texture("cards/" + cartes[0].getImagePath());
            cardJ2Texture = new Texture("cards/" + cartes[1].getImagePath());
            int res = Carte.compare(cartes[0], cartes[1]);

            if (res == 1) {
                game.getJ1().gagne(cartes[0], cartes[1]);
                roundWinner = 1;

                if (awaitingBataille.size() != 0) {
                    System.out.println("Joueur 1 a gagné la bataille");
                    for (int i = 0; i < awaitingBataille.size(); i++) {
                        game.getJ1().ajouterCarte(awaitingBataille.remove(i));
                    }
                }

            } else if (res == -1) {
                game.getJ2().gagne(cartes[1], cartes[0]);

                if (awaitingBataille.size() != 0) {
                    System.out.println("Joueur 2 a gagné la bataille");
                    for (int i = 0; i < awaitingBataille.size(); i++) {
                        game.getJ2().ajouterCarte(awaitingBataille.remove(i));
                    }
                }
                roundWinner = 2;
            } else {
                System.out.println("Bataille!");
                roundWinner = 0;

                awaitingBataille.add(cartes[0]);
                awaitingBataille.add(cartes[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void renderCards() {
        if (cardJ1Texture == null || cardJ2Texture == null ) return;

        stage.getBatch().draw(
                cardJ1Texture,
                camera.viewportWidth / 2 - cardJ1.width / 2 - 75,
                camera.viewportHeight / 2 - cardJ1.height / 2,
                cardJ1.width, cardJ1.height
        );
        stage.getBatch().draw(
                cardJ2Texture,
                camera.viewportWidth / 2 - cardJ2.width / 2 + 75,
                camera.viewportHeight / 2 - cardJ2.height / 2,
                cardJ2.width, cardJ2.height
        );
    }

    @Override
    public void dispose() {
        batailleTexture.dispose();
        cardJ1Texture.dispose();
        cardJ2Texture.dispose();
        winnerTexture.dispose();
        stage.dispose();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        stage.getCamera().viewportWidth = WORLD_WIDTH;
        stage.getCamera().viewportHeight = WORLD_HEIGHT * height / width;
        stage.getCamera().position.set(stage.getCamera().viewportWidth / 2, stage.getCamera().viewportHeight / 2, 0);
        stage.getCamera().update();

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

}
