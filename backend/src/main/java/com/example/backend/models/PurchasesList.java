package com.example.backend.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchasesList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "purchases_ads",
            joinColumns = {@JoinColumn(name = "purchases_list_id")},
            inverseJoinColumns = {@JoinColumn(name = "ads_id")})
    private List<Advertisement> advertisements;

    public PurchasesList(User user, List<Advertisement> products) {
        this.user = user;
        this.advertisements = products;
    }
}
