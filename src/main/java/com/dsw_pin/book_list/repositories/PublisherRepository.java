package com.dsw_pin.book_list.repositories;

import com.dsw_pin.book_list.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PublisherRepository extends JpaRepository<Publisher, UUID> {
}
