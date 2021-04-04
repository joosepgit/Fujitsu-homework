package feedback.fujitsu.feedbacksystem;

import feedback.fujitsu.feedbacksystem.model.EntryDto;
import feedback.fujitsu.feedbacksystem.service.EntryService;
import feedback.fujitsu.feedbacksystem.service.EntryServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EntryController {

    @Autowired
    private EntryService entryService;

    @GetMapping("/entries")
    public ResponseEntity<List<EntryDto>> getAllEntries() {
        List<EntryDto> entries = entryService.getAllEntries();
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    @PostMapping("/entry")
    public ResponseEntity<EntryDto> getAllEntries(@RequestBody EntryDto entryDto){
        EntryDto entry = entryService.addEntry(entryDto);
        return new ResponseEntity<>(entry, HttpStatus.CREATED);
    }

    @PutMapping("/entry/{id}/update")
    public ResponseEntity<EntryDto> updateEntry(@PathVariable(name = "id") Long id,
                                                @RequestBody EntryDto entry){
        EntryDto entryDto = entryService.updateEntry(id, entry);
        return new ResponseEntity<>(entryDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/entry/{id}/delete")
    public ResponseEntity<Void> deleteEntry(@PathVariable(name = "id") Long id){
        String message = entryService.deleteEntry(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
