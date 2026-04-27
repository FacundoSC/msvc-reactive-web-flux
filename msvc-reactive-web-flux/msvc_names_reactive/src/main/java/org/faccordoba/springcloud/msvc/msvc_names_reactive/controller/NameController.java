package org.faccordoba.springcloud.msvc.msvc_names_reactive.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class NameController {

    @GetMapping(value = "/names", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_EVENT_STREAM_VALUE})
  public Flux<String> getAllNames() {
      List<String> names = List.of("Alice", "Bob", "Charlie", "David", "Eve");
    return Flux.fromIterable(names)
                .delayElements(java.time.Duration.ofSeconds(1));
  }




}
