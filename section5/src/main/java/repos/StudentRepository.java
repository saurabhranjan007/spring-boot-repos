package repos;

import entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

// after student here we specify the type of the ID from JPA entity that we're to use.
public interface StudentRepository extends JpaRepository<Student, Long> {

}


//  Note: now that we have defined the interface using Spring JPA.
//        So now we will be able to use the CRUD operations w/o actually defining it,
//        and that is because of Spring JPA.