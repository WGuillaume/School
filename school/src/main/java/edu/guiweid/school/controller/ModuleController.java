package edu.guiweid.school.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.guiweid.school.dao.ModuleDao;
import edu.guiweid.school.model.Module;
import edu.guiweid.school.view.ModuleView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ModuleController {
    @Autowired
    private ModuleDao moduleDao ;
    @GetMapping("/modules")
    @JsonView(ModuleView.class)
    public List<Module>getAllModule() {
        return moduleDao.findAll();
    }
    @GetMapping("/module/{id}")
    @JsonView(ModuleView.class)
    public ResponseEntity<Module> getModuleById(@PathVariable int id){
        Optional<Module> module = moduleDao.findById(id);
        if (module.isPresent()) {

            return new ResponseEntity<>(module.get(), HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/admin/module")
    public ResponseEntity<Module> saveModule(@RequestBody Module module) {

        //si l'utilisateur fourni n'a pas toutes les informations
        if (module == null || module.getName().equals("")){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //si l'id est fourni et qu'il n'existe pas dans la base donnée
        if(module.getId() !=null){
            Optional<Module> moduleDatabase=moduleDao.findById(module.getId());
            //si il n'existe pas dans la base de donnée
            if (moduleDatabase.isEmpty()){
                return new ResponseEntity<>(module ,HttpStatus.BAD_REQUEST);
            }
            //l'id n'est pas fourni c'est donc un update
            moduleDao.save(module);
            return new ResponseEntity<>(module ,HttpStatus.OK);

        }
        //l'id n'est pas fourni c'est donc un CREATE
        moduleDao.save(module);
        return new ResponseEntity<>(module ,HttpStatus.CREATED);

    }

    @DeleteMapping("/admin/module/{id}")
    public ResponseEntity<Module> deleteModule(@PathVariable int id) {
        Optional<Module> module = moduleDao.findById(id);

        if (module.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
         moduleDao.deleteById(id);
        return new ResponseEntity<>(module.get(),HttpStatus.OK);
    }
}
