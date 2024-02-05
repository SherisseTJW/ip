package Oak.controller;

import Oak.exceptions.InvalidInputException;
import Oak.feedback.FeedbackService;
import Oak.feedback.enums.CommandEnum;

import java.io.IOException;
import java.util.Scanner;

/**
 * The type Terminal controller.
 */
public class TerminalController {
    private FeedbackService feedbackService = new FeedbackService();

    /**
     * Print welcome message and start listening for user inputs
     */
    public void start() {
        System.out.println(this.feedbackService.getWelcomeMessage());
        this.listen();
    }

    /**
     * Start listening for user inputs and prints out the messages obtained from FeedbackService
     * Exits when the user inputs 'bye'
     */
    private void listen() {
        Scanner scanner = new Scanner(System.in);
        boolean stop = false;

        String feedback = null;

        while (!stop) {
            String curInput = scanner.nextLine();

            if (curInput.equals(CommandEnum.Bye.getCommandValue())) {
                stop = true;
            }

            try {
                feedback = feedbackService.run(curInput);
            }
            catch (InvalidInputException | IOException e) {
                System.out.println(e.getMessage());
                continue;
            }

            System.out.println("----------------------------------------------");
            System.out.println(feedback);
            System.out.println("----------------------------------------------");
        }
    }
}