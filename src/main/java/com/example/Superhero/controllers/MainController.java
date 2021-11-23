package com.example.Superhero.controllers;

import com.example.Superhero.dao.SuperCharacterDao;
import com.example.Superhero.dto.SuperCharacter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class MainController {
    @Autowired // DI-ed in
    SuperCharacterDao superCharacterDao;

    @GetMapping("superCharacters")
    public String displaySuperCharacters(Model model) {
        List<SuperCharacter> superCharacters = superCharacterDao.getAllSuperCharacters();
        model.addAttribute("superCharacters", superCharacters);
        return "SuperCharacters";
    }

    @PostMapping("addSuperCharacter")
    public String addSuperCharacter(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String superpower = request.getParameter("superpower");

        SuperCharacter superChar = new SuperCharacter();
        superChar.setName(name);
        superChar.setDescription(description);
        superChar.setSuperpower(superpower);

        superCharacterDao.addSuperCharacter(superChar);

        return "redirect:/superCharacters";
    }
}
