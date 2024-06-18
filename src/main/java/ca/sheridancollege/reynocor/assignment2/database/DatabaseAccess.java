package ca.sheridancollege.reynocor.assignment2.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.reynocor.assignment2.beans.Note;

@Repository
public class DatabaseAccess {
    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    public void addNote(Note note){
        MapSqlParameterSource namedParams = new MapSqlParameterSource();
        namedParams.addValue("noteName", note.getName());
        namedParams.addValue("noteDesc", note.getDesc());
        namedParams.addValue("noteDate", note.getNote_date());
        String query = "INSERT INTO Notes(name, desc, note_date) VALUES (:noteName, :noteDesc, :noteDate)";
        int rowsAffected = jdbc.update(query, namedParams);
        if (rowsAffected > 0)
            System.out.println("Note Added.");
    }

    public void deleteNote(Long id){
        MapSqlParameterSource namedParams = new MapSqlParameterSource();
        namedParams.addValue("id", id);
        String query = "DELETE FROM Notes WHERE id = :id";
        if (jdbc.update(query, namedParams) > 0)
            System.out.println("Note deleted.");
    }

    public void editNote(Note note){
        MapSqlParameterSource namedParams = new MapSqlParameterSource();
        namedParams.addValue("id", note.getId());
        namedParams.addValue("name", note.getName());
        namedParams.addValue("desc", note.getDesc());
        String query = "UPDATE Notes SET name = :name, desc = :desc WHERE id = :id";
        int rowsAffected = jdbc.update(query, namedParams);
        if (rowsAffected > 0)
            System.out.println("Note updated.");
    }

    public List<Note> getNoteList(){
        MapSqlParameterSource namedParams = new MapSqlParameterSource();
        String query = "SELECT * FROM Notes";
        return jdbc.query(query, namedParams, new BeanPropertyRowMapper<Note>(Note.class));
    };

    public List<Note> getNoteListById(Long id){
        MapSqlParameterSource namedParams = new MapSqlParameterSource();
        namedParams.addValue("id", id);
        String query = "SELECT * FROM Notes WHERE id = :id";
        return jdbc.query(query, namedParams, new BeanPropertyRowMapper<Note>(Note.class));
    }

    public List<Note> getNoteListFilter(String nameOrDescFilter){
        MapSqlParameterSource namedParams = new MapSqlParameterSource();
        namedParams.addValue("nameOrDescFilter", "%" + (nameOrDescFilter !=null ? nameOrDescFilter.toLowerCase() : "") + "%");
        String query = "SELECT * FROM Notes WHERE lower(name) LIKE :nameOrDescFilter OR lower(desc) LIKE :nameOrDescFilter";
        return jdbc.query(query, namedParams, new BeanPropertyRowMapper<Note>(Note.class));
    }
}