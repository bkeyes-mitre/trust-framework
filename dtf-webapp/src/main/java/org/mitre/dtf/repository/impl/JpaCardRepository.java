package org.mitre.dtf.repository.impl;

import static org.mitre.util.jpa.JpaUtil.saveOrUpdate;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.mitre.dtf.model.Card;
import org.mitre.dtf.repository.CardRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository layer for translating Card POJOs to and from database.
 * 
 * @author wkim
 *
 */
@Repository("jpaCardRepository")
public class JpaCardRepository implements CardRepository {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional
	public Set<Card> getAll() {
		
		TypedQuery<Card> query = em.createNamedQuery("Card.findAll", Card.class);

		return new LinkedHashSet<Card>(query.getResultList());
	}
	
	@Override
	@Transactional
	public Card getById(long id) {
		return em.find(Card.class, id);
	}
	
	@Override
	@Transactional
	public void remove(Card card) {
		Card found = em.find(Card.class, card.getId());

		if (found != null) {
			em.remove(card);
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	@Override
	@Transactional
	public Card save(Card card) {
		return saveOrUpdate(card.getId(), em, card);
	}
}
