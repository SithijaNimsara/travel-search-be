package com.sithija.travelsearch.entity;

import lombok.*;

import javax.persistence.*;

@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Gallery {

    @Id
    @Column(name="gallery_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int galleryId;

    @Lob
    private byte[] image;;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User userId;

}
