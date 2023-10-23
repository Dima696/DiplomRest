package ru.netodology.backendnet.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import ru.netodology.backendnet.dto.FileRs;
import ru.netodology.backendnet.model.User;
import ru.netodology.backendnet.model.UserFile;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<UserFile, Integer> {
    Optional<UserFile> findByUserIdAndFilename(Integer userId, String filename);

    List<FileRs> findByUserId(Integer userId, Pageable pageable);

    boolean existsByFilename(String filename);

    @Modifying
    void deleteByFilename(String filename);

    boolean existsByFilenameAndUserId(String filename, Integer userId);
}
