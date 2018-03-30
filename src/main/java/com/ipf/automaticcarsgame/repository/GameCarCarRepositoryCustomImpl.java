package com.ipf.automaticcarsgame.repository;

import com.ipf.automaticcarsgame.domain.GameCar;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GameCarCarRepositoryCustomImpl implements GameCarRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<GameCar> findGamesCar(List<Integer> gameIds, List<String> mapNames, List<String> carNames) {
        final List<Predicate> andPredcateList = new ArrayList<>();
        final CriteriaBuilder cb = em.getCriteriaBuilder();

        final CriteriaQuery<GameCar> cq = cb.createQuery(GameCar.class);
        final Root<GameCar> gameRoot = cq.from(GameCar.class);
        if (!CollectionUtils.isEmpty(mapNames))
            andPredcateList.add(gameRoot.get("game").get("roadmap").get("name").in(cb.parameter(List.class, "mapNames")));
        if (!CollectionUtils.isEmpty(gameIds))
            andPredcateList.add(gameRoot.get("game").get("id").in(cb.parameter(List.class, "gameIds")));
        if (!CollectionUtils.isEmpty(carNames))
            andPredcateList.add(gameRoot.get("car").get("name").in(cb.parameter(List.class, "carNames")));
        cq.where(cb.and(andPredcateList.toArray(new Predicate[andPredcateList.size()])));

        final TypedQuery<GameCar> query = em.createQuery(cq);
        if (!CollectionUtils.isEmpty(mapNames)) query.setParameter("mapNames", mapNames);
        if (!CollectionUtils.isEmpty(gameIds)) query.setParameter("gameIds", gameIds);
        if (!CollectionUtils.isEmpty(carNames)) query.setParameter("carNames", carNames);
        return query.getResultList();
    }

}

