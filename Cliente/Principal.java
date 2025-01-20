/* ***************************************************************
* Autor............: Joao Victor Gomes Macedo
* Matricula........: 202210166
* Inicio...........: 20/11/2024
* Ultima alteracao.: 30/11/2024
* Nome.............: Principal
* Funcao...........: Este codigo tem a funcao de fazer com que
                    o GUI feito no FXMLDocument seja executado.
*************************************************************** */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import controller.ControllerTelaInicial;
import controller.ControllerEntrarAdicionarGrupo;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Principal extends Application {
  @Override
  public void start(Stage stage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("view/telaInicial.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setResizable(false);
    stage.setTitle("HowUDoing");
    stage.getIcons().add(new Image("img/hudIcon.png"));
    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
      @Override
      public void handle(WindowEvent event) {
        Platform.exit();
        if (ControllerTelaInicial.cliente != null && ControllerTelaInicial.cliente.getClienteTCP() != null) {
          ControllerTelaInicial.cliente.getClienteTCP().closeConnection();
        }
        System.exit(0);
      }
    });
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
