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
 * En lo que a ellos se refiere, se matriculan en las distintas asignaturas de la oferta docente del centro y luego, se les asigna o seleccionan unos grupos (tanto teóricos como prácticos) determinados para poder cumplir con el objetivo de la docencia.
 * El proceso de matriculación viene dado por la interfaz 'Matriculable', y está restringido por dos variables: los prerrequisitos de las asignaturas y el solapamiento con otros grupos.
 * 
 * @author Pedro Tubío Figueira
 * @author Jose Ángel Regueiro Janeiro*/

public class Alumno extends Persona implements Matriculable {
	
	@SuppressWarnings("unused")
	/**Fecha de ingreso a la titulación. Es la fecha en la que el alumno ha sido admitido oficialmente para cursar la titulación.*/
	private Date fechaIngresoTitulacion;
	
	/**Nota media del expediente del alumno. Nota comprendida entre 5 y 10 que registra la media del histórico de calificaciones del alumno. */
	private double notaMediaExpediente;
	
	/**Fecha de ingreso a la titulación como String. El formato es de dd/MM/yyyy, y es utilizada para facilitar lectura y escritura de archivos.*/
	private String fechaIngresoComoString;
	
	/**Variable que almacena los grupos de Teoría a los que asiste el alumno. Este listado está ordenado en función del ID de cada asignatura.*/
	TreeMap <Integer, Grupo> gruposTeoria = new TreeMap <Integer, Grupo>();
	
	/**Variable que almacena los grupos de Prácticas a los que asiste el alumno. Este listado está ordenado en función del ID de cada asignatura.*/
	TreeMap <Integer, Grupo> gruposPractica = new TreeMap <Integer, Grupo>();
	
	/**Variable que almacena las asignaturas superadas que tiene el alumno. El listado está ordenado en función del ID de cada asignatura.*/
	TreeMap <Integer, Asignatura> asignaturasSuperadas = new TreeMap <Integer, Asignatura>(); 
	
	/**Variable que almacena las asignaturas en las que se encuentra matriculado el alumno. El listado está ordenado en función del ID de cada asignatura.*/
	TreeMap <Integer, Asignatura> asignaturasMatriculadas = new TreeMap<Integer, Asignatura>(); 

	
	/**Constructor que genera un alumno con sus datos al completo. Utilizado en la lectura de archivos y en la edición de los parámetros pertinentes, además de para obtener variables del censo.
	 * 
	 * @param nombre Nombre del alumno.
	 * @param apellidos Apellidos del alumno.
	 * @param fechaNacimiento Fecha de nacimiento del alumno.
	 * @param ID Identificador único del alumno.
	 * @param fechaIngresoTitulacion Fecha de ingreso a la titulación.
	 * @param notaMediaExpediente Nota media del expediente del alumno.*/
	public Alumno(String nombre, String apellidos, String fechaNacimiento,int ID, String fechaIngresoTitulacion, double notaMediaExpediente) {
		super(nombre, apellidos, fechaNacimiento, ID);
		setFechaIngresoTitulacion(fechaIngresoTitulacion);
		fechaIngresoComoString=fechaIngresoTitulacion;
		this.notaMediaExpediente=notaMediaExpediente;
	}

	
	/**Método que devuelve el listado con los grupos de teoría a los que asiste el alumno.
	 * 
	 * @return Listado con los grupos de teoría a los que asiste el alumno.*/
	public TreeMap <Integer, Grupo> getGruposTeoria(){
		return this.gruposTeoria;
	}
	
	/**Método que devuelve el listado con los grupos de práctica a los que asiste el alumno.
	 * 
	 * @return Listado con los grupos de práctica a los que asiste el alumno.*/
	public TreeMap <Integer, Grupo> getGruposPractica(){
		return this.gruposPractica;
	}
	
	/**Método que devuelve el listado con las asignaturas que el alumno ya ha superado en la titulación.
	 * 
	 * @return Listado con las asignaturas superadas.*/
	public TreeMap <Integer, Asignatura> getAsignaturasSuperadas(){
		return this.asignaturasSuperadas;
	}
	
	/**Método que devuelve el listado con las asignaturas en las que el alumno se encuentra matriculado en ese momento. 
	 * 
	 * @return Listado de asignaturas matriculadas.*/
	public TreeMap <Integer, Asignatura> getAsignaturasMatriculadas(){
		return this.asignaturasMatriculadas;
	}
	
	/**Método que devuelve la fecha en la que el alumno ingresó en la titulación, con formato dd/MM/yyyy.
	 * 
	 * @return Fecha de acceso a la titulación (dd/MM/yyyy).*/
	public String getFechaIngresoTitulacion(){
		return this.fechaIngresoComoString;
	}
	
	/**Método que devuelve la nota media del expediente del alumno.
	 * 
	 * @return Nota media del expediente del alumno.*/
	public double getNotaMediaExpediente(){
		return this.notaMediaExpediente;
	}
	
	/**Método intero que transforma la fecha en un Date.
	 * 
	 * @param fechaIngresoTitulacion Fecha de ingreso del alumno a la titulación.*/
	private void setFechaIngresoTitulacion(String fechaIngresoTitulacion){
		SimpleDateFormat fechaTexto = new SimpleDateFormat("dd/MM/yyyy");
		
		 try{
			 this.fechaIngresoTitulacion=fechaTexto.parse(fechaIngresoTitulacion);
		 } catch (ParseException e){
			 
		 }
	}

	/**Método que inserta una asignatura como superada en el historial del alumno.´
	 * 
	 * @param asignatura Asignatura que el alumno ha superado con éxito.*/
	public void setAsignaturaSuperada(Asignatura asignatura){
		asignaturasSuperadas.put(new Integer(asignatura.getID()), asignatura);
	}

	/**Método que matricula al alumno en la asignatura correspondiente. Este método viene heredado de la interfaz 'Matriculable'.
	 * 
	 * @param asignatura Asignatura a matricular.*/
	public void matricularAsignatura(Asignatura asignatura) {
		asignaturasMatriculadas.put(asignatura.getID(), asignatura);
	}
	
	/**Método que permite conocer si un alumno está matriculado en una asignatura concreta.
	 * 
	 * @param ID ID de la asignatura a comprobar.
	 * @return Afirmación o negación sobre si el alumno se encuentra matriculado en dicha asignatura.*/
	public boolean getEstaMatriculado(int ID){
		if(asignaturasMatriculadas.containsKey(ID)) return true;
		
		return false;
	}
	
	/**Método que actualiza la nota media de un alumno tras haber superado una asignatura.
	 * 
	 * @param notaEvaluacion Nota de la asignatura superada.*/
	public void actualizarNota(double notaEvaluacion){
		int n = asignaturasSuperadas.size();

		DecimalFormat decimales = (DecimalFormat) DecimalFormat.getInstance();
		decimales.applyPattern("#.##");
		
		
		notaMediaExpediente=((n*notaMediaExpediente + notaEvaluacion)/(n+1));
		
		notaMediaExpediente=Double.parseDouble(decimales.format(notaMediaExpediente).replace(",","."));
		

	}
	
	
	/**Método que devuelve una lista de los IDs de las asignaturas que el alumno ha superado.
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
	
	/**Método que devuelve una lista con los IDs de las asignaturas en las que el alumno está matriculado.
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
	
	/**Método que devuelve el listado de los grupos a los que el alumno asiste a recibir docencia.
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
