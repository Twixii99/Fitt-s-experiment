package alexu.edu.eg.fetts.app;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;

public class DataCollector {
    private static List<Integer> distances_between_buttons = new ArrayList<>();
    private static List<Integer>  width_of_buttons = new ArrayList<>();
    public static List<Long>  time_elapsed = new ArrayList<>();
    public static List<Double> difficultyIndex = new ArrayList<>();  // based on fitt's formula
    public static FileWriter myWriter;

    static {
        try {
            myWriter = new FileWriter("collected data.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int size = 0;
//    private static double a_parameter, b_parameter;

    public static void enterData(int d, int w, long t) throws IOException {
        DataCollector.myWriter.write("distance = " + d + "\twidth = " + w + "\ttime = " + t + "\tID = " + Math.log(2.0 * Double.valueOf(d) / Double.valueOf(w)) / Math.log(2) + "\n");
        ++DataCollector.size;
        DataCollector.distances_between_buttons.add(d);
        DataCollector.width_of_buttons.add(w);
        DataCollector.time_elapsed.add(t);
        DataCollector.difficultyIndex.add(Math.log(2.0 * Double.valueOf(d) / Double.valueOf(w)) / Math.log(2));
    }

//    public static void evaluateParameters() {
//        DataCollector.a_parameter = 0;
//        DataCollector.b_parameter = 0;
//       for(int i = 0; i < DataCollector.difficultyIndex.size(); ++i) {
//           for(int j = i+1; j < DataCollector.difficultyIndex.size(); ++j) {
//               double b_auxiliary = (DataCollector.time_elapsed.get(i) - DataCollector.time_elapsed.get(j)) /
//                       (DataCollector.difficultyIndex.get(i) - DataCollector.difficultyIndex.get(j)),
//                       a_auxiliary = DataCollector.time_elapsed.get(i) - b_auxiliary * DataCollector.difficultyIndex.get(i);
//               DataCollector.a_parameter += a_auxiliary;
//               DataCollector.b_parameter += b_auxiliary;
//           }
//       }
//       DataCollector.a_parameter /= Double.valueOf(DataCollector.size);     // to get the average value to minimize the error.
//        DataCollector.b_parameter /= Double.valueOf(DataCollector.size);
//    }

    public static int getSize() {
        return DataCollector.size;
    }
}
