package mousquetaires.languages.ytree.statements;

import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;


public class YJumpStatement extends YStatement {

    public final String jumptoLabel;

    public YJumpStatement(String jumptoLabel) {
        this(null, jumptoLabel);
    }

    private YJumpStatement(String label, String jumptoLabel) {
        super(label);
        this.jumptoLabel = jumptoLabel;
    }

    @Override
    public YJumpStatement withLabel(String newLabel) {
        return new YJumpStatement(newLabel, jumptoLabel);
    }

    @Override
    public Iterator<YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom();
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YJumpStatement copy() {
        return new YJumpStatement(label, jumptoLabel);
    }
}
