package com.fertilizeo.service;


import com.fertilizeo.entity.Compte;
import com.fertilizeo.entity.Produit;
import com.fertilizeo.entity.Stock;
import com.fertilizeo.entity.StockExportDTO;
import com.fertilizeo.repository.StockRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final ProductService productService;

    private  final CompteService compteService;



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
           produit=productService.getProduitById(id);
          Optional<Stock> stockOptional= stockRepository.findByProduit(produit);
          if (stockOptional.isPresent()){
              Stock stock= stockOptional.get();
              return stock;
          }
           else {
              throw new StockNotFoundException("Aucun stock trouvé pour le produit");
          }

    }

    public List<Stock> getAllStocksByCompte(Long compteId) {
        return stockRepository.findByCompteIdcompte(compteId);
    }

    public Optional<Stock> getStockByProduitId(Long produitId) {
        return stockRepository.findByProduitIdproduit(produitId);
    }

    public void saveOrUpdateStock(Stock stock) {
        stockRepository.save(stock);
    }

    public void exportStocksToExcel(List<StockExportDTO> stockExportDTOs) throws IOException {
        // Création d'un nouveau classeur Excel
        Workbook workbook = new XSSFWorkbook();
        // Création d'une nouvelle feuille dans le classeur
        Sheet sheet = workbook.createSheet("Stocks");

        // Création de l'en-tête
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Product ID");
        headerRow.createCell(1).setCellValue("Product Name");
        headerRow.createCell(2).setCellValue("Account Name");
        headerRow.createCell(3).setCellValue("Quantity");

        // Remplissage des données
        int rowNum = 1;
        for (StockExportDTO stockExportDTO : stockExportDTOs) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(stockExportDTO.getProductId());
            row.createCell(1).setCellValue(stockExportDTO.getProductName());
            row.createCell(2).setCellValue(stockExportDTO.getAccountName());
            row.createCell(3).setCellValue(stockExportDTO.getQuantity());
        }

        // Écriture du contenu dans un fichier
        String filePath = "C:/Users/inclu/Downloads/stock_export.xlsx";
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
        }
    }

    public void exportStocksByCompteToExcel(Long accountId) throws IOException {
        // Récupérer tous les stocks liés au compte
        List<Stock> stocks = stockRepository.findByCompteIdcompte(accountId);

        // Liste pour stocker les données d'export
        List<StockExportDTO> stockExportDTOs = new ArrayList<>();

        // Boucler sur les stocks récupérés
        for (Stock stock : stocks) {
            // Obtenir le nom du produit, l'ID du produit et le nom du compte
            String productName = getProductNameById(stock.getProduit().getIdproduit());
            long productId = stock.getProduit().getIdproduit();
            String accountName = getAccountNameById(stock.getCompte().getIdcompte());

            // Créer un objet DTO d'export avec les informations nécessaires
            StockExportDTO stockExportDTO = new StockExportDTO( productName, accountName, stock.getQuantity(),productId);

            // Ajouter l'objet DTO à la liste
            stockExportDTOs.add(stockExportDTO);
        }

        // Exporter les données vers Excel
        exportStocksToExcel(stockExportDTOs);
    }

    public String getProductNameById(Long id) {
        Produit produit = productService.getProduitById(id);
        return produit.getName(); // Supposons que vous ayez une méthode pour obtenir le nom du produit
    }

    public String getAccountNameById(Long id) {
        Optional<Compte> compteOptional = compteService.findById(id);
        if(compteOptional.isPresent()){
            Compte compte= compteOptional.get();
            return compte.getName();
        }
       else {
            return "Non du produit non connu";
        }
    }

}




