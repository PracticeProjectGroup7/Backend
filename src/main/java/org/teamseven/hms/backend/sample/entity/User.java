package org.teamseven.hms.backend.sample.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;

@Table(name = "users")
@SQLDelete(sql = "UPDATE patients SET is_active = true WHERE id=?")
@Where(clause = "is_active = true")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private String password;

    @NotNull
    private Date dateOfBirth;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    @Column(unique = true)
    private String phoneNumber;

    @NotNull
    @Column(unique = true)
    private String icDocument;


    @NotNull
    private String address;

    @NotNull
    @Column(columnDefinition = "VARCHAR(10)")
    @Enumerated(EnumType.STRING)
    private UserGender gender;

    @NotNull
    @Column(columnDefinition = "VARCHAR(20)")
    @Enumerated(EnumType.STRING)
    private AccountType type;

    @NotNull
    private boolean isActive = true;

    @NotNull
    private OffsetDateTime createdAt = OffsetDateTime.now();

    private OffsetDateTime modifiedAt;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIcDocument() {
        return icDocument;
    }

    public void setIcDocument(String icDocument) {
        this.icDocument = icDocument;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserGender getGender() {
        return gender;
    }

    public void setGender(UserGender gender) {
        this.gender = gender;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(OffsetDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}