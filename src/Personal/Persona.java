package Personal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;



/**La clase 'Persona' es una generalización de todos los individuos que forman parte del sistema universitario. Como tales, están identificadas mediante un identificador único en el sistema, su nombre, sus apellidos y su fecha de nacimiento.
 * El propósito de esta clase no es otro que la generalización de los individuos. No obstante, no habrá 'personas' como tales en el sistema, pues todos tendrán un 'tipo' de persona al que pertenecerán, tal que 'Alumnos' y 'Profesores'.
 * La clase no ha sido declarada como abstracta porque se empleará para obtener gente de una manera directa de los listados censoriales. En el sistema jamás encontraremos a una 'Persona' como tal.
 * 
 * A priori, no existe un límite definido sobre la cantidad de personas que pueden estar incluidas en el sistema.
 * 
 * @author Pedro Tubío Figueira
 * @author Jose Ángel Regueiro Janeiro 
 * */


public class Persona {

	
	/**Nombre de la persona.*/
	private String nombre;
	
	/**Apellidos de la persona.*/
	private String apellidos;
	
	@SuppressWarnings("unused")
	/**Fecha de nacimiento de la persona. El formato será tal que dd/MM/yyyy.*/
	private Date fechaNacimiento;
	
	/**Identificador único de la persona en el sistema. El identificador empezará siempre a partir del 1.*/
	private int ID;
	
	/**Es una variable auxiliar que almacena la fecha de nacimiento en formato String, para comodidad del sistema.*/
	private String fechaComoString;
	
	/**Constructor vacío usada por el 'super()' de 'Profesor' para la implementación correcta de la interfaz de comparación. También se utiliza como genérico a la hora de extraer gente del 'Censo'.*/
	public Persona(){
		
	}
	
	
	/**Constructor de superclase que inserta los parámetros siguientes en las variables correspondientes. Se referencia de las subclases correspondientes cuando se crea un objeto de las mismas y se requiere la edición de dichas variables.
	 * 
	 * @param nombre Nombre de la persona.
	 * @param apellidos Apellidos de la persona.
	 * @param fechaNacimiento Fecha de nacimiento de la persona.
	 * @param ID Identificador único en el sistema de la persona
	 * */
	public Persona (String nombre, String apellidos, String fechaNacimiento, int ID){
		this.nombre=nombre;
		this.apellidos=apellidos;
		fechaComoString=fechaNacimiento;
		setFechaNacimiento(fechaNacimiento);
		this.ID=ID;
			
	}
	
	/**Método con el que obtenemos el nombre de la persona.
	 * 
	 * @return Nombre de la persona.*/
	public String getNombre(){
		return this.nombre;
	}
	
	/**Método con el que obtenemos los apellidos de la persona.
	 * 
	 * @return Apellidos de la persona.*/
	public String getApellidos(){
		return this.apellidos;
	}
	
	/**Método con el que obtenemos la fecha de nacimiento de la persona.
	 * 
	 * @return Fecha de nacimiento de la persona.*/
	public String getFechaNacimiento(){
		return this.fechaComoString;
	}
	
	/**Método con el que obtenemos el identificador único en el sistema de la persona.
	 * 
	 * @return Identificador único de la persona.*/
	public int getID(){
		return this.ID;
	}
	
	/**Método de uso interno que transforma la fecha de nacimiento, de formato String, a formato Date.
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
