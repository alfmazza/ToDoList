package com.alpi.todolist.logic;

import com.alpi.todolist.persistence.PersistenceController;
import java.util.List;

public class LogicController {
    
    PersistenceController persController = new PersistenceController();

    public void save(String taskName, String taskDesc) {
        
      Tasks task = new Tasks();
      task.setTitle(taskName);
      task.setDescription(taskDesc);
      
      persController.save(task);
    }

    public List<Tasks> bringTable() {
        
        return persController.bringTasks();
    }

    public void deleteTask(int taskID) {
        persController.deleteTask(taskID);
    }

    public Tasks bringTask(int taskID) {
        
        return persController.bringTask(taskID);
    }

    public void editTask(Tasks task, String taskName, String taskDesc) {
        
        persController.editTask(task);
        
        Tasks taskid = this.searchTaskId(task.getId());
        task.setTitle(taskName);
        task.setDescription(taskDesc);
        task.getId();
        
        this.taskEdit(taskid);
        
        
    }

    private Tasks searchTaskId(int taskId) {
        
        return persController.bringTaskId(taskId);
    }

    private void taskEdit(Tasks taskid) {
        
        persController.taskEdit(taskid);
    }

    

    
}
