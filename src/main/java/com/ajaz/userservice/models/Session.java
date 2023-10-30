package com.ajaz.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Session extends BaseModel{
    private Date expiryAt;
    @Enumerated(EnumType.ORDINAL)
    private SessionStatus sessionStatus;
    private String token;

    @ManyToOne
    private User user;

}
