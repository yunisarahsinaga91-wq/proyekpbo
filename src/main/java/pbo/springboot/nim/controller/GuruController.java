package pbo.springboot.nim.controller;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pbo.springboot.nim.model.Guru;
import pbo.springboot.nim.service.GuruService;

@Controller
@RequestMapping("/guru")
public class GuruController {
    private final GuruService guruService;

    public GuruController(GuruService guruService) {
        this.guruService = guruService;
    }

    private boolean isAdmin(HttpSession session) {
        Object role = session.getAttribute("role");
        return role != null && "ADMIN".equals(role.toString());
    }

    @GetMapping
    public String guru(HttpSession session, Model model) {
        Object role = session.getAttribute("role");
        model.addAttribute("listGuru", guruService.findAll());
        if ("ADMIN".equals(role)) return "admin/guruprestasi/guru";
        if ("SISWA".equals(role)) return "redirect:/siswa/data-guru";
        return "guru"; // guest
    }

    @GetMapping("/tambah")
    public String formTambah(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("guru", new Guru());
        return "admin/guruprestasi/edit-guru";
    }

    @PostMapping("/simpan")
    public String simpan(HttpSession session, @ModelAttribute Guru guru, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        try {
            guruService.save(guru);
            return "redirect:/guru";
        } catch (Exception e) {
            model.addAttribute("error", "Gagal menyimpan: " + e.getMessage());
            model.addAttribute("guru", guru);
            return "admin/guruprestasi/edit-guru";
        }
    }

    @GetMapping("/edit/{id}")
    public String formEdit(HttpSession session, @PathVariable Long id, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("guru", guruService.findById(id));
        return "admin/guruprestasi/edit-guru";
    }

    @PostMapping("/update/{id}")
    public String update(HttpSession session, @PathVariable Long id,
                         @ModelAttribute Guru guru, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        try {
            guru.setId(id);
            guruService.save(guru);
            return "redirect:/guru";
        } catch (Exception e) {
            model.addAttribute("error", "Gagal mengupdate: " + e.getMessage());
            model.addAttribute("guru", guru);
            return "admin/guruprestasi/edit-guru";
        }
    }

    @GetMapping("/hapus/{id}")
    public String hapus(HttpSession session, @PathVariable Long id) {
        if (!isAdmin(session)) return "redirect:/login";
        guruService.deleteById(id);
        return "redirect:/guru";
    }
}