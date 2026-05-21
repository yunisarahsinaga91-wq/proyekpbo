package pbo.springboot.nim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GaleriController {

    @Autowired
    private GaleriRepository galeriRepository;

    @GetMapping("/galeri")
    public String galeri(Model model) {

        model.addAttribute(
                "listGaleri",
                galeriRepository.findAll()
        );

        return "galeri";
    }

}