package Personal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;



/**La clase 'Persona' es una generalizaci�n de todos los individuos que forman parte del sistema universitario. Como tales, est�n identificadas mediante un identificador �nico en el sistema, su nombre, sus apellidos y su fecha de nacimiento.
 * El prop�sito de esta clase no es otro que la generalizaci�n de los individuos. No obstante, no habr� 'personas' como tales en el sistema, pues todos tendr�n un 'tipo' de persona al que pertenecer�n, tal que 'Alumnos' y 'Profesores'.
 * La clase no ha sido declarada como abstracta porque se emplear� para obtener gente de una manera directa de los listados censoriales. En el sistema jam�s encontraremos a una 'Persona' como tal.
 * 
 * A priori, no existe un l�mite definido sobre la cantidad de personas que pueden estar incluidas en el sistema.
 * 
 * @author Pedro Tub�o Figueira
 * @author Jose �ngel Regueiro Janeiro 
 * */


public class Persona {

	
	/**Nombre de la persona.*/
	private String nombre;
	
	/**Apellidos de la persona.*/
	private String apellidos;
	
	@SuppressWarnings("unused")
	/**Fecha de nacimiento de la persona. El formato ser� tal que dd/MM/yyyy.*/
	private Date fechaNacimiento;
	
	/**Identificador �nico de la persona en el sistema. El identificador empezar� siempre a partir del 1.*/
	private int ID;
	
	/**Es una variable auxiliar que almacena la fecha de nacimiento en formato String, para comodidad del sistema.*/
	private String fechaComoString;
	
	/**Constructor vac�o usada por el 'super()' de 'Profesor' para la implementaci�n correcta de la interfaz de comparaci�n. Tambi�n se utiliza como gen�rico a la hora de extraer gente del 'Censo'.*/
	public Persona(){
		
	}
	
	
	/**Constructor de superclase que inserta los par�metros siguientes en las variables correspondientes. Se referencia de las subclases correspondientes cuando se crea un objeto de las mismas y se requiere la edici�n de dichas variables.
	 * 
	 * @param nombre Nombre de la persona.
	 * @param apellidos Apellidos de la persona.
	 * @param fechaNacimiento Fecha de nacimiento de la persona.
	 * @param ID Identificador �nico en el sistema de la persona
	 * */
	public Persona (String nombre, String apellidos, String fechaNacimiento, int ID){
		this.nombre=nombre;
		this.apellidos=apellidos;
		fechaComoString=fechaNacimiento;
		setFechaNacimiento(fechaNacimiento);
		this.ID=ID;
			
	}
	
	/**M�todo con el que obtenemos el nombre de la persona.
	 * 
	 * @return Nombre de la persona.*/
	public String getNombre(){
		return this.nombre;
	}
	
	/**M�todo con el que obtenemos los apellidos de la persona.
	 * 
	 * @return Apellidos de la persona.*/
	public String getApellidos(){
		return this.apellidos;
	}
	
	/**M�todo con el que obtenemos la fecha de nacimiento de la persona.
	 * 
	 * @return Fecha de nacimiento de la persona.*/
	public String getFechaNacimiento(){
		return this.fechaComoString;
	}
	
	/**M�todo con el que obtenemos el identificador �nico en el sistema de la persona.
	 * 
	 * @return Identificador �nico de la persona.*/
	public int getID(){
		return this.ID;
	}
	
	/**M�todo de uso interno que transforma la fecha de nacimiento, de formato String, a formato Date.
	 * 
	 * @param fechaNacimiento Fecha de nacimiento de la persona.*/
	private void setFechaNacimiento(String fechaNacimiento){
		 SimpleDateFormat fechaTexto = new SimpleDateFormat("dd/MM/yyyy");
		 
		 try{
			 this.fechaNacimiento=fechaTexto.parse(fechaNacimiento);
		 } catch (ParseException e){
			 
		 }
		 
	}
}
