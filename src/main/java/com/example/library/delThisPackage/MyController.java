package com.example.library.delThisPackage;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {
    @GetMapping("/checkUser")
    public String checkUser() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal instanceof UserDetails) {
                String username = ((UserDetails)principal).getUsername();
                System.out.println("if");
                System.out.println(username);
            } else {
                String username = principal.toString();
                System.out.println("else");
                System.out.println(username);
            }

            System.out.println("--------------------");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Object principal1 = authentication.getPrincipal();
            System.out.println("Username: " + authentication.getName());
            System.out.println("Authorities: " + authentication.getAuthorities());
            System.out.println("Principal:" + principal1);
            return "del1";
        } catch (Exception e) {
            e.printStackTrace();
            return "del2";
        }
    }
}
//    @GetMapping("/checkUser")
//    public String checkUser() {
//        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication != null && authentication.isAuthenticated()) {
//                System.out.println("User is saved");
//                // Виводимо всі дані користувача в консоль
//                Object principal = authentication.getPrincipal();
//                // Виводимо всі дані користувача в консоль
//                System.out.println("Username: " + authentication.getName());
//                System.out.println("Authorities: " + authentication.getAuthorities());
//                System.out.println(principal);
//                // Можна виконати додаткові дії або перенаправити на іншу сторінку
//                return "del1";
//            } else {
//                System.out.println("User is unsaved");
//                // Користувач не авторизований
//                // Можна перенаправити на сторінку логіну або зробити щось інше
//                return "del2";
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//            return "registration";
//        }
//    }


//Я трішки змінив наш клас та виявив, що проблеми з авторизацією. Ось як тепер виглядає клас:
//package com.example.library.delThisPackage;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//public class MyController {
//    @GetMapping("/checkUser")
//    public String checkUser() {
//        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            Object principal = authentication.getPrincipal();
//            System.out.println("Username: " + authentication.getName());
//            System.out.println("Authorities: " + authentication.getAuthorities());
//            System.out.println(principal);
//            return "del1";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "del2";
//        }
//    }
//}
//Я авторизувався http://localhost:8081/login та перейшов на сторінку користувача qwerty з паролем qwerty, після чого ввів адресу http://localhost:8081/login отримавши таке повідомлення:
//Username: anonymousUser
//Authorities: [ROLE_ANONYMOUS]
//anonymousUser
