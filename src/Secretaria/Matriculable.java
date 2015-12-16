package Secretaria;

import Docencia.Asignatura;


/**Interfaz que implementa la relación de realización de un alumno con una asignatura.
 * 
 * @author Pedro Tubío Figueira
 * @author Jose Ángel Regueiro Janeiro*/

public interface Matriculable {
	
	/**Método que matricula al alumno en la asignatura correspondiente.
	 * 
	 * @param asignatura Asignatura a matricular.*/
	public abstract void matricularAsignatura(Asignatura asignatura);
	
}
