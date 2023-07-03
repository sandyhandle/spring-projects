package com.santhosh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.logging.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.toString());

    private final CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args){
        SpringApplication.run(Main.class,args);
    }
    @GetMapping
    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }

    record NewCustomerRequest(
            String name,
            String email,
            Integer age
    ){}
    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request){
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setAge(request.age());
        customer.setEmail(request.email());
//        logger.log(Level.INFO,"email: " + request.email());
        logger.info("summa");
        customerRepository.save(customer);

    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id){
        customerRepository.deleteById(id);
    }

    @PutMapping("{customerId}")
    public void updateCustomerEmail(@RequestBody NewCustomerRequest request,
            @PathVariable("customerId") Integer id){

        Customer customer = customerRepository.findById(id).get();

        customer.setName(request.name());
        customer.setAge(request.age());
        customer.setEmail(request.email());

        customerRepository.save(customer);
    }


//    @GetMapping("/")
//    public GreetResponse greet(){
//        return new GreetResponse(
//                "Hello Santhosh",
//                List.of("Java", "Python", "JavaScript"),
//                new Person("Ranjith", 19, 2_000)
//        );
//    }
//
//    record Person(String name, int age, double savings){}
//
//    record GreetResponse(
//            String greet,
//            List<String> favivorateProgrammingLanguages,
//            Person person
//
//
//    ){}
}
