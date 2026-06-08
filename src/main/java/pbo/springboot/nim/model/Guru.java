package pbo.springboot.nim.model;

import jakarta.persistence.*;

@Entity
@Table(name = "guru")
public class Guru {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nama;

    @Column(nullable = false)
    private String mataPelajaran;

    private String foto;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getMataPelajaran() { return mataPelajaran; }
    public void setMataPelajaran(String v) { this.mataPelajaran = v; }
    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }
}