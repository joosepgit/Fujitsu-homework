package feedback.fujitsu.feedbacksystem.service;

import feedback.fujitsu.feedbacksystem.model.Category;
import feedback.fujitsu.feedbacksystem.model.Entry;
import feedback.fujitsu.feedbacksystem.model.EntryDto;
import feedback.fujitsu.feedbacksystem.repo.CategoryRepo;
import feedback.fujitsu.feedbacksystem.repo.EntryRepo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EntryServiceImplementation implements EntryService{

    @Resource
    private EntryRepo entryRepo;

    @Resource
    private CategoryRepo categoryRepo;

    @Transactional
    @Override
    public EntryDto addEntry(EntryDto entryDto){
        Entry entry = new Entry();
        mapDtoToEntity(entryDto, entry);
        Entry savedEntry = entryRepo.save(entry);
        return mapEntityToDto(savedEntry);
    }

    @Override
    public List<EntryDto> getAllEntries(){
        List<EntryDto> entryDtos = new ArrayList<>();
        List<Entry> entries = entryRepo.findAll();
        entries.stream().forEach(entry -> {
            EntryDto entryDto = mapEntityToDto(entry);
            entryDtos.add(entryDto);
        });
        return entryDtos;
    }

    @Transactional
    @Override
    public EntryDto updateEntry(Long id, EntryDto entryDto){
        Entry entry = entryRepo.getOne(id);
        entry.getCategories().clear();
        mapDtoToEntity(entryDto, entry);
        Entry savedEntry = entryRepo.save(entry);
        return mapEntityToDto(savedEntry);

    }

    @Override
    public String deleteEntry(Long entryId){
        Optional<Entry> entry = entryRepo.findById(entryId);
        if(entry.isPresent()) {
            entry.get().removeCategories();
            entryRepo.deleteById(entry.get().getId());
            return "Entry with id: " + entryId + " successfully deleted";
        }
        return null;

    }

    private void mapDtoToEntity(EntryDto entryDto, Entry entry) {
        entry.setName(entryDto.getName());
        entry.setEmail(entryDto.getEmail());
        entry.setText(entryDto.getText());
        if (null == entry.getCategories()) {
            entry.setCategories(new HashSet<>());
        }
        entryDto.getCategories().stream().forEach(categoryName -> {
            Category category = categoryRepo.findByName(categoryName);
            if (null == category) {
                category = new Category();
                category.setEntries(new HashSet<>());
            }
            category.setName(categoryName);
            entry.addCategory(category);
        });
    }

    private EntryDto mapEntityToDto(Entry entry) {
        EntryDto responseDto = new EntryDto();
        responseDto.setName(entry.getName());
        responseDto.setId(entry.getId());
        responseDto.setEmail(entry.getEmail());
        responseDto.setText(entry.getText());
        responseDto.setCategories(entry.getCategories().stream().map(Category::getName).collect(Collectors.toSet()));
        return responseDto;
    }

}
