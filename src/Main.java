import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        //new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        Game game = new Game();
        Player me = new Player(shipFiles.MY, "Oleg");
        Player comp = new Player(shipFiles.COMP, "Nastya");
        Board.printBoards("Корабли расставлены", me.testBoard, comp.testBoard);
        // TODO: 11.11.2022 сделать цикл для двух игроков

        game.startPlaying(me);
        game.finishGame(me);
    }

}
