package mousquetaires.languages.syntax.ytree.expressions.atomics;

import mousquetaires.languages.common.citation.CodeLocation;
import mousquetaires.languages.syntax.ytree.types.YType;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;


public class YParameter implements YAtom {

    private final CodeLocation location;
    private final YType type;
    private final YVariable variable;

    public YParameter(CodeLocation location, YType type, YVariable variable) {
        this.location = location;
        this.variable = variable.asGlobal();
        this.type = type;
    }

    public YType getType() {
        return type;
    }

    public YVariable getVariable() {
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
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
