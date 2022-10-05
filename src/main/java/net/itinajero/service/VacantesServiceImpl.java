package net.itinajero.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import net.itinajero.model.vacante;

@Service //Declarando la clase como componente de servicio
//Esta clase de servicios nos va a regresar la lista de objetos de tip vacante
public class VacantesServiceImpl implements IVacantesServices{

	private List<vacante> lista = null;//Declarando la lista como un atributo a nivel de la clase
	
	//Declarando un constructor para crear la lista
	//Recuerda que el constructor solo se ejecuta una vez cada que se crea una instancia
	public VacantesServiceImpl() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		lista = new LinkedList<vacante>();
		
		try {
			
		//CREAMOS CUATRO OBJETOS DEL TIPO VACANTE
			//Creamos la oferta de trabajo 1
			vacante vacante1 = new vacante();
			vacante1.setId(1);
			vacante1.setNombre("Ingeniero Civil");
			vacante1.setDescripcion("Se solicita Ing. Civil para diseñar puente peatonal");
			vacante1.setFecha(sdf.parse("08-02-2019"));
			vacante1.setSalario(14000.0);
			vacante1.setDestacado(1);
			vacante1.setImagen("empresa1.png");
			
			//Creamos la oferta de trabajo 2
			vacante vacante2 = new vacante();
			vacante2.setId(2);
			vacante2.setNombre("Contador Publico");
			vacante2.setDescripcion("Empresa importante solicita contador con 5 años de experiencia titulado");
			vacante2.setFecha(sdf.parse("09-02-2019"));
			vacante2.setSalario(12000.0);
			vacante2.setDestacado(0);
			vacante2.setImagen("empresa2.png");
			
			//Creamos la oferta de trabajo 3
			vacante vacante3 = new vacante();
			vacante3.setId(3);
			vacante3.setNombre("Ingeniero Eléctrico");
			vacante3.setDescripcion("Empresa internacional solicita Ingeniero Mecánico para mantenimiento de la instalacion electrica");
			vacante3.setFecha(sdf.parse("10-02-2019"));
			vacante3.setSalario(10500.0);
			vacante3.setDestacado(0);
		
			//Creamos la oferta de trabajo 4
			vacante vacante4 = new vacante();
			vacante4.setId(4);
			vacante4.setNombre("Diseñador Gráfico");
			vacante4.setDescripcion("Solicitamos Diseñador Grafico titulado para diseñar publicidad de la empresa");
			vacante4.setFecha(sdf.parse("11-02-2019"));
			vacante4.setSalario(7500.0);
			vacante4.setDestacado(1);
			vacante4.setImagen("empresa3.png");
			
			//Agregamos los 4 objetos de tipo vacante a la lista...
			lista.add(vacante1);
			lista.add(vacante2);
			lista.add(vacante3);
			lista.add(vacante4);
			
			//controlando la excepcion
		}catch (ParseException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
	}
	
	//Vamos a regresar la lista
	
	@Override
	public List<vacante> buscarTodas() {
		// TODO Auto-generated method stub
		return lista;
	}

	//Va a recorrer la lista y comparara el id de cada objeto d tip vacante y cuando encuentre
	//un id que sea igual al que se pasa x parametro, regresara el objeto
	@Override
	public vacante buscarPorId(Integer idVacante) {
		for(vacante v: lista) {
			if(v.getId()==idVacante) {
				return v;
			}
		}
		return null;
	}

	@Override
	public void guardar(vacante vacante) {
		// mandamos a llamar nuestra lista para guardar 
		lista.add(vacante);
		
	}

}
