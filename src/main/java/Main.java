import enums.EnvVar;
import enums.MyFiles;
import enums.ReturnStr;
import enums.InputVar;

public class Main {
    public static void main(String[] args) {
        //new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        if (args.length != 0 && args[0].equals("-v")) {
            printAbout();
            return;
//            System.exit(0);
        }
        printAbout();
        while (true) {
            String gameType = Features.getUserInput("Введите тип игры: 1 - с другим игроком, 2 - с компьютером", InputVar.GAME_TYPE);
            if (gameType.equals(ReturnStr.HUMAN_GAME.toString())) {

                break;
            } else {
                System.out.println("Игра с компьютером в разработке ...");
                System.out.println("Сыграйте пока друг с другом =8-)");
                break;
            }
        }
        // TODO: 22.11.2022 переместить в цикл когда будет готов класс игрока компьютера
        Game game = new Game(new HumanPlayer(MyFiles.PLAYER1_SHIPS, getPlayersName(1)),
                new HumanPlayer(MyFiles.PLAYER2_SHIPS, getPlayersName(2)));
        game.startPlaying();
//        Player player1 = new Player(MyFiles.PLAYER1_SHIPS, "Oleg");
//        Player player2 = new Player(MyFiles.PLAYER2_SHIPS, "Nastya");

    }

    private static void printAbout(){
        System.out.println(EnvVar.ABOUT);
        System.out.println(EnvVar.VERSION);
        System.out.println(EnvVar.AUTHOR);
        System.out.println();
    }
    private static String getPlayersName(int playerNumber) {
        return Features.getUserInput("Введите имя игрока №" + playerNumber + " (от 1 до 20 символов)", InputVar.NAME_INPUT);
    }
}
