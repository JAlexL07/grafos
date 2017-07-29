/**
 * Descripcion: Implementacion de la clase Arco
 * Autor:
 * 	Br. Diego Pedroza, CARNET: 12-11281
 * 	Br. Jean Alexander, CARNET: 12-10848
 */

public class Arco extends Lado
{
	private Vertice extremoInicial;	//Vertice donde inicia el Arco
	private Vertice extremoFinal;		//Vertice donde finaliza el Arco
	
	/**
	 * Descripcion: constructor de la clase Arco
	 * @param id - identificador del Arco
	 * @param peso - peso del Arco
	 * @param extremoInicial - Vertice inicial del Arco
	 * @param extremoFinal - Vertice final del Arco
	 */
	public Arco(String id, double peso, Vertice extremoInicial, Vertice extremoFinal) {
		super(id,peso);
		this.extremoInicial = extremoInicial;
		this.extremoFinal = extremoFinal;
	}
	
	/**
	 * Descripcion: metodo que retorna el Vertice inicial del Arco
	 * @return extremoInicial - Vertice inicial del Arco
	 */
	public Vertice getExtremoInicial() {
		return extremoInicial;
	}

	/**
	 * Descripcion: metodo que retorna el Vertice final del Arco
	 * @return extremoFinal - Vertice final del Arco
	 */
	public Vertice getExtremoFinal() {
		return extremoFinal;
	}
	
	/**
	 * Descripcion: metodo que retorna la representacion en String del Arco
	 * @return str - representacion en String del Arco
	 */
	public String toString() {
		String str = "Arco: "+getId()+" de "+extremoInicial+" a "+extremoFinal;
		return str;
	}
}
