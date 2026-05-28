package pbo.springboot.nim.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pbo.springboot.nim.service.PengumumanService;
import pbo.springboot.nim.service.GaleriService;

@Controller
public class HomeController {

    private final PengumumanService pengumumanService;
    private final GaleriService galeriService;

    public HomeController(PengumumanService pengumumanService,
                          GaleriService galeriService) {
        this.pengumumanService = pengumumanService;
        this.galeriService = galeriService;
    }

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        // Jika user sudah login, redirect ke dashboard
        if (session.getAttribute("userLogin") != null) {
            if ("ADMIN".equals(session.getAttribute("role"))) {
                return "redirect:/admin/dashboard";
            } else if ("SISWA".equals(session.getAttribute("role"))) {
                return "redirect:/siswa/dashboard";
            }
        }
        
        try {
            model.addAttribute("listPengumuman", pengumumanService.findAll());
            model.addAttribute("listGaleri", galeriService.findAll());
            model.addAttribute("username", session.getAttribute("username"));
            model.addAttribute("userLogin", session.getAttribute("userLogin"));
            return "index";
        } catch (Exception e) {
            model.addAttribute("listPengumuman", java.util.List.of());
            model.addAttribute("listGaleri", java.util.List.of());
            return "index";
        }
    }
}