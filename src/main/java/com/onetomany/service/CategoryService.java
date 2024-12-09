package com.onetomany.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.onetomany.model.Category;
import com.onetomany.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	public List<String> validate(Category category) {

		List<String> error = new ArrayList<>();

		if (category.getName() == null) {
			error.add("Name Cannot Be Empty");
		}

		return error;
	}

	public Page<Category> GetAllCategory1(Pageable pageable) {
		return categoryRepository.findAll(pageable);
	}

	public List<Category> GetAllCategory() {
		return (List<Category>) this.categoryRepository.findAll();

	}

	public Optional<Category> GetCategory(Integer id) {
		return this.categoryRepository.findById(id);
	}

	public Category AddCategory(Category category) {
		return this.categoryRepository.save(category);
	}

	public boolean DeleteCategory(Integer id) {
		boolean exists = categoryRepository.existsById(id);
		if (exists) {
			categoryRepository.deleteById(id);
			return true;
		} else {

			return false;
		}
	}

	public Category UpdateCategory(Integer id, Category category) {
		Category existingcategory = categoryRepository.findById(id).orElse(null);
		existingcategory.setName(category.getName());
		return categoryRepository.save(existingcategory);
	}
}
