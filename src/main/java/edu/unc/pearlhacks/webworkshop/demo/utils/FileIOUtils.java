package edu.unc.pearlhacks.webworkshop.demo.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileIOUtils {

  private static final String PATH_TO_FILE = "/Users/bpatlan/DevEnvironment/demo/src/main/resources/persistence/ToDoList.csv";
  private static final String CSV_SEPARATOR = ",";

  public static List<String> readAll() {
    List<String> lines = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(PATH_TO_FILE))) {
      String line;

      while ((line = br.readLine()) != null) {
        lines.add(line);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    return lines;
  }

  public static String read(Integer id) {
    List<String> lines = readAll();
    String line = null;

    for (String lineOfFile : lines) {
      if (lineOfFile.startsWith(id + CSV_SEPARATOR)) {
        line = lineOfFile;
        break;
      }
    }
    return line;
  }

  public static void writeAll(List<String> lines) {
    try (FileWriter fileWriter = new FileWriter(PATH_TO_FILE, false)) {
      boolean firstLine = true;
      fileWriter.write("");
      for (String line : lines) {
        if (!firstLine) {
          fileWriter.append("\n");
        }
        fileWriter.append(line);
        firstLine = false;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void write(String line) {
    try (FileWriter fileWriter = new FileWriter(PATH_TO_FILE, true)) {
      fileWriter.append("\n");
      fileWriter.append(line);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void delete(Integer id) {
    List<String> lines = readAll();

    for (String line : lines) {
      if (line.startsWith(id + CSV_SEPARATOR)) {
        lines.remove(line);
        break;
      }
    }
    writeAll(lines);
  }

  public static void update(String line) {
    String[] values = line.split(CSV_SEPARATOR);
    Integer id = Integer.valueOf(values[0]);
    delete(id);
    write(line);
  }
}
