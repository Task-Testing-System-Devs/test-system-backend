package testsystem.backend.utilities;

import testsystem.backend.model.user.UserInfoForRatingDownload;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * A utility class for converting a list of {@link testsystem.backend.model.user.UserInfoForRatingDownload} objects
 * to a CSV file.
 */
public class FileService {

    /**
     * Converts a list of {@link testsystem.backend.model.user.UserInfoForRatingDownload} objects to a CSV file.
     * @param headers Headers of the CSV file.
     * @param models List of {@link testsystem.backend.model.user.UserInfoForRatingDownload} objects to be converted.
     * @param filename Name of the CSV file to be created.
     * @throws IOException If an I/O error occurs while writing to the CSV file.
     */
    public void convertToCSV(String headers, List<UserInfoForRatingDownload> models, String filename) throws IOException {
        try (FileWriter csvWriter = new FileWriter(filename)) {
            csvWriter.write(headers + "\n");

            // Convert each model to a CSV row and write it to the file.
            for (var model : models) {
                csvWriter.append(model.getRatingPosition().toString())
                        .append(";")
                        .append(model.getLastName())
                        .append(";")
                        .append(model.getFirstName())
                        .append(";")
                        .append(model.getEmail())
                        .append(";")
                        .append(model.getDepartment())
                        .append(";")
                        .append(model.getGroupName())
                        .append("\n");
            }
        }
    }
}
