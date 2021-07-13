// in the name of Allah

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.io.File;

import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.scene.effect.DropShadow;

import java.io.*;
import java.util.Scanner;
import java.util.*;

import javafx.scene.effect.Reflection;
import javafx.scene.shape.StrokeType;

/**
 * The class <b>Setup</b> launches the program and set scenes.
 *
 * @author Farkhondeh Arzi
 * @author Mahdieh ShahHosseinian
 */

public class Main extends Application {

    private Scene scene;

    private Player player = new Player();
    private Menu menu = new Menu();

    {
        player.getContinueLabel().setOnMouseClicked(mouseEvent -> setScene(menu.getParent()));

        menu.getNewGameButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                setScene(menu.getTetris().getParent());
                menu.getTetris().getMenuButton().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        player.setScore(menu.getTetris().getScore());
                        menu.getTetris().getScoresManager().addPlayer(player);

                        menu.getTetris().save();
                        menu.getTetris().getGameSound().stop();
                        setScene(menu.getParent());
                    }
                });
            }
        });

        menu.getScoreTableButton().setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {

                setScene(menu.getTetris().getScoresManager().getParent());
                menu.getTetris().getScoresManager().getBackLabel().setOnMouseClicked(mouseEvent -> setScene(menu.getParent()));
            }
        });

        menu.getSettingsButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                setScene(menu.getTetris().getSettingsManager().getParent());
                menu.getTetris().getSettingsManager().getBack().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        menu.getTetris().save();
                        setScene(menu.getParent());
                    }
                });
            }
        });
    }

    @Override
    public void start(Stage stage) throws Exception {

        {
            scene = new Scene(player.getParent(),
                    GameBoard.ROW * GameBoard.CELL_SIZE + 20, GameBoard.ROW * GameBoard.CELL_SIZE + 20);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.getIcons().add(new Image("icon.png"));
            stage.setTitle("T E T R I S");
            stage.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Getters and Setters

    /**
     * Setter for scene
     *
     * @param scene Placed as root of the main scene.
     */
    public void setScene(Parent scene) {
        this.scene.setRoot(scene);
    }
}

class Menu {

    private Parent parent;

    private TETRIS tetris;

    private BorderPane borderPane = new BorderPane();
    private Label label = new Label("T  E  T  R  I  S");
    private ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("menu.jpg")));
    private VBox vBox = new VBox(8);
    private Button newGameButton = new Button("_New Game");
    private Button scoreTableButton = new Button("Score _Table");
    private Button settingsButton = new Button("_Settings");
    private Button exitButton = new Button("_Exit");

    {
        {
            borderPane.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));
            borderPane.setPadding(new Insets(0, 0, 10, 0));
        }

        {
            label.setBackground(new Background(new BackgroundFill(Color.rgb(255, 102, 178), CornerRadii.EMPTY, Insets.EMPTY)));
            label.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.ITALIC, 20));
            label.prefWidthProperty().bind(borderPane.widthProperty());
            label.setAlignment(Pos.CENTER);
            label.setTextFill(Color.BLACK);
            label.setPrefHeight(25);
        }
        borderPane.setTop(label);

        {
            imageView.setFitWidth(300);
            imageView.setFitHeight(200);
        }
        borderPane.setCenter(imageView);

        {
            newGameButton.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
            newGameButton.setStyle("-fx-background-color: FF66B2");
            newGameButton.setTextFill(Color.BLACK);
            newGameButton.setPrefWidth(130);
            newGameButton.setPrefHeight(30);
            newGameButton.setMnemonicParsing(true);
        }
        vBox.getChildren().add(newGameButton);

        {
            scoreTableButton.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
            scoreTableButton.setStyle("-fx-background-color: FF66B2");
            scoreTableButton.setTextFill(Color.BLACK);
            scoreTableButton.setPrefWidth(130);
            scoreTableButton.setPrefHeight(30);
            scoreTableButton.setMnemonicParsing(true);
        }
        vBox.getChildren().add(scoreTableButton);

        {
            settingsButton.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
            settingsButton.setStyle("-fx-background-color: FF66B2");
            settingsButton.setTextFill(Color.BLACK);
            settingsButton.setPrefWidth(130);
            settingsButton.setPrefHeight(30);
            settingsButton.setMnemonicParsing(true);
        }
        vBox.getChildren().add(settingsButton);

        {
            exitButton.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
            exitButton.setStyle("-fx-background-color: FF66B2");
            exitButton.setTextFill(Color.BLACK);
            exitButton.setPrefWidth(130);
            exitButton.setPrefHeight(30);
            exitButton.setMnemonicParsing(true);
            exitButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.exit(0);
                }
            });
        }
        vBox.getChildren().add(exitButton);

        vBox.setAlignment(Pos.CENTER);
        borderPane.setBottom(vBox);

        parent = borderPane;
    }

    // Getters and Setters

    /**
     * Getter for parent
     *
     * @return parent as a root of the main scene
     */
    public Parent getParent() {
        tetris = new TETRIS();
        return parent;
    }

    /**
     * Getter for TETRIS
     *
     * @return the game manager <b>TETRIS</b> class
     */
    public TETRIS getTetris() {
        return tetris;
    }

    /**
     * Getter for new game button
     *
     * @return the button for OnAction handling
     */
    public Button getNewGameButton() {
        return newGameButton;
    }

    /**
     * Getter for score table button
     *
     * @return the button for OnAction handling
     */
    public Button getScoreTableButton() {
        return scoreTableButton;
    }

    /**
     * Getter for settings button
     *
     * @return the button for OnAction handling
     */
    public Button getSettingsButton() {
        return settingsButton;
    }
}

/**
 * This class is responsible for handling arrow keys
 * and manages the score & the heart & the level of the player and determines game over
 * and sets new game board
 *
 * @author Farkhondeh Arzi
 * @author Mahdieh ShahHosseinian
 */
class TETRIS {

    private int score = 0;
    private int heart = 4;
    private int level;

    private GameBoard gameBoard;
    private ScoresManager scoresManager = new ScoresManager();
    private SettingsManager settingsManager = new SettingsManager(this, scoresManager);

    private Parent parent;

    private StackPane stackPane = new StackPane();

    private BorderPane startCenter = new BorderPane();
    private BorderPane pauseCenter = new BorderPane();
    private BorderPane brokenHeartCenter = new BorderPane();
    private BorderPane gameOverCenter = new BorderPane();

    private Rectangle startShade = new Rectangle(GameBoard.COLUMN * GameBoard.CELL_SIZE, GameBoard.ROW * GameBoard.CELL_SIZE);
    private Rectangle pauseShade = new Rectangle(GameBoard.COLUMN * GameBoard.CELL_SIZE, GameBoard.ROW * GameBoard.CELL_SIZE);
    private Rectangle brokenHeartShade = new Rectangle(GameBoard.COLUMN * GameBoard.CELL_SIZE, GameBoard.ROW * GameBoard.CELL_SIZE);
    private Rectangle gameOverShade = new Rectangle(GameBoard.COLUMN * GameBoard.CELL_SIZE, GameBoard.ROW * GameBoard.CELL_SIZE);

    private Label gameOverInformation = new Label("You made " + score + " points\nand reached level " + level + "!");

    private BorderPane borderPane = new BorderPane();
    private GridPane gridPane = new GridPane();
    private BorderPane toolBorderPane = new BorderPane();

    /**
     * This grid pane is the next brick that shows in the top_right of the boarder pane
     */
    private GridPane brickGridPane = new GridPane();

    private VBox toolVBox1 = new VBox(15);
    private Label levelLabel = new Label("  1");
    private Label scoreLabel = new Label("  0");
    private Label heartLabel = new Label();

    private VBox toolVBox2 = new VBox(10);
    private Button menuButton = new Button("_MENU");
    private Button startButton = new Button("START");

    private MediaPlayer gameSound;
    private MediaPlayer brokenHeartSound;
    private MediaPlayer gameOverSound;

    public TETRIS() {
        setNewGameBoard();
    }

    {
        level = settingsManager.getStartLevel();

        {
            {
                ImageView startImage = new ImageView(new Image("file:///C:\\Users\\top.TOP-PC\\IdeaProjects\\Tetris\\src\\Start.png"));
                startImage.setFitHeight(100.0);
                startImage.setFitWidth(100.0);

                Label startTitle = new Label("Press start button");
                startTitle.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 15.0));
                startTitle.setTextAlignment(TextAlignment.CENTER);
                startTitle.setTextFill(Color.WHITE);

                VBox startVBox = new VBox();
                startVBox.getChildren().addAll(startImage, startTitle);
                startVBox.setAlignment(Pos.CENTER);

                startCenter.setCenter(startVBox);

                startShade.setOpacity(0.5);
                startShade.setFill(Color.BLACK);
            }

            {
                ImageView pauseImage = new ImageView(new Image("pause.png"));

                Label pauseTitle = new Label("PAUSE");
                pauseTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15.0));
                pauseTitle.setTextAlignment(TextAlignment.CENTER);
                pauseTitle.setTextFill(Color.WHITE);

                VBox pauseVBox = new VBox();
                pauseVBox.getChildren().addAll(pauseImage, pauseTitle);
                pauseVBox.setAlignment(Pos.CENTER);

                pauseCenter.setCenter(pauseVBox);

                pauseShade.setOpacity(0.5);
                pauseShade.setFill(Color.BLACK);
            }

            {
                ImageView brokenHeartImage = new ImageView(new Image("file:///C:\\Users\\top.TOP-PC\\IdeaProjects\\Tetris\\src\\brokenHeart.png"));
                brokenHeartImage.setFitHeight(60.0);
                brokenHeartImage.setFitWidth(57.0);

                Label brokenHeartTitle = new Label("sometimes you have to lose to win");
                brokenHeartTitle.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13.0));
                brokenHeartTitle.setTextAlignment(TextAlignment.CENTER);
                brokenHeartTitle.setTextFill(Color.WHITE);

                VBox brokenHeartVBox = new VBox();
                brokenHeartVBox.getChildren().addAll(brokenHeartImage, brokenHeartTitle);
                brokenHeartVBox.setAlignment(Pos.CENTER);

                brokenHeartCenter.setCenter(brokenHeartVBox);

                brokenHeartShade.setOpacity(0.5);
                brokenHeartShade.setFill(Color.BLACK);
            }

            {
                ImageView gameOverImage = new ImageView(new Image("gameOver.png"));

                Label gameOverTitle = new Label("GAME OVER");
                gameOverTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15.0));
                gameOverTitle.setTextAlignment(TextAlignment.CENTER);
                gameOverTitle.setTextFill(Color.WHITE);

                gameOverInformation.setFont(Font.font("Segoe UI Semilight", 13.0));
                gameOverInformation.setTextAlignment(TextAlignment.CENTER);
                gameOverInformation.setTextFill(Color.WHITE);

                VBox gameOverVBox = new VBox();
                gameOverVBox.getChildren().addAll(gameOverImage, gameOverTitle, gameOverInformation);
                gameOverVBox.setAlignment(Pos.CENTER);

                gameOverCenter.setCenter(gameOverVBox);

                gameOverShade.setOpacity(0.5);
                gameOverShade.setFill(Color.BLACK);
            }
        }

        {
            gridPane.setGridLinesVisible(true);
            for (int i = 0; i < GameBoard.COLUMN; i++) {
                gridPane.getColumnConstraints().add(new ColumnConstraints(GameBoard.CELL_SIZE));
            }
            for (int i = 0; i < GameBoard.ROW; i++) {
                gridPane.getRowConstraints().add(new RowConstraints(GameBoard.CELL_SIZE));
            }
        }
        stackPane.getChildren().add(gridPane);
        borderPane.setLeft(stackPane);

        {
            brickGridPane.setPadding(new Insets(20, 10, 10, 10));
            brickGridPane.setGridLinesVisible(true);
            for (int i = 0; i < 3; i++) {
                brickGridPane.getColumnConstraints().add(new ColumnConstraints(GameBoard.CELL_SIZE));
            }
            for (int i = 0; i < 3; i++) {
                brickGridPane.getRowConstraints().add(new RowConstraints(GameBoard.CELL_SIZE));
            }
        }
        toolBorderPane.setTop(brickGridPane);

        {
            ImageView levelImage = new ImageView(
                    new Image(getClass().getResourceAsStream("level.png")));
            levelImage.setFitHeight(30.0);
            levelImage.setFitWidth(30.0);
            levelLabel.setGraphic(levelImage);
            levelLabel.setTextFill(Color.WHITE);
        }
        toolVBox1.getChildren().add(levelLabel);

        {
            ImageView scoreImage = new ImageView(
                    new Image(getClass().getResourceAsStream("star.png")));
            scoreImage.setFitHeight(30.0);
            scoreImage.setFitWidth(30.0);
            scoreLabel.setGraphic(scoreImage);
            scoreLabel.setTextFill(Color.WHITE);
        }
        toolVBox1.getChildren().add(scoreLabel);

        {
            ImageView heartImage = new ImageView(
                    new Image(getClass().getResourceAsStream("heart.png")));
            heartImage.setFitHeight(23.0);
            heartImage.setFitWidth(23.0);
            heartLabel.setGraphic(heartImage);
            heartLabel.setTextFill(Color.WHITE);
        }
        toolVBox1.getChildren().add(heartLabel);

        toolVBox1.setAlignment(Pos.CENTER);
        toolBorderPane.setCenter(toolVBox1);

        {
            menuButton.setMaxWidth(100);
            menuButton.setMaxHeight(50);
            menuButton.setFont(Font.font("Helvetica", FontWeight.BOLD, 12));
            menuButton.setStyle("-fx-background-color: FF66B2");
            menuButton.setTextFill(Color.BLACK);
            menuButton.setMnemonicParsing(true);
        }
        toolVBox2.getChildren().add(menuButton);

        {
            startButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    switch (startButton.getText()) {
                        case "START":
                            startButton.setText("STOP");
                            menuButton.setDisable(true);
                            gameSound.play();
                            start();
                            break;
                        case "STOP":
                            startButton.setText("START");
                            menuButton.setDisable(false);
                            gameSound.pause();
                            stop();
                            break;
                    }
                }
            });
            startButton.setMaxWidth(100);
            startButton.setMaxHeight(50);
            startButton.setFont(Font.font("Helvetica", FontWeight.BOLD, 12));
            startButton.setStyle("-fx-background-color: FF66B2");
            startButton.setTextFill(Color.BLACK);
            startButton.setMnemonicParsing(true);
        }
        toolVBox2.getChildren().add(startButton);

        toolBorderPane.setBottom(toolVBox2);

        {
            borderPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
            borderPane.setPadding(new Insets(10));
            borderPane.setRight(toolBorderPane);
        }

        parent = borderPane;

        {
            parent.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
                try {
                    switch (keyEvent.getCode()) {
                        case LEFT:
                        case A:
                            move(EDirection.LEFT);
                            break;
                        case RIGHT:
                        case D:
                            move(EDirection.RIGHT);
                            break;
                        case DOWN:
                        case S:
                            move(EDirection.DOWN);
                            break;
                        case UP:
                        case W:
                            gameBoard.rotate();
                            break;
                        case ENTER:
                            moveDown();
                            keyEvent.consume();
                    }
                } catch (NullPointerException ignored) {

                }
            });
        }

        gameSound = new MediaPlayer(new Media(new File("C:\\Users\\top.TOP-PC\\IdeaProjects\\Tetris\\src\\Vicious.mp3").toURI().toString()));

        gameSound.setOnEndOfMedia(new Runnable() {
            public void run() {
                gameSound.seek(Duration.ZERO);
            }
        });

        brokenHeartSound = new MediaPlayer(new Media(new File("C:\\Users\\top.TOP-PC\\IdeaProjects\\Tetris\\src\\broken-heart.wav").toURI().toString()));

        gameOverSound = new MediaPlayer(new Media(new File("C:\\Users\\top.TOP-PC\\IdeaProjects\\Tetris\\src\\game-over.wav").toURI().toString()));
    }

    /**
     * This method saves all the specifications.
     */
    public void save() {
        settingsManager.save();
        scoresManager.save();
    }

    /**
     * This method calls the method  {@link #gameBoard moveDown()} and request move down for the brick.
     */
    public void moveDown() {
        gameBoard.moveDown();
    }

    /**
     * This method asks {@link #gameBoard} to move the brick to the right, left or down one cell.
     *
     * @param direction determines the direction of movement.
     */
    public void move(EDirection direction) {
        gameBoard.move(direction);
    }

    /**
     * This method removes both of the old grid and added shades
     * Then adds the game new grid to the stack pane
     */
    public void start() {

        stackPane.getChildren().remove(gameBoard.getGridPane());
        gameBoard.start();

        stackPane.getChildren().removeAll(startShade, startCenter, pauseShade, pauseCenter, brokenHeartShade, brokenHeartCenter, gameOverShade, gameOverCenter);
        stackPane.getChildren().add(gameBoard.getGridPane());
        borderPane.setLeft(stackPane);
    }

    /**
     * This method calls the stop of {@link #gameBoard}.
     * and sets the pause shade into scene
     */
    public void stop() {
        stackPane.getChildren().addAll(pauseShade, pauseCenter);
        gameBoard.stop();
    }

    /**
     * This method manages the needed shad for the user interface during playing the game
     * <p>
     * Depends on heart number chooses the shade and then sets it in front of the game board
     */
    private void setShade() {

        switch (heart) {
            case 0:
                gameOverSound.stop();
                gameOverSound.play();
                stackPane.getChildren().addAll(gameOverShade, gameOverCenter);
                startButton.setDisable(true);
                break;
            case 3:
                stackPane.getChildren().addAll(startShade, startCenter);
                break;
            default:
                brokenHeartSound.stop();
                brokenHeartSound.play();
                stackPane.getChildren().addAll(brokenHeartShade, brokenHeartCenter);
        }
    }

    /**
     * Creates a new gameBoard to start a new game and updates the layout and needed variables
     * <p>
     * When the player starts the game for the first time after clicking on {@link Menu#getNewGameButton()}
     * and when the player lose heart
     */
    public void setNewGameBoard() {

        gameBoard = new GameBoard(this, level);
        settingsManager.load();

        heartLabel.setText("   " + --heart);
        startButton.setText("START");
        menuButton.setDisable(false);
        gameSound.pause();
        setShade();
    }

    /**
     * Removes full lines (if present), updates score and level
     */
    public void updateScore() {
        score += settingsManager.getScoresPerRow();
        scoreLabel.setText("  " + score);
        gameOverInformation.setText("You made " + score + " points\nand reached level " + level + "!");
        if ((score / settingsManager.getScoresPerRow() % 2 == 0) && level < 11) {
            setLevel(++level);
        }
    }

    // Getters and Setters

    /**
     * Getter for parent
     *
     * @return parent as a root of the main scene
     */
    public Parent getParent() {
        return parent;
    }

    /**
     * Setter for the level
     * <p>
     * the level of the TETRIS and {@link #gameBoard} will be update.
     *
     * @param level new level of the game
     */
    public void setLevel(int level) {
        this.level = level;
        levelLabel.setText("  " + level);
        gameBoard.setLevel(level);
        gameOverInformation.setText("You made " + score + " points\nand reached level " + level + "!");
    }

    /**
     * Getter for the sound of the game
     *
     * @return the sound of the game
     */
    public MediaPlayer getGameSound() {
        return gameSound;
    }

    /**
     * Getter for the menu button
     *
     * @return the menu button for handling
     */
    public Button getMenuButton() {
        return menuButton;
    }

    /**
     * Getter for the grid pane of the TETRIS
     *
     * @return the grid pane of the TETRIS
     */
    public GridPane getGridPane() {
        return gridPane;
    }

    /**
     * Getter for the score manager
     *
     * @return the score manager
     */
    public ScoresManager getScoresManager() {
        return scoresManager;
    }

    /**
     * Getter for the settings manager
     *
     * @return the settings manager
     */
    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    /**
     * Getter for the game board
     *
     * @return the game board of the TETRIS
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * Getter for the grid pane that is in the right of the boarder pane
     *
     * @return grid pane
     */
    public GridPane getBrickGridPane() {
        return brickGridPane;
    }

    /**
     * Getter for score of player
     *
     * @return score
     */
    public int getScore() {
        return score;
    }
}

/**
 * Game status
 */
enum EState {STOP, START}

/**
 * Type of movement
 */
enum EDirection {LEFT, RIGHT, DOWN}


/**
 * The class <b>GameBoard</b> represents the board/grid used in TETRIS.
 *
 * @author Farkhondeh Arzi
 * @author Mahdieh ShahHosseinian
 */

class GameBoard {

    /**
     * The state of the game
     */
    private EState state;

    /**
     * The background color of the board
     */
    private Color backgroundColor;
    private int level;
    public static final int ROW = 20, COLUMN = 15;
    public static final int CELL_SIZE = 20;

    /**
     * The value of each cell of the board
     */
    private boolean[][] board = new boolean[COLUMN][ROW];

    private TETRIS tetris;
    private Brick currentBrick;
    private Brick nextBrick;
    private GridPane gridPane = new GridPane();

    /**
     * A list of rectangles of the fallen bricks
     */
    private List<Rectangle> rectangles = new ArrayList<>();

    GameBoard gameBoard = this;

    private MediaPlayer clearSound = new MediaPlayer(new Media(new File("C:\\Users\\top.TOP-PC\\IdeaProjects\\Tetris\\src\\clear.wav").toURI().toString()));

    public GameBoard(TETRIS tetris, int level) {
        this.tetris = tetris;
        this.level = level;
    }

    {
        {
            for (int i = 0; i < COLUMN; i++) {
                for (int j = 0; j < ROW; j++) {
                    board[i][j] = false;
                }
            }
        }

        {
            gridPane.setGridLinesVisible(true);
            gridPane.setBackground(new Background(new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY)));

            for (int i = 0; i < COLUMN; i++) {
                gridPane.getColumnConstraints().add(new ColumnConstraints(CELL_SIZE));
            }
            for (int i = 0; i < ROW; i++) {
                gridPane.getRowConstraints().add(new RowConstraints(CELL_SIZE));
            }
        }

        nextBrick = randomBrick();
    }

    /**
     * This methods starts the game.
     * While current brick doesn't achieve to down paints it.
     */
    public void start() {

        state = EState.START;

        Runnable runnable = new Runnable() {

            @Override
            public void run() {

                startLoop:
                while (true) {

                    if (boardFilled()) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                tetris.setNewGameBoard();
                            }
                        });
                        break;
                    }

                    currentBrick = nextBrick;
                    nextBrick = randomBrick();

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {

                            tetris.getBrickGridPane().getChildren().clear();

                            for (int i = 0; i < 3; i++) {
                                for (int j = 0; j < 3; j++) {
                                    if (nextBrick.getPointsStatus()[i][j]) {

                                        TRectangle tRectangle = new TRectangle(Color.rgb(255, 102, 178));
                                        tRectangle.requestReflection(true);
                                        tRectangle.setEffect();
                                        tetris.getBrickGridPane().add(tRectangle.getRectangle(), i, j);
                                    }
                                }
                            }
                        }
                    });

                    while (currentBrick.next()) {

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                paint();
                            }
                        });

                        try {
                            Thread.sleep(sleepTime());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                reform();
                            }
                        });

                        if (state.equals(EState.STOP)) {
                            break startLoop;
                        }
                    }
                }
            }
        };
        new Thread(runnable).start();
    }

    /**
     * This method paints current brick in the board and lowers it one cell.
     */
    public void paint() {
        currentBrick.paint();
        currentBrick.updateY(1);
    }

    /**
     * This method clears the extra rectangles in the board.
     */
    public void reform() {

        if (currentBrick.next()) {
            gridPane.getChildren().removeAll(currentBrick.getRectangles());
        }
        updateGrid();
    }

    /**
     * This method changes the state of the game into stop.
     */
    public void stop() {
        state = EState.STOP;
    }

    /**
     * This method moves current brick down of the board.
     */
    public void moveDown() {

        while (currentBrick.move(EDirection.DOWN)) {
            currentBrick.updateY(1);
        }
    }

    /**
     * This method moves current brick to the left, right or down one cell.
     *
     * @param direction determines the direction of movement.
     */
    public void move(EDirection direction) {

        switch (direction) {
            case LEFT:
                if (currentBrick.move(EDirection.LEFT)) {
                    currentBrick.updateX(-1);
                }
                break;
            case RIGHT:
                if (currentBrick.move(EDirection.RIGHT)) {
                    currentBrick.updateX(1);
                }
                break;
            case DOWN:
                if (currentBrick.move(EDirection.DOWN)) {
                    currentBrick.updateY(1);
                }
                break;
        }
    }

    /**
     * This method check if the current brick can rotate and then rotates it.
     */
    public void rotate() {

        if (currentBrick.canRotate()) {
            currentBrick.rotate();
        }
    }

    /**
     * This method check if a cell of the board is filled or not.
     *
     * @param x determines column of the cell that must be checked.
     * @param y determines row of the cell that must be checked.
     * @return the value of the board in this cell.
     */
    public boolean isFilled(int x, int y) {
        return board[x][y];
    }

    /**
     * This method fills a cell of the board.
     *
     * @param x determines column of the cell that must be filled.
     * @param y determines row of the cell that must be filled.
     */
    public void fill(int x, int y) {
        board[x][y] = true;
    }

    /**
     * This method check if the board is filled or not.
     */
    public boolean boardFilled() {

        if (currentBrick == null) {
            return false;
        }
        if (currentBrick.getY() <= 0) {
            state = EState.STOP;
            return true;
        }
        return false;
    }

    /**
     * This method updates the gridPane & removes filled lines and brings down the top lines.
     */
    public void updateGrid() {

        for (Integer i : filledLines()) {

            clearSound.play();

            for (Rectangle rectangle : rectangles) {
                for (int j = 0; j < COLUMN; j++) {
                    if (GridPane.getColumnIndex(rectangle).equals(j) && GridPane.getRowIndex(rectangle).equals(i)) {
                        gridPane.getChildren().remove(rectangle);
                    }
                }
            }

            for (int j = i; j > 0; j--) {
                for (int k = 0; k < COLUMN; k++) {

                    if (board[k][j]) {

                        board[k][j] = false;
                        for (Rectangle rectangle : rectangles) {
                            if (GridPane.getColumnIndex(rectangle).equals(k) && GridPane.getRowIndex(rectangle).equals(j)) {
                                gridPane.getChildren().remove(rectangle);
                            }
                        }
                    }

                    if (board[k][j - 1]) {

                        board[k][j] = true;
                        List<Rectangle> rectangleList = new ArrayList<>();
                        for (Rectangle rectangle : rectangles) {
                            if (GridPane.getColumnIndex(rectangle).equals(k) && GridPane.getRowIndex(rectangle).equals(j - 1)) {
                                TRectangle tRectangle = new TRectangle((Color) rectangle.getFill());

                                gridPane.add(tRectangle.getRectangle(), k, j);
                                rectangleList.add(tRectangle.getRectangle());
                            }
                        }
                        rectangles.addAll(rectangleList);
                    }
                }
            }
        }
        clearSound.stop();
    }

    /**
     * This method checks if a line is filled and then add that line to the list.
     *
     * @return a list of filled lines.
     */
    public List<Integer> filledLines() {

        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < ROW; i++) {

            boolean lineFilled = true;
            for (int j = 0; j < COLUMN; j++) {
                if (!board[j][i]) {
                    lineFilled = false;
                    break;
                }
            }

            if (lineFilled) {
                tetris.updateScore();
                list.add(i);
            }
        }

        return list;
    }

    /**
     * This method choose randomly a brick.
     *
     * @return a brick.
     */
    private Brick randomBrick() {

        Random random = new Random();

        switch (random.nextInt(9) + 1) {
            case 1:
                return new Brick1(gameBoard, 7, 0);
            case 2:
                return new Brick2(gameBoard, 7, 0);
            case 3:
                return new Brick3(gameBoard, 7, 0);
            case 4:
                return new Brick4(gameBoard, 7, 0);
            case 5:
                return new Brick5(gameBoard, 7, 0);
            case 6:
                return new Brick6(gameBoard, 7, 0);
            case 7:
                return new Brick7(gameBoard, 7, 0);
            case 8:
                return new Brick8(gameBoard, 7, 0);
            case 9:
                return new Brick9(gameBoard, 7, 0);
        }
        return null;
    }

    /**
     * This method puts sleep time of thread based on level.
     *
     * @return sleep time of thread.
     */
    private int sleepTime() {

        switch (level) {
            case 10:
                return 100;
            case 9:
                return 150;
            case 8:
                return 200;
            case 7:
                return 300;
            case 6:
                return 400;
            case 5:
                return 500;
            case 4:
                return 600;
            case 3:
                return 700;
            case 2:
                return 800;
            case 1:
                return 900;
        }
        return 1000;
    }

    // Setters and Getters

    /**
     * Setter for backgroundColor
     *
     * @param backgroundColor the new color of background
     */
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        gridPane.setBackground(new Background(new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    /**
     * Setter of level
     *
     * @param level the new level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * @return game board
     */
    public GridPane getGridPane() {
        return gridPane;
    }

    /**
     * Setter of rectangles list
     *
     * @param rectangles add to rectangles list of the gameBoard.
     */
    public void setRectangles(List<Rectangle> rectangles) {
        this.rectangles.addAll(rectangles);
    }
}


/**
 * This class makes a brick
 * and sets the coordinate of it and paints it in the board of the gameBoard.
 * It is responsible for rotation and movement.
 *
 * @author Farkhondeh Arzi
 * @author Mahdieh SahHosseinian
 */

abstract class Brick {

    /**
     * these fields keep the coordinate of the brick.
     * x and y belong to the left and top rectangle of each brick
     */
    protected int x, y;
    private static final int ROW = 3, COlUMN = 3;

    /**
     * Specifies full cells.
     */
    protected boolean[][] pointsStatus = new boolean[COlUMN][ROW];
    protected Color color;

    /**
     * list of rectangles in brick
     */
    private List<Rectangle> rectangles = new ArrayList<>();
    protected GameBoard gameBoard;

    public Brick(GameBoard gameBoard, int x, int y) {
        this.gameBoard = gameBoard;
        this.x = x;
        this.y = y;
    }

    /**
     * This method rotates the brick to the right.
     */
    public abstract void rotate();

    /**
     * Helper method for rotating the brick
     *
     * @return true if the next rotation is within the board and does not collide
     * with other bricks
     */
    public abstract boolean canRotate();

    /**
     * This method makes new rectangles for full cells and adds them to the rectangles list.
     */
    public void paint() {

        rectangles.clear();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (pointsStatus[j][i]) {

                    TRectangle tRectangle = new TRectangle(color);
                    tRectangle.setEffect();
                    rectangles.add(tRectangle.getRectangle());
                    gameBoard.getGridPane().add(tRectangle.getRectangle(), x + j, y + i);
                }
            }
        }
    }

    /**
     * This method fills the cells related to the brick in the board.
     */
    public void freeze() {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (pointsStatus[j][i]) {
                    gameBoard.fill(x + j, y - 1 + i);
                }
            }
        }
        gameBoard.setRectangles(rectangles);
    }

    /**
     * This method checks if the brick can move or not.
     *
     * @param direction determines the direction of movement.
     * @return true if the brick has conditions for movement.
     */
    public boolean move(EDirection direction) {

        switch (direction) {
            case RIGHT:
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (pointsStatus[j][i]) {

                            try {
                                if ((x + j) + 1 > GameBoard.COLUMN - 1 || gameBoard.isFilled((x + j) + 1, y + i)) {
                                    return false;
                                }
                            } catch (ArrayIndexOutOfBoundsException ignored) {
                                return false;
                            }
                        }
                    }
                }
                break;
            case LEFT:
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (pointsStatus[j][i]) {

                            try {
                                if ((x + j) - 1 < 0 || gameBoard.isFilled((x + j) - 1, y + i)) {
                                    return false;
                                }
                            } catch (ArrayIndexOutOfBoundsException ignored) {
                                return false;
                            }
                        }
                    }
                }
                break;
            case DOWN:
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (pointsStatus[j][i]) {

                            if ((y + i) + 1 > GameBoard.ROW - 1 || gameBoard.isFilled(x + j, (y + i) + 1)) {
                                return false;
                            }
                        }
                    }
                }
                break;
        }
        return true;
    }

    /**
     * This method checks if the brick can come down one cell or not.
     *
     * @return true if the brick doesn't achieve to the bottom or a frozen bricks.
     */
    public boolean next() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (pointsStatus[j][i]) {

                    if (y + i > GameBoard.ROW - 1 || gameBoard.isFilled(x + j, y + i)) {
                        if (!gameBoard.boardFilled()) freeze();
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //setters & getters

    /**
     * Updates the x
     *
     * @param update the new x coordinate of the brick
     */
    public void updateX(int update) {
        this.x += update;
    }

    /**
     * Updates the y
     *
     * @param update the new y coordinate of the brick
     */
    public void updateY(int update) {
        this.y += update;
    }

    /**
     * Getter for y
     *
     * @return the y coordinate of the brick
     */
    public int getY() {
        return y;
    }

    /**
     * Getter for pointsStatus
     *
     * @return the points status of the brick
     */
    public boolean[][] getPointsStatus() {
        return pointsStatus;
    }

    /**
     * Getter for rectangles
     *
     * @return the rectangles of this brick
     */
    public List<Rectangle> getRectangles() {
        return rectangles;
    }
}

class Brick1 extends Brick {

    public Brick1(GameBoard gameBoard, int x, int y) {
        super(gameBoard, x, y);
    }

    {
        pointsStatus[0][0] = pointsStatus[1][0] = pointsStatus[0][1] = pointsStatus[1][1] = true;
        color = Color.rgb(255, 0, 0);
    }

    @Override
    public void rotate() {
    }

    @Override
    public boolean canRotate() {
        return false;
    }
}

class Brick2 extends Brick {

    public Brick2(GameBoard gameBoard, int x, int y) {
        super(gameBoard, x, y);
    }

    {
        pointsStatus[0][0] = pointsStatus[0][1] = pointsStatus[1][1] = true;
        color = Color.rgb(255, 0, 255);
    }

    @Override
    public void rotate() {

        if (!pointsStatus[1][0]) {
            pointsStatus[1][1] = false;
            pointsStatus[1][0] = true;
        } else if (!pointsStatus[1][1]) {
            pointsStatus[0][1] = false;
            pointsStatus[1][1] = true;
        } else if (!pointsStatus[0][1]) {
            pointsStatus[0][0] = false;
            pointsStatus[0][1] = true;
        } else if (!pointsStatus[0][0]) {
            pointsStatus[1][0] = false;
            pointsStatus[0][0] = true;
        }
    }

    @Override
    public boolean canRotate() {
        if (!pointsStatus[1][0]) {
            return !gameBoard.isFilled(x + 1, y);
        } else if (!pointsStatus[1][1]) {
            return !gameBoard.isFilled(x + 1, y + 1);
        } else if (!pointsStatus[0][1]) {
            return !gameBoard.isFilled(x, y + 1);
        } else if (!pointsStatus[0][0]) {
            return !gameBoard.isFilled(x, y);
        }
        return true;
    }
}

class Brick3 extends Brick {

    public Brick3(GameBoard gameBoard, int x, int y) {
        super(gameBoard, x, y);
    }

    {
        pointsStatus[0][0] = pointsStatus[0][1] = pointsStatus[0][2] = true;
        color = Color.rgb(0, 255, 255);
    }

    @Override
    public void rotate() {

        if (pointsStatus[0][2]) {
            pointsStatus[0][0] = pointsStatus[0][2] = false;
            pointsStatus[1][1] = pointsStatus[2][1] = true;
        } else {
            pointsStatus[1][1] = pointsStatus[2][1] = false;
            pointsStatus[0][0] = pointsStatus[0][2] = true;
        }
    }

    @Override
    public boolean canRotate() {
        if (x == GameBoard.COLUMN - 1 || x == GameBoard.COLUMN - 2 || y == 19 || y == 18) {
            return false;
        }
        if (pointsStatus[0][2]) {
            return !gameBoard.isFilled(x + 1, y + 1) && !gameBoard.isFilled(x + 2, y + 1);
        }
        if (pointsStatus[1][1]) {
            return !gameBoard.isFilled(x, y) && !gameBoard.isFilled(x, y + 2);
        }
        return true;
    }
}

class Brick4 extends Brick {

    public Brick4(GameBoard gameBoard, int x, int y) {
        super(gameBoard, x, y);
    }

    {
        pointsStatus[1][0] = pointsStatus[1][1] = pointsStatus[1][2] = pointsStatus[0][2] = true;
        color = Color.rgb(0, 0, 255);
    }

    @Override
    public void rotate() {

        if (pointsStatus[1][2]) {
            pointsStatus[1][0] = pointsStatus[1][2] = pointsStatus[0][2] = false;
            pointsStatus[0][0] = pointsStatus[0][1] = pointsStatus[2][1] = true;
        } else if (pointsStatus[2][1] && pointsStatus[0][1]) {
            pointsStatus[1][1] = pointsStatus[2][1] = false;
            pointsStatus[1][0] = pointsStatus[0][2] = true;
        } else if (pointsStatus[0][1] && pointsStatus[0][2]) {
            pointsStatus[0][1] = pointsStatus[0][2] = false;
            pointsStatus[2][0] = pointsStatus[2][1] = true;
        } else if (pointsStatus[2][0]) {
            pointsStatus[2][0] = pointsStatus[2][1] = pointsStatus[0][0] = false;
            pointsStatus[1][1] = pointsStatus[1][2] = pointsStatus[0][2] = true;
        }
    }

    @Override
    public boolean canRotate() {
        if (x == GameBoard.COLUMN - 1 || x == GameBoard.COLUMN - 2 || y == 19 || y == 18) {
            return false;
        }
        if (pointsStatus[1][2]) {
            return !gameBoard.isFilled(x, y) && !gameBoard.isFilled(x, y + 1) && !gameBoard.isFilled(x + 2, y + 1);
        }
        if (pointsStatus[2][1] && pointsStatus[0][1]) {
            return !gameBoard.isFilled(x + 1, y) && !gameBoard.isFilled(x, y + 2);
        }
        if (pointsStatus[0][1] && pointsStatus[0][2]) {
            return !gameBoard.isFilled(x + 2, y) && !gameBoard.isFilled(x + 2, y + 1);
        }
        if (pointsStatus[2][0]) {
            return !gameBoard.isFilled(x + 1, y + 1) && !gameBoard.isFilled(x + 1, y + 2) && !gameBoard.isFilled(x, y + 2);
        }
        return true;
    }
}

class Brick5 extends Brick {

    public Brick5(GameBoard gameBoard, int x, int y) {
        super(gameBoard, x, y);
    }

    {
        pointsStatus[0][0] = pointsStatus[0][1] = pointsStatus[0][2] = pointsStatus[1][2] = true;
        color = Color.rgb(255, 128, 0);
    }

    @Override
    public void rotate() {

        if (pointsStatus[0][2]) {
            pointsStatus[0][2] = pointsStatus[1][2] = false;
            pointsStatus[1][0] = pointsStatus[2][0] = true;
        } else if (pointsStatus[1][0] && pointsStatus[2][0]) {
            pointsStatus[0][1] = pointsStatus[2][0] = false;
            pointsStatus[1][1] = pointsStatus[1][2] = true;
        } else if (pointsStatus[1][1] && pointsStatus[1][2]) {
            pointsStatus[1][0] = pointsStatus[0][0] = pointsStatus[1][2] = false;
            pointsStatus[2][0] = pointsStatus[2][1] = pointsStatus[0][1] = true;
        } else if (pointsStatus[2][1]) {
            pointsStatus[2][0] = pointsStatus[2][1] = pointsStatus[1][1] = false;
            pointsStatus[0][0] = pointsStatus[0][2] = pointsStatus[1][2] = true;
        }
    }

    @Override
    public boolean canRotate() {
        if (x == GameBoard.COLUMN - 1 || x == GameBoard.COLUMN - 2 || y == 19 || y == 18) {
            return false;
        }
        if (pointsStatus[0][2]) {
            return !gameBoard.isFilled(x + 1, y) && !gameBoard.isFilled(x + 2, y);
        }
        if (pointsStatus[1][0] && pointsStatus[2][0]) {
            return !gameBoard.isFilled(x + 1, y + 1) && !gameBoard.isFilled(x + 1, y + 2);
        }
        if (pointsStatus[1][1] && pointsStatus[1][2]) {
            return !gameBoard.isFilled(x + 2, y) && !gameBoard.isFilled(x + 2, y + 1) && !gameBoard.isFilled(x, y + 1);
        }
        if (pointsStatus[2][1]) {
            return !gameBoard.isFilled(x, y) && !gameBoard.isFilled(x, y + 2) && !gameBoard.isFilled(x + 1, y + 2);
        }
        return true;
    }
}

class Brick6 extends Brick {

    public Brick6(GameBoard gameBoard, int x, int y) {
        super(gameBoard, x, y);
    }

    {
        pointsStatus[0][0] = pointsStatus[1][0] = pointsStatus[1][1] = pointsStatus[2][1] = true;
        color = Color.rgb(255, 255, 0);
    }

    @Override
    public void rotate() {

        if (pointsStatus[0][0]) {
            pointsStatus[0][0] = pointsStatus[2][1] = false;
            pointsStatus[0][1] = pointsStatus[0][2] = true;
        } else if (pointsStatus[0][1]) {
            pointsStatus[0][1] = pointsStatus[0][2] = false;
            pointsStatus[0][0] = pointsStatus[2][1] = true;
        }
    }

    @Override
    public boolean canRotate() {
        if (x == GameBoard.COLUMN - 2 || x == GameBoard.COLUMN - 1 || y == GameBoard.ROW - 2 || y == GameBoard.ROW - 1) {
            return false;
        }
        if (pointsStatus[0][0]) {
            return !gameBoard.isFilled(x, y + 1) && !gameBoard.isFilled(x, y + 2);
        }
        if (pointsStatus[0][1]) {
            return !gameBoard.isFilled(x, y) && !gameBoard.isFilled(x + 2, y + 1);
        }
        return true;
    }
}

class Brick7 extends Brick {

    public Brick7(GameBoard gameBoard, int x, int y) {
        super(gameBoard, x, y);
    }

    {
        pointsStatus[1][0] = pointsStatus[2][0] = pointsStatus[0][1] = pointsStatus[1][1] = true;
        color = Color.rgb(127, 0, 255);
    }

    @Override
    public void rotate() {

        if (pointsStatus[2][0]) {
            pointsStatus[1][0] = pointsStatus[2][0] = false;
            pointsStatus[0][0] = pointsStatus[1][2] = true;
        } else if (pointsStatus[0][0]) {
            pointsStatus[0][0] = pointsStatus[1][2] = false;
            pointsStatus[1][0] = pointsStatus[2][0] = true;
        }
    }

    @Override
    public boolean canRotate() {
        if (x == GameBoard.COLUMN - 2 || x == GameBoard.COLUMN - 1 || y == GameBoard.ROW - 2 || y == GameBoard.ROW - 1) {
            return false;
        }
        if (pointsStatus[2][0]) {
            return !gameBoard.isFilled(x, y) && !gameBoard.isFilled(x + 1, y + 2);
        }
        if (pointsStatus[0][0]) {
            return !gameBoard.isFilled(x + 1, y) && !gameBoard.isFilled(x + 2, y);
        }
        return true;
    }
}

class Brick8 extends Brick {

    public Brick8(GameBoard gameBoard, int x, int y) {
        super(gameBoard, x, y);
    }

    {
        pointsStatus[0][0] = pointsStatus[1][0] = pointsStatus[2][0] = pointsStatus[0][1] = pointsStatus[2][1] = true;
        color = Color.rgb(255, 0, 127);
    }

    @Override
    public void rotate() {

        if (pointsStatus[1][0] && pointsStatus[2][0]) {
            pointsStatus[2][0] = pointsStatus[0][1] = pointsStatus[2][1] = false;
            pointsStatus[1][1] = pointsStatus[1][2] = pointsStatus[0][2] = true;
        } else if (pointsStatus[1][1] && pointsStatus[1][2]) {
            pointsStatus[1][2] = pointsStatus[0][2] = pointsStatus[1][0] = false;
            pointsStatus[0][1] = pointsStatus[2][0] = pointsStatus[2][1] = true;
        } else if (pointsStatus[1][1] && pointsStatus[2][1]) {
            pointsStatus[2][0] = pointsStatus[2][1] = pointsStatus[1][1] = false;
            pointsStatus[1][0] = pointsStatus[0][2] = pointsStatus[1][2] = true;
        } else if (pointsStatus[0][1] && pointsStatus[0][2]) {
            pointsStatus[0][2] = pointsStatus[1][2] = false;
            pointsStatus[2][0] = pointsStatus[2][1] = true;
        }
    }

    @Override
    public boolean canRotate() {
        if (x == GameBoard.COLUMN - 2 || y == GameBoard.ROW - 2 || y == GameBoard.ROW - 1) {
            return false;
        }
        if (pointsStatus[1][0] && pointsStatus[2][0]) {
            return !gameBoard.isFilled(x + 1, y + 1) && !gameBoard.isFilled(x + 1, y + 2) && !gameBoard.isFilled(x, y + 2);
        }
        if (pointsStatus[1][1] && pointsStatus[1][2]) {
            return !gameBoard.isFilled(x, y + 1) && !gameBoard.isFilled(x + 2, y) && !gameBoard.isFilled(x + 2, y + 1);
        }
        if (pointsStatus[1][1] && pointsStatus[2][1]) {
            return !gameBoard.isFilled(x + 1, y) && !gameBoard.isFilled(x, y + 2) && !gameBoard.isFilled(x + 1, y + 2);
        }
        if (pointsStatus[0][1] && pointsStatus[0][2]) {
            return !gameBoard.isFilled(x + 2, y) && !gameBoard.isFilled(x + 2, y + 1);
        }
        return true;
    }
}

class Brick9 extends Brick {

    public Brick9(GameBoard gameBoard, int x, int y) {
        super(gameBoard, x, y);
    }

    {
        pointsStatus[1][0] = pointsStatus[0][1] = pointsStatus[1][1] = pointsStatus[2][1] = true;
        color = Color.rgb(0, 255, 0);
    }

    @Override
    public void rotate() {

        if (pointsStatus[2][1]) {
            pointsStatus[2][1] = pointsStatus[1][0] = false;
            pointsStatus[0][0] = pointsStatus[0][2] = true;
        } else if (pointsStatus[0][2]) {
            pointsStatus[0][1] = pointsStatus[0][2] = false;
            pointsStatus[1][0] = pointsStatus[2][0] = true;
        } else if (pointsStatus[2][0]) {
            pointsStatus[0][0] = pointsStatus[2][0] = false;
            pointsStatus[0][1] = pointsStatus[1][2] = true;
        } else if (pointsStatus[1][2]) {
            pointsStatus[1][2] = false;
            pointsStatus[2][1] = true;
        }
    }

    @Override
    public boolean canRotate() {
        if (x == GameBoard.COLUMN - 2 || y == GameBoard.ROW - 1 || y == GameBoard.ROW - 2) {
            return false;
        }
        if (pointsStatus[2][1]) {
            return !gameBoard.isFilled(x, y) && !gameBoard.isFilled(x, y + 2);
        }
        if (pointsStatus[0][2]) {
            return !gameBoard.isFilled(x + 1, y) && !gameBoard.isFilled(x + 2, y);
        }
        if (pointsStatus[2][0]) {
            return !gameBoard.isFilled(x, y + 1) && !gameBoard.isFilled(x + 1, y + 2);
        }
        if (pointsStatus[1][2]) {
            return !gameBoard.isFilled(x + 2, y + 1);
        }
        return true;
    }
}

class Player {

    private int score = 0;
    private String name;

    private Parent parent;

    private VBox infoVBox = new VBox(120);
    private Label welcomeLabel = new Label("W E L C O M E");
    private HBox userNameBox = new HBox(10);
    private Label userNameLabel = new Label("Enter your name:");
    private TextField userNameField = new TextField();
    private Label continueLabel = new Label();

    {
        infoVBox.setAlignment(Pos.CENTER_RIGHT);
        infoVBox.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));

        {
            welcomeLabel.setAlignment(Pos.TOP_CENTER);
            welcomeLabel.setPrefHeight(25);
            welcomeLabel.setPrefWidth(GameBoard.ROW * GameBoard.CELL_SIZE + 20);
            welcomeLabel.setTextFill(Color.BLACK);
            welcomeLabel.setStyle("-fx-background-color: FF66B2");
            welcomeLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.ITALIC, 20));
        }
        infoVBox.getChildren().add(welcomeLabel);

        {
            userNameLabel.setTextFill(Color.BLACK);
            userNameLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
        }
        userNameBox.getChildren().add(userNameLabel);

        userNameBox.getChildren().add(userNameField);

        {
            userNameBox.setPrefWidth(GameBoard.ROW * GameBoard.CELL_SIZE + 20);
            userNameBox.setPrefHeight(130);
            userNameBox.setAlignment(Pos.CENTER);
            userNameBox.setBackground(new Background(new BackgroundFill(Color.rgb(255, 102, 178), CornerRadii.EMPTY, Insets.EMPTY)));
        }
        infoVBox.getChildren().add(userNameBox);

        {
            ImageView levelImage = new ImageView(
                    new Image(getClass().getResourceAsStream("continue.png")));
            levelImage.setFitHeight(50.0);
            levelImage.setFitWidth(99.0);
            continueLabel.setGraphic(levelImage);
        }
        infoVBox.getChildren().add(continueLabel);

        userNameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String userName) {
                name = userName;
            }
        });

        parent = infoVBox;
    }

    public Player() {
    }

    public Player(int score, String name) {
        this.score = score;
        this.name = name;
    }

    //setter & getter

    /**
     * Getter for the score
     *
     * @return current score
     */
    public int getScore() {
        return score;
    }

    /**
     * Setter for score
     *
     * @param score the new value of score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Getter for name
     *
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name
     *
     * @param name a new name for player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for parent
     *
     * @return parent as a root of the main scene
     */
    public Parent getParent() {
        return parent;
    }

    /**
     * Getter for continue label
     *
     * @return the continue label for OnAction handling
     */
    public Label getContinueLabel() {
        return continueLabel;
    }
}

/**
 * This class manages the settings.
 *
 * @author Farkhondeh Arzi
 * @author Madieh ShahHosseinian
 */

class SettingsManager {

    private TETRIS tetris;
    private ScoresManager scoresManager;
    private int scoresPerRow = 10;
    private int startLevel = 1;

    private Parent parent;

    private VBox vBox = new VBox(50);
    private GridPane gridPane = new GridPane();

    private Color backGroundColor = Color.rgb(224, 224, 224);
    private Label topLabel = new Label("S E T T I N G S");

    private Label scoresLabel = new Label("Scores per row:");
    private ComboBox<Integer> scoreComboBox = new ComboBox<>();

    private Label levelLabel = new Label("Start level:");
    private ComboBox<Integer> levelComboBox = new ComboBox<>();

    private Label colorLabel = new Label("Background color:");
    private ColorPicker colorPicker = new ColorPicker();

    private Button clear = new Button("Clear Scores");

    private Button back = new Button("Back");

    private File settingsFile = new File("settings.txt");

    private Alert clearAlert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
    private MediaPlayer alertSound = new MediaPlayer(new Media(new File("C:\\Users\\top.TOP-PC\\IdeaProjects\\Tetris\\src\\alert.mp3").toURI().toString()));

    SettingsManager(TETRIS tetris, ScoresManager scoresManager) {
        this.tetris = tetris;
        this.scoresManager = scoresManager;
    }

    {
        vBox.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));

        {
            topLabel.setAlignment(Pos.TOP_CENTER);
            topLabel.setPrefHeight(25);
            topLabel.setPrefWidth(GameBoard.ROW * GameBoard.CELL_SIZE + 20);
            topLabel.setTextFill(Color.BLACK);
            topLabel.setStyle("-fx-background-color: FF66B2");
            topLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.ITALIC, 20));
        }
        vBox.getChildren().add(topLabel);

        {
            gridPane.setPadding(new Insets(20));
            gridPane.setAlignment(Pos.CENTER);
            gridPane.setVgap(15);
            gridPane.setHgap(20);
        }

        {
            {
                scoresLabel.setTextFill(Color.WHITE);
                scoresLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
            }
            gridPane.add(scoresLabel, 0, 0);

            {
                scoreComboBox.setEditable(false);
                scoreComboBox.setVisibleRowCount(3);
                scoreComboBox.setPrefWidth(150);
                scoreComboBox.setPrefHeight(20);
                scoreComboBox.setPromptText("10");
                for (int i = 1; i <= 10; i++) {
                    scoreComboBox.getItems().add(i * 10);
                }
                scoreComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
                    @Override
                    public void changed(ObservableValue<? extends Integer> observableValue, Integer oldScore, Integer newScore) {
                        scoresPerRow = newScore;
                    }
                });
                scoreComboBox.setOnShowing(e -> {

                    ListView list = (ListView) ((ComboBoxListViewSkin) scoreComboBox.getSkin()).getPopupContent();
                    list.scrollTo(Math.max(0, scoreComboBox.getSelectionModel().getSelectedIndex()));
                });
            }
            gridPane.add(scoreComboBox, 1, 0);
        }

        {
            {
                colorLabel.setTextFill(Color.WHITE);
                colorLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
            }
            gridPane.add(colorLabel, 0, 1);

            {
                colorPicker.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        backGroundColor = colorPicker.getValue();
                        tetris.getGridPane().setBackground(new Background(new BackgroundFill(backGroundColor, CornerRadii.EMPTY, Insets.EMPTY)));
                    }
                });
                colorPicker.setPrefWidth(150);
                colorPicker.setPrefHeight(23);
                colorPicker.setValue(backGroundColor);
                colorPicker.getStyleClass().add("split-button");
                colorPicker.setStyle("-fx-color-label-visible: false; -fx-color-rect-width: 125px; -fx-color-rect-height: 23px");
            }
            gridPane.add(colorPicker, 1, 1);
        }

        {
            {
                levelLabel.setTextFill(Color.WHITE);
                levelLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
            }
            gridPane.add(levelLabel, 0, 2);

            {
                levelComboBox.setEditable(false);
                levelComboBox.setVisibleRowCount(3);
                levelComboBox.setPrefWidth(150);
                levelComboBox.setPrefHeight(20);
                levelComboBox.setPromptText("1");
                for (int i = 1; i <= 10; i++) {
                    levelComboBox.getItems().add(i);
                }
                levelComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
                    @Override
                    public void changed(ObservableValue<? extends Integer> observableValue, Integer oldLevel, Integer newLevel) {
                        startLevel = newLevel;
                        tetris.setLevel(startLevel);
                    }
                });
                levelComboBox.setOnShowing(e -> {

                    ListView list = (ListView) ((ComboBoxListViewSkin) levelComboBox.getSkin()).getPopupContent();
                    list.scrollTo(Math.max(0, levelComboBox.getSelectionModel().getSelectedIndex()));
                });
            }
            gridPane.add(levelComboBox, 1, 2);

            {
                clearAlert.getDialogPane().setStyle("-fx-padding:0.5em 0.5em 0.5em 0.5em; -fx-background-color:black;");
                {
                    clearAlert.getDialogPane().lookupButton(ButtonType.YES).setStyle("-fx-background-color:black; -fx-text-fill:white;" +
                            "-fx-font-weight: bold; -fx-effect: dropshadow( three-pass-box, FF66B2, 10.0, 0.0, 0.0, 0.0); -fx-cursor:hand;");
                    clearAlert.getDialogPane().lookupButton(ButtonType.NO).setStyle("-fx-background-color:black; -fx-text-fill:white;" +
                            "-fx-font-weight: bold; -fx-effect: dropshadow( three-pass-box, FF66B2, 10.0, 0.0, 0.0, 0.0); -fx-cursor:hand;");
                    clearAlert.getDialogPane().lookupButton(ButtonType.CANCEL).setStyle("-fx-background-color:black; -fx-text-fill:white;" +
                            "-fx-font-weight: bold; -fx-effect: dropshadow( three-pass-box, FF66B2, 10.0, 0.0, 0.0, 0.0); -fx-cursor:hand;");
                }

                {
                    Text text = new Text("\n  Are you sure?");
                    text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
                    text.setFill(Color.rgb(255, 102, 178));
                    clearAlert.getDialogPane().setContent(text);
                }

                {
                    ImageView icon = new ImageView("information.png");
                    icon.setFitHeight(35);
                    icon.setFitWidth(35);
                    clearAlert.getDialogPane().setGraphic(icon);
                }

                {
                    clearAlert.getDialogPane().setPrefSize(100, 120);
                    clearAlert.initStyle(StageStyle.TRANSPARENT);
                    clearAlert.setHeaderText(null);
                }
            }
        }

        {
            clear.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                    alertSound.stop();
                    alertSound.play();
                    clearAlert.showAndWait();
                    if (clearAlert.getResult() == ButtonType.YES) {
                        clearScores();
                    }
                }
            });
            clear.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
            clear.setStyle("-fx-background-color: FF66B2");
            clear.setTextFill(Color.BLACK);
            clear.setPrefWidth(130);
            clear.setPrefHeight(30);
        }
        gridPane.add(clear, 0, 9, 2, 4);

        {
            back.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
            back.setStyle("-fx-background-color: FF66B2");
            back.setTextFill(Color.BLACK);
            back.setPrefWidth(130);
            back.setPrefHeight(30);
            back.setDefaultButton(true);
        }
        gridPane.add(back, 0, 12, 2, 4);

        vBox.getChildren().add(gridPane);

        parent = vBox;
    }

    /**
     * This method reads the Specifications of file and loads it to fields.
     */
    public void load() {

        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(settingsFile)))) {

            scoresPerRow = scanner.nextInt();
            scoreComboBox.setPromptText(String.valueOf(scoresPerRow));
            scoreComboBox.getSelectionModel().select((scoresPerRow / 10) - 1);

            backGroundColor = Color.valueOf(scanner.next());
            colorPicker.setValue(backGroundColor);
            tetris.getGridPane().setBackground(new Background(new BackgroundFill(backGroundColor, CornerRadii.EMPTY, Insets.EMPTY)));
            tetris.getGameBoard().setBackgroundColor(backGroundColor);

            startLevel = scanner.nextInt();
            levelComboBox.setPromptText(String.valueOf(startLevel));
            levelComboBox.getSelectionModel().select(startLevel - 1);
            tetris.setLevel(startLevel);

        } catch (FileNotFoundException ignored) {
        }
    }

    /**
     * This method sorts the list of the top players
     * and if the number of top players becomes more than 10, it removes last player
     * and prints fields in the file.
     */
    public void save() {

        try (PrintWriter printWriter = new PrintWriter(new FileWriter(settingsFile))) {

            printWriter.println(scoresPerRow);
            printWriter.println(backGroundColor);
            printWriter.println(startLevel);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method calls {@link #scoresManager clearAllPlayers()} method and clears all players.
     */
    public void clearScores() {
        scoresManager.clearAllPlayers();
    }

    // Setters and Getters

    /**
     * Getter for start level
     *
     * @return the start level
     */
    public int getStartLevel() {
        return startLevel;
    }

    /**
     * Getter for parent
     *
     * @return parent as a root of the main scene
     */
    public Parent getParent() {
        return parent;
    }

    /**
     * Getter for back button
     *
     * @return the back button for OnAction handling
     */
    public Button getBack() {
        return back;
    }

    /**
     * Getter for the score per row
     *
     * @return score value per row
     */
    public int getScoresPerRow() {
        return scoresPerRow;
    }
}

class ScoresManager {

    private List<Player> topPlayers = new ArrayList<>();

    private Parent parent;

    private VBox vBox = new VBox();
    private GridPane gridPane = new GridPane();
    private Label topLabel = new Label("S C O R E    T A B L E");
    private Label backLabel = new Label();

    private File scoresFile = new File("scores.txt");

    {
        load();
        vBox.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));

        {
            topLabel.setAlignment(Pos.TOP_CENTER);
            topLabel.setPrefHeight(25);
            topLabel.setPrefWidth(GameBoard.ROW * GameBoard.CELL_SIZE + 20);
            topLabel.setTextFill(Color.BLACK);
            topLabel.setStyle("-fx-background-color: FF66B2");
            topLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.ITALIC, 20));
        }
        vBox.getChildren().add(topLabel);

        {
            gridPane.setVgap(3);
            gridPane.setHgap(3);
            gridPane.setAlignment(Pos.TOP_CENTER);
            gridPane.setPadding(new Insets(27, 20, 8, 20));

            for (int i = 0; i < 3; i++) {
                gridPane.getColumnConstraints().addAll(new ColumnConstraints(100));
            }
            for (int i = 0; i < 11; i++) {
                gridPane.getRowConstraints().add(new RowConstraints(26));
            }
        }

        {
            {
                Label rankLabel = new Label("Rank");
                rankLabel.setAlignment(Pos.CENTER);
                rankLabel.setTextFill(Color.BLACK);
                rankLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
                rankLabel.setStyle("-fx-background-color: FFCCE5");
                rankLabel.setPrefSize(100, 26);
                gridPane.add(rankLabel, 0, 0);

                Label nameLabel = new Label("Name");
                nameLabel.setAlignment(Pos.CENTER);
                nameLabel.setTextFill(Color.BLACK);
                nameLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
                nameLabel.setStyle("-fx-background-color: FF99CC");
                nameLabel.setPrefSize(100, 26);
                gridPane.add(nameLabel, 1, 0);

                Label scoreLabel = new Label("Score");
                scoreLabel.setAlignment(Pos.CENTER);
                scoreLabel.setTextFill(Color.BLACK);
                scoreLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
                scoreLabel.setStyle("-fx-background-color: FF66B2");
                scoreLabel.setPrefSize(100, 26);
                gridPane.add(scoreLabel, 2, 0);
            }

            for (int i = 1; i <= 10; i++) {

                Label rankLabel = new Label();
                rankLabel.setText(String.valueOf(i));
                rankLabel.setAlignment(Pos.CENTER);
                rankLabel.setTextFill(Color.BLACK);
                rankLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, 12));
                rankLabel.setStyle("-fx-background-color: FFCCE5");
                rankLabel.setPrefSize(100, 26);
                gridPane.add(rankLabel, 0, i);

                Label nameLabel = new Label();
                if (topPlayers.size() >= i) nameLabel.setText(topPlayers.get(i - 1).getName());
                nameLabel.setAlignment(Pos.CENTER);
                nameLabel.setTextFill(Color.BLACK);
                nameLabel.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 12));
                nameLabel.setStyle("-fx-background-color: FF99CC");
                nameLabel.setPrefSize(100, 26);
                gridPane.add(nameLabel, 1, i);

                Label scoreLabel = new Label();
                if (topPlayers.size() >= i) scoreLabel.setText(String.valueOf(topPlayers.get(i - 1).getScore()));
                scoreLabel.setAlignment(Pos.CENTER);
                scoreLabel.setTextFill(Color.BLACK);
                scoreLabel.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 12));
                scoreLabel.setStyle("-fx-background-color: FF66B2");
                scoreLabel.setPrefSize(100, 26);
                gridPane.add(scoreLabel, 2, i);
            }
        }
        vBox.getChildren().add(gridPane);

        {
            ImageView levelImage = new ImageView(
                    new Image(getClass().getResourceAsStream("back.png")));
            levelImage.setFitHeight(36.0);
            levelImage.setFitWidth(59.0);
            backLabel.setGraphic(levelImage);
        }
        vBox.getChildren().add(backLabel);

        parent = vBox;
    }

    /**
     * This method checks if new Player already exist or not & adds it to top players.
     *
     * @param newPlayer player that is playing
     */
    public void addPlayer(Player newPlayer) {

        if (newPlayer.getName() == null) {
            newPlayer.setName("Player" + (topPlayers.size() + 1));
        }

        for (Player topPlayer : topPlayers) {
            if (newPlayer.getName().equals(topPlayer.getName())) {
                topPlayer.setScore(Math.max(newPlayer.getScore(), topPlayer.getScore()));
                return;
            }
        }

        topPlayers.add(newPlayer);
    }

    /**
     * This method clears all players in the list of top players and the file.
     */
    public void clearAllPlayers() {
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(scoresFile, false), false);
            printWriter.flush();
            printWriter.close();
        } catch (Exception ignored) {
        }
        topPlayers.clear();
    }

    /**
     * This method reads the Specifications of file and loads it to fields.
     */
    public void load() {

        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(scoresFile)))) {
            while (scanner.hasNext()) {
                topPlayers.add(new Player(scanner.nextInt(), scanner.next()));
            }
        } catch (FileNotFoundException ignored) {
        }
    }

    /**
     * This method sorts the list of the top players
     * and if the number of top players becomes more than 10, it removes last player
     * and prints fields in the file.
     */
    public void save() {

        topPlayers.sort(new Comparator<Player>() {

            @Override
            public int compare(Player player1, Player player2) {
                return Integer.compare(player2.getScore(), player1.getScore());
            }
        });
        if (topPlayers.size() == 11) topPlayers.remove(10);

        try (PrintWriter printWriter = new PrintWriter(new FileWriter(scoresFile))) {
            for (Player topPlayer : topPlayers) {

                printWriter.println(topPlayer.getScore() + " " + topPlayer.getName());
            }
        } catch (IOException ignored) {
        }
    }

    // Getters and Setters

    /**
     * Getter for parent
     *
     * @return parent as a root of the main scene
     */
    public Parent getParent() {
        return parent;
    }

    /**
     * Getter for back label
     *
     * @return the back label for OnAction handling
     */
    public Label getBackLabel() {
        return backLabel;
    }
}

/**
 * The Class <b>TRectangle</b> builds requirement rectangle for the brick
 *
 * @author Farkhondeh Arzi
 * @author Mahdieh ShahHosseinian
 */

class TRectangle {

    private Rectangle rectangle;

    /**
     * This field specifying the request for reflection effect
     * used for the next brick grid on the right side of the window
     */
    private boolean requestReflection = false;

    /**
     * This Constructor makes a new rectangles with some specific effects
     *
     * @param color the color of rectangle
     */
    public TRectangle(Color color) {

        rectangle = new Rectangle(GameBoard.CELL_SIZE, GameBoard.CELL_SIZE, color);
        rectangle.setStrokeType(StrokeType.INSIDE);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(0.2);
    }

    /**
     * This method sets the required effects into rectangles
     */
    public void setEffect() {

        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetY(0.5);
        dropShadow.setOffsetX(0.5);
        dropShadow.setColor(Color.TRANSPARENT);

        if (requestReflection) {
            Reflection reflection = new Reflection();
            dropShadow.setInput(reflection);
        }

        rectangle.setEffect(dropShadow);
    }

    // Setters and Getters

    /**
     * @param requestReflection the new value of the requestReflection field
     */
    public void requestReflection(boolean requestReflection) {
        this.requestReflection = requestReflection;
    }

    /**
     * Getter for rectangles
     *
     * @return the rectangle
     */
    public Rectangle getRectangle() {
        return rectangle;
    }
}

