/**
 * Descripcion: Implementacion de la clase Digrafo
 * Autor:
 * 	Br. Diego Pedroza, CARNET: 12-11281
 * 	Br. Jean Alexander, CARNET: 12-10848
 */

import java.util.*;
import java.io.*;

public class Digrafo implements Grafo
{
	private HashMap<String,Integer> mapa = new HashMap<String,Integer>();
	private int nV;	//Numero de Vertices
	private int nL;	//Numero de Arcos
	private HashMap<String,ArrayList<String>> listaAdyacencias;	//HashMap de String(id de Vertice) a ArrayList de String(id de Arco)
	private HashMap<String,ArrayList<String>> listaIncidencias;	//HashMap de String(id de Vertice) a ArrayList de String(id de Arco)
	private HashMap<String,Vertice> mapaV;	//HashMap de String(id de Vertice) a Vertice
	private HashMap<String,Arco> mapaA;		//HashMap de String(id de Arco) a Arco
	
    /**
	 * Descripcion: constructor del Digrafo
	 */
	public Digrafo() {
		this.nV = 0;
		this.nL = 0;
	}

	/**
	 * Descripcion: metodo que carga de un archivo un Grafo. Si el
	 * formato de archivo esta mal arroja una excepcion de entrada/salida
	 * @param dirArchivo - direccion del archivo que se quiere cargar
	 * @return b - true si el grafo se carga correctamente y en caso
	 * contrario false
	 */
	public boolean cargarGrafo(String dirArchivo) throws IOException {
		try{
			BufferedReader in = new BufferedReader(new FileReader(dirArchivo));
			String s;
			String[] tok;

			this.nV = Integer.valueOf(in.readLine());
			this.nL = Integer.valueOf(in.readLine());
			this.mapaV = new HashMap<String,Vertice>(nV);
			this.listaAdyacencias = new HashMap<String,ArrayList<String>>(nV);
			this.listaIncidencias = new HashMap<String,ArrayList<String>>(nV);
			this.mapaA = new HashMap<String,Arco>(nL);
			for(int i = 0; i < this.nV; i++){
				s = in.readLine();
				tok = s.split("\\b\\s");
				if(!this.mapaV.containsKey(tok[0])){
					this.mapaV.put(tok[0], new Vertice(tok[0],Double.valueOf(tok[1])));
					this.listaAdyacencias.put(tok[0], new ArrayList<String>(0));
					this.listaIncidencias.put(tok[0], new ArrayList<String>(0));
				}
			}
			

			for(int i = 0; i < this.nL; i++){
				s = in.readLine();
				tok = s.split("\\b\\s");
				try{
		
					if(!this.mapaA.containsKey(tok[0])){
						this.mapaA.put(tok[0],new Arco(tok[0],Double.valueOf(tok[3]),this.mapaV.get(tok[1]),this.mapaV.get(tok[2])));
						ArrayList<String> g = this.listaAdyacencias.get(tok[1]);
						g.add(tok[0]);
						this.listaAdyacencias.put(tok[1],g);
						ArrayList<String> b = this.listaIncidencias.get(tok[2]);
						b.add(tok[0]);
						this.listaIncidencias.put(tok[2],b);
					}
				}catch(OutOfMemoryError e){
					System.out.println("Se agotó la memoria. El grafo es muy grande para nuestra estructura");
					System.exit(0);
					}
			}
			in.close();
			return true;
		}catch(IOException a){
			System.out.println("Problemas al abrir el archivo: "+a.getCause());
			return false;
		}
    }

	/**
	 * Descripcion: metodo que retorna el numero de vertices del Digrafo
	 * @return this.nV - numero de vertices del Digrafo
	 */
    public int numeroDeVertices() {
		return this.nV;
    }

	/**
	 * Descripcion: metodo que retorna el numero de aristas del Digrafo
	 * @return this.nL - numero de aristas del Digrafo
	 */
    public int numeroDeLados() {
		return this.nL;
    }

	/**
	 * Descripcion: metodo que agrega un Vertice al Digrafo
	 * @param v - Vertice que se quiere agregar
	 * @return b - true si el Vertice se agrega y false en caso contrario
	 */
    public boolean agregarVertice(Vertice v) {
		boolean b = estaVertice(v.getId());
		if(!b){
			this.mapaV.put(v.getId(),v);
			this.listaAdyacencias.put(v.getId(), new ArrayList<String>(0));
			this.listaIncidencias.put(v.getId(), new ArrayList<String>(0));
			this.nV += 1;
		}
		return !b;
    }

	/**
	 * Descripcion: metodo que agrega un Vertice al Digrafo
	 * @param id - identificador del Vertice que se quiere agregar
	 * @param peso - peso del Vertice que se quiere agregar
	 * @return b - true si el Vertice se agrega y false en caso contrario
	 */
    public boolean agregarVertice(String id, double peso) {
		boolean b = estaVertice(id);
		if(!b){
			this.mapaV.put(id,new Vertice(id,peso));
			this.listaAdyacencias.put(id, new ArrayList<String>(0));
			this.listaIncidencias.put(id, new ArrayList<String>(0));
			this.nV += 1;
		}
		return !b;
    }

	/**
	 * Descripcion: metodo obtiene un Vertice del Digrafo dado
	 * su identificador. Si el Vertice no se encuentra arroja la excepcion
	 * NoSuchElementException
	 * @param id - identificador del Vertice que se quiere obtener
	 * @return V - Vertice que se quiere obtener
	 */
    public Vertice obtenerVertice(String id){
		if(estaVertice(id)){
			Vertice V = this.mapaV.get(id);
			return V;
		}else{
			throw new NoSuchElementException();
		}
    }

	/**
	 * Descripcion: metodo que verifica si un Vertice se encuentra en el
	 * Digrafo
	 * @param id - identificador del Vertice que se quiere obtener
	 * @return b - true si el Vertice esta y false en caso contrario
	 */
	public boolean estaVertice(String id) {
		boolean b = this.mapaV.containsKey(id);
		return b;
	}

	/**
	 * Descripcion: metodo que verifica si un Lado(Arco) se encuentra
	 * en el Digrafo
	 * @param u - identificador del Vertice con que inicia la Arco
	 * @param v - identificador del Vertice con que finaliza la Arco
	 * @return b - true si la Arco esta y false en caso contrario
	 */
    public boolean estaLado(String u, String v) {
		boolean b = false;
		try{
		if(estaVertice(u)) {
			ArrayList<String> actualVertice = this.listaAdyacencias.get(u);
			
			for (int i=0;i<actualVertice.size();i++) {
				Object arcoActual = (actualVertice.get(i));
				String arcoal = arcoActual.toString();
				Arco arco = this.mapaA.get(arcoal);
				if ((arco.getExtremoFinal()).getId().equals(v)) {}
					b = true;
					break;
				}
				return b;
			}else {
				return b;
		}}catch(NullPointerException a){return false;}
    }

	/**
	 * Descripcion: metodo que elimina un Vertice del Digrafo
	 * @param id - identificador del Vertice que se quiere eliminar
	 * @return b - true si se elimina el Vertice y false en caso contrario
	 */
    public boolean eliminarVertice(String id) {
		boolean b = false;
		if(estaVertice(id)) {
			ArrayList<String> verticeAdyacente = this.listaAdyacencias.get(id);
			ArrayList<String> verticeIncidente = this.listaIncidencias.get(id);
			
			for(int i = 0; i < verticeAdyacente.size(); i++) {
				eliminarArco(verticeAdyacente.get(i).toString());
				
			}
			for(int i = 0; i < verticeIncidente.size(); i++) {
				eliminarArco(verticeIncidente.get(i).toString());
				
			}
			
			this.mapaV.remove(id);
			this.nV -= 1;
			b = true;
			return b;
		}else {
			return b;
		}
    }

	/**
	 * Descripcion: metodo que retorna una lista de los Vertices del 
	 * Digrafo
	 * @return V - lista de Vertices del Digrafo
	 */
	public List<Vertice> vertices() {
		ArrayList<Vertice> V = new ArrayList<Vertice>(this.mapaV.values());
		return V;
    }

	/**
	 * Descripcion: metodo que retorna una lista de las Arcos del
	 * Digrafo
	 * @return A - lista de Arcos del Digrafo
	 */
	public List<Lado> lados() {
		ArrayList<Lado> A = new ArrayList<Lado>(this.mapaA.values());
		return A;
    }

	/**
	 * Descripcion: metodo que retorna el grado de un Vertice
	 * @param id - identificador del Vertice al que se le quiere sacar
	 * el grado
	 * @return g - grado del Vertice deseado
	 */
	public int grado(String id) {
		if(estaVertice(id)) {
			int g = this.listaAdyacencias.get(id).size();
			int f = this.listaIncidencias.get(id).size();
			g += f;
			return g;
		}else{
			throw new NoSuchElementException();
		}
    }

	/**
	 * Descripcion: metodo que retorna una Lista de Vertices adyacentes
	 * al Vertice asociado a un identificador dado
	 * @param id - identificador del Vertice al cual buscarle los
	 * Vertices adyacentes
	 * @return s - lista de Vertices adyacentes al Vertice asociado al
	 * identificador dado
	 */
	public List<Vertice> adyacentes(String id) {
		if(estaVertice(id)) {
			ArrayList<String> lista = this.listaAdyacencias.get(id);
			List<Vertice> s = new ArrayList<Vertice>();
			for(int i = 0; i < lista.size(); i++){
				s.add(this.mapaA.get(lista.get(i)).getExtremoFinal());
			}
			return s;
		}else {
			throw new NoSuchElementException();
		}
    }
   
	/**
	 * Descripcion: metodo que retorna una Lista de Arcos incidentes
	 * al Vertice asociado a un identificador dado
	 * @param id - identificador del Vertice al cual buscarle las
	 * Arcos incidentes
	 * @return a - lista de Arcos incidentes al Vertice asociado al
	 * identificador dado
	 */
	public List<Lado> incidentes(String id) {
		if(estaVertice(id)) {
			List<Lado> a = new ArrayList<Lado>();
			ArrayList<String> lista = this.listaIncidencias.get(id);
			for(int i = 0; i < lista.size(); i++) {
				a.add(this.mapaA.get(lista.get(i)));
			}
			return a;
		}else {
			throw new NoSuchElementException();
		}
    }
    
   public Digrafo clone() {
	   Digrafo P = new Digrafo();
	   P.nV = this.nV;
	   P.nL = this.nL;
	   P.mapaV.putAll(this.mapaV);
	   P.mapaA.putAll(this.mapaA);
	   P.listaAdyacencias.putAll(this.listaAdyacencias);
	   P.listaIncidencias.putAll(this.listaIncidencias);
	   return P;
   }    
	
	/**
	 * Descripcion: metodo que retorna una representacion en String del
	 * Digrafo
	 * @return res - representacion en String del Digrafo
	 */
    public String toString() {
		try{List<Lado> lA = lados();
		String res = "";
		for(int i = 0; i < lA.size(); i++){
			res = res+lA.get(i)+"\n";
		}
		return res;
		}catch(NullPointerException a){return " ";}
    }

	/**
	 * Descripcion: metodo que retorna el grado interior de un Vertice
	 * @param id - identificador del Vertice al que se le quiere sacar
	 * el grado interior
	 * @return g - grado interior del Vertice deseado
	 */
    public int gradoInterior(String id) {
		if(estaVertice(id)) {
			int g = this.listaIncidencias.get(id).size();
			return g;
		}else{
			throw new NoSuchElementException();
		}
    }

	/**
	 * Descripcion: metodo que retorna el grado exterior de un Vertice
	 * @param id - identificador del Vertice al que se le quiere sacar
	 * el grado exterior
	 * @return g - grado exterior del Vertice deseado
	 */
    public int gradoExterior(String id) {
		if(estaVertice(id)) {
			int g = this.listaAdyacencias.get(id).size();
			return g;
		}else {
			throw new NoSuchElementException();
		}
    }

	/**
	 * Descripcion: metodo que retorna una Lista de Vertices sucesores
	 * dado el identificador de un Vertice
	 * @param id - identificador del Vertice al cual se le quiere encontrar
	 * la Lista de Vertices sucesores
	 * @return s - Lista de Vertices sucesores
	 */
    public List<Vertice> sucesores(String id) {
		if(estaVertice(id)) {
			List<Vertice> s = adyacentes(id);
			return s;
		}else {
			throw new NoSuchElementException();
		}
    }

	/**
	 * Descripcion: metodo que retorna una Lista de Vertices predecesores
	 * dado el identificador de un Vertice
	 * @param id - identificador del Vertice al cual se le quiere encontrar
	 * la Lista de Vertices predecesores
	 * @return s - Lista de Vertices predecesores
	 */
    public List<Vertice> predecesores(String id) {
		if(estaVertice(id)) {
			ArrayList<String> lista = this.listaIncidencias.get(id);
			List<Vertice> s = new ArrayList<Vertice>();
			for(int i = 0; i < lista.size(); i++){
				s.add(this.mapaA.get(lista.get(i)).getExtremoInicial());
			}
			return s;
		}else{
			throw new NoSuchElementException();
		}
    }
	
	/**
	 * Descripcion: metodo que agrega un Arco al Digrafo
	 * @param a - Arco que se desea agregar al Digrafo
	 * @return b - true si el Arco se agrega y false en caso contrario
	 */
	public boolean agregarArco(Arco a) {
		boolean b = this.mapaA.containsKey(a.getId());
		if(!b){
			this.mapaA.put(a.getId(),a);
			this.listaAdyacencias.get(a.getExtremoInicial().getId()).add(a.getId());
			this.listaIncidencias.get(a.getExtremoFinal().getId()).add(a.getId());
			this.nL += 1;
			return !b;
		}else{
			return !b;
		}
    } 

	/**
	 * Descripcion: metodo que agrega un Arco al Digrafo
	 * @param id - identificador del Arco que se desea agregar
	 * @param peso - peso del Arco que se desea agregar
	 * @param extremoInicial - identificador del Vertice donde inicia el Arco
	 * @param extremoFinal - identificador del Vertice donde finaliza el Arco
	 * @return b - true si el Arco se agrega y false en caso contrario
	 */
    public boolean agregarArco(String id, double peso, String extremoInicial, String extremoFinal) {		
		boolean b = false;
		if(!estaLado(extremoInicial,extremoFinal)){
			this.mapaA.put(id,new Arco(id,peso,this.mapaV.get(extremoInicial),this.mapaV.get(extremoFinal)));
			this.listaAdyacencias.get(extremoInicial).add(id);
			this.listaIncidencias.get(extremoFinal).add(id);
			b = true;
			this.nL += 1;
			return b;
		}else{
			return b;
		}	
    }

	/**
	 * Descripcion: metodo que elimina un Arco del Digrafo
	 * @param id - identificador del Arco que se desea eliminar
	 * @return b - true si se elimina el Arco y false en caso contrario
	 */
	public boolean eliminarArco(String id) {	
		boolean b = false;
		Arco a = this.mapaA.get(id);
		if(!a.equals(null)) {
			Vertice vI = a.getExtremoInicial();
			Vertice vE = a.getExtremoFinal();
		
			ArrayList<String> Adyacente = this.listaAdyacencias.get(vI.getId());
			ArrayList<String> Incidente = this.listaIncidencias.get(vE.getId());
		
			Adyacente.remove(id);
			Incidente.remove(id);
			this.mapaA.remove(id);
			b = true;
			this.nL -= 1;
			return b;
		}else {
			return b;
		}	
    }

	/**
	 * Descripcion: metodo que retorna un Arco del Digrafo dado
	 * su identificador
	 * @param id - identificador del Arco que se desea obtener
	 * @return A - Arco del Digrafo, asociada al identificador dado
	 */
    public Arco obtenerArco(String id) {
		if(!this.mapaA.get(id).equals(null)) {
			Arco A = this.mapaA.get(id);
			return A;
		}else {
			throw new NoSuchElementException();
		}
    }

	/**
	 * Descripcion: metodo que dado un identificador de un vertice
	 * imprime el numero de caminos desde ese vertice hasta los demas
	 * @param s - identificador de un vertice
	 */
	public void dfs(String s) {
		List<Vertice> succ;
		Stack<String> abiertos = new Stack<String>();
		int valor;
		String v,w;
		Vertice V;
		abiertos.push(s);
		while(!abiertos.empty()) {
			v = abiertos.pop();
			valor = this.mapa.get(v);
			valor++;
			this.mapa.put(v,valor);
			succ = sucesores(v);
			for(int k = 0; k < succ.size(); k++) {
				V = succ.get(k);
				w = V.getId();
				abiertos.push(w);
			}
		}
		List<Vertice> vertice = vertices();
		int n = vertice.size();
		String aux;
		for(int i = 0; i < n; i++) {
			V = vertice.get(i);
			aux = V.getId();
			if(!s.equals(aux)) {
				System.out.println(aux + " " + this.mapa.get(aux));
			}
		}
	}
	
	/**
	 * Descripcion: metodo que inicializa el mapa donde se guardan el
	 * numero de caminos de un vertice a todos los demas
	 */
	public void iniciarMapa() {
		String id[] = new String[this.nV];
		Set <String> set = this.mapaV.keySet();
		set.toArray(id);
		for(int i = 0; i < this.nV; i++) {
			this.mapa.put(id[i],0);
		}
	}
}
