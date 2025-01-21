/* ***************************************************************
 * Autor............: Joao Victor Gomes Macedo
 * Matricula........: 202210166
 * Inicio...........: 20/11/2024
 * Ultima alteracao.: 30/11/2024
 * Nome.............: ControllerEntrarAdicionarGrupo
 * Funcao...........: Controlar a tela de adicao/inclusao em novos
 *                    grupos
 *************************************************************** */
package controller;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import model.Mensagem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import controller.ControllerConversa;
import model.Mensagem;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerEntrarAdicionarGrupo implements Initializable {
  @FXML
  private TextField nomeGrupo;
  @FXML
  private Label aviso;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    nomeGrupo.textProperty().addListener(createTextChangeListener(aviso));
  }

  /*
   * ***************************************************************
   * Metodo: confirmarGrupo
   * Funcao: confirma o grupo e realiza a chamada do TCP.
   * Parametros:
   * ActionEvent - event
   * Retorno: Sem retorno
   */
  @FXML
  private void confirmarGrupo(ActionEvent event) {
    if (nomeGrupo.getText().isEmpty()) {
      aviso.setText("O campo está vazio!");
    } else {
      ControllerTelaInicial.cliente.getClienteTCP().entrarNoGrupo(nomeGrupo.getText().trim());// solicita a entrada no
                                                                                              // grupo via TCP
      ControllerTelaInicial.grupos.putIfAbsent(nomeGrupo.getText().trim(), new ArrayList<Mensagem>());
      ControllerConversa.nomeDoGrupo = nomeGrupo.getText().trim();// seta o nome do grupo na tela de conversa
      ControllerTelaPrincipal.getInstance().atualizarListaDeGrupos();// atualiza a lista de grupos
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/conversa.fxml"));
      try {// carrega a tela de conversa do grupo
        Parent mainScreen = fxmlLoader.load();
        ControllerTelaPrincipal.conversaEAddGrupoStatic.getChildren().clear();
        ControllerTelaPrincipal.conversaEAddGrupoStatic.getChildren().addAll(mainScreen);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  /*
   * ***************************************************************
   * Metodo: createTextChangeListener
   * Funcao: Adiciona um ouvinte de mudancas na caixa de texto
   * Parametros:
   * Label - aviso
   * Retorno:
   * ChangeListener<String>
   */
  private ChangeListener<String> createTextChangeListener(Label aviso) {
    return (observable, oldValue, newValue) -> {
      if (newValue.isEmpty()) {
        aviso.setText("O campo está vazio!");
        aviso.setVisible(true);
      } else {
        aviso.setText("");
        aviso.setVisible(false);
      }
    };
  }

}
