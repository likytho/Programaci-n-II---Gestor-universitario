package Personal;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeMap;

import Docencia.Asignatura;
import Docencia.Grupo;

/**Los profesores son aquellas personas encargadas de impartir docencia en el Centro Universitario, proceso b�sico del mismo.
 *Por un lado, tendremos dos tipos de profesores (en funci�n de su categor�a), que ser�n 'Titulares' e 'Interinos'. Los titulares, adem�s de impartir docencia, tendr�n la posibilidad de coordinar las asignaturas. 
 *Cada profesor tendr� una dedicaci�n semanal (en horas) que determinar� el n�mero m�ximo de horas que un profesor puede dedicar a impartir docencia. Este n�mero quedar� determinado en funci�n de la categor�a del profesor.
 *
 *@author Pedro Tub�o Figueira
 *@author Jose �ngel Regueiro Janeiro
 **/


public class Profesor extends Persona implements Comparator<Profesor> {
	
	/**Departamento al que pertenece el profesor. Dentro del centro, habr� distintos departamentos de investigaci�n y dedicaci�n seg�n especialidad, por lo que cada profesor estar� asociado al que corresponda seg�n sus estudios.*/
	private String departamento;
	
	/**Categor�a del profesor. Esta puede ser de 'Titular' o de 'Interino'.*/
	private String categoria;
	
	/**N�mero de horas que podr� dedicar el profesor a impartir clase. El m�ximo de dichas horas viene determinado en funci�n de la categor�a a la que pertenezca el profesor, siendo 15 para los interinos y 20 para los titulares.*/
	private int horasAsignables;
	
	/**Listado que contiene las asignaturas que coordina el profesor. La cantidad de asignaturas que un profesor titular puede coordinar var�a entre 0 y 2. En caso de los profesores no titulares, esta variable se anular�.*/
	private TreeMap <Integer, Asignatura> listaCoordinacion = new TreeMap<Integer, Asignatura>();
	
	/**Listado que contiene los grpuos que imparte el profesor. La cantidad de grupos variar� en funci�n de la carga docente que pueda soportar.*/
	private TreeMap <Integer, Grupo> listaGruposAsignados = new TreeMap<Integer, Grupo>();
	
	/**Constructor que genera un Profesor con todos sus datos al completo.
	 * 
	 * @param nombre Nombre del profesor.
	 * @param apellidos Apellidos del profesor.
	 * @param fechaNacimiento Fecha de nacimiento del profesor.
	 * @param ID Identificador �nico del profesor.
	 * @param departamento Departamento al que pertenece el profesor.
	 * @param categoria Categor�a del profesor.
	 * @param horasAsignables Cantidad de horas que el profesor puede dedicar a impartir docencia.*/
	public Profesor(String nombre, String apellidos, String fechaNacimiento, int ID,String departamento, String categoria, int horasAsignables) {
		super(nombre, apellidos, fechaNacimiento, ID);
		this.departamento=departamento;
		this.categoria=categoria;
		this.horasAsignables=horasAsignables;
		
		if (!getPuedeCoordinar()) this.listaCoordinacion=null; 

	}
	
	/**Constructor vac�o usado por la interfaz Comparator para ordenar a los profesores en funci�n de su carga docente.*/
	public Profesor(){
		
	}

	
	/**M�todo que nos permite obtener el listado con las asignaturas que coordina un profesor para realizar los trabajos pertinentes.
	 * 
	 * @return Listado con las asignaturas que el profesor coordina.*/
	public TreeMap<Integer, Asignatura> getListaCoordinacion(){
		return this.listaCoordinacion;
	}
	
	/**M�todo que nos permite obtener el listado de los grupos en los que el profesor imparte docencia.
	 * 
	 * @return Listado con los grupos en los que el profesor imparte docencia.*/
	public TreeMap<Integer, Grupo> getListaGruposAsignados(){
		return this.listaGruposAsignados;
	}
	
	/**M�todo que informa sobre la posibilidad de coordinar asignaturas de un profesor. 
	 * 
	 * @return 'true' en caso de que pueda coordinar. 'false' en caso de que haya alcanzado el m�ximo de asignaturas como coordinador o no pertenezca a la categor�a de 'titular'.*/
	public boolean getPuedeCoordinar(){
		if(categoria.equals("titular") && listaCoordinacion.size() < 2) return true;
		else return false;
	}
	
	/**M�todo que nos permite obtener el departamento al que pertenece un profesor.
	 * 
	 * @return Departamento al que pertenece el docente.*/
	public String getDepartamento(){
		return this.departamento;
	}
	
	/**M�todo que nos permite obtener la categor�a del profesor.
	 * 
	 * @return Categor�a del profesor, bien 'titular' o 'interino'.*/
	public String getCategoria(){
		return this.categoria;
	}
	
	/**M�todo que nos permite obtener la carga docente m�xima que puede soportar el docente.
	 * 
	 * @return N�mero de horas que puede impartir clase.*/
	public int getHorasAsignables(){
		return this.horasAsignables;
	}
	
	/**M�todo que devuelve la cantidad de horas que el profesor invierte en la docencia. Las horas vienen dadas en funci�n del tiempo que ocupe al profesor en impartir la clase.
	 * 
	 * @return N�mero de horas asignadas.*/
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
			//System.out.println("La duraci�n de este grupo es: " + grupo.getDuracionGrupo());
		}
		
		iterador = null;
		
		return cargaDocente;
	}

	/**M�todo que registra el identificador del profesor que coordina la asignatura.
	 * 
	 * @param asignatura Asignatura a coordinar.*/
	public void setCoordinadorAsignatura(Asignatura asignatura){
		this.listaCoordinacion.put(new Integer(asignatura.getID()), asignatura);
	}

	/**M�todo que elimina al profesor de la coordinaci�n de una asignatura. Se emplea en caso de que una asignatura pase a tener un nuevo coordinador.
	 * 
	 * @param asignatura Asignatura que deja de coordinar.*/
	public void eliminarAsignaturaCoordinada (Asignatura asignatura){
		listaCoordinacion.remove(asignatura.getID());
	}
	
	/**M�todo implementado perteneciente a la interfaz 'Comparator'. Permite al programa ordenar a los profesores en funci�n de su carga docente. En caso de empate, ir� primero el que tenga el identificador m�s peque�o.
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


