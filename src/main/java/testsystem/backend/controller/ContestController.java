package testsystem.backend.controller;

import org.graalvm.polyglot.*;
import org.graalvm.polyglot.proxy.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/contest")
public class ContestController {


    @GetMapping("/get-info")
    public ResponseEntity<?> getContestInfo() throws IOException {
        Map<String, String> options = new HashMap<>();
// Enable CommonJS experimental support.
        options.put("js.commonjs-require", "true");

        options.put("js.commonjs-require-cwd", "/Users/shvetsovdanil/Desktop/puppeteer_test");


// Create context with IO support and experimental options.
        Context cx = Context.newBuilder("js")
                .allowExperimentalOptions(true)
                .allowIO(true)
                .options(options)
                .build();
// Require a module


        BufferedReader reader = new BufferedReader(new FileReader("/Users/shvetsovdanil/Desktop/puppeteer_test/auth.js"));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        String ls = System.getProperty("line.separator");
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }
// delete the last new line separator
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        reader.close();

        String JS_CODE = "(" + stringBuilder + ")";


        Value module = cx.eval("js", JS_CODE);
        module.execute();

        return ResponseEntity.ok().body("");
        /*try {
            return

        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }*/
    }

}
