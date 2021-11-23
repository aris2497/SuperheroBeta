package com.example.Superhero.controllers;

import com.example.Superhero.dao.SuperCharacterDao;
import com.example.Superhero.dto.SuperCharacter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

   /* @PostMapping("addTeacher")
    public String addTeacher(HttpServletRequest request) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String specialty = request.getParameter("specialty");

        Teacher teacher = new Teacher();
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        teacher.setSpecialty(specialty);

        teacherDao.addTeacher(teacher);

        return "redirect:/teachers";
    }*/
}
