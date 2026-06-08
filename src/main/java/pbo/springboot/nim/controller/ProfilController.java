package pbo.springboot.nim.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pbo.springboot.nim.model.ProfilSekolah;
import pbo.springboot.nim.service.ProfilService;

@Controller
@RequestMapping("/profil")
public class ProfilController {

    private final ProfilService profilService;

    public ProfilController(ProfilService profilService) {
        this.profilService = profilService;
    }

    private boolean isAdmin(HttpSession session) {
        Object role = session.getAttribute("role");
        return role != null && "ADMIN".equals(role.toString());
    }

    @GetMapping
    public String profil(HttpSession session, Model model) {
        Object role = session.getAttribute("role");
        model.addAttribute("listProfil", profilService.findAll());
        if ("ADMIN".equals(role)) return "admin/profil/profil";
        if ("SISWA".equals(role))  return "redirect:/siswa/profil-sekolah";
        return "profil";
    }

    /**
     * Admin tidak bisa "tambah" jika sudah ada profil → arahkan ke edit.
     * Jika belum ada, baru boleh form tambah.
     */
    @GetMapping("/tambah")
    public String formTambah(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";

        // Singleton: jika sudah ada profil, redirect ke edit
        ProfilSekolah existing = profilService.getOrCreate();
        if (existing.getId() != null) {
            return "redirect:/profil/edit/" + existing.getId();
        }

        model.addAttribute("profil", new ProfilSekolah());
        return "admin/profil/edit-profil";
    }

    @PostMapping("/simpan")
    public String simpan(HttpSession session,
                         @ModelAttribute ProfilSekolah profil,
                         Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        try {
            profilService.saveOrUpdate(profil); // singleton save
            return "redirect:/profil";
        } catch (Exception e) {
            model.addAttribute("error", "Gagal menyimpan: " + e.getMessage());
            model.addAttribute("profil", profil);
            return "admin/profil/edit-profil";
        }
    }

    @GetMapping("/edit/{id}")
    public String formEdit(HttpSession session,
                           @PathVariable Long id,
                           Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("profil", profilService.findById(id));
        return "admin/profil/edit-profil";
    }

    @PostMapping("/update/{id}")
    public String update(HttpSession session,
                         @PathVariable Long id,
                         @ModelAttribute ProfilSekolah profil,
                         Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        try {
            profil.setId(id);
            profilService.save(profil);
            return "redirect:/profil";
        } catch (Exception e) {
            model.addAttribute("error", "Gagal mengupdate: " + e.getMessage());
            model.addAttribute("profil", profil);
            return "admin/profil/edit-profil";
        }
    }

    @GetMapping("/hapus/{id}")
    public String hapus(HttpSession session, @PathVariable Long id) {
        if (!isAdmin(session)) return "redirect:/login";
        profilService.deleteById(id);
        return "redirect:/profil";
    }
}