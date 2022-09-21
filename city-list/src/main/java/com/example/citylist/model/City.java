package com.example.citylist.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = City.TABLE_NAME)
@DynamicUpdate
public class City {

    public static final String TABLE_NAME = "CITY";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // database ID (auto-increment)

    @Column(nullable = false)
    private String name;
    private String photoUrl;
}
