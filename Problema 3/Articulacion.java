/**
 * Clase Articulacion
 * Autor:
 * 	Br. Diego Pedroza	12-11281
 * 	Br. Jean Alexander	12-10848
 * Ultima Modificacion:	26-05-15
 */

import java.util.*;
import java.io.*;

public class Articulacion
{
	public static void main(String [] args) {
		GrafoNoDirigido G = new GrafoNoDirigido();
		try {
			G.cargarGrafo(args[2]);
		}catch(IOException e) {
			System.err.println("ERROR en el formato de entrada");
			System.exit(0);
		}
		G.articular();
		G.dfs(args[0],args[1]);
	}
}
