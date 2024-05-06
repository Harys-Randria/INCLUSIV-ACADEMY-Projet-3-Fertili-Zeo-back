package com.fertilizeo.service;

import com.fertilizeo.entity.Produit;
import com.fertilizeo.repository.ProductRepository;
import jakarta.mail.MessagingException;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
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
    private ProductRepository productRepository;
    @Autowired
    private EmailSenderService emailSenderService;

    // Seuil de stock bas
    private static final int STOCK_THRESHOLD = 10;

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
            produit.setImage(productDetails.getImage()); // Utiliser setImageUrl si nécessaire
            produit.setQuantity(productDetails.getQuantity()); // Mettre à jour la quantité
            return productRepository.save(produit);
        } else {
            return null;
        }
    }

    public void deleteProduct(Long productId) {
        Optional<Produit> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            productRepository.delete(optionalProduct.get());
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



    public void processPurchase(Long productId, Double quantityPurchased) {
        Optional<Produit> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Produit product = optionalProduct.get();
            Double updatedQuantity = product.getQuantity() - quantityPurchased;
            if (updatedQuantity >= 0) {
                product.setQuantity(updatedQuantity.intValue());
                productRepository.save(product);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantité insuffisante en stock pour l'achat.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit non trouvé avec l'ID : " + productId);
        }
    }





    // Méthode pour vérifier le stock bas et envoyer des notifications par e-mail au fournisseur
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000) // Vérifiez toutes les 24 heures
    public void checkLowStockAndNotifySupplier() {
        List<Produit> produits = productRepository.findAll();
        for (Produit produit : produits) {
            if (produit.getQuantity() < STOCK_THRESHOLD) {
                String message = "Le stock du produit " + produit.getName() + " est bas. Quantité en stock : " + produit.getQuantity();
                try {
                    emailSenderService.sendEmailToSupplier(produit.getCompte().getEmail(), message);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
