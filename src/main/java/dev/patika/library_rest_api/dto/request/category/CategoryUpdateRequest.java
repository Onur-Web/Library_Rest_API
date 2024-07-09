package dev.patika.library_rest_api.dto.request.category;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryUpdateRequest {

    @Positive
    private int id;

    private String name;
    private String description;

    private int bookId;

}
