/* ***************************************************************
 * Autor............: Joao Victor Gomes Macedo
 * Matricula........: 202210166
 * Inicio...........: 20/11/2024
 * Ultima alteracao.: 01/11/2024
 * Nome.............: ControllerConversa
 * Funcao...........: Controla a tela que exibe a conversa
 *************************************************************** */
package controller;

import javafx.application.Platform;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import model.Mensagem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ControllerConversa implements Initializable {
  @FXML
  private ScrollPane rolarMensagens;
  static ScrollPane rolarMensagensStatic;
  @FXML
  VBox mensagens;
  static VBox vBoxMensagens;
  @FXML
  TextArea mensagemTexto;
  @FXML
  Label nomeGrupo;

  public static String nomeDoGrupo;//Variavel usada para setar o nome do grupo na janela.
  private static ControllerConversa instance;//Singleton para conseguir atualizar corretamente a janela.

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    vBoxMensagens = mensagens;
    rolarMensagensStatic = rolarMensagens;
    vBoxMensagens.heightProperty().addListener((observable, oldValue, newValue) -> {
      rolarMensagensStatic.setVvalue(1.0);
    });
    nomeGrupo.setText(nomeDoGrupo);
    exibirMensagens();
    mensagemTexto.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        if (event.isShiftDown()) {
          int caretPosition = mensagemTexto.getCaretPosition();
          mensagemTexto.insertText(caretPosition, "\n");
          event.consume();
        } else {
          enviarMensagem(new ActionEvent());
          event.consume();
        }
      }
    });

  }

  /* ***************************************************************
   * Metodo: enviarMensagem
   * Funcao: enviar uma mensagem via UDP quando o evento for disparado
   * Parametros:
   *              ActionEvent - event
   * Retorno:  Sem retorno
   *************************************************************** */
  @FXML
  public void enviarMensagem(ActionEvent event) {
    String mensagem = mensagemTexto.getText();
    if (!mensagem.isEmpty()) {
      Mensagem m = new Mensagem(ControllerTelaInicial.cliente.getNome(), mensagem, LocalDateTime.now(), true);
      ControllerTelaInicial.grupos.get(nomeDoGrupo).add(m);
      adicionarMensagem(m);
      ControllerTelaInicial.cliente.getClienteUDP().enviarMensagem(mensagem, nomeDoGrupo);
      mensagemTexto.clear();
    }
  }

  /* ***************************************************************
   * Metodo: sairDoGrupo
   * Funcao: sair do grupo ao clicar no botao de saida
   * Parametros:
   *              ActionEvent - event
   * Retorno:  Sem retorno
   *************************************************************** */
  @FXML
  public void sairDoGrupo(ActionEvent event) {
    ControllerTelaInicial.cliente.getClienteTCP().sairDoGrupo(nomeDoGrupo);
    ControllerTelaInicial.grupos.remove(nomeDoGrupo);
    try {//quando o usuario sai do grupo, retorna a tela anterior
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/telaPrincipal.fxml"));
      Parent mainScreen = fxmlLoader.load();
      Stage stage = (Stage) mensagens.getScene().getWindow();
      stage.setScene(new Scene(mainScreen));
      stage.show();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

  /* ***************************************************************
   * Metodo: getInstance
   * Funcao: metodo para estabelecimento do Singleton
   * Parametros: Sem parametros
   * Retorno:  Sem retorno
   *************************************************************** */
  public static ControllerConversa getInstance() {
    if (instance == null) {
      instance = new ControllerConversa();
    }
    return instance;
  }

  /* ***************************************************************
   * Metodo: adicionarMensagem
   * Funcao: Adiciona uma mensagem na tela de mensagens
   * Parametros:
   *            Mensagem - mensagem
   * Retorno:  Sem retorno
   *************************************************************** */
  public void adicionarMensagem(Mensagem mensagem) {
    VBox vbox = new VBox();
    vbox.setSpacing(2);
    Label nomeRemetente = new Label(mensagem.isEnviei() ? "Você" : mensagem.getNomeRemetente());
    nomeRemetente.setStyle("-fx-font-weight: bold; -fx-text-fill: #555555;");
    Text textoMensagem = new Text(mensagem.getTexto());
    textoMensagem.setWrappingWidth(300);
    textoMensagem.setStyle("-fx-font-size: 14px;");
    TextFlow mensagemFlow = new TextFlow(textoMensagem);
    mensagemFlow.setStyle("-fx-background-color: #e6e6e6; -fx-padding: 10; -fx-border-radius: 10; -fx-background-radius: 10;");
    mensagemFlow.setMaxWidth(300);
    if (mensagem.isEnviei()) {
      mensagemFlow.setStyle("-fx-background-color: #62c0e8; -fx-padding: 10; -fx-border-radius: 10; -fx-background-radius: 10;");
      vbox.setAlignment(Pos.CENTER_RIGHT);
    } else {
      vbox.setAlignment(Pos.CENTER_LEFT);
    }
    Label data = new Label(mensagem.getData().format(DateTimeFormatter.ofPattern("HH:mm")));
    data.setStyle("-fx-font-size: 10px; -fx-text-fill: #888888;");
    vbox.getChildren().addAll(nomeRemetente, mensagemFlow, data);
    HBox hbox = new HBox();
    hbox.setPadding(new Insets(5, 10, 5, 10));
    hbox.getChildren().add(vbox);
    hbox.setAlignment(mensagem.isEnviei() ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
    vBoxMensagens.getChildren().add(hbox);
  }

  /* ***************************************************************
   * Metodo: exibirMensagens
   * Funcao: Itera sobre todas as  mensagens de um grupo, exibindo-as
   *         na tela.
   * Parametros: Sem parametros
   * Retorno: Sem retorno
   *************************************************************** */
  public void exibirMensagens() {
    vBoxMensagens.getChildren().clear();
    if (!ControllerTelaInicial.grupos.get(nomeDoGrupo).isEmpty()) {
      for (Mensagem m : ControllerTelaInicial.grupos.get(nomeDoGrupo)) {
        adicionarMensagem(m);
      }
    }
  }
}
