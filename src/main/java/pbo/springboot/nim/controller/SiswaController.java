package pbo.springboot.nim.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pbo.springboot.nim.exception.AccessDeniedException;
import pbo.springboot.nim.service.*;

@Controller
@RequestMapping("/siswa")
public class SiswaController {

    private final PengumumanService pengumumanService;
    private final GaleriService galeriService;
    private final GuruService guruService;
    private final PrestasiService prestasiService;
    private final ProfilService profilService;

    // Constructor injection - bukan @Autowired
    public SiswaController(PengumumanService pengumumanService,
                            GaleriService galeriService,
                            GuruService guruService,
                            PrestasiService prestasiService,
                            ProfilService profilService) {
        this.pengumumanService = pengumumanService;
        this.galeriService = galeriService;
        this.guruService = guruService;
        this.prestasiService = prestasiService;
        this.profilService = profilService;
    }

    // Validasi role SISWA - lempar AccessDeniedException (bukan RuntimeException)
    private void validateSiswa(HttpSession session) {
        Object role = session.getAttribute("role");
        if (role == null || !"SISWA".equals(role)) {
            throw new AccessDeniedException("Halaman ini hanya untuk siswa. Silakan login terlebih dahulu.");
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        validateSiswa(session);
        try {
            model.addAttribute("username", session.getAttribute("username"));
            model.addAttribute("pengumumanTerbaru",
                    pengumumanService.findAll().stream()
                            .sorted((a, b) -> b.getId().compareTo(a.getId()))
                            .limit(5)
                            .toList());
            model.addAttribute("totalGaleri", galeriService.findAll().size());
            model.addAttribute("totalGuru", guruService.findAll().size());
            model.addAttribute("totalPrestasi", prestasiService.findAll().size());
            return "siswa/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", "Gagal memuat dashboard: " + e.getMessage());
            return "siswa/dashboard";
        }
    }

    @GetMapping("/profil-sekolah")
    public String profilSekolah(HttpSession session, Model model) {
        validateSiswa(session);
        model.addAttribute("profils", profilService.findAll());
        return "siswa/profil-sekolah";
    }

    @GetMapping("/pengumuman")
    public String pengumuman(HttpSession session, Model model) {
        validateSiswa(session);
        model.addAttribute("pengumumans", pengumumanService.findAll());
        return "siswa/pengumuman";
    }

    @GetMapping("/galeri")
    public String galeri(HttpSession session, Model model) {
        validateSiswa(session);
        model.addAttribute("galeris", galeriService.findAll());
        return "siswa/galeri";
    }

    @GetMapping("/data-guru")
    public String dataGuru(HttpSession session, Model model) {
        validateSiswa(session);
        model.addAttribute("gurus", guruService.findAll());
        return "siswa/data-guru";
    }

    @GetMapping("/prestasi")
    public String prestasi(HttpSession session, Model model) {
        validateSiswa(session);
        model.addAttribute("prestasis", prestasiService.findAll());
        return "siswa/prestasi";
    }
}
