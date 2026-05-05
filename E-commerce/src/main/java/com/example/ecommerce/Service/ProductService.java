package com.example.ecommerce.Service;

import com.example.ecommerce.Model.Category;
import com.example.ecommerce.Model.MerchantStock;
import com.example.ecommerce.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ProductService {
    private final CategoryService categoryService;
    private final MerchantService merchantService;

    ArrayList<Product> products = new ArrayList<>();

    public ArrayList<Product> getProduct() {
        return products;
    }


    public int addProduct(Product product) {
        Category category = categoryService.categoryExit(product.getCategoryID());
        if (category == null) {
            return 0;
        }
        Product product1 = productExist(product.getId());
        if (product1 != null) {
            return -1;// product already exists
        }
        products.add(product);
        return 1;
    }


    public boolean updateProduct(String id, Product product) {
        Category category = categoryService.categoryExit(product.getCategoryID());
        if (category == null) {
            return false;
        }
        Product product1 = productExist(product.getId());
        if (product1 == null) {
            return false;
        }
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                products.set(i, product);
                return true;
            }
        }
        return false;
    }


    public boolean deleteProduct(String id) {
        for (Product p : products) {
            if (p.getId().equals(id)) {
                products.remove(p);
                return true;
            }
        }
        return false;
    }

    // method for add MerchantStockController
    public Product productExist(String id) {
        for (Product p : products) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    // extra point 1. get product by category
    public List<Product> getByCategory(String categoryId) {
        List<Product> byCategory = new ArrayList<>();
        for (Product p : products) {
            if (p.getCategoryID().equals(categoryId)) {
                byCategory.add(p);
            }
        }
        return byCategory;
    }

    //extra point 2.  search product by name
    public ArrayList<Product> searchByProduct(String name) {
        ArrayList<Product> products1 = new ArrayList<>();
        for (Product p : products) {
            if (p.getName().equalsIgnoreCase(name)) {
                products1.add(p);
            }
        }
        return products1;
    }

    //extra point 4. filter by price
    public List<Product> byPrice(int min, int max) {
        List<Product> products1 = new ArrayList<>();
        for(Product p : products){
            if (p.getPrice() >= min && p.getPrice() <= max) {
                products1.add(p);
            }
          }
        return products1;
    }

    
    //extra endpoint 5. total price
    public List<Product> totalPrice(int price){
        List<Product> totalPrice = new ArrayList<>();
        for(Product p : products){
            double taxP= p.getPrice()+(p.getPrice()*0.15);
            if (taxP <= price) {
                totalPrice.add(p);
            }
            }
        return totalPrice;
        }

    // 8 static system
        public int getUsersCount(UserService userService) {
            return userService.getUsers().size();
        }
    public int getProductsCount() {
        return products.size();
    }

    public int getTotalStock(MerchantStockService merchantStockService) {
        int total = 0;
        for (MerchantStock m : merchantStockService.getMerchantStocks()) {
            total += m.getStock();
        }
        return total;
    }

    //9 number of product inside category
    public int countProductsByCategory(String categoryId) {
        int count = 0;
        for (Product p : products) {
            if (p.getCategoryID().equals(categoryId)) {
                count++;
            }
        }
        return count;
    }

    //10 Expensive product
    public Product maxPrice() {
        if (products.isEmpty()) {
            return null;
        }
        Product max = products.get(0);
        for (Product p : products) {
            if (p.getPrice() > max.getPrice()) {
                max = p;
            }
        }
        return max;
    }

}
