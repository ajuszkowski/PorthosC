package mousquetaires.app.modules;

import java.io.IOException;


public interface IAppModule {

    AppVerdict run() throws IOException;
}
