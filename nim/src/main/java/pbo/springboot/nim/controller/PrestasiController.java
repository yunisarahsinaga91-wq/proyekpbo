package pbo.springboot.nim.controller;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pbo.springboot.nim.model.Prestasi;
import pbo.springboot.nim.service.PrestasiService;

@Controller
@RequestMapping("/prestasi")
public class PrestasiController {
    private final PrestasiService prestasiService;

    public PrestasiController(PrestasiService prestasiService) {
        this.prestasiService = prestasiService;
    }

    private boolean isAdmin(HttpSession session) {
        Object role = session.getAttribute("role");
        return role != null && "ADMIN".equals(role.toString());
    }

    @GetMapping
    public String prestasi(HttpSession session, Model model) {
        Object role = session.getAttribute("role");
        model.addAttribute("listPrestasi", prestasiService.findAll());
        if ("ADMIN".equals(role)) return "admin/guruprestasi/prestasi";
        if ("SISWA".equals(role)) return "redirect:/siswa/prestasi";
        return "prestasi"; // guest
    }

    @GetMapping("/tambah")
    public String formTambah(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("prestasi", new Prestasi());
        return "admin/guruprestasi/edit-prestasi";
    }

    @PostMapping("/simpan")
    public String simpan(HttpSession session, @ModelAttribute Prestasi prestasi, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        try {
            prestasiService.save(prestasi);
            return "redirect:/prestasi";
        } catch (Exception e) {
            model.addAttribute("error", "Gagal menyimpan: " + e.getMessage());
            model.addAttribute("prestasi", prestasi);
            return "admin/guruprestasi/edit-prestasi";
        }
    }

    @GetMapping("/edit/{id}")
    public String formEdit(HttpSession session, @PathVariable Long id, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("prestasi", prestasiService.findById(id));
        return "admin/guruprestasi/edit-prestasi";
    }

    @PostMapping("/update/{id}")
    public String update(HttpSession session, @PathVariable Long id,
                         @ModelAttribute Prestasi prestasi, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        try {
            prestasi.setId(id);
            prestasiService.save(prestasi);
            return "redirect:/prestasi";
        } catch (Exception e) {
            model.addAttribute("error", "Gagal mengupdate: " + e.getMessage());
            model.addAttribute("prestasi", prestasi);
            return "admin/guruprestasi/edit-prestasi";
        }
    }

    @GetMapping("/hapus/{id}")
    public String hapus(HttpSession session, @PathVariable Long id) {
        if (!isAdmin(session)) return "redirect:/login";
        prestasiService.deleteById(id);
        return "redirect:/prestasi";
    }
}