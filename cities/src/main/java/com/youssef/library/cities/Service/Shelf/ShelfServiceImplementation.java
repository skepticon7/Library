package com.youssef.library.cities.Service.Shelf;

import com.youssef.library.cities.Entities.Library;
import com.youssef.library.cities.Entities.Shelf;
import com.youssef.library.cities.Enums.Category;
import com.youssef.library.cities.ExceptionHandlers.LimitExceededException;
import com.youssef.library.cities.ExceptionHandlers.NotFoundException;
import com.youssef.library.cities.ExceptionHandlers.ServerErrorException;
import com.youssef.library.cities.Repository.LibraryRepository;
import com.youssef.library.cities.Repository.ShelfRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ShelfServiceImplementation implements ShelfService{

    private ShelfRepository shelfRepository;
    private LibraryRepository libraryRepository;

    @Override
    public Shelf saveShelf(Shelf shelf, String libraryId) {
        try {
            Library library = libraryRepository.findLibraryById(libraryId);
            if(library == null)
                throw new NotFoundException("Library");
            if(library.getShelvesInLibrary().size() + 1 > library.getShelvesCapacity())
                throw new LimitExceededException("Shelf");
            shelf.setId(UUID.randomUUID().toString());
            library.getShelvesInLibrary().add(shelf);
            shelf.setLibraryForShelf(library);
            return shelfRepository.save(shelf);
        }catch(DataAccessException e){
            throw new ServerErrorException("Internal server error : failed to save the shelf in the database");
        }
    }

    @Override
    public Shelf getShelfById(String shelfId) {
        return shelfRepository.findShelfById(shelfId);
    }


    @Override
    public List<Shelf> getShelvesInLibraryById(String libraryId) {
        Library library = libraryRepository.findLibraryById(libraryId);
        if(library == null)
            throw new NotFoundException("Library");
        return shelfRepository.findShelvesInLibraryById(libraryId);
    }

    @Override
    public List<Shelf> getShelvesInLibraryByName(String libraryName) {
        Library library = libraryRepository.findLibraryByName(libraryName);
        if(library == null)
            throw new NotFoundException("Library");
        return shelfRepository.findShelvesInLibraryByName(libraryName);
    }

    @Override
    public List<Shelf> getShelvesInLibraryByIdAndCategory(String libraryId, Category category) {
        Library library = libraryRepository.findLibraryById(libraryId);
        if(library == null)
            throw new NotFoundException("Library");
        return shelfRepository.findShelfInLibraryByIdAndCategory(libraryId,category);
    }


    @Override
    public List<Shelf> filterShelvesInLibraryByDesc(String libraryId) {
        Library library = libraryRepository.findLibraryById(libraryId);
        if(library == null)
            throw new NotFoundException("Library");
        return shelfRepository.filterByLibraryIdAndDescBooks(libraryId);
    }

    @Override
    public List<Shelf> filterShelvesInLibraryByAsc(String libraryId) {
        Library library = libraryRepository.findLibraryById(libraryId);
        if(library == null)
            throw new NotFoundException("Library");
        return shelfRepository.filterByLibraryIdAndAscBooks(libraryId);
    }

    @Override
    public List<Shelf> filterShelvesInLibraryByOrder(String libraryId, String order, String orderType, String minOccupied, String maxOccupied, String minCapacity, String maxCapacity) {
        Library library = libraryRepository.findLibraryById(libraryId);
        if(library == null)
            throw new NotFoundException("Library");
        if(orderType.equals("capacity")){
            if("DESC".equalsIgnoreCase(order))
                return shelfRepository.filterShelvesInLibraryByCapacityDesc(libraryId,minOccupied,maxOccupied,minCapacity,maxCapacity);
            return shelfRepository.filterShelvesInLibraryByCapacityAsc(libraryId,minOccupied,maxOccupied,minCapacity,maxCapacity);
        }else{
            if("DESC".equalsIgnoreCase(order))
                return shelfRepository.filterShelvesInLibraryByOccupiedDesc(libraryId,minOccupied,maxOccupied,minCapacity,maxCapacity);
            return shelfRepository.filterShelvesInLibraryByOccupiedAsc(libraryId,minOccupied,maxOccupied,minCapacity,maxCapacity);
        }
    }

    @Override
    public List<Shelf> filterShelvesInLibraryWithoutOrder(String libraryId, String minOccupied, String maxOccupied, String minCapacity, String maxCapacity) {
        Library library = libraryRepository.findLibraryById(libraryId);
        if(library == null)
            throw new NotFoundException("Library");
        return shelfRepository.findShelvesInLibraryByFilters(libraryId,minOccupied,maxOccupied,minCapacity,maxCapacity);
    }
}
