/* ***************************************************************
 * Autor............: Joao Victor Gomes Macedo
 * Matricula........: 202210166
 * Inicio...........: 20/11/2024
 * Ultima alteracao.: 30/11/2024
 * Nome.............: Usuario
 * Funcao...........: Modela e gerencia o usuario
 *************************************************************** */
package model;

import java.util.Objects;

public class Usuario {
  private String nome;
  private String ipAddress;
  private int porta;

  /* ***************************************************************
   * Metodo: Usuario
   * Funcao: Construtor da classe.
   * Parametros:
   *            String - nome
   *            String - ipAdress
   * Retorno:  Sem retorno
   *************************************************************** */
  public Usuario(String nome, String ipAddress) {
    this.nome = nome;
    this.ipAddress = ipAddress;
  }

  public String getNome() {
    return nome;
  }
  //foi necessario sobrescrever os dois metodos abaixo para que a remocao do cliente desconectado ocorresse corretamente.
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Usuario usuario = (Usuario) o;
    return ipAddress.equals(usuario.ipAddress);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ipAddress);
  }

  public String getIpAddress() {
    return ipAddress;
  }
}
