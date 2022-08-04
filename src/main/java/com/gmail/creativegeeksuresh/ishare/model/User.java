package com.gmail.creativegeeksuresh.ishare.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Column
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Integer sno;

    @Column(unique = true, nullable = false)
    private String uid;

    /**
     * username may be userid or email
     */
    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String emailAddress;

    @Column(nullable = false)
    private String displayName;

    @Column(nullable = false)
    @Default
    private Boolean status = Boolean.TRUE;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @OneToOne(fetch = FetchType.EAGER)
    Role role;

}
