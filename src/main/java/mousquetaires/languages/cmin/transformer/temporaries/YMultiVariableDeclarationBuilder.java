//package mousquetaires.languages.cmin.transformer.temporaries;
//
//import mousquetaires.languages.ytree.YEntity;
//import mousquetaires.languages.ytree.expressions.YAssignmentExpression;
//import mousquetaires.languages.ytree.expressions.YExpression;
//import mousquetaires.languages.ytree.expressions.YVariableRef;
//import mousquetaires.languages.ytree.statements.YLinearStatement;
//import mousquetaires.languages.ytree.statements.YSequenceStatement;
//import mousquetaires.languages.ytree.statements.YVariableDeclarationStatement;
//import mousquetaires.languages.ytree.types.YType;
//import mousquetaires.patterns.Builder;
//import mousquetaires.utils.exceptions.UninitialisedFieldException;
//import org.antlr.v4.misc.OrderedHashMap;
//
//import java.util.Map;
//
//
//// NOTE: it's mutable!
//public class YMultiVariableDeclarationBuilder
//        extends Builder<YSequenceStatement> implements YEntity {
//
//    private YType declaredType;
//    private Map<YVariableRef, YExpression> variablesInitialisations;  // value may be null in case 'int x;'
//
//    @Override
//    public YSequenceStatement build() {
//        if (declaredType == null) {
//            throw new UninitialisedFieldException("declaredType");
//        }
//        if (variablesInitialisations == null || variablesInitialisations.size() == 0) {
//            throw new UninitialisedFieldException("variablesInitialisations");
//        }
//
//        YSequenceStatementBuilder builder = new YSequenceStatementBuilder();
//
//        for (Map.Entry<YVariableRef, YExpression> entry : variablesInitialisations.entrySet()) {
//
//            YVariableRef variable = entry.getKey();
//            YExpression initialiser = entry.getValue();
//
//            builder.add(new YVariableDeclarationStatement(declaredType, variable));
//
//            if (initialiser != null) {
//                YAssignmentExpression assignmentExpression = new YAssignmentExpression(variable, initialiser);
//                builder.add(new YLinearStatement(assignmentExpression));
//            }
//        }
//        return builder.build();
//    }
//
//    public void setDeclaredType(YType type) {
//        declaredType = type;
//    }
//
//    public void add(YVariableRef variableRef, YExpression assignmentExpr) {
//        if (variablesInitialisations == null) {
//            variablesInitialisations = new OrderedHashMap<>();
//        }
//        variablesInitialisations.put(variableRef, assignmentExpr);
//    }
//}
