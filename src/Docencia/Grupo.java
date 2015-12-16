package Docencia;

import java.util.Comparator;

/**En función del número de alumnos matriculados, la docencia de una asignatura requiere repartir al alumnado en una serie de 'grupos', con diversas cantidades de alumnos (generalmente equiparadas). Los grupos tendrán un carácter (teórico o práctico), y cada alumno solo podrá pertenecer a uno de ellos en cada asignatura.
 * Además, los grupos generan una carga docente en el profesor que los imparte, por lo que un profesor impartirá siempre un número determinado (y relativamente bajo) de grupos en una asignatura.
 * 
 * Las asignaturas podrán no contener grupos teóricos o grupos prácticos, pero siempre han de tener, al menos, un grupo (independientemente de su carácter).
 * 
 * Puesto que el número de alumnos que acoge un grupo depende del carácter del mismo, en una misma asignatura puede haber diferencia entre la cantidad de grupos teóricos y prácticos que se ofrezcan.
 * 
 * La clase implementa la interfaz 'Comparator', necesaria para poder ordenar los grupos cronológicamente.
 * 
 * @author Pedro Tubío Figueira
 * @author Jose Ángel Regueiro Janeiro*/

public class Grupo implements Comparator<Grupo>{

	/**Carácter del grupo, puede ser 'T' (teórico) ó 'P' (práctico).*/
	private char tipoGrupo;
	
	/**Identificador del grupo en función de su carácter. Siempre será igual o mayor que 1 (no puede ser 0).*/
	private int IDGrupo;
	
	/**Identificador de la asignatura a la que pertenece el grupo.*/
	private int IDAsignatura;
	
	/**Día de la semana que se imparte el grupo. Los distintos valores que toma se corresponden con L (Lunes) / M (Martes) / X (Miércoles) / J (Jueves) / V (Viernes).*/
	private char dia;
	
	/**Hora a la que se imparte la docencia del grupo. La hora estará comprendida siempre entre las 9h y las 19h debido a los horarios de apertura y cierre del centro.*/
	private int hora;
	
	/**Identificador del docente que imparte la asignatura. En caso de no estar asignado, su valor será '0'.*/
	private int IDDocente;
	
	/**Duración del grupo. Es lo que genera la carga docente del profesorado. Su valor será siempre 1 ó 2 (horas).*/
	private int duracionGrupo;

	/**Constructor que genera un nuevo grupo con los datos que se detallan a continuación. Este constructor se utiliza cuando se tienen todos los datos relevantes del grupo (leyendo del fichero de asignaturas).
	 * 
	 * @param duracionGrupo Duración del grupo.
	 * @param tipoGrupo Carácter del gurpo.
	 * @param IDGrupo Identificador del grupo dentro de su carácter.
	 * @param dia Día en que se imparte la docencia del grupo.
	 * @param hora Hora del día en que se imparte docencia del grupo.*/
	public Grupo(int duracionGrupo, char tipoGrupo, int IDGrupo, char dia, int hora){
		this.tipoGrupo=tipoGrupo;
		this.IDGrupo=IDGrupo;
		this.dia=dia;
		this.hora=hora;
		this.duracionGrupo=duracionGrupo;
	}
	
	/**Constructor que genera un grupo cuando no tenemos los datos más importantes del mismo. Este grupo, en caso de generarse, se completaría más adelante con los correspondientes setters.
	 * 
	 * @param ID Identificador del grupo.
	 * @param tipo Carácter del grupo.
	 * @param IDAsignatura Identificador de la asignatura a la que pertenece el grupo.*/
	public Grupo (int ID, char tipo, int IDAsignatura){	
		this.IDGrupo=ID;
		this.tipoGrupo=tipo;
		this.IDAsignatura = IDAsignatura;
	}
	
	/**Constructor vacío usado únicamente para generar un objeto huérfano que permita a la intefaz de comparación ordenar los grupos por orden cronológico.*/
	public Grupo() {
		
	}

	/**Método que nos devuelve el carácter del grupo.
	 * 
	 * @return Tipo del grupo (teórico o práctico), como "Teoría" o "Práctica".*/
	public String getTipoGrupo(){
		if (this.tipoGrupo == 'T') return "Teoría";
		return "Práctica";
	}
	
	/**Método que nos devuelve el identificador del grupo.
	 * 
	 * @return Identificador del grupo.*/
	public int getIDGrupo(){
		return this.IDGrupo;
	}
	
	/**Método que nos devuelve el día de la semana en el que se imparte la docencia del grupo.
	 * 
	 * @return L/M/X/J/V, en función del día de la semana correspondiente en el que se imparta docencia, tal que Lunes/Martes/Miércoles/Jueves/Viernes, respectivamente.*/
	public char getDia(){
		return this.dia;
	}
	
	/**Método que nos devuelve la hora del día en la que se imparte la docencia del grupo.
	 * 
	 * @return Una hora comprendida entre las 9 y las 19, debido a las restricciones por los horarios de apertura y cierre del centro.*/
	public int getHora(){
		return this.hora;
	}
	
	/**Método que nos devuelve el identificador del docente que imparte docencia en el grupo.
	 * 
	 * @return Identificador del docente que imparte clase en el grupo.*/
	public int getIDDocente(){
		return this.IDDocente;
	}
	
	/**Método que nos devuelve la duración del grupo.
	 * 
	 * @return Duración (en horas) del grupo. Puede ser de 1 ó 2 (horas).*/
	public int getDuracionGrupo(){
		return this.duracionGrupo;
	}

	/**Método que devuelve el identificador de la asignatura a la que pertenece el grupo.
	 * 
	 * @return Identificador de la asignatura a la que pertenece el grupo.*/
	public int getIDAsignatura() {
		return IDAsignatura;
	}
	
	
	/**Método que devuelve un número que lo identifica con el día y la hora a la que se imparte la docencia en el centro. Este número es el que empleará el sistema para generar los horarios de los alumnos por orden cronológico y para comprobar si se genera algún tipo de solape.
	 * 
	 * @return Un número de tres cifras, ###, en el cual las CENTENAS son el equivalente del día de la semana, y las DECENAS y UNIDADES indican la hora del día.*/
	public int getNumeroCronologico(){
		int numeroCronologico = 0;
		
		switch (dia){
		case ('L'):{
			numeroCronologico=100;
			numeroCronologico+=hora;
			break;
		}
		
		case ('M'):{
			numeroCronologico=200;
			numeroCronologico+=hora;
			break;
		}
		
		case ('X'):{
			numeroCronologico=300;
			numeroCronologico+=hora;
			break;
		}
		
		case ('J'):{
			numeroCronologico=400;
			numeroCronologico+=hora;
			break;
		}
		
		case ('V'):{
			numeroCronologico=500;
			numeroCronologico+=hora;
			break;
		}

		}
		
		return numeroCronologico;
		
	}

	/**Método que registra la duración del grupo.
	 * 
	 * @param duracionGrupo Duración del gurpo.*/
	public void setDuracionGrupo(int duracionGrupo){
		this.duracionGrupo=duracionGrupo;
	}
	
	/**Método que registra el identificador del docente que imparte clase en el grupo.
	 * 
	 * @param IDDocente Identificador del docente que imparte clase.*/
	public void setIDDocente(int IDDocente){
		this.IDDocente=IDDocente;
	}
	
	/**Método que registra el carácter del grupo.
	 * 
	 * @param tipoGrupo Carácter del grupo.*/
	public void setTipoGrupo(char tipoGrupo){
		this.tipoGrupo=tipoGrupo;
	}
	
	/**Método que registra el identificador del grupo dentro de los de su mismo carácter.
	 * 
	 * @param IDGrupo Identificador del grupo.*/
	public void  setIDGrupo(int IDGrupo){
		this.IDGrupo = IDGrupo;
	}
	
	/**Método que registra el día de la semana en el que se imparte la docencia del grupo.
	 * 
	 * @param dia Día de la semana en el que se imparte la docencia del grupo.*/
	public void setDia(char dia){
		this.dia=dia;
	}
	
	/**Método que registra la hora del día en el que se imparte la docencia del grupo.
	 * 
	 * @param hora Hora del día en el que se imparte docencia del grupo.*/
	public void setHora(int hora){
		this.hora=hora;
	}


	/**Método hererado de la interfaz 'Comparator'. En el, se comparan los instantes en los que se imparte docencia en el centro entre dos grupos. 
	 * 
	 * @param grupo1 Grupo en función del que se compara (referencia).
	 * @param grupo2 Grupo que se compara.
	 * 
	 * @return Diferencia momentánea entre los instantes en los que se imparte clase del grupo1 con respecto del grupo2*/
	@Override
	public int compare(Grupo grupo1, Grupo grupo2) {
		return grupo1.getNumeroCronologico() - grupo2.getNumeroCronologico();
	}

}
