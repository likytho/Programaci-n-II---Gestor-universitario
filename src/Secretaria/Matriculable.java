package Secretaria;

import Docencia.Asignatura;


/**Interfaz que implementa la relaci�n de realizaci�n de un alumno con una asignatura.
 * 
 * @author Pedro Tub�o Figueira
 * @author Jose �ngel Regueiro Janeiro*/

public interface Matriculable {
	
	/**M�todo que matricula al alumno en la asignatura correspondiente.
	 * 
	 * @param asignatura Asignatura a matricular.*/
	public abstract void matricularAsignatura(Asignatura asignatura);
	
}
