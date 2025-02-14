package org.example.service;

import org.example.dao.*;
import org.example.model.*;
import org.example.model.entities.Adversity;
import org.example.model.tiles.Corridor;
import org.example.model.tiles.ExitTile;
import org.example.model.tiles.Wall;

import java.util.List;
import java.util.stream.Collectors;

public class GameSessionService {
    private final GameSessionDAO gameSessionDAO = new GameSessionDAO();
    private final MazeDAO mazeDAO = new MazeDAO();
    private final TileDAO tileDAO = new TileDAO();
    private final MazeService mazeService = new MazeService();
    private final ScoringService scoringService = new ScoringService();
    public static final int EXIT_REACHED = 1;

    public GameSession startNewMinimaze(DifficultyLevel difficulty,Profile profile) {
       ProfileDAO profileDAO = new ProfileDAO();

        // ✅ Verifica se il profilo è già salvato
        Profile existingProfile = profileDAO.findByUsername(profile.getUsername());
        if (existingProfile == null) {
            profileDAO.save(profile); // ✅ Salva il profilo se non esiste
            existingProfile = profileDAO.findByUsername(profile.getUsername()); // Ricarica l'oggetto salvato
        }

        //Generazione del minimaze
        Maze maze = mazeService.generateRandomMaze(difficulty);
        Tile startTile = mazeService.getStartTile(maze);

        //Previsualizzazione del minimaze
        previewMaze(maze);


        //Creiamo la sessione di gioco
        GameSession gameSession = new GameSession(existingProfile, maze, startTile, 60);
        gameSessionDAO.save(gameSession);

        System.out.println("✅ Il gioco inizia ora con la visione limitata!");

        return gameSession;
    }


    // verificare se il giocatore incontra un ostacolo o un nemico.
    public void checkTileEffects(GameSession gameSession, Player player) {
        Maze maze = gameSession.getMaze();
        int playerX = player.getX();
        int playerY = player.getY();


        // Utilizziamo il nuovo metodo che restituisce solo adversities non attivate
        List<Adversity> adversities = new AdversityDAO().findAllActiveByMaze(maze.getId())
                .stream()
                .filter(a -> a.getX() == playerX && a.getY() == playerY)
                .collect(Collectors.toList());

        for (Adversity adversity : adversities) {
            // Applica l'effetto solo se non è già stato attivato
            adversity.triggerEffect(player);
            adversity.setActivated(true);

            // Aggiorna la posizione del giocatore in base all'effetto, se necessario
            gameSession.setCurrentTile(
                    tileDAO.findTileByPosition(player.getX(), player.getY(), maze.getId()));
            gameSessionDAO.update(gameSession);

            System.out.println("🎭 Effetto applicato: " + adversity.getAdversityType());
        }
    }

    public int movePlayer(GameSession gameSession, String direction) {


        Tile currentTile = gameSession.getCurrentTile();
        Player player = gameSession.getPlayer(); // ✅ Recuperiamo il giocatore dalla sessione



        Tile newTile = null;
        // Leggiamo il comando inserito per muoverci
        switch (direction.toUpperCase()) {
            case "W": //SU
                newTile = tileDAO.findTileByPosition(player.getX(), player.getY() - 1, gameSession.getMaze().getId());
                break;
            case "S": // GIU
                newTile = tileDAO.findTileByPosition(player.getX(), player.getY() + 1, gameSession.getMaze().getId());
                break;
            case "A": // SX
                newTile = tileDAO.findTileByPosition(player.getX() - 1, player.getY(), gameSession.getMaze().getId());
                break;
            case "D": // DX
                newTile = tileDAO.findTileByPosition(player.getX() + 1, player.getY(), gameSession.getMaze().getId());
                break;
            default:
                System.out.println("[DEBUG] Comando non valido: " + direction);
                System.out.println("❌ Direzione non valida. Usa: W, A, S, D.");
                return -1;
        }

        // Se è null significa che il giocatore si voleva muovere in una posizione fuori labirinto
        if (newTile == null) {
            System.out.println("[DEBUG] ❌ Movimento non consentito! Sei fuori dai confini del labirinto.");
            return -1; // movimento non valido
        }

        //Se siamo sul muro non ci si sposta
        if (newTile instanceof Wall) {
            System.out.println("[DEBUG] 🚧 Hai colpito un muro! Non puoi passare.");
            return -1; // movimento non valido
        }



        // Se il giocatore raggiunge l'uscita, aggiorniamo la posizione e restituiamo un flag
        if (newTile instanceof ExitTile) {
            player.setPosition(newTile.getX(), newTile.getY());
            gameSession.setCurrentTile(newTile);
            gameSessionDAO.update(gameSession);
            return EXIT_REACHED;
        }

        // ✅ Aggiorna la posizione e lo storico del giocatore movimento valido
        player.setPosition(newTile.getX(), newTile.getY());
        gameSession.setCurrentTile(newTile);
        gameSessionDAO.update(gameSession);



        //controlliamo se ci sono effetti da applicare
        checkTileEffects(gameSession, player);
        return -1; // movimento valido ma minimaze non completato

    }

    public void regenerateMaze(GameSession gameSession) {
        System.out.println("🔄 Rigenerazione del minimaze in corso...");

        // Generiamo un nuovo labirinto con la stessa difficoltà
        Maze newMaze = mazeService.generateRandomMaze(gameSession.getMaze().getDifficulty());
        Tile newStartTile = mazeService.getStartTile(newMaze);

        // Mostriamo la previsualizzazione del nuovo labirinto
        previewMaze(newMaze);

        // ✅ Reimpostiamo il giocatore nella nuova posizione di partenza
        gameSession.getPlayer().setPosition(newStartTile.getX(), newStartTile.getY());
        gameSession.setMaze(newMaze);
        gameSession.setCurrentTile(newStartTile);

        // ✅ Aggiorniamo il timer della sessione
        gameSession.setTimeRemaining(60);
        gameSessionDAO.update(gameSession);

        System.out.println("✅ Nuovo minimaze pronto! Il gioco riprende con la visione limitata.");
    }



    public void displayLimitedView(GameSession gameSession) {
        mazeService.displayLimitedView(gameSession.getMaze(), gameSession.getCurrentTile().getX(), gameSession.getCurrentTile().getY());
    }

    public void previewMaze(Maze maze) {
        System.out.println("🔍 Visualizzazione completa del labirinto per " + maze.getDifficulty().getPreviewTime() + " secondi:");

        // Mostriamo il labirinto intero
        mazeService.displayMaze(maze);

        try {
            Thread.sleep(maze.getDifficulty().getPreviewTime() * 1000); // Attesa per la previsualizzazione
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("⏳ Previsualizzazione terminata, il gioco sta per iniziare...");
    }

    public int computeStars(long timeTakenSeconds) {
        return scoringService.computeStars(timeTakenSeconds);
    }


}
