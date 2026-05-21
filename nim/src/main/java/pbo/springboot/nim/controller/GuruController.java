package pbo.springboot.nim.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GuruController {

    @GetMapping("/guru")
    public String guru() {
        return "guru";
    }

    @GetMapping("/prestasi")
    public String prestasi() {
        return "prestasi";
    }
}