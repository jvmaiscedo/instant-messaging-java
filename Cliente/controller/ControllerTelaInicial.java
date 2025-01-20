/* ***************************************************************
 * Autor............: Joao Victor Gomes Macedo
 * Matricula........: 202210166
 * Inicio...........: 20/11/2024
 * Ultima alteracao.: 30/11/2024
 * Nome.............: ControllerTelaInicial
 * Funcao...........: Controlar a tela inicial e gerar as instancias
 *                    do cliente TCP e UDP
 *************************************************************** */
package controller;

import model.Cliente;
import model.Mensagem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import controller.ControllerTelaPrincipal;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerTelaInicial implements Initializable {
  @FXML
  private Button confirmarServidorIP;
  @FXML
  private TextField nomeCliente;
  @FXML
  private TextField servidorIP;
  @FXML
  private Label aviso;

  public static Cliente cliente;
  public static Map<String, List<Mensagem>> grupos = new HashMap<>();

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    aviso.setVisible(false);
    servidorIP.textProperty().addListener(createTextChangeListener(aviso));
    nomeCliente.textProperty().addListener(createTextChangeListener(aviso));
    confirmarServidorIP.setOnMouseClicked(this::confirmarInformacoes);
  }

  /* ***************************************************************
   * Metodo: confirmarInformacoes
   * Funcao: confirma o nome e endereco do servidor, e instancia os
   *         clientes TCP e UDP.
   * Parametros:
   *            ActionEvent - event
   * Retorno:  Sem retorno
   *************************************************************** */
  public void confirmarInformacoes(MouseEvent mouseEvent) {
    try {
      if (servidorIP.getText().isEmpty() || nomeCliente.getText().isEmpty()) {
        aviso.setText("Algum campo está vazio!");
        aviso.setVisible(true);
      } else {
        if (validarIP(servidorIP.getText())) {
          try {
            cliente = new Cliente(servidorIP.getText(), nomeCliente.getText());
            new Thread(() -> ControllerTelaInicial.cliente.getClienteUDP().receberMensagem(ControllerConversa.getInstance())).start();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/telaPrincipal.fxml"));
            Parent mainScreen = fxmlLoader.load();
            Stage stage = (Stage) confirmarServidorIP.getScene().getWindow();
            stage.setScene(new Scene(mainScreen));
            stage.show();
          } catch (IOException e) {//Tratando a excecao do Cliente TCP quando o endereco do servidor é errado ou o servidor nao esta ativo.
            aviso.setText("Falha na conexão com o servidor. " +
              "\nVerifique o IP e tente novamente.");
            aviso.setVisible(true);
          }
        } else {
          aviso.setText("IP inválido");
          aviso.setVisible(true);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      aviso.setText("Erro inesperado. Tente novamente.");
      aviso.setVisible(true);
    }
  }

  /* ***************************************************************
   * Metodo: validarIP
   * Funcao: realiza a validacao do endereco IP informado
   * Parametros:
   *            String - ip
   * Retorno:  Sem retorno
   *************************************************************** */
  public boolean validarIP(String ip) {
    String regex = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(ip);
    return matcher.matches();
  }

  /* ***************************************************************
   * Metodo: createTextChangeListener
   * Funcao: Adiciona um ouvinte de mudancas na caixa de texto
   * Parametros:
   *            Label - aviso
   * Retorno:
   *           ChangeListener<String>
   *************************************************************** */
  private ChangeListener<String> createTextChangeListener(Label aviso) {
    return (observable, oldValue, newValue) -> {
      if (newValue.isEmpty()) {
        aviso.setText("Algum campo está vazio!");
        aviso.setVisible(true);
      } else {
        aviso.setText("");
        aviso.setVisible(false);
      }
    };
  }


}
