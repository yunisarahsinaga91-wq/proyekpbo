package pbo.springboot.nim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pbo.springboot.nim.model.ProfilSekolah;

public interface ProfilRepository
        extends JpaRepository<ProfilSekolah, Long> {}