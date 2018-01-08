package mousquetaires.languages.ytree.temporaries;

import mousquetaires.languages.ytree.YEntity;
import mousquetaires.languages.ytree.expressions.YAssignmentExpression;
import mousquetaires.languages.ytree.expressions.YExpression;
import mousquetaires.languages.ytree.expressions.lvalue.YVariableRef;
import mousquetaires.languages.ytree.statements.YLinearStatement;
import mousquetaires.languages.ytree.statements.YSequenceStatement;
import mousquetaires.languages.ytree.statements.YVariableDeclarationStatement;
import mousquetaires.languages.ytree.types.InternalType;
import mousquetaires.patterns.Builder;
import mousquetaires.utils.exceptions.UninitialisedFieldException;
import org.antlr.v4.misc.OrderedHashMap;

import java.util.Map;


// TODO: it's mutable!
public class InternalMultiVariableDeclarationBuilder
        extends Builder<YSequenceStatement> implements YEntity {

    private InternalType declaredType;
    private Map<YVariableRef, YExpression> variablesInitialisations;  // value may be null in case 'int x;'

    @Override
    public YSequenceStatement build() {
        if (declaredType == null) {
            throw new UninitialisedFieldException("declaredType");
        }
        if (variablesInitialisations == null || variablesInitialisations.size() == 0) {
            throw new UninitialisedFieldException("variablesInitialisations");
        }

        InternalSequenceStatementBuilder builder = new InternalSequenceStatementBuilder();

        for (Map.Entry<YVariableRef, YExpression> entry : variablesInitialisations.entrySet()) {

            YVariableRef variable = entry.getKey();
            YExpression initialiser = entry.getValue();

            builder.append(new YVariableDeclarationStatement(declaredType, variable));

            if (initialiser != null) {
                YAssignmentExpression assignmentExpression = new YAssignmentExpression(variable, initialiser);
                builder.append(new YLinearStatement(assignmentExpression));
            }
        }
        return builder.build();
    }

    public void setDeclaredType(InternalType type) {
        declaredType = type;
    }

    public void addVariableInitialisation(YVariableRef variableRef, YExpression assignmentExpr) {
        if (variablesInitialisations == null) {
            variablesInitialisations = new OrderedHashMap<>();
        }
        variablesInitialisations.put(variableRef, assignmentExpr);
    }
}
