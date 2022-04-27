package com.app.msuser.model.request;

import lombok.Data;

@Data
public class RegisterRequest {

    /*@NotNull @NotBlank @NotEmpty*/
    private String name;

    /*@NotNull @NotBlank @NotEmpty*/
    private String surname;

    /*@Email(regexp = "") @NotNull*/
    private String email;


}
