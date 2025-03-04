package com.example.demo;



import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class Controller {
//TEST PUSH COMPANY BRUNCH 56789
//我在測試衝突


    List<User> users = new ArrayList<User>(){{
        add(new User("LILI",18));
        add(new User("DEVIN",30));
        add(new User("BBBB",7));
    }};


    @GetMapping("/GetUsers")
    public List<User>GetUsers(int _age){

        List<User>  resultList=users.stream()
                .filter(x->x.age==_age).collect(Collectors.toList());
        return  resultList;

    }

    @GetMapping("/GetAge")
    public String GetAge(String _name){
        var xxx="";

        var  resultList=users.stream()
                .filter(x->x.name.equals(_name)).findFirst().get().age;
        return  "年齡:"+resultList;
    }


    @GetMapping("/GetList")
    public List<User> GetList(){

        ObjectMapper json = new ObjectMapper();

        String jsonString = "";
        List<User> jsontoList = new ArrayList<>();

        String jsontoListString="[{\"name\":\"LILI\",\"age\":18},{\"name\":\"DEVIN\",\"age\":30}]";
        try {
            jsonString = json.writeValueAsString(users);
            jsontoList=json.readValue(jsontoListString, new TypeReference<List<User>>(){});
            //WjsontoList.stream().sorted(Comparator.comparing(User::).reversed());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsontoList;
    }


    @PostMapping("/AddUser")
    public String AddUser(@RequestBody User u){

        var getu=users.stream()
                .filter(x->x.name.equals(u.name)).findFirst();
        if (getu.isPresent()){
           return "Duplicated user " + u.name;
        }else{
            users.add(u);
        }


        return  String.valueOf( users.stream().count());
    }



    @RequestMapping(value = "/{name}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public String ModifyUser(@PathVariable String name, @RequestBody User user) {

        Optional<User> getu=users.stream()
                .filter(x->x.name.equals(user.name)).findFirst();

        if (getu.isPresent()){
            User foundUser = getu.get();
            foundUser.age=user.age;
            return "修改" + foundUser.name + "為:" + foundUser.age + " OK";
        }else{
            return "NOT_FOUND " + user.name;
        }


    }

@DeleteMapping("/{name}")
    public List<User> RemoveUser(@PathVariable String name, @RequestBody User user) {
    Optional<User> UserList = users.stream()
            .filter(u -> u.name.equals(user.name))
            .findFirst();

    if (UserList.isPresent()) {

        users.removeIf(u -> u.name.equals(user.name));

    } else {
        return users;
    }

    return users;
}





//    public void UserController(){
//
//        List<User> users=new ArrayList<User>();
//        users.add(new User("AMY",80));
//
//    }



//    @GetMapping("/")
//    public String hello(){
//
//
//        return "hello world";
//    }

}
