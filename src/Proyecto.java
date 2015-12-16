import Secretaria.Censo;
import Secretaria.Funcion;



/**El 'Gestor' es el encargado de realizar todas aquellas operaciones pertinentes para mantener el orden en el Centro Universitario. Su funci�n, en pocas palabras, se traduce en organizar el centro, realizando las modificaciones pertinetnes en los archivos que contienen la informaci�n relativa al personal y a las asignaturas. 
 * 
 * @author Pedro Tub�o Figueira
 * @author Jose �ngel Regueiro Janeiro*/


public class Proyecto {

	/**M�todo que realiza las operaciones pertinentes. Es el 'cerebro' del sistema. Genera un censo en el que almacena los datos preexistentes, los modifica a gusto del usuario y luego los vuelve a almacenar.
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