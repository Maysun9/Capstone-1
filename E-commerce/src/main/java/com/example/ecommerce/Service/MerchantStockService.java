package com.example.ecommerce.Service;

import com.example.ecommerce.Model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
@RequiredArgsConstructor
public class MerchantStockService {
    private final ProductService productService;
    private final MerchantService merchantService;
    private final UserService userService;

    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();

    //get
    public ArrayList<MerchantStock> getMerchantStocks() {
        return merchantStocks;
    }

    //add test
    public int addMerchantStock(MerchantStock merchantStock) {
        Product product = productService.productExist(merchantStock.getProductId());
        if (product == null) {
            return -1; //for check
        }
        Merchant merchant = merchantService.merchantExist(merchantStock.getMerchantId());
        if (merchant == null) {
            return -2; // for check
        }
        merchantStocks.add(merchantStock);
        return 1; // for added if everythings ok
    }

    //update
    public boolean updateMerchantStocks(String id, MerchantStock merchantStock) {
        Product product = productService.productExist(merchantStock.getProductId());
        if (product == null) {
            return false;
        }
        Merchant merchant = merchantService.merchantExist(merchantStock.getMerchantId());
        if (merchant == null) {
            return false;
        }
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equals(id)) {
                merchantStocks.set(i, merchantStock);
                return true;
            }
        }
        return false;
    }
//        for (int i = 0; i < merchantStocks.size(); i++) {
//            if (merchantStocks.get(i).getId().equals(id)) {
//                merchantStocks.set(i, merchantStock);
//                return true;
//            }
//        }
//        return false;
//    }

    //delete
    public boolean deletMerchantStocks(String id) {
        for (MerchantStock m : merchantStocks) {
            if (m.getId().equals(id)) {
                merchantStocks.remove(m);
                return true;
            }
        }
        return false;
    }

    // 11. merchant add more stocks of product to a merchant stock
    public boolean addStock(String merchantId, String productId, int addStock) {
        for (MerchantStock m : merchantStocks) {
            //if(m.getProductId() == productId && m.getMerchantId()==merchantId){
            if (m.getProductId().equals(productId) && m.getMerchantId().equals(merchantId)) {
                m.setStock(m.getStock() + addStock);
                return true;
            }
        }
        return false;
    }


    public MerchantStock getStock(String productId, String merchantId) {
        for (MerchantStock m : merchantStocks) {
            if (m.getProductId().equals(productId) && m.getMerchantId().equals(merchantId)) {
                return m;
            }
        }
        return null;
    }

    //12. user can buy product directly

    public int buyProduct(String userId, String productId, String merchantId) {
        User user = userService.userExist(userId);
        if (user == null) {
            return 0;
        }       //dose break needed it here in test must check it
        Product product = productService.productExist(productId);
        if (product == null) {
            return -1;
        }
        Merchant merchant = merchantService.merchantExist(merchantId);
        if (merchant == null) {
            return -2;
        }
        MerchantStock stock = getStock(productId, merchantId);
        if (stock == null || stock.getStock() <= 0) {
            return -3;
        }
        if (user.getBalance() < product.getPrice()) {
            return -4;
        }
        stock.setStock(stock.getStock() - 1);
        user.setBalance(user.getBalance() - product.getPrice());
        return 1;
    }

    //extra point 3. get product by merchant
    public ArrayList<Product> productByMerchant(String merchantId) {
        ArrayList<Product> p = new ArrayList<>();
        for (MerchantStock m : merchantStocks) {
            if (m.getMerchantId().equals(merchantId)) {
                Product product = productService.productExist(m.getProductId());
                if (product != null) {
                    p.add(product);
                }
            }
        }
        return p;
    }

    //extra endpoint 6. get stock  by product id
    public int getStockByProductId(String productId) {
        for (MerchantStock m : merchantStocks) {
            if (m.getProductId().equals(productId)) {
                return m.getStock();
            }
        }
        return -1;
    }

    //extra endpoint 7 . rating
    public int rating(String userId, String merchantId, String productId, int rating){
        if(rating<0 || rating>5){
            return -1;
        }
        User user = userService.userExist(userId);
        if (user == null) {
            return -2;
        }
        Merchant merchant = merchantService.merchantExist(merchantId);
        if (merchant == null) {
            return -3;
        }
        Product product = productService.productExist(productId);
        if (product == null) {
            return -4;
        }
        product.setRating(rating); //Rating

        return 1;
    }

    //11. available product in stock
    public ArrayList<Product> availableProducts(int limit) {
        ArrayList<Product> av = new ArrayList<>();
        for (MerchantStock m : merchantStocks) {
            if (m.getStock() > 0) {
                Product product = productService.productExist(m.getProductId());
                if (product != null && !av.contains(product)) {
                    av.add(product);
                }
                if (av.size() == limit) {
                    break;
                }
            }
        }
        return av;

    }
        }



