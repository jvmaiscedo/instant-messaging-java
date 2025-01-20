/* ***************************************************************
 * Autor............: Joao Victor Gomes Macedo
 * Matricula........: 202210166
 * Inicio...........: 20/11/2024
 * Ultima alteracao.: 30/11/2024
 * Nome.............: ControllerTelaPrincipal
 * Funcao...........: Controlar a tela principal de navegacao entre
 *                    os grupos
 *************************************************************** */
package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import controller.ControllerEntrarAdicionarGrupo;
import controller.ControllerConversa;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerTelaPrincipal implements Initializable {
  @FXML
  private Pane conversaEAddGrupo;
  public static Pane conversaEAddGrupoStatic;
  @FXML
  private VBox listaGrupos;
  private static VBox listaGruposStatic;
  private static ControllerTelaPrincipal instance;//Singleton para conseguir atualizar corretamente a janela.


  @Override
  public void initialize(URL url, ResourceBundle rb) {
    conversaEAddGrupoStatic = conversaEAddGrupo;
    listaGruposStatic = listaGrupos;
    atualizarListaDeGrupos();
  }

  /* ***************************************************************
   * Metodo: entrarNoGrupo
   * Funcao: Entra no grupo clicado na lista, alterando a tela e
   *        carregando os elementos da conversa na tela.
   * Parametros:
   *            ActionEvent - event
   * Retorno:  Sem retorno
   *************************************************************** */
  @FXML
  public void entrarNoGrupo(ActionEvent event) {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/telaEntrarAdicionarGrupo.fxml"));
    try {
      Parent mainScreen = fxmlLoader.load();
      conversaEAddGrupo.getChildren().addAll(mainScreen);
    } catch (IOException e) {
      throw new RuntimeException("Erro ao tentar entrar no grupo", e.getCause());
    }
  }
  /* ***************************************************************
   * Metodo: getInstance
   * Funcao: metodo para estabelecimento do Singleton
   * Parametros: Sem parametros
   * Retorno:  Sem retorno
   *************************************************************** */
  public static ControllerTelaPrincipal getInstance() {
    if (instance == null) {
      instance = new ControllerTelaPrincipal();
    }
    return instance;
  }
  /* ***************************************************************
   * Metodo: criarGrupo
   * Funcao: Cria a box que representa o grupo na lista e adiciona
   *         a VBox.
   * Parametros:
   *            String - nomeGrupo
   * Retorno:  Sem retorno
   *************************************************************** */
  private HBox criarGrupo(String nomeGrupo) {
    HBox hbox = new HBox();
    hbox.setSpacing(10);
    hbox.setStyle("-fx-padding: 10; -fx-border-color: gray; -fx-border-width: 1; -fx-alignment: center; "
      + "-fx-background-color: #40b7e7;");
    hbox.setPrefHeight(40);
    Label label = new Label(nomeGrupo);
    label.setStyle("-fx-font-size: 14px; -fx-text-alignment: center; -fx-cursor: hand; -fx-text-fill: white");
    label.setMaxWidth(Double.MAX_VALUE);
    label.setAlignment(javafx.geometry.Pos.CENTER);
    hbox.getChildren().add(label);
    HBox.setHgrow(label, javafx.scene.layout.Priority.ALWAYS);
    hbox.setOnMouseClicked(event -> {
      mudarTela(nomeGrupo);
    });
    return hbox;
  }

  /* ***************************************************************
   * Metodo: mudarTela
   * Funcao: Carregar a conversa assim que houver um clique sobre
   *         o grupo na lista de grupos.
   * Parametros:
   *            String - nomeGrupo
   * Retorno:  Sem retorno
   *************************************************************** */
  public void mudarTela(String nomeGrupo) {
    ControllerConversa.nomeDoGrupo = nomeGrupo;
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/conversa.fxml"));
    try {
      Parent mainScreen = fxmlLoader.load();
      conversaEAddGrupoStatic.getChildren().clear();
      conversaEAddGrupoStatic.getChildren().addAll(mainScreen);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /* ***************************************************************
   * Metodo: atualizarListaDeGrupos
   * Funcao: Atualizar a lista de grupos exibida na tela.
   * Parametros: Sem parametros
   * Retorno:  Sem retorno
   *************************************************************** */
  public void atualizarListaDeGrupos() {
    if (!ControllerTelaInicial.grupos.keySet().isEmpty()) {
      listaGruposStatic.getChildren().clear();
      for (String nomeGrupo : ControllerTelaInicial.grupos.keySet()) {
        listaGruposStatic.getChildren().add(criarGrupo(nomeGrupo));
      }
    }
  }
}
