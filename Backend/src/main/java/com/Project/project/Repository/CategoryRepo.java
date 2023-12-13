package com.Project.project.Repository;

import com.Project.project.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Long > {

}
