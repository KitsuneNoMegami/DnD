package game.display;

import game.Dungeon;
import game.Map;
import game.entities.Entity;

import static game.display.Printer.*;
import static game.entities.EntityType.MONSTER;
import static game.entities.EntityType.PLAYER;

public class DisplayDungeon {
    private String[][] _map;
    private Dungeon _dungeon;
    private Map _positions;
    public DisplayDungeon(Dungeon dungeon) {
        _dungeon = dungeon;
        _positions = _dungeon.getMap();
        int[] size= _dungeon.getSize();
        _map = new String[size[0] + 3][size[1] + 1];
        initialiseMap();
    }

    private void initialiseMap() {
        for (int j = 0; j < _map[0].length; j++) {
            if (j >= 1) {
                _map[_map.length - 1][j] = (char) ('A' + j - 1) + "  ";
            } else {
                _map[_map.length - 1][j] = "      ";
            }
        }

        for (int j = 0; j < _map[0].length; j++) {
            _map[0][j] = "   *";
            _map[_map.length - 2][j] = "   *";
            for (int k = 1; k < _map[0].length; k++) {
                _map[0][k] = "---";
                _map[_map.length - 2][k] = "---";
            }
            _map[0][_map[0].length - 1] = "-----*";
            _map[_map.length - 2][_map[0].length - 1] = "-----*";
        }

        for (int i = 1; i < _map.length - 2; i++) {
            for (int j = 0; j < _map[i].length; j++) {
                if (j == 0) {
                    _map[i][j] = (i < 10 ? " " : "") + Integer.toString(i) + " | ";
                } else {
                    _map[i][j] = (j == _map[i].length - 1 ? " .  |" : " . ");
                }
            }
        }
    }

    public void displayMap() {
        initialiseMap();
        loadBoard();
        for (int i = 0; i < _map.length; i++) {
            for (int j = 0; j < _map[i].length; j++) {
                System.out.print(_map[i][j]);
            }
            Printer.print("");
        }
        printLegend();
    }

    private void printLegend() {
        String legend=PURPLE+"Légende de la carte:"+RESET;
        legend += " . : Empty Cell ";
        legend += GREEN + "|¨|: Obstacle  ";
        legend += YELLOW + "[⊟]: Equipment  ";
        legend += RED + "[X]: Monster   ";
        legend += BLUE + "[P]: Player\n" + RESET;
        Printer.print(legend);

    }

    //* Affichage du donjon
    private void loadBoard() {
        setObstacles();
        setEquipments();
        setEntities();
    }

    //? Ajout du caractère correspondant dans la map
    private void setObstacles() {
        for (int[] coord : _positions.getObstacles()) {
            _map[coord[0]][coord[1]] = GREEN +"|¨|"+RESET;
        }
    }
    private void setEntities() {
        // Pour toutes les entité dans le dictionnaire
        for (java.util.Map.Entry<Entity, int[]> entry : _positions.getEntities().entrySet()) {
            Entity entity = entry.getKey();
            int[] coord = entry.getValue();
            // Si les coordonnées sont bonnes
            if (coord[0] != -1 && coord[1] != -1) {
                if (entity.getType() == PLAYER) {
                    _map[coord[0]][coord[1]] = BLUE + entity.getPseudo() + RESET;
                } else if (entity.getType() == MONSTER) {
                    _map[coord[0]][coord[1]] = RED + entity.getPseudo() + RESET;
                }
            }
        }
    }
    private void setEquipments() {
        _positions.getEquipmentsPosition().forEach((item, coord) ->
                _map[coord[0]][coord[1]] = YELLOW + "[⊟]" + RESET
        );
    }
}
