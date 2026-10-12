package com.ecom.repository;

import com.ecom.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    List<Photo> findByProductId(Long productId);

    void deleteByProductId(Long productId);
}
