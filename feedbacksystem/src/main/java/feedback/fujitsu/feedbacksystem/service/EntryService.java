package feedback.fujitsu.feedbacksystem.service;

import feedback.fujitsu.feedbacksystem.model.EntryDto;

import java.util.List;

public interface EntryService {
    public EntryDto addEntry(EntryDto entryDto);
    public List<EntryDto> getAllEntries();
    public EntryDto updateEntry(Long entryId, EntryDto entry);
    public String deleteEntry(Long entryId);
}
