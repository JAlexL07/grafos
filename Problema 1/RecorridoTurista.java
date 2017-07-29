import java.io.IOException;




public class RecorridoTurista {

	public static void main(String[] args) {	
		if(args.length!=5){
			
			System.out.println("Error. No hay suficientes argumentos");
			System.out.println("Introduzca java RecorridoTurista <origen> <destino>"
					+ "<presupuesto> <maxPorViaje> <archivo>");
			System.exit(0);
		}else{
			try {
				Grafo grafo = new Grafo(args[4]);
				Double presupuesto = Double.parseDouble(args[2]);
				Double maxPorViaje = Double.parseDouble(args[3]);
				boolean respuesta = grafo.dfs(args[0],args[1],maxPorViaje,presupuesto);
				
				if(!respuesta){
					System.out.println("\nNo existe ruta hacia esas ciudades");
					System.out.println(args[2]);
				}
				
			}
				catch(IOException e) {
				System.out.println("Error en el formato del archivo");
				e.printStackTrace();
				System.exit(0);
			} catch(FileNotFoundException a){
				System.out.println("Error en el formato del archivo");
				a.printStackTrace();
				System.exit(0);
			}
			
			
			
			
			
		}

	}

}
