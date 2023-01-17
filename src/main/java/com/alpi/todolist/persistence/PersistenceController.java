package com.alpi.todolist.persistence;

import com.alpi.todolist.logic.Tasks;
import com.alpi.todolist.persistence.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenceController {
    
    TasksJpaController tasksJpa = new TasksJpaController();

    public void save(Tasks task) {

        tasksJpa.create(task);
    }

    public List<Tasks> bringTasks() {

        return tasksJpa.findTasksEntities();
    }

    public void deleteTask(int taskID) {
        try {
            tasksJpa.destroy(taskID);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Tasks bringTask(int taskID) {
        return tasksJpa.findTasks(taskID);
    }

    public void editTask(Tasks task) {
        
        try {
            tasksJpa.edit(task);
        } catch (Exception ex) {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Tasks bringTaskId(int taskId) {
        
        return tasksJpa.findTasks(taskId);
    }
    

    public void taskEdit(Tasks taskid) {
        
        try {
            tasksJpa.edit(taskid);
        } catch (Exception ex) {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
