import game.DM;
import game.display.Printer;
import game.entities.Player;

import static game.utils.Utils.*;

public class Main {
    public static void main(String[] args) {
        DM _dm = new DM();
        allPlayers.clear();
        allMonsters.clear();
        _dm.createGame();
        for (int i=1;i<4;i++){
            _dm.createDungeon(i);
            _dm.play();
            if (_dm.checkPlayerDead()){
                System.out.println("All players are dead. Game over.");
                break;
            }
            for (Player player: allPlayers) {
                player.addLife(20); // Regenerate life after each dungeon
            }
        }
        Printer.print("Game finished. Thanks for playing!");
    }
}
