package mousquetaires.languages.syntax.ytree.statements;

import mousquetaires.languages.common.citation.Origin;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.visitors.YtreeVisitor;

import java.util.Objects;


// while + for loops
public class YLoopStatement extends YStatement {

    private final YExpression condition;
    private final YStatement body;


    public YLoopStatement(Origin origin, YExpression condition, YStatement body) {
        this(origin, null, condition, body);
    }

    private YLoopStatement(Origin origin, String label, YExpression condition, YStatement body) {
        super(origin, label);
        this.condition = condition;
        this.body = body;
    }

    public YExpression getCondition() {
        return condition;
    }

    public YStatement getBody() {
        return body;
    }

    @Override
    public YLoopStatement withLabel(String newLabel) {
        return new YLoopStatement(origin(), newLabel, getCondition(), getBody());
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return String.format("while (%s) %s", getCondition(), getBody());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YLoopStatement)) return false;
        YLoopStatement that = (YLoopStatement) o;
        return Objects.equals(getCondition(), that.getCondition()) &&
                Objects.equals(getBody(), that.getBody());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCondition(), getBody());
    }
}
