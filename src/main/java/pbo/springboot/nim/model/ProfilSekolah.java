package pbo.springboot.nim.model;

import jakarta.persistence.*;

@Entity
@Table(name = "profil_sekolah")
public class ProfilSekolah {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String namaSekolah;

    @Column(columnDefinition = "TEXT")
    private String sejarah;

    @Column(columnDefinition = "TEXT")
    private String visiMisi;

    @Column(columnDefinition = "TEXT")
    private String fasilitas;

    private String alamat;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNamaSekolah() { return namaSekolah; }
    public void setNamaSekolah(String v) { this.namaSekolah = v; }
    public String getSejarah() { return sejarah; }
    public void setSejarah(String v) { this.sejarah = v; }
    public String getVisiMisi() { return visiMisi; }
    public void setVisiMisi(String v) { this.visiMisi = v; }
    public String getFasilitas() { return fasilitas; }
    public void setFasilitas(String v) { this.fasilitas = v; }
    public String getAlamat() { return alamat; }
    public void setAlamat(String v) { this.alamat = v; }
}