package com.moein.game.entity;

import com.moein.game.common.DataTypes;
import lombok.*;

import javax.persistence.*;
@Entity(name = "game_chat")
@Table(name = "game_chat")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameChat {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameChatId;

    @ManyToOne
    @JoinColumn(name="game_id", nullable = false)
    private Game game;

    @ManyToOne
    @JoinColumn(name="username", nullable = false)
    private User user;

    @Lob
    @Column(columnDefinition = DataTypes.NVARCHAR_MAX, nullable = false)
    private String message;
}
