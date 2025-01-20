/* ***************************************************************
* Autor............: Joao Victor Gomes Macedo
* Matricula........: 202210166
* Inicio...........: 20/11/2024
* Ultima alteracao.: 01/11/2024
* Nome.............: APDU
* Funcao...........: Funcao de modelar as mensagens enviadas via
*                    rede.
*************************************************************** */
package model;

import java.nio.charset.StandardCharsets;

public class APDU {

  /* ***************************************************************
   * Metodo: join
   * Funcao: Formar a String contendo os dados que serao transmitidos
   *         na rede.
   * Parametros:
   *            String - nome
   *            String - grupo
   * Retorno:
   *            String - apdu
   *************************************************************** */
  public String join(String nome, String grupo) {
    StringBuilder apduJoin = new StringBuilder();
    apduJoin.append("join*").append(nome).append("*").append(grupo);
    return apduJoin.toString();
  }

  /* ***************************************************************
   * Metodo: leave
   * Funcao: Formar a String contendo os dados que serao transmitidos
   *         na rede.
   * Parametros:
   *            String - nome
   *            String - grupo
   * Retorno:
   *            String - apdu
   *************************************************************** */
  public String leave(String nome, String grupo) {
    StringBuilder apduLeave = new StringBuilder();
    apduLeave.append("leave*").append(nome).append("*").append(grupo);
    return apduLeave.toString();
  }

  /* ***************************************************************
   * Metodo: send
   * Funcao: Formar a String contendo os dados que serao transmitidos
   *         na rede.
   * Parametros:
   *            String - mensagems
   *            String - grupo
   * Retorno:
   *            String - apdu
   *************************************************************** */
  public byte[] send(String mensagem, String grupo) {
    StringBuilder apduSend = new StringBuilder();
    apduSend.append("send*").append(mensagem).append("*").append(grupo);
    return apduSend.toString().getBytes(StandardCharsets.UTF_8);//usando o padrao UTF_8.
  }

}
