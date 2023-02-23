package com.mjc.school.repository.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.TagModel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;
import java.util.Optional;

@Repository
public class TagRepository implements BaseRepository<TagModel, Long> {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @PersistenceUnit
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TagModel> readAll() {
        return entityManager.createQuery("select t from TagModel t").getResultList();
    }

    @Override
    public Optional<TagModel> readById(Long id) {
        return Optional.ofNullable(entityManager.find(TagModel.class, id));
    }

    @Override
    public TagModel create(TagModel entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        return entity;
    }

    @Override
    public TagModel update(TagModel entity) {
        entityManager.getTransaction().begin();
        TagModel toUpdate = entityManager.getReference(TagModel.class, entity.getId());
        toUpdate.setName(entity.getName());
        entityManager.getTransaction().commit();
        return toUpdate;
    }

    @Override
    public boolean deleteById(Long id) {
        entityManager.getTransaction().begin();
        boolean isDeleted = entityManager.createQuery("delete from TagModel t where t.id=:id")
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
