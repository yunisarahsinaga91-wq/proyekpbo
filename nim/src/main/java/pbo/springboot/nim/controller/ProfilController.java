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

    // cek admin
    private boolean isAdmin(HttpSession session) {

        Object role = session.getAttribute("role");

        return role != null &&
               role.toString().equals("ADMIN");
    }

    // tampil profil
    @GetMapping
    public String profil(HttpSession session,
                         Model model) {

        model.addAttribute(
                "listProfil",
                profilService.findAll()
        );

        Object role = session.getAttribute("role");

        if (role != null &&
            role.toString().equals("ADMIN")) {

            return "admin/profil/profil";
        }

        if (role != null &&
            role.toString().equals("SISWA")) {

            return "siswa/profil-sekolah";
        }

        return "profil";
    }

    // form tambah
    @GetMapping("/tambah")
    public String formTambah(HttpSession session,
                             Model model) {

        if (!isAdmin(session)) {

            return "redirect:/login";
        }

        model.addAttribute(
                "profil",
                new ProfilSekolah()
        );

        return "admin/profil/edit-profil";
    }

    // simpan
    @PostMapping("/simpan")
    public String simpan(HttpSession session,
                         @ModelAttribute ProfilSekolah profil) {

        if (!isAdmin(session)) {

            return "redirect:/login";
        }

        profilService.save(profil);

        return "redirect:/profil";
    }

    // form edit
    @GetMapping("/edit/{id}")
    public String formEdit(HttpSession session,
                           @PathVariable Long id,
                           Model model) {

        if (!isAdmin(session)) {

            return "redirect:/login";
        }

        model.addAttribute(
                "profil",
                profilService.findById(id)
        );

        return "admin/profil/edit-profil";
    }

    // update
    @PostMapping("/update/{id}")
    public String update(HttpSession session,
                         @PathVariable Long id,
                         @ModelAttribute ProfilSekolah profil) {

        if (!isAdmin(session)) {

            return "redirect:/login";
        }

        profil.setId(id);

        profilService.save(profil);

        return "redirect:/profil";
    }

    // hapus
    @GetMapping("/hapus/{id}")
    public String hapus(HttpSession session,
                        @PathVariable Long id) {

        if (!isAdmin(session)) {

            return "redirect:/login";
        }

        profilService.deleteById(id);

        return "redirect:/profil";
    }
}