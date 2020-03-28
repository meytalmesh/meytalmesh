package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

/**
 * this class is the main of our Search Engine!!!
 * this class extend Application, ->> start and open the app
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getClassLoader().getResource("View.fxml"));
            primaryStage.setTitle("Search Engine");
            primaryStage.setScene(new Scene(root, 638, 575));
            primaryStage.show();

        }
        catch (Exception e){

        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
