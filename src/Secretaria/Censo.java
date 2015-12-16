package Secretaria;

import java.util.TreeMap;

import Docencia.Asignatura;
import Personal.Persona;

/**El 'Censo' es el lugar donde se encuentran identificados todos los ítems relativos a un sistema. En este caso, aquí encontraremos los listados que congregan a las personas y a las asignaturas, identificadas por su ID.
 * 
 * @author Pedro Tubío Figueira
 * @author Jose Ángel Regueiro Janeiro*/

public class Censo {

	/**Listado donde se encuentran identificadas y ordenadas todas las asignaturas del sistema.*/
	private TreeMap<Integer, Asignatura> asignaturas = new TreeMap<Integer, Asignatura>();
	
	/**Listado donde se encuentran identificadas y ordenadas todas las personas del sisetma.*/
	private TreeMap<Integer, Persona> personas = new TreeMap<Integer, Persona>();
	
	/**Constructor que genera las listas necesarias para almacenar a las personas y a las asignaturas.*/
	public Censo(){

	}
	
	/**Método que devuelve el listado con las asignaturas.
	 * 
	 * @return Listado con las asignaturas.*/
	public TreeMap<Integer, Asignatura> getAsignaturas(){
		return this.asignaturas;
	}
	
	/**Método que devuelve el listado con las personas.
	 * 
	 * @return Listado con las personas.*/
	public TreeMap<Integer, Persona> getPersonas(){
		return this.personas;
	}
}
