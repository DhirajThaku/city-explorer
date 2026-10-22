package com.cityexplorer.backend.favorite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cityexplorer.backend.favorite.entity.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    boolean existsByUser_IdAndPlace_Id(Long userId, Long placeId);

    List<Favorite> findByUser_Id(Long userId);
}
