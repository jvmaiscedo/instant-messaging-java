/* ***************************************************************
 * Autor............: Joao Victor Gomes Macedo
 * Matricula........: 202210166
 * Inicio...........: 20/11/2024
 * Ultima alteracao.: 01/11/2024
 * Nome.............: ServidorUDP
 * Funcao...........: Modela e gerencia o fluxo de dados
 *                    UDP do servidor.
 *************************************************************** */
package model;


import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class ServidorUDP {
  DatagramSocket socket;
  int portaServidorUDP = 6790;//porta do Servidor UDP
  int portaClienteUDP = 6791;//porta do cliente UDP

  /* ***************************************************************
   * Metodo: ServidorUDP
   * Funcao: Construtor da classe.
   * Parametros: Sem Parametros
   * Retorno:  Sem retorno
   *************************************************************** */
  public ServidorUDP() {
    try {
      socket = new DatagramSocket(portaServidorUDP);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /* ***************************************************************
   * Metodo: stayAlert
   * Funcao: Manter o servidor escutando novos pacotes
   * Parametros: Sem Parametros
   * Retorno:  Sem retorno
   *************************************************************** */
  public void stayAlert() {
    byte[] buffer = new byte[1024];
    while (true) {
      DatagramPacket pacote = new DatagramPacket(buffer, buffer.length);
      try {
        socket.receive(pacote);
        new Thread(() -> handleMessage(pacote)).start();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  /* ***************************************************************
   * Metodo: handleMessage
   * Funcao: Tratar o pacote recebido e realizar as acoes necessarias
   * Parametros:
   *            DatagramPacket - pacote
   * Retorno:  Sem retorno
   *************************************************************** */
  public synchronized void handleMessage(DatagramPacket pacote) {
    String[] partes = new String(pacote.getData(), 0, pacote.getLength(), StandardCharsets.UTF_8).split("\\*");//garantindo o padrao UTF_8
    String mensagem = partes[1];
    String nomeGrupo = partes[2];
    System.out.println("Requisição de envio de mensagem" +
      "\nCliente: " + GruposDoServidor.getGrupos().get(nomeGrupo).getUsuario(pacote.getAddress().getHostAddress()).getNome() +
      "\nGrupo: " + nomeGrupo +
      "\n############################################################\n");
    if (GruposDoServidor.getGrupos().get(nomeGrupo) != null && GruposDoServidor.getGrupos().get(nomeGrupo).getUsuario(pacote.getAddress().getHostAddress()) != null) {
      for (Usuario u : GruposDoServidor.getGrupos().get(partes[2]).getUsuarios()) {
        if (!u.getIpAddress().equals(pacote.getAddress().getHostAddress())) {
          try {
            byte[] apdu = apduSendServidorCliente(GruposDoServidor.getGrupos().get(nomeGrupo).getUsuario(pacote.getAddress().getHostAddress()).getNome(), nomeGrupo, mensagem);
            DatagramPacket pacoteSaida = new DatagramPacket(apdu, apdu.length, InetAddress.getByName(u.getIpAddress()), portaClienteUDP);
            socket.send(pacoteSaida);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    } else {
      System.out.println("Voce nao faz mais parte do grupo " + partes[2]);
    }

  }

  /* ***************************************************************
   * Metodo: apduSendServidorCliente
   * Funcao: Constroi a apdu que sera enviado pelo servidor
   * Parametros:
   *            String - remetente
   *            String - grupo
   *            String - mensagem
   * Retorno:
   *          byte[] - apdu em bytes.
   *************************************************************** */
  public byte[] apduSendServidorCliente(String remetente, String grupo, String mensagem) {
    //apdu sera nomeRemetente*grupo*mensagem
    StringBuilder apdu = new StringBuilder();
    apdu.append(remetente).append("*").append(grupo).append("*").append(mensagem);
    return apdu.toString().getBytes(StandardCharsets.UTF_8);//garantindo o padrao UTF_8
  }
}
