package com.sithija.travelsearch.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.sql.Blob;

public class Hotel {
    @Id
    @Column
    private int hotelId;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String telephone;

    @Column
    private String password;

    @Column
    private String address;

    @Column
    private String state;

    @Column
    private String country;

    @Column
    private int type;

    @Column
    private int rating;

    @Lob
    private Blob image;
}
