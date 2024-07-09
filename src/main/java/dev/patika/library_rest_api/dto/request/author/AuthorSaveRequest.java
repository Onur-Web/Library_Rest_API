package dev.patika.library_rest_api.dto.request.author;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorSaveRequest {

    @NotNull
    private String name;

    private int birthDate;
    private String country;

    private int bookId;

}
