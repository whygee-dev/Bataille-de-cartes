package com.whygee.bataille.desktop;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Carte {
    private String valeur;
    private String type;

    public static final Set<String> types = Set.of(
            "Clubs",
            "Diamonds",
            "Hearts",
            "Spades"
    );

    public static final Set<String> valeurs = Set.of(
      "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"
    );

    private final static Map<String, Integer> valeursMap = new HashMap<String, Integer>() {{
        put("2", 2);
        put("3", 3);
        put("4", 4);
        put("5", 5);
        put("6", 6);
        put("7", 7);
        put("8", 8);
        put("9", 9);
        put("10", 10);
        put("J", 11);
        put("Q", 12);
        put("K", 13);
        put("A", 14);
    }};


    public Carte(String type, String valeur) throws Exception{
        if (types.contains(type) && valeurs.contains(valeur)) {
            this.type = type;
            this.valeur = valeur;
        } else {
            throw new Exception("Valeur ou type invalide");
        }
    }

    public String getImagePath() {
        return "card" + this.type + valeur + ".png";
    }

    public static int getRealValue(Carte c) throws Exception{
        String stringValue = c.valeur;

        Integer valeur = valeursMap.get(stringValue);

        if (valeur == null) {
            throw new Exception("Valeur Invalide");
        }
        return valeursMap.get(stringValue);
    }

    public static String convertToStringValue(int val) throws Exception{
        if (val <= 10) {
            return String.valueOf(val);
        }

        switch (val) {
            case 11:
                return "J";
            case 12:
                return "Q";
            case 13:
                return "K";
            case 14:
                return "A";
        }

        throw new Exception("Valeur invalide");
    }

    public static int compare(Carte c1, Carte c2) throws Exception{
        return Integer.compare(getRealValue(c1), getRealValue(c2));
    }

    public String toString() {
        return valeur + " " + type;
    }
}
