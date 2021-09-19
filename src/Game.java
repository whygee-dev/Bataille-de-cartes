import java.util.Arrays;
import java.util.Collections;

public class Game {

    public static Carte[] init(int nombreDeCartes) {
        Carte[] cartes = new Carte[nombreDeCartes];

        for (int i = 0; i < nombreDeCartes / 13; i++) {
            for (int j = 0; j < 13; j++){
                String couleur = i < 2 ? "noir" : "rouge";
                cartes[i + 4 * j] =  new Carte(couleur, Carte.convertToStringValue(j + 2));
            }
        }

        System.out.println(Arrays.toString(cartes));
        Collections.shuffle(Arrays.asList(cartes));
        System.out.println(Arrays.toString(cartes));
        System.out.println(cartes.length);
        return cartes;
    }

    public static void attribuerCartes(Joueur j1, Joueur j2, Carte[] cartes) {
        for (int i = 0; i < cartes.length / 2; i++){
            j1.ajouterCarte(cartes[i]);
            j2.ajouterCarte(cartes[i+1]);
        }
    }

    public static void loop(Joueur j1, Joueur j2) {
        while(true) {
            System.out.println(j1.getNombreDeCartes());
            System.out.println(j2.getNombreDeCartes());

            if (j1.getNombreDeCartes() == 0 || j2.getNombreDeCartes() == 0) {
                break;
            }
            Carte c1 = j1.tirer();
            Carte c2 = j2.tirer();

            int res = Carte.compare(c1, c2);

            if (res == 1) {
                j1.gagne(c1, c2);
            } else if (res == -1) {
                j2.gagne(c2, c1);
            } else {
                System.out.println("Egalité!");
                j1.egalite(c1); j2.egalite(c2);
            }
        }

        System.out.println("\nScore final:\n" + "Joueur1 " + j1.getPoints() + "   Joueur2 " + j2.getPoints() + "\n");
        if (j1.getPoints() > j2.getPoints()) {
            System.out.println(j1 + " a gagné!\n");
        } else if (j1.getPoints() < j2.getPoints()){
            System.out.println(j2 + " a gagné\n");
        } else {
            System.out.println("Egalité!");
        }
        System.out.println(j1.getNombreDeCartes());
        System.out.println(j2.getNombreDeCartes());
    }
}
