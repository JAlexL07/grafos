/**
 * Clase ListaCamino
 * Autor:
 * 	Br. Diego Pedroza	12-11281
 * 	Br. Jean Alexander	12-10848
 * Ultima Modificacion:	26-05-15
 */

import java.util.*;
import java.io.*;

public class ListaCamino
{	
	/**
	 * Programa Principal
	 */
	public static void main(String [] args) {
		Digrafo G = new Digrafo();
		try {
			G.cargarGrafo(args[1]);
		}catch(IOException e) {
			System.err.println("ERROR en el formato de entrada");
			System.exit(0);
		}
		G.iniciarMapa();
		G.dfs(args[0]);
	}
}
