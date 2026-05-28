package pbo.springboot.nim.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pbo.springboot.nim.model.User;
import pbo.springboot.nim.service.UserService;

@Controller
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerProses(@RequestParam String username,
                                  @RequestParam String password,
                                  Model model) {
        try {
            if (userService.isUsernameExists(username)) {
                model.addAttribute("error", "Username sudah digunakan");
                return "auth/register";
            }
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setRole("SISWA");
            userService.register(user);
            return "redirect:/login";

        } catch (Exception e) {
            model.addAttribute("error", "Gagal registrasi, coba lagi nanti");
            return "auth/register";
        }
    }
}
