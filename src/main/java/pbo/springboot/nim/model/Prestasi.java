package pbo.springboot.nim.model;

import jakarta.persistence.*;

@Entity
@Table(name = "prestasi")
public class Prestasi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String judul;

    @Column(columnDefinition = "TEXT")
    private String deskripsi;

    private String tahun;

    // nilai: "akademik" atau "non-akademik"
    private String kategori;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getJudul() { return judul; }
    public void setJudul(String judul) { this.judul = judul; }
    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String v) { this.deskripsi = v; }
    public String getTahun() { return tahun; }
    public void setTahun(String tahun) { this.tahun = tahun; }
    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }
}