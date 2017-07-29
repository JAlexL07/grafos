import java.util.*;
import java.io.*;


public class Grafo {
	
	
	private int numV;
	private int numA;
	private HashMap<String,ArrayList<String>> ciudades;
	private HashMap<String,ArrayList<String>> sucesores;
	
	
	/**
	 * Descripcion: Constructor del grafo vacío;
	 */
	
	public Grafo()
	{
		this.numA = 0;
		this.numV = 0;
		this.ciudades = new HashMap<String,ArrayList<String>>();
		this.sucesores = new HashMap<String,ArrayList<String>>();
		
	}
	
	/**
	 * Descripcion: Constructor del Grafo si se tiene un archivo de Entrada
	 * @param archivoEntrada Nombre del archivo de Entrada
	 * @throws IOException Si el archivo de Entrada no es el adecuado
	 * @throws NullPointerException Si el formato del archivo no es el adecuado
	 */
	
	public Grafo(String archivoEntrada) throws IOException,NullPointerException {
		Scanner archivo = new Scanner(new File(archivoEntrada));
		//Entrada Numero de Ciudades y Numero de Rutas
		this.ciudades = new HashMap<String,ArrayList<String>>();
		this.sucesores = new HashMap<String,ArrayList<String>>();
		this.numV = archivo.nextInt();
		this.numA = archivo.nextInt();
		int i=0;
		String[] tokes = new String[2];
		String[] tokens = new String[4];
		//inicializo Ciudad y Peso
		for (i=0;i<this.numV;i++){
			tokes[0] = archivo.next();
			tokes[1] = archivo.next();
			this.ciudades.put(tokes[0], new ArrayList<String>(2));
			this.ciudades.get(tokes[0]).add(tokes[1]);
		}
		
		for (i=0;i<this.numA;i++){
			
			tokens[0] = archivo.next();
			tokens[1] = archivo.next();
			tokens[2] = archivo.next();
			tokens[3] = archivo.next();
			if (tokens.length == 4){
				try{
					ciudades.get(tokens[1]).add(tokens[2]);
					ciudades.get(tokens[1]).add(tokens[0]);
					ciudades.get(tokens[1]).add(tokens[3]);
				}catch(NullPointerException a){
					System.out.println(i);
					System.out.println("Error en el formato del archivo");
					System.out.println("La Ciudad: '"+tokens[1]+"'no existe");
					System.exit(0);
				}
			}else{
			System.out.println("Error en el formato del archivo");
			System.exit(0);
			}
		}
		//Inicializando la lista de Sucesores(HashMap)
			Set<String> ABS = this.ciudades.keySet();
			String[] idCiudades = new String[ABS.size()];
			ABS.toArray(idCiudades);
		for(i=0;i<idCiudades.length;i++){
			this.sucesores.put(idCiudades[i],(sucesores(idCiudades[i])));	
		}
		archivo.close();
	}
	/**
	 * Descripcion: Devuelve el numero de Vertices del Grafo
	 * @return int numero de Vertices
	 */
	public int getNumeroVertices(){
		return this.numV;
	}
	
	/**
	 * Descripcion: Dado el id de un vertice, buscara los id de los vertices sucesores
	 * al vertice dado
	 * @param a Id del Vertice del cual se buscaran los sucesores
	 * @return ArrayList< String > donde estan los id de los sucesores del vertice
	 */
	
	private ArrayList<String> sucesores(String a) {
		ArrayList<String> sucesores = new ArrayList<String>();
		ArrayList<String> saco = this.ciudades.get(a);
		int i =1;
		while( i<saco.size()){
			sucesores.add(saco.get(i));
			i = i + 3;
		}		
		return sucesores;
		
	}
	/**
	 * Descripcion: Dados dos vertices, verifica si uno es sucesor del otro
	 * @param VerticePadre Es el vertice con el cual vamos a comparar el otro vertice, como el padre
	 * @param VerticeSucesor Es el vertice al que no sabemos si es sucesor o no
	 * @return true si el VerticeSucesor es sucesor del VerticeInicial, false en caso contrario
	 */
	private boolean isSucesor(String VerticePadre, String VerticeSucesor){
		try{
		return this.sucesores.get(VerticePadre).contains(VerticeSucesor);
		}catch(NullPointerException a){
			return false;
		}
	}
	
	
	/**
	 * Descripcion: Busqueda por profundidad iterativo con el cual encuentra todos los caminos 
	 * entre las ciudades dadas.
	 * @param CiudadInicio Ciudad de Salida, vertice inicial
	 * @param CiudadFinal Ciudad de Llegada, vertice final
	 */
	public boolean dfs(String CiudadInicio, 
			String CiudadFinal, double maxPorViaje, double presupuesto){
		LinkedList<String> abiertos = new LinkedList<String>();
		LinkedList<String> camino = new LinkedList<String>();
		ArrayList<String> desechados = new ArrayList<String>();
		ArrayList<String> posibles = this.sucesores.get(CiudadInicio);
		LinkedList<String> ultimoCaminoPosible =  new LinkedList<String>();
		String Ultima_abierta;
		String UltimoCamino;
		String ciudadActual;
		abiertos.add(CiudadInicio);
		boolean encontroCamino=false;
		if(CiudadInicio.equals(CiudadFinal)){
			camino.add(CiudadInicio);
			if(caminoValido(camino,presupuesto,maxPorViaje)){
				encontroCamino = true;
			}
		}
		else{
			abiertos.remove(CiudadInicio);
			if (camino.size()!=0){
			if(!isSucesor(camino.getLast(),abiertos.getLast())){
				if (camino.size()!=0){
					camino.removeLast();
			}}}
			camino.add(CiudadInicio);
			for(int i=0;i<posibles.size();i++){
				abiertos.add(posibles.get(i));
			}
			while(!abiertos.isEmpty()){	
				if(!isSucesor(camino.getLast(),abiertos.getLast())){
									camino.removeLast();
				}
				
				ciudadActual = abiertos.removeLast();
				if(camino.size()!=0){
					
				if(this.sucesores.get(ciudadActual)!=null){
					abiertos.addAll(this.sucesores.get(ciudadActual));
					if(desechados.contains(ciudadActual)){
						camino.removeLast();
						abiertos.removeLast();
						
					}else{	
						if(ciudadActual.equals(CiudadFinal)){
							camino.add(ciudadActual);
								
							if (caminoValido(camino,presupuesto,maxPorViaje)){
								encontroCamino=true;
							}
							camino.remove(ciudadActual); 
							if(encontroCamino){
								break;
							}else{
								
							}
										
							if(!isSucesor(camino.getLast(),abiertos.getLast())){
								camino.removeLast();
							}
							if(!isSucesor(camino.getLast(),abiertos.getLast())){
								camino.removeLast();
							}
							if(!isSucesor(camino.getLast(),abiertos.getLast())){
								camino.removeLast();
							}

	
							
						}else{
							
							if(desechados.containsAll(this.sucesores.get(ciudadActual))){
								desechados.add(ciudadActual);
								camino.remove(ciudadActual);
								
							}else{
								camino.add(ciudadActual);
								//Rutina de eliminacion
		
								//Si no lo he encontrado
								//Y ciudadActual no tiene sucesores
								//Entonces no hay camino posible por esa ciudad
								//Tengo que eliminar esa ciudad 
								//y la agrega a desechados
								if(this.sucesores(ciudadActual).size()<=1){
									if(!ciudadActual.equals(CiudadFinal)){
										//Elimino lo elimino de las posiblidades pues
										//ya no puedo seguir por sus sucesores(NO TIENE)
										desechados.add(ciudadActual);
									if(!isSucesor(camino.getLast(),abiertos.getLast())){
										camino.removeLast();
									}
										
									}
									UltimoCamino = camino.getLast();
									if(abiertos.size()!=0){
										Ultima_abierta = abiertos.getLast();
										if(!isSucesor(UltimoCamino, Ultima_abierta)){
											//Elimino lo elimino del camino pues
											//ya no puedo seguir por sus sucesores
											camino.removeLast();
										}

		
										
									}else{
										//Paso, abierto esta vacio, se rompera la condicion del ciclo
									}
									
								}else{
									//Introduzco los sucesores de la ciudadActual a los abiertos
									
								}
							}
						}
				
				
					}
				}
				}else{
					break;
				}if(camino.size()==0){
					break;
				}
			}
		
		
		}
	
		return encontroCamino;
	}
		

		
	/**
	 * Descripcion: Muestra el camino si es valido y devuelve true, o
	 * en caso contrario no hace nada
	 * @param camino - LinkedList<String> El Camino de todas sus ciudades
	 * @param presupuesto - int Cantidad del Costo maximo del total del vaije
	 * @param maxPorViaje - int Cantidad del Costo maximo por pasaje
	 * @return boleean 
	 */
	private boolean caminoValido(LinkedList<String> camino,double presupuesto, 
			double maxPorViaje){
		String str="";
		int k,j = 0;
		double costoTotal = 0;
		double pasaje = 0;
		double costo =0;
		double CostoValido = 0;
		String pasaje_antes; //Antes de convertir el pasaje a un entero
		String costo_ciudad; //Antes de convertir de la estadia a un entero
		String id_ciudad;
		String id_sucesor;
		String id_transporte;
		ArrayList<String> verticeActual;
		LinkedList<String> respuesta= new LinkedList<String>();
		//System.out.println(str);
		int tam = camino.size();
		//System.out.println("Dentro camino valido\n"+camino);
		for(k=0;k<tam;k++){
			id_ciudad = camino.get(k);
			respuesta.add(id_ciudad);
			str = str + id_ciudad; //AGREGANDO CIUDAD
			verticeActual = this.ciudades.get(id_ciudad); //Busco la ciudad y su adyacentes
			costo_ciudad = verticeActual.get(0); // Busco el costo de la ciudad
			costo = Double.parseDouble(costo_ciudad); // Lo vuelvo un entero
			costoTotal = costoTotal + costo; //Lo sumo al costo
			if(k+1>=tam){
				//ESTOY EN EL ULTIMO ELEMENTO DEL CAMINO
			}else{
				//TODAVIA ME QUEDA CIUDADES
				id_sucesor = camino.get(k+1);
				j = verticeActual.indexOf(id_sucesor);
				//Encuentro el costo del pasaje
				pasaje_antes = verticeActual.get(j+2);
				try{
				pasaje = Double.parseDouble(pasaje_antes);
				}catch(NumberFormatException a){
					System.out.println("\nERROR\n");
					System.out.println(camino);
					System.out.println(id_sucesor);
					System.out.println(verticeActual);
					System.out.println(verticeActual.get(j+2));
					System.exit(0);
				}
				if(pasaje <= maxPorViaje){
					//Podría funcionar
					id_transporte = verticeActual.get(j+1);
					respuesta.add(id_transporte);
					costoTotal = costoTotal + pasaje;
					str=str+", "+id_transporte+", ";
				}else{
					//BORRO TODO EL CAMINO 
					respuesta.clear();
					break;
				}
			}
			
			
			}
			if(costoTotal>presupuesto){
				respuesta.clear();
				return false;
			}else{
				CostoValido = presupuesto - costoTotal;
				respuesta.add(String.valueOf(CostoValido));
				System.out.println("\n"+str);
				System.out.println(CostoValido+"\n");
				return true;
			}

				
			
	}	
		
		
		

	
	
	
	
	
	
	/**
	 * Muestra el grafo Vertice: sucesor_1, sucesor_2, ..., sucesor_n
	 */
	
	public String toString(){
		
		Set<String> ABS = this.ciudades.keySet();
		String[] idCiudades = new String[ABS.size()];
		String respuesta ="";
		ArrayList <String> sucesor;
		for(int i=0;i<idCiudades.length;i++){
			respuesta += "["+idCiudades[i]+": ";
			try{
			sucesor = this.sucesores.get(idCiudades[i]);
			for(int j=0;j<sucesor.size();j++){
				if(sucesor.get(i).equals(sucesor.get(sucesor.size()-1))){
					respuesta += sucesor.get(i) + " ]";
				} else{
					respuesta += sucesor.get(i)+" , ";
				}	
			}
			}catch(NullPointerException a){	}
		}		
		return respuesta;
	}
	
	
}
	

		
	
	

	
