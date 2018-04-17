package mousquetaires.languages.common.citation;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Interval;


public class CodeCitationService {

    private final String file;
    private final CommonTokenStream input;

    public CodeCitationService(String file, CommonTokenStream input) {
        this.file = file;
        this.input = input;
    }

    public CodeLocation getLocation(ParserRuleContext ctx) {
        int start = ctx.getStart().getTokenIndex();
        int stop = ctx.stop.getTokenIndex();
        return new CodeLocation(file, start, stop);
    }

    public String getCitation(CodeLocation codeLocation) {
        Interval interval = new Interval(codeLocation.start(), codeLocation.end());
        return input.getText(interval);
    }
}
