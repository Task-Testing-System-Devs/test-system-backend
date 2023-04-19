package testsystem.backend.utilities;

import testsystem.backend.model.user.UserInfoForRatingDownload;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileService {

    public void convertToCSV(String headers, List<UserInfoForRatingDownload> models, String filename) throws IOException {
        try (FileWriter csvWriter = new FileWriter(filename)) {
            csvWriter.write(headers + "\n");

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
