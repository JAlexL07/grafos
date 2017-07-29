/**
 * Descripcion: Implementacion de la clase Arista
 * Autor:
 * 	Br. Diego Pedroza, CARNET: 12-11281
 * 	Br. Jean Alexander, CARNET: 12-10848
 */

public class Arista extends Lado
{
	private Vertice u;	//Vertice donde inicia la Arista
	private Vertice v;	//Vertice donde finaliza la Arista
	
	/**
	 * Descripcion: constructor de la clase Arista
	 * @param id - identificador de la Arista
	 * @param peso - peso de la Arista
	 * @param u - Vertice inicial de la Arista
	 * @param v - Vertice final de la Arista
	 */
	public Arista(String id, double peso, Vertice u, Vertice v) {
		super(id,peso);
		this.u = u;
		this.v = v;
	}
	
	/**
	 * Descripcion: metodo que retorna el Vertice inicial de la Arista
	 * @return u - Vertice inicial de la Arista
	 */
	public Vertice getExtremo1() {
		return u;
	}

	/**
	 * Descripcion: metodo que retorna el Vertice final de la Arista
	 * @return v - Vertice final de la Arista
	 */
	public Vertice getExtremo2() {
		return v;
	}

	/**
	 * Descripcion: metodo que retorna la representacion en String de la Arista
	 * @return str - representacion en String de la Arista
	 */
	public String toString() {
		String str = "Arista: "+getId()+" de "+u+" a "+v+", peso: "+Double.valueOf(getPeso());
		return str;
	}
}
