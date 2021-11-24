package com.example.Superhero.controllers;

import com.example.Superhero.dao.*;
import com.example.Superhero.dto.*;
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

    @Autowired
    LocationDaoDB locationDaoDB;

    @Autowired
    OrganisationDao organisationDao;

    @Autowired
    MemberDaoDB memberDaoDB;

    @GetMapping("home")
    public String displayNewsFeed(Model model) {
        List<Sighting> sightings = sightingDaoDB.getAllSightingByDate();
        System.out.println(sightings.toString());
        for (Sighting s: sightings
             ) {
            s.setLocation(locationDaoDB.getLocationById(Integer.parseInt(s.getLocation())).getName());
            s.setSuperCharacter(superCharacterDao.getSuperCharacterById(Integer.parseInt(s.getSuperCharacter())).getName());
        }
        //getSuperCharacterById()
        //getLocationById()
        model.addAttribute("sightings", sightings);
        return "Home";
    }

    @GetMapping("locations")
    public String displayLocations(Model model) {
        List<Location> locations = locationDaoDB.getAllLocations();
        model.addAttribute("locations", locations);

        return "Location";
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
    @GetMapping("seeMembers")
    public String seeMembers(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        List<Member> members = memberDaoDB.getAllMembersByOrganisation(id);

        model.addAttribute("members", members);
        return "MembersAtOrganisation";
    }
    @GetMapping("seeOrganisationSuperCharacters")
    public String seeOrganisationSuperCharacters(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        List<SuperCharacter> superCharacters  = superCharacterDao.get(id);

        model.addAttribute("superCharacters", superCharacters);
        return "SuperCharAtOrganisation";
    }

    @GetMapping("superCharactersAtLocation")
    public String getSuperCharactersAtLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationDaoDB.getLocationById(id);
        List<SuperCharacter> superCharacters = superCharacterDao.getAllSuperCharactersByLocation(location);

        model.addAttribute("superCharacters", superCharacters);
        System.out.println(superCharacters.toString());
        return "SuperCharactersAtLocation";
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

    @GetMapping("organisations")
    public String getOrganisations(Model model){
        List<Organisation> organizations = organisationDao.getAllOrganisation();
        model.addAttribute("organizations", organizations);

        return "Organisations";
    }
}
