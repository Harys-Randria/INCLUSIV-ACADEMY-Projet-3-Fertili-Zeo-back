package com.fertilizeo.controller;

import com.fertilizeo.entity.*;
import com.fertilizeo.repository.StockHistoryRepository;
import com.fertilizeo.repository.StockRepository;
import com.fertilizeo.service.ProductService;
import com.fertilizeo.service.StockNotFoundException;
import com.fertilizeo.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockHistoryRepository stockHistoryRepository;


    @Autowired
    private ProductService productService;
    @GetMapping("/quantity/{productId}")
    public ResponseEntity<Integer> getStockQuantityByProductId(@PathVariable Long productId) {
        int stockQuantity = stockService.getStockQuantityByProductId(productId);
        return ResponseEntity.ok(stockQuantity);
    }

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/{produitId}")
    public ResponseEntity<Integer> getQuantiteEnStock(@PathVariable Long idproduit) {
        Produit produit = productService.getProduitById(idproduit);
        if (produit != null) {

            int quantiteEnStock = stockService.getQuantiteEnStock(produit);
            return ResponseEntity.ok(quantiteEnStock);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/du_produit/{produitId}")
    public ResponseEntity<Long> getStockQuantityByProduitId(@PathVariable Long produitId) {
        Long stockQuantity = stockService.findByProduitIdproduit(produitId);
        if (stockQuantity != null) {
            return ResponseEntity.ok(stockQuantity);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/augmenter/{produitId}")
    public ResponseEntity<Void> augmenterStock(@PathVariable Long idproduit, @RequestParam Integer quantity) {
        Produit produit = productService.getProduitById(idproduit);
        if (produit != null) {

            stockService.augmenterStock(produit, quantity);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/diminuer/{produitId}")
    public ResponseEntity<Void> diminuerStock(@PathVariable Long idproduit, @RequestParam Integer quantity) {
        Produit produit = productService.getProduitById(idproduit);
        if (produit != null) {


            stockService.diminuerStock(produit, quantity);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/purchase/{produitId}")
    public Stock purchaseStock(@PathVariable Long produitId, @RequestBody PurchaseRequest purchaseRequest) {
        // Récupérer le stock actuel du produit
        Integer quantity = purchaseRequest.getQuantity();
        Produit produit = new Produit();
        produit.setIdproduit(produitId);
        Optional<Stock> currentStockOptional = stockRepository.findByProduit(produit);
        if (currentStockOptional.isPresent()) {
            Stock currentStock = currentStockOptional.get();

            // Récupérer le stock initial
            int initialStock = currentStock.getQuantity();

            // Vérifier si le stock est suffisant pour l'achat
            if (currentStock.getQuantity() >= quantity) {
                // Mettre à jour le stock
                currentStock.setQuantity(currentStock.getQuantity() - quantity);
                Stock updatedStock = stockRepository.save(currentStock);

                // Enregistrer l'historique de stock pour l'achat
                StockHistory stockHistory = new StockHistory();
                stockHistory.setStock(updatedStock);
                stockHistory.setQuantityChange(-quantity); // Quantité négative pour un achat
                stockHistory.setDate(new Date());
                stockHistory.setMouvementType("Achat");

                // Stock initial avant l'achat
                stockHistory.setInitialStock(initialStock);

                // Stock final après l'achat
                stockHistory.setFinalStock(updatedStock.getQuantity());

                stockHistoryRepository.save(stockHistory);

                // Créer un objet StockExportDTO pour la sortie
                StockExportDTO stockExportDTO = new StockExportDTO(
                        updatedStock.getProduit().getName(),
                        updatedStock.getCompte().getName(),
                        updatedStock.getQuantity(),
                        produitId,
                        initialStock,
                        updatedStock.getQuantity(),
                        "Achat", // Type de mouvement
                        -quantity, // Quantité changée
                        Arrays.asList(stockHistory) // Mettre l'historique dans une liste
                );

                // Vous pouvez retourner le stock exporté si vous en avez besoin
                // return stockExportDTO;

                return updatedStock;
            } else {
                // Gérer le cas où le stock est insuffisant
                // Vous pouvez lever une exception, renvoyer un message d'erreur, etc.
                // Ici, je renvoie null, mais vous devriez adapter cela à votre logique de gestion des erreurs
                return null;
            }
        }
        return null;
    }






    @PostMapping("/import")
    public ResponseEntity<String> importStockData(@RequestParam("file") MultipartFile file) {
        try {
            stockService.importStockData(file);
            return ResponseEntity.ok().body("Stock data imported successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error importing stock data");
        } catch (StockNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/export/{accountId}")
    public ResponseEntity<byte[]> exportStocksToExcel(@PathVariable Long accountId) {
        try {
            // Appeler la méthode du service pour exporter les stocks liés au compte vers Excel
            byte[] excelData = stockService.exportStocksByCompteToExcel(accountId);

            // Définir les en-têtes de la réponse pour indiquer le type de contenu et le nom de fichier
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "stock_history.xlsx"); // Nom du fichier Excel

            // Retourner une réponse OK avec les données du fichier Excel en tant que corps de la réponse
            return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
        } catch (IOException e) {
            // Retourner une réponse avec erreur 500 en cas d'échec de l'export
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error exporting stocks data".getBytes());
        }
    }


}
