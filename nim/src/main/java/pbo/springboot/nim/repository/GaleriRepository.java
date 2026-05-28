package pbo.springboot.nim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pbo.springboot.nim.model.Galeri;

public interface GaleriRepository
        extends JpaRepository<Galeri, Long> {}