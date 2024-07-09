package dev.patika.library_rest_api.business.abstracts;

import dev.patika.library_rest_api.entities.Publisher;
import org.springframework.data.domain.Page;

public interface IPublisherService {

    Publisher save(Publisher publisher);

    Publisher get(int id);

    Page<Publisher> cursor(int page, int pageSize);

    Publisher update(Publisher publisher);

    boolean delete(int id);

}
