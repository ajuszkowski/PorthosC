package mousquetaires.languages.syntax.ytree.statements.jumps;

import mousquetaires.languages.common.citation.Origin;
import mousquetaires.languages.syntax.ytree.statements.YStatement;
import mousquetaires.languages.syntax.ytree.visitors.YtreeVisitor;

import java.util.Objects;


// NOTE: immutability of this class is emulated manually!
public class YJumpStatement extends YStatement {
    public enum Kind {
        Goto,
        Return,
        Break,
        Continue,
        ;

        public YJumpStatement createJumpStatement(Origin location) {
            return new YJumpStatement(location, this, new YJumpLabel(this.toString()));
        }

        public YJumpStatement createJumpStatement(Origin location, YJumpLabel jumpLabel) {
            return new YJumpStatement(location, this, jumpLabel);
        }

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    private final Kind kind;
    private final YJumpLabel jumpLabel; // label of statement to which we jump

    private YJumpStatement(Origin location, Kind kind, YJumpLabel jumpLabel) {
        this(location, newLabel(), kind, jumpLabel);
    }

    private YJumpStatement(Origin location, String selfLabel, Kind kind, YJumpLabel jumpLabel) {
        super(location, selfLabel);
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
        return new YJumpStatement(codeLocation(), newLabel, getKind(), getJumpLabel());
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
