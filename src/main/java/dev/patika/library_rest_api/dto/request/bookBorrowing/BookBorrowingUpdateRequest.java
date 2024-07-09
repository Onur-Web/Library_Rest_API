package dev.patika.library_rest_api.dto.request.bookBorrowing;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookBorrowingUpdateRequest {

    @Positive
    private int id;

    @NotNull
    private String borrowerName;

    @Positive
    private int bookId;

}
