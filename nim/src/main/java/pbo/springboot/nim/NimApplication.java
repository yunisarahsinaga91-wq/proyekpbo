package pbo.springboot.nim;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pbo.springboot.nim.model.User;
import pbo.springboot.nim.repository.UserRepository;

@SpringBootApplication
public class NimApplication {

    public static void main(String[] args) {
        SpringApplication.run(NimApplication.class, args);
    }

    @Bean
    CommandLineRunner initAdmin(UserRepository userRepository) {
        return args -> {
            // Buat akun admin
            if (userRepository.findByUsername("admin") == null) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword("admin123");
                admin.setRole("ADMIN");
                userRepository.save(admin);
                System.out.println(">> Akun ADMIN berhasil dibuat (username: admin, password: admin123)");
            }
            
            // Buat akun siswa default
            if (userRepository.findByUsername("siswa") == null) {
                User siswa = new User();
                siswa.setUsername("siswa");
                siswa.setPassword("siswa123");
                siswa.setRole("SISWA");
                userRepository.save(siswa);
                System.out.println(">> Akun SISWA berhasil dibuat (username: siswa, password: siswa123)");
            }
        };
    }
}