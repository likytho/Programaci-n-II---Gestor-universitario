package Docencia;

import java.util.TreeMap;


/**Las 'Asignaturas' en conjunto es lo que componen la oferta docente del Centro Universitario. Son impartidas por el profesorado y a ellas asiste el alumnado del mismo. 
 * Las asignaturas se caracterizan, principalmente, por su nombre y sus siglas (adem�s de un identificador �nico), aunque pueden tener, a mayores una serie de 'prerrequisitos' (o asignaturas llave) que, previamente, el alumno deber� cumplir antes de matricularse en dicha asignatura.
 * 
 * @author Pedro Tub�o Figueira
 * @author Jose �ngel Regueiro Janeiro
 * */



public class Asignatura implements Comparable<Object>{

	/**Identificador �nico de la asignatura en el sistema.*/
	private int ID;
	
	/**Nombre de la asignatura.*/
	private String nombre;
	
	/**Siglas de la asignatura.*/
	private String siglas;
	
	/**Identificador del coordinador de la asignatura. En caso de que la asignatura no tenga coordinador, su valor ser� '0'.*/
	private int IDCoordinador;
	
	/**Prerrequisitos necesarios para poder matricularse de la asignatura. En caso de no existir, esta variable se anular�.*/
	private String [] prerrequisitos;


	/**Listado de los grupos de car�cter 'te�rico' que tiene la asignatura. Se identifican por el ID propio de cada uno de ellos.*/
	private TreeMap <Integer, Grupo> listaGruposTeoricos = new TreeMap<Integer, Grupo>();
	
	/**Listado de los grupos de car�cter 'pr�ctico' que tiene  la asignatura. Se identifican por el ID propio de cada uno de ellos.*/
	private TreeMap <Integer, Grupo> listaGruposPracticos = new TreeMap<Integer, Grupo>();
	
	
	/**Constructor que genera una nueva asignatura al iniciarse el sistema y recoger la informaci�n pertinente de los archivos de entrada. El 'Gestor' jam�s generar� nuevas asignaturas, ajenas a las existentes en el sistema, dadas en el archivo de entrada.
	 * 
	 *@param ID Identificador de la asignatura.
	 *@param nombre Nombre de la asignatura.
	 *@param siglas Siglas de la asignatura.
	 *@param IDCoordinador Identificador del coordinador de la asignatura. En caso de no tener, la variable de forma interna ser� de '0'
	 *@param prerrequisitos Prerrequisitos a superar para poder matricularse de la asignatura. En caso de no existir, la variable se anular�.*/
	public Asignatura(int ID, String nombre, String siglas, int IDCoordinador, String [] prerrequisitos){
		this.ID=ID;
		this.nombre=nombre;
		this.siglas=siglas;
		this.IDCoordinador=IDCoordinador;
		this.prerrequisitos=prerrequisitos;


	}
	
	/**Constructor s�lo usado cuando se lee el archivo que contiene a las personas, para mantener la jerarqu�a de relaci�n existente en el Centro entre las personas y las asignaturas. Para completar su utilidad, est�n los getters y setters pertinentes.
	 * 
	 * @param ID Identificador de la asignatura.*/
	public Asignatura (int ID){
		this.ID=ID;
	}

	/**M�todo empleado para extraer de la asignatura el listado con los grupos te�ricos pertenecientes a la misma y poder trabajar sobre el mismo.
	 * 
	 * @return Listado con los grupos te�ricos pertenecientes a la asignatura.*/
	public TreeMap<Integer, Grupo> getListaGruposTeoricos(){
		return this.listaGruposTeoricos;
	}
	
	/**M�todo empleado para extraer de la asignatura el listado con los grupos pr�cticos pertenecientes a la misma y poder trabajar sobre el mismo.
	 * 
	 * @return Listado con los grupos pr�cticos pertenecientes a la asignatura.*/
	public TreeMap<Integer, Grupo> getListaGruposPracticos(){
		return this.listaGruposPracticos;
	}
	
	
	/**M�todo empleado para obtener el identificador de la asignatura.
	 * 
	 * @return Identificador de la asignatura.*/
	public int getID(){
		return this.ID;
	}
	
	
	/**M�todo empleado para obtener el nombre de la asignatura.
	 * 
	 * @return Nombre de la asignatura.*/
	public String getNombre(){
		return this.nombre;
	}
	
	
	/**M�todo empleado para obtener las siglas de la asignatura.
	 * 
	 * @return Siglas de la asignatura.*/
	public String getSiglas(){
		return this.siglas;
	}
	
	/**M�todo empleado para obtener el identificador del coordinador de la asignatura. Este ser� '0' si la asignatura no tiene coordinador.
	 * 
	 * @return Identificador del coordinador de la asignatura.*/
	public int getIDCoordinador(){
		return this.IDCoordinador;
	}
	
	/**M�todo empleado para obtener los prerrequisitos de la asignatura. Estos ser�n nulos si la asignatura no tiene prerrequisitos.
	 * 
	 * @return Prerrequisitos necesarios para matricularse en la asignatura.*/
	public String[] getPrerrequisitos(){
		return this.prerrequisitos;
	}

	/**M�todo empleado para registrar el nombre de la asignatura.
	 * 
	 * @param nombre Nombre de la asignatura.*/
	public void setNombre(String nombre){
		this.nombre=nombre;
	}

	/**M�todo empleado para registrar las siglas de la asignatura.
	 * 
	 * @param siglas Siglas de la asignatura.*/
	public void setSiglas(String siglas){
		this.siglas=siglas;
	}
	
	/**M�todo empleado para registar el ID del coordinador de la asignatura.
	 * 
	 * @param ID ID del coordinador de la asignatura.*/
	public void setCoordinador(int ID){
		IDCoordinador=ID;
	}
	
	/**M�todo empleado para registrar los prerrequisitos de la asignatura. 
	 * 
	 * @param prerrequisitos Prerrequisitos de la asignatura.*/
	public void setPrerrequisitos (String [] prerrequisitos){
		this.prerrequisitos=prerrequisitos;
	}

	/**M�todo autogenerado por la interfaz comparable.
	 * 
	 * @param arg0 none
	 * @return none*/
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}

