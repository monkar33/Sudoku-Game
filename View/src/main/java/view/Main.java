package view;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.ResourceBundle;


public class Main extends Application {

    private static final Logger logger = LoggerFactory.getLogger(Main.class.getName());
    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Locale locale = new Locale("en");
        ResourceBundle bundle = ResourceBundle.getBundle("Language", locale);
        Parent root = FXMLLoader.load(getClass().getResource("startView.fxml"),bundle);
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

    }


    public static void main(String[] args) {
        logger.info("Application Start");
        launch(args);
    }
}