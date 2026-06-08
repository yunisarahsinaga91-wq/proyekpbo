package pbo.springboot.nim.service;

import org.springframework.stereotype.Service;
import pbo.springboot.nim.exception.ResourceNotFoundException;
import pbo.springboot.nim.model.User;
import pbo.springboot.nim.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Login - cek username & password
    public User login(String username, String password) {
        try {
            return userRepository.findByUsernameAndPassword(username, password);
        } catch (Exception e) {
            throw new RuntimeException("Gagal proses login: " + e.getMessage());
        }
    }

    // Cek username sudah ada atau belum
    public boolean isUsernameExists(String username) {
        try {
            return userRepository.findByUsername(username) != null;
        } catch (Exception e) {
            throw new RuntimeException("Gagal cek username: " + e.getMessage());
        }
    }

    // Register user baru
    public void register(User user) {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Gagal menyimpan user: " + e.getMessage());
        }
    }
}
