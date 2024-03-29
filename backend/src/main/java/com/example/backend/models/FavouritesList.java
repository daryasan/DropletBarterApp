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
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "favourites_list")
@Data
@NoArgsConstructor
public class FavouritesList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "favs_ads",
            joinColumns = {@JoinColumn(name = "fav_list_id")},
            inverseJoinColumns = {@JoinColumn(name = "ads_id")})
    private List<Advertisement> advertisements;

    public FavouritesList(User user, List<Advertisement> products) {
        this.user = user;
        this.advertisements = products;
    }
}
