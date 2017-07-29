/**
 * Descripcion: Implementacion de la clase Vertice
 * Autor:
 * 	Br. Diego Pedroza, CARNET: 12-11281
 * 	Br. Jean Alexander, CARNET: 12-10848
 */

public class Vertice
{
	private String id;		//identificador del Vertice
	private double peso;	//peso del Vertice
	
	/**
	 * Descripcion: constructor de la clase Vertice
	 * @param id - identificador del Vertice
	 * @param peso - peso del Vertice
	 */
	public Vertice(String id, double peso) {
		this.id = id;
		this.peso = peso;
	}
	
	/**
	 * Descripcion: metodo que retorna el peso del Vertice
	 * @return peso - peso del Vertice
	 */
	public double getPeso() {
		return this.peso;
	}

	/**
	 * Descripcion: metodo que retorna el identificador del Vertice
	 * @return id - identificador del Vertice
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Descripcion: metodo que retorna el peso del Vertice
	 * @return str - representacion en String del Vertice
	 */
	public String toString() {
		String str = "(vertice: "+this.id+")";
		return str;
	}
}
