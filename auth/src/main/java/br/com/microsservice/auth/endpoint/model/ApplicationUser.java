package br.com.microsservice.auth.endpoint.model;

import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ApplicationUser implements Serializable {

    @Id
    @SequenceGenerator(
            name = "user_id",
            sequenceName = "user_id"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_id"
    )
    @EqualsAndHashCode.Include
    private Long id;
    @NotBlank(message = "This field 'username' cannot be empty")
    @Column(unique = true, nullable = false)
    private String username;
    @NotBlank(message = "This field 'password' cannot be empty")
    @Column(nullable = false)
    @ToString.Exclude
    private String password;
    @NotBlank(message = "This field 'role' cannot be empty")
    @Column(nullable = false)
    private String role = "USER";

    public ApplicationUser(@NotNull ApplicationUser applicationUser) {
        this.id = applicationUser.getId();
        this.username = applicationUser.getUsername();
        this.password = applicationUser.getPassword();
        this.role = applicationUser.getRole();
    }
}
