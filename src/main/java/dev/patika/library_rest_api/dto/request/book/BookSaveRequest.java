package dev.patika.library_rest_api.dto.request.book;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookSaveRequest {

    @NotNull
    private String name;

    private int publicationYear;
    private int stock;

    private int authorId;
    private int publisherId;
    private List<Integer> categoryIds;

}
