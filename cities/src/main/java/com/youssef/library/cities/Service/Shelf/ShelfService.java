package com.youssef.library.cities.Service.Shelf;

import com.youssef.library.cities.Entities.Shelf;
import com.youssef.library.cities.Enums.Category;

import java.util.List;

public interface ShelfService {
    Shelf saveShelf(Shelf shelf , String libraryId);
    Shelf getShelfById(String id);
    List<Shelf> getShelvesInLibraryById(String libraryId);
    List<Shelf> getShelvesInLibraryByName(String libraryId);
    List<Shelf> getShelvesInLibraryByIdAndCategory(String libraryId, Category category);
    List<Shelf> filterShelvesInLibraryByDesc(String libraryId);
    List<Shelf> filterShelvesInLibraryByAsc(String libraryId);
    List<Shelf> filterShelvesInLibraryByOrder(String libraryId , String order , String orderType , String minOccupied , String maxOccupied , String minCapacity , String maxCapacity);
    List<Shelf> filterShelvesInLibraryWithoutOrder(String libraryId , String minOccupied , String maxOccupied , String minCapacity , String maxCapacity);
}
