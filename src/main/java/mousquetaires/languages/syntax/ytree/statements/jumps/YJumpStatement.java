package mousquetaires.languages.syntax.ytree.statements.jumps;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.statements.YStatement;
import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;


// NOTE: immutability of this class is emulated manually!
public class YJumpStatement extends YStatement {

    private final YJumpLabel jumpLabel; // label of statement to which we jump

    public YJumpStatement(YJumpLabel jumpLabel) {
        this(newLabel(), jumpLabel);
    }

    private YJumpStatement(String selfLabel, YJumpLabel jumpLabel) {
        super(selfLabel);
        this.jumpLabel = jumpLabel;
    }

    //protected void setJumpLabel(YJumpLabel jumpLabel) {
    //    if (getJumpLabel() != null) {
    //        throw new IllegalStateException("Jump label has already been set to the value: " +
    //                StringUtils.wrap(getJumpLabel().getValue()));
    //    }
    //    this.jumpLabel = jumpLabel;
    //}

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
