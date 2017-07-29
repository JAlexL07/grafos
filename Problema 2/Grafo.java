/**
 * Descripcion: Implementacion de la clase Grafo
 * Autor:
 * 	Br. Diego Pedroza, CARNET: 12-11281
 * 	Br. Jean Alexander, CARNET: 12-10848
 */

import java.util.*;
import java.io.*;

public interface Grafo
{	
    public boolean cargarGrafo(String dirArchivo) throws IOException;
    
    public int numeroDeVertices();

    public int numeroDeLados();

    public boolean agregarVertice(Vertice v);

    public boolean agregarVertice(String id, double peso);
	
	public Vertice obtenerVertice(String id);

    public boolean estaVertice(String id);
	
    public boolean estaLado(String u, String v);

    public boolean eliminarVertice(String id);

    public List<Vertice> vertices();
	
    public List<Lado> lados();

    public int grado(String id);

    public List<Vertice> adyacentes(String id);
 
    public List<Lado> incidentes(String id);

	public Grafo clone();

    public String toString();

}
