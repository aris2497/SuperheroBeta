package com.example.Superhero.controllers;

import com.example.Superhero.dao.SightingDao;
import com.example.Superhero.dao.SightingDaoDB;
import com.example.Superhero.dao.SuperCharacterDao;
import com.example.Superhero.dto.Sighting;
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

    @Autowired
    SightingDaoDB sightingDaoDB;

    @GetMapping
    public String displayNewsFeed(Model model) {
        List<Sighting> sightings = sightingDaoDB.getAllSightingByDate();
        System.out.println(sightings.toString());
        model.addAttribute("sightings", sightings.);
        return "Home";
    }

    @GetMapping("superCharacters")
    public String displaySuperCharacters(Model model) {
        List<SuperCharacter> superCharacters = superCharacterDao.getAllSuperCharacters();
        System.out.println(superCharacters.toString());
        model.addAttribute("superCharacters", superCharacters);
        return "SuperCharacters";
    }

    @PostMapping("addSuperCharacter")
    public String addSuperCharacter(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String superpower = request.getParameter("superpower");
        System.out.println("checkHere: " + name + description + superpower);

        SuperCharacter superChar = new SuperCharacter();
        superChar.setName(name);
        superChar.setDescription(description);
        superChar.setSuperpower(superpower);
        System.out.println();

        superCharacterDao.addSuperCharacter(superChar);

        return "redirect:/superCharacters";
    }

    @GetMapping("deleteSuperCharacter")
    public String deleteTeacher(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        superCharacterDao.deleteSuperCharacterById(id);

        return "redirect:/superCharacters";
    }

    @GetMapping("editSuperCharacter")
    public String editSuperCharacter(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        SuperCharacter superCharacter = superCharacterDao.getSuperCharacterById(id);

        model.addAttribute("superCharacter", superCharacter);
        System.out.println(superCharacter.toString());
        return "EditSuperCharacter";
    }

    @PostMapping("editSuperCharacter")
    public String performEditTeacher(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        SuperCharacter superCharacter = superCharacterDao.getSuperCharacterById(id);

        superCharacter.setName(request.getParameter("name"));
        superCharacter.setDescription(request.getParameter("description"));
        superCharacter.setSuperpower(request.getParameter("superpower"));

        superCharacterDao.updateSuperCharacter(superCharacter);

        return "redirect:/superCharacters";
    }
}
