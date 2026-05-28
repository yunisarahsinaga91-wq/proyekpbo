package pbo.springboot.nim.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 404 - Data tidak ditemukan
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleNotFound(ResourceNotFoundException e,
                                  Model model, HttpServletRequest request) {
        model.addAttribute("status", 404);
        model.addAttribute("error", "Data Tidak Ditemukan");
        model.addAttribute("message", e.getMessage());
        model.addAttribute("path", request.getRequestURI());
        return "error/404";
    }

    // 403 - Akses ditolak
    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDenied(AccessDeniedException e,
                                      Model model, HttpServletRequest request) {
        model.addAttribute("status", 403);
        model.addAttribute("error", "Akses Ditolak");
        model.addAttribute("message", e.getMessage());
        model.addAttribute("path", request.getRequestURI());
        return "error/403";
    }

    // 500 - Error umum
    @ExceptionHandler(Exception.class)
    public String handleGeneral(Exception e,
                                 Model model, HttpServletRequest request) {
        model.addAttribute("status", 500);
        model.addAttribute("error", "Terjadi Kesalahan Server");
        model.addAttribute("message", e.getMessage());
        model.addAttribute("path", request.getRequestURI());
        return "error/500";
    }
}
