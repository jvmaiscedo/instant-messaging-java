/* ***************************************************************
 * Autor............: Joao Victor Gomes Macedo
 * Matricula........: 202210166
 * Inicio...........: 20/11/2024
 * Ultima alteracao.: 30/11/2024
 * Nome.............: Principal
 * Funcao...........: Instancia os Servidores TCP e UDP
 *************************************************************** */
import model.ServidorTCP;
import model.ServidorUDP;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Principal {


  public static void main(String[] args) {
    ServidorTCP servidorTCP = new ServidorTCP();
    ServidorUDP servidorUDP = new ServidorUDP();
    new Thread(() -> servidorTCP.esperandoPorConexao()).start();
    new Thread(() -> servidorUDP.stayAlert()).start();
    try {
      String ipPrivado = getIpPrivado();
      System.out.println("IPV4 DO SERVIDOR: " + ipPrivado);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static String getIpPrivado() {
    String ip = null;
    Enumeration<NetworkInterface> interfaces = null;
    try {
      interfaces = NetworkInterface.getNetworkInterfaces();
      while (interfaces.hasMoreElements()) {
        NetworkInterface netInterface = interfaces.nextElement();
        if (netInterface.isLoopback() || !netInterface.isUp()) {
          continue;
        }
        Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
        while (addresses.hasMoreElements()) {
          InetAddress address = addresses.nextElement();
          if (!address.isLoopbackAddress() && address instanceof InetAddress) {
            ip = address.getHostAddress();
          }
        }
      }
    } catch (SocketException e) {
      throw new RuntimeException("Erro ao obter o IP do servidor", e.getCause());
    }
    return ip;
  }
}
