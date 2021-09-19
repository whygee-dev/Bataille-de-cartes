import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Carte {
    private String couleur;
    private String valeur;

    public static final Set<String> couleurs = Set.of(
            "rouge",
            "noir"
    );

    public static final Set<String> valeurs = Set.of(
      "2", "3", "4", "5", "6", "7", "8", "9", "10", "V", "D", "R", "A"
    );

    public static Map<String, Integer> valeursMap = new HashMap<String, Integer>() {{
        put("2", 2);
        put("3", 3);
        put("4", 4);
        put("5", 5);
        put("6", 6);
        put("7", 7);
        put("8", 8);
        put("9", 9);
        put("10", 10);
        put("V", 11);
        put("D", 12);
        put("R", 13);
        put("A", 14);
    }};

    public Carte(String couleur, String valeur){
        if (couleurs.contains(couleur) && valeurs.contains(valeur)) {
            this.couleur = couleur;
            this.valeur = valeur;
        } else {
            throw new Error("Valeur ou couleur invalide");
        }
    }

    public static int getRealValue(Carte c) {
        String stringValue = c.valeur;

        Integer valeur = valeursMap.get(stringValue);

        if (valeur == null) {
            throw new Error("Valeur Invalide");
        }
        return valeursMap.get(stringValue);
    }

    public static String convertToStringValue(int val) {
        if (val <= 10) {
            return String.valueOf(val);
        }

        switch (val) {
            case 11:
                return "V";
            case 12:
                return "D";
            case 13:
                return "R";
            case 14:
                return "A";
        }

        throw new Error("Valeur invalide");
    }

    public static int compare(Carte c1, Carte c2) {
        return Integer.compare(getRealValue(c1), getRealValue(c2));
    }

    public String toString() {
        return valeur + " " + couleur;
    }
}
