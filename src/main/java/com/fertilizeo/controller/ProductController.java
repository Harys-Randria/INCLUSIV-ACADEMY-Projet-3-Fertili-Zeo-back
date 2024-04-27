package com.fertilizeo.controller;

import com.fertilizeo.entity.Produit;
import com.fertilizeo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


    @RestController
    @RequestMapping("/produit")
    public class ProductController {

        @Autowired
        private ProductService productService;

        @GetMapping
        public List<Produit> getAllProducts() {
            return productService.getAllProducts();
        }

        @GetMapping("/{id}")
        public ResponseEntity<Produit> getProductById(@PathVariable Long id) {
            Optional<Produit> product = productService.getProductById(id);
            if (product.isPresent()) {
                return ResponseEntity.ok().body(product.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @PostMapping("/ajouter")
        public ResponseEntity<Produit> createProduct(@RequestBody Produit produit) {
            Produit createdProduct = productService.createProduct(produit);
            return ResponseEntity.ok().body(createdProduct);
        }

        @PutMapping("/modifier/{id}")
        public ResponseEntity<Produit> updateProduct(@PathVariable Long id, @RequestBody Produit produit) {
            Produit updatedProduct = productService.updateProduct(id, produit);
            if (updatedProduct != null) {
                return ResponseEntity.ok().body(updatedProduct);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @DeleteMapping("/supprimer/{id}")
        public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
            productService.deleteProduct(id);
            return ResponseEntity.ok().build();
        }
    }



