package com.example.grouppurchase_backend.Entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "`group`")
public class Group {
    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    @Column(name = "group_id")
    private int group_id;

    @Column(name = "group_name")
    private String group_name;

    @Column(name = "number")
    private int number;

    @Column(name = "group_des")
    private String des;

    @Column(name = "group_post")
    private String post;

    @Column(name = "begin_time")
    private String begin_time;

    @Column(name = "end_time")
    private String end_time;

//    @ManyToOne(fetch = FetchType.EAGER)
    @Column (name = "head_id")
    private int head;

    @Column(name="follower")
    private String follower;

    @Column(name = "link")
    private String link;

}