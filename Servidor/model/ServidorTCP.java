/* ***************************************************************
 * Autor............: Joao Victor Gomes Macedo
 * Matricula........: 202210166
 * Inicio...........: 20/11/2024
 * Ultima alteracao.: 30/11/2024
 * Nome.............: ServidorTCP
 * Funcao...........: Modela e gerencia a conexao TCP do servidor.
 *************************************************************** */
package model;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServidorTCP {
  ServerSocket servidor;
  int porta;

  /* ***************************************************************
   * Metodo: ServidorTCP
   * Funcao: Construtor da classe.
   * Parametros: Sem Parametros
   * Retorno:  Sem retorno
   *************************************************************** */
  public ServidorTCP() {
    this.porta = 6789;
    try {
      servidor = new ServerSocket(porta);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /* ***************************************************************
   * Metodo: esperandoPorConexao
   * Funcao: Espera por uma nova conexao, e instancia uma Thread para
   *         lidar com o fluxo de dados apos a conexao ser aceita.
   * Parametros: Sem parametros
   * Retorno:  Sem retorno
   *************************************************************** */
  public void esperandoPorConexao() {
    while (true) {
      try {
        Socket cliente = servidor.accept();
        new Thread(() -> handleConnection(cliente)).start();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /* ***************************************************************
   * Metodo: handleConnection
   * Funcao: Lida com a conexao, esperando por envio de mensagens
   * Parametros:
   *             Socket - clienteConectado
   * Retorno:  Sem retorno
   *************************************************************** */
  public void handleConnection(Socket clienteConectado) {
    try {
      ObjectInputStream entrada = new ObjectInputStream(clienteConectado.getInputStream());
      ObjectOutputStream saida = new ObjectOutputStream(clienteConectado.getOutputStream());
      while (true) {
        String apdu = (String) entrada.readObject();
        String[] partesApdu = apdu.split("\\*");
        System.out.println("Tipo da requisição: " + partesApdu[0] +
          "\nCom o usuario: " + partesApdu[1] +
          "\nPara o grupo: " + partesApdu[2] +
          "\n############################################################\n");
        switch (partesApdu[0]) {
          case "join":
            joinGroup(partesApdu[2], new Usuario(partesApdu[1], clienteConectado.getInetAddress().getHostAddress()));
            break;
          case "leave":
            String grupoNome = partesApdu[2];
            leaveGroup(grupoNome, GruposDoServidor.getGrupos().get(grupoNome).getUsuario(clienteConectado.getInetAddress().getHostAddress()));
            break;
          default:
            System.out.println("ALUGM ERRO NA SEPARACAO DOS ITENS DA APDU");
            break;
        }
      }
    } catch (SocketException e) {
      removerUsuarioDesconectado(clienteConectado);
      System.out.println("O cliente "+clienteConectado.getInetAddress().getHostAddress()+" se desconectou.");
      try {
        clienteConectado.close();
      } catch (IOException ex) {
        throw new RuntimeException("Erro ao finalizar a conexao");
      }
    } catch (Exception e) {
      removerUsuarioDesconectado(clienteConectado);
      System.out.println("O cliente "+clienteConectado.getInetAddress().getHostAddress()+" se desconectou.");
      try {
        clienteConectado.close();
      } catch (IOException ex) {
        throw new RuntimeException("Erro ao finalizar a conexao");
      }
    }
  }

  /* ***************************************************************
   * Metodo: joinGroup
   * Funcao: Insere o novo usuario no grupo, caso exista. Caso nao,
   *         cria o grupo e o adiciona.
   * Parametros:
   *             String - nomeGrupo
   *             Usuario - usuario
   * Retorno:  Sem retorno
   *************************************************************** */
  public void joinGroup(String nomeGrupo, Usuario usuario) {
    if (GruposDoServidor.getGrupos().containsKey(nomeGrupo)) {
      GruposDoServidor.getGrupos().get(nomeGrupo).adicionarUsuario(usuario);
    } else {
      GruposDoServidor.getGrupos().put(nomeGrupo, new Grupo(nomeGrupo));
      GruposDoServidor.getGrupos().get(nomeGrupo).adicionarUsuario(usuario);
    }
  }

  /* ***************************************************************
   * Metodo: leaveGroup
   * Funcao: Remove o usuario no grupo, caso seja o ultimo, tambem
   *          remove o grupo da lista de grupos.
   * Parametros:
   *             String - nomeGrupo
   *             Usuario - usuario
   * Retorno:  Sem retorno
   *************************************************************** */
  public void leaveGroup(String nomeGrupo, Usuario usuario) {
    if (GruposDoServidor.getGrupos().containsKey(nomeGrupo)) {
      GruposDoServidor.getGrupos().get(nomeGrupo).removerUsuario(usuario);
      if (GruposDoServidor.getGrupos().get(nomeGrupo).getUsuarios().isEmpty()) {
        GruposDoServidor.getGrupos().remove(nomeGrupo);
      }
    }
  }

  /* ***************************************************************
   * Metodo: removerUsuarioDesconectado
   * Funcao: Remove o usuario desconectado de todos os grupos do qual
   *         faz parte.
   * Parametros:
   *             Socket - cliente
   * Retorno:  Sem retorno
   *************************************************************** */
  public void removerUsuarioDesconectado(Socket cliente) {
    List<Grupo> gruposDoUsuario = new ArrayList<>();
    Usuario usuarioDesconectado = null;
    for (Grupo grupo : GruposDoServidor.getGrupos().values()) {
      for (Usuario usuario : grupo.getUsuarios()) {
        if (usuario.getIpAddress().equals(cliente.getInetAddress().getHostAddress())) {
          usuarioDesconectado = usuario;
          gruposDoUsuario.add(grupo);
        }
      }
    }
    for (Grupo grupo : gruposDoUsuario) {
      GruposDoServidor.getGrupos().get(grupo.getNome()).removerUsuario(usuarioDesconectado);
      if (GruposDoServidor.getGrupos().get(grupo.getNome()).getUsuarios().isEmpty()) {
        GruposDoServidor.getGrupos().remove(grupo.getNome());
      }
    }
  }
}
