public class Bataille {

    public static void main(String[] args) {

        int nombreDeCarte;

        try{
            nombreDeCarte = Integer.parseInt(args[0]);
        } catch (Error err) {
            System.err.println("Nombre de cartes non fourni");

            return;
        }

        Joueur j1 = new Joueur(nombreDeCarte / 2, "Joueur 1");
        Joueur j2 = new Joueur(nombreDeCarte / 2, "Joueur 2");

        Carte[] cartes = Game.init(nombreDeCarte);

        Game.attribuerCartes(j1, j2, cartes);

        System.out.println(j1.getNombreDeCartes());
        System.out.println(j2.getNombreDeCartes());
        Game.loop(j1, j2);
    }
}
