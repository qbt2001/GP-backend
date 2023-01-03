package com.example.grouppurchase_backend.Entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    @Column(name = "order_id")
    private int order_id;

    @Column(name = "pay_time")
    private String pay_time;

    //@ManyToOne(fetch = FetchType.EAGER)
    @Column(name = "group_id")
    private int group_id;

    //@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @Column(name = "commodity_id")
    private int commodity_id;

    //@ManyToOne(fetch = FetchType.EAGER)
    @Column(name = "user_id")
    private int  user_id;

    @Column(name = "commodity_amount")
    private int commodity_amount;

    @Column(name = "money")
    private int money;

    @Column(name = "post")
    private String post;

    @Column(name = "ispaid")
    private int ispaid;
}