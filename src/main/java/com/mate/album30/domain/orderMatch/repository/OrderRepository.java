package com.mate.album30.domain.orderMatch.repository;

import com.mate.album30.domain.album.entity.Album;
import com.mate.album30.domain.orderMatch.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
