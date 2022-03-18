package com.spotify.faceapi.utility;

import com.spotify.faceapi.controller.MainController;

import java.util.logging.Logger;

public class TermUtility {

  public static String getTerm(final Integer term) {
    Logger logger = Logger.getLogger(MainController.class.getName());

    try {
      if (term == 0) {
        return "Past Month";
      } else if (term == 1) {
        return "Past 6 Months";
      } else {
        return "All Time";
      }
    } catch (NullPointerException e) {
      logger.info("NullPointerException Caught");
    }
    return "Past Month";
  }

  private TermUtility() {}
}
