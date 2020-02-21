package edu.unc.pearlhacks.webworkshop.demo.controller;

import edu.unc.pearlhacks.webworkshop.demo.model.ToDoItem;
import edu.unc.pearlhacks.webworkshop.demo.utils.CsvMapper;
import edu.unc.pearlhacks.webworkshop.demo.utils.FileIOUtils;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("todo-list")
public class ToDoListController {

  @GetMapping(value = "/")
  public List<ToDoItem> getList() {
    List<String> fileLines = FileIOUtils.readAll();
    List<ToDoItem> itemList = fileLines.stream()
        .map(line -> CsvMapper.toObject(line))
        .collect(Collectors.toList());
    return itemList;
  }

  @GetMapping(value = "/item/{id}")
  public ToDoItem getItem(@PathVariable("id") Integer id) {
    String fileLine = FileIOUtils.read(id);
    ToDoItem item = CsvMapper.toObject(fileLine);
    return item;
  }

  @PostMapping(value = "/item")
  public void saveItem(@RequestBody ToDoItem item) {
    item.setId(FileIOUtils.nextId());
    String newLine = CsvMapper.toCsv(item);
    FileIOUtils.write(newLine);
    //return item;
  }

  @PutMapping(value = "/item")
  public ToDoItem updateItem(@RequestBody ToDoItem item) {
    String updatedLine = CsvMapper.toCsv(item);
    FileIOUtils.update(updatedLine);
    return item;
  }

  @DeleteMapping(value = "/item/{id}")
  public void removeItem(@PathVariable("id") Integer id) {
    FileIOUtils.delete(id);
  }

  @PutMapping(value = "/item/{id}/mark-complete")
  public ToDoItem completeItem(@PathVariable("id") Integer id) {
    ToDoItem item = getItem(id);
    item.setCompleted(true);
    updateItem(item);
    return item;
  }

  @PutMapping(value = "/item/{id}/mark-uncomplete")
  public ToDoItem uncompleteItem(@PathVariable("id") Integer id) {
    ToDoItem item = getItem(id);
    item.setCompleted(false);
    updateItem(item);
    return item;
  }
}
