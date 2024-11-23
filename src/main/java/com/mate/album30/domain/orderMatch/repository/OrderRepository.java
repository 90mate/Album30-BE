package com.mate.album30.domain.orderMatch.repository;

import com.mate.album30.domain.album.entity.Album;
import com.mate.album30.domain.common.enums.OrderStatus;
import com.mate.album30.domain.common.enums.Role;
import com.mate.album30.domain.orderMatch.entity.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.role = :role AND o.orderStatus = :isCompleted")
    List<Order> findByRoleAndOrderStatus(@Param("role") Role role,
                                         @Param("orderStatus") OrderStatus orderStatus,
                                         Sort sort);

    List<Order> findByRole(Role role);
}
