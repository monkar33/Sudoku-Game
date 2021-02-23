package view;

import javafx.scene.control.Alert;

public class Information {

    public static void showInfo(final String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("\n" +
                text);
        alert.showAndWait();
    }
}
