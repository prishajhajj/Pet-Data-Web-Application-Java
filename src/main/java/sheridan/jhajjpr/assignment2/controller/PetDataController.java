package sheridan.jhajjpr.assignment2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sheridan.jhajjpr.assignment2.data.service.PetDataService;
import sheridan.jhajjpr.assignment2.model.PetsForm;
import java.util.List;


@Slf4j
@Controller
public class PetDataController {
    private static final String[] petKind = {
            "--- Select Pet Kind ---",
            "Dog",
            "Cat",
            "Rabbit"
    };

    private final PetDataService petDataService;

    public PetDataController(PetDataService petDataService) {
        this.petDataService = petDataService;
    }

    @GetMapping(value={"/", "/index"})
    public String index(){
        log.trace("index() is called");
        return "Index";
    }

    @GetMapping("/add-a-pet")
    public ModelAndView addPet() {
        log.trace("addPet() is called");
        ModelAndView modelAndView =
                new ModelAndView("AddPet", "form", new PetsForm());
        modelAndView.addObject("kind", petKind);
        return modelAndView;
    }

    @PostMapping("/insert-a-pet")
    public String insertPet(
        @Validated @ModelAttribute("form") PetsForm form,
        BindingResult bindingResult,
        Model model) {
    log.trace("insertPet() is called");
    log.debug("form =" + form);
    if(bindingResult.hasErrors()) {
        log.trace("input validation errors");
        model.addAttribute("PetKind", petKind);
        return "AddPets";
    }
    else {
        log.trace("user inputted correct values");
        petDataService.insertPetForm(form);
        log.debug("id =" + form.getId( ));
        return "redirect:confirm-pet-insert/" + form.getId();
        }
    }

    @GetMapping("/confirm-pet-insert/{id}")
    public String confirmPetInsert(@PathVariable(name = "id") String strId, Model model) {
        log.trace("confirmPetInsert() is called");
        log.debug("id = " + strId);
        try {
            int id = Integer.parseInt(strId);
            log.trace("Searching the database for data");
            PetsForm form = petDataService.getPetForm(id);
            if(form == null) {
                log.trace("For this id no data found:" + id);
                return "DataNotFound";
            }
            else {
                log.trace("Showing data");
                model.addAttribute("form", form);
                return "ConfirmPetInsert";
            }
        }
        catch(NumberFormatException e) {
            log.trace("ID not an integer");
            return "DataNotFound";
        }
    }

    @GetMapping("/list-pets")
    public ModelAndView listPets() {
        log.trace("listPets() is called");
        List<PetsForm> pList = petDataService.getAllPetsForm();
        log.debug("Size of List: " + pList.size());
        return new ModelAndView("ListPets", "pets", pList);
    }

    @GetMapping("/delete-pets")
    public String deletePets() {
        log.trace("deletePets() is called");
        petDataService.deleteAllPetForms();
        return "redirect:list-pets";
    }

    @GetMapping("detail-of-pets/{id}")
    public String detailsOfPets(@PathVariable String id, Model model){
        log.trace("detailsOfPets() is called");
        log.debug("id =" + id);
        try {
            PetsForm form = petDataService.getPetForm(Integer.parseInt(id));
            if(form != null) {
                model.addAttribute("pet", form);
                return "PetDetails";
            }
            else {
                log.trace("For this id no data found:" + id);
                return "DataNotFound";
            }
        }
        catch (NumberFormatException e) {
            log.trace("ID not an integer");
            return "DataNotFound";
        }
    }

    @GetMapping("/delete-a-pet")
    public String deleteAPet(@RequestParam String id, Model model) {
        log.trace("deleteAPet() is called");
        try {
            PetsForm form = petDataService.getPetForm(Integer.parseInt(id));
            if(form != null) {
                model.addAttribute("pet", form);
                return "DeletePets";
            }
            else {
                return "redirect:list-pets";
            }
        }
        catch (NumberFormatException e) {
            return "redirect:list-pets";
        }
    }

    @PostMapping("/remove-a-pet")
    public String removeAPet(@RequestParam String id) {
        log.trace("removeAPet() is called");
        log.debug("id =" + id);
        try {
            petDataService.deletePetForm(Integer.parseInt(id));
        }
        catch (NumberFormatException e) {
            log.trace("ID not an integer");
        }
        return "redirect:list-pets";
    }

    @GetMapping("/edit-a-pet")
    public String editAPet(@RequestParam String id, Model model) {
        log.trace("editAPet() is called");
        log.debug("id =" + id);
        try {
            PetsForm form = petDataService.getPetForm(Integer.parseInt(id));
            if (form != null) {
                model.addAttribute("form", form);
                model.addAttribute("petKind", petKind);
                return "EditPets";
            }
            else {
                log.trace("For this id no data found:" + id);
                return "redirect:list-pets";
            }
        }
        catch (NumberFormatException e) {
            log.trace("ID not an integer");
            return "redirect:list-pets";
        }
    }

    @PostMapping("/update-a-pet")
    public String updateAPet(
            @Validated @ModelAttribute("form") PetsForm form,
            BindingResult bindingResult,
            Model model) {
        log.trace("updateAPet() is called");
        log.debug("form = " + form);
        if (bindingResult.hasErrors()) {
            log.trace("input validation errors");
            model.addAttribute("petKind", petKind);
            return "EditPets";
        }
        else {
            log.trace("Inputs of user are correct");
            petDataService.updatePetForm(form);
            log.debug("id =" + form.getId());
            return "redirect:pet-details/" + form.getId();
        }
    }

}






