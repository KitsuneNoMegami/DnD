package game.utils;

import game.entities.Player;
import game.entities.Monster;
import game.items.Equipment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Utils {
    public static final List<Equipment> allEquipments = EquipmentRepository.getAllEquipment();
    public static final List<Player> allPlayers = new ArrayList<>();
    public static final List<Monster> allMonsters = new ArrayList<>();
    public static Scanner scanner = new Scanner(System.in);
    public static int rollDice(int number, int side) {
        Random random = new Random();
        int result = 0;
        for (int i = 0; i < number; i++) {
            result += random.nextInt(1,side+1) ; // nextInt(dice) génère un nombre entre 0 (inclus) et dice (exclus)
        }
        return result;
    }
}