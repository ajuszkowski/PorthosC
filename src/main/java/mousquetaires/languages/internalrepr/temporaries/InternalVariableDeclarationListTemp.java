package mousquetaires.languages.internalrepr.temporaries;

import mousquetaires.languages.internalrepr.expressions.InternalAssignmentExpression;
import mousquetaires.languages.internalrepr.expressions.InternalExpression;
import mousquetaires.languages.internalrepr.statements.InternalBlockStatement;
import mousquetaires.languages.internalrepr.statements.InternalLinearStatement;
import mousquetaires.languages.internalrepr.statements.InternalStatement;
import mousquetaires.languages.internalrepr.statements.InternalVariableDeclarationStatement;
import mousquetaires.languages.internalrepr.types.InternalType;
import mousquetaires.languages.internalrepr.variables.InternalVariable;

import java.util.ArrayList;
import java.util.List;


// TODO: it's mutable!
public class InternalVariableDeclarationListTemp extends InternalStatement {

    private InternalType type;
    private List<InternalVariableDeclarationTemp> declarators;

    public InternalBlockStatement toBlockStatement() {
        InternalBlockStatementBuilder builder = new InternalBlockStatementBuilder();

        for (InternalVariableDeclarationTemp declarator : declarators) {

            InternalVariable assignee = new InternalVariable(type, declarator.variableName);
            builder.append(new InternalVariableDeclarationStatement(assignee));

            InternalExpression declaratorExpr = declarator.expression;
            if (declaratorExpr != null){
                InternalAssignmentExpression assignmentExpression = new InternalAssignmentExpression(assignee, declaratorExpr);
                builder.append(new InternalLinearStatement(assignmentExpression));
            }
        }
        return builder.build();
    }

    public InternalType getType() {
        return type;
    }

    public void setType(InternalType type) {
        this.type = type;
    }

    public void addDeclarator(InternalVariableDeclarationTemp declarator) {
        if (declarators == null) {
            declarators = new ArrayList<>();
        }
        declarators.add(declarator);
    }
}
