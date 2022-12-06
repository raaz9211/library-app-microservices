package com.epam.library.repository;

import com.epam.library.data.Library;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface LibraryRepository extends CrudRepository<Library, String> {
    @Transactional
    Optional<Library> findByUsernameAndBookId(String username, int bookId);
    @Transactional
    List<Library> findAllByUsername(String username);
    @Transactional
    int countByUsername(String username);

}
