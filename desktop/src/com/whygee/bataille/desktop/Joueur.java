package com.whygee.bataille.desktop;

import java.util.ArrayList;

public class Joueur {
    private String nom;
    private ArrayList<Carte> cartes;
    private int points;

    public Joueur(int nombreDeCartes, String nom) {
        this.nom = nom;
        this.cartes = new ArrayList<Carte>(nombreDeCartes);
    }

    public Carte tirer() {
        return cartes.remove(0);
    }

    public void ajouterCarte(Carte c) {
        cartes.add(c);
    }

    public void gagne(Carte maCarte, Carte carteAdversaire) {
        ajouterCarte(carteAdversaire); ajouterCarte(maCarte);
        points++;
        System.out.println(nom + " a gagne la manche Score:" + getPoints());
    }


    public int getPoints() {
        return points;
    }

    public String toString(){
        return nom + " Score:" + points;
    }

    public String printCards() {
        return cartes.toString();
    }

    public int getNombreDeCartes() {
        return cartes.size();
    }
}
