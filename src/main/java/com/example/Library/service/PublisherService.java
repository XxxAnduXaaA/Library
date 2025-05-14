    package com.example.Library.service;//package com.example.library.service;

    import com.example.Library.entity.Publisher;
    import com.example.Library.repository.PublisherRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import java.util.List;
    import java.util.Optional;

    @Service
    public class PublisherService {

        private final PublisherRepository publisherRepository;

        public PublisherService(PublisherRepository publisherRepository) {
            this.publisherRepository = publisherRepository;
        }

        public List<Publisher> getAllPublishers() {
            return publisherRepository.findAll();
        }

        public Optional<Publisher> getPublisherById(Long id) {
            return publisherRepository.findById(id);
        }

        public Publisher createPublisher(Publisher publisher) {

            publisherRepository.findByName(publisher.getName()).ifPresent(existingPublisher -> {
                throw new RuntimeException("Такой издатель уже существует");
            });

            return publisherRepository.save(publisher);
        }

        public Publisher updatePublisher(Long id, Publisher updatedPublisher) {
            return publisherRepository.findById(id).map(publisher -> {
                publisher.setName(updatedPublisher.getName());
                return publisherRepository.save(publisher);
            }).orElseThrow(() -> new RuntimeException("Publisher not found"));
        }

        public void deletePublisher(Long id) {
            publisherRepository.deleteById(id);
        }
    }
