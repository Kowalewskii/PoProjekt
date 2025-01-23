package others;

import physical.abstractt.Animal;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.math.BigDecimal;
import java.util.List;

public class GameStatic {

    static final int yearDays = 365;

    public static Color getAnimalColor(int maximumEnergy, Animal activeAnimal) {
        Color finishColor = Color.RED;
        Color startColor = Color.YELLOW;
        double value;
        if (maximumEnergy == 0) {
            value = 0.0;
        } else {
            value = (double) activeAnimal.getEnergy() / maximumEnergy;
        }


        double red = finishColor.getRed() + (startColor.getRed() - finishColor.getRed()) * value;
        double green = finishColor.getGreen() + (startColor.getGreen() - finishColor.getGreen()) * value;
        double blue = finishColor.getBlue() + (startColor.getBlue() - finishColor.getBlue()) * value;

        return new Color(red, green, blue, 1.0);
    }

    public static String toStringGenes(List<Integer> genes) {
        StringBuilder sb = new StringBuilder("(");

        for (Integer integer : genes) {
            sb.append(integer).append(" ");
        }

        return sb.deleteCharAt(sb.length() - 1).append(")").toString();
    }

    public static String toStringIDGenes(List<Integer> genes, int id) {
        StringBuilder sb = new StringBuilder("( ");

        for (int i = 0; i < genes.size(); i++) {
            if (i == id) {
                sb.append("[");
            }
            sb.append(genes.get(i)).append(" ");
            if (i == id) {
                sb.deleteCharAt(sb.length() - 1).append("] ");
            }
        }

        return sb.deleteCharAt(sb.length() - 1).append(" )").toString();
    }

    public static void fillGenesWithIndex(TextFlow textFlow, List<Integer> genes, int id, boolean animalWellBeing) {
        textFlow.getChildren().clear();

        textFlow.getChildren().add(new Text("( "));

        for (int i = 0; i < genes.size(); i++) {
            String textValue = genes.get(i).toString();
            Text text = new Text(textValue);

            if (i == id) {
                text.setStyle("-fx-font-weight: bold; -fx-fill: " + (animalWellBeing ? "#4CAF50" : "#e74c3c") + ";");
            }

            textFlow.getChildren().add(text);

            if (i < genes.size() - 1) {
                textFlow.getChildren().add(new Text(" "));
            }
        }

        textFlow.getChildren().add(new Text(" )"));
    }

    public static String convertDaysToDate(int days) {
        int dateYear = days / yearDays;
        int dateDay = days % yearDays;

        return "Year: " + removeTrailingZeros(dateYear) + " Day: " + removeTrailingZeros(dateDay);
    }

    public static String convertDaysToTime(double days) {
        int timeYears = (int) days / yearDays;
        double timeDays = (double) Math.round(days % yearDays * 100) / 100;

        return removeTrailingZeros(timeYears) + " years " + removeTrailingZeros(timeDays) + " days";
    }
    public static String convertNumberToString(double number) {
        double parsedNumber = (double) Math.round(number * 1000) / 1000;
        return removeTrailingZeros(parsedNumber);
    }
    private static String removeTrailingZeros(double number) {
        return new BigDecimal(String.valueOf(number)).stripTrailingZeros().toPlainString();
    }
}
