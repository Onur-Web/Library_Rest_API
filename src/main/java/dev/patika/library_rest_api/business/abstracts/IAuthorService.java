package dev.patika.library_rest_api.business.abstracts;

import dev.patika.library_rest_api.entities.Author;
import org.springframework.data.domain.Page;

public interface IAuthorService {

    Author save(Author author);

    Author get(int id);

    Page<Author> cursor(int page, int pageSize);

    Author update(Author author);

    boolean delete(int id);

}
