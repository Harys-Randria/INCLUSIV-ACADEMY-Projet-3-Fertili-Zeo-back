package com.fertilizeo.service;

import com.fertilizeo.entity.Produit;
import com.fertilizeo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<Produit> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Produit> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    public Produit createProduct(Produit produit) {
        return productRepository.save(produit);
    }


    public Produit updateProduct(Long productId, Produit productDetails) {
        Optional<Produit> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Produit produit = optionalProduct.get();
            produit.setName(productDetails.getName());
            produit.setPrice(productDetails.getPrice());
            produit.setExpirationDate(productDetails.getExpirationDate());
            produit.setType(productDetails.getType());
            produit.setCategory(productDetails.getCategory());
            produit.setDescription(productDetails.getDescription());
            produit.setImageUrl(productDetails.getImageUrl());
            return productRepository.save(produit);
        } else {

            return null;
        }
    }

    public void deleteProduct(Long productId) {
        Optional<Produit> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            productRepository.delete(optionalProduct.get());
        } else {

        }
    }

}