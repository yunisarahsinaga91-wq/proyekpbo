package pbo.springboot.nim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InformasiController {

    @Autowired
    private InformasiRepository informasiRepository;

    @GetMapping("/informasi")
    public String informasi(Model model) {

        model.addAttribute(
                "listInformasi",
                informasiRepository.findAll()
        );

        return "informasi";
    }

}