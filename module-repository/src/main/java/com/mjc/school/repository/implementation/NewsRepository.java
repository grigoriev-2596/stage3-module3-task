package com.mjc.school.repository.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.NewsModel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;


@Repository
public class NewsRepository implements BaseRepository<NewsModel, Long> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<NewsModel> readAll() {
        return entityManager.createQuery("select n from NewsModel n").getResultList();
    }

    @Override
    public Optional<NewsModel> readById(Long id) {
        return Optional.ofNullable(entityManager.find(NewsModel.class, id));
    }

    @Transactional
    @Override
    public NewsModel create(NewsModel entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    @Override
    public NewsModel update(NewsModel entity) {
        Optional<NewsModel> maybeNull = readById(entity.getId());
        if (maybeNull.isEmpty()) {
            return null;
        }
        NewsModel toUpdate = maybeNull.get();
        toUpdate.setTitle(entity.getTitle());
        toUpdate.setContent(entity.getContent());
        toUpdate.setLastUpdateDate(entity.getLastUpdateDate());
        toUpdate.setAuthor(entity.getAuthor());
        toUpdate.setTags(entity.getTags());
        return toUpdate;
    }

    @Transactional
    @Override
    public boolean deleteById(Long id) {
        return entityManager.createQuery("delete from NewsModel n where n.id=:id")
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public boolean existById(Long id) {
        return readById(id).isPresent();
    }
}
