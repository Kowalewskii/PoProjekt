package visualization;
import others.*;
import records.*;
import mains.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.io.*;
import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
public class MenuVisualizator extends BaseVisualizator {

    @FXML
    private TextField formFireInterval;

    @FXML
    private TextField formFireDuration;

    @FXML
    private TextField formMapHeight;

    @FXML
    private TextField formMapWidth;

    @FXML
    private TextField formStartPlantNumber;

    @FXML
    private TextField formDailyPlantIncrease;

    @FXML
    private TextField formOnePlantEnergy;

    @FXML
    private TextField formStartAnimalNumber;

    @FXML
    private TextField formStartAnimalEnergy;

    @FXML
    private TextField formAnimalSexEnergyLoss;

    @FXML
    private TextField formAnimalDailyEnergyLoss;

    @FXML
    private TextField formAnimalSexEnergy;

    @FXML
    private TextField formAnimalMinMutation;

    @FXML
    private TextField formAnimalMaxMutation;

    @FXML
    private TextField formAnimalGenesLength;

    @FXML
    private CheckBox formFireMap;

    @FXML
    private CheckBox formMutatedAnimal;

    @FXML
    private CheckBox formExportStatistics;

    @FXML
    private Button createGame;

    @FXML
    private void initialize() throws Exception {
        createGame.setOnAction(event -> {
            try {
                WorldStats worldStats = getWorldConfigFromParams();
                checkParams(worldStats);
                createNewGame(worldStats);
            } catch (Exception e) {
                showAlert("Error", "Invalid data in game parameters", "Cannot start the Darwin World", Alert.AlertType.ERROR);
            }
        });
    }

    private void createNewGame(WorldStats worldStats) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Stage stage = new Stage();

        loader.setLocation(getClass().getClassLoader().getResource("game.fxml"));
        GridPane view = loader.load();
        GameVisualizator presenter = loader.getController();
        presenter.setWorldConfig(worldStats);
        presenter.setShouldExportStatistics(formExportStatistics.isSelected());
        presenter.actOfCreation();
        Simulation simulation = new Simulation(worldStats, presenter);
        ExtendedThread thread = new ExtendedThread(simulation);
        thread.start();

        stage.setOnCloseRequest(event -> {
            simulation.stopRunning();
        });

        presenter.setThread(thread);
        stage.setTitle("Darwin Simulation: " + simulation.getWorld().getId());
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon.png")));
        stage.getIcons().add(icon);
        stage.setScene(new Scene(view));
        stage.setMaximized(true);
        stage.show();
    }

    private void checkParams(WorldStats worldStats) throws Exception {
        if (worldStats.mapWidth() <= 0 || worldStats.mapHeight() <= 0 || worldStats.mapWidth() > 200 || worldStats.mapHeight() > 200) {
            throw new InvalidGameParamsException();
        }
        if (worldStats.startPlantNumber() < 0 || worldStats.dailyPlantIncrease() < 0 || worldStats.onePlantEnergy() < 0) {
            throw new InvalidGameParamsException();
        }
        if (worldStats.startAnimalNumber() <= 0 || worldStats.startAnimalEnergy() <= 0 || worldStats.animalSexRequiredEnergy() <= 0) {
            throw new InvalidGameParamsException();
        }
        if (worldStats.animalSexEnergyLoss() <= 0 || worldStats.animalDailyEnergyLoss() <= 0) {
            throw new InvalidGameParamsException();
        }//zmiana
        if (worldStats.animalMinMutation() < 0 || worldStats.animalMaxMutation() < 0 || worldStats.animalGenesLength() <= 0) {
            throw new InvalidGameParamsException();
        }
        if (worldStats.animalMinMutation() > worldStats.animalMaxMutation()) {
            throw new InvalidGameParamsException();
        }
        if (worldStats.animalMaxMutation() > worldStats.animalGenesLength()) {
            throw new InvalidGameParamsException();
        }
    }

    private WorldStats getWorldConfigFromParams() {
        return new WorldStats(
                Integer.parseInt(formMapHeight.getText()),
                Integer.parseInt(formMapWidth.getText()),
                Integer.parseInt(formStartPlantNumber.getText()),
                Integer.parseInt(formDailyPlantIncrease.getText()),
                Integer.parseInt(formOnePlantEnergy.getText()),
                Integer.parseInt(formStartAnimalNumber.getText()),
                Integer.parseInt(formStartAnimalEnergy.getText()),
                Integer.parseInt(formAnimalSexEnergyLoss.getText()),
                Integer.parseInt(formAnimalDailyEnergyLoss.getText()),
                Integer.parseInt(formAnimalSexEnergy.getText()),
                Integer.parseInt(formAnimalMinMutation.getText()),
                Integer.parseInt(formAnimalMaxMutation.getText()),
                Integer.parseInt(formAnimalGenesLength.getText()),
                formFireMap.isSelected(),
                formMutatedAnimal.isSelected(),
                Integer.parseInt(formFireInterval.getText()),
                Integer.parseInt(formFireDuration.getText())
        );
    }


    private void fillFormWorldConfigParams(HashMap<String, String> formValues) throws InvalidKeyException {
        for (Map.Entry<String, String> value : formValues.entrySet()) {
            switch (value.getKey()) {
                case "Property":
                    break;
                case "formMapHeight":
                    formMapHeight.setText(value.getValue());
                    break;
                case "formMapWidth":
                    formMapWidth.setText(value.getValue());
                    break;
                case "formPlantStart":
                    formStartPlantNumber.setText(value.getValue());
                    break;
                case "formPlantDaily":
                    formDailyPlantIncrease.setText(value.getValue());
                    break;
                case "formPlantEnergy":
                    formOnePlantEnergy.setText(value.getValue());
                    break;
                case "formAnimalStart":
                    formStartAnimalNumber.setText(value.getValue());
                    break;
                case "formAnimalStartEnergy":
                    formStartAnimalEnergy.setText(value.getValue());
                    break;
                case "formAnimalEnergyReproductionDepletion":
                    formAnimalSexEnergyLoss.setText(value.getValue());
                    break;
                case "formAnimalEnergyDailyDepletion":
                    formAnimalDailyEnergyLoss.setText(value.getValue());
                    break;
                case "formAnimalEnergyToReproduce":
                    formAnimalSexEnergy.setText(value.getValue());
                    break;
                case "formAnimalMutationMinimum":
                    formAnimalMinMutation.setText(value.getValue());
                    break;
                case "formAnimalMutationMaximum":
                    formAnimalMaxMutation.setText(value.getValue());
                    break;
                case "formAnimalGenotypeLength":
                    formAnimalGenesLength.setText(value.getValue());
                    break;
                case "formVariantMap":
                    formFireMap.setSelected(Boolean.parseBoolean(value.getValue()));
                    break;
                case "formVariantAnimal":
                    formMutatedAnimal.setSelected(Boolean.parseBoolean(value.getValue()));
                    break;
                    case "formFireInterval":
                        formFireInterval.setText(value.getValue());
                        break;
                        case "formFireDuration":
                            formFireDuration.setText(value.getValue());

                default:
                    throw new InvalidKeyException();
            }
        }
    }
}