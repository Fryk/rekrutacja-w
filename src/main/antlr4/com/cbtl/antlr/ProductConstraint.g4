grammar ProductConstraint;

/*
 * Parser Rules
 */

expression
    : OPERATOR '(' expression (',' expression)? ')' #expressionFunction
    | CONSTRAINT #expressionConstraint
    ;

/*
 * Lexer Rules
 */

fragment CONSTRAINT_NAME
    : [A-Z_]+
    ;

fragment CONSTRAINT_DELIMITER
    : '\''
    ;


CONSTRAINT
    : CONSTRAINT_DELIMITER CONSTRAINT_NAME CONSTRAINT_DELIMITER
    ;

OPERATOR
    : [A-Z]+
    ;
