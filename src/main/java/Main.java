import enums.EnvVar;

public class Main {
    public static void main(String[] args) {
        //new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        if (args.length != 0 && args[0].equals("-v")) {
            System.out.println(EnvVar.ABOUT);
            System.out.println(EnvVar.VERSION);
            System.out.println(EnvVar.AUTHOR);
            return;
//            System.exit(0);
        }
        Game game = new Game();
//        Player player1 = new Player(ShipFiles.PLAYER1_SHIPS, "Oleg");
//        Player player2 = new Player(ShipFiles.PLAYER2_SHIPS, "Nastya");

        // TODO: 11.11.2022 сделать цикл для двух игроков

        game.startPlaying();
    }
}
