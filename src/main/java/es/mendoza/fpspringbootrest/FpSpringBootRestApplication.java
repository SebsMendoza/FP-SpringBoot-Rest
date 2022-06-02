package es.mendoza.fpspringbootrest;

import es.mendoza.fpspringbootrest.service.uploads.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FpSpringBootRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(FpSpringBootRestApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(StorageService storageService) {
        return args -> {
            // Inicializamos el servicio de ficheros
            storageService.deleteAll(); // Borramos todos (esto deberíamos quitarlo)
            storageService.init(); // inicializamos
        };
    }
}
