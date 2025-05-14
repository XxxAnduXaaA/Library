    package com.example.Library.controller;//package com.example.library.controller;

    import com.example.Library.entity.Publisher;
    import com.example.Library.service.PublisherService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;
    import java.util.Optional;

    @RestController
    @RequestMapping("/api/publishers")
    public class PublisherController {

        @Autowired
        private PublisherService publisherService;

        @GetMapping
        public List<Publisher> getAllPublishers() {
            return publisherService.getAllPublishers();
        }

        @GetMapping("/{id}")
        public ResponseEntity<Publisher> getPublisherById(@PathVariable Long id) {
            Optional<Publisher> publisher = publisherService.getPublisherById(id);
            return publisher.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }

        @PostMapping
        public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher) {
            Publisher newPublisher = publisherService.createPublisher(publisher);
            return ResponseEntity.status(HttpStatus.CREATED).body(newPublisher);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Publisher> updatePublisher(@PathVariable Long id, @RequestBody Publisher publisher) {
            try {
                Publisher updatedPublisher = publisherService.updatePublisher(id, publisher);
                return ResponseEntity.ok(updatedPublisher);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletePublisher(@PathVariable Long id) {
            try {
                publisherService.deletePublisher(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
    }

