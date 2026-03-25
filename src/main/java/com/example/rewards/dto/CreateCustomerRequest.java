
package com.example.rewards.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateCustomerRequest {
    @NotNull(message = "id is required")
    private Long id;
    @NotBlank(message = "name is required")
    @Size(max = 100, message = "name must be at most 100 chars")
    private String name;
    @Email(message = "email must be valid")
    private String email;
    @Size(max = 20, message = "phone must be at most 20 chars")
    private String phone;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
