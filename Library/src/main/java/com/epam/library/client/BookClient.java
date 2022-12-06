package com.epam.library.client;

import com.epam.library.data.BookDTO;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@FeignClient(name="books",fallbackFactory = BookClientFallbackFactory.class)
@LoadBalancerClient(name="books")
public interface BookClient {
    @GetMapping("books")
    public List<BookDTO> getAllBook();

    @GetMapping("books/{id}")
    public BookDTO getBook(@PathVariable("id") int id);


    @PostMapping("books")
    public BookDTO addBook(@RequestBody @Valid BookDTO bookDTO);

    @DeleteMapping("books/{id}")
    public void deleteBook(@PathVariable("id") int id);

    @PutMapping("books/{id}")
    public BookDTO updateBook(@PathVariable("id") int id, @RequestBody @Valid BookDTO bookDTO);
}