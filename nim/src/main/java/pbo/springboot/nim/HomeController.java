package pbo.springboot.nim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private InformasiRepository informasiRepository;

    @Autowired
    private GaleriRepository galeriRepository;

    // ================= HOME =================
    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute(
                "listInformasi",
                informasiRepository.findAll()
        );

        model.addAttribute(
                "listGaleri",
                galeriRepository.findAll()
        );

        return "index";
    }

}