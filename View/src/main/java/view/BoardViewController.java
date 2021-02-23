package view;

import dao.Dao;
import dao.DaoException;
import dao.SudokuBoardDaoFactory;
import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import model.BacktrackingSudokuSolver;
import model.SudokuBoard;
import model.SudokuSolver;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class BoardViewController {

    private final int colLenght = 9;

    private SudokuBoard board;

    @FXML
    private GridPane sudokuBoardGrid;

    @FXML
    private Button showButton;

    @FXML
    private Button checkButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button loadButton;

    private final ResourceBundle bundle = StartViewController.getBundle();

    private static final Logger logger = LoggerFactory.getLogger(BoardViewController.class);

    @FXML
    public void showBoardButtonClicked(Event e) {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        this.board = new SudokuBoard(solver);
        board.solveGame();
        DifficultyLevel d = new DifficultyLevel(StartViewController.getLevel());
        d.removeFildsFromBoard(board);

        checkButton.setDisable(false);
        saveButton.setDisable(false);
        loadButton.setDisable(true);


        showBoard();

        showButton.setDisable(true);
        logger.info("Board displayed.");
    }

    @FXML
    public void buttonCheckClicked(Event e) {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard b = new SudokuBoard(solver);


        try {
            for (int i = 0; i < colLenght; i++) {
                for (int j = 0; j < colLenght; j++) {
                    String value = ((TextField) sudokuBoardGrid
                            .getChildren().get(i * colLenght + j)).getText();
                    b.set(i, j, Integer.parseInt(value));
                }
            }

            if (b.checkBoard()) {
                Information.showInfo(bundle.getString("winAlert"));
                logger.info("Wrong sudoku solution.");

            } else {
                Information.showInfo(bundle.getString("loseAlert"));
                logger.info("Correct sudoku solution.");
            }

        } catch (NumberFormatException n) {
            logger.warn("Empty field found.");
            Information.showInfo(bundle.getString("emptyAlert"));
        }

    }

    @FXML
    public void saveButtonClicked(Event e) {
        Stage chooseStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        File selectedFile = fileChooser.showOpenDialog(chooseStage);
        logger.info("FileChooser open.");
        try {
            String filename = selectedFile.getAbsolutePath();
            SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
            Dao<SudokuBoard> sudokuBoardDao;
            sudokuBoardDao = factory.getFileDao(filename);
            sudokuBoardDao.write(board);
        } catch (NullPointerException | DaoException d) {
            logger.warn("Writing to file failed.");
            Information.showInfo(bundle.getString("nofile"));
        }
    }

    @FXML
    public void loadButtonClicked(Event e) {
        Stage chooseStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(bundle.getString("fileChoser"));
        try {
            File selectedFile = fileChooser.showOpenDialog(chooseStage);
            logger.info("FileChooser open.");
            String filename = selectedFile.getAbsolutePath();

            SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
            Dao<SudokuBoard> sudokuBoardDao;
            sudokuBoardDao = factory.getFileDao(filename);
            this.board = sudokuBoardDao.read();

            showBoard();

            logger.info("Board displayed.");
            checkButton.setDisable(false);
            showButton.setDisable(true);
            loadButton.setDisable(true);
            saveButton.setDisable(false);
        } catch (NullPointerException | DaoException d) {
            logger.warn("Reading from file failed.");
            Information.showInfo(bundle.getString("nofile"));
        }
    }

    public void showBoard() {
        StringConverter converter = new IntegerStringConverter();
        try {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {

                    TextField textField = (TextField)sudokuBoardGrid.getChildren().get(i*9+j);
                    textField.setMinSize(50, 50);
                    textField.setFont(Font.font(20));


                    UnaryOperator<TextFormatter.Change> filter = change -> {
                        String text = change.getControlNewText();
                        if (change.isAdded()){
                            if (text.matches("[1-9]")) {
                                return change;
                            }
                        }
                        if (change.isReplaced()){
                            if (text.matches("[1-9]")) {
                                return change;
                            }
                        }
                        if (change.isDeleted()) {
                            if (change.getControlNewText().isEmpty()) {
                                return change;
                            }
                        }
                        return null;
                    };
                    TextFormatter<String> textFormatter1 = new TextFormatter<>(filter);
                    textField.setTextFormatter(textFormatter1);
                    try {
                        JavaBeanIntegerPropertyBuilder builder = JavaBeanIntegerPropertyBuilder.create();
                        JavaBeanIntegerProperty integerProperty = builder.bean(board.getBoard()
                                .get(i*colLenght + j)).name("value").build();

                        textField.textProperty().bindBidirectional(integerProperty, converter);

                    } catch (NoSuchMethodException ex) {
                        logger.warn("Binding fail.");
                    }

                    if (board.get(i, j) != 0) {
                        textField.setDisable(true);
                        textField.setText(String.valueOf(board.get(i, j)));
                    }
                    if (!board.getBoard().get(i * colLenght + j).isFilled()) {
                        textField.setDisable(false);

                    }
                }
            }

        } catch (NullPointerException e2) {
            logger.warn("Show Board fail.");
        }

    }




}
