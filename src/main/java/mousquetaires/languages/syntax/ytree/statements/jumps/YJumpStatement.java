package mousquetaires.languages.syntax.ytree.statements.jumps;

import mousquetaires.languages.syntax.ytree.statements.YStatement;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;

import java.util.Objects;


// NOTE: immutability of this class is emulated manually!
public class YJumpStatement extends YStatement {
    public enum Kind {
        Goto,
        Return,
        Break,
        Continue,
        ;


        public YJumpStatement createJumpStatement() {
            return new YJumpStatement(this, new YJumpLabel(this.toString()));
        }

        public YJumpStatement createJumpStatement(YJumpLabel jumpLabel) {
            return new YJumpStatement(this, jumpLabel);
        }

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    private final Kind kind;
    private final YJumpLabel jumpLabel; // label of statement to which we jump

    private YJumpStatement(Kind kind, YJumpLabel jumpLabel) {
        this(newLabel(), kind, jumpLabel);
    }

    private YJumpStatement(String selfLabel, Kind kind, YJumpLabel jumpLabel) {
        super(selfLabel);
        this.kind = kind;
        this.jumpLabel = jumpLabel;
    }

    public Kind getKind() {
        return kind;
    }

    public YJumpLabel getJumpLabel() {
        return jumpLabel;
    }


    /**
     * NOTE: this method changes self label, NOT the label to jump to
     */
    @Override
    public YStatement withLabel(String newLabel) {
        return new YJumpStatement(newLabel, getKind(), getJumpLabel());
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return kind.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YJumpStatement)) return false;
        YJumpStatement that = (YJumpStatement) o;
        return getKind() == that.getKind() &&
                Objects.equals(getJumpLabel(), that.getJumpLabel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKind(), getJumpLabel());
    }
}
