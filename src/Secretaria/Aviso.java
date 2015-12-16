package Secretaria;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**En muchas de las acciones a ejecutar, habrá que cumplir una serie de condiciones para poder llevar a cabo el objetivo deseado. El propósito de esta clase no es otro que informar al usuario de que ciertas acciones no se han podido llevar a cabo por unas circunstancias determinadas, que son las que se incluyen en este código.
 * 
 * @author Pedro Tubío Figueira
 * @author Jose Ángel Regueiro Janeiro*/

public class Aviso {

	/**Constructor que genera un aviso común.
	 * @param comando Comando que genera el aviso.
	 * @param IDAviso Identificador del aviso.*/
	public Aviso(String comando, int IDAviso){
		emitirAviso(comando, IDAviso,0,0,"");
	}
	
	/**Constructor que genera un aviso relativo al archivo de evaluación.
	 * @param comando Comando que genera el aviso.
	 * @param IDAviso Identificador del aviso.
	 * @param linea Línea del archivo de evaluación en la que se ha generado el aviso.*/
	public Aviso(String comando, int IDAviso, int linea){
		emitirAviso(comando, IDAviso,linea, 0,"");
	}
	
	/**Constructor que genera un aviso relativo al archivo de evaluación.
	 * 
	 * @param comando Comando que genera el aviso.
	 * @param IDAviso Identificador del aviso.
	 * @param linea Linea del archivo en la que se ha generado el aviso.
	 * @param IDAlumno Identificador del alumno que ha generado el aviso.*/
	public Aviso(String comando, int IDAviso, int linea, int IDAlumno){
		emitirAviso(comando, IDAviso,linea, IDAlumno,"");
	}
	
	/**Constructor que genera un aviso relativo a un comando incorrecto.
	 * 
	 * @param comando Comando que genera el aviso.
	 * @param nombreComando Nombre del comando que no se ha introducido correctamente.*/
	public Aviso(String comando, String nombreComando){
		emitirAviso(comando, 0,0,0,nombreComando);
	}
	
	/**Constructor que genera el aviso pertinente cuando un fichero no se encuentra disponible en el sistema.
	 * @param errorFichero Mensaje de error del fichero correspondiente.*/
	public Aviso(String errorFichero){
		emitirAviso(errorFichero,0,0,0,"");
	}
	
	/**Método que identifica el aviso pertinente, lo genera y lo envía al archivo de 'avisos.txt'.
	 * 
	 * @param comando Comando que ha generado el aviso.
	 * @param IDAviso Identificador del aviso.
	 * @param linea Linea del archivo de evaluación en la que se ha generado el aviso.
	 * @param IDAlumno Identificador del alumno que ha generado el aviso.
	 * @param nombreComando Nombre del comando a imprimir.*/
	private void emitirAviso(String comando, int IDAviso,int linea, int IDAlumno, String nombreComando){
		
		if(comando.equalsIgnoreCase("comandoIncorrecto")){
			escribeFichero("Comando incorrecto: <" + nombreComando + ">.");
		}
		
		if(comando.equalsIgnoreCase("Error: no existe el fichero de ejecución.")){
			escribeFichero("Error: no existe el fichero de ejecución.");
		}
		
		
		
		if(comando.equalsIgnoreCase("InsertaPersona")){
			
			
			String aviso="IP -- ";
			
			switch (IDAviso){
			
			case 0:{
				aviso += "Fecha incorrecta.";
				break;
			}
			
			case 1:{
				aviso += "Fecha de ingreso incorrecta.";
				break;
			}
			
			case 2:{
				aviso += "Nota media incorrecta.";
				break;
			}
			
			case 3:{
				aviso += "Numero de horas incorrecto.";
				break;
			}
			
		
			
			}
			
			escribeFichero(aviso);
			
		}
		
		if(comando.equalsIgnoreCase("AsignaCoordinador")){
			
			
			String aviso="ACOORD -- ";
			
			
			switch (IDAviso){
			
			case 0:{
				aviso += "Profesor inexistente.";
				break;
			}
			
			case 1:{
				
				aviso += "Profesor no titular.";
				
				break;
			}
			
			case 2:{
				
				aviso += "Asignatura inexistente";
				
				break;
			}
			
			case 3:{
				
				aviso += "Profesor ya es coordinador de 2 materias.";
				
				break;
			}
			
		
			
			}
			
			escribeFichero(aviso);
			
			
			
		}
		
		if(comando.equalsIgnoreCase("AsignaCargaDocente")){
			
			
			String aviso="ACD -- ";
			
			switch (IDAviso){
			
			case 0:{
				aviso +="Profesor inexistente.";
				break;
			}
			
			case 1:{
				aviso +="Asignatura inexistente.";
				break;
			}
			
			case 2:{
				aviso +="Tipo de grupo incorrecto";
				break;
			}
			
			case 3:{
				aviso +="Grupo inexistente.";
				break;
			}
			
			case 4:{
				aviso+="Grupo ya asignado";
				break;
			}
			
			case 5:{
				aviso+="Horas asignables superior al máximo.";
				break;
			}
			
			case 6:{
				aviso+="Se genera solape.";
				break;
			}
			
		
			
			}
			
			escribeFichero(aviso);
			
		}
		
		if(comando.equalsIgnoreCase("Matricula")){
			
			
			String aviso="MAT -- ";
			
			switch (IDAviso){
			
			case 0:{
				aviso+="Alumno inexistente.";
				break;
			}
			
			case 1:{
				aviso+="Asignatura inexistente.";
				break;
			}
			
			case 2:{
				aviso+="Ya es alumno de la asignatura indicada.";
				break;
			}
			
			case 3:{
				aviso+="No cumple requisitos.";
				break;
			}
			
		
			
			}
			
			escribeFichero(aviso);
			
		}
		
		if(comando.equalsIgnoreCase("AsignaGrupo")){
			
			
			String aviso="AGRUPO -- ";
			
			switch (IDAviso){
			
			case 0:{
				aviso += "Alumno inexistente"; 
				break;
			}
			
			case 1:{
				aviso += "Asignatura inexistente";
				break;
			}
			
			case 2:{
				aviso += "Alumno no matriculado.";
				break;
			}
			
			case 3:{
				aviso += "Tipo de grupo incorrecto.";
				break;
			}
			
			case 4:{
				aviso += "Grupo inexistente.";
				break;
			}
			
			case 5:{
				aviso += "Se genera solape.";
				break;
			}
			
		
			
			}
			
			escribeFichero(aviso);
			
		}
		
		if(comando.equalsIgnoreCase("Evaluar")){
			
			
			String aviso="EVALUA -- ";
			
			switch (IDAviso){
			
			case 0:{
				aviso += "Asignatura inexistente.";
				break;
			}
			
			case 1:{
				aviso += "Fichero de notas inexistente.";
				break;
			}
			
			case 2:{
				aviso += "Error en linea <" + linea + ">: ";
				aviso += "Alumno inexistente <" + IDAlumno + ">.";
				break;
			}
			
			case 3:{
				aviso += "Error en linea <" + linea + ">: ";
				aviso += "Alumno no matriculado <" + IDAlumno + ">.";
				break;
			}
			
			case 4:{
				aviso += "Error en linea <" + linea + ">: ";
				aviso += "Nota incorrecta.";
				break;
			}
			
			
		
			
			}
			
			escribeFichero(aviso);
			
		}
		
		if(comando.equalsIgnoreCase("ObtenerCalendarioClases")){
			
			
			String aviso="OCALEN -- ";
			
			switch (IDAviso){
			
			case 0:{
				aviso += "Alumno inexistente.";
				break;
			}
			
			case 1:{
				aviso += "Alumno sin asignaciones.";
				break;
			}
			
		
			
			}
			
			escribeFichero(aviso);
			
		}
		
		if(comando.equalsIgnoreCase("OrdenarProfesoresPorCargaDocente")){
			
			
			String aviso="OTIT -- ";
			
			switch (IDAviso){
			
			case 0:{
				aviso += "No hay profesores titulares";
				break;
			}
			
			
			}
			
			escribeFichero(aviso);
			
		}
	}

	/**Método que escribe el aviso pertinente en el fichero de 'avisos.txt' Este método genera el archivo en caso de que éste no exista, o nos induce a escribir al final del mismo en caso de que sí exista.
	 * 
	 * @param aviso Cadena de texto con el aviso a imprimir en el fichero.*/
	public static void escribeFichero(String aviso){

		File fichero=new File("avisos.txt");
		
		if(fichero.exists()){
			try{
				FileWriter punteroEscritura=new FileWriter("avisos.txt",true);
				punteroEscritura.write(System.getProperty("line.separator") + aviso);
				punteroEscritura.close();	
			}catch(IOException e){
				
			}
		}else{
			try{
			
			FileWriter punteroEscritura=new FileWriter(fichero);
			punteroEscritura.write(aviso);
			punteroEscritura.close();
			}catch(IOException e)
				{
					
				}
			}
			
		}
}

 