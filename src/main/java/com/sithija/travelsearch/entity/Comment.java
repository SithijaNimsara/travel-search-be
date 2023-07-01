package com.sithija.travelsearch.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.Timestamp;

@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {

    @Id
    @Column(name="comment_id")
    private int commentId;

    @Column
    private String comment;

    @Column(insertable = false)
    @CreationTimestamp
    private Timestamp time;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User userId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post postId;

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", comment='" + comment + '\'' +
                ", time=" + time +
                ", userId=" + userId +
                ", postId=" + postId +
                '}';
    }
}
