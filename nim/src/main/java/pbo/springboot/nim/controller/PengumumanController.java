package pbo.springboot.nim.controller;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pbo.springboot.nim.model.Pengumuman;
import pbo.springboot.nim.service.PengumumanService;

@Controller
@RequestMapping("/pengumuman")
public class PengumumanController {
    private final PengumumanService pengumumanService;

    public PengumumanController(PengumumanService pengumumanService) {
        this.pengumumanService = pengumumanService;
    }

    private boolean isAdmin(HttpSession session) {
        Object role = session.getAttribute("role");
        return role != null && "ADMIN".equals(role.toString());
    }

    @GetMapping
    public String pengumuman(HttpSession session, Model model) {
        Object role = session.getAttribute("role");
        if (role == null) return "redirect:/login";
        if ("SISWA".equals(role)) return "redirect:/siswa/pengumuman";
        model.addAttribute("listPengumuman", pengumumanService.findAll());
        return "admin/pengumuman/pengumuman";
    }

    @GetMapping("/tambah")
    public String formTambah(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("pengumuman", new Pengumuman());
        return "admin/pengumuman/edit-pengumuman";
    }

    @PostMapping("/simpan")
    public String simpan(HttpSession session, @ModelAttribute Pengumuman pengumuman, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        try {
            pengumumanService.save(pengumuman);
            return "redirect:/pengumuman";
        } catch (Exception e) {
            model.addAttribute("error", "Gagal menyimpan: " + e.getMessage());
            model.addAttribute("pengumuman", pengumuman);
            return "admin/pengumuman/edit-pengumuman";
        }
    }

    @GetMapping("/edit/{id}")
    public String formEdit(HttpSession session, @PathVariable Long id, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("pengumuman", pengumumanService.findById(id));
        return "admin/pengumuman/edit-pengumuman";
    }

    @PostMapping("/update/{id}")
    public String update(HttpSession session, @PathVariable Long id,
                         @ModelAttribute Pengumuman pengumuman, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        try {
            pengumuman.setId(id);
            pengumumanService.save(pengumuman);
            return "redirect:/pengumuman";
        } catch (Exception e) {
            model.addAttribute("error", "Gagal mengupdate: " + e.getMessage());
            model.addAttribute("pengumuman", pengumuman);
            return "admin/pengumuman/edit-pengumuman";
        }
    }

    @GetMapping("/hapus/{id}")
    public String hapus(HttpSession session, @PathVariable Long id) {
        if (!isAdmin(session)) return "redirect:/login";
        pengumumanService.deleteById(id);
        return "redirect:/pengumuman";
    }
}