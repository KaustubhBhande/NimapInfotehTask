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

import com.onetomany.model.Product;
import com.onetomany.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService productService;

	@GetMapping("/all")
	public ResponseEntity<?> getAllProducts() {

		List<Product> product = productService.getAllProducts();
		if (product.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
		} else {
			return ResponseEntity.ok(product);
		}
	}

	@GetMapping("/page")
	public Page<Product> getCategoryWithPagination(@RequestParam(defaultValue = "0") int page,
	                                               @RequestParam(defaultValue = "5") int pageSize) {
	    Pageable pageable = PageRequest.of(page, pageSize);
	    return productService.getAllProducts1(pageable);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getProductById(@PathVariable Integer id) {

		Optional<Product> productById = productService.getProduct(id);
		if (productById.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product By Id Not Found");

		} else {
			Product product = productById.get();

			return ResponseEntity.ok().body(product);
		}
	}

	@PostMapping("")
	public ResponseEntity<?> addProduct(@RequestBody Product product) {
		List<String> error = productService.validate(product);
		if (error.size() != 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		}
		productService.addProduct(product);
		return ResponseEntity.status(HttpStatus.CREATED).body("Product Added Sucessfully.");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {

		boolean deleted = productService.deleteProduct(id);

		if (deleted) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Product with ID " + id + " Deleted Sucessfully.");
		}

		else

		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with ID " + id + " Not Found.");

		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestBody Product product) {

		List<String> errors = productService.validate(product);
		if (!errors.isEmpty()) {
			return ResponseEntity.badRequest().body(errors);
		}

		Optional<Product> existingproduct = productService.getProduct(id);
		if (!existingproduct.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with ID " + id + "Not Found.");
		}

		productService.updateProduct(id, product);
		return ResponseEntity.ok().body("Product with ID " + id + " Updated Sucessfully.");
	}
}

