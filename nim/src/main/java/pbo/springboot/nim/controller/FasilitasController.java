package pbo.springboot.nim.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FasilitasController {

    @GetMapping("/fasilitas")
    public String fasilitas() {
        return "fasilitas";
    }
}