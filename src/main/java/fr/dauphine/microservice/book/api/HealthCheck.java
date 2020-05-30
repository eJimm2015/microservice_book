package fr.dauphine.microservice.book.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthcheck")
public class HealthCheck {

        @GetMapping
        public ResponseEntity<String> healthcheck(){
            return ResponseEntity.ok("Knock knock Neo, The Matrix has you ...");
        }
}
