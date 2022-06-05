package pgdp.luckypenguin;

import static pgdp.MiniJava.*;

public class LuckyPenguin {
    public static void main(String[] args) {
        int numberOfPenguins = readInt("Number of penguins:");
        while (numberOfPenguins <= 1)
            numberOfPenguins = readInt("Number of penguins should be >1:");
        int numFish = readInt("Starting fish per penguin:");
        while (numFish <= 0)
            numFish = readInt("Starting fish should be >0:");
        int[] numOfFishEachPenguinHas = new int[numberOfPenguins];
        for (int i = 0; i < numberOfPenguins; i++) {
            numOfFishEachPenguinHas[i] = numFish;
        }
        int[] playField = new int[13];
        int[] bankruptPenguinsArray = new int[numberOfPenguins];
        int playerNum = 0;
        int firstDice;
        int secondDice;
        while (true) {
            if (bankruptPenguinsArray[playerNum] == 1) {
                playerNum++;
                if (playerNum == numberOfPenguins) playerNum = 0;
                continue;
            }
            writeBoard(playField[3], playField[4], playField[5], playField[6], playField[7], playField[8], playField[9], playField[10], playField[11]);
            System.out.println("It's penguin " + playerNum + " turn:");
            int quittersCounter = 0;
            for (int i = 0; i < numberOfPenguins; i++) {
                if (bankruptPenguinsArray[i] == 1) quittersCounter++;
            }
            if (quittersCounter == numberOfPenguins - 1) {
                System.out.println("You are the last penguin to play! You win all the fish on the board!");
                for (int i = 1; i < 10; i++) {
                    numOfFishEachPenguinHas[playerNum] += playField[i];
                }
                break;
            }
            int sum;
            firstDice = dice();
            secondDice = dice();
            sum = firstDice + secondDice;
            System.out.println(firstDice + " + " + secondDice + " = " + sum + " was rolled.");
            if (sum == 12) {
                System.out.println("King Penguin! You win all the fish on the board!");
                for (int i = 1; i < playField.length - 1; i++) {
                    numOfFishEachPenguinHas[playerNum] += playField[i];
                    playField[i] = 0;
                }
            } else if (sum == 7) {
                System.out.println("Wedding! You give a fish and place it on F7.");
                playField[7]++;
                numOfFishEachPenguinHas[playerNum]--;
            } else if (sum == 2) {
                System.out.println("Lucky penguin! You win all fish on the board except F7!");
                for (int i = 1; i < playField.length - 1; i++) {
                    if (i == 5) continue;
                    numOfFishEachPenguinHas[playerNum] += playField[i];
                    playField[i] = 0;
                }
            } else if (playField[sum] == 0) {
                System.out.println("You put a fish on F" + sum + ".");
                numOfFishEachPenguinHas[playerNum]--;
                playField[sum]++;
            } else {
                System.out.println("They take the fish from F" + sum + ".");
                numOfFishEachPenguinHas[playerNum]++;
                playField[sum]--;
            }
            System.out.println("You now have " + numOfFishEachPenguinHas[playerNum] + " fish!");
            if (numOfFishEachPenguinHas[playerNum] == 0) {
                System.out.println("You have lost all fish, so you can no longer play!");
                bankruptPenguinsArray[playerNum] = 1;
            } else {
                int ask = readInt("Enter 1 to exit now:");
                if (ask == 1) {
                    bankruptPenguinsArray[playerNum] = 1;
                }
            }
            playerNum++;
            if (playerNum == numberOfPenguins) playerNum = 0;
        }
        int WinningNum = numOfFishEachPenguinHas[0];
        for (int i = 0; i < numberOfPenguins; i++) {
            if (numOfFishEachPenguinHas[i] > WinningNum) {
                WinningNum = numOfFishEachPenguinHas[i];
            }
        }
        System.out.println("The winning penguins with " + WinningNum + " fish:");
        for (int i = 0; i < numberOfPenguins; i++) {
            if (numOfFishEachPenguinHas[i] == WinningNum) {
                System.out.println("Penguin " + i);
            }
        }
    }
}
