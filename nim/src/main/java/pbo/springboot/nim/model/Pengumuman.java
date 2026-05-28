package pbo.springboot.nim.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pengumuman")
public class Pengumuman {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String judul;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String isi;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getJudul() { return judul; }
    public void setJudul(String judul) { this.judul = judul; }
    public String getIsi() { return isi; }
    public void setIsi(String isi) { this.isi = isi; }
}