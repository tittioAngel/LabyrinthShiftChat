package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.model.Player;
import org.example.model.Profile;
import org.example.service.GameService;
//import org.example.service.PlayerService;
import org.example.service.ProfileService;
import org.example.util.MenuUtil;


import java.util.HashMap;

@NoArgsConstructor
@AllArgsConstructor
public class GameController {

    private GameService gameService = new GameService();

    public void startGame(){
        System.out.println("🎮 Benvenuto in LabyrinthShiftChat! 🎮");
        gameService.gameStart();
    }


}
