package com.mjc.school.repository.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepository implements BaseRepository<AuthorModel, Long> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<AuthorModel> readAll() {
        return entityManager.createQuery("select a from AuthorModel a").getResultList();
    }

    @Override
    public Optional<AuthorModel> readById(Long id) {
        return Optional.ofNullable(entityManager.find(AuthorModel.class, id));
    }

    @Transactional
    @Override
    public AuthorModel create(AuthorModel entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    @Override
    public AuthorModel update(AuthorModel entity) {
        Optional<AuthorModel> maybeNull = readById(entity.getId());
        if (maybeNull.isEmpty()) {
            return null;
        }
        AuthorModel toUpdate = maybeNull.get();
        toUpdate.setName(entity.getName());
        return toUpdate;
    }

    @Transactional
    @Override
    public boolean deleteById(Long id) {
        Optional<AuthorModel> maybeNullAuthor = readById(id);
        if (maybeNullAuthor.isEmpty()) {
            return false;
        }
        entityManager.remove(maybeNullAuthor.get());
        return true;
    }

    @Transactional
    public AuthorModel getAuthorByNewsId(Long newsId) {
        NewsModel newsModel = entityManager.find(NewsModel.class, newsId);
        if (newsModel == null) {
            return null;
        }
        return newsModel.getAuthor();
    }

    @Override
    public boolean existById(Long id) {
        return readById(id).isPresent();
    }
}
