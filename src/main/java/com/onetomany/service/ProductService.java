package com.onetomany.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.onetomany.model.Product;
import com.onetomany.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;

	public List<Product> getAllProducts() {
		List<Product> list = (List<Product>) this.productRepository.findAll();
		return list;
	}

	public Page<Product> getAllProducts1(Pageable pageable) {
		return this.productRepository.findAll(pageable);
	}

	public Optional<Product> getProduct(Integer id) {
		return this.productRepository.findById(id);
	}

	public Product addProduct(Product product) {
		// get category
		// set in product.setCategory(c)
		return this.productRepository.save(product);
	}

	public boolean deleteProduct(Integer id) {
		boolean exists = productRepository.existsById(id);
		if (exists) {
			productRepository.deleteById(id);
			return true;
		} else {

			return false;
		}
	}

	public Product updateProduct(Integer id, Product product) {
		Product existingproduct = productRepository.findById(id).orElse(null);
		existingproduct.setId(product.getId());
		existingproduct.setName(product.getName());
		existingproduct.setDescription(product.getDescription());
		existingproduct.setPrice(product.getPrice());
		return productRepository.save(existingproduct);
	}

	public List<String> validate(Product product) {

		List<String> error = new ArrayList<>();

		if (product.getName() == null) {
			error.add("Name Cannot Be Empty");
		}

		if (product.getDescription() == null) {
			error.add("Description Cannot Be Empty");
		}

		return error;

	}
}
