package com.nicolas.product_backend.resources_or_controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nicolas.product_backend.models.Product;

import jakarta.annotation.PostConstruct;

import java.net.URI;
import java.security.cert.CertPathValidatorException.Reason;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//https://learn.microsoft.com/pt-br/azure/architecture/best-practices/api-design
//restfull api design 
//restfull api bestPratices 

//ENDPOINT NÃO PODE SER VERBO
//TENTAR SER SEMPRE NO PLURAL

//ctrl+ k solta +z -> modo zen do vsCode
@RestController
@CrossOrigin // proteção do navegador para erro cors
public class ProductController {

    // private List<Product> products = new ArrayList<>();
    private List<Product> products = new ArrayList<>();

    // Arrays.asList(
    // new Product(1, "Product 01", false, false, 1, "Arroz 1", 100.50),
    // new Product(2, "Product 02", true, true, 2, "Arroz 2", 200.50),
    // new Product(3, "Product 03", false, true, 3, "Arroz 3", 300.50),
    // new Product(4, "Product 04", true, false, 4, "Arroz 4", 400.50));

    // int id, String description, boolean promotion, boolean newProduct, int
    // idCategory, String name,
    // double price
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

    @PostMapping("products") // eu vou pegar o body dentro da requisição e vou transformar em produto
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        product.setId(products.size() + 1);
        products.add(product);

        // CTRL . para importar as bibliotecas
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("{id}")
                .buildAndExpand(product.getId())
                .toUri();
        // location é uri para esse recurso. O get dele
        return ResponseEntity.created(location).body(product);
    }

}
