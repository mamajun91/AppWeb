package com.appweb.controller;

import com.appweb.model.Person;
import com.appweb.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private  ApiService apiService;


    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("person", new Person());
        return "persons/create";
    }

    @GetMapping()
    public String listPersons(Model model) {
        List<Person> persons = apiService.getAllPersons();
        System.out.println("nombre de personnes: " + persons.size());
        model.addAttribute("persons", persons);
        return "persons/list";
    }

    @GetMapping("/{id}")
    public String viewPerson(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        Person person = apiService.getPersonById(id);
        if (person != null) {
            model.addAttribute("person", person);
            return "persons/view";
        } else {
            redirectAttributes.addFlashAttribute("error", "Person not found");
            return "redirect:/persons/list";
        }
    }

    @PostMapping
    public String createPerson(@ModelAttribute Person person, RedirectAttributes redirectAttributes) {
        Person createdPerson = apiService.createPerson(person);
        if (createdPerson != null) {
            redirectAttributes.addFlashAttribute("success", "Person created successfully");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to create person");
        }
        return "redirect:/persons";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        Person person = apiService.getPersonById(id);
        if (person != null) {
            model.addAttribute("person", person);
            return "persons/edit";
        } else {
            redirectAttributes.addFlashAttribute("error", "Person not found");
            return "redirect:/persons/list";
        }
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.POST, RequestMethod.PUT})
    public String updatePerson(@PathVariable Integer id, @ModelAttribute Person person, RedirectAttributes redirectAttributes) {
        Person updatedPerson = apiService.updatePerson(id, person);
        if (updatedPerson != null) {
            redirectAttributes.addFlashAttribute("success", "Person updated successfully");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to update person");
        }
        return "redirect:/persons";
    }

    @GetMapping("/{id}/delete")
    public String deletePerson(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            apiService.deletePerson(id);
            redirectAttributes.addFlashAttribute("success", "Person deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete person: " + e.getMessage());
        }
        return "redirect:/persons";
    }
}

