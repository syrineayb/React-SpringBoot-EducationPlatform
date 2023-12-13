package com.Project.project.Controler;

import com.Project.project.Repository.CategoryRepo;
import com.Project.project.ServiceImp.CategoryServiceImp;
import com.Project.project.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class CategoryController {
    @Autowired
    private CategoryServiceImp Cservice;
    @Autowired
private CategoryRepo Crepo;

        @GetMapping("/Category")
        public List<Category>ListALLCategory(){
        return this.Cservice.listAllCategories();
        }

        @GetMapping("/find/{idCategory}")
        public ResponseEntity<Category> getCateByIdCat(@PathVariable("idCategory") int idCategory) {
        Category cat = Cservice.getCategoryById(idCategory);
        return new ResponseEntity<>(cat, HttpStatus.OK);
        }
        @PostMapping("/add")
        public ResponseEntity<Category> add(@RequestBody Category category) {
                Category newcat = Cservice.addCategory(category);
                return new ResponseEntity<>(newcat, HttpStatus.CREATED);
        }
        @DeleteMapping("/delete/{idCategory}")
        public ResponseEntity<?> deletecategory(@PathVariable("idCategory") int idCategory) {
                Cservice.deleteCategory(idCategory);
                return new ResponseEntity<>(HttpStatus.OK);
        }

       @PostMapping("update/{idCategory}")
       public Category updateCategoryById(@PathVariable Long idCategory, @RequestBody Category updatedCategory) {
               return Cservice.update(idCategory, updatedCategory);
       }



}
