package com.mjc.school.repository.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.NewsModel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;
import java.util.Optional;


@Repository
public class NewsRepository implements BaseRepository<NewsModel, Long> {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @PersistenceUnit
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<NewsModel> readAll() {
        return entityManager.createQuery("select n from NewsModel n").getResultList();
    }

    @Override
    public Optional<NewsModel> readById(Long id) {
        return Optional.ofNullable(entityManager.find(NewsModel.class, id));
    }

    @Override
    public NewsModel create(NewsModel entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        return entity;
    }

    @Override
    public NewsModel update(NewsModel entity) {
        entityManager.getTransaction().begin();
        NewsModel toUpdate = entityManager.getReference(NewsModel.class, entity.getId());
        toUpdate.setTitle(entity.getTitle());
        toUpdate.setContent(entity.getContent());
        toUpdate.setAuthor(entity.getAuthor());
        toUpdate.setTags(entity.getTags());
        entityManager.getTransaction().commit();
        return toUpdate;
    }

    @Override
    public boolean deleteById(Long id) {
        entityManager.getTransaction().begin();
        boolean isDeleted = entityManager.createQuery("delete from NewsModel n where n.id=:id")
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
