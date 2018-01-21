package mousquetaires.languages.syntax.ytree.statements.labeled;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;


public class YJumpStatement extends YLabeledStatement {

    private final String jumpLabel;

    public YJumpStatement(String jumpLabel) {
        this(null, jumpLabel);
    }

    private YJumpStatement(String label, String jumpLabel) {
        super(label);
        this.jumpLabel = jumpLabel;
    }

    public String getJumpLabel() {
        return jumpLabel;
    }

    @Override
    public YJumpStatement withLabel(String newLabel) {
        return new YJumpStatement(newLabel, getJumpLabel());
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom();
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YJumpStatement copy() {
        return new YJumpStatement(getLabel(), getJumpLabel());
    }
}
