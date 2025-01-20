/* ***************************************************************
 * Autor............: Joao Victor Gomes Macedo
 * Matricula........: 202210166
 * Inicio...........: 20/11/2024
 * Ultima alteracao.: 30/11/2024
 * Nome.............: Grupo
 * Funcao...........: Modela e gerencia o grupo
 *************************************************************** */
package model;

import java.util.ArrayList;

public class Grupo {
  private String nome;
  private ArrayList<Usuario> usuarios;

  /* ***************************************************************
   * Metodo: Grupo
   * Funcao: Construtor da classe.
   * Parametros:
   *            String - nome
   * Retorno:  Sem retorno
   *************************************************************** */
  public Grupo(String nome) {
    this.nome = nome;
    this.usuarios = new ArrayList<>();
  }

  /* ***************************************************************
   * Metodo: getNome
   * Funcao: Retornar o nome do grupo
   * Parametros: Sem parametros
   * Retorno:
   *          String - nome
   *************************************************************** */
  public String getNome() {
    return nome;
  }

  /* ***************************************************************
   * Metodo: getNome
   * Funcao: Adicionar o usuario ao grupo
   * Parametros:
   *          Usuario - usuario
   * Retorno: Sem retorno
   *************************************************************** */
  public void adicionarUsuario(Usuario usuario) {
    usuarios.add(usuario);
  }

  /* ***************************************************************
   * Metodo: removerUsuario
   * Funcao: Remove o usuairo do grupo
   * Parametros:
   *          Usuario - usuario
   * Retorno: Sem retorno
   *************************************************************** */
  public void removerUsuario(Usuario usuario) {
    usuarios.remove(usuario);
  }

  /* ***************************************************************
   * Metodo: getUsuarios
   * Funcao: Retorna a lista de usuarios do grupo
   * Parametros: Sem parametros
   * Retorno:
   *          ArrayList<Usuario> - usuarios
   *************************************************************** */
  public ArrayList<Usuario> getUsuarios() {
    return usuarios;
  }

  /* ***************************************************************
   * Metodo: getUsuario
   * Funcao: Retorna um usuario do grupo
   * Parametros:
   *          String - endereco
   * Retorno:
   *          Usuario - usuarioProcurado
   *************************************************************** */
  public Usuario getUsuario(String endereco) {
    Usuario usuarioProcurado = null;
    for (Usuario usuario : usuarios) {
      if (usuario.getIpAddress().equals(endereco)) {
        usuarioProcurado = usuario;
      }
    }
    return usuarioProcurado;
  }

}
