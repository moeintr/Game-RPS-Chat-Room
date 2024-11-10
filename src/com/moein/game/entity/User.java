package com.moein.game.entity;

import com.moein.game.common.DataTypes;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @Column(columnDefinition = DataTypes.NVARCHAR_100, nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "username")
    private List<Role> userRoles;
}
