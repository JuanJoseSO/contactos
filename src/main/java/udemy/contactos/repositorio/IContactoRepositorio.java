package udemy.contactos.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import udemy.contactos.modelo.Contacto;

@Repository
public interface IContactoRepositorio extends JpaRepository<Contacto,Integer> {}