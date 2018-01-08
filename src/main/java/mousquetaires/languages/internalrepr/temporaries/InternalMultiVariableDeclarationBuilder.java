package mousquetaires.languages.internalrepr.temporaries;

import mousquetaires.languages.internalrepr.InternalEntity;
import mousquetaires.languages.internalrepr.expressions.InternalAssignmentExpression;
import mousquetaires.languages.internalrepr.expressions.InternalExpression;
import mousquetaires.languages.internalrepr.expressions.lvalue.InternalVariableRef;
import mousquetaires.languages.internalrepr.statements.InternalLinearStatement;
import mousquetaires.languages.internalrepr.statements.InternalSequenceStatement;
import mousquetaires.languages.internalrepr.statements.InternalVariableDeclarationStatement;
import mousquetaires.languages.internalrepr.types.InternalType;
import mousquetaires.patterns.Builder;
import mousquetaires.utils.exceptions.UninitialisedFieldException;
import org.antlr.v4.misc.OrderedHashMap;

import java.util.Map;


// TODO: it's mutable!
public class InternalMultiVariableDeclarationBuilder
        extends Builder<InternalSequenceStatement> implements InternalEntity {

    private InternalType declaredType;
    private Map<InternalVariableRef, InternalExpression> variablesInitialisations;  // value may be null in case 'int x;'

    @Override
    public InternalSequenceStatement build() {
        if (declaredType == null) {
            throw new UninitialisedFieldException("declaredType");
        }
        if (variablesInitialisations == null || variablesInitialisations.size() == 0) {
            throw new UninitialisedFieldException("variablesInitialisations");
        }

        InternalSequenceStatementBuilder builder = new InternalSequenceStatementBuilder();

        for (Map.Entry<InternalVariableRef, InternalExpression> entry : variablesInitialisations.entrySet()) {

            InternalVariableRef variable = entry.getKey();
            InternalExpression initialiser = entry.getValue();

            builder.append(new InternalVariableDeclarationStatement(declaredType, variable));

            if (initialiser != null) {
                InternalAssignmentExpression assignmentExpression = new InternalAssignmentExpression(variable, initialiser);
                builder.append(new InternalLinearStatement(assignmentExpression));
            }
        }
        return builder.build();
    }

    public void setDeclaredType(InternalType type) {
        declaredType = type;
    }

    public void addVariableInitialisation(InternalVariableRef variableRef, InternalExpression assignmentExpr) {
        if (variablesInitialisations == null) {
            variablesInitialisations = new OrderedHashMap<>();
        }
        variablesInitialisations.put(variableRef, assignmentExpr);
    }
}
