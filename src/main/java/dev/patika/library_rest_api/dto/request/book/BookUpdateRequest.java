package dev.patika.library_rest_api.dto.request.book;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookUpdateRequest {

    @Positive
    private int id;

    @NotNull
    private String name;

    private int publicationYear;
    private int stock;

    private int authorId;
    private int publisherId;
    private int categoryId;

}
