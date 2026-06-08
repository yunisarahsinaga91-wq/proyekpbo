package pbo.springboot.nim.service;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import pbo.springboot.nim.exception.ResourceNotFoundException;
import pbo.springboot.nim.model.ProfilSekolah;
import pbo.springboot.nim.repository.ProfilRepository;

import java.util.List;

@Service
public class ProfilService {

    private final ProfilRepository profilRepo;

    public ProfilService(ProfilRepository profilRepo) {
        this.profilRepo = profilRepo;
    }

    public List<ProfilSekolah> findAll() {
        try {
            return profilRepo.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Gagal mengambil data profil: " + e.getMessage());
        }
    }

    public ProfilSekolah getOrCreate() {
        try {
            List<ProfilSekolah> list = profilRepo.findAll();
            if (list.isEmpty()) return new ProfilSekolah();
            return list.get(0);
        } catch (Exception e) {
            throw new RuntimeException("Gagal mengambil profil sekolah: " + e.getMessage());
        }
    }

    public ProfilSekolah findById(@NonNull Long id) {
        try {
            return profilRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Profil dengan id " + id + " tidak ditemukan"));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Gagal mengambil profil: " + e.getMessage());
        }
    }

    public void saveOrUpdate(@NonNull ProfilSekolah profil) {
        try {
            List<ProfilSekolah> existing = profilRepo.findAll();
            if (!existing.isEmpty()) {
                profil.setId(existing.get(0).getId());
            }
            profilRepo.save(profil);
        } catch (Exception e) {
            throw new RuntimeException("Gagal menyimpan profil: " + e.getMessage());
        }
    }

    public void save(@NonNull ProfilSekolah profil) {
        try {
            profilRepo.save(profil);
        } catch (Exception e) {
            throw new RuntimeException("Gagal menyimpan profil: " + e.getMessage());
        }
    }

    public void deleteById(@NonNull Long id) {
        try {
            if (!profilRepo.existsById(id)) {
                throw new ResourceNotFoundException(
                        "Profil dengan id " + id + " tidak ditemukan");
            }
            profilRepo.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Gagal menghapus profil: " + e.getMessage());
        }
    }
}