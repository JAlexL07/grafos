/**
 * Descripcion: Implementacion de la clase Grafo No Dirigido
 * Autor:
 * 	Br. Diego Pedroza, CARNET: 12-11281
 * 	Br. Jean Alexander, CARNET: 12-10848
 */

import java.util.*;
import java.lang.*;
import java.io.*;

public class GrafoNoDirigido implements Grafo
{
    private int nV;	//Numero de Vertices
	private int nL;	//Numero de Aristas
	private HashMap<String,ArrayList<String>> listaAdyacencias;	//HashMap de String(id de Vertice) a ArrayList de String(id de Arista)
	private HashMap<String,ArrayList<String>> listaIncidencias;	//HashMap de String(id de Vertice) a ArrayList de String(id de Arista)
	private HashMap<String,Vertice> mapaV;	//HashMap de String(id de Vertice) a Vertice
	private HashMap<String,Arista> mapaA;	//HashMap de String(id de Arista) a Arista
	private ArrayList<String> articulacion = new ArrayList<String>();
	
    /**
	 * Descripcion: constructor del GrafoNoDirigido
	 */
    public GrafoNoDirigido() {
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
		BufferedReader in = new BufferedReader(new FileReader(dirArchivo));
		String s;
		String[] tok;
		ArrayList<String> aux1,aux2,aux3,aux4;
		
		try{
			this.nV = Integer.valueOf(in.readLine());
			this.nL = Integer.valueOf(in.readLine());
			this.mapaV = new HashMap<String,Vertice>(nV);
			this.listaAdyacencias = new HashMap<String,ArrayList<String>>(nV);
			this.listaIncidencias = new HashMap<String,ArrayList<String>>(nV);
			this.mapaA = new HashMap<String,Arista>(nL);
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
						this.mapaA.put(tok[0],new Arista(tok[0],Double.valueOf(tok[3]),this.mapaV.get(tok[1]),this.mapaV.get(tok[2])));
						aux1 = this.listaAdyacencias.get(tok[1]);
						aux2 = this.listaAdyacencias.get(tok[2]);
						aux1.add(tok[0]);
						aux2.add(tok[0]);
						this.listaAdyacencias.put(tok[1],aux1);
						this.listaAdyacencias.put(tok[2],aux2);
						aux3 = this.listaIncidencias.get(tok[1]);
						aux4 = this.listaIncidencias.get(tok[2]);
						aux3.add(tok[0]);
						aux4.add(tok[0]);
						this.listaIncidencias.put(tok[1],aux3);
						this.listaIncidencias.put(tok[2],aux4);
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
	 * Descripcion: metodo que retorna el numero de vertices del GrafoNoDirigido
	 * @return this.nV - numero de vertices del GrafoNoDirigido
	 */
    public int numeroDeVertices() {
		return this.nV;
    }

	/**
	 * Descripcion: metodo que retorna el numero de aristas del GrafoNoDirigido
	 * @return this.nL - numero de aristas del GrafoNoDirigido
	 */
    public int numeroDeLados() {
		return this.nL;
    }

	/**
	 * Descripcion: metodo que agrega un Vertice al GrafoNoDirigido
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
	 * Descripcion: metodo que agrega un Vertice al GrafoNoDirigido
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
	 * Descripcion: metodo obtiene un Vertice del GrafoNoDirigido dado
	 * su identificador. Si el Vertice no se encuentra arroja la excepcion
	 * NoSuchElementException
	 * @param id - identificador del Vertice que se quiere obtener
	 * @return V - Vertice que se quiere obtener
	 */
    public Vertice obtenerVertice(String id) {
		if(estaVertice(id)) {
			Vertice V = this.mapaV.get(id);
			return V;
		}else {
			throw new NoSuchElementException();
		}
    }

	/**
	 * Descripcion: metodo que verifica si un Vertice se encuentra en el
	 * GrafoNoDirigido
	 * @param id - identificador del Vertice que se quiere obtener
	 * @return b - true si el Vertice esta y false en caso contrario
	 */
    public boolean estaVertice(String id) {
		boolean b = this.mapaV.containsKey(id);
		return b;
    }

	/**
	 * Descripcion: metodo que verifica si un Lado(Arista) se encuentra
	 * en el GrafoNoDirigido
	 * @param u - identificador del Vertice con que inicia la Arista
	 * @param v - identificador del Vertice con que finaliza la Arista
	 * @return b - true si la Arista esta y false en caso contrario
	 */
    public boolean estaLado(String u, String v){
		boolean b = false;
		if(estaVertice(u)) {
			ArrayList<String> actualVertice = this.listaAdyacencias.get(u);
			
			for (int i=0;i<actualVertice.size();i++) {
				Object arcoActual = (actualVertice.get(i));
				String arcoal = arcoActual.toString();
				Arista arco = this.mapaA.get(arcoal);
				if ((arco.getExtremo2()).getId().equals(v)) {}
					b = true;
					break;
				}
			return b;
				
		}else {
			return b;
		}
    }

	/**
	 * Descripcion: metodo que elimina un Vertice del GrafoNoDirigido
	 * @param id - identificador del Vertice que se quiere eliminar
	 * @return b - true si se elimina el Vertice y false en caso contrario
	 */
    public boolean eliminarVertice(String id) {
		boolean b = false;
		if(estaVertice(id)) {
			ArrayList<String> verticeAdyacente = this.listaAdyacencias.get(id);
			ArrayList<String> verticeIncidente = this.listaIncidencias.get(id);
			
			for(int i = 0; i < verticeAdyacente.size(); i++) {
				eliminarArista(verticeAdyacente.get(i).toString());
				
			}
			for(int i = 0; i < verticeIncidente.size(); i++) {
				eliminarArista(verticeIncidente.get(i).toString());
				
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
	 * GrafoNoDirigido
	 * @return V - lista de Vertices del GrafoNoDirigido
	 */
	public List<Vertice> vertices() {
		ArrayList<Vertice> V = new ArrayList<Vertice>(this.mapaV.values());
		return V;
    }

	/**
	 * Descripcion: metodo que retorna una lista de las Aristas del
	 * GrafoNoDirigido
	 * @return A - lista de Aristas del GrafoNoDirigido
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
				s.add(this.mapaA.get(lista.get(i)).getExtremo2());
			}
			return s;
		}else {
			throw new NoSuchElementException();
		}
	}
	
	/**
	 * Descripcion: metodo que retorna una Lista de Aristas incidentes
	 * al Vertice asociado a un identificador dado
	 * @param id - identificador del Vertice al cual buscarle las
	 * Aristas incidentes
	 * @return a - lista de Aristas incidentes al Vertice asociado al
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

	public GrafoNoDirigido clone() {
	   GrafoNoDirigido P = new GrafoNoDirigido();
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
	 * GrafoNoDirigido
	 * @return res - representacion en String del GrafoNoDirigido
	 */
    public String toString() {
		try{
		List<Lado> lA = lados();
		String res = "";
		for(int i = 0; i < lA.size(); i++){
			res = res+lA.get(i)+"\n";
		}
		return res;
	}catch(NullPointerException a){return " ";}
    }

	/**
	 * Descripcion: metodo que agrega una Arista al GrafoNoDirigido
	 * @param a - Arista que se desea agregar al GrafoNoDirigido
	 * @return b - true si la Arista se agrega y false en caso contrario
	 */
    public boolean agregarArista(Arista a) {
		boolean b = this.mapaA.containsKey(a.getId());
		if(!b){
			this.mapaA.put(a.getId(),a);
			this.listaAdyacencias.get(a.getExtremo1().getId()).add(a.getId());
			this.listaIncidencias.get(a.getExtremo2().getId()).add(a.getId());
			this.nL += 1;
			return !b;
		}else{
			return !b;
		}
    }
	
	/**
	 * Descripcion: metodo que agrega una Arista al GrafoNoDirigido
	 * @param id - identificador de la Arista que se desea agregar
	 * @param peso - peso de la Arista que se desea agregar
	 * @param u - identificador del Vertice donde inicia la Arista
	 * @param v - identificador del Vertice donde finaliza la Arista
	 * @return b - true si la Arista se agrega y false en caso contrario
	 */
    public boolean agregarArista(String id, double peso, String u, String v) {
		boolean b = false;
		if(!estaLado(u,v)){
			this.mapaA.put(id,new Arista(id,peso,this.mapaV.get(u),this.mapaV.get(v)));
			this.listaAdyacencias.get(u).add(id);
			this.listaIncidencias.get(v).add(id);
			b = true;
			this.nL += 1;
			return b;
		}else{
			return b;
		}
    }

	/**
	 * Descripcion: metodo que elimina una Arista del GrafoNoDirigido
	 * @param id - identificador de la Arista que se desea eliminar
	 * @return b - true si se elimina la Arista y false en caso contrario
	 */
    public boolean eliminarArista(String id) {
		boolean b = false;
		Arista a = this.mapaA.get(id);
		if(!a.equals(null)) {
			Vertice vI = a.getExtremo1();
			Vertice vE = a.getExtremo2();
		
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
	 * Descripcion: metodo que retorna una Arista del GrafoNoDirigido dado
	 * su identificador
	 * @param id - identificador de la Arista que se desea obtener
	 * @return A - Arista  del GrafoNoDirigido, asociada al identificador dado
	 */
    public Arista obtenerArista(String id) {
		if(!this.mapaA.get(id).equals(null)) {
			Arista A = this.mapaA.get(id);
			return A;
		}else {
			throw new NoSuchElementException();
		}
    }
	
	/**
	 * Descripcion: metodo que dado un identificador de un vertice
	 * imprime el numero de caminos desde ese vertice a cada uno de los
	 * demas vertices
	 * @param s - identificador de un vertice
	 */
	public void articular() {
		List<Vertice> succ = new ArrayList<Vertice>();
		Stack<String> abiertos = new Stack<String>();
		LinkedList<String> cerrados = new LinkedList<String>();
		int tam = 0;
		boolean b;
		String s,v,w;
		Vertice V;
		List<Vertice> vertice = vertices();
		for(int i = 0; i < this.nV; i++) {
			V = vertice.get(i);
			s = V.getId();
			abiertos.push(s);
			while(!abiertos.empty()) {
				v = abiertos.pop();
				b = cerrados.contains(v);
				if(!b) {
					succ = adyacentes(v);
					cerrados.add(v);
					tam = succ.size();
					if(tam > 2) {
						this.articulacion.add(v);
					}
				}
				for(int k = 0; k < tam; k++) {
					V = succ.get(k);
					w = V.getId();
					b = cerrados.contains(w);
					if(!b) {
						abiertos.push(w);
					}
				}
			}
		}
		tam = this.articulacion.size();
		for(int j = 0; j < tam; j++) {
			s = this.articulacion.get(j);
			System.out.print(s+" ");
		}
		System.out.println();
	}
	
	
	
	/**
	 * Descripcion: Busqueda por profundidad iterativo con el cual encuentra todos los caminos 
	 * entre las ciudades dadas.
	 * @param CiudadInicio Ciudad de Salida, vertice inicial
	 * @param CiudadFinal Ciudad de Llegada, vertice final
	 */	
	public void dfs(String CiudadInicio, String CiudadFinal) {
		LinkedList<String> abiertos = new LinkedList<String>();
		LinkedList<String> camino = new LinkedList<String>();
		LinkedList<String> desechados = new LinkedList<String>();
		String ciudadActual;
		String Ultima_abierta;
		String UltimoCamino;
		boolean encontroCamino=false;
		
		abiertos.add(CiudadInicio);
		if(CiudadInicio.equals(CiudadFinal)){
			encontroCamino=true;
			camino.add(CiudadInicio);
		
		}else{
			abiertos.remove(CiudadInicio);
			abiertos.addAll(this.listaIncidencias.get(CiudadInicio));
			while(!abiertos.isEmpty() && !encontroCamino && camino.size()!=0){
				System.out.println(abiertos);
				ciudadActual = abiertos.removeLast();
				camino.add(ciudadActual);
				if(CiudadInicio.equals(CiudadFinal)){
					encontroCamino=true;
					break;
				}else if(this.articulacion.contains(ciudadActual)){
						camino.removeLast();
				}
				else{
					if(this.listaIncidencias.get(ciudadActual)==null){
						desechados.add(ciudadActual);
						camino.remove(ciudadActual);
					}else{
					if(desechados.containsAll(this.listaIncidencias.get(ciudadActual))){
						desechados.add(ciudadActual);
						if(this.articulacion.contains(ciudadActual)){
							//Pass
						}else{
							camino.add(ciudadActual);
							//Rutina de eliminacion
							if(this.articulacion.contains(ciudadActual)){
								camino.removeLast();
							}
								//Si no lo he encontrado
								//Y ciudadActual no tiene sucesores
								//Entonces no hay camino posible por esa ciudad
								//Tengo que eliminar esa ciudad 
								//y la agrega a desechados
								if(this.listaIncidencias.get(ciudadActual).size()<=1){
									if(!ciudadActual.equals(CiudadFinal)){
										//Elimino lo elimino de las posiblidades pues
										//ya no puedo seguir por sus sucesores(NO TIENE)
										desechados.add(ciudadActual);
									if(!this.listaIncidencias.get(camino.getLast()).contains(abiertos.getLast())){
										camino.removeLast();
									}
										
									}
									UltimoCamino = camino.getLast();
									if(abiertos.size()!=0){
										Ultima_abierta = abiertos.getLast();
										if(!this.listaIncidencias.get(UltimoCamino).contains(Ultima_abierta)){
											//Elimino lo elimino del camino pues
											//ya no puedo seguir por sus sucesores
											camino.removeLast();
										}

		
										
									}else{
										//Paso, abierto esta vacio, se rompera la condicion del ciclo
									}
									if(this.articulacion.contains(ciudadActual)){
										camino.removeLast();
										break;
									}
									
								}else{
									//Introduzco los sucesores de la ciudadActual a los abiertos
									
								}
							}

				}	
				}

			}

		}		
		if(encontroCamino){
			System.out.println("si");
		}else{
			System.out.println("no");
		}
		
		}
	
	}
		
		
		
		
		
		
		 
		 
	
}
