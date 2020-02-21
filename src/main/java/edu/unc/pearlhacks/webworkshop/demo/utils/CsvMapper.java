package edu.unc.pearlhacks.webworkshop.demo.utils;

import edu.unc.pearlhacks.webworkshop.demo.model.ToDoItem;

public class CsvMapper {

  private static final String CSV_SEPARATOR = ",";

  public static final String toCsv(ToDoItem item){
    StringBuffer buffer = new StringBuffer();

    buffer.append(item.getId());
    buffer.append(CSV_SEPARATOR);
    buffer.append(item.getDescription());
    buffer.append(CSV_SEPARATOR);
    buffer.append(item.isCompleted());

    return buffer.toString();
  }

  public static final ToDoItem toObject(String line){
    ToDoItem item = new ToDoItem();

    String[] values = line.split(CSV_SEPARATOR);

    item.setId(Integer.valueOf(values[0]));
    item.setDescription(values[1]);
    item.setCompleted(Boolean.valueOf(values[2]));
    return item;
  }
}
