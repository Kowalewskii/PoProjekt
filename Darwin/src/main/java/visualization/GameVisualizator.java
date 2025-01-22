package visualization;
import records.*;
import others.*;
import observers.*;
import enums.*;
import physical.*;
import physical.abstractt.*;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextFlow;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

public class GameVisualizator extends BaseVisualizator implements MapChangeListener {
    @FXML
    private Button stopGame;
    @FXML
    private Label gameStatsDays;
    @FXML
    private Label gameStatsAnimals;
    @FXML
    private Label gameStatsPlants;
    @FXML
    private Label gameStatsNotOccupiedFields;
    @FXML
    private Label gameStatsBestGene1;
    @FXML
    private Label gameStatsBestGene2;
    @FXML
    private Label gameStatsBestGene3;
    @FXML
    private Label gameStatsAverageEnergy;
    @FXML
    private Label gameStatsAverageAge;
    @FXML
    private Label gameStatsAverageDescendants1;
    @FXML
    private TextFlow animalStatsGenes;
    @FXML
    private Label animalStatsEnergy;
    @FXML
    private Label animalStatsConsumedPlants;
    @FXML
    private Label animalStatsDescendants1;
    @FXML
    private Label animalStatsDescendants2;
    @FXML
    private Label animalStatsLifespan;
    @FXML
    private Label animalStatsFinalDay;

    @FXML
    private Button mostPopularGenesButton;
    @FXML
    private Button JungleFieldsButton;

    private ExtendedThread thread;
    private WorldStats worldStats;
    private final Map<Vector2d, Pane> fields = new HashMap<>();
    private Animal chosenCreature = null;
    private List<Animal> mostPopularGenesCreatures = new ArrayList<>();
    private Set<Vector2d> pos1 = new HashSet<>();
    private boolean shouldExportStatistics;
    private boolean showExportStatisticsAlert = true;
    private volatile boolean running = false;
    private volatile boolean paused = false;
    @FXML
    private GridPane generalStatsPane;

    @FXML
    private GridPane boardPane;

    private boolean ifShowMostPop = false;
    private boolean ifShowJungle = false;

    @FXML
    //@Deprecated(since = "1.2")
    private void initialize() {

        for (Node node : getClassElements("animal-statistic")) {
            node.setVisible(false);
        }

        generalStatsPane.setHgap(0);
        generalStatsPane.setVgap(0);

        stopGame.setOnAction(event -> {
            if (running) {
                thread.pauseSimulation();
                stopGame.setText("Resume simulation");
                running = false;
                paused = false;
            } else {
                thread.resumeSimulation();
                stopGame.setText("Pause simulation");
                running = true;
                paused = true;
            }
        });


        mostPopularGenesButton.setOnAction(event -> {
            if (!paused) {
                if (!ifShowMostPop) {
                    this.showMostPopGenes();
                } else {
                    this.hidMostPopGenes();
                }
                ifShowMostPop = !ifShowMostPop;
            }
           
        });


        JungleFieldsButton.setOnAction(event -> {
            if (!paused) {
                if (!ifShowJungle) {
                    this.showJungleFields();
                } else {
                    this.hidePreferredFields();
                }

                ifShowJungle = !ifShowJungle;
            }
        });
    }

    private void showMostPopGenes() {
        for (Animal animal : mostPopularGenesCreatures) {
            Pane pane = fields.get(animal.getPosition());
            Pane displayPane = new Pane();
            displayPane.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY)));
            pane.getChildren().add(displayPane);
            displayPane.toBack();
        }
    }

    private void showJungleFields() {
        for (Vector2d position : pos1) {
            Pane pane = fields.get(position);
            Pane displayPane = new Pane();
            displayPane.setBorder(new Border(new javafx.scene.layout.BorderStroke(Color.GRAY,
                    javafx.scene.layout.BorderStrokeStyle.SOLID,
                    CornerRadii.EMPTY,
                    new BorderWidths(pane.getWidth() / 10.0))));
            pane.getChildren().add(displayPane);
        }
    }

    private void hidMostPopGenes() {
        for (Animal animal : mostPopularGenesCreatures) {
            Pane pane = fields.get(animal.getPosition());

            pane.getChildren().removeIf(child -> child.getStyleClass().contains("toOptionalDeleteGenotype"));
        }
    }

    private void hidePreferredFields() {
        for (Vector2d position : pos1) {
            Pane pane = fields.get(position);

            pane.getChildren().removeIf(child -> child.getStyleClass().contains("toOptionalDeleteField"));
        }
    }

    @Override
    public void mapChanged(World world, Statistics statistics) {
        Platform.runLater(() -> {
            this.mostPopularGenesCreatures =
                    this.findMostPopAnimals(statistics.getMostPopularGenes(), world);
            this.pos1 = world.getPositions1();
            this.creationOfTheWorld(world);
            this.showWorldStats(statistics);
            this.showAnimalStats();

            if (shouldExportStatistics) {
                exportCsvStats(world, statistics);
            }
        });
    }

    private List<Animal> findMostPopAnimals(List<List<Integer>> genotypes, World world) {
        Set<List<Integer>> setGenotypes = new HashSet<>(genotypes);
        return world.getAllAnimals().isEmpty() ? new ArrayList<>() : world.getAllAnimals().stream()
                .filter(animal -> setGenotypes.contains(animal.getGenes()))
                .filter(animal -> animal == world.dominatingAnimals(world.getAnimals().get(animal.getPosition()), 1).get(0))
                .toList();
    }

    public void setWorldConfig(WorldStats worldStats) {
        this.worldStats = worldStats;
    }

    public void setShouldExportStatistics(boolean shouldExportStatistics) {
        this.shouldExportStatistics = shouldExportStatistics;
    }

    public void actOfCreation() {
        for (int row = 0; row < worldStats.mapHeight(); row++) {
            for (int col = 0; col < worldStats.mapWidth(); col++) {
                Pane pane = new StackPane();
                pane.setPadding(new Insets(0));
                pane.setMinSize((double) 600 / Math.max(worldStats.mapWidth(), worldStats.mapHeight()), (double) 600 / Math.max(worldStats.mapWidth(), worldStats.mapHeight()));
                pane.setMaxSize((double) 600 / Math.max(worldStats.mapWidth(), worldStats.mapHeight()), (double) 600 / Math.max(worldStats.mapWidth(), worldStats.mapHeight()));
                fields.put(new Vector2d(col, row), pane);
                boardPane.add(pane, col, row);
            }
        }
    }

    public void creationOfTheWorld(World world) {
        for (int row = 0; row < worldStats.mapHeight(); row++) {
            for (int col = 0; col < worldStats.mapWidth(); col++) {
                Pane pane = fields.get(new Vector2d(col, row));
                pane.setStyle("-fx-background-color: rgba(144, 238, 144, 0.2)");
                pane.getChildren().clear();
            }
        }

        for (Plant plant : world.getPlants().values()) {
            Pane pane = fields.get(plant.getPosition());
            WorldElementBox plantBox = new WorldElementBox(plant, pane, false);
            Pane plantPane = plantBox.getCellDisplay();
            pane.getChildren().add(plantPane);
        }

        Animal animalEnerMax = world.getAllAnimals().isEmpty() ? null :
                world.dominatingAnimals(world.getAllAnimals(), 1).get(0);

        for (List<Animal> animalsArray : world.getAnimals().values()) {
            Animal chosenCreature = world.dominatingAnimals(animalsArray, 1).get(0);
            Pane pane = fields.get(chosenCreature.getPosition());
            WorldElementBox animalBox = new WorldElementBox(chosenCreature, pane, true);
            Pane animalPane = animalBox.getCellDisplay();
            Circle circle = (Circle) animalPane.getChildren().get(0);
            circle.setFill(GameStatic.getAnimalColor(animalEnerMax.getEnergy(), chosenCreature));

            if (chosenCreature.equals(this.chosenCreature)) {
                circle.setFill(Color.INDIGO);
            }

            pane.getChildren().add(animalPane);
        }

        for (Node node : getClassElements("animal-pane")) {
            node.setOnMouseClicked(nextEvent -> {
                stalkCreature(world, world.getAnimalID(UUID.fromString(node.getId())));
            });
        }
    }

    public void showWorldStats(Statistics statistics) {
        gameStatsDays.setText(GameStatic.convertDaysToDate(statistics.getDayNumber()));
        gameStatsAnimals.setText(GameStatic.convertNumberToString(statistics.getAnimalNumber()));
        gameStatsPlants.setText(GameStatic.convertNumberToString(statistics.getPlantsNumber()));
        gameStatsNotOccupiedFields.setText(GameStatic.convertNumberToString(statistics.getNotOccupiedFields()));
        gameStatsAverageEnergy.setText(GameStatic.convertNumberToString(statistics.getAverageEnergy()));
        gameStatsAverageAge.setText(GameStatic.convertDaysToTime(statistics.getAverageFinalDate()));
        gameStatsAverageDescendants1.setText(GameStatic.convertNumberToString(statistics.getAverageDescendantsNumber()));

        if (!statistics.getMostPopularGenes().isEmpty()) {
            gameStatsBestGene1.setText(GameStatic.toStringGenes(statistics.getMostPopularGenes().get(0)));
        } else {
            gameStatsBestGene1.setText(GameLabelText.NO_GENOTYPE.toString());
        }

        if (statistics.getMostPopularGenes().size() > 1) {
            gameStatsBestGene2.setText(GameStatic.toStringGenes(statistics.getMostPopularGenes().get(1)));
        } else {
            gameStatsBestGene2.setText(GameLabelText.NO_GENOTYPE.toString());
        }

        if (statistics.getMostPopularGenes().size() > 2) {
            gameStatsBestGene3.setText(GameStatic.toStringGenes(statistics.getMostPopularGenes().get(2)));
        } else {
            gameStatsBestGene3.setText(GameLabelText.NO_GENOTYPE.toString());
        }

    }

    public void stalkCreature(World world, Animal animal) {
        if (chosenCreature != null) {
            Pane lastAnimalPane = (Pane) rootPane.lookup("#" + chosenCreature.getId());
            Circle lastCircle = (Circle) lastAnimalPane.getChildren().get(0);
            lastCircle.setFill(GameStatic.getAnimalColor(chosenCreature.getEnergy(), chosenCreature));
        }

        if (animal.equals(chosenCreature)) {
            chosenCreature = null;
        } else {
            chosenCreature = animal;
            Pane animalPane = (Pane) rootPane.lookup("#" + animal.getId());
            Circle circle = (Circle) animalPane.getChildren().get(0);
            circle.setFill(Color.INDIGO);
        }
        showAnimalStats();
    }

    public void showAnimalStats() {
        if (chosenCreature != null) {
            for (Node node : getClassElements("animal-statistic")) {
                node.setVisible(true);
            }
            rootPane.lookup(".animal-statistic-header").setVisible(true);

            animalStatsEnergy.setText(GameStatic.convertNumberToString(chosenCreature.getEnergy()));
            animalStatsConsumedPlants.setText(GameStatic.convertNumberToString(chosenCreature.getConsumedPlants()));
            animalStatsDescendants1.setText(GameStatic.convertNumberToString(chosenCreature.getDescendants1()));
            animalStatsDescendants2.setText(GameStatic.convertNumberToString(chosenCreature.getDescendants2()));
            animalStatsLifespan.setText(GameStatic.convertDaysToTime(chosenCreature.getAge()));
            animalStatsFinalDay.setText(GameStatic.convertDaysToDate(chosenCreature.getFinalDay()));

            GameStatic.fillGenesWithIndex(
                    animalStatsGenes, chosenCreature.getGenes(), chosenCreature.getGenesID(), chosenCreature.getFinalDay() == 0
            );
        } else {
            for (Node node : getClassElements("animal-statistic")) {
                node.setVisible(false);
            }
            rootPane.lookup(".animal-statistic-header").setVisible(false);
        }
    }

    public void exportCsvStats(World world, Statistics statistics) {
        String projectPath = System.getProperty("user.dir");
        String filename = "World_Statistics_" + world.getId() + ".csv";
        String filePath = projectPath + "/src/main/resources/statistics/" + filename;

        File csvFile = new File(filePath);
        boolean fileExist = csvFile.exists();

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            if (!fileExist) {
                CsvChanger.setStatsTop(writer);
            }

            CsvChanger.fillStatisticsDay(writer, world.getId(), statistics, chosenCreature);

        } catch (Exception e) {
            if (showExportStatisticsAlert) {
                showExportStatisticsAlert = false;
                showAlert("Error", "Error on saving statistics", "Cannot export file with world and animal statistics", Alert.AlertType.ERROR);
            }
        }
    }

    public void setThread(ExtendedThread thread) {
        this.thread = thread;
    }
}