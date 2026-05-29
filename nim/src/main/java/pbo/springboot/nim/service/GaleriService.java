package pbo.springboot.nim.service;

import org.springframework.stereotype.Service;
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
        return galeriRepo.findAll();
    }

    public Galeri findById(Long id) {
        return galeriRepo.findById(id).orElse(null);
    }

    public void save(Galeri galeri) {
        galeriRepo.save(galeri);
    }

    public void deleteById(Long id) {
        galeriRepo.deleteById(id);
    }
}