/* ***************************************************************
 * Autor............: Joao Victor Gomes Macedo
 * Matricula........: 202210166
 * Inicio...........: 20/11/2024
 * Ultima alteracao.: 30/11/2024
 * Nome.............: ClienteUDP
 * Funcao...........: Modela e gerencia o envio e recebimento
 *                    de informacoes via UDP.
 *************************************************************** */
package model;

import controller.ControllerConversa;
import controller.ControllerTelaInicial;
import javafx.application.Platform;

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Scanner;

public class ClienteUDP {
  DatagramSocket socket;
  String servidorIP;
  int servidorPorta;
  String nomeCliente;
  APDU apdu;

  /* ***************************************************************
   * Metodo: ClienteUDP
   * Funcao: Construtor da classe.
   * Parametros:
   *              String - servidorIP
   *              String - nomeCliente
   * Retorno:  Sem retorno
   *************************************************************** */
  public ClienteUDP(String servidorIP, String nomeCliente) {
    this.servidorIP = servidorIP;
    this.nomeCliente = nomeCliente;
    this.servidorPorta = 6790;
    this.apdu = new APDU();
    try {
      socket = new DatagramSocket(6791);
    } catch (SocketException e) {
      throw new RuntimeException(e);
    }
  }

  /* ***************************************************************
   * Metodo: enviarMensagem
   * Funcao: enviar uma mensagem via UDP.
   * Parametros:
   *             String - mensagem
   *             String - grupo
   * Retorno:  Sem retorno
   *************************************************************** */
  public void enviarMensagem(String mensagem, String grupo) {
    try {
      InetAddress enderecoIPServidor = InetAddress.getByName(servidorIP);
      byte[] saida;
      saida = apdu.send(mensagem, grupo);
      DatagramPacket pacoteEnviado = new DatagramPacket(saida, saida.length,
        enderecoIPServidor, servidorPorta);
      socket.send(pacoteEnviado);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /* ***************************************************************
   * Metodo: receberMensagem
   * Funcao: Receber as mensagens via UDP.
   * Parametros:
   *              ControllerConversa - controllerConversa
   * Retorno:  Sem retorno
   *************************************************************** */
  public void receberMensagem(ControllerConversa controllerConversa) {
    while (true) {
      byte[] dadosRecebidos = new byte[1024];
      try {
        DatagramPacket pacoteRecebido = new DatagramPacket(dadosRecebidos, dadosRecebidos.length);
        socket.receive(pacoteRecebido);
        String recebida = new String(pacoteRecebido.getData(), 0, pacoteRecebido.getLength(), StandardCharsets.UTF_8);//garantindo o mesmo padrao de codificacao dos caracteres
        processarMensagem(controllerConversa, recebida);//metodo para processa a apdu recebida.
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /* ***************************************************************
   * Metodo: processarMensagem
   * Funcao: Processa a mensagem recebida atualizando as estruturas de
   *         dados.
   * Parametros:
   *              ControllerConversa - controllerConversa
   *               String - mensagem
   * Retorno:  Sem retorno
   *************************************************************** */
  public void processarMensagem(ControllerConversa controllerConversa, String mensagem) {
    //apdu sera nomeRemetente*grupo*mensagem
    String[] dados = mensagem.split("\\*");//separando as partes da apdu
    Mensagem m = new Mensagem(dados[0], dados[2], LocalDateTime.now(), false);//criando uma nova mensagem
    ControllerTelaInicial.grupos.get(dados[1]).add(m);//adicionando a mensagem ao conjunto de mensagens do grupo
    ControllerConversa.getInstance();
    Platform.runLater(() -> controllerConversa.exibirMensagens());//incluindo o elemento visual da mensagem
  }

}
