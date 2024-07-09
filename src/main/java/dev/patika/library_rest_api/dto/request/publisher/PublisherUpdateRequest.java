package dev.patika.library_rest_api.dto.request.publisher;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublisherUpdateRequest {

    @Positive
    private int id;

    private String name;
    private int establishmentYear;
    private String address;

}
