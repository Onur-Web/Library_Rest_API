package dev.patika.library_rest_api.dto.response.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {

    private int id;
    private String name;
    private int publicationYear;
    private int stock;

}
