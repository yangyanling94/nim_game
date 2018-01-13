package src;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * @author yanling
 * @date 1/12/2018
 * @description 
 * Randomly create a number of heaps where the number must either be 3, 5 or 7
 * For each heap created, randomly assign the initial number of objects within the heap. The number can be either 9, 11 or 13
 * Randomly assign the first player, either the human or the computer.
 * The first player removes any positive and non-zero number of objects (including all objects) for any one heap
 * The second player then does the same
 * Play continues, alternating turns, until no objects remain in any heap.
 * The player taking the last object(s) is the winner
 */
public class NimGame {
	
	private static int[] heap; 
	private static int player = 0; // 0 -> computer, 1 -> human
	private static Random generate = new Random();
	private static Scanner keyboard = new Scanner(System.in);
	
	static {
		heap = createHeaps();
	}
	
	public static void main(String[] args) {
		startGame();
	}

	private static void startGame() {
		setUpGame();
		playGame();
	}

	private static void setUpGame() {
		setUpHeaps();
		selectFirstPlayer();
	}
	
	private static int[] createHeaps() {
		int numOfHeaps = 2 * generate.nextInt(3) + 3;
		int[] res = new int[numOfHeaps];
		return res;
	}

	private static void setUpHeaps() {
		for (int i = 0; i < heap.length; i++) {
			int  numOfObjects = 2 * generate.nextInt(3) + 9;
			heap[i] = numOfObjects;
		}
		String heapsEach = Arrays.toString(heap).replaceAll("[^0-9]", " ");
		System.out.println("Created " + heap.length + " heaps of sizes " + heapsEach);
	}

	private static void selectFirstPlayer() {
		player = generate.nextInt(2);
		String name = getPlayerName(player);
		System.out.println("Player " + name + " goes first");
	}

	private static String getPlayerName(int player2) {
		String name = "computer";
		if (player == 1) {
			name = "human";
		}
		return name;
	}

	private static void playGame() {
		while (!gameIsOver()) {
			takeTurn();
			selectNextPlayer();
		}
		printWinner();
	}

	private static boolean gameIsOver() {
		for (int i = 0; i < heap.length; i++) {
			if (heap[i] != 0) return false;
		}
		return true;
	}
	
	private static void takeTurn() {
		if (player == 0) {
			takeComputerTurn();
		} else {
			takeHumanTurn();
		}
	}
	
	private static void takeComputerTurn() {
		int[] move = generateComputerMove();
		makeMove(move);
	}

	private static int[] generateComputerMove() {
		int[] res = new int[2];
		int heapNum = generate.nextInt(heap.length);
		while (heap[heapNum] == 0) {
			heapNum = generate.nextInt(heap.length);
		}
		int objectsNumRemoved = generate.nextInt(heap[heapNum] + 1);
		res[1] = heapNum;
		res[0] = objectsNumRemoved;
		return res;
		
	}
	
	private static void makeMove(int[] move) {
		int heapNum = move[1];
		int objectsNumRemoved = move[0];
		heap[heapNum] -= objectsNumRemoved;
		heapNum += 1;
		String name = getPlayerName(player);
		System.out.println("Player " + name + " took " +  objectsNumRemoved + " from heap " + heapNum);
		for (int remainedEach : heap) {
			System.out.print(remainedEach + " ");
		}
		System.out.println();
	}

	private static void takeHumanTurn() {
		int[] move = new int[2];
		do {
			System.out.println("Player human enter the number of objects (Y) to take from what heap (X)- in order: Y X");
			move = generateHumanMove();
		} while (validateHumanMove(move));
		makeMove(move);
	}

	private static int[] generateHumanMove() {
		// Scanner keyboard = new Scanner(System.in);
		int[] move = new int[2];
		
		String input = keyboard.nextLine();
		String[] inputs = input.trim().split(" ");
		for (int i = 0; i < 2; i++) {
			move[i] = Integer.parseInt(inputs[i]);
		}
		move[1] -= 1;
		return move;
	}
	
	private static boolean validateHumanMove(int[] move) {
		int heapNum = move[1];
		int objectsNumRemoved = move[0];
		if (heap[heapNum] == 0 || heap[heapNum] < objectsNumRemoved) {
			System.out.println("Player human that is an invalid move, try again");
			return true;
		}
		return false;
	}

	private static void selectNextPlayer() {
		if (player == 0) {
			player = 1;
		} else {
			player = 0;
		}
	}

	private static void printWinner() {
		if (player == 0) {
			System.out.println("Player human has won");
		} else {
			System.out.println("Player computer has won");
		}
		keyboard.close();
	}

}
