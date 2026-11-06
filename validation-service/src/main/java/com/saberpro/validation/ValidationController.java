package com.saberpro.validation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidationController {

    public record ValidationRequest(String justification) {}
    public record ValidationResponse(boolean valid, String message) {}

    /** Regla del enunciado: la justificación es válida si su longitud es > 15 caracteres. */
    @PostMapping("/validate")
    public ValidationResponse validate(@RequestBody ValidationRequest request) {
        String j = request.justification();
        boolean valid = j != null && j.trim().length() > 15;
        return new ValidationResponse(valid,
                valid ? "Justificación válida" : "La justificación debe tener más de 15 caracteres");
    }
}
