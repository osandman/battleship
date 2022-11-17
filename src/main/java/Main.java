public class Main {
    public static void main(String[] args) {
        //new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        Game game = new Game();
        Player me = new Player(ShipFiles.MY, "Oleg");
        Player comp = new Player(ShipFiles.COMP, "Nastya");
        Board.printBoards("Корабли расставлены", me.testBoard, comp.testBoard);
        // TODO: 11.11.2022 сделать цикл для двух игроков

        game.startPlaying(me, comp);


    }

}
