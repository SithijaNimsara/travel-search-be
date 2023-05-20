package com.sithija.travelsearch.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post {

    @Id
    @Column(name="post_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int postId;

    @Column
    private String caption;

    @Column(insertable = false)
    @CreationTimestamp
    private Timestamp time;

    @Lob
    private byte[] image;;

    @ManyToOne
    @JoinColumn(name = "hotel_id", referencedColumnName = "user_id")
    private User hotelId;

    @ManyToMany(mappedBy = "userPosts")
    private Set<User> postUsers = new HashSet<>();

}
