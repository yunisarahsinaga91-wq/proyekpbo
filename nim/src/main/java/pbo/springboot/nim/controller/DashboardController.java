package pbo.springboot.nim.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pbo.springboot.nim.repository.*;

@Controller
public class DashboardController {

    @Autowired private ProfilRepository     profilRepository;
    @Autowired private PengumumanRepository pengumumanRepository;
    @Autowired private GaleriRepository     galeriRepository;
    @Autowired private GuruRepository       guruRepository;
    @Autowired private PrestasiRepository   prestasiRepository;
    @Autowired private UserRepository       userRepository;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (session.getAttribute("adminLogin") == null) return "redirect:/login";

        model.addAttribute("totalProfil",     profilRepository.count());
        model.addAttribute("totalPengumuman", pengumumanRepository.count());
        model.addAttribute("totalGaleri",     galeriRepository.count());
        model.addAttribute("totalGuru",       guruRepository.count());
        model.addAttribute("totalPrestasi",   prestasiRepository.count());
        model.addAttribute("totalUser",       userRepository.count());
        return "admin/dashboard";
    }
}