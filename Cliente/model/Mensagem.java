/* ***************************************************************
 * Autor............: Joao Victor Gomes Macedo
 * Matricula........: 202210166
 * Inicio...........: 20/11/2024
 * Ultima alteracao.: 30/11/2024
 * Nome.............: Mensagem
 * Funcao...........: Modela a mensagem recebida via UDP
 *************************************************************** */
package model;

import java.time.LocalDateTime;
import java.util.Date;

public class Mensagem {
  private String nomeRemetente;//quem enviou a mensagem
  private String texto;//o texto contido na mensagem
  private LocalDateTime data;//o momento em que a mensagem chegou
  private boolean enviei;//booleana que controla quais das mensagens enviei

  /* ***************************************************************
   * Metodo: Mensagem
   * Funcao: Construtor da classe.
   * Parametros:
   *              String - nomeRemetente
   *              String - texto
   *              LocalDateTime - data
   *              boolean - enviei
   * Retorno:  Sem retorno
   *************************************************************** */
  public Mensagem(String nomeRemetente, String texto, LocalDateTime data, boolean enviei) {
    this.nomeRemetente = nomeRemetente;
    this.texto = texto;
    this.data = data;
    this.enviei = enviei;
  }

  /* ***************************************************************
   * Metodo: getNomeRemetente
   * Funcao: Obter informacao sobre o remetente da mensagem
   * Parametros: Sem parametros
   * Retorno:  Sem retorno
   *************************************************************** */
  public String getNomeRemetente() {
    return nomeRemetente;
  }

  /* ***************************************************************
   * Metodo: getTexto
   * Funcao: Obter informacao sobre o texto da mensagem
   * Parametros: Sem parametros
   * Retorno:  Sem retorno
   *************************************************************** */
  public String getTexto() {
    return texto;
  }

  /* ***************************************************************
   * Metodo: getData
   * Funcao: Obter informacao sobre a data da mensagem
   * Parametros: Sem parametros
   * Retorno:  Sem retorno
   *************************************************************** */
  public LocalDateTime getData() {
    return data;
  }

  /* ***************************************************************
   * Metodo: isEnviei
   * Funcao: Verificar se o proprio cliente emitiu a mensagem
   * Parametros: Sem parametros
   * Retorno:  Sem retorno
   *************************************************************** */
  public boolean isEnviei() {
    return enviei;
  }
}
