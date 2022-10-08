package net.itinajero.service;

import java.util.List;

import net.itinajero.model.vacante;

public interface IVacantesServices {
	List<vacante> buscarTodas();
	vacante buscarPorId(Integer idVacante);
	
	//Metodo que no regresara nada, recibe como parametro el objeto de tipo vacante
	void guardar(vacante vacante);
	//Metodo que regresara una lista de objetos de tipo vacante 
	List<vacante> buscarDestacadas();
	
	//Metodo que no regresa nada 
	void eliminar(Integer idVacante);
	
}
