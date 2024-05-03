package com.fertilizeo.controller;

import com.fertilizeo.entity.Compte;
import com.fertilizeo.entity.Produit;
import com.fertilizeo.service.CompteService;
import com.fertilizeo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


    @RestController
    @RequestMapping("/produit")
    @CrossOrigin(origins = "/http://localhost:3000")
    public class ProductController {

        @Autowired
        private ProductService productService;

        @Autowired
        private CompteService compteService;

        @GetMapping("/allproduct")
        public ResponseEntity<List<Produit>> getAllProducts() {
            List<Produit> products= productService.getAllProducts();
            return ResponseEntity.ok().body(products);
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
        public ResponseEntity<Produit> createProduct(@RequestParam("image") MultipartFile file,
                                                     @RequestParam("name") String prodName,
                                                     @RequestParam("price") double prodPrice,
                                                     @RequestParam("type") String type,
                                                     @RequestParam("category") String category,
                                                     @RequestParam("description") String descProduct,
                                                     @RequestParam("expirationDate")LocalDate dateExpiration,
                                                     @RequestParam("userId") Long id) {

            Optional<Compte> optionalCompte = compteService.findById(id);

            if (optionalCompte.isPresent()) {
                Compte compte = optionalCompte.get();
                Produit newProduct = new Produit();
                newProduct.setName(prodName);
                newProduct.setPrice(prodPrice);
                newProduct.setType(type);
                newProduct.setCategory(category);
                newProduct.setDescription(descProduct);
                newProduct.setExpirationDate(dateExpiration);
                newProduct.setCompte(compte);
                try{
                    byte[] image = file.getBytes();
                    newProduct.setImage(image);
                } catch (Exception e){
                    e.printStackTrace();
                }
                productService.createProduct(newProduct);
                return ResponseEntity.ok().body(newProduct);
            }else {
                return ResponseEntity.notFound().build();
            }
        }

        @PutMapping  ("/modifier/{id}")
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
            Produit produit = productService.getProduitById(id);
            if (produit != null) {
                StringBuilder detailsBuilder = new StringBuilder();
                detailsBuilder.append("ID: ").append(produit.getIdproduit()).append("\n");
                detailsBuilder.append("Name: ").append(produit.getName()).append("\n");
                detailsBuilder.append("Price: ").append(produit.getPrice()).append("\n");
                detailsBuilder.append("Expiration Date: ").append(produit.getExpirationDate()).append("\n");
                detailsBuilder.append("Type: ").append(produit.getType()).append("\n");
                detailsBuilder.append("Category: ").append(produit.getCategory()).append("\n");
                detailsBuilder.append("Description: ").append(produit.getDescription()).append("\n");
                detailsBuilder.append("Image URL: ").append(produit.getImage()).append("\n");

                return ResponseEntity.ok(detailsBuilder.toString());
            }
            return ResponseEntity.notFound().build();
        }

        // @GetMapping("/{produitId}/image-url")
        // public String getImageUrl(@PathVariable Long idproduit) {
        //     return productService.getImageUrl(idproduit);
        // }

        // @PostMapping("/{produitId}/upload-image")
        // public void uploadImage(@PathVariable Long idproduit, @RequestParam("image") MultipartFile imageFile) throws IOException {
        //     String resizedImageUrl = productService.resizeAndCompressImage(imageFile, 300, 300, 0.8f);
        //     productService.saveImageUrl(idproduit, resizedImageUrl);
        // }
    }




