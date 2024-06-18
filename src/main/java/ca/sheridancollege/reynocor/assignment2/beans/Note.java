package ca.sheridancollege.reynocor.assignment2.beans;

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Note {
    private Long id;
    private String name;
    private String desc;
    private LocalDate note_date;
    private String nameOrDescFilter;
}