package com.dsw_pin.book_list.dtos;

import java.util.Set;
import java.util.UUID;

public record BookRecordDto(String title, UUID publisherId, Set<UUID> authorIds, int publicationYear, String photoUrl, String summary) {



}
