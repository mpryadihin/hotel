package ru.mpryadihin.diplom.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mpryadihin.diplom.models.Client;
import ru.mpryadihin.diplom.servicies.ClientService;
import ru.mpryadihin.diplom.util.ClientValidator;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;
    private final ClientValidator clientValidator;
    @Autowired
    public ClientController(ClientService clientService, ClientValidator clientValidator) {
        this.clientService = clientService;
        this.clientValidator = clientValidator;
    }
    @GetMapping()
    public String index(Model model, @ModelAttribute("client") String client){
        model.addAttribute("clients" , clientService.findAll());
        return "client/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("client", clientService.findOne(id));
        return "client/show";
    }

    @GetMapping("/search")
    public String search(@RequestParam("surname") String surname, Model model){
        model.addAttribute("search", clientService.search(surname));
        return "client/index";
    }
    @GetMapping("/new")
    public String newClient(@ModelAttribute("client") Client client){
        return "client/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("client") @Valid Client client,
                         BindingResult bindingResult){
        clientValidator.validate(client, bindingResult);
        if (bindingResult.hasErrors()){
            return "client/new";
        }
        clientService.save(client);
        return "redirect:/client";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("client", clientService.findOne(id));
        return "client/edit";
    }
    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("client") @Valid Client client,
                         BindingResult bindingResult){
        clientValidator.validate(client, bindingResult);
        if (bindingResult.hasErrors())
            return "client/edit";

        clientService.update(id, client);
        return "redirect:/client";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        clientService.delete(id);
        return "redirect:/client";
    }

}
