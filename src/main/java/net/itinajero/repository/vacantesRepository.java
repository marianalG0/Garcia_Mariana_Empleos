package net.itinajero.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import net.itinajero.model.vacante;

public interface vacantesRepository extends JpaRepository<vacante, Integer> {
	
	//DEFINIENDO NUESTRO METODO SIGUIENDO LAS REGLAS
	
	List<vacante> findByEstatus(String estatus);
	
	List<vacante> findByDestacadoAndEstatusOrderByIdDesc(int destacado, String estatus);
		//Como vamos a buscar por dos atributos agregamos el "AND" y agregamos ORDERBY para decir de que manera se mostrara en este caso por id y de forma descendiente
	
	List<vacante> findBySalarioBetweenOrderBySalarioDesc(double s1, double s2);
		//Regresa una lista de objetos de tipo vacante, escribimos la palabra reservada Findby, en este caso buscamos por salario
		// Ponemos between para buscar por un rando(en este caso de salario)
		// y tambien lo ordenamos por salarios y en forma descendiente
		//Dos parametros que seran los rangos 
	
	List<vacante> findByEstatusIn(String[] estatus);
		//	Buscar vacantes por varios status, usando la palabra reservada In
		//Le pasamos un arreglo de tipo string
}
