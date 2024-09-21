package udemy.contactos.controlador;

import org.springframework.web.bind.annotation.*;
import udemy.contactos.modelo.Contacto;
import udemy.contactos.servicio.ContactoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import java.util.List;

@Controller
public class ContactoControlador {

    private static final Logger logger = LoggerFactory.getLogger(ContactoControlador.class);

    @Autowired
    ContactoServicio contactoServicio;

    //@GetMapping("/"): Mapea una solicitud HTTP GET para la ruta raíz (/).
    //El ModelMap es un objeto que permite pasar datos desde el controlador a la vista.
    @GetMapping("/")
    public String iniciar(ModelMap modelo) {
        List<Contacto> contactos = contactoServicio.listarContactos();
        contactos.forEach((contacto) -> logger.info(contacto.toString()));
        modelo.put("contactos", contactos);
        return "index"; //index.html
    }

    //@GetMapping("/agregar"): Mapea una solicitud GET para la URL /agregar, simplemente devuelve la vista agregar.html
    @GetMapping("/agregar")
    public String mostrarAgregar() {
        return "/agregar";
    }

    /* @PostMapping("/agregar"): Mapea una solicitud HTTP POST a /agregar. Este metodo es invocado cuando el formulario
        de agregar contacto es enviado. */
    @PostMapping("/agregar")
    /*@ModelAttribute("formContacto"): Se usa para vincular los datos del formulario a un objeto de tipo Contacto.
        Thymeleaf debe está configurado para enviar los datos del formulario con este nombre (formContacto).*/
    public String agregarContacto(@ModelAttribute("formContacto") Contacto contacto) {
        logger.info("Contacto a agregar: {}", contacto);
        contactoServicio.guardarContacto(contacto);
        return "redirect:/";
    }

    /* @GetMapping("/editar/{id}"): Mapea una solicitud GET con un parámetro dinámico {id} para la URL /editar.
        Esto permite que la solicitud incluya el ID del contacto a editar. */
    @GetMapping("/editar/{id}")
    //@PathVariable(value = "id"): Extrae el valor del ID de la URL y lo pasa como parámetro al metodo.
    public String mostrarEditar(@PathVariable(value = "id") int idContacto, ModelMap modelo) {
        Contacto contacto = contactoServicio.buscarContactoPorId(idContacto);
        logger.info("Contacto a editar: {}", contacto);
        modelo.put("contacto", contacto);
        return "editar";
    }

    /* @PostMapping("/editar"): Mapea una solicitud POST para /editar. Este metodo es invocado cuando el formulario
        de edición es enviado */
    @PostMapping("/editar")
    public String editar(@ModelAttribute("contacto") Contacto contacto) {
        logger.info("Contacto a modificar: {}", contacto);
        contactoServicio.guardarContacto(contacto);
        return "redirect:/";
    }

    /*@GetMapping("/eliminar/{id}"): Mapea una solicitud GET con un parámetro dinámico {id} para la URL /eliminar.
        Esto permite eliminar un contacto por su ID.*/
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") int idContacto) {
        Contacto contacto = contactoServicio.buscarContactoPorId(idContacto);
        logger.info("Contacto a eliminar: " + contacto);
        contactoServicio.eliminarContacto(contacto);
        return "redirect:/";
    }

    //Por lo general usamos GET para lectura y POST para escritura, a excepción del eliminar en este caso, que aunque
    // es recomendable que usemos el metodo POST, podemos usar get.
}
