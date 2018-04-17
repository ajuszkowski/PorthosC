package mousquetaires.languages.syntax.ytree.statements;

import mousquetaires.languages.common.citation.CodeLocation;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;

import java.util.Objects;


public class YWhileLoopStatement extends YStatement {

    private final YExpression condition;
    private final YStatement body;


    public YWhileLoopStatement(CodeLocation location, YExpression condition, YStatement body) {
        this(location, newLabel(), condition, body);
    }

    private YWhileLoopStatement(CodeLocation location, String label, YExpression condition, YStatement body) {
        super(location, label);
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
    public YWhileLoopStatement withLabel(String newLabel) {
        return new YWhileLoopStatement(codeLocation(), newLabel, getCondition(), getBody());
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return String.format("while (%s) { %s }", getCondition(), getBody());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YWhileLoopStatement)) return false;
        YWhileLoopStatement that = (YWhileLoopStatement) o;
        return Objects.equals(getCondition(), that.getCondition()) &&
                Objects.equals(getBody(), that.getBody());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCondition(), getBody());
    }
}
