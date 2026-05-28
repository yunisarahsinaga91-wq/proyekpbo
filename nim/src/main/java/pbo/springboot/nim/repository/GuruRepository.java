package pbo.springboot.nim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pbo.springboot.nim.model.Guru;

public interface GuruRepository
        extends JpaRepository<Guru, Long> {}