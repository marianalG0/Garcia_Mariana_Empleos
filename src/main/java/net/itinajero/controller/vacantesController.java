package net.itinajero.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.itinajero.model.vacante;
import net.itinajero.service.ICategoriasService;
import net.itinajero.service.IVacantesServices;
import net.itinajero.util.Utileria;

@Controller
@RequestMapping(value="/vacantes")
public class vacantesController {
	
	//Inyectando propiedad 
	//declaramos variable privada
	@Value("${empleosapp.ruta.imagenes}")//le damos como valor el nombre de la propiedad
	//@Value("empleosapp.ruta.imagenes")//
	private String ruta;
	
	//Inyectando nuestra clase de servicio en el controlador
	@Autowired
	private IVacantesServices serviceVacantes;
	
	@Autowired
	//@Qualifier("categoriaServicejpa") 
	private ICategoriasService serviceCategorias; 
	
	
	//Metodo del index
	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		
		//1. Obtener todas las vacantes (recuperarlas con la clase de servicio)
		   List<vacante> lista = serviceVacantes.buscarTodas();
		//2. Agregar al modelo el listado de vacantes
		   model.addAttribute("vacantes",lista);
		//3. Renderizar las vacantes en la vista (integrar el archivo template-empleos/listVacantes.html)
		//4. Agregar al menu una aopcion llamada vacantes configurando la url "vacantes/index"
		
		return "vacantes/listVacantes";
	}
	
	@GetMapping("/create")
	public String crear(vacante vacante, Model model) {//Pasamos un objeto tipo vacante para poder desplegar errores en la vista
		//model.addAttribute("categorias", serviceCategorias.buscarTodas() );//Ya tenemos en la vista este atributo qur nos regresa la lista de categorias
		return "vacantes/formVacante";
	}
	
	
	//Configurando el data binding 
	//Agregamos el BindingResult para verificar errores 
	@PostMapping("/save")
	public String guardar(vacante vacante, BindingResult result, RedirectAttributes attributes, 
			@RequestParam("archivoImagen") MultipartFile multiPart) {//declarando parametros para su uso
		if (result.hasErrors()) {//si sale verdaderp, es porque existe errores 
			for (ObjectError error: result.getAllErrors()){
				System.out.println("Ocurrio un error: "+ error.getDefaultMessage());
			}//	Aqui con el for desplegamos los errores en consola			
			return "vacantes/formVacante";
		}
		
		if (!multiPart.isEmpty()) {//Verificamos si no viene vacio el objeto multiPart 
			 
			//Si no viene vacio se ejecuta este codigo
			//string ruta = "/empleos/img-vacantes/"; //Linux/MAC
			//String ruta = "c:/empleos/img-vacantes/"; //Windows, directorio donde vamos a guardar el archivo
			String nombreImagen = Utileria.guardarArchivo(multiPart, ruta);//declaramos una variable y le asignamos el valor qu enos devuelva el metodo guardar archivo,
			if (nombreImagen != null) {//La imagen si se subio
				//procesamos la variable nombreImagen
				vacante.setImagen(nombreImagen);
				
			}
		}
		
		serviceVacantes.guardar(vacante);//Mandamos a llamar la instancia de nuestra clase de servicio y el metodo de guardar 
										// De esta forma cuando llegue el objeto vacante al controlador automaticamente se guarda en la lista
		attributes.addFlashAttribute("msg", "Registro Guardado");// usamos el metodo addFlashAttribute, proporcionando el nombre del atributo y su valor 	
		System.out.println("Vacante: " + vacante);	//mostramos nuestro objeto vacante 	
		return "redirect:/vacantes/index"; //De esta forma estamos realizando indirectamente una peticion http de tipo get a la url, por lo que
		                                   // En este metodo se realiza dos peticiones: 1) la original, 2) redirect
	}
	
	
	//Metodo para recibir los valores de cada uno de los input de nuestro formulario con requestParam
	
	/*
	@PostMapping("/save")
	public String guardar(@RequestParam("nombre") String nombre, @RequestParam("descripcion") String descripcion, 
			@RequestParam("estatus") String estatus, @RequestParam("fecha") String fecha, @RequestParam("destacado") int destacado, 
			@RequestParam("salario") double salario, @RequestParam("detalles") String detalles) {
		System.out.println("Nombre Vacante: " + nombre);
		System.out.println("Descripcion: " + descripcion);
		System.out.println("Estatus: " + estatus);
		System.out.println("Fecha Publicación: " + fecha);
		System.out.println("Destacado: " + destacado);
		System.out.println("Salario Ofrecido: " + salario);
		System.out.println("detalles: " + detalles);
		return "vacantes/listVacantes"; 
	}*/
	
	
	//Configurando el data binding 
	/*@PostMapping("/save")
	public String guardar(vacante vacante) {//toma parametros de el tipo de nuestro modelo 
		
		//Mandamos a llamar la instancia de nuestra clase de servicio y el metodo de guardar
		serviceVacantes.guardar(vacante);// De esta forma cuando llegue el objeto vacante al controlador automaticamente se guarda en la lista
		System.out.println("Vacante: " + vacante);//mostramos nuestro objeto vacante 
		return "vacantes/listVacantes"; 
	}*/
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable ("id") int idVacante, RedirectAttributes attributes) {
		System.out.println("Borrando vacante con id: " + idVacante);
		serviceVacantes.eliminar(idVacante);//Ya estamos eliminando en base de datos
		attributes.addFlashAttribute("msg","La vacante fue eliminada");
		//model.addAttribute("id",idVacante); //No agregamos id al modelo por eso se elimina
		
		return "redirect:/vacantes/index";
	}
	
	
	//Metodo que va a buscar por id el objeto y lo enviara al formulario
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable ("id") int idVacante, Model model ){
		vacante vacante = serviceVacantes.buscarPorId(idVacante);
		model.addAttribute("vacante", vacante);
		
		return "vacantes/formVacante";
	}
	
	@GetMapping("/view/{id}")
	public String verDetalle(@PathVariable("id") int idVacante,Model model){
		vacante vacante = serviceVacantes.buscarPorId(idVacante); 
		
		System.out.println("vacante: " + vacante);
		model.addAttribute("vacante",vacante);
		
		//Buscar los detalles de la vacante en la BD
		return "detalle";
		}//El metodo espera un tipo de dato numerico
	
	
	//Agregando datos genericos o comunes para los metodos de este controlador
	@ModelAttribute
	public void setGenericos(Model model) {
		model.addAttribute("categorias", serviceCategorias.buscarTodas() );
	}
	
	
	//Metodo publico
	@InitBinder //anotacion
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");/*Primero declaramos un objeto de tipo dompleDateformat que es una clase 
		de java estandar y basicamente es una clase en donde especificamos el tipo de formato que usara la fecha*/
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));/*De nuestro parametro mandamos a llamar el metodo
		registerCustom.. le pasamos 2 parametros(clase estandar de java, creando instancia de la clase y basicamente 
		le decimos que vamos a manejar la fecha utilizando la clase dateformat)*/
	}

}
