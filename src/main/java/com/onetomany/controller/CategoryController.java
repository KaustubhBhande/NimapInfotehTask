package com.onetomany.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onetomany.model.Category;
import com.onetomany.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/all")
	public ResponseEntity<?> getCategories() {

		List<Category> category = categoryService.GetAllCategory();
		if (category.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
		}
		return ResponseEntity.ok(category);
	}

	@GetMapping("")
	public Page<Category> getCategoryWithPagination(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize);
		return categoryService.GetAllCategory1(pageable);
	}

	@GetMapping("/{id}")

	public ResponseEntity<?> getCategoryById(@PathVariable Integer id) {

		Optional<Category> CategoryById = categoryService.GetCategory(id);
		if (CategoryById.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category By Id Not Found");

		} else {
			Category Category = CategoryById.get();

			return ResponseEntity.ok().body(Category);
		}
	}

	@PostMapping(" ")
	public ResponseEntity<?> AddCategory(@RequestBody Category category) {
		List<String> errors = categoryService.validate(category);
		if (!errors.isEmpty()) {
			return ResponseEntity.badRequest().body(errors);
		}

		categoryService.AddCategory(category);
		return ResponseEntity.status(HttpStatus.CREATED).body("Category Added Sucessfully.");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> DeleteCategory(@PathVariable Integer id) {

		boolean deleted = categoryService.DeleteCategory(id);

		if (deleted) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT)
					.body("Category with ID " + id + " Deleted Sucessfully.");
		}

		else

		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category with ID " + id + " Not Found.");

		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateCategory(@PathVariable Integer id, @RequestBody Category category) {

		List<String> errors = categoryService.validate(category);
		if (!errors.isEmpty()) {
			return ResponseEntity.badRequest().body(errors);
		}

		Optional<Category> existingCategory = categoryService.GetCategory(id);
		if (!existingCategory.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category with ID " + id + "Not Found.");
		}

		categoryService.UpdateCategory(id, category);
		return ResponseEntity.ok().body("Category with ID " + id + " Updated Sucessfully.");
	}
}
