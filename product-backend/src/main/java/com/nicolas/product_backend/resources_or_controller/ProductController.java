package com.nicolas.product_backend.resources_or_controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nicolas.product_backend.models.Product;

import jakarta.annotation.PostConstruct;

import java.security.cert.CertPathValidatorException.Reason;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

//https://learn.microsoft.com/pt-br/azure/architecture/best-practices/api-design
//restfull api design 
//restfull api bestPratices 

//ENDPOINT NÃO PODE SER VERBO
//TENTAR SER SEMPRE NO PLURAL

//ctrl+ k solta +z -> modo zen do vsCode
@RestController
public class ProductController {

    // private List<Product> products = new ArrayList<>();
    private List<Product> products = Arrays.asList(new Product(1, "Arroz", 100.50),
            new Product(2, "Feijão", 200.50),
            new Product(3, "Picanha", 300.50));

    // após a construção do objeto esse método é chamado. Tipo inicializar
    // as variaveis no método construtor, mas tem que colocar essar diretiva
    // postConstruct
    // @PostConstruct
    // public void init() {

    // Product p1 = new Product(1, "Arroz", 100.50);
    // products.add(p1);

    // Product p2 = new Product(2, "Feijão", 200.50);
    // products.add(p2);

    // Product p3 = new Product(3, "Picanha", 300.50);
    // p3.setId(3);
    // p3.setName("Picanha");
    // p3.setPrice(300.50);
    // products.add(p3);
    // }
    // um endpoint nunca pode deixar vazar um erro 500, é um erro da aplicação

    // @PathVariable já é auto explicativo
    @GetMapping("products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        // if (id <= products.size())
        // return ResponseEntity.ok(products.get(id - 1));
        // else {
        // // assim retorna um 404
        // throw new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found");
        // // apache tomCAT
        // // TEM QUE IR NO ARQUIVO DE PROPRIEDADES
        // // Configuração de erro para não aparecer um monte de coisa
        // // server.error.include-stacktrace=never
        // // return ResponseEntity.notFound().build();
        // }

        // stream tem métodos que não tem na classe list
        // programação funcional
        Product prod = products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));
        return ResponseEntity.ok(prod);

    }

    // aqui ctrl+.
    @GetMapping("products")
    public List<Product> getProducts() {
        return products;
    }

}
