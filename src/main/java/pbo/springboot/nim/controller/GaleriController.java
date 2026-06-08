package pbo.springboot.nim.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pbo.springboot.nim.model.Galeri;
import pbo.springboot.nim.service.GaleriService;

import java.io.File;
import java.util.UUID;

@Controller
@RequestMapping("/galeri")
public class GaleriController {

    private final GaleriService galeriService;

    public GaleriController(GaleriService galeriService) {
        this.galeriService = galeriService;
    }

    private boolean isAdmin(HttpSession session) {
        Object role = session.getAttribute("role");
        return role != null && "ADMIN".equals(role.toString());
    }

    private String uploadDir() {
        return System.getProperty("user.dir") + "/src/main/resources/static/images/";
    }

    @GetMapping
    public String galeri(HttpSession session, Model model) {
        Object role = session.getAttribute("role");
        model.addAttribute("listGaleri", galeriService.findAll());

        if ("ADMIN".equals(role)) return "admin/galeri/galeri";
        if ("SISWA".equals(role)) return "redirect:/siswa/galeri";
        return "galeri";
    }

    @GetMapping("/tambah")
    public String formTambah(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("galeri", new Galeri());
        return "admin/galeri/edit-galeri";
    }

    @PostMapping("/simpan")
    public String simpan(HttpSession session,
                         @RequestParam("judul") String judul,
                         @RequestParam("file") MultipartFile file,
                         Model model) {

        if (!isAdmin(session)) return "redirect:/login";

        try {
            if (file.isEmpty()) {
                model.addAttribute("error", "File gambar belum dipilih.");
                model.addAttribute("galeri", new Galeri());
                return "admin/galeri/edit-galeri";
            }

            File folder = new File(uploadDir());
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String namaFile = UUID.randomUUID() + "_" + file.getOriginalFilename();
            file.transferTo(new File(uploadDir() + namaFile));

            Galeri galeri = new Galeri();
            galeri.setJudul(judul);
            galeri.setGambar(namaFile);

            galeriService.save(galeri);

            return "redirect:/galeri";
        } catch (Exception e) {
            model.addAttribute("error", "Gagal menyimpan galeri: " + e.getMessage());
            model.addAttribute("galeri", new Galeri());
            return "admin/galeri/edit-galeri";
        }
    }

    @GetMapping("/edit/{id}")
    public String formEdit(HttpSession session, @PathVariable Long id, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("galeri", galeriService.findById(id));
        return "admin/galeri/edit-galeri";
    }

    @PostMapping("/update/{id}")
    public String update(HttpSession session,
                         @PathVariable Long id,
                         @RequestParam("judul") String judul,
                         @RequestParam(value = "file", required = false) MultipartFile file,
                         Model model) {

        if (!isAdmin(session)) return "redirect:/login";

        try {
            Galeri galeri = galeriService.findById(id);
            if (galeri == null) return "redirect:/galeri";

            galeri.setJudul(judul);

            if (file != null && !file.isEmpty()) {
                File folder = new File(uploadDir());
                if (!folder.exists()) {
                    folder.mkdirs();
                }

                String namaFile = UUID.randomUUID() + "_" + file.getOriginalFilename();
                file.transferTo(new File(uploadDir() + namaFile));
                galeri.setGambar(namaFile);
            }

            galeriService.save(galeri);
            return "redirect:/galeri";
        } catch (Exception e) {
            model.addAttribute("error", "Gagal mengupdate galeri: " + e.getMessage());
            model.addAttribute("galeri", galeriService.findById(id));
            return "admin/galeri/edit-galeri";
        }
    }

    @GetMapping("/hapus/{id}")
    public String hapus(HttpSession session, @PathVariable Long id) {
        if (!isAdmin(session)) return "redirect:/login";
        galeriService.deleteById(id);
        return "redirect:/galeri";
    }
}