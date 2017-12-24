import mousquetaires.options.CommandLineOptions;


public class App {

    public static void main(String[] args) {
        CommandLineOptions options = CommandLineOptions.parse(args);

        switch (options.module) {
            case Porthos:
                break;
            case Dartagnan:
                break;
            case Aramis:
                throw new UnsupportedOperationException(options.module.toString());
        }
    }
}
