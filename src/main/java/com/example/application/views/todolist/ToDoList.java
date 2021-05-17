package com.example.application.views.todolist;

import com.example.application.views.main.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.util.LinkedList;
import java.util.List;

@Route(value = "list", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("To-Do List")
public class ToDoList extends VerticalLayout {

    HorizontalLayout headerLayout = new HorizontalLayout();
    HorizontalLayout bottomButtonLayout = new HorizontalLayout();
    private Button addTaskButtom;
    private Button completeButton;
    private Button deleteButton;
    private TextField taskTextField;
    private List<Task> itemsToDo = new LinkedList<>();
    Grid<Task> grid = new Grid<>(Task.class);


    public static class Task implements Cloneable {
        private String setTask;
        private boolean done;

        Task(String value, boolean b) {
            setTask = value;
            done = b;
        }

        public String getSetTask() {
            return setTask;
        }

        public void setSetTask(String setTask) {
            this.setTask = setTask;
        }

        public boolean isDone() {
            return done;
        }

        public void setDone(boolean done) {
            this.done = done;
        }
    }

    Task testtask1 = new Task("Take out the trash", false);
    Task testtask2 = new Task("Workout for an hour", false);

    public ToDoList(List<Task> tasks) {

        //Input header layout
        taskTextField = new TextField("Add a task:");
        taskTextField.setMinWidth("60%");
        addTaskButtom = new Button("Add");

        headerLayout.add(taskTextField, addTaskButtom);
        headerLayout.setVerticalComponentAlignment(Alignment.BASELINE, taskTextField, addTaskButtom);

        //grid init
        itemsToDo.add(testtask1);
        itemsToDo.add(testtask2);
        grid.setItems(itemsToDo);
        grid.setColumns("setTask", "done");
        grid.setHeightByRows(true);
        grid.getColumnByKey("setTask").setWidth("80%");
        grid.getColumnByKey("done").setWidth("10%");
        grid.setSelectionMode(SelectionMode.MULTI);


        //general init
        addClassName("list-view");

        add(taskTextField, addTaskButtom);
        add(grid);
        addTaskButtom.addClickListener(e -> {

            //create a bew task ibject
            itemsToDo.add(new Task(taskTextField.getValue(), false));

            Notification.show("Added task: '" + taskTextField.getValue() + "'");
            grid.setItems(itemsToDo);
            reloadGraph();
        });

        //complete and delete buttons

        completeButton = new Button("Mark completed"); completeButton.setThemeName("success primary");
        deleteButton = new Button("Delete"); deleteButton.setThemeName("error primary");

        completeButton.addClickListener(click -> {
            grid.getSelectedItems().forEach(task -> task.setDone(true));
            reloadGraph();
        });

        deleteButton.addClickListener(click -> {
           grid.getSelectedItems().forEach(task -> itemsToDo.remove(task));
           reloadGraph();
        });

        bottomButtonLayout.add(completeButton, deleteButton);
        add(bottomButtonLayout);


    }

    public void reloadGraph() {
        remove(grid); remove(bottomButtonLayout);
        grid.setItems(itemsToDo);
        add(grid,bottomButtonLayout);
    }

}
