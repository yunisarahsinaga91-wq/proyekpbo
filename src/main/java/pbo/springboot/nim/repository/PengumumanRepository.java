package pbo.springboot.nim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pbo.springboot.nim.model.Pengumuman;

public interface PengumumanRepository
        extends JpaRepository<Pengumuman, Long> {}