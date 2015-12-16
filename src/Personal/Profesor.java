package Personal;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeMap;

import Docencia.Asignatura;
import Docencia.Grupo;

/**Los profesores son aquellas personas encargadas de impartir docencia en el Centro Universitario, proceso básico del mismo.
 *Por un lado, tendremos dos tipos de profesores (en función de su categoría), que serán 'Titulares' e 'Interinos'. Los titulares, además de impartir docencia, tendrán la posibilidad de coordinar las asignaturas. 
 *Cada profesor tendrá una dedicación semanal (en horas) que determinará el número máximo de horas que un profesor puede dedicar a impartir docencia. Este número quedará determinado en función de la categoría del profesor.
 *
 *@author Pedro Tubío Figueira
 *@author Jose Ángel Regueiro Janeiro
 **/


public class Profesor extends Persona implements Comparator<Profesor> {
	
	/**Departamento al que pertenece el profesor. Dentro del centro, habrá distintos departamentos de investigación y dedicación según especialidad, por lo que cada profesor estará asociado al que corresponda según sus estudios.*/
	private String departamento;
	
	/**Categoría del profesor. Esta puede ser de 'Titular' o de 'Interino'.*/
	private String categoria;
	
	/**Número de horas que podrá dedicar el profesor a impartir clase. El máximo de dichas horas viene determinado en función de la categoría a la que pertenezca el profesor, siendo 15 para los interinos y 20 para los titulares.*/
	private int horasAsignables;
	
	/**Listado que contiene las asignaturas que coordina el profesor. La cantidad de asignaturas que un profesor titular puede coordinar varía entre 0 y 2. En caso de los profesores no titulares, esta variable se anulará.*/
	private TreeMap <Integer, Asignatura> listaCoordinacion = new TreeMap<Integer, Asignatura>();
	
	/**Listado que contiene los grpuos que imparte el profesor. La cantidad de grupos variará en función de la carga docente que pueda soportar.*/
	private TreeMap <Integer, Grupo> listaGruposAsignados = new TreeMap<Integer, Grupo>();
	
	/**Constructor que genera un Profesor con todos sus datos al completo.
	 * 
	 * @param nombre Nombre del profesor.
	 * @param apellidos Apellidos del profesor.
	 * @param fechaNacimiento Fecha de nacimiento del profesor.
	 * @param ID Identificador único del profesor.
	 * @param departamento Departamento al que pertenece el profesor.
	 * @param categoria Categoría del profesor.
	 * @param horasAsignables Cantidad de horas que el profesor puede dedicar a impartir docencia.*/
	public Profesor(String nombre, String apellidos, String fechaNacimiento, int ID,String departamento, String categoria, int horasAsignables) {
		super(nombre, apellidos, fechaNacimiento, ID);
		this.departamento=departamento;
		this.categoria=categoria;
		this.horasAsignables=horasAsignables;
		
		if (!getPuedeCoordinar()) this.listaCoordinacion=null; 

	}
	
	/**Constructor vacío usado por la interfaz Comparator para ordenar a los profesores en función de su carga docente.*/
	public Profesor(){
		
	}

	
	/**Método que nos permite obtener el listado con las asignaturas que coordina un profesor para realizar los trabajos pertinentes.
	 * 
	 * @return Listado con las asignaturas que el profesor coordina.*/
	public TreeMap<Integer, Asignatura> getListaCoordinacion(){
		return this.listaCoordinacion;
	}
	
	/**Método que nos permite obtener el listado de los grupos en los que el profesor imparte docencia.
	 * 
	 * @return Listado con los grupos en los que el profesor imparte docencia.*/
	public TreeMap<Integer, Grupo> getListaGruposAsignados(){
		return this.listaGruposAsignados;
	}
	
	/**Método que informa sobre la posibilidad de coordinar asignaturas de un profesor. 
	 * 
	 * @return 'true' en caso de que pueda coordinar. 'false' en caso de que haya alcanzado el máximo de asignaturas como coordinador o no pertenezca a la categoría de 'titular'.*/
	public boolean getPuedeCoordinar(){
		if(categoria.equals("titular") && listaCoordinacion.size() < 2) return true;
		else return false;
	}
	
	/**Método que nos permite obtener el departamento al que pertenece un profesor.
	 * 
	 * @return Departamento al que pertenece el docente.*/
	public String getDepartamento(){
		return this.departamento;
	}
	
	/**Método que nos permite obtener la categoría del profesor.
	 * 
	 * @return Categoría del profesor, bien 'titular' o 'interino'.*/
	public String getCategoria(){
		return this.categoria;
	}
	
	/**Método que nos permite obtener la carga docente máxima que puede soportar el docente.
	 * 
	 * @return Número de horas que puede impartir clase.*/
	public int getHorasAsignables(){
		return this.horasAsignables;
	}
	
	/**Método que devuelve la cantidad de horas que el profesor invierte en la docencia. Las horas vienen dadas en función del tiempo que ocupe al profesor en impartir la clase.
	 * 
	 * @return Número de horas asignadas.*/
	public int getCargaDocente(){
		int cargaDocente = 0;
		Iterator<Integer> iterador = listaGruposAsignados.keySet().iterator();
		
		while (iterador.hasNext()){
			Integer i = new Integer(iterador.next());
			Grupo grupo = listaGruposAsignados.get(i);
		
			if(grupo.getIDDocente() != this.getID()){
				iterador.remove();				
			} else
			cargaDocente+=grupo.getDuracionGrupo();
			//System.out.println("La duración de este grupo es: " + grupo.getDuracionGrupo());
		}
		
		iterador = null;
		
		return cargaDocente;
	}

	/**Método que registra el identificador del profesor que coordina la asignatura.
	 * 
	 * @param asignatura Asignatura a coordinar.*/
	public void setCoordinadorAsignatura(Asignatura asignatura){
		this.listaCoordinacion.put(new Integer(asignatura.getID()), asignatura);
	}

	/**Método que elimina al profesor de la coordinación de una asignatura. Se emplea en caso de que una asignatura pase a tener un nuevo coordinador.
	 * 
	 * @param asignatura Asignatura que deja de coordinar.*/
	public void eliminarAsignaturaCoordinada (Asignatura asignatura){
		listaCoordinacion.remove(asignatura.getID());
	}
	
	/**Método implementado perteneciente a la interfaz 'Comparator'. Permite al programa ordenar a los profesores en función de su carga docente. En caso de empate, irá primero el que tenga el identificador más pequeño.
	 * 
	 * @param o1 Profesor con el que se compara.
	 * @param o2 Profesor a comparar.
	 * 
	 * @return 'Diferencia' relativa entre ambos profesores.*/
	@Override
	public int compare(Profesor o1, Profesor o2) {
		int comparacion = -1;
		
		comparacion = o1.getCargaDocente() - o2.getCargaDocente();
		
		if(comparacion == 0){
			comparacion = o1.getID() - o2.getID();
		}
		return comparacion;
	}
	
	
	

}


