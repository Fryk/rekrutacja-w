package com.cbtl.expression.util;

import com.cbtl.error.NotSupportedConstraintException;
import com.cbtl.expression.constraint.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConstraintTypeUtil {
    private static final Logger logger = LogManager.getLogger(ConstraintTypeUtil.class);

    public static Verifiable getConstraintForString(String name) {
        logger.debug("Looking for constraint {}", name);
        return switch (name) {
            case "VIP_ONLY" -> new VIPOnly();
            case "FOR_RICH_PEOPLE" -> new ForRichPeople();
            case "WITH_RELATIONS" -> new WithRelations();
            case "ADULTS_ONLY" -> new AdultsOnly();
            case "SUFFICIENT_BALANCE" -> new SufficientBalance();
            default -> {
                logger.error("Not supported constraint {}", name);
                throw new NotSupportedConstraintException("Not supported constraint: " + name);
            }
        };
    }
}
