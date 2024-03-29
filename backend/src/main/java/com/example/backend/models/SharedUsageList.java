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
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class SharedUsageList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sharedusage_ads",
            joinColumns = {@JoinColumn(name = "shared_usage_list_id")},
            inverseJoinColumns = {@JoinColumn(name = "ads_id")})
    private List<Advertisement> advertisements;

    public SharedUsageList(User user, List<Advertisement> products) {
        this.user = user;
        this.advertisements = products;
    }
}
