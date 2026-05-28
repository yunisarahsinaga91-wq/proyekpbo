package pbo.springboot.nim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pbo.springboot.nim.model.Prestasi;

public interface PrestasiRepository
        extends JpaRepository<Prestasi, Long> {}