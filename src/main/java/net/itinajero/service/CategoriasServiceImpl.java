package net.itinajero.service;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;
import net.itinajero.model.Categoria;

@Service
public class CategoriasServiceImpl implements ICategoriasService{

	private List<Categoria> lista = null;
	
	//Declarando un constructor para crear la lista
		//Recuerda que el constructor solo se ejecuta una vez cada que se crea una instancia
	public CategoriasServiceImpl() {
		lista = new LinkedList<Categoria>();
		
		// Creamos algunas Categorias para poblar la lista ...
		
		// Categoria 1
		Categoria cat1 = new Categoria();
		cat1.setId(1);
		cat1.setNombre("Contabilidad");
		cat1.setDescripcion("Descripcion de la categoria Contabilidad");
		
		// Categoria 2
		Categoria cat2 = new Categoria();
		cat2.setId(2);
		cat2.setNombre("Ventas");
		cat2.setDescripcion("Trabajos relacionados con Ventas");
		
					
		// Categoria 3
		Categoria cat3 = new Categoria();
		cat3.setId(3);
		cat3.setNombre("Comunicaciones");
		cat3.setDescripcion("Trabajos relacionados con Comunicaciones");
		
		// Categoria 4
		Categoria cat4 = new Categoria();
		cat4.setId(4);
		cat4.setNombre("Arquitectura");
		cat4.setDescripcion("Trabajos de Arquitectura");
		
		// Categoria 5
		Categoria cat5 = new Categoria();
		cat5.setId(5);
		cat5.setNombre("Educacion");
		cat5.setDescripcion("Maestros, tutores, etc");
		
		// Categoria 6
		Categoria cat6 = new Categoria();
		cat6.setId(6);
		cat6.setNombre("Desarrollo de software");
		cat6.setDescripcion("Trabajo para programadores");
		
		/**
		 * Agregamos los 5 objetos de tipo Categoria a la lista ...
		 */
		lista.add(cat1);			
		lista.add(cat2);
		lista.add(cat3);
		lista.add(cat4);
		lista.add(cat5);
		lista.add(cat6);

	}
	
	public void guardar(Categoria categoria) {	
		// mandamos a llamar nuestra lista para guardar 
		lista.add(categoria);
	}

	//Vamos a regresar la lista
	public List<Categoria> buscarTodas() {
		return lista;
	}

	//Va a recorrer la lista y comparara el id de cada objeto d tip categoria y cuando encuentre
		//un id que sea igual al que se pasa x parametro, regresara el objeto
	public Categoria buscarPorId(Integer idCategoria) {			
		for (Categoria cat : lista) {
			if (cat.getId()==idCategoria) {
				return cat;
			}
		}		
		return null;	
	}

}
