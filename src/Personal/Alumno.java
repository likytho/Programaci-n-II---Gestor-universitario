package Personal;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeMap;

import Docencia.Asignatura;
import Docencia.Grupo;
import Secretaria.Matriculable;

/**Los alumnos son aquellas personas que reciben docencia en el Centro Universitario. 
 * 
 * En lo que a ellos se refiere, se matriculan en las distintas asignaturas de la oferta docente del centro y luego, se les asigna o seleccionan unos grupos (tanto te�ricos como pr�cticos) determinados para poder cumplir con el objetivo de la docencia.
 * El proceso de matriculaci�n viene dado por la interfaz 'Matriculable', y est� restringido por dos variables: los prerrequisitos de las asignaturas y el solapamiento con otros grupos.
 * 
 * @author Pedro Tub�o Figueira
 * @author Jose �ngel Regueiro Janeiro*/

public class Alumno extends Persona implements Matriculable {
	
	@SuppressWarnings("unused")
	/**Fecha de ingreso a la titulaci�n. Es la fecha en la que el alumno ha sido admitido oficialmente para cursar la titulaci�n.*/
	private Date fechaIngresoTitulacion;
	
	/**Nota media del expediente del alumno. Nota comprendida entre 5 y 10 que registra la media del hist�rico de calificaciones del alumno. */
	private double notaMediaExpediente;
	
	/**Fecha de ingreso a la titulaci�n como String. El formato es de dd/MM/yyyy, y es utilizada para facilitar lectura y escritura de archivos.*/
	private String fechaIngresoComoString;
	
	/**Variable que almacena los grupos de Teor�a a los que asiste el alumno. Este listado est� ordenado en funci�n del ID de cada asignatura.*/
	TreeMap <Integer, Grupo> gruposTeoria = new TreeMap <Integer, Grupo>();
	
	/**Variable que almacena los grupos de Pr�cticas a los que asiste el alumno. Este listado est� ordenado en funci�n del ID de cada asignatura.*/
	TreeMap <Integer, Grupo> gruposPractica = new TreeMap <Integer, Grupo>();
	
	/**Variable que almacena las asignaturas superadas que tiene el alumno. El listado est� ordenado en funci�n del ID de cada asignatura.*/
	TreeMap <Integer, Asignatura> asignaturasSuperadas = new TreeMap <Integer, Asignatura>(); 
	
	/**Variable que almacena las asignaturas en las que se encuentra matriculado el alumno. El listado est� ordenado en funci�n del ID de cada asignatura.*/
	TreeMap <Integer, Asignatura> asignaturasMatriculadas = new TreeMap<Integer, Asignatura>(); 

	
	/**Constructor que genera un alumno con sus datos al completo. Utilizado en la lectura de archivos y en la edici�n de los par�metros pertinentes, adem�s de para obtener variables del censo.
	 * 
	 * @param nombre Nombre del alumno.
	 * @param apellidos Apellidos del alumno.
	 * @param fechaNacimiento Fecha de nacimiento del alumno.
	 * @param ID Identificador �nico del alumno.
	 * @param fechaIngresoTitulacion Fecha de ingreso a la titulaci�n.
	 * @param notaMediaExpediente Nota media del expediente del alumno.*/
	public Alumno(String nombre, String apellidos, String fechaNacimiento,int ID, String fechaIngresoTitulacion, double notaMediaExpediente) {
		super(nombre, apellidos, fechaNacimiento, ID);
		setFechaIngresoTitulacion(fechaIngresoTitulacion);
		fechaIngresoComoString=fechaIngresoTitulacion;
		this.notaMediaExpediente=notaMediaExpediente;
	}

	
	/**M�todo que devuelve el listado con los grupos de teor�a a los que asiste el alumno.
	 * 
	 * @return Listado con los grupos de teor�a a los que asiste el alumno.*/
	public TreeMap <Integer, Grupo> getGruposTeoria(){
		return this.gruposTeoria;
	}
	
	/**M�todo que devuelve el listado con los grupos de pr�ctica a los que asiste el alumno.
	 * 
	 * @return Listado con los grupos de pr�ctica a los que asiste el alumno.*/
	public TreeMap <Integer, Grupo> getGruposPractica(){
		return this.gruposPractica;
	}
	
	/**M�todo que devuelve el listado con las asignaturas que el alumno ya ha superado en la titulaci�n.
	 * 
	 * @return Listado con las asignaturas superadas.*/
	public TreeMap <Integer, Asignatura> getAsignaturasSuperadas(){
		return this.asignaturasSuperadas;
	}
	
	/**M�todo que devuelve el listado con las asignaturas en las que el alumno se encuentra matriculado en ese momento. 
	 * 
	 * @return Listado de asignaturas matriculadas.*/
	public TreeMap <Integer, Asignatura> getAsignaturasMatriculadas(){
		return this.asignaturasMatriculadas;
	}
	
	/**M�todo que devuelve la fecha en la que el alumno ingres� en la titulaci�n, con formato dd/MM/yyyy.
	 * 
	 * @return Fecha de acceso a la titulaci�n (dd/MM/yyyy).*/
	public String getFechaIngresoTitulacion(){
		return this.fechaIngresoComoString;
	}
	
	/**M�todo que devuelve la nota media del expediente del alumno.
	 * 
	 * @return Nota media del expediente del alumno.*/
	public double getNotaMediaExpediente(){
		return this.notaMediaExpediente;
	}
	
	/**M�todo intero que transforma la fecha en un Date.
	 * 
	 * @param fechaIngresoTitulacion Fecha de ingreso del alumno a la titulaci�n.*/
	private void setFechaIngresoTitulacion(String fechaIngresoTitulacion){
		SimpleDateFormat fechaTexto = new SimpleDateFormat("dd/MM/yyyy");
		
		 try{
			 this.fechaIngresoTitulacion=fechaTexto.parse(fechaIngresoTitulacion);
		 } catch (ParseException e){
			 
		 }
	}

	/**M�todo que inserta una asignatura como superada en el historial del alumno.�
	 * 
	 * @param asignatura Asignatura que el alumno ha superado con �xito.*/
	public void setAsignaturaSuperada(Asignatura asignatura){
		asignaturasSuperadas.put(new Integer(asignatura.getID()), asignatura);
	}

	/**M�todo que matricula al alumno en la asignatura correspondiente. Este m�todo viene heredado de la interfaz 'Matriculable'.
	 * 
	 * @param asignatura Asignatura a matricular.*/
	public void matricularAsignatura(Asignatura asignatura) {
		asignaturasMatriculadas.put(asignatura.getID(), asignatura);
	}
	
	/**M�todo que permite conocer si un alumno est� matriculado en una asignatura concreta.
	 * 
	 * @param ID ID de la asignatura a comprobar.
	 * @return Afirmaci�n o negaci�n sobre si el alumno se encuentra matriculado en dicha asignatura.*/
	public boolean getEstaMatriculado(int ID){
		if(asignaturasMatriculadas.containsKey(ID)) return true;
		
		return false;
	}
	
	/**M�todo que actualiza la nota media de un alumno tras haber superado una asignatura.
	 * 
	 * @param notaEvaluacion Nota de la asignatura superada.*/
	public void actualizarNota(double notaEvaluacion){
		int n = asignaturasSuperadas.size();

		DecimalFormat decimales = (DecimalFormat) DecimalFormat.getInstance();
		decimales.applyPattern("#.##");
		
		
		notaMediaExpediente=((n*notaMediaExpediente + notaEvaluacion)/(n+1));
		
		notaMediaExpediente=Double.parseDouble(decimales.format(notaMediaExpediente).replace(",","."));
		

	}
	
	
	/**M�todo que devuelve una lista de los IDs de las asignaturas que el alumno ha superado.
	 * 
	 * @return Listado de IDs de las asignaturas superadas.*/
	public String getListaIDsSuperadas(){
		
		String lista="";;
		
		
		Iterator<Integer> iterador = asignaturasSuperadas.keySet().iterator();
		
		while(iterador.hasNext()){
			lista += (Integer) iterador.next();
			if(iterador.hasNext()) lista += ", ";
		}
		
		iterador=null;
		return lista;
		
	}
	
	/**M�todo que devuelve una lista con los IDs de las asignaturas en las que el alumno est� matriculado.
	 * 
	 * @return Listado de IDs de las asignaturas matriculadas.*/
	public String getListaIDsMatriculadas(){
		
		String lista;
		
		lista = "Asignaturas Matriculadas: ";
		Iterator<Integer> iterador = asignaturasMatriculadas.keySet().iterator();
		
		while(iterador.hasNext()){
			lista += (Integer) iterador.next() + ", ";
		}
		
		iterador=null;
		return lista;
		
	}
	
	/**M�todo que devuelve el listado de los grupos a los que el alumno asiste a recibir docencia.
	 * 
	 * @return Listado de grupos asociados al alumno.*/
	public String getListaGrupos(){
		Iterator<Integer> iterador = gruposTeoria.keySet().iterator();
		String retorno="";
		
		while (iterador.hasNext()){
			Grupo grupo = gruposTeoria.get(iterador.next());
			
			retorno += grupo.getIDAsignatura() + " " + grupo.getDia() + " " + grupo.getHora() + " " + "Profesor : " + grupo.getIDDocente() + " (" + grupo.getDuracionGrupo() + ") || ";
			
		}
		
		
		iterador = gruposPractica.keySet().iterator();
		
		while (iterador.hasNext()){
			Grupo grupo = gruposPractica.get(iterador.next());
			
			retorno += grupo.getIDAsignatura() + " " + grupo.getDia() + " " + grupo.getHora() + " " + "Profesor : " + grupo.getIDDocente() + " (" + grupo.getDuracionGrupo() + ") || ";
			
		}
		
		iterador=null;
		
		return retorno;
	}
	
	
}
