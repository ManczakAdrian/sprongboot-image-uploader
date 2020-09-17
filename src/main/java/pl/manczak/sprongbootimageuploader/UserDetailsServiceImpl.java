package pl.manczak.sprongbootimageuploader;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.manczak.sprongbootimageuploader.repo.AppUserRepo;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private  AppUserRepo appUserRepo;


   @Autowired
    public UserDetailsServiceImpl(AppUserRepo appUserRepo) {
        this.appUserRepo = appUserRepo;

    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return appUserRepo.finByUsername(s);


        //UserDetailServiceImpl ma zwracać użytkownika na podstawie loginu. tą informację czerpiemy z repozytorium
        //klasa ta jest servisem , odpowiada za wyciąganie użytkowników z bazy


    }

}
