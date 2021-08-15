package com.cbtl.error;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SyntaxErrorListener extends BaseErrorListener {
    private final Logger logger = LogManager.getLogger(SyntaxErrorListener.class);

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        this.logger.error("Malformed input, reason: {}", msg);
        throw new MalformedInputException("Malformed input");
    }
}
