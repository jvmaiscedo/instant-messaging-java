/* ***************************************************************
 * Autor............: Joao Victor Gomes Macedo
 * Matricula........: 202210166
 * Inicio...........: 20/11/2024
 * Ultima alteracao.: 30/11/2024
 * Nome.............: GruposDoServidor
 * Funcao...........: Modela um Singleton da lista, que sera
 *                    acessada pelos servidores TCP e UDP.
 *************************************************************** */
package model;
import java.util.HashMap;

public class GruposDoServidor {
     static HashMap<String, Grupo> grupos = new HashMap<>();

     public static HashMap<String, Grupo> getGrupos() {
        return grupos;
      }


}
