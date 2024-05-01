package com.fertilizeo.controller;

import com.fertilizeo.entity.Produit;
import com.fertilizeo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

        @GetMapping("/details/{id}")
        public ResponseEntity<String> getProduitDetailsDescriptor(@PathVariable("id") Long id) {
            Optional<Produit> optionalProduit = productService.getProductById(id);
            if (optionalProduit.isPresent()) {
                Produit produit = optionalProduit.get();
                StringBuilder detailsBuilder = new StringBuilder();
                detailsBuilder.append("ID: ").append(produit.getIdproduit()).append("\n");
                detailsBuilder.append("Name: ").append(produit.getName()).append("\n");
                detailsBuilder.append("Price: ").append(produit.getPrice()).append("\n");
                detailsBuilder.append("Expiration Date: ").append(produit.getExpirationDate()).append("\n");
                detailsBuilder.append("Type: ").append(produit.getType()).append("\n");
                detailsBuilder.append("Category: ").append(produit.getCategory()).append("\n");
                detailsBuilder.append("Description: ").append(produit.getDescription()).append("\n");
                detailsBuilder.append("Image URL: ").append(produit.getImageUrl()).append("\n");
                detailsBuilder.append("Details Descriptor: ").append(produit.getDetailsDecriptor()).append("\n");

                return ResponseEntity.ok(detailsBuilder.toString());
            }
            return ResponseEntity.notFound().build();
        }



        @GetMapping("/{produitId}/image-url")
        public String getImageUrl(@PathVariable Long idproduit) {
            return productService.getImageUrl(idproduit);
        }

        @PostMapping("/{produitId}/upload-image")
        public void uploadImage(@PathVariable Long idproduit, @RequestParam("image") MultipartFile imageFile) throws IOException {
            String resizedImageUrl = productService.resizeAndCompressImage(imageFile, 300, 300, 0.8f);
            productService.saveImageUrl(idproduit, resizedImageUrl);
        }
    }




