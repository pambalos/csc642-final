package com.example.application.views.worktracker;

import com.example.application.views.main.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Route(value = "tracker", layout = MainView.class)
@PageTitle("Work Tracker")
public class WorkTracker extends Div {

    VerticalLayout pageLayout = new VerticalLayout();
    TextArea inputField = new TextArea();
    LinkedList<WorkEntry> entries = new LinkedList<>();
    Grid<WorkEntry> diary = new Grid<>(WorkEntry.class);

    public WorkTracker() {
        addClassName("tracker-view");
        //Create the different options
        List<String> options = new LinkedList<>();
        options.add("Got a lot of work done!");
        options.add("Was decently productive");
        options.add("Didn't get much done");
        RadioButtonGroup<String> selection = new RadioButtonGroup<>();
        selection.setLabel("How much did you get done today?");
        selection.setItems(options);
        diary.setHeightByRows(true);
        //input area for description

        inputField.setWidth("90%");
        inputField.setLabel("Entry");


        //save button
        Button saveEntry = new Button("Save Entry");
        saveEntry.addClickListener(e -> {
            entries.add(new WorkEntry(selection.getValue(), inputField.getValue()));
            reloadGrid();
        });

        //history page if exists
        if (!entries.isEmpty()) {
            diary.setItems(entries);
            add(diary);
        }
        pageLayout.add(selection, inputField, saveEntry);
        add(pageLayout);

    }

    private void reloadGrid() {
        diary.setColumns("mood", "entry");
        remove(diary);
        diary.setItems(entries);
        add(diary);
    }

    public class WorkEntry {
        private String mood;
        private String entry;

        public WorkEntry(String label, String value) {
            mood = label;
            entry = value;
        }

        public String getMood() {
            return mood;
        }

        public void setMood(String mood) {
            this.mood = mood;
        }

        public String getEntry() {
            return entry;
        }

        public void setEntry(String entry) {
            this.entry = entry;
        }
    }

}
