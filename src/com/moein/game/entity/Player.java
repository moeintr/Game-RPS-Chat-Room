package com.moein.game.entity;

import com.moein.game.common.DataTypes;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "player")
@Table(name = "player")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Player implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer playerId;

    @Column(columnDefinition = DataTypes.NVARCHAR_100)
    private String playerName;

    @Enumerated(EnumType.STRING)
    private GameMove gameMove;

    @ManyToOne
    @JoinColumn(name="username")
    private User user;
}
