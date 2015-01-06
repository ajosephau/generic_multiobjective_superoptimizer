import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.dgso.DistributedGenericSuperoptimizer;

public class Main {
    private static Logger mainLogger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        String inputFile;

        // setup logger
        BasicConfigurator.configure();

        // create a CharStream that reads from standard input
        if (args.length != 1) {
            mainLogger.error("dgso requires 1 argument: the path to the configuration file.");
            System.exit(-1);
        }

        inputFile = args[0];

        DistributedGenericSuperoptimizer.runAsSingleProcessStandalone(inputFile);
    }

}
