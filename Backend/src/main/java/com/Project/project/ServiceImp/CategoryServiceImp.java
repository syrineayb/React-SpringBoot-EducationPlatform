package com.Project.project.ServiceImp;

import com.Project.project.Repository.CategoryRepo;
import com.Project.project.Service.CategoryService;
import com.Project.project.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryServiceImp implements CategoryService {
    @Autowired
private CategoryRepo repository;
    public List<Category> listAllCategories() {
        if(repository == null) {
            throw new RuntimeException("CategoryRepo is null");
        }
        return repository.findAll();
    }

    @Override
    public Category getCategoryById(int idCategory) {
        return repository.findById((long) idCategory).get();
    }

    @Override
    public Category addCategory(Category cat) {
        return repository.save(cat);
    }

    @Override
    public void deleteCategory(int id) {
        repository.deleteById((long) id);
    }
    @Override
    public Category update(long idCategory, Category cat) {
        Optional<Category> optionalCategory = repository.findById(idCategory);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setName(cat.getName());
            return repository.save(category);
        } else {
            return null;
        }
    }


}
