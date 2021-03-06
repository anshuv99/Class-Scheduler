import entry.Subject;
import graphUtils.CycleDetector;
import graphUtils.GraphHelper;
import org.json.simple.JSONArray;
import utils.Constants;
import utils.ErrorHandler;
import utils.InputValidator;
import utils.Reader;

import java.util.List;

/**
 * This is the main Driver class which calls create graphs, validates and print Schedule by calling helper methods
 */
public class Scheduler {
    public static void main(String[] args) {
        // Validate Input
        InputValidator.getInputValidator().validateInput(args);

        JSONArray jsonArray = Reader.getReader().readFile(args[0]);

        // This list contains all the subject and prerequisites
        List<Subject> subjectList = Reader.getReader().parseJsonArray(jsonArray);


        // This calls creates connection among various subjects according to the prerequisites defined in the given file
        GraphHelper.getGraphHelper().createConnections(subjectList);


        // This calls validates if the cycle contains graph. In case the graph contains cycle break
        if (CycleDetector.getCycleDetector().isCycleFound(subjectList)) {
            ErrorHandler.getErrorHandler().printError(String.format(Constants.CYCLIC_DEPENDENCY_FOUND, args[0]));
            throw new RuntimeException(String.format(Constants.CYCLIC_DEPENDENCY_FOUND, args[0]));
        }

        // This calls prints final schedule
        GraphHelper.getGraphHelper().printSchedule(subjectList);
    }
}
