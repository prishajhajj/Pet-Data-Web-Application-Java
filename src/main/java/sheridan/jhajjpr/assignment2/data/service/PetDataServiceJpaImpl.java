package sheridan.jhajjpr.assignment2.data.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sheridan.jhajjpr.assignment2.data.repository.PetDataRepositoryJpa;
import sheridan.jhajjpr.assignment2.data.repository.PetEntityJpa;
import sheridan.jhajjpr.assignment2.model.PetsForm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class PetDataServiceJpaImpl implements PetDataService {

    private final PetDataRepositoryJpa petDataRepositoryJpa;

    PetDataServiceJpaImpl(PetDataRepositoryJpa petDataRepositoryJpa) {
        log.trace("constructor() is called");
        this.petDataRepositoryJpa = petDataRepositoryJpa;
    }

    private static void copyFormToEntity(PetsForm form, PetEntityJpa pet) {
        log.trace("copyFormToEntity() is called");
        pet.setFirstName(form.getFirstName());
        pet.setLastName(form.getLastName());
        pet.setPetKind(form.getPetKind());
        pet.setPetGender(form.getPetGender());
        pet.setPetVaccination(form.isPetVaccination());
    }

    @Override
    public void insertPetForm(PetsForm form) {
        log.trace("insertPetsForm() is called");
        log.debug("insert pet form" + form);
        PetEntityJpa pet = new PetEntityJpa();
        copyFormToEntity(form, pet);
        pet = petDataRepositoryJpa.save(pet);
        form.setId(pet.getId());
    }

    public List<PetsForm> getAllPetsForm() {
        log.trace("getAllPetForms() is called");
        List<PetsForm> pformList = new ArrayList<>();
        List<PetEntityJpa> petsList = petDataRepositoryJpa.findAll();
        for(PetEntityJpa pet: petsList) {
            PetsForm form = new PetsForm();
            copyFormToEntity(form, pet);
            pformList.add(form);
        }
        log.trace("retrived {} form objects", pformList.size());
        return pformList;
    }
    @Override
    public void deleteAllPetForms() {
        log.trace("deletePetsForm() is called");
        log.debug("All pet forms are deleting");
        petDataRepositoryJpa.deleteAll();
    }

    public void deletePetForm(int id) {
        log.trace("deletePetForm() is called");
        log.debug("deleting student form for id=" + id);
        petDataRepositoryJpa.deleteById(id);
    }
    @Override
    public PetsForm getPetForm(int id) {
        log.trace("getPetForm() is called");
        log.debug("getting pet form for id =" + id);
        Optional<PetEntityJpa> petResult = petDataRepositoryJpa.findById(id);
        if(petResult.isPresent()) {
            PetsForm form = new PetsForm();
            PetEntityJpa pet = petResult.get();
            copyFormToEntity(form, pet);
            log.debug("the form for id{} is retieved", id);
            return form;
        }
        log.debug("the form for id={} is not found", id);
        return null;
    }

    public void updatePetForm(PetsForm form) {
        log.trace("updatePetForm() is called");
        log.debug("form=" + form);
        Optional<PetEntityJpa> petResult = petDataRepositoryJpa.findById(form.getId());
        if(petResult.isPresent()) {
            PetEntityJpa pet = petResult.get();
            copyFormToEntity(form, pet);
            petDataRepositoryJpa.save(pet);
        }
    }
}
