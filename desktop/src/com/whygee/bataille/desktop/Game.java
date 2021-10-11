package com.whygee.bataille.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.whygee.bataille.desktop.screens.MenuScreen;

public class Game extends com.badlogic.gdx.Game {
    private Music mainMusic;
    private Joueur j1, j2;

    public Game(Joueur j1, Joueur j2) {
        super();

        this.j1 = j1;
        this.j2 = j2;
    }

    public Joueur getJ1() {
        return j1;
    }

    public Joueur getJ2() {
        return j2;
    }

    @Override
    public void create() {
        mainMusic = Gdx.audio.newMusic(Gdx.files.internal("main.mp3"));
        mainMusic.setLooping(true);
        mainMusic.play();


        setScreen(new MenuScreen(this));
    }


    @Override
    public void render() {
        super.render();
    }
}
