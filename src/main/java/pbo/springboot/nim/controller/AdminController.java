package pbo.springboot.nim.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pbo.springboot.nim.repository.UserRepository;
import pbo.springboot.nim.service.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PengumumanService pengumumanService;
    private final GaleriService galeriService;
    private final GuruService guruService;
    private final PrestasiService prestasiService;
    private final ProfilService profilService;          // ✅ untuk totalProfil
    private final UserRepository userRepository;        // ✅ untuk totalUser

    public AdminController(PengumumanService pengumumanService,
                           GaleriService galeriService,
                           GuruService guruService,
                           PrestasiService prestasiService,
                           ProfilService profilService,
                           UserRepository userRepository) {
        this.pengumumanService = pengumumanService;
        this.galeriService = galeriService;
        this.guruService = guruService;
        this.prestasiService = prestasiService;
        this.profilService = profilService;
        this.userRepository = userRepository;
    }

    private boolean isAdmin(HttpSession session) {
        Object role = session.getAttribute("role");
        return role != null && "ADMIN".equals(role.toString());
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        try {
            model.addAttribute("username", session.getAttribute("username"));

            model.addAttribute("totalProfil", profilService.findAll().size());
            model.addAttribute("totalPengumuman", pengumumanService.findAll().size());
            model.addAttribute("totalGaleri", galeriService.findAll().size());
            model.addAttribute("totalGuru", guruService.findAll().size());
            model.addAttribute("totalPrestasi", prestasiService.findAll().size());
            model.addAttribute("totalUser", userRepository.count());

            return "admin/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", "Gagal memuat dashboard");
            model.addAttribute("totalProfil", 0);
            model.addAttribute("totalPengumuman", 0);
            model.addAttribute("totalGaleri", 0);
            model.addAttribute("totalGuru", 0);
            model.addAttribute("totalPrestasi", 0);
            model.addAttribute("totalUser", 0);
            return "admin/dashboard";
        }
    }
}