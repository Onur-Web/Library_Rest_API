package dev.patika.library_rest_api.dto.request.bookBorrowing;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookBorrowingSaveRequest {

    @NotNull
    private String borrowerName;
    @Email
    private String borrowerEmail;

    private int bookId;

}
