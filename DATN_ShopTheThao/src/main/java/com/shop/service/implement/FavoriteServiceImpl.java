package com.shop.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shop.entity.Favorite;
import com.shop.repository.FavoriteRepository;
import com.shop.service.FavoriteService;

@Service
public class FavoriteServiceImpl implements FavoriteService{
	
    private final FavoriteRepository favoriteRepository;
	
    public FavoriteServiceImpl(FavoriteRepository favoriteRepository) {
		this.favoriteRepository = favoriteRepository;
	}

	@Override
    public Page<Favorite> findAllFavorites(Pageable pageable) {
        return favoriteRepository.findAll(pageable);
    }
    
    @Override
    public List<Favorite> findAllFavorites() {
        return favoriteRepository.findAll();
    }

    @Override
    public Optional<Favorite> findFavoriteById(Integer favoriteId) {
        return favoriteRepository.findById(favoriteId);
    }

    @Override
    public Favorite createFavorite(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    @Override
    public Favorite updateFavorite(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    @Override
    public void deleteFavorite(Integer favoriteId) {
        favoriteRepository.deleteById(favoriteId);
    }
    
    @Override
	public List<Favorite> findByUsername(String userName) {
		return favoriteRepository.findByUsername(userName);
	}
}