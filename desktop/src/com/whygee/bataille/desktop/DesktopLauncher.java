package com.whygee.bataille.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class DesktopLauncher {
	public static void main (String[] args) throws Exception{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Bataille de cartes";
		config.addIcon("icon-128x128.png", Files.FileType.Internal);
		config.addIcon("icon-32x32.png", Files.FileType.Internal);
		int nombreDeCarte = 52;

		Joueur j1 = new Joueur(nombreDeCarte / 2, "Joueur 1");
		Joueur j2 = new Joueur(nombreDeCarte / 2, "Joueur 2");

		Carte[] cartes = GameLogic.init(nombreDeCarte);

		GameLogic.attribuerCartes(j1, j2, cartes);

		new LwjglApplication(new Game(j1, j2), config);
	}
}
