import java.util.Scanner;


public class Main {

    public static String currentPlayer;
    public static String player1;
    public static String player2;
    public static String dumbBotPlayer1;
    public static String dumbBotPlayer2;
    public static String smartBotPlayer1;
    public static String smartBotPlayer2;
    public static boolean bot = false;
    public static boolean human = false;

    public static char[][] board = new char[6][7];


    public static void main(String[] args) throws InterruptedException {

        board = initializeBoard();

        System.out.println("\n".repeat(20));
        System.out.println("=============================");
        System.out.println("=  Välkommen till Connect4  =");
        System.out.println("=============================\n");

        // Start the game
        displayBoard(board);
        welcome();
        players();
        gameLoop();

    }

    // tittar på om spelaren är en människa eller en dum/smart bot
    public static void players() {


        if (player1.contains("-+")) {
            bot = true;
        } else human = true;

        if (player1.contains("-")) {
            player1 = player1.substring(1);
            dumbBotPlayer1 = player1;
        } else if (player1.contains("+")) {
            player1 = player1.substring(1);
            smartBotPlayer1 = player1;
        } else {
            player1 = player1.substring(0);
        }
        if (player2.contains("-+")) {
            bot = true;
        } else human = true;

        if (player2.contains("-")) {
            player2 = player2.substring(1);
            dumbBotPlayer2 = player2;
        } else if (player2.contains("+")) {
            player2 = player2.substring(1);
            smartBotPlayer2 = player2;
        } else {
            player2 = player2.substring(0);
        }
    }

    // spelets huvudloop
    public static void gameLoop() throws InterruptedException {
        Scanner in = new Scanner(System.in);
        currentPlayer = player1;
        int round = 1;
        char player = player1.charAt(0);
        boolean winner = false;

        // spelares tur
        while (!winner && round <= 6 * 7) {
            boolean badMove;
            int move;

            do {
                System.out.println("\n".repeat(20));
                displayBoard(board);
                System.out.println("Turn : " + round + " out of 42\n" + currentPlayer + "'s turn, choose a column to play (1-7): \n");

                if (currentPlayer.equals(dumbBotPlayer1)) {
                    move = dumbBotMove();
                } else if (currentPlayer.equals(dumbBotPlayer2)) {
                    move = dumbBotMove();
                } else if (currentPlayer.equals(smartBotPlayer1)) {
                    move = smartBotMove();
                } else if (currentPlayer.equals(smartBotPlayer2)) {
                    move = smartBotMove();

                } else move = in.nextInt() - 1;


                badMove = checkMove(move, board);
            }
            while (!badMove);

            //släpp markören
            for (int row = board.length - 1; row >= 0; row--) {

                if (board[row][move] == ' ') {
                    board[row][move] = player;
                    break;
                }
            }
            winner = isWin(player, board);

            if (currentPlayer.equals(player1)) {
                currentPlayer = player2;
            } else {
                currentPlayer = player1;
            }
            // byta spelare
            if (player == player1.charAt(0)) {
                player = player2.charAt(0);
            } else {
                player = player1.charAt(0);
            }
            round++;
        }
        displayBoard(board);

        if (winner) {
            if (player == player1.charAt(0)) {
                System.out.println(player2 + " has won!\n");
            } else {
                System.out.println(player1 + " is the winner!\n");
            }
        } else {
            System.out.println("It's a tie!\n");
        }
        restart();
    }

    // En welcome till spelaren som berättar regler och tar emot spelarnamn
    public static void welcome() {
        Scanner in = new Scanner(System.in);
        System.out.println("RULES:  \nGet four in a row before your opponent. You can get 4 in a row, vertical - horizontal - or diagonally.\n\n");

        System.out.println("Time to enter your player names.\nWould you like to play vs the computer write '-' before the name for a dumb bot, or write '+' for a smart bot\n");

        System.out.println("Player one enter your name: ");
        player1 = in.nextLine();
        System.out.println("Player two enter your name: ");
        player2 = in.nextLine();
        System.out.println("\n".repeat(30));

    }

    // initialisera board
    public static char[][] initializeBoard() {
        // fyll board med tomrum
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                board[row][col] = ' ';
            }
        }
        return board;
    }

    // printa ut board
    public static void displayBoard(char[][] board) {

        System.out.println("   1   2   3   4   5   6   7");
        System.out.println("------------------------------");
        for (char[] chars : board) {
            System.out.print(" | ");
            for (int col = 0; col < board[0].length; col++) {
                System.out.print(chars[col]);
                System.out.print(" | ");
            }
            System.out.println();
            System.out.println("------------------------------");
        }
        System.out.println("   1   2   3   4   5   6   7");
    }

    // kolla om ditt drag är giltigt
    public static boolean checkMove(int col, char[][] board) {
        // Giltig colum?
        if (col < 0 || col > board.length) {
            return false;
        }
        // full colum?
        return board[0][col] == ' ';

    }

    // kolla om där är en vinnare
    public static boolean isWin(char player, char[][] board) {
        // kolla efter 4 i rad horizontal

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] == player &&
                        board[row][col + 1] == player &&
                        board[row][col + 2] == player &&
                        board[row][col + 3] == player) {
                    return true;
                }
            }
        }
        // kolla efter 4 i rad uppåt och neråt
        for (int row = 0; row < board.length -3; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] == player &&
                        board[row + 1][col] == player &&
                        board[row + 2][col] == player &&
                        board[row + 3][col] == player) {
                    return true;
                }
            }
        }
        // kolla efter 4 i rad diagonalt upp

        for (int row = 3; row < board.length; row++) {
            for (int col = 0; col < board[0].length - 3; col++) {
                if (board[row][col] == player &&
                        board[row - 1][col + 1] == player &&
                        board[row - 2][col + 2] == player &&
                        board[row - 3][col + 3] == player) {
                    return true;
                }
            }
        }
        // kolla efter 4 i rad diagonalt ned

        for (int row = 0; row < board.length - 3; row++) {
            for (int col = 0; col < board[0].length - 3; col++) {
                if (board[row][col] == player &&
                        board[row + 1][col + 1] == player &&
                        board[row + 2][col + 2] == player &&
                        board[row + 3][col + 3] == player) {
                    return true;
                }
            }
        }
        return false;
    }

    // Dum bot move
    public static int dumbBotMove() throws InterruptedException {
        thinking();
        int play = (int) (Math.random() * 7);
        while (board[0][play] != ' ')
            play = (int) (Math.random() * 7);
        if (play > 6) System.out.println("woops, my bad");
        return play;
    }
    // Smart bot move
    public static int smartBotMove() throws InterruptedException {
        thinking();
        int play = 3;
        for (int i = 0; i < board.length; i++) {
            if (board[0][i] != ' ') {
                play++;
            }
            if(play > 6) {
                play = 1;
            }
        }
        return play;
    }
        // Programmet ger en menu och användaren får välja hur den vill fortsätta när spelet är slut
    public static void restart () throws InterruptedException {
            Scanner in = new Scanner(System.in);
            System.out.println("The game has ended, how would you like to proceed:\n1. Restart game with new settings.\n2. Restart the game with the same settings.\n3. Exit game.");
            int choice = in.nextInt();
            if (choice == 1) {
                initializeBoard();
                welcome();
                players();
                gameLoop();
            }
            if (choice == 2) {
                initializeBoard();
                gameLoop();
            } else {
                System.out.println("Thank you for playing Connect4");
            }
        }
        // ger användaren upplevelse att datorn tänker
    public static void thinking () throws InterruptedException {
            System.out.println("Thinking...");
            Thread.sleep(1500);
        }

}



