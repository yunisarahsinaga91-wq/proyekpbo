package pbo.springboot.nim.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pbo.springboot.nim.service.*;

import java.util.List;

@Controller
public class HomeController {

    private final PengumumanService pengumumanService;
    private final GaleriService galeriService;
    private final ProfilService profilService;
    private final PrestasiService prestasiService;
    private final GuruService guruService;

    public HomeController(PengumumanService pengumumanService,
                          GaleriService galeriService,
                          ProfilService profilService,
                          PrestasiService prestasiService,
                          GuruService guruService) {

        this.pengumumanService = pengumumanService;
        this.galeriService = galeriService;
        this.profilService = profilService;
        this.prestasiService = prestasiService;
        this.guruService = guruService;
    }

    @GetMapping("/")
    public String home(HttpSession session, Model model) {

        // redirect login
        if (session.getAttribute("userLogin") != null) {
            if ("ADMIN".equals(session.getAttribute("role"))) {
                return "redirect:/admin/dashboard";
            } else if ("SISWA".equals(session.getAttribute("role"))) {
                return "redirect:/siswa/dashboard";
            }
        }

        // ambil data dari database (AMAN)
        model.addAttribute("listPengumuman", safeList(pengumumanService.findAll()));
        model.addAttribute("listGaleri", safeList(galeriService.findAll()));
        model.addAttribute("listProfil", safeList(profilService.findAll()));
        model.addAttribute("listPrestasi", safeList(prestasiService.findAll()));
        model.addAttribute("listGuru", safeList(guruService.findAll()));

        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("userLogin", session.getAttribute("userLogin"));

        return "index";
    }

    
    private <T> List<T> safeList(List<T> data) {
        return (data != null) ? data : List.of();
    }
}