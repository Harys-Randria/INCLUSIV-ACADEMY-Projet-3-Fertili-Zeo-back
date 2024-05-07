package com.fertilizeo.service;

import com.fertilizeo.entity.*;
import com.fertilizeo.repository.StockHistoryRepository;
import com.fertilizeo.repository.StockRepository;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Data
@NoArgsConstructor
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CompteService compteService;

    @Autowired
    private StockHistoryRepository stockHistoryRepository;



    // Ajoutez ce constructeur pour injecter ProductService
    public StockService(StockRepository stockRepository, ProductService productService, CompteService compteService) {
        this.stockRepository = stockRepository;
        this.productService = productService;
        this.compteService = compteService;
    }


    public int getQuantiteEnStock(Produit produit) {
        Optional<Stock> stockOption = stockRepository.findByProduit(produit);
        return stockOption.map(Stock::getQuantity).orElse(0);
    }

    public void augmenterStock(Produit produit, Integer quantity) {
        Optional<Stock> stockOption = stockRepository.findByProduit(produit);
        Stock stock = stockOption.orElse(new Stock());
        stock.setProduit(produit);
        stock.setQuantity(stock.getQuantity() + quantity);
        stockRepository.save(stock);
    }

    public void diminuerStock(Produit produit, Integer quantity) {

        Optional<Stock> stockOption = stockRepository.findByProduit(produit);
        Stock stock = stockOption.orElseThrow(() -> new IllegalArgumentException("Stock introuvable"));

        Integer currentquantity = stock.getQuantity();
        if (currentquantity < quantity) {
            throw new IllegalArgumentException("Stock insuffisant");
        }
        stock.setQuantity(currentquantity - quantity);
        stockRepository.save(stock);
    }


    public void reajusterStock(Produit produit) {
        // Logique pour réajuster le stock du produit avant d'effectuer une opération

        // Vérification des seuils de réapprovisionnement
        if (produit.getQuantity() < produit.getSeuilreapprovisionnement()) {
            //  méthode pour réapprovisionner le produit à appeler

        }

        // Autres ajustements spécifiques selon les besoins FERTILI'ZEO
    }

    public void importStockData(MultipartFile file) throws IOException, StockNotFoundException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Ignorer l'en-tête

            Long productId = (long) row.getCell(0).getNumericCellValue(); // ID du produit dans la première colonne
            int quantity = (int) row.getCell(3).getNumericCellValue(); // Quantité dans la quatrième colonne

            // Vérifier si un stock correspondant existe déjà
            Optional<Stock> existingStockOptional = stockRepository.findById(productId);

            if (existingStockOptional.isPresent()) {
                Stock existingStock = existingStockOptional.get();
                existingStock.setQuantity(quantity);
                stockRepository.save(existingStock);
            } else {
                throw new StockNotFoundException("Aucun stock trouvé pour le produit avec l'ID : " + productId);
            }
        }
    }


    public void exportStockData(List<Stock> stockList, String filePath) throws IOException {
        // Créer un nouveau classeur Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Stock");

        // En-tête
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID Stock");
        headerRow.createCell(1).setCellValue("Quantité");
        // Ajoutez d'autres en-têtes si nécessaire

        // Données de stock
        int rowNum = 1;
        for (Stock stock : stockList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(stock.getIdstock());
            row.createCell(1).setCellValue(stock.getQuantity());
            // Ajoutez d'autres données de stock si nécessaire
        }

        // Enregistrer le classeur Excel dans un fichier
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        }

        // Fermer le classeur Excel
        workbook.close();
    }

    public Stock findStockByIdProduit(Long id) throws StockNotFoundException {
        Produit produit = new Produit();
        produit = productService.getProduitById(id);
        Optional<Stock> stockOptional = stockRepository.findByProduit(produit);
        if (stockOptional.isPresent()) {
            Stock stock = stockOptional.get();
            return stock;
        } else {
            throw new StockNotFoundException("Aucun stock trouvé pour le produit");
        }

    }

    public List<Stock> getAllStocksByCompte(Long compteId) {
        return stockRepository.findByCompteIdcompte(compteId);
    }

    public List<StockHistory> getStockHistoryByProductId(Long productId) {
        // Récupérer tous les enregistrements de l'historique de stock associés à un produit spécifique
        Produit produit = new Produit();
        produit.setIdproduit(productId);
        Optional<Stock> stockOptional = stockRepository.findByProduit(produit);
        if (stockOptional.isPresent()) {
            Stock stock = stockOptional.get();
            return stockHistoryRepository.findByStockId(stock.getIdstock());
        } else {
            return Collections.emptyList(); // Retourner une liste vide si aucun stock n'est trouvé pour le produit
        }
    }


    public int getStockQuantityByProductId(Long productId) {
        Optional<Stock> stockOptional = stockRepository.findByProduitIdproduit(productId);
        return stockOptional.map(Stock::getQuantity).orElse(0);
    }

    public void saveOrUpdateStock(Stock stock) {
        stockRepository.save(stock);
    }


    public byte[] exportStocksToExcel(List<StockExportDTO> stockExportDTOs) throws IOException {

        // Création d'un nouveau classeur Excel
        Workbook workbook = new XSSFWorkbook();
        // Création d'une nouvelle feuille dans le classeur
        Sheet sheet = workbook.createSheet("Stocks");

        // Création de l'en-tête
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Date");
        headerRow.createCell(2).setCellValue("Product Name");
        headerRow.createCell(3).setCellValue("Account Name");
        headerRow.createCell(4).setCellValue("Stock Initial");
        headerRow.createCell(5).setCellValue("Stock Final");
        headerRow.createCell(6).setCellValue("Type de Mouvement");
        headerRow.createCell(7).setCellValue("Quantité Changée");

        // Remplissage des données
        int rowNum = 1;
        for (StockExportDTO stockExportDTO : stockExportDTOs) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(stockExportDTO.getProductId());
            row.createCell(1).setCellValue(stockExportDTO.getStockHistory().get(0).getDate().toString());
            row.createCell(2).setCellValue(stockExportDTO.getProductName());
            row.createCell(3).setCellValue(stockExportDTO.getAccountName());
            row.createCell(4).setCellValue(stockExportDTO.getStockInitial());
            row.createCell(5).setCellValue(stockExportDTO.getStockFinal());
            row.createCell(6).setCellValue(stockExportDTO.getMouvementType());
            row.createCell(7).setCellValue(stockExportDTO.getQuantityChanged());
        }

        // Ajuster la largeur des colonnes
        for (int i = 0; i < 8; i++) {
            sheet.autoSizeColumn(i);
        }

        // Créer un flux de sortie pour écrire les données dans un tableau de bytes
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }





    public byte[] exportStocksByCompteToExcel(Long accountId) throws IOException {
        // Récupérer tous les stocks liés au compte
        List<Stock> stocks = stockRepository.findByCompteIdcompte(accountId);

        // Liste pour stocker les données d'export
        List<StockExportDTO> stockExportDTOs = new ArrayList<>();

        // Boucler sur les stocks récupérés
        for (Stock stock : stocks) {
            // Obtenir le nom du produit, l'ID du produit et le nom du compte
            String productName = stock.getProduit().getName();
            long productId = stock.getProduit().getIdproduit();
            String accountName = stock.getCompte().getName();

            // Récupérer l'historique de stock pour ce stock
            List<StockHistory> stockHistoryList = stockHistoryRepository.findByStockId(stock.getIdstock());

            // Vérifier si l'historique de stock est vide
            if (!stockHistoryList.isEmpty()) {
                // Parcourir l'historique de stock pour obtenir les informations de mouvement
                for (StockHistory history : stockHistoryList) {
                    // Créer un objet DTO d'export avec les informations nécessaires
                    StockExportDTO stockExportDTO = new StockExportDTO(
                            productName, accountName, stock.getQuantity(), productId,
                            history.getInitialStock(), history.getFinalStock(),
                            history.getMouvementType(), history.getQuantityChange(), stockHistoryList);

                    // Ajouter l'objet DTO à la liste
                    stockExportDTOs.add(stockExportDTO);
                }
            }
        }

        // Exporter les données vers Excel
        byte[] excelData = exportStocksToExcel(stockExportDTOs);

        return excelData;
    }


    public String getProductNameById(Long id) {
        Produit produit = productService.getProduitById(id);
        return produit.getName(); // Supposons que vous ayez une méthode pour obtenir le nom du produit
    }

    public String getAccountNameById(Long id) {
        Optional<Compte> compteOptional = compteService.findById(id);
        if (compteOptional.isPresent()) {
            Compte compte = compteOptional.get();
            return compte.getName();
        } else {
            return "Non du produit non connu";
        }
    }


    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Long findByProduitIdproduit(Long produitId) {
        return stockRepository.findQuantityByProduitId(produitId);
    }

}
