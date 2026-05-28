package pbo.springboot.nim.service;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import pbo.springboot.nim.exception.ResourceNotFoundException;
import pbo.springboot.nim.model.Pengumuman;
import pbo.springboot.nim.repository.PengumumanRepository;
import java.util.List;

@Service
public class PengumumanService {

    private final PengumumanRepository pengumumanRepo;

    public PengumumanService(PengumumanRepository pengumumanRepo) {
        this.pengumumanRepo = pengumumanRepo;
    }

    public List<Pengumuman> findAll() {
        try {
            return pengumumanRepo.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Gagal mengambil data pengumuman: " + e.getMessage());
        }
    }

    public Pengumuman findById(@NonNull Long id) {
        try {
            return pengumumanRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Pengumuman dengan id " + id + " tidak ditemukan"));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Gagal mengambil pengumuman: " + e.getMessage());
        }
    }

    public void save(@NonNull Pengumuman pengumuman) {
        try {
            pengumumanRepo.save(pengumuman);
        } catch (Exception e) {
            throw new RuntimeException("Gagal menyimpan pengumuman: " + e.getMessage());
        }
    }

    public void deleteById(@NonNull Long id) {
        try {
            if (!pengumumanRepo.existsById(id)) {
                throw new ResourceNotFoundException(
                        "Pengumuman dengan id " + id + " tidak ditemukan");
            }
            pengumumanRepo.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Gagal menghapus pengumuman: " + e.getMessage());
        }
    }
}
