package com.example.ecommerce.Service;
import com.example.ecommerce.Model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class CategoryService {
ArrayList<Category> categorys = new ArrayList<>();

//get
    public ArrayList<Category> getCategory() {
        return categorys;
    }

    //add
    public void addCategory(Category category) {
        categorys.add(category);
    }

    //update
    public boolean updateCategory(String id , Category category){
        for (int i = 0; i < categorys.size(); i++) {
            if(categorys.get(i).getId().equals(id)){
                categorys.set(i, category);
                return true;
            }
        }
        return false;
    }

    //delete
    public boolean deleteCategory(String id){
        for (int i = 0; i < categorys.size(); i++) {
            if(categorys.get(i).getId().equals(id)){
                categorys.remove(i);
                return true;
            }
        }
        return false;
    }

    //get by id
    public Category categoryExit(String id) {
        for (Category category : categorys) {
            if (category.getId().equals(id)) {
                return category;
            }
        }
        return null;
    }



}