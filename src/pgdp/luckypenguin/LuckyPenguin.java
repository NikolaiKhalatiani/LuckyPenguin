package pgdp.luckypenguin;

import static pgdp.MiniJava.*;

public class LuckyPenguin {
    public static void main(String[] args) {
        int num = readInt("Number of penguins:");
        while (num <= 1)
            num = readInt("Number of penguins should be >1:");
        int numfish = readInt("Starting fish per penguin:");
        while (numfish <= 0)
            numfish = readInt("Starting fish should be >0:");
        int[] pingu = new int[num];
        for (int i = 0; i < num; i++) {
            pingu[i] = numfish;
        }
        boolean test = false;
        int[] field = new int[13];
        int[] check = new int[num];
        int k = 0;
        int firstdice;
        int seconddice;
        while (true) {
            if (check[k] == 1) {
                k++;
                if (k == num ) k=0;
                continue;
            }
            int sum;
            writeBoard(field[3], field[4], field[5], field[6], field[7], field[8], field[9], field[10], field[11]);
            write("It's penguin " + k + " turn:");
            int stop = 0;
            for (int i = 0; i < num; i++) {
                if (check[i] == 1) stop++;
            }
            if (stop == num - 1) {
                write("You are the last penguin to play! You win all the fish on the board!");
                for (int i = 1; i < 10; i++) {
                    pingu[k] += field[i];
                }
                test = true;
                break;
            }
            firstdice = dice();
            seconddice = dice();
            sum = firstdice + seconddice;
            write(firstdice + " + " + seconddice + " = " + sum + " was rolled.");
            if (sum == 12) {
                write("King Penguin! You win all the fish on the board!");
                for (int i = 1; i < field.length -1; i++) {
                    pingu[k] += field[i];
                    field[i] = 0;
                }
            } else if (sum == 7) {
                write("Wedding! You give a fish and place it on F7.");
                field[5]++;
                pingu[k]--;
            } else if (sum == 2) {
                write("Lucky penguin! You win all fish on the board except F7!");
                for (int i = 1; i < field.length - 1; i++) {
                    if (i == 5) continue;
                    pingu[k] += field[i];
                    field[i] = 0;
                }
            } else if (field[sum - 2] == 0) {
                write("You put a fish on F" + sum + ".");
                pingu[k]--;
                field[sum - 2]++;
            } else {
                write("They take the fish from F" + sum + ".");
                pingu[k]++;
                field[sum - 2]--;
            }
            if (pingu[k] == 1) write("You now have " + pingu[k] + " fish!");
            else write("You now have " + pingu[k] + " fishes!");
            if (pingu[k] == 0) {
                write("You have lost all fish, so you can no longer play!");
                check[k] = 1;
            } else {
                int ask = readInt("Enter 1 to exit now:");
                if (ask == 1) {
                    check[k] = 1;
                }
            }
            k++;
            if (k == num) k = 0;
        }
        int WinningNum = pingu[0];
        for (int i = 0; i < num; i++) {
            if (pingu[i] > WinningNum) {
                WinningNum = pingu[i];
            }
        }
        if (WinningNum == 1) write("The winning penguins with " + WinningNum + " fish:");
        else {
            write("The winning penguins with " + WinningNum + " fishes:");
        }
        for (int i = 0; i < num; i++) {
            if (pingu[i] == WinningNum) {
                write("Penguin " + i);
            }
        }
    }
}
