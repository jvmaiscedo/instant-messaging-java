/* ***************************************************************
 * Autor............: Joao Victor Gomes Macedo
 * Matricula........: 202210166
 * Inicio...........: 20/11/2024
 * Ultima alteracao.: 30/11/2024
 * Nome.............: ClienteTCP
 * Funcao...........: Modela e gerencia a conexao TCP.
 *************************************************************** */
package model;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClienteTCP {
  String nome;
  String servidorIP;
  int porta;
  Socket socket;
  APDU apdu;
  ObjectInputStream entrada;
  ObjectOutputStream saida;

  /* ***************************************************************
   * Metodo: ClienteTCP
   * Funcao: Construtor da classe.
   * Parametros:
   *              String - servidorIP
   *              String - nomeCliente
   * Retorno:  Sem retorno
   *************************************************************** */
  public ClienteTCP(String servidorIP, String nomeCliente) throws IOException {//Essa excecao eh lancada para ser tratada no controller.
    this.servidorIP = servidorIP;
    this.nome = nomeCliente;
    this.porta = 6789;
    this.apdu = new APDU();
    try {
      socket = new Socket(servidorIP, porta);
      saida = new ObjectOutputStream(socket.getOutputStream());
      entrada = new ObjectInputStream(socket.getInputStream());
    } catch (UnknownHostException e) {
      System.out.println("TCP CATCH 1");
      e.printStackTrace();
    }
  }

  /* ***************************************************************
   * Metodo: entrarNoGrupo
   * Funcao: enviar uma requisicao de entrada em um grupo.
   * Parametros:
   *             String - nomeGrupo
   * Retorno:  Sem retorno
   *************************************************************** */
  public void entrarNoGrupo(String nomeGrupo) {
    try {
      saida.writeObject(apdu.join(this.nome, nomeGrupo));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /* ***************************************************************
   * Metodo: sairDoGrupo
   * Funcao: enviar uma requisicao de saida do grupo.
   * Parametros:
   *             String - nomeGrupo
   * Retorno:  Sem retorno
   *************************************************************** */
  public void sairDoGrupo(String nomeGrupo) {
    try {
      saida.writeObject(apdu.leave(this.nome, nomeGrupo));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /* ***************************************************************
   * Metodo: closeConnection
   * Funcao: encerrar a conexao com o servidor.
   * Parametros: Sem parametros
   * Retorno:  Sem retorno
   *************************************************************** */
  public void closeConnection() {
    try {
      this.socket.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
