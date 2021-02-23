package view;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import static view.Level.Medium;

public class StartViewController implements Initializable {


    private static Level level;

    private static Locale locale = new Locale("en");

    private static ResourceBundle bundle = ResourceBundle.getBundle("Language", locale);

    private static final Logger logger = LoggerFactory.getLogger(StartViewController.class);

    @FXML
    private ComboBox comboBoxLevel;

    @FXML
    private ComboBox comboBoxLanguage;


    public static Level getLevel() {
        return level;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        comboBoxLanguage.getItems().clear();
        comboBoxLevel.getItems().clear();

        comboBoxLevel.getItems().add(bundle.getString("lvlEasy"));
        comboBoxLevel.getItems().add(bundle.getString("lvlMedium"));
        comboBoxLevel.getItems().add(bundle.getString("lvlHard"));

        comboBoxLanguage.getItems().add(bundle.getString("pl"));
        comboBoxLanguage.getItems().add(bundle.getString("en"));

    }

    @FXML
    public void buttonStartGameClicked(Event e) {
        System.out.println("clicked.");
        try {
            String value = comboBoxLevel.getSelectionModel().getSelectedItem().toString();
            this.level = Level.valueOf(value);
            logger.warn("Level selected.");
        } catch (NullPointerException e2) {
            this.level = Medium;
            logger.warn("Level not selected. ");
        }
        try {
            Parent root = FXMLLoader.load(getClass().getResource("boardView.fxml"),bundle);
            Main.getStage().setScene(new Scene(root, 900, 484));
            BoardViewController view = new BoardViewController();
            Main.getStage().show();
            logger.info("Game started.");


        } catch (IOException er) {
           logger.warn("Failed attempt to read fxml file");
        }

    }

    @FXML
    public void buttonReloadClicked(Event e) {
        try {
            String value = comboBoxLanguage.getSelectionModel().getSelectedItem().toString();
            this.locale = new Locale(Language.valueOf(value).getValue());
            this.bundle = ResourceBundle.getBundle("Language", locale);

            try {
                Parent root = FXMLLoader.load(getClass().getResource("startView.fxml"),bundle);
                Main.getStage().setScene(new Scene(root, 600, 400));
                Main.getStage().show();
                logger.info("Language changed.");
            } catch (IOException ex) {
                logger.warn("Failed attempt to read fxml file");
            }

        } catch (NullPointerException e2) {
            logger.warn("Language not selected.");
        }

    }

    @FXML
    public void buttonAuthorsClicked(Event e) {
        ResourceBundle authors = ResourceBundle.getBundle("view.Authors",locale);

        Information.showInfo(authors.getString("1") + "\n" +
                authors.getString("a1"));
        logger.info("Authors shown");

    }

    public Locale getLocale() {
        return locale;
    }

    public static ResourceBundle getBundle() {
        return bundle;
    }
}
