package pbo.springboot.nim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pbo.springboot.nim.model.User;
import pbo.springboot.nim.repository.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) {

        if (userRepository.findByUsername("admin") == null) {

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin123");

            admin.setRole("ADMIN");

            userRepository.save(admin);

            System.out.println("User admin berhasil dibuat");

        } else {
            System.out.println("User admin sudah ada");
        }
    }
}