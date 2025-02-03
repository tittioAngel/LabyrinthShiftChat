//package org.example.controller;
//
//import org.example.model.Maze;
//import org.example.service.MazeService;
//import org.example.singleton.ServiceFactory;
//
//import java.util.List;
//
//public class MazeController {
//
//    private final MazeService mazeService;
//
//    public MazeController() {
//        this.mazeService = ServiceFactory.getInstance().getMazeService();
//    }
//
//    public void createMaze(int difficulty, boolean dynamicPaths) {
//        mazeService.createMaze(difficulty, dynamicPaths);
//        System.out.println("Maze created with difficulty: " + difficulty);
//    }
//
//    public void displayMaze(Long id) {
//        Maze maze = mazeService.getMazeById(id);
//        if (maze != null) {
//            System.out.println("Maze ID: " + maze.getId() + ", Difficulty: " + maze.getDifficultyLevel());
//        } else {
//            System.out.println("Maze not found.");
//        }
//    }
//
//    public void displayAllMazes() {
//        List<Maze> mazes = mazeService.getAllMazes();
//        mazes.forEach(m -> System.out.println("Maze ID: " + m.getId() + ", Difficulty: " + m.getDifficultyLevel()));
//    }
//}
