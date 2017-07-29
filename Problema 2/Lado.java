/**
 * Descripcion: Implementacion de la clase Lado
 * Autor:
 * 	Br. Diego Pedroza, CARNET: 12-11281
 * 	Br. Jean Alexander, CARNET: 12-10848
 */

public abstract class Lado
{
	private String id;		//identificador del Lado
	private double peso;	//peso del Lado
	
	/**
	 * Descripcion: constructor de la clase Lado
	 * @param id - identificador del Lado
	 * @param peso - peso del Lado
	 */
	public Lado(String id, double peso) {
		this.id = id;
		this.peso = peso;
	}
	
	/**
	 * Descripcion: metodo que retorna el identificador del Lado
	 * @return id - identificador del Lado
	 */
	public String getId() {
		return id;
	}

	/**
	 * Descripcion: metodo que retorna el peso del Lado
	 * @return peso - peso del Lado
	 */
	public double getPeso() {
		return peso;
	}

	/**
	 * Descripcion: metodo abstracto para la representacion en String del Lado
	 */
	public abstract String toString();
}
