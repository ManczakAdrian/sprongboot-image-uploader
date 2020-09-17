package pl.manczak.sprongbootimageuploader;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.manczak.sprongbootimageuploader.model.AppUser;
import pl.manczak.sprongbootimageuploader.repo.AppUserRepo;

@Configuration
public class WebSeciurityConfig extends WebSecurityConfigurerAdapter {


    private UserDetailsServiceImpl userDetailsService;
    private AppUserRepo appUserRepo;


   @Autowired
    public WebSeciurityConfig(UserDetailsServiceImpl userDetailsService, AppUserRepo appUserRepo) {
       this.userDetailsService = userDetailsService;
       this.appUserRepo=appUserRepo;
    }

    public WebSeciurityConfig(boolean disableDefaults, UserDetailsServiceImpl userDetailsService) {
        super(disableDefaults);
        this.userDetailsService = userDetailsService;
    }

        //   auth.inMemoryAuthentication().withUser(new User("Jan",passwordEncoder().encode("Jan123"), Collections.singleton(new SimpleGrantedAuthority("user"))));
    // to służy do pobierania użytkowników z pamięci a my musimy pobierać z bazy danych



       @Override
       protected void configure(AuthenticationManagerBuilder auth) throws Exception {
           auth.userDetailsService(userDetailsService);
       }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/test1").hasRole("USER")
                .antMatchers("/test2").hasRole("ADMIN")
                .and()
                .formLogin().permitAll();

    }
    @Bean
    public PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder(); }

    @EventListener(ApplicationReadyEvent.class)
       // ta adnotacja oznacza że ta metoda zostanie wywołana zawsze kiedy aplikacja wystartuje, będzie to pierwsza metoda która się wykona w aplikacji

       public void get(){
        // ta metoda służy do tego aby stworzyć użytkownika

        AppUser appUserUser=new AppUser("UserJan",passwordEncoder().encode("UserJan"),"ROLE_USER");
        AppUser appUserAdmin=new AppUser("AdminJan",passwordEncoder().encode("AdminJan"),"ROLE_ADMIN");

        // teraz użytkownika pchamy do bazy danych metodą save. metoda nic nie zwraca , zapisuje tylko użytkownika do bazy danych
        appUserRepo.save(appUserUser);
        appUserRepo.save(appUserAdmin);
    }
}

