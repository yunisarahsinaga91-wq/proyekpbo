package pbo.springboot.nim.service;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import pbo.springboot.nim.exception.ResourceNotFoundException;
import pbo.springboot.nim.model.Guru;
import pbo.springboot.nim.repository.GuruRepository;
import java.util.List;

@Service
public class GuruService {

    private final GuruRepository guruRepo;

    public GuruService(GuruRepository guruRepo) {
        this.guruRepo = guruRepo;
    }

    public List<Guru> findAll() {
        try {
            return guruRepo.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Gagal mengambil data guru: " + e.getMessage());
        }
    }

    public Guru findById(@NonNull Long id) {
        try {
            return guruRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Guru dengan id " + id + " tidak ditemukan"));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Gagal mengambil guru: " + e.getMessage());
        }
    }

    public void save(@NonNull Guru guru) {
        try {
            guruRepo.save(guru);
        } catch (Exception e) {
            throw new RuntimeException("Gagal menyimpan guru: " + e.getMessage());
        }
    }

    public void deleteById(@NonNull Long id) {
        try {
            if (!guruRepo.existsById(id)) {
                throw new ResourceNotFoundException(
                        "Guru dengan id " + id + " tidak ditemukan");
            }
            guruRepo.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Gagal menghapus guru: " + e.getMessage());
        }
    }
}
