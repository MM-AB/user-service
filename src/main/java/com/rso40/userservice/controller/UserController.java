package com.rso40.userservice.controller;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rso40.userservice.dto.*;
import org.springframework.http.HttpStatus;

import com.rso40.userservice.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.naming.NameNotFoundException;

//@RestController
@Controller
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public String index (Model model){
        //ModelAndView modelAndView = new ModelAndView();
        //modelAndView.setViewName("index");
        return "index";
    }

    /*@GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView index (){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }*/

    @GetMapping("/order")
    @ResponseStatus(HttpStatus.OK)
    public String newOrder (Model model){
        //ModelAndView modelAndView = new ModelAndView();
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        ResponseEntity<ProductRes[]> resResponseEntity = restTemplate.getForEntity("http://localhost:8082/order/products", ProductRes[].class);
        List<ProductRes> productRes = mapper.convertValue(resResponseEntity.getBody(), new TypeReference<List<ProductRes>>() {});
        //System.out.println(productRes);

        //modelAndView.setViewName("order");
        model.addAttribute("productRes",productRes);
        return "order";
    }

    /*@GetMapping("/order")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView newOrder (){
        ModelAndView modelAndView = new ModelAndView();
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        ResponseEntity<ProductRes[]> resResponseEntity = restTemplate.getForEntity("http://localhost:8082/order/products", ProductRes[].class);
        List<ProductRes> productRes = mapper.convertValue(resResponseEntity.getBody(), new TypeReference<List<ProductRes>>() {});
        //System.out.println(productRes);

        modelAndView.setViewName("order");
        modelAndView.getModelMap().addAttribute("productRes",productRes);
        return modelAndView;
    }*/

    @GetMapping("/order-history")
    @ResponseStatus(HttpStatus.OK)
    public String orderHistory (Model model){
        //ModelAndView modelAndView = new ModelAndView();
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        ResponseEntity<OrderRes[]> resResponseEntity = restTemplate.getForEntity("http://localhost:8082/order", OrderRes[].class);
        List<OrderRes> orderRes = mapper.convertValue(resResponseEntity.getBody(), new TypeReference<List<OrderRes>>() {});
        //System.out.println(orderRes);

        //modelAndView.setViewName("orders");
        model.addAttribute("orderRes",orderRes);
        return "orders";
    }

    /*@GetMapping("/order-history")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView orderHistory (){
        ModelAndView modelAndView = new ModelAndView();
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        ResponseEntity<OrderRes[]> resResponseEntity = restTemplate.getForEntity("http://localhost:8082/order", OrderRes[].class);
        List<OrderRes> orderRes = mapper.convertValue(resResponseEntity.getBody(), new TypeReference<List<OrderRes>>() {});
        //System.out.println(orderRes);

        modelAndView.setViewName("orders");
        modelAndView.getModelMap().addAttribute("orderRes",orderRes);
        return modelAndView;
    }*/


    //End point
    @PostMapping("/post-order")
    @ResponseStatus(HttpStatus.CREATED)
    public String createOrder(Model model, OrderReq orderReq, @RequestParam String name, @RequestParam String address, @RequestParam BigDecimal price){
        orderReq.setName(name);
        orderReq.setPrice(price);
        orderReq.setUserId("1");
        orderReq.setAddress(address);

        RestTemplate restTemplate = new RestTemplate();

        //System.out.println(orderReq);

        ResponseEntity<OrderReq> result = restTemplate.postForEntity("http://localhost:8082/order", orderReq, OrderReq.class);


        //ModelAndView modelAndView = new ModelAndView();
        //modelAndView.setViewName("neworder");
        return "neworder";
    }

    /*@PostMapping("/post-order")
    @ResponseStatus(HttpStatus.CREATED)
    public ModelAndView createOrder(OrderReq orderReq, @RequestParam String name, @RequestParam String address, @RequestParam BigDecimal price){
        orderReq.setName(name);
        orderReq.setPrice(price);
        orderReq.setUserId("1");
        orderReq.setAddress(address);

        RestTemplate restTemplate = new RestTemplate();

        //System.out.println(orderReq);

        ResponseEntity<OrderReq> result = restTemplate.postForEntity("http://localhost:8082/order", orderReq, OrderReq.class);


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("neworder");
        return modelAndView;
    }*/

}
