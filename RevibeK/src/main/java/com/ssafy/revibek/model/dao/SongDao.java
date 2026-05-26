package com.ssafy.revibek.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.revibek.model.dto.SongDto;

@Mapper
public interface SongDao {
    int insertSong(SongDto song);
    List<SongDto> selectAllSongs();
    SongDto selectSongById(String id);
    SongDto selectSongByTitle(String title);
    List<SongDto> selectSongsByGenre(String genre);
    List<SongDto> selectRecommendSongs();
    int updateSong(SongDto song);
    int deleteSong(String id);
}