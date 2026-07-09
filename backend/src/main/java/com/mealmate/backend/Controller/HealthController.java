package com.mealmate.backend.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    
    @GetMapping("/api/health")
    public String health(){
        for (int i = 0 ; i < 10 ; i++){
            System.out.println("Health check iteration: " + i);
        }
        return "Meal Mate API is up!";
    }
}
