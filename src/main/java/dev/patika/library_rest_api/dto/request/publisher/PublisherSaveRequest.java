package dev.patika.library_rest_api.dto.request.publisher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublisherSaveRequest {

    private String name;
    private int establishmentYear;
    private String address;

}
