/* ***************************************************************
 * Autor............: Joao Victor Gomes Macedo
 * Matricula........: 202210166
 * Inicio...........: 20/11/2024
 * Ultima alteracao.: 30/11/2024
 * Nome.............: Cliente
 * Funcao...........: Gerencia as instancias dos clientes TCP e UDP
 *************************************************************** */
package model;

import model.ClienteTCP;
import model.ClienteUDP;

import java.io.IOException;
import java.net.SocketException;

public class Cliente {
  ClienteUDP clienteUDP;
  ClienteTCP clienteTCP;
  String nome;

  /* ***************************************************************
   * Metodo: Cliente
   * Funcao: Construtor do objeto Cliente
   * Parametros:
   *            String - servidorIP
   *            String - nomeCliente
   * Retorno:  Sem retorno
   *************************************************************** */
  public Cliente(String servidorIP, String nomeCliente) throws IOException {//Essa excecao eh lancada para ser tratada no controller.
    this.clienteTCP = new ClienteTCP(servidorIP, nomeCliente);
    this.clienteUDP = new ClienteUDP(servidorIP, nomeCliente);
    this.nome = nomeCliente;

  }

  /* ***************************************************************
   * Metodo: getClienteTCP
   * Funcao: retornar a instancia do cliente TCP
   * Parametros: Sem parametros
   * Retorno:  Sem retorno
   *************************************************************** */
  public ClienteTCP getClienteTCP() {
    return clienteTCP;
  }

  /* ***************************************************************
   * Metodo: getClienteUDP
   * Funcao: retornar a instancia do cliente UDP
   * Parametros: Sem parametros
   * Retorno:  Sem retorno
   *************************************************************** */
  public ClienteUDP getClienteUDP() {
    return clienteUDP;
  }

  /* ***************************************************************
   * Metodo: getNome
   * Funcao: retornar o nome do cliente.
   * Parametros: Sem parametros
   * Retorno:  Sem retorno
   *************************************************************** */
  public String getNome() {
    return nome;
  }
}
