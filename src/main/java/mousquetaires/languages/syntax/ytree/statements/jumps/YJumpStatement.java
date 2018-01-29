package mousquetaires.languages.syntax.ytree.statements.jumps;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.statements.YStatement;
import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;


public class YJumpStatement extends YStatement {

    private final YJumpLabel jumpLabel; // label of statement to which we jump

    public YJumpStatement(YJumpLabel jumpLabel) {
        this(newLabel(), jumpLabel);
    }

    private YJumpStatement(String selfLabel, YJumpLabel jumpLabel) {
        super(selfLabel);
        this.jumpLabel = jumpLabel;
    }

    public YJumpLabel getJumpLabel() {
        return jumpLabel;
    }

    /**
     * NOTE: this method changes self label, NOT the label to jump to
     */
    @Override
    public YStatement withLabel(String newLabel) {
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
        return new YJumpStatement(getJumpLabel());
    }
}
