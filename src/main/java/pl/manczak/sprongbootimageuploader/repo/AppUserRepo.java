package pl.manczak.sprongbootimageuploader.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.manczak.sprongbootimageuploader.model.AppUser;


@Repository
public interface AppUserRepo extends JpaRepository<AppUser, Long> {

    AppUser finByUsername(String username);

}
