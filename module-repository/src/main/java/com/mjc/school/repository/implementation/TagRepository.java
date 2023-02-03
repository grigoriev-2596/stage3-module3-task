package com.mjc.school.repository.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TagRepository implements BaseRepository<TagModel, Long> {
    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public List<TagModel> readAll() {
        return entityManager.createQuery("select t from TagModel t").getResultList();
    }

    @Override
    public Optional<TagModel> readById(Long id) {
        return Optional.ofNullable(entityManager.find(TagModel.class, id));
    }

    @Transactional
    @Override
    public TagModel create(TagModel entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    @Override
    public TagModel update(TagModel entity) {
        Optional<TagModel> maybeNullTag = readById(entity.getId());
        if (maybeNullTag.isEmpty()) {
            return null;
        }
        TagModel toUpdate = maybeNullTag.get();
        toUpdate.setName(entity.getName());
        return toUpdate;
    }

    @Transactional
    @Override
    public boolean deleteById(Long id) {
        return entityManager.createQuery("delete from TagModel where id=:id")
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    public List<TagModel> getTagsByNewsId(Long newsId) {
        NewsModel newsModel = entityManager.find(NewsModel.class, newsId);
        if (newsModel == null) {
            return null;
        }
        return newsModel.getTags();
    }

    @Override
    public boolean existById(Long id) {
        return readById(id).isPresent();
    }
}