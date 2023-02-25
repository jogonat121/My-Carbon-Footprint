package ui.menu;

import data.UserRecords;
import data.exceptions.CannotAccessDataException;
import data.exceptions.RecordNotFoundException;

import java.util.Scanner;

public class DeleteRecordMenu extends Menu {
    private final Scanner input;

    public DeleteRecordMenu() throws CannotAccessDataException {
        super("Delete Record");
        input = new Scanner(System.in);
        runMenu();
    }

    private void runMenu() throws CannotAccessDataException {
        System.out.println("Enter unique ID of the record to be deleted (Enter 'q' to go back)");

        UserRecords userRecords;
        userRecords = new UserRecords();
        String recordID = input.next();
        try {
            if (!recordID.equalsIgnoreCase("q")) {
                userRecords.removeRecord(recordID, false);
            }
        } catch (RecordNotFoundException e) {
            System.out.println(e.getMessage());
            runMenu();
        }
    }
}
