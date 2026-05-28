package pbo.springboot.nim.controller;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pbo.springboot.nim.model.Galeri;
import pbo.springboot.nim.service.GaleriService;

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

    @GetMapping
    public String galeri(HttpSession session, Model model) {
        Object role = session.getAttribute("role");
        model.addAttribute("listGaleri", galeriService.findAll());
        if ("ADMIN".equals(role)) return "admin/galeri/galeri";
        if ("SISWA".equals(role)) return "redirect:/siswa/galeri";
        return "galeri"; // guest
    }

    @GetMapping("/tambah")
    public String formTambah(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("galeri", new Galeri());
        return "admin/galeri/edit-galeri";
    }

    @PostMapping("/simpan")
    public String simpan(HttpSession session, @ModelAttribute Galeri galeri, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        try {
            galeriService.save(galeri);
            return "redirect:/galeri";
        } catch (Exception e) {
            model.addAttribute("error", "Gagal menyimpan: " + e.getMessage());
            model.addAttribute("galeri", galeri);
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
    public String update(HttpSession session, @PathVariable Long id,
                         @ModelAttribute Galeri galeri, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        try {
            galeri.setId(id);
            galeriService.save(galeri);
            return "redirect:/galeri";
        } catch (Exception e) {
            model.addAttribute("error", "Gagal mengupdate: " + e.getMessage());
            model.addAttribute("galeri", galeri);
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