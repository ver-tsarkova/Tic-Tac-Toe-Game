/**
* XsAndOs.java
* @author Veronica Tsarkova
* @since 11/06/18
* A program of Xs and Os game (Tic Tac Toe) in a 3 by 3 grid that is played by one user playing both X and O.
* It prompts for user to enter an X token and O token alternately. Whenever a token is entered,
* the program redisplays the board and determines the status of the game. When the three of the same marks (either X or O)
* get placed in a horizontal, vertical, or diagonal row that mark wins the game.
*/

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Ellipse;

/**
* This is a GUI class that has a reference to the board.
* It also has an inner class that represents one Cell of the board.
*/
public class XsAndOs extends Application {

    private boolean gameOver = false;  // Flag
    private char whoseTurn = 'X'; // 'X' or 'O' but 'X' starts
    private Cell[][] board =  new Cell[3][3];  // The board for playing
    private Label statusLabel = new Label("X's turn to play");  // Let user know status of game

    @Override
    public void start(Stage primaryStage) {
        GridPane pane = new GridPane();
        // CODE HERE: use nested loop to create a Cell object for each location (row/column) in the board
        // use pane.add(object, column, row) to add that Cell object to the GridPane
        // remember: each Cell object is accessed by row and column
        for (int row = 0; row < 3; row++)
        {
          for (int column = 0; column < 3; column++)
          {
            board[row][column] = new Cell();
            pane.add(board[row][column], column, row);
          }
        }

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pane);
        borderPane.setBottom(statusLabel);

        Scene scene = new Scene(borderPane, 300, 300);
        primaryStage.setTitle("XsAndOs");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Determine if cells are all occupied
    public boolean isFull() {
        // CODE HERE to check if board is full
        for (int i = 0; i < 3; i++)
        for (int j = 0; j < 3; j++)
        if (board[i][j].getToken() == ' ')
           return false;
        return true;
    }

    // Determine if the player with the specified token wins
    public boolean hasWon(char tkn) {
        // CODE HERE to check board to see if there is a winner
        for (int i = 0; i < 3; i++)
        if (board[i][0].getToken() == tkn && board[i][1].getToken() == tkn
            && board[i][2].getToken() == tkn){
              return true;
            }

        for (int j = 0; j < 3; j++)
        if (board[0][j].getToken() == tkn && board[1][j].getToken() == tkn
            && board[2][j].getToken() == tkn){
              return true;
            }

        if (board[0][0].getToken() == tkn && board[1][1].getToken() == tkn
            && board[2][2].getToken() == tkn){
              return true;
            }

        if (board[0][2].getToken() == tkn && board[1][1].getToken() == tkn
            && board[2][0].getToken() == tkn){
              return true;
            }

        return false;
    }

    // HERE IS INNER CLASS REPRESENTING ONE CELL IN BOARD
    // The inner class has access to all of the outer classes data/methods
    public class Cell extends Pane {

        private char token = ' ';   // One of blank, X, or O

        public Cell() {
            setStyle("-fx-border-color: black");
            setPrefSize(100, 100);
            setOnMouseClicked(e -> handleMouseClick());
        }

        // Return token
        public char getToken() {
            // CODE HERE
            return token;
        }

        public void drawX() {
            double w = getWidth();
            double h = getHeight();
            // CODE HERE TO CREATE TWO LINES FOR 'X'
            Line line1 = new Line(0, 0, w, h);
            Line line2 = new Line(w, 0, 0, h);
            getChildren().addAll(line1, line2);

            setToken('X');
            if (hasWon('X'))
            {
              gameOver = true;
            }
        }

        public void drawO() {
            double w = getWidth();
            double h = getHeight();
            // CODE HERE TO CREATE AN 'O'
            Ellipse ellipse = new Ellipse(w / 2, h / 2, w / 2, h / 2);
            getChildren().add(ellipse);

            setToken('O');
            if (hasWon('O'))
            {
              gameOver = true;
            }
        }

        // Set a new token
        public void setToken(char c) {
            // CODE HERE TO DRAW AN X or O
            token = c;

            if (token == 'X'){
              Line line1 = new Line(10, 10, this.getWidth() - 10, this.getHeight() - 10);
              line1.endXProperty().bind(this.widthProperty().subtract(10));
              line1.endYProperty().bind(this.heightProperty().subtract(10));
              Line line2 = new Line(10, this.getHeight() - 10, this.getWidth() - 10, 10);
              line2.startYProperty().bind(this.heightProperty().subtract(10));
              line2.endXProperty().bind(this.widthProperty().subtract(10));
              // Adds lines to the pane (draws an X)
              this.getChildren().addAll(line1, line2);
            }

            else if (token == 'O'){
              Ellipse ellipse = new Ellipse(this.getWidth() / 2, this.getHeight() / 2,
                        this.getWidth() / 2 - 10, this.getHeight() / 2 - 10);
              ellipse.centerXProperty().bind(this.widthProperty().divide(2));
              ellipse.centerYProperty().bind(this.heightProperty().divide(2));
              ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
              ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
              ellipse.setStroke(Color.BLACK);
              ellipse.setFill(Color.WHITE);
              // Adds ellipse to the pane (draws an O)
              getChildren().add(ellipse);
            }
        }

        private void handleMouseClick() {
            String s = "";
            if (!gameOver) {
                // CODE HERE FOR LOGIC OF THE GAME
                // Set token in the cell
                setToken(whoseTurn);

                // Check game status
                if(hasWon(whoseTurn)){
                  statusLabel.setText(whoseTurn + " won! The game is over");
                  whoseTurn = ' ';
                }
                else if (isFull()){
                  statusLabel.setText("Draw!");
                  whoseTurn = ' ';
                }
                else {
                  // Change the turn
                  whoseTurn = (whoseTurn == 'X') ? 'O' : 'X';
                  // Display whose turn
                  statusLabel.setText(whoseTurn + "'s turn to play");
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
