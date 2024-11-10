package com.moein.game.entity;

import com.moein.game.common.DataTypes;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
@Entity(name = "roles")
@Table(name = "roles")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;
    @Column(columnDefinition = DataTypes.NVARCHAR_100, nullable = false)
    private String username;
    @Column(columnDefinition = DataTypes.VARCHAR_20, name = "role_name")
    private String roleName;
}
