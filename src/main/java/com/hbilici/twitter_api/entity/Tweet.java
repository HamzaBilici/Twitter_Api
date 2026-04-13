package com.hbilici.twitter_api.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Table(name = "tweet",schema = "public")
@Entity

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 280)
    private String content;

    @NotNull
    @CreationTimestamp
    private LocalDateTime date;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Like> likes = new HashSet<>();

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Retweet> retweets = new HashSet<>();

}
