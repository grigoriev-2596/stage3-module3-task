package com.mjc.school.repository.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepository implements BaseRepository<AuthorModel, Long> {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @PersistenceUnit
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<AuthorModel> readAll() {
        return entityManager.createQuery("select a from AuthorModel a").getResultList();
    }

    @Override
    public Optional<AuthorModel> readById(Long id) {
        return Optional.ofNullable(entityManager.find(AuthorModel.class, id));
    }

    @Override
    public AuthorModel create(AuthorModel entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        return entity;
    }

    @Override
    public AuthorModel update(AuthorModel entity) {
        entityManager.getTransaction().begin();
        AuthorModel toUpdate = entityManager.getReference(AuthorModel.class, entity.getId());
        toUpdate.setName(entity.getName());
        entityManager.getTransaction().commit();
        return toUpdate;
    }

    @Override
    public boolean deleteById(Long id) {
        entityManager.getTransaction().begin();
        boolean isDeleted = entityManager.createQuery("delete from AuthorModel a where a.id=:id")
                .setParameter("id", id)
                .executeUpdate() != 0;
        entityManager.getTransaction().commit();
        return isDeleted;
    }

    @Override
    public boolean existById(Long id) {
        return readById(id).isPresent();
    }
}
