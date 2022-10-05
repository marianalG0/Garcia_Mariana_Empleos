package net.itinajero.util;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class Utileria {
	//Codigo del metodo guardar archivo en el disco duro
	public static String guardarArchivo(MultipartFile multiPart, String ruta) {
		//Obtenemos el nombre original del archivo 
		String nombreOriginal = multiPart.getOriginalFilename();
		//De la variable, mandamos a llamar un metodo en donde pasamos dos parametros 1) caracter que buscamos 2)caracter que remplace el primero
		nombreOriginal = nombreOriginal.replace(" ", "-");
		String nombreFinal = randomAlphaNumeric(8) + nombreOriginal;// en esta variable ya tenemos el archivo final con los 8 caracteres al inicio seguido del nombre original
		
		try {
			//formamos el nombre del archivo para guardarlo en el disco duro
			File imageFile = new File(ruta+ nombreFinal);
			System.out.println("Archivo: " + imageFile.getAbsolutePath());
			//Guardamos fisicamente el archivo en HD
			multiPart.transferTo(imageFile);
			return nombreFinal;
		}catch(IOException e) {
			System.out.println("Error" + e.getMessage());
			return null;
		}
	}
	
	/*
	 * Metodo para generar una cadena aleatoria de longitud N
	 * @param count
	 * @return
	 * */
	public static String randomAlphaNumeric(int count) {//La cadena sera del numero al que le pasemos al count
		String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";//En la cadena se incluye los siguientes caracteres
		StringBuilder builder = new StringBuilder();
		while (count-- !=0) {
			int character = (int)(Math.random()* CARACTERES.length());
			builder.append(CARACTERES.charAt(character));
		}
		return builder.toString();
	}
} //NJknka
