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
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
//@Controller
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private static final Object API_KEY = "";
    private static final String PATH_URL = "http://20.120.124.86"; //http://20.120.124.86 //http://localhost:8082
    private final UserService userService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView index (){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }


    @GetMapping("/order")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView newOrder (){
        ModelAndView modelAndView = new ModelAndView();
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        ResponseEntity<ProductRes[]> resResponseEntity = restTemplate.getForEntity(PATH_URL+"/order/products", ProductRes[].class);
        List<ProductRes> productRes = mapper.convertValue(resResponseEntity.getBody(), new TypeReference<List<ProductRes>>() {});
        //System.out.println(productRes);

        modelAndView.setViewName("order");
        modelAndView.getModelMap().addAttribute("productRes",productRes);
        return modelAndView;
    }


    @GetMapping("/order-history")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView orderHistory (){
        ModelAndView modelAndView = new ModelAndView();
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        ResponseEntity<OrderRes[]> resResponseEntity = restTemplate.getForEntity(PATH_URL+"/order", OrderRes[].class);
        List<OrderRes> orderRes = mapper.convertValue(resResponseEntity.getBody(), new TypeReference<List<OrderRes>>() {});
        //System.out.println(orderRes);

        modelAndView.setViewName("orders");
        modelAndView.getModelMap().addAttribute("orderRes",orderRes);
        return modelAndView;
    }


    @PostMapping("/post-order")
    @ResponseStatus(HttpStatus.CREATED)
    public ModelAndView createOrder(OrderReq orderReq, @RequestParam String name, @RequestParam String address, @RequestParam BigDecimal price){
        orderReq.setName(name);
        orderReq.setPrice(price);
        orderReq.setUserId("1");
        orderReq.setAddress(address);

        RestTemplate restTemplate = new RestTemplate();

        //System.out.println(orderReq);

        ResponseEntity<OrderReq> result = restTemplate.postForEntity(PATH_URL + "/order", orderReq, OrderReq.class);

        // Get time info
        String time = getTime();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("neworder");
        modelAndView.getModelMap().addAttribute("time",time);
        return modelAndView;
    }

    @GetMapping("/location")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView getLocation(){

        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("maps.googleapis.com")
                .path("/maps/api/geocode/json")
                .queryParam("address", "Vilharjeva 1,ljubljana,SI")
                .queryParam("key", API_KEY)
                .build();

        ResponseEntity<GeoResponse> geoResponse = new RestTemplate().getForEntity(uri.toUriString(), GeoResponse.class);

        GeoResponse response = geoResponse.getBody();

        String address = response.getResult()[0].getAddress();
        double lat = response.getResult()[0].getGeometry().getLocation().getLat();
        double lng = response.getResult()[0].getGeometry().getLocation().getLng();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("location");
        modelAndView.getModelMap().addAttribute("address",address);
        modelAndView.getModelMap().addAttribute("lat",lat);
        modelAndView.getModelMap().addAttribute("lng",lng);

        return modelAndView;
    }


    @GetMapping("/dist")
    @ResponseStatus(HttpStatus.OK)
    public String getTime() {

        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("maps.googleapis.com")
                .path("/maps/api/directions/json")
                .queryParam("origin", "Večna pot 113,ljubljana,SI")
                .queryParam("destination", "Vilharjeva 1,ljubljana,SI")
                .queryParam("key", API_KEY)
                .build();
        ResponseEntity<DistResponse> distResponse = new RestTemplate().getForEntity(uri.toUriString(), DistResponse.class);

        DistResponse response = distResponse.getBody();

        String time = response.getRoutes()[0].getLegs()[0].getDuration().getText();

        return time;
    }
}