package com.example.grouppurchase_backend.Entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Table(name = "commodity")
public class Commodity {
    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    @Column(name = "commodity_id")
    private int commodity_id;

    @Column(name = "commodity_name")
    private String commodity_name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "image_url")
    private String image_url;

    @Column(name = "description")
    private String des;

    @Column(name = "price")
    private int price;

    @Column(name = "inventory")
    private int inventory;

    @Column(name = "miao")
    private boolean miao;

}