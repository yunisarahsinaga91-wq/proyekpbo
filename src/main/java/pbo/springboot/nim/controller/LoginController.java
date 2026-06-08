package pbo.springboot.nim.controller;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pbo.springboot.nim.model.User;
import pbo.springboot.nim.service.UserService;

@Controller
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        Object role = session.getAttribute("role");
        if (role != null) {
            if ("ADMIN".equals(role)) return "redirect:/admin/dashboard";
            if ("SISWA".equals(role)) return "redirect:/siswa/dashboard";
        }
        return "auth/login";
    }

    @PostMapping("/login")
    public String loginProses(@RequestParam String username, @RequestParam String password,
                               HttpSession session, Model model) {
        try {
            User user = userService.login(username, password);
            if (user != null) {
                session.setAttribute("userLogin", true);
                session.setAttribute("username", user.getUsername());
                session.setAttribute("role", user.getRole());
                if ("ADMIN".equals(user.getRole())) {session.setAttribute("adminLogin", true);
                    return "redirect:/admin/dashboard";
                } else if ("SISWA".equals(user.getRole())) {
                    return "redirect:/siswa/dashboard";
                }
                return "redirect:/";
            }
            model.addAttribute("error", "Username atau password salah");
            return "auth/login";
        } catch (Exception e) { model.addAttribute("error", "Gagal login, coba lagi nanti");
            return "auth/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}