package com.E_Commerce.config;

import com.E_Commerce.Model.Role;
import com.E_Commerce.Model.User;
import com.E_Commerce.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class UserDataLoader {
    final private UserRepository userRepository;
    @Autowired
    final private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public CommandLineRunner loadUsers(){
        return args -> {
            if(userRepository.count()==0){
                User admin=new User();
                admin.setUsername("admin");
                admin.setPassword(bCryptPasswordEncoder.encode("admin123"));
                admin.setRole(Role.ROLE_ADMIN);

                userRepository.save(admin);

                User customer=new User();
                customer.setUsername("customer");
                customer.setPassword(bCryptPasswordEncoder.encode("customer123"));
                customer.setRole(Role.ROLE_CUSTOMER);

                userRepository.save(customer);

                System.out.println("ADMIN & CUSTOMER USERS ARE CREATED! AND INSERTED INTO USER REPO! ");
            }
        };
    }


}
