package com.example.finalpro.service;

import com.example.finalpro.dao.CustomerDAO;
import com.example.finalpro.entity.Customer;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Setter
public class CustomerService implements UserDetailsService {

    @Autowired
    private CustomerDAO dao;

    public List<Customer> findAll(){return dao.findAll();}

    // 특정 회원의 정보를 출력하는 메소드
    // 1. main.html에서 회원의 선호하는 장르 cateid를 가져오기 위함
    public Customer findByCustid(String custid){
        if(dao.findById(custid).isPresent()){
            return dao.findById(custid).get();
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("사용자 로그인 처리");
        System.out.println("username:"+username);
        Optional<Customer> obj = dao.findById(username);
        UserDetails user = null;
        if(obj.isPresent()) {
            Customer c = obj.get();
            user = User.builder().username(username).password(c.getPwd()).roles(c.getRole()).build();
            System.out.println(user);
        }else {
            throw new UsernameNotFoundException(username);
        }

        return user;
    }

}
