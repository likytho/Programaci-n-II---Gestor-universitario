package Secretaria;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.TreeMap;

import Docencia.Asignatura;
import Docencia.Grupo;
import Personal.Alumno;
import Personal.Persona;
import Personal.Profesor;


/**La clase 'Funcion' es gracias a la cual la gestión se realiza de manera factible. En ella, se incluyen todos los métodos necesarios para la correcta gestión del centro. Por un lado, tenemos métodos que se encargan de leer los datos que preexistan en el sistema, los cuales deben ser siempre correctos (pues el programa no está preparado para detectar errores en ellos). Por otro, los que se encargan de guardar las modificaciones que se hayan llevado a cabo. Esta parte es transcendental, porque una incorrección a la hora de guardar los datos puede dar lugar a la inestabilidad del sistema.
 * Finalmente, están los demás metodos, los que se detallan como comandos en la especificación del programa. Estos métodos son los que se encargan de realizar todos los cambios que el usuario requiera. Estos métodos podrán generar algunos avisos en caso de que se den unas circunstancias concretas, que se encuentran implementados en la clase 'Avisos'.
 * 
 * El usuario debe ser cuidadoso con los datos que introduce, pues un error que no esté declarado podría generar inestabilidad en el sistema.
 * 
 * @author Pedro Tubío Figueira
 * @author Jose Ángel Regueiro Janeiro.*/

public class Funcion {

	/**Método encargado de leer los datos preexistentes en el sistema en relación a las personas. 
	 * Este método se ocupa de leer los datos almacenados en el fichero 'personas.txt', que supondrá siempre correctos. Es un método un poco burdo, y que podría llegar a generar en algún momento objetos de naturaleza un tanto abstracta, pero es un método que, para su correcta consecución, requiere del método de lectura del archivo que contiene la información relativa a las asignaturas, pues ambos archivos contienen relación entre sí.
	 * 
	 * @param censo Censo en el que se encuentran almacenadas e identificadas las personas y las asignaturas que formarían parte del sistema.*/
	public static void leerPersonas(Censo censo){
		Scanner punteroLectura=null;
		TreeMap <Integer, Persona> personas = censo.getPersonas();
		TreeMap <Integer, Asignatura> asignaturas = censo.getAsignaturas();
		
		try{
			punteroLectura = new Scanner(new FileInputStream("personas.txt"));
		} catch (FileNotFoundException e){
			new Aviso("Error, no existe el archivo 'personas.txt'.");
			System.exit(-1);
		}
		

		while(punteroLectura.hasNextLine()){
			
			punteroLectura.useDelimiter("\\*");

			String listadoArgumentos[] = punteroLectura.next().trim().split("\n");
			String listadoLimpio="";
			
			for(int i=0; i<listadoArgumentos.length; i++){
				listadoLimpio += listadoArgumentos[i].replaceAll(" +"," ");
				listadoLimpio += '-';
			}
			

			String listadoLimpio2[]= listadoLimpio.split("-");			
			String listadoLimpioFinalArray="";
			
			for(int i=0; i<listadoLimpio2.length; i++){
				if(i==0 && listadoLimpio2[i].length() == 0){
					continue;
				}else listadoLimpioFinalArray += listadoLimpio2[i].trim() + "-";
				
			}
			
			if(listadoLimpio2.length!=9){
				for (int j=0; j<9 - listadoLimpio2.length; j++){
					listadoLimpioFinalArray += "@" + "-"; //Carácter que denotará el final del archivo cuando falte algún dato
				}
			}
			
			
			String listadoDefinitivo[] = listadoLimpioFinalArray.split("-");
			for(int i=0; i<listadoDefinitivo.length; i++){
				if(listadoDefinitivo[i].length() == 0) listadoDefinitivo[i] = "@";
			}
						
			/* En este punto ya tengo el famoso array con las personas listas. El próximo paso es trabajar los datos y mandarlas al comando InsertarPersona. Los datos de 
			   los que no dispongamos se denotan por "@". Todos los arrays de Strings son de tamaño 9 (es decir, rango de variables de 0 a 8). */
			String arrayDatosConcretos="";
			
			if(listadoDefinitivo[1].equals("alumno")){
				arrayDatosConcretos += listadoDefinitivo[4] + "-" + listadoDefinitivo[5] + "-" + listadoDefinitivo[7];
			}
			
			
			
			if(listadoDefinitivo[1].equals("profesor")){
				arrayDatosConcretos += listadoDefinitivo[4] + "-" + listadoDefinitivo[5] + "-" + listadoDefinitivo[6] + "-" + listadoDefinitivo[7];
			}
			
			String arrayDatos[] = arrayDatosConcretos.split("-");
			
			insertaPersona(listadoDefinitivo[1], listadoDefinitivo[2], listadoDefinitivo[3], arrayDatos , censo, Integer.parseInt(listadoDefinitivo[0].trim().replaceAll(" ","")), false);

			if(listadoDefinitivo[1].equals("alumno")){
				//Asignaturas superadas
				if(!listadoDefinitivo[6].equals("@")){
					String[] listadoAsignaturasSuperadas = listadoDefinitivo[6].replaceAll(" ","").trim().split(",");
					/*
					for(int i=0; i<listadoAsignaturasSuperadas.length; i++){
						//System.out.println(listadoAsignaturasSuperadas[i]);
					}*/
					
					for(int i=0; i<listadoAsignaturasSuperadas.length; i++){
						int IDComprobar = Integer.parseInt(listadoAsignaturasSuperadas[i]);
						
						
						if(asignaturas.containsKey(IDComprobar)){
	
							Alumno alumno=(Alumno)personas.get(new Integer(listadoDefinitivo[0]));
							Asignatura asignatura = asignaturas.get(new Integer(listadoAsignaturasSuperadas[i]));

							alumno.setAsignaturaSuperada(asignatura);

						} else {
					
							Asignatura asignatura = new Asignatura(Integer.parseInt(listadoAsignaturasSuperadas[i].trim().replaceAll(" +","")));
							Integer ID = new Integer(asignatura.getID());
							
							asignaturas.put(ID, asignatura);
							
							Alumno alumno=(Alumno)personas.get(new Integer(listadoDefinitivo[0]));
							alumno.setAsignaturaSuperada(asignatura);
						
						}
					}
				} 
	
				//Asignaturas y Grupos
				if(!listadoDefinitivo[8].trim().equals("@")){
					String arrayAsignaturas[] = listadoDefinitivo[8].split("; ");

					for (int i=0; i<arrayAsignaturas.length; i++){
						
						String datosGrupo[] = arrayAsignaturas[i].split(" ");
						int IDAsignatura = Integer.parseInt(datosGrupo[0]);
						

						if(datosGrupo.length != 3){
							
							if(asignaturas.containsKey(IDAsignatura)){
								
								Alumno alumno=(Alumno)personas.get(new Integer(listadoDefinitivo[0]));
								Asignatura asignatura = asignaturas.get(new Integer(IDAsignatura));
								
								alumno.matricularAsignatura(asignatura);
								
							} else {
								
								Asignatura asignatura = new Asignatura(Integer.parseInt(datosGrupo[0].trim().replaceAll(" +","")));
								Integer ID = new Integer(asignatura.getID());
								asignaturas.put(ID, asignatura);
								
								Alumno alumno=(Alumno)personas.get(new Integer(listadoDefinitivo[0]));
								alumno.matricularAsignatura(asignatura);

							}
							
							
						} else { //Si está matriculado y tiene grupo
							
							if(asignaturas.containsKey(IDAsignatura)){
								
								Alumno alumno=(Alumno)personas.get(new Integer(listadoDefinitivo[0]));
								Asignatura asignatura = asignaturas.get(new Integer(IDAsignatura));

								if (!alumno.getEstaMatriculado(IDAsignatura)){
									alumno.matricularAsignatura(asignatura);
								}
							
								String tipoGrupo = datosGrupo[1].trim();
								
								if (tipoGrupo.charAt(0) == 'T'){
									
									TreeMap <Integer, Grupo> listaGruposTeoricos = asignatura.getListaGruposTeoricos();
									
									if(listaGruposTeoricos.containsKey(Integer.parseInt(datosGrupo[2].trim()))){
										Grupo grupo = listaGruposTeoricos.get(Integer.parseInt(datosGrupo[2].trim()));
										TreeMap <Integer, Grupo> gruposTeoria = alumno.getGruposTeoria();
										gruposTeoria.put(asignatura.getID(), grupo);

									} else {
										Grupo grupo = new Grupo(Integer.parseInt(datosGrupo[2].trim()),tipoGrupo.charAt(0), IDAsignatura);
										TreeMap <Integer, Grupo> gruposTeoria = alumno.getGruposTeoria();
										gruposTeoria.put(asignatura.getID(), grupo);
										listaGruposTeoricos.put(new Integer(datosGrupo[2].trim()), grupo);
									}
								}
								
								if (tipoGrupo.charAt(0)=='P'){
									
									TreeMap <Integer,Grupo> listaGruposPracticos = asignatura.getListaGruposPracticos();
									
									if(listaGruposPracticos.containsKey(Integer.parseInt(datosGrupo[2].trim()))){
										Grupo grupo = listaGruposPracticos.get(Integer.parseInt(datosGrupo[2].trim()));
										TreeMap <Integer, Grupo> gruposPractica = alumno.getGruposPractica();
										gruposPractica.put(asignatura.getID(), grupo);
										
										
									} else {
										Grupo grupo = new Grupo(Integer.parseInt(datosGrupo[2].trim()),tipoGrupo.charAt(0), IDAsignatura);
										TreeMap <Integer, Grupo> gruposPractica = alumno.getGruposPractica();
										gruposPractica.put(asignatura.getID(), grupo);
										listaGruposPracticos.put(new Integer(datosGrupo[2].trim()), grupo);
									}
									
								}

								
							} else {
								
								Asignatura asignatura = new Asignatura(Integer.parseInt(datosGrupo[0].trim().replaceAll(" +","")));
								Integer ID = new Integer(asignatura.getID());
								asignaturas.put(ID, asignatura);
								
								Alumno alumno=(Alumno)personas.get(new Integer(listadoDefinitivo[0]));

								if (!alumno.getEstaMatriculado(IDAsignatura)){
									alumno.matricularAsignatura(asignatura);
								}
							
								String tipoGrupo = datosGrupo[1].trim();
								
								if (tipoGrupo.charAt(0) == 'T'){
									
									TreeMap <Integer, Grupo> listaGruposTeoricos = asignatura.getListaGruposTeoricos();
									
									if(listaGruposTeoricos.containsKey(Integer.parseInt(datosGrupo[2].trim()))){
										Grupo grupo = listaGruposTeoricos.get(Integer.parseInt(datosGrupo[2].trim()));
										TreeMap <Integer, Grupo> gruposTeoria = alumno.getGruposTeoria();
										gruposTeoria.put(asignatura.getID(), grupo);

									} else {
										Grupo grupo = new Grupo(Integer.parseInt(datosGrupo[2].trim()),tipoGrupo.charAt(0), IDAsignatura);
										TreeMap <Integer, Grupo> gruposTeoria = alumno.getGruposTeoria();
										gruposTeoria.put(asignatura.getID(), grupo);
										listaGruposTeoricos.put(new Integer(datosGrupo[2].trim()), grupo);
									}
								}
								
								if (tipoGrupo.charAt(0)=='P'){
									
									TreeMap <Integer,Grupo> listaGruposPracticos = asignatura.getListaGruposPracticos();
									
									if(listaGruposPracticos.containsKey(Integer.parseInt(datosGrupo[2].trim()))){
										Grupo grupo = listaGruposPracticos.get(Integer.parseInt(datosGrupo[2].trim()));
										TreeMap <Integer, Grupo> gruposPractica = alumno.getGruposPractica();
										gruposPractica.put(asignatura.getID(), grupo);
										
										
									} else {
										Grupo grupo = new Grupo(Integer.parseInt(datosGrupo[2].trim()),tipoGrupo.charAt(0), IDAsignatura);
										TreeMap <Integer, Grupo> gruposPractica = alumno.getGruposPractica();
										gruposPractica.put(asignatura.getID(), grupo);
										listaGruposPracticos.put(new Integer(datosGrupo[2].trim()), grupo);
									}
									
								}
	
							}
							
						}
						
					}
				}

			} else {//Se acaba alumno. pasamos a la parte específica de profesor, que en este caso, pasa a ser "Docencia Impartida" (los grupos).
				
				if(!listadoDefinitivo[8].trim().equals("@")){
				Profesor profesor = (Profesor)personas.get(new Integer(listadoDefinitivo[0]));
				//System.out.println(profesor.getID());
				
				String arrayAsignaturas[] = listadoDefinitivo[8].split("; ");
				
				for (int i=0; i<arrayAsignaturas.length; i++){
					//System.out.println("'"+arrayAsignaturas[i]+"'");
					String datosGrupo[] = arrayAsignaturas[i].split(" ");

					int IDAsignatura = Integer.parseInt(datosGrupo[0]);
					
					if(asignaturas.containsKey(IDAsignatura)){
						Asignatura asignatura = asignaturas.get(new Integer(IDAsignatura));
						String tipoGrupo = datosGrupo[1].trim();
						
						if (tipoGrupo.charAt(0) == 'T'){
							
							TreeMap <Integer, Grupo> listaGruposTeoricos = asignatura.getListaGruposTeoricos();
							
							if(listaGruposTeoricos.containsKey(Integer.parseInt(datosGrupo[2].trim()))){
								Grupo grupo = listaGruposTeoricos.get(Integer.parseInt(datosGrupo[2].trim()));
								grupo.setIDDocente(profesor.getID());
								
								TreeMap <Integer, Grupo> listaGruposAsignados = profesor.getListaGruposAsignados();
								//profesor.listaDocencia.put(new Integer(asignatura.getID()), asignatura);
								listaGruposAsignados.put(new Integer (listaGruposAsignados.size() +1), grupo); 
								

							} else {
								
								Grupo grupo = new Grupo(Integer.parseInt(datosGrupo[2].trim()),tipoGrupo.charAt(0), IDAsignatura);
								listaGruposTeoricos.put(new Integer(datosGrupo[2].trim()), grupo);
								grupo.setIDDocente(profesor.getID());
								//profesor.listaDocencia.put(new Integer(asignatura.getID()), asignatura);
								TreeMap <Integer, Grupo> listaGruposAsignados = profesor.getListaGruposAsignados();
								listaGruposAsignados.put(new Integer (listaGruposAsignados.size() +1), grupo); 
								
								
							}
						}
						
						if (tipoGrupo.charAt(0)=='P'){
							
							TreeMap <Integer, Grupo> listaGruposPracticos = asignatura.getListaGruposPracticos();
							
							if(listaGruposPracticos.containsKey(Integer.parseInt(datosGrupo[2].trim()))){
								Grupo grupo = listaGruposPracticos.get(Integer.parseInt(datosGrupo[2].trim()));
								grupo.setIDDocente(profesor.getID());
								//profesor.listaDocencia.put(new Integer(asignatura.getID()), asignatura);
								TreeMap <Integer, Grupo> listaGruposAsignados = profesor.getListaGruposAsignados();
								listaGruposAsignados.put(new Integer (listaGruposAsignados.size() +1), grupo); 
								
								
							} else {
								Grupo grupo = new Grupo(Integer.parseInt(datosGrupo[2].trim()),tipoGrupo.charAt(0), IDAsignatura);
								listaGruposPracticos.put(new Integer(datosGrupo[2].trim()), grupo);
								grupo.setIDDocente(profesor.getID());
								TreeMap <Integer, Grupo> listaGruposAsignados = profesor.getListaGruposAsignados();
								//profesor.listaDocencia.put(new Integer(asignatura.getID()), asignatura);
								listaGruposAsignados.put(new Integer (listaGruposAsignados.size() +1), grupo); 
								
							}
							
						}
						
				

					} else {
						
						Asignatura asignatura = new Asignatura(Integer.parseInt(datosGrupo[0].trim().replaceAll(" +","")));
						Integer ID = new Integer(asignatura.getID());
						asignaturas.put(ID, asignatura);

						//Añado grupos correspondientes a la asignatura.
						String tipoGrupo = datosGrupo[1].trim();
						//System.out.println(listadoDefinitivo[0]);
						if (tipoGrupo.charAt(0) == 'T'){
							
							TreeMap <Integer, Grupo> listaGruposTeoricos = asignatura.getListaGruposTeoricos();
							
							if(listaGruposTeoricos.containsKey(Integer.parseInt(datosGrupo[2].trim()))){
								Grupo grupo = listaGruposTeoricos.get(Integer.parseInt(datosGrupo[2].trim()));							
								grupo.setIDDocente(profesor.getID());
								//profesor.listaDocencia.put(new Integer(asignatura.getID()), asignatura);
								TreeMap <Integer, Grupo> listaGruposAsignados = profesor.getListaGruposAsignados();
								listaGruposAsignados.put(new Integer (listaGruposAsignados.size() +1), grupo); 
								

							} else {
								Grupo grupo = new Grupo(Integer.parseInt(datosGrupo[2].trim()),tipoGrupo.charAt(0), IDAsignatura);
								listaGruposTeoricos.put(new Integer(datosGrupo[2].trim()), grupo);
								grupo.setIDDocente(profesor.getID());
								//profesor.listaDocencia.put(new Integer(asignatura.getID()), asignatura);
								TreeMap <Integer, Grupo> listaGruposAsignados = profesor.getListaGruposAsignados();
								listaGruposAsignados.put(new Integer (listaGruposAsignados.size() +1), grupo); 
								
								
							}
						}
						
						if (tipoGrupo.charAt(0)=='P'){
							
							TreeMap <Integer, Grupo> listaGruposPracticos = asignatura.getListaGruposPracticos();
							
							if(listaGruposPracticos.containsKey(Integer.parseInt(datosGrupo[2].trim()))){
								Grupo grupo = listaGruposPracticos.get(Integer.parseInt(datosGrupo[2].trim()));
								grupo.setIDDocente(profesor.getID());
								//profesor.listaDocencia.put(new Integer(asignatura.getID()), asignatura);
								TreeMap <Integer, Grupo> listaGruposAsignados = profesor.getListaGruposAsignados();
								listaGruposAsignados.put(new Integer (listaGruposAsignados.size() +1), grupo); 
								
								
							} else {
								Grupo grupo = new Grupo(Integer.parseInt(datosGrupo[2].trim()),tipoGrupo.charAt(0), IDAsignatura);
								listaGruposPracticos.put(new Integer(datosGrupo[2].trim()), grupo);
								grupo.setIDDocente(profesor.getID());
								//profesor.listaDocencia.put(new Integer(asignatura.getID()), asignatura);
								TreeMap <Integer, Grupo> listaGruposAsignados = profesor.getListaGruposAsignados();
								listaGruposAsignados.put(new Integer (listaGruposAsignados.size() +1), grupo); 
								
							}
						}	
					}
				
			}

			}
			
		}
			
		}
		
		punteroLectura.close();
		
	}
	
	/**Método encargado de leer los datos preexistentes en el sistema en relación a las asignaturas.
	 * Este método se ocupa de leer los datos almacenados en el fichero 'asignaturas.txt', que supondrá siempre correctos. Se apoyará en lo ya leido de personas, y completará la ejecución del mismo.
	 * 
	 * @param censo Censo en el que se encuentran almacenadas e identificadas las personas y las asignaturas que formarían parte del sistema.*/
	public static void leerAsignaturas (Censo censo){
		TreeMap <Integer, Asignatura> asignaturas = censo.getAsignaturas();
		Scanner punteroLectura=null;
		
		try{
			punteroLectura = new Scanner(new FileInputStream("asignaturas.txt"));
		} catch (FileNotFoundException e){
			new Aviso ("Error: no existe el archivo 'asignaturas.txt'.");
			System.exit(-1);
		}
		
		//System.out.println("El archivo de personas existe.");
		
		//Posible simplificación de código. FUNCIONA, ERGO NO SE TOCA.
		while(punteroLectura.hasNextLine()){
			punteroLectura.useDelimiter("\\*");
			
			String arrayArchivo="";
			for(int i=0; i<9; i++){
				if (i!=8) arrayArchivo += punteroLectura.nextLine().replaceAll(" +"," ") + "#";
				else {
					arrayArchivo += punteroLectura.next().replaceAll(" +"," ");
					if (punteroLectura.hasNextLine()) punteroLectura.nextLine();
				}
			}

			String listaDatos[] = arrayArchivo.split("#");
	
			
			//System.out.println("Array de longitud " + listaDatos.length);
			for(int i=0; i<listaDatos.length; i++){
				if (listaDatos[i].trim().length() == 0) listaDatos[i] = "@";
				////System.out.println(listaDatos[i]);
			}
			//System.out.println();
			
			String[] prerrequisitosFinal = null;
			
			if(!listaDatos[4].trim().equals("@")){
			
				String[] prerrequisitos = listaDatos[4].replaceAll(" +", " ").split("; ");
				String temp="";

			
				for(int i=0; i<prerrequisitos.length; i++){
					if (i != prerrequisitos.length - 1) temp += prerrequisitos[i] + "#";
					else temp += prerrequisitos[i];
				}
				//System.out.println("Array prerrequisitos: " + temp);
				prerrequisitosFinal = temp.trim().split("#");
			
			}
			
			if(listaDatos[3].trim().equals("@")){
				//System.out.println("No hay coordinador");
				listaDatos[3] = "0";
			}
			
			if(listaDatos[5].trim().equals("@")){ //Duración Teoría
				listaDatos[5] = "0";
			}
			
			if(listaDatos[6].trim().equals("@")){ //Duración Práctica
				listaDatos[6] = "0";
			}
			
			
			/*
			//System.out.println("Array de longitud " + listaDatos.length);
			for(int i=0; i<listaDatos.length; i++){
				if (listaDatos[i].length() == 0) listaDatos[i] = "@";
				////System.out.println(listaDatos[i]);
			}
			//System.out.println();
			*/
			
			
			if(!asignaturas.containsKey(Integer.parseInt(listaDatos[0].trim()))){
				//System.out.println("Creo asignatura");
				Asignatura asignatura = new Asignatura(Integer.parseInt(listaDatos[0].trim()), listaDatos[1].trim(), listaDatos[2].trim(), Integer.parseInt(listaDatos[3].trim()), prerrequisitosFinal);
				asignaturas.put(new Integer(Integer.parseInt(listaDatos[0].trim())), asignatura);
				
			} else {
				
				Asignatura asignatura = asignaturas.get(Integer.parseInt(listaDatos[0].trim()));
				asignatura.setNombre(listaDatos[1].trim());
				asignatura.setSiglas(listaDatos[2].trim());
				asignatura.setCoordinador(Integer.parseInt(listaDatos[3].trim()));
				if(Integer.parseInt(listaDatos[3].trim()) != 0){

					asignaCoordinador(Integer.parseInt(listaDatos[3].trim()),Integer.parseInt(listaDatos[0].trim()),censo);
	
				}
				asignatura.setPrerrequisitos(prerrequisitosFinal);
				
			}
			
			//Vamos con los grupos. Este dato puede existir o no.
			
			if(!listaDatos[7].trim().equals("@")){
				
				Asignatura asignatura = asignaturas.get(Integer.parseInt(listaDatos[0].trim()));
				String listadoGrupos[] = listaDatos[7].trim().split("; "); //Duda, se sobreescribe el grupo que haya en el list cuando metes un grupo con ese mismo ID?
				
				for (int i=0; i<listadoGrupos.length; i++){
					String datosGrupoNuevo[] = listadoGrupos[i].split(" ");
					TreeMap <Integer, Grupo> listaGruposTeoricos = asignatura.getListaGruposTeoricos();
					
					if (!listaGruposTeoricos.containsKey(new Integer(datosGrupoNuevo[0]))){
						Grupo grupo = new Grupo (Integer.parseInt(datosGrupoNuevo[0]), 'T', asignatura.getID());
						grupo.setDia(datosGrupoNuevo[1].charAt(0));
						grupo.setHora(Integer.parseInt(datosGrupoNuevo[2]));
						grupo.setDuracionGrupo(Integer.parseInt(listaDatos[5].trim()));
						listaGruposTeoricos.put(new Integer(datosGrupoNuevo[0]), grupo);
						
					} else {
						
						Grupo grupo = listaGruposTeoricos.get(new Integer(datosGrupoNuevo[0]));
						grupo.setDia(datosGrupoNuevo[1].charAt(0));
						grupo.setHora(Integer.parseInt(datosGrupoNuevo[2]));
						grupo.setDuracionGrupo(Integer.parseInt(listaDatos[5].trim()));

					}
				}
				
			}
			
			
			if(!listaDatos[8].trim().equals("@")){
				Asignatura asignatura = asignaturas.get(Integer.parseInt(listaDatos[0].trim()));
				String listadoGrupos[] = listaDatos[8].trim().split("; "); //Duda, se sobreescribe el grupo que haya en el list cuando metes un grupo con ese mismo ID?
				
				for (int i=0; i<listadoGrupos.length; i++){
					String datosGrupoNuevo[] = listadoGrupos[i].split(" ");
					TreeMap <Integer, Grupo> listaGruposPracticos = asignatura.getListaGruposPracticos();
					
					if (!listaGruposPracticos.containsKey(new Integer(datosGrupoNuevo[0]))){
						Grupo grupo = new Grupo (Integer.parseInt(datosGrupoNuevo[0]), 'P', asignatura.getID());
						grupo.setDia(datosGrupoNuevo[1].charAt(0));
						grupo.setHora(Integer.parseInt(datosGrupoNuevo[2]));
						grupo.setDuracionGrupo(Integer.parseInt(listaDatos[6].trim()));
						
						
						listaGruposPracticos.put(new Integer(datosGrupoNuevo[0]), grupo);
						
					} else {
						
						Grupo grupo = listaGruposPracticos.get(new Integer(datosGrupoNuevo[0]));
						grupo.setDia(datosGrupoNuevo[1].charAt(0));
						grupo.setHora(Integer.parseInt(datosGrupoNuevo[2]));
						grupo.setDuracionGrupo(Integer.parseInt(listaDatos[6].trim()));
						

						
					}
				}
			}
				
	
		}	

		punteroLectura.close();
			
	}

	/**Método encargado de leer los comandos que el administrador inserte en el sistema. 
	 * Los comandos serán los que modifiquen los datos a gusto del gestor. Este archivo no recibirá modificación alguna en ningún momento.
	 * 
	 * @param censo Censo en el que están almacenados los datos relativos al Centro Universitario.*/
	public static void leerEjecucion(Censo censo){
		Scanner punteroLectura=null;
		String comandoAEjecutar="";
		
		Scanner punteroPrueba=null;
		
		try{
			punteroLectura = new Scanner(new FileInputStream("ejecucion.txt"));
			punteroPrueba = new Scanner(new FileInputStream("ejecucion.txt"));
		} catch (FileNotFoundException e){
			new Aviso("Error: no existe el fichero de ejecución.");
			System.exit(-1);
		}
		
		//System.out.println("El archivo de ejecución existe.");
		
		while(punteroLectura.hasNextLine() && punteroPrueba.hasNextLine()){
			comandoAEjecutar="";
			punteroLectura.useDelimiter(" ");
			
			String lecturaPrueba = punteroPrueba.nextLine();
			System.out.println(lecturaPrueba);
			
			if(lecturaPrueba.charAt(0) == '*') punteroLectura.nextLine();
			else {
				comandoAEjecutar = punteroLectura.next();
				seleccionarFuncion(comandoAEjecutar, punteroLectura, censo);
			}
		}
		
		punteroLectura.close();
		
		
	}

	/**Método que se encarga de seleccionar el comando adecuado a las órdenes del gestor. Este método llamará a los comandos necesarios en función de las órdenes introducidas.
	 * 
	 * @param funcion Comando que debe ejecutarse.
	 * @param punteroLectura Puntero que lee del archivo fuente de los comandos.
	 * @param censo Censo donde están almacenados los datos relativos sl Centro Universitairo.*/
	public static void seleccionarFuncion(String funcion, Scanner punteroLectura, Censo censo){
		
		boolean comandoCorrecto = false;
		
		
		if (funcion.equalsIgnoreCase("InsertaPersona")) {
			
			
			String datos = punteroLectura.nextLine().trim().replace(" +"," ");
			boolean controlComando = true;
			String arrayDatos [] = datos.split("\"");

			
			boolean perfilCorrecto = false;
			
			if (arrayDatos[0].trim().equalsIgnoreCase("alumno")){
				
				String arrayDatosEspecificos[] = null;
				perfilCorrecto = true;
				
				
				if(arrayDatos.length != 5){
					controlComando = false;
				} else{
					arrayDatosEspecificos = arrayDatos[4].replaceAll(" +"," ").trim().split(" "); 
					
					if(arrayDatosEspecificos.length != 3){
						controlComando = false;
					}
					
				}

				
				if(controlComando){
					
					String nombre = arrayDatos[1].trim();
					String apellidos = arrayDatos[3].trim();
					
					insertaPersona("alumno", nombre, apellidos, arrayDatosEspecificos, censo, 0, true);
					comandoCorrecto = true;
				} else comandoCorrecto = false;

				
			} 
			
			if (arrayDatos[0].trim().equalsIgnoreCase("profesor")){
				perfilCorrecto = true;
				String arrayDatosEspecificos[] = null;
				
				if(arrayDatos.length != 7){
					controlComando = false;
				} else {
					String arrayDatosEspecificosTemp [] = arrayDatos[4].replaceAll(" +"," ").trim().split(" ");
					
					if(arrayDatosEspecificosTemp.length != 2){
						controlComando = false;
					} else{
						String arrayDatosEspecificosTemp2 = arrayDatosEspecificosTemp[0] + "#" + arrayDatosEspecificosTemp[1] +"#" + arrayDatos[5].trim() + "#" + arrayDatos[6];
						arrayDatosEspecificos = arrayDatosEspecificosTemp2.trim().split("#");
						if(arrayDatosEspecificos.length != 4){
							
							controlComando = false;
							
						}
						
					}
					
			
					
					

					
				}
		
				
				if(controlComando){
					
					String nombre = arrayDatos[1].trim();
					String apellidos = arrayDatos[3].trim();
					insertaPersona("profesor", nombre, apellidos, arrayDatosEspecificos, censo, 0, true);
					comandoCorrecto = true;
					
				} else comandoCorrecto = false;
				

			}
			
			if(!perfilCorrecto){
				comandoCorrecto = false;
			}

			
		}
		
		
		
		
		if (funcion.equalsIgnoreCase("AsignaCoordinador")){
			boolean avisoGenerado = false;
			comandoCorrecto = true;
			
			String [] datos = punteroLectura.nextLine().trim().replaceAll(" +", " ").split(" ");
			
			if(datos.length == 2){
				int IDProfesor = 0;
				int IDAsignatura = 0;
				
				try{
					IDProfesor = Integer.parseInt(datos[0]);
					IDAsignatura = Integer.parseInt(datos[1]);
				} catch (NumberFormatException e){
					avisoGenerado = true;
				}

				
				
				if(!avisoGenerado){
					asignaCoordinador(IDProfesor, IDAsignatura, censo);
					
				} else new Aviso ("comandoIncorrecto", funcion);

				
			} else comandoCorrecto = false;


			
		}
		
		
		if (funcion.equalsIgnoreCase("AsignaCargaDocente")){
			String [] datos = punteroLectura.nextLine().trim().replaceAll(" +"," ").split(" ");
			boolean avisoGenerado = false;
			comandoCorrecto = true;	
			
			if(datos.length==4){
				
				int IDProfesor = 0;
				int IDAsignatura = 0;
				
				try{
					IDProfesor = Integer.parseInt(datos[0].trim());
					IDAsignatura = Integer.parseInt(datos[1].trim());
					
					@SuppressWarnings("unused")
					int IDGrupo = Integer.parseInt(datos[3].trim());
				} catch (NumberFormatException e){
					
					avisoGenerado = true;
				}
			
				if(!avisoGenerado){
					asignaCargaDocente(IDProfesor, IDAsignatura , datos, censo);
					
				} else new Aviso ("comandoIncorrecto",funcion);

				
			} else comandoCorrecto = false;

		}
		
		if(funcion.equalsIgnoreCase("Matricula")){
			String [] datos = punteroLectura.nextLine().trim().replaceAll(" +"," ").split(" ");
			boolean avisoGenerado = false;
			comandoCorrecto = true;
			
			if(datos.length == 2){
				int IDAlumno = 0;
				int IDAsignatura = 0;
				
				try{
					IDAlumno = Integer.parseInt(datos[0].trim());
					IDAsignatura = Integer.parseInt(datos[1].trim());
				} catch (NumberFormatException e){
					
					avisoGenerado = true;
				}
				
				if(!avisoGenerado){
					matricula(IDAlumno, IDAsignatura, censo);
					
				} else new Aviso ("comandoIncorrecto",funcion);
	
			} else comandoCorrecto = false;
	

		}
		
		if(funcion.equalsIgnoreCase("AsignaGrupo")){
		
			String [] datos = punteroLectura.nextLine().trim().replaceAll(" +"," ").split(" ");
			boolean avisoGenerado = false;
			comandoCorrecto = true;
			
			if(datos.length == 4){
				
				int IDAlumno =0;
				int IDAsignatura = 0;
				int IDGrupo =0;
				char tipoGrupo = datos[2].trim().charAt(0);
				
				try{
					IDAlumno = Integer.parseInt(datos[0].trim());
					IDAsignatura = Integer.parseInt(datos[1].trim());
					IDGrupo = Integer.parseInt(datos[3].trim());
				} catch (NumberFormatException e){
					
					avisoGenerado = true;
				}
				
				

				if(!avisoGenerado){
					asignaGrupo(IDAlumno, IDAsignatura, tipoGrupo, IDGrupo, censo);
					
				} else new Aviso ("comandoIncorrecto",funcion);

				
			} else comandoCorrecto = false;

		}
		
		
		if(funcion.equalsIgnoreCase("Evaluar")){
			String [] datos = punteroLectura.nextLine().trim().replaceAll(" +"," ").split(" ");
			boolean avisoGenerado = false;
			comandoCorrecto = true;	
			
			if(datos.length == 2){

				int IDAsignatura = 0;
				
				try {
					IDAsignatura = Integer.parseInt(datos[0]);
				} catch (NumberFormatException e){
					
					avisoGenerado = true;
				}
				
				String salida = datos[1];
				
				if(!avisoGenerado){
					evaluar(IDAsignatura, salida, censo);
					
				} else new Aviso ("comandoIncorrecto",funcion);

			}else comandoCorrecto = false;
			

		}
		
		if(funcion.equalsIgnoreCase("ObtenerCalendarioClases")){
			
			String [] datos = punteroLectura.nextLine().trim().replaceAll(" +", " ").split(" ");
			boolean avisoGenerado = false;
			comandoCorrecto = true;
			
			if(datos.length == 2){
				int IDAlumno = 0;
				
				try{
					IDAlumno = Integer.parseInt(datos[0]);
				} catch (NumberFormatException e){
					
					avisoGenerado = true;
				}
				String salida = datos[1].trim();
				
				if(!avisoGenerado){
					obtenerCalendarioClases(IDAlumno, salida, censo);
					
				}else new Aviso ("comandoIncorrecto",funcion);

				
			} else comandoCorrecto = false;
			

		}
	
		
		if (funcion.equalsIgnoreCase("OrdenarProfesoresPorCargaDocente")){
			String[] datos = punteroLectura.nextLine().trim().replaceAll(" +"," ").split(" ");
			comandoCorrecto = true;
			
			if(datos.length == 1){
			
				ordenarProfesoresPorCargaDocente(datos[0].trim(), censo);
				
				
			} else comandoCorrecto = false;
			
		}

		
		if(!comandoCorrecto){
			new Aviso("comandoIncorrecto", funcion);
		}
	}

	/**Método que ingresa a una persona en el sistema. Este método comprobará que los datos introducidos sean correctos y, en caso afirmativo, procederá a agregar dicha persona al sistema universitario del centro.
	 * En caso contrario, generará un aviso correspondiente y no se insertará a la persona en el sistema.
	 * 
	 * @param perfil Perfil de la persona (alumno o profesor).
	 * @param nombre Nombre de la persona.
	 * @param apellidos Apellidos de la persona.
	 * @param arrayDatos Listado con los datos específicos de cada persona en función de su perfil.
	 * @param censo Censo donde se encuentran almacenadas todas las variables relativas al Centro Universitario.
	 * @param IDenviada Identificador de la persona. Se emplea como variable debido a que, al leer del archivo, el ID ya está asignado, pero si se inserta a la persona mediante un comando, se le debe asignar el primer identificador libre. 
	 * @param esComando Parámetro que diferencia a las personas insertadas manualmente (a las que se les debe asignar internamente un identificador) de las que no (ya lo traen asignado).*/
	public static void insertaPersona(String perfil, String nombre, String apellidos, String[] arrayDatos, Censo censo, int IDenviada, boolean esComando){
		
		boolean avisoGenerado = false;
		TreeMap <Integer, Persona> personas = censo.getPersonas();
		 
		 String fechaNacString[] = arrayDatos[0].trim().split("/");
		 GregorianCalendar fechaNGreg = null;

		 try{
			 fechaNGreg = new GregorianCalendar(Integer.parseInt(fechaNacString[2].trim()), Integer.parseInt(fechaNacString[1].trim()), Integer.parseInt(fechaNacString[0].trim()));
		 } catch(NumberFormatException e){
				new Aviso("InsertaPersona",0);
				avisoGenerado = true;
		 } catch(IndexOutOfBoundsException e){
				new Aviso("InsertaPersona",0);
				avisoGenerado = true;
		 }

			 
		 if(esComando){
			 if(!avisoGenerado){
				 if(fechaNGreg.before(new GregorianCalendar(1950,01,01)) || (fechaNGreg.after(new GregorianCalendar(2020, 01,01)))){
						
						new Aviso("InsertaPersona",0);
						avisoGenerado = true;
					 } 
			 }
		 }

		int ID;
		if(esComando){
		 ID = getPrimerIDLibre(personas);
		} else ID = IDenviada;

			if (perfil.equals("alumno")){			 

				
				if(esComando){
					
					String fechaN = arrayDatos[0].trim();
					String fechaNS[] = fechaN.split("/");
					int year1 = 0;
					int month1 = 0;
					int day1 = 0;
					GregorianCalendar fechaN1 = null;
					
					
					try{
						year1 = Integer.parseInt(fechaNS[2]);
						month1 = Integer.parseInt(fechaNS[1]);
						day1 = Integer.parseInt(fechaNS[0]);
					} catch (NumberFormatException e){
						new Aviso("InsertaPersona",0);
						avisoGenerado = true;
					}

					if(!avisoGenerado){
						if (((month1-1) < Calendar.getInstance().getMinimum(Calendar.MONTH)) || ((month1-1) > Calendar.getInstance().getMaximum(Calendar.MONTH))) {
							new Aviso("InsertaPersona",0);
							avisoGenerado = true;
				        	
				        } 
				    		fechaN1 = new GregorianCalendar(year1, month1-1, 1);
				    		
				    		if (day1 > fechaN1.getActualMaximum(Calendar.DAY_OF_MONTH)){
								new Aviso("InsertaPersona",0);
								avisoGenerado = true;				    			
				    		}
					}
					
				}
				
				if(esComando){
					
					String fechaI = arrayDatos[1].trim();
					
					String fechaIS[] = fechaI.split("/");
					int year1 = 0;
					int month1 = 0;
					int day1 = 0;
					GregorianCalendar fechaI1 = null;
					
					
					try{
						year1 = Integer.parseInt(fechaIS[2]);
						month1 = Integer.parseInt(fechaIS[1]);
						day1 = Integer.parseInt(fechaIS[0]);
					} catch (NumberFormatException e){
						new Aviso("InsertaPersona",0);
						avisoGenerado = true;
					}

					if(!avisoGenerado){
						System.out.println("Me ejecuto");
						if (((month1-1) < Calendar.getInstance().getMinimum(Calendar.MONTH)) || ((month1-1) > Calendar.getInstance().getMaximum(Calendar.MONTH))) {
							new Aviso("InsertaPersona",0);
							avisoGenerado = true;
				        	
				        } 
				    		fechaI1 = new GregorianCalendar(year1, month1-1, 1);
				    		
				    		if (day1 > fechaI1.getActualMaximum(Calendar.DAY_OF_MONTH)){
								new Aviso("InsertaPersona",0);
								avisoGenerado = true;				    			
				    		}
				    		
							if(!avisoGenerado){
								if(fechaI1.before(new GregorianCalendar(1950,01,01)) || (fechaI1.after(new GregorianCalendar(2020, 01,01)))){
									
									new Aviso("InsertaPersona",0);
									avisoGenerado = true;
								 } 
							}
					}
								
				
				}
				

				
				int diferenciaFechas = diferenciaFechas(arrayDatos[0].trim(), arrayDatos[1].trim());
				//System.out.println("DiferenciaFechas: " + diferenciaFechas + "ID: " + IDenviada);
				if(esComando){
					
					if((diferenciaFechas < 15) || (diferenciaFechas >= 65)){
						
						new Aviso("InsertaPersona",1);
						avisoGenerado=true;
						
					}
				}



				double notaMediaExpediente=0.00;
				
				try{
					notaMediaExpediente= Double.parseDouble(arrayDatos[2].trim().replaceAll(" ",""));
				} catch(NumberFormatException e){
					if(esComando){
						new Aviso("InsertaPersona",2);
						avisoGenerado = true;
					}
				}
				
				if(esComando && !avisoGenerado){
					if((notaMediaExpediente<5) || (notaMediaExpediente>10)){
						new Aviso("InsertaPersona",2);
						avisoGenerado = true;
					}
				}
				
				 if(!avisoGenerado) {
					 
					 personas.put(ID, new Alumno (nombre, apellidos, arrayDatos[0].trim(), ID, arrayDatos[1].trim(), notaMediaExpediente));
				 }
			}
				
			if (perfil.equals("profesor")){
				
				int horasAsignables = Integer.parseInt(arrayDatos[3].trim().replaceAll(" ",""));

				
				if(esComando){
					
					String fechaN = arrayDatos[0].trim();
					String fechaNS[] = fechaN.split("/");
					int year1 = 0;
					int month1 = 0;
					int day1 = 0;
					GregorianCalendar fechaN1 = null;
					
					
					try{
						year1 = Integer.parseInt(fechaNS[2]);
						month1 = Integer.parseInt(fechaNS[1]);
						day1 = Integer.parseInt(fechaNS[0]);
					} catch (NumberFormatException e){
						new Aviso("InsertaPersona",0);
						avisoGenerado = true;
					}

					if(!avisoGenerado){
						if (((month1-1) < Calendar.getInstance().getMinimum(Calendar.MONTH)) || ((month1-1) > Calendar.getInstance().getMaximum(Calendar.MONTH))) {
							new Aviso("InsertaPersona",0);
							avisoGenerado = true;
				        	
				        } 
				    		fechaN1 = new GregorianCalendar(year1, month1-1, 1);
				    		
				    		if (day1 > fechaN1.getActualMaximum(Calendar.DAY_OF_MONTH)){
								new Aviso("InsertaPersona",0);
								avisoGenerado = true;				    			
				    		}
					}
					
					
					if(arrayDatos[1].equalsIgnoreCase("titular")){
						if((horasAsignables < 0) || (horasAsignables > 20)){
							new Aviso("InsertaPersona",3);
							avisoGenerado = true;
						}
					}
					
					if(arrayDatos[1].equalsIgnoreCase("interino")){
						if((horasAsignables < 0) || (horasAsignables > 15)){
							new Aviso("InsertaPersona",3);
							avisoGenerado = true;
						}
					}
				}

				if(!avisoGenerado) personas.put(ID, new Profesor(nombre, apellidos, arrayDatos[0].trim(), ID, arrayDatos[2].trim(), arrayDatos[1].trim(), horasAsignables));
						
			}

	}

	/**Método que asigna a una asignatura un coordinador. Este método hará las comprobaciones pertinentes para saber si el profesor puede coordinar dicha asignatura y, si las supera todas, se le asignará.
	 * 
	 * @param IDProfesor Identificador del profesor futuro coordinador.
	 * @param IDAsignatura Identificador de la asignatura a coordinar.
	 * @param censo Censo donde se encuentran almacenadas todas las variables relativas al Centro Universitario.*/
	public static void asignaCoordinador(int IDProfesor, int IDAsignatura, Censo censo){
		TreeMap <Integer, Persona> personas = censo.getPersonas();
		TreeMap <Integer, Asignatura> asignaturas = censo.getAsignaturas();
		
		boolean avisoGenerado = false;
		
		if (!personas.containsKey(IDProfesor)){
			new Aviso ("AsignaCoordinador",0);
			avisoGenerado = true;
		}
		
		if (personas.containsKey(IDProfesor)){
			
			if(!(personas.get(IDProfesor) instanceof Profesor)){
				new Aviso ("AsignaCoordinador",0);
				avisoGenerado = true;
				
			}
			
			if(!avisoGenerado){
				if(((Profesor)personas.get(IDProfesor)).getCategoria().equalsIgnoreCase("interino")){
					new Aviso ("AsignaCoordinador",1);
					avisoGenerado = true;
				}
			}

		}
		
		if (!asignaturas.containsKey(IDAsignatura)){
			new Aviso ("AsignaCoordinador",2);
			avisoGenerado = true;
		}
		
		if (!avisoGenerado){
	
				if(((Profesor)personas.get(IDProfesor)).getListaCoordinacion().size() == 2){
					new Aviso ("AsignaCoordinador",3);
					avisoGenerado = true;
				}
			

		}
		
		
		if(!avisoGenerado){
			Profesor profesor = (Profesor)personas.get(IDProfesor);
			Asignatura asignatura = asignaturas.get(IDAsignatura);
			if(asignatura.getIDCoordinador() != 0){
				Profesor profesorAntiguo = (Profesor)personas.get(asignatura.getIDCoordinador());
				profesorAntiguo.eliminarAsignaturaCoordinada(asignatura);
				profesor.setCoordinadorAsignatura(asignatura);
				asignatura.setCoordinador(profesor.getID());
				
			}else {
			
				profesor.setCoordinadorAsignatura(asignatura);
				asignatura.setCoordinador(profesor.getID());
		
			}
		}	
	}

	/**Método que asigna un grupo de docencia a un profesor. Este método hará las comprobaciones pertinentes para saber si el profesor y el grupo reúnen los requisitos necesarios para poder llevar a cabo la conclusión de esta relación.
	 * 
	 * @param IDProfesor Identificador del profesor que se pretende que imparta el grupo.
	 * @param IDAsignatura IDentificador de la asignatura que contiene el grupo.
	 * @param datos Datos específicos del grupo que se debe impartir.
	 * @param censo Censo donde se encuentran almacenadas todas las variables relativas al Centro Univeristario.*/
	public static void asignaCargaDocente(int IDProfesor, int IDAsignatura, String[] datos, Censo censo){
		boolean avisoGenerado = false;
		TreeMap <Integer, Persona> personas = censo.getPersonas();
		TreeMap <Integer, Asignatura> asignaturas = censo.getAsignaturas();
		Asignatura asignatura = null;
		Profesor profesor = null;
		Grupo grupo = null;
		
		if(!personas.containsKey(IDProfesor)){
			new Aviso("AsignaCargaDocente", 0);
			avisoGenerado = true;
		} else {
			if (!(personas.get(IDProfesor) instanceof Profesor)){
				new Aviso("AsignaCargaDocente", 0);
				avisoGenerado = true;
			}
		}
		
		if(!asignaturas.containsKey(IDAsignatura)){
			new Aviso("AsignaCargaDocente", 1);
			avisoGenerado=true;
		}
		
		
		
		if((datos[2].charAt(0) != 'T' ) && (datos[2].charAt(0) != 'P')){
			new Aviso("AsignaCargaDocente", 2);
			avisoGenerado=true;
		}
		
		if(!avisoGenerado){
			profesor = (Profesor)personas.get(IDProfesor);
			asignatura = asignaturas.get(IDAsignatura);
			
			
			if((datos[2].charAt(0) == 'T') &&(asignaturas.containsKey(IDAsignatura))){
				
				TreeMap <Integer, Grupo> listaGruposTeoricos = asignatura.getListaGruposTeoricos();
				
				if (!listaGruposTeoricos.containsKey(Integer.parseInt(datos[3].trim()))){
					new Aviso ("AsignaCargaDocente", 3);
					avisoGenerado = true;
				}
				
			}
			

			if((datos[2].charAt(0) == 'P') && (asignaturas.containsKey(IDAsignatura))){
				TreeMap <Integer, Grupo> listaGruposPracticos = asignatura.getListaGruposPracticos();
				
				if (!listaGruposPracticos.containsKey(Integer.parseInt(datos[3].trim()))){
					new Aviso ("AsignaCargaDocente", 3);
					avisoGenerado = true;
				}
				
			}		

		}

		
		if(!avisoGenerado){
			
			if(datos[2].charAt(0) == 'T'){
				TreeMap <Integer, Grupo> listaGruposTeoricos = asignatura.getListaGruposTeoricos();
				
				grupo = listaGruposTeoricos.get(Integer.parseInt(datos[3].trim()));
				if (grupo.getIDDocente() != 0){
					new Aviso ("AsignaCargaDocente", 4);
					avisoGenerado=true;
				}
				
				if(!avisoGenerado){
					
					if(grupo.getDuracionGrupo() + profesor.getCargaDocente() > profesor.getHorasAsignables()){
						new Aviso ("AsignaCargaDocente", 5);
						avisoGenerado=true;
					}
					
					
				}

				
				
				if(!avisoGenerado){
					TreeMap <Integer, Grupo> listaGruposAsignados = profesor.getListaGruposAsignados();
					Iterator<Integer> iterador =listaGruposAsignados.keySet().iterator();
				
					while(iterador.hasNext()){
						Grupo grupoTemporal = listaGruposAsignados.get(iterador.next());
						
						if(grupo.getNumeroCronologico() == grupoTemporal.getNumeroCronologico()){
							new Aviso("AsignaCargaDocente", 6);
							avisoGenerado = true;
						}
					
					}
					
				}
				
				
			}
			
			if(datos[2].charAt(0) == 'P'){
				TreeMap <Integer, Grupo> listaGruposPracticos = asignatura.getListaGruposPracticos();
				
				grupo = listaGruposPracticos.get(Integer.parseInt(datos[3].trim()));
				if (grupo.getIDDocente() != 0){
					new Aviso ("AsignaCargaDocente", 4);
					avisoGenerado = true;
				}
				
				if(!avisoGenerado){
					if(grupo.getDuracionGrupo() + profesor.getCargaDocente() > profesor.getHorasAsignables()){
						new Aviso ("AsignaCargaDocente", 5);
						avisoGenerado = true;
					}	
					
				}

				if(!avisoGenerado){
					TreeMap <Integer, Grupo> listaGruposAsignados = profesor.getListaGruposAsignados();
					Iterator<Integer> iterador = listaGruposAsignados.keySet().iterator();
				
					while(iterador.hasNext()){
						Grupo grupoTemporal = listaGruposAsignados.get(iterador.next());
					
						if(grupo.getNumeroCronologico() == grupoTemporal.getNumeroCronologico()){
							new Aviso("AsignaCargaDocente", 6);
							avisoGenerado = true;
					}
					
				}
					
				}
				
				
				
				
			}
			

			
		}
		

		if(!avisoGenerado){
		
		if(datos[2].charAt(0) == 'T'){
			TreeMap <Integer, Grupo> listaGruposTeoricos = asignatura.getListaGruposTeoricos();
			
			grupo = listaGruposTeoricos.get(Integer.parseInt(datos[3].trim()));
			
			if (grupo.getIDDocente() != profesor.getID()){
				
				if (personas.containsKey(grupo.getIDDocente())){
				
					Profesor profesorAntiguo = (Profesor)personas.get(grupo.getIDDocente());
				
					TreeMap <Integer, Grupo> listaGruposAsignados = profesorAntiguo.getListaGruposAsignados();
					Iterator<Integer> iterador = listaGruposAsignados.keySet().iterator();
				
					Integer i = new Integer(0);
					
					while (iterador.hasNext()){
						i = iterador.next();
						Grupo grupoAntiguo = listaGruposAsignados.get(i);
						if ((grupo.getIDAsignatura() == grupoAntiguo.getIDAsignatura()) && (grupo.getTipoGrupo().equals(grupoAntiguo.getTipoGrupo())) && (grupo.getNumeroCronologico() == grupoAntiguo.getNumeroCronologico())){
							
							iterador.remove();
							
					}
			
				}
					iterador=null;
				}
				
				
				
			}
			
			grupo.setIDDocente(Integer.parseInt(datos[0].trim()));

			TreeMap <Integer, Grupo> listaGruposAsignados = profesor.getListaGruposAsignados();
			listaGruposAsignados.put(new Integer (listaGruposAsignados.size() +1), grupo); 

		} 
		
		
		if(datos[2].charAt(0) == 'P'){
			TreeMap <Integer, Grupo> listaGruposPracticos = asignatura.getListaGruposPracticos();
			
			grupo = listaGruposPracticos.get(Integer.parseInt(datos[3].trim()));

		
			if (grupo.getIDDocente() != profesor.getID()){
				
				if (personas.containsKey(grupo.getIDDocente())){
				
					Profesor profesorAntiguo = (Profesor)personas.get(grupo.getIDDocente());
				
					TreeMap <Integer, Grupo> listaGruposAsignados = profesorAntiguo.getListaGruposAsignados();
					Iterator<Integer> iterador = listaGruposAsignados.keySet().iterator();
				
					Integer i = new Integer(0);
					
					while (iterador.hasNext()){
						i = iterador.next();
						Grupo grupoAntiguo = listaGruposAsignados.get(i);
						if ((grupo.getIDAsignatura() == grupoAntiguo.getIDAsignatura()) && (grupo.getTipoGrupo().equals(grupoAntiguo.getTipoGrupo())) && (grupo.getNumeroCronologico() == grupoAntiguo.getNumeroCronologico())){
							
							iterador.remove();

					}
			
				}
					iterador=null;
				}
				
				
				
			}
			
			

			grupo.setIDDocente(Integer.parseInt(datos[0].trim()));
			


			//profesor.listaDocencia.put(new Integer(asignatura.getID()), asignatura);
			TreeMap <Integer, Grupo> listaGruposAsignados = profesor.getListaGruposAsignados();
			listaGruposAsignados.put(new Integer (listaGruposAsignados.size() +1), grupo); 

			

		} 
		
		}
		
	}

	/**Método que matricula a un alumno en una asignatura. Este método hará las comprobaciones pertinentes y, si se reunen los requisitos, matriculará al alumno en la asignatura indicada.
	 * 
	 * @param IDAlumno Identificador del alumno a matricular.
	 * @param IDAsignatura Identificador de la asignatura a matricular.
	 * @param censo Censo donde están almacenadas todas las variables relativas al Centro Universitario.*/
	public static void matricula (int  IDAlumno, int IDAsignatura, Censo censo){
		boolean avisoGenerado = false;
		Alumno alumno = null;
		Asignatura asignatura = null;
		TreeMap <Integer, Persona> personas = censo.getPersonas();
		TreeMap <Integer, Asignatura> asignaturas = censo.getAsignaturas();
		
		if(!personas.containsKey(IDAlumno)){
			new Aviso ("Matricula", 0);
			avisoGenerado = true;
		} else {
			
			
			if (!(personas.get(IDAlumno) instanceof Alumno)){
				new Aviso("Matricula", 0);
				avisoGenerado = true;
			}
		}
		
		
		
		if(!asignaturas.containsKey(IDAsignatura)){
			new Aviso ("Matricula", 1);
			avisoGenerado = true;
		}
		
		
		
		if (!avisoGenerado){
			
			alumno = (Alumno)personas.get(IDAlumno);
			asignatura = asignaturas.get(IDAsignatura);
			
			TreeMap <Integer, Asignatura> asignaturasMatriculadas = alumno.getAsignaturasMatriculadas();
			if(asignaturasMatriculadas.containsKey(IDAsignatura)){
				new Aviso ("Matricula", 2);
				avisoGenerado = true;
			}

		}
		
		if (!avisoGenerado){
			String [] prerrequisitos = asignatura.getPrerrequisitos();
			boolean cumplePrerreq = true;
			
			if(prerrequisitos != null){
				
				String prerrTemp = prerrequisitos[0];
				prerrequisitos = prerrTemp.trim().split(", ");
				TreeMap <Integer, Asignatura> asignaturasSuperadas = alumno.getAsignaturasSuperadas();
				
				for (int i=0; i<prerrequisitos.length; i++){

					if(!asignaturasSuperadas.containsKey(Integer.parseInt(prerrequisitos[i]))){
						cumplePrerreq = false;
					}
				}
				
			}

			
			
			if(!cumplePrerreq){
				
				new Aviso("Matricula", 3);
				avisoGenerado = true;
			}
			
			
			
		}
		
		
		
		
		
		
		
		
		if(!avisoGenerado) alumno.matricularAsignatura(asignatura);
		
	}

	/**Método que asigna un alumno a un grupo de docencia. Este método hará las comprobaciones pertinentes para ver si el alumno puede asistir a dicho grupo y, en caso afirmativo, se le será asignado.
	 * 
	 * @param IDAlumno Identificador del alumno que requiere un grupo.
	 * @param IDAsignatura Identificador de la asignatura de la que se requiere el grupo.
	 * @param tipoGrupo Carácter del grupo que se requiere.
	 * @param IDGrupo Identificador del grupo.
	 * @param censo Censo donde se encuentran almacenadas todas las variables relativas al Centro Universitario.*/
	public static void asignaGrupo(int IDAlumno, int IDAsignatura, char tipoGrupo, int IDGrupo, Censo censo){
		
		boolean avisoGenerado = false;
		
		Alumno alumno = null;
		Asignatura asignatura = null;
		Grupo grupo = null;
		
		TreeMap <Integer, Persona> personas = censo.getPersonas();
		TreeMap <Integer, Asignatura> asignaturas = censo.getAsignaturas();
		
		
		if(!personas.containsKey(IDAlumno)){
			new Aviso("AsignaGrupo", 0);
			avisoGenerado = true;
		} else {
			if (!(personas.get(IDAlumno) instanceof Alumno)){
				new Aviso("AsignaGrupo", 0);
				avisoGenerado = true;
			}
		}
		
		
		if(!asignaturas.containsKey(IDAsignatura)){
			new Aviso("AsignaGrupo", 1);
			avisoGenerado = true;
		}
		
		
		if(!avisoGenerado){
			
			alumno = (Alumno)personas.get(IDAlumno);
			asignatura = asignaturas.get(IDAsignatura);
			TreeMap <Integer, Asignatura> asignaturasMatriculadas = alumno.getAsignaturasMatriculadas();
			if(!asignaturasMatriculadas.containsKey(IDAsignatura)){
				new Aviso("AsignaGrupo", 2);
				avisoGenerado = true;
			}
			
			
			if((tipoGrupo != 'T' ) && (tipoGrupo != 'P')){
				new Aviso("AsignaGrupo", 3);
				avisoGenerado=true;
			}
			
			
		}
		
		
		if(!avisoGenerado){
			
			if(tipoGrupo == 'T'){
				TreeMap <Integer, Grupo> listaGruposTeoricos = asignatura.getListaGruposTeoricos();
				
				if (!listaGruposTeoricos.containsKey(IDGrupo)){
					new Aviso ("AsignaGrupo", 4);
					avisoGenerado = true;
				} else grupo = listaGruposTeoricos.get(IDGrupo);
			}
			
			
			if(tipoGrupo == 'P'){
				TreeMap <Integer, Grupo> listaGruposPracticos = asignatura.getListaGruposPracticos();
				
				if (!listaGruposPracticos.containsKey(IDGrupo)){
					new Aviso ("AsignaGrupo", 4);
					avisoGenerado = true;
				} else grupo = listaGruposPracticos.get(IDGrupo);
			}
			
			
			
			
		}
		
		
		if(!avisoGenerado){
			TreeMap <Integer, Grupo> gruposTeoria = alumno.getGruposTeoria();
			Iterator<Integer> iterador = gruposTeoria.keySet().iterator();
			
			while(iterador.hasNext()){
				Grupo grupoTemp = gruposTeoria.get(iterador.next());
				
				if(grupo.getNumeroCronologico() == grupoTemp.getNumeroCronologico()){
					new Aviso ("AsignaGrupo",5);
					avisoGenerado = true;
				}
			}
			
			TreeMap <Integer, Grupo> gruposPractica = alumno.getGruposPractica();
			iterador = gruposPractica.keySet().iterator();
			
			while(iterador.hasNext()){
				Grupo grupoTemp = gruposPractica.get(iterador.next());
				
				if(grupo.getNumeroCronologico() == grupoTemp.getNumeroCronologico()){
					new Aviso ("AsignaGrupo",5);
					avisoGenerado = true;
				}
			}
			
			
			
		}

		
		if(!avisoGenerado){
			
			if(grupo.getTipoGrupo().equalsIgnoreCase("Teoría")){
				TreeMap <Integer, Grupo> gruposTeoria = alumno.getGruposTeoria();
				
				if(gruposTeoria.containsKey(asignatura)){
					gruposTeoria.remove(asignatura);
				}
				
				gruposTeoria.put(asignatura.getID(), grupo);
				
			}

			
			if(grupo.getTipoGrupo().equalsIgnoreCase("Práctica")){
				TreeMap <Integer, Grupo> gruposPractica = alumno.getGruposPractica();
				if(gruposPractica.containsKey(asignatura)){
					gruposPractica.remove(asignatura);
				}
				
				gruposPractica.put(asignatura.getID(), grupo);
			}
			

			
		}
	}

	/**Método que evalua una asignatura en función de un archivo con notas y actualiza los parámetros necesarios en los alumnos correspondientes. Concretamente, se actualizarán las asignaturas superadas, la nota media y los grupos a los que el alumno asiste a clase.
	 * 
	 * @param IDAsignatura Identificador de la asignatura a evaluar.
	 * @param directorioFichero Directorio en el que se encuentra el fichero con las notas.
	 * @param censo Censo donde se encuentran almacenadas las variables relativas al Centro Universitario.*/
	public static void evaluar(int IDAsignatura, String directorioFichero, Censo censo){
		
		boolean avisoGenerado = false;
		
		Scanner punteroLectura=null;
		Asignatura asignatura = null;
		
		TreeMap <Integer, Persona> personas = censo.getPersonas();
		TreeMap <Integer, Asignatura> asignaturas = censo.getAsignaturas();
		
		if(!asignaturas.containsKey(IDAsignatura)){
			new Aviso ("Evaluar",0);
			avisoGenerado = true;
		} else asignatura = asignaturas.get(IDAsignatura);
		

		try{
			punteroLectura = new Scanner(new FileInputStream(directorioFichero));

		} catch (FileNotFoundException e){
			new Aviso ("Evaluar" , 1);
			avisoGenerado = true;
		}
		
		if(!avisoGenerado){
			
			int linea = 1;
			
			while(punteroLectura.hasNextLine()){
				
				String datos[] = punteroLectura.nextLine().trim().replaceAll(" +", " ").split(" ");
				int IDAlumno = 0;
				double notaEvaluacion = -1;
				Alumno alumno = null;
				
				
				try{
					IDAlumno = Integer.parseInt(datos[0]);
					
				} catch (NumberFormatException e){
					
				}
				
				
				
				if(!personas.containsKey(IDAlumno)){
					new Aviso ("Evaluar", 2,linea, IDAlumno);
					avisoGenerado = true;
				} else {
					if(!(personas.get(IDAlumno) instanceof Alumno)){
						new Aviso ("Evaluar", 2, linea, IDAlumno);
						avisoGenerado = true;
					} else alumno = (Alumno)personas.get(IDAlumno);
				}
				
				if(!avisoGenerado){
					TreeMap <Integer, Asignatura> asignaturasMatriculadas = alumno.getAsignaturasMatriculadas();
					if(!(asignaturasMatriculadas.containsKey(IDAsignatura))){
						new Aviso ("Evaluar", 3, linea, IDAlumno);
						avisoGenerado = true;
					}
				}
				
				
				try{
					notaEvaluacion = Double.parseDouble(datos[1].trim());
				} catch (NumberFormatException e){
					
				}
				
				
				
				
				if ((notaEvaluacion < 0) || (notaEvaluacion > 10)){
					new Aviso ("Evaluar", 4, linea);
					avisoGenerado = true;
				}
				
				if(!avisoGenerado){
					if(notaEvaluacion >= 5){
						
						alumno.actualizarNota(notaEvaluacion);
						TreeMap <Integer, Asignatura> asignaturasMatriculadas = alumno.getAsignaturasMatriculadas();
						asignaturasMatriculadas.remove(asignatura.getID());
						alumno.setAsignaturaSuperada(asignatura);

						TreeMap <Integer, Grupo> gruposTeoria = alumno.getGruposTeoria();
						Iterator <Integer> iterador = gruposTeoria.keySet().iterator();
						
						while(iterador.hasNext()){
							Grupo grupoTemp = gruposTeoria.get(iterador.next());
							if(grupoTemp.getIDAsignatura() == asignatura.getID()) iterador.remove();
							
						}
						
						TreeMap <Integer, Grupo> gruposPractica = alumno.getGruposPractica();
						iterador = gruposPractica.keySet().iterator();
						
						while(iterador.hasNext()){
							Grupo grupoTemp = gruposPractica.get(iterador.next());
							if(grupoTemp.getIDAsignatura() == asignatura.getID()) iterador.remove();
							
						}

							
						
					} else {

						TreeMap <Integer, Grupo> gruposTeoria = alumno.getGruposTeoria();
						Iterator <Integer> iterador = gruposTeoria.keySet().iterator();
						
						while(iterador.hasNext()){
							Grupo grupoTemp = gruposTeoria.get(iterador.next());
							if(grupoTemp.getIDAsignatura() == asignatura.getID()) iterador.remove();
							
						}
						TreeMap <Integer, Grupo> gruposPractica = alumno.getGruposPractica();
						iterador = gruposPractica.keySet().iterator();
						
						while(iterador.hasNext()){
							Grupo grupoTemp = gruposPractica.get(iterador.next());
							if(grupoTemp.getIDAsignatura() == asignatura.getID()) iterador.remove();
							
						}
						
					}
						
				}
				avisoGenerado = false;
				linea ++;		
			}
					
		}		
	}

	/**Método que devuelve en un archivo el horario de un alumno, en orden cronológico creciente. El formato del archivo de salida vendrá dado como sigue: 'dia; hora; tipo grupo; id grupo; asignatura; docente'.
	 * 
	 * @param IDAlumno Identificador del alumno del que se quiere obtener el horario.
	 * @param salida Nombre del archivo de salida que contendrá el horario.
	 * @param censo Censo donde se encuentran almacenadas todas las variables relativas al Centro Universitario.*/
	public static void obtenerCalendarioClases(int IDAlumno, String salida,Censo censo){
		PrintWriter punteroEscritura = null;
		boolean avisoGenerado = false;
		Alumno alumno = null;
		TreeMap <Integer, Persona> personas = censo.getPersonas();
		TreeMap <Integer, Asignatura> asignaturas = censo.getAsignaturas();
		
		if(!(personas.containsKey(IDAlumno))){
			new Aviso ("ObtenerCalendarioClases",0);
			avisoGenerado = true;
		} else {
			if(!(personas.get(IDAlumno) instanceof Alumno)){
				new Aviso ("ObtenerCalendarioClases", 0);
				avisoGenerado = true;
			} else alumno = (Alumno)personas.get(IDAlumno);
			
		}
		
		if(!avisoGenerado){
			TreeMap <Integer, Grupo> gruposTeoria = alumno.getGruposTeoria();
			TreeMap <Integer, Grupo> gruposPractica = alumno.getGruposPractica();
			if((gruposTeoria.size() == 0) && (gruposPractica.size() == 0)){
				new Aviso ("ObtenerCalendarioClases" , 1);
				avisoGenerado = true;
			}
		}
		
		if(!avisoGenerado){
			new File(salida);
			
			try{
				punteroEscritura = new PrintWriter (new FileOutputStream (salida));
				
			} catch (FileNotFoundException e){
				
			}
			
			punteroEscritura.println("dia; hora; tipo grupo; id grupo; asignatura; docente");
			
			
			LinkedList<Grupo> horario = ordenCronologico(alumno);
			
	        ListIterator<Grupo> iterador2 = horario.listIterator();
	        
	        while (iterador2.hasNext()){
	        	
	        	Grupo grupo = new Grupo();
	        	grupo = iterador2.next();
	        	int IDDocente = grupo.getIDDocente();
	        	int IDAsignatura = grupo.getIDAsignatura();
	        	char tipoGrupo;
	        	
	        	if(grupo.getTipoGrupo().equalsIgnoreCase("Teoría")){
	        		tipoGrupo = 'T';
	        	} else tipoGrupo = 'P';
	        	
				String lineaSalida = grupo.getDia() + "; " + grupo.getHora() + "; " + tipoGrupo + "; " + grupo.getIDGrupo()  + "; ";
				
				try{
				lineaSalida += (asignaturas.get(IDAsignatura)).getNombre() + "; ";
				lineaSalida += (personas.get(IDDocente)).getNombre() + " ";
				lineaSalida += (personas.get(IDDocente)).getApellidos();
				} catch (NullPointerException e){
					new Aviso("comandoIncorrecto","ObtenerCalendarioClases");
				}
				
				
				punteroEscritura.println(lineaSalida);
	        	
	        }
			

	        iterador2=null;
	        
	        punteroEscritura.close();
		}
	
		
	}
	
	/**Método que devuelve, en un archivo, el listado de profesores titulares ordenados por la cantidad de carga docente asignada en orden creciente.
	 * 
	 * @param salida Nombre del archivo en el que se mostrarán los profesores. Este archivo tendrá el mismo formato que el archivo 'personas.txt'.
	 * @param censo Censo donde se encuentran almacenadas todas las variables relativas al Centro Universitario.*/
	public static void ordenarProfesoresPorCargaDocente(String salida, Censo censo){

		boolean avisoGenerado = false;
		
		LinkedList<Profesor> listaProfesores = ordenCargaDocente(censo);
		
		
		if(listaProfesores.size() == 0){
			new Aviso("OrdenarProfesoresPorCargaDocente",0);
			avisoGenerado = true;
		}
		
		
		if(!avisoGenerado){
			PrintWriter punteroEscritura = null;
			new File(salida);
			
			try{
				punteroEscritura = new PrintWriter (new FileOutputStream (salida));
				
			} catch (FileNotFoundException e){
				
			}
			
			int i=0;
	        ListIterator<Profesor> iterador2 = listaProfesores.listIterator();
	        while (iterador2.hasNext()){
	        	Profesor profesor = new Profesor();
	        	profesor = iterador2.next();
	        
	        		if (i==0){
	        			
	        			i++;
	        			
	        		} else {
	        			punteroEscritura.println("*");
	        			punteroEscritura.println(profesor.getID());
	        		}
	        		
	        		punteroEscritura.println("profesor");
	        		punteroEscritura.println(profesor.getNombre());
	        		punteroEscritura.println(profesor.getApellidos());
	        		punteroEscritura.println(profesor.getFechaNacimiento());
	        		punteroEscritura.println(profesor.getCategoria());
	        		punteroEscritura.println(profesor.getDepartamento());
	        		punteroEscritura.println(profesor.getHorasAsignables());
	        		TreeMap <Integer, Grupo> listaGruposAsignados = profesor.getListaGruposAsignados();
	            	Iterator<Integer> listaGrupos = listaGruposAsignados.keySet().iterator();
	            	
	            	String listaGruposString="";
	            	
	            	while(listaGrupos.hasNext()){
	            	//	if (profesor.getCategoria().equalsIgnoreCase("titular")){
	            			Grupo grupo = listaGruposAsignados.get(listaGrupos.next());
	                		char tipoGrupo;
	                		if(grupo.getTipoGrupo().equalsIgnoreCase("Teoría")) tipoGrupo='T' ; else tipoGrupo = 'P';
	                		
	                		listaGruposString += grupo.getIDAsignatura() + " " +  tipoGrupo + " " + grupo.getIDGrupo();
	                		if(listaGrupos.hasNext()) listaGruposString += ", ";
	            			
	            		//} else listaGrupos.next();
	            		
	            		
	            	}
	            	
	            	
	            	punteroEscritura.println(listaGruposString);
	            	
	            	//punteroEscritura.println("*");
	        	}

	        iterador2=null;
	       
	        punteroEscritura.close();
		}
		
	}

	/**Método que devuelve el listado de grupos al que asiste el alumno en orden cronológico creciente. Se usa en el método 'obtenerCalendarioClases'.
	 * 
	 * @param alumno Alumno del que se desea obtener el listado de grupos ordenado cronológicamente.
	 * 
	 * @return Listado con los grupos del alumno ordenados cronológicamente.*/
    public static LinkedList<Grupo> ordenCronologico(Alumno alumno){
    	
		LinkedList<Grupo> lista = new LinkedList <> ();
		
		TreeMap <Integer, Grupo> gruposTeoria = alumno.getGruposTeoria();
		Iterator<Integer> iterador1 = gruposTeoria.keySet().iterator();
		
		
		while (iterador1.hasNext()) {

			
				Grupo grupo = gruposTeoria.get(iterador1.next());
				lista.add(grupo);
				Collections.sort(lista, new Grupo());
		}
		TreeMap <Integer, Grupo> gruposPractica = alumno.getGruposPractica();
		iterador1 = gruposPractica.keySet().iterator();
		while (iterador1.hasNext()) {

			Grupo grupo = gruposPractica.get(iterador1.next());
			lista.add(grupo);
			Collections.sort(lista, new Grupo());
	}
		
		iterador1=null;
		return lista;
	}
    
    /**Método que devuelve el listado de profesores ordenados en función de la carga docente asignada.
     * @param censo Censo donde se encuentran almacenadas todas las variables relativas al Centro Universitario.
     * @return Listado con los profesores ordenados en función de su carga docente.*/
    public static LinkedList<Profesor> ordenCargaDocente(Censo censo){
    	LinkedList<Profesor> lista = new LinkedList<>();
    	TreeMap <Integer, Persona> personas = censo.getPersonas();
    	
    	Iterator<Integer> iterador = personas.keySet().iterator();
		while (iterador.hasNext()) {
			
			Persona persona = personas.get(iterador.next());
			
			if (persona instanceof Profesor){
				if(((Profesor)persona).getCategoria().equalsIgnoreCase("titular")){
					lista.add((Profesor)persona);
					Collections.sort(lista, new Profesor());
				}

			}
			
			

	}
    	
    	iterador=null;
    	return lista;
    }

    /**Método que calcula la diferencia entre dos fechas. Se utiliza para comprobar que las fechas estén en el rango permitido.
     * Este código (y el de los métodos que en el se utilizan) se ha reutilizado de la práctica 1, desarrollada por los creadores de este mismo proyecto.
     * @param fechaNacimiento Fecha de nacimiento del sujeto.
     * @param fechaIngreso Fecha de ingreso a la titulación del sujeto.
     * 
     * @return Diferencia, en años, entre las fechas introducidas.*/
	public static int diferenciaFechas(String fechaNacimiento, String fechaIngreso) {

		
		String fechaNacDef[] = fechaNacimiento.split("/");
		String fechaIngDef[] = fechaIngreso.split("/");
		
		GregorianCalendar fechaN = null;
		GregorianCalendar fechaI = null;
		
		int year1 = Integer.parseInt(fechaNacDef[2]);
		int month1 = Integer.parseInt(fechaNacDef[1]);
		int day1 = Integer.parseInt(fechaNacDef[0]);
		
		int year2 = Integer.parseInt(fechaIngDef[2]);
		int month2 =Integer.parseInt(fechaIngDef[1]);
		int day2 = Integer.parseInt(fechaIngDef[0]);
		
    	fechaN = new GregorianCalendar(year1, month1-1, day1);
    	fechaI = new GregorianCalendar(year2, (month2-1), day2);

		//Apartado 1 del ejercicio: diferencia entre las dos fechas expresada en años, meses y días.
		
		//Parámetro de control
 		fechaI.add(Calendar.MONTH, -1);
		int diffDayTest = fechaI.getActualMaximum(Calendar.DAY_OF_MONTH);
 		fechaI.add(Calendar.MONTH, 1);
		
		//Variables necesarias
		int diffYear = 0;
		int diffMonth = 0;
		int diffDay = 0;
		
		diffYear = getAnualDiff (fechaN, fechaI);
		diffMonth = getMonthDiff (fechaN, fechaI);
		diffDay = getDayDiff (fechaN, fechaI);
				
		//Realizamos unos ajustes para casos especiales (casos muy particulares; que el día coincida o que se lleguen a los 12 meses).
		int control=0;
		if (diffMonth==11) {
			diffYear++;
			diffMonth = 0;
			control=1;
		}
			
		if (diffDayTest == diffDay)	{
			if (control == 0) diffMonth++;
			diffDay=0; 
		}

		return diffYear;
	
    
    
	}

	/**Método inherente de 'diferenciaFechas'. Sin utilidad fuera de dicho método.
	 * 
	 * @param calendario1 Fecha Nacimiento.
	 * @param calendario2 Fecha Ingreso.
	 * 
	 * @return Diferencia de años.*/
    public static int getAnualDiff (Calendar calendario1, Calendar calendario2){
		int diffYear = -1;
		if (calendario1.equals(calendario2)) diffYear = 0;
		else {
		    while (calendario1.before(calendario2)) {
				calendario1.add(Calendar.YEAR, 1);
				diffYear++;
			}
			calendario1.add(Calendar.YEAR, -1);		
		}
		
		return diffYear;
	}
	
	/**Método inherente de 'diferenciaFechas'. Sin utilidad fuera de dicho método.
	 * 
	 * @param calendario1 Fecha Nacimiento.
	 * @param calendario2 Fecha Ingreso.
	 * 
	 * @return Diferencia de meses.*/
	public static int getMonthDiff (Calendar calendario1, Calendar calendario2){
		int diffMonth = -1;
		if (calendario1.equals(calendario2)) diffMonth = 0;
		else {
		    while (calendario1.before(calendario2)) {
				calendario1.add(Calendar.MONTH, 1);
				diffMonth++;
			}
			calendario1.add(Calendar.MONTH, -1);		
		}
		
		return diffMonth;
	}
	
	/**Método inherente de 'diferenciaFechas'. Sin utilidad fuera de dicho método.
	 * 
	 * @param calendario1 Fecha Nacimiento.
	 * @param calendario2 Fecha Ingreso.
	 * 
	 * @return Diferencia de días.*/
	public static int getDayDiff (Calendar calendario1, Calendar calendario2){
		int diffDay = 0;
		if (calendario1.equals(calendario2)) diffDay = 0;
		else {
		    while (calendario1.before(calendario2)) {
				calendario1.add(Calendar.DAY_OF_MONTH, 1);
				diffDay++;
			}	
		}
		
		return diffDay;
	}
    
	/**Método que se encarga de guardar en el archivo 'personas.txt' todos los datos necesarios para poder identificar a una persona en el sistema, junto con los correspondientes atributos académcicos.
	 * 
	 * @param censo Censo donde se encuentran almacenadas todas las variables relativas al Centro Universitario.*/
	public static void guardarPesonas(Censo censo){
		TreeMap <Integer, Persona> personas = censo.getPersonas();
		
		PrintWriter punteroEscritura = null;
		String salida = "personas.txt";
		
		try{
			punteroEscritura = new PrintWriter(salida);
		}catch(FileNotFoundException e){
			
		}
		
		
		int i=0;
		Iterator<Integer> iterador = personas.keySet().iterator();
		
		while(iterador.hasNext()){	
			Persona persona= personas.get(iterador.next());
			
				if(i==0){
					i++;
				}else{
					punteroEscritura.println("*");
        			
        		}
				
				punteroEscritura.println(persona.getID());
				
					if(persona instanceof Profesor){
						punteroEscritura.println("profesor");
					}else punteroEscritura.println("alumno");
					
        		punteroEscritura.println(persona.getNombre());
        		punteroEscritura.println(persona.getApellidos());
        		punteroEscritura.println(persona.getFechaNacimiento());
        			
        			if(persona instanceof Profesor){
        				
        				
        				punteroEscritura.println(((Profesor)persona).getCategoria());
        				punteroEscritura.println(((Profesor)persona).getDepartamento());
        				punteroEscritura.println(((Profesor)persona).getHorasAsignables());
        		
        				TreeMap <Integer, Grupo> listaGruposAsignados = ((Profesor)persona).getListaGruposAsignados();
        				Iterator<Integer> listaGrupos = listaGruposAsignados.keySet().iterator();
            	
        				String listaGruposString="";
            	
        				while(listaGrupos.hasNext()){
        					Grupo grupo = listaGruposAsignados.get(listaGrupos.next());
        					char tipoGrupo = 0;
        					if(grupo.getTipoGrupo().equalsIgnoreCase("Teoría")) tipoGrupo='T' ;
        					if(grupo.getTipoGrupo().equalsIgnoreCase("Práctica")) tipoGrupo = 'P';
        					listaGruposString += grupo.getIDAsignatura() +" " +  tipoGrupo + " " + grupo.getIDGrupo();
        					if(listaGrupos.hasNext()) listaGruposString += "; ";
        				}
        				punteroEscritura.println(listaGruposString);
        			}else{
        				
        				
        				punteroEscritura.println(((Alumno)persona).getFechaIngresoTitulacion());
        				
        				punteroEscritura.println(((Alumno)persona).getListaIDsSuperadas());
        				
        				String notaMedia ="";
        						
        						if(((Alumno)persona).getNotaMediaExpediente()==0){
        						
        						} else notaMedia += ((Alumno)persona).getNotaMediaExpediente();
        						
        				punteroEscritura.println(notaMedia);
        				
        				
        				String listaGrupoString="";
        				
        				TreeMap <Integer, Grupo> gruposTeoria = ((Alumno)persona).getGruposTeoria();
        				Iterator<Integer> iteradorGrupos = gruposTeoria.keySet().iterator();
        				TreeMap <Integer, Asignatura> asignaturasMatriculadas = ((Alumno)persona).getAsignaturasMatriculadas();

        				while(iteradorGrupos.hasNext()){
        					Grupo grupo = gruposTeoria.get(iteradorGrupos.next());
        					
        					
        					listaGrupoString += grupo.getIDAsignatura() + " " + 'T' + " " + grupo.getIDGrupo();
        					if(iteradorGrupos.hasNext())  listaGrupoString += "; ";
        					
        					if(asignaturasMatriculadas.containsKey(grupo.getIDAsignatura())) asignaturasMatriculadas.remove(grupo.getIDAsignatura());
        					
        				}
        				
        				TreeMap <Integer, Grupo> gruposPractica = ((Alumno)persona).getGruposPractica();
        				iteradorGrupos = gruposPractica.keySet().iterator();
        				if((gruposPractica.size() != 0) && (!listaGrupoString.equals(""))){
        					listaGrupoString += "; ";
        				}
        				
        				while(iteradorGrupos.hasNext()){
        					
        					Grupo grupo = gruposPractica.get(iteradorGrupos.next());
        					
        					
        					listaGrupoString += grupo.getIDAsignatura() + " " + 'P' + " " + grupo.getIDGrupo();
        					if(iteradorGrupos.hasNext())  listaGrupoString += "; ";
        					
        					if(asignaturasMatriculadas.containsKey(grupo.getIDAsignatura())) asignaturasMatriculadas.remove(grupo.getIDAsignatura());
        					
        				}
        				
        				
        				if(asignaturasMatriculadas.size() != 0){
        					Iterator<Integer> iteradorMatriculadas = asignaturasMatriculadas.keySet().iterator();
        					if(!listaGrupoString.equals("")) listaGrupoString += "; ";
        					
        					while(iteradorMatriculadas.hasNext()){
        						Asignatura asigTemp = asignaturasMatriculadas.get(iteradorMatriculadas.next());
        						
        						listaGrupoString += asigTemp.getID();
        						if(iteradorMatriculadas.hasNext()) listaGrupoString += "; ";
        					}
        					
        				}
        				
        				punteroEscritura.println(listaGrupoString);
        			}

        			
		}
		iterador=null;
	       
        punteroEscritura.close();
	}	

	/**Método que se encarga de guardar en el archivo 'asignaturas.txt' todos los datos relativos a las asignaturas.
	 * 
	 *  @param censo Censo donde se encuentran almacenadas todas las variables relativas al Centro Universitario.*/
	public static void guardarAsignaturas(Censo censo){
		TreeMap <Integer, Asignatura> asignaturas = censo.getAsignaturas();
		
		PrintWriter punteroEscritura = null;
		String salida = "asignaturas.txt";
		
		try{
			punteroEscritura = new PrintWriter(salida);
		}catch(FileNotFoundException e){

		}
		
		
		int i=0;
		Iterator<Integer> iterador = asignaturas.keySet().iterator();
		
		while (iterador.hasNext()){
			Asignatura asignatura = asignaturas.get(iterador.next());
			
			if(i==0){
				i++;
			}else{
				punteroEscritura.println("*");
			}
			
			
			punteroEscritura.println(asignatura.getID());
			punteroEscritura.println(asignatura.getNombre());
			punteroEscritura.println(asignatura.getSiglas());
			
			int IDCoordinador = asignatura.getIDCoordinador();
			
			
			if(IDCoordinador == 0){
				punteroEscritura.println("");
			} else punteroEscritura.println(IDCoordinador);
			
			
			
			String [] prerrequisitos = asignatura.getPrerrequisitos();
			
			if(prerrequisitos != null){
				String prerrTemp = prerrequisitos[0];
				prerrequisitos = prerrTemp.trim().split(", ");
				String prerreqSalida="";
				
				if(prerrequisitos.length != 0){
						for (int j=0; j<prerrequisitos.length; j++){
						prerreqSalida += prerrequisitos[j];
					
						if(!(j==prerrequisitos.length-1)) prerreqSalida +=", ";
					}
				
					punteroEscritura.println(prerreqSalida);
				} else punteroEscritura.println("");
				
				
			}else punteroEscritura.println("");
			
			
			
			if(asignatura.getListaGruposTeoricos().size() == 0)	punteroEscritura.println("");
			else punteroEscritura.println(asignatura.getListaGruposTeoricos().get(1).getDuracionGrupo());
		
			
			if(asignatura.getListaGruposPracticos().size() == 0) punteroEscritura.println("");
			else punteroEscritura.println(asignatura.getListaGruposPracticos().get(1).getDuracionGrupo());
			
			
			
			TreeMap <Integer, Grupo> listaGruposTeoricos = asignatura.getListaGruposTeoricos();
			
			if(listaGruposTeoricos.size() != 0){
				Iterator<Integer> iterador2 = listaGruposTeoricos.keySet().iterator();
				String listaGruposTeoriaString="";
				
				while (iterador2.hasNext()){
					Grupo grupo = listaGruposTeoricos.get(iterador2.next());
					listaGruposTeoriaString += grupo.getIDGrupo() + " " + grupo.getDia() + " " + grupo.getHora();
					if(iterador2.hasNext()) listaGruposTeoriaString += "; ";
					
				}
				
				punteroEscritura.println(listaGruposTeoriaString);
			} else punteroEscritura.println("");
			
			
			TreeMap <Integer, Grupo> listaGruposPracticos = asignatura.getListaGruposPracticos();
			
			if(listaGruposPracticos.size() != 0){
				Iterator<Integer> iterador2 = listaGruposPracticos.keySet().iterator();
				String listaGruposTeoriaString="";
				
				while (iterador2.hasNext()){
					Grupo grupo = listaGruposPracticos.get(iterador2.next());
					listaGruposTeoriaString += grupo.getIDGrupo() + " " + grupo.getDia() + " " + grupo.getHora();
					if(iterador2.hasNext()) listaGruposTeoriaString += "; ";
					
				}
				
				if(iterador.hasNext()) punteroEscritura.println(listaGruposTeoriaString);
				else punteroEscritura.print(listaGruposTeoriaString);
				
			} else{
				
				if(iterador.hasNext()) punteroEscritura.println("");
				else punteroEscritura.print("");
			}
			

			
		}
		
		iterador=null;
		punteroEscritura.close();
	}

	/**Método que busca el primer identificador para personas libre en el sistema.
	 * 
	 * @param listado Listado con las personas incluidas en el sistema, referenciadas por su identificador.
	 * 
	 * @return Primer identificador para personas libre del sistema.*/
	public static int getPrimerIDLibre(TreeMap<Integer, Persona> listado){
		
		int IDRetorno=1;
		
		if(!listado.isEmpty()){
			
			if (listado.size() == 1){
				if (listado.containsKey(1)) IDRetorno = 2;
				else IDRetorno = 1;
			} else {
				
				Iterator<Integer> iterador = listado.keySet().iterator();
				
				Integer key=0;
				
				while(iterador.hasNext()){
					key = (Integer) iterador.next();
				}
				
				if (key-listado.size() != 0){

					for (int j=1; j<key; j++){
						if (!listado.containsKey(j))return j;
						
					}
					
				} else IDRetorno = listado.size()+1;
				
			}

		}

		return IDRetorno;
	}
	
	
}
