package com.ipf.automaticcarsgame.repository;

import com.ipf.automaticcarsgame.domain.GameCar;
import com.ipf.automaticcarsgame.domain.Movement;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class MovementRepositoryCustomImpl implements MovementRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    public List<Movement> findMovements(GameCar gameCar, Integer limit) {
        final Optional<Integer> limitOpt = Optional.ofNullable(limit);

        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Movement> cq = cb.createQuery(Movement.class);
        final Root<Movement> movementRoot = cq.from(Movement.class);

        cq.select(movementRoot);
        cq.where(cb.equal(movementRoot.get("gameCar"), cb.parameter(GameCar.class, "gameCar")));
        cq.orderBy(cb.desc(movementRoot.get("insertDate")));

        final TypedQuery<Movement> query = em.createQuery(cq);
        query.setParameter("gameCar", gameCar);
        limitOpt.ifPresent(lim -> query.setMaxResults(lim));
        return query.getResultList();

    }

}

