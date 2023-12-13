package com.Project.project.Service;

import com.Project.project.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> listAllCategories();
    Category getCategoryById(int idCategory);
    Category addCategory(Category cat);
    void deleteCategory(int id );
        Category update(long idCategory, Category cat);
}
