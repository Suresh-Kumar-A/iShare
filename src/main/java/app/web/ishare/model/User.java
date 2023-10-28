package app.web.ishare.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(unique = true, nullable = false)
    private String uid;

    @Column(unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    private String displayName;

    @Column(nullable = false)
    private String password;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column
    @Default
    private Boolean locked = false;

    /*
     * If you don't use CascadeType.MERGE you can't add a user in app startup using
     * CommandLineRunner you will get an error : detached entity passed to persist
     * OR just remove the CascadeType
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_role_id")
    private Role role;
}
