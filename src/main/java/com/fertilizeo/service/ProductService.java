package com.fertilizeo.service;

import com.fertilizeo.entity.Produit;
import com.fertilizeo.entity.Stock;
import com.fertilizeo.repository.ProductRepository;
import com.fertilizeo.repository.StockRepository;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    StockRepository stockRepository;



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
            produit.setImage(productDetails.getImage());
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

    public byte[] getImage(Long idproduit) {
        Produit produit = productRepository.findById(idproduit).orElse(null);
        return produit != null ? produit.getImage() : null;
    }

    public String resizeAndCompressImage(MultipartFile imageFile, int width, int height, float quality) throws IOException, IOException {
        // Redimensionnement et compression de l'image
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(imageFile.getInputStream())
                .size(width, height)
                .outputQuality(quality)
                .toOutputStream(outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        // Convertir les données binaires de l'image en Base64
        String base64Image = java.util.Base64.getEncoder().encodeToString(imageBytes);
        return "data:image/jpeg;base64," + base64Image; // Format d'URL pour les données Base64
    }

    // public void saveImageUrl(Long idproduit, String imageUrl) {
    //     Optional<Produit> optionalProduct = productRepository.findById(idproduit);
    //     if (optionalProduct.isPresent()) {
    //         Produit existingProduct = optionalProduct.get();
    //         existingProduct.setImageUrl(imageUrl);
    //         productRepository.save(existingProduct);
    //     } else {
    //         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit non trouvé avec l'ID : " + idproduit);
    //     }
    // }

    public Produit getProduitById(Long id) {
        return productRepository.findById(id).orElse(null);
    }



    public List<Produit> getAllProductsWithStock() {
        List<Produit> products = productRepository.findAllProductsWithStock();
        // Récupérer la quantité de stock pour chaque produit
        for (Produit product : products) {
            Optional<Stock> optionalStock = stockRepository.findByProduitIdproduit(product.getIdproduit());
            optionalStock.ifPresent(stock -> product.setStock(stock));
        }
        return products;
    }


}

