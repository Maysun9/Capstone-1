package com.example.ecommerce.Service;

import com.example.ecommerce.Model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ProductService productService;
    ArrayList<User> users = new ArrayList<>();

    //get
    public ArrayList<User> getUsers(){
        return users;
    }

    //add
    public void addUser(User user){
        users.add(user);
    }

    //update
    public boolean updateUser(String id, User user) {

        for (int i = 0; i < users.size(); i++) {

            if (users.get(i).getId().equals(id)) {
                users.set(i, user);
                return true;
            }
        }
        return false;
    }

    //daelet
    public boolean deleteUser(String id) {
        for (User u : users) {
            if (u.getId().equals(id)) {
                users.remove(u);
                return true;
            }
        }

        return false;
    }

    public User userExist(String id){
        for(User u : users){
            if(u.getId().equals(id)){
                users.add(u);
                return u;
            }
        }
        return null;
    }
//history
public List<Product> getPurchaseHistory(String userId) {
    User user = userExist(userId);
    if (user == null) {
        return new ArrayList<>();
    }
    List<Product> history = new ArrayList<>();
    for (String productId : user.getBuyProduct()) {
        Product p = productService.productExist(productId);
        if (p != null) {
            history.add(p);
        }
    }
    return history;
}
}
