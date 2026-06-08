package pbo.springboot.nim.service;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import pbo.springboot.nim.exception.ResourceNotFoundException;
import pbo.springboot.nim.model.Prestasi;
import pbo.springboot.nim.repository.PrestasiRepository;
import java.util.List;

@Service
public class PrestasiService {

    private final PrestasiRepository prestasiRepo;

    public PrestasiService(PrestasiRepository prestasiRepo) {
        this.prestasiRepo = prestasiRepo;
    }

    public List<Prestasi> findAll() {
        try {
            return prestasiRepo.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Gagal mengambil data prestasi: " + e.getMessage());
        }
    }

    public Prestasi findById(@NonNull Long id) {
        try {
            return prestasiRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Prestasi dengan id " + id + " tidak ditemukan"));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Gagal mengambil prestasi: " + e.getMessage());
        }
    }

    public void save(@NonNull Prestasi prestasi) {
        try {
            prestasiRepo.save(prestasi);
        } catch (Exception e) {
            throw new RuntimeException("Gagal menyimpan prestasi: " + e.getMessage());
        }
    }

    public void deleteById(@NonNull Long id) {
        try {
            if (!prestasiRepo.existsById(id)) {
                throw new ResourceNotFoundException(
                        "Prestasi dengan id " + id + " tidak ditemukan");
            }
            prestasiRepo.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Gagal menghapus prestasi: " + e.getMessage());
        }
    }
}
