package com.example.sgswimming.controllers.handlers;

import com.example.sgswimming.controllers.exceptions.NotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NotFoundExceptionHandler{

    @ExceptionHandler(NotFoundException.class)
    protected String handle(NotFoundException ex, Model model) {

        model.addAttribute("id", ex.getId());
        model.addAttribute("class", ex.getAClass().getSimpleName());

        return "exceptions/NotFoundException";
    }
}
