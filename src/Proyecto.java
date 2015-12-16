import Secretaria.Censo;
import Secretaria.Funcion;



/**El 'Gestor' es el encargado de realizar todas aquellas operaciones pertinentes para mantener el orden en el Centro Universitario. Su función, en pocas palabras, se traduce en organizar el centro, realizando las modificaciones pertinetnes en los archivos que contienen la información relativa al personal y a las asignaturas. 
 * 
 * @author Pedro Tubío Figueira
 * @author Jose Ángel Regueiro Janeiro*/


public class Proyecto {

	/**Método que realiza las operaciones pertinentes. Es el 'cerebro' del sistema. Genera un censo en el que almacena los datos preexistentes, los modifica a gusto del usuario y luego los vuelve a almacenar.
	 * 
	 * @param args none*/
	public static void main (String[] args){

		Censo censo = new Censo ();

		Funcion.leerPersonas(censo);
		Funcion.leerAsignaturas(censo);
		Funcion.leerEjecucion(censo);	
		Funcion.guardarPesonas(censo);
		Funcion.guardarAsignaturas(censo);
		
	}


}