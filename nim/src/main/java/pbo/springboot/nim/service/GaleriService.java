package pbo.springboot.nim.service;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import pbo.springboot.nim.exception.ResourceNotFoundException;
import pbo.springboot.nim.model.Galeri;
import pbo.springboot.nim.repository.GaleriRepository;
import java.util.List;

@Service
public class GaleriService {

    private final GaleriRepository galeriRepo;

    public GaleriService(GaleriRepository galeriRepo) {
        this.galeriRepo = galeriRepo;
    }

    public List<Galeri> findAll() {
        try {
            return galeriRepo.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Gagal mengambil data galeri: " + e.getMessage());
        }
    }

    public Galeri findById(@NonNull Long id) {
        try {
            return galeriRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Galeri dengan id " + id + " tidak ditemukan"));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Gagal mengambil galeri: " + e.getMessage());
        }
    }

    public void save(@NonNull Galeri galeri) {
        try {
            galeriRepo.save(galeri);
        } catch (Exception e) {
            throw new RuntimeException("Gagal menyimpan galeri: " + e.getMessage());
        }
    }

    public void deleteById(@NonNull Long id) {
        try {
            if (!galeriRepo.existsById(id)) {
                throw new ResourceNotFoundException(
                        "Galeri dengan id " + id + " tidak ditemukan");
            }
            galeriRepo.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Gagal menghapus galeri: " + e.getMessage());
        }
    }
}
