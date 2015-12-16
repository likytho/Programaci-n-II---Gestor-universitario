package Docencia;

import java.util.Comparator;

/**En funci�n del n�mero de alumnos matriculados, la docencia de una asignatura requiere repartir al alumnado en una serie de 'grupos', con diversas cantidades de alumnos (generalmente equiparadas). Los grupos tendr�n un car�cter (te�rico o pr�ctico), y cada alumno solo podr� pertenecer a uno de ellos en cada asignatura.
 * Adem�s, los grupos generan una carga docente en el profesor que los imparte, por lo que un profesor impartir� siempre un n�mero determinado (y relativamente bajo) de grupos en una asignatura.
 * 
 * Las asignaturas podr�n no contener grupos te�ricos o grupos pr�cticos, pero siempre han de tener, al menos, un grupo (independientemente de su car�cter).
 * 
 * Puesto que el n�mero de alumnos que acoge un grupo depende del car�cter del mismo, en una misma asignatura puede haber diferencia entre la cantidad de grupos te�ricos y pr�cticos que se ofrezcan.
 * 
 * La clase implementa la interfaz 'Comparator', necesaria para poder ordenar los grupos cronol�gicamente.
 * 
 * @author Pedro Tub�o Figueira
 * @author Jose �ngel Regueiro Janeiro*/

public class Grupo implements Comparator<Grupo>{

	/**Car�cter del grupo, puede ser 'T' (te�rico) � 'P' (pr�ctico).*/
	private char tipoGrupo;
	
	/**Identificador del grupo en funci�n de su car�cter. Siempre ser� igual o mayor que 1 (no puede ser 0).*/
	private int IDGrupo;
	
	/**Identificador de la asignatura a la que pertenece el grupo.*/
	private int IDAsignatura;
	
	/**D�a de la semana que se imparte el grupo. Los distintos valores que toma se corresponden con L (Lunes) / M (Martes) / X (Mi�rcoles) / J (Jueves) / V (Viernes).*/
	private char dia;
	
	/**Hora a la que se imparte la docencia del grupo. La hora estar� comprendida siempre entre las 9h y las 19h debido a los horarios de apertura y cierre del centro.*/
	private int hora;
	
	/**Identificador del docente que imparte la asignatura. En caso de no estar asignado, su valor ser� '0'.*/
	private int IDDocente;
	
	/**Duraci�n del grupo. Es lo que genera la carga docente del profesorado. Su valor ser� siempre 1 � 2 (horas).*/
	private int duracionGrupo;

	/**Constructor que genera un nuevo grupo con los datos que se detallan a continuaci�n. Este constructor se utiliza cuando se tienen todos los datos relevantes del grupo (leyendo del fichero de asignaturas).
	 * 
	 * @param duracionGrupo Duraci�n del grupo.
	 * @param tipoGrupo Car�cter del gurpo.
	 * @param IDGrupo Identificador del grupo dentro de su car�cter.
	 * @param dia D�a en que se imparte la docencia del grupo.
	 * @param hora Hora del d�a en que se imparte docencia del grupo.*/
	public Grupo(int duracionGrupo, char tipoGrupo, int IDGrupo, char dia, int hora){
		this.tipoGrupo=tipoGrupo;
		this.IDGrupo=IDGrupo;
		this.dia=dia;
		this.hora=hora;
		this.duracionGrupo=duracionGrupo;
	}
	
	/**Constructor que genera un grupo cuando no tenemos los datos m�s importantes del mismo. Este grupo, en caso de generarse, se completar�a m�s adelante con los correspondientes setters.
	 * 
	 * @param ID Identificador del grupo.
	 * @param tipo Car�cter del grupo.
	 * @param IDAsignatura Identificador de la asignatura a la que pertenece el grupo.*/
	public Grupo (int ID, char tipo, int IDAsignatura){	
		this.IDGrupo=ID;
		this.tipoGrupo=tipo;
		this.IDAsignatura = IDAsignatura;
	}
	
	/**Constructor vac�o usado �nicamente para generar un objeto hu�rfano que permita a la intefaz de comparaci�n ordenar los grupos por orden cronol�gico.*/
	public Grupo() {
		
	}

	/**M�todo que nos devuelve el car�cter del grupo.
	 * 
	 * @return Tipo del grupo (te�rico o pr�ctico), como "Teor�a" o "Pr�ctica".*/
	public String getTipoGrupo(){
		if (this.tipoGrupo == 'T') return "Teor�a";
		return "Pr�ctica";
	}
	
	/**M�todo que nos devuelve el identificador del grupo.
	 * 
	 * @return Identificador del grupo.*/
	public int getIDGrupo(){
		return this.IDGrupo;
	}
	
	/**M�todo que nos devuelve el d�a de la semana en el que se imparte la docencia del grupo.
	 * 
	 * @return L/M/X/J/V, en funci�n del d�a de la semana correspondiente en el que se imparta docencia, tal que Lunes/Martes/Mi�rcoles/Jueves/Viernes, respectivamente.*/
	public char getDia(){
		return this.dia;
	}
	
	/**M�todo que nos devuelve la hora del d�a en la que se imparte la docencia del grupo.
	 * 
	 * @return Una hora comprendida entre las 9 y las 19, debido a las restricciones por los horarios de apertura y cierre del centro.*/
	public int getHora(){
		return this.hora;
	}
	
	/**M�todo que nos devuelve el identificador del docente que imparte docencia en el grupo.
	 * 
	 * @return Identificador del docente que imparte clase en el grupo.*/
	public int getIDDocente(){
		return this.IDDocente;
	}
	
	/**M�todo que nos devuelve la duraci�n del grupo.
	 * 
	 * @return Duraci�n (en horas) del grupo. Puede ser de 1 � 2 (horas).*/
	public int getDuracionGrupo(){
		return this.duracionGrupo;
	}

	/**M�todo que devuelve el identificador de la asignatura a la que pertenece el grupo.
	 * 
	 * @return Identificador de la asignatura a la que pertenece el grupo.*/
	public int getIDAsignatura() {
		return IDAsignatura;
	}
	
	
	/**M�todo que devuelve un n�mero que lo identifica con el d�a y la hora a la que se imparte la docencia en el centro. Este n�mero es el que emplear� el sistema para generar los horarios de los alumnos por orden cronol�gico y para comprobar si se genera alg�n tipo de solape.
	 * 
	 * @return Un n�mero de tres cifras, ###, en el cual las CENTENAS son el equivalente del d�a de la semana, y las DECENAS y UNIDADES indican la hora del d�a.*/
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

	/**M�todo que registra la duraci�n del grupo.
	 * 
	 * @param duracionGrupo Duraci�n del gurpo.*/
	public void setDuracionGrupo(int duracionGrupo){
		this.duracionGrupo=duracionGrupo;
	}
	
	/**M�todo que registra el identificador del docente que imparte clase en el grupo.
	 * 
	 * @param IDDocente Identificador del docente que imparte clase.*/
	public void setIDDocente(int IDDocente){
		this.IDDocente=IDDocente;
	}
	
	/**M�todo que registra el car�cter del grupo.
	 * 
	 * @param tipoGrupo Car�cter del grupo.*/
	public void setTipoGrupo(char tipoGrupo){
		this.tipoGrupo=tipoGrupo;
	}
	
	/**M�todo que registra el identificador del grupo dentro de los de su mismo car�cter.
	 * 
	 * @param IDGrupo Identificador del grupo.*/
	public void  setIDGrupo(int IDGrupo){
		this.IDGrupo = IDGrupo;
	}
	
	/**M�todo que registra el d�a de la semana en el que se imparte la docencia del grupo.
	 * 
	 * @param dia D�a de la semana en el que se imparte la docencia del grupo.*/
	public void setDia(char dia){
		this.dia=dia;
	}
	
	/**M�todo que registra la hora del d�a en el que se imparte la docencia del grupo.
	 * 
	 * @param hora Hora del d�a en el que se imparte docencia del grupo.*/
	public void setHora(int hora){
		this.hora=hora;
	}


	/**M�todo hererado de la interfaz 'Comparator'. En el, se comparan los instantes en los que se imparte docencia en el centro entre dos grupos. 
	 * 
	 * @param grupo1 Grupo en funci�n del que se compara (referencia).
	 * @param grupo2 Grupo que se compara.
	 * 
	 * @return Diferencia moment�nea entre los instantes en los que se imparte clase del grupo1 con respecto del grupo2*/
	@Override
	public int compare(Grupo grupo1, Grupo grupo2) {
		return grupo1.getNumeroCronologico() - grupo2.getNumeroCronologico();
	}

}
