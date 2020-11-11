package pgdp.luckypenguin;

import static pgdp.MiniJava.*;

public class LuckyPenguin {
	public static void main(String[] args) {

		//input
		int numPenguins = readInt("Number of penguins:");
		while(numPenguins < 2)
			numPenguins = readInt("Number of penguins should be >1:");

		int numStartingFish = readInt("Starting fish per penguin:");
		while(numStartingFish < 1)
			numStartingFish = readInt("Starting fish should be >0:");


		//initialize
		int[] roomNumFish = new int[12]; //only cells 3-11 are used! otherwise you'd have to offset every index
		int[] penguinQuit = new int[numPenguins]; //1 if the penguin has either no more fish or quit, 0 otherwise. boolean would be better here!
		int[] penguinNumFish = new int[numPenguins]; //number of fish each penguin has
		{ //index p should only stay in this scope. way cooler: use a for loop!
			int p = 0;
			while(p < numPenguins) {
				penguinNumFish[p] = numStartingFish;
				p++;
			}
		}


		//game logic
		int numActivePenguins = numPenguins; //the number of penguins currently playing
		//keep playing the game until every penguin quits
		while(0 < numActivePenguins) {

			//each penguin takes turns playing
			int currentPenguin = 0;
			while(currentPenguin < numPenguins) {
				if(penguinQuit[currentPenguin] == 0) { //this penguin is still playing

					//output board
					writeBoard(roomNumFish[3], roomNumFish[4], roomNumFish[5], roomNumFish[6], roomNumFish[7],
							roomNumFish[8], roomNumFish[9], roomNumFish[10], roomNumFish[11]);

					//currentPenguin's turn
					write("It's penguin " + currentPenguin + " turn:");

					//check for game over
					if(numActivePenguins == 1) //im the only penguin left
					{
						//take all coins on board
						write("You are the last penguin to play! You win all the fish on the board!");
						int room = 3;
						while(room <= 11) {
							penguinNumFish[currentPenguin] += roomNumFish[room];
							roomNumFish[room] = 0;
							room++;
						}

						//quit game
						penguinQuit[currentPenguin] = 1;
						numActivePenguins--;
					}
					else //play the game normally
					{
						//penguin rolls dice
						int dice1 = dice(), dice2 = dice(), sum = dice1 + dice2;
						write(dice1 + " + " + dice2 + " = " + sum + " was rolled.");

						//follow rules according to number rolled
						if(sum == 2) { //lucky penguin: take all fish except from the wedding
							write("Lucky penguin! You win all fish on the board except F7!");
							int room = 3;
							while(room <= 11) {
								if(room != 7) {
									penguinNumFish[currentPenguin] += roomNumFish[room];
									roomNumFish[room] = 0;
								}
								room++;
							}
						}
						else if(sum == 7) { //wedding
							write("Wedding! You give a fish and place it on F7.");
							penguinNumFish[currentPenguin]--;
							roomNumFish[sum]++;
						}
						else if(sum == 12) { //king penguin: take ALL fish on the board
							write("King Penguin! You win all the fish on the board!");
							int room = 3;
							while(room <= 11) {
								penguinNumFish[currentPenguin] += roomNumFish[room];
								roomNumFish[room] = 0;
								room++;
							}
						}
						else { //put fish if field is empty, take if not
							if(roomNumFish[sum] == 1) {
								write("They take the fish from F" + sum + ".");
								penguinNumFish[currentPenguin]++;
								roomNumFish[sum]--;
							}
							else if(roomNumFish[sum] == 0) {
								write("You put a fish on F" + sum + ".");
								penguinNumFish[currentPenguin]--;
								roomNumFish[sum]++;
							}
						}

						//output new number of fish
						if(penguinNumFish[currentPenguin] == 1)
							write("You now have 1 fish!");
						else
							write("You now have " + penguinNumFish[currentPenguin] + " fishes!");

						//decide if penguin should quit
						if(penguinNumFish[currentPenguin] == 0) { //lost all fish
							write("You have lost all fish, so you can no longer play!");
							penguinQuit[currentPenguin] = 1;
							numActivePenguins--;
						}
						else if(readInt("Enter 1 to exit now:") == 1) {
							penguinQuit[currentPenguin] = 1;
							numActivePenguins--;
						}
					}
				}

				currentPenguin++; //next penguin's turn
			}
		}

		//find winners
		int maxFish = 0, p = 0;
		while(p < numPenguins) {
			if(maxFish < penguinNumFish[p])
				maxFish = penguinNumFish[p];
			p++;
		}

		p = 0;
		write("The winning penguins with " + maxFish + " fishes:");
		while(p < numPenguins) {
			if(maxFish == penguinNumFish[p])
				write("Penguin " + p);
			p++;
		}
	}
}