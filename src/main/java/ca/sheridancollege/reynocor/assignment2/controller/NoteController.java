package ca.sheridancollege.reynocor.assignment2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import ca.sheridancollege.reynocor.assignment2.beans.Note;
import ca.sheridancollege.reynocor.assignment2.database.DatabaseAccess;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteController {
    @Autowired
    private DatabaseAccess da;

    @GetMapping("/")
    public String index(Model model) {
        Note note = new Note();
        model.addAttribute("note", note);
        model.addAttribute("noteList", da.getNoteList());
        return "index";
    }
    
    @PostMapping("/addNote")
    public String addNote(Model model, @ModelAttribute Note note) {
        da.addNote(note);
        model.addAttribute("note", new Note());
        model.addAttribute("noteList", da.getNoteList());
        return "index";
    }
    
    @GetMapping("/deleteNote/{id}")
    public String deleteNote(@PathVariable Long id, Model model) {
        da.deleteNote(id);
        model.addAttribute("noteList", da.getNoteList());
        return "redirect:/";
    }
    
    @GetMapping("/editNote/{id}")
    public String editNote(@PathVariable Long id, Model model) {
        List<Note> noteList = da.getNoteListById(id);
        if (!noteList.isEmpty()){
            Note note = noteList.get(0);
            model.addAttribute("note", note);
        }
        model.addAttribute("noteList", da.getNoteList());
        return "index";
    }
    
    @PostMapping("/getNoteListFilter")
    public String getNoteListFilter(@ModelAttribute Note note, Model model) {
        String nameOrDescFilter = note.getNameOrDescFilter();
        model.addAttribute("noteListFilter", da.getNoteListFilter(nameOrDescFilter));
        model.addAttribute("noteList", da.getNoteList());
        return "index";
    }
    
    @PostMapping("/addOrEditNote")
    public String addOrEditNote(@ModelAttribute Note note, Model model) {
        if (note.getId() == null){
            da.addNote(note);
        } else {
            da.editNote(note);
        }
        model.addAttribute("note", new Note());
        model.addAttribute("noteList", da.getNoteList());
        return "redirect:/";
    }
}
