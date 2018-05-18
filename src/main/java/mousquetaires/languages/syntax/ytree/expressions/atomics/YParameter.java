package mousquetaires.languages.syntax.ytree.expressions.atomics;

import mousquetaires.languages.common.citation.CodeLocation;
import mousquetaires.languages.syntax.ytree.types.YType;
import mousquetaires.languages.syntax.ytree.visitors.YtreeVisitor;


public class YParameter implements YAtom {

    private final CodeLocation location;
    private final YType type;
    private final YVariableRef variable;

    public YParameter(CodeLocation location, YType type, YVariableRef variable) {
        this.location = location;
        this.variable = variable.asGlobal();
        this.type = type;
    }

    public YType getType() {
        return type;
    }

    public YVariableRef getVariable() {
        return variable;
    }

    @Override
    public Kind getKind() {
        return getVariable().getKind();
    }

    @Override
    public int getPointerLevel() {
        return getVariable().getPointerLevel();
    }

    @Override
    public YParameter withPointerLevel(int level) {
        return new YParameter(codeLocation(), getType(), getVariable().withPointerLevel(level));
    }

    @Override
    public CodeLocation codeLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "param(" + getType() + getVariable() + ")";
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
