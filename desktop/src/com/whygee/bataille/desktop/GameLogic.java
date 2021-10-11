package com.whygee.bataille.desktop;

import java.util.Arrays;
import java.util.Collections;

public class GameLogic {

    private GameLogic(){}

    public static Carte[] init(int nombreDeCartes) throws Exception{
        Carte[] cartes = new Carte[nombreDeCartes];

        for (int i = 0; i < nombreDeCartes / 13; i++) {
            for (int j = 0; j < 13; j++){
                String type = "";

                switch (i) {
                    case 0:
                        type = "Clubs";
                        break;
                    case 1:
                        type = "Diamonds";
                        break;
                    case 2:
                        type = "Hearts";
                        break;
                    case 3:
                        type = "Spades";
                        break;
                }

                cartes[i + 4 * j] =  new Carte(type, Carte.convertToStringValue(j + 2));
            }
        }

        Collections.shuffle(Arrays.asList(cartes));

        return cartes;
    }

    public static void attribuerCartes(Joueur j1, Joueur j2, Carte[] cartes) {
        for (int i = 0; i < cartes.length / 2; i++){
            j1.ajouterCarte(cartes[i]);
            j2.ajouterCarte(cartes[i+1]);
        }
    }

    public static Carte[] popCards(Joueur j1, Joueur j2){
        System.out.println(j1.getNombreDeCartes());
        System.out.println(j2.getNombreDeCartes());

        Carte c1 = j1.tirer();
        Carte c2 = j2.tirer();

        Carte[] cartes = {c1, c2};


        return cartes;
    }

}
