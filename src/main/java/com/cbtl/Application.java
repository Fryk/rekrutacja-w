package com.cbtl;

import com.cbtl.domain.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class Application {
    public static final Logger logger = LogManager.getLogger(Application.class);
    public static void main(String[] args) {
        Client lombokTestClient = Client.builder().vip(false).birthDate(LocalDate.now()).build();
        logger.info("Test message");
        logger.debug(lombokTestClient);
    }
}
