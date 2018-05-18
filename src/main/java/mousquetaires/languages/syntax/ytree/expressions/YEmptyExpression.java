package mousquetaires.languages.syntax.ytree.expressions;

import mousquetaires.languages.common.citation.CodeLocation;
import mousquetaires.languages.syntax.ytree.visitors.YtreeVisitor;
import mousquetaires.utils.exceptions.NotSupportedException;


public class YEmptyExpression implements YExpression {

    private final CodeLocation codeLocation;

    public YEmptyExpression(CodeLocation codeLocation) {
        this.codeLocation = codeLocation;
    }

    @Override
    public int getPointerLevel() {
        return 0;
    }

    @Override
    public YExpression withPointerLevel(int level) {
        throw new NotSupportedException("Cannot be a pointer: " + YEmptyExpression.class.getSimpleName());
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public CodeLocation codeLocation() {
        return codeLocation;
    }
}
