package domain.repository;

import domain.entity.Guest;

import java.util.List;

public interface IGuestRepository extends IRepository<Guest, Integer> {
    List<Guest> findByName(String name);
}